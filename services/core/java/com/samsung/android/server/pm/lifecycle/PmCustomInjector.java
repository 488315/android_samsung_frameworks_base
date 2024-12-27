package com.samsung.android.server.pm.lifecycle;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.secutil.Slog;

import com.android.server.pm.AppCategoryHintHelper;
import com.android.server.pm.AppCategoryHintHelper$$ExternalSyntheticLambda5;
import com.android.server.pm.AsecInstallHelper;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda44;
import com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda9;
import com.android.server.pm.PackageManagerServiceInjector;

import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.pm.appcategory.AppCategoryFilter;
import com.samsung.android.server.pm.appcategory.AppCategoryListParserWithScpm;
import com.samsung.android.server.pm.google.ChinaGmsToggleUtils;
import com.samsung.android.server.pm.install.ChnPrePackageInstaller;
import com.samsung.android.server.pm.install.OmcInstallHelper;
import com.samsung.android.server.pm.install.PrePackageInstaller;
import com.samsung.android.server.pm.install.SkippingApks;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import com.samsung.android.server.pm.rescueparty.PackageManagerBackupController;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

public final class PmCustomInjector {
    public final Singleton mAppCategoryFilterProducer;
    public final Singleton mAsecInstallHelperProducer;
    public final Singleton mChinaGmsToggleUtilsProducer;
    public final PackageManagerServiceInjector mInjector;
    public final Singleton mMaintenanceModeManagerProducer;
    public final Singleton mMultiUserInstallPolicyProducer;
    public final Singleton mOmcInstallHelperProducer;
    public final Singleton mPackageManagerBackupControllerProducer;
    public final PackageManagerService mPmService;
    public final Singleton mPrePackageInstaller;
    public final Singleton mSkippingApksProducer;
    public final Singleton mUnknownSourceAppManagerProducer;
    public final Singleton mUserManagerHandlerProducer;

    public interface Producer {
        Object produce(
                PackageManagerService packageManagerService,
                PackageManagerServiceInjector packageManagerServiceInjector);
    }

    public final class Singleton {
        public volatile Object mInstance = null;
        public final Producer mProducer;

        public Singleton(Producer producer) {
            this.mProducer = producer;
        }

        public final Object get(
                PackageManagerService packageManagerService,
                PackageManagerServiceInjector packageManagerServiceInjector) {
            if (this.mInstance == null) {
                this.mInstance =
                        this.mProducer.produce(
                                packageManagerService, packageManagerServiceInjector);
            }
            return this.mInstance;
        }
    }

    public PmCustomInjector() {
        this.mInjector = null;
        this.mPmService = null;
        this.mSkippingApksProducer = null;
        this.mMultiUserInstallPolicyProducer = null;
        this.mOmcInstallHelperProducer = null;
        this.mChinaGmsToggleUtilsProducer = null;
        this.mPackageManagerBackupControllerProducer = null;
        this.mPrePackageInstaller = null;
        this.mUnknownSourceAppManagerProducer = null;
        this.mUserManagerHandlerProducer = null;
        this.mMaintenanceModeManagerProducer = null;
        this.mAppCategoryFilterProducer = null;
        this.mAsecInstallHelperProducer = null;
    }

