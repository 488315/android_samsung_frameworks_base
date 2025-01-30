package com.android.server.pm;

import android.app.ActivityManagerInternal;
import android.app.backup.IBackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.incremental.IncrementalManager;
import android.util.DisplayMetrics;
import com.android.server.SystemConfig;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.dex.ArtManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DynamicCodeLogger;
import com.android.server.pm.dex.ViewCompiler;
import com.android.server.pm.parsing.PackageParser2;
import com.android.server.pm.permission.LegacyPermissionManagerInternal;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class PackageManagerServiceInjector {
  public final PackageAbiHelper mAbiHelper;
  public final Singleton mApexManagerProducer;
  public final Singleton mAppsFilterProducer;
  public final Singleton mArtManagerServiceProducer;
  public final Singleton mBackgroundDexOptService;
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
  public final Object mInstallLock;
  public final Installer mInstaller;
  public final ProducerWithArgument mInstantAppResolverConnectionProducer;
  public final Singleton mLegacyPermissionManagerInternalProducer;
  public final PackageManagerTracedLock mLock;
  public final Singleton mModuleInfoProviderProducer;
  public final Singleton mPackageDexOptimizerProducer;
  public final Singleton mPackageInstallerServiceProducer;
  public PackageManagerService mPackageManager;
  public final Singleton mPermissionManagerServiceProducer;
  public final Singleton mPlatformCompatProducer;
  public final Producer mPreparingPackageParserProducer;
  public final Producer mScanningCachingPackageParserProducer;
  public final Producer mScanningPackageParserProducer;
  public final Singleton mSettingsProducer;
  public final Singleton mSharedLibrariesProducer;
  public final Singleton mSystemConfigProducer;
  public final List mSystemPartitions;
  public final SystemWrapper mSystemWrapper;
  public final Singleton mUpdateOwnershipHelperProducer;
  public final Singleton mUserManagerProducer;
  public final Singleton mViewCompilerProducer;

  interface Producer {
    Object produce(
        PackageManagerServiceInjector packageManagerServiceInjector,
        PackageManagerService packageManagerService);
  }

  public interface ProducerWithArgument {
    Object produce(
        PackageManagerServiceInjector packageManagerServiceInjector,
        PackageManagerService packageManagerService,
        Object obj);
  }

  public interface ServiceProducer {
    Object produce(Class cls);
  }

  public interface SystemWrapper {
    void disablePackageCaches();

    void enablePackageCaches();
  }

  class Singleton {
    public volatile Object mInstance = null;
    public final Producer mProducer;

    public Singleton(Producer producer) {
      this.mProducer = producer;
    }

    public Object get(
        PackageManagerServiceInjector packageManagerServiceInjector,
        PackageManagerService packageManagerService) {
      if (this.mInstance == null) {
        this.mInstance =
            this.mProducer.produce(packageManagerServiceInjector, packageManagerService);
      }
      return this.mInstance;
    }
  }

  public PackageManagerServiceInjector(
      Context context,
      PackageManagerTracedLock packageManagerTracedLock,
      Installer installer,
      Object obj,
      PackageAbiHelper packageAbiHelper,
      Handler handler,
      List list,
      Producer producer,
      Producer producer2,
      Producer producer3,
      Producer producer4,
      Producer producer5,
      Producer producer6,
      Producer producer7,
      Producer producer8,
      Producer producer9,
      Producer producer10,
      Producer producer11,
      Producer producer12,
      Producer producer13,
      Producer producer14,
      Producer producer15,
      Producer producer16,
      Producer producer17,
      Producer producer18,
      Producer producer19,
      Producer producer20,
      ProducerWithArgument producerWithArgument,
      Producer producer21,
      Producer producer22,
      Producer producer23,
      Producer producer24,
      SystemWrapper systemWrapper,
      ServiceProducer serviceProducer,
      ServiceProducer serviceProducer2,
      Producer producer25,
      Producer producer26,
      Producer producer27,
      Producer producer28,
      Producer producer29) {
    this.mContext = context;
    this.mLock = packageManagerTracedLock;
    this.mInstaller = installer;
    this.mAbiHelper = packageAbiHelper;
    this.mInstallLock = obj;
    this.mBackgroundHandler = handler;
    this.mBackgroundExecutor = new HandlerExecutor(handler);
    this.mSystemPartitions = list;
    this.mComponentResolverProducer = new Singleton(producer);
    this.mPermissionManagerServiceProducer = new Singleton(producer2);
    this.mUserManagerProducer = new Singleton(producer3);
    this.mSettingsProducer = new Singleton(producer4);
    this.mAppsFilterProducer = new Singleton(producer5);
    this.mPlatformCompatProducer = new Singleton(producer6);
    this.mSystemConfigProducer = new Singleton(producer7);
    this.mPackageDexOptimizerProducer = new Singleton(producer8);
    this.mDexManagerProducer = new Singleton(producer9);
    this.mDynamicCodeLoggerProducer = new Singleton(producer10);
    this.mArtManagerServiceProducer = new Singleton(producer11);
    this.mApexManagerProducer = new Singleton(producer12);
    this.mViewCompilerProducer = new Singleton(producer13);
    this.mIncrementalManagerProducer = new Singleton(producer14);
    this.mDefaultAppProviderProducer = new Singleton(producer15);
    this.mDisplayMetricsProducer = new Singleton(producer16);
    this.mScanningCachingPackageParserProducer = producer17;
    this.mScanningPackageParserProducer = producer18;
    this.mPreparingPackageParserProducer = producer19;
    this.mPackageInstallerServiceProducer = new Singleton(producer20);
    this.mInstantAppResolverConnectionProducer = producerWithArgument;
    this.mModuleInfoProviderProducer = new Singleton(producer21);
    this.mLegacyPermissionManagerInternalProducer = new Singleton(producer22);
    this.mSystemWrapper = systemWrapper;
    this.mGetLocalServiceProducer = serviceProducer;
    this.mGetSystemServiceProducer = serviceProducer2;
    this.mDomainVerificationManagerInternalProducer = new Singleton(producer23);
    this.mHandlerProducer = new Singleton(producer24);
    this.mBackgroundDexOptService = new Singleton(producer25);
    this.mIBackupManager = new Singleton(producer26);
    this.mSharedLibrariesProducer = new Singleton(producer27);
    this.mCrossProfileIntentFilterHelperProducer = new Singleton(producer28);
    this.mUpdateOwnershipHelperProducer = new Singleton(producer29);
  }

  public void bootstrap(PackageManagerService packageManagerService) {
    this.mPackageManager = packageManagerService;
  }

  public UserManagerInternal getUserManagerInternal() {
    return getUserManagerService().getInternalForInjectorOnly();
  }

  public PackageAbiHelper getAbiHelper() {
    return this.mAbiHelper;
  }

  public Object getInstallLock() {
    return this.mInstallLock;
  }

  public List getSystemPartitions() {
    return this.mSystemPartitions;
  }

  public UserManagerService getUserManagerService() {
    return (UserManagerService) this.mUserManagerProducer.get(this, this.mPackageManager);
  }

  public PackageManagerTracedLock getLock() {
    return this.mLock;
  }

  public CrossProfileIntentFilterHelper getCrossProfileIntentFilterHelper() {
    return (CrossProfileIntentFilterHelper)
        this.mCrossProfileIntentFilterHelperProducer.get(this, this.mPackageManager);
  }

  public Installer getInstaller() {
    return this.mInstaller;
  }

  public ComponentResolver getComponentResolver() {
    return (ComponentResolver) this.mComponentResolverProducer.get(this, this.mPackageManager);
  }

  public PermissionManagerServiceInternal getPermissionManagerServiceInternal() {
    return (PermissionManagerServiceInternal)
        this.mPermissionManagerServiceProducer.get(this, this.mPackageManager);
  }

  public Context getContext() {
    return this.mContext;
  }

  public Settings getSettings() {
    return (Settings) this.mSettingsProducer.get(this, this.mPackageManager);
  }

  public AppsFilterImpl getAppsFilter() {
    return (AppsFilterImpl) this.mAppsFilterProducer.get(this, this.mPackageManager);
  }

  public PlatformCompat getCompatibility() {
    return (PlatformCompat) this.mPlatformCompatProducer.get(this, this.mPackageManager);
  }

  public SystemConfig getSystemConfig() {
    return (SystemConfig) this.mSystemConfigProducer.get(this, this.mPackageManager);
  }

  public PackageDexOptimizer getPackageDexOptimizer() {
    return (PackageDexOptimizer) this.mPackageDexOptimizerProducer.get(this, this.mPackageManager);
  }

  public DexManager getDexManager() {
    return (DexManager) this.mDexManagerProducer.get(this, this.mPackageManager);
  }

  public DynamicCodeLogger getDynamicCodeLogger() {
    return (DynamicCodeLogger) this.mDynamicCodeLoggerProducer.get(this, this.mPackageManager);
  }

  public ArtManagerService getArtManagerService() {
    return (ArtManagerService) this.mArtManagerServiceProducer.get(this, this.mPackageManager);
  }

  public ApexManager getApexManager() {
    return (ApexManager) this.mApexManagerProducer.get(this, this.mPackageManager);
  }

  public ViewCompiler getViewCompiler() {
    return (ViewCompiler) this.mViewCompilerProducer.get(this, this.mPackageManager);
  }

  public Handler getBackgroundHandler() {
    return this.mBackgroundHandler;
  }

  public Executor getBackgroundExecutor() {
    return this.mBackgroundExecutor;
  }

  public DisplayMetrics getDisplayMetrics() {
    return (DisplayMetrics) this.mDisplayMetricsProducer.get(this, this.mPackageManager);
  }

  public Object getLocalService(Class cls) {
    return this.mGetLocalServiceProducer.produce(cls);
  }

  public Object getSystemService(Class cls) {
    return this.mGetSystemServiceProducer.produce(cls);
  }

  public SystemWrapper getSystemWrapper() {
    return this.mSystemWrapper;
  }

  public IncrementalManager getIncrementalManager() {
    return (IncrementalManager) this.mIncrementalManagerProducer.get(this, this.mPackageManager);
  }

  public DefaultAppProvider getDefaultAppProvider() {
    return (DefaultAppProvider) this.mDefaultAppProviderProducer.get(this, this.mPackageManager);
  }

  public PackageParser2 getScanningCachingPackageParser() {
    return (PackageParser2)
        this.mScanningCachingPackageParserProducer.produce(this, this.mPackageManager);
  }

  public PackageParser2 getScanningPackageParser() {
    return (PackageParser2) this.mScanningPackageParserProducer.produce(this, this.mPackageManager);
  }

  public PackageParser2 getPreparingPackageParser() {
    return (PackageParser2)
        this.mPreparingPackageParserProducer.produce(this, this.mPackageManager);
  }

  public PackageInstallerService getPackageInstallerService() {
    return (PackageInstallerService)
        this.mPackageInstallerServiceProducer.get(this, this.mPackageManager);
  }

  public InstantAppResolverConnection getInstantAppResolverConnection(ComponentName componentName) {
    return (InstantAppResolverConnection)
        this.mInstantAppResolverConnectionProducer.produce(
            this, this.mPackageManager, componentName);
  }

  public ModuleInfoProvider getModuleInfoProvider() {
    return (ModuleInfoProvider) this.mModuleInfoProviderProducer.get(this, this.mPackageManager);
  }

  public LegacyPermissionManagerInternal getLegacyPermissionManagerInternal() {
    return (LegacyPermissionManagerInternal)
        this.mLegacyPermissionManagerInternalProducer.get(this, this.mPackageManager);
  }

  public DomainVerificationManagerInternal getDomainVerificationManagerInternal() {
    return (DomainVerificationManagerInternal)
        this.mDomainVerificationManagerInternalProducer.get(this, this.mPackageManager);
  }

  public Handler getHandler() {
    return (Handler) this.mHandlerProducer.get(this, this.mPackageManager);
  }

  public ActivityManagerInternal getActivityManagerInternal() {
    return (ActivityManagerInternal) getLocalService(ActivityManagerInternal.class);
  }

  public BackgroundDexOptService getBackgroundDexOptService() {
    return (BackgroundDexOptService) this.mBackgroundDexOptService.get(this, this.mPackageManager);
  }

  public IBackupManager getIBackupManager() {
    return (IBackupManager) this.mIBackupManager.get(this, this.mPackageManager);
  }

  public SharedLibrariesImpl getSharedLibrariesImpl() {
    return (SharedLibrariesImpl) this.mSharedLibrariesProducer.get(this, this.mPackageManager);
  }

  public UpdateOwnershipHelper getUpdateOwnershipHelper() {
    return (UpdateOwnershipHelper)
        this.mUpdateOwnershipHelperProducer.get(this, this.mPackageManager);
  }
}
