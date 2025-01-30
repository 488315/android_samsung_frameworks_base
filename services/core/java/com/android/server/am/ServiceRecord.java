package com.android.server.am;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.Notification;
import android.app.PendingIntent;
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
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.am.ActivityManagerService;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriPermissionOwner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
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
    public boolean inSharedIsolatedProcess;
    public final ComponentName instanceName;
    public final Intent.FilterComparison intent;
    public boolean isForeground;
    public boolean isNotAppComponentUsage;
    public final boolean isSdkSandbox;
    public ProcessRecord isolationHostProc;
    public long lastActivity;
    public int lastStartId;
    public long lastTopAlmostPerceptibleBindRequestUptimeMs;
    public boolean mAllowUiJobScheduling;
    public boolean mAllowWhileInUsePermissionInFgs;
    public boolean mAllowWhileInUsePermissionInFgsAtEntering;
    public ProcessRecord mAppForAllowingBgActivityStartsByStart;
    public Runnable mCleanUpAllowBgActivityStartsByStartCallback;
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
    public int totalRestartCount;
    public ServiceState tracker;
    public final int userId;
    public final ArrayMap bindings = new ArrayMap();
    public final ArrayMap connections = new ArrayMap();
    public ArrayList mBackgroundStartPrivilegesByStart = new ArrayList();
    public BackgroundStartPrivileges mBackgroundStartPrivilegesByStartMerged = BackgroundStartPrivileges.NONE;
    public int mAllowWhileInUsePermissionInFgsReason = -1;
    public long mFgsEnterTime = 0;
    public long mFgsExitTime = 0;
    public int mAllowStartForeground = -1;
    public int mAllowStartForegroundAtEntering = -1;
    public final ArrayList pendingBinds = new ArrayList();
    public final ArrayList deliveredStarts = new ArrayList();
    public final ArrayList pendingStarts = new ArrayList();

    public class StartItem {
        public final int callingId;
        public long deliveredTime;
        public int deliveryCount;
        public int doneExecutingCount;

        /* renamed from: id */
        public final int f1633id;
        public final Intent intent;
        public final String mCallingPackageName;
        public final String mCallingProcessName;
        public final int mCallingProcessState;
        public final NeededUriGrants neededGrants;

        /* renamed from: sr */
        public final ServiceRecord f1634sr;
        public String stringName;
        public final boolean taskRemoved;
        public UriPermissionOwner uriPermissions;

        public StartItem(ServiceRecord serviceRecord, boolean z, int i, Intent intent, NeededUriGrants neededUriGrants, int i2, String str, String str2, int i3) {
            this.f1634sr = serviceRecord;
            this.taskRemoved = z;
            this.f1633id = i;
            this.intent = intent;
            this.neededGrants = neededUriGrants;
            this.callingId = i2;
            this.mCallingProcessName = str;
            this.mCallingPackageName = str2;
            this.mCallingProcessState = i3;
        }

        public UriPermissionOwner getUriPermissionsLocked() {
            if (this.uriPermissions == null) {
                this.uriPermissions = new UriPermissionOwner(this.f1634sr.ams.mUgmInternal, this);
            }
            return this.uriPermissions;
        }

        public void removeUriPermissionsLocked() {
            UriPermissionOwner uriPermissionOwner = this.uriPermissions;
            if (uriPermissionOwner != null) {
                uriPermissionOwner.removeUriPermissions();
                this.uriPermissions = null;
            }
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.f1633id);
            ProtoUtils.toDuration(protoOutputStream, 1146756268034L, this.deliveredTime, j2);
            protoOutputStream.write(1120986464259L, this.deliveryCount);
            protoOutputStream.write(1120986464260L, this.doneExecutingCount);
            Intent intent = this.intent;
            if (intent != null) {
                intent.dumpDebug(protoOutputStream, 1146756268037L, true, true, true, false);
            }
            NeededUriGrants neededUriGrants = this.neededGrants;
            if (neededUriGrants != null) {
                neededUriGrants.dumpDebug(protoOutputStream, 1146756268038L);
            }
            UriPermissionOwner uriPermissionOwner = this.uriPermissions;
            if (uriPermissionOwner != null) {
                uriPermissionOwner.dumpDebug(protoOutputStream, 1146756268039L);
            }
            protoOutputStream.end(start);
        }

        public String toString() {
            String str = this.stringName;
            if (str != null) {
                return str;
            }
            StringBuilder sb = new StringBuilder(128);
            sb.append("ServiceRecord{");
            sb.append(Integer.toHexString(System.identityHashCode(this.f1634sr)));
            sb.append(' ');
            sb.append(this.f1634sr.shortInstanceName);
            sb.append(" StartItem ");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" id=");
            sb.append(this.f1633id);
            sb.append('}');
            String sb2 = sb.toString();
            this.stringName = sb2;
            return sb2;
        }
    }

    public class BindItem {
        public final IApplicationThread caller;
        public final String callingPackage;
        public final IServiceConnection connection;
        public long flags;
        public final String instanceName;
        public boolean isSdkSandboxService;
        public final String resolvedType;
        public final String sdkSandboxClientAppPackage;
        public int sdkSandboxClientAppUid;
        public final IApplicationThread sdkSandboxClientApplicationThread;
        public final Intent service;
        public final IBinder token;
        public int userId;

        public BindItem(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, boolean z, int i, String str3, IApplicationThread iApplicationThread2, String str4, int i2) {
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

    public class ShortFgsInfo {
        public int mStartForegroundCount;
        public int mStartId;
        public final long mStartTime;

        public ShortFgsInfo(long j) {
            this.mStartTime = j;
            update();
        }

        public void update() {
            ServiceRecord serviceRecord = ServiceRecord.this;
            this.mStartForegroundCount = serviceRecord.mStartForegroundCount;
            this.mStartId = serviceRecord.getLastStartId();
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public int getStartForegroundCount() {
            return this.mStartForegroundCount;
        }

        public int getStartId() {
            return this.mStartId;
        }

        public boolean isCurrent() {
            return this.mStartForegroundCount == ServiceRecord.this.mStartForegroundCount;
        }

        public long getTimeoutTime() {
            return this.mStartTime + ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration;
        }

        public long getProcStateDemoteTime() {
            return this.mStartTime + ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration + ServiceRecord.this.ams.mConstants.mShortFgsProcStateExtraWaitDuration;
        }

        public long getAnrTime() {
            return this.mStartTime + ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration + ServiceRecord.this.ams.mConstants.mShortFgsAnrExtraWaitDuration;
        }
    }

    public void dumpStartList(PrintWriter printWriter, String str, List list, long j) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            StartItem startItem = (StartItem) list.get(i);
            printWriter.print(str);
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(" id=");
            printWriter.print(startItem.f1633id);
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
            printWriter.println("");
            printWriter.print(str);
            printWriter.print("  intent=");
            Intent intent = startItem.intent;
            if (intent != null) {
                printWriter.println(intent.toString());
            } else {
                printWriter.println("null");
            }
            if (startItem.neededGrants != null) {
                printWriter.print(str);
                printWriter.print("  neededGrants=");
                printWriter.println(startItem.neededGrants);
            }
            UriPermissionOwner uriPermissionOwner = startItem.uriPermissions;
            if (uriPermissionOwner != null) {
                uriPermissionOwner.dump(printWriter, str);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x01f6, code lost:
    
        if (r1 == 0) goto L56;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long j2;
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.shortInstanceName);
        protoOutputStream.write(1133871366146L, this.app != null);
        ProcessRecord processRecord = this.app;
        if (processRecord != null) {
            protoOutputStream.write(1120986464259L, processRecord.getPid());
        }
        Intent.FilterComparison filterComparison = this.intent;
        if (filterComparison != null) {
            j2 = 1133871366146L;
            filterComparison.getIntent().dumpDebug(protoOutputStream, 1146756268036L, false, true, false, false);
        } else {
            j2 = 1133871366146L;
        }
        protoOutputStream.write(1138166333445L, this.packageName);
        protoOutputStream.write(1138166333446L, this.processName);
        protoOutputStream.write(1138166333447L, this.permission);
        long uptimeMillis = SystemClock.uptimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.appInfo != null) {
            long start2 = protoOutputStream.start(1146756268040L);
            protoOutputStream.write(1138166333441L, this.appInfo.sourceDir);
            ApplicationInfo applicationInfo = this.appInfo;
            if (!Objects.equals(applicationInfo.sourceDir, applicationInfo.publicSourceDir)) {
                protoOutputStream.write(1138166333442L, this.appInfo.publicSourceDir);
            }
            protoOutputStream.write(1138166333443L, this.appInfo.dataDir);
            protoOutputStream.end(start2);
        }
        ProcessRecord processRecord2 = this.app;
        if (processRecord2 != null) {
            processRecord2.dumpDebug(protoOutputStream, 1146756268041L);
        }
        ProcessRecord processRecord3 = this.isolationHostProc;
        if (processRecord3 != null) {
            processRecord3.dumpDebug(protoOutputStream, 1146756268042L);
        }
        protoOutputStream.write(1133871366155L, this.allowlistManager);
        protoOutputStream.write(1133871366156L, this.delayed);
        if (this.isForeground || this.foregroundId != 0) {
            long start3 = protoOutputStream.start(1146756268045L);
            protoOutputStream.write(1120986464257L, this.foregroundId);
            this.foregroundNoti.dumpDebug(protoOutputStream, 1146756268034L);
            protoOutputStream.write(1120986464259L, this.foregroundServiceType);
            protoOutputStream.end(start3);
        }
        ProtoUtils.toDuration(protoOutputStream, 1146756268046L, this.createRealTime, elapsedRealtime);
        ProtoUtils.toDuration(protoOutputStream, 1146756268047L, this.startingBgTimeout, uptimeMillis);
        ProtoUtils.toDuration(protoOutputStream, 1146756268048L, this.lastActivity, uptimeMillis);
        ProtoUtils.toDuration(protoOutputStream, 1146756268049L, this.restartTime, uptimeMillis);
        protoOutputStream.write(1133871366162L, this.createdFromFg);
        protoOutputStream.write(1133871366171L, this.mAllowWhileInUsePermissionInFgs);
        if (this.startRequested || this.delayedStop || this.lastStartId != 0) {
            long start4 = protoOutputStream.start(1146756268051L);
            protoOutputStream.write(1133871366145L, this.startRequested);
            protoOutputStream.write(j2, this.delayedStop);
            protoOutputStream.write(1133871366147L, this.stopIfKilled);
            protoOutputStream.write(1120986464261L, this.lastStartId);
            protoOutputStream.write(1120986464262L, this.startCommandResult);
            protoOutputStream.end(start4);
        }
        if (this.executeNesting != 0) {
            long start5 = protoOutputStream.start(1146756268052L);
            protoOutputStream.write(1120986464257L, this.executeNesting);
            protoOutputStream.write(j2, this.executeFg);
            ProtoUtils.toDuration(protoOutputStream, 1146756268035L, this.executingStart, uptimeMillis);
            protoOutputStream.end(start5);
        }
        if (this.destroying || this.destroyTime != 0) {
            ProtoUtils.toDuration(protoOutputStream, 1146756268053L, this.destroyTime, uptimeMillis);
        }
        if (this.crashCount == 0 && this.restartCount == 0) {
            long j3 = this.nextRestartTime;
            if (j3 - this.mRestartSchedulingTime == 0) {
            }
        }
        long start6 = protoOutputStream.start(1146756268054L);
        protoOutputStream.write(1120986464257L, this.restartCount);
        ProtoUtils.toDuration(protoOutputStream, 1146756268034L, this.nextRestartTime - this.mRestartSchedulingTime, uptimeMillis);
        ProtoUtils.toDuration(protoOutputStream, 1146756268035L, this.nextRestartTime, uptimeMillis);
        protoOutputStream.write(1120986464260L, this.crashCount);
        protoOutputStream.end(start6);
        if (this.deliveredStarts.size() > 0) {
            int size = this.deliveredStarts.size();
            for (int i = 0; i < size; i++) {
                ((StartItem) this.deliveredStarts.get(i)).dumpDebug(protoOutputStream, 2246267895831L, uptimeMillis);
            }
        }
        if (this.pendingStarts.size() > 0) {
            int size2 = this.pendingStarts.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((StartItem) this.pendingStarts.get(i2)).dumpDebug(protoOutputStream, 2246267895832L, uptimeMillis);
            }
        }
        if (this.bindings.size() > 0) {
            int size3 = this.bindings.size();
            for (int i3 = 0; i3 < size3; i3++) {
                ((IntentBindRecord) this.bindings.valueAt(i3)).dumpDebug(protoOutputStream, 2246267895833L);
            }
        }
        if (this.connections.size() > 0) {
            int size4 = this.connections.size();
            for (int i4 = 0; i4 < size4; i4++) {
                ArrayList arrayList = (ArrayList) this.connections.valueAt(i4);
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    ((ConnectionRecord) arrayList.get(i5)).dumpDebug(protoOutputStream, 2246267895834L);
                }
            }
        }
        ShortFgsInfo shortFgsInfo = this.mShortFgsInfo;
        if (shortFgsInfo != null && shortFgsInfo.isCurrent()) {
            long start7 = protoOutputStream.start(1146756268060L);
            protoOutputStream.write(1112396529665L, this.mShortFgsInfo.getStartTime());
            protoOutputStream.write(1120986464259L, this.mShortFgsInfo.getStartId());
            protoOutputStream.write(1112396529668L, this.mShortFgsInfo.getTimeoutTime());
            protoOutputStream.write(1112396529669L, this.mShortFgsInfo.getProcStateDemoteTime());
            protoOutputStream.write(1112396529670L, this.mShortFgsInfo.getAnrTime());
            protoOutputStream.end(start7);
        }
        protoOutputStream.end(start);
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0326, code lost:
    
        if (r0 == 0) goto L65;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("intent={");
        printWriter.print(this.intent.getIntent().toShortString(false, true, false, false));
        printWriter.println('}');
        printWriter.print(str);
        printWriter.print("packageName=");
        printWriter.println(this.packageName);
        printWriter.print(str);
        printWriter.print("processName=");
        printWriter.println(this.processName);
        if (this.permission != null) {
            printWriter.print(str);
            printWriter.print("permission=");
            printWriter.println(this.permission);
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.appInfo != null) {
            printWriter.print(str);
            printWriter.print("baseDir=");
            printWriter.println(this.appInfo.sourceDir);
            ApplicationInfo applicationInfo = this.appInfo;
            if (!Objects.equals(applicationInfo.sourceDir, applicationInfo.publicSourceDir)) {
                printWriter.print(str);
                printWriter.print("resDir=");
                printWriter.println(this.appInfo.publicSourceDir);
            }
            printWriter.print(str);
            printWriter.print("dataDir=");
            printWriter.println(this.appInfo.dataDir);
        }
        printWriter.print(str);
        printWriter.print("app=");
        printWriter.println(this.app);
        if (this.isolationHostProc != null) {
            printWriter.print(str);
            printWriter.print("isolationHostProc=");
            printWriter.println(this.isolationHostProc);
        }
        if (this.allowlistManager) {
            printWriter.print(str);
            printWriter.print("allowlistManager=");
            printWriter.println(this.allowlistManager);
        }
        if (this.mIsAllowedBgActivityStartsByBinding) {
            printWriter.print(str);
            printWriter.print("mIsAllowedBgActivityStartsByBinding=");
            printWriter.println(this.mIsAllowedBgActivityStartsByBinding);
        }
        if (this.mBackgroundStartPrivilegesByStartMerged.allowsAny()) {
            printWriter.print(str);
            printWriter.print("mIsAllowedBgActivityStartsByStart=");
            printWriter.println(this.mBackgroundStartPrivilegesByStartMerged);
        }
        printWriter.print(str);
        printWriter.print("mAllowWhileInUsePermissionInFgsReason=");
        printWriter.println(PowerExemptionManager.reasonCodeToString(this.mAllowWhileInUsePermissionInFgsReason));
        printWriter.print(str);
        printWriter.print("allowUiJobScheduling=");
        printWriter.println(this.mAllowUiJobScheduling);
        printWriter.print(str);
        printWriter.print("recentCallingPackage=");
        printWriter.println(this.mRecentCallingPackage);
        printWriter.print(str);
        printWriter.print("recentCallingUid=");
        printWriter.println(this.mRecentCallingUid);
        printWriter.print(str);
        printWriter.print("allowStartForeground=");
        printWriter.println(PowerExemptionManager.reasonCodeToString(this.mAllowStartForeground));
        printWriter.print(str);
        printWriter.print("startForegroundCount=");
        printWriter.println(this.mStartForegroundCount);
        printWriter.print(str);
        printWriter.print("infoAllowStartForeground=");
        printWriter.println(this.mInfoAllowStartForeground);
        printWriter.print(str);
        printWriter.print("serviceDelayed=");
        printWriter.println(this.serviceDelayed);
        if (this.delayed) {
            printWriter.print(str);
            printWriter.print("delayed=");
            printWriter.println(this.delayed);
        }
        if (this.isForeground || this.foregroundId != 0) {
            printWriter.print(str);
            printWriter.print("isForeground=");
            printWriter.print(this.isForeground);
            printWriter.print(" foregroundId=");
            printWriter.print(this.foregroundId);
            printWriter.printf(" types=%08X", Integer.valueOf(this.foregroundServiceType));
            printWriter.print(" foregroundNoti=");
            printWriter.println(this.foregroundNoti);
            if (isShortFgs() && this.mShortFgsInfo != null) {
                printWriter.print(str);
                printWriter.print("isShortFgs=true");
                printWriter.print(" startId=");
                printWriter.print(this.mShortFgsInfo.getStartId());
                printWriter.print(" startForegroundCount=");
                printWriter.print(this.mShortFgsInfo.getStartForegroundCount());
                printWriter.print(" startTime=");
                TimeUtils.formatDuration(this.mShortFgsInfo.getStartTime(), uptimeMillis, printWriter);
                printWriter.print(" timeout=");
                TimeUtils.formatDuration(this.mShortFgsInfo.getTimeoutTime(), uptimeMillis, printWriter);
                printWriter.print(" demoteTime=");
                TimeUtils.formatDuration(this.mShortFgsInfo.getProcStateDemoteTime(), uptimeMillis, printWriter);
                printWriter.print(" anrTime=");
                TimeUtils.formatDuration(this.mShortFgsInfo.getAnrTime(), uptimeMillis, printWriter);
                printWriter.println();
            }
        }
        if (this.mIsFgsDelegate) {
            printWriter.print(str);
            printWriter.print("isFgsDelegate=");
            printWriter.println(this.mIsFgsDelegate);
        }
        printWriter.print(str);
        printWriter.print("createTime=");
        TimeUtils.formatDuration(this.createRealTime, elapsedRealtime, printWriter);
        printWriter.print(" startingBgTimeout=");
        TimeUtils.formatDuration(this.startingBgTimeout, uptimeMillis, printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("lastActivity=");
        TimeUtils.formatDuration(this.lastActivity, uptimeMillis, printWriter);
        printWriter.print(" restartTime=");
        TimeUtils.formatDuration(this.restartTime, uptimeMillis, printWriter);
        printWriter.print(" createdFromFg=");
        printWriter.println(this.createdFromFg);
        if (this.pendingConnectionGroup != 0) {
            printWriter.print(str);
            printWriter.print(" pendingConnectionGroup=");
            printWriter.print(this.pendingConnectionGroup);
            printWriter.print(" Importance=");
            printWriter.println(this.pendingConnectionImportance);
        }
        if (this.startRequested || this.delayedStop || this.lastStartId != 0) {
            printWriter.print(str);
            printWriter.print("startRequested=");
            printWriter.print(this.startRequested);
            printWriter.print(" delayedStop=");
            printWriter.print(this.delayedStop);
            printWriter.print(" stopIfKilled=");
            printWriter.print(this.stopIfKilled);
            printWriter.print(" callStart=");
            printWriter.print(this.callStart);
            printWriter.print(" lastStartId=");
            printWriter.println(this.lastStartId);
            printWriter.print(" startCommandResult=");
            printWriter.println(this.startCommandResult);
        }
        if (this.executeNesting != 0) {
            printWriter.print(str);
            printWriter.print("executeNesting=");
            printWriter.print(this.executeNesting);
            printWriter.print(" executeFg=");
            printWriter.print(this.executeFg);
            printWriter.print(" executingStart=");
            TimeUtils.formatDuration(this.executingStart, uptimeMillis, printWriter);
            printWriter.println();
        }
        if (this.destroying || this.destroyTime != 0) {
            printWriter.print(str);
            printWriter.print("destroying=");
            printWriter.print(this.destroying);
            printWriter.print(" destroyTime=");
            TimeUtils.formatDuration(this.destroyTime, uptimeMillis, printWriter);
            printWriter.println();
        }
        if (this.crashCount == 0 && this.restartCount == 0) {
            long j = this.nextRestartTime;
            if (j - this.mRestartSchedulingTime == 0) {
            }
        }
        printWriter.print(str);
        printWriter.print("restartCount=");
        printWriter.print(this.restartCount);
        printWriter.print(" restartDelay=");
        TimeUtils.formatDuration(this.nextRestartTime - this.mRestartSchedulingTime, uptimeMillis, printWriter);
        printWriter.print(" nextRestartTime=");
        TimeUtils.formatDuration(this.nextRestartTime, uptimeMillis, printWriter);
        printWriter.print(" crashCount=");
        printWriter.println(this.crashCount);
        if (this.deliveredStarts.size() > 0) {
            printWriter.print(str);
            printWriter.println("Delivered Starts:");
            dumpStartList(printWriter, str, this.deliveredStarts, uptimeMillis);
        }
        if (this.pendingStarts.size() > 0) {
            printWriter.print(str);
            printWriter.println("Pending Starts:");
            dumpStartList(printWriter, str, this.pendingStarts, 0L);
        }
        if (this.bindings.size() > 0) {
            printWriter.print(str);
            printWriter.println("Bindings:");
            for (int i = 0; i < this.bindings.size(); i++) {
                IntentBindRecord intentBindRecord = (IntentBindRecord) this.bindings.valueAt(i);
                printWriter.print(str);
                printWriter.print("* IntentBindRecord{");
                printWriter.print(Integer.toHexString(System.identityHashCode(intentBindRecord)));
                if ((intentBindRecord.collectFlags() & 1) != 0) {
                    printWriter.append(" CREATE");
                }
                printWriter.println("}:");
                intentBindRecord.dumpInService(printWriter, str + "  ");
            }
        }
        if (this.connections.size() > 0) {
            printWriter.print(str);
            printWriter.println("All Connections:");
            for (int i2 = 0; i2 < this.connections.size(); i2++) {
                ArrayList arrayList = (ArrayList) this.connections.valueAt(i2);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.println(arrayList.get(i3));
                }
            }
        }
    }

    public ServiceRecord(ActivityManagerService activityManagerService, ComponentName componentName, ComponentName componentName2, String str, int i, Intent.FilterComparison filterComparison, ServiceInfo serviceInfo, boolean z, Runnable runnable, String str2, int i2, String str3, boolean z2) {
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
        this.packageName = applicationInfo.packageName;
        this.isSdkSandbox = i2 != -1;
        this.sdkSandboxClientAppUid = i2;
        this.sdkSandboxClientAppPackage = str3;
        this.inSharedIsolatedProcess = z2;
        this.processName = str2;
        this.permission = serviceInfo.permission;
        this.exported = serviceInfo.exported;
        this.restarter = runnable;
        this.createRealTime = SystemClock.elapsedRealtime();
        this.lastActivity = SystemClock.uptimeMillis();
        this.userId = UserHandle.getUserId(this.appInfo.uid);
        this.createdFromFg = z;
        updateKeepWarmLocked();
        updateFgsHasNotificationPermission();
    }

    public ServiceState getTracker() {
        ServiceState serviceState = this.tracker;
        if (serviceState != null) {
            return serviceState;
        }
        ServiceInfo serviceInfo = this.serviceInfo;
        ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        if ((applicationInfo.flags & 8) == 0) {
            ServiceState serviceState2 = this.ams.mProcessStats.getServiceState(serviceInfo.packageName, applicationInfo.uid, applicationInfo.longVersionCode, serviceInfo.processName, serviceInfo.name);
            this.tracker = serviceState2;
            if (serviceState2 != null) {
                serviceState2.applyNewOwner(this);
            }
        }
        return this.tracker;
    }

    public void forceClearTracker() {
        ServiceState serviceState = this.tracker;
        if (serviceState != null) {
            serviceState.clearCurrentOwner(this, true);
            this.tracker = null;
        }
    }

    public void makeRestarting(int i, long j) {
        if (this.restartTracker == null) {
            ServiceInfo serviceInfo = this.serviceInfo;
            ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
            if ((applicationInfo.flags & 8) == 0) {
                this.restartTracker = this.ams.mProcessStats.getServiceState(serviceInfo.packageName, applicationInfo.uid, applicationInfo.longVersionCode, serviceInfo.processName, serviceInfo.name);
            }
            if (this.restartTracker == null) {
                return;
            }
        }
        this.restartTracker.setRestarting(true, i, j);
    }

    public void setProcess(ProcessRecord processRecord, IApplicationThread iApplicationThread, int i, UidRecord uidRecord) {
        if (processRecord != null) {
            ProcessRecord processRecord2 = this.mAppForAllowingBgActivityStartsByStart;
            if (processRecord2 != null && processRecord2 != processRecord) {
                processRecord2.removeBackgroundStartPrivileges(this);
                this.ams.mHandler.removeCallbacks(this.mCleanUpAllowBgActivityStartsByStartCallback);
            }
            this.mAppForAllowingBgActivityStartsByStart = this.mBackgroundStartPrivilegesByStartMerged.allowsAny() ? processRecord : null;
            BackgroundStartPrivileges backgroundStartPrivilegesWithExclusiveToken = getBackgroundStartPrivilegesWithExclusiveToken();
            if (backgroundStartPrivilegesWithExclusiveToken.allowsAny()) {
                processRecord.addOrUpdateBackgroundStartPrivileges(this, backgroundStartPrivilegesWithExclusiveToken);
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
        if (this.pendingConnectionGroup > 0 && processRecord != null) {
            ProcessServiceRecord processServiceRecord = processRecord.mServices;
            processServiceRecord.setConnectionService(this);
            processServiceRecord.setConnectionGroup(this.pendingConnectionGroup);
            processServiceRecord.setConnectionImportance(this.pendingConnectionImportance);
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
            processRecord.mServices.updateBoundClientUids();
            processRecord.mServices.updateHostingComonentTypeForBindingsLocked();
        }
    }

    public void updateProcessStateOnRequest() {
        ProcessRecord processRecord = this.app;
        this.mProcessStateOnRequest = (processRecord == null || processRecord.getThread() == null || this.app.isKilled()) ? 20 : this.app.mState.getCurProcState();
    }

    public ArrayMap getConnections() {
        return this.connections;
    }

    public void addConnection(IBinder iBinder, ConnectionRecord connectionRecord) {
        ArrayList arrayList = (ArrayList) this.connections.get(iBinder);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.connections.put(iBinder, arrayList);
        }
        arrayList.add(connectionRecord);
        ProcessRecord processRecord = this.app;
        if (processRecord != null) {
            processRecord.mServices.addBoundClientUid(connectionRecord.clientUid, connectionRecord.clientPackageName, connectionRecord.getFlags());
            this.app.mProfile.addHostingComponentType(512);
        }
    }

    public void removeConnection(IBinder iBinder) {
        this.connections.remove(iBinder);
        ProcessRecord processRecord = this.app;
        if (processRecord != null) {
            processRecord.mServices.updateBoundClientUids();
            this.app.mServices.updateHostingComonentTypeForBindingsLocked();
        }
    }

    public boolean canStopIfKilled(boolean z) {
        if (isShortFgs()) {
            return true;
        }
        return this.startRequested && (this.stopIfKilled || z) && this.pendingStarts.isEmpty();
    }

    public void updateIsAllowedBgActivityStartsByBinding() {
        boolean z = false;
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) this.connections.valueAt(size);
            int i = 0;
            while (true) {
                if (i >= arrayList.size()) {
                    break;
                }
                if (((ConnectionRecord) arrayList.get(i)).hasFlag(1048576)) {
                    z = true;
                    break;
                }
                i++;
            }
            if (z) {
                break;
            }
        }
        setAllowedBgActivityStartsByBinding(z);
    }

    public void setAllowedBgActivityStartsByBinding(boolean z) {
        this.mIsAllowedBgActivityStartsByBinding = z;
        updateParentProcessBgActivityStartsToken();
    }

    public void allowBgActivityStartsOnServiceStart(BackgroundStartPrivileges backgroundStartPrivileges) {
        Preconditions.checkArgument(backgroundStartPrivileges.allowsAny());
        this.mBackgroundStartPrivilegesByStart.add(backgroundStartPrivileges);
        setAllowedBgActivityStartsByStart(backgroundStartPrivileges.merge(this.mBackgroundStartPrivilegesByStartMerged));
        ProcessRecord processRecord = this.app;
        if (processRecord != null) {
            this.mAppForAllowingBgActivityStartsByStart = processRecord;
        }
        if (this.mCleanUpAllowBgActivityStartsByStartCallback == null) {
            this.mCleanUpAllowBgActivityStartsByStartCallback = new Runnable() { // from class: com.android.server.am.ServiceRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceRecord.this.lambda$allowBgActivityStartsOnServiceStart$0();
                }
            };
        }
        ActivityManagerService activityManagerService = this.ams;
        activityManagerService.mHandler.postDelayed(this.mCleanUpAllowBgActivityStartsByStartCallback, activityManagerService.mConstants.SERVICE_BG_ACTIVITY_START_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$allowBgActivityStartsOnServiceStart$0() {
        ActivityManagerService activityManagerService = this.ams;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mBackgroundStartPrivilegesByStart.remove(0);
                if (!this.mBackgroundStartPrivilegesByStart.isEmpty()) {
                    BackgroundStartPrivileges merge = BackgroundStartPrivileges.merge(this.mBackgroundStartPrivilegesByStart);
                    this.mBackgroundStartPrivilegesByStartMerged = merge;
                    if (merge.allowsAny()) {
                        ProcessRecord processRecord = this.mAppForAllowingBgActivityStartsByStart;
                        if (processRecord != null) {
                            processRecord.addOrUpdateBackgroundStartPrivileges(this, getBackgroundStartPrivilegesWithExclusiveToken());
                        }
                    } else {
                        Slog.wtf("ActivityManager", "Service callback to revoke bg activity starts by service start triggered but mBackgroundStartPrivilegesByStartMerged = " + this.mBackgroundStartPrivilegesByStartMerged + ". This should never happen.");
                    }
                } else {
                    ProcessRecord processRecord2 = this.app;
                    ProcessRecord processRecord3 = this.mAppForAllowingBgActivityStartsByStart;
                    if (processRecord2 == processRecord3) {
                        setAllowedBgActivityStartsByStart(BackgroundStartPrivileges.NONE);
                    } else if (processRecord3 != null) {
                        processRecord3.removeBackgroundStartPrivileges(this);
                    }
                    this.mAppForAllowingBgActivityStartsByStart = null;
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void updateAllowUiJobScheduling(boolean z) {
        if (this.mAllowUiJobScheduling == z) {
            return;
        }
        this.mAllowUiJobScheduling = z;
    }

    public final void setAllowedBgActivityStartsByStart(BackgroundStartPrivileges backgroundStartPrivileges) {
        if (this.mBackgroundStartPrivilegesByStartMerged == backgroundStartPrivileges) {
            return;
        }
        this.mBackgroundStartPrivilegesByStartMerged = backgroundStartPrivileges;
        updateParentProcessBgActivityStartsToken();
    }

    public final void updateParentProcessBgActivityStartsToken() {
        if (this.app == null) {
            return;
        }
        BackgroundStartPrivileges backgroundStartPrivilegesWithExclusiveToken = getBackgroundStartPrivilegesWithExclusiveToken();
        if (backgroundStartPrivilegesWithExclusiveToken.allowsAny()) {
            this.app.addOrUpdateBackgroundStartPrivileges(this, backgroundStartPrivilegesWithExclusiveToken);
        } else {
            this.app.removeBackgroundStartPrivileges(this);
        }
    }

    public final BackgroundStartPrivileges getBackgroundStartPrivilegesWithExclusiveToken() {
        if (this.mIsAllowedBgActivityStartsByBinding) {
            return BackgroundStartPrivileges.ALLOW_BAL;
        }
        if (this.mBackgroundStartPrivilegesByStart.isEmpty()) {
            return BackgroundStartPrivileges.NONE;
        }
        return this.mBackgroundStartPrivilegesByStartMerged;
    }

    public void updateKeepWarmLocked() {
        boolean z;
        if (this.ams.mConstants.KEEP_WARMING_SERVICES.contains(this.name)) {
            int currentUserId = this.ams.mUserController.getCurrentUserId();
            int i = this.userId;
            if (currentUserId == i || this.ams.mUserController.isCurrentProfile(i) || this.ams.isSingleton(this.processName, this.appInfo, this.instanceName.getClassName(), this.serviceInfo.flags)) {
                z = true;
                this.mKeepWarming = z;
            }
        }
        z = false;
        this.mKeepWarming = z;
    }

    public AppBindRecord retrieveAppBindingLocked(Intent intent, ProcessRecord processRecord, ProcessRecord processRecord2) {
        Intent.FilterComparison filterComparison = new Intent.FilterComparison(intent);
        IntentBindRecord intentBindRecord = (IntentBindRecord) this.bindings.get(filterComparison);
        if (intentBindRecord == null) {
            intentBindRecord = new IntentBindRecord(this, filterComparison);
            this.bindings.put(filterComparison, intentBindRecord);
        }
        AppBindRecord appBindRecord = (AppBindRecord) intentBindRecord.apps.get(processRecord);
        if (appBindRecord != null) {
            return appBindRecord;
        }
        AppBindRecord appBindRecord2 = new AppBindRecord(this, intentBindRecord, processRecord, processRecord2);
        intentBindRecord.apps.put(processRecord, appBindRecord2);
        return appBindRecord2;
    }

    public boolean hasAutoCreateConnections() {
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

    public void updateAllowlistManager() {
        this.allowlistManager = false;
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) this.connections.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                if (((ConnectionRecord) arrayList.get(i)).hasFlag(16777216)) {
                    this.allowlistManager = true;
                    return;
                }
            }
        }
    }

    public void resetRestartCounter() {
        this.restartCount = 0;
        this.restartDelay = 0L;
        this.restartTime = 0L;
        this.mEarliestRestartTime = 0L;
        this.mRestartSchedulingTime = 0L;
    }

    public StartItem findDeliveredStart(int i, boolean z, boolean z2) {
        int size = this.deliveredStarts.size();
        for (int i2 = 0; i2 < size; i2++) {
            StartItem startItem = (StartItem) this.deliveredStarts.get(i2);
            if (startItem.f1633id == i && startItem.taskRemoved == z) {
                if (z2) {
                    this.deliveredStarts.remove(i2);
                }
                return startItem;
            }
        }
        return null;
    }

    public int getLastStartId() {
        return this.lastStartId;
    }

    public int makeNextStartId() {
        int i = this.lastStartId + 1;
        this.lastStartId = i;
        if (i < 1) {
            this.lastStartId = 1;
        }
        return this.lastStartId;
    }

    public final void updateFgsHasNotificationPermission() {
        final String str = this.packageName;
        final int i = this.appInfo.uid;
        this.ams.mHandler.post(new Runnable() { // from class: com.android.server.am.ServiceRecord.1
            @Override // java.lang.Runnable
            public void run() {
                NotificationManagerInternal notificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                ServiceRecord.this.mFgsHasNotificationPermission = notificationManagerInternal.areNotificationsEnabledForPackage(str, i);
            }
        });
    }

    public void postNotification(final boolean z) {
        ProcessRecord processRecord;
        if (!this.isForeground || this.foregroundNoti == null || (processRecord = this.app) == null) {
            return;
        }
        final int i = this.appInfo.uid;
        final int pid = processRecord.getPid();
        final String str = this.packageName;
        final int i2 = this.foregroundId;
        final Notification notification = this.foregroundNoti;
        this.ams.mHandler.post(new Runnable() { // from class: com.android.server.am.ServiceRecord.2
            @Override // java.lang.Runnable
            public void run() {
                int i3;
                NotificationManagerInternal notificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                ServiceRecord.this.mFgsHasNotificationPermission = notificationManagerInternal.areNotificationsEnabledForPackage(str, i);
                Notification notification2 = notification;
                try {
                    if (notification2.getSmallIcon() == null) {
                        Slog.v("ActivityManager", "Attempted to start a foreground service (" + ServiceRecord.this.shortInstanceName + ") with a broken notification (no icon: " + notification2 + ")");
                        ServiceRecord serviceRecord = ServiceRecord.this;
                        CharSequence loadLabel = serviceRecord.appInfo.loadLabel(serviceRecord.ams.mContext.getPackageManager());
                        if (loadLabel == null) {
                            loadLabel = ServiceRecord.this.appInfo.packageName;
                        }
                        try {
                            ServiceRecord serviceRecord2 = ServiceRecord.this;
                            Notification.Builder builder = new Notification.Builder(serviceRecord2.ams.mContext.createPackageContextAsUser(serviceRecord2.appInfo.packageName, 0, new UserHandle(ServiceRecord.this.userId)), notification2.getChannelId());
                            builder.setSmallIcon(ServiceRecord.this.appInfo.icon);
                            builder.setFlag(64, true);
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", ServiceRecord.this.appInfo.packageName, null));
                            ServiceRecord serviceRecord3 = ServiceRecord.this;
                            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(serviceRecord3.ams.mContext, 0, intent, 201326592, null, UserHandle.of(serviceRecord3.userId));
                            builder.setColor(ServiceRecord.this.ams.mContext.getColor(R.color.system_notification_accent_color));
                            builder.setContentTitle(ServiceRecord.this.ams.mContext.getString(R.string.close_button_text, loadLabel));
                            builder.setContentText(ServiceRecord.this.ams.mContext.getString(R.string.clone_profile_label_badge, loadLabel));
                            builder.setContentIntent(activityAsUser);
                            notification2 = builder.build();
                        } catch (PackageManager.NameNotFoundException unused) {
                        }
                    }
                    if (notificationManagerInternal.getNotificationChannel(str, i, notification2.getChannelId()) == null) {
                        try {
                            PackageManager packageManager = ServiceRecord.this.ams.mContext.getPackageManager();
                            ServiceRecord serviceRecord4 = ServiceRecord.this;
                            i3 = packageManager.getApplicationInfoAsUser(serviceRecord4.appInfo.packageName, 0, serviceRecord4.userId).targetSdkVersion;
                        } catch (PackageManager.NameNotFoundException unused2) {
                            i3 = 27;
                        }
                        if (i3 >= 27) {
                            throw new RuntimeException("invalid channel for service notification: " + ServiceRecord.this.foregroundNoti);
                        }
                    }
                    if (notification2.getSmallIcon() == null) {
                        throw new RuntimeException("invalid service notification: " + ServiceRecord.this.foregroundNoti);
                    }
                    String str2 = str;
                    notificationManagerInternal.enqueueNotification(str2, str2, i, pid, null, i2, notification2, ServiceRecord.this.userId, z);
                    ServiceRecord serviceRecord5 = ServiceRecord.this;
                    serviceRecord5.foregroundNoti = notification2;
                    serviceRecord5.signalForegroundServiceNotification(serviceRecord5.packageName, serviceRecord5.appInfo.uid, i2, false);
                } catch (RuntimeException e) {
                    Slog.w("ActivityManager", "Error showing notification for service", e);
                    ServiceRecord.this.ams.mServices.killMisbehavingService(this, i, pid, str, 2);
                }
            }
        });
    }

    public void cancelNotification() {
        final String str = this.packageName;
        final int i = this.foregroundId;
        final int i2 = this.appInfo.uid;
        ProcessRecord processRecord = this.app;
        final int pid = processRecord != null ? processRecord.getPid() : 0;
        this.ams.mHandler.post(new Runnable() { // from class: com.android.server.am.ServiceRecord.3
            @Override // java.lang.Runnable
            public void run() {
                NotificationManagerInternal notificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                try {
                    String str2 = str;
                    notificationManagerInternal.cancelNotification(str2, str2, i2, pid, null, i, ServiceRecord.this.userId);
                } catch (RuntimeException e) {
                    Slog.w("ActivityManager", "Error canceling notification for service", e);
                }
                ServiceRecord serviceRecord = ServiceRecord.this;
                serviceRecord.signalForegroundServiceNotification(serviceRecord.packageName, serviceRecord.appInfo.uid, i, true);
            }
        });
    }

    public final void signalForegroundServiceNotification(String str, int i, int i2, boolean z) {
        ActivityManagerService activityManagerService = this.ams;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                for (int size = this.ams.mForegroundServiceStateListeners.size() - 1; size >= 0; size--) {
                    ((ActivityManagerInternal.ForegroundServiceStateListener) this.ams.mForegroundServiceStateListeners.get(size)).onForegroundServiceNotificationUpdated(str, this.appInfo.uid, i2, z);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void stripForegroundServiceFlagFromNotification() {
        final int i = this.foregroundId;
        final int i2 = this.userId;
        final String str = this.packageName;
        this.ams.mHandler.post(new Runnable() { // from class: com.android.server.am.ServiceRecord.4
            @Override // java.lang.Runnable
            public void run() {
                NotificationManagerInternal notificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                notificationManagerInternal.removeForegroundServiceFlagFromNotification(str, i, i2);
            }
        });
    }

    public void clearDeliveredStartsLocked() {
        for (int size = this.deliveredStarts.size() - 1; size >= 0; size--) {
            ((StartItem) this.deliveredStarts.get(size)).removeUriPermissionsLocked();
        }
        this.deliveredStarts.clear();
    }

    public String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("ServiceRecord{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" u");
        sb.append(this.userId);
        sb.append(' ');
        sb.append(this.shortInstanceName);
        sb.append('}');
        String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    public ComponentName getComponentName() {
        return this.name;
    }

    public boolean isShortFgs() {
        return this.startRequested && this.isForeground && this.foregroundServiceType == 2048;
    }

    public ShortFgsInfo getShortFgsInfo() {
        if (isShortFgs()) {
            return this.mShortFgsInfo;
        }
        return null;
    }

    public void setShortFgsInfo(long j) {
        this.mShortFgsInfo = new ShortFgsInfo(j);
    }

    public boolean hasShortFgsInfo() {
        return this.mShortFgsInfo != null;
    }

    public void clearShortFgsInfo() {
        this.mShortFgsInfo = null;
    }

    public final boolean shouldTriggerShortFgsTimedEvent(long j, long j2) {
        ShortFgsInfo shortFgsInfo;
        return isAppAlive() && this.startRequested && isShortFgs() && (shortFgsInfo = this.mShortFgsInfo) != null && shortFgsInfo.isCurrent() && j <= j2;
    }

    public boolean shouldTriggerShortFgsTimeout(long j) {
        ShortFgsInfo shortFgsInfo = this.mShortFgsInfo;
        return shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getTimeoutTime(), j);
    }

    public boolean shouldDemoteShortFgsProcState(long j) {
        ShortFgsInfo shortFgsInfo = this.mShortFgsInfo;
        return shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getProcStateDemoteTime(), j);
    }

    public boolean shouldTriggerShortFgsAnr(long j) {
        ShortFgsInfo shortFgsInfo = this.mShortFgsInfo;
        return shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getAnrTime(), j);
    }

    public final boolean isAppAlive() {
        ProcessRecord processRecord = this.app;
        return (processRecord == null || processRecord.getThread() == null || this.app.isKilled() || this.app.isKilledByAm()) ? false : true;
    }
}