    public PmCustomInjector(
            PackageManagerServiceInjector packageManagerServiceInjector,
            PackageManagerService packageManagerService,
            PackageManagerService$$ExternalSyntheticLambda9
                    packageManagerService$$ExternalSyntheticLambda9,
            PackageManagerService$$ExternalSyntheticLambda44
                    packageManagerService$$ExternalSyntheticLambda44) {
        this.mPmService = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        final int i = 0;
        this.mSkippingApksProducer =
                new Singleton(
                        new Producer() { // from class:
                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                switch (i) {
                                    case 0:
                                        SkippingApks skippingApks = new SkippingApks();
                                        skippingApks.mSkippingApkList = new ArrayList();
                                        return skippingApks;
                                    case 1:
                                        Context context = packageManagerServiceInjector2.mContext;
                                        OmcInstallHelper omcInstallHelper = new OmcInstallHelper();
                                        omcInstallHelper.mContext = context;
                                        return omcInstallHelper;
                                    case 2:
                                        return new ChinaGmsToggleUtils(
                                                packageManagerServiceInjector2.mContext);
                                    case 3:
                                        return new PackageManagerBackupController(
                                                packageManagerServiceInjector2.mLock,
                                                packageManagerServiceInjector2.mContext);
                                    case 4:
                                        HandlerThread handlerThread =
                                                new HandlerThread("UserManagerHandler");
                                        handlerThread.start();
                                        return handlerThread.getThreadHandler();
                                    default:
                                        AppCategoryFilter appCategoryFilter =
                                                new AppCategoryFilter();
                                        appCategoryFilter.mLock = new Object();
                                        appCategoryFilter.mEnabled = false;
                                        appCategoryFilter.mParser =
                                                new AppCategoryListParserWithScpm();
                                        appCategoryFilter.mAppCategoryCallback =
                                                new PackageFeatureCallback() { // from class:
                                                    // com.samsung.android.server.pm.appcategory.AppCategoryFilter.1
                                                    public AnonymousClass1() {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void onPackageFeatureDataChanged(
                                                            PackageFeatureData
                                                                    packageFeatureData) {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void
                                                            onUnformattedPackageFeatureFileChanged(
                                                                    String str, Function function) {
                                                        boolean z;
                                                        FileDescriptor fileDescriptor =
                                                                (FileDescriptor)
                                                                        function.apply(str);
                                                        if (fileDescriptor == null) {
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        synchronized (
                                                                AppCategoryFilter.this.mLock) {
                                                            try {
                                                                if (AppCategoryFilter.this.mParser
                                                                        instanceof
                                                                        AppCategoryListParserWithScpm) {
                                                                    AppCategoryListParserWithScpm
                                                                            updateParserIfNeeded =
                                                                                    AppCategoryListParserWithScpm
                                                                                            .updateParserIfNeeded(
                                                                                                    fileDescriptor);
                                                                    if (updateParserIfNeeded
                                                                            != null) {
                                                                        AppCategoryFilter
                                                                                appCategoryFilter2 =
                                                                                        AppCategoryFilter
                                                                                                .this;
                                                                        arrayList =
                                                                                AppCategoryFilter
                                                                                        .m1231$$Nest$mmakeChangedAppList(
                                                                                                appCategoryFilter2,
                                                                                                (AppCategoryListParserWithScpm)
                                                                                                        appCategoryFilter2
                                                                                                                .mParser,
                                                                                                updateParserIfNeeded);
                                                                        AppCategoryFilter.this
                                                                                        .mParser =
                                                                                updateParserIfNeeded;
                                                                        z = true;
                                                                    } else {
                                                                        z = false;
                                                                    }
                                                                    if (z) {
                                                                        AppCategoryFilter.this
                                                                                .getClass();
                                                                        AppCategoryHintHelper
                                                                                appCategoryHintHelper =
                                                                                        PackageManagerService
                                                                                                .sAppCategoryHintHelper;
                                                                        if (!appCategoryHintHelper
                                                                                .mInit.get()) {
                                                                            Slog.d(
                                                                                    "AppCategoryHintHelper",
                                                                                    "AppCategoryHintHelper"
                                                                                        + " is not"
                                                                                        + " initialized,"
                                                                                        + " updateScpmAppCategory"
                                                                                        + " is not"
                                                                                        + " required.");
                                                                        } else {
                                                                            PackageManagerService
                                                                                    .invalidatePackageInfoCache();
                                                                            arrayList.forEach(
                                                                                    new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                                                                            appCategoryHintHelper,
                                                                                            1));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                };
                                        appCategoryFilter.mEnabled = false;
                                        return appCategoryFilter;
                                }
                            }
                        });
        this.mMultiUserInstallPolicyProducer =
                new Singleton(packageManagerService$$ExternalSyntheticLambda9);
        final int i2 = 1;
        this.mOmcInstallHelperProducer =
                new Singleton(
                        new Producer() { // from class:
                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                switch (i2) {
                                    case 0:
                                        SkippingApks skippingApks = new SkippingApks();
                                        skippingApks.mSkippingApkList = new ArrayList();
                                        return skippingApks;
                                    case 1:
                                        Context context = packageManagerServiceInjector2.mContext;
                                        OmcInstallHelper omcInstallHelper = new OmcInstallHelper();
                                        omcInstallHelper.mContext = context;
                                        return omcInstallHelper;
                                    case 2:
                                        return new ChinaGmsToggleUtils(
                                                packageManagerServiceInjector2.mContext);
                                    case 3:
                                        return new PackageManagerBackupController(
                                                packageManagerServiceInjector2.mLock,
                                                packageManagerServiceInjector2.mContext);
                                    case 4:
                                        HandlerThread handlerThread =
                                                new HandlerThread("UserManagerHandler");
                                        handlerThread.start();
                                        return handlerThread.getThreadHandler();
                                    default:
                                        AppCategoryFilter appCategoryFilter =
                                                new AppCategoryFilter();
                                        appCategoryFilter.mLock = new Object();
                                        appCategoryFilter.mEnabled = false;
                                        appCategoryFilter.mParser =
                                                new AppCategoryListParserWithScpm();
                                        appCategoryFilter.mAppCategoryCallback =
                                                new PackageFeatureCallback() { // from class:
                                                    // com.samsung.android.server.pm.appcategory.AppCategoryFilter.1
                                                    public AnonymousClass1() {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void onPackageFeatureDataChanged(
                                                            PackageFeatureData
                                                                    packageFeatureData) {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void
                                                            onUnformattedPackageFeatureFileChanged(
                                                                    String str, Function function) {
                                                        boolean z;
                                                        FileDescriptor fileDescriptor =
                                                                (FileDescriptor)
                                                                        function.apply(str);
                                                        if (fileDescriptor == null) {
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        synchronized (
                                                                AppCategoryFilter.this.mLock) {
                                                            try {
                                                                if (AppCategoryFilter.this.mParser
                                                                        instanceof
                                                                        AppCategoryListParserWithScpm) {
                                                                    AppCategoryListParserWithScpm
                                                                            updateParserIfNeeded =
                                                                                    AppCategoryListParserWithScpm
                                                                                            .updateParserIfNeeded(
                                                                                                    fileDescriptor);
                                                                    if (updateParserIfNeeded
                                                                            != null) {
                                                                        AppCategoryFilter
                                                                                appCategoryFilter2 =
                                                                                        AppCategoryFilter
                                                                                                .this;
                                                                        arrayList =
                                                                                AppCategoryFilter
                                                                                        .m1231$$Nest$mmakeChangedAppList(
                                                                                                appCategoryFilter2,
                                                                                                (AppCategoryListParserWithScpm)
                                                                                                        appCategoryFilter2
                                                                                                                .mParser,
                                                                                                updateParserIfNeeded);
                                                                        AppCategoryFilter.this
                                                                                        .mParser =
                                                                                updateParserIfNeeded;
                                                                        z = true;
                                                                    } else {
                                                                        z = false;
                                                                    }
                                                                    if (z) {
                                                                        AppCategoryFilter.this
                                                                                .getClass();
                                                                        AppCategoryHintHelper
                                                                                appCategoryHintHelper =
                                                                                        PackageManagerService
                                                                                                .sAppCategoryHintHelper;
                                                                        if (!appCategoryHintHelper
                                                                                .mInit.get()) {
                                                                            Slog.d(
                                                                                    "AppCategoryHintHelper",
                                                                                    "AppCategoryHintHelper"
                                                                                        + " is not"
                                                                                        + " initialized,"
                                                                                        + " updateScpmAppCategory"
                                                                                        + " is not"
                                                                                        + " required.");
                                                                        } else {
                                                                            PackageManagerService
                                                                                    .invalidatePackageInfoCache();
                                                                            arrayList.forEach(
                                                                                    new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                                                                            appCategoryHintHelper,
                                                                                            1));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                };
                                        appCategoryFilter.mEnabled = false;
                                        return appCategoryFilter;
                                }
                            }
                        });
        final int i3 = 2;
        this.mChinaGmsToggleUtilsProducer =
                new Singleton(
                        new Producer() { // from class:
                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                switch (i3) {
                                    case 0:
                                        SkippingApks skippingApks = new SkippingApks();
                                        skippingApks.mSkippingApkList = new ArrayList();
                                        return skippingApks;
                                    case 1:
                                        Context context = packageManagerServiceInjector2.mContext;
                                        OmcInstallHelper omcInstallHelper = new OmcInstallHelper();
                                        omcInstallHelper.mContext = context;
                                        return omcInstallHelper;
                                    case 2:
                                        return new ChinaGmsToggleUtils(
                                                packageManagerServiceInjector2.mContext);
                                    case 3:
                                        return new PackageManagerBackupController(
                                                packageManagerServiceInjector2.mLock,
                                                packageManagerServiceInjector2.mContext);
                                    case 4:
                                        HandlerThread handlerThread =
                                                new HandlerThread("UserManagerHandler");
                                        handlerThread.start();
                                        return handlerThread.getThreadHandler();
                                    default:
                                        AppCategoryFilter appCategoryFilter =
                                                new AppCategoryFilter();
                                        appCategoryFilter.mLock = new Object();
                                        appCategoryFilter.mEnabled = false;
                                        appCategoryFilter.mParser =
                                                new AppCategoryListParserWithScpm();
                                        appCategoryFilter.mAppCategoryCallback =
                                                new PackageFeatureCallback() { // from class:
                                                    // com.samsung.android.server.pm.appcategory.AppCategoryFilter.1
                                                    public AnonymousClass1() {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void onPackageFeatureDataChanged(
                                                            PackageFeatureData
                                                                    packageFeatureData) {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void
                                                            onUnformattedPackageFeatureFileChanged(
                                                                    String str, Function function) {
                                                        boolean z;
                                                        FileDescriptor fileDescriptor =
                                                                (FileDescriptor)
                                                                        function.apply(str);
                                                        if (fileDescriptor == null) {
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        synchronized (
                                                                AppCategoryFilter.this.mLock) {
                                                            try {
                                                                if (AppCategoryFilter.this.mParser
                                                                        instanceof
                                                                        AppCategoryListParserWithScpm) {
                                                                    AppCategoryListParserWithScpm
                                                                            updateParserIfNeeded =
                                                                                    AppCategoryListParserWithScpm
                                                                                            .updateParserIfNeeded(
                                                                                                    fileDescriptor);
                                                                    if (updateParserIfNeeded
                                                                            != null) {
                                                                        AppCategoryFilter
                                                                                appCategoryFilter2 =
                                                                                        AppCategoryFilter
                                                                                                .this;
                                                                        arrayList =
                                                                                AppCategoryFilter
                                                                                        .m1231$$Nest$mmakeChangedAppList(
                                                                                                appCategoryFilter2,
                                                                                                (AppCategoryListParserWithScpm)
                                                                                                        appCategoryFilter2
                                                                                                                .mParser,
                                                                                                updateParserIfNeeded);
                                                                        AppCategoryFilter.this
                                                                                        .mParser =
                                                                                updateParserIfNeeded;
                                                                        z = true;
                                                                    } else {
                                                                        z = false;
                                                                    }
                                                                    if (z) {
                                                                        AppCategoryFilter.this
                                                                                .getClass();
                                                                        AppCategoryHintHelper
                                                                                appCategoryHintHelper =
                                                                                        PackageManagerService
                                                                                                .sAppCategoryHintHelper;
                                                                        if (!appCategoryHintHelper
                                                                                .mInit.get()) {
                                                                            Slog.d(
                                                                                    "AppCategoryHintHelper",
                                                                                    "AppCategoryHintHelper"
                                                                                        + " is not"
                                                                                        + " initialized,"
                                                                                        + " updateScpmAppCategory"
                                                                                        + " is not"
                                                                                        + " required.");
                                                                        } else {
                                                                            PackageManagerService
                                                                                    .invalidatePackageInfoCache();
                                                                            arrayList.forEach(
                                                                                    new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                                                                            appCategoryHintHelper,
                                                                                            1));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                };
                                        appCategoryFilter.mEnabled = false;
                                        return appCategoryFilter;
                                }
                            }
                        });
        final int i4 = 3;
        this.mPackageManagerBackupControllerProducer =
                new Singleton(
                        new Producer() { // from class:
                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                switch (i4) {
                                    case 0:
                                        SkippingApks skippingApks = new SkippingApks();
                                        skippingApks.mSkippingApkList = new ArrayList();
                                        return skippingApks;
                                    case 1:
                                        Context context = packageManagerServiceInjector2.mContext;
                                        OmcInstallHelper omcInstallHelper = new OmcInstallHelper();
                                        omcInstallHelper.mContext = context;
                                        return omcInstallHelper;
                                    case 2:
                                        return new ChinaGmsToggleUtils(
                                                packageManagerServiceInjector2.mContext);
                                    case 3:
                                        return new PackageManagerBackupController(
                                                packageManagerServiceInjector2.mLock,
                                                packageManagerServiceInjector2.mContext);
                                    case 4:
                                        HandlerThread handlerThread =
                                                new HandlerThread("UserManagerHandler");
                                        handlerThread.start();
                                        return handlerThread.getThreadHandler();
                                    default:
                                        AppCategoryFilter appCategoryFilter =
                                                new AppCategoryFilter();
                                        appCategoryFilter.mLock = new Object();
                                        appCategoryFilter.mEnabled = false;
                                        appCategoryFilter.mParser =
                                                new AppCategoryListParserWithScpm();
                                        appCategoryFilter.mAppCategoryCallback =
                                                new PackageFeatureCallback() { // from class:
                                                    // com.samsung.android.server.pm.appcategory.AppCategoryFilter.1
                                                    public AnonymousClass1() {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void onPackageFeatureDataChanged(
                                                            PackageFeatureData
                                                                    packageFeatureData) {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void
                                                            onUnformattedPackageFeatureFileChanged(
                                                                    String str, Function function) {
                                                        boolean z;
                                                        FileDescriptor fileDescriptor =
                                                                (FileDescriptor)
                                                                        function.apply(str);
                                                        if (fileDescriptor == null) {
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        synchronized (
                                                                AppCategoryFilter.this.mLock) {
                                                            try {
                                                                if (AppCategoryFilter.this.mParser
                                                                        instanceof
                                                                        AppCategoryListParserWithScpm) {
                                                                    AppCategoryListParserWithScpm
                                                                            updateParserIfNeeded =
                                                                                    AppCategoryListParserWithScpm
                                                                                            .updateParserIfNeeded(
                                                                                                    fileDescriptor);
                                                                    if (updateParserIfNeeded
                                                                            != null) {
                                                                        AppCategoryFilter
                                                                                appCategoryFilter2 =
                                                                                        AppCategoryFilter
                                                                                                .this;
                                                                        arrayList =
                                                                                AppCategoryFilter
                                                                                        .m1231$$Nest$mmakeChangedAppList(
                                                                                                appCategoryFilter2,
                                                                                                (AppCategoryListParserWithScpm)
                                                                                                        appCategoryFilter2
                                                                                                                .mParser,
                                                                                                updateParserIfNeeded);
                                                                        AppCategoryFilter.this
                                                                                        .mParser =
                                                                                updateParserIfNeeded;
                                                                        z = true;
                                                                    } else {
                                                                        z = false;
                                                                    }
                                                                    if (z) {
                                                                        AppCategoryFilter.this
                                                                                .getClass();
                                                                        AppCategoryHintHelper
                                                                                appCategoryHintHelper =
                                                                                        PackageManagerService
                                                                                                .sAppCategoryHintHelper;
                                                                        if (!appCategoryHintHelper
                                                                                .mInit.get()) {
                                                                            Slog.d(
                                                                                    "AppCategoryHintHelper",
                                                                                    "AppCategoryHintHelper"
                                                                                        + " is not"
                                                                                        + " initialized,"
                                                                                        + " updateScpmAppCategory"
                                                                                        + " is not"
                                                                                        + " required.");
                                                                        } else {
                                                                            PackageManagerService
                                                                                    .invalidatePackageInfoCache();
                                                                            arrayList.forEach(
                                                                                    new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                                                                            appCategoryHintHelper,
                                                                                            1));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                };
                                        appCategoryFilter.mEnabled = false;
                                        return appCategoryFilter;
                                }
                            }
                        });
        this.mUnknownSourceAppManagerProducer =
                new Singleton(packageManagerService$$ExternalSyntheticLambda44);
        if (Arrays.asList("CHZ", "CHN", "CHM", "CHU", "CTC", "CHC")
                .contains(SystemProperties.get("ro.csc.sales_code"))) {
            final int i5 = 1;
            this.mPrePackageInstaller =
                    new Singleton(
                            new Producer(
                                    this) { // from class:
                                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda1
                                public final /* synthetic */ PmCustomInjector f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                                public final Object produce(
                                        PackageManagerService packageManagerService2,
                                        PackageManagerServiceInjector
                                                packageManagerServiceInjector2) {
                                    int i6 = i5;
                                    PmCustomInjector pmCustomInjector = this.f$0;
                                    pmCustomInjector.getClass();
                                    switch (i6) {
                                        case 0:
                                            return new AsecInstallHelper(
                                                    pmCustomInjector.mPmService);
                                        case 1:
                                            ChnPrePackageInstaller chnPrePackageInstaller =
                                                    new ChnPrePackageInstaller(
                                                            packageManagerServiceInjector2.mContext,
                                                            pmCustomInjector.mInjector
                                                                    .getPackageInstallerService(),
                                                            packageManagerService2.mFirstBoot,
                                                            packageManagerService2
                                                                    .isDeviceUpgrading());
                                            new HashSet();
                                            chnPrePackageInstaller.mLoaded = false;
                                            chnPrePackageInstaller.mCscAddedAPKList =
                                                    new ArrayList();
                                            chnPrePackageInstaller.mCscUpdateAPKList =
                                                    new ArrayList();
                                            chnPrePackageInstaller.mCscInstallOnceAPKList =
                                                    new ArrayList();
                                            chnPrePackageInstaller.mCscUninstallPKGList =
                                                    new ArrayList();
                                            return chnPrePackageInstaller;
                                        case 2:
                                            return new PrePackageInstaller(
                                                    packageManagerServiceInjector2.mContext,
                                                    pmCustomInjector.mInjector
                                                            .getPackageInstallerService(),
                                                    packageManagerService2.mFirstBoot,
                                                    packageManagerService2.isDeviceUpgrading());
                                        default:
                                            return new MaintenanceModeManager(
                                                    packageManagerServiceInjector2.mContext,
                                                    pmCustomInjector.getUserManagerHandler(),
                                                    packageManagerServiceInjector2
                                                            .getUserManagerService());
                                    }
                                }
                            });
        } else {
            final int i6 = 2;
            this.mPrePackageInstaller =
                    new Singleton(
                            new Producer(
                                    this) { // from class:
                                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda1
                                public final /* synthetic */ PmCustomInjector f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                                public final Object produce(
                                        PackageManagerService packageManagerService2,
                                        PackageManagerServiceInjector
                                                packageManagerServiceInjector2) {
                                    int i62 = i6;
                                    PmCustomInjector pmCustomInjector = this.f$0;
                                    pmCustomInjector.getClass();
                                    switch (i62) {
                                        case 0:
                                            return new AsecInstallHelper(
                                                    pmCustomInjector.mPmService);
                                        case 1:
                                            ChnPrePackageInstaller chnPrePackageInstaller =
                                                    new ChnPrePackageInstaller(
                                                            packageManagerServiceInjector2.mContext,
                                                            pmCustomInjector.mInjector
                                                                    .getPackageInstallerService(),
                                                            packageManagerService2.mFirstBoot,
                                                            packageManagerService2
                                                                    .isDeviceUpgrading());
                                            new HashSet();
                                            chnPrePackageInstaller.mLoaded = false;
                                            chnPrePackageInstaller.mCscAddedAPKList =
                                                    new ArrayList();
                                            chnPrePackageInstaller.mCscUpdateAPKList =
                                                    new ArrayList();
                                            chnPrePackageInstaller.mCscInstallOnceAPKList =
                                                    new ArrayList();
                                            chnPrePackageInstaller.mCscUninstallPKGList =
                                                    new ArrayList();
                                            return chnPrePackageInstaller;
                                        case 2:
                                            return new PrePackageInstaller(
                                                    packageManagerServiceInjector2.mContext,
                                                    pmCustomInjector.mInjector
                                                            .getPackageInstallerService(),
                                                    packageManagerService2.mFirstBoot,
                                                    packageManagerService2.isDeviceUpgrading());
                                        default:
                                            return new MaintenanceModeManager(
                                                    packageManagerServiceInjector2.mContext,
                                                    pmCustomInjector.getUserManagerHandler(),
                                                    packageManagerServiceInjector2
                                                            .getUserManagerService());
                                    }
                                }
                            });
        }
        final int i7 = 4;
        this.mUserManagerHandlerProducer =
                new Singleton(
                        new Producer() { // from class:
                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                switch (i7) {
                                    case 0:
                                        SkippingApks skippingApks = new SkippingApks();
                                        skippingApks.mSkippingApkList = new ArrayList();
                                        return skippingApks;
                                    case 1:
                                        Context context = packageManagerServiceInjector2.mContext;
                                        OmcInstallHelper omcInstallHelper = new OmcInstallHelper();
                                        omcInstallHelper.mContext = context;
                                        return omcInstallHelper;
                                    case 2:
                                        return new ChinaGmsToggleUtils(
                                                packageManagerServiceInjector2.mContext);
                                    case 3:
                                        return new PackageManagerBackupController(
                                                packageManagerServiceInjector2.mLock,
                                                packageManagerServiceInjector2.mContext);
                                    case 4:
                                        HandlerThread handlerThread =
                                                new HandlerThread("UserManagerHandler");
                                        handlerThread.start();
                                        return handlerThread.getThreadHandler();
                                    default:
                                        AppCategoryFilter appCategoryFilter =
                                                new AppCategoryFilter();
                                        appCategoryFilter.mLock = new Object();
                                        appCategoryFilter.mEnabled = false;
                                        appCategoryFilter.mParser =
                                                new AppCategoryListParserWithScpm();
                                        appCategoryFilter.mAppCategoryCallback =
                                                new PackageFeatureCallback() { // from class:
                                                    // com.samsung.android.server.pm.appcategory.AppCategoryFilter.1
                                                    public AnonymousClass1() {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void onPackageFeatureDataChanged(
                                                            PackageFeatureData
                                                                    packageFeatureData) {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void
                                                            onUnformattedPackageFeatureFileChanged(
                                                                    String str, Function function) {
                                                        boolean z;
                                                        FileDescriptor fileDescriptor =
                                                                (FileDescriptor)
                                                                        function.apply(str);
                                                        if (fileDescriptor == null) {
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        synchronized (
                                                                AppCategoryFilter.this.mLock) {
                                                            try {
                                                                if (AppCategoryFilter.this.mParser
                                                                        instanceof
                                                                        AppCategoryListParserWithScpm) {
                                                                    AppCategoryListParserWithScpm
                                                                            updateParserIfNeeded =
                                                                                    AppCategoryListParserWithScpm
                                                                                            .updateParserIfNeeded(
                                                                                                    fileDescriptor);
                                                                    if (updateParserIfNeeded
                                                                            != null) {
                                                                        AppCategoryFilter
                                                                                appCategoryFilter2 =
                                                                                        AppCategoryFilter
                                                                                                .this;
                                                                        arrayList =
                                                                                AppCategoryFilter
                                                                                        .m1231$$Nest$mmakeChangedAppList(
                                                                                                appCategoryFilter2,
                                                                                                (AppCategoryListParserWithScpm)
                                                                                                        appCategoryFilter2
                                                                                                                .mParser,
                                                                                                updateParserIfNeeded);
                                                                        AppCategoryFilter.this
                                                                                        .mParser =
                                                                                updateParserIfNeeded;
                                                                        z = true;
                                                                    } else {
                                                                        z = false;
                                                                    }
                                                                    if (z) {
                                                                        AppCategoryFilter.this
                                                                                .getClass();
                                                                        AppCategoryHintHelper
                                                                                appCategoryHintHelper =
                                                                                        PackageManagerService
                                                                                                .sAppCategoryHintHelper;
                                                                        if (!appCategoryHintHelper
                                                                                .mInit.get()) {
                                                                            Slog.d(
                                                                                    "AppCategoryHintHelper",
                                                                                    "AppCategoryHintHelper"
                                                                                        + " is not"
                                                                                        + " initialized,"
                                                                                        + " updateScpmAppCategory"
                                                                                        + " is not"
                                                                                        + " required.");
                                                                        } else {
                                                                            PackageManagerService
                                                                                    .invalidatePackageInfoCache();
                                                                            arrayList.forEach(
                                                                                    new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                                                                            appCategoryHintHelper,
                                                                                            1));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                };
                                        appCategoryFilter.mEnabled = false;
                                        return appCategoryFilter;
                                }
                            }
                        });
        final int i8 = 3;
        this.mMaintenanceModeManagerProducer =
                new Singleton(
                        new Producer(
                                this) { // from class:
                                        // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda1
                            public final /* synthetic */ PmCustomInjector f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                int i62 = i8;
                                PmCustomInjector pmCustomInjector = this.f$0;
                                pmCustomInjector.getClass();
                                switch (i62) {
                                    case 0:
                                        return new AsecInstallHelper(pmCustomInjector.mPmService);
                                    case 1:
                                        ChnPrePackageInstaller chnPrePackageInstaller =
                                                new ChnPrePackageInstaller(
                                                        packageManagerServiceInjector2.mContext,
                                                        pmCustomInjector.mInjector
                                                                .getPackageInstallerService(),
                                                        packageManagerService2.mFirstBoot,
                                                        packageManagerService2.isDeviceUpgrading());
                                        new HashSet();
                                        chnPrePackageInstaller.mLoaded = false;
                                        chnPrePackageInstaller.mCscAddedAPKList = new ArrayList();
                                        chnPrePackageInstaller.mCscUpdateAPKList = new ArrayList();
                                        chnPrePackageInstaller.mCscInstallOnceAPKList =
                                                new ArrayList();
                                        chnPrePackageInstaller.mCscUninstallPKGList =
                                                new ArrayList();
                                        return chnPrePackageInstaller;
                                    case 2:
                                        return new PrePackageInstaller(
                                                packageManagerServiceInjector2.mContext,
                                                pmCustomInjector.mInjector
                                                        .getPackageInstallerService(),
                                                packageManagerService2.mFirstBoot,
                                                packageManagerService2.isDeviceUpgrading());
                                    default:
                                        return new MaintenanceModeManager(
                                                packageManagerServiceInjector2.mContext,
                                                pmCustomInjector.getUserManagerHandler(),
                                                packageManagerServiceInjector2
                                                        .getUserManagerService());
                                }
                            }
                        });
        final int i9 = 5;
        this.mAppCategoryFilterProducer =
                new Singleton(
                        new Producer() { // from class:
                            // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                switch (i9) {
                                    case 0:
                                        SkippingApks skippingApks = new SkippingApks();
                                        skippingApks.mSkippingApkList = new ArrayList();
                                        return skippingApks;
                                    case 1:
                                        Context context = packageManagerServiceInjector2.mContext;
                                        OmcInstallHelper omcInstallHelper = new OmcInstallHelper();
                                        omcInstallHelper.mContext = context;
                                        return omcInstallHelper;
                                    case 2:
                                        return new ChinaGmsToggleUtils(
                                                packageManagerServiceInjector2.mContext);
                                    case 3:
                                        return new PackageManagerBackupController(
                                                packageManagerServiceInjector2.mLock,
                                                packageManagerServiceInjector2.mContext);
                                    case 4:
                                        HandlerThread handlerThread =
                                                new HandlerThread("UserManagerHandler");
                                        handlerThread.start();
                                        return handlerThread.getThreadHandler();
                                    default:
                                        AppCategoryFilter appCategoryFilter =
                                                new AppCategoryFilter();
                                        appCategoryFilter.mLock = new Object();
                                        appCategoryFilter.mEnabled = false;
                                        appCategoryFilter.mParser =
                                                new AppCategoryListParserWithScpm();
                                        appCategoryFilter.mAppCategoryCallback =
                                                new PackageFeatureCallback() { // from class:
                                                    // com.samsung.android.server.pm.appcategory.AppCategoryFilter.1
                                                    public AnonymousClass1() {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void onPackageFeatureDataChanged(
                                                            PackageFeatureData
                                                                    packageFeatureData) {}

                                                    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
                                                    public final void
                                                            onUnformattedPackageFeatureFileChanged(
                                                                    String str, Function function) {
                                                        boolean z;
                                                        FileDescriptor fileDescriptor =
                                                                (FileDescriptor)
                                                                        function.apply(str);
                                                        if (fileDescriptor == null) {
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        synchronized (
                                                                AppCategoryFilter.this.mLock) {
                                                            try {
                                                                if (AppCategoryFilter.this.mParser
                                                                        instanceof
                                                                        AppCategoryListParserWithScpm) {
                                                                    AppCategoryListParserWithScpm
                                                                            updateParserIfNeeded =
                                                                                    AppCategoryListParserWithScpm
                                                                                            .updateParserIfNeeded(
                                                                                                    fileDescriptor);
                                                                    if (updateParserIfNeeded
                                                                            != null) {
                                                                        AppCategoryFilter
                                                                                appCategoryFilter2 =
                                                                                        AppCategoryFilter
                                                                                                .this;
                                                                        arrayList =
                                                                                AppCategoryFilter
                                                                                        .m1231$$Nest$mmakeChangedAppList(
                                                                                                appCategoryFilter2,
                                                                                                (AppCategoryListParserWithScpm)
                                                                                                        appCategoryFilter2
                                                                                                                .mParser,
                                                                                                updateParserIfNeeded);
                                                                        AppCategoryFilter.this
                                                                                        .mParser =
                                                                                updateParserIfNeeded;
                                                                        z = true;
                                                                    } else {
                                                                        z = false;
                                                                    }
                                                                    if (z) {
                                                                        AppCategoryFilter.this
                                                                                .getClass();
                                                                        AppCategoryHintHelper
                                                                                appCategoryHintHelper =
                                                                                        PackageManagerService
                                                                                                .sAppCategoryHintHelper;
                                                                        if (!appCategoryHintHelper
                                                                                .mInit.get()) {
                                                                            Slog.d(
                                                                                    "AppCategoryHintHelper",
                                                                                    "AppCategoryHintHelper"
                                                                                        + " is not"
                                                                                        + " initialized,"
                                                                                        + " updateScpmAppCategory"
                                                                                        + " is not"
                                                                                        + " required.");
                                                                        } else {
                                                                            PackageManagerService
                                                                                    .invalidatePackageInfoCache();
                                                                            arrayList.forEach(
                                                                                    new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                                                                            appCategoryHintHelper,
                                                                                            1));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                };
                                        appCategoryFilter.mEnabled = false;
                                        return appCategoryFilter;
                                }
                            }
                        });
        final int i10 = 0;
        this.mAsecInstallHelperProducer =
                new Singleton(
                        new Producer(
                                this) { // from class:
                                        // com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda1
                            public final /* synthetic */ PmCustomInjector f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                            public final Object produce(
                                    PackageManagerService packageManagerService2,
                                    PackageManagerServiceInjector packageManagerServiceInjector2) {
                                int i62 = i10;
                                PmCustomInjector pmCustomInjector = this.f$0;
                                pmCustomInjector.getClass();
                                switch (i62) {
                                    case 0:
                                        return new AsecInstallHelper(pmCustomInjector.mPmService);
                                    case 1:
                                        ChnPrePackageInstaller chnPrePackageInstaller =
                                                new ChnPrePackageInstaller(
                                                        packageManagerServiceInjector2.mContext,
                                                        pmCustomInjector.mInjector
                                                                .getPackageInstallerService(),
                                                        packageManagerService2.mFirstBoot,
                                                        packageManagerService2.isDeviceUpgrading());
                                        new HashSet();
                                        chnPrePackageInstaller.mLoaded = false;
                                        chnPrePackageInstaller.mCscAddedAPKList = new ArrayList();
                                        chnPrePackageInstaller.mCscUpdateAPKList = new ArrayList();
                                        chnPrePackageInstaller.mCscInstallOnceAPKList =
                                                new ArrayList();
                                        chnPrePackageInstaller.mCscUninstallPKGList =
                                                new ArrayList();
                                        return chnPrePackageInstaller;
                                    case 2:
                                        return new PrePackageInstaller(
                                                packageManagerServiceInjector2.mContext,
                                                pmCustomInjector.mInjector
                                                        .getPackageInstallerService(),
                                                packageManagerService2.mFirstBoot,
                                                packageManagerService2.isDeviceUpgrading());
                                    default:
                                        return new MaintenanceModeManager(
                                                packageManagerServiceInjector2.mContext,
                                                pmCustomInjector.getUserManagerHandler(),
                                                packageManagerServiceInjector2
                                                        .getUserManagerService());
                                }
                            }
                        });
    }

    public final AsecInstallHelper getAsecInstallHelper() {
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        return (AsecInstallHelper)
                this.mAsecInstallHelperProducer.get(this.mPmService, packageManagerServiceInjector);
    }

    public final MaintenanceModeManager getMaintenanceModeManager() {
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        return (MaintenanceModeManager)
                this.mMaintenanceModeManagerProducer.get(
                        this.mPmService, packageManagerServiceInjector);
    }

    public final UnknownSourceAppManager getUnknownSourceAppManager() {
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        return (UnknownSourceAppManager)
                this.mUnknownSourceAppManagerProducer.get(
                        this.mPmService, packageManagerServiceInjector);
    }

    public final Handler getUserManagerHandler() {
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        return (Handler)
                this.mUserManagerHandlerProducer.get(
                        this.mPmService, packageManagerServiceInjector);
    }
}
