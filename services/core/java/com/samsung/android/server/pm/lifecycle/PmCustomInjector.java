package com.samsung.android.server.pm.lifecycle;

import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceInjector;
import com.samsung.android.server.pm.google.ChinaGmsToggleUtils;
import com.samsung.android.server.pm.install.ChnPrePackageInstaller;
import com.samsung.android.server.pm.install.MultiUserInstallPolicy;
import com.samsung.android.server.pm.install.OmcInstallHelper;
import com.samsung.android.server.pm.install.PackageInstallTimeLogger;
import com.samsung.android.server.pm.install.PrePackageInstaller;
import com.samsung.android.server.pm.install.PrePackageInstallerBase;
import com.samsung.android.server.pm.install.SkippingApks;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.server.pm.rescueparty.PackageManagerBackupController;

/* loaded from: classes2.dex */
public class PmCustomInjector {
    public final Singleton mChinaGmsToggleUtilsProducer;
    public final PackageManagerServiceInjector mInjector;
    public final Singleton mInstallLogger;
    public final Singleton mMultiUserInstallPolicyProducer;
    public final Singleton mOmcInstallHelperProducer;
    public final Singleton mPackageManagerBackupControllerProducer;
    public final PackageManagerService mPmService;
    public final Singleton mPrePackageInstaller;
    public final Singleton mSkippingApksProducer;
    public final Singleton mUnknownSourceAppManagerProducer;

    public interface Producer {
        Object produce(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService);
    }

    public PmCustomInjector() {
        this.mInjector = null;
        this.mPmService = null;
        this.mSkippingApksProducer = null;
        this.mMultiUserInstallPolicyProducer = null;
        this.mOmcInstallHelperProducer = null;
        this.mChinaGmsToggleUtilsProducer = null;
        this.mPackageManagerBackupControllerProducer = null;
        this.mInstallLogger = null;
        this.mPrePackageInstaller = null;
        this.mUnknownSourceAppManagerProducer = null;
    }

    public PmCustomInjector(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService, Producer producer, Producer producer2) {
        this.mPmService = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mSkippingApksProducer = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda0
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                SkippingApks lambda$new$0;
                lambda$new$0 = PmCustomInjector.lambda$new$0(packageManagerServiceInjector2, packageManagerService2);
                return lambda$new$0;
            }
        });
        this.mMultiUserInstallPolicyProducer = new Singleton(producer);
        this.mOmcInstallHelperProducer = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda1
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                OmcInstallHelper lambda$new$1;
                lambda$new$1 = PmCustomInjector.lambda$new$1(packageManagerServiceInjector2, packageManagerService2);
                return lambda$new$1;
            }
        });
        this.mChinaGmsToggleUtilsProducer = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda2
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                ChinaGmsToggleUtils lambda$new$2;
                lambda$new$2 = PmCustomInjector.lambda$new$2(packageManagerServiceInjector2, packageManagerService2);
                return lambda$new$2;
            }
        });
        this.mPackageManagerBackupControllerProducer = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda3
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                PackageManagerBackupController lambda$new$3;
                lambda$new$3 = PmCustomInjector.lambda$new$3(packageManagerServiceInjector2, packageManagerService2);
                return lambda$new$3;
            }
        });
        this.mInstallLogger = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda4
            @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
            public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                PackageInstallTimeLogger lambda$new$4;
                lambda$new$4 = PmCustomInjector.lambda$new$4(packageManagerServiceInjector2, packageManagerService2);
                return lambda$new$4;
            }
        });
        this.mUnknownSourceAppManagerProducer = new Singleton(producer2);
        if (PrePackageInstallerBase.isChinaModel()) {
            this.mPrePackageInstaller = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda5
                @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                    PrePackageInstallerBase lambda$new$5;
                    lambda$new$5 = PmCustomInjector.this.lambda$new$5(packageManagerServiceInjector2, packageManagerService2);
                    return lambda$new$5;
                }
            });
        } else {
            this.mPrePackageInstaller = new Singleton(new Producer() { // from class: com.samsung.android.server.pm.lifecycle.PmCustomInjector$$ExternalSyntheticLambda6
                @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
                public final Object produce(PackageManagerServiceInjector packageManagerServiceInjector2, PackageManagerService packageManagerService2) {
                    PrePackageInstallerBase lambda$new$6;
                    lambda$new$6 = PmCustomInjector.this.lambda$new$6(packageManagerServiceInjector2, packageManagerService2);
                    return lambda$new$6;
                }
            });
        }
    }

    public static /* synthetic */ SkippingApks lambda$new$0(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new SkippingApks();
    }

    public static /* synthetic */ OmcInstallHelper lambda$new$1(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new OmcInstallHelper(packageManagerServiceInjector.getContext());
    }

    public static /* synthetic */ ChinaGmsToggleUtils lambda$new$2(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new ChinaGmsToggleUtils(packageManagerServiceInjector.getContext());
    }

    public static /* synthetic */ PackageManagerBackupController lambda$new$3(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageManagerBackupController(packageManagerServiceInjector.getLock(), packageManagerServiceInjector.getContext());
    }

    public static /* synthetic */ PackageInstallTimeLogger lambda$new$4(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PackageInstallTimeLogger(packageManagerServiceInjector.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ PrePackageInstallerBase lambda$new$5(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new ChnPrePackageInstaller(packageManagerServiceInjector.getContext(), this.mInjector.getPackageInstallerService(), packageManagerService.isFirstBoot(), packageManagerService.isDeviceUpgrading());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ PrePackageInstallerBase lambda$new$6(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
        return new PrePackageInstaller(packageManagerServiceInjector.getContext(), this.mInjector.getPackageInstallerService(), packageManagerService.isFirstBoot(), packageManagerService.isDeviceUpgrading());
    }

    public PrePackageInstallerBase getPrePackageInstaller() {
        return (PrePackageInstallerBase) this.mPrePackageInstaller.get(this.mInjector, this.mPmService);
    }

    public SkippingApks getSkippingApks() {
        return (SkippingApks) this.mSkippingApksProducer.get(this.mInjector, this.mPmService);
    }

    public OmcInstallHelper getOmcInstallHelper() {
        return (OmcInstallHelper) this.mOmcInstallHelperProducer.get(this.mInjector, this.mPmService);
    }

    public ChinaGmsToggleUtils getChinaGmsToggleUtils() {
        return (ChinaGmsToggleUtils) this.mChinaGmsToggleUtilsProducer.get(this.mInjector, this.mPmService);
    }

    public MultiUserInstallPolicy getMumUserInstallPolicy() {
        return (MultiUserInstallPolicy) this.mMultiUserInstallPolicyProducer.get(this.mInjector, this.mPmService);
    }

    public PackageManagerBackupController getPackageManagerBackupController() {
        return (PackageManagerBackupController) this.mPackageManagerBackupControllerProducer.get(this.mInjector, this.mPmService);
    }

    public UnknownSourceAppManager getUnknownSourceAppManager() {
        return (UnknownSourceAppManager) this.mUnknownSourceAppManagerProducer.get(this.mInjector, this.mPmService);
    }

    public class Singleton {
        public volatile Object mInstance = null;
        public final Producer mProducer;

        public Singleton(Producer producer) {
            this.mProducer = producer;
        }

        public Object get(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerService packageManagerService) {
            if (this.mInstance == null) {
                this.mInstance = this.mProducer.produce(packageManagerServiceInjector, packageManagerService);
            }
            return this.mInstance;
        }
    }
}
