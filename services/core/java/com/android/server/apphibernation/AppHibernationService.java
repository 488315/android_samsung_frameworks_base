package com.android.server.apphibernation;

import android.app.ActivityThread;
import android.app.Flags;
import android.app.IActivityManager;
import android.app.StatsManager;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.apphibernation.HibernationStats;
import android.apphibernation.IAppHibernationService;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.pm.PackageManagerService;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppHibernationService extends SystemService {
    public static boolean sIsServiceEnabled;
    public final Executor mBackgroundExecutor;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Map mGlobalHibernationStates;
    public final HibernationStateDiskStore mGlobalLevelHibernationDiskStore;
    public final IActivityManager mIActivityManager;
    public final IPackageManager mIPackageManager;
    public final Injector mInjector;
    public final Object mLock;
    public final boolean mOatArtifactDeletionEnabled;
    public final PackageManagerInternal mPackageManagerInternal;
    public final AppHibernationServiceStub mServiceStub;
    public final StorageStatsManager mStorageStatsManager;
    public final AppHibernationService$$ExternalSyntheticLambda0 mUsageEventListener;
    public final SparseArray mUserDiskStores;
    public final UserManager mUserManager;
    public final SparseArray mUserStates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppHibernationServiceStub extends IAppHibernationService.Stub {
        public final AppHibernationService mService;

        public AppHibernationServiceStub(AppHibernationService appHibernationService) {
            this.mService = appHibernationService;
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            AppHibernationService appHibernationService = this.mService;
            if (DumpUtils.checkDumpAndUsageStatsPermission(
                    appHibernationService.getContext(), "AppHibernationService", printWriter)) {
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "  ");
                synchronized (appHibernationService.mLock) {
                    try {
                        int size = appHibernationService.mUserStates.size();
                        for (int i = 0; i < size; i++) {
                            int keyAt = appHibernationService.mUserStates.keyAt(i);
                            indentingPrintWriter.print("User Level Hibernation States, ");
                            indentingPrintWriter.printPair("user", Integer.valueOf(keyAt));
                            indentingPrintWriter.println();
                            Map map = (Map) appHibernationService.mUserStates.get(keyAt);
                            indentingPrintWriter.increaseIndent();
                            Iterator it = map.values().iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter.print((UserLevelState) it.next());
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.print("Global Level Hibernation States");
                        indentingPrintWriter.println();
                        Iterator it2 =
                                ((ArrayMap) appHibernationService.mGlobalHibernationStates)
                                        .values()
                                        .iterator();
                        while (it2.hasNext()) {
                            indentingPrintWriter.print((GlobalLevelState) it2.next());
                            indentingPrintWriter.println();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final List getHibernatingPackagesForUser(int i) {
            return this.mService.getHibernatingPackagesForUser(i);
        }

        public final Map getHibernationStatsForUser(List list, int i) {
            Set<String> arraySet = list != null ? new ArraySet(list) : null;
            AppHibernationService appHibernationService = this.mService;
            appHibernationService.getClass();
            ArrayMap arrayMap = new ArrayMap();
            if (AppHibernationService.sIsServiceEnabled) {
                appHibernationService
                        .getContext()
                        .enforceCallingOrSelfPermission(
                                "android.permission.MANAGE_APP_HIBERNATION",
                                "Caller does not have MANAGE_APP_HIBERNATION permission.");
                int handleIncomingUser =
                        appHibernationService.handleIncomingUser(i, "getHibernationStatsForUser");
                synchronized (appHibernationService.mLock) {
                    try {
                        if (appHibernationService.checkUserStatesExist(
                                handleIncomingUser, "getHibernationStatsForUser", true)) {
                            Map map =
                                    (Map) appHibernationService.mUserStates.get(handleIncomingUser);
                            if (arraySet == null) {
                                arraySet = map.keySet();
                            }
                            for (String str : arraySet) {
                                if (appHibernationService.mPackageManagerInternal.canQueryPackage(
                                        Binder.getCallingUid(), str)) {
                                    if (((ArrayMap) appHibernationService.mGlobalHibernationStates)
                                                    .containsKey(str)
                                            && map.containsKey(str)) {
                                        arrayMap.put(
                                                str,
                                                new HibernationStats(
                                                        ((GlobalLevelState)
                                                                                ((ArrayMap)
                                                                                                appHibernationService
                                                                                                        .mGlobalHibernationStates)
                                                                                        .get(str))
                                                                        .savedByte
                                                                + ((UserLevelState) map.get(str))
                                                                        .savedByte));
                                    }
                                    Slog.w(
                                            "AppHibernationService",
                                            TextUtils.formatSimple(
                                                    "No hibernation state associated with package"
                                                        + " %s user %d. Maybethe package was"
                                                        + " uninstalled? ",
                                                    new Object[] {
                                                        str, Integer.valueOf(handleIncomingUser)
                                                    }));
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
            return arrayMap;
        }

        public final boolean isHibernatingForUser(String str, int i) {
            return this.mService.isHibernatingForUser(str, i);
        }

        public final boolean isHibernatingGlobally(String str) {
            return this.mService.isHibernatingGlobally(str);
        }

        public final boolean isOatArtifactDeletionEnabled() {
            AppHibernationService appHibernationService = this.mService;
            appHibernationService
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.MANAGE_APP_HIBERNATION",
                            "Caller does not have MANAGE_APP_HIBERNATION permission.");
            return appHibernationService.mOatArtifactDeletionEnabled;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new AppHibernationShellCommand(this.mService)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void setHibernatingForUser(String str, int i, boolean z) {
            this.mService.setHibernatingForUser(str, i, z);
        }

        public final void setHibernatingGlobally(String str, boolean z) {
            this.mService.setHibernatingGlobally(str, z);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InjectorImpl implements Injector {
        public final Context mContext;
        public final ScheduledExecutorService mScheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        public final UserLevelHibernationProto mUserLevelHibernationProto =
                new UserLevelHibernationProto();

        public InjectorImpl(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public final AppHibernationService mService;

        public LocalService(AppHibernationService appHibernationService) {
            this.mService = appHibernationService;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {}

        public final int onPullAtom(int i, List list) {
            long j;
            int i2;
            if (!AppHibernationService.sIsServiceEnabled && (i == 10107 || i == 10109)) {
                return 0;
            }
            if (i == 10107) {
                List aliveUsers = AppHibernationService.this.mUserManager.getAliveUsers();
                int size = aliveUsers.size();
                for (int i3 = 0; i3 < size; i3++) {
                    int i4 = ((UserInfo) aliveUsers.get(i3)).id;
                    if (AppHibernationService.this.mUserManager.isUserUnlockingOrUnlocked(i4)) {
                        list.add(
                                FrameworkStatsLog.buildStatsEvent(
                                        i,
                                        ((ArrayList)
                                                        AppHibernationService.this
                                                                .getHibernatingPackagesForUser(i4))
                                                .size(),
                                        i4));
                    }
                }
            } else {
                if (i != 10109) {
                    return 1;
                }
                synchronized (AppHibernationService.this.mLock) {
                    try {
                        j = 0;
                        i2 = 0;
                        for (GlobalLevelState globalLevelState :
                                ((ArrayMap) AppHibernationService.this.mGlobalHibernationStates)
                                        .values()) {
                            if (globalLevelState.hibernated) {
                                i2++;
                                j += globalLevelState.savedByte;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                list.add(FrameworkStatsLog.buildStatsEvent(i, i2, j / 1000000));
            }
            return 0;
        }
    }

    public AppHibernationService(Context context) {
        this(new InjectorImpl(context));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AppHibernationService(
            com.android.server.apphibernation.AppHibernationService.Injector r11) {
        /*
            r10 = this;
            r0 = r11
            com.android.server.apphibernation.AppHibernationService$InjectorImpl r0 = (com.android.server.apphibernation.AppHibernationService.InjectorImpl) r0
            android.content.Context r1 = r0.mContext
            r10.<init>(r1)
            java.lang.Object r1 = new java.lang.Object
            r1.<init>()
            r10.mLock = r1
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            r10.mUserStates = r1
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            r10.mUserDiskStores = r1
            android.util.ArrayMap r1 = new android.util.ArrayMap
            r1.<init>()
            r10.mGlobalHibernationStates = r1
            com.android.server.apphibernation.AppHibernationService$LocalService r1 = new com.android.server.apphibernation.AppHibernationService$LocalService
            r1.<init>(r10)
            com.android.server.apphibernation.AppHibernationService$AppHibernationServiceStub r2 = new com.android.server.apphibernation.AppHibernationService$AppHibernationServiceStub
            r2.<init>(r10)
            r10.mServiceStub = r2
            com.android.server.apphibernation.AppHibernationService$1 r2 = new com.android.server.apphibernation.AppHibernationService$1
            r2.<init>()
            com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda0 r3 = new com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda0
            r3.<init>()
            android.content.Context r4 = r0.mContext
            java.lang.String r5 = "package"
            android.os.IBinder r6 = android.os.ServiceManager.getService(r5)
            android.content.pm.IPackageManager r6 = android.content.pm.IPackageManager.Stub.asInterface(r6)
            r10.mIPackageManager = r6
            java.lang.Class<android.content.pm.PackageManagerInternal> r6 = android.content.pm.PackageManagerInternal.class
            java.lang.Object r6 = com.android.server.LocalServices.getService(r6)
            android.content.pm.PackageManagerInternal r6 = (android.content.pm.PackageManagerInternal) r6
            r10.mPackageManagerInternal = r6
            android.app.IActivityManager r6 = android.app.ActivityManager.getService()
            r10.mIActivityManager = r6
            android.content.Context r6 = r0.mContext
            java.lang.Class<android.os.UserManager> r7 = android.os.UserManager.class
            java.lang.Object r6 = r6.getSystemService(r7)
            android.os.UserManager r6 = (android.os.UserManager) r6
            r10.mUserManager = r6
            android.content.Context r6 = r0.mContext
            java.lang.Class<android.app.usage.StorageStatsManager> r7 = android.app.usage.StorageStatsManager.class
            java.lang.Object r6 = r6.getSystemService(r7)
            android.app.usage.StorageStatsManager r6 = (android.app.usage.StorageStatsManager) r6
            r10.mStorageStatsManager = r6
            java.io.File r6 = new java.io.File
            java.io.File r7 = android.os.Environment.getDataSystemDirectory()
            java.lang.String r8 = "hibernation"
            r6.<init>(r7, r8)
            com.android.server.apphibernation.HibernationStateDiskStore r7 = new com.android.server.apphibernation.HibernationStateDiskStore
            com.android.server.apphibernation.GlobalLevelHibernationProto r8 = new com.android.server.apphibernation.GlobalLevelHibernationProto
            r8.<init>()
            java.util.concurrent.ScheduledExecutorService r9 = r0.mScheduledExecutorService
            r7.<init>(r6, r8, r9)
            r10.mGlobalLevelHibernationDiskStore = r7
            java.util.concurrent.ScheduledExecutorService r6 = r0.mScheduledExecutorService
            r10.mBackgroundExecutor = r6
            android.content.Context r0 = r0.mContext
            android.content.res.Resources r0 = r0.getResources()
            r6 = 17891760(0x11101b0, float:2.6633505E-38)
            boolean r0 = r0.getBoolean(r6)
            r10.mOatArtifactDeletionEnabled = r0
            r10.mInjector = r11
            android.os.UserHandle r10 = android.os.UserHandle.ALL
            r11 = 0
            android.content.Context r10 = r4.createContextAsUser(r10, r11)
            android.content.IntentFilter r11 = new android.content.IntentFilter
            r11.<init>()
            java.lang.String r0 = "android.intent.action.PACKAGE_ADDED"
            r11.addAction(r0)
            java.lang.String r0 = "android.intent.action.PACKAGE_REMOVED"
            r11.addAction(r0)
            r11.addDataScheme(r5)
            r10.registerReceiver(r2, r11)
            java.lang.Class<com.android.server.apphibernation.AppHibernationService$LocalService> r10 = com.android.server.apphibernation.AppHibernationService.LocalService.class
            com.android.server.LocalServices.addService(r10, r1)
            java.lang.Class<android.app.usage.UsageStatsManagerInternal> r10 = android.app.usage.UsageStatsManagerInternal.class
            java.lang.Object r10 = com.android.server.LocalServices.getService(r10)
            android.app.usage.UsageStatsManagerInternal r10 = (android.app.usage.UsageStatsManagerInternal) r10
            r10.registerListener(r3)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.apphibernation.AppHibernationService.<init>(com.android.server.apphibernation.AppHibernationService$Injector):void");
    }

    public final boolean checkUserStatesExist(int i, String str, boolean z) {
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            if (z) {
                Slog.w(
                        "AppHibernationService",
                        TextUtils.formatSimple(
                                "Attempt to call %s on stopped or nonexistent user %d",
                                new Object[] {str, Integer.valueOf(i)}));
            }
            return false;
        }
        if (this.mUserStates.contains(i)) {
            return true;
        }
        if (z) {
            Slog.w(
                    "AppHibernationService",
                    TextUtils.formatSimple(
                            "Attempt to call %s before states have been read from disk",
                            new Object[] {str}));
        }
        return false;
    }

    public final List getHibernatingPackagesForUser(int i) {
        ArrayList arrayList = new ArrayList();
        if (!sIsServiceEnabled) {
            return arrayList;
        }
        getContext()
                .enforceCallingOrSelfPermission(
                        "android.permission.MANAGE_APP_HIBERNATION",
                        "Caller does not have MANAGE_APP_HIBERNATION permission.");
        int handleIncomingUser = handleIncomingUser(i, "getHibernatingPackagesForUser");
        synchronized (this.mLock) {
            try {
                if (!checkUserStatesExist(
                        handleIncomingUser, "getHibernatingPackagesForUser", true)) {
                    return arrayList;
                }
                for (UserLevelState userLevelState :
                        ((Map) this.mUserStates.get(handleIncomingUser)).values()) {
                    if (this.mPackageManagerInternal.canQueryPackage(
                                    Binder.getCallingUid(), userLevelState.packageName)
                            && userLevelState.hibernated) {
                        arrayList.add(userLevelState.packageName);
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int handleIncomingUser(int i, String str) {
        try {
            return this.mIActivityManager.handleIncomingUser(
                    Binder.getCallingPid(),
                    Binder.getCallingUid(),
                    i,
                    false,
                    true,
                    str,
                    (String) null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void initializeGlobalHibernationStates(List list) {
        try {
            List list2 = this.mIPackageManager.getInstalledPackages(541893120L, 0).getList();
            int size = list2.size();
            for (int i = 0; i < size; i++) {
                String str = ((PackageInfo) list2.get(i)).packageName;
                GlobalLevelState globalLevelState = new GlobalLevelState();
                globalLevelState.packageName = str;
                ((ArrayMap) this.mGlobalHibernationStates).put(str, globalLevelState);
            }
            if (list != null) {
                ArraySet arraySet = new ArraySet();
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arraySet.add(((PackageInfo) list2.get(i2)).packageName);
                }
                int size3 = list.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    GlobalLevelState globalLevelState2 = (GlobalLevelState) list.get(i3);
                    if (arraySet.contains(globalLevelState2.packageName)) {
                        ((ArrayMap) this.mGlobalHibernationStates)
                                .put(globalLevelState2.packageName, globalLevelState2);
                    } else {
                        Slog.w(
                                "AppHibernationService",
                                TextUtils.formatSimple(
                                        "No hibernation state associated with package %s. Maybe the"
                                            + " package was uninstalled? ",
                                        new Object[] {globalLevelState2.packageName}));
                    }
                }
            }
        } catch (RemoteException e) {
            throw new IllegalStateException("Package manager not available", e);
        }
    }

    public final void initializeUserHibernationStates(int i, List list) {
        try {
            List list2 = this.mIPackageManager.getInstalledPackages(537698816L, i).getList();
            ArrayMap arrayMap = new ArrayMap();
            int size = list2.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str = ((PackageInfo) list2.get(i2)).packageName;
                UserLevelState userLevelState = new UserLevelState();
                userLevelState.packageName = str;
                arrayMap.put(str, userLevelState);
            }
            if (list != null) {
                ArrayMap arrayMap2 = new ArrayMap();
                int size2 = list2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayMap2.put(
                            ((PackageInfo) list2.get(i3)).packageName, (PackageInfo) list2.get(i3));
                }
                int size3 = list.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    String str2 = ((UserLevelState) list.get(i4)).packageName;
                    PackageInfo packageInfo = (PackageInfo) arrayMap2.get(str2);
                    UserLevelState userLevelState2 = (UserLevelState) list.get(i4);
                    if (packageInfo == null) {
                        Slog.w(
                                "AppHibernationService",
                                TextUtils.formatSimple(
                                        "No hibernation state associated with package %s user %d."
                                            + " Maybethe package was uninstalled? ",
                                        new Object[] {str2, Integer.valueOf(i)}));
                    } else {
                        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                        if (applicationInfo != null) {
                            int i5 = applicationInfo.flags & 2097152;
                            applicationInfo.flags = i5;
                            if (i5 == 0 && userLevelState2.hibernated) {
                                userLevelState2.hibernated = false;
                                userLevelState2.lastUnhibernatedMs = System.currentTimeMillis();
                            }
                        }
                        arrayMap.put(str2, userLevelState2);
                    }
                }
            }
            this.mUserStates.put(i, arrayMap);
        } catch (RemoteException e) {
            throw new IllegalStateException("Package manager not available", e);
        }
    }

    public final boolean isHibernatingForUser(String str, int i) {
        if (!sIsServiceEnabled) {
            return false;
        }
        getContext()
                .enforceCallingOrSelfPermission(
                        "android.permission.MANAGE_APP_HIBERNATION",
                        "Caller did not have permission while calling isHibernatingForUser");
        int handleIncomingUser = handleIncomingUser(i, "isHibernatingForUser");
        synchronized (this.mLock) {
            try {
                if (!checkUserStatesExist(handleIncomingUser, "isHibernatingForUser", false)) {
                    return false;
                }
                UserLevelState userLevelState =
                        (UserLevelState) ((Map) this.mUserStates.get(handleIncomingUser)).get(str);
                if (userLevelState != null
                        && this.mPackageManagerInternal.canQueryPackage(
                                Binder.getCallingUid(), str)) {
                    return userLevelState.hibernated;
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isHibernatingGlobally(String str) {
        if (!sIsServiceEnabled) {
            return false;
        }
        getContext()
                .enforceCallingOrSelfPermission(
                        "android.permission.MANAGE_APP_HIBERNATION",
                        "Caller does not have MANAGE_APP_HIBERNATION permission.");
        synchronized (this.mLock) {
            try {
                GlobalLevelState globalLevelState =
                        (GlobalLevelState) ((ArrayMap) this.mGlobalHibernationStates).get(str);
                if (globalLevelState != null
                        && this.mPackageManagerInternal.canQueryPackage(
                                Binder.getCallingUid(), str)) {
                    return globalLevelState.hibernated;
                }
                return false;
            } finally {
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            this.mBackgroundExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppHibernationService appHibernationService =
                                    AppHibernationService.this;
                            List readHibernationStates =
                                    appHibernationService.mGlobalLevelHibernationDiskStore
                                            .readHibernationStates();
                            synchronized (appHibernationService.mLock) {
                                appHibernationService.initializeGlobalHibernationStates(
                                        readHibernationStates);
                            }
                        }
                    });
        }
        if (i == 500) {
            sIsServiceEnabled =
                    DeviceConfig.getBoolean("app_hibernation", "app_hibernation_enabled", true);
            DeviceConfig.addOnPropertiesChangedListener(
                    "app_hibernation",
                    ActivityThread.currentApplication().getMainExecutor(),
                    new DeviceConfig
                            .OnPropertiesChangedListener() { // from class:
                                                             // com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda2
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            AppHibernationService.this.getClass();
                            Iterator it = properties.getKeyset().iterator();
                            while (it.hasNext()) {
                                if (TextUtils.equals(
                                        "app_hibernation_enabled", (String) it.next())) {
                                    AppHibernationService.sIsServiceEnabled =
                                            DeviceConfig.getBoolean(
                                                    "app_hibernation",
                                                    "app_hibernation_enabled",
                                                    true);
                                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                            "AppHibernationService",
                                            new StringBuilder(
                                                    "App hibernation changed to enabled="),
                                            AppHibernationService.sIsServiceEnabled);
                                    return;
                                }
                            }
                        }
                    });
            StatsManager statsManager =
                    (StatsManager) getContext().getSystemService(StatsManager.class);
            StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new StatsPullAtomCallbackImpl();
            statsManager.setPullAtomCallback(
                    FrameworkStatsLog.USER_LEVEL_HIBERNATED_APPS,
                    (StatsManager.PullAtomMetadata) null,
                    this.mBackgroundExecutor,
                    statsPullAtomCallbackImpl);
            statsManager.setPullAtomCallback(
                    FrameworkStatsLog.GLOBAL_HIBERNATED_APPS,
                    (StatsManager.PullAtomMetadata) null,
                    this.mBackgroundExecutor,
                    statsPullAtomCallbackImpl);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("app_hibernation", this.mServiceStub);
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            this.mUserDiskStores.remove(userIdentifier);
            this.mUserStates.remove(userIdentifier);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        InjectorImpl injectorImpl = (InjectorImpl) this.mInjector;
        injectorImpl.getClass();
        HibernationStateDiskStore hibernationStateDiskStore =
                new HibernationStateDiskStore(
                        new File(
                                Environment.getDataSystemCeDirectory(userIdentifier),
                                "hibernation"),
                        injectorImpl.mUserLevelHibernationProto,
                        injectorImpl.mScheduledExecutorService);
        this.mUserDiskStores.put(userIdentifier, hibernationStateDiskStore);
        this.mBackgroundExecutor.execute(
                new AppHibernationService$$ExternalSyntheticLambda3(
                        this, hibernationStateDiskStore, userIdentifier, 0));
    }

    public final void setHibernatingForUser(final String str, int i, boolean z) {
        if (sIsServiceEnabled) {
            getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.MANAGE_APP_HIBERNATION",
                            "Caller does not have MANAGE_APP_HIBERNATION permission.");
            final int handleIncomingUser = handleIncomingUser(i, "setHibernatingForUser");
            synchronized (this.mLock) {
                try {
                    if (checkUserStatesExist(handleIncomingUser, "setHibernatingForUser", true)) {
                        final UserLevelState userLevelState =
                                (UserLevelState)
                                        ((Map) this.mUserStates.get(handleIncomingUser)).get(str);
                        if (userLevelState != null
                                && this.mPackageManagerInternal.canQueryPackage(
                                        Binder.getCallingUid(), str)) {
                            if (userLevelState.hibernated == z) {
                                return;
                            }
                            userLevelState.hibernated = z;
                            if (z) {
                                this.mBackgroundExecutor.execute(
                                        new Runnable() { // from class:
                                            // com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                AppHibernationService appHibernationService =
                                                        AppHibernationService.this;
                                                String str2 = str;
                                                int i2 = handleIncomingUser;
                                                UserLevelState userLevelState2 = userLevelState;
                                                appHibernationService.getClass();
                                                Trace.traceBegin(524288L, "hibernatePackage");
                                                long clearCallingIdentity =
                                                        Binder.clearCallingIdentity();
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                ApplicationInfo applicationInfo =
                                                                        appHibernationService
                                                                                .mIPackageManager
                                                                                .getApplicationInfo(
                                                                                        str2,
                                                                                        537698816L,
                                                                                        i2);
                                                                StorageStats queryStatsForPackage =
                                                                        appHibernationService
                                                                                .mStorageStatsManager
                                                                                .queryStatsForPackage(
                                                                                        applicationInfo
                                                                                                .storageUuid,
                                                                                        str2,
                                                                                        new UserHandle(
                                                                                                i2));
                                                                if (Flags.appRestrictionsApi()) {
                                                                    try {
                                                                        appHibernationService
                                                                                .mIActivityManager
                                                                                .noteAppRestrictionEnabled(
                                                                                        str2,
                                                                                        applicationInfo
                                                                                                .uid,
                                                                                        60,
                                                                                        true,
                                                                                        2,
                                                                                        (String)
                                                                                                null,
                                                                                        3,
                                                                                        7776000000L);
                                                                    } catch (
                                                                            RemoteException
                                                                                    unused) {
                                                                        Slog.e(
                                                                                "AppHibernationService",
                                                                                "Couldn't set"
                                                                                    + " restriction"
                                                                                    + " state"
                                                                                    + " change");
                                                                    }
                                                                }
                                                                appHibernationService
                                                                        .mIActivityManager
                                                                        .forceStopPackage(str2, i2);
                                                                appHibernationService
                                                                        .mIPackageManager
                                                                        .deleteApplicationCacheFilesAsUser(
                                                                                str2,
                                                                                i2,
                                                                                (IPackageDataObserver)
                                                                                        null);
                                                                synchronized (
                                                                        appHibernationService
                                                                                .mLock) {
                                                                    userLevelState2.savedByte =
                                                                            queryStatsForPackage
                                                                                    .getCacheBytes();
                                                                }
                                                            } catch (RemoteException e) {
                                                                throw new IllegalStateException(
                                                                        "Failed to hibernate due to"
                                                                            + " manager not being"
                                                                            + " available",
                                                                        e);
                                                            }
                                                        } catch (IOException e2) {
                                                            Slog.e(
                                                                    "AppHibernationService",
                                                                    "Storage device not found",
                                                                    e2);
                                                        }
                                                    } catch (
                                                            PackageManager.NameNotFoundException
                                                                    e3) {
                                                        Slog.e(
                                                                "AppHibernationService",
                                                                "Package name not found when"
                                                                    + " querying storage stats",
                                                                e3);
                                                    }
                                                    Binder.restoreCallingIdentity(
                                                            clearCallingIdentity);
                                                    Trace.traceEnd(524288L);
                                                } catch (Throwable th) {
                                                    Binder.restoreCallingIdentity(
                                                            clearCallingIdentity);
                                                    Trace.traceEnd(524288L);
                                                    throw th;
                                                }
                                            }
                                        });
                            } else {
                                this.mBackgroundExecutor.execute(
                                        new AppHibernationService$$ExternalSyntheticLambda3(
                                                this, str, handleIncomingUser, 1));
                                userLevelState.lastUnhibernatedMs = System.currentTimeMillis();
                            }
                            final UserLevelState userLevelState2 = new UserLevelState();
                            userLevelState2.packageName = userLevelState.packageName;
                            userLevelState2.hibernated = userLevelState.hibernated;
                            userLevelState2.savedByte = userLevelState.savedByte;
                            userLevelState2.lastUnhibernatedMs = userLevelState.lastUnhibernatedMs;
                            this.mBackgroundExecutor.execute(
                                    new Runnable() { // from class:
                                                     // com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda6
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            UserLevelState userLevelState3 = UserLevelState.this;
                                            FrameworkStatsLog.write(
                                                    FrameworkStatsLog
                                                            .USER_LEVEL_HIBERNATION_STATE_CHANGED,
                                                    userLevelState3.packageName,
                                                    handleIncomingUser,
                                                    userLevelState3.hibernated);
                                        }
                                    });
                            ((HibernationStateDiskStore)
                                            this.mUserDiskStores.get(handleIncomingUser))
                                    .scheduleWriteHibernationStates(
                                            new ArrayList(
                                                    ((Map) this.mUserStates.get(handleIncomingUser))
                                                            .values()));
                            return;
                        }
                        Slog.e(
                                "AppHibernationService",
                                TextUtils.formatSimple(
                                        "Package %s is not installed for user %s",
                                        new Object[] {str, Integer.valueOf(handleIncomingUser)}));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setHibernatingGlobally(final String str, boolean z) {
        if (sIsServiceEnabled) {
            getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.MANAGE_APP_HIBERNATION",
                            "Caller does not have MANAGE_APP_HIBERNATION permission.");
            synchronized (this.mLock) {
                try {
                    final GlobalLevelState globalLevelState =
                            (GlobalLevelState) ((ArrayMap) this.mGlobalHibernationStates).get(str);
                    if (globalLevelState != null
                            && this.mPackageManagerInternal.canQueryPackage(
                                    Binder.getCallingUid(), str)) {
                        if (globalLevelState.hibernated != z) {
                            globalLevelState.hibernated = z;
                            if (z) {
                                this.mBackgroundExecutor.execute(
                                        new Runnable() { // from class:
                                                         // com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda7
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                AppHibernationService appHibernationService =
                                                        AppHibernationService.this;
                                                String str2 = str;
                                                GlobalLevelState globalLevelState2 =
                                                        globalLevelState;
                                                appHibernationService.getClass();
                                                Trace.traceBegin(
                                                        524288L, "hibernatePackageGlobally");
                                                long j = 0;
                                                if (appHibernationService
                                                        .mOatArtifactDeletionEnabled) {
                                                    ((PackageManagerService
                                                                            .PackageManagerInternalImpl)
                                                                    appHibernationService
                                                                            .mPackageManagerInternal)
                                                            .mService.snapshotComputer();
                                                    j =
                                                            Math.max(
                                                                    PackageManagerService
                                                                            .deleteOatArtifactsOfPackage(
                                                                                    str2),
                                                                    0L);
                                                }
                                                synchronized (appHibernationService.mLock) {
                                                    globalLevelState2.savedByte = j;
                                                }
                                                Trace.traceEnd(524288L);
                                            }
                                        });
                            } else {
                                globalLevelState.savedByte = 0L;
                                globalLevelState.lastUnhibernatedMs = System.currentTimeMillis();
                            }
                            this.mGlobalLevelHibernationDiskStore.scheduleWriteHibernationStates(
                                    new ArrayList(
                                            ((ArrayMap) this.mGlobalHibernationStates).values()));
                        }
                        return;
                    }
                    Slog.e(
                            "AppHibernationService",
                            TextUtils.formatSimple(
                                    "Package %s is not installed for any user",
                                    new Object[] {str}));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
