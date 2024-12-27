package com.android.server.usage;

import android.app.AppOpsManager;
import android.app.usage.ExternalStorageStats;
import android.app.usage.ICacheQuotaService;
import android.app.usage.IStorageStatsManager;
import android.app.usage.StorageStats;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.CrateInfo;
import android.os.storage.CrateMetadata;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DataUnit;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseLongArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.art.model.ArtManagedFileStats;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.storage.CacheQuotaStrategy;
import com.android.server.usage.StorageStatsManagerLocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StorageStatsService extends IStorageStatsManager.Stub {
    public static final long DEFAULT_QUOTA = DataUnit.MEBIBYTES.toBytes(64);
    public final AppOpsManager mAppOps;
    public final ArrayMap mCacheQuotas;
    public final Context mContext;
    public final H mHandler;
    public final Installer mInstaller;
    public final PackageManager mPackage;
    public final StorageManager mStorage;
    public final UserManager mUser;
    public final CopyOnWriteArrayList mStorageStatsAugmenters = new CopyOnWriteArrayList();
    public int mStorageThresholdPercentHigh = 20;
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public long mPreviousBytes;
        public final StatFs mStats;
        public final long mTotalBytes;

        public H(Looper looper) {
            super(looper);
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            this.mStats = statFs;
            this.mPreviousBytes = statFs.getAvailableBytes();
            this.mTotalBytes = statFs.getTotalBytes();
        }

        public static void recalculateQuotas(CacheQuotaStrategy cacheQuotaStrategy) {
            ServiceInfo serviceInfo;
            if (cacheQuotaStrategy.mServiceConnection == null) {
                cacheQuotaStrategy.mServiceConnection = new ServiceConnection() { // from class: com.android.server.storage.CacheQuotaStrategy.1

                    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
                    /* renamed from: com.android.server.storage.CacheQuotaStrategy$1$1 */
                    public final class RunnableC00241 implements Runnable {
                        public final /* synthetic */ IBinder val$service;

                        public RunnableC00241(IBinder iBinder) {
                            r2 = iBinder;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            synchronized (CacheQuotaStrategy.this.mLock) {
                                CacheQuotaStrategy.this.mRemoteService = ICacheQuotaService.Stub.asInterface(r2);
                                List m961$$Nest$mgetUnfulfilledRequests = CacheQuotaStrategy.m961$$Nest$mgetUnfulfilledRequests(CacheQuotaStrategy.this);
                                try {
                                    CacheQuotaStrategy.this.mRemoteService.computeCacheQuotaHints(new RemoteCallback(CacheQuotaStrategy.this), m961$$Nest$mgetUnfulfilledRequests);
                                } catch (RemoteException e) {
                                    Slog.w("CacheQuotaStrategy", "Remote exception occurred while trying to get cache quota", e);
                                }
                            }
                        }
                    }

                    public AnonymousClass1() {
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        AsyncTask.execute(new Runnable() { // from class: com.android.server.storage.CacheQuotaStrategy.1.1
                            public final /* synthetic */ IBinder val$service;

                            public RunnableC00241(IBinder iBinder2) {
                                r2 = iBinder2;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                synchronized (CacheQuotaStrategy.this.mLock) {
                                    CacheQuotaStrategy.this.mRemoteService = ICacheQuotaService.Stub.asInterface(r2);
                                    List m961$$Nest$mgetUnfulfilledRequests = CacheQuotaStrategy.m961$$Nest$mgetUnfulfilledRequests(CacheQuotaStrategy.this);
                                    try {
                                        CacheQuotaStrategy.this.mRemoteService.computeCacheQuotaHints(new RemoteCallback(CacheQuotaStrategy.this), m961$$Nest$mgetUnfulfilledRequests);
                                    } catch (RemoteException e) {
                                        Slog.w("CacheQuotaStrategy", "Remote exception occurred while trying to get cache quota", e);
                                    }
                                }
                            }
                        });
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                        synchronized (CacheQuotaStrategy.this.mLock) {
                            CacheQuotaStrategy.this.mRemoteService = null;
                        }
                    }
                };
            }
            String servicesSystemSharedLibraryPackageName = cacheQuotaStrategy.mContext.getPackageManager().getServicesSystemSharedLibraryPackageName();
            ComponentName componentName = null;
            if (servicesSystemSharedLibraryPackageName == null) {
                Slog.w("CacheQuotaStrategy", "could not access the cache quota service: no package!");
            } else {
                ResolveInfo resolveService = cacheQuotaStrategy.mContext.getPackageManager().resolveService(ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("android.app.usage.CacheQuotaService", servicesSystemSharedLibraryPackageName), 132);
                if (resolveService == null || (serviceInfo = resolveService.serviceInfo) == null) {
                    Slog.w("CacheQuotaStrategy", "No valid components found.");
                } else {
                    componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                }
            }
            if (componentName != null) {
                Intent intent = new Intent();
                intent.setComponent(componentName);
                cacheQuotaStrategy.mContext.bindServiceAsUser(intent, cacheQuotaStrategy.mServiceConnection, 1, UserHandle.CURRENT);
            }
        }

        public final CacheQuotaStrategy getInitializedStrategy() {
            UsageStatsManagerInternal usageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
            StorageStatsService storageStatsService = StorageStatsService.this;
            return new CacheQuotaStrategy(storageStatsService.mContext, usageStatsManagerInternal, storageStatsService.mInstaller, storageStatsService.mCacheQuotas);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            long j;
            if (StorageStatsService.isCacheQuotaCalculationsEnabled(StorageStatsService.this.mContext.getContentResolver())) {
                switch (message.what) {
                    case 100:
                        this.mStats.restat(Environment.getDataDirectory().getAbsolutePath());
                        long abs = Math.abs(this.mPreviousBytes - this.mStats.getAvailableBytes());
                        synchronized (StorageStatsService.this.mLock) {
                            try {
                                long availableBytes = this.mStats.getAvailableBytes();
                                long j2 = this.mTotalBytes;
                                j = availableBytes > (((long) StorageStatsService.this.mStorageThresholdPercentHigh) * j2) / 100 ? (j2 * 5) / 100 : (j2 * 2) / 100;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (abs > j) {
                            this.mPreviousBytes = this.mStats.getAvailableBytes();
                            recalculateQuotas(getInitializedStrategy());
                            StorageStatsService.this.mContext.getContentResolver().notifyChange(Uri.parse("content://com.android.externalstorage.documents/"), (ContentObserver) null, false);
                        }
                        sendEmptyMessageDelayed(100, 30000L);
                        return;
                    case 101:
                        CacheQuotaStrategy initializedStrategy = getInitializedStrategy();
                        this.mPreviousBytes = -1L;
                        try {
                            this.mPreviousBytes = initializedStrategy.setupQuotasFromFile();
                        } catch (IOException e) {
                            Slog.e("StorageStatsService", "An error occurred while reading the cache quota file.", e);
                        } catch (IllegalStateException e2) {
                            Slog.e("StorageStatsService", "Cache quota XML file is malformed?", e2);
                        }
                        if (this.mPreviousBytes < 0) {
                            this.mStats.restat(Environment.getDataDirectory().getAbsolutePath());
                            this.mPreviousBytes = this.mStats.getAvailableBytes();
                            recalculateQuotas(initializedStrategy);
                        }
                        sendEmptyMessageDelayed(100, 30000L);
                        sendEmptyMessageDelayed(102, 36000000L);
                        return;
                    case 102:
                        recalculateQuotas(getInitializedStrategy());
                        sendEmptyMessageDelayed(102, 36000000L);
                        return;
                    case 103:
                        recalculateQuotas(getInitializedStrategy());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("storagestats", new StorageStatsService(getContext()));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService implements StorageStatsManagerLocal {
        public LocalService() {
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal
        public final void registerStorageStatsAugmenter(StorageStatsManagerLocal.StorageStatsAugmenter storageStatsAugmenter, String str) {
            StorageStatsService.this.mStorageStatsAugmenters.add(Pair.create(str, storageStatsAugmenter));
        }
    }

    public StorageStatsService(Context context) {
        Context context2 = (Context) Preconditions.checkNotNull(context);
        this.mContext = context2;
        this.mAppOps = (AppOpsManager) Preconditions.checkNotNull((AppOpsManager) context.getSystemService(AppOpsManager.class));
        this.mUser = (UserManager) Preconditions.checkNotNull((UserManager) context.getSystemService(UserManager.class));
        this.mPackage = (PackageManager) Preconditions.checkNotNull(context.getPackageManager());
        StorageManager storageManager = (StorageManager) Preconditions.checkNotNull((StorageManager) context.getSystemService(StorageManager.class));
        this.mStorage = storageManager;
        this.mCacheQuotas = new ArrayMap();
        Installer installer = new Installer(context);
        this.mInstaller = installer;
        installer.onStart();
        try {
            if (installer.checkBeforeRemote()) {
                try {
                    installer.mInstalld.invalidateMounts();
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            }
        } catch (Installer.InstallerException e2) {
            Slog.wtf("StorageStatsService", "Failed to invalidate mounts", e2);
        }
        H h = new H(IoThread.get().getLooper());
        this.mHandler = h;
        h.sendEmptyMessage(101);
        storageManager.registerListener(new StorageEventListener() { // from class: com.android.server.usage.StorageStatsService.1
            public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
                int i3 = volumeInfo.type;
                if ((i3 == 0 || i3 == 1 || i3 == 2) && i2 == 2) {
                    StorageStatsService storageStatsService = StorageStatsService.this;
                    storageStatsService.getClass();
                    try {
                        Installer installer2 = storageStatsService.mInstaller;
                        if (installer2.checkBeforeRemote()) {
                            try {
                                installer2.mInstalld.invalidateMounts();
                            } catch (Exception e3) {
                                Installer.InstallerException.from(e3);
                                throw null;
                            }
                        }
                    } catch (Installer.InstallerException e4) {
                        Slog.wtf("StorageStatsService", "Failed to invalidate mounts", e4);
                    }
                }
            }
        });
        LocalManagerRegistry.addManager(StorageStatsManagerLocal.class, new LocalService());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        context2.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.usage.StorageStatsService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context3, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_FULLY_REMOVED".equals(action)) {
                    StorageStatsService.this.mHandler.removeMessages(103);
                    StorageStatsService.this.mHandler.sendEmptyMessage(103);
                }
            }
        }, intentFilter);
        updateConfig();
        DeviceConfig.addOnPropertiesChangedListener("storage_native_boot", context2.getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda3
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                StorageStatsService.this.updateConfig();
            }
        });
    }

    public static void checkCratesEnable() {
        if (!SystemProperties.getBoolean("fw.storage_crates", false)) {
            throw new IllegalStateException("Storage Crate feature is disabled.");
        }
    }

    public static void checkEquals(long j, long j2, String str) {
        if (j != j2) {
            Slog.e("StorageStatsService", str + " expected " + j + " actual " + j2);
        }
    }

    public static void checkEquals(String str, PackageStats packageStats, PackageStats packageStats2) {
        checkEquals(packageStats.codeSize, packageStats2.codeSize, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " codeSize"));
        checkEquals(packageStats.dataSize, packageStats2.dataSize, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), str, " dataSize"));
        checkEquals(packageStats.cacheSize, packageStats2.cacheSize, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), str, " cacheSize"));
        checkEquals(packageStats.externalCodeSize, packageStats2.externalCodeSize, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), str, " externalCodeSize"));
        checkEquals(packageStats.externalDataSize, packageStats2.externalDataSize, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), str, " externalDataSize"));
        checkEquals(packageStats.externalCacheSize, packageStats2.externalCacheSize, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), str, " externalCacheSize"));
    }

    public static void computeAppStatsByDataTypes(PackageStats packageStats, String str, String str2) {
        File file = new File(str);
        if (file.isFile()) {
            str = file.getParent();
            file = new File(str);
        }
        packageStats.apkSize = getFileBytesInDir(file, ".apk") + packageStats.apkSize;
        packageStats.dmSize = getFileBytesInDir(file, ".dm") + packageStats.dmSize;
        packageStats.libSize = getDirBytes(new File(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "/lib/"))) + packageStats.libSize;
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            ArtManagedFileStats artManagedFileStats = DexOptHelper.getArtManagerLocal().getArtManagedFileStats(withFilteredSnapshot, str2);
            if (withFilteredSnapshot != null) {
                withFilteredSnapshot.close();
            }
            packageStats.dexoptSize = artManagedFileStats.getTotalSizeBytesByType(0) + packageStats.dexoptSize;
            packageStats.refProfSize = artManagedFileStats.getTotalSizeBytesByType(1) + packageStats.refProfSize;
            packageStats.curProfSize = artManagedFileStats.getTotalSizeBytesByType(2) + packageStats.curProfSize;
        } catch (Throwable th) {
            if (withFilteredSnapshot != null) {
                try {
                    withFilteredSnapshot.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static List convertCrateInfoFrom(CrateMetadata[] crateMetadataArr) {
        CrateInfo copyFrom;
        if (ArrayUtils.isEmpty(crateMetadataArr)) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (CrateMetadata crateMetadata : crateMetadataArr) {
            if (crateMetadata != null && !TextUtils.isEmpty(crateMetadata.id) && !TextUtils.isEmpty(crateMetadata.packageName) && (copyFrom = CrateInfo.copyFrom(crateMetadata.uid, crateMetadata.packageName, crateMetadata.id)) != null) {
                arrayList.add(copyFrom);
            }
        }
        return arrayList;
    }

    public static long getDirBytes(File file) {
        long dirBytes;
        long j = 0;
        if (!file.isDirectory()) {
            return 0L;
        }
        try {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    dirBytes = file2.length();
                } else if (file2.isDirectory()) {
                    dirBytes = getDirBytes(file2);
                }
                j += dirBytes;
            }
        } catch (NullPointerException unused) {
            Slog.w("StorageStatsService", "Failed to list directory " + file.getName());
        }
        return j;
    }

    public static long getFileBytesInDir(File file, String str) {
        long j = 0;
        if (!file.isDirectory()) {
            return 0L;
        }
        try {
            for (File file2 : file.listFiles()) {
                if (file2.isFile() && file2.getName().endsWith(str)) {
                    j += file2.length();
                }
            }
        } catch (NullPointerException unused) {
            Slog.w("StorageStatsService", "Failed to list directory " + file.getName());
        }
        return j;
    }

    public static boolean isCacheQuotaCalculationsEnabled(ContentResolver contentResolver) {
        return Settings.Global.getInt(contentResolver, "enable_cache_quota_calculation", 1) != 0;
    }

    public static StorageStats translate(PackageStats packageStats) {
        StorageStats storageStats = new StorageStats();
        storageStats.codeBytes = packageStats.codeSize + packageStats.externalCodeSize;
        storageStats.dataBytes = packageStats.dataSize + packageStats.externalDataSize;
        long j = packageStats.cacheSize;
        long j2 = packageStats.externalCacheSize;
        storageStats.cacheBytes = j + j2;
        storageStats.dexoptBytes = packageStats.dexoptSize;
        storageStats.curProfBytes = packageStats.curProfSize;
        storageStats.refProfBytes = packageStats.refProfSize;
        storageStats.apkBytes = packageStats.apkSize;
        storageStats.libBytes = packageStats.libSize;
        storageStats.dmBytes = packageStats.dmSize;
        storageStats.externalCacheBytes = j2;
        return storageStats;
    }

    public final String checkStatsPermission(int i, String str, boolean z) {
        int noteOp = z ? this.mAppOps.noteOp(43, i, str) : this.mAppOps.checkOp(43, i, str);
        if (noteOp == 0) {
            return null;
        }
        if (noteOp != 3) {
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "Package ", str, " from UID ", " blocked by mode ");
            m.append(noteOp);
            return m.toString();
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") == 0) {
            return null;
        }
        return SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Caller does not have android.permission.PACKAGE_USAGE_STATS; callingPackage=", str, ", callingUid=");
    }

    public final void enforceStatsPermission(int i, String str) {
        String checkStatsPermission = checkStatsPermission(i, str, true);
        if (checkStatsPermission != null) {
            throw new SecurityException(checkStatsPermission);
        }
    }

    public final void forEachStorageStatsAugmenter(String str, Consumer consumer) {
        int size = this.mStorageStatsAugmenters.size();
        for (int i = 0; i < size; i++) {
            Pair pair = (Pair) this.mStorageStatsAugmenters.get(i);
            String str2 = (String) pair.first;
            StorageStatsManagerLocal.StorageStatsAugmenter storageStatsAugmenter = (StorageStatsManagerLocal.StorageStatsAugmenter) pair.second;
            Trace.traceBegin(524288L, str + ":" + str2);
            try {
                consumer.accept(storageStatsAugmenter);
                Trace.traceEnd(524288L);
            } catch (Throwable th) {
                Trace.traceEnd(524288L);
                throw th;
            }
        }
    }

    public final ParceledListSlice getAppCrates(String str, int i, String[] strArr) {
        try {
            Installer installer = this.mInstaller;
            CrateMetadata[] crateMetadataArr = null;
            if (installer.checkBeforeRemote()) {
                try {
                    crateMetadataArr = installer.mInstalld.getAppCrates(str, strArr, i);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            }
            return new ParceledListSlice(convertCrateInfoFrom(crateMetadataArr));
        } catch (Installer.InstallerException e2) {
            throw new ParcelableException(new IOException(e2.getMessage()));
        }
    }

    public final int[] getAppIds(int i) {
        Iterator it = this.mPackage.getInstalledApplicationsAsUser(8192, i).iterator();
        int[] iArr = null;
        while (it.hasNext()) {
            int appId = UserHandle.getAppId(((ApplicationInfo) it.next()).uid);
            if (!ArrayUtils.contains(iArr, appId)) {
                iArr = ArrayUtils.appendInt(iArr, appId);
            }
        }
        return iArr;
    }

    public final long getCacheBytes(String str, String str2) {
        enforceStatsPermission(Binder.getCallingUid(), str2);
        Iterator it = this.mUser.getUsers().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += queryStatsForUser(str, ((UserInfo) it.next()).id, null).cacheBytes;
        }
        return j;
    }

    public final long getCacheQuotaBytes(String str, int i, String str2) {
        enforceStatsPermission(Binder.getCallingUid(), str2);
        return this.mCacheQuotas.containsKey(str) ? ((SparseLongArray) this.mCacheQuotas.get(str)).get(i, DEFAULT_QUOTA) : DEFAULT_QUOTA;
    }

    public final long getFreeBytes(String str, String str2) {
        long usableSpace;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                File findPathForUuid = this.mStorage.findPathForUuid(str);
                if (isQuotaSupported(str, "android")) {
                    usableSpace = findPathForUuid.getUsableSpace() + Math.max(0L, getCacheBytes(str, "android") - this.mStorage.getStorageCacheBytes(findPathForUuid, 0));
                } else {
                    usableSpace = findPathForUuid.getUsableSpace();
                }
                Slog.d("StorageStatsService", "getFreeBytes: " + usableSpace);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return usableSpace;
            } catch (FileNotFoundException e) {
                throw new ParcelableException(e);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final long getTotalBytes(String str, String str2) {
        if (str != StorageManager.UUID_PRIVATE_INTERNAL) {
            VolumeInfo findVolumeByUuid = this.mStorage.findVolumeByUuid(str);
            if (findVolumeByUuid != null) {
                return FileUtils.roundStorageSize(findVolumeByUuid.disk.size);
            }
            throw new ParcelableException(new IOException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Failed to find storage device for UUID ", str)));
        }
        long primaryStorageSize = this.mStorage.getPrimaryStorageSize();
        DataUnit dataUnit = DataUnit.GIGABYTES;
        if (primaryStorageSize <= dataUnit.toBytes(512L)) {
            return primaryStorageSize;
        }
        long internalStorageBlockDeviceSize = this.mStorage.getInternalStorageBlockDeviceSize();
        long roundStorageSize = FileUtils.roundStorageSize(internalStorageBlockDeviceSize);
        return roundStorageSize - internalStorageBlockDeviceSize <= dataUnit.toBytes(3L) ? roundStorageSize : internalStorageBlockDeviceSize;
    }

    public final boolean isQuotaSupported(String str, String str2) {
        try {
            Installer installer = this.mInstaller;
            if (!installer.checkBeforeRemote()) {
                return false;
            }
            try {
                return installer.mInstalld.isQuotaSupported(str);
            } catch (Exception e) {
                Installer.InstallerException.from(e);
                throw null;
            }
        } catch (Installer.InstallerException e2) {
            throw new ParcelableException(new IOException(e2.getMessage()));
        }
    }

    public final boolean isReservedSupported(String str, String str2) {
        if (str == StorageManager.UUID_PRIVATE_INTERNAL) {
            return SystemProperties.getBoolean("vold.has_reserved", false) || Build.IS_ARC;
        }
        return false;
    }

    public final ParceledListSlice queryCratesForPackage(String str, String str2, int i, String str3) {
        checkCratesEnable();
        if (i != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        try {
            if (Binder.getCallingUid() != this.mPackage.getApplicationInfoAsUser(str2, 8192, i).uid) {
                Binder.getCallingUid();
                this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_CRATES", str3);
            }
            return getAppCrates(str, i, new String[]{str2});
        } catch (PackageManager.NameNotFoundException e) {
            throw new ParcelableException(e);
        }
    }

    public final ParceledListSlice queryCratesForUid(String str, int i, String str2) {
        checkCratesEnable();
        int userId = UserHandle.getUserId(i);
        if (userId != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        if (Binder.getCallingUid() != i) {
            Binder.getCallingUid();
            this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_CRATES", str2);
        }
        String[] strArr = new String[0];
        for (String str3 : ArrayUtils.defeatNullable(this.mPackage.getPackagesForUid(i))) {
            if (!TextUtils.isEmpty(str3)) {
                try {
                    if (this.mPackage.getApplicationInfoAsUser(str3, 8192, userId) != null) {
                        strArr = (String[]) ArrayUtils.appendElement(String.class, strArr, str3);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    throw new ParcelableException(e);
                }
            }
        }
        return getAppCrates(str, userId, strArr);
    }

    public final ParceledListSlice queryCratesForUser(String str, int i, String str2) {
        checkCratesEnable();
        if (i != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        Binder.getCallingUid();
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_CRATES", str2);
        try {
            Installer installer = this.mInstaller;
            CrateMetadata[] crateMetadataArr = null;
            if (installer.checkBeforeRemote()) {
                try {
                    crateMetadataArr = installer.mInstalld.getUserCrates(str, i);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            }
            return new ParceledListSlice(convertCrateInfoFrom(crateMetadataArr));
        } catch (Installer.InstallerException e2) {
            throw new ParcelableException(new IOException(e2.getMessage()));
        }
    }

    public final ExternalStorageStats queryExternalStatsForUser(String str, int i, String str2) {
        long[] externalSize;
        long[] externalSize2;
        if (i != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        enforceStatsPermission(Binder.getCallingUid(), str2);
        int[] appIds = getAppIds(i);
        try {
            Installer installer = this.mInstaller;
            int i2 = SystemProperties.getBoolean("fw.disable_quota", false) ? 0 : 4096;
            if (installer.checkBeforeRemote()) {
                try {
                    externalSize = installer.mInstalld.getExternalSize(str, i, i2, appIds);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } else {
                externalSize = new long[6];
            }
            if (SystemProperties.getBoolean("fw.verify_storage", false)) {
                Installer installer2 = this.mInstaller;
                if (installer2.checkBeforeRemote()) {
                    try {
                        externalSize2 = installer2.mInstalld.getExternalSize(str, i, 0, appIds);
                    } catch (Exception e2) {
                        Installer.InstallerException.from(e2);
                        throw null;
                    }
                } else {
                    externalSize2 = new long[6];
                }
                String str3 = "External " + i;
                for (int i3 = 0; i3 < externalSize2.length; i3++) {
                    checkEquals(externalSize2[i3], externalSize[i3], str3 + "[" + i3 + "]");
                }
            }
            ExternalStorageStats externalStorageStats = new ExternalStorageStats();
            externalStorageStats.totalBytes = externalSize[0];
            externalStorageStats.audioBytes = externalSize[1];
            externalStorageStats.videoBytes = externalSize[2];
            externalStorageStats.imageBytes = externalSize[3];
            externalStorageStats.appBytes = externalSize[4];
            externalStorageStats.obbBytes = externalSize[5];
            return externalStorageStats;
        } catch (Installer.InstallerException e3) {
            throw new ParcelableException(new IOException(e3.getMessage()));
        }
    }

    public final StorageStats queryStatsForPackage(String str, final String str2, int i, String str3) {
        final boolean z;
        if (i != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        try {
            ApplicationInfo applicationInfoAsUser = this.mPackage.getApplicationInfoAsUser(str2, 8192, i);
            if (Binder.getCallingUid() == applicationInfoAsUser.uid) {
                z = checkStatsPermission(Binder.getCallingUid(), str3, false) == null;
            } else {
                enforceStatsPermission(Binder.getCallingUid(), str3);
                z = true;
            }
            if (ArrayUtils.defeatNullable(this.mPackage.getPackagesForUid(applicationInfoAsUser.uid)).length == 1) {
                return queryStatsForUid(str, applicationInfoAsUser.uid, str3);
            }
            int appId = UserHandle.getAppId(applicationInfoAsUser.uid);
            String[] strArr = {str2};
            long[] jArr = new long[1];
            String[] strArr2 = new String[0];
            if ((!applicationInfoAsUser.isSystemApp() || applicationInfoAsUser.isUpdatedSystemApp()) && applicationInfoAsUser.getCodePath() != null) {
                strArr2 = (String[]) ArrayUtils.appendElement(String.class, strArr2, applicationInfoAsUser.getCodePath());
            }
            String[] strArr3 = strArr2;
            final PackageStats packageStats = new PackageStats("StorageStatsService");
            try {
                this.mInstaller.getAppSize(str, strArr, i, 0, appId, jArr, strArr3, packageStats);
                if (str == StorageManager.UUID_PRIVATE_INTERNAL) {
                    final UserHandle of = UserHandle.of(i);
                    forEachStorageStatsAugmenter("queryStatsForPackage", new Consumer() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((StorageStatsManagerLocal.StorageStatsAugmenter) obj).augmentStatsForPackageForUser(packageStats, str2, of, z);
                        }
                    });
                }
                return translate(packageStats);
            } catch (Installer.InstallerException e) {
                throw new ParcelableException(new IOException(e.getMessage()));
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new ParcelableException(e2);
        }
    }

    public final StorageStats queryStatsForUid(String str, final int i, String str2) {
        final PackageStats packageStats;
        int userId = UserHandle.getUserId(i);
        int appId = UserHandle.getAppId(i);
        if (userId != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        boolean z = true;
        if (Binder.getCallingUid() != i) {
            enforceStatsPermission(Binder.getCallingUid(), str2);
        } else if (checkStatsPermission(Binder.getCallingUid(), str2, false) != null) {
            z = false;
        }
        final boolean z2 = z;
        String[] defeatNullable = ArrayUtils.defeatNullable(this.mPackage.getPackagesForUid(i));
        long[] jArr = new long[defeatNullable.length];
        String[] strArr = new String[0];
        PackageStats packageStats2 = new PackageStats("StorageStatsService");
        for (int i2 = 0; i2 < defeatNullable.length; i2++) {
            try {
                ApplicationInfo applicationInfoAsUser = this.mPackage.getApplicationInfoAsUser(defeatNullable[i2], 8192, userId);
                if (!applicationInfoAsUser.isSystemApp() || applicationInfoAsUser.isUpdatedSystemApp()) {
                    if (applicationInfoAsUser.getCodePath() != null) {
                        strArr = (String[]) ArrayUtils.appendElement(String.class, strArr, applicationInfoAsUser.getCodePath());
                    }
                    String[] strArr2 = strArr;
                    if (android.app.usage.Flags.getAppBytesByDataTypeApi()) {
                        try {
                            computeAppStatsByDataTypes(packageStats2, applicationInfoAsUser.sourceDir, defeatNullable[i2]);
                        } catch (NullPointerException e) {
                            Slog.e("StorageStatsService", defeatNullable[i2] + " sourceDir is null", e);
                        }
                    }
                    strArr = strArr2;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                throw new ParcelableException(e2);
            }
        }
        try {
            this.mInstaller.getAppSize(str, defeatNullable, userId, SystemProperties.getBoolean("fw.disable_quota", false) ? 0 : 4096, appId, jArr, strArr, packageStats2);
            if (SystemProperties.getBoolean("fw.verify_storage", false)) {
                PackageStats packageStats3 = new PackageStats("StorageStatsService");
                this.mInstaller.getAppSize(str, defeatNullable, userId, 0, appId, jArr, strArr, packageStats3);
                packageStats = packageStats2;
                checkEquals("UID " + i, packageStats3, packageStats);
            } else {
                packageStats = packageStats2;
            }
            if (str == StorageManager.UUID_PRIVATE_INTERNAL) {
                forEachStorageStatsAugmenter("queryStatsForUid", new Consumer() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((StorageStatsManagerLocal.StorageStatsAugmenter) obj).augmentStatsForUid(packageStats, i, z2);
                    }
                });
            }
            return translate(packageStats);
        } catch (Installer.InstallerException e3) {
            throw new ParcelableException(new IOException(e3.getMessage()));
        }
    }

    public final StorageStats queryStatsForUser(String str, int i, String str2) {
        if (i != UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "StorageStatsService");
        }
        enforceStatsPermission(Binder.getCallingUid(), str2);
        int[] appIds = getAppIds(i);
        final PackageStats packageStats = new PackageStats("StorageStatsService");
        try {
            this.mInstaller.getUserSize(str, i, SystemProperties.getBoolean("fw.disable_quota", false) ? 0 : 4096, appIds, packageStats);
            if (SystemProperties.getBoolean("fw.verify_storage", false)) {
                PackageStats packageStats2 = new PackageStats("StorageStatsService");
                this.mInstaller.getUserSize(str, i, 0, appIds, packageStats2);
                checkEquals("User " + i, packageStats2, packageStats);
            }
            if (str == StorageManager.UUID_PRIVATE_INTERNAL) {
                final UserHandle of = UserHandle.of(i);
                forEachStorageStatsAugmenter("queryStatsForUser", new Consumer() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((StorageStatsManagerLocal.StorageStatsAugmenter) obj).augmentStatsForUser(packageStats, of);
                    }
                });
            }
            return translate(packageStats);
        } catch (Installer.InstallerException e) {
            throw new ParcelableException(new IOException(e.getMessage()));
        }
    }

    public final void updateConfig() {
        synchronized (this.mLock) {
            this.mStorageThresholdPercentHigh = DeviceConfig.getInt("storage_native_boot", "storage_threshold_percent_high", 20);
        }
    }
}
