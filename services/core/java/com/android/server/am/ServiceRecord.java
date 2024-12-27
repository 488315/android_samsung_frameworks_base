package com.android.server.am;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerExemptionManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;

import com.android.internal.app.procstats.ServiceState;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.notification.NotificationManagerService;
import com.android.server.uri.GrantUri;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriPermission;
import com.android.server.uri.UriPermissionOwner;
import com.android.server.wm.ActivityServiceConnectionsHolder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ServiceRecord extends Binder implements ComponentName.WithComponentName {
    public boolean allowlistManager;
    public final ActivityManagerService ams;
    public ProcessRecord app;
    public ApplicationInfo appInfo;
    public boolean callStart;
    public int crashCount;
    public final long createRealTime;
    public boolean createdFromFg;
    public final String definingPackageName;
    public final int definingUid;
    public int delayCount;
    public boolean delayService;
    public boolean delayServiceStop;
    public long delayTimeout;
    public boolean delayed;
    public boolean delayedStop;
    public long destroyTime;
    public boolean destroying;
    public boolean executeFg;
    public int executeNesting;
    public long executingStart;
    public final boolean exported;
    public long fgDisplayTime;
    public boolean fgRequired;
    public boolean fgWaiting;
    public int foregroundId;
    public Notification foregroundNoti;
    public int foregroundServiceType;
    public final boolean inSharedIsolatedProcess;
    public final ComponentName instanceName;
    public final Intent.FilterComparison intent;
    public boolean isForeground;
    public boolean isNotAppComponentUsage;
    public final boolean isSdkSandbox;
    public ProcessRecord isolationHostProc;
    public long lastActivity;
    public int lastStartId;
    public long lastTopAlmostPerceptibleBindRequestUptimeMs;
    public int mAdjSeq;
    public boolean mAllowUiJobScheduling;
    public boolean mAllowWhileInUsePermissionInFgsAtEntering;
    public ProcessRecord mAppForAllowingBgActivityStartsByStart;
    public ServiceRecord$$ExternalSyntheticLambda0 mCleanUpAllowBgActivityStartsByStartCallback;
    public long mEarliestRestartTime;
    public ForegroundServiceDelegation mFgsDelegation;
    public boolean mFgsHasNotificationPermission;
    public boolean mFgsNotificationDeferred;
    public boolean mFgsNotificationShown;
    public boolean mFgsNotificationWasDeferred;
    public String mInfoAllowStartForeground;
    public ActivityManagerService.FgsTempAllowListItem mInfoTempFgsAllowListReason;
    public boolean mIsAllowedBgActivityStartsByBinding;
    public boolean mIsFgsDelegate;
    public boolean mKeepWarming;
    public boolean mLoggedInfoAllowStartForeground;
    public int mProcessStateOnRequest;
    public ApplicationInfo mRecentCallerApplicationInfo;
    public String mRecentCallingPackage;
    public int mRecentCallingUid;
    public long mRestartSchedulingTime;
    public ShortFgsInfo mShortFgsInfo;
    public int mStartForegroundCount;
    public final ComponentName name;
    public long nextRestartTime;
    public final String packageName;
    public int pendingConnectionGroup;
    public int pendingConnectionImportance;
    public final String permission;
    public final String processName;
    public int restartCount;
    public long restartDelay;
    public long restartTime;
    public ServiceState restartTracker;
    public final Runnable restarter;
    public final String sdkSandboxClientAppPackage;
    public final int sdkSandboxClientAppUid;
    public boolean serviceDelayed;
    public final ServiceInfo serviceInfo;
    public final String shortInstanceName;
    public int startCommandResult;
    public boolean startRequested;
    public long startingBgTimeout;
    public boolean stopIfKilled;
    public String stringName;
    public ServiceState tracker;
    public final int userId;
    public final ArrayMap bindings = new ArrayMap();
    public final ArrayMap connections = new ArrayMap();
    public final ArrayList mBackgroundStartPrivilegesByStart = new ArrayList();
    public BackgroundStartPrivileges mBackgroundStartPrivilegesByStartMerged =
            BackgroundStartPrivileges.NONE;
    public int mAllowWiu_noBinding = -1;
    public long mFgsEnterTime = 0;
    public long mFgsExitTime = 0;
    public int mAllowStart_noBinding = -1;
    public int mAllowStartForegroundAtEntering = -1;
    public int mAllowWiu_inBindService = -1;
    public int mAllowWiu_byBindings = -1;
    public int mAllowStart_inBindService = -1;
    public int mAllowStart_byBindings = -1;
    public final ArrayList pendingBinds = new ArrayList();
    public final ArrayList deliveredStarts = new ArrayList();
    public final ArrayList pendingStarts = new ArrayList();
    public final ArrayList pendingRemoveConnections = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BindItem {
        public final IApplicationThread caller;
        public final String callingPackage;
        public final IServiceConnection connection;
        public final long flags;
        public final String instanceName;
        public final boolean isSdkSandboxService;
        public final String resolvedType;
        public final String sdkSandboxClientAppPackage;
        public final int sdkSandboxClientAppUid;
        public final IApplicationThread sdkSandboxClientApplicationThread;
        public final Intent service;
        public final IBinder token;
        public final int userId;

        public BindItem(
                IApplicationThread iApplicationThread,
                IBinder iBinder,
                Intent intent,
                String str,
                IServiceConnection iServiceConnection,
                long j,
                String str2,
                boolean z,
                int i,
                String str3,
                IApplicationThread iApplicationThread2,
                String str4,
                int i2) {
            this.caller = iApplicationThread;
            this.token = iBinder;
            this.service = intent;
            this.resolvedType = str;
            this.connection = iServiceConnection;
            this.flags = j;
            this.instanceName = str2;
            this.isSdkSandboxService = z;
            this.sdkSandboxClientAppUid = i;
            this.sdkSandboxClientAppPackage = str3;
            this.sdkSandboxClientApplicationThread = iApplicationThread2;
            this.callingPackage = str4;
            this.userId = i2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ShortFgsInfo {
        public int mStartForegroundCount;
        public int mStartId;
        public final long mStartTime;

        public ShortFgsInfo(long j) {
            this.mStartTime = j;
            this.mStartForegroundCount = ServiceRecord.this.mStartForegroundCount;
            this.mStartId = ServiceRecord.this.lastStartId;
        }

        public final long getAnrTime() {
            return this.mStartTime
                    + ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration
                    + ServiceRecord.this.ams.mConstants.mShortFgsAnrExtraWaitDuration;
        }

        public final long getProcStateDemoteTime() {
            return this.mStartTime
                    + ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration
                    + ServiceRecord.this.ams.mConstants.mShortFgsProcStateExtraWaitDuration;
        }

        public final long getTimeoutTime() {
            return this.mStartTime + ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StartItem {
        public final int callingId;
        public long deliveredTime;
        public int deliveryCount;
        public int doneExecutingCount;
        public final int id;
        public final Intent intent;
        public final String mCallingPackageName;
        public final String mCallingProcessName;
        public final int mCallingProcessState;
        public final NeededUriGrants neededGrants;
        public final ServiceRecord sr;
        public String stringName;
        public final boolean taskRemoved;
        public UriPermissionOwner uriPermissions;

        public StartItem(
                ServiceRecord serviceRecord,
                boolean z,
                int i,
                Intent intent,
                NeededUriGrants neededUriGrants,
                int i2,
                String str,
                String str2,
                int i3) {
            this.sr = serviceRecord;
            this.taskRemoved = z;
            this.id = i;
            this.intent = intent;
            this.neededGrants = neededUriGrants;
            this.callingId = i2;
            this.mCallingProcessName = str;
            this.mCallingPackageName = str2;
            this.mCallingProcessState = i3;
        }

        public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.id);
            ProtoUtils.toDuration(protoOutputStream, 1146756268034L, this.deliveredTime, j2);
            protoOutputStream.write(1120986464259L, this.deliveryCount);
            protoOutputStream.write(1120986464260L, this.doneExecutingCount);
            Intent intent = this.intent;
            if (intent != null) {
                intent.dumpDebug(protoOutputStream, 1146756268037L, true, true, true, false);
            }
            NeededUriGrants neededUriGrants = this.neededGrants;
            if (neededUriGrants != null) {
                long start2 = protoOutputStream.start(1146756268038L);
                protoOutputStream.write(1138166333441L, neededUriGrants.targetPkg);
                protoOutputStream.write(1120986464258L, neededUriGrants.targetUid);
                protoOutputStream.write(1120986464259L, neededUriGrants.flags);
                int size = neededUriGrants.uris.size();
                for (int i = 0; i < size; i++) {
                    ((GrantUri) neededUriGrants.uris.valueAt(i))
                            .dumpDebug(protoOutputStream, 2246267895812L);
                }
                protoOutputStream.end(start2);
            }
            UriPermissionOwner uriPermissionOwner = this.uriPermissions;
            if (uriPermissionOwner != null) {
                long start3 = protoOutputStream.start(1146756268039L);
                protoOutputStream.write(1138166333441L, uriPermissionOwner.mOwner.toString());
                synchronized (uriPermissionOwner) {
                    try {
                        ArraySet arraySet = uriPermissionOwner.mReadPerms;
                        if (arraySet != null) {
                            Iterator it = arraySet.iterator();
                            while (it.hasNext()) {
                                ((UriPermission) it.next())
                                        .uri.dumpDebug(protoOutputStream, 2246267895810L);
                            }
                        }
                        ArraySet arraySet2 = uriPermissionOwner.mWritePerms;
                        if (arraySet2 != null) {
                            Iterator it2 = arraySet2.iterator();
                            while (it2.hasNext()) {
                                ((UriPermission) it2.next())
                                        .uri.dumpDebug(protoOutputStream, 2246267895811L);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                protoOutputStream.end(start3);
            }
            protoOutputStream.end(start);
        }

        public final String toString() {
            String str = this.stringName;
            if (str != null) {
                return str;
            }
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ServiceRecord{");
            ServiceRecord serviceRecord = this.sr;
            m.append(Integer.toHexString(System.identityHashCode(serviceRecord)));
            m.append(' ');
            m.append(serviceRecord.shortInstanceName);
            m.append(" StartItem ");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" id=");
            String m2 =
                    WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(m, this.id, '}');
            this.stringName = m2;
            return m2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TimeLimitedFgsInfo {
        public long mFirstFgsStartRealtime;
        public long mFirstFgsStartUptime;
        public long mLastFgsStartTime;
        public int mNumParallelServices;
        public long mTimeLimitExceededAt;
        public long mTotalRuntime;

        public final void decNumParallelServices() {
            int i = this.mNumParallelServices;
            if (i > 0) {
                this.mNumParallelServices = i - 1;
            }
            if (this.mNumParallelServices == 0) {
                this.mLastFgsStartTime = 0L;
            }
        }

        public final long getFirstFgsStartRealtime() {
            return this.mFirstFgsStartRealtime;
        }

        public final long getTimeLimitExceededAt() {
            return this.mTimeLimitExceededAt;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class removeConnectionItem {
        public final ConnectionRecord c;
        public final boolean enqueueOomAdj;
        public final ActivityServiceConnectionsHolder skipAct;
        public final ProcessRecord skipApp;

        public removeConnectionItem(
                ConnectionRecord connectionRecord,
                ProcessRecord processRecord,
                ActivityServiceConnectionsHolder activityServiceConnectionsHolder,
                boolean z) {
            this.c = connectionRecord;
            this.skipApp = processRecord;
            this.skipAct = activityServiceConnectionsHolder;
            this.enqueueOomAdj = z;
        }
    }

    /* renamed from: -$$Nest$msignalForegroundServiceNotification, reason: not valid java name */
    public static void m206$$Nest$msignalForegroundServiceNotification(
            int i, ServiceRecord serviceRecord, String str, boolean z) {
        ActivityManagerService activityManagerService = serviceRecord.ams;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                for (int size = serviceRecord.ams.mForegroundServiceStateListeners.size() - 1;
                        size >= 0;
                        size--) {
                    ((ActivityManagerInternal.ForegroundServiceStateListener)
                                    serviceRecord.ams.mForegroundServiceStateListeners.get(size))
                            .onForegroundServiceNotificationUpdated(
                                    str, serviceRecord.appInfo.uid, i, z);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public ServiceRecord(
            ActivityManagerService activityManagerService,
            ComponentName componentName,
            ComponentName componentName2,
            String str,
            int i,
            Intent.FilterComparison filterComparison,
            ServiceInfo serviceInfo,
            boolean z,
            ActiveServices.ServiceRestarter serviceRestarter,
            String str2,
            int i2,
            String str3,
            boolean z2) {
        this.ams = activityManagerService;
        this.name = componentName;
        this.instanceName = componentName2;
        this.shortInstanceName = componentName2.flattenToShortString();
        this.definingPackageName = str;
        this.definingUid = i;
        this.intent = filterComparison;
        this.serviceInfo = serviceInfo;
        ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        this.appInfo = applicationInfo;
        String str4 = applicationInfo.packageName;
        this.packageName = str4;
        this.isSdkSandbox = i2 != -1;
        this.sdkSandboxClientAppUid = i2;
        this.sdkSandboxClientAppPackage = str3;
        this.inSharedIsolatedProcess = z2;
        this.processName = str2;
        this.permission = serviceInfo.permission;
        this.exported = serviceInfo.exported;
        this.restarter = serviceRestarter;
        this.createRealTime = SystemClock.elapsedRealtime();
        this.lastActivity = SystemClock.uptimeMillis();
        this.userId = UserHandle.getUserId(this.appInfo.uid);
        this.createdFromFg = z;
        updateKeepWarmLocked();
        activityManagerService.mHandler.post(
                new Runnable(
                        str4,
                        this.appInfo.uid) { // from class: com.android.server.am.ServiceRecord.1
                    public final /* synthetic */ int val$appUid;
                    public final /* synthetic */ String val$localPackageName;

                    {
                        this.val$appUid = r3;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerInternal notificationManagerInternal =
                                (NotificationManagerInternal)
                                        LocalServices.getService(NotificationManagerInternal.class);
                        if (notificationManagerInternal == null) {
                            return;
                        }
                        ServiceRecord.this.mFgsHasNotificationPermission =
                                NotificationManagerService.this.mPermissionHelper.hasPermission(
                                        this.val$appUid);
                    }
                });
    }

    public static void dumpReasonCode(int i, PrintWriter printWriter, String str, String str2) {
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print("=");
        printWriter.println(PowerExemptionManager.reasonCodeToString(i));
    }

    public static void dumpStartList(PrintWriter printWriter, String str, List list, long j) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            StartItem startItem = (StartItem) list.get(i);
            printWriter.print(str);
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(" id=");
            printWriter.print(startItem.id);
            if (j != 0) {
                printWriter.print(" dur=");
                TimeUtils.formatDuration(startItem.deliveredTime, j, printWriter);
            }
            if (startItem.deliveryCount != 0) {
                printWriter.print(" dc=");
                printWriter.print(startItem.deliveryCount);
            }
            if (startItem.doneExecutingCount != 0) {
                printWriter.print(" dxc=");
                printWriter.print(startItem.doneExecutingCount);
            }
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, "", str, "  intent=");
            Intent intent = startItem.intent;
            if (intent != null) {
                printWriter.println(intent.toString());
            } else {
                printWriter.println("null");
            }
            NeededUriGrants neededUriGrants = startItem.neededGrants;
            if (neededUriGrants != null) {
                printWriter.print(str);
                printWriter.print("  neededGrants=");
                printWriter.println(neededUriGrants);
            }
            UriPermissionOwner uriPermissionOwner = startItem.uriPermissions;
            if (uriPermissionOwner != null) {
                uriPermissionOwner.dump(printWriter, str);
            }
        }
    }

    public final void addConnection(IBinder iBinder, ConnectionRecord connectionRecord) {
        ArrayList arrayList = (ArrayList) this.connections.get(iBinder);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.connections.put(iBinder, arrayList);
        }
        arrayList.add(connectionRecord);
        ProcessRecord processRecord = this.app;
        if (processRecord != null) {
            ProcessServiceRecord processServiceRecord = processRecord.mServices;
            ArraySet arraySet = processServiceRecord.mBoundClientUids;
            int i = connectionRecord.clientUid;
            arraySet.add(Integer.valueOf(i));
            processServiceRecord.mApp.mWindowProcessController.addBoundClientUid(
                    i, connectionRecord.clientPackageName, connectionRecord.flags);
            this.app.mProfile.addHostingComponentType(512);
        }
    }

    public final boolean canStopIfKilled(boolean z) {
        if (isShortFgs()) {
            return true;
        }
        return this.startRequested && (this.stopIfKilled || z) && this.pendingStarts.isEmpty();
    }

    public final void cancelNotification() {
        final String str = this.packageName;
        final int i = this.foregroundId;
        final int i2 = this.appInfo.uid;
        ProcessRecord processRecord = this.app;
        final int i3 = processRecord != null ? processRecord.mPid : 0;
        this.ams.mHandler.post(
                new Runnable() { // from class: com.android.server.am.ServiceRecord.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationManagerInternal notificationManagerInternal =
                                (NotificationManagerInternal)
                                        LocalServices.getService(NotificationManagerInternal.class);
                        if (notificationManagerInternal == null) {
                            return;
                        }
                        try {
                            String str2 = str;
                            int i4 = i2;
                            int i5 = i3;
                            int i6 = i;
                            int i7 = ServiceRecord.this.userId;
                            NotificationManagerService.AnonymousClass17 anonymousClass17 =
                                    (NotificationManagerService.AnonymousClass17)
                                            notificationManagerInternal;
                            NotificationManagerService.this.getClass();
                            NotificationManagerService.this.cancelNotificationInternal(
                                    i4,
                                    i5,
                                    i6,
                                    i7,
                                    NotificationManagerService.isCallingUidSystem() ? 0 : 33856,
                                    str2,
                                    str2,
                                    null);
                        } catch (RuntimeException e) {
                            Slog.w(
                                    "ActivityManager",
                                    "Error canceling notification for service",
                                    e);
                        }
                        ServiceRecord serviceRecord = ServiceRecord.this;
                        String str3 = serviceRecord.packageName;
                        int i8 = serviceRecord.appInfo.uid;
                        ServiceRecord.m206$$Nest$msignalForegroundServiceNotification(
                                i, serviceRecord, str3, true);
                    }
                });
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x03fd, code lost:

       if (r12 == 0) goto L95;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v15, types: [int] */
    /* JADX WARN: Type inference failed for: r13v17 */
    /* JADX WARN: Type inference failed for: r14v14, types: [android.util.ArrayMap] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 1469
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ServiceRecord.dump(java.io.PrintWriter,"
                    + " java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0219, code lost:

       if (r1 == 0) goto L56;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpDebug(android.util.proto.ProtoOutputStream r29) {
        /*
            Method dump skipped, instructions count: 1232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ServiceRecord.dumpDebug(android.util.proto.ProtoOutputStream):void");
    }

    public final StartItem findDeliveredStart(int i, boolean z, boolean z2) {
        int size = this.deliveredStarts.size();
        for (int i2 = 0; i2 < size; i2++) {
            StartItem startItem = (StartItem) this.deliveredStarts.get(i2);
            if (startItem.id == i && startItem.taskRemoved == z) {
                if (z2) {
                    this.deliveredStarts.remove(i2);
                }
                return startItem;
            }
        }
        return null;
    }

    public final BackgroundStartPrivileges getBackgroundStartPrivilegesWithExclusiveToken() {
        return this.mIsAllowedBgActivityStartsByBinding
                ? BackgroundStartPrivileges.ALLOW_BAL
                : this.mBackgroundStartPrivilegesByStart.isEmpty()
                        ? BackgroundStartPrivileges.NONE
                        : this.mBackgroundStartPrivilegesByStartMerged;
    }

    public final ComponentName getComponentName() {
        return this.name;
    }

    public final int getFgsAllowStart() {
        Flags.newFgsRestrictionLogic();
        if (CompatChanges.isChangeEnabled(311208749L, this.appInfo.uid)) {
            int i = this.mAllowStart_noBinding;
            return i != -1 ? i : this.mAllowStart_byBindings;
        }
        int i2 = this.mAllowStart_noBinding;
        int i3 = this.mAllowStart_inBindService;
        return i2 != -1 ? i2 : i3 != -1 ? i3 : this.mAllowStart_byBindings;
    }

    public final int getFgsAllowWiu_forStart() {
        Flags.newFgsRestrictionLogic();
        if (CompatChanges.isChangeEnabled(311208629L, this.appInfo.uid)) {
            int i = this.mAllowWiu_noBinding;
            return i != -1 ? i : this.mAllowWiu_byBindings;
        }
        int i2 = this.mAllowWiu_noBinding;
        return i2 != -1 ? i2 : this.mAllowWiu_inBindService;
    }

    public final ShortFgsInfo getShortFgsInfo() {
        if (isShortFgs()) {
            return this.mShortFgsInfo;
        }
        return null;
    }

    public final ServiceState getTracker() {
        ServiceState serviceStateLocked;
        ServiceState serviceState = this.tracker;
        if (serviceState != null) {
            return serviceState;
        }
        ServiceInfo serviceInfo = this.serviceInfo;
        ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        if ((applicationInfo.flags & 8) == 0) {
            ProcessStatsService processStatsService = this.ams.mProcessStats;
            String str = serviceInfo.packageName;
            int i = applicationInfo.uid;
            long j = applicationInfo.longVersionCode;
            String str2 = serviceInfo.processName;
            String str3 = serviceInfo.name;
            synchronized (processStatsService.mLock) {
                serviceStateLocked =
                        processStatsService.mProcessStats.getServiceStateLocked(
                                str, i, j, str2, str3);
            }
            this.tracker = serviceStateLocked;
            if (serviceStateLocked != null) {
                serviceStateLocked.applyNewOwner(this);
            }
        }
        return this.tracker;
    }

    public final boolean hasAutoCreateConnections() {
        int size = this.connections.size() - 1;
        while (true) {
            if (size < 0) {
                return false;
            }
            ArrayList arrayList = (ArrayList) this.connections.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                if (((ConnectionRecord) arrayList.get(i)).hasFlag(1)) {
                    return true;
                }
            }
            size--;
        }
    }

    public final boolean isFgsAllowedStart() {
        return getFgsAllowStart() != -1;
    }

    public final boolean isFgsAllowedWiu_forCapabilities() {
        int fgsAllowWiu_forStart;
        Flags.newFgsRestrictionLogic();
        if (CompatChanges.isChangeEnabled(313677553L, this.appInfo.uid)) {
            fgsAllowWiu_forStart = this.mAllowWiu_noBinding;
            int i = this.mAllowWiu_byBindings;
            if (fgsAllowWiu_forStart == -1) {
                fgsAllowWiu_forStart = i;
            }
        } else {
            fgsAllowWiu_forStart = getFgsAllowWiu_forStart();
        }
        return fgsAllowWiu_forStart != -1;
    }

    public final boolean isShortFgs() {
        return this.startRequested && this.isForeground && this.foregroundServiceType == 2048;
    }

    public final void makeRestarting(int i, long j) {
        ServiceState serviceStateLocked;
        if (this.restartTracker == null) {
            ServiceInfo serviceInfo = this.serviceInfo;
            ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
            if ((applicationInfo.flags & 8) == 0) {
                ProcessStatsService processStatsService = this.ams.mProcessStats;
                String str = serviceInfo.packageName;
                int i2 = applicationInfo.uid;
                long j2 = applicationInfo.longVersionCode;
                String str2 = serviceInfo.processName;
                String str3 = serviceInfo.name;
                synchronized (processStatsService.mLock) {
                    serviceStateLocked =
                            processStatsService.mProcessStats.getServiceStateLocked(
                                    str, i2, j2, str2, str3);
                }
                this.restartTracker = serviceStateLocked;
            }
            if (this.restartTracker == null) {
                return;
            }
        }
        this.restartTracker.setRestarting(true, i, j);
    }

    public final void maybeLogFgsLogicChange() {
        int i = this.mAllowWiu_noBinding;
        int i2 = this.mAllowWiu_inBindService;
        if (i != -1) {
            i2 = i;
        }
        int i3 = this.mAllowWiu_byBindings;
        if (i == -1) {
            i = i3;
        }
        int i4 = this.mAllowStart_noBinding;
        int i5 = this.mAllowStart_inBindService;
        int i6 = this.mAllowStart_byBindings;
        if (i4 != -1) {
            i5 = i4;
        } else if (i5 == -1) {
            i5 = i6;
        }
        if (i4 == -1) {
            i4 = i6;
        }
        boolean z = (i2 == -1) != (i == -1);
        boolean z2 = (i5 == -1) != (i4 == -1);
        if (z || z2) {
            StringBuilder sb = new StringBuilder("FGS logic changed:");
            sb.append(z ? " [WIU changed]" : "");
            sb.append(z2 ? " [BFSL changed]" : "");
            sb.append(" Orig WIU:");
            sb.append(PowerExemptionManager.reasonCodeToString(i2));
            sb.append(" New WIU:");
            sb.append(PowerExemptionManager.reasonCodeToString(i));
            sb.append(" Orig BFSL:");
            sb.append(PowerExemptionManager.reasonCodeToString(i5));
            sb.append(" New BFSL:");
            sb.append(PowerExemptionManager.reasonCodeToString(i4));
            sb.append(
                    " cmp: "
                            + this.name.toShortString()
                            + " sdk: "
                            + this.appInfo.targetSdkVersion);
            Slog.wtf("ActivityManager", sb.toString());
        }
    }

    public final void postNotification(final boolean z) {
        final Notification notification;
        ProcessRecord processRecord;
        if (!this.isForeground
                || (notification = this.foregroundNoti) == null
                || (processRecord = this.app) == null) {
            return;
        }
        final int i = this.appInfo.uid;
        final int i2 = processRecord.mPid;
        final String str = this.packageName;
        final int i3 = this.foregroundId;
        this.ams.mHandler.post(
                new Runnable() { // from class: com.android.server.am.ServiceRecord.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i4;
                        NotificationManagerInternal notificationManagerInternal =
                                (NotificationManagerInternal)
                                        LocalServices.getService(NotificationManagerInternal.class);
                        if (notificationManagerInternal == null) {
                            return;
                        }
                        NotificationManagerService.AnonymousClass17 anonymousClass17 =
                                (NotificationManagerService.AnonymousClass17)
                                        notificationManagerInternal;
                        ServiceRecord.this.mFgsHasNotificationPermission =
                                NotificationManagerService.this.mPermissionHelper.hasPermission(i);
                        Notification notification2 = notification;
                        try {
                            if (notification2.getSmallIcon() == null) {
                                Slog.v(
                                        "ActivityManager",
                                        "Attempted to start a foreground service ("
                                                + ServiceRecord.this.shortInstanceName
                                                + ") with a broken notification (no icon: "
                                                + notification2
                                                + ")");
                                ServiceRecord serviceRecord = ServiceRecord.this;
                                CharSequence loadLabel =
                                        serviceRecord.appInfo.loadLabel(
                                                serviceRecord.ams.mContext.getPackageManager());
                                if (loadLabel == null) {
                                    loadLabel = ServiceRecord.this.appInfo.packageName;
                                }
                                try {
                                    ServiceRecord serviceRecord2 = ServiceRecord.this;
                                    Notification.Builder builder =
                                            new Notification.Builder(
                                                    serviceRecord2.ams.mContext
                                                            .createPackageContextAsUser(
                                                                    serviceRecord2
                                                                            .appInfo
                                                                            .packageName,
                                                                    0,
                                                                    new UserHandle(
                                                                            ServiceRecord.this
                                                                                    .userId)),
                                                    notification2.getChannelId());
                                    builder.setSmallIcon(ServiceRecord.this.appInfo.icon);
                                    builder.setFlag(64, true);
                                    Intent intent =
                                            new Intent(
                                                    "android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.setData(
                                            Uri.fromParts(
                                                    "package",
                                                    ServiceRecord.this.appInfo.packageName,
                                                    null));
                                    ServiceRecord serviceRecord3 = ServiceRecord.this;
                                    PendingIntent activityAsUser =
                                            PendingIntent.getActivityAsUser(
                                                    serviceRecord3.ams.mContext,
                                                    0,
                                                    intent,
                                                    201326592,
                                                    null,
                                                    UserHandle.of(serviceRecord3.userId));
                                    builder.setColor(
                                            ServiceRecord.this.ams.mContext.getColor(
                                                    R.color.system_notification_accent_color));
                                    builder.setContentTitle(
                                            ServiceRecord.this.ams.mContext.getString(
                                                    R.string.conference_call, loadLabel));
                                    builder.setContentText(
                                            ServiceRecord.this.ams.mContext.getString(
                                                    R.string
                                                            .condition_provider_service_binding_label,
                                                    loadLabel));
                                    builder.setContentIntent(activityAsUser);
                                    notification2 = builder.build();
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                            }
                            if (anonymousClass17.getNotificationChannel(
                                            i, str, notification2.getChannelId())
                                    == null) {
                                try {
                                    PackageManager packageManager =
                                            ServiceRecord.this.ams.mContext.getPackageManager();
                                    ServiceRecord serviceRecord4 = ServiceRecord.this;
                                    i4 =
                                            packageManager.getApplicationInfoAsUser(
                                                            serviceRecord4.appInfo.packageName,
                                                            0,
                                                            serviceRecord4.userId)
                                                    .targetSdkVersion;
                                } catch (PackageManager.NameNotFoundException unused2) {
                                    i4 = 27;
                                }
                                if (i4 >= 27) {
                                    throw new RuntimeException(
                                            "invalid channel for service notification: "
                                                    + ServiceRecord.this.foregroundNoti);
                                }
                            }
                            if (notification2.getSmallIcon() == null) {
                                throw new RuntimeException(
                                        "invalid service notification: "
                                                + ServiceRecord.this.foregroundNoti);
                            }
                            String str2 = str;
                            NotificationManagerService.this.enqueueNotificationInternal(
                                    str2,
                                    str2,
                                    i,
                                    i2,
                                    null,
                                    i3,
                                    notification2,
                                    ServiceRecord.this.userId,
                                    false,
                                    z);
                            ServiceRecord serviceRecord5 = ServiceRecord.this;
                            serviceRecord5.foregroundNoti = notification2;
                            String str3 = serviceRecord5.packageName;
                            int i5 = serviceRecord5.appInfo.uid;
                            ServiceRecord.m206$$Nest$msignalForegroundServiceNotification(
                                    i3, serviceRecord5, str3, false);
                        } catch (RuntimeException e) {
                            Slog.w("ActivityManager", "Error showing notification for service", e);
                            ActiveServices activeServices = ServiceRecord.this.ams.mServices;
                            ServiceRecord serviceRecord6 = this;
                            int i6 = i;
                            int i7 = i2;
                            String str4 = str;
                            ActivityManagerService activityManagerService = activeServices.mAm;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService) {
                                try {
                                    if (serviceRecord6.destroying) {
                                        ServiceRecord serviceRecord7 =
                                                (ServiceRecord)
                                                        activeServices
                                                                .getServiceMapLocked(
                                                                        serviceRecord6.userId)
                                                                .mServicesByInstanceName
                                                                .remove(
                                                                        serviceRecord6
                                                                                .instanceName);
                                        if (serviceRecord7 != null) {
                                            activeServices.stopServiceLocked(serviceRecord7, false);
                                        }
                                    } else {
                                        activeServices.stopServiceLocked(serviceRecord6, false);
                                    }
                                    activeServices.mAm.crashApplicationWithTypeWithExtras(
                                            i6,
                                            i7,
                                            str4,
                                            -1,
                                            "Bad notification for startForeground",
                                            true,
                                            2,
                                            null);
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                } catch (Throwable th) {
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                        }
                    }
                });
    }

    public final void setProcess(ProcessRecord processRecord) {
        if (processRecord != null) {
            ProcessRecord processRecord2 = this.mAppForAllowingBgActivityStartsByStart;
            if (processRecord2 != null && processRecord2 != processRecord) {
                processRecord2.removeBackgroundStartPrivileges(this);
                this.ams.mHandler.removeCallbacks(
                        this.mCleanUpAllowBgActivityStartsByStartCallback);
            }
            this.mAppForAllowingBgActivityStartsByStart =
                    this.mBackgroundStartPrivilegesByStartMerged.allowsAny() ? processRecord : null;
            BackgroundStartPrivileges backgroundStartPrivilegesWithExclusiveToken =
                    getBackgroundStartPrivilegesWithExclusiveToken();
            if (backgroundStartPrivilegesWithExclusiveToken.allowsAny()) {
                processRecord.addOrUpdateBackgroundStartPrivileges(
                        this, backgroundStartPrivilegesWithExclusiveToken);
            } else {
                processRecord.removeBackgroundStartPrivileges(this);
            }
        }
        ProcessRecord processRecord3 = this.app;
        if (processRecord3 != null && processRecord3 != processRecord) {
            if (this.mBackgroundStartPrivilegesByStartMerged.allowsNothing()) {
                this.app.removeBackgroundStartPrivileges(this);
            }
            this.app.mServices.updateBoundClientUids();
            this.app.mServices.updateHostingComonentTypeForBindingsLocked();
        }
        this.app = processRecord;
        updateProcessStateOnRequest();
        int i = this.pendingConnectionGroup;
        if (i > 0 && processRecord != null) {
            ProcessServiceRecord processServiceRecord = processRecord.mServices;
            processServiceRecord.mConnectionService = this;
            processServiceRecord.mConnectionGroup = i;
            processServiceRecord.mConnectionImportance = this.pendingConnectionImportance;
            this.pendingConnectionImportance = 0;
            this.pendingConnectionGroup = 0;
        }
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) this.connections.valueAt(size);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i2);
                if (processRecord != null) {
                    connectionRecord.startAssociationIfNeeded();
                } else {
                    connectionRecord.stopAssociation();
                }
            }
        }
        if (processRecord != null) {
            ProcessServiceRecord processServiceRecord2 = processRecord.mServices;
            processServiceRecord2.updateBoundClientUids();
            processServiceRecord2.updateHostingComonentTypeForBindingsLocked();
        }
    }

    public final boolean shouldTriggerShortFgsTimedEvent(long j, long j2) {
        ShortFgsInfo shortFgsInfo;
        ProcessRecord processRecord = this.app;
        return (processRecord == null
                        || processRecord.mThread == null
                        || processRecord.mKilled
                        || processRecord.mKilledByAm
                        || !this.startRequested
                        || !isShortFgs()
                        || (shortFgsInfo = this.mShortFgsInfo) == null
                        || shortFgsInfo.mStartForegroundCount
                                != ServiceRecord.this.mStartForegroundCount
                        || j > j2)
                ? false
                : true;
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ServiceRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" u");
        m.append(this.userId);
        m.append(' ');
        m.append(this.shortInstanceName);
        if (this.mRecentCallingPackage != null) {
            m.append(" c:");
            m.append(this.mRecentCallingPackage);
        }
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0041, code lost:

       if (com.android.server.am.ActivityManagerService.isSingleton(r1, r2, r3, r4) != false) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateKeepWarmLocked() {
        /*
            r5 = this;
            com.android.server.am.ActivityManagerService r0 = r5.ams
            com.android.server.am.ActivityManagerConstants r0 = r0.mConstants
            android.util.ArraySet r0 = r0.KEEP_WARMING_SERVICES
            android.content.ComponentName r1 = r5.name
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L49
            com.android.server.am.ActivityManagerService r0 = r5.ams
            com.android.server.am.UserController r0 = r0.mUserController
            int r0 = r0.getCurrentUserId()
            int r1 = r5.userId
            if (r0 == r1) goto L47
            com.android.server.am.ActivityManagerService r0 = r5.ams
            com.android.server.am.UserController r0 = r0.mUserController
            java.lang.Object r2 = r0.mLock
            monitor-enter(r2)
            int[] r0 = r0.mCurrentProfileIds     // Catch: java.lang.Throwable -> L44
            boolean r0 = com.android.internal.util.ArrayUtils.contains(r0, r1)     // Catch: java.lang.Throwable -> L44
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L44
            if (r0 != 0) goto L47
            com.android.server.am.ActivityManagerService r0 = r5.ams
            java.lang.String r1 = r5.processName
            android.content.pm.ApplicationInfo r2 = r5.appInfo
            android.content.ComponentName r3 = r5.instanceName
            java.lang.String r3 = r3.getClassName()
            android.content.pm.ServiceInfo r4 = r5.serviceInfo
            int r4 = r4.flags
            r0.getClass()
            boolean r0 = com.android.server.am.ActivityManagerService.isSingleton(r1, r2, r3, r4)
            if (r0 == 0) goto L49
            goto L47
        L44:
            r5 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L44
            throw r5
        L47:
            r0 = 1
            goto L4a
        L49:
            r0 = 0
        L4a:
            r5.mKeepWarming = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ServiceRecord.updateKeepWarmLocked():void");
    }

    public final void updateOomAdjSeq() {
        ProcessRecord processRecord = this.app;
        if (processRecord != null) {
            this.mAdjSeq = processRecord.mState.mAdjSeq;
        }
    }

    public final void updateProcessStateOnRequest() {
        ProcessRecord processRecord = this.app;
        this.mProcessStateOnRequest =
                (processRecord == null || processRecord.mThread == null || processRecord.mKilled)
                        ? 20
                        : processRecord.mState.mCurProcState;
    }

    public final boolean wasOomAdjUpdated() {
        ProcessRecord processRecord = this.app;
        return processRecord != null && processRecord.mState.mAdjSeq > this.mAdjSeq;
    }
}
