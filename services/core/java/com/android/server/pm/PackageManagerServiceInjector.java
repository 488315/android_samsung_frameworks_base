package com.android.server.pm;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerExecutor;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.server.SystemConfig;
import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PackageManagerServiceInjector {
    public final PackageAbiHelper mAbiHelper;
    public final Singleton mApexManagerProducer;
    public final Singleton mAppsFilterProducer;
    public final Singleton mArtManagerServiceProducer;
    public final Executor mBackgroundExecutor;
    public final Handler mBackgroundHandler;
    public final Singleton mComponentResolverProducer;
    public final Context mContext;
    public final Singleton mCrossProfileIntentFilterHelperProducer;
    public final Singleton mDefaultAppProviderProducer;
    public final Singleton mDexManagerProducer;
    public final Singleton mDisplayMetricsProducer;
    public final Singleton mDomainVerificationManagerInternalProducer;
    public final Singleton mDynamicCodeLoggerProducer;
    public final ServiceProducer mGetLocalServiceProducer;
    public final ServiceProducer mGetSystemServiceProducer;
    public final Singleton mHandlerProducer;
    public final Singleton mIBackupManager;
    public final Singleton mIncrementalManagerProducer;
    public final PackageManagerTracedLock mInstallLock;
    public final Installer mInstaller;
    public final VcnManagementService$$ExternalSyntheticLambda10 mInstantAppResolverConnectionProducer;
    public final Singleton mLegacyPermissionManagerInternalProducer;
    public final PackageManagerTracedLock mLock;
    public final Singleton mModuleInfoProviderProducer;
    public final Singleton mPackageDexOptimizerProducer;
    public final Singleton mPackageInstallerServiceProducer;
    public PackageManagerService mPackageManager;
    public final Singleton mPackageMonitorCallbackHelper;
    public final Singleton mPermissionManagerServiceProducer;
    public final Singleton mPlatformCompatProducer;
    public final Producer mPreparingPackageParserProducer;
    public final Producer mScanningCachingPackageParserProducer;
    public final Producer mScanningPackageParserProducer;
    public final Singleton mSettingsProducer;
    public final Singleton mSharedLibrariesProducer;
    public final Singleton mSystemConfigProducer;
    public final List mSystemPartitions;
    public final PackageManagerService.DefaultSystemWrapper mSystemWrapper;
    public final Singleton mUpdateOwnershipHelperProducer;
    public final Singleton mUserManagerProducer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Producer {
        Object produce(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ServiceProducer {
        Object produce(Class cls);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Singleton {
        public volatile Object mInstance = null;
        public final Producer mProducer;

        public Singleton(Producer producer) {
            this.mProducer = producer;
        }

        public final Object get(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector) {
            if (this.mInstance == null) {
                this.mInstance = this.mProducer.produce(packageManagerService, packageManagerServiceInjector);
            }
            return this.mInstance;
        }
    }

    public PackageManagerServiceInjector(Context context, PackageManagerTracedLock packageManagerTracedLock, Installer installer, PackageManagerTracedLock packageManagerTracedLock2, PackageAbiHelperImpl packageAbiHelperImpl, Handler handler, List list, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda9, PackageManagerService$$ExternalSyntheticLambda13 packageManagerService$$ExternalSyntheticLambda13, PackageManagerService$$ExternalSyntheticLambda30 packageManagerService$$ExternalSyntheticLambda30, PackageManagerService$$ExternalSyntheticLambda33 packageManagerService$$ExternalSyntheticLambda33, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda92, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda93, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda94, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda95, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda96, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda97, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda98, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda99, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda910, PackageManagerService$$ExternalSyntheticLambda13 packageManagerService$$ExternalSyntheticLambda132, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda911, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda912, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda913, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda914, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda915, VcnManagementService$$ExternalSyntheticLambda10 vcnManagementService$$ExternalSyntheticLambda10, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda916, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda917, PackageManagerService$$ExternalSyntheticLambda22 packageManagerService$$ExternalSyntheticLambda22, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda918, PackageManagerService.DefaultSystemWrapper defaultSystemWrapper, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda919, PackageManagerService$$ExternalSyntheticLambda13 packageManagerService$$ExternalSyntheticLambda133, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda920, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda921, PackageManagerService$$ExternalSyntheticLambda13 packageManagerService$$ExternalSyntheticLambda134, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda922, PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda923) {
        this.mContext = context;
        this.mLock = packageManagerTracedLock;
        this.mInstaller = installer;
        this.mAbiHelper = packageAbiHelperImpl;
        this.mInstallLock = packageManagerTracedLock2;
        this.mBackgroundHandler = handler;
        this.mBackgroundExecutor = new HandlerExecutor(handler);
        this.mSystemPartitions = list;
        this.mComponentResolverProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda9);
        this.mPermissionManagerServiceProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda13);
        this.mUserManagerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda30);
        this.mSettingsProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda33);
        this.mAppsFilterProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda92);
        this.mPlatformCompatProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda93);
        this.mSystemConfigProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda94);
        this.mPackageDexOptimizerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda95);
        this.mDexManagerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda96);
        this.mDynamicCodeLoggerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda97);
        this.mArtManagerServiceProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda98);
        this.mApexManagerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda99);
        this.mIncrementalManagerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda910);
        this.mDefaultAppProviderProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda132);
        this.mDisplayMetricsProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda911);
        this.mScanningCachingPackageParserProducer = packageManagerService$$ExternalSyntheticLambda912;
        this.mScanningPackageParserProducer = packageManagerService$$ExternalSyntheticLambda913;
        this.mPreparingPackageParserProducer = packageManagerService$$ExternalSyntheticLambda914;
        this.mPackageInstallerServiceProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda915);
        this.mInstantAppResolverConnectionProducer = vcnManagementService$$ExternalSyntheticLambda10;
        this.mModuleInfoProviderProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda916);
        this.mLegacyPermissionManagerInternalProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda917);
        this.mSystemWrapper = defaultSystemWrapper;
        this.mGetLocalServiceProducer = packageManagerService$$ExternalSyntheticLambda919;
        this.mGetSystemServiceProducer = packageManagerService$$ExternalSyntheticLambda133;
        this.mDomainVerificationManagerInternalProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda22);
        this.mHandlerProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda918);
        this.mIBackupManager = new Singleton(packageManagerService$$ExternalSyntheticLambda920);
        this.mSharedLibrariesProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda921);
        this.mCrossProfileIntentFilterHelperProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda134);
        this.mUpdateOwnershipHelperProducer = new Singleton(packageManagerService$$ExternalSyntheticLambda922);
        this.mPackageMonitorCallbackHelper = new Singleton(packageManagerService$$ExternalSyntheticLambda923);
    }

    public final PlatformCompat getCompatibility() {
        return (PlatformCompat) this.mPlatformCompatProducer.get(this.mPackageManager, this);
    }

    public final Handler getHandler() {
        return (Handler) this.mHandlerProducer.get(this.mPackageManager, this);
    }

    public final InstantAppResolverConnection getInstantAppResolverConnection(ComponentName componentName) {
        getClass();
        return new InstantAppResolverConnection(this.mContext, componentName);
    }

    public final LegacyPermissionManagerService.Internal getLegacyPermissionManagerInternal() {
        return (LegacyPermissionManagerService.Internal) this.mLegacyPermissionManagerInternalProducer.get(this.mPackageManager, this);
    }

    public final ModuleInfoProvider getModuleInfoProvider() {
        return (ModuleInfoProvider) this.mModuleInfoProviderProducer.get(this.mPackageManager, this);
    }

    public final PackageInstallerService getPackageInstallerService() {
        return (PackageInstallerService) this.mPackageInstallerServiceProducer.get(this.mPackageManager, this);
    }

    public final PackageParser2 getPreparingPackageParser() {
        return (PackageParser2) this.mPreparingPackageParserProducer.produce(this.mPackageManager, this);
    }

    public final PackageParser2 getScanningCachingPackageParser() {
        return (PackageParser2) this.mScanningCachingPackageParserProducer.produce(this.mPackageManager, this);
    }

    public final SharedLibrariesImpl getSharedLibrariesImpl() {
        return (SharedLibrariesImpl) this.mSharedLibrariesProducer.get(this.mPackageManager, this);
    }

    public final SystemConfig getSystemConfig() {
        return (SystemConfig) this.mSystemConfigProducer.get(this.mPackageManager, this);
    }

    public final List getSystemPartitions() {
        return this.mSystemPartitions;
    }

    public final UserManagerService getUserManagerService() {
        return (UserManagerService) this.mUserManagerProducer.get(this.mPackageManager, this);
    }
}
