package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.BackgroundStartPrivileges;
import android.app.ForegroundServiceDelegationOptions;
import android.app.ForegroundServiceTypePolicy;
import android.app.IApplicationThread;
import android.app.IForegroundServiceObserver;
import android.app.IServiceConnection;
import android.app.InvalidForegroundServiceTypeException;
import android.app.MissingForegroundServiceTypeException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteServiceException;
import android.app.ServiceStartArgs;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ServiceInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerExemptionManager;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.TransactionTooLargeException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.SomeArgs;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppStateTracker;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.ServiceRecord;
import com.android.server.am.ServiceRecord.ShortFgsInfo;
import com.android.server.am.mars.MARsFreezeStateRecord;
import com.android.server.appop.AppOpsService;
import com.android.server.chimera.ChimeraManagerService;
import com.android.server.chimera.RestartImmediatePackages;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.notification.NotificationManagerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.uri.UriPermissionOwner;
import com.android.server.utils.AnrTimer;
import com.android.server.wm.ActivityServiceConnectionsHolder;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowProcessController;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActiveServices {
    public final ProcessAnrTimer mActiveServiceAnrTimer;
    public final ActivityManagerService mAm;
    public AppStateTracker mAppStateTracker;
    public String mCachedDeviceProvisioningPackage;
    public final ForegroundServiceTypeLoggerModule mFGSLogger;
    public String mLastAnrDump;
    public final AnonymousClass1 mLastAnrDumpClearer;
    public int mMaxStartingBackground;
    public final AnonymousClass1 mPostDeferredFGSNotifications;
    public final ProcessAnrTimer mServiceFGAnrTimer;
    public final ProcessAnrTimer mShortFGSAnrTimer;
    public static final AtomicReference sNumForegroundServices = new AtomicReference(new Pair(0, 0));
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final List mRevivalServicesMessages = new ArrayList();
    public final SparseArray mServiceMap = new SparseArray();
    public final ArrayMap mServiceConnections = new ArrayMap();
    public final ArrayList mPendingServices = new ArrayList();
    public final ArrayList mRestartingServices = new ArrayList();
    public final ArrayList mDestroyingServices = new ArrayList();
    public final ArrayList mPendingFgsNotifications = new ArrayList();
    public final ArrayMap mFgsDelegations = new ArrayMap();
    public long mBindServiceSeqCounter = 0;
    public boolean mFgsDeferralRateLimited = true;
    public final SparseLongArray mFgsDeferralEligible = new SparseLongArray();
    public final RemoteCallbackList mFgsObservers = new RemoteCallbackList();
    public final ArrayMap mPendingBringups = new ArrayMap();
    public ArrayList mTmpCollectionResults = null;
    public final SparseArray mFgsAppOpCallbacks = new SparseArray();
    public final ArraySet mRestartBackoffDisabledPackages = new ArraySet();
    public boolean mScreenOn = true;
    public final SparseArray mTimeLimitedFgsInfo = new SparseArray();
    public final ArraySet mAllowListWhileInUsePermissionInFgs = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActiveForegroundApp {
        public boolean mAppOnTop;
        public long mEndTime;
        public long mHideTime;
        public int mNumActive;
        public String mPackageName;
        public boolean mShownWhileScreenOn;
        public boolean mShownWhileTop;
        public long mStartTime;
        public long mStartVisibleTime;
        public int mUid;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppOpCallback {
        public static final int[] LOGGED_AP_OPS = {0, 1, 27, 26};
        public final AppOpsManager mAppOpsManager;
        public final ProcessRecord mProcessRecord;
        public final SparseIntArray mAcceptedOps = new SparseIntArray();
        public final SparseIntArray mRejectedOps = new SparseIntArray();
        public final Object mCounterLock = new Object();
        public final SparseIntArray mAppOpModes = new SparseIntArray();
        public int mNumFgs = 0;
        public boolean mDestroyed = false;
        public final AnonymousClass1 mOpNotedCallback = new AppOpsManager.OnOpNotedInternalListener() { // from class: com.android.server.am.ActiveServices.AppOpCallback.1
            public final void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
                AppOpCallback.m164$$Nest$mincrementOpCountIfNeeded(AppOpCallback.this, i, i2, i4);
            }
        };
        public final AnonymousClass2 mOpStartedCallback = new AppOpsManager.OnOpStartedListener() { // from class: com.android.server.am.ActiveServices.AppOpCallback.2
            public final void onOpStarted(int i, int i2, String str, String str2, int i3, int i4) {
                AppOpCallback.m164$$Nest$mincrementOpCountIfNeeded(AppOpCallback.this, i, i2, i4);
            }
        };

        /* renamed from: -$$Nest$mincrementOpCountIfNeeded, reason: not valid java name */
        public static void m164$$Nest$mincrementOpCountIfNeeded(AppOpCallback appOpCallback, int i, int i2, int i3) {
            ProcessRecord processRecord = appOpCallback.mProcessRecord;
            if (i2 != processRecord.uid || processRecord.mState.mCurProcState == 2) {
                return;
            }
            boolean z = i3 == 0;
            synchronized (appOpCallback.mCounterLock) {
                try {
                    SparseIntArray sparseIntArray = z ? appOpCallback.mAcceptedOps : appOpCallback.mRejectedOps;
                    int indexOfKey = sparseIntArray.indexOfKey(i);
                    if (indexOfKey < 0) {
                        sparseIntArray.put(i, 1);
                    } else {
                        sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
                    }
                } finally {
                }
            }
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.am.ActiveServices$AppOpCallback$1] */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.am.ActiveServices$AppOpCallback$2] */
        public AppOpCallback(ProcessRecord processRecord, AppOpsManager appOpsManager) {
            this.mProcessRecord = processRecord;
            this.mAppOpsManager = appOpsManager;
            int[] iArr = LOGGED_AP_OPS;
            for (int i = 0; i < 4; i++) {
                int i2 = iArr[i];
                this.mAppOpModes.put(i2, appOpsManager.unsafeCheckOpRawNoThrow(i2, processRecord.uid, processRecord.info.packageName));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BackgroundRestrictedListener implements AppStateTracker.BackgroundRestrictedAppListener {
        public BackgroundRestrictedListener() {
        }

        public final void updateBackgroundRestrictedForUidPackage(int i, final String str, final boolean z) {
            if (MARsPolicyManager.MARs_ENABLE) {
                MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                mARsPolicyManager.getClass();
                if (MARsPolicyManager.isChinaPolicyEnabled() && mARsPolicyManager.isMARsTarget(UserHandle.getUserId(i), str)) {
                    return;
                }
            }
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    final ProcessList processList = ActiveServices.this.mAm.mProcessList;
                    UidRecord uidRecord = processList.mActiveUids.get(i);
                    if (uidRecord != null) {
                        final long elapsedRealtime = SystemClock.elapsedRealtime();
                        uidRecord.forEachProcess(new Consumer() { // from class: com.android.server.am.ProcessList$$ExternalSyntheticLambda8
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ProcessList processList2 = ProcessList.this;
                                String str2 = str;
                                boolean z2 = z;
                                long j = elapsedRealtime;
                                ProcessRecord processRecord = (ProcessRecord) obj;
                                processList2.getClass();
                                if (TextUtils.equals(processRecord.info.packageName, str2)) {
                                    processRecord.mState.mBackgroundRestricted = z2;
                                    if (z2) {
                                        processList2.mAppsInBackgroundRestricted.add(processRecord);
                                        long killAppIfBgRestrictedAndCachedIdleLocked = processList2.killAppIfBgRestrictedAndCachedIdleLocked(processRecord, j);
                                        if (killAppIfBgRestrictedAndCachedIdleLocked > 0) {
                                            ActivityManagerService activityManagerService2 = processList2.mService;
                                            if (activityManagerService2.mDeterministicUidIdle || !activityManagerService2.mHandler.hasMessages(58)) {
                                                processList2.mService.mHandler.sendEmptyMessageDelayed(58, killAppIfBgRestrictedAndCachedIdleLocked - j);
                                            }
                                        }
                                    } else {
                                        processList2.mAppsInBackgroundRestricted.remove(processRecord);
                                    }
                                    if (processRecord.mKilledByAm) {
                                        return;
                                    }
                                    processList2.mService.enqueueOomAdjTargetLocked(processRecord);
                                }
                            }
                        });
                        processList.mService.updateOomAdjPendingTargetsLocked(21);
                    }
                    if (!ActiveServices.this.isForegroundServiceAllowedInBackgroundRestricted(i, str) && !ActiveServices.this.isTempAllowedByAlarmClock(i)) {
                        ActiveServices.this.stopAllForegroundServicesLocked(i, str);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessAnrTimer extends AnrTimer {
        public final /* synthetic */ int $r8$classId = 1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ProcessAnrTimer(int i, ActivityManagerService activityManagerService, String str) {
            super(activityManagerService.mHandler, i, str, new AnrTimer.Args());
            Objects.requireNonNull(activityManagerService);
        }

        public /* synthetic */ ProcessAnrTimer(Handler handler, int i, String str, AnrTimer.Args args) {
            super(handler, i, str, args);
        }

        @Override // com.android.server.utils.AnrTimer
        public final int getPid(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    return ((ProcessRecord) obj).mPid;
                default:
                    ProcessRecord processRecord = ((ServiceRecord) obj).app;
                    if (processRecord != null) {
                        return processRecord.mPid;
                    }
                    return 0;
            }
        }

        @Override // com.android.server.utils.AnrTimer
        public final int getUid(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    return ((ProcessRecord) obj).uid;
                default:
                    ApplicationInfo applicationInfo = ((ServiceRecord) obj).appInfo;
                    if (applicationInfo != null) {
                        return applicationInfo.uid;
                    }
                    return 0;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceDumper {
        public final String[] args;
        public final boolean dumpAll;
        public final String dumpPackage;
        public final FileDescriptor fd;
        public final ActivityManagerService.ItemMatcher matcher;
        public final PrintWriter pw;
        public final ArrayList services = new ArrayList();
        public final long nowReal = SystemClock.elapsedRealtime();
        public boolean needSep = false;
        public boolean printedAnything = false;
        public boolean printed = false;

        public ServiceDumper(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, int i, boolean z, String str) {
            this.fd = fileDescriptor;
            this.pw = printWriter;
            this.args = strArr;
            this.dumpAll = z;
            this.dumpPackage = str;
            ActivityManagerService.ItemMatcher itemMatcher = new ActivityManagerService.ItemMatcher();
            this.matcher = itemMatcher;
            while (i < strArr.length) {
                String str2 = strArr[i];
                if ("--".equals(str2)) {
                    break;
                }
                itemMatcher.build(str2);
                i++;
            }
            for (int i2 : ActiveServices.this.mAm.mUserController.getUsers()) {
                ServiceMap serviceMapLocked = ActiveServices.this.getServiceMapLocked(i2);
                if (serviceMapLocked.mServicesByInstanceName.size() > 0) {
                    for (int i3 = 0; i3 < serviceMapLocked.mServicesByInstanceName.size(); i3++) {
                        ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.valueAt(i3);
                        if (this.matcher.match(serviceRecord.name, serviceRecord) && (str == null || str.equals(serviceRecord.appInfo.packageName))) {
                            this.services.add(serviceRecord);
                        }
                    }
                }
            }
        }

        public final void dumpHeaderLocked() {
            this.pw.println("ACTIVITY MANAGER SERVICES (dumpsys activity services)");
            ActiveServices activeServices = ActiveServices.this;
            if (activeServices.mLastAnrDump != null) {
                this.pw.println("  Last ANR service:");
                this.pw.print(activeServices.mLastAnrDump);
                this.pw.println();
            }
        }

        public final void dumpLocked() {
            dumpHeaderLocked();
            try {
                for (int i : ActiveServices.this.mAm.mUserController.getUsers()) {
                    int i2 = 0;
                    while (i2 < this.services.size() && ((ServiceRecord) this.services.get(i2)).userId != i) {
                        i2++;
                    }
                    this.printed = false;
                    if (i2 < this.services.size()) {
                        this.needSep = false;
                        while (i2 < this.services.size()) {
                            ServiceRecord serviceRecord = (ServiceRecord) this.services.get(i2);
                            i2++;
                            if (serviceRecord.userId != i) {
                                break;
                            } else {
                                dumpServiceLocalLocked(serviceRecord);
                            }
                        }
                        this.needSep |= this.printed;
                    }
                    dumpUserRemainsLocked(i);
                }
            } catch (Exception e) {
                Slog.w("ActivityManager", "Exception in dumpServicesLocked", e);
            }
            dumpRemainsLocked();
        }

        public final void dumpRemainsLocked() {
            String str;
            ProcessRecord processRecord;
            String str2;
            String str3;
            String str4;
            boolean z = false;
            if (ActiveServices.this.mPendingServices.size() > 0) {
                this.printed = false;
                for (int i = 0; i < ActiveServices.this.mPendingServices.size(); i++) {
                    ServiceRecord serviceRecord = (ServiceRecord) ActiveServices.this.mPendingServices.get(i);
                    if (this.matcher.match(serviceRecord.name, serviceRecord) && ((str4 = this.dumpPackage) == null || str4.equals(serviceRecord.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.needSep = true;
                            this.pw.println("  Pending services:");
                            this.printed = true;
                        }
                        this.pw.print("  * Pending ");
                        this.pw.println(serviceRecord);
                        serviceRecord.dump(this.pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (ActiveServices.this.mRestartingServices.size() > 0) {
                this.printed = false;
                for (int i2 = 0; i2 < ActiveServices.this.mRestartingServices.size(); i2++) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) ActiveServices.this.mRestartingServices.get(i2);
                    if (this.matcher.match(serviceRecord2.name, serviceRecord2) && ((str3 = this.dumpPackage) == null || str3.equals(serviceRecord2.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.needSep = true;
                            this.pw.println("  Restarting services:");
                            this.printed = true;
                        }
                        this.pw.print("  * Restarting ");
                        this.pw.println(serviceRecord2);
                        serviceRecord2.dump(this.pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (ActiveServices.this.mDestroyingServices.size() > 0) {
                this.printed = false;
                for (int i3 = 0; i3 < ActiveServices.this.mDestroyingServices.size(); i3++) {
                    ServiceRecord serviceRecord3 = (ServiceRecord) ActiveServices.this.mDestroyingServices.get(i3);
                    if (this.matcher.match(serviceRecord3.name, serviceRecord3) && ((str2 = this.dumpPackage) == null || str2.equals(serviceRecord3.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.needSep = true;
                            this.pw.println("  Destroying services:");
                            this.printed = true;
                        }
                        this.pw.print("  * Destroy ");
                        this.pw.println(serviceRecord3);
                        serviceRecord3.dump(this.pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (this.dumpAll) {
                this.printed = false;
                for (int i4 = 0; i4 < ActiveServices.this.mServiceConnections.size(); i4++) {
                    ArrayList arrayList = (ArrayList) ActiveServices.this.mServiceConnections.valueAt(i4);
                    for (int i5 = 0; i5 < arrayList.size(); i5++) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i5);
                        ActivityManagerService.ItemMatcher itemMatcher = this.matcher;
                        ServiceRecord serviceRecord4 = connectionRecord.binding.service;
                        if (itemMatcher.match(serviceRecord4.name, serviceRecord4) && ((str = this.dumpPackage) == null || ((processRecord = connectionRecord.binding.client) != null && str.equals(processRecord.info.packageName)))) {
                            this.printedAnything = true;
                            if (!this.printed) {
                                if (this.needSep) {
                                    this.pw.println();
                                }
                                this.needSep = true;
                                this.pw.println("  Connection bindings to services:");
                                this.printed = true;
                            }
                            this.pw.print("  * ");
                            this.pw.println(connectionRecord);
                            PrintWriter printWriter = this.pw;
                            printWriter.println("    binding=" + connectionRecord.binding);
                            ActivityServiceConnectionsHolder activityServiceConnectionsHolder = connectionRecord.activity;
                            if (activityServiceConnectionsHolder != null) {
                                printWriter.println("    activity=" + activityServiceConnectionsHolder.mActivity);
                            }
                            printWriter.println("    conn=" + connectionRecord.conn.asBinder() + " flags=0x" + Long.toHexString(connectionRecord.flags));
                        }
                    }
                }
            }
            if (this.matcher.all) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int[] users = ActiveServices.this.mAm.mUserController.getUsers();
                int length = users.length;
                int i6 = 0;
                while (i6 < length) {
                    int i7 = users[i6];
                    ServiceMap serviceMap = (ServiceMap) ActiveServices.this.mServiceMap.get(i7);
                    if (serviceMap != null) {
                        boolean z2 = z;
                        for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                            ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size);
                            String str5 = this.dumpPackage;
                            if (str5 == null || str5.equals(activeForegroundApp.mPackageName)) {
                                if (!z2) {
                                    this.printedAnything = true;
                                    if (this.needSep) {
                                        this.pw.println();
                                    }
                                    this.needSep = true;
                                    this.pw.print("Active foreground apps - user ");
                                    this.pw.print(i7);
                                    this.pw.println(":");
                                    z2 = true;
                                }
                                this.pw.print("  #");
                                this.pw.print(size);
                                this.pw.print(": ");
                                this.pw.println(activeForegroundApp.mPackageName);
                                this.pw.print("    mNumActive=");
                                this.pw.print(activeForegroundApp.mNumActive);
                                this.pw.print(" mAppOnTop=");
                                this.pw.print(activeForegroundApp.mAppOnTop);
                                this.pw.print(" mShownWhileTop=");
                                this.pw.print(activeForegroundApp.mShownWhileTop);
                                this.pw.print(" mShownWhileScreenOn=");
                                this.pw.println(activeForegroundApp.mShownWhileScreenOn);
                                this.pw.print("    mStartTime=");
                                boolean z3 = z2;
                                TimeUtils.formatDuration(activeForegroundApp.mStartTime - elapsedRealtime, this.pw);
                                this.pw.print(" mStartVisibleTime=");
                                TimeUtils.formatDuration(activeForegroundApp.mStartVisibleTime - elapsedRealtime, this.pw);
                                this.pw.println();
                                if (activeForegroundApp.mEndTime != 0) {
                                    this.pw.print("    mEndTime=");
                                    TimeUtils.formatDuration(activeForegroundApp.mEndTime - elapsedRealtime, this.pw);
                                    this.pw.println();
                                }
                                z2 = z3;
                            }
                        }
                        if (serviceMap.hasMessagesOrCallbacks()) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.printedAnything = true;
                            this.needSep = true;
                            this.pw.print("  Handler - user ");
                            this.pw.print(i7);
                            this.pw.println(":");
                            serviceMap.dumpMine(new PrintWriterPrinter(this.pw), "    ");
                        }
                    }
                    i6++;
                    z = false;
                }
            }
            if (this.printedAnything) {
                return;
            }
            this.pw.println("  (nothing)");
        }

        public final void dumpServiceClient(ServiceRecord serviceRecord) {
            IApplicationThread iApplicationThread;
            ProcessRecord processRecord = serviceRecord.app;
            if (processRecord == null || (iApplicationThread = processRecord.mThread) == null) {
                return;
            }
            this.pw.println("    Client:");
            this.pw.flush();
            try {
                TransferPipe transferPipe = new TransferPipe();
                try {
                    iApplicationThread.dumpService(transferPipe.getWriteFd(), serviceRecord, this.args);
                    transferPipe.setBufferPrefix("      ");
                    transferPipe.go(this.fd, 2000L);
                    transferPipe.kill();
                } catch (Throwable th) {
                    transferPipe.kill();
                    throw th;
                }
            } catch (RemoteException unused) {
                this.pw.println("      Got a RemoteException while dumping the service");
            } catch (IOException e) {
                this.pw.println("      Failure while dumping the service: " + e);
            }
            this.needSep = true;
        }

        public final void dumpServiceLocalLocked(ServiceRecord serviceRecord) {
            int i = serviceRecord.userId;
            if (!this.printed) {
                if (this.printedAnything) {
                    this.pw.println();
                }
                ActiveServices$$ExternalSyntheticOutline0.m(i, this.pw, "  User ", " active services:");
                this.printed = true;
            }
            this.printedAnything = true;
            if (this.needSep) {
                this.pw.println();
            }
            this.pw.print("  * ");
            this.pw.println(serviceRecord);
            if (this.dumpAll) {
                serviceRecord.dump(this.pw, "    ");
                this.needSep = true;
                return;
            }
            this.pw.print("    app=");
            this.pw.println(serviceRecord.app);
            this.pw.print("    created=");
            TimeUtils.formatDuration(serviceRecord.createRealTime, this.nowReal, this.pw);
            this.pw.print(" started=");
            this.pw.print(serviceRecord.startRequested);
            this.pw.print(" connections=");
            ArrayMap arrayMap = serviceRecord.connections;
            this.pw.println(arrayMap.size());
            if (arrayMap.size() > 0) {
                this.pw.println("    Connections:");
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    ArrayList arrayList = (ArrayList) arrayMap.valueAt(i2);
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i3);
                        this.pw.print("      ");
                        this.pw.print(connectionRecord.binding.intent.intent.getIntent().toShortString(false, false, false, false));
                        this.pw.print(" -> ");
                        ProcessRecord processRecord = connectionRecord.binding.client;
                        this.pw.println(processRecord != null ? processRecord.toShortString() : "null");
                    }
                }
            }
        }

        public final void dumpUserRemainsLocked(int i) {
            String str;
            ActivityManagerService.ItemMatcher itemMatcher;
            ServiceMap serviceMapLocked = ActiveServices.this.getServiceMapLocked(i);
            this.printed = false;
            int size = serviceMapLocked.mDelayedStartList.size();
            int i2 = 0;
            while (true) {
                str = this.dumpPackage;
                itemMatcher = this.matcher;
                if (i2 >= size) {
                    break;
                }
                ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mDelayedStartList.get(i2);
                if (itemMatcher.match(serviceRecord.name, serviceRecord) && (str == null || str.equals(serviceRecord.appInfo.packageName))) {
                    if (!this.printed) {
                        if (this.printedAnything) {
                            this.pw.println();
                        }
                        ActiveServices$$ExternalSyntheticOutline0.m(i, this.pw, "  User ", " delayed start services:");
                        this.printed = true;
                    }
                    this.printedAnything = true;
                    this.pw.print("  * Delayed start ");
                    this.pw.println(serviceRecord);
                }
                i2++;
            }
            this.printed = false;
            int size2 = serviceMapLocked.mStartingBackground.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMapLocked.mStartingBackground.get(i3);
                if (itemMatcher.match(serviceRecord2.name, serviceRecord2) && (str == null || str.equals(serviceRecord2.appInfo.packageName))) {
                    if (!this.printed) {
                        if (this.printedAnything) {
                            this.pw.println();
                        }
                        ActiveServices$$ExternalSyntheticOutline0.m(i, this.pw, "  User ", " starting in background:");
                        this.printed = true;
                    }
                    this.printedAnything = true;
                    this.pw.print("  * Starting bg ");
                    this.pw.println(serviceRecord2);
                }
            }
        }

        public final void dumpWithClient() {
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    dumpHeaderLocked();
                } finally {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            try {
                for (int i : ActiveServices.this.mAm.mUserController.getUsers()) {
                    int i2 = 0;
                    while (i2 < this.services.size() && ((ServiceRecord) this.services.get(i2)).userId != i) {
                        i2++;
                    }
                    this.printed = false;
                    if (i2 < this.services.size()) {
                        this.needSep = false;
                        while (i2 < this.services.size()) {
                            ServiceRecord serviceRecord = (ServiceRecord) this.services.get(i2);
                            i2++;
                            if (serviceRecord.userId != i) {
                                break;
                            }
                            ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService2) {
                                try {
                                    dumpServiceLocalLocked(serviceRecord);
                                } finally {
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            dumpServiceClient(serviceRecord);
                        }
                        this.needSep |= this.printed;
                    }
                    ActivityManagerService activityManagerService3 = ActiveServices.this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService3) {
                        try {
                            dumpUserRemainsLocked(i);
                        } finally {
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Exception e) {
                Slog.w("ActivityManager", "Exception in dumpServicesLocked", e);
            }
            ActivityManagerService activityManagerService4 = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService4) {
                try {
                    dumpRemainsLocked();
                } finally {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceLookupResult {
        public final ComponentName aliasComponent;
        public final String permission;
        public final ServiceRecord record;

        public ServiceLookupResult(ServiceRecord serviceRecord, ComponentName componentName) {
            this.record = serviceRecord;
            this.permission = null;
            this.aliasComponent = componentName;
        }

        public ServiceLookupResult(String str) {
            this.record = null;
            this.permission = str;
            this.aliasComponent = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceMap extends Handler {
        public final ArrayMap mActiveForegroundApps;
        public boolean mActiveForegroundAppsChanged;
        public final ArrayList mDelayServiceList;
        public final ArrayList mDelayedStartList;
        public final ArrayList mPendingRemoveForegroundApps;
        public final ArrayMap mServicesByInstanceName;
        public final ArrayMap mServicesByIntent;
        public final ArrayList mStartingBackground;
        public final int mUserId;

        public ServiceMap(Looper looper, int i) {
            super(looper);
            this.mServicesByInstanceName = new ArrayMap();
            this.mServicesByIntent = new ArrayMap();
            this.mDelayedStartList = new ArrayList();
            this.mDelayServiceList = new ArrayList();
            this.mStartingBackground = new ArrayList();
            this.mActiveForegroundApps = new ArrayMap();
            this.mPendingRemoveForegroundApps = new ArrayList();
            this.mUserId = i;
        }

        public final void ensureNotStartingBackgroundLocked(ServiceRecord serviceRecord) {
            if (this.mStartingBackground.remove(serviceRecord)) {
                removeMessages(3);
                sendMessage(obtainMessage(3));
            }
            this.mDelayedStartList.remove(serviceRecord);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ActivityManagerService activityManagerService = ActiveServices.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        rescheduleDelayedStartsLocked();
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService2) {
                        try {
                            rescheduleDelayedStartsLocked();
                        } finally {
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (i != 101) {
                    return;
                }
                ActivityManagerService activityManagerService3 = ActiveServices.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService3) {
                    try {
                        startDelayedServiceLocked();
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            }
            ActiveServices activeServices = ActiveServices.this;
            ActivityManagerService activityManagerService4 = activeServices.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService4) {
                try {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    this.mPendingRemoveForegroundApps.clear();
                    int size = this.mActiveForegroundApps.size() - 1;
                    long j = Long.MAX_VALUE;
                    while (size >= 0) {
                        ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) this.mActiveForegroundApps.valueAt(size);
                        if (activeForegroundApp.mEndTime != 0) {
                            if (activeServices.foregroundAppShownEnoughLocked(activeForegroundApp, elapsedRealtime)) {
                                this.mPendingRemoveForegroundApps.add((String) this.mActiveForegroundApps.keyAt(size));
                                this.mActiveForegroundAppsChanged = true;
                                size--;
                            } else {
                                long j2 = activeForegroundApp.mHideTime;
                                if (j2 < j) {
                                    j = j2;
                                }
                            }
                        }
                        if (!activeForegroundApp.mAppOnTop && !activeServices.isForegroundServiceAllowedInBackgroundRestricted(activeForegroundApp.mUid, activeForegroundApp.mPackageName)) {
                            int size2 = this.mActiveForegroundApps.size();
                            activeServices.stopAllForegroundServicesLocked(activeForegroundApp.mUid, activeForegroundApp.mPackageName);
                            int size3 = size2 - this.mActiveForegroundApps.size();
                            if (size3 > 1) {
                                size = (size - size3) + 1;
                            }
                        }
                        size--;
                    }
                    for (int size4 = this.mPendingRemoveForegroundApps.size() - 1; size4 >= 0; size4--) {
                        this.mActiveForegroundApps.remove(this.mPendingRemoveForegroundApps.get(size4));
                    }
                    removeMessages(2);
                    if (j < Long.MAX_VALUE) {
                        sendMessageAtTime(obtainMessage(2), (j + SystemClock.uptimeMillis()) - SystemClock.elapsedRealtime());
                    }
                    this.mActiveForegroundAppsChanged = false;
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public final void rescheduleDelayedStartsLocked() {
            String str;
            ActiveServices activeServices;
            String str2;
            removeMessages(1);
            long uptimeMillis = SystemClock.uptimeMillis();
            int size = this.mStartingBackground.size();
            int i = 0;
            while (true) {
                str = "ActivityManager";
                if (i >= size) {
                    break;
                }
                ServiceRecord serviceRecord = (ServiceRecord) this.mStartingBackground.get(i);
                if (serviceRecord.startingBgTimeout <= uptimeMillis) {
                    Slog.i("ActivityManager", "Waited long enough for: " + serviceRecord);
                    this.mStartingBackground.remove(i);
                    size += -1;
                    i--;
                }
                i++;
            }
            while (true) {
                int size2 = this.mDelayedStartList.size();
                activeServices = ActiveServices.this;
                if (size2 <= 0 || this.mStartingBackground.size() >= activeServices.mMaxStartingBackground) {
                    break;
                }
                ServiceRecord serviceRecord2 = (ServiceRecord) this.mDelayedStartList.remove(0);
                serviceRecord2.delayed = false;
                if (serviceRecord2.pendingStarts.size() <= 0) {
                    Slog.wtf(str, "**** NO PENDING STARTS! " + serviceRecord2 + " startReq=" + serviceRecord2.startRequested + " delayedStop=" + serviceRecord2.delayedStop);
                } else {
                    try {
                        ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord2.pendingStarts.get(0);
                        str2 = str;
                        try {
                            ActiveServices.this.startServiceInnerLocked(this, startItem.intent, serviceRecord2, false, true, startItem.callingId, startItem.mCallingProcessName, startItem.mCallingProcessState, serviceRecord2.startRequested, startItem.mCallingPackageName);
                        } catch (TransactionTooLargeException unused) {
                        }
                    } catch (TransactionTooLargeException unused2) {
                    }
                    str = str2;
                }
                str2 = str;
                str = str2;
            }
            if (this.mStartingBackground.size() > 0) {
                long j = ((ServiceRecord) this.mStartingBackground.get(0)).startingBgTimeout;
                if (j > uptimeMillis) {
                    uptimeMillis = j;
                }
                sendMessageAtTime(obtainMessage(1), uptimeMillis);
            }
            if (this.mStartingBackground.size() < activeServices.mMaxStartingBackground) {
                activeServices.mAm.mBroadcastQueue.getClass();
            }
        }

        public final void startDelayedServiceLocked() {
            removeMessages(101);
            if (!ActiveServices.this.mAm.isNowAppLaunching.get()) {
                while (this.mDelayServiceList.size() > 0) {
                    startOrBindServiceLocked((ServiceRecord) this.mDelayServiceList.remove(0));
                }
                return;
            }
            while (this.mDelayServiceList.size() > 0) {
                if (SystemClock.uptimeMillis() < ((ServiceRecord) this.mDelayServiceList.get(0)).delayTimeout) {
                    sendMessageDelayed(obtainMessage(101), 50L);
                    return;
                }
                startOrBindServiceLocked((ServiceRecord) this.mDelayServiceList.remove(0));
            }
        }

        public final void startOrBindServiceLocked(ServiceRecord serviceRecord) {
            try {
                serviceRecord.delayService = false;
                serviceRecord.delayServiceStop = true;
                if (serviceRecord.pendingBinds.size() > 0) {
                    ServiceRecord.BindItem bindItem = (ServiceRecord.BindItem) serviceRecord.pendingBinds.get(0);
                    if (ActiveServices.this.mAm.getRecordForAppLOSP(bindItem.caller) != null) {
                        ActiveServices.this.bindServiceLocked(bindItem.caller, bindItem.token, bindItem.service, bindItem.resolvedType, bindItem.connection, bindItem.flags, bindItem.instanceName, bindItem.isSdkSandboxService, bindItem.sdkSandboxClientAppUid, bindItem.sdkSandboxClientAppPackage, bindItem.sdkSandboxClientApplicationThread, bindItem.callingPackage, bindItem.userId);
                    }
                } else if (serviceRecord.pendingStarts.size() > 0) {
                    ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.pendingStarts.get(0);
                    ActiveServices.this.startServiceInnerLocked(this, startItem.intent, serviceRecord, false, true, startItem.callingId, startItem.mCallingProcessName, startItem.mCallingProcessState, serviceRecord.startRequested, startItem.mCallingPackageName);
                } else {
                    Slog.w("ActivityManager", "no pendingStarts or pendingBinds: " + serviceRecord.shortInstanceName);
                }
            } catch (TransactionTooLargeException unused) {
                Slog.w("ActivityManager", "start or bind delayed service fail");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceRestarter implements Runnable {
        public ServiceRecord mService;

        public ServiceRestarter() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActivityManagerService activityManagerService = ActiveServices.this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    ActiveServices.this.performServiceRestartLocked(this.mService);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemExemptedFgsTypePermission extends ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ActiveServices this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SystemExemptedFgsTypePermission(ActiveServices activeServices, int i) {
            super("System exempted");
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = activeServices;
                    super("Media projection screen capture permission");
                    break;
                default:
                    this.this$0 = activeServices;
                    break;
            }
        }

        public final int checkPermission(Context context, int i, int i2, String str, boolean z) {
            boolean z2;
            switch (this.$r8$classId) {
                case 0:
                    AppRestrictionController appRestrictionController = this.this$0.mAm.mAppRestrictionController;
                    int potentialSystemExemptionReason = appRestrictionController.getPotentialSystemExemptionReason(i);
                    if (potentialSystemExemptionReason == -1 && (potentialSystemExemptionReason = appRestrictionController.getPotentialSystemExemptionReason(i, str)) == -1) {
                        potentialSystemExemptionReason = appRestrictionController.getPotentialUserAllowedExemptionReason(i, str);
                    }
                    if (potentialSystemExemptionReason == -1 && ArrayUtils.contains(this.this$0.mAm.getPackageManagerInternal().getKnownPackageNames(2, 0), str)) {
                        potentialSystemExemptionReason = 326;
                    }
                    if (potentialSystemExemptionReason != 10 && potentialSystemExemptionReason != 11 && potentialSystemExemptionReason != 51 && potentialSystemExemptionReason != 63 && potentialSystemExemptionReason != 65 && potentialSystemExemptionReason != 300 && potentialSystemExemptionReason != 55 && potentialSystemExemptionReason != 56 && potentialSystemExemptionReason != 326 && potentialSystemExemptionReason != 327) {
                        switch (potentialSystemExemptionReason) {
                            case FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ROLE_EMERGENCY /* 319 */:
                            case 320:
                            case 321:
                            case 322:
                            case 323:
                            case FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN /* 324 */:
                                break;
                            default:
                                return -1;
                        }
                    }
                    return 0;
                default:
                    ActivityManagerService activityManagerService = this.this$0.mAm;
                    synchronized (activityManagerService.mMediaProjectionTokenMap) {
                        try {
                            int indexOfKey = activityManagerService.mMediaProjectionTokenMap.indexOfKey(i);
                            z2 = indexOfKey >= 0 && !((ArraySet) activityManagerService.mMediaProjectionTokenMap.valueAt(indexOfKey)).isEmpty();
                        } finally {
                        }
                    }
                    return z2 ? 0 : -1;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.server.am.ActiveServices$1] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.server.am.ActiveServices$1] */
    public ActiveServices(ActivityManagerService activityManagerService) {
        int i;
        final int i2 = 0;
        this.mLastAnrDumpClearer = new Runnable(this) { // from class: com.android.server.am.ActiveServices.1
            public final /* synthetic */ ActiveServices this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        ActivityManagerService activityManagerService2 = this.this$0.mAm;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService2) {
                            try {
                                this.this$0.mLastAnrDump = null;
                            } finally {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    default:
                        long uptimeMillis = SystemClock.uptimeMillis();
                        ActivityManagerService activityManagerService3 = this.this$0.mAm;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService3) {
                            try {
                                for (int size = this.this$0.mPendingFgsNotifications.size() - 1; size >= 0; size--) {
                                    ServiceRecord serviceRecord = (ServiceRecord) this.this$0.mPendingFgsNotifications.get(size);
                                    if (serviceRecord.fgDisplayTime <= uptimeMillis) {
                                        this.this$0.mPendingFgsNotifications.remove(size);
                                        if (serviceRecord.isForeground && serviceRecord.app != null) {
                                            serviceRecord.postNotification(true);
                                            serviceRecord.mFgsNotificationShown = true;
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                }
            }
        };
        final int i3 = 1;
        this.mPostDeferredFGSNotifications = new Runnable(this) { // from class: com.android.server.am.ActiveServices.1
            public final /* synthetic */ ActiveServices this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                    case 0:
                        ActivityManagerService activityManagerService2 = this.this$0.mAm;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService2) {
                            try {
                                this.this$0.mLastAnrDump = null;
                            } finally {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    default:
                        long uptimeMillis = SystemClock.uptimeMillis();
                        ActivityManagerService activityManagerService3 = this.this$0.mAm;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService3) {
                            try {
                                for (int size = this.this$0.mPendingFgsNotifications.size() - 1; size >= 0; size--) {
                                    ServiceRecord serviceRecord = (ServiceRecord) this.this$0.mPendingFgsNotifications.get(size);
                                    if (serviceRecord.fgDisplayTime <= uptimeMillis) {
                                        this.this$0.mPendingFgsNotifications.remove(size);
                                        if (serviceRecord.isForeground && serviceRecord.app != null) {
                                            serviceRecord.postNotification(true);
                                            serviceRecord.mFgsNotificationShown = true;
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                }
            }
        };
        this.mAm = activityManagerService;
        try {
            i = Integer.parseInt(SystemProperties.get("ro.config.max_starting_bg", "0"));
        } catch (RuntimeException unused) {
            i = 0;
        }
        this.mMaxStartingBackground = i <= 0 ? 25 : i;
        ServiceManager.getService("platform_compat");
        this.mFGSLogger = new ForegroundServiceTypeLoggerModule();
        AnrTimer.Args args = new AnrTimer.Args();
        Objects.requireNonNull(activityManagerService);
        this.mActiveServiceAnrTimer = new ProcessAnrTimer(activityManagerService.mHandler, 12, "SERVICE_TIMEOUT", args);
        this.mShortFGSAnrTimer = new ProcessAnrTimer(78, activityManagerService, "SHORT_FGS_TIMEOUT");
        this.mServiceFGAnrTimer = new ProcessAnrTimer(66, activityManagerService, "SERVICE_FOREGROUND_TIMEOUT");
    }

    public static String generateAdditionalSeInfoFromService(Intent intent) {
        return (intent == null || intent.getAction() == null) ? "" : (intent.getAction().equals("android.service.voice.HotwordDetectionService") || intent.getAction().equals("android.service.voice.VisualQueryDetectionService") || intent.getAction().equals("android.service.wearable.WearableSensingService") || intent.getAction().equals("android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService")) ? ":isolatedComputeApp" : "";
    }

    public static String getProcessNameForService(ServiceInfo serviceInfo, ComponentName componentName, String str, String str2, boolean z, boolean z2, boolean z3) {
        if (z) {
            return str2;
        }
        if ((serviceInfo.flags & 2) == 0 || (z3 && !serviceInfo.applicationInfo.processName.equals(serviceInfo.processName))) {
            return serviceInfo.processName;
        }
        if (z2) {
            return AnyMotionDetector$$ExternalSyntheticOutline0.m(str, ":ishared:", str2);
        }
        return serviceInfo.processName + ":" + componentName.getClassName();
    }

    public static boolean isServiceNeededLocked(ServiceRecord serviceRecord, boolean z, boolean z2) {
        if (serviceRecord.startRequested) {
            return true;
        }
        if (!z) {
            z2 = serviceRecord.hasAutoCreateConnections();
        }
        return z2;
    }

    public static ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked(ServiceRecord serviceRecord) {
        ActivityManager.RunningServiceInfo runningServiceInfo = new ActivityManager.RunningServiceInfo();
        runningServiceInfo.service = serviceRecord.name;
        ProcessRecord processRecord = serviceRecord.app;
        if (processRecord != null) {
            runningServiceInfo.pid = processRecord.mPid;
        }
        runningServiceInfo.uid = serviceRecord.appInfo.uid;
        runningServiceInfo.process = serviceRecord.processName;
        runningServiceInfo.foreground = serviceRecord.isForeground;
        runningServiceInfo.activeSince = serviceRecord.createRealTime;
        runningServiceInfo.started = serviceRecord.startRequested;
        runningServiceInfo.clientCount = serviceRecord.connections.size();
        runningServiceInfo.crashCount = serviceRecord.crashCount;
        runningServiceInfo.lastActivityTime = serviceRecord.lastActivity;
        if (serviceRecord.isForeground) {
            runningServiceInfo.flags |= 2;
        }
        if (serviceRecord.startRequested) {
            runningServiceInfo.flags |= 1;
        }
        ProcessRecord processRecord2 = serviceRecord.app;
        if (processRecord2 != null && processRecord2.mPid == ActivityManagerService.MY_PID) {
            runningServiceInfo.flags |= 4;
        }
        if (processRecord2 != null && processRecord2.mPersistent) {
            runningServiceInfo.flags |= 8;
        }
        ArrayMap arrayMap = serviceRecord.connections;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) arrayMap.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i);
                if (connectionRecord.clientLabel != 0) {
                    runningServiceInfo.clientPackage = connectionRecord.binding.client.info.packageName;
                    runningServiceInfo.clientLabel = connectionRecord.clientLabel;
                    return runningServiceInfo;
                }
            }
        }
        return runningServiceInfo;
    }

    public static void requestUpdateActiveForegroundAppsLocked(ServiceMap serviceMap, long j) {
        Message obtainMessage = serviceMap.obtainMessage(2);
        if (j != 0) {
            serviceMap.sendMessageAtTime(obtainMessage, (SystemClock.uptimeMillis() + j) - SystemClock.elapsedRealtime());
        } else {
            serviceMap.mActiveForegroundAppsChanged = true;
            serviceMap.sendMessage(obtainMessage);
        }
    }

    public static void resetFgsRestrictionLocked(ServiceRecord serviceRecord) {
        serviceRecord.mAllowWiu_noBinding = -1;
        serviceRecord.mAllowWiu_inBindService = -1;
        serviceRecord.mAllowWiu_byBindings = -1;
        serviceRecord.mAllowStart_noBinding = -1;
        serviceRecord.mAllowStart_inBindService = -1;
        serviceRecord.mAllowStart_byBindings = -1;
        serviceRecord.mInfoAllowStartForeground = null;
        serviceRecord.mInfoTempFgsAllowListReason = null;
        serviceRecord.mLoggedInfoAllowStartForeground = false;
        boolean z = serviceRecord.getFgsAllowWiu_forStart() != -1;
        if (serviceRecord.mAllowUiJobScheduling == z) {
            return;
        }
        serviceRecord.mAllowUiJobScheduling = z;
    }

    public static void traceInstant(String str, ServiceRecord serviceRecord) {
        if (Trace.isTagEnabled(64L)) {
            ComponentName componentName = serviceRecord.name;
            Trace.instant(64L, str + (componentName != null ? componentName.toShortString() : "(?)"));
        }
    }

    public static void updateAllowlistManagerLocked(ProcessServiceRecord processServiceRecord) {
        processServiceRecord.mAllowlistManager = false;
        for (int size = processServiceRecord.mServices.size() - 1; size >= 0; size--) {
            if (processServiceRecord.getRunningServiceAt(size).allowlistManager) {
                processServiceRecord.mAllowlistManager = true;
                return;
            }
        }
    }

    public final boolean appRestrictedAnyInBackground(int i, String str) {
        if (MARsPolicyManager.MARs_ENABLE) {
            MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
            mARsPolicyManager.getClass();
            if (MARsPolicyManager.isChinaPolicyEnabled() && mARsPolicyManager.isMARsTarget(UserHandle.getUserId(i), str)) {
                return false;
            }
        }
        if (this.mAppStateTracker == null) {
            this.mAppStateTracker = (AppStateTracker) LocalServices.getService(AppStateTracker.class);
        }
        AppStateTracker appStateTracker = this.mAppStateTracker;
        if (appStateTracker != null) {
            return appStateTracker.isAppBackgroundRestricted(i, str);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006d, code lost:
    
        if (r5 < r4.mFgsDeferralEligible.get(r1.appInfo.uid, 0)) goto L48;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.ActivityManagerInternal.ServiceNotificationPolicy applyForegroundServiceNotificationLocked(android.app.Notification r5, java.lang.String r6, int r7, java.lang.String r8, int r9) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.applyForegroundServiceNotificationLocked(android.app.Notification, java.lang.String, int, java.lang.String, int):android.app.ActivityManagerInternal$ServiceNotificationPolicy");
    }

    public final boolean attachApplicationLocked(ProcessRecord processRecord, String str) {
        boolean z;
        processRecord.mState.mBackgroundRestricted = appRestrictedAnyInBackground(processRecord.uid, processRecord.info.packageName);
        if (this.mPendingServices.size() > 0) {
            ServiceRecord serviceRecord = null;
            int i = 0;
            z = false;
            while (i < this.mPendingServices.size()) {
                try {
                    ServiceRecord serviceRecord2 = (ServiceRecord) this.mPendingServices.get(i);
                    try {
                        if (processRecord == serviceRecord2.isolationHostProc || (processRecord.uid == serviceRecord2.appInfo.uid && str.equals(serviceRecord2.processName))) {
                            IApplicationThread iApplicationThread = processRecord.mThread;
                            int i2 = processRecord.mPid;
                            this.mPendingServices.remove(i);
                            int i3 = i - 1;
                            ApplicationInfo applicationInfo = serviceRecord2.appInfo;
                            processRecord.addPackage(applicationInfo.packageName, applicationInfo.longVersionCode, this.mAm.mProcessStats);
                            try {
                                if (Trace.isTagEnabled(64L)) {
                                    Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord2.shortInstanceName);
                                }
                                realStartServiceLocked(serviceRecord2, processRecord, iApplicationThread, i2, serviceRecord2.createdFromFg, 0);
                                Trace.traceEnd(64L);
                                if (!isServiceNeededLocked(serviceRecord2, false, false)) {
                                    bringDownServiceLocked(serviceRecord2, true);
                                }
                                this.mAm.updateOomAdjPendingTargetsLocked(6);
                                z = true;
                                i = i3;
                            } finally {
                                Trace.traceEnd(64L);
                            }
                        }
                        i++;
                        serviceRecord = serviceRecord2;
                    } catch (RemoteException e) {
                        e = e;
                        serviceRecord = serviceRecord2;
                        Slog.w("ActivityManager", "Exception in new application when starting service " + serviceRecord.shortInstanceName, e);
                        throw e;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                }
            }
        } else {
            z = false;
        }
        if (this.mRestartingServices.size() > 0) {
            boolean z2 = false;
            for (int i4 = 0; i4 < this.mRestartingServices.size(); i4++) {
                ServiceRecord serviceRecord3 = (ServiceRecord) this.mRestartingServices.get(i4);
                if (processRecord == serviceRecord3.isolationHostProc || (processRecord.uid == serviceRecord3.appInfo.uid && str.equals(serviceRecord3.processName))) {
                    this.mAm.mHandler.removeCallbacks(serviceRecord3.restarter);
                    this.mAm.mHandler.post(serviceRecord3.restarter);
                    z2 = true;
                }
            }
            if (z2) {
                this.mAm.mHandler.post(new ActiveServices$$ExternalSyntheticLambda3(0, this));
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:172:0x04b0 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x054d A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0581 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x05ac A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:212:0x05b9 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x05de A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0600 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0610 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0624  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0637 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0661 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x066e A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0681 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x06d5 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0724 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x072b  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x073a A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0756 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0774 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:311:0x07a6 A[Catch: all -> 0x07be, TryCatch #6 {all -> 0x07be, blocks: (B:309:0x079f, B:311:0x07a6, B:313:0x07ac, B:317:0x07b5, B:320:0x07ba, B:321:0x07fb, B:323:0x0805, B:325:0x080b, B:326:0x0833, B:329:0x0862, B:338:0x083e, B:340:0x0846, B:343:0x0851, B:346:0x07c5, B:350:0x07b3, B:351:0x081c, B:353:0x0823), top: B:308:0x079f }] */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0805 A[Catch: all -> 0x07be, TryCatch #6 {all -> 0x07be, blocks: (B:309:0x079f, B:311:0x07a6, B:313:0x07ac, B:317:0x07b5, B:320:0x07ba, B:321:0x07fb, B:323:0x0805, B:325:0x080b, B:326:0x0833, B:329:0x0862, B:338:0x083e, B:340:0x0846, B:343:0x0851, B:346:0x07c5, B:350:0x07b3, B:351:0x081c, B:353:0x0823), top: B:308:0x079f }] */
    /* JADX WARN: Removed duplicated region for block: B:328:0x083d A[ADDED_TO_REGION, DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0879  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0880  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x087c  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x083e A[Catch: all -> 0x07be, TryCatch #6 {all -> 0x07be, blocks: (B:309:0x079f, B:311:0x07a6, B:313:0x07ac, B:317:0x07b5, B:320:0x07ba, B:321:0x07fb, B:323:0x0805, B:325:0x080b, B:326:0x0833, B:329:0x0862, B:338:0x083e, B:340:0x0846, B:343:0x0851, B:346:0x07c5, B:350:0x07b3, B:351:0x081c, B:353:0x0823), top: B:308:0x079f }] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0823 A[Catch: all -> 0x07be, TryCatch #6 {all -> 0x07be, blocks: (B:309:0x079f, B:311:0x07a6, B:313:0x07ac, B:317:0x07b5, B:320:0x07ba, B:321:0x07fb, B:323:0x0805, B:325:0x080b, B:326:0x0833, B:329:0x0862, B:338:0x083e, B:340:0x0846, B:343:0x0851, B:346:0x07c5, B:350:0x07b3, B:351:0x081c, B:353:0x0823), top: B:308:0x079f }] */
    /* JADX WARN: Removed duplicated region for block: B:360:0x072e  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0678  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x066a  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x0640  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0627 A[Catch: all -> 0x04a2, TryCatch #0 {all -> 0x04a2, blocks: (B:158:0x045c, B:160:0x0471, B:162:0x047d, B:163:0x0483, B:170:0x04a7, B:172:0x04b0, B:174:0x04c1, B:175:0x04d0, B:177:0x054d, B:178:0x054f, B:193:0x056b, B:194:0x056c, B:196:0x0575, B:198:0x057b, B:200:0x0581, B:201:0x0588, B:202:0x058d, B:204:0x0596, B:206:0x059e, B:207:0x05a4, B:209:0x05ac, B:210:0x05b1, B:212:0x05b9, B:215:0x05c0, B:217:0x05ca, B:218:0x05d0, B:219:0x05d5, B:221:0x05de, B:222:0x05e1, B:224:0x05e6, B:226:0x05ea, B:228:0x05ee, B:230:0x05f6, B:231:0x05fc, B:233:0x0600, B:234:0x0606, B:236:0x0610, B:237:0x061a, B:241:0x0637, B:244:0x0646, B:246:0x064a, B:248:0x0650, B:250:0x0661, B:252:0x066e, B:253:0x067b, B:255:0x0681, B:258:0x068e, B:260:0x06a4, B:265:0x06ce, B:267:0x06d5, B:269:0x06df, B:270:0x06e4, B:272:0x06e8, B:273:0x06ea, B:275:0x06f4, B:278:0x0707, B:280:0x0712, B:282:0x0717, B:284:0x0724, B:287:0x0730, B:289:0x073a, B:291:0x0743, B:293:0x074b, B:294:0x0752, B:296:0x0756, B:303:0x0768, B:305:0x0774, B:306:0x077a, B:362:0x06f8, B:364:0x06fe, B:375:0x0627, B:383:0x04a1, B:180:0x0550, B:182:0x0554, B:184:0x0558, B:186:0x055c, B:187:0x0563, B:188:0x0568, B:165:0x0484, B:167:0x048a, B:168:0x049e), top: B:157:0x045c, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:376:0x05b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int bindServiceLocked(android.app.IApplicationThread r49, android.os.IBinder r50, android.content.Intent r51, java.lang.String r52, android.app.IServiceConnection r53, long r54, java.lang.String r56, boolean r57, int r58, java.lang.String r59, android.app.IApplicationThread r60, java.lang.String r61, int r62) {
        /*
            Method dump skipped, instructions count: 2246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.bindServiceLocked(android.app.IApplicationThread, android.os.IBinder, android.content.Intent, java.lang.String, android.app.IServiceConnection, long, java.lang.String, boolean, int, java.lang.String, android.app.IApplicationThread, java.lang.String, int):int");
    }

    public final boolean bringDownDisabledPackageServicesLocked(int i, String str, Set set, boolean z, boolean z2, boolean z3) {
        ArrayList arrayList = this.mTmpCollectionResults;
        if (arrayList != null) {
            arrayList.clear();
        }
        if (i == -1) {
            for (int size = this.mServiceMap.size() - 1; size >= 0; size--) {
                r2 |= collectPackageServicesLocked(str, set, z, z3, ((ServiceMap) this.mServiceMap.valueAt(size)).mServicesByInstanceName);
                if (!z3 && r2) {
                    return true;
                }
                if (z3 && set == null) {
                    forceStopPackageLocked(((ServiceMap) this.mServiceMap.valueAt(size)).mUserId, str);
                }
            }
        } else {
            ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
            r2 = serviceMap != null ? collectPackageServicesLocked(str, set, z, z3, serviceMap.mServicesByInstanceName) : false;
            if (z3 && set == null) {
                forceStopPackageLocked(i, str);
            }
        }
        ArrayList arrayList2 = this.mTmpCollectionResults;
        if (arrayList2 != null) {
            int size2 = arrayList2.size();
            for (int i2 = size2 - 1; i2 >= 0; i2--) {
                bringDownServiceLocked((ServiceRecord) this.mTmpCollectionResults.get(i2), true);
            }
            ActivityManagerService activityManagerService = this.mAm;
            if (size2 > 0) {
                activityManagerService.updateOomAdjPendingTargetsLocked(22);
            }
            if (z2 && !this.mTmpCollectionResults.isEmpty()) {
                activityManagerService.mHandler.postDelayed(new ActiveServices$$ExternalSyntheticLambda3(2, (ArrayList) this.mTmpCollectionResults.clone()), 250L);
            }
            this.mTmpCollectionResults.clear();
        }
        return r2;
    }

    public final void bringDownServiceIfNeededLocked(ServiceRecord serviceRecord, boolean z, boolean z2, boolean z3) {
        if (isServiceNeededLocked(serviceRecord, z, z2) || this.mPendingServices.contains(serviceRecord)) {
            return;
        }
        bringDownServiceLocked(serviceRecord, z3);
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0398 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01ad A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0209  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bringDownServiceLocked(com.android.server.am.ServiceRecord r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 970
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.bringDownServiceLocked(com.android.server.am.ServiceRecord, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02ad A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x03bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String bringUpServiceInnerLocked(com.android.server.am.ServiceRecord r47, int r48, boolean r49, boolean r50, boolean r51, boolean r52, int r53) {
        /*
            Method dump skipped, instructions count: 971
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.bringUpServiceInnerLocked(com.android.server.am.ServiceRecord, int, boolean, boolean, boolean, boolean, int):java.lang.String");
    }

    public final String bringUpServiceLocked(ServiceRecord serviceRecord, int i, boolean z, boolean z2, boolean z3, boolean z4, int i2) {
        try {
            if (Trace.isTagEnabled(64L)) {
                Trace.traceBegin(64L, "bringUpServiceLocked: " + serviceRecord.shortInstanceName);
            }
            String bringUpServiceInnerLocked = bringUpServiceInnerLocked(serviceRecord, i, z, z2, z3, z4, i2);
            Trace.traceEnd(64L);
            return bringUpServiceInnerLocked;
        } catch (Throwable th) {
            Trace.traceEnd(64L);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bumpServiceExecutingLocked(com.android.server.am.ServiceRecord r11, boolean r12, java.lang.String r13, int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.bumpServiceExecutingLocked(com.android.server.am.ServiceRecord, boolean, java.lang.String, int, boolean):void");
    }

    public final String canBindingClientStartFgsLocked(int i) {
        Pair pair = (Pair) this.mAm.mProcessList.searchEachLruProcessesLOSP(new ActiveServices$$ExternalSyntheticLambda2(this, i, new ArraySet(), 0), false);
        if (pair != null) {
            return (String) pair.second;
        }
        return null;
    }

    public final boolean canStartForegroundServiceLocked(int i, int i2, String str) {
        if (!this.mAm.mConstants.mFlagBackgroundFgsStartRestrictionEnabled) {
            return true;
        }
        BackgroundStartPrivileges backgroundStartPrivileges = BackgroundStartPrivileges.NONE;
        int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, null, backgroundStartPrivileges), i, i2, str, null, backgroundStartPrivileges);
        if (shouldAllowFgsStartForegroundNoBindingCheckLocked == -1 && canBindingClientStartFgsLocked(i2) != null) {
            shouldAllowFgsStartForegroundNoBindingCheckLocked = 54;
        }
        return shouldAllowFgsStartForegroundNoBindingCheckLocked != -1;
    }

    public final void cancelForegroundNotificationLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.foregroundNoti != null) {
            ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
            for (int size = serviceMapLocked.mServicesByInstanceName.size() - 1; size >= 0; size--) {
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.valueAt(size);
                if (serviceRecord2 != serviceRecord && serviceRecord2.isForeground && serviceRecord2.foregroundId == serviceRecord.foregroundId && serviceRecord2.packageName.equals(serviceRecord.packageName)) {
                    return;
                }
            }
            serviceRecord.cancelNotification();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void cleanUpServices(int i, ComponentName componentName, Intent intent) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        ArrayMap arrayMap = getServiceMapLocked(i).mServicesByInstanceName;
        boolean z2 = 1;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(size);
            if (serviceRecord.packageName.equals(componentName.getPackageName())) {
                arrayList.add(serviceRecord);
            }
        }
        int size2 = arrayList.size() - 1;
        boolean z3 = false;
        while (size2 >= 0) {
            ServiceRecord serviceRecord2 = (ServiceRecord) arrayList.get(size2);
            if (!serviceRecord2.startRequested) {
                z = z2;
            } else if ((serviceRecord2.serviceInfo.flags & z2) != 0) {
                BootReceiver$$ExternalSyntheticOutline0.m59m(new StringBuilder("Stopping service "), serviceRecord2.shortInstanceName, ": remove task", "ActivityManager");
                stopServiceLocked(serviceRecord2, z2);
                z = z2;
                z3 = z;
            } else {
                serviceRecord2.pendingStarts.add(new ServiceRecord.StartItem(serviceRecord2, true, serviceRecord2.lastStartId, intent, null, 0, null, null, -1));
                ProcessRecord processRecord = serviceRecord2.app;
                if (processRecord == null || !processRecord.isThreadReady()) {
                    z = true;
                } else {
                    z = true;
                    try {
                        sendServiceArgsLocked(serviceRecord2, true, false);
                    } catch (TransactionTooLargeException unused) {
                    }
                    size2--;
                    z2 = z;
                }
            }
            size2--;
            z2 = z;
        }
        if (z3) {
            this.mAm.updateOomAdjPendingTargetsLocked(17);
        }
    }

    public final void clearRestartingIfNeededLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.restartTracker != null) {
            for (int size = this.mRestartingServices.size() - 1; size >= 0; size--) {
                if (((ServiceRecord) this.mRestartingServices.get(size)).restartTracker == serviceRecord.restartTracker) {
                    return;
                }
            }
            synchronized (this.mAm.mProcessStats.mLock) {
                serviceRecord.restartTracker.setRestarting(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
            }
            serviceRecord.restartTracker = null;
        }
    }

    public final boolean collectPackageServicesLocked(String str, Set set, boolean z, boolean z2, ArrayMap arrayMap) {
        boolean z3 = false;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(size);
            if (str != null) {
                if (!serviceRecord.packageName.equals(str)) {
                    continue;
                } else if (set != null) {
                    if (!((ArraySet) set).contains(serviceRecord.name.getClassName())) {
                        continue;
                    }
                }
            }
            ProcessRecord processRecord = serviceRecord.app;
            if (processRecord == null || z || !processRecord.mPersistent) {
                if (!z2) {
                    return true;
                }
                Slog.i("ActivityManager", "  Force stopping service " + serviceRecord);
                ProcessRecord processRecord2 = serviceRecord.app;
                if (processRecord2 != null && !processRecord2.mPersistent) {
                    stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
                }
                if (serviceRecord.app != null) {
                    this.mActiveServiceAnrTimer.discard();
                }
                serviceRecord.setProcess(null);
                serviceRecord.isolationHostProc = null;
                if (this.mTmpCollectionResults == null) {
                    this.mTmpCollectionResults = new ArrayList();
                }
                this.mTmpCollectionResults.add(serviceRecord);
                z3 = true;
            }
        }
        return z3;
    }

    public final void decActiveForegroundAppLocked(ServiceMap serviceMap, ServiceRecord serviceRecord) {
        ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.get(serviceRecord.packageName);
        if (activeForegroundApp != null) {
            int i = activeForegroundApp.mNumActive - 1;
            activeForegroundApp.mNumActive = i;
            if (i <= 0) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                activeForegroundApp.mEndTime = elapsedRealtime;
                if (foregroundAppShownEnoughLocked(activeForegroundApp, elapsedRealtime)) {
                    serviceMap.mActiveForegroundApps.remove(serviceRecord.packageName);
                    serviceMap.mActiveForegroundAppsChanged = true;
                    requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
                } else {
                    long j = activeForegroundApp.mHideTime;
                    if (j < Long.MAX_VALUE) {
                        requestUpdateActiveForegroundAppsLocked(serviceMap, j);
                    }
                }
            }
        }
    }

    public final boolean deferServiceBringupIfFrozenLocked(final ServiceRecord serviceRecord, final Intent intent, final String str, final String str2, final int i, final int i2, final String str3, final int i3, final boolean z, final boolean z2, final int i4, final BackgroundStartPrivileges backgroundStartPrivileges, final boolean z3, final IServiceConnection iServiceConnection) {
        if (!this.mAm.getPackageManagerInternal().isPackageFrozen(i, serviceRecord.userId, serviceRecord.packageName)) {
            return false;
        }
        ArrayList arrayList = (ArrayList) this.mPendingBringups.get(serviceRecord);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mPendingBringups.put(serviceRecord, arrayList);
        }
        arrayList.add(new Runnable() { // from class: com.android.server.am.ActiveServices.4
            @Override // java.lang.Runnable
            public final void run() {
                ActivityManagerService activityManagerService;
                ActivityManagerService activityManagerService2 = ActiveServices.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        if (!ActiveServices.this.mPendingBringups.containsKey(serviceRecord)) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (!ActiveServices.this.requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord, str, str2, i, intent, z2, i4, z3, iServiceConnection)) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (z3) {
                            try {
                                ActiveServices.this.bringUpServiceLocked(serviceRecord, intent.getFlags(), z2, false, false, false, 0);
                                activityManagerService = ActiveServices.this.mAm;
                            } catch (TransactionTooLargeException unused) {
                                activityManagerService = ActiveServices.this.mAm;
                            } catch (Throwable th) {
                                ActiveServices.this.mAm.updateOomAdjPendingTargetsLocked(6);
                                throw th;
                            }
                            activityManagerService.updateOomAdjPendingTargetsLocked(6);
                        } else {
                            try {
                                ActiveServices.this.startServiceInnerLocked(serviceRecord, intent, i, i2, str3, i3, z, z2, backgroundStartPrivileges, str);
                            } catch (TransactionTooLargeException unused2) {
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th2) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
            }
        });
        return true;
    }

    public final void dropFgsNotificationStateLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.foregroundNoti == null) {
            return;
        }
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(serviceRecord.userId);
        if (serviceMap != null) {
            int size = serviceMap.mServicesByInstanceName.size();
            for (int i = 0; i < size; i++) {
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i);
                if (serviceRecord2 != serviceRecord && serviceRecord2.isForeground && serviceRecord.foregroundId == serviceRecord2.foregroundId && serviceRecord.appInfo.packageName.equals(serviceRecord2.appInfo.packageName)) {
                    return;
                }
            }
        } else {
            Slog.wtf("ActivityManager", "FGS " + serviceRecord + " not found!");
        }
        final int i2 = serviceRecord.foregroundId;
        final int i3 = serviceRecord.userId;
        final String str = serviceRecord.packageName;
        serviceRecord.ams.mHandler.post(new Runnable() { // from class: com.android.server.am.ServiceRecord.4
            public final /* synthetic */ int val$localForegroundId;
            public final /* synthetic */ String val$localPackageName;
            public final /* synthetic */ int val$localUserId;

            public AnonymousClass4(final int i22, final int i32, final String str2) {
                r3 = str2;
                r1 = i22;
                r2 = i32;
            }

            @Override // java.lang.Runnable
            public final void run() {
                NotificationManagerInternal notificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                NotificationManagerService.AnonymousClass17 anonymousClass17 = (NotificationManagerService.AnonymousClass17) notificationManagerInternal;
                anonymousClass17.removeForegroundServiceFlagFromNotification(r1, r2, r3);
            }
        });
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long start = protoOutputStream.start(1146756268033L);
                for (int i : this.mAm.mUserController.getUsers()) {
                    ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
                    if (serviceMap != null) {
                        long start2 = protoOutputStream.start(2246267895809L);
                        protoOutputStream.write(1120986464257L, i);
                        ArrayMap arrayMap = serviceMap.mServicesByInstanceName;
                        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                            ((ServiceRecord) arrayMap.valueAt(i2)).dumpDebug(protoOutputStream);
                        }
                        protoOutputStream.end(start2);
                    }
                }
                protoOutputStream.end(start);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void dumpService(FileDescriptor fileDescriptor, PrintWriter printWriter, ServiceRecord serviceRecord, String[] strArr, boolean z) {
        IApplicationThread iApplicationThread;
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                printWriter.print("");
                printWriter.print("SERVICE ");
                printWriter.print(serviceRecord.shortInstanceName);
                printWriter.print(" ");
                printWriter.print(Integer.toHexString(System.identityHashCode(serviceRecord)));
                printWriter.print(" pid=");
                ProcessRecord processRecord = serviceRecord.app;
                if (processRecord != null) {
                    printWriter.print(processRecord.mPid);
                    printWriter.print(" user=");
                    printWriter.println(serviceRecord.userId);
                } else {
                    printWriter.println("(not running)");
                }
                if (z) {
                    serviceRecord.dump(printWriter, "  ");
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        ProcessRecord processRecord2 = serviceRecord.app;
        if (processRecord2 == null || (iApplicationThread = processRecord2.mThread) == null) {
            return;
        }
        printWriter.print("");
        printWriter.println("  Client:");
        printWriter.flush();
        try {
            TransferPipe transferPipe = new TransferPipe();
            try {
                iApplicationThread.dumpService(transferPipe.getWriteFd(), serviceRecord, strArr);
                transferPipe.setBufferPrefix("    ");
                transferPipe.go(fileDescriptor);
                transferPipe.kill();
            } catch (Throwable th2) {
                transferPipe.kill();
                throw th2;
            }
        } catch (RemoteException unused) {
            printWriter.println("    Got a RemoteException while dumping the service");
        } catch (IOException e) {
            printWriter.println("    Failure while dumping the service: " + e);
        }
    }

    public final boolean dumpService(FileDescriptor fileDescriptor, final PrintWriter printWriter, String str, int[] iArr, String[] strArr, boolean z) {
        int[] users;
        try {
            boolean z2 = false;
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(false);
            ArrayList arrayList = new ArrayList();
            Predicate filterRecord = DumpUtils.filterRecord(str);
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                if (iArr == null) {
                    try {
                        users = this.mAm.mUserController.getUsers();
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                } else {
                    users = iArr;
                }
                for (int i : users) {
                    ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
                    if (serviceMap != null) {
                        ArrayMap arrayMap = serviceMap.mServicesByInstanceName;
                        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                            ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(i2);
                            if (filterRecord.test(serviceRecord)) {
                                arrayList.add(serviceRecord);
                            }
                        }
                    }
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            if (arrayList.size() <= 0) {
                this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
                return false;
            }
            arrayList.sort(Comparator.comparing(new ActiveServices$$ExternalSyntheticLambda10()));
            int i3 = 0;
            while (i3 < arrayList.size()) {
                if (z2) {
                    printWriter.println();
                }
                if (((ServiceRecord) arrayList.get(i3)).shortInstanceName.contains("SystemUIService")) {
                    printWriter.print("  SystemUI Revival Count:");
                    printWriter.println(((ArrayList) this.mRevivalServicesMessages).size());
                    if (((ArrayList) this.mRevivalServicesMessages).size() > 0) {
                        ((ArrayList) this.mRevivalServicesMessages).forEach(new Consumer() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda11
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(printWriter, "    ", (String) obj);
                            }
                        });
                    }
                }
                dumpService(fileDescriptor, printWriter, (ServiceRecord) arrayList.get(i3), strArr, z);
                i3++;
                z2 = true;
            }
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
            return true;
        } catch (Throwable th2) {
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
            throw th2;
        }
    }

    public final void forceStopPackageLocked(int i, String str) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        if (serviceMap != null && serviceMap.mActiveForegroundApps.size() > 0) {
            for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                if (((ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size)).mPackageName.equals(str)) {
                    serviceMap.mActiveForegroundApps.removeAt(size);
                    serviceMap.mActiveForegroundAppsChanged = true;
                }
            }
            if (serviceMap.mActiveForegroundAppsChanged) {
                requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
            }
        }
        for (int size2 = this.mPendingBringups.size() - 1; size2 >= 0; size2--) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingBringups.keyAt(size2);
            if (TextUtils.equals(serviceRecord.packageName, str) && serviceRecord.userId == i) {
                this.mPendingBringups.removeAt(size2);
            }
        }
        this.mRestartBackoffDisabledPackages.remove(str);
        for (int size3 = this.mPendingFgsNotifications.size() - 1; size3 >= 0; size3--) {
            ServiceRecord serviceRecord2 = (ServiceRecord) this.mPendingFgsNotifications.get(size3);
            if (i == serviceRecord2.userId && serviceRecord2.appInfo.packageName.equals(str)) {
                this.mPendingFgsNotifications.remove(size3);
            }
        }
    }

    public final boolean foregroundAppShownEnoughLocked(ActiveForegroundApp activeForegroundApp, long j) {
        activeForegroundApp.mHideTime = Long.MAX_VALUE;
        if (activeForegroundApp.mShownWhileTop) {
            return true;
        }
        boolean z = this.mScreenOn;
        ActivityManagerService activityManagerService = this.mAm;
        if (z || activeForegroundApp.mShownWhileScreenOn) {
            long j2 = activeForegroundApp.mStartVisibleTime;
            long j3 = j2 + (activeForegroundApp.mStartTime != j2 ? activityManagerService.mConstants.FGSERVICE_SCREEN_ON_AFTER_TIME : activityManagerService.mConstants.FGSERVICE_MIN_SHOWN_TIME);
            if (j >= j3) {
                return true;
            }
            long j4 = j + activityManagerService.mConstants.FGSERVICE_MIN_REPORT_TIME;
            if (j4 > j3) {
                j3 = j4;
            }
            activeForegroundApp.mHideTime = j3;
        } else {
            long j5 = activeForegroundApp.mEndTime + activityManagerService.mConstants.FGSERVICE_SCREEN_ON_BEFORE_TIME;
            if (j >= j5) {
                return true;
            }
            activeForegroundApp.mHideTime = j5;
        }
        return false;
    }

    public final ArraySet getClientPackagesLocked(String str) {
        ArraySet arraySet = new ArraySet();
        for (int i : this.mAm.mUserController.getUsers()) {
            ArrayMap arrayMap = getServiceMapLocked(i).mServicesByInstanceName;
            int size = arrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(i2);
                if (serviceRecord.name.getPackageName().equals(str)) {
                    ArrayMap arrayMap2 = serviceRecord.connections;
                    for (int size2 = arrayMap2.size() - 1; size2 >= 0; size2--) {
                        ArrayList arrayList = (ArrayList) arrayMap2.valueAt(size2);
                        int size3 = arrayList.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            ProcessRecord processRecord = ((ConnectionRecord) arrayList.get(i3)).binding.client;
                            if (processRecord != null) {
                                arraySet.add(processRecord.info.packageName);
                            }
                        }
                    }
                }
            }
        }
        return arraySet;
    }

    public final long getExtraRestartTimeInBetweenLocked() {
        ActivityManagerService activityManagerService = this.mAm;
        if (!activityManagerService.mConstants.mEnableExtraServiceRestartDelayOnMemPressure) {
            return 0L;
        }
        return activityManagerService.mConstants.mExtraServiceRestartDelayOnMemPressure[activityManagerService.mAppProfiler.getLastMemoryLevelLocked()];
    }

    public final ServiceRecord.TimeLimitedFgsInfo getFgsTimeLimitedInfo(int i, int i2) {
        SparseArray sparseArray = (SparseArray) this.mTimeLimitedFgsInfo.get(i);
        if (sparseArray != null) {
            return (ServiceRecord.TimeLimitedFgsInfo) sparseArray.get(i2);
        }
        return null;
    }

    public final int getForegroundServiceTypeLocked(ComponentName componentName, IBinder iBinder) {
        int callingUserId = UserHandle.getCallingUserId();
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord serviceRecord = (ServiceRecord) getServiceMapLocked(callingUserId).mServicesByInstanceName.get(componentName);
            if (serviceRecord != iBinder) {
                serviceRecord = null;
            }
            return serviceRecord != null ? serviceRecord.foregroundServiceType : 0;
        } finally {
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final PendingIntent getRunningServiceControlPanelLocked(ComponentName componentName) {
        this.mAm.mInjector.getClass();
        ServiceRecord serviceRecord = (ServiceRecord) getServiceMapLocked(UserHandle.getUserId(Binder.getCallingUid())).mServicesByInstanceName.get(componentName);
        if (serviceRecord == null) {
            return null;
        }
        ArrayMap arrayMap = serviceRecord.connections;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) arrayMap.valueAt(size);
            for (int i = 0; i < arrayList.size(); i++) {
                if (((ConnectionRecord) arrayList.get(i)).clientIntent != null) {
                    return ((ConnectionRecord) arrayList.get(i)).clientIntent;
                }
            }
        }
        return null;
    }

    public final List getRunningServiceInfoLocked(int i, int i2, boolean z, boolean z2) {
        ProcessRecord processRecord;
        ProcessRecord processRecord2;
        ArrayList arrayList = new ArrayList();
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i3 = 0;
        try {
            if (z2) {
                int[] users = activityManagerService.mUserController.getUsers();
                for (int i4 = 0; i4 < users.length && arrayList.size() < i; i4++) {
                    ArrayMap arrayMap = getServiceMapLocked(users[i4]).mServicesByInstanceName;
                    for (int i5 = 0; i5 < arrayMap.size() && arrayList.size() < i; i5++) {
                        arrayList.add(makeRunningServiceInfoLocked((ServiceRecord) arrayMap.valueAt(i5)));
                    }
                }
                while (i3 < this.mRestartingServices.size() && arrayList.size() < i) {
                    ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i3);
                    ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked = makeRunningServiceInfoLocked(serviceRecord);
                    makeRunningServiceInfoLocked.restarting = serviceRecord.nextRestartTime;
                    arrayList.add(makeRunningServiceInfoLocked);
                    i3++;
                }
            } else {
                int userId = UserHandle.getUserId(i2);
                ArrayMap arrayMap2 = getServiceMapLocked(userId).mServicesByInstanceName;
                for (int i6 = 0; i6 < arrayMap2.size() && arrayList.size() < i; i6++) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) arrayMap2.valueAt(i6);
                    if (z || ((processRecord2 = serviceRecord2.app) != null && processRecord2.uid == i2)) {
                        arrayList.add(makeRunningServiceInfoLocked(serviceRecord2));
                    }
                }
                while (i3 < this.mRestartingServices.size() && arrayList.size() < i) {
                    ServiceRecord serviceRecord3 = (ServiceRecord) this.mRestartingServices.get(i3);
                    if (serviceRecord3.userId == userId && (z || ((processRecord = serviceRecord3.app) != null && processRecord.uid == i2))) {
                        ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked2 = makeRunningServiceInfoLocked(serviceRecord3);
                        makeRunningServiceInfoLocked2.restarting = serviceRecord3.nextRestartTime;
                        arrayList.add(makeRunningServiceInfoLocked2);
                    }
                    i3++;
                }
            }
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return arrayList;
        } catch (Throwable th) {
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0061, code lost:
    
        if ((!((r20.flags & 4294971392L) != 0)) != false) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getServiceBindingOomAdjPolicyForAddLocked(com.android.server.am.ProcessRecord r18, com.android.server.am.ProcessRecord r19, com.android.server.am.ConnectionRecord r20) {
        /*
            r17 = this;
            r3 = r18
            r2 = r19
            com.android.server.am.Flags.serviceBindingOomAdjPolicy()
            r0 = 0
            if (r3 == 0) goto L95
            com.android.server.am.ProcessStateRecord r1 = r3.mState
            r14 = 7
            if (r3 != r2) goto L11
        Lf:
            r15 = r14
            goto L22
        L11:
            boolean r4 = r1.isCached()
            if (r4 == 0) goto L21
            boolean r4 = r18.isFreezable()
            if (r4 == 0) goto Lf
            r4 = 15
            r15 = r4
            goto L22
        L21:
            r15 = r0
        L22:
            r4 = r15 & 4
            if (r4 != 0) goto L94
            r4 = r17
            com.android.server.am.ActivityManagerService r4 = r4.mAm
            com.android.server.am.OomAdjuster r4 = r4.mOomAdjuster
            r4.getClass()
            boolean r5 = com.android.server.am.OomAdjuster.evaluateConnectionPrelude(r18, r19)
            r6 = 1
            if (r5 == 0) goto L38
            r0 = r6
            goto L90
        L38:
            com.android.server.am.ProcessStateRecord r5 = r2.mState
            int r7 = r5.mSetAdj
            int r8 = r1.mSetAdj
            if (r7 > r8) goto L64
            int r7 = r5.mSetProcState
            int r8 = r1.mSetProcState
            if (r7 > r8) goto L64
            int r5 = r5.mSetCapability
            int r1 = r1.mSetCapability
            r5 = r5 & r1
            if (r5 == r1) goto L90
            r1 = r20
            long r7 = r1.flags
            r9 = 4294971392(0x100001000, double:2.1219978147E-314)
            long r7 = r7 & r9
            r9 = 0
            int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r5 == 0) goto L5f
            r5 = r6
            goto L60
        L5f:
            r5 = r0
        L60:
            r5 = r5 ^ r6
            if (r5 == 0) goto L66
            goto L90
        L64:
            r1 = r20
        L66:
            com.android.server.am.OomAdjuster$Injector r0 = r4.mInjector
            r0.getClass()
            long r5 = android.os.SystemClock.uptimeMillis()
            com.android.server.am.ActivityManagerService r0 = r4.mService
            com.android.server.am.ProcessRecord r7 = r0.getTopApp()
            r12 = 0
            r13 = 1
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r16 = 900(0x384, float:1.261E-42)
            r0 = r4
            r1 = r20
            r2 = r19
            r3 = r18
            r4 = r5
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r16
            boolean r0 = r0.computeServiceHostOomAdjLSP(r1, r2, r3, r4, r6, r7, r8, r9, r10, r11, r12, r13)
        L90:
            if (r0 != 0) goto L94
            r0 = r14
            goto L95
        L94:
            r0 = r15
        L95:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.getServiceBindingOomAdjPolicyForAddLocked(com.android.server.am.ProcessRecord, com.android.server.am.ProcessRecord, com.android.server.am.ConnectionRecord):int");
    }

    public final ServiceMap getServiceMapLocked(int i) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        if (serviceMap != null) {
            return serviceMap;
        }
        ServiceMap serviceMap2 = new ServiceMap(this.mAm.mHandler.getLooper(), i);
        this.mServiceMap.put(i, serviceMap2);
        return serviceMap2;
    }

    public final String getShortProcessNameForStats(int i, String str) {
        String[] packagesForUid = this.mAm.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length == 1) {
            if (TextUtils.equals(packagesForUid[0], str)) {
                return null;
            }
            if (str != null && str.startsWith(packagesForUid[0])) {
                return str.substring(packagesForUid[0].length());
            }
        }
        return str;
    }

    public final int getTimeLimitedFgsType(int i) {
        long j;
        int i2 = 8192;
        if ((i & 8192) == 8192) {
            j = this.mAm.mConstants.mMediaProcessingFgsTimeoutDuration;
        } else {
            i2 = 0;
            j = 0;
        }
        if ((i & 1) != 1) {
            return i2;
        }
        if (j != 0 && this.mAm.mConstants.mDataSyncFgsTimeoutDuration <= j) {
            return i2;
        }
        long j2 = this.mAm.mConstants.mDataSyncFgsTimeoutDuration;
        return 1;
    }

    public final boolean hasForegroundServiceNotificationLocked(int i, String str, String str2) {
        Notification notification;
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i);
        if (serviceMap != null) {
            for (int i2 = 0; i2 < serviceMap.mServicesByInstanceName.size(); i2++) {
                ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i2);
                if (serviceRecord.appInfo.packageName.equals(str) && serviceRecord.isForeground && (notification = serviceRecord.foregroundNoti) != null && Objects.equals(notification.getChannelId(), str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean hasServiceTimedOutLocked(ComponentName componentName, IBinder iBinder) {
        int callingUserId = UserHandle.getCallingUserId();
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord serviceRecord = (ServiceRecord) getServiceMapLocked(callingUserId).mServicesByInstanceName.get(componentName);
            if (serviceRecord != iBinder) {
                serviceRecord = null;
            }
            if (serviceRecord == null) {
                return false;
            }
            return getTimeLimitedFgsType(serviceRecord.foregroundServiceType) != 0;
        } finally {
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isBgFgsRestrictionEnabled(int i, ServiceRecord serviceRecord) {
        if (!this.mAm.mConstants.mFlagFgsStartRestrictionEnabled || !CompatChanges.isChangeEnabled(170668199L, serviceRecord.appInfo.uid)) {
            return false;
        }
        if (!this.mAm.mConstants.mFgsStartRestrictionCheckCallerTargetSdk) {
            return true;
        }
        Flags.newFgsRestrictionLogic();
        return i == 1000 || CompatChanges.isChangeEnabled(170668199L, i);
    }

    public final boolean isDeviceProvisioningPackage(String str) {
        if (this.mCachedDeviceProvisioningPackage == null) {
            this.mCachedDeviceProvisioningPackage = this.mAm.mContext.getResources().getString(R.string.display_manager_built_in_display_name);
        }
        String str2 = this.mCachedDeviceProvisioningPackage;
        return str2 != null && str2.equals(str);
    }

    public final boolean isForegroundServiceAllowedInBackgroundRestricted(int i, String str) {
        ProcessRecord processRecord;
        UidRecord uidRecord = this.mAm.mProcessList.mActiveUids.get(i);
        if (uidRecord != null) {
            int size = uidRecord.mProcRecords.size() - 1;
            while (true) {
                if (size >= 0) {
                    processRecord = (ProcessRecord) uidRecord.mProcRecords.valueAt(size);
                    if (processRecord != null && TextUtils.equals(processRecord.info.packageName, str)) {
                        break;
                    }
                    size--;
                } else {
                    processRecord = null;
                    break;
                }
            }
            if (processRecord != null && isForegroundServiceAllowedInBackgroundRestricted(processRecord)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isForegroundServiceAllowedInBackgroundRestricted(ProcessRecord processRecord) {
        int i;
        ProcessStateRecord processStateRecord = processRecord.mState;
        if (!isDeviceProvisioningPackage(processRecord.info.packageName) && processStateRecord.mBackgroundRestricted && (i = processStateRecord.mSetProcState) > 3) {
            return i == 4 && processStateRecord.mSetBoundByNonBgRestrictedApp;
        }
        return true;
    }

    public final boolean isTempAllowedByAlarmClock(int i) {
        ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i);
        return isAllowlistedForFgsStartLOSP != null && isAllowlistedForFgsStartLOSP.mReasonCode == 301;
    }

    public final void killServicesLocked(ProcessRecord processRecord, boolean z, boolean z2) {
        boolean z3;
        ArraySet arraySet;
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        for (int size = processServiceRecord.mConnections.size() - 1; size >= 0; size--) {
            removeConnectionLocked(processServiceRecord.getConnectionAt(size), processRecord, null, true);
        }
        updateServiceConnectionActivitiesLocked(processServiceRecord);
        int size2 = processServiceRecord.mConnections.size();
        for (int i = 0; i < size2; i++) {
            ConnectionRecord connectionRecord = (ConnectionRecord) processServiceRecord.mConnections.valueAt(i);
            AppBindRecord appBindRecord = connectionRecord.binding;
            ProcessRecord processRecord2 = appBindRecord.attributedClient;
            if (processRecord2 != null && appBindRecord.service.isSdkSandbox && (arraySet = processRecord2.mServices.mSdkSandboxConnections) != null) {
                arraySet.remove(connectionRecord);
            }
        }
        processServiceRecord.mConnections.clear();
        ArraySet arraySet2 = processServiceRecord.mSdkSandboxConnections;
        if (arraySet2 != null) {
            arraySet2.clear();
        }
        processServiceRecord.mAllowlistManager = false;
        for (int size3 = processServiceRecord.mServices.size() - 1; size3 >= 0; size3--) {
            ServiceRecord runningServiceAt = processServiceRecord.getRunningServiceAt(size3);
            this.mAm.mBatteryStatsService.noteServiceStopLaunch(runningServiceAt.appInfo.uid, runningServiceAt.name.getPackageName(), runningServiceAt.name.getClassName());
            ProcessRecord processRecord3 = runningServiceAt.app;
            if (processRecord3 != processRecord && processRecord3 != null && !processRecord3.mPersistent) {
                runningServiceAt.app.mServices.stopService(runningServiceAt);
                runningServiceAt.app.mServices.updateBoundClientUids();
            }
            runningServiceAt.setProcess(null);
            runningServiceAt.isolationHostProc = null;
            runningServiceAt.executeNesting = 0;
            synchronized (this.mAm.mProcessStats.mLock) {
                ServiceState serviceState = runningServiceAt.tracker;
                if (serviceState != null) {
                    serviceState.clearCurrentOwner(runningServiceAt, true);
                    runningServiceAt.tracker = null;
                }
            }
            this.mDestroyingServices.remove(runningServiceAt);
            for (int size4 = runningServiceAt.bindings.size() - 1; size4 >= 0; size4--) {
                IntentBindRecord intentBindRecord = (IntentBindRecord) runningServiceAt.bindings.valueAt(size4);
                intentBindRecord.binder = null;
                intentBindRecord.hasBound = false;
                intentBindRecord.received = false;
                intentBindRecord.requested = false;
                for (int size5 = intentBindRecord.apps.size() - 1; size5 >= 0; size5--) {
                    ProcessRecord processRecord4 = (ProcessRecord) intentBindRecord.apps.keyAt(size5);
                    if (!processRecord4.mKilledByAm && processRecord4.mThread != null) {
                        AppBindRecord appBindRecord2 = (AppBindRecord) intentBindRecord.apps.valueAt(size5);
                        for (int size6 = appBindRecord2.connections.size() - 1; size6 >= 0; size6--) {
                            ConnectionRecord connectionRecord2 = (ConnectionRecord) appBindRecord2.connections.valueAt(size6);
                            if (!connectionRecord2.hasFlag(1) || !connectionRecord2.notHasFlag(48)) {
                            }
                        }
                    }
                }
            }
        }
        ServiceMap serviceMapLocked = getServiceMapLocked(processRecord.userId);
        for (int size7 = processServiceRecord.mServices.size() - 1; size7 >= 0; size7--) {
            ServiceRecord runningServiceAt2 = processServiceRecord.getRunningServiceAt(size7);
            if (!processRecord.mPersistent) {
                processServiceRecord.stopService(runningServiceAt2);
                processServiceRecord.updateBoundClientUids();
            }
            ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.get(runningServiceAt2.instanceName);
            if (serviceRecord != runningServiceAt2) {
                if (serviceRecord != null) {
                    Slog.wtf("ActivityManager", "Service " + runningServiceAt2 + " in process " + processRecord + " not same as in map: " + serviceRecord);
                }
            } else if (z && runningServiceAt2.crashCount >= this.mAm.mConstants.BOUND_SERVICE_MAX_CRASH_RETRY && (runningServiceAt2.serviceInfo.applicationInfo.flags & 8) == 0) {
                Slog.w("ActivityManager", "Service crashed " + runningServiceAt2.crashCount + " times, stopping: " + runningServiceAt2);
                Integer valueOf = Integer.valueOf(runningServiceAt2.userId);
                Integer valueOf2 = Integer.valueOf(runningServiceAt2.crashCount);
                String str = runningServiceAt2.shortInstanceName;
                ProcessRecord processRecord5 = runningServiceAt2.app;
                EventLog.writeEvent(30034, valueOf, valueOf2, str, Integer.valueOf(processRecord5 != null ? processRecord5.mPid : -1));
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (!z || !this.mAm.mUserController.isUserRunning(runningServiceAt2.userId, 0)) {
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (z2) {
                boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                String str2 = processRecord.info.packageName;
                int i2 = processRecord.userId;
                freecessController.getClass();
                boolean z5 = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                mARsPolicyManager.getClass();
                if (!MARsPolicyManager.isChinaModel || mARsPolicyManager.isAutoRunBlockedApp(str2, i2)) {
                    synchronized (MARsPolicyManager.MARsLock) {
                        try {
                            FreecessPkgStatus packageStatus = freecessController.getPackageStatus(i2, str2);
                            if (packageStatus != null) {
                                MARsFreezeStateRecord mARsFreezeStateRecord = packageStatus.freezedRecord;
                                z3 = !mARsFreezeStateRecord.isFrozen && "Signal".equals(mARsFreezeStateRecord.unfreezedReason);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } else {
                    z3 = false;
                }
                if (z3) {
                    bringDownServiceLocked(runningServiceAt2, true);
                } else {
                    this.mAm.pendingScheduleServiceRestart(processRecord.uid, runningServiceAt2);
                }
            } else {
                tryScheduleServiceRestartLocked(runningServiceAt2);
            }
        }
        this.mAm.updateOomAdjPendingTargetsLocked(19);
        if (!z || z2) {
            processServiceRecord.mServices.clear();
            processServiceRecord.updateHasTopStartedAlmostPerceptibleServices();
            processServiceRecord.clearBoundClientUids();
            for (int size8 = this.mRestartingServices.size() - 1; size8 >= 0; size8--) {
                ServiceRecord serviceRecord2 = (ServiceRecord) this.mRestartingServices.get(size8);
                if (serviceRecord2.processName.equals(processRecord.processName) && serviceRecord2.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mRestartingServices.remove(size8);
                    clearRestartingIfNeededLocked(serviceRecord2);
                }
            }
            for (int size9 = this.mPendingServices.size() - 1; size9 >= 0; size9--) {
                ServiceRecord serviceRecord3 = (ServiceRecord) this.mPendingServices.get(size9);
                if (serviceRecord3.processName.equals(processRecord.processName) && serviceRecord3.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mPendingServices.remove(size9);
                }
            }
            for (int size10 = this.mPendingBringups.size() - 1; size10 >= 0; size10--) {
                ServiceRecord serviceRecord4 = (ServiceRecord) this.mPendingBringups.keyAt(size10);
                if (serviceRecord4.processName.equals(processRecord.processName) && serviceRecord4.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mPendingBringups.removeAt(size10);
                }
            }
        }
        int size11 = this.mDestroyingServices.size();
        while (size11 > 0) {
            size11--;
            ServiceRecord serviceRecord5 = (ServiceRecord) this.mDestroyingServices.get(size11);
            if (serviceRecord5.app == processRecord) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    ServiceState serviceState2 = serviceRecord5.tracker;
                    if (serviceState2 != null) {
                        serviceState2.clearCurrentOwner(serviceRecord5, true);
                        serviceRecord5.tracker = null;
                    }
                }
                this.mDestroyingServices.remove(size11);
            }
        }
        processServiceRecord.mExecutingServices.clear();
        processServiceRecord.mScheduleServiceTimeoutPending = false;
    }

    public final void logFGSStateChangeLocked(ServiceRecord serviceRecord, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean z2;
        int i6;
        int i7;
        int i8;
        if (ActivityManagerUtils.shouldSamplePackageForAtom(this.mAm.mConstants.mFgsAtomSampleRate, serviceRecord.packageName)) {
            if (i == 1 || i == 2 || i == 5) {
                z2 = serviceRecord.mAllowWhileInUsePermissionInFgsAtEntering;
                i6 = serviceRecord.mAllowStartForegroundAtEntering;
            } else {
                z2 = serviceRecord.isFgsAllowedWiu_forCapabilities();
                i6 = serviceRecord.getFgsAllowStart();
            }
            boolean z3 = z2;
            int i9 = i6;
            ApplicationInfo applicationInfo = serviceRecord.mRecentCallerApplicationInfo;
            int i10 = applicationInfo != null ? applicationInfo.targetSdkVersion : 0;
            ApplicationInfo applicationInfo2 = serviceRecord.appInfo;
            int i11 = applicationInfo2.uid;
            String str = serviceRecord.shortInstanceName;
            int i12 = applicationInfo2.targetSdkVersion;
            int i13 = serviceRecord.mRecentCallingUid;
            ActivityManagerService.FgsTempAllowListItem fgsTempAllowListItem = serviceRecord.mInfoTempFgsAllowListReason;
            int i14 = fgsTempAllowListItem != null ? fgsTempAllowListItem.mCallingUid : -1;
            boolean z4 = serviceRecord.mFgsNotificationWasDeferred;
            boolean z5 = serviceRecord.mFgsNotificationShown;
            int i15 = serviceRecord.mStartForegroundCount;
            boolean z6 = serviceRecord.mFgsHasNotificationPermission;
            int i16 = serviceRecord.foregroundServiceType;
            boolean z7 = serviceRecord.mIsFgsDelegate;
            ForegroundServiceDelegation foregroundServiceDelegation = serviceRecord.mFgsDelegation;
            FrameworkStatsLog.write(60, i11, str, i, z3, i9, i12, i13, i10, i14, z4, z5, i2, i15, 0, z6, i16, i4, z7, foregroundServiceDelegation != null ? foregroundServiceDelegation.mOptions.mClientUid : -1, foregroundServiceDelegation != null ? foregroundServiceDelegation.mOptions.mDelegationService : 0, 0, null, null, this.mAm.mProcessList.getUidProcStateLOSP(i11), this.mAm.mProcessList.getUidProcessCapabilityLOSP(serviceRecord.appInfo.uid), this.mAm.mProcessList.getUidProcStateLOSP(serviceRecord.mRecentCallingUid), this.mAm.mProcessList.getUidProcessCapabilityLOSP(serviceRecord.mRecentCallingUid), 0L, 0L, serviceRecord.mAllowWiu_noBinding, serviceRecord.mAllowWiu_inBindService, serviceRecord.mAllowWiu_byBindings, serviceRecord.mAllowStart_noBinding, serviceRecord.mAllowStart_inBindService, serviceRecord.mAllowStart_byBindings, i5, z);
            if (i == 1) {
                i8 = 30100;
                i7 = 2;
            } else {
                i7 = 2;
                if (i == 2) {
                    i8 = 30102;
                } else if (i == 3) {
                    i8 = 30101;
                } else if (i != 5) {
                    return;
                } else {
                    i8 = 30103;
                }
            }
            EventLog.writeEvent(i8, Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, Integer.valueOf(z3 ? 1 : 0), PowerExemptionManager.reasonCodeToString(i9), Integer.valueOf(serviceRecord.appInfo.targetSdkVersion), Integer.valueOf(i10), Integer.valueOf(serviceRecord.mFgsNotificationWasDeferred ? 1 : 0), Integer.valueOf(serviceRecord.mFgsNotificationShown ? 1 : 0), Integer.valueOf(i2), Integer.valueOf(serviceRecord.mStartForegroundCount), i3 != 1 ? i3 != i7 ? "UNKNOWN" : "STOP_SERVICE" : "STOP_FOREGROUND", Integer.valueOf(serviceRecord.foregroundServiceType));
        }
    }

    public final void logFgsBackgroundStart(ServiceRecord serviceRecord) {
        if (serviceRecord.mLoggedInfoAllowStartForeground) {
            return;
        }
        StringBuilder sb = new StringBuilder("Background started FGS: ");
        sb.append(serviceRecord.isFgsAllowedStart() ? "Allowed " : "Disallowed ");
        sb.append(serviceRecord.mInfoAllowStartForeground);
        sb.append(serviceRecord.isShortFgs() ? " (Called on SHORT_SERVICE)" : "");
        String sb2 = sb.toString();
        if (serviceRecord.isFgsAllowedStart()) {
            if (ActivityManagerUtils.shouldSamplePackageForAtom(this.mAm.mConstants.mFgsStartAllowedLogSampleRate, serviceRecord.packageName)) {
                Slog.wtfQuiet("ActivityManager", sb2);
            }
            Slog.i("ActivityManager", sb2);
        } else {
            Slog.wtfQuiet("ActivityManager", sb2);
            Slog.w("ActivityManager", sb2);
        }
        serviceRecord.mLoggedInfoAllowStartForeground = true;
    }

    public final void maybeStopFgsTimeoutLocked(ServiceRecord serviceRecord) {
        int timeLimitedFgsType = getTimeLimitedFgsType(serviceRecord.foregroundServiceType);
        if (timeLimitedFgsType == 0) {
            return;
        }
        ServiceRecord.TimeLimitedFgsInfo fgsTimeLimitedInfo = getFgsTimeLimitedInfo(serviceRecord.appInfo.uid, timeLimitedFgsType);
        if (fgsTimeLimitedInfo != null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            fgsTimeLimitedInfo.mTotalRuntime = (uptimeMillis - fgsTimeLimitedInfo.mLastFgsStartTime) + fgsTimeLimitedInfo.mTotalRuntime;
            fgsTimeLimitedInfo.mLastFgsStartTime = uptimeMillis;
            fgsTimeLimitedInfo.decNumParallelServices();
        }
        Slog.d("ActivityManager", "Stop FGS timeout: " + serviceRecord);
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mHandler.removeMessages(84, serviceRecord);
        activityManagerService.mHandler.removeMessages(85, serviceRecord);
    }

    public final void maybeStopShortFgsTimeoutLocked(ServiceRecord serviceRecord) {
        serviceRecord.mShortFgsInfo = null;
        if (serviceRecord.isShortFgs()) {
            unscheduleShortFgsTimeoutLocked(serviceRecord);
        }
    }

    public final void maybeUpdateFgsTrackingLocked(int i, ServiceRecord serviceRecord) {
        int timeLimitedFgsType = getTimeLimitedFgsType(i);
        if (timeLimitedFgsType != 0 || (serviceRecord.startRequested && serviceRecord.isForeground && serviceRecord.ams.mServices.getTimeLimitedFgsType(serviceRecord.foregroundServiceType) != 0)) {
            ActivityManagerService activityManagerService = this.mAm;
            if (timeLimitedFgsType != 0) {
                ServiceRecord.TimeLimitedFgsInfo fgsTimeLimitedInfo = getFgsTimeLimitedInfo(serviceRecord.appInfo.uid, timeLimitedFgsType);
                if (fgsTimeLimitedInfo != null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    fgsTimeLimitedInfo.mTotalRuntime = (uptimeMillis - fgsTimeLimitedInfo.mLastFgsStartTime) + fgsTimeLimitedInfo.mTotalRuntime;
                    fgsTimeLimitedInfo.mLastFgsStartTime = uptimeMillis;
                    fgsTimeLimitedInfo.decNumParallelServices();
                }
                if (!serviceRecord.startRequested || !serviceRecord.isForeground || serviceRecord.ams.mServices.getTimeLimitedFgsType(serviceRecord.foregroundServiceType) == 0) {
                    activityManagerService.mHandler.removeMessages(84, serviceRecord);
                    activityManagerService.mHandler.removeMessages(85, serviceRecord);
                    return;
                }
            }
            traceInstant("FGS start: ", serviceRecord);
            long uptimeMillis2 = SystemClock.uptimeMillis();
            SparseArray sparseArray = (SparseArray) this.mTimeLimitedFgsInfo.get(serviceRecord.appInfo.uid);
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                this.mTimeLimitedFgsInfo.put(serviceRecord.appInfo.uid, sparseArray);
            }
            int timeLimitedFgsType2 = getTimeLimitedFgsType(serviceRecord.foregroundServiceType);
            ServiceRecord.TimeLimitedFgsInfo timeLimitedFgsInfo = (ServiceRecord.TimeLimitedFgsInfo) sparseArray.get(timeLimitedFgsType2);
            if (timeLimitedFgsInfo == null) {
                timeLimitedFgsInfo = new ServiceRecord.TimeLimitedFgsInfo();
                timeLimitedFgsInfo.mTimeLimitExceededAt = Long.MIN_VALUE;
                timeLimitedFgsInfo.mTotalRuntime = 0L;
                timeLimitedFgsInfo.mNumParallelServices = 0;
                sparseArray.put(timeLimitedFgsType2, timeLimitedFgsInfo);
            }
            int i2 = timeLimitedFgsInfo.mNumParallelServices + 1;
            timeLimitedFgsInfo.mNumParallelServices = i2;
            if (i2 == 1) {
                timeLimitedFgsInfo.mFirstFgsStartUptime = uptimeMillis2;
                timeLimitedFgsInfo.mFirstFgsStartRealtime = SystemClock.elapsedRealtime();
            }
            timeLimitedFgsInfo.mLastFgsStartTime = uptimeMillis2;
            activityManagerService.mHandler.removeMessages(84, serviceRecord);
            activityManagerService.mHandler.removeMessages(85, serviceRecord);
            Message obtainMessage = activityManagerService.mHandler.obtainMessage(84, serviceRecord);
            long j = timeLimitedFgsType2 != 1 ? timeLimitedFgsType2 != 8192 ? Long.MAX_VALUE : this.mAm.mConstants.mMediaProcessingFgsTimeoutDuration : this.mAm.mConstants.mDataSyncFgsTimeoutDuration;
            long max = j == Long.MAX_VALUE ? Long.MAX_VALUE : Math.max(0L, j - timeLimitedFgsInfo.mTotalRuntime) + timeLimitedFgsInfo.mLastFgsStartTime;
            if (max != Long.MAX_VALUE) {
                activityManagerService.mHandler.sendMessageAtTime(obtainMessage, max);
                return;
            }
            Slog.wtf("ActivityManager", "Couldn't calculate timeout for time-limited fgs: " + serviceRecord);
        }
    }

    public final void maybeUpdateShortFgsTrackingLocked(ServiceRecord serviceRecord, boolean z) {
        if (!serviceRecord.isShortFgs()) {
            serviceRecord.mShortFgsInfo = null;
            unscheduleShortFgsTimeoutLocked(serviceRecord);
            return;
        }
        boolean z2 = serviceRecord.mShortFgsInfo != null;
        if (!z && z2) {
            ServiceRecord.ShortFgsInfo shortFgsInfo = serviceRecord.getShortFgsInfo();
            ServiceRecord serviceRecord2 = ServiceRecord.this;
            shortFgsInfo.mStartForegroundCount = serviceRecord2.mStartForegroundCount;
            shortFgsInfo.mStartId = serviceRecord2.lastStartId;
            return;
        }
        traceInstant("short FGS start/extend: ", serviceRecord);
        serviceRecord.mShortFgsInfo = serviceRecord.new ShortFgsInfo(SystemClock.uptimeMillis());
        unscheduleShortFgsTimeoutLocked(serviceRecord);
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mHandler.sendMessageAtTime(activityManagerService.mHandler.obtainMessage(76, serviceRecord), serviceRecord.getShortFgsInfo().getTimeoutTime());
    }

    public final void onFgsCrashTimeout(ServiceRecord serviceRecord) {
        int timeLimitedFgsType = getTimeLimitedFgsType(serviceRecord.foregroundServiceType);
        if (timeLimitedFgsType == 0) {
            return;
        }
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ServiceRecord.TimeLimitedFgsInfo fgsTimeLimitedInfo = getFgsTimeLimitedInfo(serviceRecord.appInfo.uid, timeLimitedFgsType);
                if (fgsTimeLimitedInfo != null) {
                    fgsTimeLimitedInfo.decNumParallelServices();
                }
                String str = "A foreground service of type " + ServiceInfo.foregroundServiceTypeToLabel(timeLimitedFgsType) + " did not stop within its timeout: " + serviceRecord.name;
                if (android.app.Flags.enableFgsTimeoutCrashBehavior()) {
                    Slog.e("ActivityManager", "FGS Crashed: " + serviceRecord);
                    traceInstant("FGS Crash: ", serviceRecord);
                    ProcessRecord processRecord = serviceRecord.app;
                    if (processRecord != null) {
                        this.mAm.crashApplicationWithTypeWithExtras(processRecord.uid, processRecord.mPid, processRecord.info.packageName, serviceRecord.app.userId, str, false, 7, RemoteServiceException.ForegroundServiceDidNotStopInTimeException.createExtrasForService(serviceRecord.name));
                    }
                } else {
                    Slog.wtf("ActivityManager", str);
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void onFgsTimeout(ServiceRecord serviceRecord) {
        ProcessRecord processRecord;
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                int timeLimitedFgsType = getTimeLimitedFgsType(serviceRecord.foregroundServiceType);
                if (timeLimitedFgsType != 0 && (processRecord = serviceRecord.app) != null) {
                    boolean z = processRecord.mState.mCurProcState <= 2;
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long j = z ? uptimeMillis : serviceRecord.app.mState.mLastTopTime;
                    long j2 = timeLimitedFgsType != 1 ? timeLimitedFgsType != 8192 ? Long.MAX_VALUE : this.mAm.mConstants.mMediaProcessingFgsTimeoutDuration : this.mAm.mConstants.mDataSyncFgsTimeoutDuration;
                    if (j != Long.MIN_VALUE && j2 > uptimeMillis - j) {
                        this.mAm.mHandler.removeMessages(84, serviceRecord);
                        this.mAm.mHandler.removeMessages(85, serviceRecord);
                        this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(84, serviceRecord), j + j2);
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    Slog.e("ActivityManager", "FGS (" + ServiceInfo.foregroundServiceTypeToLabel(timeLimitedFgsType) + ") timed out: " + serviceRecord);
                    traceInstant("FGS timed out: ", serviceRecord);
                    ServiceRecord.TimeLimitedFgsInfo fgsTimeLimitedInfo = getFgsTimeLimitedInfo(serviceRecord.appInfo.uid, timeLimitedFgsType);
                    if (fgsTimeLimitedInfo != null) {
                        fgsTimeLimitedInfo.mTotalRuntime = (uptimeMillis - fgsTimeLimitedInfo.mLastFgsStartTime) + fgsTimeLimitedInfo.mTotalRuntime;
                        fgsTimeLimitedInfo.mLastFgsStartTime = uptimeMillis;
                        fgsTimeLimitedInfo.mTimeLimitExceededAt = uptimeMillis;
                        long j3 = fgsTimeLimitedInfo.mFirstFgsStartUptime;
                        logFGSStateChangeLocked(serviceRecord, 5, uptimeMillis > j3 ? (int) (uptimeMillis - j3) : 0, 0, 0, 0, false);
                    }
                    try {
                        serviceRecord.app.mThread.scheduleTimeoutServiceForType(serviceRecord, serviceRecord.lastStartId, timeLimitedFgsType);
                    } catch (RemoteException e) {
                        Slog.w("ActivityManager", "Exception from scheduleTimeoutServiceForType: " + e);
                    }
                    Message obtainMessage = this.mAm.mHandler.obtainMessage(85, serviceRecord);
                    ActivityManagerService activityManagerService2 = this.mAm;
                    activityManagerService2.mHandler.sendMessageDelayed(obtainMessage, activityManagerService2.mConstants.mFgsCrashExtraWaitDuration);
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mAm.mHandler.removeMessages(85, serviceRecord);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void onForegroundServiceNotificationUpdateLocked(boolean z, Notification notification, int i, String str, int i2) {
        int i3;
        int size = this.mPendingFgsNotifications.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingFgsNotifications.get(size);
            if (i2 == serviceRecord.userId && i == serviceRecord.foregroundId && serviceRecord.appInfo.packageName.equals(str) && z) {
                serviceRecord.mFgsNotificationShown = true;
                serviceRecord.mFgsNotificationDeferred = false;
                this.mPendingFgsNotifications.remove(size);
            }
            size--;
        }
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(i2);
        if (serviceMap != null) {
            for (i3 = 0; i3 < serviceMap.mServicesByInstanceName.size(); i3++) {
                ServiceRecord serviceRecord2 = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i3);
                if (serviceRecord2.isForeground && i == serviceRecord2.foregroundId && serviceRecord2.appInfo.packageName.equals(str)) {
                    serviceRecord2.foregroundNoti = notification;
                }
            }
        }
    }

    public final void onShortFgsAnrTimeout(ServiceRecord serviceRecord) {
        TimeoutRecord forShortFgsTimeout = TimeoutRecord.forShortFgsTimeout("A foreground service of FOREGROUND_SERVICE_TYPE_SHORT_SERVICE did not stop within a timeout: " + serviceRecord.name);
        forShortFgsTimeout.mLatencyTracker.waitingOnAMSLockStarted();
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                forShortFgsTimeout.mLatencyTracker.waitingOnAMSLockEnded();
                long uptimeMillis = SystemClock.uptimeMillis();
                ServiceRecord.ShortFgsInfo shortFgsInfo = serviceRecord.mShortFgsInfo;
                if (!serviceRecord.shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getAnrTime(), uptimeMillis)) {
                    this.mShortFGSAnrTimer.discard();
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mShortFGSAnrTimer.mFeature.getClass();
                Slog.e("ActivityManager", "Short FGS ANR'ed: " + serviceRecord);
                traceInstant("short FGS ANR: ", serviceRecord);
                this.mAm.mAnrHelper.appNotResponding(serviceRecord.app, forShortFgsTimeout);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void onShortFgsProcstateTimeout(ServiceRecord serviceRecord) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                ServiceRecord.ShortFgsInfo shortFgsInfo = serviceRecord.mShortFgsInfo;
                if (!serviceRecord.shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getProcStateDemoteTime(), uptimeMillis)) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.e("ActivityManager", "Short FGS procstate demoted: " + serviceRecord);
                traceInstant("short FGS demote: ", serviceRecord);
                this.mAm.updateOomAdjLocked(13, serviceRecord.app);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void onShortFgsTimeout(ServiceRecord serviceRecord) {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                ServiceRecord.ShortFgsInfo shortFgsInfo = serviceRecord.mShortFgsInfo;
                if (!serviceRecord.shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getTimeoutTime(), uptimeMillis)) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                Slog.e("ActivityManager", "Short FGS timed out: " + serviceRecord);
                traceInstant("short FGS timeout: ", serviceRecord);
                long j = serviceRecord.mFgsEnterTime;
                logFGSStateChangeLocked(serviceRecord, 5, uptimeMillis > j ? (int) (uptimeMillis - j) : 0, 0, 0, 0, false);
                try {
                    serviceRecord.app.mThread.scheduleTimeoutService(serviceRecord, serviceRecord.getShortFgsInfo().mStartId);
                } catch (RemoteException e) {
                    Slog.w("ActivityManager", "Exception from scheduleTimeoutService: " + e.toString());
                }
                this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(77, serviceRecord), serviceRecord.getShortFgsInfo().getProcStateDemoteTime());
                this.mShortFGSAnrTimer.start(serviceRecord.getShortFgsInfo().getAnrTime() - SystemClock.uptimeMillis(), serviceRecord);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final IBinder peekServiceLocked(Intent intent, String str, String str2) {
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        int callingPid = Binder.getCallingPid();
        activityManagerService.mInjector.getClass();
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, null, false, -1, null, str, str2, callingPid, Binder.getCallingUid(), UserHandle.getCallingUserId(), false, false, false, false, null, false, false, false);
        if (retrieveServiceLocked != null) {
            ServiceRecord serviceRecord = retrieveServiceLocked.record;
            if (serviceRecord == null) {
                StringBuilder sb = new StringBuilder("Permission Denial: Accessing service from pid=");
                activityManagerService.mInjector.getClass();
                sb.append(Binder.getCallingPid());
                sb.append(", uid=");
                activityManagerService.mInjector.getClass();
                sb.append(Binder.getCallingUid());
                sb.append(" requires ");
                sb.append(retrieveServiceLocked.permission);
                throw new SecurityException(sb.toString());
            }
            IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.get(serviceRecord.intent);
            if (intentBindRecord != null) {
                return intentBindRecord.binder;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
    
        if (r3 != r0) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void performRescheduleServiceRestartOnMemoryPressureLocked(long r17, long r19, long r21, java.lang.String r23) {
        /*
            r16 = this;
            r6 = r16
            r7 = r21
            long r0 = r19 - r17
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto Ld
            return
        Ld:
            com.android.server.am.ActivityManagerService r1 = r6.mAm
            if (r0 <= 0) goto L89
            com.android.server.am.ActivityManagerConstants r0 = r1.mConstants
            long r0 = r0.SERVICE_MIN_RESTART_TIME_BETWEEN
            long r9 = r0 + r19
            java.util.ArrayList r0 = r6.mRestartingServices
            int r11 = r0.size()
            r12 = 0
            r0 = r7
            r13 = r12
        L20:
            if (r13 >= r11) goto L9a
            java.util.ArrayList r2 = r6.mRestartingServices
            java.lang.Object r2 = r2.get(r13)
            com.android.server.am.ServiceRecord r2 = (com.android.server.am.ServiceRecord) r2
            android.content.pm.ServiceInfo r3 = r2.serviceInfo
            android.content.pm.ApplicationInfo r3 = r3.applicationInfo
            int r3 = r3.flags
            r3 = r3 & 8
            if (r3 != 0) goto L84
            java.lang.String r3 = r2.packageName
            android.util.ArraySet r4 = r6.mRestartBackoffDisabledPackages
            boolean r3 = r4.contains(r3)
            r4 = 1
            r3 = r3 ^ r4
            if (r3 != 0) goto L41
            goto L84
        L41:
            int r3 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r3 > 0) goto L5c
            long r0 = r2.nextRestartTime
            long r14 = r2.mEarliestRestartTime
            long r4 = r2.mRestartSchedulingTime
            long r4 = r4 + r19
            long r3 = java.lang.Math.max(r14, r4)
            long r3 = java.lang.Math.max(r7, r3)
            r2.nextRestartTime = r3
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 == 0) goto L6c
            goto L6a
        L5c:
            long r3 = r2.nextRestartTime
            long r3 = r3 - r0
            int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r3 >= 0) goto L6c
            long r0 = r0 + r9
            long r0 = java.lang.Math.max(r0, r7)
            r2.nextRestartTime = r0
        L6a:
            r4 = 1
            goto L6d
        L6c:
            r4 = r12
        L6d:
            long r14 = r2.nextRestartTime
            long r0 = r14 - r7
            r2.restartDelay = r0
            if (r4 == 0) goto L82
            java.lang.String r3 = "Rescheduling"
            r0 = r16
            r1 = r2
            r2 = r3
            r3 = r23
            r4 = r21
            r0.performScheduleRestartLocked(r1, r2, r3, r4)
        L82:
            r0 = r14
            goto L86
        L84:
            long r0 = r2.nextRestartTime
        L86:
            int r13 = r13 + 1
            goto L20
        L89:
            if (r0 >= 0) goto L9a
            com.android.server.am.ActivityManagerConstants r0 = r1.mConstants
            long r3 = r0.SERVICE_MIN_RESTART_TIME_BETWEEN
            r0 = r16
            r1 = r19
            r5 = r21
            r7 = r23
            r0.rescheduleServiceRestartIfPossibleLocked(r1, r3, r5, r7)
        L9a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.performRescheduleServiceRestartOnMemoryPressureLocked(long, long, long, java.lang.String):void");
    }

    public final void performScheduleRestartLocked(ServiceRecord serviceRecord, String str, String str2, long j) {
        if (serviceRecord.fgRequired && serviceRecord.fgWaiting) {
            this.mServiceFGAnrTimer.cancel(serviceRecord);
            serviceRecord.fgWaiting = false;
        }
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mHandler.removeCallbacks(serviceRecord.restarter);
        activityManagerService.mHandler.postAtTime(serviceRecord.restarter, serviceRecord.nextRestartTime);
        serviceRecord.nextRestartTime = j + serviceRecord.restartDelay;
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " restart of crashed service ");
        m.append(serviceRecord.shortInstanceName);
        m.append(" in ");
        m.append(serviceRecord.restartDelay);
        m.append("ms for ");
        m.append(str2);
        Slog.w("ActivityManager", m.toString());
        EventLog.writeEvent(30035, Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, Long.valueOf(serviceRecord.restartDelay));
    }

    public final void performServiceRestartLocked(ServiceRecord serviceRecord) {
        ActivityManagerService activityManagerService = this.mAm;
        if (this.mRestartingServices.contains(serviceRecord)) {
            if (!isServiceNeededLocked(serviceRecord, false, false)) {
                Slog.wtf("ActivityManager", "Restarting service that is not needed: " + serviceRecord);
            } else {
                try {
                    bringUpServiceLocked(serviceRecord, serviceRecord.intent.getIntent().getFlags(), serviceRecord.createdFromFg, true, false, false, 0);
                } catch (TransactionTooLargeException unused) {
                } catch (Throwable th) {
                    activityManagerService.updateOomAdjPendingTargetsLocked(6);
                    throw th;
                }
                activityManagerService.updateOomAdjPendingTargetsLocked(6);
            }
        }
    }

    public final void processStartTimedOutLocked(ProcessRecord processRecord) {
        int size = this.mPendingServices.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mPendingServices.get(i);
            if (Constants.SYSTEMUI_PACKAGE_NAME.equals(serviceRecord.processName)) {
                String str = "Do not bringing down SystemUI services : " + serviceRecord;
                Slog.wtf("ActivityManager", str);
                ((ArrayList) this.mRevivalServicesMessages).add(DATE_FORMATTER.format(Long.valueOf(System.currentTimeMillis())) + " " + str);
            } else {
                if ((processRecord.uid == serviceRecord.appInfo.uid && processRecord.processName.equals(serviceRecord.processName)) || serviceRecord.isolationHostProc == processRecord) {
                    Slog.w("ActivityManager", "Forcing bringing down service: " + serviceRecord);
                    serviceRecord.isolationHostProc = null;
                    this.mPendingServices.remove(i);
                    size = this.mPendingServices.size();
                    i--;
                    bringDownServiceLocked(serviceRecord, true);
                    z = true;
                }
            }
            i++;
        }
        if (z) {
            this.mAm.updateOomAdjPendingTargetsLocked(12);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r12v3 */
    public final void publishServiceLocked(ServiceRecord serviceRecord, Intent intent, IBinder iBinder) {
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (serviceRecord != null) {
            try {
                Intent.FilterComparison filterComparison = new Intent.FilterComparison(intent);
                IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.get(filterComparison);
                boolean z = false;
                if (intentBindRecord != null && !intentBindRecord.received) {
                    intentBindRecord.binder = iBinder;
                    intentBindRecord.requested = true;
                    intentBindRecord.received = true;
                    ArrayMap arrayMap = serviceRecord.connections;
                    int size = arrayMap.size() - 1;
                    while (size >= 0) {
                        ?? r11 = (ArrayList) arrayMap.valueAt(size);
                        for (?? r12 = z; r12 < r11.size(); r12++) {
                            ConnectionRecord connectionRecord = (ConnectionRecord) r11.get(r12);
                            if (filterComparison.equals(connectionRecord.binding.intent.intent)) {
                                ComponentName componentName = connectionRecord.aliasComponent;
                                if (componentName == null) {
                                    componentName = serviceRecord.name;
                                }
                                try {
                                    connectionRecord.conn.connected(componentName, iBinder, z);
                                } catch (Exception e) {
                                    Slog.w("ActivityManager", "Failure sending service " + serviceRecord.shortInstanceName + " to connection " + connectionRecord.conn.asBinder() + " (in " + connectionRecord.binding.client.processName + ")", e);
                                }
                            }
                            z = false;
                        }
                        size--;
                        z = false;
                    }
                }
                boolean contains = this.mDestroyingServices.contains(serviceRecord);
                Flags.serviceBindingOomAdjPolicy();
                serviceDoneExecutingLocked(serviceRecord, contains, false, false, serviceRecord.wasOomAdjUpdated() ? 20 : 0);
            } finally {
                activityManagerService.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0193  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void realStartServiceLocked(com.android.server.am.ServiceRecord r19, com.android.server.am.ProcessRecord r20, android.app.IApplicationThread r21, int r22, boolean r23, int r24) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.realStartServiceLocked(com.android.server.am.ServiceRecord, com.android.server.am.ProcessRecord, android.app.IApplicationThread, int, boolean, int):void");
    }

    public final void registerAppOpCallbackLocked(ServiceRecord serviceRecord) {
        if (serviceRecord.app == null) {
            return;
        }
        int i = serviceRecord.appInfo.uid;
        AppOpCallback appOpCallback = (AppOpCallback) this.mFgsAppOpCallbacks.get(i);
        if (appOpCallback == null) {
            appOpCallback = new AppOpCallback(serviceRecord.app, this.mAm.getAppOpsManager$1());
            this.mFgsAppOpCallbacks.put(i, appOpCallback);
        }
        if (appOpCallback.mDestroyed) {
            Slog.wtf("ActivityManager", "Trying to register on a stale AppOpCallback.");
            return;
        }
        int i2 = appOpCallback.mNumFgs + 1;
        appOpCallback.mNumFgs = i2;
        if (i2 == 1) {
            AppOpsManager appOpsManager = appOpCallback.mAppOpsManager;
            int[] iArr = AppOpCallback.LOGGED_AP_OPS;
            appOpsManager.startWatchingNoted(iArr, appOpCallback.mOpNotedCallback);
            appOpCallback.mAppOpsManager.startWatchingStarted(iArr, appOpCallback.mOpStartedCallback);
        }
    }

    public final boolean registerForegroundServiceObserverLocked(int i, IForegroundServiceObserver iForegroundServiceObserver) {
        try {
            int size = this.mServiceMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceMap serviceMap = (ServiceMap) this.mServiceMap.valueAt(i2);
                if (serviceMap != null) {
                    int size2 = serviceMap.mServicesByInstanceName.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(i3);
                        if (serviceRecord.isForeground) {
                            ApplicationInfo applicationInfo = serviceRecord.appInfo;
                            if (i == applicationInfo.uid) {
                                iForegroundServiceObserver.onForegroundStateChanged(serviceRecord, applicationInfo.packageName, serviceRecord.userId, true);
                            }
                        }
                    }
                }
            }
            this.mFgsObservers.register(iForegroundServiceObserver);
            return true;
        } catch (RemoteException unused) {
            NandswapManager$$ExternalSyntheticOutline0.m(i, "Bad FGS observer from uid ", "ActivityManager");
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:166:0x02a1, code lost:
    
        if ((!((r12.flags & 4294971392L) != 0)) != false) goto L162;
     */
    /* JADX WARN: Removed duplicated region for block: B:170:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x02d0 A[Catch: Exception -> 0x02d9, TryCatch #1 {Exception -> 0x02d9, blocks: (B:175:0x02b5, B:177:0x02be, B:179:0x02c6, B:181:0x02d0, B:182:0x02db), top: B:174:0x02b5 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x02ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int removeConnectionLocked(com.android.server.am.ConnectionRecord r12, com.android.server.am.ProcessRecord r13, com.android.server.wm.ActivityServiceConnectionsHolder r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 845
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.removeConnectionLocked(com.android.server.am.ConnectionRecord, com.android.server.am.ProcessRecord, com.android.server.wm.ActivityServiceConnectionsHolder, boolean):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean requestServiceBindingLocked(com.android.server.am.ServiceRecord r18, com.android.server.am.IntentBindRecord r19, boolean r20, boolean r21, int r22) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.requestServiceBindingLocked(com.android.server.am.ServiceRecord, com.android.server.am.IntentBindRecord, boolean, boolean, int):boolean");
    }

    public final boolean requestStartTargetPermissionsReviewIfNeededLocked(final ServiceRecord serviceRecord, String str, String str2, int i, final Intent intent, final boolean z, final int i2, boolean z2, final IServiceConnection iServiceConnection) {
        ActivityManagerService activityManagerService = this.mAm;
        if (!activityManagerService.getPackageManagerInternal().isPermissionsReviewRequired(serviceRecord.userId, serviceRecord.packageName)) {
            return true;
        }
        if (!z) {
            StringBuilder sb = new StringBuilder("u");
            sb.append(serviceRecord.userId);
            sb.append(z2 ? " Binding" : " Starting");
            sb.append(" a service in package");
            ProfileService$1$$ExternalSyntheticOutline0.m(sb, serviceRecord.packageName, " requires a permissions review", "ActivityManager");
            return false;
        }
        final Intent m = BatteryService$$ExternalSyntheticOutline0.m(411041792, "android.intent.action.REVIEW_PERMISSIONS");
        m.putExtra("android.intent.extra.PACKAGE_NAME", serviceRecord.packageName);
        if (z2) {
            m.putExtra("android.intent.extra.REMOTE_CALLBACK", (Parcelable) new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.am.ActiveServices.2
                public final void onResult(Bundle bundle) {
                    ActivityManagerService activityManagerService2;
                    ActivityManagerService activityManagerService3 = ActiveServices.this.mAm;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService3) {
                        try {
                            ActiveServices.this.mAm.mInjector.getClass();
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (!ActiveServices.this.mPendingServices.contains(serviceRecord)) {
                                    ActiveServices.this.mAm.mInjector.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                PackageManagerInternal packageManagerInternal = ActiveServices.this.mAm.getPackageManagerInternal();
                                ServiceRecord serviceRecord2 = serviceRecord;
                                if (packageManagerInternal.isPermissionsReviewRequired(serviceRecord2.userId, serviceRecord2.packageName)) {
                                    ActiveServices.this.unbindServiceLocked(iServiceConnection);
                                } else {
                                    try {
                                        try {
                                            ActiveServices.this.bringUpServiceLocked(serviceRecord, intent.getFlags(), z, false, false, false, 0);
                                            activityManagerService2 = ActiveServices.this.mAm;
                                        } catch (Throwable th) {
                                            ActiveServices.this.mAm.updateOomAdjPendingTargetsLocked(6);
                                            throw th;
                                        }
                                    } catch (RemoteException unused) {
                                        activityManagerService2 = ActiveServices.this.mAm;
                                    }
                                    activityManagerService2.updateOomAdjPendingTargetsLocked(6);
                                }
                                ActiveServices.this.mAm.mInjector.getClass();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            } catch (Throwable th2) {
                                ActiveServices.this.mAm.mInjector.getClass();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th2;
                            }
                        } catch (Throwable th3) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th3;
                        }
                    }
                }
            }));
        } else {
            m.putExtra("android.intent.extra.INTENT", new IntentSender(activityManagerService.mPendingIntentController.getIntentSender(4, i, i2, 0, 1409286144, null, null, str, str2, null, new Intent[]{intent}, new String[]{intent.resolveType(activityManagerService.mContext.getContentResolver())})));
        }
        activityManagerService.mHandler.post(new Runnable() { // from class: com.android.server.am.ActiveServices.3
            @Override // java.lang.Runnable
            public final void run() {
                ActiveServices.this.mAm.mContext.startActivityAsUser(m, new UserHandle(i2));
            }
        });
        return false;
    }

    public final void rescheduleServiceRestartIfPossibleLocked(long j, long j2, long j3, String str) {
        long j4;
        long j5;
        long j6;
        long j7 = j + j2;
        long j8 = j7 * 2;
        int size = this.mRestartingServices.size();
        int i = -1;
        int i2 = 0;
        long j9 = j3;
        while (i2 < size) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i2);
            if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) == 0) {
                if (!this.mRestartBackoffDisabledPackages.contains(serviceRecord.packageName)) {
                    long j10 = j9 + j7;
                    long j11 = j8;
                    long j12 = serviceRecord.mEarliestRestartTime;
                    if (j10 <= j12) {
                        serviceRecord.nextRestartTime = Math.max(j3, Math.max(j12, i2 > 0 ? ((ServiceRecord) this.mRestartingServices.get(i2 - 1)).nextRestartTime + j7 : 0L));
                    } else {
                        if (j9 <= j3) {
                            serviceRecord.nextRestartTime = Math.max(j3, Math.max(j12, serviceRecord.mRestartSchedulingTime + j));
                        } else {
                            serviceRecord.nextRestartTime = Math.max(j3, j10);
                        }
                        int i3 = i + 1;
                        if (i2 > i3) {
                            this.mRestartingServices.remove(i2);
                            this.mRestartingServices.add(i3, serviceRecord);
                        }
                    }
                    int i4 = i;
                    long j13 = j9;
                    int i5 = i + 1;
                    while (true) {
                        if (i5 > i2) {
                            j4 = j7;
                            j5 = j11;
                            break;
                        }
                        ServiceRecord serviceRecord2 = (ServiceRecord) this.mRestartingServices.get(i5);
                        long j14 = serviceRecord2.nextRestartTime;
                        if (i5 == 0) {
                            j4 = j7;
                            j6 = j13;
                        } else {
                            j4 = j7;
                            j6 = ((ServiceRecord) this.mRestartingServices.get(i5 - 1)).nextRestartTime;
                        }
                        long j15 = j14 - j6;
                        j5 = j11;
                        if (j15 >= j5) {
                            break;
                        }
                        i4 = i5;
                        j13 = serviceRecord2.nextRestartTime;
                        i5++;
                        j11 = j5;
                        j7 = j4;
                    }
                    serviceRecord.restartDelay = serviceRecord.nextRestartTime - j3;
                    performScheduleRestartLocked(serviceRecord, "Rescheduling", str, j3);
                    i = i4;
                    j9 = j13;
                    i2++;
                    j8 = j5;
                    j7 = j4;
                }
            }
            j4 = j7;
            j5 = j8;
            j9 = serviceRecord.nextRestartTime;
            i = i2;
            i2++;
            j8 = j5;
            j7 = j4;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:261|262|(13:266|267|268|269|270|(3:272|(2:276|277)|278)|281|282|(3:284|(2:288|289)|290)|294|295|131|(5:133|134|135|136|(2:138|139)(2:140|(2:142|143)(2:144|(2:146|(2:148|149)(2:150|151))(2:152|(2:172|173)(2:156|(4:165|(1:167)|168|169)(2:163|164))))))(1:176))|298|267|268|269|270|(0)|281|282|(0)|294|295|131|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0325, code lost:
    
        if ((r7.flags & 2) != 0) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x032f, code lost:
    
        throw new java.lang.IllegalArgumentException("Service cannot be both sdk sandbox and isolated");
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0515, code lost:
    
        if (r9 == null) goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x051b, code lost:
    
        if ((r2.flags & 16) == 0) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0534, code lost:
    
        throw new java.lang.SecurityException("BIND_SHARED_ISOLATED_PROCESS failed, " + r5 + " has not set the allowSharedIsolatedProcess  attribute.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x053d, code lost:
    
        throw new java.lang.IllegalArgumentException("instanceName must be provided for binding a service into a shared isolated process.");
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0736  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x08da A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0696 A[Catch: RemoteException -> 0x06e8, TryCatch #5 {RemoteException -> 0x06e8, blocks: (B:270:0x067e, B:272:0x0696, B:274:0x06aa, B:276:0x06b2, B:278:0x06b7, B:282:0x06ba, B:284:0x06c4, B:286:0x06d8, B:288:0x06e0), top: B:269:0x067e }] */
    /* JADX WARN: Removed duplicated region for block: B:284:0x06c4 A[Catch: RemoteException -> 0x06e8, TryCatch #5 {RemoteException -> 0x06e8, blocks: (B:270:0x067e, B:272:0x0696, B:274:0x06aa, B:276:0x06b2, B:278:0x06b7, B:282:0x06ba, B:284:0x06c4, B:286:0x06d8, B:288:0x06e0), top: B:269:0x067e }] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x015f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0266  */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.server.am.ActivityManagerService] */
    /* JADX WARN: Type inference failed for: r11v5, types: [com.android.server.am.ActivityManagerService] */
    /* JADX WARN: Type inference failed for: r2v35, types: [int] */
    /* JADX WARN: Type inference failed for: r2v36 */
    /* JADX WARN: Type inference failed for: r2v71 */
    /* JADX WARN: Type inference failed for: r2v97 */
    /* JADX WARN: Type inference failed for: r2v98 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v54 */
    /* JADX WARN: Type inference failed for: r3v55, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v67, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r63v0 */
    /* JADX WARN: Type inference failed for: r63v1 */
    /* JADX WARN: Type inference failed for: r63v10 */
    /* JADX WARN: Type inference failed for: r63v14 */
    /* JADX WARN: Type inference failed for: r63v15 */
    /* JADX WARN: Type inference failed for: r63v16 */
    /* JADX WARN: Type inference failed for: r63v17 */
    /* JADX WARN: Type inference failed for: r63v18 */
    /* JADX WARN: Type inference failed for: r63v2 */
    /* JADX WARN: Type inference failed for: r63v3 */
    /* JADX WARN: Type inference failed for: r63v4 */
    /* JADX WARN: Type inference failed for: r63v5 */
    /* JADX WARN: Type inference failed for: r63v6 */
    /* JADX WARN: Type inference failed for: r63v7 */
    /* JADX WARN: Type inference failed for: r63v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked(android.content.Intent r66, java.lang.String r67, boolean r68, int r69, java.lang.String r70, java.lang.String r71, java.lang.String r72, int r73, int r74, int r75, boolean r76, boolean r77, boolean r78, boolean r79, android.app.ForegroundServiceDelegationOptions r80, boolean r81, boolean r82, boolean r83) {
        /*
            Method dump skipped, instructions count: 2291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.retrieveServiceLocked(android.content.Intent, java.lang.String, boolean, int, java.lang.String, java.lang.String, java.lang.String, int, int, int, boolean, boolean, boolean, boolean, android.app.ForegroundServiceDelegationOptions, boolean, boolean, boolean):com.android.server.am.ActiveServices$ServiceLookupResult");
    }

    public final void schedulePendingServiceStartLocked(int i, String str) {
        int size = this.mPendingBringups.size();
        while (true) {
            for (int i2 = size - 1; i2 >= 0 && size > 0; i2--) {
                ServiceRecord serviceRecord = (ServiceRecord) this.mPendingBringups.keyAt(i2);
                if (serviceRecord.userId == i && TextUtils.equals(serviceRecord.packageName, str)) {
                    ArrayList arrayList = (ArrayList) this.mPendingBringups.valueAt(i2);
                    if (arrayList != null) {
                        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                            ((Runnable) arrayList.get(size2)).run();
                        }
                        arrayList.clear();
                    }
                    int size3 = this.mPendingBringups.size();
                    this.mPendingBringups.remove(serviceRecord);
                    if (size != size3) {
                        break;
                    } else {
                        size = this.mPendingBringups.size();
                    }
                }
            }
            return;
            size = this.mPendingBringups.size();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v24, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11, types: [int] */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.util.ArrayList] */
    public final boolean scheduleServiceRestartLocked(ServiceRecord serviceRecord, boolean z) {
        boolean z2;
        long j;
        String str;
        Boolean bool;
        long j2;
        boolean z3;
        String str2;
        long j3;
        long j4;
        if (ActivityTaskManagerService.this.mShuttingDown) {
            ProfileService$1$$ExternalSyntheticOutline0.m(new StringBuilder("Not scheduling restart of crashed service "), serviceRecord.shortInstanceName, " - system is shutting down", "ActivityManager");
            return false;
        }
        ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
        if (serviceMapLocked.mServicesByInstanceName.get(serviceRecord.instanceName) != serviceRecord) {
            Slog.wtf("ActivityManager", "Attempting to schedule restart of " + serviceRecord + " when found in map: " + ((ServiceRecord) serviceMapLocked.mServicesByInstanceName.get(serviceRecord.instanceName)));
            return false;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int indexOf = this.mRestartingServices.indexOf(serviceRecord);
        int i = -1;
        boolean z4 = indexOf != -1;
        if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) == 0) {
            ActivityManagerConstants activityManagerConstants = this.mAm.mConstants;
            long j5 = activityManagerConstants.SERVICE_RESTART_DURATION;
            long j6 = activityManagerConstants.SERVICE_RESET_RUN_DURATION;
            int size = serviceRecord.deliveredStarts.size();
            if (size > 0) {
                int i2 = size - 1;
                boolean z5 = false;
                while (i2 >= 0) {
                    ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.deliveredStarts.get(i2);
                    UriPermissionOwner uriPermissionOwner = startItem.uriPermissions;
                    if (uriPermissionOwner != null) {
                        uriPermissionOwner.removeUriPermission(3, i, null, null);
                        startItem.uriPermissions = null;
                    }
                    if (startItem.intent == null) {
                        j4 = uptimeMillis;
                    } else if (!z || (startItem.deliveryCount < 3 && startItem.doneExecutingCount < 6)) {
                        serviceRecord.pendingStarts.add(0, startItem);
                        j4 = uptimeMillis;
                        long uptimeMillis2 = (SystemClock.uptimeMillis() - startItem.deliveredTime) * 2;
                        if (j5 < uptimeMillis2) {
                            j5 = uptimeMillis2;
                        }
                        if (j6 < uptimeMillis2) {
                            j6 = uptimeMillis2;
                        }
                    } else {
                        StringBuilder sb = new StringBuilder("Canceling start item ");
                        sb.append(startItem.intent);
                        sb.append(" in service ");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, serviceRecord.shortInstanceName, "ActivityManager");
                        j4 = uptimeMillis;
                        z5 = true;
                    }
                    i2--;
                    uptimeMillis = j4;
                    i = -1;
                }
                j2 = uptimeMillis;
                serviceRecord.deliveredStarts.clear();
                z3 = z5;
            } else {
                j2 = uptimeMillis;
                z3 = false;
            }
            if (z) {
                boolean canStopIfKilled = serviceRecord.canStopIfKilled(z3);
                if (canStopIfKilled && !serviceRecord.hasAutoCreateConnections()) {
                    return false;
                }
                str2 = (!serviceRecord.startRequested || canStopIfKilled) ? "connection" : "start-requested";
            } else {
                str2 = "always";
            }
            long j7 = serviceRecord.restartDelay;
            if (j7 == 0) {
                serviceRecord.restartCount++;
                serviceRecord.restartDelay = j5;
            } else {
                if (serviceRecord.crashCount > 1) {
                    serviceRecord.restartDelay = this.mAm.mConstants.BOUND_SERVICE_CRASH_RESTART_DURATION * (r9 - 1);
                } else if (j2 > serviceRecord.restartTime + j6) {
                    serviceRecord.restartCount = 1;
                    serviceRecord.restartDelay = j5;
                } else {
                    long j8 = j7 * this.mAm.mConstants.SERVICE_RESTART_DURATION_FACTOR;
                    serviceRecord.restartDelay = j8;
                    if (j8 < j5) {
                        serviceRecord.restartDelay = j5;
                    }
                }
            }
            if (!this.mRestartBackoffDisabledPackages.contains(serviceRecord.packageName)) {
                long j9 = j2 + serviceRecord.restartDelay;
                serviceRecord.mEarliestRestartTime = j9;
                serviceRecord.nextRestartTime = j9;
                if (z4) {
                    this.mRestartingServices.remove(indexOf);
                    z4 = false;
                }
                if (this.mRestartingServices.isEmpty()) {
                    long max = Math.max(getExtraRestartTimeInBetweenLocked() + j2, serviceRecord.nextRestartTime);
                    serviceRecord.nextRestartTime = max;
                    serviceRecord.restartDelay = max - j2;
                } else {
                    long extraRestartTimeInBetweenLocked = getExtraRestartTimeInBetweenLocked() + this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN;
                    loop2: while (true) {
                        long j10 = serviceRecord.nextRestartTime;
                        for (int size2 = this.mRestartingServices.size() - 1; size2 >= 0; size2--) {
                            long j11 = ((ServiceRecord) this.mRestartingServices.get(size2)).nextRestartTime;
                            if (j10 >= j11 - extraRestartTimeInBetweenLocked) {
                                j3 = j11 + extraRestartTimeInBetweenLocked;
                                if (j10 < j3) {
                                    break;
                                }
                            }
                            if (j10 >= j11 + extraRestartTimeInBetweenLocked) {
                                break loop2;
                            }
                        }
                        serviceRecord.nextRestartTime = j3;
                        serviceRecord.restartDelay = j3 - j2;
                    }
                }
            } else {
                long j12 = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                serviceRecord.restartDelay = j12;
                serviceRecord.nextRestartTime = j2 + j12;
            }
            str = str2;
            j = j2;
            z2 = false;
        } else {
            z2 = false;
            serviceRecord.restartCount = 0;
            serviceRecord.restartDelay = 0L;
            serviceRecord.mEarliestRestartTime = 0L;
            j = uptimeMillis;
            serviceRecord.nextRestartTime = j;
            str = "persistent";
        }
        serviceRecord.mRestartSchedulingTime = j;
        if (!z4) {
            if (indexOf == -1) {
                serviceRecord.createdFromFg = z2;
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.makeRestarting(this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            int size3 = this.mRestartingServices.size();
            ?? r3 = z2;
            while (true) {
                if (r3 >= size3) {
                    this.mRestartingServices.add(serviceRecord);
                    break;
                }
                if (((ServiceRecord) this.mRestartingServices.get(r3)).nextRestartTime > serviceRecord.nextRestartTime) {
                    this.mRestartingServices.add(r3, serviceRecord);
                    break;
                }
                r3++;
            }
        }
        cancelForegroundNotificationLocked(serviceRecord);
        if (((ChimeraManagerService) ServiceManager.getService("ChimeraManagerService")) != null) {
            String str3 = serviceRecord.packageName;
            RestartImmediatePackages restartImmediatePackages = RestartImmediatePackages.getInstance();
            if (str3 == null) {
                restartImmediatePackages.getClass();
                bool = Boolean.FALSE;
            } else {
                bool = (Boolean) ((ConcurrentHashMap) restartImmediatePackages.sPackages).get(str3);
            }
            if (bool != null) {
                long j13 = bool.booleanValue() ? 180000L : this.mAm.mConstants.SERVICE_RESTART_DURATION;
                serviceRecord.restartDelay = j13;
                serviceRecord.nextRestartTime = j13 + j;
            }
        }
        performScheduleRestartLocked(serviceRecord, "Scheduling", str, j);
        return true;
    }

    public final void scheduleServiceTimeoutLocked(ProcessRecord processRecord) {
        if (processRecord.mServices.mExecutingServices.size() == 0 || processRecord.mThread == null) {
            return;
        }
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        boolean z = processServiceRecord.mExecServicesFg;
        ActivityManagerService activityManagerService = this.mAm;
        this.mActiveServiceAnrTimer.start(z ? activityManagerService.mConstants.SERVICE_TIMEOUT : activityManagerService.mConstants.SERVICE_BACKGROUND_TIMEOUT, processRecord);
        processServiceRecord.mScheduleServiceTimeoutPending = false;
    }

    public final void sendServiceArgsLocked(ServiceRecord serviceRecord, boolean z, boolean z2) {
        int i;
        int size = serviceRecord.pendingStarts.size();
        if (size == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (serviceRecord.pendingStarts.size() <= 0) {
                break;
            }
            ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.pendingStarts.remove(0);
            if (startItem.intent != null || size <= 1) {
                startItem.deliveredTime = SystemClock.uptimeMillis();
                serviceRecord.deliveredStarts.add(startItem);
                startItem.deliveryCount++;
                NeededUriGrants neededUriGrants = startItem.neededGrants;
                if (neededUriGrants != null) {
                    UriGrantsManagerInternal uriGrantsManagerInternal = this.mAm.mUgmInternal;
                    if (startItem.uriPermissions == null) {
                        startItem.uriPermissions = new UriPermissionOwner(startItem.sr.ams.mUgmInternal, startItem);
                    }
                    ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal).grantUriPermissionUncheckedFromIntent(neededUriGrants, startItem.uriPermissions);
                }
                this.mAm.grantImplicitAccess(serviceRecord.userId, startItem.intent, startItem.callingId, UserHandle.getAppId(serviceRecord.appInfo.uid));
                bumpServiceExecutingLocked(serviceRecord, z, "start", 0, false);
                if (serviceRecord.fgRequired && !serviceRecord.fgWaiting) {
                    if (serviceRecord.isForeground) {
                        serviceRecord.fgRequired = false;
                    } else if (serviceRecord.app.mServices.mExecutingServices.size() != 0 && serviceRecord.app.mThread != null) {
                        serviceRecord.fgWaiting = true;
                        this.mServiceFGAnrTimer.start(this.mAm.mConstants.mServiceStartForegroundTimeoutMs, serviceRecord);
                    }
                }
                i = startItem.deliveryCount > 1 ? 2 : 0;
                if (startItem.doneExecutingCount > 0) {
                    i |= 1;
                }
                arrayList.add(new ServiceStartArgs(startItem.taskRemoved, startItem.id, i, startItem.intent));
            }
        }
        if (!z2) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            this.mAm.updateOomAdjPendingTargetsLocked(6);
        }
        ParceledListSlice parceledListSlice = new ParceledListSlice(arrayList);
        parceledListSlice.setInlineCountLimit(4);
        try {
            serviceRecord.app.mThread.scheduleServiceArgs(serviceRecord, parceledListSlice);
            e = null;
        } catch (TransactionTooLargeException e) {
            e = e;
            Slog.w("ActivityManager", "Failed delivering service starts", e);
        } catch (RemoteException e2) {
            e = e2;
            Slog.w("ActivityManager", "Failed delivering service starts", e);
        } catch (Exception e3) {
            e = e3;
            Slog.w("ActivityManager", "Unexpected exception", e);
        }
        if (e != null) {
            boolean contains = this.mDestroyingServices.contains(serviceRecord);
            int size2 = arrayList.size();
            while (i < size2) {
                serviceDoneExecutingLocked(serviceRecord, contains, contains, true, 19);
                i++;
            }
            this.mAm.updateOomAdjPendingTargetsLocked(19);
            if (e instanceof TransactionTooLargeException) {
                throw ((TransactionTooLargeException) e);
            }
        }
    }

    public final void serviceDoneExecutingLocked(ServiceRecord serviceRecord, int i, int i2, int i3) {
        boolean contains = this.mDestroyingServices.contains(serviceRecord);
        ActivityManagerService activityManagerService = this.mAm;
        if (serviceRecord == null) {
            StringBuilder sb = new StringBuilder("Done executing unknown service from pid ");
            activityManagerService.mInjector.getClass();
            sb.append(Binder.getCallingPid());
            Slog.w("ActivityManager", sb.toString());
            return;
        }
        boolean z = true;
        if (i == 1) {
            serviceRecord.callStart = true;
            if (i3 != 1000) {
                serviceRecord.startCommandResult = i3;
            }
            if (i3 == 0 || i3 == 1) {
                serviceRecord.findDeliveredStart(i2, false, true);
                serviceRecord.stopIfKilled = false;
            } else if (i3 == 2) {
                serviceRecord.findDeliveredStart(i2, false, true);
                if (serviceRecord.lastStartId == i2) {
                    serviceRecord.stopIfKilled = true;
                }
            } else if (i3 == 3) {
                ServiceRecord.StartItem findDeliveredStart = serviceRecord.findDeliveredStart(i2, false, false);
                if (findDeliveredStart != null) {
                    findDeliveredStart.deliveryCount = 0;
                    findDeliveredStart.doneExecutingCount++;
                    serviceRecord.stopIfKilled = true;
                }
            } else {
                if (i3 != 1000) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "Unknown service start result: "));
                }
                serviceRecord.findDeliveredStart(i2, true, true);
            }
            if (i3 == 0) {
                serviceRecord.callStart = false;
            }
        } else if (i == 2) {
            if (contains) {
                if (serviceRecord.executeNesting != 1) {
                    Slog.w("ActivityManager", "Service done with onDestroy, but executeNesting=" + serviceRecord.executeNesting + ": " + serviceRecord);
                    serviceRecord.executeNesting = 1;
                }
            } else if (serviceRecord.app != null) {
                Slog.w("ActivityManager", "Service done with onDestroy, but not inDestroying: " + serviceRecord + ", app=" + serviceRecord.app);
            }
            activityManagerService.mInjector.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Flags.serviceBindingOomAdjPolicy();
            serviceDoneExecutingLocked(serviceRecord, contains, contains, false, (!serviceRecord.wasOomAdjUpdated() || z) ? 20 : 0);
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        z = false;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        Flags.serviceBindingOomAdjPolicy();
        serviceDoneExecutingLocked(serviceRecord, contains, contains, false, (!serviceRecord.wasOomAdjUpdated() || z) ? 20 : 0);
        activityManagerService.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity2);
    }

    public final void serviceDoneExecutingLocked(ServiceRecord serviceRecord, boolean z, boolean z2, boolean z3, int i) {
        int i2 = serviceRecord.executeNesting - 1;
        serviceRecord.executeNesting = i2;
        if (i2 <= 0) {
            ProcessRecord processRecord = serviceRecord.app;
            if (processRecord != null) {
                ProcessServiceRecord processServiceRecord = processRecord.mServices;
                processServiceRecord.mExecServicesFg = false;
                processServiceRecord.mExecutingServices.remove(serviceRecord);
                if (processServiceRecord.mExecutingServices.size() == 0) {
                    ProcessRecord processRecord2 = serviceRecord.app;
                    if (processRecord2.mPid != 0) {
                        this.mActiveServiceAnrTimer.cancel(processRecord2);
                    }
                } else if (serviceRecord.executeFg) {
                    int size = processServiceRecord.mExecutingServices.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        if (((ServiceRecord) processServiceRecord.mExecutingServices.valueAt(size)).executeFg) {
                            processServiceRecord.mExecServicesFg = true;
                            break;
                        }
                        size--;
                    }
                }
                if (z) {
                    this.mDestroyingServices.remove(serviceRecord);
                    serviceRecord.bindings.clear();
                }
                if (i != 0) {
                    if (z3) {
                        this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
                    } else {
                        this.mAm.updateOomAdjLocked(i, serviceRecord.app);
                    }
                }
                serviceRecord.updateOomAdjSeq();
            }
            serviceRecord.executeFg = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    try {
                        int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                        long uptimeMillis = SystemClock.uptimeMillis();
                        serviceRecord.tracker.setExecuting(false, memFactorLocked, uptimeMillis);
                        serviceRecord.tracker.setForeground(false, memFactorLocked, uptimeMillis);
                        if (z2) {
                            serviceRecord.tracker.clearCurrentOwner(serviceRecord, false);
                            serviceRecord.tracker = null;
                        }
                    } finally {
                    }
                }
            }
            if (z2) {
                ProcessRecord processRecord3 = serviceRecord.app;
                if (processRecord3 != null && !processRecord3.mPersistent) {
                    stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
                }
                serviceRecord.setProcess(null);
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void serviceForegroundTimeout(ServiceRecord serviceRecord) {
        try {
            Trace.traceBegin(64L, "serviceForegroundTimeout()");
            TimeoutRecord forServiceStartWithEndTime = TimeoutRecord.forServiceStartWithEndTime("Context.startForegroundService() did not then call Service.startForeground(): " + serviceRecord, SystemClock.uptimeMillis());
            forServiceStartWithEndTime.mLatencyTracker.waitingOnAMSLockStarted();
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    forServiceStartWithEndTime.mLatencyTracker.waitingOnAMSLockEnded();
                    if (serviceRecord.fgRequired && serviceRecord.fgWaiting && !serviceRecord.destroying) {
                        ProcessRecord processRecord = serviceRecord.app;
                        if (processRecord != null && processRecord.mDebugging) {
                            this.mServiceFGAnrTimer.discard();
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                        this.mServiceFGAnrTimer.mFeature.getClass();
                        serviceRecord.fgWaiting = false;
                        stopServiceLocked(serviceRecord, false);
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        if (processRecord != null) {
                            Message obtainMessage = this.mAm.mHandler.obtainMessage(67);
                            SomeArgs obtain = SomeArgs.obtain();
                            obtain.arg1 = processRecord;
                            obtain.arg2 = forServiceStartWithEndTime;
                            obtainMessage.obj = obtain;
                            this.mAm.mHandler.sendMessageDelayed(obtainMessage, r6.mConstants.mServiceStartForegroundAnrDelayMs);
                        }
                        return;
                    }
                    this.mServiceFGAnrTimer.discard();
                    ActivityManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public final void serviceProcessGoneLocked(ServiceRecord serviceRecord, boolean z) {
        if (serviceRecord.tracker != null) {
            synchronized (this.mAm.mProcessStats.mLock) {
                int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                long uptimeMillis = SystemClock.uptimeMillis();
                serviceRecord.tracker.setExecuting(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setForeground(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setBound(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setStarted(false, memFactorLocked, uptimeMillis);
            }
        }
        serviceDoneExecutingLocked(serviceRecord, true, true, z, 12);
    }

    public final void serviceTimeout(ProcessRecord processRecord) {
        TimeoutRecord timeoutRecord;
        ServiceRecord serviceRecord;
        try {
            Trace.traceBegin(64L, "serviceTimeout()");
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (processRecord.mDebugging) {
                        this.mActiveServiceAnrTimer.discard();
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        Trace.traceEnd(64L);
                        return;
                    }
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processServiceRecord.mExecutingServices.size() != 0 && processRecord.mThread != null && !processRecord.mKilled) {
                        long uptimeMillis = SystemClock.uptimeMillis();
                        long j = uptimeMillis - (processServiceRecord.mExecServicesFg ? this.mAm.mConstants.SERVICE_TIMEOUT : this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT);
                        int size = processServiceRecord.mExecutingServices.size() - 1;
                        long j2 = 0;
                        while (true) {
                            timeoutRecord = null;
                            if (size < 0) {
                                serviceRecord = null;
                                break;
                            }
                            serviceRecord = (ServiceRecord) processServiceRecord.mExecutingServices.valueAt(size);
                            long j3 = serviceRecord.executingStart;
                            if (j3 < j) {
                                break;
                            }
                            if (j3 > j2) {
                                j2 = j3;
                            }
                            size--;
                        }
                        if (serviceRecord == null || !this.mAm.mProcessList.mLruProcesses.contains(processRecord)) {
                            this.mActiveServiceAnrTimer.discard();
                            this.mActiveServiceAnrTimer.start(processServiceRecord.mExecServicesFg ? j2 + this.mAm.mConstants.SERVICE_TIMEOUT : (j2 + this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT) - SystemClock.uptimeMillis(), processRecord);
                        } else {
                            this.mActiveServiceAnrTimer.mFeature.getClass();
                            Slog.w("ActivityManager", "Timeout executing service: " + serviceRecord);
                            StringWriter stringWriter = new StringWriter();
                            FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 1024);
                            fastPrintWriter.println(serviceRecord);
                            serviceRecord.dump((PrintWriter) fastPrintWriter, "    ");
                            fastPrintWriter.close();
                            this.mLastAnrDump = stringWriter.toString();
                            this.mAm.mHandler.removeCallbacks(this.mLastAnrDumpClearer);
                            this.mAm.mHandler.postDelayed(this.mLastAnrDumpClearer, 7200000L);
                            timeoutRecord = TimeoutRecord.forServiceExec(serviceRecord.shortInstanceName, uptimeMillis - serviceRecord.executingStart).setExpiredTimer((AutoCloseable) null);
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        if (timeoutRecord != null) {
                            this.mAm.mAnrHelper.appNotResponding(processRecord, timeoutRecord);
                        }
                        return;
                    }
                    this.mActiveServiceAnrTimer.discard();
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    Trace.traceEnd(64L);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public final void setFgsRestrictionLocked(String str, int i, int i2, Intent intent, ServiceRecord serviceRecord, BackgroundStartPrivileges backgroundStartPrivileges, boolean z, boolean z2) {
        int i3;
        int i4;
        String str2;
        int i5;
        String str3;
        int i6;
        if (z) {
            i3 = serviceRecord.mAllowWiu_inBindService;
            i4 = serviceRecord.mAllowStart_inBindService;
        } else {
            i3 = serviceRecord.mAllowWiu_noBinding;
            i4 = serviceRecord.mAllowStart_noBinding;
        }
        int i7 = i3;
        int i8 = i4;
        ActivityManagerService activityManagerService = this.mAm;
        if (i7 == -1 || i8 == -1) {
            int shouldAllowFgsWhileInUsePermissionLocked = shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, serviceRecord.app, backgroundStartPrivileges);
            int i9 = i7 == -1 ? shouldAllowFgsWhileInUsePermissionLocked : i7;
            if (i8 == -1) {
                ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP = activityManagerService.isAllowlistedForFgsStartLOSP(i2);
                serviceRecord.mInfoTempFgsAllowListReason = isAllowlistedForFgsStartLOSP;
                int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked, i, i2, str, serviceRecord, backgroundStartPrivileges);
                if (shouldAllowFgsStartForegroundNoBindingCheckLocked == -1) {
                    str2 = canBindingClientStartFgsLocked(i2);
                    if (str2 != null) {
                        shouldAllowFgsStartForegroundNoBindingCheckLocked = 54;
                    }
                } else {
                    str2 = null;
                }
                int uidProcStateLOSP = activityManagerService.mProcessList.getUidProcStateLOSP(i2);
                try {
                    i5 = activityManagerService.mContext.getPackageManager().getTargetSdkVersion(str);
                } catch (PackageManager.NameNotFoundException unused) {
                    i5 = -1;
                }
                boolean z3 = (activityManagerService.mProcessList.getUidProcessCapabilityLOSP(i2) & 16) != 0;
                StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i2, "[callingPackage: ", str, "; callingUid: ", "; uidState: ");
                m.append(ActivityManager.procStateToString(uidProcStateLOSP));
                m.append("; uidBFSL: ");
                m.append(z3 ? "[BFSL]" : "n/a");
                m.append("; intent: ");
                m.append(intent);
                m.append("; code:");
                m.append(PowerExemptionManager.reasonCodeToString(shouldAllowFgsStartForegroundNoBindingCheckLocked));
                m.append("; tempAllowListReason:<");
                if (isAllowlistedForFgsStartLOSP == null) {
                    str3 = null;
                } else {
                    str3 = isAllowlistedForFgsStartLOSP.mReason + ",reasonCode:" + PowerExemptionManager.reasonCodeToString(isAllowlistedForFgsStartLOSP.mReasonCode) + ",duration:" + isAllowlistedForFgsStartLOSP.mDuration + ",callingUid:" + isAllowlistedForFgsStartLOSP.mCallingUid;
                }
                m.append(str3);
                m.append(">; targetSdkVersion:");
                ServiceKeeper$$ExternalSyntheticOutline0.m(serviceRecord.appInfo.targetSdkVersion, i5, "; callerTargetSdkVersion:", "; startForegroundCount:", m);
                AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(serviceRecord.mStartForegroundCount, "; bindFromPackage:", str2, ": isBindService:", m);
                String m2 = OptionalBool$$ExternalSyntheticOutline0.m("]", m, z);
                if (!m2.equals(serviceRecord.mInfoAllowStartForeground)) {
                    serviceRecord.mLoggedInfoAllowStartForeground = false;
                    serviceRecord.mInfoAllowStartForeground = m2;
                }
                i8 = shouldAllowFgsStartForegroundNoBindingCheckLocked;
            }
            i7 = i9;
        }
        if (z) {
            serviceRecord.mAllowWiu_inBindService = i7;
            serviceRecord.mAllowStart_inBindService = i8;
            return;
        }
        if (z2) {
            i6 = -1;
            if (serviceRecord.mAllowWiu_byBindings == -1) {
                serviceRecord.mAllowWiu_byBindings = i7;
            }
            if (serviceRecord.mAllowStart_byBindings == -1) {
                serviceRecord.mAllowStart_byBindings = i8;
            }
        } else {
            serviceRecord.mAllowWiu_noBinding = i7;
            serviceRecord.mAllowStart_noBinding = i8;
            i6 = -1;
        }
        if (serviceRecord.mAllowWiu_byBindings == i6) {
            Integer num = (Integer) activityManagerService.mProcessList.searchEachLruProcessesLOSP(new ActiveServices$$ExternalSyntheticLambda2(this, i2, new ArraySet(), 1), false);
            serviceRecord.mAllowWiu_byBindings = num == null ? -1 : num.intValue();
        }
        if (serviceRecord.mAllowStart_byBindings == -1) {
            serviceRecord.mAllowStart_byBindings = serviceRecord.mAllowWiu_byBindings;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:203:0x0278, code lost:
    
        r5 = false;
        r1 = true;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0519  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0599 A[Catch: all -> 0x017d, TryCatch #10 {all -> 0x017d, blocks: (B:42:0x0156, B:47:0x0175, B:48:0x017c, B:49:0x0180, B:318:0x01be, B:53:0x01cf, B:82:0x0480, B:84:0x0484, B:85:0x04a3, B:87:0x04a9, B:88:0x04bc, B:90:0x04c1, B:93:0x04cc, B:96:0x04dd, B:101:0x051b, B:102:0x0556, B:106:0x0599, B:108:0x059f, B:109:0x05a4, B:111:0x05b4, B:113:0x05c6, B:115:0x05dd, B:117:0x05e1, B:120:0x05e9, B:122:0x05ed, B:123:0x0601, B:125:0x0621, B:126:0x0627, B:133:0x0646, B:137:0x0685, B:138:0x0691, B:142:0x069c, B:146:0x06a2, B:153:0x0643, B:155:0x06a3, B:157:0x06b0, B:158:0x06b3, B:160:0x06d3, B:162:0x06d9, B:181:0x0568, B:182:0x0580, B:183:0x0526, B:186:0x0532, B:188:0x053b, B:190:0x054d, B:197:0x050d, B:198:0x0512, B:237:0x040f, B:239:0x0422, B:241:0x043f, B:244:0x043c, B:250:0x0458, B:140:0x0692, B:141:0x069b, B:128:0x0628, B:130:0x062e, B:131:0x0640), top: B:41:0x0156, inners: #0, #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x06e0  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0706  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0870 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0581  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x072a  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0750  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x03c6 A[Catch: all -> 0x0313, TryCatch #8 {all -> 0x0313, blocks: (B:267:0x02f6, B:269:0x0304, B:270:0x0317, B:272:0x0323, B:274:0x033f, B:278:0x034c, B:282:0x035a, B:284:0x0370, B:285:0x0387, B:286:0x038c, B:289:0x038f, B:229:0x03c2, B:231:0x03c6, B:233:0x03ca, B:235:0x03dc), top: B:226:0x02a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0589  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0198 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01d9  */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v61 */
    /* JADX WARN: Type inference failed for: r0v62 */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.server.am.ActiveServices] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v27 */
    /* JADX WARN: Type inference failed for: r10v28 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [com.android.server.am.ActiveServices] */
    /* JADX WARN: Type inference failed for: r1v55, types: [com.android.server.am.ActivityManagerService] */
    /* JADX WARN: Type inference failed for: r49v0, types: [com.android.server.am.ServiceRecord, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setServiceForegroundInnerLocked(com.android.server.am.ServiceRecord r49, int r50, android.app.Notification r51, int r52, int r53, int r54) {
        /*
            Method dump skipped, instructions count: 2161
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.setServiceForegroundInnerLocked(com.android.server.am.ServiceRecord, int, android.app.Notification, int, int, int):void");
    }

    public final void setServiceForegroundLocked(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) {
        int callingUserId = UserHandle.getCallingUserId();
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        int callingUid = Binder.getCallingUid();
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord serviceRecord = (ServiceRecord) getServiceMapLocked(callingUserId).mServicesByInstanceName.get(componentName);
            if (serviceRecord != iBinder) {
                serviceRecord = null;
            }
            ServiceRecord serviceRecord2 = serviceRecord;
            if (serviceRecord2 != null) {
                setServiceForegroundInnerLocked(serviceRecord2, i, notification, i2, i3, callingUid);
            }
        } finally {
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setServiceRestartBackoffEnabledLocked(String str, boolean z) {
        if (z) {
            this.mRestartBackoffDisabledPackages.remove(str);
            return;
        }
        if (this.mRestartBackoffDisabledPackages.contains(str)) {
            return;
        }
        this.mRestartBackoffDisabledPackages.add(str);
        long uptimeMillis = SystemClock.uptimeMillis();
        int size = this.mRestartingServices.size();
        for (int i = 0; i < size; i++) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mRestartingServices.get(i);
            if (TextUtils.equals(serviceRecord.packageName, str)) {
                long j = serviceRecord.nextRestartTime - uptimeMillis;
                long j2 = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                if (j > j2) {
                    serviceRecord.restartDelay = j2;
                    serviceRecord.nextRestartTime = j2 + uptimeMillis;
                    performScheduleRestartLocked(serviceRecord, "Rescheduling", "shell", uptimeMillis);
                }
            }
            Collections.sort(this.mRestartingServices, new ActiveServices$$ExternalSyntheticLambda1());
        }
    }

    public final boolean shouldAllowBootCompletedStart(int i, ServiceRecord serviceRecord) {
        int fgsAllowStart = serviceRecord.getFgsAllowStart();
        Flags.fgsBootCompleted();
        return (CompatChanges.isChangeEnabled(296558535L, serviceRecord.appInfo.uid) && fgsAllowStart == 200 && (this.mAm.mConstants.FGS_BOOT_COMPLETED_ALLOWLIST & i) == 0) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00d4, code lost:
    
        if (com.android.server.am.ActivityManagerService.checkComponentPermission(r10, r11, "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND", 0, -1, true) == 0) goto L48;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int shouldAllowFgsStartForegroundNoBindingCheckLocked(int r9, int r10, final int r11, java.lang.String r12, com.android.server.am.ServiceRecord r13, android.app.BackgroundStartPrivileges r14) {
        /*
            Method dump skipped, instructions count: 382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.shouldAllowFgsStartForegroundNoBindingCheckLocked(int, int, int, java.lang.String, com.android.server.am.ServiceRecord, android.app.BackgroundStartPrivileges):int");
    }

    public final int shouldAllowFgsWhileInUsePermissionLocked(String str, int i, final int i2, ProcessRecord processRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        ActiveInstrumentation activeInstrumentation;
        Integer num;
        int appId;
        ActivityManagerService activityManagerService = this.mAm;
        int uidProcStateLOSP = activityManagerService.mProcessList.getUidProcStateLOSP(i2);
        int reasonCodeFromProcState = uidProcStateLOSP <= 2 ? PowerExemptionManager.getReasonCodeFromProcState(uidProcStateLOSP) : -1;
        if (reasonCodeFromProcState == -1 && ActivityTaskManagerService.this.hasActiveVisibleWindow(i2)) {
            reasonCodeFromProcState = 50;
        }
        if (reasonCodeFromProcState == -1 && backgroundStartPrivileges.allowsBackgroundActivityStarts()) {
            reasonCodeFromProcState = 53;
        }
        if (reasonCodeFromProcState == -1 && ((appId = UserHandle.getAppId(i2)) == 0 || appId == 1000 || appId == 1027 || appId == 2000)) {
            reasonCodeFromProcState = 51;
        }
        if (reasonCodeFromProcState == -1 && (num = (Integer) activityManagerService.mProcessList.searchEachLruProcessesLOSP(new Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ProcessRecord processRecord2 = (ProcessRecord) obj;
                if (processRecord2.uid == i2) {
                    WindowProcessController windowProcessController = processRecord2.mWindowProcessController;
                    if (windowProcessController.areBackgroundActivityStartsAllowed(windowProcessController.mAtm.mAppSwitchesState, true).allows()) {
                        return 52;
                    }
                }
                return null;
            }
        }, false)) != null) {
            reasonCodeFromProcState = num.intValue();
        }
        if (reasonCodeFromProcState == -1 && activityManagerService.mInternal.isTempAllowlistedForFgsWhileInUse(i2)) {
            return 70;
        }
        if (reasonCodeFromProcState == -1 && processRecord != null && (activeInstrumentation = processRecord.mInstr) != null && activeInstrumentation.mHasBackgroundActivityStartsPermission) {
            reasonCodeFromProcState = 60;
        }
        if (reasonCodeFromProcState == -1 && activityManagerService.checkPermissionForDevice("android.permission.START_ACTIVITIES_FROM_BACKGROUND", i, i2, 0) == 0) {
            reasonCodeFromProcState = 58;
        }
        if (reasonCodeFromProcState == -1) {
            if (!((i2 == 0 || i2 == 1000) ? true : ((PackageManagerService.PackageManagerInternalImpl) activityManagerService.getPackageManagerInternal()).isSameApp(i2, UserHandle.getUserId(i2), 0L, str))) {
                EventLog.writeEvent(1397638484, "215003903", Integer.valueOf(i2), SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, "callingPackage:", str, " does not belong to callingUid:"));
            } else if (this.mAllowListWhileInUsePermissionInFgs.contains(str)) {
                reasonCodeFromProcState = 65;
            }
        }
        if (reasonCodeFromProcState == -1 && activityManagerService.mInternal.isDeviceOwner(i2)) {
            return 55;
        }
        return reasonCodeFromProcState;
    }

    public final boolean shouldServiceTimeOutLocked(ComponentName componentName, IBinder iBinder) {
        int callingUserId = UserHandle.getCallingUserId();
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ServiceRecord serviceRecord = (ServiceRecord) getServiceMapLocked(callingUserId).mServicesByInstanceName.get(componentName);
            if (serviceRecord != iBinder) {
                serviceRecord = null;
            }
            if (serviceRecord == null) {
                activityManagerService.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            ServiceRecord.ShortFgsInfo shortFgsInfo = serviceRecord.mShortFgsInfo;
            return serviceRecord.shouldTriggerShortFgsTimedEvent(shortFgsInfo == null ? 0L : shortFgsInfo.getTimeoutTime(), uptimeMillis);
        } finally {
            activityManagerService.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void showFgsBgRestrictedNotificationLocked(ServiceRecord serviceRecord) {
        if (this.mAm.mConstants.mFgsStartRestrictionNotificationEnabled) {
            Context context = this.mAm.mContext;
            String str = "App restricted: " + serviceRecord.mRecentCallingPackage;
            long currentTimeMillis = System.currentTimeMillis();
            ((NotificationManager) context.getSystemService(NotificationManager.class)).notifyAsUser(Long.toString(currentTimeMillis), 61, new Notification.Builder(context, SystemNotificationChannels.ALERTS).setGroup("com.android.fgs-bg-restricted").setSmallIcon(17304523).setWhen(0L).setColor(context.getColor(R.color.system_notification_accent_color)).setTicker("Foreground Service BG-Launch Restricted").setContentTitle("Foreground Service BG-Launch Restricted").setContentText(str).setStyle(new Notification.BigTextStyle().bigText(DATE_FORMATTER.format(Long.valueOf(currentTimeMillis)) + " " + serviceRecord.mInfoAllowStartForeground)).build(), UserHandle.ALL);
        }
    }

    public final void signalForegroundServiceObserversLocked(ServiceRecord serviceRecord) {
        int beginBroadcast = this.mFgsObservers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mFgsObservers.getBroadcastItem(i).onForegroundStateChanged(serviceRecord, serviceRecord.appInfo.packageName, serviceRecord.userId, serviceRecord.isForeground);
            } catch (RemoteException unused) {
            }
        }
        this.mFgsObservers.finishBroadcast();
    }

    public final boolean startForegroundServiceDelegateLocked(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, final ServiceConnection serviceConnection) {
        ProcessRecord processRecord;
        IApplicationThread iApplicationThread;
        ProcessRecord processRecord2;
        ServiceRecord serviceRecord;
        Notification notification;
        Slog.v("ActivityManager", "startForegroundServiceDelegateLocked " + foregroundServiceDelegationOptions.getDescription());
        final ComponentName componentName = foregroundServiceDelegationOptions.getComponentName();
        for (int size = this.mFgsDelegations.size() - 1; size >= 0; size--) {
            if (((ForegroundServiceDelegation) this.mFgsDelegations.keyAt(size)).mOptions.isSameDelegate(foregroundServiceDelegationOptions)) {
                Slog.e("ActivityManager", "startForegroundServiceDelegate " + foregroundServiceDelegationOptions.getDescription() + " already exists, multiple connections are not allowed");
                return false;
            }
        }
        int i = foregroundServiceDelegationOptions.mClientPid;
        int i2 = foregroundServiceDelegationOptions.mClientUid;
        int userId = UserHandle.getUserId(i2);
        String str = foregroundServiceDelegationOptions.mClientPackageName;
        if (!canStartForegroundServiceLocked(i, i2, str)) {
            Slog.d("ActivityManager", "startForegroundServiceDelegateLocked aborted, app is in the background");
            return false;
        }
        IApplicationThread iApplicationThread2 = foregroundServiceDelegationOptions.mClientAppThread;
        if (iApplicationThread2 != null) {
            processRecord2 = this.mAm.getRecordForAppLOSP(iApplicationThread2);
        } else {
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord = this.mAm.mPidsSelfLocked.get(i);
                iApplicationThread = processRecord.mThread;
            }
            processRecord2 = processRecord;
            iApplicationThread2 = iApplicationThread;
        }
        if (processRecord2 == null) {
            throw new SecurityException("Unable to find app for caller " + iApplicationThread2 + " (pid=" + i + ") when startForegroundServiceDelegateLocked " + componentName);
        }
        Intent intent = new Intent();
        intent.setComponent(componentName);
        ProcessRecord processRecord3 = processRecord2;
        ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, null, false, -1, null, null, str, i, i2, userId, true, false, false, false, foregroundServiceDelegationOptions, false, false, false);
        if (retrieveServiceLocked == null || (serviceRecord = retrieveServiceLocked.record) == null) {
            Slog.d("ActivityManager", "startForegroundServiceDelegateLocked retrieveServiceLocked returns null");
            return false;
        }
        serviceRecord.setProcess(processRecord3);
        serviceRecord.mIsFgsDelegate = true;
        final ForegroundServiceDelegation foregroundServiceDelegation = new ForegroundServiceDelegation(foregroundServiceDelegationOptions, serviceConnection);
        serviceRecord.mFgsDelegation = foregroundServiceDelegation;
        this.mFgsDelegations.put(foregroundServiceDelegation, serviceRecord);
        serviceRecord.isForeground = true;
        serviceRecord.mFgsEnterTime = SystemClock.uptimeMillis();
        serviceRecord.foregroundServiceType = foregroundServiceDelegationOptions.mForegroundServiceTypes;
        serviceRecord.updateOomAdjSeq();
        setFgsRestrictionLocked(str, i, i2, intent, serviceRecord, BackgroundStartPrivileges.NONE, false, false);
        ProcessServiceRecord processServiceRecord = processRecord3.mServices;
        processServiceRecord.startService(serviceRecord);
        updateServiceForegroundLocked(processServiceRecord, true);
        synchronized (this.mAm.mProcessStats.mLock) {
            try {
                ServiceState tracker = serviceRecord.getTracker();
                if (tracker != null) {
                    tracker.setForeground(true, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mAm.mBatteryStatsService.noteServiceStartRunning(i2, str, componentName.getClassName());
        AppOpsService appOpsService = this.mAm.mAppOpsService;
        appOpsService.startOperation(AppOpsManager.getToken(appOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null, true, false, null, false, 0, -1);
        registerAppOpCallbackLocked(serviceRecord);
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceStart(serviceRecord.appInfo.uid, serviceRecord);
        }
        logFGSStateChangeLocked(serviceRecord, 1, 0, 0, 0, 4, false);
        if (serviceConnection != null) {
            this.mAm.mHandler.post(new Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    serviceConnection.onServiceConnected(componentName, foregroundServiceDelegation.mBinder);
                }
            });
        }
        signalForegroundServiceObserversLocked(serviceRecord);
        if (serviceRecord.foregroundId != 0 && (notification = serviceRecord.foregroundNoti) != null) {
            notification.flags |= 64;
            serviceRecord.postNotification(true);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName startServiceInnerLocked(com.android.server.am.ActiveServices.ServiceMap r31, android.content.Intent r32, com.android.server.am.ServiceRecord r33, boolean r34, boolean r35, int r36, java.lang.String r37, int r38, boolean r39, java.lang.String r40) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.startServiceInnerLocked(com.android.server.am.ActiveServices$ServiceMap, android.content.Intent, com.android.server.am.ServiceRecord, boolean, boolean, int, java.lang.String, int, boolean, java.lang.String):android.content.ComponentName");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x016f, code lost:
    
        if (r3 >= 10) goto L81;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0092  */
    /* JADX WARN: Type inference failed for: r1v31, types: [com.android.server.am.ServiceRecord$$ExternalSyntheticLambda0] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName startServiceInnerLocked(final com.android.server.am.ServiceRecord r31, android.content.Intent r32, int r33, int r34, java.lang.String r35, int r36, boolean r37, boolean r38, android.app.BackgroundStartPrivileges r39, java.lang.String r40) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.startServiceInnerLocked(com.android.server.am.ServiceRecord, android.content.Intent, int, int, java.lang.String, int, boolean, boolean, android.app.BackgroundStartPrivileges, java.lang.String):android.content.ComponentName");
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName startServiceLocked(android.app.IApplicationThread r28, android.content.Intent r29, java.lang.String r30, int r31, int r32, boolean r33, java.lang.String r34, java.lang.String r35, int r36, android.app.BackgroundStartPrivileges r37, boolean r38, int r39, java.lang.String r40, java.lang.String r41) {
        /*
            Method dump skipped, instructions count: 878
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.startServiceLocked(android.app.IApplicationThread, android.content.Intent, java.lang.String, int, int, boolean, java.lang.String, java.lang.String, int, android.app.BackgroundStartPrivileges, boolean, int, java.lang.String, java.lang.String):android.content.ComponentName");
    }

    public final void stopAllForegroundServicesLocked(int i, String str) {
        ServiceMap serviceMapLocked = getServiceMapLocked(UserHandle.getUserId(i));
        int size = serviceMapLocked.mServicesByInstanceName.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            ServiceRecord serviceRecord = (ServiceRecord) serviceMapLocked.mServicesByInstanceName.valueAt(i2);
            ServiceInfo serviceInfo = serviceRecord.serviceInfo;
            if ((i == serviceInfo.applicationInfo.uid || str.equals(serviceInfo.packageName)) && serviceRecord.isForeground && serviceRecord.mAllowStartForegroundAtEntering != 301 && !isDeviceProvisioningPackage(serviceRecord.packageName)) {
                arrayList.add(serviceRecord);
            }
        }
        int size2 = arrayList.size();
        for (int i3 = 0; i3 < size2; i3++) {
            setServiceForegroundInnerLocked((ServiceRecord) arrayList.get(i3), 0, null, 0, 0, 0);
        }
    }

    public final void stopForegroundServiceDelegateLocked(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions) {
        ServiceRecord serviceRecord;
        int size = this.mFgsDelegations.size();
        while (true) {
            size--;
            if (size < 0) {
                serviceRecord = null;
                break;
            } else if (((ForegroundServiceDelegation) this.mFgsDelegations.keyAt(size)).mOptions.isSameDelegate(foregroundServiceDelegationOptions)) {
                Slog.d("ActivityManager", "stopForegroundServiceDelegateLocked " + foregroundServiceDelegationOptions.getDescription());
                serviceRecord = (ServiceRecord) this.mFgsDelegations.valueAt(size);
                break;
            }
        }
        if (serviceRecord != null) {
            serviceRecord.updateOomAdjSeq();
            bringDownServiceLocked(serviceRecord, false);
        } else {
            Slog.e("ActivityManager", "stopForegroundServiceDelegateLocked delegate does not exist " + foregroundServiceDelegationOptions.getDescription());
        }
    }

    public final void stopForegroundServiceDelegateLocked(ServiceConnection serviceConnection) {
        ServiceRecord serviceRecord;
        int size = this.mFgsDelegations.size();
        while (true) {
            size--;
            if (size < 0) {
                serviceRecord = null;
                break;
            }
            ForegroundServiceDelegation foregroundServiceDelegation = (ForegroundServiceDelegation) this.mFgsDelegations.keyAt(size);
            if (foregroundServiceDelegation.mConnection == serviceConnection) {
                Slog.d("ActivityManager", "stopForegroundServiceDelegateLocked " + foregroundServiceDelegation.mOptions.getDescription());
                serviceRecord = (ServiceRecord) this.mFgsDelegations.valueAt(size);
                break;
            }
        }
        if (serviceRecord == null) {
            Slog.e("ActivityManager", "stopForegroundServiceDelegateLocked delegate does not exist");
        } else {
            serviceRecord.updateOomAdjSeq();
            bringDownServiceLocked(serviceRecord, false);
        }
    }

    public final void stopInBackgroundLocked(int i) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(UserHandle.getUserId(i));
        if (serviceMap != null) {
            int size = serviceMap.mServicesByInstanceName.size() - 1;
            ArrayList arrayList = null;
            while (true) {
                boolean z = false;
                if (size < 0) {
                    break;
                }
                ServiceRecord serviceRecord = (ServiceRecord) serviceMap.mServicesByInstanceName.valueAt(size);
                ApplicationInfo applicationInfo = serviceRecord.appInfo;
                int i2 = applicationInfo.uid;
                if (i2 == i && serviceRecord.startRequested) {
                    if (this.mAm.getAppStartModeLOSP(i2, applicationInfo.targetSdkVersion, -1, serviceRecord.packageName, false, false, false) != 0) {
                        String str = serviceRecord.packageName;
                        int i3 = serviceRecord.userId;
                        if (str != null) {
                            try {
                                IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
                                if (asInterface != null) {
                                    z = asInterface.isApplicationForceStopDisabled(str, i3, (String) null, (String) null, (String) null, false);
                                }
                            } catch (RemoteException unused) {
                            }
                        }
                        if (z) {
                            Slog.d("ActivityManager", "cannot stop service due to MDM policy restriction");
                        } else {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            String str2 = serviceRecord.shortInstanceName;
                            EventLog.writeEvent(30056, Integer.valueOf(serviceRecord.appInfo.uid), str2);
                            StringBuilder sb = new StringBuilder(64);
                            sb.append("Stopping service due to app idle: ");
                            UserHandle.formatUid(sb, serviceRecord.appInfo.uid);
                            sb.append(" ");
                            TimeUtils.formatDuration(serviceRecord.createRealTime - SystemClock.elapsedRealtime(), sb);
                            sb.append(" ");
                            sb.append(str2);
                            Slog.w("ActivityManager", sb.toString());
                            arrayList.add(serviceRecord);
                            if (appRestrictedAnyInBackground(serviceRecord.appInfo.uid, serviceRecord.packageName)) {
                                cancelForegroundNotificationLocked(serviceRecord);
                            }
                        }
                    }
                }
                size--;
            }
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i4 = size2 - 1; i4 >= 0; i4--) {
                    ServiceRecord serviceRecord2 = (ServiceRecord) arrayList.get(i4);
                    serviceRecord2.delayed = false;
                    serviceMap.ensureNotStartingBackgroundLocked(serviceRecord2);
                    stopServiceLocked(serviceRecord2, true);
                }
                if (size2 > 0) {
                    this.mAm.updateOomAdjPendingTargetsLocked(18);
                }
            }
        }
    }

    public final void stopServiceAndUpdateAllowlistManagerLocked(ServiceRecord serviceRecord) {
        maybeStopShortFgsTimeoutLocked(serviceRecord);
        ProcessServiceRecord processServiceRecord = serviceRecord.app.mServices;
        processServiceRecord.stopService(serviceRecord);
        processServiceRecord.updateBoundClientUids();
        if (serviceRecord.allowlistManager) {
            updateAllowlistManagerLocked(processServiceRecord);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x009b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int stopServiceLocked(android.app.IApplicationThread r22, android.content.Intent r23, java.lang.String r24, int r25, boolean r26, int r27, java.lang.String r28, java.lang.String r29) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            com.android.server.am.ActivityManagerService r11 = r0.mAm
            com.android.server.am.ProcessRecord r2 = r11.getRecordForAppLOSP(r1)
            if (r1 == 0) goto Le
            if (r2 == 0) goto L11
        Le:
            r3 = r23
            goto L40
        L11:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Unable to find app for caller "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r1 = " (pid="
            r2.append(r1)
            com.android.server.am.ActivityManagerService$Injector r1 = r11.mInjector
            r1.getClass()
            int r1 = android.os.Binder.getCallingPid()
            r2.append(r1)
            java.lang.String r1 = ") when stopping service "
            r2.append(r1)
            r3 = r23
            r2.append(r3)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L40:
            com.android.server.am.ActivityManagerService$Injector r1 = r11.mInjector
            r1.getClass()
            int r9 = android.os.Binder.getCallingPid()
            com.android.server.am.ActivityManagerService$Injector r1 = r11.mInjector
            r1.getClass()
            int r10 = android.os.Binder.getCallingUid()
            r17 = 0
            r18 = 0
            r8 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r19 = 0
            r1 = r21
            r2 = r23
            r3 = r29
            r4 = r26
            r5 = r27
            r6 = r28
            r7 = r24
            r20 = r11
            r11 = r25
            com.android.server.am.ActiveServices$ServiceLookupResult r1 = r1.retrieveServiceLocked(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            r2 = 0
            if (r1 == 0) goto Lc1
            com.android.server.am.ServiceRecord r1 = r1.record
            if (r1 == 0) goto Lbf
            java.lang.String r4 = r1.packageName
            if (r4 == 0) goto L98
            java.lang.String r3 = "application_policy"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r3)     // Catch: android.os.RemoteException -> L98
            com.samsung.android.knox.application.IApplicationPolicy r3 = com.samsung.android.knox.application.IApplicationPolicy.Stub.asInterface(r3)     // Catch: android.os.RemoteException -> L98
            if (r3 == 0) goto L98
            r7 = 0
            r8 = 0
            r9 = 0
            r6 = 0
            r5 = r25
            boolean r3 = r3.isApplicationForceStopDisabled(r4, r5, r6, r7, r8, r9)     // Catch: android.os.RemoteException -> L98
            goto L99
        L98:
            r3 = r2
        L99:
            if (r3 == 0) goto L9c
            return r2
        L9c:
            r3 = r20
            com.android.server.am.ActivityManagerService$Injector r4 = r3.mInjector
            r4.getClass()
            long r4 = android.os.Binder.clearCallingIdentity()
            r0.stopServiceLocked(r1, r2)     // Catch: java.lang.Throwable -> Lb4
            com.android.server.am.ActivityManagerService$Injector r0 = r3.mInjector
            r0.getClass()
            android.os.Binder.restoreCallingIdentity(r4)
            r0 = 1
            return r0
        Lb4:
            r0 = move-exception
            r1 = r0
            com.android.server.am.ActivityManagerService$Injector r0 = r3.mInjector
            r0.getClass()
            android.os.Binder.restoreCallingIdentity(r4)
            throw r1
        Lbf:
            r0 = -1
            return r0
        Lc1:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.stopServiceLocked(android.app.IApplicationThread, android.content.Intent, java.lang.String, int, boolean, int, java.lang.String, java.lang.String):int");
    }

    public final void stopServiceLocked(ServiceRecord serviceRecord, boolean z) {
        traceInstant("stopService(): ", serviceRecord);
        try {
            Trace.traceBegin(64L, "stopServiceLocked()");
            if (serviceRecord.delayed) {
                serviceRecord.delayedStop = true;
                return;
            }
            maybeStopShortFgsTimeoutLocked(serviceRecord);
            maybeStopFgsTimeoutLocked(serviceRecord);
            int i = serviceRecord.appInfo.uid;
            String packageName = serviceRecord.name.getPackageName();
            String className = serviceRecord.name.getClassName();
            FrameworkStatsLog.write(99, i, packageName, className, 2);
            this.mAm.mBatteryStatsService.noteServiceStopRunning(i, packageName, className);
            serviceRecord.startRequested = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
            serviceRecord.callStart = false;
            bringDownServiceIfNeededLocked(serviceRecord, false, false, z);
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public final boolean stopServiceTokenLocked(int i, IBinder iBinder, ComponentName componentName) {
        ServiceRecord serviceRecord = (ServiceRecord) getServiceMapLocked(UserHandle.getCallingUserId()).mServicesByInstanceName.get(componentName);
        if (serviceRecord != iBinder) {
            serviceRecord = null;
        }
        if (serviceRecord == null) {
            return false;
        }
        if (i >= 0) {
            ServiceRecord.StartItem findDeliveredStart = serviceRecord.findDeliveredStart(i, false, false);
            if (findDeliveredStart != null) {
                while (serviceRecord.deliveredStarts.size() > 0) {
                    ServiceRecord.StartItem startItem = (ServiceRecord.StartItem) serviceRecord.deliveredStarts.remove(0);
                    UriPermissionOwner uriPermissionOwner = startItem.uriPermissions;
                    if (uriPermissionOwner != null) {
                        uriPermissionOwner.removeUriPermission(3, -1, null, null);
                        startItem.uriPermissions = null;
                    }
                    if (startItem == findDeliveredStart) {
                        break;
                    }
                }
            }
            if (serviceRecord.lastStartId != i) {
                return false;
            }
            if (serviceRecord.deliveredStarts.size() > 0) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "stopServiceToken startId ", " is last, but have ");
                m.append(serviceRecord.deliveredStarts.size());
                m.append(" remaining args");
                Slog.w("ActivityManager", m.toString());
            }
        }
        maybeStopShortFgsTimeoutLocked(serviceRecord);
        maybeStopFgsTimeoutLocked(serviceRecord);
        int i2 = serviceRecord.appInfo.uid;
        String packageName = serviceRecord.name.getPackageName();
        String className = serviceRecord.name.getClassName();
        FrameworkStatsLog.write(99, i2, packageName, className, 2);
        this.mAm.mBatteryStatsService.noteServiceStopRunning(i2, packageName, className);
        serviceRecord.startRequested = false;
        if (serviceRecord.tracker != null) {
            synchronized (this.mAm.mProcessStats.mLock) {
                serviceRecord.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
            }
        }
        serviceRecord.callStart = false;
        this.mAm.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        bringDownServiceIfNeededLocked(serviceRecord, false, false, false);
        this.mAm.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    public final void tryScheduleServiceRestartLocked(ServiceRecord serviceRecord) {
        if (!scheduleServiceRestartLocked(serviceRecord, true)) {
            bringDownServiceLocked(serviceRecord, true);
            return;
        }
        if (serviceRecord.canStopIfKilled(false)) {
            serviceRecord.startRequested = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), SystemClock.uptimeMillis());
                }
            }
        }
    }

    public final void unbindFinishedLocked(ServiceRecord serviceRecord, Intent intent) {
        boolean z;
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (serviceRecord != null) {
            try {
                IntentBindRecord intentBindRecord = (IntentBindRecord) serviceRecord.bindings.get(new Intent.FilterComparison(intent));
                boolean contains = this.mDestroyingServices.contains(serviceRecord);
                if (intentBindRecord != null) {
                    if (intentBindRecord.apps.size() <= 0 || contains) {
                        intentBindRecord.doRebind = true;
                    } else {
                        int size = intentBindRecord.apps.size() - 1;
                        while (true) {
                            if (size >= 0) {
                                ProcessRecord processRecord = ((AppBindRecord) intentBindRecord.apps.valueAt(size)).client;
                                if (processRecord != null && processRecord.mState.mSetSchedGroup > 0) {
                                    z = true;
                                    break;
                                }
                                size--;
                            } else {
                                z = false;
                                break;
                            }
                        }
                        try {
                            requestServiceBindingLocked(serviceRecord, intentBindRecord, z, true, 0);
                        } catch (TransactionTooLargeException unused) {
                        }
                    }
                }
                Flags.serviceBindingOomAdjPolicy();
                serviceDoneExecutingLocked(serviceRecord, contains, false, false, serviceRecord.wasOomAdjUpdated() ? 5 : 0);
            } finally {
                activityManagerService.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean unbindServiceLocked(IServiceConnection iServiceConnection) {
        long j;
        String num;
        int i;
        IntentBindRecord intentBindRecord;
        Intent.FilterComparison filterComparison;
        IBinder asBinder = iServiceConnection.asBinder();
        ArrayList arrayList = (ArrayList) this.mServiceConnections.get(asBinder);
        int i2 = 0;
        if (arrayList == null) {
            Slog.w("ActivityManager", "Unbind failed: could not find connection for " + iServiceConnection.asBinder());
            return false;
        }
        this.mAm.mInjector.getClass();
        int callingPid = Binder.getCallingPid();
        this.mAm.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        long j2 = 64;
        try {
            if (Trace.isTagEnabled(64L)) {
                try {
                    if (arrayList.size() > 0) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(0);
                        num = connectionRecord.binding.service.shortInstanceName + " from " + connectionRecord.clientProcessName;
                    } else {
                        num = Integer.toString(callingPid);
                    }
                    Trace.traceBegin(64L, "unbindServiceLocked: " + num);
                } catch (Throwable th) {
                    th = th;
                    j = j2;
                    Trace.traceEnd(j);
                    this.mAm.mInjector.getClass();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            boolean z = false;
            while (arrayList.size() > 0) {
                ConnectionRecord connectionRecord2 = (ConnectionRecord) arrayList.get(i2);
                int removeConnectionLocked = removeConnectionLocked(connectionRecord2, null, null, true);
                if (arrayList.size() > 0) {
                    if (arrayList.get(i2) == connectionRecord2) {
                        Slog.wtf("ActivityManager", "Connection " + connectionRecord2 + " not removed for binder " + asBinder);
                        arrayList.remove(i2);
                    }
                }
                ProcessRecord processRecord = connectionRecord2.binding.service.app;
                if (processRecord != null) {
                    boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                    if (freecessController.mIsFreecessEnable) {
                        i = removeConnectionLocked;
                        freecessController.protectFreezePackage(processRecord.uid, "unbindService", 2000L);
                        ServiceRecord serviceRecord = connectionRecord2.binding.service;
                        freecessController.protectFreezePackage(serviceRecord.userId, serviceRecord.packageName, 2000L, "unbindService");
                    } else {
                        i = removeConnectionLocked;
                    }
                    if (MARsPolicyManager.MARs_ENABLE && (intentBindRecord = connectionRecord2.binding.intent) != null && (filterComparison = intentBindRecord.intent) != null && filterComparison.getIntent() != null) {
                        ProcessRecord processRecord2 = connectionRecord2.binding.client;
                        String str = (processRecord2 == null || processRecord2.info == null) ? null : connectionRecord2.binding.client.info.packageName;
                        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                        AppBindRecord appBindRecord = connectionRecord2.binding;
                        String str2 = appBindRecord.service.packageName;
                        String action = appBindRecord.intent.intent.getIntent().getAction();
                        int i3 = connectionRecord2.binding.service.userId;
                        mARsPolicyManager.getClass();
                        MARsPolicyManager.onSpecialUnBindServiceActions(i3, str2, action, str);
                    }
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processServiceRecord.mAllowlistManager) {
                        updateAllowlistManagerLocked(processServiceRecord);
                    }
                    if (connectionRecord2.hasFlag(134217728)) {
                        processServiceRecord.mTreatLikeActivity = true;
                        this.mAm.mProcessList.updateLruProcessLocked(processRecord, null, true);
                    }
                    if (i == 0) {
                        this.mAm.enqueueOomAdjTargetLocked(processRecord);
                        z = true;
                    }
                }
                i2 = 0;
                j2 = 64;
            }
            if (z) {
                this.mAm.updateOomAdjPendingTargetsLocked(5);
            }
            Trace.traceEnd(64L);
            this.mAm.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th2) {
            th = th2;
            j = 64;
        }
    }

    public final void unregisterAppOpCallbackLocked(ServiceRecord serviceRecord) {
        int i;
        int i2;
        int i3 = serviceRecord.appInfo.uid;
        AppOpCallback appOpCallback = (AppOpCallback) this.mFgsAppOpCallbacks.get(i3);
        if (appOpCallback != null) {
            int i4 = appOpCallback.mNumFgs - 1;
            appOpCallback.mNumFgs = i4;
            if (i4 <= 0) {
                appOpCallback.mDestroyed = true;
                synchronized (appOpCallback.mCounterLock) {
                    try {
                        int[] iArr = AppOpCallback.LOGGED_AP_OPS;
                        for (int i5 = 0; i5 < 4; i5++) {
                            int i6 = iArr[i5];
                            int i7 = appOpCallback.mAcceptedOps.get(i6);
                            int i8 = appOpCallback.mRejectedOps.get(i6);
                            if (i7 > 0 || i8 > 0) {
                                int i9 = appOpCallback.mProcessRecord.uid;
                                int i10 = appOpCallback.mAppOpModes.get(i6);
                                if (i10 != 0) {
                                    if (i10 == 1) {
                                        i2 = 2;
                                    } else if (i10 != 4) {
                                        i = 0;
                                    } else {
                                        i2 = 3;
                                    }
                                    i = i2;
                                } else {
                                    i = 1;
                                }
                                FrameworkStatsLog.write(256, i9, i6, i, i7, i8);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                appOpCallback.mAppOpsManager.stopWatchingNoted(appOpCallback.mOpNotedCallback);
                appOpCallback.mAppOpsManager.stopWatchingStarted(appOpCallback.mOpStartedCallback);
            }
            if (appOpCallback.mDestroyed) {
                this.mFgsAppOpCallbacks.remove(i3);
            }
        }
    }

    public final void unscheduleServiceRestartLocked(int i, ServiceRecord serviceRecord, String str, boolean z) {
        if (z || serviceRecord.restartDelay != 0) {
            boolean remove = this.mRestartingServices.remove(serviceRecord);
            if (remove || i != serviceRecord.appInfo.uid) {
                serviceRecord.restartCount = 0;
                serviceRecord.restartDelay = 0L;
                serviceRecord.restartTime = 0L;
                serviceRecord.mEarliestRestartTime = 0L;
                serviceRecord.mRestartSchedulingTime = 0L;
            }
            if (remove) {
                clearRestartingIfNeededLocked(serviceRecord);
                EventLog.writeEvent(1000102, Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, Long.valueOf(serviceRecord.restartDelay), str);
            }
            this.mAm.mHandler.removeCallbacks(serviceRecord.restarter);
        }
    }

    public final void unscheduleShortFgsTimeoutLocked(ServiceRecord serviceRecord) {
        this.mShortFGSAnrTimer.cancel(serviceRecord);
        ActivityManagerService activityManagerService = this.mAm;
        activityManagerService.mHandler.removeMessages(77, serviceRecord);
        activityManagerService.mHandler.removeMessages(76, serviceRecord);
    }

    public final void updateNumForegroundServicesLocked() {
        int i;
        int i2;
        AtomicReference atomicReference = sNumForegroundServices;
        ProcessList processList = this.mAm.mProcessList;
        ActivityManagerService activityManagerService = processList.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                int size = processList.mLruProcesses.size();
                i = 0;
                i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    ProcessServiceRecord processServiceRecord = ((ProcessRecord) processList.mLruProcesses.get(i3)).mServices;
                    int size2 = processServiceRecord.mServices.size();
                    int i4 = 0;
                    for (int i5 = 0; i5 < size2; i5++) {
                        if (((ServiceRecord) processServiceRecord.mServices.valueAt(i5)).isForeground) {
                            i4++;
                        }
                    }
                    if (i4 > 0) {
                        i += i4;
                        i2++;
                    }
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        atomicReference.set(new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public final void updateScreenStateLocked(boolean z) {
        if (this.mScreenOn != z) {
            this.mScreenOn = z;
            if (z) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                for (int size = this.mServiceMap.size() - 1; size >= 0; size--) {
                    ServiceMap serviceMap = (ServiceMap) this.mServiceMap.valueAt(size);
                    boolean z2 = false;
                    long j = Long.MAX_VALUE;
                    for (int size2 = serviceMap.mActiveForegroundApps.size() - 1; size2 >= 0; size2--) {
                        ActiveForegroundApp activeForegroundApp = (ActiveForegroundApp) serviceMap.mActiveForegroundApps.valueAt(size2);
                        if (activeForegroundApp.mEndTime != 0) {
                            if (!activeForegroundApp.mShownWhileScreenOn && activeForegroundApp.mStartVisibleTime == activeForegroundApp.mStartTime) {
                                activeForegroundApp.mStartVisibleTime = elapsedRealtime;
                                activeForegroundApp.mEndTime = elapsedRealtime;
                            }
                            if (foregroundAppShownEnoughLocked(activeForegroundApp, elapsedRealtime)) {
                                serviceMap.mActiveForegroundApps.remove(activeForegroundApp.mPackageName);
                                serviceMap.mActiveForegroundAppsChanged = true;
                                z2 = true;
                            } else {
                                long j2 = activeForegroundApp.mHideTime;
                                if (j2 < j) {
                                    j = j2;
                                }
                            }
                        } else if (!activeForegroundApp.mShownWhileScreenOn) {
                            activeForegroundApp.mShownWhileScreenOn = true;
                            activeForegroundApp.mStartVisibleTime = elapsedRealtime;
                        }
                    }
                    if (z2) {
                        requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
                    } else if (j < Long.MAX_VALUE) {
                        requestUpdateActiveForegroundAppsLocked(serviceMap, j);
                    }
                }
            }
        }
    }

    public final void updateServiceApplicationInfoLocked(ApplicationInfo applicationInfo) {
        ServiceMap serviceMap = (ServiceMap) this.mServiceMap.get(UserHandle.getUserId(applicationInfo.uid));
        if (serviceMap != null) {
            ArrayMap arrayMap = serviceMap.mServicesByInstanceName;
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                ServiceRecord serviceRecord = (ServiceRecord) arrayMap.valueAt(size);
                if (applicationInfo.packageName.equals(serviceRecord.appInfo.packageName)) {
                    serviceRecord.appInfo = applicationInfo;
                    serviceRecord.serviceInfo.applicationInfo = applicationInfo;
                }
            }
        }
    }

    public final void updateServiceClientActivitiesLocked(ProcessServiceRecord processServiceRecord, ConnectionRecord connectionRecord, boolean z) {
        ProcessRecord processRecord;
        if (connectionRecord == null || (processRecord = connectionRecord.binding.client) == null || processRecord.mWindowProcessController.mHasActivities) {
            boolean z2 = false;
            for (int size = processServiceRecord.mServices.size() - 1; size >= 0 && !z2; size--) {
                ArrayMap arrayMap = processServiceRecord.getRunningServiceAt(size).connections;
                for (int size2 = arrayMap.size() - 1; size2 >= 0 && !z2; size2--) {
                    ArrayList arrayList = (ArrayList) arrayMap.valueAt(size2);
                    int size3 = arrayList.size() - 1;
                    while (true) {
                        if (size3 < 0) {
                            break;
                        }
                        ProcessRecord processRecord2 = ((ConnectionRecord) arrayList.get(size3)).binding.client;
                        if (processRecord2 != null && processRecord2 != processServiceRecord.mApp && processRecord2.mWindowProcessController.mHasActivities) {
                            z2 = true;
                            break;
                        }
                        size3--;
                    }
                }
            }
            if (z2 != processServiceRecord.mHasClientActivities) {
                processServiceRecord.mHasClientActivities = z2;
                processServiceRecord.mApp.mWindowProcessController.mHasClientActivities = z2;
                if (z) {
                    this.mAm.updateLruProcessLocked(processServiceRecord.mApp, null, z2);
                }
            }
        }
    }

    public final void updateServiceConnectionActivitiesLocked(ProcessServiceRecord processServiceRecord) {
        ArraySet arraySet = null;
        for (int i = 0; i < processServiceRecord.mConnections.size(); i++) {
            ProcessRecord processRecord = processServiceRecord.getConnectionAt(i).binding.service.app;
            if (processRecord != null && processRecord != processServiceRecord.mApp) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                } else if (arraySet.contains(processRecord)) {
                }
                arraySet.add(processRecord);
                updateServiceClientActivitiesLocked(processRecord.mServices, null, false);
            }
        }
    }

    public final void updateServiceForegroundLocked(ProcessServiceRecord processServiceRecord, boolean z) {
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        for (int size = processServiceRecord.mServices.size() - 1; size >= 0; size--) {
            ServiceRecord runningServiceAt = processServiceRecord.getRunningServiceAt(size);
            if (runningServiceAt.isForeground || runningServiceAt.fgRequired) {
                int i2 = runningServiceAt.foregroundServiceType;
                i |= i2;
                if (i2 == 0) {
                    z2 = true;
                    z3 = true;
                } else {
                    z3 = true;
                }
            }
        }
        this.mAm.updateProcessForegroundLocked(i, processServiceRecord.mApp, z3, z2, z);
        processServiceRecord.mRepHasForegroundServices = z3;
    }

    public final void updateServiceGroupLocked(IServiceConnection iServiceConnection, int i, int i2) {
        ArrayList arrayList = (ArrayList) this.mServiceConnections.get(iServiceConnection.asBinder());
        if (arrayList == null) {
            throw new IllegalArgumentException("Could not find connection for " + iServiceConnection.asBinder());
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = ((ConnectionRecord) arrayList.get(size)).binding.service;
            if (serviceRecord != null && (serviceRecord.serviceInfo.flags & 2) != 0) {
                ProcessRecord processRecord = serviceRecord.app;
                if (processRecord != null) {
                    ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (i > 0) {
                        processServiceRecord.mConnectionService = serviceRecord;
                        processServiceRecord.mConnectionGroup = i;
                        processServiceRecord.mConnectionImportance = i2;
                    } else {
                        processServiceRecord.mConnectionService = null;
                        processServiceRecord.mConnectionGroup = 0;
                        processServiceRecord.mConnectionImportance = 0;
                    }
                } else if (i > 0) {
                    serviceRecord.pendingConnectionGroup = i;
                    serviceRecord.pendingConnectionImportance = i2;
                } else {
                    serviceRecord.pendingConnectionGroup = 0;
                    serviceRecord.pendingConnectionImportance = 0;
                }
            }
        }
    }

    public final Pair validateForegroundServiceType(ServiceRecord serviceRecord, int i, int i2, int i3) {
        Object obj;
        ForegroundServiceTypePolicy defaultPolicy = ForegroundServiceTypePolicy.getDefaultPolicy();
        ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = defaultPolicy.getForegroundServiceTypePolicyInfo(i, i2);
        Context context = this.mAm.mContext;
        String str = serviceRecord.packageName;
        ProcessRecord processRecord = serviceRecord.app;
        int checkForegroundServiceTypePolicy = defaultPolicy.checkForegroundServiceTypePolicy(context, str, processRecord.uid, processRecord.mPid, serviceRecord.getFgsAllowWiu_forStart() != -1, foregroundServiceTypePolicyInfo);
        if (checkForegroundServiceTypePolicy != 2) {
            if (checkForegroundServiceTypePolicy != 3) {
                if (checkForegroundServiceTypePolicy == 4) {
                    StringBuilder sb = new StringBuilder("Starting FGS with type ");
                    sb.append(ServiceInfo.foregroundServiceTypeToLabel(i));
                    sb.append(" code=");
                    sb.append(checkForegroundServiceTypePolicy);
                    sb.append(" callerApp=");
                    sb.append(serviceRecord.app);
                    sb.append(" targetSDK=");
                    sb.append(serviceRecord.app.info.targetSdkVersion);
                    sb.append(" requiredPermissions=");
                    sb.append(foregroundServiceTypePolicyInfo.toPermissionString());
                    sb.append(foregroundServiceTypePolicyInfo.hasForegroundOnlyPermission() ? " and the app must be in the eligible state/exemptions to access the foreground only permission" : "");
                    String sb2 = sb.toString();
                    Slog.wtfQuiet("ActivityManager", sb2);
                    Slog.w("ActivityManager", sb2);
                } else if (checkForegroundServiceTypePolicy == 5) {
                    StringBuilder sb3 = new StringBuilder("Starting FGS with type ");
                    sb3.append(ServiceInfo.foregroundServiceTypeToLabel(i));
                    sb3.append(" callerApp=");
                    sb3.append(serviceRecord.app);
                    sb3.append(" targetSDK=");
                    sb3.append(serviceRecord.app.info.targetSdkVersion);
                    sb3.append(" requires permissions: ");
                    sb3.append(foregroundServiceTypePolicyInfo.toPermissionString());
                    sb3.append(foregroundServiceTypePolicyInfo.hasForegroundOnlyPermission() ? " and the app must be in the eligible state/exemptions to access the foreground only permission" : "");
                    obj = new SecurityException(sb3.toString());
                }
            } else if (i3 == -1 && i == 0) {
                obj = new MissingForegroundServiceTypeException("Starting FGS without a type  callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion);
            } else {
                StringBuilder sb4 = new StringBuilder("Starting FGS with type ");
                sb4.append(ServiceInfo.foregroundServiceTypeToLabel(i));
                sb4.append(" callerApp=");
                sb4.append(serviceRecord.app);
                sb4.append(" targetSDK=");
                obj = new InvalidForegroundServiceTypeException(AmFmBandRange$$ExternalSyntheticOutline0.m(serviceRecord.app.info.targetSdkVersion, sb4, " has been prohibited"));
            }
            return Pair.create(Integer.valueOf(checkForegroundServiceTypePolicy), obj);
        }
        String str2 = "Starting FGS with type " + ServiceInfo.foregroundServiceTypeToLabel(i) + " code=" + checkForegroundServiceTypePolicy + " callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion;
        Slog.wtfQuiet("ActivityManager", str2);
        Slog.w("ActivityManager", str2);
        obj = null;
        return Pair.create(Integer.valueOf(checkForegroundServiceTypePolicy), obj);
    }
}
