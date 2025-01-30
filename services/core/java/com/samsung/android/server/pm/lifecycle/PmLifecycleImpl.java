package com.samsung.android.server.pm.lifecycle;

import android.os.FileUtils;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.Watchdog;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceInjector;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmFileValidator;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.allowlist.RestrictedReceiverFilter;
import com.samsung.android.server.pm.google.ChinaGmsToggleUtils;
import com.samsung.android.server.pm.install.OmcInstallHelper;
import com.samsung.android.server.pm.install.PackageBlockListPolicy;
import com.samsung.android.server.pm.install.PdpUtils;
import com.samsung.android.server.pm.install.SkippingApks;
import com.samsung.android.server.pm.permission.OmcPermissionPolicy;
import com.samsung.android.server.pm.rescueparty.SystemFileBackupManager;
import com.samsung.android.server.pm.role.RoleLogger;
import com.samsung.android.server.pm.scan.CacheCorruptionChecker;
import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class PmLifecycleImpl {
    public ChinaGmsToggleUtils mChinaGmsToggleUtils;
    public final PmCustomInjector mCustomInjector;
    public boolean mEnabled;
    public final PackageManagerServiceInjector mInjector;
    public OmcInstallHelper mOmcInstallHelper;
    public final PackageManagerService mPm;
    public final RestrictedReceiverFilter mRRFilter;
    public SkippingApks mSkippingApks;

    public void onDataScanning() {
    }

    public void onInitCompleted() {
    }

    public PmLifecycleImpl() {
        this.mRRFilter = RestrictedReceiverFilter.getInstance();
        this.mEnabled = false;
        this.mPm = null;
        this.mInjector = null;
        this.mCustomInjector = null;
    }

    public PmLifecycleImpl(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector, PmCustomInjector pmCustomInjector) {
        this.mRRFilter = RestrictedReceiverFilter.getInstance();
        this.mEnabled = false;
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mCustomInjector = pmCustomInjector;
    }

    public void onInstalldStarting() {
        this.mRRFilter.enableAndConfigure(true);
        PdpUtils.waitUntilInstalldConnected(new Supplier() { // from class: com.samsung.android.server.pm.lifecycle.PmLifecycleImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$onInstalldStarting$0;
                lambda$onInstalldStarting$0 = PmLifecycleImpl.this.lambda$onInstalldStarting$0();
                return lambda$onInstalldStarting$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$onInstalldStarting$0() {
        return Boolean.valueOf(this.mInjector.getInstaller().isInstalldConnected());
    }

    public void onInitPreparing() {
        this.mEnabled = true;
        SystemFileBackupManager.getInstance().registerController(this.mCustomInjector.getPackageManagerBackupController());
        this.mSkippingApks = this.mCustomInjector.getSkippingApks();
        OmcInstallHelper omcInstallHelper = this.mCustomInjector.getOmcInstallHelper();
        this.mOmcInstallHelper = omcInstallHelper;
        omcInstallHelper.waitToReadAIDwhenTssAndNonActivated();
        this.mSkippingApks.initSkippingApkList();
        this.mOmcInstallHelper.setOmcAndTssInit();
        this.mInjector.getInstaller().removeNotTargetedPreloadApksIfNeeded();
        if (PMRune.PM_ENABLE_GMS) {
            this.mChinaGmsToggleUtils = this.mCustomInjector.getChinaGmsToggleUtils();
        }
    }

    public void onInitStarting() {
        if (this.mEnabled) {
            PmFileValidator.validateRoleFile(this.mInjector.getUserManagerService().getUserIds());
        }
    }

    public void onSystemScanning(File file) {
        if (this.mEnabled) {
            if (SystemProperties.getBoolean("persist.sys.clear_package_cache_needed", false)) {
                PmLog.logCriticalInfoAndLogcat("Clear pkg caches due to BR allowlist updated");
                if (file != null) {
                    FileUtils.deleteContents(file);
                }
                SystemProperties.set("persist.sys.clear_package_cache_needed", Boolean.toString(false));
            }
            this.mOmcInstallHelper.deleteContentsIfNeeded(file);
            if (CacheCorruptionChecker.isPackageCacheCorrupted(this.mInjector.getContext())) {
                CacheCorruptionChecker.deletePackageCaches(file);
            }
            CacheCorruptionChecker.setPackageScanStarted(this.mInjector.getContext(), true);
        }
    }

    public void onSystemReady() {
        if (this.mEnabled) {
            synchronized (this.mInjector.getLock()) {
                this.mCustomInjector.getMumUserInstallPolicy().applyInstallPolicyLPw();
            }
            if (PMRune.PM_ENABLE_GMS) {
                this.mChinaGmsToggleUtils.setGmsEnabledSetting(-1);
                this.mChinaGmsToggleUtils.registerContentObserverForGoogleControlCore(this.mInjector.getHandler());
            }
            CacheCorruptionChecker.setPackageScanStarted(this.mInjector.getContext(), false);
            boolean z = this.mPm.isDeviceUpgrading() || this.mPm.isFirstBoot();
            boolean needsOmcOrTssInit = this.mOmcInstallHelper.needsOmcOrTssInit();
            if (z || needsOmcOrTssInit) {
                OmcPermissionPolicy omcPermissionPolicy = new OmcPermissionPolicy(OmcPermissionPolicy.createPmServiceProxy(this.mInjector.getContext()));
                PackageManagerServiceUtils.logCriticalInfo(5, "Set up omc permissions (firstBootOrUpgrade: " + z + ", needsOmcOrTssInit: " + needsOmcOrTssInit + ")");
                omcPermissionPolicy.grantDefaultPermissions(UserManagerService.getInstance().getUserIds(), z);
                this.mOmcInstallHelper.writeTssSettings();
            }
            synchronized (this.mInjector.getLock()) {
                this.mCustomInjector.getUnknownSourceAppManager().initUnknownSourceAppSettingsLPr();
            }
            SystemFileBackupManager.getInstance().onSystemReady(this.mInjector.getContext());
            new RoleLogger().onSystemReady(this.mInjector.getContext());
            PackageBlockListPolicy.registerContentObserverForRdu(this.mInjector.getContext(), this.mInjector.getHandler());
        }
    }

    public void onWaitForAppDataPrepared() {
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            Watchdog.getInstance().pauseWatchingCurrentThread("prepackageinstaller");
            Future runPrePackageInstaller = this.mCustomInjector.getPrePackageInstaller().runPrePackageInstaller();
            if (runPrePackageInstaller != null) {
                ConcurrentUtils.waitForFutureNoInterrupt(runPrePackageInstaller, "wait for pre-installing");
            }
            Watchdog.getInstance().resumeWatchingCurrentThread("prepackageinstaller");
            Slog.i("PrePackageInstaller", "Install took " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
        } catch (Throwable th) {
            Watchdog.getInstance().resumeWatchingCurrentThread("prepackageinstaller");
            throw th;
        }
    }

    public void onDump(Computer computer, final PrintWriter printWriter, String[] strArr) {
        if (this.mEnabled && !hasDumpProto(strArr) && hasDumpAll(strArr)) {
            printWriter.println();
            this.mRRFilter.logViolationsIfNeeded(new Consumer() { // from class: com.samsung.android.server.pm.lifecycle.PmLifecycleImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    printWriter.println((String) obj);
                }
            });
            this.mSkippingApks.dump(printWriter);
            PmLog.dumpDebugInfos(printWriter);
            printWriter.println("Required system packages:");
            for (String str : this.mPm.getRequiredSystemPackages()) {
                printWriter.print("  ");
                printWriter.println(str);
            }
        }
    }

    public void onNewUserCreated(int i) {
        if (this.mEnabled && PMRune.PM_ENABLE_GMS) {
            this.mChinaGmsToggleUtils.setGmsEnabledSetting(i);
        }
    }

    public final boolean hasDumpProto(String[] strArr) {
        String str;
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        int i = 0;
        while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
            i++;
            if ("--proto".equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasDumpAll(String[] strArr) {
        String str;
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        int i = 0;
        while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
            i++;
            if ("-a".equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void onPackageInstalled(String str, PackageStateInternal packageStateInternal, int[] iArr, boolean z) {
        if (z) {
            if (PMRune.PM_ENABLE_GMS && ChinaGmsToggleUtils.isGMSPackage(str)) {
                this.mChinaGmsToggleUtils.setGmsEnabledPackage(str, iArr);
            }
            SystemFileBackupManager.getInstance().scheduleOnetimeBackupJob(this.mInjector.getContext());
            synchronized (this.mInjector.getLock()) {
                this.mCustomInjector.getMumUserInstallPolicy().applyInstallPolicyPackageAsUserLPw(str, -1);
            }
        }
    }
}
