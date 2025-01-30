package com.android.server;

import android.app.ActivityManager;
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
import com.android.server.SystemService;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class SystemServiceManager implements Dumpable {
    public static final String TAG = SystemServiceManager.class.getSimpleName();
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

    public SystemService startService(String str) {
        return startService(loadClassFromLoader(str, SystemServiceManager.class.getClassLoader()));
    }

    public SystemService startServiceFromJar(String str, String str2) {
        return startService(loadClassFromLoader(str, SystemServerClassLoaderFactory.getOrCreateClassLoader(str2, SystemServiceManager.class.getClassLoader(), isJarInTestApex(str2))));
    }

    public final boolean isJarInTestApex(String str) {
        Path path = Paths.get(str, new String[0]);
        if (path.getNameCount() < 2 || !path.getName(0).toString().equals("apex")) {
            return false;
        }
        try {
            return (this.mContext.getPackageManager().getPackageInfo(ApexManager.getInstance().getActivePackageNameForApexModuleName(path.getName(1).toString()), PackageManager.PackageInfoFlags.of(1073741824L)).applicationInfo.flags & 256) != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static Class loadClassFromLoader(String str, ClassLoader classLoader) {
        try {
            return Class.forName(str, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to create service " + str + " from class loader " + classLoader.toString() + ": service class not found, usually indicates that the caller should have called PackageManager.hasSystemFeature() to check whether the feature is available on this device before trying to start the services that implement it. Also ensure that the correct path for the classloader is supplied, if applicable.", e);
        }
    }

    public SystemService startService(Class cls) {
        try {
            String name = cls.getName();
            String str = TAG;
            Slog.i(str, "Starting " + name);
            Trace.traceBegin(524288L, "StartService " + name);
            if (!SystemService.class.isAssignableFrom(cls)) {
                throw new RuntimeException("Failed to create " + name + ": service must extend " + SystemService.class.getName());
            }
            if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6 && name.equals(SystemProperties.get("sys.isrb.crashservice", "ISRB")) && IsrbHooks.canSkip(name)) {
                Slog.d(str, "startService isrb return :" + name);
                Trace.traceEnd(524288L);
                Trace.traceEnd(524288L);
                return null;
            }
            try {
                try {
                    SystemService systemService = (SystemService) cls.getConstructor(Context.class).newInstance(this.mContext);
                    startService(systemService);
                    return systemService;
                } catch (InstantiationException e) {
                    if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                        Slog.d(TAG, "startService isrb setname");
                        IsrbHooks.saveCrashServiceName(name);
                    }
                    throw new RuntimeException("Failed to create service " + name + ": service could not be instantiated", e);
                } catch (InvocationTargetException e2) {
                    if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                        Slog.d(TAG, "startService isrb setname");
                        IsrbHooks.saveCrashServiceName(name);
                    }
                    throw new RuntimeException("Failed to create service " + name + ": service constructor threw an exception", e2);
                }
            } catch (IllegalAccessException e3) {
                if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                    Slog.d(TAG, "startService isrb setname");
                    IsrbHooks.saveCrashServiceName(name);
                }
                throw new RuntimeException("Failed to create service " + name + ": service must have a public constructor with a Context argument", e3);
            } catch (NoSuchMethodException e4) {
                if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                    Slog.d(TAG, "startService isrb setname");
                    IsrbHooks.saveCrashServiceName(name);
                }
                throw new RuntimeException("Failed to create service " + name + ": service must have a public constructor with a Context argument", e4);
            }
        } finally {
            Trace.traceEnd(524288L);
        }
    }

    public void startService(SystemService systemService) {
        String name = systemService.getClass().getName();
        if (this.mServiceClassnames.contains(name)) {
            Slog.i(TAG, "Not starting an already started service " + name);
            return;
        }
        this.mServiceClassnames.add(name);
        this.mServices.add(systemService);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            systemService.onStart();
            warnIfTooLong(SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onStart");
        } catch (RuntimeException e) {
            if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                Slog.d(TAG, "startService isrb setname");
                IsrbHooks.saveCrashServiceName(systemService.getClass().getName());
            }
            throw new RuntimeException("Failed to start service " + systemService.getClass().getName() + ": onStart threw an exception", e);
        }
    }

    public void sealStartedServices() {
        this.mServiceClassnames = Collections.emptySet();
        this.mServices = Collections.unmodifiableList(this.mServices);
    }

    public void startBootPhase(TimingsTraceAndSlog timingsTraceAndSlog, int i) {
        if (i <= this.mCurrentPhase) {
            throw new IllegalArgumentException("Next phase must be larger than previous");
        }
        this.mCurrentPhase = i;
        Slog.i(TAG, "Starting phase " + this.mCurrentPhase);
        try {
            timingsTraceAndSlog.traceBegin("OnBootPhase_" + i);
            int size = this.mServices.size();
            for (int i2 = 0; i2 < size; i2++) {
                SystemService systemService = (SystemService) this.mServices.get(i2);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                timingsTraceAndSlog.traceBegin("OnBootPhase_" + i + "_" + systemService.getClass().getName());
                if (SystemProperties.getBoolean("sys.isrb.wificrash", false) && systemService.getClass().getName().indexOf("Wifi") >= 0) {
                    warnIfTooLong(SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onBootPhase", i);
                    timingsTraceAndSlog.traceEnd();
                } else {
                    try {
                        systemService.onBootPhase(this.mCurrentPhase);
                        warnIfTooLong(SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onBootPhase", i);
                        timingsTraceAndSlog.traceEnd();
                    } catch (Exception e) {
                        if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                            Slog.d(TAG, "startBootPhase isrb setname ");
                            IsrbHooks.saveCrashServiceName(systemService.getClass().getName());
                        }
                        throw new RuntimeException("Failed to boot service " + systemService.getClass().getName() + ": onBootPhase threw an exception during phase " + this.mCurrentPhase, e);
                    }
                }
            }
            timingsTraceAndSlog.traceEnd();
            if (i == 1000) {
                timingsTraceAndSlog.logDuration("TotalBootTime", SystemClock.uptimeMillis() - this.mRuntimeStartUptime);
                SystemServerInitThreadPool.shutdown();
            }
        } catch (Throwable th) {
            timingsTraceAndSlog.traceEnd();
            throw th;
        }
    }

    public boolean isBootCompleted() {
        return this.mCurrentPhase >= 1000;
    }

    public void updateOtherServicesStartIndex() {
        if (isBootCompleted()) {
            return;
        }
        sOtherServicesStartIndex = this.mServices.size();
    }

    public void preSystemReady() {
        this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    public final SystemService.TargetUser getTargetUser(int i) {
        SystemService.TargetUser targetUser;
        synchronized (this.mTargetUsers) {
            targetUser = (SystemService.TargetUser) this.mTargetUsers.get(i);
        }
        return targetUser;
    }

    public final SystemService.TargetUser newTargetUser(int i) {
        UserInfo userInfo = this.mUserManagerInternal.getUserInfo(i);
        Preconditions.checkState(userInfo != null, "No UserInfo for " + i);
        return new SystemService.TargetUser(userInfo);
    }

    public void onUserStarting(TimingsTraceAndSlog timingsTraceAndSlog, int i) {
        SystemService.TargetUser newTargetUser = newTargetUser(i);
        synchronized (this.mTargetUsers) {
            if (i == 0) {
                if (this.mTargetUsers.contains(i)) {
                    Slog.e(TAG, "Skipping starting system user twice");
                    return;
                }
            }
            this.mTargetUsers.put(i, newTargetUser);
            EventLog.writeEvent(30082, i);
            onUser(timingsTraceAndSlog, "Start", null, newTargetUser);
        }
    }

    public void onUserUnlocking(int i) {
        EventLog.writeEvent(30084, i);
        onUser("Unlocking", i);
    }

    public void onUserUnlocked(int i) {
        EventLog.writeEvent(30085, i);
        onUser("Unlocked", i);
    }

    public void onUserSwitching(int i, int i2) {
        SystemService.TargetUser targetUser;
        SystemService.TargetUser targetUser2;
        EventLog.writeEvent(30083, Integer.valueOf(i), Integer.valueOf(i2));
        synchronized (this.mTargetUsers) {
            SystemService.TargetUser targetUser3 = this.mCurrentUser;
            if (targetUser3 == null) {
                targetUser = newTargetUser(i);
            } else {
                if (i != targetUser3.getUserIdentifier()) {
                    Slog.wtf(TAG, "switchUser(" + i + "," + i2 + "): mCurrentUser is " + this.mCurrentUser + ", it should be " + i);
                }
                targetUser = this.mCurrentUser;
            }
            targetUser2 = getTargetUser(i2);
            this.mCurrentUser = targetUser2;
            Preconditions.checkState(targetUser2 != null, "No TargetUser for " + i2);
        }
        onUser(TimingsTraceAndSlog.newAsyncLog(), "Switch", targetUser, targetUser2);
    }

    public void onUserStopping(int i) {
        EventLog.writeEvent(30086, i);
        onUser("Stop", i);
    }

    public void onUserStopped(int i) {
        EventLog.writeEvent(30087, i);
        onUser("Cleanup", i);
        synchronized (this.mTargetUsers) {
            this.mTargetUsers.remove(i);
        }
    }

    public void onUserCompletedEvent(int i, int i2) {
        SystemService.TargetUser targetUser;
        EventLog.writeEvent(30088, Integer.valueOf(i), Integer.valueOf(i2));
        if (i2 == 0 || (targetUser = getTargetUser(i)) == null) {
            return;
        }
        onUser(TimingsTraceAndSlog.newAsyncLog(), "CompletedEvent", null, targetUser, new SystemService.UserCompletedEventType(i2));
    }

    public final void onUser(String str, int i) {
        SystemService.TargetUser targetUser = getTargetUser(i);
        Preconditions.checkState(targetUser != null, "No TargetUser for " + i);
        onUser(TimingsTraceAndSlog.newAsyncLog(), str, null, targetUser);
    }

    public final void onUser(TimingsTraceAndSlog timingsTraceAndSlog, String str, SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        onUser(timingsTraceAndSlog, str, targetUser, targetUser2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0223 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0193 A[Catch: Exception -> 0x01da, TryCatch #1 {Exception -> 0x01da, blocks: (B:31:0x015d, B:32:0x01de, B:33:0x01f2, B:53:0x018e, B:54:0x0193, B:55:0x01a0, B:56:0x01ac, B:59:0x01c2, B:60:0x01ca, B:61:0x01ce), top: B:52:0x018e }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01a0 A[Catch: Exception -> 0x01da, TryCatch #1 {Exception -> 0x01da, blocks: (B:31:0x015d, B:32:0x01de, B:33:0x01f2, B:53:0x018e, B:54:0x0193, B:55:0x01a0, B:56:0x01ac, B:59:0x01c2, B:60:0x01ca, B:61:0x01ce), top: B:52:0x018e }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01ac A[Catch: Exception -> 0x01da, TryCatch #1 {Exception -> 0x01da, blocks: (B:31:0x015d, B:32:0x01de, B:33:0x01f2, B:53:0x018e, B:54:0x0193, B:55:0x01a0, B:56:0x01ac, B:59:0x01c2, B:60:0x01ca, B:61:0x01ce), top: B:52:0x018e }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01ce A[Catch: Exception -> 0x01da, TryCatch #1 {Exception -> 0x01da, blocks: (B:31:0x015d, B:32:0x01de, B:33:0x01f2, B:53:0x018e, B:54:0x0193, B:55:0x01a0, B:56:0x01ac, B:59:0x01c2, B:60:0x01ca, B:61:0x01ce), top: B:52:0x018e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUser(TimingsTraceAndSlog timingsTraceAndSlog, String str, SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2, SystemService.UserCompletedEventType userCompletedEventType) {
        String str2;
        SystemService systemService;
        int i;
        int i2;
        ExecutorService executorService;
        char c;
        String str3;
        int userIdentifier = targetUser2.getUserIdentifier();
        timingsTraceAndSlog.traceBegin("ssm." + str + "User-" + userIdentifier);
        String str4 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Calling on");
        sb.append(str);
        sb.append("User ");
        sb.append(userIdentifier);
        if (targetUser != null) {
            str2 = " (from " + targetUser + ")";
        } else {
            str2 = "";
        }
        sb.append(str2);
        Slog.i(str4, sb.toString());
        boolean useThreadPool = useThreadPool(userIdentifier, str);
        ExecutorService newFixedThreadPool = useThreadPool ? Executors.newFixedThreadPool(this.mNumUserPoolThreads) : null;
        int size = this.mServices.size();
        boolean z = false;
        int i3 = 0;
        while (i3 < size) {
            SystemService systemService2 = (SystemService) this.mServices.get(i3);
            String name = systemService2.getClass().getName();
            boolean isUserSupported = systemService2.isUserSupported(targetUser2);
            if (!isUserSupported && targetUser != null) {
                isUserSupported = systemService2.isUserSupported(targetUser);
            }
            if (!isUserSupported) {
                Slog.i(TAG, "Skipping " + str + "User-" + userIdentifier + " on " + name);
                i = i3;
                i2 = size;
                executorService = newFixedThreadPool;
            } else {
                boolean z2 = useThreadPool && useThreadPoolForService(str, i3);
                if (!z2) {
                    timingsTraceAndSlog.traceBegin("ssm.on" + str + "User-" + userIdentifier + "_" + name);
                }
                long elapsedRealtime = SystemClock.elapsedRealtime();
                try {
                } catch (Exception e) {
                    e = e;
                    systemService = systemService2;
                    i = i3;
                    i2 = size;
                    executorService = newFixedThreadPool;
                }
                switch (str.hashCode()) {
                    case -1805606060:
                        if (str.equals("Switch")) {
                            c = 0;
                            switch (c) {
                                case 0:
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    systemService.onUserSwitching(targetUser, targetUser2);
                                    if (!z2) {
                                        warnIfTooLong(SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "on" + str + "User-" + userIdentifier);
                                        timingsTraceAndSlog.traceEnd();
                                        break;
                                    } else {
                                        break;
                                    }
                                case 1:
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    if (z2) {
                                        executorService.submit(getOnUserStartingRunnable(timingsTraceAndSlog, systemService, targetUser2));
                                    } else {
                                        systemService.onUserStarting(targetUser2);
                                    }
                                    if (!z2) {
                                    }
                                    break;
                                case 2:
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    systemService.onUserUnlocking(targetUser2);
                                    if (!z2) {
                                    }
                                    break;
                                case 3:
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    systemService.onUserUnlocked(targetUser2);
                                    if (!z2) {
                                    }
                                    break;
                                case 4:
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    systemService.onUserStopping(targetUser2);
                                    if (!z2) {
                                    }
                                    break;
                                case 5:
                                    str3 = name;
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    try {
                                        systemService.onUserStopped(targetUser2);
                                    } catch (Exception e2) {
                                        e = e2;
                                        name = str3;
                                        logFailure(str, targetUser2, name, e);
                                        if (!z2) {
                                        }
                                        i3 = i + 1;
                                        newFixedThreadPool = executorService;
                                        size = i2;
                                    }
                                    if (!z2) {
                                    }
                                    break;
                                case 6:
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    try {
                                        executorService.submit(getOnUserCompletedEventRunnable(timingsTraceAndSlog, systemService2, name, targetUser2, userCompletedEventType));
                                        systemService = systemService2;
                                    } catch (Exception e3) {
                                        e = e3;
                                        name = name;
                                        systemService = systemService2;
                                        logFailure(str, targetUser2, name, e);
                                        if (!z2) {
                                        }
                                        i3 = i + 1;
                                        newFixedThreadPool = executorService;
                                        size = i2;
                                    }
                                    if (!z2) {
                                    }
                                    break;
                                default:
                                    str3 = name;
                                    systemService = systemService2;
                                    i = i3;
                                    i2 = size;
                                    executorService = newFixedThreadPool;
                                    throw new IllegalArgumentException(str + " what?");
                                    break;
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    case -1773539708:
                        if (str.equals("Cleanup")) {
                            c = 5;
                            switch (c) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    case -240492034:
                        if (str.equals("Unlocking")) {
                            c = 2;
                            switch (c) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    case -146305277:
                        if (str.equals("Unlocked")) {
                            c = 3;
                            switch (c) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    case 2587682:
                        if (str.equals("Stop")) {
                            c = 4;
                            switch (c) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    case 80204866:
                        if (str.equals("Start")) {
                            c = 1;
                            switch (c) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    case 537825071:
                        if (str.equals("CompletedEvent")) {
                            c = 6;
                            switch (c) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                    default:
                        c = 65535;
                        switch (c) {
                        }
                }
            }
            i3 = i + 1;
            newFixedThreadPool = executorService;
            size = i2;
        }
        ExecutorService executorService2 = newFixedThreadPool;
        if (useThreadPool) {
            executorService2.shutdown();
            try {
                z = executorService2.awaitTermination(30L, TimeUnit.SECONDS);
            } catch (InterruptedException e4) {
                logFailure(str, targetUser2, "(user lifecycle threadpool was interrupted)", e4);
            }
            if (!z) {
                logFailure(str, targetUser2, "(user lifecycle threadpool was not terminated)", null);
            }
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final boolean useThreadPool(int i, String str) {
        str.hashCode();
        return !str.equals("Start") ? str.equals("CompletedEvent") : (ActivityManager.isLowRamDeviceStatic() || i == 0) ? false : true;
    }

    public final boolean useThreadPoolForService(String str, int i) {
        str.hashCode();
        return !str.equals("Start") ? str.equals("CompletedEvent") : i >= sOtherServicesStartIndex;
    }

    public final Runnable getOnUserStartingRunnable(final TimingsTraceAndSlog timingsTraceAndSlog, final SystemService systemService, final SystemService.TargetUser targetUser) {
        return new Runnable() { // from class: com.android.server.SystemServiceManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SystemServiceManager.this.lambda$getOnUserStartingRunnable$0(timingsTraceAndSlog, systemService, targetUser);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnUserStartingRunnable$0(TimingsTraceAndSlog timingsTraceAndSlog, SystemService systemService, SystemService.TargetUser targetUser) {
        TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog(timingsTraceAndSlog);
        String name = systemService.getClass().getName();
        int userIdentifier = targetUser.getUserIdentifier();
        timingsTraceAndSlog2.traceBegin("ssm.onStartUser-" + userIdentifier + "_" + name);
        try {
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                systemService.onUserStarting(targetUser);
                warnIfTooLong(SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onStartUser-" + userIdentifier);
            } catch (Exception e) {
                logFailure("Start", targetUser, name, e);
            }
        } finally {
            timingsTraceAndSlog2.traceEnd();
        }
    }

    public final Runnable getOnUserCompletedEventRunnable(final TimingsTraceAndSlog timingsTraceAndSlog, final SystemService systemService, final String str, final SystemService.TargetUser targetUser, final SystemService.UserCompletedEventType userCompletedEventType) {
        return new Runnable() { // from class: com.android.server.SystemServiceManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SystemServiceManager.this.lambda$getOnUserCompletedEventRunnable$1(timingsTraceAndSlog, targetUser, userCompletedEventType, str, systemService);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnUserCompletedEventRunnable$1(TimingsTraceAndSlog timingsTraceAndSlog, SystemService.TargetUser targetUser, SystemService.UserCompletedEventType userCompletedEventType, String str, SystemService systemService) {
        TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog(timingsTraceAndSlog);
        int userIdentifier = targetUser.getUserIdentifier();
        timingsTraceAndSlog2.traceBegin("ssm.onCompletedEventUser-" + userIdentifier + "_" + userCompletedEventType + "_" + str);
        try {
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                systemService.onUserCompletedEvent(targetUser, userCompletedEventType);
                warnIfTooLong(SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onCompletedEventUser-" + userIdentifier);
            } catch (Exception e) {
                logFailure("CompletedEvent", targetUser, str, e);
                throw e;
            }
        } finally {
            timingsTraceAndSlog2.traceEnd();
        }
    }

    public final void logFailure(String str, SystemService.TargetUser targetUser, String str2, Exception exc) {
        Slog.wtf(TAG, "SystemService failure: Failure reporting " + str + " of user " + targetUser + " to service " + str2, exc);
    }

    public void setSafeMode(boolean z) {
        this.mSafeMode = z;
    }

    public boolean isSafeMode() {
        return this.mSafeMode;
    }

    public boolean isRuntimeRestarted() {
        return this.mRuntimeRestarted;
    }

    public long getRuntimeStartElapsedTime() {
        return this.mRuntimeStartElapsedTime;
    }

    public long getRuntimeStartUptime() {
        return this.mRuntimeStartUptime;
    }

    public void setStartInfo(boolean z, long j, long j2) {
        this.mRuntimeRestarted = z;
        this.mRuntimeStartElapsedTime = j;
        this.mRuntimeStartUptime = j2;
    }

    public final void warnIfTooLong(long j, SystemService systemService, String str, int i) {
        if (j > 50) {
            String name = systemService.getClass().getName();
            String str2 = TAG;
            Slog.w(str2, "Service " + name + " took " + j + " ms in " + str);
            if (i == 0 || 200 >= j) {
                return;
            }
            Slog.d(str2, "!@Boot_SystemServer: " + j + "ms : (" + i + ") " + name);
            Slog.i(str2, "!@Boot_EBS:   Took " + j + "ms by '" + name + "' (" + i + ")");
        }
    }

    public final void warnIfTooLong(long j, SystemService systemService, String str) {
        warnIfTooLong(j, systemService, str, 0);
    }

    public static File ensureSystemDir() {
        if (sSystemDir == null) {
            File file = new File(Environment.getDataDirectory(), "system");
            sSystemDir = file;
            file.mkdirs();
        }
        return sSystemDir;
    }

    @Override // android.util.Dumpable
    public String getDumpableName() {
        return SystemServiceManager.class.getSimpleName();
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        printWriter.printf("Current phase: %d\n", Integer.valueOf(this.mCurrentPhase));
        synchronized (this.mTargetUsers) {
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
                    ((SystemService.TargetUser) this.mTargetUsers.valueAt(i2)).dump(printWriter);
                    if (i2 != size - 1) {
                        printWriter.print(", ");
                    }
                }
                printWriter.println();
            } else {
                printWriter.println("No target users");
            }
        }
        int size2 = this.mServices.size();
        if (size2 > 0) {
            printWriter.printf("%d started services:\n", Integer.valueOf(size2));
            for (i = 0; i < size2; i++) {
                SystemService systemService = (SystemService) this.mServices.get(i);
                printWriter.print("  ");
                printWriter.println(systemService.getClass().getCanonicalName());
            }
            return;
        }
        printWriter.println("No started services");
    }
}
