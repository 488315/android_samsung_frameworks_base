package com.android.server.usage;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.role.RoleManager;
import android.app.usage.AppLaunchEstimateInfo;
import android.app.usage.AppStandbyInfo;
import android.app.usage.BroadcastResponseStats;
import android.app.usage.BroadcastResponseStatsList;
import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageEventsQuery;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutServiceInternal;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseSetArray;
import android.util.proto.ProtoInputStream;

import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.job.controllers.PrefetchController;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.usage.AppTimeLimitController.AppUsageGroup;
import com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup;
import com.android.server.usage.AppTimeLimitController.SessionUsageGroup;
import com.android.server.utils.AlarmQueue;

import libcore.util.EmptyArray;

import com.samsung.android.app.usage.IUsageStatsWatcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsageStatsService extends SystemService
        implements UserUsageStatsService.StatsUpdatedListener {
    public static final File COMMON_USAGE_STATS_DIR;
    public static final File LEGACY_COMMON_USAGE_STATS_DIR;
    public static final File LEGACY_USER_USAGE_STATS_DIR;
    public AppOpsManager mAppOps;
    public AppStandbyInternal mAppStandby;
    public AppTimeLimitController mAppTimeLimit;
    public DevicePolicyManagerInternal mDpmInternal;
    public final CopyOnWriteArraySet mEstimatedLaunchTimeChangedListeners;
    public H mHandler;
    public final Injector mInjector;
    public Handler mIoHandler;
    public final UsageStatsService$$ExternalSyntheticLambda0 mIoHandlerCallback;
    public final Map mLastTimeComponentUsedGlobal;
    public final SparseArray mLaunchTimeAlarmQueues;
    public final Object mLock;
    public PackageManager mPackageManager;
    public PackageManagerInternal mPackageManagerInternal;
    public final MyPackageMonitor mPackageMonitor;
    public final SparseSetArray mPendingLaunchTimeChangePackages;
    public long mRealTimeSnapshot;
    public final SparseArray mReportedEvents;
    public BroadcastResponseStatsTracker mResponseStatsTracker;
    public ShortcutServiceInternal mShortcutServiceInternal;
    public final AnonymousClass1 mStandbyChangeListener;
    public long mSystemTimeSnapshot;
    public final AnonymousClass3 mUidObserver;
    public final SparseIntArray mUidToKernelCounter;
    public final ArraySet mUsageEventListeners;
    public final SparseArray mUsageReporters;
    public int mUsageSource;
    public final HashMap mUsageStatsWatchers;
    public final HashMap mUsageStatsWatchersComponent;
    public UserManager mUserManager;
    public final SparseArray mUserState;
    public final CopyOnWriteArraySet mUserUnlockedStates;
    public final SparseArray mVisibleActivities;
    public static final boolean ENABLE_TIME_CHANGE_CORRECTION =
            SystemProperties.getBoolean("persist.debug.time_correction", true);
    public static final boolean USE_DEDICATED_HANDLER_THREAD =
            SystemProperties.getBoolean(
                    "persist.debug.use_dedicated_handler_thread",
                    android.app.usage.Flags.useDedicatedHandlerThread());
    public static final boolean DEBUG_RESPONSE_STATS = Log.isLoggable("UsageStatsService", 3);
    public static final File KERNEL_COUNTER_FILE = new File("/proc/uid_procstat/set");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.usage.UsageStatsService$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActivityData {
        public int lastEvent = 0;
        public final String mTaskRootClass;
        public final String mTaskRootPackage;
        public final String mUsageSourcePackage;

        public ActivityData(String str, String str2, String str3) {
            this.mTaskRootPackage = str;
            this.mTaskRootClass = str2;
            this.mUsageSourcePackage = str3;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IUsageStatsManager.Stub {
        public BinderService() {}

        public final void checkCallerIsSameApp(String str) {
            int callingUid = Binder.getCallingUid();
            if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(
                            str, 0L, UserHandle.getUserId(callingUid))
                    != callingUid) {
                throw new SecurityException(
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                callingUid,
                                "Calling uid ",
                                " cannot query eventsfor package ",
                                str));
            }
        }

        public final void clearBroadcastEvents(String str, int i) {
            Objects.requireNonNull(str);
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.ACCESS_BROADCAST_RESPONSE_STATS",
                            "clearBroadcastEvents");
            int callingUid = Binder.getCallingUid();
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            false,
                            false,
                            "clearBroadcastResponseStats",
                            str);
            BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                    UsageStatsService.this.mResponseStatsTracker;
            synchronized (broadcastResponseStatsTracker.mLock) {
                try {
                    UserBroadcastEvents userBroadcastEvents =
                            (UserBroadcastEvents)
                                    broadcastResponseStatsTracker.mUserBroadcastEvents.get(
                                            handleIncomingUser);
                    if (userBroadcastEvents == null) {
                        return;
                    }
                    userBroadcastEvents.clear(callingUid);
                } finally {
                }
            }
        }

        public final void clearBroadcastResponseStats(String str, long j, String str2, int i) {
            Objects.requireNonNull(str2);
            if (j < 0) {
                throw new IllegalArgumentException("id needs to be >=0");
            }
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.ACCESS_BROADCAST_RESPONSE_STATS",
                            "clearBroadcastResponseStats");
            int callingUid = Binder.getCallingUid();
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            false,
                            false,
                            "clearBroadcastResponseStats",
                            str2);
            BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                    UsageStatsService.this.mResponseStatsTracker;
            synchronized (broadcastResponseStatsTracker.mLock) {
                try {
                    SparseArray sparseArray =
                            (SparseArray)
                                    broadcastResponseStatsTracker.mUserResponseStats.get(
                                            callingUid);
                    if (sparseArray == null) {
                        return;
                    }
                    UserBroadcastResponseStats userBroadcastResponseStats =
                            (UserBroadcastResponseStats) sparseArray.get(handleIncomingUser);
                    if (userBroadcastResponseStats == null) {
                        return;
                    }
                    for (int size = userBroadcastResponseStats.mResponseStats.size() - 1;
                            size >= 0;
                            size--) {
                        BroadcastEvent broadcastEvent =
                                (BroadcastEvent)
                                        userBroadcastResponseStats.mResponseStats.keyAt(size);
                        if ((j == 0 || j == broadcastEvent.mIdForResponseEvent)
                                && (str == null || str.equals(broadcastEvent.mTargetPackage))) {
                            userBroadcastResponseStats.mResponseStats.removeAt(size);
                        }
                    }
                } finally {
                }
            }
        }

        public final void deleteUsageStats() {
            if (UserHandle.getAppId(Binder.getCallingUid()) == 1000) {
                UsageStatsService usageStatsService = UsageStatsService.this;
                synchronized (usageStatsService.mLock) {
                    UserUsageStatsService userUsageStatsServiceLocked =
                            usageStatsService.getUserUsageStatsServiceLocked(0);
                    HashMap installedPackages = usageStatsService.getInstalledPackages(0);
                    long checkAndGetTimeLocked =
                            userUsageStatsServiceLocked.checkAndGetTimeLocked();
                    try {
                        UsageStatsDatabase usageStatsDatabase =
                                userUsageStatsServiceLocked.mDatabase;
                        usageStatsDatabase.prune(checkAndGetTimeLocked, true);
                        int i = 0;
                        while (true) {
                            LongSparseArray[] longSparseArrayArr =
                                    usageStatsDatabase.mSortedStatFiles;
                            if (i >= longSparseArrayArr.length) {
                                break;
                            }
                            LongSparseArray longSparseArray = longSparseArrayArr[i];
                            if (longSparseArray != null) {
                                longSparseArray.clear();
                            }
                            i++;
                        }
                        userUsageStatsServiceLocked.init(
                                checkAndGetTimeLocked, installedPackages, true);
                    } catch (Exception e) {
                        WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                                e, "deleteUsageData fail: ", "UsageStatsService");
                    }
                }
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            boolean z;
            boolean z2;
            int[] iArr;
            boolean z3;
            boolean z4;
            if (DumpUtils.checkDumpAndUsageStatsPermission(
                    UsageStatsService.this.getContext(), "UsageStatsService", printWriter)) {
                UsageStatsService usageStatsService = UsageStatsService.this;
                usageStatsService.getClass();
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "  ");
                ArrayList arrayList = new ArrayList();
                boolean z5 = true;
                if (strArr != null) {
                    z = false;
                    z2 = false;
                    for (int i = 0; i < strArr.length; i++) {
                        String str = strArr[i];
                        if ("--checkin".equals(str)) {
                            z = true;
                        } else if ("-c".equals(str)) {
                            z2 = true;
                        } else {
                            if ("flush".equals(str)) {
                                synchronized (usageStatsService.mLock) {
                                    usageStatsService.flushToDiskLocked();
                                }
                                usageStatsService.mAppStandby.flushToDisk();
                                printWriter.println("Flushed stats to disk");
                                return;
                            }
                            if ("is-app-standby-enabled".equals(str)) {
                                printWriter.println(
                                        usageStatsService.mAppStandby.isAppIdleEnabled());
                                return;
                            }
                            if ("apptimelimit".equals(str)) {
                                synchronized (usageStatsService.mLock) {
                                    int i2 = i + 1;
                                    try {
                                        if (i2 >= strArr.length) {
                                            usageStatsService.mAppTimeLimit.dump(printWriter, null);
                                        } else {
                                            usageStatsService.mAppTimeLimit.dump(
                                                    printWriter,
                                                    (String[])
                                                            Arrays.copyOfRange(
                                                                    strArr, i2, strArr.length));
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            if ("file".equals(str)) {
                                IndentingPrintWriter indentingPrintWriter2 =
                                        new IndentingPrintWriter(printWriter, "  ");
                                synchronized (usageStatsService.mLock) {
                                    try {
                                        if (i + 1 >= strArr.length) {
                                            int size = usageStatsService.mUserState.size();
                                            for (int i3 = 0; i3 < size; i3++) {
                                                int keyAt = usageStatsService.mUserState.keyAt(i3);
                                                if (usageStatsService.mUserUnlockedStates.contains(
                                                        Integer.valueOf(keyAt))) {
                                                    indentingPrintWriter2.println("user=" + keyAt);
                                                    indentingPrintWriter2.increaseIndent();
                                                    ((UserUsageStatsService)
                                                                    usageStatsService.mUserState
                                                                            .valueAt(i3))
                                                            .dumpFile(indentingPrintWriter2, null);
                                                    indentingPrintWriter2.decreaseIndent();
                                                }
                                            }
                                        } else {
                                            int parseUserIdFromArgs =
                                                    usageStatsService.parseUserIdFromArgs(
                                                            strArr, i, indentingPrintWriter2);
                                            if (parseUserIdFromArgs != -10000) {
                                                ((UserUsageStatsService)
                                                                usageStatsService.mUserState.get(
                                                                        parseUserIdFromArgs))
                                                        .dumpFile(
                                                                indentingPrintWriter2,
                                                                (String[])
                                                                        Arrays.copyOfRange(
                                                                                strArr,
                                                                                i + 2,
                                                                                strArr.length));
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            if ("database-info".equals(str)) {
                                IndentingPrintWriter indentingPrintWriter3 =
                                        new IndentingPrintWriter(printWriter, "  ");
                                synchronized (usageStatsService.mLock) {
                                    try {
                                        if (i + 1 >= strArr.length) {
                                            int size2 = usageStatsService.mUserState.size();
                                            for (int i4 = 0; i4 < size2; i4++) {
                                                int keyAt2 = usageStatsService.mUserState.keyAt(i4);
                                                if (usageStatsService.mUserUnlockedStates.contains(
                                                        Integer.valueOf(keyAt2))) {
                                                    indentingPrintWriter3.println("user=" + keyAt2);
                                                    indentingPrintWriter3.increaseIndent();
                                                    ((UserUsageStatsService)
                                                                    usageStatsService.mUserState
                                                                            .valueAt(i4))
                                                            .mDatabase.dump(
                                                                    indentingPrintWriter3, false);
                                                    indentingPrintWriter3.decreaseIndent();
                                                }
                                            }
                                        } else {
                                            int parseUserIdFromArgs2 =
                                                    usageStatsService.parseUserIdFromArgs(
                                                            strArr, i, indentingPrintWriter3);
                                            if (parseUserIdFromArgs2 != -10000) {
                                                ((UserUsageStatsService)
                                                                usageStatsService.mUserState.get(
                                                                        parseUserIdFromArgs2))
                                                        .mDatabase.dump(
                                                                indentingPrintWriter3, false);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            if ("appstandby".equals(str)) {
                                usageStatsService.mAppStandby.dumpState(strArr, printWriter);
                                return;
                            }
                            if ("stats-directory".equals(str)) {
                                IndentingPrintWriter indentingPrintWriter4 =
                                        new IndentingPrintWriter(printWriter, "  ");
                                synchronized (usageStatsService.mLock) {
                                    try {
                                        int parseUserIdFromArgs3 =
                                                usageStatsService.parseUserIdFromArgs(
                                                        strArr, i, indentingPrintWriter4);
                                        if (parseUserIdFromArgs3 != -10000) {
                                            indentingPrintWriter4.println(
                                                    new File(
                                                                    Environment
                                                                            .getDataSystemCeDirectory(
                                                                                    parseUserIdFromArgs3),
                                                                    "usagestats")
                                                            .getAbsolutePath());
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            if ("mappings".equals(str)) {
                                IndentingPrintWriter indentingPrintWriter5 =
                                        new IndentingPrintWriter(printWriter, "  ");
                                synchronized (usageStatsService.mLock) {
                                    try {
                                        int parseUserIdFromArgs4 =
                                                usageStatsService.parseUserIdFromArgs(
                                                        strArr, i, indentingPrintWriter5);
                                        if (parseUserIdFromArgs4 != -10000) {
                                            ((UserUsageStatsService)
                                                            usageStatsService.mUserState.get(
                                                                    parseUserIdFromArgs4))
                                                    .mDatabase.dumpMappings(indentingPrintWriter5);
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            if ("broadcast-response-stats".equals(str)) {
                                synchronized (usageStatsService.mLock) {
                                    usageStatsService.mResponseStatsTracker.dump(
                                            indentingPrintWriter);
                                }
                                return;
                            }
                            if ("app-component-usage".equals(str)) {
                                IndentingPrintWriter indentingPrintWriter6 =
                                        new IndentingPrintWriter(printWriter, "  ");
                                synchronized (usageStatsService.mLock) {
                                    try {
                                        if (!((ArrayMap)
                                                        usageStatsService
                                                                .mLastTimeComponentUsedGlobal)
                                                .isEmpty()) {
                                            indentingPrintWriter6.println("App Component Usages:");
                                            indentingPrintWriter6.increaseIndent();
                                            for (String str2 :
                                                    ((ArrayMap)
                                                                    usageStatsService
                                                                            .mLastTimeComponentUsedGlobal)
                                                            .keySet()) {
                                                indentingPrintWriter6.println(
                                                        "package="
                                                                + str2
                                                                + " lastUsed="
                                                                + UserUsageStatsService
                                                                        .formatDateTime(
                                                                                ((Long)
                                                                                                ((ArrayMap)
                                                                                                                usageStatsService
                                                                                                                        .mLastTimeComponentUsedGlobal)
                                                                                                        .get(
                                                                                                                str2))
                                                                                        .longValue(),
                                                                                true));
                                            }
                                            indentingPrintWriter6.decreaseIndent();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            if (str != null
                                    && !str.startsWith(
                                            PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                                arrayList.add(str);
                            }
                        }
                    }
                } else {
                    z = false;
                    z2 = false;
                }
                StringBuilder m$1 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                printWriter,
                                "Flags:",
                                "    android.app.usage.user_interaction_type_api: ");
                m$1.append(android.app.usage.Flags.userInteractionTypeApi());
                printWriter.println(m$1.toString());
                printWriter.println(
                        "    android.app.usage.use_parceled_list: "
                                + android.app.usage.Flags.useParceledList());
                printWriter.println(
                        "    android.app.usage.filter_based_event_query_api: "
                                + android.app.usage.Flags.filterBasedEventQueryApi());
                printWriter.println(
                        "    android.app.usage.disable_idle_check: "
                                + android.app.usage.Flags.disableIdleCheck());
                synchronized (usageStatsService.mLock) {
                    try {
                        int size3 = usageStatsService.mUserState.size();
                        iArr = new int[size3];
                        int i5 = 0;
                        while (i5 < size3) {
                            int keyAt3 = usageStatsService.mUserState.keyAt(i5);
                            iArr[i5] = keyAt3;
                            indentingPrintWriter.printPair("user", Integer.valueOf(keyAt3));
                            indentingPrintWriter.println();
                            indentingPrintWriter.increaseIndent();
                            if (usageStatsService.mUserUnlockedStates.contains(
                                    Integer.valueOf(keyAt3))) {
                                if (z) {
                                    ((UserUsageStatsService)
                                                    usageStatsService.mUserState.valueAt(i5))
                                            .checkin(indentingPrintWriter);
                                } else {
                                    ((UserUsageStatsService)
                                                    usageStatsService.mUserState.valueAt(i5))
                                            .dump(indentingPrintWriter, arrayList, z2);
                                    indentingPrintWriter.println();
                                }
                                z3 = z;
                                z4 = z5;
                            } else {
                                synchronized (usageStatsService.mReportedEvents) {
                                    LinkedList linkedList =
                                            (LinkedList)
                                                    usageStatsService.mReportedEvents.get(keyAt3);
                                    if (linkedList == null || linkedList.isEmpty()) {
                                        z3 = z;
                                        z4 = z5;
                                    } else {
                                        int size4 = linkedList.size();
                                        indentingPrintWriter.println(
                                                "Pending events: count=" + size4);
                                        indentingPrintWriter.increaseIndent();
                                        int i6 = 0;
                                        while (i6 < size4) {
                                            UserUsageStatsService.printEvent(
                                                    indentingPrintWriter,
                                                    (UsageEvents.Event) linkedList.get(i6),
                                                    true);
                                            i6++;
                                            z = z;
                                        }
                                        z3 = z;
                                        z4 = true;
                                        indentingPrintWriter.decreaseIndent();
                                        indentingPrintWriter.println();
                                    }
                                }
                            }
                            indentingPrintWriter.decreaseIndent();
                            i5++;
                            z5 = z4;
                            z = z3;
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.printPair(
                                "Usage Source",
                                UsageStatsManager.usageSourceToString(
                                        usageStatsService.mUsageSource));
                        indentingPrintWriter.println();
                        usageStatsService.mAppTimeLimit.dump(printWriter, null);
                        indentingPrintWriter.println();
                        usageStatsService.mResponseStatsTracker.dump(indentingPrintWriter);
                    } catch (Throwable th) {
                        throw th;
                    } finally {
                    }
                }
                usageStatsService.mAppStandby.dumpUsers(indentingPrintWriter, iArr, arrayList);
                if (CollectionUtils.isEmpty(arrayList)) {
                    printWriter.println();
                    usageStatsService.mAppStandby.dumpState(strArr, printWriter);
                }
                synchronized (usageStatsService.mUsageStatsWatchers) {
                    try {
                        indentingPrintWriter.println("mUsageStatsWatchers = [");
                        Iterator it = usageStatsService.mUsageStatsWatchers.entrySet().iterator();
                        while (it.hasNext()) {
                            IUsageStatsWatcher iUsageStatsWatcher =
                                    (IUsageStatsWatcher) ((Map.Entry) it.next()).getValue();
                            indentingPrintWriter.print("  ");
                            indentingPrintWriter.println(iUsageStatsWatcher);
                        }
                        indentingPrintWriter.println("]");
                    } finally {
                    }
                }
            }
        }

        public final void forceUsageSourceSettingRead() {
            UsageStatsService.this.readUsageSourceSetting();
        }

        public final int getAppMinStandbyBucket(String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            try {
                int handleIncomingUser =
                        ActivityManager.getService()
                                .handleIncomingUser(
                                        Binder.getCallingPid(),
                                        callingUid,
                                        i,
                                        false,
                                        false,
                                        "getAppStandbyBucket",
                                        (String) null);
                int packageUid =
                        UsageStatsService.this.mPackageManagerInternal.getPackageUid(
                                str, 0L, handleIncomingUser);
                if (packageUid != callingUid && !hasQueryPermission(str2)) {
                    throw new SecurityException(
                            "Don't have permission to query min app standby bucket");
                }
                boolean isPackageEphemeral =
                        UsageStatsService.this.mPackageManagerInternal.isPackageEphemeral(
                                handleIncomingUser, str);
                boolean m1013$$Nest$mshouldObfuscateInstantAppsForCaller =
                        UsageStatsService.m1013$$Nest$mshouldObfuscateInstantAppsForCaller(
                                UsageStatsService.this, callingUid, handleIncomingUser);
                if (packageUid < 0
                        || (isPackageEphemeral
                                && m1013$$Nest$mshouldObfuscateInstantAppsForCaller)) {
                    throw new IllegalArgumentException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Cannot get min standby bucket for non existent package (",
                                    str,
                                    ")"));
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return UsageStatsService.this.mAppStandby.getAppMinStandbyBucket(
                            str, UserHandle.getAppId(packageUid), handleIncomingUser, false);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public final int getAppStandbyBucket(String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            try {
                int handleIncomingUser =
                        ActivityManager.getService()
                                .handleIncomingUser(
                                        Binder.getCallingPid(),
                                        callingUid,
                                        i,
                                        false,
                                        false,
                                        "getAppStandbyBucket",
                                        (String) null);
                int packageUid =
                        UsageStatsService.this.mPackageManagerInternal.getPackageUid(
                                str, 0L, handleIncomingUser);
                boolean z = packageUid == callingUid;
                if (!z && !hasQueryPermission(str2)) {
                    throw new SecurityException(
                            "Don't have permission to query app standby bucket");
                }
                boolean isPackageEphemeral =
                        UsageStatsService.this.mPackageManagerInternal.isPackageEphemeral(
                                handleIncomingUser, str);
                boolean m1013$$Nest$mshouldObfuscateInstantAppsForCaller =
                        UsageStatsService.m1013$$Nest$mshouldObfuscateInstantAppsForCaller(
                                UsageStatsService.this, callingUid, handleIncomingUser);
                if (packageUid < 0
                        || (!z
                                && isPackageEphemeral
                                && m1013$$Nest$mshouldObfuscateInstantAppsForCaller)) {
                    throw new IllegalArgumentException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Cannot get standby bucket for non existent package (",
                                    str,
                                    ")"));
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return UsageStatsService.this.mAppStandby.getAppStandbyBucket(
                            str, handleIncomingUser, SystemClock.elapsedRealtime(), false);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public final ParceledListSlice getAppStandbyBuckets(String str, int i) {
            final int callingUid = Binder.getCallingUid();
            try {
                final int handleIncomingUser =
                        ActivityManager.getService()
                                .handleIncomingUser(
                                        Binder.getCallingPid(),
                                        callingUid,
                                        i,
                                        false,
                                        false,
                                        "getAppStandbyBucket",
                                        (String) null);
                if (!hasQueryPermission(str)) {
                    throw new SecurityException(
                            "Don't have permission to query app standby bucket");
                }
                final boolean m1013$$Nest$mshouldObfuscateInstantAppsForCaller =
                        UsageStatsService.m1013$$Nest$mshouldObfuscateInstantAppsForCaller(
                                UsageStatsService.this, callingUid, handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List appStandbyBuckets =
                            UsageStatsService.this.mAppStandby.getAppStandbyBuckets(
                                    handleIncomingUser);
                    if (appStandbyBuckets == null) {
                        return ParceledListSlice.emptyList();
                    }
                    appStandbyBuckets.removeIf(
                            new Predicate() { // from class:
                                              // com.android.server.usage.UsageStatsService$BinderService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    UsageStatsService.BinderService binderService =
                                            UsageStatsService.BinderService.this;
                                    int i2 = callingUid;
                                    int i3 = handleIncomingUser;
                                    boolean z = m1013$$Nest$mshouldObfuscateInstantAppsForCaller;
                                    AppStandbyInfo appStandbyInfo = (AppStandbyInfo) obj;
                                    UsageStatsService usageStatsService = UsageStatsService.this;
                                    if (usageStatsService.mPackageManagerInternal.getPackageUid(
                                                    appStandbyInfo.mPackageName, 0L, i3)
                                            != i2) {
                                        UsageStatsService usageStatsService2 =
                                                UsageStatsService.this;
                                        if (usageStatsService2.mPackageManagerInternal
                                                        .isPackageEphemeral(
                                                                i3, appStandbyInfo.mPackageName)
                                                && z) {
                                            return true;
                                        }
                                    }
                                    return false;
                                }
                            });
                    return new ParceledListSlice(appStandbyBuckets);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public final String getAppStandbyConstant(String str) {
            Objects.requireNonNull(str);
            if (hasPermissions("android.permission.READ_DEVICE_CONFIG")) {
                return UsageStatsService.this.mAppStandby.getAppStandbyConstant(str);
            }
            throw new SecurityException("Caller doesn't have READ_DEVICE_CONFIG permission");
        }

        public final long getLastTimeAnyComponentUsed(String str, String str2) {
            long millis;
            if (!hasPermissions("android.permission.INTERACT_ACROSS_USERS")) {
                throw new SecurityException("Caller doesn't have INTERACT_ACROSS_USERS permission");
            }
            if (!hasQueryPermission(str2)) {
                throw new SecurityException("Don't have permission to query usage stats");
            }
            synchronized (UsageStatsService.this.mLock) {
                long longValue =
                        ((Long)
                                        UsageStatsService.this.mLastTimeComponentUsedGlobal
                                                .getOrDefault(str, 0L))
                                .longValue();
                TimeUnit timeUnit = TimeUnit.DAYS;
                millis = (longValue / timeUnit.toMillis(1L)) * timeUnit.toMillis(1L);
            }
            return millis;
        }

        public final int getUsageSource() {
            int i;
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            synchronized (UsageStatsService.this.mLock) {
                i = UsageStatsService.this.mUsageSource;
            }
            return i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int handleShellCommand(
                ParcelFileDescriptor parcelFileDescriptor,
                ParcelFileDescriptor parcelFileDescriptor2,
                ParcelFileDescriptor parcelFileDescriptor3,
                String[] strArr) {
            return new UsageStatsShellCommand(UsageStatsService.this)
                    .exec(
                            this,
                            parcelFileDescriptor.getFileDescriptor(),
                            parcelFileDescriptor2.getFileDescriptor(),
                            parcelFileDescriptor3.getFileDescriptor(),
                            strArr);
        }

        public final boolean hasObserverPermission() {
            int callingUid = Binder.getCallingUid();
            DevicePolicyManagerInternal dpmInternal = UsageStatsService.this.getDpmInternal();
            if (callingUid != 1000) {
                return (dpmInternal != null
                                && (dpmInternal.isActiveProfileOwner(callingUid)
                                        || dpmInternal.isActiveDeviceOwner(callingUid)))
                        || UsageStatsService.this
                                        .getContext()
                                        .checkCallingPermission(
                                                "android.permission.OBSERVE_APP_USAGE")
                                == 0;
            }
            return true;
        }

        public final boolean hasPermissions(String... strArr) {
            if (Binder.getCallingUid() == 1000) {
                return true;
            }
            Context context = UsageStatsService.this.getContext();
            boolean z = true;
            for (String str : strArr) {
                z = z && context.checkCallingPermission(str) == 0;
            }
            return z;
        }

        public final boolean hasQueryPermission(String str) {
            int callingUid = Binder.getCallingUid();
            if (callingUid == 1000) {
                return true;
            }
            int noteOp = UsageStatsService.this.mAppOps.noteOp(43, callingUid, str);
            return noteOp == 3
                    ? UsageStatsService.this
                                    .getContext()
                                    .checkCallingPermission(
                                            "android.permission.PACKAGE_USAGE_STATS")
                            == 0
                    : noteOp == 0;
        }

        public final boolean isAppInactive(String str, int i, String str2) {
            int callingUid = Binder.getCallingUid();
            try {
                int handleIncomingUser =
                        ActivityManager.getService()
                                .handleIncomingUser(
                                        Binder.getCallingPid(),
                                        callingUid,
                                        i,
                                        false,
                                        false,
                                        "isAppInactive",
                                        (String) null);
                if (str.equals(str2)) {
                    if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(
                                    str2, 0L, handleIncomingUser)
                            != callingUid) {
                        return false;
                    }
                } else if (!hasQueryPermission(str2)) {
                    return false;
                }
                boolean m1013$$Nest$mshouldObfuscateInstantAppsForCaller =
                        UsageStatsService.m1013$$Nest$mshouldObfuscateInstantAppsForCaller(
                                UsageStatsService.this, callingUid, handleIncomingUser);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return UsageStatsService.this.mAppStandby.isAppIdleFiltered(
                            str,
                            handleIncomingUser,
                            SystemClock.elapsedRealtime(),
                            m1013$$Nest$mshouldObfuscateInstantAppsForCaller);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public final boolean isAppStandbyEnabled() {
            return UsageStatsService.this.mAppStandby.isAppIdleEnabled();
        }

        public final boolean isPackageExemptedFromBroadcastResponseStats(String str, int i) {
            Objects.requireNonNull(str);
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.DUMP",
                            "isPackageExemptedFromBroadcastResponseStats");
            BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                    UsageStatsService.this.mResponseStatsTracker;
            UserHandle of = UserHandle.of(i);
            synchronized (broadcastResponseStatsTracker.mLock) {
                try {
                    if (broadcastResponseStatsTracker.doesPackageHoldExemptedPermission(str, of)) {
                        return true;
                    }
                    return broadcastResponseStatsTracker.doesPackageHoldExemptedRole(str, of);
                } finally {
                }
            }
        }

        public final void onCarrierPrivilegedAppsChanged() {
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.BIND_CARRIER_SERVICES",
                            "onCarrierPrivilegedAppsChanged can only be called by privileged"
                                + " apps.");
            UsageStatsService.this.mAppStandby.clearCarrierPrivilegedApps();
        }

        public final BroadcastResponseStatsList queryBroadcastResponseStats(
                String str, long j, String str2, int i) {
            Objects.requireNonNull(str2);
            if (j < 0) {
                throw new IllegalArgumentException("id needs to be >=0");
            }
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.ACCESS_BROADCAST_RESPONSE_STATS",
                            "queryBroadcastResponseStats");
            int callingUid = Binder.getCallingUid();
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            false,
                            false,
                            "queryBroadcastResponseStats",
                            str2);
            BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                    UsageStatsService.this.mResponseStatsTracker;
            broadcastResponseStatsTracker.getClass();
            ArrayList arrayList = new ArrayList();
            synchronized (broadcastResponseStatsTracker.mLock) {
                try {
                    SparseArray sparseArray =
                            (SparseArray)
                                    broadcastResponseStatsTracker.mUserResponseStats.get(
                                            callingUid);
                    if (sparseArray != null) {
                        UserBroadcastResponseStats userBroadcastResponseStats =
                                (UserBroadcastResponseStats) sparseArray.get(handleIncomingUser);
                        if (userBroadcastResponseStats != null) {
                            for (int size = userBroadcastResponseStats.mResponseStats.size() - 1;
                                    size >= 0;
                                    size--) {
                                BroadcastEvent broadcastEvent =
                                        (BroadcastEvent)
                                                userBroadcastResponseStats.mResponseStats.keyAt(
                                                        size);
                                if ((j == 0 || j == broadcastEvent.mIdForResponseEvent)
                                        && (str == null
                                                || str.equals(broadcastEvent.mTargetPackage))) {
                                    arrayList.add(
                                            (BroadcastResponseStats)
                                                    userBroadcastResponseStats.mResponseStats
                                                            .valueAt(size));
                                }
                            }
                        }
                    }
                } finally {
                }
            }
            return new BroadcastResponseStatsList(arrayList);
        }

        public final ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List queryConfigurationStats =
                        UsageStatsService.this.queryConfigurationStats(callingUserId, i, j, j2);
                if (queryConfigurationStats != null) {
                    return new ParceledListSlice(queryConfigurationStats);
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParceledListSlice queryEventStats(int i, long j, long j2, String str) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List queryEventStats =
                        UsageStatsService.this.queryEventStats(callingUserId, i, j, j2);
                if (queryEventStats != null) {
                    return new ParceledListSlice(queryEventStats);
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final UsageEvents queryEvents(long j, long j2, String str) {
            if (hasQueryPermission(str)) {
                return queryEventsHelper(
                        UserHandle.getCallingUserId(), j, j2, str, EmptyArray.INT, null);
            }
            return null;
        }

        public final UsageEvents queryEventsForPackage(long j, long j2, String str) {
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            checkCallerIsSameApp(str);
            boolean hasQueryPermission = hasQueryPermission(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return UsageStatsService.this.queryEventsForPackage(
                        userId, str, j, j2, hasQueryPermission);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final UsageEvents queryEventsForPackageForUser(
                long j, long j2, int i, String str, String str2) {
            if (!hasQueryPermission(str2)) {
                return null;
            }
            if (i != UserHandle.getCallingUserId()) {
                UsageStatsService.this
                        .getContext()
                        .enforceCallingPermission(
                                "android.permission.INTERACT_ACROSS_USERS_FULL",
                                "No permission to query usage stats for this user");
            }
            if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
                checkCallerIsSameApp(str);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return UsageStatsService.this.queryEventsForPackage(i, str, j, j2, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final UsageEvents queryEventsForUser(long j, long j2, int i, String str) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            if (i != UserHandle.getCallingUserId()) {
                UsageStatsService.this
                        .getContext()
                        .enforceCallingPermission(
                                "android.permission.INTERACT_ACROSS_USERS_FULL",
                                "No permission to query usage stats for this user");
            }
            return queryEventsHelper(i, j, j2, str, EmptyArray.INT, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0057 A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:3:0x0018, B:5:0x001e, B:6:0x0028, B:8:0x002d, B:9:0x003a, B:11:0x0041, B:13:0x0053, B:15:0x0057, B:17:0x006c, B:19:0x0070, B:21:0x0074, B:22:0x0079, B:28:0x005b, B:33:0x0046), top: B:2:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x006c A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:3:0x0018, B:5:0x001e, B:6:0x0028, B:8:0x002d, B:9:0x003a, B:11:0x0041, B:13:0x0053, B:15:0x0057, B:17:0x006c, B:19:0x0070, B:21:0x0074, B:22:0x0079, B:28:0x005b, B:33:0x0046), top: B:2:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0070 A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:3:0x0018, B:5:0x001e, B:6:0x0028, B:8:0x002d, B:9:0x003a, B:11:0x0041, B:13:0x0053, B:15:0x0057, B:17:0x006c, B:19:0x0070, B:21:0x0074, B:22:0x0079, B:28:0x005b, B:33:0x0046), top: B:2:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0074 A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:3:0x0018, B:5:0x001e, B:6:0x0028, B:8:0x002d, B:9:0x003a, B:11:0x0041, B:13:0x0053, B:15:0x0057, B:17:0x006c, B:19:0x0070, B:21:0x0074, B:22:0x0079, B:28:0x005b, B:33:0x0046), top: B:2:0x0018 }] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x005b A[Catch: all -> 0x008d, TryCatch #0 {all -> 0x008d, blocks: (B:3:0x0018, B:5:0x001e, B:6:0x0028, B:8:0x002d, B:9:0x003a, B:11:0x0041, B:13:0x0053, B:15:0x0057, B:17:0x006c, B:19:0x0070, B:21:0x0074, B:22:0x0079, B:28:0x005b, B:33:0x0046), top: B:2:0x0018 }] */
        /* JADX WARN: Type inference failed for: r3v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v5 */
        /* JADX WARN: Type inference failed for: r3v6 */
        /* JADX WARN: Type inference failed for: r3v7 */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v4 */
        /* JADX WARN: Type inference failed for: r8v6 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.app.usage.UsageEvents queryEventsHelper(
                int r18,
                long r19,
                long r21,
                java.lang.String r23,
                int[] r24,
                android.util.ArraySet r25) {
            /*
                r17 = this;
                r0 = r17
                int r1 = android.os.Binder.getCallingUid()
                int r2 = android.os.Binder.getCallingPid()
                com.android.server.usage.UsageStatsService r3 = com.android.server.usage.UsageStatsService.this
                int r4 = android.os.UserHandle.getCallingUserId()
                boolean r3 = com.android.server.usage.UsageStatsService.m1013$$Nest$mshouldObfuscateInstantAppsForCaller(r3, r1, r4)
                long r4 = android.os.Binder.clearCallingIdentity()
                com.android.server.usage.UsageStatsService r6 = com.android.server.usage.UsageStatsService.this     // Catch: java.lang.Throwable -> L8d
                android.content.pm.ShortcutServiceInternal r7 = r6.mShortcutServiceInternal     // Catch: java.lang.Throwable -> L8d
                if (r7 != 0) goto L28
                java.lang.Class<android.content.pm.ShortcutServiceInternal> r7 = android.content.pm.ShortcutServiceInternal.class
                java.lang.Object r7 = com.android.server.LocalServices.getService(r7)     // Catch: java.lang.Throwable -> L8d
                android.content.pm.ShortcutServiceInternal r7 = (android.content.pm.ShortcutServiceInternal) r7     // Catch: java.lang.Throwable -> L8d
                r6.mShortcutServiceInternal = r7     // Catch: java.lang.Throwable -> L8d
            L28:
                android.content.pm.ShortcutServiceInternal r6 = r6.mShortcutServiceInternal     // Catch: java.lang.Throwable -> L8d
                r7 = 1
                if (r6 == 0) goto L37
                r9 = r18
                r8 = r23
                boolean r6 = r6.hasShortcutHostPermission(r9, r8, r2, r1)     // Catch: java.lang.Throwable -> L8d
                r6 = r6 ^ r7
                goto L3a
            L37:
                r9 = r18
                r6 = r7
            L3a:
                com.android.server.usage.UsageStatsService r8 = com.android.server.usage.UsageStatsService.this     // Catch: java.lang.Throwable -> L8d
                r10 = 0
                r11 = 1000(0x3e8, float:1.401E-42)
                if (r1 != r11) goto L46
                r8.getClass()     // Catch: java.lang.Throwable -> L8d
            L44:
                r8 = r10
                goto L53
            L46:
                android.content.Context r8 = r8.getContext()     // Catch: java.lang.Throwable -> L8d
                java.lang.String r12 = "android.permission.ACCESS_LOCUS_ID_USAGE_STATS"
                int r8 = r8.checkPermission(r12, r2, r1)     // Catch: java.lang.Throwable -> L8d
                if (r8 == 0) goto L44
                r8 = r7
            L53:
                com.android.server.usage.UsageStatsService r12 = com.android.server.usage.UsageStatsService.this     // Catch: java.lang.Throwable -> L8d
                if (r1 != r11) goto L5b
                r12.getClass()     // Catch: java.lang.Throwable -> L8d
                goto L6a
            L5b:
                android.content.Context r11 = r12.getContext()     // Catch: java.lang.Throwable -> L8d
                java.lang.String r12 = "android.permission.MANAGE_NOTIFICATIONS"
                int r1 = r11.checkPermission(r12, r2, r1)     // Catch: java.lang.Throwable -> L8d
                if (r1 == 0) goto L68
                goto L69
            L68:
                r7 = r10
            L69:
                r10 = r7
            L6a:
                if (r6 == 0) goto L6e
                r3 = r3 | 2
            L6e:
                if (r8 == 0) goto L72
                r3 = r3 | 8
            L72:
                if (r10 == 0) goto L78
                r1 = r3 | 4
                r14 = r1
                goto L79
            L78:
                r14 = r3
            L79:
                com.android.server.usage.UsageStatsService r8 = com.android.server.usage.UsageStatsService.this     // Catch: java.lang.Throwable -> L8d
                r9 = r18
                r10 = r19
                r12 = r21
                r15 = r24
                r16 = r25
                android.app.usage.UsageEvents r0 = r8.queryEventsWithQueryFilters(r9, r10, r12, r14, r15, r16)     // Catch: java.lang.Throwable -> L8d
                android.os.Binder.restoreCallingIdentity(r4)
                return r0
            L8d:
                r0 = move-exception
                android.os.Binder.restoreCallingIdentity(r4)
                throw r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usage.UsageStatsService.BinderService.queryEventsHelper(int,"
                        + " long, long, java.lang.String, int[],"
                        + " android.util.ArraySet):android.app.usage.UsageEvents");
        }

        public final UsageEvents queryEventsWithFilter(
                UsageEventsQuery usageEventsQuery, String str) {
            Objects.requireNonNull(usageEventsQuery);
            Objects.requireNonNull(str);
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUserId = UserHandle.getCallingUserId();
            int userId = usageEventsQuery.getUserId();
            int i = userId == -10000 ? callingUserId : userId;
            if (i != callingUserId) {
                UsageStatsService.this
                        .getContext()
                        .enforceCallingPermission(
                                "android.permission.INTERACT_ACROSS_USERS_FULL",
                                "No permission to query usage stats for user " + i);
            }
            return queryEventsHelper(
                    i,
                    usageEventsQuery.getBeginTimeMillis(),
                    usageEventsQuery.getEndTimeMillis(),
                    str,
                    usageEventsQuery.getEventTypes(),
                    new ArraySet(usageEventsQuery.getPackageNames()));
        }

        public final ParceledListSlice queryUsageStats(int i, long j, long j2, String str, int i2) {
            if (!hasQueryPermission(str)) {
                return null;
            }
            int callingUid = Binder.getCallingUid();
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            false,
                            true,
                            "queryUsageStats",
                            str);
            boolean m1013$$Nest$mshouldObfuscateInstantAppsForCaller =
                    UsageStatsService.m1013$$Nest$mshouldObfuscateInstantAppsForCaller(
                            UsageStatsService.this, callingUid, UserHandle.getCallingUserId());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List queryUsageStats =
                        UsageStatsService.this.queryUsageStats(
                                handleIncomingUser,
                                j,
                                j2,
                                i,
                                m1013$$Nest$mshouldObfuscateInstantAppsForCaller);
                if (queryUsageStats != null) {
                    return new ParceledListSlice(queryUsageStats);
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerAppUsageLimitObserver(
                int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) {
            int callingUid = Binder.getCallingUid();
            DevicePolicyManagerInternal dpmInternal = UsageStatsService.this.getDpmInternal();
            if (!hasPermissions(
                            "android.permission.SUSPEND_APPS",
                            "android.permission.OBSERVE_APP_USAGE")
                    && (dpmInternal == null || !dpmInternal.isActiveSupervisionApp(callingUid))) {
                throw new SecurityException(
                        "Caller must be the active supervision app or it must have both"
                            + " SUSPEND_APPS and OBSERVE_APP_USAGE permissions");
            }
            if (strArr == null || strArr.length == 0) {
                throw new IllegalArgumentException("Must specify at least one package");
            }
            if (pendingIntent == null && j2 < j) {
                throw new NullPointerException("callbackIntent can't be null");
            }
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.registerAppUsageLimitObserver(
                        callingUid, i, strArr, j, j2, pendingIntent, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerAppUsageObserver(
                int i, String[] strArr, long j, PendingIntent pendingIntent, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            if (strArr == null || strArr.length == 0) {
                throw new IllegalArgumentException("Must specify at least one package");
            }
            if (pendingIntent == null) {
                throw new NullPointerException("callbackIntent can't be null");
            }
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.registerAppUsageObserver(
                        callingUid, i, strArr, j, pendingIntent, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerUsageSessionObserver(
                int i,
                String[] strArr,
                long j,
                long j2,
                PendingIntent pendingIntent,
                PendingIntent pendingIntent2,
                String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            if (strArr == null || strArr.length == 0) {
                throw new IllegalArgumentException("Must specify at least one observed entity");
            }
            if (pendingIntent == null) {
                throw new NullPointerException("limitReachedCallbackIntent can't be null");
            }
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.registerUsageSessionObserver(
                        callingUid, i, strArr, j, j2, pendingIntent, pendingIntent2, userId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) {
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                try {
                    if (!UsageStatsService.this.mUsageStatsWatchers.containsKey(
                            iUsageStatsWatcher.asBinder())) {
                        UsageStatsService.this.mUsageStatsWatchers.put(
                                iUsageStatsWatcher.asBinder(), iUsageStatsWatcher);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void registerUsageStatsWatcherWithComponent(
                IUsageStatsWatcher iUsageStatsWatcher, List list) {
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                try {
                    if (!UsageStatsService.this.mUsageStatsWatchers.containsKey(
                            iUsageStatsWatcher.asBinder())) {
                        UsageStatsService.this.mUsageStatsWatchers.put(
                                iUsageStatsWatcher.asBinder(), iUsageStatsWatcher);
                        if (list != null) {
                            UsageStatsService.this.mUsageStatsWatchersComponent.put(
                                    iUsageStatsWatcher.asBinder(), list);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void reportChooserSelection(
                String str, int i, String str2, String[] strArr, String str3) {
            if (str == null) {
                throw new IllegalArgumentException("Package selection must not be null.");
            }
            if (str2 == null || str2.isBlank() || str3 == null || str3.isBlank()) {
                return;
            }
            if (android.app.usage.Flags.reportUsageStatsPermission()
                    && UserHandle.getAppId(Binder.getCallingUid()) != 1000
                    && UsageStatsService.this
                                    .getContext()
                                    .checkCallingPermission("android.permission.REPORT_USAGE_STATS")
                            != 0) {
                throw new SecurityException(
                        "Only the system or holders of the REPORT_USAGE_STATS permission are"
                            + " allowed to call reportChooserSelection");
            }
            if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, i) < 0) {
                Slog.w("UsageStatsService", "Event report user selecting an invalid package");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(9, SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mAction = str3;
            event.mContentType = str2;
            event.mContentAnnotations = strArr;
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        public final void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) {
            ArraySet arraySet;
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UsageStatsService.this.mUsageReporters) {
                    try {
                        arraySet =
                                (ArraySet)
                                        UsageStatsService.this.mUsageReporters.get(
                                                iBinder.hashCode());
                        if (arraySet == null) {
                            arraySet = new ArraySet();
                            UsageStatsService.this.mUsageReporters.put(
                                    iBinder.hashCode(), arraySet);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                synchronized (arraySet) {
                    if (!arraySet.add(str)) {
                        throw new IllegalArgumentException(
                                str
                                        + " for "
                                        + str2
                                        + " is already reported as started for this activity");
                    }
                }
                UsageStatsService.this.mAppTimeLimit.noteUsageStart(
                        userId, UsageStatsService.buildFullToken(str2, str), j);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void reportUsageStart(IBinder iBinder, String str, String str2) {
            reportPastUsageStart(iBinder, str, 0L, str2);
        }

        public final void reportUsageStop(IBinder iBinder, String str, String str2) {
            ArraySet arraySet;
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UsageStatsService.this.mUsageReporters) {
                    arraySet =
                            (ArraySet)
                                    UsageStatsService.this.mUsageReporters.get(iBinder.hashCode());
                    if (arraySet == null) {
                        throw new IllegalArgumentException(
                                "Unknown reporter trying to stop token " + str + " for " + str2);
                    }
                }
                synchronized (arraySet) {
                    if (!arraySet.remove(str)) {
                        throw new IllegalArgumentException(
                                str
                                        + " for "
                                        + str2
                                        + " is already reported as stopped for this activity");
                    }
                }
                UsageStatsService.this.mAppTimeLimit.noteUsageStop(
                        userId, UsageStatsService.buildFullToken(str2, str));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void reportUserInteraction(String str, int i) {
            reportUserInteractionInnerHelper(str, i, null);
        }

        public final void reportUserInteractionInnerHelper(
                String str, int i, PersistableBundle persistableBundle) {
            if (android.app.usage.Flags.reportUsageStatsPermission()) {
                if (UserHandle.getAppId(Binder.getCallingUid()) != 1000
                        && UsageStatsService.this
                                        .getContext()
                                        .checkCallingPermission(
                                                "android.permission.REPORT_USAGE_STATS")
                                != 0) {
                    throw new SecurityException(
                            "Only the system or holders of the REPORT_USAGE_STATS permission are"
                                + " allowed to call reportUserInteraction");
                }
                if (i != UserHandle.getCallingUserId()) {
                    UsageStatsService.this
                            .getContext()
                            .enforceCallingPermission(
                                    "android.permission.INTERACT_ACROSS_USERS_FULL",
                                    "Caller doesn't have INTERACT_ACROSS_USERS_FULL permission");
                }
            } else if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
                throw new SecurityException("Only system is allowed to call reportUserInteraction");
            }
            if (UsageStatsService.this.mPackageManagerInternal.getPackageUid(str, 0L, i) < 0) {
                throw new IllegalArgumentException(
                        XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " does not exist!"));
            }
            UsageEvents.Event event = new UsageEvents.Event(7, SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mExtras = persistableBundle;
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        public final void reportUserInteractionWithBundle(
                String str, int i, PersistableBundle persistableBundle) {
            Objects.requireNonNull(str);
            if (persistableBundle == null || persistableBundle.size() == 0) {
                throw new IllegalArgumentException("Emtry extras!");
            }
            String string = persistableBundle.getString("android.app.usage.extra.EVENT_CATEGORY");
            if (TextUtils.isEmpty(string)) {
                throw new IllegalArgumentException("Empty android.app.usage.extra.EVENT_CATEGORY");
            }
            String string2 = persistableBundle.getString("android.app.usage.extra.EVENT_ACTION");
            if (TextUtils.isEmpty(string2)) {
                throw new IllegalArgumentException("Empty android.app.usage.extra.EVENT_ACTION");
            }
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putString(
                    "android.app.usage.extra.EVENT_CATEGORY",
                    UsageStatsService.m1008$$Nest$mgetTrimmedString(
                            UsageStatsService.this, string));
            persistableBundle2.putString(
                    "android.app.usage.extra.EVENT_ACTION",
                    UsageStatsService.m1008$$Nest$mgetTrimmedString(
                            UsageStatsService.this, string2));
            reportUserInteractionInnerHelper(str, i, persistableBundle2);
        }

        public final void setAppInactive(String str, boolean z, int i) {
            try {
                int handleIncomingUser =
                        ActivityManager.getService()
                                .handleIncomingUser(
                                        Binder.getCallingPid(),
                                        Binder.getCallingUid(),
                                        i,
                                        false,
                                        true,
                                        "setAppInactive",
                                        (String) null);
                UsageStatsService.this
                        .getContext()
                        .enforceCallingPermission(
                                "android.permission.CHANGE_APP_IDLE_STATE",
                                "No permission to change app idle state");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (UsageStatsService.this.mAppStandby.getAppId(str) < 0) {
                        return;
                    }
                    UsageStatsService.this.mAppStandby.setAppIdleAsync(str, z, handleIncomingUser);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public final void setAppStandbyBucket(String str, int i, int i2) {
            setAppStandbyBucket_enforcePermission();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.mAppStandby.setAppStandbyBucket(
                        str, i, i2, callingUid, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) {
            setAppStandbyBuckets_enforcePermission();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.this.mAppStandby.setAppStandbyBuckets(
                        parceledListSlice.getList(), i, callingUid, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setEstimatedLaunchTime(String str, long j, int i) {
            setEstimatedLaunchTime_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService usageStatsService = UsageStatsService.this;
                usageStatsService.getClass();
                if (j > System.currentTimeMillis()
                        && j != usageStatsService.mAppStandby.getEstimatedLaunchTime(str, i)) {
                    usageStatsService.mAppStandby.setEstimatedLaunchTime(str, i, j);
                    if (usageStatsService.stageChangedEstimatedLaunchTime(i, str)) {
                        usageStatsService.mHandler.sendEmptyMessage(9);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) {
            setEstimatedLaunchTimes_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UsageStatsService.m1012$$Nest$msetEstimatedLaunchTimes(
                        UsageStatsService.this, i, parceledListSlice.getList());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterAppUsageLimitObserver(int i, String str) {
            int callingUid = Binder.getCallingUid();
            DevicePolicyManagerInternal dpmInternal = UsageStatsService.this.getDpmInternal();
            if (!hasPermissions(
                            "android.permission.SUSPEND_APPS",
                            "android.permission.OBSERVE_APP_USAGE")
                    && (dpmInternal == null || !dpmInternal.isActiveSupervisionApp(callingUid))) {
                throw new SecurityException(
                        "Caller must be the active supervision app or it must have both"
                            + " SUSPEND_APPS and OBSERVE_APP_USAGE permissions");
            }
            UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AppTimeLimitController appTimeLimitController =
                        UsageStatsService.this.mAppTimeLimit;
                synchronized (appTimeLimitController.mLock) {
                    try {
                        AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup =
                                (AppTimeLimitController.AppUsageLimitGroup)
                                        appTimeLimitController
                                                .getOrCreateObserverAppDataLocked(callingUid)
                                                .appUsageLimitGroups
                                                .get(i);
                        if (appUsageLimitGroup != null) {
                            appUsageLimitGroup.remove();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterAppUsageObserver(int i, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            int callingUid = Binder.getCallingUid();
            UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AppTimeLimitController appTimeLimitController =
                        UsageStatsService.this.mAppTimeLimit;
                synchronized (appTimeLimitController.mLock) {
                    try {
                        AppTimeLimitController.AppUsageGroup appUsageGroup =
                                (AppTimeLimitController.AppUsageGroup)
                                        appTimeLimitController
                                                .getOrCreateObserverAppDataLocked(callingUid)
                                                .appUsageGroups
                                                .get(i);
                        if (appUsageGroup != null) {
                            appUsageGroup.remove();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterUsageSessionObserver(int i, String str) {
            if (!hasObserverPermission()) {
                throw new SecurityException("Caller doesn't have OBSERVE_APP_USAGE permission");
            }
            int callingUid = Binder.getCallingUid();
            UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AppTimeLimitController appTimeLimitController =
                        UsageStatsService.this.mAppTimeLimit;
                synchronized (appTimeLimitController.mLock) {
                    try {
                        AppTimeLimitController.SessionUsageGroup sessionUsageGroup =
                                (AppTimeLimitController.SessionUsageGroup)
                                        appTimeLimitController
                                                .getOrCreateObserverAppDataLocked(callingUid)
                                                .sessionUsageGroups
                                                .get(i);
                        if (sessionUsageGroup != null) {
                            sessionUsageGroup.remove();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) {
            UsageStatsService.this
                    .getContext()
                    .enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", null);
            synchronized (UsageStatsService.this.mUsageStatsWatchers) {
                try {
                    if (UsageStatsService.this.mUsageStatsWatchers.containsKey(
                            iUsageStatsWatcher.asBinder())) {
                        UsageStatsService.this.mUsageStatsWatchers.remove(
                                iUsageStatsWatcher.asBinder());
                        UsageStatsService.this.mUsageStatsWatchersComponent.remove(
                                iUsageStatsWatcher.asBinder());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int size;
            int keyAt;
            switch (message.what) {
                case 0:
                    UsageStatsService.this.reportEvent(
                            message.arg1, (UsageEvents.Event) message.obj);
                    return;
                case 1:
                    UsageStatsService.this.flushToDisk();
                    return;
                case 2:
                    UsageStatsService.this.onUserRemoved(message.arg1);
                    return;
                case 3:
                case 8:
                default:
                    super.handleMessage(message);
                    return;
                case 4:
                    UsageStatsService.this.reportEventToAllUserId((UsageEvents.Event) message.obj);
                    return;
                case 5:
                    int i = message.arg1;
                    try {
                        try {
                            Trace.traceBegin(524288L, "usageStatsHandleUserUnlocked(" + i + ")");
                            UsageStatsService.m1011$$Nest$monUserUnlocked(
                                    UsageStatsService.this, i);
                        } catch (Exception e) {
                            if (UsageStatsService.this.mUserManager.isUserUnlocked(i)) {
                                throw e;
                            }
                            Slog.w(
                                    "UsageStatsService",
                                    "Attempted to unlock stopped or removed user " + message.arg1);
                        }
                        return;
                    } finally {
                        Trace.traceEnd(524288L);
                    }
                case 6:
                    UsageStatsService.m1010$$Nest$monPackageRemoved(
                            UsageStatsService.this, message.arg1, (String) message.obj);
                    return;
                case 7:
                    synchronized (UsageStatsService.this.mLock) {
                        UsageStatsService.m1009$$Nest$mloadGlobalComponentUsageLocked(
                                UsageStatsService.this);
                    }
                    return;
                case 9:
                    removeMessages(9);
                    ArraySet arraySet = new ArraySet();
                    synchronized (UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                        size = UsageStatsService.this.mPendingLaunchTimeChangePackages.size();
                    }
                    for (int i2 = size - 1; i2 >= 0; i2--) {
                        arraySet.clear();
                        synchronized (UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                            keyAt =
                                    UsageStatsService.this.mPendingLaunchTimeChangePackages.keyAt(
                                            i2);
                            arraySet.addAll(
                                    UsageStatsService.this.mPendingLaunchTimeChangePackages.get(
                                            keyAt));
                            UsageStatsService.this.mPendingLaunchTimeChangePackages.remove(keyAt);
                        }
                        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                            String str = (String) arraySet.valueAt(size2);
                            long estimatedPackageLaunchTime =
                                    UsageStatsService.this.getEstimatedPackageLaunchTime(
                                            keyAt, str);
                            Iterator it =
                                    UsageStatsService.this.mEstimatedLaunchTimeChangedListeners
                                            .iterator();
                            while (it.hasNext()) {
                                PrefetchController.AnonymousClass1 anonymousClass1 =
                                        (PrefetchController.AnonymousClass1) it.next();
                                anonymousClass1.getClass();
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = str;
                                obtain.argi1 = keyAt;
                                obtain.argl1 = estimatedPackageLaunchTime;
                                PrefetchController.this
                                        .mHandler
                                        .obtainMessage(1, obtain)
                                        .sendToTarget();
                            }
                        }
                    }
                    return;
                case 10:
                    BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                            UsageStatsService.this.mResponseStatsTracker;
                    int i3 = message.arg1;
                    synchronized (broadcastResponseStatsTracker.mLock) {
                        try {
                            for (int size3 =
                                            broadcastResponseStatsTracker.mUserBroadcastEvents
                                                            .size()
                                                    - 1;
                                    size3 >= 0;
                                    size3--) {
                                ((UserBroadcastEvents)
                                                broadcastResponseStatsTracker.mUserBroadcastEvents
                                                        .valueAt(size3))
                                        .clear(i3);
                            }
                            broadcastResponseStatsTracker.mUserResponseStats.remove(i3);
                        } finally {
                        }
                    }
                    return;
                case 11:
                    UsageStatsService.this.mAppStandby.postCheckIdleStates(message.arg1);
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LaunchTimeAlarmQueue extends AlarmQueue {
        public final int mUserId;

        public LaunchTimeAlarmQueue(int i, Context context, Looper looper) {
            super(context, looper, "*usage.launchTime*", "Estimated launch times", true, 30000L);
            this.mUserId = i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public final boolean isForUser(int i, Object obj) {
            return this.mUserId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public final void processExpiredAlarms(ArraySet arraySet) {
            if (arraySet.size() > 0) {
                synchronized (UsageStatsService.this.mPendingLaunchTimeChangePackages) {
                    UsageStatsService.this.mPendingLaunchTimeChangePackages.addAll(
                            this.mUserId, arraySet);
                }
                UsageStatsService.this.mHandler.sendEmptyMessage(9);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends UsageStatsManagerInternal {
        public LocalService() {}

        @Override // android.app.usage.UsageStatsManagerInternal
        public final void applyRestoredPayload(int i, String str, byte[] bArr) {
            synchronized (UsageStatsService.this.mLock) {
                try {
                    if (!UsageStatsService.this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                        Slog.w(
                                "UsageStatsService",
                                "Failed to apply restored payload for locked user " + i);
                    } else {
                        UserUsageStatsService userUsageStatsServiceLocked =
                                UsageStatsService.this.getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked == null) {
                            return;
                        }
                        userUsageStatsServiceLocked.checkAndGetTimeLocked();
                        UsageStatsService.this.mAppStandby.restoreAppsToRare(
                                userUsageStatsServiceLocked.mDatabase.applyRestoredPayload(
                                        str, bArr),
                                i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final int getAppStandbyBucket(int i, String str, long j) {
            return UsageStatsService.this.mAppStandby.getAppStandbyBucket(str, i, j, false);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final byte[] getBackupPayload(int i, String str) {
            if (!UsageStatsService.this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Failed to get backup payload for locked user ", "UsageStatsService");
                return null;
            }
            synchronized (UsageStatsService.this.mLock) {
                try {
                    UserUsageStatsService userUsageStatsServiceLocked =
                            UsageStatsService.this.getUserUsageStatsServiceLocked(i);
                    if (userUsageStatsServiceLocked == null) {
                        return null;
                    }
                    Slog.i("UsageStatsService", "Returning backup payload for u=" + i);
                    userUsageStatsServiceLocked.checkAndGetTimeLocked();
                    userUsageStatsServiceLocked.persistActiveStats();
                    return userUsageStatsServiceLocked.mDatabase.getBackupPayload(str, 4);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final void registerListener(
                UsageStatsManagerInternal.UsageEventListener usageEventListener) {
            UsageStatsService usageStatsService = UsageStatsService.this;
            synchronized (usageStatsService.mUsageEventListeners) {
                usageStatsService.mUsageEventListeners.add(usageEventListener);
            }
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final void reportEvent(int i, int i2, String str) {
            if (str == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i2,
                        "Event reported without a package name, eventType:",
                        "UsageStatsService");
                return;
            }
            UsageEvents.Event event = new UsageEvents.Event(i2, SystemClock.elapsedRealtime());
            event.mPackage = str;
            UsageStatsService.this.reportEventOrAddToQueue(i, event);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final void reportNotificationPosted(String str, UserHandle userHandle, long j) {
            UsageStatsService.this.mResponseStatsTracker.reportNotificationEvent(
                    0, str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final void reportNotificationUpdated(String str, UserHandle userHandle, long j) {
            UsageStatsService.this.mResponseStatsTracker.reportNotificationEvent(
                    1, str, userHandle, j);
        }

        @Override // android.app.usage.UsageStatsManagerInternal
        public final void reportUserInteractionEvent(
                String str, int i, PersistableBundle persistableBundle) {
            int size = persistableBundle.size();
            UsageStatsService usageStatsService = UsageStatsService.this;
            if (size != 0) {
                String string =
                        persistableBundle.getString("android.app.usage.extra.EVENT_CATEGORY");
                String string2 =
                        persistableBundle.getString("android.app.usage.extra.EVENT_ACTION");
                persistableBundle.putString(
                        "android.app.usage.extra.EVENT_CATEGORY",
                        UsageStatsService.m1008$$Nest$mgetTrimmedString(usageStatsService, string));
                persistableBundle.putString(
                        "android.app.usage.extra.EVENT_ACTION",
                        UsageStatsService.m1008$$Nest$mgetTrimmedString(
                                usageStatsService, string2));
            }
            UsageEvents.Event event = new UsageEvents.Event(7, SystemClock.elapsedRealtime());
            event.mPackage = str;
            event.mExtras = persistableBundle;
            usageStatsService.reportEventOrAddToQueue(i, event);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {}

        public final void onPackageRemoved(String str, int i) {
            int changingUserId = getChangingUserId();
            UsageStatsService usageStatsService = UsageStatsService.this;
            UserHandle of = UserHandle.of(changingUserId);
            DevicePolicyManagerInternal dpmInternal = usageStatsService.getDpmInternal();
            if (dpmInternal == null
                    || dpmInternal.getProfileOwnerOrDeviceOwnerSupervisionComponent(of) == null) {
                UsageStatsService.this
                        .mHandler
                        .obtainMessage(6, changingUserId, 0, str)
                        .sendToTarget();
            }
            BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                    UsageStatsService.this.mResponseStatsTracker;
            int userId = UserHandle.getUserId(i);
            synchronized (broadcastResponseStatsTracker.mLock) {
                try {
                    UserBroadcastEvents userBroadcastEvents =
                            (UserBroadcastEvents)
                                    broadcastResponseStatsTracker.mUserBroadcastEvents.get(userId);
                    if (userBroadcastEvents != null) {
                        userBroadcastEvents.mBroadcastEvents.remove(str);
                    }
                    for (int size = broadcastResponseStatsTracker.mUserResponseStats.size() - 1;
                            size >= 0;
                            size--) {
                        UserBroadcastResponseStats userBroadcastResponseStats =
                                (UserBroadcastResponseStats)
                                        ((SparseArray)
                                                        broadcastResponseStatsTracker
                                                                .mUserResponseStats.valueAt(size))
                                                .get(userId);
                        if (userBroadcastResponseStats != null) {
                            for (int size2 = userBroadcastResponseStats.mResponseStats.size() - 1;
                                    size2 >= 0;
                                    size2--) {
                                if (((BroadcastEvent)
                                                userBroadcastResponseStats.mResponseStats.keyAt(
                                                        size2))
                                        .mTargetPackage.equals(str)) {
                                    userBroadcastResponseStats.mResponseStats.removeAt(size2);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            super.onPackageRemoved(str, i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidRemovedReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UsageStatsService this$0;

        public /* synthetic */ UidRemovedReceiver(UsageStatsService usageStatsService, int i) {
            this.$r8$classId = i;
            this.this$0 = usageStatsService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                    if (intExtra != -1) {
                        this.this$0.mHandler.obtainMessage(10, intExtra, 0).sendToTarget();
                        break;
                    }
                    break;
                default:
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    String action = intent.getAction();
                    if (!"android.intent.action.USER_REMOVED".equals(action)) {
                        if ("android.intent.action.USER_STARTED".equals(action) && intExtra2 >= 0) {
                            if (!android.app.usage.Flags.disableIdleCheck() || intExtra2 > 0) {
                                this.this$0.mHandler.obtainMessage(11, intExtra2, 0).sendToTarget();
                                break;
                            }
                        }
                    } else if (intExtra2 >= 0) {
                        this.this$0.mHandler.obtainMessage(2, intExtra2, 0).sendToTarget();
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mgetTrimmedString, reason: not valid java name */
    public static String m1008$$Nest$mgetTrimmedString(
            UsageStatsService usageStatsService, String str) {
        usageStatsService.getClass();
        return (str == null || str.length() <= 127) ? str : str.substring(0, 127);
    }

    /* renamed from: -$$Nest$mloadGlobalComponentUsageLocked, reason: not valid java name */
    public static void m1009$$Nest$mloadGlobalComponentUsageLocked(
            UsageStatsService usageStatsService) {
        usageStatsService.getClass();
        AtomicFile atomicFile =
                new AtomicFile(new File(COMMON_USAGE_STATS_DIR, "globalcomponentusage"));
        if (!atomicFile.exists()) {
            atomicFile =
                    new AtomicFile(new File(LEGACY_COMMON_USAGE_STATS_DIR, "globalcomponentusage"));
            if (!atomicFile.exists()) {
                return;
            } else {
                Slog.i("UsageStatsService", "Reading globalcomponentusage file from old location");
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        try {
            FileInputStream openRead = atomicFile.openRead();
            try {
                UsageStatsProtoV2.readGlobalComponentUsage(openRead, arrayMap);
                if (openRead != null) {
                    openRead.close();
                }
                Map.Entry[] entryArr = (Map.Entry[]) arrayMap.entrySet().toArray();
                int length = entryArr.length;
                for (int i = 0; i < length; i++) {
                    usageStatsService.mLastTimeComponentUsedGlobal.putIfAbsent(
                            (String) entryArr[i].getKey(), (Long) entryArr[i].getValue());
                }
            } finally {
            }
        } catch (Exception unused) {
            Slog.e("UsageStatsService", "Could not read " + atomicFile.getBaseFile());
        }
    }

    /* renamed from: -$$Nest$monPackageRemoved, reason: not valid java name */
    public static void m1010$$Nest$monPackageRemoved(
            UsageStatsService usageStatsService, int i, String str) {
        synchronized (usageStatsService.mPendingLaunchTimeChangePackages) {
            try {
                ArraySet arraySet = usageStatsService.mPendingLaunchTimeChangePackages.get(i);
                if (arraySet != null) {
                    arraySet.remove(str);
                }
            } finally {
            }
        }
        synchronized (usageStatsService.mLaunchTimeAlarmQueues) {
            try {
                LaunchTimeAlarmQueue launchTimeAlarmQueue =
                        (LaunchTimeAlarmQueue) usageStatsService.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue != null) {
                    launchTimeAlarmQueue.removeAlarmForKey(str);
                }
            } finally {
            }
        }
        synchronized (usageStatsService.mLock) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                if (usageStatsService.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    UserUsageStatsService userUsageStatsService =
                            (UserUsageStatsService) usageStatsService.mUserState.get(i);
                    if (userUsageStatsService == null) {
                        return;
                    }
                    int onPackageRemoved =
                            userUsageStatsService.onPackageRemoved(currentTimeMillis, str);
                    if (onPackageRemoved != -1) {
                        Context context = usageStatsService.getContext();
                        int i2 = UsageStatsIdleService.$r8$clinit;
                        ComponentName componentName =
                                new ComponentName(
                                        context.getPackageName(),
                                        UsageStatsIdleService.class.getName());
                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putInt("user_id", i);
                        JobInfo build =
                                new JobInfo.Builder(i, componentName)
                                        .setRequiresDeviceIdle(true)
                                        .setExtras(persistableBundle)
                                        .setPersisted(true)
                                        .build();
                        JobScheduler forNamespace =
                                ((JobScheduler) context.getSystemService(JobScheduler.class))
                                        .forNamespace("usagestats_prune");
                        if (build.equals(forNamespace.getPendingJob(i))) {
                            return;
                        }
                        forNamespace.cancel(i);
                        forNamespace.schedule(build);
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$monUserUnlocked, reason: not valid java name */
    public static void m1011$$Nest$monUserUnlocked(UsageStatsService usageStatsService, int i) {
        HashMap installedPackages = usageStatsService.getInstalledPackages(i);
        Context context = usageStatsService.getContext();
        int i2 = UsageStatsIdleService.$r8$clinit;
        ComponentName componentName =
                new ComponentName(context.getPackageName(), UsageStatsIdleService.class.getName());
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt("user_id", i);
        JobInfo.Builder persisted = new JobInfo.Builder(i, componentName).setPersisted(true);
        TimeUnit timeUnit = TimeUnit.DAYS;
        JobInfo build =
                persisted
                        .setMinimumLatency(timeUnit.toMillis(1L))
                        .setOverrideDeadline(timeUnit.toMillis(2L))
                        .setExtras(persistableBundle)
                        .build();
        JobScheduler forNamespace =
                ((JobScheduler) context.getSystemService(JobScheduler.class))
                        .forNamespace("usagestats_mapping");
        if (!build.equals(forNamespace.getPendingJob(i))) {
            forNamespace.cancel(i);
            forNamespace.schedule(build);
        }
        UserHandle of = UserHandle.of(i);
        DevicePolicyManagerInternal dpmInternal = usageStatsService.getDpmInternal();
        boolean z =
                dpmInternal == null
                        || dpmInternal.getProfileOwnerOrDeviceOwnerSupervisionComponent(of) == null;
        synchronized (usageStatsService.mLock) {
            try {
                usageStatsService.mUserUnlockedStates.add(Integer.valueOf(i));
                UsageEvents.Event event = new UsageEvents.Event(28, SystemClock.elapsedRealtime());
                event.mPackage = "android";
                migrateStatsToSystemCeIfNeededLocked(i);
                LinkedList linkedList = new LinkedList();
                Trace.traceBegin(524288L, "loadPendingEvents");
                loadPendingEventsLocked(i, linkedList);
                Trace.traceEnd(524288L);
                synchronized (usageStatsService.mReportedEvents) {
                    LinkedList linkedList2 = (LinkedList) usageStatsService.mReportedEvents.get(i);
                    if (linkedList2 != null) {
                        linkedList.addAll(linkedList2);
                        usageStatsService.mReportedEvents.remove(i);
                    }
                }
                boolean z2 = !linkedList.isEmpty();
                usageStatsService.initializeUserUsageStatsServiceLocked(
                        i, System.currentTimeMillis(), installedPackages, z);
                UserUsageStatsService userUsageStatsServiceLocked =
                        usageStatsService.getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    Slog.i("UsageStatsService", "Attempted to unlock stopped or removed user " + i);
                    return;
                }
                while (linkedList.peek() != null) {
                    usageStatsService.reportEvent(i, (UsageEvents.Event) linkedList.poll());
                }
                usageStatsService.reportEvent(i, event);
                deleteRecursively(new File(Environment.getDataSystemDeDirectory(i), "usagestats"));
                if (z2) {
                    userUsageStatsServiceLocked.persistActiveStats();
                }
                usageStatsService.mIoHandler.obtainMessage(8, i, 0).sendToTarget();
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetEstimatedLaunchTimes, reason: not valid java name */
    public static void m1012$$Nest$msetEstimatedLaunchTimes(
            UsageStatsService usageStatsService, int i, List list) {
        usageStatsService.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            AppLaunchEstimateInfo appLaunchEstimateInfo = (AppLaunchEstimateInfo) list.get(size);
            if (appLaunchEstimateInfo.estimatedLaunchTime > currentTimeMillis) {
                long estimatedLaunchTime =
                        usageStatsService.mAppStandby.getEstimatedLaunchTime(
                                appLaunchEstimateInfo.packageName, i);
                long j = appLaunchEstimateInfo.estimatedLaunchTime;
                if (j != estimatedLaunchTime) {
                    usageStatsService.mAppStandby.setEstimatedLaunchTime(
                            appLaunchEstimateInfo.packageName, i, j);
                    z |=
                            usageStatsService.stageChangedEstimatedLaunchTime(
                                    i, appLaunchEstimateInfo.packageName);
                }
            }
        }
        if (z) {
            usageStatsService.mHandler.sendEmptyMessage(9);
        }
    }

    /* renamed from: -$$Nest$mshouldObfuscateInstantAppsForCaller, reason: not valid java name */
    public static boolean m1013$$Nest$mshouldObfuscateInstantAppsForCaller(
            UsageStatsService usageStatsService, int i, int i2) {
        return !((PackageManagerService.PackageManagerInternalImpl)
                        usageStatsService.mPackageManagerInternal)
                .mService
                .snapshotComputer()
                .canViewInstantApps(i, i2);
    }

    static {
        File file = new File(Environment.getDataSystemDirectory(), "usagestats");
        COMMON_USAGE_STATS_DIR = file;
        LEGACY_USER_USAGE_STATS_DIR = file;
        LEGACY_COMMON_USAGE_STATS_DIR =
                new File(Environment.getDataSystemDeDirectory(), "usagestats");
    }

    public UsageStatsService(Context context) {
        this(context, new Injector());
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.server.usage.UsageStatsService$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.server.usage.UsageStatsService$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.server.usage.UsageStatsService$3] */
    public UsageStatsService(Context context, Injector injector) {
        super(context);
        this.mLock = new Object();
        this.mUserState = new SparseArray();
        this.mUserUnlockedStates = new CopyOnWriteArraySet();
        this.mUidToKernelCounter = new SparseIntArray();
        this.mLastTimeComponentUsedGlobal = new ArrayMap();
        this.mUsageStatsWatchers = new HashMap();
        this.mUsageStatsWatchersComponent = new HashMap();
        this.mPackageMonitor = new MyPackageMonitor();
        this.mReportedEvents = new SparseArray();
        this.mUsageReporters = new SparseArray();
        this.mVisibleActivities = new SparseArray();
        this.mLaunchTimeAlarmQueues = new SparseArray();
        this.mUsageEventListeners = new ArraySet();
        this.mEstimatedLaunchTimeChangedListeners = new CopyOnWriteArraySet();
        this.mPendingLaunchTimeChangePackages = new SparseSetArray();
        this.mStandbyChangeListener =
                new AppStandbyInternal
                        .AppIdleStateChangeListener() { // from class:
                                                        // com.android.server.usage.UsageStatsService.1
                    public final void onAppIdleStateChanged(
                            String str, int i, boolean z, int i2, int i3) {
                        UsageEvents.Event event =
                                new UsageEvents.Event(11, SystemClock.elapsedRealtime());
                        event.mBucketAndReason =
                                (i2 << 16) | (i3 & GnssNative.GNSS_AIDING_TYPE_ALL);
                        event.mPackage = str;
                        UsageStatsService.this.reportEventOrAddToQueue(i, event);
                    }
                };
        this.mIoHandlerCallback =
                new Handler
                        .Callback() { // from class:
                                      // com.android.server.usage.UsageStatsService$$ExternalSyntheticLambda0
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.os.Handler.Callback
                    public final boolean handleMessage(Message message) {
                        UsageEvents usageEvents;
                        ArrayMap arrayMap;
                        UsageStatsService usageStatsService = UsageStatsService.this;
                        usageStatsService.getClass();
                        int i = message.what;
                        int i2 = 1;
                        if (i == 3) {
                            int i3 = message.arg1;
                            int i4 = message.arg2 <= 2 ? 0 : 1;
                            synchronized (usageStatsService.mUidToKernelCounter) {
                                if (i4 != usageStatsService.mUidToKernelCounter.get(i3, 0)) {
                                    usageStatsService.mUidToKernelCounter.put(i3, i4);
                                    try {
                                        FileUtils.stringToFile(
                                                UsageStatsService.KERNEL_COUNTER_FILE,
                                                i3 + " " + i4);
                                    } catch (IOException e) {
                                        Slog.w(
                                                "UsageStatsService",
                                                "Failed to update counter set: " + e);
                                    }
                                }
                            }
                        } else {
                            if (i != 8) {
                                if (i != 12) {
                                    return false;
                                }
                                int i5 = message.arg1;
                                UsageEvents.Event event = (UsageEvents.Event) message.obj;
                                synchronized (usageStatsService.mUsageEventListeners) {
                                    try {
                                        int size = usageStatsService.mUsageEventListeners.size();
                                        for (int i6 = 0; i6 < size; i6++) {
                                            ((UsageStatsManagerInternal.UsageEventListener)
                                                            usageStatsService.mUsageEventListeners
                                                                    .valueAt(i6))
                                                    .onUsageEvent(i5, event);
                                        }
                                    } finally {
                                    }
                                }
                                return true;
                            }
                            int i7 = message.arg1;
                            Trace.traceBegin(
                                    524288L,
                                    "usageStatsHandleEstimatedLaunchTimesOnUser(" + i7 + ")");
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            long currentTimeMillis = System.currentTimeMillis();
                            long j = currentTimeMillis - 604800000;
                            synchronized (usageStatsService.mLock) {
                                try {
                                    usageEvents = null;
                                    if (usageStatsService.mUserUnlockedStates.contains(
                                            Integer.valueOf(i7))) {
                                        UserUsageStatsService userUsageStatsServiceLocked =
                                                usageStatsService.getUserUsageStatsServiceLocked(
                                                        i7);
                                        if (userUsageStatsServiceLocked != null) {
                                            usageEvents =
                                                    userUsageStatsServiceLocked
                                                            .queryEarliestAppEvents(
                                                                    j, currentTimeMillis);
                                        }
                                    } else {
                                        Slog.w(
                                                "UsageStatsService",
                                                "Failed to query earliest events for locked user "
                                                        + i7);
                                    }
                                } finally {
                                }
                            }
                            if (usageEvents != null) {
                                ArrayMap arrayMap2 = new ArrayMap();
                                UsageEvents.Event event2 = new UsageEvents.Event();
                                UsageStatsService.LaunchTimeAlarmQueue
                                        orCreateLaunchTimeAlarmQueue =
                                                usageStatsService.getOrCreateLaunchTimeAlarmQueue(
                                                        i7);
                                boolean nextEvent = usageEvents.getNextEvent(event2);
                                boolean z = false;
                                while (nextEvent) {
                                    String packageName = event2.getPackageName();
                                    if (!arrayMap2.containsKey(packageName)) {
                                        arrayMap2.put(
                                                packageName,
                                                Boolean.valueOf(
                                                        currentTimeMillis - event2.getTimeStamp()
                                                                        > BackupManagerConstants
                                                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS
                                                                ? i2
                                                                : 0));
                                    }
                                    if (event2.getEventType() == i2) {
                                        long estimatedLaunchTime =
                                                usageStatsService.mAppStandby
                                                        .getEstimatedLaunchTime(packageName, i7);
                                        if (estimatedLaunchTime < currentTimeMillis
                                                || estimatedLaunchTime == Long.MAX_VALUE) {
                                            boolean booleanValue =
                                                    ((Boolean) arrayMap2.get(packageName))
                                                            .booleanValue();
                                            long timeStamp = event2.getTimeStamp();
                                            arrayMap = arrayMap2;
                                            long j2 =
                                                    booleanValue
                                                            ? timeStamp + 604800000
                                                            : timeStamp
                                                                    + BackupManagerConstants
                                                                            .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                                            usageStatsService.mAppStandby.setEstimatedLaunchTime(
                                                    packageName, i7, j2);
                                            estimatedLaunchTime = j2;
                                        } else {
                                            arrayMap = arrayMap2;
                                        }
                                        if (estimatedLaunchTime < currentTimeMillis + 604800000) {
                                            z |=
                                                    usageStatsService
                                                            .stageChangedEstimatedLaunchTime(
                                                                    i7, packageName);
                                        }
                                        orCreateLaunchTimeAlarmQueue.addAlarm(
                                                (estimatedLaunchTime - currentTimeMillis)
                                                        + elapsedRealtime,
                                                packageName);
                                    } else {
                                        arrayMap = arrayMap2;
                                    }
                                    nextEvent = usageEvents.getNextEvent(event2);
                                    arrayMap2 = arrayMap;
                                    i2 = 1;
                                }
                                if (z) {
                                    usageStatsService.mHandler.sendEmptyMessage(9);
                                }
                            }
                            Trace.traceEnd(524288L);
                        }
                        return true;
                    }
                };
        this.mUidObserver =
                new UidObserver() { // from class: com.android.server.usage.UsageStatsService.3
                    public final void onUidGone(int i, boolean z) {
                        onUidStateChanged(i, 20, 0L, 0);
                    }

                    public final void onUidStateChanged(int i, int i2, long j, int i3) {
                        UsageStatsService.this.mIoHandler.obtainMessage(3, i, i2).sendToTarget();
                    }
                };
        this.mInjector = injector;
    }

    public static String buildFullToken(String str, String str2) {
        StringBuilder sb = new StringBuilder(str2.length() + str.length() + 1);
        sb.append(str);
        sb.append('/');
        sb.append(str2);
        return sb.toString();
    }

    public static void copyRecursively(File file, File file2) {
        File file3;
        File[] listFiles = file2.listFiles();
        if (listFiles == null) {
            try {
                Files.copy(
                        file2.toPath(),
                        new File(file, file2.getName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                return;
            } catch (IOException e) {
                Slog.e(
                        "UsageStatsService",
                        "Failed to move usage stats file : " + file2.toString());
                throw new RuntimeException(e);
            }
        }
        for (int length = listFiles.length - 1; length >= 0; length--) {
            if (listFiles[length].isDirectory()) {
                file3 = new File(file, listFiles[length].getName());
                if (!file3.mkdirs() && !file3.exists()) {
                    throw new IllegalStateException(
                            "Failed to create usage stats directory during migration: "
                                    + file3.getAbsolutePath());
                }
            } else {
                file3 = file;
            }
            copyRecursively(file3, listFiles[length]);
        }
    }

    public static void deleteLegacyUserDir(int i) {
        File file = new File(LEGACY_USER_USAGE_STATS_DIR, Integer.toString(i));
        if (file.exists()) {
            deleteRecursively(file);
            if (file.exists()) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i,
                        "Error occurred while attempting to delete legacy usage stats dir for user"
                            + " ",
                        "UsageStatsService");
            }
        }
    }

    public static void deleteRecursively(File file) {
        File[] listFiles;
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                deleteRecursively(file2);
            }
        }
        if (!file.exists() || file.delete()) {
            return;
        }
        Slog.e("UsageStatsService", "Failed to delete " + file);
    }

    public static void loadPendingEventsLocked(int i, LinkedList linkedList) {
        FileInputStream openRead;
        File[] listFiles =
                new File(Environment.getDataSystemDeDirectory(i), "usagestats").listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        Arrays.sort(listFiles);
        int length = listFiles.length;
        for (int i2 = 0; i2 < length; i2++) {
            AtomicFile atomicFile = new AtomicFile(listFiles[i2]);
            LinkedList linkedList2 = new LinkedList();
            try {
                openRead = atomicFile.openRead();
            } catch (Exception unused) {
                Slog.e("UsageStatsService", "Could not read " + listFiles[i2] + " for user " + i);
            }
            try {
                int i3 = UsageStatsProtoV2.$r8$clinit;
                ProtoInputStream protoInputStream = new ProtoInputStream(openRead);
                while (true) {
                    int nextField = protoInputStream.nextField();
                    if (nextField == -1) {
                        break;
                    }
                    if (nextField == 23) {
                        try {
                            long start = protoInputStream.start(2246267895831L);
                            UsageEvents.Event parsePendingEvent =
                                    UsageStatsProtoV2.parsePendingEvent(protoInputStream);
                            protoInputStream.end(start);
                            if (parsePendingEvent != null) {
                                linkedList2.add(parsePendingEvent);
                            }
                        } catch (IOException e) {
                            Slog.e(
                                    "UsageStatsProtoV2",
                                    "Unable to parse some pending events from proto.",
                                    e);
                        }
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
                linkedList.addAll(linkedList2);
            } catch (Throwable th) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public static void logAppUsageEventReportedAtomLocked(int i, int i2, String str) {
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                i3 = 7;
                if (i != 7) {
                    i3 = 8;
                    if (i != 8) {
                        i3 = 9;
                        if (i != 9) {
                            i3 = 11;
                            if (i != 11) {
                                i3 = 19;
                                if (i != 19) {
                                    i3 = 20;
                                    if (i != 20) {
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                i,
                                                "Unsupported usage event logging: ",
                                                "UsageStatsService");
                                        i3 = -1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        FrameworkStatsLog.write(269, i2, str, "", i3);
    }

    public static void migrateStatsToSystemCeIfNeededLocked(int i) {
        File file = new File(Environment.getDataSystemCeDirectory(i), "usagestats");
        if (!file.mkdirs() && !file.exists()) {
            throw new IllegalStateException(
                    "Usage stats directory does not exist: " + file.getAbsolutePath());
        }
        File file2 = new File(file, "migrated");
        if (file2.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
                try {
                    if (Integer.parseInt(bufferedReader.readLine()) >= 4) {
                        deleteLegacyUserDir(i);
                        bufferedReader.close();
                        return;
                    }
                    bufferedReader.close();
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | NumberFormatException e) {
                Slog.e(
                        "UsageStatsService",
                        "Failed to read migration status file, possibly corrupted.");
                deleteRecursively(file);
                if (file.exists()) {
                    Slog.e("UsageStatsService", "Unable to delete usage stats CE directory.");
                    throw new RuntimeException(e);
                }
                if (!file.mkdirs() && !file.exists()) {
                    throw new IllegalStateException(
                            "Usage stats directory does not exist: " + file.getAbsolutePath());
                }
            }
        }
        Slog.i("UsageStatsService", "Starting migration to system CE for user " + i);
        File file3 = new File(LEGACY_USER_USAGE_STATS_DIR, Integer.toString(i));
        if (file3.exists()) {
            copyRecursively(file, file3);
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
            try {
                bufferedWriter.write(Integer.toString(4));
                bufferedWriter.write("\n");
                bufferedWriter.flush();
                bufferedWriter.close();
                Slog.i("UsageStatsService", "Finished migration to system CE for user " + i);
                deleteLegacyUserDir(i);
            } finally {
            }
        } catch (IOException e2) {
            Slog.e("UsageStatsService", "Failed to write migrated status file");
            throw new RuntimeException(e2);
        }
    }

    public final void convertToSystemTimeLocked(UsageEvents.Event event) {
        long currentTimeMillis = System.currentTimeMillis();
        if (ENABLE_TIME_CHANGE_CORRECTION) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j =
                    currentTimeMillis
                            - ((elapsedRealtime - this.mRealTimeSnapshot)
                                    + this.mSystemTimeSnapshot);
            if (Math.abs(j) > 2000) {
                Slog.i("UsageStatsService", "Time changed in by " + (j / 1000) + " seconds");
                this.mRealTimeSnapshot = elapsedRealtime;
                this.mSystemTimeSnapshot = currentTimeMillis;
            }
        }
        event.mTimeStamp =
                Math.max(0L, event.mTimeStamp - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot;
    }

    public final void flushToDisk() {
        synchronized (this.mLock) {
            UsageEvents.Event event = new UsageEvents.Event(25, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            reportEventToAllUserId(event);
            flushToDiskLocked();
        }
        this.mAppStandby.flushToDisk();
    }

    public final void flushToDiskLocked() {
        int size = this.mUserState.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mUserState.keyAt(i);
            if (this.mUserUnlockedStates.contains(Integer.valueOf(keyAt))) {
                UserUsageStatsService userUsageStatsService =
                        (UserUsageStatsService) this.mUserState.get(keyAt);
                if (userUsageStatsService != null) {
                    userUsageStatsService.persistActiveStats();
                }
            } else {
                persistPendingEventsLocked(keyAt);
            }
        }
        this.mHandler.removeMessages(1);
    }

    public final DevicePolicyManagerInternal getDpmInternal() {
        if (this.mDpmInternal == null) {
            this.mDpmInternal =
                    (DevicePolicyManagerInternal)
                            LocalServices.getService(DevicePolicyManagerInternal.class);
        }
        return this.mDpmInternal;
    }

    public final long getEstimatedPackageLaunchTime(int i, String str) {
        long estimatedLaunchTime = this.mAppStandby.getEstimatedLaunchTime(str, i);
        long currentTimeMillis = System.currentTimeMillis();
        if (estimatedLaunchTime < currentTimeMillis || estimatedLaunchTime == Long.MAX_VALUE) {
            long currentTimeMillis2 = System.currentTimeMillis();
            long j = currentTimeMillis2 - 604800000;
            long j2 = currentTimeMillis2 + 31536000000L;
            synchronized (this.mLock) {
                try {
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    UsageEvents usageEvents = null;
                    if (this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                        UserUsageStatsService userUsageStatsServiceLocked =
                                getUserUsageStatsServiceLocked(i);
                        if (userUsageStatsServiceLocked != null) {
                            usageEvents =
                                    userUsageStatsServiceLocked.queryEarliestEventsForPackage(
                                            j, currentTimeMillis2, str);
                        }
                    } else {
                        Slog.w(
                                "UsageStatsService",
                                "Failed to query earliest package events for locked user " + i);
                    }
                    if (usageEvents != null) {
                        UsageEvents.Event event = new UsageEvents.Event();
                        if (usageEvents.getNextEvent(event)) {
                            boolean z =
                                    currentTimeMillis2 - event.getTimeStamp()
                                            > BackupManagerConstants
                                                    .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                            do {
                                if (event.getEventType() == 1) {
                                    long timeStamp = event.getTimeStamp();
                                    long j3 =
                                            z
                                                    ? timeStamp + 604800000
                                                    : timeStamp
                                                            + BackupManagerConstants
                                                                    .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                                    if (j3 > currentTimeMillis2) {
                                        estimatedLaunchTime = j3;
                                        break;
                                    }
                                }
                            } while (usageEvents.getNextEvent(event));
                        }
                    }
                    estimatedLaunchTime = j2;
                    this.mAppStandby.setEstimatedLaunchTime(str, i, estimatedLaunchTime);
                    getOrCreateLaunchTimeAlarmQueue(i)
                            .addAlarm(
                                    (estimatedLaunchTime - currentTimeMillis)
                                            + SystemClock.elapsedRealtime(),
                                    str);
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return estimatedLaunchTime;
    }

    public final HashMap getInstalledPackages(int i) {
        PackageManager packageManager = this.mPackageManager;
        if (packageManager == null) {
            return null;
        }
        List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(8192, i);
        HashMap hashMap = new HashMap();
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(size);
            hashMap.put(packageInfo.packageName, Long.valueOf(packageInfo.firstInstallTime));
        }
        return hashMap;
    }

    public final LaunchTimeAlarmQueue getOrCreateLaunchTimeAlarmQueue(int i) {
        LaunchTimeAlarmQueue launchTimeAlarmQueue;
        synchronized (this.mLaunchTimeAlarmQueues) {
            try {
                launchTimeAlarmQueue = (LaunchTimeAlarmQueue) this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue == null) {
                    launchTimeAlarmQueue =
                            new LaunchTimeAlarmQueue(i, getContext(), this.mHandler.getLooper());
                    this.mLaunchTimeAlarmQueues.put(i, launchTimeAlarmQueue);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return launchTimeAlarmQueue;
    }

    public final UserUsageStatsService getUserUsageStatsServiceLocked(int i) {
        UserUsageStatsService userUsageStatsService =
                (UserUsageStatsService) this.mUserState.get(i);
        if (userUsageStatsService == null) {
            Slog.wtf(
                    "UsageStatsService",
                    "Failed to fetch usage stats service for user "
                            + i
                            + ". The user might not have been initialized yet.");
        }
        return userUsageStatsService;
    }

    public final void initializeUserUsageStatsServiceLocked(
            int i, long j, HashMap hashMap, boolean z) {
        UserUsageStatsService userUsageStatsService =
                new UserUsageStatsService(
                        getContext(),
                        i,
                        new File(Environment.getDataSystemCeDirectory(i), "usagestats"),
                        this);
        try {
            userUsageStatsService.init(j, hashMap, z);
            this.mUserState.put(i, userUsageStatsService);
        } catch (Exception e) {
            if (this.mUserManager.isUserUnlocked(i)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Failed to initialized unlocked user ", "UsageStatsService");
                throw e;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i,
                    "Attempted to initialize service for stopped or removed user ",
                    "UsageStatsService");
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        this.mAppStandby.onBootPhase(i);
        if (i == 500) {
            getDpmInternal();
            if (this.mShortcutServiceInternal == null) {
                this.mShortcutServiceInternal =
                        (ShortcutServiceInternal)
                                LocalServices.getService(ShortcutServiceInternal.class);
            }
            BroadcastResponseStatsTracker broadcastResponseStatsTracker =
                    this.mResponseStatsTracker;
            Context context = getContext();
            broadcastResponseStatsTracker.getClass();
            RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
            broadcastResponseStatsTracker.mRoleManager = roleManager;
            roleManager.addOnRoleHoldersChangedListenerAsUser(
                    BackgroundThread.getExecutor(),
                    broadcastResponseStatsTracker.mRoleHoldersChangedListener,
                    UserHandle.ALL);
            File file = KERNEL_COUNTER_FILE;
            if (file.exists()) {
                try {
                    ActivityManager.getService()
                            .registerUidObserver(this.mUidObserver, 3, -1, (String) null);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Slog.w("UsageStatsService", "Missing procfs interface: " + file);
            }
            readUsageSourceSetting();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        H h;
        UsageStatsHandlerThread usageStatsHandlerThread;
        this.mAppOps = (AppOpsManager) getContext().getSystemService("appops");
        this.mUserManager = (UserManager) getContext().getSystemService("user");
        this.mPackageManager = getContext().getPackageManager();
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        boolean z = USE_DEDICATED_HANDLER_THREAD;
        if (z) {
            synchronized (UsageStatsHandlerThread.sLock) {
                if (UsageStatsHandlerThread.sInstance == null) {
                    UsageStatsHandlerThread usageStatsHandlerThread2 =
                            new UsageStatsHandlerThread(0, "android.usagestats", true);
                    UsageStatsHandlerThread.sInstance = usageStatsHandlerThread2;
                    usageStatsHandlerThread2.start();
                    Looper looper = UsageStatsHandlerThread.sInstance.getLooper();
                    looper.setTraceTag(524288L);
                    looper.setSlowLogThresholdMs(10000L, 30000L);
                }
                usageStatsHandlerThread = UsageStatsHandlerThread.sInstance;
            }
            h = new H(usageStatsHandlerThread.getLooper());
        } else {
            h = new H(BackgroundThread.get().getLooper());
        }
        this.mHandler = h;
        this.mIoHandler = new Handler(IoThread.get().getLooper(), this.mIoHandlerCallback);
        Injector injector = this.mInjector;
        Context context = getContext();
        injector.getClass();
        AppStandbyInternal newAppStandbyController =
                AppStandbyInternal.newAppStandbyController(
                        UsageStatsService.class.getClassLoader(), context);
        this.mAppStandby = newAppStandbyController;
        this.mResponseStatsTracker =
                new BroadcastResponseStatsTracker(newAppStandbyController, getContext());
        this.mAppTimeLimit =
                new AppTimeLimitController(
                        getContext(), new AnonymousClass2(), this.mHandler.getLooper());
        this.mAppStandby.addListener(this.mStandbyChangeListener);
        MyPackageMonitor myPackageMonitor = this.mPackageMonitor;
        Context context2 = getContext();
        Looper looper2 = z ? this.mHandler.getLooper() : null;
        UserHandle userHandle = UserHandle.ALL;
        myPackageMonitor.register(context2, looper2, userHandle, true);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        getContext()
                .registerReceiverAsUser(
                        new UidRemovedReceiver(this, 1),
                        userHandle,
                        intentFilter,
                        null,
                        z ? this.mHandler : null);
        getContext()
                .registerReceiverAsUser(
                        new UidRemovedReceiver(this, 0),
                        userHandle,
                        new IntentFilter("android.intent.action.UID_REMOVED"),
                        null,
                        z ? this.mHandler : null);
        this.mRealTimeSnapshot = SystemClock.elapsedRealtime();
        this.mSystemTimeSnapshot = System.currentTimeMillis();
        publishLocalService(UsageStatsManagerInternal.class, new LocalService());
        publishLocalService(AppStandbyInternal.class, this.mAppStandby);
        publishBinderServices();
        this.mHandler.obtainMessage(7).sendToTarget();
    }

    public final void onUserRemoved(int i) {
        synchronized (this.mLock) {
            Slog.i("UsageStatsService", "Removing user " + i + " and all data.");
            this.mUserState.remove(i);
            AppTimeLimitController appTimeLimitController = this.mAppTimeLimit;
            synchronized (appTimeLimitController.mLock) {
                appTimeLimitController.mUsers.remove(i);
            }
        }
        synchronized (this.mLaunchTimeAlarmQueues) {
            try {
                LaunchTimeAlarmQueue launchTimeAlarmQueue =
                        (LaunchTimeAlarmQueue) this.mLaunchTimeAlarmQueues.get(i);
                if (launchTimeAlarmQueue != null) {
                    launchTimeAlarmQueue.removeAllAlarms();
                    this.mLaunchTimeAlarmQueues.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.mPendingLaunchTimeChangePackages) {
            this.mPendingLaunchTimeChangePackages.remove(i);
        }
        this.mAppStandby.onUserRemoved(i);
        this.mResponseStatsTracker.onUserRemoved(i);
        Context context = getContext();
        int i2 = UsageStatsIdleService.$r8$clinit;
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (jobScheduler != null) {
            jobScheduler.forNamespace("usagestats_prune").cancel(i);
        }
        JobScheduler jobScheduler2 =
                (JobScheduler) getContext().getSystemService(JobScheduler.class);
        if (jobScheduler2 != null) {
            jobScheduler2.forNamespace("usagestats_mapping").cancel(i);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        this.mUserState.put(targetUser.getUserIdentifier(), null);
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(Integer.valueOf(userIdentifier))) {
                    persistPendingEventsLocked(userIdentifier);
                    return;
                }
                UsageEvents.Event event = new UsageEvents.Event(29, SystemClock.elapsedRealtime());
                event.mPackage = "android";
                reportEvent(userIdentifier, event);
                UserUsageStatsService userUsageStatsService =
                        (UserUsageStatsService) this.mUserState.get(userIdentifier);
                if (userUsageStatsService != null) {
                    userUsageStatsService.persistActiveStats();
                    userUsageStatsService.mCachedEarlyEvents.clear();
                }
                this.mUserUnlockedStates.remove(Integer.valueOf(userIdentifier));
                this.mUserState.put(userIdentifier, null);
                synchronized (this.mLaunchTimeAlarmQueues) {
                    try {
                        LaunchTimeAlarmQueue launchTimeAlarmQueue =
                                (LaunchTimeAlarmQueue)
                                        this.mLaunchTimeAlarmQueues.get(userIdentifier);
                        if (launchTimeAlarmQueue != null) {
                            launchTimeAlarmQueue.removeAllAlarms();
                            this.mLaunchTimeAlarmQueues.remove(userIdentifier);
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(5, targetUser.getUserIdentifier(), 0).sendToTarget();
    }

    public final int parseUserIdFromArgs(
            String[] strArr, int i, IndentingPrintWriter indentingPrintWriter) {
        try {
            int parseInt = Integer.parseInt(strArr[i + 1]);
            if (this.mUserState.indexOfKey(parseInt) < 0) {
                indentingPrintWriter.println("the specified user does not exist.");
                return -10000;
            }
            if (this.mUserUnlockedStates.contains(Integer.valueOf(parseInt))) {
                return parseInt;
            }
            indentingPrintWriter.println("the specified user is currently in a locked state.");
            return -10000;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            indentingPrintWriter.println("invalid user specified.");
            return -10000;
        }
    }

    public final void persistGlobalComponentUsageLocked() {
        if (((ArrayMap) this.mLastTimeComponentUsedGlobal).isEmpty()) {
            return;
        }
        File file = COMMON_USAGE_STATS_DIR;
        if (!file.mkdirs() && !file.exists()) {
            throw new IllegalStateException(
                    "Common usage stats directory does not exist: " + file.getAbsolutePath());
        }
        File file2 = new File(file, "globalcomponentusage");
        AtomicFile atomicFile = new AtomicFile(file2);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    UsageStatsProtoV2.writeGlobalComponentUsage(
                            this.mLastTimeComponentUsedGlobal, startWrite);
                    atomicFile.finishWrite(startWrite);
                } catch (Exception unused) {
                    fileOutputStream = startWrite;
                    Slog.e("UsageStatsService", "Failed to write " + file2.getAbsolutePath());
                    atomicFile.failWrite(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    atomicFile.failWrite(fileOutputStream);
                    throw th;
                }
            } catch (Exception unused2) {
            }
            atomicFile.failWrite(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void persistPendingEventsLocked(int i) {
        LinkedList linkedList = (LinkedList) this.mReportedEvents.get(i);
        if (linkedList == null || linkedList.isEmpty()) {
            return;
        }
        File dataSystemDeDirectory = Environment.getDataSystemDeDirectory(i);
        File file = new File(dataSystemDeDirectory, "usagestats");
        if (!file.mkdir() && !file.exists()) {
            if (dataSystemDeDirectory.exists()) {
                Slog.e("UsageStatsService", "Failed to create " + file);
                return;
            } else {
                Slog.w(
                        "UsageStatsService",
                        "User " + i + " was already removed! Discarding pending events");
                linkedList.clear();
                return;
            }
        }
        File file2 = new File(file, "pendingevents_" + System.currentTimeMillis());
        AtomicFile atomicFile = new AtomicFile(file2);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    UsageStatsProtoV2.writePendingEvents(startWrite, linkedList);
                    atomicFile.finishWrite(startWrite);
                    linkedList.clear();
                } catch (Exception unused) {
                    fileOutputStream = startWrite;
                    Slog.e(
                            "UsageStatsService",
                            "Failed to write " + file2.getAbsolutePath() + " for user " + i);
                    atomicFile.failWrite(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    atomicFile.failWrite(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused2) {
        }
        atomicFile.failWrite(fileOutputStream);
    }

    public void publishBinderServices() {
        publishBinderService("usagestats", new BinderService());
    }

    public final List queryConfigurationStats(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            try {
                List list = null;
                if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    Slog.w(
                            "UsageStatsService",
                            "Failed to query configuration stats for locked user " + i);
                    return null;
                }
                UserUsageStatsService userUsageStatsServiceLocked =
                        getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                if (UserUsageStatsService.validRange(
                        userUsageStatsServiceLocked.checkAndGetTimeLocked(), j, j2)) {
                    list =
                            userUsageStatsServiceLocked.queryStats(
                                    i2, j, j2, UserUsageStatsService.sConfigStatsCombiner, true);
                }
                return list;
            } finally {
            }
        }
    }

    public final List queryEventStats(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            try {
                List list = null;
                if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    Slog.w("UsageStatsService", "Failed to query event stats for locked user " + i);
                    return null;
                }
                UserUsageStatsService userUsageStatsServiceLocked =
                        getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                if (UserUsageStatsService.validRange(
                        userUsageStatsServiceLocked.checkAndGetTimeLocked(), j, j2)) {
                    list =
                            userUsageStatsServiceLocked.queryStats(
                                    i2, j, j2, UserUsageStatsService.sEventStatsCombiner, true);
                }
                return list;
            } finally {
            }
        }
    }

    public final UsageEvents queryEventsForPackage(int i, String str, long j, long j2, boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    Slog.w(
                            "UsageStatsService",
                            "Failed to query package events for locked user " + i);
                    return null;
                }
                UserUsageStatsService userUsageStatsServiceLocked =
                        getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEventsForPackage(j, j2, str, z);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final UsageEvents queryEventsWithQueryFilters(
            int i, long j, long j2, int i2, int[] iArr, ArraySet arraySet) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    Slog.w("UsageStatsService", "Failed to query events for locked user " + i);
                    return null;
                }
                UserUsageStatsService userUsageStatsServiceLocked =
                        getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                return userUsageStatsServiceLocked.queryEvents(j, j2, i2, iArr, arraySet);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List queryUsageStats(int i, long j, long j2, int i2, boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
                    Slog.w("UsageStatsService", "Failed to query usage stats for locked user " + i);
                    return null;
                }
                UserUsageStatsService userUsageStatsServiceLocked =
                        getUserUsageStatsServiceLocked(i);
                if (userUsageStatsServiceLocked == null) {
                    return null;
                }
                List queryStats =
                        !UserUsageStatsService.validRange(
                                        userUsageStatsServiceLocked.checkAndGetTimeLocked(), j, j2)
                                ? null
                                : userUsageStatsServiceLocked.queryStats(
                                        i2, j, j2, UserUsageStatsService.sUsageStatsCombiner, true);
                if (queryStats == null) {
                    return null;
                }
                if (z) {
                    for (int size = queryStats.size() - 1; size >= 0; size--) {
                        UsageStats usageStats = (UsageStats) queryStats.get(size);
                        if (this.mPackageManagerInternal.isPackageEphemeral(
                                i, usageStats.mPackageName)) {
                            queryStats.set(size, usageStats.getObfuscatedForInstantApp());
                        }
                    }
                }
                return queryStats;
            } finally {
            }
        }
    }

    public final void readUsageSourceSetting() {
        synchronized (this.mLock) {
            this.mUsageSource =
                    Settings.Global.getInt(
                            getContext().getContentResolver(), "app_time_limit_usage_source", 2);
        }
    }

    public final void registerAppUsageLimitObserver(
            int i, int i2, String[] strArr, long j, long j2, PendingIntent pendingIntent, int i3) {
        AppTimeLimitController appTimeLimitController = this.mAppTimeLimit;
        if (j < appTimeLimitController.getMinTimeLimit()) {
            throw new IllegalArgumentException(
                    "Time limit must be >= " + appTimeLimitController.getMinTimeLimit());
        }
        synchronized (appTimeLimitController.mLock) {
            try {
                AppTimeLimitController.UserData orCreateUserDataLocked =
                        appTimeLimitController.getOrCreateUserDataLocked(i3);
                AppTimeLimitController.ObserverAppData orCreateObserverAppDataLocked =
                        appTimeLimitController.getOrCreateObserverAppDataLocked(i);
                AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup =
                        (AppTimeLimitController.AppUsageLimitGroup)
                                orCreateObserverAppDataLocked.appUsageLimitGroups.get(i2);
                if (appUsageLimitGroup != null) {
                    appUsageLimitGroup.remove();
                }
                if (orCreateObserverAppDataLocked.appUsageLimitGroups.size()
                        >= appTimeLimitController.getAppUsageLimitObserverPerUidLimit()) {
                    throw new IllegalStateException(
                            "Too many app usage observers added by uid " + i);
                }
                AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup2 =
                        appTimeLimitController
                        .new AppUsageLimitGroup(
                                orCreateUserDataLocked,
                                orCreateObserverAppDataLocked,
                                i2,
                                strArr,
                                j,
                                j2,
                                j2 >= j ? null : pendingIntent);
                orCreateObserverAppDataLocked.appUsageLimitGroups.append(i2, appUsageLimitGroup2);
                orCreateUserDataLocked.addUsageGroup(appUsageLimitGroup2);
                AppTimeLimitController.noteActiveLocked(
                        orCreateUserDataLocked,
                        appUsageLimitGroup2,
                        appTimeLimitController.getElapsedRealtime());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerAppUsageObserver(
            int i, int i2, String[] strArr, long j, PendingIntent pendingIntent, int i3) {
        AppTimeLimitController appTimeLimitController = this.mAppTimeLimit;
        if (j < appTimeLimitController.getMinTimeLimit()) {
            throw new IllegalArgumentException(
                    "Time limit must be >= " + appTimeLimitController.getMinTimeLimit());
        }
        synchronized (appTimeLimitController.mLock) {
            try {
                AppTimeLimitController.UserData orCreateUserDataLocked =
                        appTimeLimitController.getOrCreateUserDataLocked(i3);
                AppTimeLimitController.ObserverAppData orCreateObserverAppDataLocked =
                        appTimeLimitController.getOrCreateObserverAppDataLocked(i);
                AppTimeLimitController.AppUsageGroup appUsageGroup =
                        (AppTimeLimitController.AppUsageGroup)
                                orCreateObserverAppDataLocked.appUsageGroups.get(i2);
                if (appUsageGroup != null) {
                    appUsageGroup.remove();
                }
                if (orCreateObserverAppDataLocked.appUsageGroups.size()
                        >= appTimeLimitController.getAppUsageObserverPerUidLimit()) {
                    throw new IllegalStateException(
                            "Too many app usage observers added by uid " + i);
                }
                AppTimeLimitController.AppUsageGroup appUsageGroup2 =
                        appTimeLimitController
                        .new AppUsageGroup(
                                orCreateUserDataLocked,
                                orCreateObserverAppDataLocked,
                                i2,
                                strArr,
                                j,
                                pendingIntent);
                orCreateObserverAppDataLocked.appUsageGroups.append(i2, appUsageGroup2);
                orCreateUserDataLocked.addUsageGroup(appUsageGroup2);
                AppTimeLimitController.noteActiveLocked(
                        orCreateUserDataLocked,
                        appUsageGroup2,
                        appTimeLimitController.getElapsedRealtime());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerUsageSessionObserver(
            int i,
            int i2,
            String[] strArr,
            long j,
            long j2,
            PendingIntent pendingIntent,
            PendingIntent pendingIntent2,
            int i3) {
        AppTimeLimitController appTimeLimitController = this.mAppTimeLimit;
        if (j < appTimeLimitController.getMinTimeLimit()) {
            throw new IllegalArgumentException(
                    "Time limit must be >= " + appTimeLimitController.getMinTimeLimit());
        }
        synchronized (appTimeLimitController.mLock) {
            try {
                AppTimeLimitController.UserData orCreateUserDataLocked =
                        appTimeLimitController.getOrCreateUserDataLocked(i3);
                AppTimeLimitController.ObserverAppData orCreateObserverAppDataLocked =
                        appTimeLimitController.getOrCreateObserverAppDataLocked(i);
                AppTimeLimitController.SessionUsageGroup sessionUsageGroup =
                        (AppTimeLimitController.SessionUsageGroup)
                                orCreateObserverAppDataLocked.sessionUsageGroups.get(i2);
                if (sessionUsageGroup != null) {
                    sessionUsageGroup.remove();
                }
                if (orCreateObserverAppDataLocked.sessionUsageGroups.size()
                        >= appTimeLimitController.getUsageSessionObserverPerUidLimit()) {
                    throw new IllegalStateException(
                            "Too many app usage observers added by uid " + i);
                }
                AppTimeLimitController.SessionUsageGroup sessionUsageGroup2 =
                        appTimeLimitController
                        .new SessionUsageGroup(
                                orCreateUserDataLocked,
                                orCreateObserverAppDataLocked,
                                i2,
                                strArr,
                                j,
                                pendingIntent,
                                j2,
                                pendingIntent2);
                orCreateObserverAppDataLocked.sessionUsageGroups.append(i2, sessionUsageGroup2);
                orCreateUserDataLocked.addUsageGroup(sessionUsageGroup2);
                AppTimeLimitController.noteActiveLocked(
                        orCreateUserDataLocked,
                        sessionUsageGroup2,
                        appTimeLimitController.getElapsedRealtime());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x024b A[Catch: all -> 0x0090, DONT_GENERATE, TryCatch #1 {all -> 0x0090, blocks: (B:24:0x0044, B:26:0x0050, B:27:0x008e, B:29:0x0093, B:47:0x0245, B:49:0x024b, B:51:0x024d, B:52:0x0250, B:55:0x017f, B:56:0x00b8, B:57:0x00ba, B:59:0x00c6, B:60:0x00fc, B:62:0x00fe, B:64:0x0102, B:65:0x0107, B:66:0x0109, B:71:0x0117, B:90:0x0150, B:91:0x0151, B:93:0x0155, B:95:0x015d, B:98:0x0167, B:102:0x0172, B:103:0x0173, B:104:0x017a, B:105:0x0193, B:107:0x019f, B:109:0x01a3, B:111:0x01a8, B:112:0x01b6, B:113:0x01cd, B:115:0x01d3, B:118:0x01af, B:119:0x01a6, B:120:0x01c8, B:121:0x01dc, B:124:0x01f0, B:126:0x01f4, B:128:0x01f9, B:129:0x0207, B:131:0x0229, B:133:0x0231, B:135:0x0240, B:138:0x0200, B:139:0x01f7, B:68:0x010a, B:69:0x0114, B:73:0x0118, B:75:0x011f, B:77:0x0125, B:80:0x014a, B:82:0x0134, B:85:0x014d), top: B:23:0x0044, inners: #0, #3, #4, #5, #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x024d A[Catch: all -> 0x0090, TryCatch #1 {all -> 0x0090, blocks: (B:24:0x0044, B:26:0x0050, B:27:0x008e, B:29:0x0093, B:47:0x0245, B:49:0x024b, B:51:0x024d, B:52:0x0250, B:55:0x017f, B:56:0x00b8, B:57:0x00ba, B:59:0x00c6, B:60:0x00fc, B:62:0x00fe, B:64:0x0102, B:65:0x0107, B:66:0x0109, B:71:0x0117, B:90:0x0150, B:91:0x0151, B:93:0x0155, B:95:0x015d, B:98:0x0167, B:102:0x0172, B:103:0x0173, B:104:0x017a, B:105:0x0193, B:107:0x019f, B:109:0x01a3, B:111:0x01a8, B:112:0x01b6, B:113:0x01cd, B:115:0x01d3, B:118:0x01af, B:119:0x01a6, B:120:0x01c8, B:121:0x01dc, B:124:0x01f0, B:126:0x01f4, B:128:0x01f9, B:129:0x0207, B:131:0x0229, B:133:0x0231, B:135:0x0240, B:138:0x0200, B:139:0x01f7, B:68:0x010a, B:69:0x0114, B:73:0x0118, B:75:0x011f, B:77:0x0125, B:80:0x014a, B:82:0x0134, B:85:0x014d), top: B:23:0x0044, inners: #0, #3, #4, #5, #6 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportEvent(int r14, android.app.usage.UsageEvents.Event r15) {
        /*
            Method dump skipped, instructions count: 607
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.usage.UsageStatsService.reportEvent(int,"
                    + " android.app.usage.UsageEvents$Event):void");
    }

    public final void reportEventOrAddToQueue(int i, UsageEvents.Event event) {
        if (this.mUserUnlockedStates.contains(Integer.valueOf(i))) {
            this.mHandler.obtainMessage(0, i, 0, event).sendToTarget();
            return;
        }
        if (Trace.isTagEnabled(524288L)) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "usageStatsQueueEvent(", ") #");
            m.append(UserUsageStatsService.eventToString(event.mEventType));
            Trace.traceBegin(524288L, m.toString());
        }
        synchronized (this.mReportedEvents) {
            try {
                LinkedList linkedList = (LinkedList) this.mReportedEvents.get(i);
                if (linkedList == null) {
                    linkedList = new LinkedList();
                    this.mReportedEvents.put(i, linkedList);
                }
                linkedList.add(event);
                if (linkedList.size() == 1) {
                    this.mHandler.sendEmptyMessageDelayed(1, 1200000L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.traceEnd(524288L);
    }

    public final void reportEventToAllUserId(UsageEvents.Event event) {
        synchronized (this.mLock) {
            try {
                int size = this.mUserState.size();
                for (int i = 0; i < size; i++) {
                    reportEventOrAddToQueue(this.mUserState.keyAt(i), new UsageEvents.Event(event));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean stageChangedEstimatedLaunchTime(int i, String str) {
        boolean add;
        synchronized (this.mPendingLaunchTimeChangePackages) {
            add = this.mPendingLaunchTimeChangePackages.add(i, str);
        }
        return add;
    }
}
