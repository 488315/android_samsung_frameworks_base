package com.samsung.android.server.pm.lifecycle;

import android.app.AppGlobals;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ApexEnvironment;
import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceInjector;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.PackageSetting;
import com.android.server.utils.WatchedArrayMap;
import com.samsung.android.core.pm.allowlist.RestrictedReceiverFilter;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmSharedPreferences;
import com.samsung.android.server.pm.appcategory.AppCategoryFilter;
import com.samsung.android.server.pm.google.ChinaGmsToggleUtils;
import com.samsung.android.server.pm.install.MultiUserInstallPolicy;
import com.samsung.android.server.pm.install.OmcInstallHelper;
import com.samsung.android.server.pm.install.SkippingApks;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import com.samsung.android.server.pm.rescueparty.SystemFileBackupManager;
import com.samsung.android.server.pm.scan.CacheCorruptionChecker;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PmLifecycleImpl {
    public AppCategoryFilter mACFilter;
    public final MaintenanceModeManager.ATCommandReceiver mATCommandReceiver;
    public ChinaGmsToggleUtils mChinaGmsToggleUtils;
    public final PmCustomInjector mCustomInjector;
    public boolean mEnabled;
    public final PackageManagerServiceInjector mInjector;
    public boolean mIsUserTrial;
    public OmcInstallHelper mOmcInstallHelper;
    public final PackageManagerService mPm;
    public final RestrictedReceiverFilter mRRFilter;
    public SkippingApks mSkippingApks;

    public PmLifecycleImpl() {
        this.mRRFilter = RestrictedReceiverFilter.getInstance();
        this.mEnabled = false;
        this.mATCommandReceiver = new MaintenanceModeManager.ATCommandReceiver();
        this.mIsUserTrial = false;
        this.mPm = null;
        this.mInjector = null;
        this.mCustomInjector = null;
    }

    public PmLifecycleImpl(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector, PmCustomInjector pmCustomInjector) {
        this.mRRFilter = RestrictedReceiverFilter.getInstance();
        this.mEnabled = false;
        this.mATCommandReceiver = new MaintenanceModeManager.ATCommandReceiver();
        this.mIsUserTrial = false;
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mCustomInjector = pmCustomInjector;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(24:0|1|(2:3|2e)|13|(3:17|(5:21|22|23|25|26)|29)|31|(1:(2:42|(1:44))(2:36|(1:38)(2:39|(1:41))))|45|(2:49|(3:51|(4:54|(3:56|57|58)(1:60)|59|52)|61))|62|(3:64|(2:67|65)|68)|69|(2:73|(12:77|78|(1:82)|83|84|85|86|(1:88)|89|28f|96|97))|106|78|(2:80|82)|83|84|85|86|(0)|89|28f|(1:(0))) */
    /* JADX WARN: Removed duplicated region for block: B:88:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0290 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onInitPreparing() {
        /*
            Method dump skipped, instructions count: 691
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.pm.lifecycle.PmLifecycleImpl.onInitPreparing():void");
    }

    public final void onInitStarting() {
        if (this.mEnabled) {
            for (int i : this.mInjector.getUserManagerService().getUserIds()) {
                try {
                    Class<?> cls = Class.forName("com.android.role.persistence.RolesPersistenceImpl");
                    Class[] clsArr = new Class[0];
                    Constructor<?> declaredConstructor = cls.getDeclaredConstructor(null);
                    declaredConstructor.setAccessible(true);
                    cls.getMethod("readForUser", UserHandle.class).invoke(declaredConstructor.newInstance(null), UserHandle.of(i));
                } catch (InvocationTargetException unused) {
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(3, "!@Failed to read roles.xml. Initiate the files.");
                    new File(ApexEnvironment.getApexEnvironment("com.android.permission").getDeviceProtectedDataDirForUser(UserHandle.of(i)), "runtime-permissions.xml").delete();
                    new File(ApexEnvironment.getApexEnvironment("com.android.permission").getDeviceProtectedDataDirForUser(UserHandle.of(i)), "roles.xml").delete();
                } catch (Exception e) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(3, "!@Failed to use RolesPersistence class.");
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.server.pm.lifecycle.PmLifecycleImpl$$ExternalSyntheticLambda1] */
    public final void onInstalldStarting() {
        this.mRRFilter.enableAndConfigure(true);
        final ?? r0 = new Supplier() { // from class: com.samsung.android.server.pm.lifecycle.PmLifecycleImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(PmLifecycleImpl.this.mInjector.mInstaller.mInstalld != null);
            }
        };
        long currentTimeMillis = System.currentTimeMillis();
        Future submit = Executors.newSingleThreadExecutor().submit(new Callable() { // from class: com.samsung.android.server.pm.install.PdpUtils$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Supplier supplier = r0;
                while (!((Boolean) supplier.get()).booleanValue()) {
                    Slog.w("PackageManager", "installd not connected. Trying again");
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return Boolean.TRUE;
            }
        });
        try {
            submit.get(390L, TimeUnit.SECONDS);
            Slog.w("PackageManager", "Installd connected. Took " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
        } catch (TimeoutException unused) {
            PackageManagerServiceUtils.logCriticalInfo(5, "Timeout. Installd connection failed.");
            submit.cancel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onPackageInstalled(final String str, boolean z, int[] iArr) {
        if (z) {
            if (PMRune.PM_ENABLE_GMS && Arrays.stream(ChinaGmsToggleUtils.GMS_PACKAGES).anyMatch(new Predicate() { // from class: com.samsung.android.server.pm.google.ChinaGmsToggleUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((String) obj).equals(str);
                }
            })) {
                ChinaGmsToggleUtils chinaGmsToggleUtils = this.mChinaGmsToggleUtils;
                int i = Settings.Global.getInt(chinaGmsToggleUtils.mContext.getContentResolver(), "google_core_control", 0) == 1 ? 1 : 2;
                for (int i2 : iArr) {
                    try {
                        chinaGmsToggleUtils.setApplicationEnabledSettingAsUser(str, i, i2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("PackageManager", "Fail to enable " + str);
                    }
                }
            }
            SystemFileBackupManager systemFileBackupManager = SystemFileBackupManager.getInstance();
            Context context = this.mInjector.mContext;
            systemFileBackupManager.getClass();
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
            if (jobScheduler.getPendingJob(9543859) == null) {
                JobInfo.Builder builder = new JobInfo.Builder(9543859, SystemFileBackupManager.sFileBackupServiceName);
                builder.setRequiresCharging(true).setRequiresDeviceIdle(true);
                jobScheduler.schedule(builder.build());
                Slog.d("SystemFileBackupManager", "Scheduled onetime backup job");
            } else {
                Slog.d("SystemFileBackupManager", "Already scheduled");
            }
            PackageManagerTracedLock packageManagerTracedLock = this.mInjector.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    PmCustomInjector pmCustomInjector = this.mCustomInjector;
                    MultiUserInstallPolicy multiUserInstallPolicy = (MultiUserInstallPolicy) pmCustomInjector.mMultiUserInstallPolicyProducer.get(pmCustomInjector.mPmService, pmCustomInjector.mInjector);
                    PackageSetting packageSetting = (PackageSetting) ((WatchedArrayMap) multiUserInstallPolicy.mSettingsDelegator.mGetPackagesLocked.get()).mStorage.get(str);
                    if (packageSetting != null) {
                        ArrayList arrayList = new ArrayList();
                        multiUserInstallPolicy.applyInstallPolicyPackageInternalLPw(packageSetting, arrayList, multiUserInstallPolicy.getSubUserIdsAndGuestUserId(arrayList));
                    }
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            int packageCategory = this.mACFilter.getPackageCategory(str);
            if (packageCategory >= 0) {
                try {
                    AppGlobals.getPackageManager().setApplicationCategoryHint(str, packageCategory, "android");
                } catch (RemoteException unused) {
                }
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.samsung.android.server.pm.install.PackageBlockListPolicy.1.<init>(android.os.Handler, android.content.ContentResolver):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:290)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:193)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:64)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    /* JADX WARN: Type inference failed for: r3v27, types: [com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda1] */
    public final void onSystemReady() {
        /*
            Method dump skipped, instructions count: 814
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.pm.lifecycle.PmLifecycleImpl.onSystemReady():void");
    }

    public final void onSystemScanning(File file) {
        boolean z;
        if (this.mEnabled) {
            if (SystemProperties.getBoolean("persist.sys.clear_package_cache_needed", false)) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(3, "Clear pkg caches due to BR allowlist updated");
                if (file != null) {
                    FileUtils.deleteContents(file);
                }
                SystemProperties.set("persist.sys.clear_package_cache_needed", Boolean.toString(false));
            }
            OmcInstallHelper omcInstallHelper = this.mOmcInstallHelper;
            if (file == null) {
                omcInstallHelper.getClass();
            } else if (omcInstallHelper.mNeedsOmcInit || omcInstallHelper.mNeedsTssInit) {
                PackageManagerServiceUtils.logCriticalInfo(5, "Clear package cache by omcboot or tssboot");
                FileUtils.deleteContents(file);
            }
            Context context = this.mInjector.mContext;
            synchronized (PmSharedPreferences.class) {
                z = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).getBoolean("key_scan_started", false);
            }
            if (z && file != null) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(3, "Delete package caches due to corruption");
                FileUtils.deleteContents(file);
            }
            CacheCorruptionChecker.setPackageScanStarted(this.mInjector.mContext, true);
        }
    }
}
