package com.android.server;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Environment;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Dumpable;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.os.SystemServerClassLoaderFactory;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.TimingsTraceAndSlog;

import com.samsung.isrb.IsrbHooks;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class SystemServiceManager implements Dumpable {
    public static volatile int sOtherServicesStartIndex;
    public static File sSystemDir;
    public final Context mContext;
    public SystemService.TargetUser mCurrentUser;
    public boolean mRuntimeRestarted;
    public long mRuntimeStartElapsedTime;
    public long mRuntimeStartUptime;
    public boolean mSafeMode;
    public UserManagerInternal mUserManagerInternal;
    public int mCurrentPhase = -1;
    public final SparseArray mTargetUsers = new SparseArray();
    public List mServices = new ArrayList();
    public Set mServiceClassnames = new ArraySet();
    public final int mNumUserPoolThreads = Math.min(Runtime.getRuntime().availableProcessors(), 3);

    public SystemServiceManager(Context context) {
        this.mContext = context;
    }

    public static File ensureSystemDir() {
        if (sSystemDir == null) {
            File file = new File(Environment.getDataDirectory(), "system");
            sSystemDir = file;
            file.mkdirs();
        }
        return sSystemDir;
    }

    public static Class loadClassFromLoader(String str, ClassLoader classLoader) {
        try {
            return Class.forName(str, true, classLoader);
        } catch (ClassNotFoundException e) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Failed to create service ", str, " from class loader ");
            m.append(classLoader.toString());
            m.append(
                    ": service class not found, usually indicates that the caller should have"
                        + " called PackageManager.hasSystemFeature() to check whether the feature"
                        + " is available on this device before trying to start the services that"
                        + " implement it. Also ensure that the correct path for the classloader is"
                        + " supplied, if applicable.");
            throw new RuntimeException(m.toString(), e);
        }
    }

    public static void logFailure(
            String str, SystemService.TargetUser targetUser, String str2, Exception exc) {
        Slog.wtf(
                "SystemServiceManager",
                "SystemService failure: Failure reporting "
                        + str
                        + " of user "
                        + targetUser
                        + " to service "
                        + str2,
                exc);
    }

    public static void warnIfTooLong(long j, SystemService systemService, String str, int i) {
        if (j > 50) {
            String name = systemService.getClass().getName();
            StringBuilder sb = new StringBuilder("Service ");
            sb.append(name);
            sb.append(" took ");
            sb.append(j);
            ProfileService$1$$ExternalSyntheticOutline0.m(
                    sb, " ms in ", str, "SystemServiceManager");
            if (i == 0 || 200 >= j) {
                return;
            }
            StringBuilder m =
                    SystemServiceManager$$ExternalSyntheticOutline0.m(
                            i, "!@Boot_SystemServer: ", j, "ms : (");
            m.append(") ");
            m.append(name);
            Slog.d("SystemServiceManager", m.toString());
            Slog.i(
                    "SystemServiceManager",
                    "!@Boot_EBS:   Took " + j + "ms by '" + name + "' (" + i + ")");
        }
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        printWriter.printf("Current phase: %d\n", Integer.valueOf(this.mCurrentPhase));
        synchronized (this.mTargetUsers) {
            try {
                if (this.mCurrentUser != null) {
                    printWriter.print("Current user: ");
                    this.mCurrentUser.dump(printWriter);
                    printWriter.println();
                } else {
                    printWriter.println("Current user not set!");
                }
                int size = this.mTargetUsers.size();
                if (size > 0) {
                    printWriter.printf("%d target users: ", Integer.valueOf(size));
                    for (int i2 = 0; i2 < size; i2++) {
                        ((SystemService.TargetUser) this.mTargetUsers.valueAt(i2))
                                .dump(printWriter);
                        if (i2 != size - 1) {
                            printWriter.print(", ");
                        }
                    }
                    printWriter.println();
                } else {
                    printWriter.println("No target users");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int size2 = this.mServices.size();
        if (size2 <= 0) {
            printWriter.println("No started services");
            return;
        }
        printWriter.printf("%d started services:\n", Integer.valueOf(size2));
        for (i = 0; i < size2; i++) {
            SystemService systemService = (SystemService) this.mServices.get(i);
            printWriter.print("  ");
            printWriter.println(systemService.getClass().getCanonicalName());
        }
    }

    @Override // android.util.Dumpable
    public final String getDumpableName() {
        return "SystemServiceManager";
    }

    public final long getRuntimeStartUptime() {
        return this.mRuntimeStartUptime;
    }

    public final SystemService.TargetUser getTargetUser(int i) {
        SystemService.TargetUser targetUser;
        synchronized (this.mTargetUsers) {
            targetUser = (SystemService.TargetUser) this.mTargetUsers.get(i);
        }
        return targetUser;
    }

    public final boolean isBootCompleted() {
        return this.mCurrentPhase >= 1000;
    }

    public final SystemService.TargetUser newTargetUser(int i) {
        UserInfo userInfo = this.mUserManagerInternal.getUserInfo(i);
        Preconditions.checkState(userInfo != null, "No UserInfo for " + i);
        return new SystemService.TargetUser(userInfo);
    }

    public final void onUser(int i, String str) {
        SystemService.TargetUser targetUser = getTargetUser(i);
        Preconditions.checkState(targetUser != null, "No TargetUser for " + i);
        onUser(TimingsTraceAndSlog.newAsyncLog(), str, null, targetUser, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Can't wrap try/catch for region: R(7:(3:22|(2:24|26)(2:119|26)|(6:28|(1:30)|31|32|33|34))|121|(0)|31|32|33|34) */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x025c, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x025d, code lost:

       r21 = r1;
       r17 = r4;
       r24 = r5;
       r25 = r13;
       r22 = r14;
       r23 = r15;
       r1 = r26;
       r14 = r2;
       r15 = r6;
       r13 = r7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00db, code lost:

       r0 = true;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x014c A[Catch: Exception -> 0x0112, TRY_LEAVE, TryCatch #8 {Exception -> 0x0112, blocks: (B:109:0x010a, B:35:0x0126, B:92:0x012e, B:95:0x0138, B:98:0x0142, B:101:0x014c), top: B:108:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0156 A[Catch: Exception -> 0x025c, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x025c, blocks: (B:33:0x0102, B:104:0x0156), top: B:32:0x0102 }] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x010a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0126 A[Catch: Exception -> 0x0112, TryCatch #8 {Exception -> 0x0112, blocks: (B:109:0x010a, B:35:0x0126, B:92:0x012e, B:95:0x0138, B:98:0x0142, B:101:0x014c), top: B:108:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01cf A[Catch: Exception -> 0x01b8, TryCatch #7 {Exception -> 0x01b8, blocks: (B:44:0x01ad, B:58:0x01cf, B:59:0x01e2, B:60:0x01f5, B:61:0x0208), top: B:43:0x01ad }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01e2 A[Catch: Exception -> 0x01b8, TryCatch #7 {Exception -> 0x01b8, blocks: (B:44:0x01ad, B:58:0x01cf, B:59:0x01e2, B:60:0x01f5, B:61:0x0208), top: B:43:0x01ad }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01f5 A[Catch: Exception -> 0x01b8, TryCatch #7 {Exception -> 0x01b8, blocks: (B:44:0x01ad, B:58:0x01cf, B:59:0x01e2, B:60:0x01f5, B:61:0x0208), top: B:43:0x01ad }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0208 A[Catch: Exception -> 0x01b8, TRY_LEAVE, TryCatch #7 {Exception -> 0x01b8, blocks: (B:44:0x01ad, B:58:0x01cf, B:59:0x01e2, B:60:0x01f5, B:61:0x0208), top: B:43:0x01ad }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0246 A[Catch: Exception -> 0x0238, TRY_LEAVE, TryCatch #6 {Exception -> 0x0238, blocks: (B:69:0x0231, B:64:0x023f, B:76:0x0246), top: B:68:0x0231 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0164 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x012e A[Catch: Exception -> 0x0112, TryCatch #8 {Exception -> 0x0112, blocks: (B:109:0x010a, B:35:0x0126, B:92:0x012e, B:95:0x0138, B:98:0x0142, B:101:0x014c), top: B:108:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0138 A[Catch: Exception -> 0x0112, TryCatch #8 {Exception -> 0x0112, blocks: (B:109:0x010a, B:35:0x0126, B:92:0x012e, B:95:0x0138, B:98:0x0142, B:101:0x014c), top: B:108:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0142 A[Catch: Exception -> 0x0112, TryCatch #8 {Exception -> 0x0112, blocks: (B:109:0x010a, B:35:0x0126, B:92:0x012e, B:95:0x0138, B:98:0x0142, B:101:0x014c), top: B:108:0x010a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUser(
            final com.android.server.utils.TimingsTraceAndSlog r27,
            java.lang.String r28,
            com.android.server.SystemService.TargetUser r29,
            final com.android.server.SystemService.TargetUser r30,
            final com.android.server.SystemService.UserCompletedEventType r31) {
        /*
            Method dump skipped, instructions count: 754
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.SystemServiceManager.onUser(com.android.server.utils.TimingsTraceAndSlog,"
                    + " java.lang.String, com.android.server.SystemService$TargetUser,"
                    + " com.android.server.SystemService$TargetUser,"
                    + " com.android.server.SystemService$UserCompletedEventType):void");
    }

    public final void onUserSwitching(int i, int i2) {
        SystemService.TargetUser targetUser;
        SystemService.TargetUser targetUser2;
        SystemService.TargetUser targetUser3;
        EventLog.writeEvent(30083, Integer.valueOf(i), Integer.valueOf(i2));
        synchronized (this.mTargetUsers) {
            try {
                SystemService.TargetUser targetUser4 = this.mCurrentUser;
                if (targetUser4 == null) {
                    targetUser = newTargetUser(i);
                } else {
                    if (i != targetUser4.getUserIdentifier()) {
                        Slog.wtf(
                                "SystemServiceManager",
                                "switchUser("
                                        + i
                                        + ","
                                        + i2
                                        + "): mCurrentUser is "
                                        + this.mCurrentUser
                                        + ", it should be "
                                        + i);
                    }
                    targetUser = this.mCurrentUser;
                }
                targetUser2 = targetUser;
                targetUser3 = getTargetUser(i2);
                this.mCurrentUser = targetUser3;
                Preconditions.checkState(targetUser3 != null, "No TargetUser for " + i2);
            } catch (Throwable th) {
                throw th;
            }
        }
        onUser(TimingsTraceAndSlog.newAsyncLog(), "Switch", targetUser2, targetUser3, null);
    }

    public final void setSafeMode(boolean z) {
        this.mSafeMode = z;
    }

    public final void startBootPhase(TimingsTraceAndSlog timingsTraceAndSlog, int i) {
        SystemServerInitThreadPool systemServerInitThreadPool;
        if (i <= this.mCurrentPhase) {
            throw new IllegalArgumentException("Next phase must be larger than previous");
        }
        this.mCurrentPhase = i;
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                new StringBuilder("Starting phase "), this.mCurrentPhase, "SystemServiceManager");
        try {
            timingsTraceAndSlog.traceBegin("OnBootPhase_" + i);
            int size = this.mServices.size();
            for (int i2 = 0; i2 < size; i2++) {
                SystemService systemService = (SystemService) this.mServices.get(i2);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                timingsTraceAndSlog.traceBegin(
                        "OnBootPhase_" + i + "_" + systemService.getClass().getName());
                if (!SystemProperties.getBoolean("sys.isrb.wificrash", false)
                        || systemService.getClass().getName().indexOf("Wifi") < 0) {
                    try {
                        systemService.onBootPhase(this.mCurrentPhase);
                        warnIfTooLong(
                                SystemClock.elapsedRealtime() - elapsedRealtime,
                                systemService,
                                "onBootPhase",
                                i);
                        timingsTraceAndSlog.traceEnd();
                    } catch (Exception e) {
                        if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())) {
                            Slog.d("SystemServiceManager", "startBootPhase isrb setname ");
                            IsrbHooks.saveCrashServiceName(systemService.getClass().getName());
                        }
                        throw new RuntimeException(
                                "Failed to boot service "
                                        + systemService.getClass().getName()
                                        + ": onBootPhase threw an exception during phase "
                                        + this.mCurrentPhase,
                                e);
                    }
                } else {
                    warnIfTooLong(
                            SystemClock.elapsedRealtime() - elapsedRealtime,
                            systemService,
                            "onBootPhase",
                            i);
                    timingsTraceAndSlog.traceEnd();
                }
            }
            timingsTraceAndSlog.traceEnd();
            if (i == 1000) {
                timingsTraceAndSlog.logDuration(
                        "TotalBootTime", SystemClock.uptimeMillis() - this.mRuntimeStartUptime);
                boolean z = SystemServerInitThreadPool.IS_DEBUGGABLE;
                Slog.d("SystemServerInitThreadPool", "Shutdown requested");
                synchronized (SystemServerInitThreadPool.LOCK) {
                    try {
                        TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog();
                        timingsTraceAndSlog2.traceBegin("WaitInitThreadPoolShutdown");
                        SystemServerInitThreadPool systemServerInitThreadPool2 =
                                SystemServerInitThreadPool.sInstance;
                        if (systemServerInitThreadPool2 == null) {
                            timingsTraceAndSlog2.traceEnd();
                            Slog.wtf(
                                    "SystemServerInitThreadPool",
                                    "Already shutdown",
                                    new Exception());
                            return;
                        }
                        synchronized (systemServerInitThreadPool2.mPendingTasks) {
                            systemServerInitThreadPool = SystemServerInitThreadPool.sInstance;
                            systemServerInitThreadPool.mShutDown = true;
                        }
                        systemServerInitThreadPool.mService.shutdown();
                        try {
                            boolean awaitTermination =
                                    SystemServerInitThreadPool.sInstance.mService.awaitTermination(
                                            20000L, TimeUnit.MILLISECONDS);
                            if (!awaitTermination) {
                                SystemServerInitThreadPool.dumpStackTraces();
                            }
                            List<Runnable> shutdownNow =
                                    SystemServerInitThreadPool.sInstance.mService.shutdownNow();
                            if (awaitTermination) {
                                SystemServerInitThreadPool.sInstance = null;
                                Slog.d("SystemServerInitThreadPool", "Shutdown successful");
                                timingsTraceAndSlog2.traceEnd();
                                return;
                            }
                            ArrayList arrayList = new ArrayList();
                            synchronized (SystemServerInitThreadPool.sInstance.mPendingTasks) {
                                arrayList.addAll(
                                        SystemServerInitThreadPool.sInstance.mPendingTasks);
                            }
                            timingsTraceAndSlog2.traceEnd();
                            throw new IllegalStateException(
                                    "Cannot shutdown. Unstarted tasks "
                                            + shutdownNow
                                            + " Unfinished tasks "
                                            + arrayList);
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                            SystemServerInitThreadPool.dumpStackTraces();
                            timingsTraceAndSlog2.traceEnd();
                            throw new IllegalStateException(
                                    "SystemServerInitThreadPool init interrupted");
                        }
                    } finally {
                    }
                }
            }
        } catch (Throwable th) {
            timingsTraceAndSlog.traceEnd();
            throw th;
        }
    }

    public final SystemService startService(Class cls) {
        try {
            String name = cls.getName();
            Slog.i("SystemServiceManager", "Starting ".concat(name));
            Trace.traceBegin(524288L, "StartService ".concat(name));
            if (!SystemService.class.isAssignableFrom(cls)) {
                throw new RuntimeException(
                        "Failed to create "
                                + name
                                + ": service must extend "
                                + SystemService.class.getName());
            }
            if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())
                    && name.equals(SystemProperties.get("sys.isrb.crashservice", "ISRB"))
                    && IsrbHooks.canSkip(name)) {
                Slog.d("SystemServiceManager", "startService isrb return :".concat(name));
                Trace.traceEnd(524288L);
                Trace.traceEnd(524288L);
                return null;
            }
            try {
                try {
                    SystemService systemService =
                            (SystemService)
                                    cls.getConstructor(Context.class).newInstance(this.mContext);
                    startService(systemService);
                    return systemService;
                } catch (InstantiationException e) {
                    if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())) {
                        Slog.d("SystemServiceManager", "startService isrb setname");
                        IsrbHooks.saveCrashServiceName(name);
                    }
                    throw new RuntimeException(
                            "Failed to create service "
                                    + name
                                    + ": service could not be instantiated",
                            e);
                } catch (NoSuchMethodException e2) {
                    if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())) {
                        Slog.d("SystemServiceManager", "startService isrb setname");
                        IsrbHooks.saveCrashServiceName(name);
                    }
                    throw new RuntimeException(
                            "Failed to create service "
                                    + name
                                    + ": service must have a public constructor with a Context"
                                    + " argument",
                            e2);
                }
            } catch (IllegalAccessException e3) {
                if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())) {
                    Slog.d("SystemServiceManager", "startService isrb setname");
                    IsrbHooks.saveCrashServiceName(name);
                }
                throw new RuntimeException(
                        "Failed to create service "
                                + name
                                + ": service must have a public constructor with a Context"
                                + " argument",
                        e3);
            } catch (InvocationTargetException e4) {
                if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())) {
                    Slog.d("SystemServiceManager", "startService isrb setname");
                    IsrbHooks.saveCrashServiceName(name);
                }
                throw new RuntimeException(
                        "Failed to create service "
                                + name
                                + ": service constructor threw an exception",
                        e4);
            }
        } finally {
            Trace.traceEnd(524288L);
        }
    }

    public final SystemService startService(String str) {
        return startService(loadClassFromLoader(str, SystemServiceManager.class.getClassLoader()));
    }

    public final void startService(SystemService systemService) {
        String name = systemService.getClass().getName();
        if (this.mServiceClassnames.contains(name)) {
            Slog.i("SystemServiceManager", "Not starting an already started service ".concat(name));
            return;
        }
        this.mServiceClassnames.add(name);
        this.mServices.add(systemService);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            systemService.onStart();
            warnIfTooLong(
                    SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onStart", 0);
        } catch (RuntimeException e) {
            if ("ISRB_BOOT".equals(RescueParty.getRescuePartyReason())) {
                Slog.d("SystemServiceManager", "startService isrb setname");
                IsrbHooks.saveCrashServiceName(systemService.getClass().getName());
            }
            throw new RuntimeException(
                    "Failed to start service "
                            + systemService.getClass().getName()
                            + ": onStart threw an exception",
                    e);
        }
    }

    public final void startServiceFromJar(String str, String str2) {
        ClassLoader classLoader = SystemServiceManager.class.getClassLoader();
        boolean z = false;
        Path path = Paths.get(str2, new String[0]);
        if (path.getNameCount() >= 2 && path.getName(0).toString().equals("apex")) {
            try {
                if ((this.mContext
                                        .getPackageManager()
                                        .getPackageInfo(
                                                ApexManager.getInstance()
                                                        .getActivePackageNameForApexModuleName(
                                                                path.getName(1).toString()),
                                                PackageManager.PackageInfoFlags.of(1073741824L))
                                        .applicationInfo
                                        .flags
                                & 256)
                        != 0) {
                    z = true;
                }
            } catch (Exception unused) {
            }
        }
        startService(
                loadClassFromLoader(
                        str,
                        SystemServerClassLoaderFactory.getOrCreateClassLoader(
                                str2, classLoader, z)));
    }

    public final void updateOtherServicesStartIndex() {
        if (isBootCompleted()) {
            return;
        }
        sOtherServicesStartIndex = this.mServices.size();
    }
}
