package com.android.server.pm;

import android.app.Flags;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IBackgroundInstallControlService;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.SparseSetArray;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.pm.permission.PermissionManagerService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class BackgroundInstallControlService extends SystemService {
    public SparseSetArray mBackgroundInstalledPackages;
    public final BinderService mBinderService;
    public final BackgroundInstallControlCallbackHelper mCallbackHelper;
    public final Context mContext;
    public final File mDiskFile;
    public final EventHandler mHandler;
    public final SparseArrayMap mInstallerForegroundTimeFrames;
    public final PackageManager mPackageManager;
    public final PackageManagerInternal mPackageManagerInternal;
    protected final PackageManagerInternal.PackageListObserver mPackageObserver;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;

    public final class BinderService extends IBackgroundInstallControlService.Stub {
        public final BackgroundInstallControlService mService;

        public BinderService(BackgroundInstallControlService backgroundInstallControlService) {
            this.mService = backgroundInstallControlService;
        }

        public final ParceledListSlice getBackgroundInstalledPackages(long j, int i) {
            if (Flags.bicClient()) {
                this.mService.mContext.enforceCallingOrSelfPermission(
                        "android.permission.GET_BACKGROUND_INSTALLED_PACKAGES",
                        "User is not permitted to call service:"
                            + " android.permission.GET_BACKGROUND_INSTALLED_PACKAGES");
            }
            if (!Build.IS_DEBUGGABLE) {
                return this.mService.getBackgroundInstalledPackages(j, i);
            }
            String str = SystemProperties.get("debug.transparency.bg-install-apps");
            if (TextUtils.isEmpty(str)) {
                return this.mService.getBackgroundInstalledPackages(j, i);
            }
            BackgroundInstallControlService backgroundInstallControlService = this.mService;
            backgroundInstallControlService.getClass();
            String[] split = str.split(",");
            ArrayList arrayList = new ArrayList();
            for (String str2 : split) {
                try {
                    arrayList.add(
                            backgroundInstallControlService.mPackageManager.getPackageInfo(
                                    str2, PackageManager.PackageInfoFlags.of(131072L)));
                } catch (PackageManager.NameNotFoundException unused) {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m(
                            "Package's PackageInfo not found ",
                            str2,
                            "BackgroundInstallControlService");
                }
            }
            return new ParceledListSlice(arrayList);
        }

        public final void registerBackgroundInstallCallback(IRemoteCallback iRemoteCallback) {
            BackgroundInstallControlCallbackHelper backgroundInstallControlCallbackHelper =
                    this.mService.mCallbackHelper;
            synchronized (backgroundInstallControlCallbackHelper.mCallbacks) {
                backgroundInstallControlCallbackHelper.mCallbacks.register(iRemoteCallback, null);
            }
        }

        public final void unregisterBackgroundInstallCallback(IRemoteCallback iRemoteCallback) {
            BackgroundInstallControlCallbackHelper backgroundInstallControlCallbackHelper =
                    this.mService.mCallbackHelper;
            synchronized (backgroundInstallControlCallbackHelper.mCallbacks) {
                backgroundInstallControlCallbackHelper.mCallbacks.unregister(iRemoteCallback);
            }
        }
    }

    public final class EventHandler extends Handler {
        public final BackgroundInstallControlService mService;

        public EventHandler(
                Looper looper, BackgroundInstallControlService backgroundInstallControlService) {
            super(looper);
            this.mService = backgroundInstallControlService;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                BackgroundInstallControlService backgroundInstallControlService = this.mService;
                UsageEvents.Event event = (UsageEvents.Event) message.obj;
                int i2 = message.arg1;
                backgroundInstallControlService.getClass();
                int i3 = event.mEventType;
                if (i3 == 1 || i3 == 2 || i3 == 23) {
                    String str = event.mPackage;
                    if (backgroundInstallControlService.mInstallerForegroundTimeFrames.contains(
                                    i2, str)
                            || PermissionManagerService.this.checkPermission(
                                            str,
                                            "android.permission.INSTALL_PACKAGES",
                                            "default:0",
                                            i2)
                                    == 0) {
                        if (!backgroundInstallControlService.mInstallerForegroundTimeFrames
                                .contains(i2, event.mPackage)) {
                            backgroundInstallControlService.mInstallerForegroundTimeFrames.add(
                                    i2, event.mPackage, new TreeSet());
                        }
                        TreeSet treeSet =
                                (TreeSet)
                                        backgroundInstallControlService
                                                .mInstallerForegroundTimeFrames.get(
                                                i2, event.mPackage);
                        if (treeSet.size() == 0
                                || ((ForegroundTimeFrame) treeSet.last()).endTimeStampMillis != 0) {
                            if (event.mEventType != 1) {
                                return;
                            } else {
                                treeSet.add(new ForegroundTimeFrame(event.mTimeStamp));
                            }
                        }
                        ForegroundTimeFrame foregroundTimeFrame =
                                (ForegroundTimeFrame) treeSet.last();
                        foregroundTimeFrame.getClass();
                        int i4 = event.mEventType;
                        if (i4 == 1) {
                            ((ArraySet) foregroundTimeFrame.activities)
                                    .add(Integer.valueOf(event.mInstanceId));
                        } else if (i4 == 2 || i4 == 23) {
                            if (((ArraySet) foregroundTimeFrame.activities)
                                    .contains(Integer.valueOf(event.mInstanceId))) {
                                ((ArraySet) foregroundTimeFrame.activities)
                                        .remove(Integer.valueOf(event.mInstanceId));
                                if (((ArraySet) foregroundTimeFrame.activities).size() == 0) {
                                    foregroundTimeFrame.endTimeStampMillis = event.mTimeStamp;
                                }
                            }
                        }
                        if (treeSet.size() > 10) {
                            treeSet.pollFirst();
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (i != 1) {
                if (i != 2) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unknown message: "),
                            message.what,
                            "BackgroundInstallControlService");
                    return;
                }
                BackgroundInstallControlService backgroundInstallControlService2 = this.mService;
                String str2 = (String) message.obj;
                int i5 = message.arg1;
                backgroundInstallControlService2.initBackgroundInstalledPackages();
                backgroundInstallControlService2.mBackgroundInstalledPackages.remove(i5, str2);
                backgroundInstallControlService2.writeBackgroundInstalledPackagesToDisk();
                return;
            }
            BackgroundInstallControlService backgroundInstallControlService3 = this.mService;
            String str3 = (String) message.obj;
            int i6 = message.arg1;
            backgroundInstallControlService3.getClass();
            try {
                ApplicationInfo applicationInfoAsUser =
                        backgroundInstallControlService3.mPackageManager.getApplicationInfoAsUser(
                                str3, PackageManager.ApplicationInfoFlags.of(0L), i6);
                try {
                    InstallSourceInfo installSourceInfo =
                            backgroundInstallControlService3.mPackageManager.getInstallSourceInfo(
                                    str3);
                    String installingPackageName = installSourceInfo.getInstallingPackageName();
                    String initiatingPackageName = installSourceInfo.getInitiatingPackageName();
                    if (PermissionManagerService.this.checkPermission(
                                    installingPackageName,
                                    "android.permission.INSTALL_PACKAGES",
                                    "default:0",
                                    i6)
                            != 0) {
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long j = applicationInfoAsUser.createTimestamp;
                    try {
                        Optional latestInstallSession =
                                backgroundInstallControlService3.getLatestInstallSession(i6, str3);
                        if (latestInstallSession.isEmpty()) {
                            Slog.w(
                                    "BackgroundInstallControlService",
                                    "Package's historical install session not found, falling back"
                                        + " to appInfo.createTimestamp: "
                                            + str3);
                        } else {
                            j =
                                    ((PackageInstaller.SessionInfo) latestInstallSession.get())
                                            .getCreatedMillis();
                        }
                    } catch (Exception e) {
                        Slog.w(
                                "BackgroundInstallControlService",
                                "Retrieval of install time from historical session failed, falling"
                                    + " back to appInfo.createTimestamp");
                        Slog.w("BackgroundInstallControlService", Log.getStackTraceString(e));
                    }
                    long j2 = currentTimeMillis - (uptimeMillis - j);
                    if (PackageManagerServiceUtils.isInstalledByAdb(initiatingPackageName)) {
                        return;
                    }
                    TreeSet treeSet2 =
                            (TreeSet)
                                    backgroundInstallControlService3.mInstallerForegroundTimeFrames
                                            .get(i6, installingPackageName);
                    if (treeSet2 != null) {
                        Iterator it = treeSet2.iterator();
                        while (it.hasNext()) {
                            ForegroundTimeFrame foregroundTimeFrame2 =
                                    (ForegroundTimeFrame) it.next();
                            if (foregroundTimeFrame2.startTimeStampMillis <= j2) {
                                long j3 = foregroundTimeFrame2.endTimeStampMillis;
                                if (j3 == 0 || j2 <= j3) {
                                    return;
                                }
                            }
                        }
                    }
                    backgroundInstallControlService3.initBackgroundInstalledPackages();
                    backgroundInstallControlService3.mBackgroundInstalledPackages.add(i6, str3);
                    final BackgroundInstallControlCallbackHelper
                            backgroundInstallControlCallbackHelper =
                                    backgroundInstallControlService3.mCallbackHelper;
                    backgroundInstallControlCallbackHelper.getClass();
                    final Bundle bundle = new Bundle();
                    bundle.putCharSequence("packageName", str3);
                    bundle.putInt("userId", i6);
                    synchronized (backgroundInstallControlCallbackHelper.mCallbacks) {
                        backgroundInstallControlCallbackHelper.mHandler.post(
                                new Runnable() { // from class:
                                    // com.android.server.pm.BackgroundInstallControlCallbackHelper$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BackgroundInstallControlCallbackHelper
                                                backgroundInstallControlCallbackHelper2 =
                                                        BackgroundInstallControlCallbackHelper.this;
                                        final Bundle bundle2 = bundle;
                                        backgroundInstallControlCallbackHelper2.mCallbacks
                                                .broadcast(
                                                        new Consumer() { // from class:
                                                            // com.android.server.pm.BackgroundInstallControlCallbackHelper$$ExternalSyntheticLambda1
                                                            @Override // java.util.function.Consumer
                                                            public final void accept(Object obj) {
                                                                try {
                                                                    ((IRemoteCallback) obj)
                                                                            .sendResult(bundle2);
                                                                } catch (RemoteException e2) {
                                                                    Slog.e(
                                                                            "BackgroundInstallControlCallbackHelper",
                                                                            "error detected: "
                                                                                    + e2
                                                                                            .getLocalizedMessage(),
                                                                            e2);
                                                                }
                                                            }
                                                        });
                                    }
                                });
                    }
                    backgroundInstallControlService3.writeBackgroundInstalledPackagesToDisk();
                } catch (PackageManager.NameNotFoundException unused) {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m(
                            "Package's installer not found ",
                            str3,
                            "BackgroundInstallControlService");
                }
            } catch (PackageManager.NameNotFoundException unused2) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Package's appInfo not found ", str3, "BackgroundInstallControlService");
            }
        }
    }

    public final class ForegroundTimeFrame implements Comparable {
        public final long startTimeStampMillis;
        public long endTimeStampMillis = 0;
        public final Set activities = new ArraySet();

        public ForegroundTimeFrame(long j) {
            this.startTimeStampMillis = j;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            ForegroundTimeFrame foregroundTimeFrame = (ForegroundTimeFrame) obj;
            int compare =
                    Long.compare(
                            this.startTimeStampMillis, foregroundTimeFrame.startTimeStampMillis);
            return compare != 0
                    ? compare
                    : Integer.compare(hashCode(), foregroundTimeFrame.hashCode());
        }
    }

    public interface Injector {}

    public final class InjectorImpl implements Injector {
        public final Context mContext;

        public InjectorImpl(Context context) {
            this.mContext = context;
        }
    }

    public BackgroundInstallControlService(Context context) {
        this(new InjectorImpl(context));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BackgroundInstallControlService(
            com.android.server.pm.BackgroundInstallControlService.Injector r6) {
        /*
            r5 = this;
            com.android.server.pm.BackgroundInstallControlService$InjectorImpl r6 = (com.android.server.pm.BackgroundInstallControlService.InjectorImpl) r6
            android.content.Context r0 = r6.mContext
            r5.<init>(r0)
            r0 = 0
            r5.mBackgroundInstalledPackages = r0
            android.util.SparseArrayMap r0 = new android.util.SparseArrayMap
            r0.<init>()
            r5.mInstallerForegroundTimeFrames = r0
            com.android.server.pm.BackgroundInstallControlService$1 r0 = new com.android.server.pm.BackgroundInstallControlService$1
            r0.<init>()
            r5.mPackageObserver = r0
            android.content.Context r0 = r6.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            r5.mPackageManager = r0
            java.lang.Class<android.content.pm.PackageManagerInternal> r0 = android.content.pm.PackageManagerInternal.class
            java.lang.Object r0 = com.android.server.LocalServices.getService(r0)
            android.content.pm.PackageManagerInternal r0 = (android.content.pm.PackageManagerInternal) r0
            r5.mPackageManagerInternal = r0
            java.lang.Class<com.android.server.pm.permission.PermissionManagerService$PermissionManagerServiceInternalImpl> r0 = com.android.server.pm.permission.PermissionManagerService.PermissionManagerServiceInternalImpl.class
            java.lang.Object r0 = com.android.server.LocalServices.getService(r0)
            com.android.server.pm.permission.PermissionManagerService$PermissionManagerServiceInternalImpl r0 = (com.android.server.pm.permission.PermissionManagerService.PermissionManagerServiceInternalImpl) r0
            r5.mPermissionManager = r0
            com.android.server.pm.BackgroundInstallControlService$EventHandler r0 = new com.android.server.pm.BackgroundInstallControlService$EventHandler
            com.android.server.ServiceThread r1 = new com.android.server.ServiceThread
            java.lang.String r2 = "BackgroundInstallControlService"
            r3 = -2
            r4 = 1
            r1.<init>(r3, r2, r4)
            r1.start()
            android.os.Looper r1 = r1.getLooper()
            r0.<init>(r1, r5)
            r5.mHandler = r0
            java.io.File r0 = new java.io.File
            java.io.File r1 = android.os.Environment.getDataSystemDirectory()
            java.lang.String r2 = "bic"
            r0.<init>(r1, r2)
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "states"
            r1.<init>(r0, r2)
            r5.mDiskFile = r1
            android.content.Context r6 = r6.mContext
            r5.mContext = r6
            com.android.server.pm.BackgroundInstallControlCallbackHelper r6 = new com.android.server.pm.BackgroundInstallControlCallbackHelper
            r6.<init>()
            r5.mCallbackHelper = r6
            java.lang.Class<android.app.usage.UsageStatsManagerInternal> r6 = android.app.usage.UsageStatsManagerInternal.class
            java.lang.Object r6 = com.android.server.LocalServices.getService(r6)
            android.app.usage.UsageStatsManagerInternal r6 = (android.app.usage.UsageStatsManagerInternal) r6
            com.android.server.pm.BackgroundInstallControlService$$ExternalSyntheticLambda0 r0 = new com.android.server.pm.BackgroundInstallControlService$$ExternalSyntheticLambda0
            r0.<init>()
            r6.registerListener(r0)
            com.android.server.pm.BackgroundInstallControlService$BinderService r6 = new com.android.server.pm.BackgroundInstallControlService$BinderService
            r6.<init>(r5)
            r5.mBinderService = r6
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.BackgroundInstallControlService.<init>(com.android.server.pm.BackgroundInstallControlService$Injector):void");
    }

    public ParceledListSlice getBackgroundInstalledPackages(long j, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List installedPackagesAsUser =
                    this.mPackageManager.getInstalledPackagesAsUser(
                            PackageManager.PackageInfoFlags.of(j), i);
            initBackgroundInstalledPackages();
            ListIterator listIterator = installedPackagesAsUser.listIterator();
            while (listIterator.hasNext()) {
                if (!this.mBackgroundInstalledPackages.contains(
                        i, ((PackageInfo) listIterator.next()).packageName)) {
                    listIterator.remove();
                }
            }
            ParceledListSlice parceledListSlice = new ParceledListSlice(installedPackagesAsUser);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return parceledListSlice;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public SparseSetArray getBackgroundInstalledPackages() {
        return this.mBackgroundInstalledPackages;
    }

    public SparseArrayMap getInstallerForegroundTimeFrames() {
        return this.mInstallerForegroundTimeFrames;
    }

    public final Optional getLatestInstallSession(int i, final String str) {
        int i2;
        PackageInstallerService packageInstallerService =
                PackageManagerService.this.mInstallerService;
        packageInstallerService.getClass();
        int callingUid = Binder.getCallingUid();
        Computer snapshotComputer = packageInstallerService.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission("getAllSessions", callingUid, i, true, false);
        ArrayList arrayList = new ArrayList();
        synchronized (packageInstallerService.mSessions) {
            while (i2 < ((ArrayList) packageInstallerService.mHistoricalSessions).size()) {
                try {
                    PackageInstallerHistoricalSession packageInstallerHistoricalSession =
                            (PackageInstallerHistoricalSession)
                                    ((ArrayList) packageInstallerService.mHistoricalSessions)
                                            .get(i2);
                    i2 = (i == -1 || packageInstallerHistoricalSession.userId == i) ? 0 : i2 + 1;
                    arrayList.add(packageInstallerHistoricalSession.generateInfo());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        arrayList.removeIf(
                new PackageInstallerService$$ExternalSyntheticLambda0(
                        packageInstallerService, snapshotComputer, callingUid, 0));
        return new ParceledListSlice(arrayList)
                .getList().stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.server.pm.BackgroundInstallControlService$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return str.equals(
                                                ((PackageInstaller.SessionInfo) obj)
                                                        .getAppPackageName());
                                    }
                                })
                        .max(
                                Comparator.comparingLong(
                                        new BackgroundInstallControlService$$ExternalSyntheticLambda2()));
    }

    public void initBackgroundInstalledPackages() {
        if (this.mBackgroundInstalledPackages != null) {
            return;
        }
        this.mBackgroundInstalledPackages = new SparseSetArray();
        if (this.mDiskFile.exists()) {
            try {
                FileInputStream openRead = new AtomicFile(this.mDiskFile).openRead();
                try {
                    ProtoInputStream protoInputStream = new ProtoInputStream(openRead);
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() == 1) {
                            long start = protoInputStream.start(2246267895809L);
                            String str = null;
                            int i = -10000;
                            while (protoInputStream.nextField() != -1) {
                                int fieldNumber = protoInputStream.getFieldNumber();
                                if (fieldNumber == 1) {
                                    str = protoInputStream.readString(1138166333441L);
                                } else if (fieldNumber != 2) {
                                    Slog.w(
                                            "BackgroundInstallControlService",
                                            "Undefined field in proto: "
                                                    + protoInputStream.getFieldNumber());
                                } else {
                                    i = protoInputStream.readInt(1120986464258L) - 1;
                                }
                            }
                            protoInputStream.end(start);
                            if (str == null || i == -10000) {
                                Slog.w(
                                        "BackgroundInstallControlService",
                                        "Fails to get packageName or UserId from proto file");
                            } else {
                                this.mBackgroundInstalledPackages.add(i, str);
                            }
                        }
                    }
                    if (openRead != null) {
                        openRead.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Slog.w("BackgroundInstallControlService", "Error reading state from the disk", e);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        onStart(false);
    }

    public void onStart(boolean z) {
        if (!z) {
            publishBinderService("background_install_control", this.mBinderService);
        }
        this.mPackageManagerInternal.getPackageList(this.mPackageObserver);
    }

    public void writeBackgroundInstalledPackagesToDisk() {
        AtomicFile atomicFile = new AtomicFile(this.mDiskFile);
        try {
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream(startWrite);
                for (int i = 0; i < this.mBackgroundInstalledPackages.size(); i++) {
                    int keyAt = this.mBackgroundInstalledPackages.keyAt(i);
                    Iterator it = this.mBackgroundInstalledPackages.get(keyAt).iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        long start = protoOutputStream.start(2246267895809L);
                        protoOutputStream.write(1138166333441L, str);
                        protoOutputStream.write(1120986464258L, keyAt + 1);
                        protoOutputStream.end(start);
                    }
                }
                protoOutputStream.flush();
                atomicFile.finishWrite(startWrite);
            } catch (Exception e) {
                Slog.e(
                        "BackgroundInstallControlService",
                        "Failed to finish write to states protobuf.",
                        e);
                atomicFile.failWrite(startWrite);
            }
        } catch (IOException e2) {
            Slog.e(
                    "BackgroundInstallControlService",
                    "Failed to start write to states protobuf.",
                    e2);
        }
    }
}
