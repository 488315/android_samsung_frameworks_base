package com.android.server.pm;

import android.content.Context;
import android.content.pm.PackageStats;
import android.os.Binder;
import android.os.CreateAppDataArgs;
import android.os.CreateAppDataResult;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.server.SystemService;

import dalvik.system.BlockGuard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Installer extends SystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public volatile boolean mDeferSetFirstBoot;
    public volatile IInstalld mInstalld;
    public volatile CountDownLatch mInstalldLatch;
    public final boolean mIsolated;
    public volatile Object mWarnIfHeld;

    public final class Batch {
        public boolean mExecuted;
        public final List mArgs = new ArrayList();
        public final List mFutures = new ArrayList();

        public final synchronized CompletableFuture createAppData(
                CreateAppDataArgs createAppDataArgs) {
            CompletableFuture completableFuture;
            if (this.mExecuted) {
                throw new IllegalStateException();
            }
            completableFuture = new CompletableFuture();
            ((ArrayList) this.mArgs).add(createAppDataArgs);
            ((ArrayList) this.mFutures).add(completableFuture);
            return completableFuture;
        }

        public final synchronized void execute(Installer installer) {
            CreateAppDataResult[] createAppDataBatched;
            try {
                if (this.mExecuted) {
                    throw new IllegalStateException();
                }
                this.mExecuted = true;
                int size = ((ArrayList) this.mArgs).size();
                for (int i = 0; i < size; i += 256) {
                    int min = Math.min(size - i, 256);
                    CreateAppDataArgs[] createAppDataArgsArr = new CreateAppDataArgs[min];
                    for (int i2 = 0; i2 < min; i2++) {
                        createAppDataArgsArr[i2] =
                                (CreateAppDataArgs) ((ArrayList) this.mArgs).get(i + i2);
                    }
                    if (installer.checkBeforeRemote()) {
                        for (int i3 = 0; i3 < min; i3++) {
                            createAppDataArgsArr[i3].previousAppId = 0;
                        }
                        try {
                            createAppDataBatched =
                                    installer.mInstalld.createAppDataBatched(createAppDataArgsArr);
                        } catch (Exception e) {
                            InstallerException.from(e);
                            throw null;
                        }
                    } else {
                        createAppDataBatched = new CreateAppDataResult[min];
                        CreateAppDataResult createAppDataResult = new CreateAppDataResult();
                        createAppDataResult.ceDataInode = -1L;
                        createAppDataResult.deDataInode = -1L;
                        createAppDataResult.exceptionCode = 0;
                        createAppDataResult.exceptionMessage = null;
                        Arrays.fill(createAppDataBatched, createAppDataResult);
                    }
                    for (int i4 = 0; i4 < createAppDataBatched.length; i4++) {
                        CreateAppDataResult createAppDataResult2 = createAppDataBatched[i4];
                        CompletableFuture completableFuture =
                                (CompletableFuture) ((ArrayList) this.mFutures).get(i + i4);
                        if (createAppDataResult2.exceptionCode == 0) {
                            completableFuture.complete(createAppDataResult2);
                        } else {
                            completableFuture.completeExceptionally(
                                    new InstallerException(createAppDataResult2.exceptionMessage));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public class InstallerException extends Exception {
        public InstallerException(String str) {
            super(str);
        }

        public static void from(Exception exc) {
            throw new InstallerException(exc.toString());
        }
    }

    public class LegacyDexoptDisabledException extends Exception {
        public LegacyDexoptDisabledException() {
            super("Invalid call to legacy dexopt method while ART Service is in use.");
        }
    }

    static {
        SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c");
    }

    public Installer(Context context) {
        this(context, false);
    }

    public Installer(Context context, boolean z) {
        super(context);
        this.mInstalld = null;
        this.mInstalldLatch = new CountDownLatch(1);
        this.mIsolated = z;
    }

    public static CreateAppDataArgs buildCreateAppDataArgs(
            int i, int i2, boolean z, int i3, String str, String str2, String str3, int i4) {
        CreateAppDataArgs createAppDataArgs = new CreateAppDataArgs();
        createAppDataArgs.uuid = str;
        createAppDataArgs.packageName = str2;
        createAppDataArgs.userId = i;
        createAppDataArgs.flags = i2;
        if (z) {
            createAppDataArgs.flags = i2 | 8;
        }
        createAppDataArgs.appId = i3;
        createAppDataArgs.seInfo = str3;
        createAppDataArgs.targetSdkVersion = i4;
        return createAppDataArgs;
    }

    public final boolean checkBeforeRemote() {
        if (this.mWarnIfHeld != null && Thread.holdsLock(this.mWarnIfHeld)) {
            Slog.wtf(
                    "Installer",
                    "Calling thread "
                            + Thread.currentThread().getName()
                            + " is holding 0x"
                            + Integer.toHexString(System.identityHashCode(this.mWarnIfHeld)),
                    new Throwable());
        }
        if (this.mIsolated) {
            Slog.i("Installer", "Ignoring request because this installer is isolated");
            return false;
        }
        try {
            if (this.mInstalldLatch.await(10000L, TimeUnit.MILLISECONDS)) {
                return true;
            }
            throw new InstallerException("time out waiting for the installer to be ready");
        } catch (InterruptedException unused) {
            return true;
        }
    }

    public final void clearAppData(String str, String str2, int i, int i2, long j) {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.clearAppData(str, str2, i, i2, j);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                EventLog.writeEvent(
                        39000,
                        Integer.valueOf(Binder.getCallingPid()),
                        Integer.valueOf(Binder.getCallingUid()),
                        str2,
                        Integer.valueOf(i2));
                for (int i3 = 2; i3 < stackTrace.length; i3++) {
                    EventLog.writeEvent(
                            39001,
                            stackTrace[i3].getMethodName(),
                            stackTrace[i3].getClassName(),
                            stackTrace[i3].getFileName(),
                            Integer.valueOf(stackTrace[i3].getLineNumber()));
                }
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public final void connect() {
        IBinder service = ServiceManager.getService("installd");
        if (service != null) {
            try {
                service.linkToDeath(
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.pm.Installer$$ExternalSyntheticLambda0
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                Installer installer = Installer.this;
                                installer.getClass();
                                Slog.w("Installer", "installd died; reconnecting");
                                installer.mInstalldLatch = new CountDownLatch(1);
                                installer.connect();
                            }
                        },
                        0);
            } catch (RemoteException unused) {
                service = null;
            }
        }
        if (service == null) {
            Slog.w("Installer", "installd not found; trying again");
            BackgroundThread.getHandler()
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.android.server.pm.Installer$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Installer.this.connect();
                                }
                            },
                            1000L);
            return;
        }
        this.mInstalld = IInstalld.Stub.asInterface(service);
        this.mInstalldLatch.countDown();
        try {
            if (checkBeforeRemote()) {
                try {
                    this.mInstalld.invalidateMounts();
                } catch (Exception e) {
                    InstallerException.from(e);
                    throw null;
                }
            }
            if (this.mDeferSetFirstBoot) {
                setFirstBoot();
            }
        } catch (InstallerException unused2) {
        }
    }

    public final void destroyAppData(String str, String str2, int i, int i2, long j) {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.destroyAppData(str, str2, i, i2, j);
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public boolean dexopt(
            String str,
            int i,
            String str2,
            String str3,
            int i2,
            String str4,
            int i3,
            String str5,
            String str6,
            String str7,
            String str8,
            int i4,
            String str9,
            String str10,
            String str11) {
        throw new LegacyDexoptDisabledException();
    }

    public final void freeCache(String str, long j, int i) {
        if (checkBeforeRemote()) {
            try {
                this.mInstalld.freeCache(str, j, i);
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public final void getAppSize(
            String str,
            String[] strArr,
            int i,
            int i2,
            int i3,
            long[] jArr,
            String[] strArr2,
            PackageStats packageStats) {
        long[] jArr2;
        if (checkBeforeRemote()) {
            if (strArr2 != null) {
                for (String str2 : strArr2) {
                    BlockGuard.getVmPolicy().onPathAccess(str2);
                }
            }
            try {
                long[] appSize = this.mInstalld.getAppSize(str, strArr, i, i2, i3, jArr, strArr2);
                packageStats.codeSize += appSize[0];
                packageStats.dataSize += appSize[1];
                packageStats.cacheSize += appSize[2];
                packageStats.externalCodeSize += appSize[3];
                packageStats.externalDataSize += appSize[4];
                packageStats.externalCacheSize += appSize[5];
                if (str == null) {
                    Iterator it =
                            ((StorageManager) getContext().getSystemService(StorageManager.class))
                                    .getVolumes()
                                    .iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            jArr2 = null;
                            break;
                        }
                        VolumeInfo volumeInfo = (VolumeInfo) it.next();
                        if (volumeInfo.getDisk() != null
                                && volumeInfo.getDisk().isSd()
                                && volumeInfo.isMountedWritable()) {
                            jArr2 =
                                    this.mInstalld.getAppSize(
                                            volumeInfo.getFsUuid(),
                                            strArr,
                                            i,
                                            i2,
                                            i3,
                                            jArr,
                                            strArr2);
                            break;
                        }
                    }
                    if (jArr2 != null) {
                        packageStats.codeSize += jArr2[0];
                        packageStats.dataSize += jArr2[1];
                        packageStats.cacheSize += jArr2[2];
                        packageStats.externalCodeSize += jArr2[3];
                        packageStats.externalDataSize += jArr2[4];
                        packageStats.externalCacheSize += jArr2[5];
                    }
                }
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public final void getUserSize(
            String str, int i, int i2, int[] iArr, PackageStats packageStats) {
        if (checkBeforeRemote()) {
            try {
                long[] userSize = this.mInstalld.getUserSize(str, i, i2, iArr);
                packageStats.codeSize += userSize[0];
                packageStats.dataSize += userSize[1];
                packageStats.cacheSize += userSize[2];
                packageStats.externalCodeSize += userSize[3];
                packageStats.externalDataSize += userSize[4];
                packageStats.externalCacheSize += userSize[5];
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public final void linkFile(String str, String str2, String str3, String str4) {
        if (checkBeforeRemote()) {
            BlockGuard.getVmPolicy().onPathAccess(str3);
            BlockGuard.getVmPolicy().onPathAccess(str4);
            try {
                this.mInstalld.linkFile(str, str2, str3, str4);
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        if (!this.mIsolated) {
            connect();
        } else {
            this.mInstalld = null;
            this.mInstalldLatch.countDown();
        }
    }

    public final void rmPackageDir(String str, String str2) {
        if (checkBeforeRemote()) {
            BlockGuard.getVmPolicy().onPathAccess(str2);
            try {
                this.mInstalld.rmPackageDir(str, str2);
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public final void setFirstBoot() {
        if (checkBeforeRemote()) {
            try {
                if (this.mInstalld != null) {
                    this.mInstalld.setFirstBoot();
                } else {
                    this.mDeferSetFirstBoot = true;
                }
            } catch (Exception e) {
                InstallerException.from(e);
                throw null;
            }
        }
    }

    public final void setWarnIfHeld(Object obj) {
        this.mWarnIfHeld = obj;
    }
}
