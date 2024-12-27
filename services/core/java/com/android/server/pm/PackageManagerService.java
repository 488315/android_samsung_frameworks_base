package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.ApplicationPackageManager;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.admin.IDevicePolicyManager;
import android.app.admin.SecurityLog;
import android.app.compat.ChangeIdStateCache;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApkChecksum;
import android.content.pm.ApplicationInfo;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.ChangedPackages;
import android.content.pm.FallbackCategoryProvider;
import android.content.pm.FeatureInfo;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IMemorySaverPackageMoveObserver;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.IncrementalStatesInfo;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstantAppInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackagePartitions;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.TestUtilityService;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.content.pm.UserProperties;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.IArtManager;
import android.content.pm.overlay.OverlayPaths;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.os.incremental.PerUidReadTimeouts;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.os.storage.VolumeInfo;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.ExceptionUtils;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.SparseBooleanArray;
import android.util.Xml;
import com.android.internal.app.IAppOpsService;
import com.android.internal.content.F2fsUtils;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.os.SomeArgs;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.telephony.CarrierAppUtils;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.permission.persistence.RuntimePermissionsPersistence;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.IntentResolver;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.PackageWatchdog;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemServiceManager;
import com.android.server.ThreadPriorityBooster;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.appop.AppOpsService;
import com.android.server.art.DexUseManagerLocal;
import com.android.server.art.model.DexoptParams;
import com.android.server.art.model.OperationProgress;
import com.android.server.clipboard.ClipboardService;
import com.android.server.compat.PlatformCompat;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda0;
import com.android.server.om.OverlayReferenceMapper;
import com.android.server.pm.Installer;
import com.android.server.pm.InstantAppRegistry;
import com.android.server.pm.LauncherAppsService;
import com.android.server.pm.MovePackageHelper;
import com.android.server.pm.MovePackageHelper.AnonymousClass2;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceInjector;
import com.android.server.pm.PackageMonitorCallbackHelper;
import com.android.server.pm.ProcessLoggingHandler;
import com.android.server.pm.Settings;
import com.android.server.pm.SnapshotStatistics;
import com.android.server.pm.StagingManager;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.dex.ArtManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.dex.DynamicCodeLogger;
import com.android.server.pm.dex.PackageDynamicCodeLoading;
import com.android.server.pm.local.PackageManagerLocalImpl;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.android.server.pm.permission.LegacyPermissionSettings;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda10;
import com.android.server.pm.permission.PermissionManagerServiceInternal$PackageInstalledParams;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.SuspendParams;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.pm.utils.RequestThrottle;
import com.android.server.pm.verify.domain.DomainVerificationEnforcer;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxy;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1;
import com.android.server.storage.DeviceStorageMonitorService;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseArray;
import com.android.server.utils.WatchedSparseBooleanArray;
import com.android.server.utils.WatchedSparseIntArray;
import com.android.server.utils.Watcher;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.DefaultAppConfiguration;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;
import com.samsung.android.localeoverlaymanager.OverlayChangeObserver;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.PmServerUtils;
import com.samsung.android.server.pm.appcategory.AppCategoryFilter;
import com.samsung.android.server.pm.install.OmcInstallHelper;
import com.samsung.android.server.pm.install.PmConfigParser;
import com.samsung.android.server.pm.install.PrePackageInstallerBase;
import com.samsung.android.server.pm.install.SkippingApks;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.server.pm.lifecycle.PmCustomInjector;
import com.samsung.android.server.pm.lifecycle.PmLifecycleImpl;
import com.samsung.android.server.pm.lifecycle.PmLifecycleImpl$$ExternalSyntheticLambda2;
import com.samsung.android.server.pm.monetization.MonetizationUtils;
import com.samsung.android.server.pm.rescueparty.PackageManagerBackupController;
import dalvik.system.DexFile;
import dalvik.system.VMRuntime;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import libcore.util.HexEncoding;

public final class PackageManagerService implements PackageSender, TestUtilityService {
    public static final AnonymousClass2 BACKGROUND_HANDLER_CALLBACK;
    public static final boolean DEBUG_COMPRESSION;
    public static final boolean DEBUG_INSTANT;
    public static final long DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD;
    public static final long DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED;
    public static final int[] EMPTY_INT_ARRAY;
    public static final PerUidReadTimeouts[] EMPTY_PER_UID_READ_TIMEOUTS_ARRAY;
    public static final int MIN_INSTALLABLE_TARGET_SDK;
    public static final long PRUNE_UNUSED_SHARED_LIBRARIES_DELAY;
    public static final List SYSTEM_PARTITIONS;
    public static final AppCategoryHintHelper sAppCategoryHintHelper;
    public static PersonaManagerService sPersonaManager;
    public static final AtomicReference sSnapshot;
    public static final AtomicInteger sSnapshotPendingVersion;
    public final String mAmbientContextDetectionPackage;
    public ApplicationInfo mAndroidApplication;
    public final ApexManager mApexManager;
    public final AppDataHelper mAppDataHelper;
    public final File mAppInstallDir;
    public final String mAppPredictionServicePackage;
    public final AppsFilterImpl mAppsFilter;
    public final ArtManagerService mArtManagerService;
    public final AutoDisableHandler mAutoDisableHandler;
    public final ArrayMap mAvailableFeatures;
    public final Handler mBackgroundHandler;
    public final BroadcastHelper mBroadcastHelper;
    public final File mCacheDir;
    public final ChangedPackagesTracker mChangedPackagesTracker;
    public final CompilerStats mCompilerStats;
    public final ComponentResolver mComponentResolver;
    public final String mConfiguratorPackage;
    public final Context mContext;
    public final PmCustomInjector mCustomInjector;
    public final ComponentName mCustomResolverComponentName;
    public final int mDefParseFlags;
    public final DefaultAppProvider mDefaultAppProvider;
    public final String mDefaultTextClassifierPackage;
    public final DeletePackageHelper mDeletePackageHelper;
    public IDevicePolicyManager mDevicePolicyManager;
    public final DexManager mDexManager;
    public final DexOptHelper mDexOptHelper;
    public final ArraySet mDirtyUsers;
    public final DistractingPackageHelper mDistractingPackageHelper;
    public final DomainVerificationConnection mDomainVerificationConnection;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final DynamicCodeLogger mDynamicCodeLogger;
    public ArraySet mExistingPackages;
    public AppOpsService.AnonymousClass6 mExternalSourcesPolicy;
    public final boolean mFactoryTest;
    public final boolean mFirstBoot;
    public final FreeStorageHelper mFreeStorageHelper;
    public final FrozenPackageInterceptor mFrozenPackageInterceptor;
    public final WatchedArrayMap mFrozenPackages;
    public final SnapshotCache.Auto mFrozenPackagesSnapshot;
    public final Handler mHandler;
    public final String mIncidentReportApproverPackage;
    public final IncrementalManager mIncrementalManager;
    public final String mIncrementalVersion;
    public final InitAppsHelper mInitAppsHelper;
    public final Set mInitialNonStoppedSystemPackages;
    public final PackageManagerServiceInjector mInjector;
    public final PackageManagerTracedLock mInstallLock;
    public final InstallPackageHelper mInstallPackageHelper;
    public final Installer mInstaller;
    public final PackageInstallerService mInstallerService;
    public ActivityInfo mInstantAppInstallerActivity;
    public final ResolveInfo mInstantAppInstallerInfo;
    public final InstantAppRegistry mInstantAppRegistry;
    public final InstantAppResolverConnection mInstantAppResolverConnection;
    public final ComponentName mInstantAppResolverSettingsComponent;
    public final WatchedArrayMap mInstrumentation;
    public final SnapshotCache.Auto mInstrumentationSnapshot;
    public final boolean mIsEngBuild;
    public final boolean mIsPreNMR1Upgrade;
    public final boolean mIsPreQUpgrade;
    public final boolean mIsUpgrade;
    public final boolean mIsUserDebugBuild;
    public final WatchedSparseIntArray mIsolatedOwners;
    public final SnapshotCache.Auto mIsolatedOwnersSnapshot;
    public final ArraySet mKeepUninstalledPackages;
    public final LegacyPermissionManagerService.Internal mLegacyPermissionManager;
    public final ComputerLocked mLiveComputer;
    public final PackageManagerTracedLock mLock;
    public final ModuleInfoProvider mModuleInfoProvider;
    public final MonetizationUtils mMonetizationUtils;
    public final MovePackageHelper.MoveCallbacks mMoveCallbacks;
    public int mNextInstallToken;
    public final AtomicInteger mNextMoveId;
    public final Map mNoKillInstallObservers;
    public final OverlayConfig mOverlayConfig;
    public final String mOverlayConfigSignaturePackage;
    public final PackageManagerTracedLock mOverlayPathsLock;
    public final PackageDexOptimizer mPackageDexOptimizer;
    public final PackageMonitorCallbackHelper mPackageMonitorCallbackHelper;
    public final PackageObserverHelper mPackageObserverHelper;
    public final AnonymousClass3 mPackageParserCallback;
    public final PackageProperty mPackageProperty;
    public final PackageStateMutator mPackageStateMutator;
    public final PackageManagerTracedLock mPackageStateWriteLock;
    public final PackageUsage mPackageUsage;
    public final WatchedArrayMap mPackages;
    public final SnapshotCache.Auto mPackagesSnapshot;
    public final PendingPackageBroadcasts mPendingBroadcasts;
    public final SparseArray mPendingEnableRollback;
    public int mPendingEnableRollbackToken;
    public final Map mPendingKillInstallObservers;
    public final SparseArray mPendingVerification;
    public int mPendingVerificationToken;
    public PerUidReadTimeouts[] mPerUidReadTimeoutsCache;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;
    public AndroidPackage mPlatformPackage;
    public String[] mPlatformPackageOverlayPaths;
    public String[] mPlatformPackageOverlayResourceDirs;
    public final PmLifecycleImpl mPmLifecycle;
    public final PreferredActivityHelper mPreferredActivityHelper;
    public Future mPrepareAppDataFuture;
    public final int mPriorSdkVersion;
    public final ProcessLoggingHandler mProcessLoggingHandler;
    public final boolean mPromoteSystemApps;
    public final ArraySet mProtectedBroadcasts;
    public final ProtectedPackages mProtectedPackages;
    public final String mRecentsPackage;
    public List mReleaseOnSystemReady;
    public final RemovePackageHelper mRemovePackageHelper;
    public String[] mReplacedResolverPackageOverlayPaths;
    public String[] mReplacedResolverPackageOverlayResourceDirs;
    public final String mRequiredInstallerPackage;
    public final String mRequiredPermissionControllerPackage;
    public final String mRequiredSdkSandboxPackage;
    public final Set mRequiredSystemPackages;
    public final String mRequiredUninstallerPackage;
    public final String[] mRequiredVerifierPackages;
    public final ActivityInfo mResolveActivity;
    public ComponentName mResolveComponentName;
    public final ResolveInfo mResolveInfo;
    public final ResolveIntentHelper mResolveIntentHelper;
    public boolean mResolverReplaced;
    public final String mRetailDemoPackage;
    public final SparseArray mRunningInstalls;
    public volatile boolean mSafeMode;
    public final int mSdkVersion;
    public final String[] mSeparateProcesses;
    public final long mServiceStartWithDelay;
    public final String mServicesExtensionPackageName;
    public final Settings mSettings;
    public final String mSetupWizardPackage;
    public final SharedLibrariesImpl mSharedLibraries;
    public final String mSharedSystemSharedLibraryPackageName;
    public final boolean mShouldStopSystemPackagesByDefault;
    public final Object mSnapshotLock;
    public final SnapshotStatistics mSnapshotStatistics;
    public SpegService mSpeg;
    public final StorageEventHelper mStorageEventHelper;
    public final String mStorageManagerPackage;
    public final SuspendPackageHelper mSuspendPackageHelper;
    public volatile boolean mSystemReady;
    public final String mSystemTextClassifierPackageName;
    public final TestUtilityService mTestUtilityService;
    public final ArraySet mTransferredPackages;
    public final UserManagerService mUserManager;
    public final UserNeedsBadgingCache mUserNeedsBadging;
    public final AnonymousClass1 mWatcher;
    public final String mWearableSensingPackage;
    public final WatchedSparseBooleanArray mWebInstantAppsDisabled;

    /* renamed from: com.android.server.pm.PackageManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 implements Handler.Callback {
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            int i = message.what;
            if (i != 14) {
                if (i != 30) {
                    return false;
                }
                ((Runnable) message.obj).run();
                return true;
            }
            PackageManagerService packageManagerService = (PackageManagerService) message.obj;
            synchronized (packageManagerService.mLock) {
                packageManagerService.mBackgroundHandler.removeMessages(14);
                synchronized (packageManagerService.mDirtyUsers) {
                    if (!packageManagerService.mDirtyUsers.isEmpty()) {
                        Integer[] numArr = (Integer[]) packageManagerService.mDirtyUsers.toArray(new PackageManagerService$$ExternalSyntheticLambda5());
                        packageManagerService.mDirtyUsers.clear();
                        Settings settings = packageManagerService.mSettings;
                        settings.getClass();
                        PackageManagerService.invalidatePackageInfoCache();
                        ChangeIdStateCache.invalidate();
                        settings.dispatchChange(settings);
                        long uptimeMillis = SystemClock.uptimeMillis();
                        for (Integer num : numArr) {
                            settings.writePackageRestrictions(num.intValue(), uptimeMillis, true);
                        }
                    }
                }
            }
            return true;
        }
    }

    public final class DefaultSystemWrapper {
        public static void disablePackageCaches() {
            PackageManager.disableApplicationInfoCache();
            PackageManager.disablePackageInfoCache();
            ApplicationPackageManager.invalidateGetPackagesForUidCache();
            ApplicationPackageManager.disableGetPackagesForUidCache();
            ApplicationPackageManager.invalidateHasSystemFeatureCache();
            PackageManager.corkPackageInfoCache();
        }

        public static void enablePackageCaches() {
            PackageManager.uncorkPackageInfoCache();
        }
    }

    public final class FindPreferredActivityBodyResult {
        public boolean mChanged;
        public ResolveInfo mPreferredResolveInfo;
    }

    public final class IPackageManagerImpl extends IPackageManager.Stub {
        public final Context mContext;
        public final DexOptHelper mDexOptHelper;
        public final DomainVerificationConnection mDomainVerificationConnection;
        public final DomainVerificationManagerInternal mDomainVerificationManager;
        public final PackageInstallerService mInstallerService;
        public final ComponentName mInstantAppResolverSettingsComponent;
        public final ModuleInfoProvider mModuleInfoProvider;
        public final PackageProperty mPackageProperty;
        public final PreferredActivityHelper mPreferredActivityHelper;
        public final ComponentName mResolveComponentName;
        public final ResolveIntentHelper mResolveIntentHelper;
        public final PackageManagerService mService;
        public final String mServicesExtensionPackageName;
        public final String mSharedSystemSharedLibraryPackageName;

        public IPackageManagerImpl() {
            Context context = PackageManagerService.this.mContext;
            ComponentName componentName = PackageManagerService.this.mResolveComponentName;
            ComponentName componentName2 = PackageManagerService.this.mInstantAppResolverSettingsComponent;
            this.mService = PackageManagerService.this;
            this.mContext = context;
            this.mDexOptHelper = PackageManagerService.this.mDexOptHelper;
            this.mModuleInfoProvider = PackageManagerService.this.mModuleInfoProvider;
            this.mPreferredActivityHelper = PackageManagerService.this.mPreferredActivityHelper;
            this.mResolveIntentHelper = PackageManagerService.this.mResolveIntentHelper;
            this.mDomainVerificationManager = PackageManagerService.this.mDomainVerificationManager;
            this.mDomainVerificationConnection = PackageManagerService.this.mDomainVerificationConnection;
            this.mInstallerService = PackageManagerService.this.mInstallerService;
            this.mPackageProperty = PackageManagerService.this.mPackageProperty;
            this.mResolveComponentName = componentName;
            this.mInstantAppResolverSettingsComponent = componentName2;
            this.mServicesExtensionPackageName = PackageManagerService.this.mServicesExtensionPackageName;
            this.mSharedSystemSharedLibraryPackageName = PackageManagerService.this.mSharedSystemSharedLibraryPackageName;
        }

        public final boolean activitySupportsIntentAsUser(ComponentName componentName, Intent intent, String str, int i) {
            return this.mService.snapshotComputer().activitySupportsIntentAsUser(this.mResolveComponentName, componentName, intent, str, i);
        }

        public final void addCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) {
            PackageManagerService packageManagerService = this.mService;
            packageManagerService.addCrossProfileIntentFilter(packageManagerService.snapshotComputer(), new WatchedIntentFilter(intentFilter), str, i, i2, i3);
        }

        public final boolean addPermission(PermissionInfo permissionInfo) {
            return ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).addPermission(permissionInfo, false);
        }

        public final boolean addPermissionAsync(PermissionInfo permissionInfo) {
            return ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).addPermission(permissionInfo, true);
        }

        public final void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            WatchedIntentFilter watchedIntentFilter = new WatchedIntentFilter(intentFilter);
            preferredActivityHelper.getClass();
            int callingUid = Binder.getCallingUid();
            if (callingUid != 1000) {
                throw new SecurityException("addPersistentPreferredActivity can only be run by the system");
            }
            if (!watchedIntentFilter.mFilter.checkDataPathAndSchemeSpecificParts()) {
                EventLog.writeEvent(1397638484, "246749702", Integer.valueOf(callingUid));
                throw new IllegalArgumentException("Invalid intent data paths or scheme specific parts in the filter.");
            }
            if (watchedIntentFilter.mFilter.countActions() == 0) {
                Slog.w("PackageManager", "Cannot set a preferred activity with no filter actions");
                return;
            }
            PackageManagerTracedLock packageManagerTracedLock = preferredActivityHelper.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    WatchedSparseArray watchedSparseArray = preferredActivityHelper.mPm.mSettings.mPersistentPreferredActivities;
                    PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) watchedSparseArray.mStorage.get(i);
                    if (persistentPreferredIntentResolver == null) {
                        persistentPreferredIntentResolver = new PersistentPreferredIntentResolver();
                        watchedSparseArray.put(i, persistentPreferredIntentResolver);
                    }
                    persistentPreferredIntentResolver.addFilter((PackageDataSnapshot) preferredActivityHelper.mPm.snapshotComputer(), (WatchedIntentFilter) new PersistentPreferredActivity(watchedIntentFilter, componentName));
                    preferredActivityHelper.mPm.scheduleWritePackageRestrictions(i);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            if (PreferredActivityHelper.isHomeFilter(watchedIntentFilter)) {
                preferredActivityHelper.updateDefaultHomeNotLocked(preferredActivityHelper.mPm.snapshotComputer(), i);
            }
            preferredActivityHelper.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
        }

        public final void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2, boolean z) {
            this.mPreferredActivityHelper.addPreferredActivity(this.mService.snapshotComputer(), new WatchedIntentFilter(intentFilter), i, componentNameArr, componentName, true, i2, "Adding preferred", z);
        }

        public final boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.applyRuntimePermissionsForAllApplicationsForMDM(i, i2);
        }

        public final boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.applyRuntimePermissionsForMDM(str, list, i, i2);
        }

        public final boolean canForwardTo(Intent intent, String str, int i, int i2) {
            return this.mService.snapshotComputer().canForwardTo(intent, str, i, i2);
        }

        public final boolean[] canPackageQuery(String str, String[] strArr, int i) {
            return this.mService.snapshotComputer().canPackageQuery(str, strArr, i);
        }

        public final boolean canRequestPackageInstalls(String str, int i) {
            return this.mService.snapshotComputer().canRequestPackageInstalls(Binder.getCallingUid(), i, str, true);
        }

        public final String[] canonicalToCurrentPackageNames(String[] strArr) {
            return this.mService.snapshotComputer().canonicalToCurrentPackageNames(strArr);
        }

        public final void changeMonetizationBadgeState(String str, String str2) {
            MonetizationUtils monetizationUtils = PackageManagerService.this.mMonetizationUtils;
            monetizationUtils.getClass();
            Log.i("Monetization", "changeMonetizationBadgeState value:" + str + " PackageName: " + str2);
            if (!PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED || monetizationUtils.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new SecurityException("changeMonetizationBadgeState can only be run by the system or feature not supported");
            }
            Preconditions.checkNotNull(str, "value cannot be null");
            str.getClass();
            switch (str) {
                case "0":
                    if (monetizationUtils.mGalaxyStoreBadgeEnabled.get()) {
                        monetizationUtils.mGalaxyStoreBadgeEnabled.set(false);
                        synchronized (monetizationUtils.mBadgeLock) {
                            monetizationUtils.mGalaxyStoreAppsForBadge.clear();
                        }
                        SystemProperties.set("persist.galaxy_store.badge.feature", str);
                        return;
                    }
                    return;
                case "1":
                    if (!monetizationUtils.mGalaxyStoreBadgeEnabled.get()) {
                        monetizationUtils.mGalaxyStoreBadgeEnabled.set(true);
                        SystemProperties.set("persist.galaxy_store.badge.feature", str);
                    }
                    Preconditions.checkNotNull(str2, "packageName cannot be null");
                    monetizationUtils.updateSettingsForMonetization(str2, false, false, true);
                    return;
                case "2":
                    Preconditions.checkNotNull(str2, "packageName cannot be null");
                    monetizationUtils.updateSettingsForMonetization(str2, false, false, false);
                    return;
                default:
                    Log.i("Monetization", "Unknown parameter passed to change badge state");
                    return;
            }
        }

        public final void checkPackageStartable(String str, int i) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null) {
                throw new SecurityException("Instant applications don't have access to this method");
            }
            if (!packageManagerService.mUserManager.mLocalService.exists(i)) {
                throw new SecurityException("User doesn't exist");
            }
            snapshotComputer.enforceCrossUserPermission("checkPackageStartable", callingUid, i, false, false);
            int packageStartability = snapshotComputer.getPackageStartability(callingUid, i, str, packageManagerService.mSafeMode);
            if (packageStartability == 1) {
                throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " was not found!"));
            }
            if (packageStartability == 2) {
                throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " not a system app!"));
            }
            if (packageStartability == 3) {
                throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " is currently frozen!"));
            }
            if (packageStartability == 4) {
                throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " is not encryption aware!"));
            }
            if (packageStartability == 5) {
                throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " is not dualdar aware!"));
            }
        }

        public final int checkPermission(String str, String str2, int i) {
            return this.mService.checkPermission(str, str2, i);
        }

        public final int checkSignatures(String str, String str2, int i) {
            return this.mService.snapshotComputer().checkSignatures(str, str2, i);
        }

        public final int checkUidPermission(String str, int i) {
            return this.mService.snapshotComputer().checkUidPermission(str, i);
        }

        public final int checkUidSignatures(int i, int i2) {
            return this.mService.snapshotComputer().checkUidSignatures(i, i2);
        }

        public final void clearAppCategoryHintUser(String str) {
            PackageManagerService.sAppCategoryHintHelper.clearAppCategoryHintUser(str);
        }

        public final void clearApplicationProfileData(String str) {
            boolean z = PackageManagerServiceUtils.DEBUG;
            if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
                throw new SecurityException("Only the system or shell can clear all profile data");
            }
            AndroidPackage androidPackage = PackageManagerService.this.snapshotComputer().getPackage(str);
            PackageFreezer freezePackage = PackageManagerService.this.freezePackage(str, -1, "clearApplicationProfileData", 13, null);
            try {
                PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mInstallLock;
                packageManagerTracedLock.mLock.lock();
                try {
                    PackageManagerService.this.mAppDataHelper.getClass();
                    AppDataHelper.clearAppProfilesLIF(androidPackage);
                    packageManagerTracedLock.close();
                    freezePackage.close();
                } finally {
                }
            } catch (Throwable th) {
                try {
                    freezePackage.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        public final void clearApplicationUserData(final String str, final IPackageDataObserver iPackageDataObserver, final int i) {
            clearApplicationUserData_enforcePermission();
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("clear application data", callingUid, i, true, false);
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, str) == null) {
                if (iPackageDataObserver != null) {
                    PackageManagerService.this.mHandler.post(new PackageManagerService$$ExternalSyntheticLambda51(2, iPackageDataObserver, str));
                    return;
                }
                return;
            }
            ProtectedPackages protectedPackages = PackageManagerService.this.mProtectedPackages;
            if (protectedPackages.hasDeviceOwnerOrProfileOwner(i, str) || protectedPackages.isProtectedPackage(i, str)) {
                throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Cannot clear data for a protected package: ", str));
            }
            StringBuilder sb = new StringBuilder("START CLEAR APPLICATION USER DATA: observer{");
            sb.append(iPackageDataObserver != null ? Integer.valueOf(iPackageDataObserver.hashCode()) : "null");
            sb.append("}\npkg{");
            sb.append(str);
            sb.append("}\nuser{");
            sb.append(i);
            sb.append("}\n");
            Log.d("PackageManager", sb.toString());
            EventLog.writeEvent(3132, Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(callingUid), str);
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService.IPackageManagerImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.this.mHandler.removeCallbacks(this);
                    PackageManagerService packageManagerService = PackageManagerService.this;
                    String str2 = str;
                    packageManagerService.getClass();
                    PackageFreezer packageFreezer = new PackageFreezer(str2, -1, "clearApplicationUserData", packageManagerService, 10, null, true);
                    try {
                        PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mInstallLock;
                        packageManagerTracedLock.mLock.lock();
                        try {
                            PackageManagerService packageManagerService2 = PackageManagerService.this;
                            boolean clearApplicationUserDataLIF = packageManagerService2.clearApplicationUserDataLIF(packageManagerService2.snapshotComputer(), str, i);
                            packageManagerTracedLock.close();
                            PackageManagerService.this.mInstantAppRegistry.deleteInstantApplicationMetadata(i, str);
                            PackageManagerTracedLock packageManagerTracedLock2 = PackageManagerService.this.mLock;
                            boolean z = PackageManagerService.DEBUG_COMPRESSION;
                            synchronized (packageManagerTracedLock2) {
                                if (clearApplicationUserDataLIF) {
                                    try {
                                        PackageManagerService.m759$$Nest$mresetComponentEnabledSettingsIfNeededLPw(PackageManagerService.this, str, i);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                            packageFreezer.close();
                            if (clearApplicationUserDataLIF) {
                                DeviceStorageMonitorService.AnonymousClass2 anonymousClass2 = (DeviceStorageMonitorService.AnonymousClass2) LocalServices.getService(DeviceStorageMonitorService.AnonymousClass2.class);
                                if (anonymousClass2 != null) {
                                    DeviceStorageMonitorService deviceStorageMonitorService = DeviceStorageMonitorService.this;
                                    deviceStorageMonitorService.mHandler.removeMessages(1);
                                    deviceStorageMonitorService.mHandler.obtainMessage(1).sendToTarget();
                                }
                                if (IPackageManagerImpl.this.mService.checkPermission("android.permission.SUSPEND_APPS", str, i) == 0) {
                                    Computer snapshotComputer2 = PackageManagerService.this.snapshotComputer();
                                    PackageManagerService.this.unsuspendForSuspendingPackage(i, snapshotComputer2, str, true);
                                    PackageManagerService packageManagerService3 = PackageManagerService.this;
                                    int i2 = i;
                                    packageManagerService3.getClass();
                                    packageManagerService3.mDistractingPackageHelper.removeDistractingPackageRestrictions(snapshotComputer2, snapshotComputer2.getAllAvailablePackageNames(), i2);
                                    synchronized (PackageManagerService.this.mLock) {
                                        try {
                                            PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
                                        } finally {
                                            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                                        }
                                    }
                                }
                            }
                            if (iPackageDataObserver != null) {
                                try {
                                    StringBuilder sb2 = new StringBuilder("result of clearing user data: ");
                                    sb2.append(clearApplicationUserDataLIF ? "succeeded" : "failed");
                                    sb2.append("{");
                                    sb2.append(iPackageDataObserver.hashCode());
                                    sb2.append("}");
                                    Log.d("PackageManager", sb2.toString());
                                    iPackageDataObserver.onRemoveCompleted(str, clearApplicationUserDataLIF);
                                } catch (RemoteException unused) {
                                    Log.i("PackageManager", "Observer no longer exists.");
                                }
                            }
                        } finally {
                        }
                    } catch (Throwable th2) {
                        try {
                            packageFreezer.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            });
        }

        public final void clearCrossProfileIntentFilters(int i, String str) {
            clearCrossProfileIntentFilters_enforcePermission();
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.getClass();
            PackageManagerService.enforceOwnerRights(snapshotComputer, str, callingUid);
            PackageManagerServiceUtils.enforceShellRestriction(PackageManagerService.this.mInjector.getUserManagerService().mLocalService, callingUid, i);
            PackageManagerServiceInjector packageManagerServiceInjector = PackageManagerService.this.mInjector;
            ((CrossProfileIntentFilterHelper) packageManagerServiceInjector.mCrossProfileIntentFilterHelperProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector)).clearCrossProfileIntentFilters(i, str, null);
            PackageManagerService.this.scheduleWritePackageRestrictions(i);
        }

        public final void clearPackagePersistentPreferredActivities(String str, int i) {
            boolean clearPackagePersistentPreferredActivities;
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.getClass();
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("clearPackagePersistentPreferredActivities can only be run by the system");
            }
            PackageManagerTracedLock packageManagerTracedLock = preferredActivityHelper.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    clearPackagePersistentPreferredActivities = preferredActivityHelper.mPm.mSettings.clearPackagePersistentPreferredActivities(i, str);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            if (clearPackagePersistentPreferredActivities) {
                preferredActivityHelper.updateDefaultHomeNotLocked(preferredActivityHelper.mPm.snapshotComputer(), i);
                preferredActivityHelper.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
                preferredActivityHelper.mPm.scheduleWritePackageRestrictions(i);
            }
        }

        public final void clearPackagePreferredActivities(String str) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            preferredActivityHelper.getClass();
            int callingUid = Binder.getCallingUid();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null) {
                return;
            }
            PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
            PackageManagerService packageManagerService = preferredActivityHelper.mPm;
            if ((packageStateInternal == null || !snapshotComputer.isCallerSameApp(callingUid, str)) && packageManagerService.mContext.checkCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS") != 0) {
                if (snapshotComputer.getUidTargetSdkVersion(callingUid) < 8) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(callingUid, "Ignoring clearPackagePreferredActivities() from uid ", "PackageManager");
                    return;
                }
                packageManagerService.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
            }
            if (packageStateInternal == null || !snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, UserHandle.getUserId(callingUid))) {
                int callingUserId = UserHandle.getCallingUserId();
                try {
                    IKioskMode asInterface = IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
                    if (asInterface != null && str.equals(asInterface.getKioskHomePackageAsUser(callingUserId)) && asInterface.isKioskModeEnabledAsUser(callingUserId)) {
                        Intent intent = new Intent("com.samsung.android.knox.intent.action.TERMINATE_KIOSK_INTERNAL");
                        intent.putExtra("android.intent.extra.user_handle", callingUserId);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        packageManagerService.mContext.sendBroadcastAsUser(intent, new UserHandle(callingUserId));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Exception e) {
                    Log.w("PackageManager", " clearPackagePreferredActivities in kioskMode exception: " + e.toString());
                }
                preferredActivityHelper.clearPackagePreferredActivities(UserHandle.getCallingUserId(), str);
            }
        }

        public final void clearPackagePreferredActivitiesAsUserForMDM(String str, int i) {
            int callingUid = Binder.getCallingUid();
            if (callingUid != 0 && callingUid != 1000) {
                throw new SecurityException("clearPackagePreferredActivitiesAsUserForMDM can only be called by the system");
            }
            long currentTimeMillis = System.currentTimeMillis();
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            try {
                PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        PackageManagerService.this.clearPackagePreferredActivitiesLPw(str, sparseBooleanArray, i);
                        if (sparseBooleanArray.size() > 0) {
                            PackageManagerService.this.scheduleWritePackageRestrictions(i);
                        }
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
            } finally {
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                if (currentTimeMillis2 > 100) {
                    Log.d("PackageManager", "clearPackagePreferredActivitiesAsUserForMDM - Execution time: " + currentTimeMillis2 + " ms");
                }
            }
        }

        public final void clearPersistentPreferredActivity(IntentFilter intentFilter, int i) {
            boolean clearPersistentPreferredActivity;
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.getClass();
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("clearPersistentPreferredActivity can only be run by the system");
            }
            PackageManagerTracedLock packageManagerTracedLock = preferredActivityHelper.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    clearPersistentPreferredActivity = preferredActivityHelper.mPm.mSettings.clearPersistentPreferredActivity(intentFilter, i);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            if (clearPersistentPreferredActivity) {
                preferredActivityHelper.updateDefaultHomeNotLocked(preferredActivityHelper.mPm.snapshotComputer(), i);
                preferredActivityHelper.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
                preferredActivityHelper.mPm.scheduleWritePackageRestrictions(i);
            }
        }

        public final boolean createEncAppData(String str, int i) {
            return false;
        }

        public final String[] currentToCanonicalPackageNames(String[] strArr) {
            return this.mService.snapshotComputer().currentToCanonicalPackageNames(strArr);
        }

        public final void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) {
            deleteApplicationCacheFilesAsUser(str, UserHandle.getCallingUserId(), iPackageDataObserver);
        }

        public final void deleteApplicationCacheFilesAsUser(final String str, final int i, final IPackageDataObserver iPackageDataObserver) {
            final int callingUid = Binder.getCallingUid();
            if (PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_DELETE_CACHE_FILES") != 0) {
                if (PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_CACHE_FILES") == 0) {
                    BrailleDisplayConnection$$ExternalSyntheticOutline0.m(callingUid, "Calling uid ", " does not have android.permission.INTERNAL_DELETE_CACHE_FILES, silently ignoring", "PackageManager");
                    return;
                }
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERNAL_DELETE_CACHE_FILES", null);
            }
            PackageManagerService.this.snapshotComputer().enforceCrossUserPermission("delete application cache files", callingUid, i, true, false);
            final int checkCallingOrSelfPermission = PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS");
            IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
            if (asInterface != null) {
                try {
                    if (asInterface.isApplicationClearCacheDisabled(str, i, true)) {
                        Log.w("PackageManager", "Cache cannot be cleared for this app due to EDM policy. packageName = " + str);
                        if (iPackageDataObserver != null) {
                            try {
                                Log.d("PackageManager", "result of delete cache files: completed{" + iPackageDataObserver.hashCode() + "}");
                                iPackageDataObserver.onRemoveCompleted(str, false);
                                return;
                            } catch (RemoteException unused) {
                                Log.i("PackageManager", "Observer no longer exists.");
                                return;
                            }
                        }
                        return;
                    }
                } catch (Exception unused2) {
                }
            }
            EventLog.writeEvent(3132, Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(callingUid), str);
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                    String str2 = str;
                    int i2 = callingUid;
                    int i3 = checkCallingOrSelfPermission;
                    int i4 = i;
                    IPackageDataObserver iPackageDataObserver2 = iPackageDataObserver;
                    PackageSetting packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str2);
                    if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(UserHandle.getUserId(i2)).isInstantApp() || i3 == 0) {
                        PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mInstallLock;
                        packageManagerTracedLock.mLock.lock();
                        try {
                            AndroidPackage androidPackage = PackageManagerService.this.snapshotComputer().getPackage(str2);
                            PackageManagerService.this.mAppDataHelper.clearAppDataLIF(androidPackage, i4, 23);
                            PackageManagerService.this.mAppDataHelper.clearAppDataLIF(androidPackage, i4, 39);
                            packageManagerTracedLock.close();
                        } catch (Throwable th) {
                            try {
                                packageManagerTracedLock.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    if (iPackageDataObserver2 != null) {
                        try {
                            iPackageDataObserver2.onRemoveCompleted(str2, true);
                        } catch (RemoteException unused3) {
                            Log.i("PackageManager", "Observer no longer exists.");
                        }
                    }
                }
            });
        }

        public final void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
            this.mService.deleteExistingPackageAsUser(versionedPackage, iPackageDeleteObserver2, i);
        }

        public final void deletePackageAsUser(String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) {
            deletePackageVersioned(new VersionedPackage(str, i), new PackageManager.LegacyPackageDeleteObserver(iPackageDeleteObserver).getBinder(), i2, i3);
        }

        public final void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) {
            this.mService.mDeletePackageHelper.deletePackageVersionedInternal(versionedPackage, iPackageDeleteObserver2, i, i2, false);
        }

        public final void deletePreloadsFileCache() {
            this.mService.deletePreloadsFileCache();
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
        
            if ((r0 != null ? "com.baidu.carlife".equals(r0) : false) != false) goto L16;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dump(java.io.FileDescriptor r26, final java.io.PrintWriter r27, java.lang.String[] r28) {
            /*
                Method dump skipped, instructions count: 1446
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
        }

        public final void enterSafeMode() {
            PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request entering safe mode");
            if (PackageManagerService.this.mSystemReady) {
                return;
            }
            PackageManagerService.this.mSafeMode = true;
        }

        public final void extendVerificationTimeout(final int i, final int i2, final long j) {
            if (i >= 0) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can extend verification timeouts");
            }
            final int callingUid = Binder.getCallingUid();
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                    int i3 = i;
                    int i4 = callingUid;
                    int i5 = i2;
                    long j2 = j;
                    iPackageManagerImpl.getClass();
                    if (i3 < 0) {
                        i3 = -i3;
                    }
                    PackageVerificationState packageVerificationState = (PackageVerificationState) PackageManagerService.this.mPendingVerification.get(i3);
                    if (packageVerificationState == null || !packageVerificationState.mRequiredVerifierUids.get(i4, false) || packageVerificationState.mExtendedTimeoutUids.get(i4, false)) {
                        return;
                    }
                    packageVerificationState.mExtendedTimeoutUids.append(i4, true);
                    PackageVerificationResponse packageVerificationResponse = new PackageVerificationResponse(i5, i4);
                    if (j2 > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                        j2 = 3600000;
                    }
                    if (j2 < 0) {
                        j2 = 0;
                    }
                    Message obtainMessage = PackageManagerService.this.mHandler.obtainMessage(15);
                    obtainMessage.arg1 = i3;
                    obtainMessage.obj = packageVerificationResponse;
                    PackageManagerService.this.mHandler.sendMessageDelayed(obtainMessage, j2);
                }
            });
        }

        public final ResolveInfo findPersistentPreferredActivity(Intent intent, int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            preferredActivityHelper.getClass();
            if (!UserHandle.isSameApp(Binder.getCallingUid(), 1000)) {
                throw new SecurityException("findPersistentPreferredActivity can only be run by the system");
            }
            PackageManagerService packageManagerService = preferredActivityHelper.mPm;
            if (!packageManagerService.mUserManager.mLocalService.exists(i)) {
                return null;
            }
            int callingUid = Binder.getCallingUid();
            boolean z = PackageManagerServiceUtils.DEBUG;
            if (intent.getSelector() != null) {
                intent = intent.getSelector();
            }
            String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(packageManagerService.mContext.getContentResolver());
            long updateFlagsForResolve = snapshotComputer.updateFlagsForResolve(i, callingUid, 0L, false, snapshotComputer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, resolveTypeIfNeeded, 0L, i));
            Intent intent2 = intent;
            return snapshotComputer.findPersistentPreferredActivity(intent2, resolveTypeIfNeeded, updateFlagsForResolve, snapshotComputer.queryIntentActivitiesInternal(intent2, resolveTypeIfNeeded, updateFlagsForResolve, i), false, i);
        }

        public final void finishPackageInstall(int i, boolean z) {
            PackageManagerService packageManagerService = this.mService;
            packageManagerService.getClass();
            PackageManagerServiceUtils.enforceSystemOrRoot("Only the system is allowed to finish installs");
            Trace.asyncTraceEnd(262144L, "restore", i);
            Handler handler = packageManagerService.mHandler;
            handler.sendMessage(handler.obtainMessage(9, i, z ? 1 : 0));
        }

        public final void flushPackageRestrictionsAsUser(int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            if (snapshotComputer.getInstantAppPackageName(callingUid) == null && PackageManagerService.this.mUserManager.mLocalService.exists(i)) {
                snapshotComputer.enforceCrossUserPermission("flushPackageRestrictions", callingUid, i, false, false);
                PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
            }
        }

        public final void freeStorage(String str, long j, int i, IntentSender intentSender) {
            freeStorage_enforcePermission();
            PackageManagerService.this.mHandler.post(new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda10(this, str, j, i, intentSender, 1));
        }

        public final void freeStorageAndNotify(String str, long j, int i, IPackageDataObserver iPackageDataObserver) {
            StringBuilder sb = new StringBuilder("START FREE STORAGE AND NOTIFY: observer{");
            sb.append(iPackageDataObserver != null ? Integer.valueOf(iPackageDataObserver.hashCode()) : "null");
            sb.append("}\nfreeStorageSize{");
            sb.append(j);
            VpnManagerService$$ExternalSyntheticOutline0.m(sb, "}", "PackageManager");
            freeStorageAndNotify_enforcePermission();
            PackageManagerService.this.mHandler.post(new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda10(this, str, j, i, iPackageDataObserver, 0));
        }

        public final ActivityInfo getActivityInfo(ComponentName componentName, long j, int i) {
            return this.mService.snapshotComputer().getActivityInfo(componentName, j, i);
        }

        public final ParceledListSlice getAllIntentFilters(String str) {
            return this.mService.snapshotComputer().getAllIntentFilters(str);
        }

        public final List getAllPackages() {
            return this.mService.snapshotComputer().getAllPackages();
        }

        public final Map getAppCategoryHintUserMap() {
            Map map;
            AppCategoryHintHelper appCategoryHintHelper = PackageManagerService.sAppCategoryHintHelper;
            appCategoryHintHelper.getClass();
            if (!AppCategoryHintHelper.isSystemServerOrShell()) {
                throw new RemoteException("calling uid is not system server!");
            }
            if (!appCategoryHintHelper.mInit.get()) {
                android.util.secutil.Slog.d("AppCategoryHintHelper", "AppCategoryHintHelper is not initialized, can't set category");
                throw new RemoteException("AppCategoryHintHelper is not initialized, can't set category");
            }
            synchronized (appCategoryHintHelper.mCategoryMap) {
                final int i = 0;
                final int i2 = 1;
                map = (Map) appCategoryHintHelper.mCategoryMap.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.android.server.pm.AppCategoryHintHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Map.Entry entry = (Map.Entry) obj;
                        switch (i) {
                            case 0:
                                return (String) entry.getKey();
                            default:
                                return Integer.toString(((Integer) entry.getValue()).intValue());
                        }
                    }
                }, new Function() { // from class: com.android.server.pm.AppCategoryHintHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Map.Entry entry = (Map.Entry) obj;
                        switch (i2) {
                            case 0:
                                return (String) entry.getKey();
                            default:
                                return Integer.toString(((Integer) entry.getValue()).intValue());
                        }
                    }
                }));
            }
            return map;
        }

        public final Map getAppCategoryInfos(String str) {
            final AppCategoryHintHelper appCategoryHintHelper = PackageManagerService.sAppCategoryHintHelper;
            String[] packagesForUid = appCategoryHintHelper.mService.snapshotComputer().getPackagesForUid(Binder.getCallingUid());
            if (!(Build.isDebuggable() && Binder.getCallingUid() == 2000) && (packagesForUid == null || packagesForUid.length == 0 || !((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).isPlatformSigned(packagesForUid[0]))) {
                throw new SecurityException("Unknown or No permission, calling UID: " + Binder.getCallingUid());
            }
            final ArrayMap arrayMap = new ArrayMap();
            synchronized (appCategoryHintHelper.mService.mLock) {
                try {
                    if (str != null) {
                        PackageSetting packageLPr = appCategoryHintHelper.mService.mSettings.getPackageLPr(str);
                        if (packageLPr == null) {
                            throw new RemoteException("package not found!: ".concat(str));
                        }
                        arrayMap.put(str, new String[]{Integer.toString(packageLPr.pkg.getCategory()), Integer.toString(packageLPr.categoryOverride), Integer.toString(appCategoryHintHelper.getAppCategoryHintUser(str))});
                    } else {
                        appCategoryHintHelper.mService.mSettings.mPackages.forEach(new BiConsumer() { // from class: com.android.server.pm.AppCategoryHintHelper$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                AppCategoryHintHelper appCategoryHintHelper2 = AppCategoryHintHelper.this;
                                Map map = arrayMap;
                                String str2 = (String) obj;
                                PackageSetting packageSetting = (PackageSetting) obj2;
                                appCategoryHintHelper2.getClass();
                                AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
                                if (androidPackageInternal == null) {
                                    return;
                                }
                                map.put(str2, new String[]{Integer.toString(androidPackageInternal.getCategory()), Integer.toString(packageSetting.categoryOverride), Integer.toString(appCategoryHintHelper2.getAppCategoryHintUser(str2))});
                            }
                        });
                    }
                } catch (Throwable th) {
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            return arrayMap;
        }

        public final ParcelFileDescriptor getAppMetadataFd(String str, int i) {
            getAppMetadataFd_enforcePermission();
            PackageSetting packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(Binder.getCallingUid(), i, str);
            if (packageStateForInstalledAndFiltered == null) {
                throw new ParcelableException(new PackageManager.NameNotFoundException(str));
            }
            String str2 = packageStateForInstalledAndFiltered.mAppMetadataFilePath;
            if (str2 == null) {
                return null;
            }
            try {
                return ParcelFileDescriptor.open(new File(str2), 268435456);
            } catch (FileNotFoundException unused) {
                return null;
            }
        }

        public final int getAppMetadataSource(String str, int i) {
            getAppMetadataSource_enforcePermission();
            PackageSetting packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(Binder.getCallingUid(), i, str);
            if (packageStateForInstalledAndFiltered != null) {
                return packageStateForInstalledAndFiltered.mAppMetadataSource;
            }
            throw new ParcelableException(new PackageManager.NameNotFoundException(str));
        }

        public final String[] getAppOpPermissionPackages(String str, int i) {
            return this.mService.snapshotComputer().getAppOpPermissionPackages(str, i);
        }

        public final String getAppPredictionServicePackageName() {
            return this.mService.mAppPredictionServicePackage;
        }

        public final int getApplicationEnabledSetting(String str, int i) {
            return this.mService.snapshotComputer().getApplicationEnabledSetting(str, i);
        }

        public final boolean getApplicationHiddenSettingAsUser(String str, int i) {
            return this.mService.snapshotComputer().getApplicationHiddenSettingAsUser(str, i);
        }

        public final ApplicationInfo getApplicationInfo(String str, long j, int i) {
            return this.mService.snapshotComputer().getApplicationInfo(str, j, i);
        }

        public final Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) {
            return PackageManagerService.this.mInstallerService.mPackageArchiver.getArchivedAppIcon(str, userHandle, str2);
        }

        public final ArchivedPackageParcel getArchivedPackage(String str, int i) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.getClass();
            Objects.requireNonNull(str);
            packageManagerService.snapshotComputer().enforceCrossUserPermission("getArchivedPackage", Binder.getCallingUid(), i, true, true);
            ArchivedPackageParcel archivedPackageParcel = new ArchivedPackageParcel();
            archivedPackageParcel.packageName = str;
            synchronized (packageManagerService.mLock) {
                try {
                    PackageSetting packageLPr = packageManagerService.mSettings.getPackageLPr(str);
                    if (packageLPr != null) {
                        PackageUserStateInternal userStateOrDefault = packageLPr.getUserStateOrDefault(i);
                        ArchiveState archiveState = userStateOrDefault.getArchiveState();
                        if (archiveState != null || userStateOrDefault.isInstalled()) {
                            archivedPackageParcel.signingDetails = packageLPr.signatures.mSigningDetails;
                            long j = packageLPr.versionCode;
                            archivedPackageParcel.versionCodeMajor = (int) (j >> 32);
                            archivedPackageParcel.versionCode = (int) j;
                            archivedPackageParcel.targetSdkVersion = packageLPr.mTargetSdkVersion;
                            archivedPackageParcel.defaultToDeviceProtectedStorage = String.valueOf(packageLPr.isDefaultToDeviceProtectedStorage());
                            archivedPackageParcel.requestLegacyExternalStorage = String.valueOf((packageLPr.mPkgPrivateFlags & 536870912) != 0);
                            archivedPackageParcel.userDataFragile = String.valueOf((packageLPr.mPkgPrivateFlags & 16777216) != 0);
                            try {
                                if (archiveState != null) {
                                    archivedPackageParcel.archivedActivities = PackageArchiver.createArchivedActivities(archiveState);
                                } else {
                                    archivedPackageParcel.archivedActivities = PackageArchiver.createArchivedActivities(((ActivityManager) packageManagerService.mContext.getSystemService(ActivityManager.class)).getLauncherLargeIconSize(), packageManagerService.mInstallerService.mPackageArchiver.getLauncherActivityInfos(i, str));
                                }
                                return archivedPackageParcel;
                            } catch (Exception e) {
                                throw new IllegalArgumentException("Package does not have a main activity", e);
                            }
                        }
                    }
                    return null;
                } finally {
                }
            }
        }

        public final IArtManager getArtManager() {
            return this.mService.mArtManagerService;
        }

        public final String getAttentionServicePackageName() {
            return PackageManagerService.ensureSystemPackageName(this.mService.snapshotComputer(), this.mService.getPackageFromComponentString(R.string.date_picker_day_typeface));
        }

        public final boolean getBlockUninstallForUser(String str, int i) {
            return this.mService.snapshotComputer().getBlockUninstallForUser(str, i);
        }

        public final ChangedPackages getChangedPackages(int i, int i2) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            ChangedPackages changedPackages = null;
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null || !PackageManagerService.this.mUserManager.mLocalService.exists(i2)) {
                return null;
            }
            snapshotComputer.enforceCrossUserPermission("getChangedPackages", callingUid, i2, false, false);
            ChangedPackagesTracker changedPackagesTracker = PackageManagerService.this.mChangedPackagesTracker;
            synchronized (changedPackagesTracker.mLock) {
                try {
                    if (i < changedPackagesTracker.mChangedPackagesSequenceNumber) {
                        SparseArray sparseArray = (SparseArray) changedPackagesTracker.mUserIdToSequenceToPackage.get(i2);
                        if (sparseArray != null) {
                            ArrayList arrayList = new ArrayList(changedPackagesTracker.mChangedPackagesSequenceNumber - i);
                            while (i < changedPackagesTracker.mChangedPackagesSequenceNumber) {
                                String str = (String) sparseArray.get(i);
                                if (str != null) {
                                    arrayList.add(str);
                                }
                                i++;
                            }
                            if (!arrayList.isEmpty()) {
                                changedPackages = new ChangedPackages(changedPackagesTracker.mChangedPackagesSequenceNumber, arrayList);
                            }
                        }
                    }
                } finally {
                }
            }
            if (changedPackages != null) {
                List<String> packageNames = changedPackages.getPackageNames();
                for (int size = packageNames.size() - 1; size >= 0; size--) {
                    if (snapshotComputer.shouldFilterApplication(snapshotComputer.getPackageStateInternal(packageNames.get(size)), callingUid, i2)) {
                        packageNames.remove(size);
                    }
                }
            }
            return changedPackages;
        }

        public final int getComponentEnabledSetting(ComponentName componentName, int i) {
            return this.mService.snapshotComputer().getComponentEnabledSetting(Binder.getCallingUid(), i, componentName);
        }

        public final ParceledListSlice getDeclaredSharedLibraries(String str, long j, int i) {
            return this.mService.snapshotComputer().getDeclaredSharedLibraries(str, j, i);
        }

        public final byte[] getDefaultAppsBackup(int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.getClass();
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call getDefaultAppsBackup()");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                newFastSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                newFastSerializer.startDocument((String) null, Boolean.TRUE);
                newFastSerializer.startTag((String) null, "da");
                Settings.writeDefaultApps(newFastSerializer, preferredActivityHelper.mPm.mDefaultAppProvider.getRoleHolder(i, "android.app.role.BROWSER"));
                newFastSerializer.endTag((String) null, "da");
                newFastSerializer.endDocument();
                newFastSerializer.flush();
                return byteArrayOutputStream.toByteArray();
            } catch (Exception unused) {
                return null;
            }
        }

        public final String getDefaultTextClassifierPackageName() {
            return this.mService.mDefaultTextClassifierPackage;
        }

        public final ComponentName getDomainVerificationAgent(int i) {
            int callingUid = Binder.getCallingUid();
            if (!PackageManagerServiceUtils.isRootOrShell(callingUid)) {
                throw new SecurityException("Not allowed to query domain verification agent");
            }
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            ComponentName componentName = ((DomainVerificationService) PackageManagerService.this.mDomainVerificationManager).mProxy.getComponentName();
            PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, componentName.getPackageName());
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            WatchedArraySet disabledComponentsNoCopy = packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getDisabledComponentsNoCopy();
            if (disabledComponentsNoCopy != null) {
                if (disabledComponentsNoCopy.mStorage.contains(componentName.getClassName())) {
                    return null;
                }
            }
            return componentName;
        }

        public final byte[] getDomainVerificationBackup(int i) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call getDomainVerificationBackup()");
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
                    PackageManagerService packageManagerService = PackageManagerService.this;
                    ((DomainVerificationService) packageManagerService.mDomainVerificationManager).writeSettings(packageManagerService.snapshotComputer(), resolveSerializer, true, i);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } catch (Exception unused) {
                return null;
            }
        }

        public final int getFlagsForUid(int i) {
            return this.mService.snapshotComputer().getFlagsForUid(i);
        }

        public final List getGrantedPermissionsForMDM(String str) {
            Log.i("PackageManager", "getGrantedPermissionsForMDM");
            if (Binder.getCallingUid() != Process.myUid()) {
                Log.i("PackageManager", "getGrantedPermissionsForMDM: Caller is not SYSTEM_PROCESS");
                return null;
            }
            PackageSetting packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null || packageStateInternal.pkg == null) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("Not found ps or pkg for ", str, "PackageManager");
                return null;
            }
            ArrayList arrayList = new ArrayList();
            LegacyPermissionState legacyPermissionState = packageStateInternal.mLegacyPermissionsState;
            Collection<LegacyPermissionState.PermissionState> permissionStates = legacyPermissionState.getPermissionStates(-1);
            Collection<LegacyPermissionState.PermissionState> permissionStates2 = packageStateInternal.pkg.getTargetSdkVersion() > 22 ? legacyPermissionState.getPermissionStates(0) : null;
            if (permissionStates == null || permissionStates.size() <= 0) {
                Log.i("PackageManager", "getGrantedPermissionsForMDM : installPermissions is null");
            } else {
                for (LegacyPermissionState.PermissionState permissionState : permissionStates) {
                    if (permissionState != null && permissionState.mGranted) {
                        arrayList.add(permissionState.mName);
                    }
                }
            }
            if (permissionStates2 == null || permissionStates2.size() <= 0) {
                Log.i("PackageManager", "getGrantedPermissionsForMDM : runtimePermissions is null");
            } else {
                for (LegacyPermissionState.PermissionState permissionState2 : permissionStates2) {
                    if (permissionState2 != null && permissionState2.mGranted) {
                        arrayList.add(permissionState2.mName);
                    }
                }
            }
            return new ArrayList(arrayList);
        }

        public final CharSequence getHarmfulAppWarning(String str, int i) {
            return this.mService.snapshotComputer().getHarmfulAppWarning(str, i);
        }

        public final IBinder getHoldLockToken() {
            if (!Build.IS_DEBUGGABLE) {
                throw new SecurityException("getHoldLockToken requires a debuggable build");
            }
            PackageManagerService.this.mContext.enforceCallingPermission("android.permission.INJECT_EVENTS", "getHoldLockToken requires INJECT_EVENTS permission");
            Binder binder = new Binder();
            binder.attachInterface(this, "holdLock:" + Binder.getCallingUid());
            return binder;
        }

        public final ComponentName getHomeActivities(List list) {
            Computer snapshotComputer = this.mService.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
                return null;
            }
            return snapshotComputer.getHomeActivitiesAsUser(UserHandle.getCallingUserId(), list);
        }

        public final String getIncidentReportApproverPackageName() {
            return this.mService.mIncidentReportApproverPackage;
        }

        public final List getInitialNonStoppedSystemPackages() {
            return PackageManagerService.this.mInitialNonStoppedSystemPackages != null ? new ArrayList(PackageManagerService.this.mInitialNonStoppedSystemPackages) : new ArrayList();
        }

        public final int getInstallLocation() {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "default_install_location", 0);
        }

        public final int getInstallReason(String str, int i) {
            return this.mService.snapshotComputer().getInstallReason(str, i);
        }

        public final InstallSourceInfo getInstallSourceInfo(String str, int i) {
            return this.mService.snapshotComputer().getInstallSourceInfo(str, i);
        }

        public final ParceledListSlice getInstalledApplications(long j, int i) {
            return new ParceledListSlice(this.mService.snapshotComputer().getInstalledApplications(i, Binder.getCallingUid(), false, j));
        }

        public final List getInstalledModules(int i) {
            ModuleInfoProvider moduleInfoProvider = this.mModuleInfoProvider;
            if (!moduleInfoProvider.mMetadataLoaded) {
                throw new IllegalStateException("Call to getInstalledModules before metadata loaded");
            }
            if ((131072 & i) != 0) {
                return new ArrayList(((ArrayMap) moduleInfoProvider.mModuleInfo).values());
            }
            try {
                if (moduleInfoProvider.mPackageManager == null) {
                    moduleInfoProvider.mPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
                }
                List list = moduleInfoProvider.mPackageManager.getInstalledPackages(i | 1073741824, UserHandle.getCallingUserId()).getList();
                ArrayList arrayList = new ArrayList(list.size());
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ModuleInfo moduleInfo = (ModuleInfo) ((ArrayMap) moduleInfoProvider.mModuleInfo).get(((PackageInfo) it.next()).packageName);
                    if (moduleInfo != null) {
                        arrayList.add(moduleInfo);
                    }
                }
                return arrayList;
            } catch (RemoteException e) {
                Slog.w("PackageManager.ModuleInfoProvider", "Unable to retrieve all package names", e);
                return Collections.emptyList();
            }
        }

        public final ParceledListSlice getInstalledPackages(long j, int i) {
            return this.mService.snapshotComputer().getInstalledPackages(j, i);
        }

        public final String getInstallerPackageName(String str) {
            return this.mService.snapshotComputer().getInstallerPackageName(UserHandle.getCallingUserId(), str);
        }

        public final String getInstantAppAndroidId(String str, int i) {
            FileOutputStream fileOutputStream;
            getInstantAppAndroidId_enforcePermission();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("getInstantAppAndroidId", Binder.getCallingUid(), i, true, false);
            if (!snapshotComputer.isInstantApp(str, i)) {
                return null;
            }
            synchronized (PackageManagerService.this.mInstantAppRegistry.mLock) {
                try {
                    File file = new File(InstantAppRegistry.getInstantApplicationDir(i, str), "android_id");
                    if (file.exists()) {
                        try {
                            return IoUtils.readFileAsString(file.getAbsolutePath());
                        } catch (IOException e) {
                            Slog.e("InstantAppRegistry", "Failed to read instant app android id file: " + file, e);
                        }
                    }
                    byte[] bArr = new byte[8];
                    new SecureRandom().nextBytes(bArr);
                    String encodeToString = HexEncoding.encodeToString(bArr, false);
                    File instantApplicationDir = InstantAppRegistry.getInstantApplicationDir(i, str);
                    if (instantApplicationDir.exists() || instantApplicationDir.mkdirs()) {
                        File file2 = new File(InstantAppRegistry.getInstantApplicationDir(i, str), "android_id");
                        try {
                            fileOutputStream = new FileOutputStream(file2);
                        } catch (IOException e2) {
                            Slog.e("InstantAppRegistry", "Error writing instant app android id file: " + file2, e2);
                        }
                        try {
                            fileOutputStream.write(encodeToString.getBytes());
                            fileOutputStream.close();
                        } catch (Throwable th) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } else {
                        Slog.e("InstantAppRegistry", "Cannot create instant app cookie directory");
                    }
                    return encodeToString;
                } finally {
                }
            }
        }

        public final byte[] getInstantAppCookie(String str, int i) {
            PackageSetting packageStateInternal;
            AndroidPackageInternal androidPackageInternal;
            SomeArgs someArgs;
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("getInstantAppCookie", Binder.getCallingUid(), i, true, false);
            if (!snapshotComputer.isCallerSameApp(Binder.getCallingUid(), str) || (packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || (androidPackageInternal = packageStateInternal.pkg) == null) {
                return null;
            }
            InstantAppRegistry instantAppRegistry = PackageManagerService.this.mInstantAppRegistry;
            synchronized (instantAppRegistry.mLock) {
                try {
                    ArrayMap arrayMap = (ArrayMap) instantAppRegistry.mCookiePersistence.mPendingPersistCookies.get(i);
                    byte[] bArr = (arrayMap == null || (someArgs = (SomeArgs) arrayMap.get(androidPackageInternal.getPackageName())) == null) ? null : (byte[]) someArgs.arg1;
                    if (bArr != null) {
                        return bArr;
                    }
                    File peekInstantCookieFile = InstantAppRegistry.peekInstantCookieFile(i, androidPackageInternal.getPackageName());
                    if (peekInstantCookieFile != null && peekInstantCookieFile.exists()) {
                        try {
                            return IoUtils.readFileAsByteArray(peekInstantCookieFile.toString());
                        } catch (IOException unused) {
                            Slog.w("InstantAppRegistry", "Error reading cookie file: " + peekInstantCookieFile);
                        }
                    }
                    return null;
                } finally {
                }
            }
        }

        public final Bitmap getInstantAppIcon(String str, int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (!snapshotComputer.canViewInstantApps(Binder.getCallingUid(), i)) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getInstantAppIcon");
            }
            snapshotComputer.enforceCrossUserPermission("getInstantAppIcon", Binder.getCallingUid(), i, true, false);
            synchronized (PackageManagerService.this.mInstantAppRegistry.mLock) {
                try {
                    File file = new File(InstantAppRegistry.getInstantApplicationDir(i, str), "icon.png");
                    if (!file.exists()) {
                        return null;
                    }
                    return BitmapFactory.decodeFile(file.toString());
                } finally {
                }
            }
        }

        public final ComponentName getInstantAppInstallerComponent() {
            Computer snapshotComputer = this.mService.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
                return null;
            }
            return snapshotComputer.getInstantAppInstallerComponent();
        }

        public final ComponentName getInstantAppResolverComponent() {
            Computer snapshotComputer = this.mService.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
                return null;
            }
            return this.mService.getInstantAppResolver(snapshotComputer);
        }

        public final ComponentName getInstantAppResolverSettingsComponent() {
            return this.mInstantAppResolverSettingsComponent;
        }

        public final ParceledListSlice getInstantApps(int i) {
            List list;
            File[] listFiles;
            InstantAppRegistry.UninstalledInstantAppState parseMetadataFile;
            ArrayList arrayList;
            InstantAppInfo createInstantAppInfoForPackage;
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (!snapshotComputer.canViewInstantApps(Binder.getCallingUid(), i)) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getEphemeralApplications");
            }
            snapshotComputer.enforceCrossUserPermission("getEphemeralApplications", Binder.getCallingUid(), i, true, false);
            InstantAppRegistry instantAppRegistry = PackageManagerService.this.mInstantAppRegistry;
            instantAppRegistry.getClass();
            ArrayMap packageStates = snapshotComputer.getPackageStates();
            int size = packageStates.size();
            ArrayList arrayList2 = null;
            for (int i2 = 0; i2 < size; i2++) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i2);
                if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isInstantApp() && (createInstantAppInfoForPackage = instantAppRegistry.createInstantAppInfoForPackage(packageStateInternal, i, true)) != null) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(createInstantAppInfoForPackage);
                }
            }
            synchronized (instantAppRegistry.mLock) {
                try {
                    WatchedSparseArray watchedSparseArray = instantAppRegistry.mUninstalledInstantApps;
                    if (watchedSparseArray != null) {
                        list = (List) watchedSparseArray.mStorage.get(i);
                        if (list != null) {
                        }
                    } else {
                        list = null;
                    }
                    File file = new File(Environment.getUserSystemDirectory(i), "instant");
                    if (file.exists() && (listFiles = file.listFiles()) != null) {
                        for (File file2 : listFiles) {
                            if (file2.isDirectory() && (parseMetadataFile = InstantAppRegistry.parseMetadataFile(new File(file2, "metadata.xml"))) != null) {
                                if (list == null) {
                                    list = new ArrayList();
                                }
                                list.add(parseMetadataFile);
                            }
                        }
                    }
                    synchronized (instantAppRegistry.mLock) {
                        instantAppRegistry.mUninstalledInstantApps.put(i, list);
                    }
                } finally {
                }
            }
            if (list == null || list.isEmpty()) {
                arrayList = null;
            } else {
                int size2 = list.size();
                arrayList = null;
                for (int i3 = 0; i3 < size2; i3++) {
                    InstantAppRegistry.UninstalledInstantAppState uninstalledInstantAppState = (InstantAppRegistry.UninstalledInstantAppState) list.get(i3);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(uninstalledInstantAppState.mInstantAppInfo);
                }
            }
            if (arrayList2 == null) {
                arrayList2 = arrayList;
            } else if (arrayList != null) {
                arrayList2.addAll(arrayList);
            }
            if (arrayList2 != null) {
                return new ParceledListSlice(arrayList2);
            }
            return null;
        }

        public final InstrumentationInfo getInstrumentationInfoAsUser(ComponentName componentName, int i, int i2) {
            return this.mService.snapshotComputer().getInstrumentationInfoAsUser(componentName, i, i2);
        }

        public final ParceledListSlice getIntentFilterVerifications(String str) {
            return ParceledListSlice.emptyList();
        }

        public final int getIntentVerificationStatus(String str, int i) {
            DomainVerificationService domainVerificationService = (DomainVerificationService) this.mDomainVerificationManager;
            domainVerificationService.mConnection.getClass();
            int callingUid = Binder.getCallingUid();
            domainVerificationService.mConnection.getClass();
            int callingUserId = UserHandle.getCallingUserId();
            DomainVerificationEnforcer domainVerificationEnforcer = domainVerificationService.mEnforcer;
            if (callingUserId != i) {
                domainVerificationEnforcer.mContext.enforcePermission("android.permission.INTERACT_ACROSS_USERS_FULL", Binder.getCallingPid(), callingUid, "Caller is not allowed to edit other users");
            }
            if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(callingUserId)) {
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUserId, "User ", " does not exist"));
            }
            if (!domainVerificationEnforcer.mCallback.mUmInternal.exists(i)) {
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " does not exist"));
            }
            if (!domainVerificationEnforcer.mCallback.mPm.snapshotComputer().filterAppAccess(callingUid, i, str, true)) {
                return domainVerificationService.mLegacySettings.getUserState(i, str);
            }
            return 0;
        }

        public final KeySet getKeySetByAlias(String str, String str2) {
            return this.mService.snapshotComputer().getKeySetByAlias(str, str2);
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.content.pm.ResolveInfo getLastChosenActivity(android.content.Intent r23, java.lang.String r24, int r25) {
            /*
                Method dump skipped, instructions count: 305
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.getLastChosenActivity(android.content.Intent, java.lang.String, int):android.content.pm.ResolveInfo");
        }

        public final IntentSender getLaunchIntentSenderForPackage(String str, String str2, String str3, int i) {
            Intent intent;
            PackageManagerService packageManagerService = PackageManagerService.this;
            ResolveIntentHelper resolveIntentHelper = packageManagerService.mResolveIntentHelper;
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            resolveIntentHelper.getClass();
            Objects.requireNonNull(str);
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            snapshotComputer.enforceCrossUserPermission("get launch intent sender for package", callingUid, i, false, false);
            if (!UserHandle.isSameApp(callingUid, snapshotComputer.getPackageUid(str2, 0L, i))) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "getLaunchIntentSenderForPackage() from calling uid: ", " does not own package: ", str2));
            }
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.INFO");
            intent2.setPackage(str);
            ContentResolver contentResolver = resolveIntentHelper.mContext.getContentResolver();
            String resolveTypeIfNeeded = intent2.resolveTypeIfNeeded(contentResolver);
            List queryIntentActivitiesInternal = snapshotComputer.queryIntentActivitiesInternal(intent2, resolveTypeIfNeeded, 0L, callingUid, callingPid, i, true, false);
            if (queryIntentActivitiesInternal == null || queryIntentActivitiesInternal.size() <= 0) {
                intent2.removeCategory("android.intent.category.INFO");
                intent2.addCategory("android.intent.category.LAUNCHER");
                intent2.setPackage(str);
                resolveTypeIfNeeded = intent2.resolveTypeIfNeeded(contentResolver);
                intent = intent2;
                queryIntentActivitiesInternal = snapshotComputer.queryIntentActivitiesInternal(intent2, resolveTypeIfNeeded, 0L, callingUid, callingPid, i, true, false);
            } else {
                intent = intent2;
            }
            Intent intent3 = new Intent(intent);
            intent3.setFlags(268435456);
            if (queryIntentActivitiesInternal != null && !queryIntentActivitiesInternal.isEmpty()) {
                intent3.setPackage(null);
                intent3.setClassName(((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.packageName, ((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.name);
            }
            return new IntentSender(ActivityManager.getService().getIntentSenderWithFeature(2, str2, str3, (IBinder) null, (String) null, 1, new Intent[]{intent3}, resolveTypeIfNeeded != null ? new String[]{resolveTypeIfNeeded} : null, 67108864, (Bundle) null, i));
        }

        public final boolean getMetadataForIconTray(String str, String str2, int i, List list) {
            Bundle metaData;
            if (PackageManagerService.this.mUserManager.mLocalService.exists(i) && str != null && str2 != null && list != null) {
                Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
                snapshotComputer.enforceCrossUserPermission("get application info", Binder.getCallingUid(), i, false, false);
                int callingUid = Binder.getCallingUid();
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null || packageStateInternal.pkg == null || snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i) || (metaData = packageStateInternal.pkg.getMetaData()) == null) {
                    return false;
                }
                boolean z = metaData.getBoolean(str2);
                CharSequence charSequence = metaData.getCharSequence("com.samsung.android.icon_container.feature_appicon");
                if (charSequence != null) {
                    for (String str3 : charSequence.toString().split("\\|")) {
                        list.add(str3.trim());
                    }
                }
                return z;
            }
            return false;
        }

        public final List getMimeGroup(String str, String str2) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService packageManagerService = PackageManagerService.this;
            int callingUid = Binder.getCallingUid();
            packageManagerService.getClass();
            PackageManagerService.enforceOwnerRights(snapshotComputer, str, callingUid);
            PackageManagerService.this.getClass();
            PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return Collections.emptyList();
            }
            Map mimeGroups = packageStateInternal.getMimeGroups();
            Set set = mimeGroups != null ? (Set) mimeGroups.get(str2) : null;
            if (set != null) {
                return new ArrayList(set);
            }
            throw new IllegalArgumentException(BootReceiver$$ExternalSyntheticOutline0.m("Unknown MIME group ", str2, " for package ", str));
        }

        public final ModuleInfo getModuleInfo(String str, int i) {
            return this.mModuleInfoProvider.getModuleInfo(str, i);
        }

        public final int getMoveStatus(int i) {
            getMoveStatus_enforcePermission();
            return PackageManagerService.this.mMoveCallbacks.mLastStatus.get(i);
        }

        public final String getNameForUid(int i) {
            return this.mService.snapshotComputer().getNameForUid(i);
        }

        public final String[] getNamesForUids(int[] iArr) {
            return this.mService.snapshotComputer().getNamesForUids(iArr);
        }

        public final int[] getPackageGids(String str, long j, int i) {
            return this.mService.snapshotComputer().getPackageGids(str, j, i);
        }

        public final List getPackageGrantedPermissionsForMDM(String str) {
            int callingUid = Binder.getCallingUid();
            if (callingUid == 0 || callingUid == 1000) {
                return PermissionManagerService.this.mPermissionManagerServiceImpl.getPackageGrantedPermissionsForMDM(str);
            }
            throw new SecurityException("getPackageGrantedPermissionsForMDM can only be called by the system");
        }

        public final PackageInfo getPackageInfo(String str, long j, int i) {
            return this.mService.snapshotComputer().getPackageInfo(str, j, i);
        }

        public final PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, long j, int i) {
            return this.mService.snapshotComputer().getPackageInfoInternal(Binder.getCallingUid(), i, versionedPackage.getPackageName(), versionedPackage.getLongVersionCode(), j);
        }

        public final IPackageInstaller getPackageInstaller() {
            boolean z = PackageManagerServiceUtils.DEBUG;
            if (!PackageManagerServiceUtils.isSystemOrRoot(Binder.getCallingUid()) && this.mService.snapshotComputer().getInstantAppPackageName(Binder.getCallingUid()) != null) {
                Log.w("PackageManager", "Returning null PackageInstaller for InstantApps");
                return null;
            }
            return this.mInstallerService;
        }

        /* JADX WARN: Code restructure failed: missing block: B:84:0x010d, code lost:
        
            return r3;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getPackageListForDualDarPolicy(java.lang.String r8) {
            /*
                Method dump skipped, instructions count: 300
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.getPackageListForDualDarPolicy(java.lang.String):java.util.List");
        }

        public final void getPackageSizeInfo(String str, int i, IPackageStatsObserver iPackageStatsObserver) {
            throw new UnsupportedOperationException("Shame on you for calling the hidden API getPackageSizeInfo(). Shame!");
        }

        public final int getPackageUid(String str, long j, int i) {
            return this.mService.snapshotComputer().getPackageUid(str, j, i);
        }

        public final String[] getPackagesForUid(int i) {
            this.mService.snapshotComputer().enforceCrossUserOrProfilePermission(Binder.getCallingUid(), UserHandle.getUserId(i), "getPackagesForUid");
            return this.mService.snapshotComputer().getPackagesForUid(i);
        }

        public final ParceledListSlice getPackagesHoldingPermissions(String[] strArr, long j, int i) {
            return this.mService.snapshotComputer().getPackagesHoldingPermissions(strArr, j, i);
        }

        public final String getPermissionControllerPackageName() {
            int callingUid = Binder.getCallingUid();
            if (PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(callingUid, UserHandle.getUserId(callingUid), PackageManagerService.this.mRequiredPermissionControllerPackage) != null) {
                return PackageManagerService.this.mRequiredPermissionControllerPackage;
            }
            throw new IllegalStateException("PermissionController is not found");
        }

        public final PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
            return ((PermissionManager) this.mService.mContext.getSystemService(PermissionManager.class)).getPermissionGroupInfo(str, i);
        }

        public final ParceledListSlice getPersistentApplications(int i) {
            Computer snapshotComputer = this.mService.snapshotComputer();
            return snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null ? ParceledListSlice.emptyList() : new ParceledListSlice(snapshotComputer.getPersistentApplications(i, this.mService.mSafeMode));
        }

        public final int getPreferredActivities(List list, List list2, String str) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            preferredActivityHelper.getClass();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(new WatchedIntentFilter((IntentFilter) list.get(i)));
            }
            int callingUid = Binder.getCallingUid();
            if (snapshotComputer.getInstantAppPackageName(callingUid) == null) {
                int callingUserId = UserHandle.getCallingUserId();
                PreferredIntentResolver preferredActivities = snapshotComputer.getPreferredActivities(callingUserId);
                if (preferredActivities != null) {
                    IntentResolver.IteratorWrapper filterIterator = preferredActivities.filterIterator();
                    while (filterIterator.mI.hasNext()) {
                        PreferredActivity preferredActivity = (PreferredActivity) filterIterator.next();
                        if (preferredActivity != null) {
                            PreferredComponent preferredComponent = preferredActivity.mPref;
                            String packageName = preferredComponent.mComponent.getPackageName();
                            if (str == null || (packageName.equals(str) && preferredComponent.mAlways)) {
                                if (!snapshotComputer.shouldFilterApplication(snapshotComputer.getPackageStateInternal(packageName), callingUid, callingUserId)) {
                                    arrayList.add(new WatchedIntentFilter(preferredActivity.mFilter));
                                    if (list2 != null) {
                                        list2.add(preferredComponent.mComponent);
                                    }
                                }
                            }
                        }
                    }
                }
                IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (asInterface != null) {
                    try {
                        for (DefaultAppConfiguration defaultAppConfiguration : asInterface.getAllDefaultApplicationsInternal(callingUserId)) {
                            if (str != null && !defaultAppConfiguration.getComponentName().getPackageName().equals(str)) {
                            }
                            arrayList.add(new WatchedIntentFilter(asInterface.createIntentFilter(defaultAppConfiguration.getTaskType())));
                            if (list2 != null) {
                                list2.add(defaultAppConfiguration.getComponentName());
                            }
                        }
                    } catch (RemoteException unused) {
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            list.clear();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                list.add(((WatchedIntentFilter) arrayList.get(i2)).getIntentFilter$3());
            }
            return 0;
        }

        public final byte[] getPreferredActivityBackup(int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.getClass();
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call getPreferredActivityBackup()");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                newFastSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                newFastSerializer.startDocument((String) null, Boolean.TRUE);
                newFastSerializer.startTag((String) null, "pa");
                PackageManagerTracedLock packageManagerTracedLock = preferredActivityHelper.mPm.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        preferredActivityHelper.mPm.mSettings.writePreferredActivitiesLPr(i, newFastSerializer, true);
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                newFastSerializer.endTag((String) null, "pa");
                newFastSerializer.endDocument();
                newFastSerializer.flush();
                return byteArrayOutputStream.toByteArray();
            } catch (Exception unused) {
                return null;
            }
        }

        public final int getPrivateFlagsForUid(int i) {
            return this.mService.snapshotComputer().getPrivateFlagsForUid(i);
        }

        public final PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) {
            ArrayMap arrayMap;
            ArrayMap arrayMap2;
            ArrayMap arrayMap3;
            List list;
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = this.mService.snapshotComputer();
            snapshotComputer.enforceCrossUserOrProfilePermission(callingUid, i, "getPropertyAsUser");
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, str2) == null) {
                return null;
            }
            PackageProperty packageProperty = this.mPackageProperty;
            if (str3 == null) {
                ArrayMap arrayMap4 = packageProperty.mApplicationProperties;
                ArrayMap arrayMap5 = arrayMap4 != null ? (ArrayMap) arrayMap4.get(str) : null;
                if (arrayMap5 == null || (list = (List) arrayMap5.get(str2)) == null) {
                    return null;
                }
                return (PackageManager.Property) list.get(0);
            }
            ArrayMap arrayMap6 = packageProperty.mActivityProperties;
            PackageManager.Property property = arrayMap6 != null ? PackageProperty.getProperty(str, str2, str3, arrayMap6) : null;
            if (property == null && (arrayMap3 = packageProperty.mProviderProperties) != null) {
                property = PackageProperty.getProperty(str, str2, str3, arrayMap3);
            }
            if (property == null && (arrayMap2 = packageProperty.mReceiverProperties) != null) {
                property = PackageProperty.getProperty(str, str2, str3, arrayMap2);
            }
            return (property != null || (arrayMap = packageProperty.mServiceProperties) == null) ? property : PackageProperty.getProperty(str, str2, str3, arrayMap);
        }

        public final ProviderInfo getProviderInfo(ComponentName componentName, long j, int i) {
            return this.mService.snapshotComputer().getProviderInfo(componentName, j, i);
        }

        public final ActivityInfo getReceiverInfo(ComponentName componentName, long j, int i) {
            return this.mService.snapshotComputer().getReceiverInfo(componentName, j, i);
        }

        public final List getRequestedRuntimePermissionsForMDM(String str) {
            if (Binder.getCallingPid() != Process.myPid()) {
                throw new SecurityException();
            }
            if (str == null) {
                return null;
            }
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getRequestedRuntimePermissionsForMDM(str);
        }

        public final String getRotationResolverPackageName() {
            return PackageManagerService.ensureSystemPackageName(this.mService.snapshotComputer(), this.mService.getPackageFromComponentString(R.string.deleted_key));
        }

        public final int getRuntimePermissionsVersion(int i) {
            int i2;
            Preconditions.checkArgumentNonnegative(i);
            PackageManagerService.m757x2d683684(PackageManagerService.this, "getRuntimePermissionVersion");
            Settings.RuntimePermissionPersistence runtimePermissionPersistence = PackageManagerService.this.mSettings.mRuntimePermissionsPersistence;
            synchronized (runtimePermissionPersistence.mLock) {
                i2 = runtimePermissionPersistence.mVersions.get(i, 0);
            }
            return i2;
        }

        public final String getSdkSandboxPackageName() {
            return this.mService.mRequiredSdkSandboxPackage;
        }

        public final ServiceInfo getServiceInfo(ComponentName componentName, long j, int i) {
            return this.mService.snapshotComputer().getServiceInfo(componentName, j, i);
        }

        public final String getServicesSystemSharedLibraryPackageName() {
            return this.mServicesExtensionPackageName;
        }

        public final String getSetupWizardPackageName() {
            if (Binder.getCallingUid() == 1000) {
                return this.mService.mSetupWizardPackage;
            }
            throw new SecurityException("Non-system caller");
        }

        public final ParceledListSlice getSharedLibraries(String str, long j, int i) {
            return this.mService.snapshotComputer().getSharedLibraries(str, j, i);
        }

        public final String getSharedSystemSharedLibraryPackageName() {
            return this.mSharedSystemSharedLibraryPackageName;
        }

        public final KeySet getSigningKeySet(String str) {
            return this.mService.snapshotComputer().getSigningKeySet(str);
        }

        public final String getSplashScreenTheme(String str, int i) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            snapshotComputer.enforceCrossUserPermission("getSplashScreenTheme", callingUid, i, false, false);
            PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, str);
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            return packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getSplashScreenTheme();
        }

        public final Bundle getSuspendedPackageAppExtras(String str, int i) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = this.mService.snapshotComputer();
            if (snapshotComputer.getPackageUid(str, 0L, i) == callingUid) {
                return SuspendPackageHelper.getSuspendedPackageAppExtras(i, callingUid, snapshotComputer, str);
            }
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUid, "Calling package ", str, " does not belong to calling uid "));
        }

        public final String getSuspendingPackage(String str, int i) {
            try {
                int callingUid = Binder.getCallingUid();
                Computer snapshotComputer = this.mService.snapshotComputer();
                if (!snapshotComputer.isPackageSuspendedForUser(str, i)) {
                    return null;
                }
                PackageManagerService.this.mSuspendPackageHelper.getClass();
                UserPackage suspendingPackage = SuspendPackageHelper.getSuspendingPackage(i, callingUid, snapshotComputer, str);
                if (suspendingPackage != null) {
                    return suspendingPackage.packageName;
                }
                return null;
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }

        public final ParceledListSlice getSystemAvailableFeatures() {
            ArrayList arrayList = new ArrayList(PackageManagerService.this.mAvailableFeatures.size() + 1);
            arrayList.addAll(PackageManagerService.this.mAvailableFeatures.values());
            FeatureInfo featureInfo = new FeatureInfo();
            featureInfo.reqGlEsVersion = SystemProperties.getInt("ro.opengles.version", 0);
            arrayList.add(featureInfo);
            return new ParceledListSlice(arrayList);
        }

        public final String getSystemCaptionsServicePackageName() {
            return PackageManagerService.ensureSystemPackageName(this.mService.snapshotComputer(), this.mService.getPackageFromComponentString(R.string.deprecated_target_sdk_message));
        }

        public final String[] getSystemSharedLibraryNames() {
            ArrayMap systemSharedLibraryNamesAndPaths = this.mService.snapshotComputer().getSystemSharedLibraryNamesAndPaths();
            if (systemSharedLibraryNamesAndPaths.isEmpty()) {
                return null;
            }
            int size = systemSharedLibraryNamesAndPaths.size();
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = (String) systemSharedLibraryNamesAndPaths.keyAt(i);
            }
            return strArr;
        }

        public final Map getSystemSharedLibraryNamesAndPaths() {
            return this.mService.snapshotComputer().getSystemSharedLibraryNamesAndPaths();
        }

        public final String getSystemTextClassifierPackageName() {
            return this.mService.mSystemTextClassifierPackageName;
        }

        public final int getTargetSdkVersion(String str) {
            return this.mService.snapshotComputer().getTargetSdkVersion(str);
        }

        public final int getUidForSharedUser(String str) {
            return this.mService.snapshotComputer().getUidForSharedUser(str);
        }

        public final ParceledListSlice getUnknownSourcePackagesAsUser(long j, int i) {
            boolean contains;
            ArrayList arrayList = new ArrayList();
            UnknownSourceAppManager unknownSourceAppManager = PackageManagerService.this.mCustomInjector.getUnknownSourceAppManager();
            if (unknownSourceAppManager == null) {
                return new ParceledListSlice(arrayList);
            }
            for (PackageInfo packageInfo : this.mService.snapshotComputer().getInstalledPackages(j, i).getList()) {
                String str = packageInfo.packageName;
                synchronized (unknownSourceAppManager.mUnknownLock) {
                    contains = unknownSourceAppManager.mUnknownSourceAppSet.contains(str);
                }
                if (contains) {
                    arrayList.add(packageInfo);
                }
            }
            return new ParceledListSlice(arrayList);
        }

        public final String[] getUnsuspendablePackagesForUser(String[] strArr, int i) {
            Objects.requireNonNull(strArr, "packageNames cannot be null");
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "getUnsuspendablePackagesForUser");
            int callingUid = Binder.getCallingUid();
            if (UserHandle.getUserId(callingUid) != i) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, i, "Calling uid ", " cannot query getUnsuspendablePackagesForUser for user "));
            }
            PackageManagerService packageManagerService = PackageManagerService.this;
            SuspendPackageHelper suspendPackageHelper = packageManagerService.mSuspendPackageHelper;
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            if (!suspendPackageHelper.isSuspendAllowedForUser(snapshotComputer, i, callingUid)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Cannot suspend due to restrictions on user ", "PackageManager");
                return strArr;
            }
            ArraySet arraySet = new ArraySet();
            boolean[] canSuspendPackageForUser = suspendPackageHelper.canSuspendPackageForUser(snapshotComputer, strArr, i, callingUid);
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (!canSuspendPackageForUser[i2]) {
                    arraySet.add(strArr[i2]);
                } else if (snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, strArr[i2]) == null) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Could not find package setting for package: "), strArr[i2], "PackageManager");
                    arraySet.add(strArr[i2]);
                }
            }
            return (String[]) arraySet.toArray(new String[arraySet.size()]);
        }

        public final int getUserMinAspectRatio(String str, int i) {
            PackageSetting packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(Binder.getCallingUid(), i, str);
            if (packageStateForInstalledAndFiltered == null) {
                return 0;
            }
            return packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getMinAspectRatio();
        }

        public final VerifierDeviceIdentity getVerifierDeviceIdentity() {
            VerifierDeviceIdentity verifierDeviceIdentity;
            getVerifierDeviceIdentity_enforcePermission();
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    PackageManagerService packageManagerService = PackageManagerService.this;
                    Settings settings = packageManagerService.mSettings;
                    ComputerLocked computerLocked = packageManagerService.mLiveComputer;
                    if (settings.mVerifierDeviceIdentity == null) {
                        settings.mVerifierDeviceIdentity = VerifierDeviceIdentity.generate();
                        settings.writeLPr(computerLocked, false);
                    }
                    verifierDeviceIdentity = settings.mVerifierDeviceIdentity;
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            return verifierDeviceIdentity;
        }

        public final String getWellbeingPackageName() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return (String) CollectionUtils.firstOrNull(((RoleManager) this.mContext.getSystemService(RoleManager.class)).getRoleHolders("android.app.role.SYSTEM_WELLBEING"));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void grantRuntimePermission(String str, String str2, int i) {
            ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).grantRuntimePermission(str, str2, UserHandle.of(i));
        }

        public final boolean hasSigningCertificate(String str, byte[] bArr, int i) {
            return this.mService.snapshotComputer().hasSigningCertificate(str, bArr, i);
        }

        public final boolean hasSystemFeature(String str, int i) {
            return this.mService.hasSystemFeature(str, i);
        }

        public final boolean hasSystemUidErrors() {
            return false;
        }

        public final boolean hasUidSigningCertificate(int i, byte[] bArr, int i2) {
            return this.mService.snapshotComputer().hasUidSigningCertificate(i, bArr, i2);
        }

        public final void holdLock(IBinder iBinder, int i) {
            ((PackageManagerService) PackageManagerService.this.mTestUtilityService).verifyHoldLockToken(iBinder);
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    SystemClock.sleep(i);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }

        public final int installExistingPackageAsUser(String str, int i, int i2, int i3, List list) {
            return ((Integer) PackageManagerService.this.mInstallPackageHelper.installExistingPackageAsUser(str, i, i2, i3, null).first).intValue();
        }

        public final boolean isAppArchivable(String str, UserHandle userHandle) {
            PackageArchiver packageArchiver = PackageManagerService.this.mInstallerService.mPackageArchiver;
            packageArchiver.getClass();
            Objects.requireNonNull(str);
            Objects.requireNonNull(userHandle);
            PackageManagerService packageManagerService = packageArchiver.mPm;
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            int identifier = userHandle.getIdentifier();
            snapshotComputer.enforceCrossUserPermission("isAppArchivable", Binder.getCallingUid(), identifier, true, true);
            try {
                PackageStateInternal packageState = PackageArchiver.getPackageState(Binder.getCallingUid(), identifier, packageManagerService.snapshotComputer(), str);
                int i = ((SettingBase) packageState).mPkgFlags;
                if ((i & 1) != 0 || (i & 128) != 0 || ((Boolean) Binder.withCleanCallingIdentity(new PackageArchiver$$ExternalSyntheticLambda0(packageArchiver, UserHandle.getUid(identifier, ((PackageSetting) packageState).mAppId), str))).booleanValue()) {
                    return false;
                }
                try {
                    packageArchiver.verifyInstaller(snapshotComputer, PackageArchiver.getResponsibleInstallerPackage(((PackageSetting) packageState).installSource), identifier);
                    packageArchiver.getLauncherActivityInfos(identifier, str);
                    return !packageArchiver.isProtectivePackage(packageState, str, identifier);
                } catch (PackageManager.NameNotFoundException unused) {
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                throw new ParcelableException(e);
            }
        }

        public final boolean isAutoRevokeWhitelisted(String str) {
            return ((AppOpsManager) PackageManagerService.this.mInjector.mGetSystemServiceProducer.produce(AppOpsManager.class)).checkOpNoThrow(97, Binder.getCallingUid(), str) == 1;
        }

        public final boolean isDeviceUpgrading() {
            return this.mService.isDeviceUpgrading();
        }

        public final boolean isFirstBoot() {
            return this.mService.mFirstBoot;
        }

        public final boolean isInstantApp(String str, int i) {
            return this.mService.snapshotComputer().isInstantApp(str, i);
        }

        public final boolean isPackageAutoDisabled(String str, int i) {
            PackageSetting packageStateInternal;
            int userId = UserHandle.getUserId(i);
            if (str != null && (packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str)) != null) {
                PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(userId);
                if (userStateOrDefault.getEnabledState() == 4 && "auto_disabler".equals(userStateOrDefault.getLastDisableAppCaller())) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isPackageAvailable(String str, int i) {
            return this.mService.snapshotComputer().isPackageAvailable(str, i);
        }

        public final boolean isPackageDeviceAdminOnAnyUser(String str) {
            PackageManagerService packageManagerService = this.mService;
            return packageManagerService.isPackageDeviceAdminOnAnyUser(packageManagerService.snapshotComputer(), str);
        }

        public final boolean isPackageQuarantinedForUser(String str, int i) {
            try {
                return this.mService.snapshotComputer().isPackageQuarantinedForUser(str, i);
            } catch (PackageManager.NameNotFoundException unused) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown target package: ", str));
            }
        }

        public final boolean isPackageSignedByKeySet(String str, KeySet keySet) {
            return this.mService.snapshotComputer().isPackageSignedByKeySet(str, keySet);
        }

        public final boolean isPackageSignedByKeySetExactly(String str, KeySet keySet) {
            return this.mService.snapshotComputer().isPackageSignedByKeySetExactly(str, keySet);
        }

        public final boolean isPackageStateProtected(String str, int i) {
            int callingUid = Binder.getCallingUid();
            int appId = UserHandle.getAppId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("isPackageStateProtected", callingUid, i, false, true);
            if (PackageManagerServiceUtils.isSystemOrRoot(appId) || snapshotComputer.checkUidPermission("android.permission.MANAGE_DEVICE_ADMINS", callingUid) == 0) {
                return PackageManagerService.this.mProtectedPackages.isPackageStateProtected(i, str);
            }
            throw new SecurityException("Caller must have the android.permission.MANAGE_DEVICE_ADMINS permission.");
        }

        public final boolean isPackageStoppedForUser(String str, int i) {
            try {
                return this.mService.snapshotComputer().isPackageStoppedForUser(str, i);
            } catch (PackageManager.NameNotFoundException unused) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown target package: ", str));
            }
        }

        public final boolean isPackageSuspendedForUser(String str, int i) {
            try {
                return this.mService.snapshotComputer().isPackageSuspendedForUser(str, i);
            } catch (PackageManager.NameNotFoundException unused) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown target package: ", str));
            }
        }

        public final boolean isProtectedBroadcast(String str) {
            boolean contains;
            if (str != null && (str.startsWith("android.net.netmon.lingerExpired") || str.startsWith("com.android.server.sip.SipWakeupTimer") || str.startsWith("com.android.internal.telephony.data-reconnect") || str.startsWith("android.net.netmon.launchCaptivePortalApp"))) {
                return true;
            }
            synchronized (PackageManagerService.this.mProtectedBroadcasts) {
                contains = PackageManagerService.this.mProtectedBroadcasts.contains(str);
            }
            return contains;
        }

        public final boolean isSafeMode() {
            return this.mService.mSafeMode;
        }

        public final boolean isStorageLow() {
            PackageManagerService packageManagerService = this.mService;
            packageManagerService.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStorageMonitorService.AnonymousClass2 anonymousClass2 = (DeviceStorageMonitorService.AnonymousClass2) packageManagerService.mInjector.mGetLocalServiceProducer.produce(DeviceStorageMonitorService.AnonymousClass2.class);
                if (anonymousClass2 != null) {
                    return anonymousClass2.isMemoryLow();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isSystemCompressedPackage(String str, int i) {
            PackageSetting disabledSystemPackage;
            Objects.requireNonNull(str);
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = this.mService.snapshotComputer();
            PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, str);
            return packageStateForInstalledAndFiltered != null && (disabledSystemPackage = snapshotComputer.getDisabledSystemPackage(str)) != null && disabledSystemPackage.pkg.isStub() && packageStateForInstalledAndFiltered.pkg.getLongVersionCode() == disabledSystemPackage.pkg.getLongVersionCode();
        }

        public final boolean isUidPrivileged(int i) {
            return this.mService.snapshotComputer().isUidPrivileged(i);
        }

        public final boolean isUnknownSourcePackage(String str) {
            boolean contains;
            UnknownSourceAppManager unknownSourceAppManager = PackageManagerService.this.mCustomInjector.getUnknownSourceAppManager();
            if (unknownSourceAppManager == null) {
                return false;
            }
            synchronized (unknownSourceAppManager.mUnknownLock) {
                contains = unknownSourceAppManager.mUnknownSourceAppSet.contains(str);
            }
            return contains && this.mService.snapshotComputer().getPackageStateInternal(str) != null;
        }

        public final void logAppProcessStartIfNeeded(String str, String str2, int i, String str3, String str4, int i2) {
            boolean z;
            final ProcessLoggingHandler.LoggingInfo loggingInfo;
            if (PackageManagerService.this.snapshotComputer().getInstantAppPackageName(Binder.getCallingUid()) == null && SecurityLog.isLoggingEnabled()) {
                PackageManagerService packageManagerService = PackageManagerService.this;
                final ProcessLoggingHandler processLoggingHandler = packageManagerService.mProcessLoggingHandler;
                Context context = packageManagerService.mContext;
                PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                processLoggingHandler.getClass();
                final Bundle bundle = new Bundle();
                bundle.putLong("startTimestamp", System.currentTimeMillis());
                bundle.putString("processName", str2);
                bundle.putInt("uid", i);
                bundle.putString("seinfo", str3);
                bundle.putInt("pid", i2);
                if (str4 == null) {
                    final String str5 = "No APK";
                    processLoggingHandler.post(new Runnable() { // from class: com.android.server.pm.ProcessLoggingHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ProcessLoggingHandler processLoggingHandler2 = ProcessLoggingHandler.this;
                            Bundle bundle2 = bundle;
                            String str6 = str5;
                            processLoggingHandler2.getClass();
                            ProcessLoggingHandler.logSecurityLogEvent(bundle2, str6);
                        }
                    });
                    return;
                }
                synchronized (processLoggingHandler.mLoggingInfo) {
                    try {
                        ProcessLoggingHandler.LoggingInfo loggingInfo2 = (ProcessLoggingHandler.LoggingInfo) processLoggingHandler.mLoggingInfo.get(str4);
                        z = loggingInfo2 == null;
                        if (z) {
                            loggingInfo2 = new ProcessLoggingHandler.LoggingInfo();
                            loggingInfo2.apkHash = null;
                            loggingInfo2.pendingLogEntries = new ArrayList();
                            processLoggingHandler.mLoggingInfo.put(str4, loggingInfo2);
                        }
                        loggingInfo = loggingInfo2;
                    } finally {
                    }
                }
                synchronized (loggingInfo) {
                    try {
                        if (TextUtils.isEmpty(loggingInfo.apkHash)) {
                            ((ArrayList) loggingInfo.pendingLogEntries).add(bundle);
                            if (z) {
                                try {
                                    IOnChecksumsReadyListener anonymousClass1 = new IOnChecksumsReadyListener.Stub() { // from class: com.android.server.pm.ProcessLoggingHandler.1
                                        public final /* synthetic */ LoggingInfo val$loggingInfo;

                                        public AnonymousClass1(final LoggingInfo loggingInfo3) {
                                            r2 = loggingInfo3;
                                        }

                                        public final void onChecksumsReady(List list) {
                                            ProcessLoggingHandler processLoggingHandler2 = ProcessLoggingHandler.this;
                                            LoggingInfo loggingInfo3 = r2;
                                            processLoggingHandler2.getClass();
                                            int size = list.size();
                                            for (int i3 = 0; i3 < size; i3++) {
                                                ApkChecksum apkChecksum = (ApkChecksum) list.get(i3);
                                                if (apkChecksum.getType() == 8) {
                                                    ProcessLoggingHandler.processChecksum(loggingInfo3, apkChecksum.getValue());
                                                    return;
                                                }
                                            }
                                            Slog.e("ProcessLoggingHandler", "requestChecksums() failed to return SHA256, see logs for details.");
                                            ProcessLoggingHandler.processChecksum(loggingInfo3, null);
                                        }
                                    };
                                    int userId = context.getUserId();
                                    Executor executor = processLoggingHandler.mExecutor;
                                    PackageManagerService packageManagerService2 = ((PackageManagerInternalImpl) packageManagerInternal).mService;
                                    packageManagerService2.requestChecksumsInternal(packageManagerService2.snapshotComputer(), str, false, 0, 8, null, anonymousClass1, userId, executor, processLoggingHandler);
                                } catch (Throwable th) {
                                    Slog.e("ProcessLoggingHandler", "requestChecksums() failed", th);
                                    processLoggingHandler.post(new Runnable() { // from class: com.android.server.pm.ProcessLoggingHandler$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ProcessLoggingHandler processLoggingHandler2 = ProcessLoggingHandler.this;
                                            ProcessLoggingHandler.LoggingInfo loggingInfo3 = loggingInfo3;
                                            processLoggingHandler2.getClass();
                                            ProcessLoggingHandler.processChecksum(loggingInfo3, null);
                                        }
                                    });
                                }
                            }
                        } else {
                            final String str6 = loggingInfo3.apkHash;
                            processLoggingHandler.post(new Runnable() { // from class: com.android.server.pm.ProcessLoggingHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ProcessLoggingHandler processLoggingHandler2 = ProcessLoggingHandler.this;
                                    Bundle bundle2 = bundle;
                                    String str62 = str6;
                                    processLoggingHandler2.getClass();
                                    ProcessLoggingHandler.logSecurityLogEvent(bundle2, str62);
                                }
                            });
                        }
                    } finally {
                    }
                }
            }
        }

        public final void makeProviderVisible(int i, String str) {
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            int userId = UserHandle.getUserId(i);
            ProviderInfo grantImplicitAccessProviderInfo = snapshotComputer.getGrantImplicitAccessProviderInfo(i, str);
            if (grantImplicitAccessProviderInfo == null) {
                return;
            }
            PackageManagerService.this.grantImplicitAccess(snapshotComputer, userId, null, UserHandle.getAppId(i), grantImplicitAccessProviderInfo.applicationInfo.uid, false, false);
        }

        public final void makeUidVisible(int i, int i2) {
            makeUidVisible_enforcePermission();
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(i);
            int userId2 = UserHandle.getUserId(i2);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("makeUidVisible", callingUid, userId, false, false);
            snapshotComputer.enforceCrossUserPermission("makeUidVisible", callingUid, userId2, false, false);
            snapshotComputer.enforceCrossUserPermission("makeUidVisible", i, userId2, false, false);
            PackageManagerService.this.grantImplicitAccess(snapshotComputer, userId, null, UserHandle.getAppId(i), i2, false, false);
        }

        public final int movePackage(final String str, final String str2) {
            movePackage_enforcePermission();
            Log.d("PackageManager", XmlUtils$$ExternalSyntheticOutline0.m("START MOVE PACKAGE: pkg{", str, "}\nvolumeUuid{", str2, "}\n"));
            final int callingUid = Binder.getCallingUid();
            final UserHandle userHandle = new UserHandle(UserHandle.getUserId(callingUid));
            final int andIncrement = PackageManagerService.this.mNextMoveId.getAndIncrement();
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                    String str3 = str;
                    String str4 = str2;
                    int i = andIncrement;
                    int i2 = callingUid;
                    UserHandle userHandle2 = userHandle;
                    iPackageManagerImpl.getClass();
                    try {
                        new MovePackageHelper(PackageManagerService.this).movePackageInternal(i, i2, userHandle2, str3, str4);
                    } catch (PackageManagerException e) {
                        Slog.w("PackageManager", "Failed to move " + str3, e);
                        PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(i, e.error);
                    }
                }
            });
            return andIncrement;
        }

        public final int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) {
            movePackageToSd_enforcePermission();
            PackageManagerService packageManagerService = PackageManagerService.this;
            MovePackageHelper movePackageHelper = new MovePackageHelper(packageManagerService);
            Map map = MovePackageHelper.sMoveIdMapForSd;
            synchronized (map) {
                if (!AsecInstallHelper.sPreMounted) {
                    try {
                        iMemorySaverPackageMoveObserver.onStatusChanged(-1, -6, -1L);
                    } catch (Exception e) {
                        Log.w("PackageManager", " remote exception on observer " + e);
                    }
                    ((HashMap) MovePackageHelper.sMoveIdMapForSd).clear();
                    MovePackageHelper.sPendingMoves.clear();
                    return -1;
                }
                int callingUid = Binder.getCallingUid();
                UserHandle userHandle = new UserHandle(UserHandle.getUserId(callingUid));
                int andIncrement = packageManagerService.mNextMoveId.getAndIncrement();
                if (iMemorySaverPackageMoveObserver != null) {
                    ((HashMap) map).put(Integer.valueOf(andIncrement), iMemorySaverPackageMoveObserver);
                }
                MovePackageHelper.SdcardParams sdcardParams = new MovePackageHelper.SdcardParams(callingUid, andIncrement, userHandle, str, str2);
                ArrayList arrayList = MovePackageHelper.sPendingMoves;
                arrayList.add(arrayList.size(), sdcardParams);
                if (arrayList.size() == 1) {
                    packageManagerService.mHandler.post(movePackageHelper.new AnonymousClass2(sdcardParams));
                }
                return andIncrement;
            }
        }

        public final int movePrimaryStorage(String str) {
            movePrimaryStorage_enforcePermission();
            final int andIncrement = PackageManagerService.this.mNextMoveId.getAndIncrement();
            PackageManagerService.this.mMoveCallbacks.notifyCreated(andIncrement, AccountManagerService$$ExternalSyntheticOutline0.m142m("android.os.storage.extra.FS_UUID", str));
            ((StorageManager) PackageManagerService.this.mInjector.mGetSystemServiceProducer.produce(StorageManager.class)).setPrimaryStorageUuid(str, new IPackageMoveObserver.Stub() { // from class: com.android.server.pm.PackageManagerService.IPackageManagerImpl.2
                public final void onCreated(int i, Bundle bundle) {
                }

                public final void onStatusChanged(int i, int i2, long j) {
                    PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(andIncrement, i2, j);
                }
            });
            return andIncrement;
        }

        public final void notifyDexLoad(String str, Map map, String str2) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = this.mService.snapshotComputer();
            boolean z = PackageManagerServiceUtils.DEBUG;
            if (!PackageManagerServiceUtils.isSystemOrRoot(Binder.getCallingUid()) && !snapshotComputer.isCallerSameApp(callingUid, str, true)) {
                Slog.w("PackageManager", TextUtils.formatSimple("Invalid dex load report. loadingPackageName=%s, uid=%d", new Object[]{str, Integer.valueOf(callingUid)}));
                return;
            }
            UserHandle callingUserHandle = Binder.getCallingUserHandle();
            int identifier = callingUserHandle.getIdentifier();
            DexUseManagerLocal dexUseManagerLocal = DexOptHelper.getDexUseManagerLocal();
            if (dexUseManagerLocal == null) {
                ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(str, 0L, identifier);
                if (applicationInfo == null) {
                    Slog.w("PackageManager", "Loading a package that does not exist for the calling user. package=" + str + ", user=" + identifier);
                    return;
                }
                DexManager dexManager = PackageManagerService.this.mDexManager;
                boolean isIsolated = Process.isIsolated(callingUid);
                dexManager.getClass();
                try {
                    dexManager.notifyDexLoadInternal(applicationInfo, map, str2, identifier, isIsolated);
                    return;
                } catch (RuntimeException e) {
                    Slog.w("DexManager", "Exception while notifying dex load for package " + applicationInfo.packageName, e);
                    return;
                }
            }
            PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = ((PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class)).withFilteredSnapshot(callingUid, callingUserHandle);
            if (str2 != null) {
                try {
                    PackageState packageState = withFilteredSnapshot.getPackageState(str);
                    if (packageState != null) {
                        String primaryCpuAbi = packageState.getPrimaryCpuAbi();
                        if (primaryCpuAbi == null) {
                            primaryCpuAbi = Build.SUPPORTED_ABIS[0];
                        }
                        String instructionSet = VMRuntime.getInstructionSet(primaryCpuAbi);
                        String str3 = InstructionSets.PREFERRED_INSTRUCTION_SET;
                        String str4 = SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
                        if (!TextUtils.isEmpty(str4)) {
                            instructionSet = str4;
                        }
                        if (!str2.equals(instructionSet)) {
                            Log.wtf("PackageManager", "Invalid loaderIsa in notifyDexLoad call from " + str + ", uid " + callingUid + ": expected " + instructionSet + ", got " + str2);
                            withFilteredSnapshot.close();
                            return;
                        }
                    }
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
            dexUseManagerLocal.notifyDexContainersLoaded(withFilteredSnapshot, str, map);
            if (withFilteredSnapshot != null) {
                withFilteredSnapshot.close();
            }
        }

        public final void notifyPackageUse(String str, int i) {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null ? snapshotComputer.isCallerSameApp(callingUid, str) : !snapshotComputer.isInstantAppInternal(userId, 1000, str)) {
                PackageManagerService.m758$$Nest$mnotifyPackageUseInternal(PackageManagerService.this, str, i);
            }
        }

        public final void notifyPackagesReplacedReceived(String[] strArr) {
            ArraySet notifyPackagesForReplacedReceived = PackageManagerService.this.snapshotComputer().getNotifyPackagesForReplacedReceived(strArr);
            for (int i = 0; i < notifyPackagesForReplacedReceived.size(); i++) {
                PackageManagerService.this.notifyInstallObserver((String) notifyPackagesForReplacedReceived.valueAt(i), false);
            }
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            new PackageManagerShellCommand(this, packageManagerService.mContext, ((DomainVerificationService) packageManagerService.mDomainVerificationManager).mShell).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (RuntimeException e) {
                if (!(e instanceof SecurityException) && !(e instanceof IllegalArgumentException) && !(e instanceof ParcelableException)) {
                    Slog.wtf("PackageManager", "Package Manager Unexpected Exception", e);
                }
                throw e;
            }
        }

        public final void overrideLabelAndIcon(ComponentName componentName, String str, int i, int i2) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Override label should be a valid String");
            }
            PackageManagerService.this.updateComponentLabelIcon(componentName, str, Integer.valueOf(i), i2);
        }

        public final int performDexOptForADCP(String str, boolean z) {
            int i = z ? 2 : 0;
            Slog.e("dex2oat", "For test new API");
            DexOptHelper dexOptHelper = PackageManagerService.this.mDexOptHelper;
            DexoptOptions dexoptOptions = new DexoptOptions(23, i | 5, str, "speed-profile", null);
            dexOptHelper.getClass();
            Trace.traceBegin(16384L, "dexopt");
            try {
                return DexOptHelper.performDexOptWithArtService(dexoptOptions, 4);
            } finally {
                Trace.traceEnd(16384L);
            }
        }

        public final boolean performDexOptMode(String str, boolean z, String str2, boolean z2, boolean z3, String str3) {
            PackageSetting packageStateInternal;
            PackageSetting packageStateInternal2;
            Computer snapshotComputer = this.mService.snapshotComputer();
            if (!z) {
                Log.w("PackageManager", "Ignored checkProfiles=false flag");
            }
            DexOptHelper dexOptHelper = this.mDexOptHelper;
            dexOptHelper.getClass();
            boolean z4 = PackageManagerServiceUtils.DEBUG;
            if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid()) && ((packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || (packageStateInternal2 = snapshotComputer.getPackageStateInternal(packageStateInternal.installSource.mInstallerPackageName)) == null || packageStateInternal2.pkg.getUid() != Binder.getCallingUid())) {
                throw new SecurityException("performDexOptMode");
            }
            int i = (z3 ? 4 : 0) | (z2 ? 2 : 0);
            if (DexFile.isProfileGuidedCompilerFilter(str2)) {
                i |= 1;
            }
            return dexOptHelper.performDexOpt(new DexoptOptions(12, i, str, str2, str3));
        }

        public final boolean performDexOptSecondary(String str, String str2, boolean z) {
            DexOptHelper dexOptHelper = this.mDexOptHelper;
            dexOptHelper.getClass();
            return dexOptHelper.performDexOpt(new DexoptOptions(12, (z ? 2 : 0) | 13, str, str2, null));
        }

        public final ParceledListSlice queryContentProviders(String str, int i, long j, String str2) {
            return this.mService.snapshotComputer().queryContentProviders(str, i, j, str2);
        }

        public final ParceledListSlice queryInstrumentationAsUser(String str, int i, int i2) {
            return this.mService.snapshotComputer().queryInstrumentationAsUser(str, i, i2);
        }

        public final ParceledListSlice queryIntentActivities(Intent intent, String str, long j, int i) {
            SemPersonaManager semPersonaManager;
            try {
                Trace.traceBegin(262144L, "queryIntentActivities");
                List queryIntentActivitiesInternal = this.mService.snapshotComputer().queryIntentActivitiesInternal(intent, str, j, i);
                if (SemPersonaManager.isDoEnabled(i) && (semPersonaManager = (SemPersonaManager) this.mContext.getSystemService(SemPersonaManager.class)) != null && semPersonaManager.isAppSeparationPresent()) {
                    queryIntentActivitiesInternal = semPersonaManager.getUpdatedListWithAppSeparation(queryIntentActivitiesInternal);
                }
                ParceledListSlice parceledListSlice = new ParceledListSlice(queryIntentActivitiesInternal);
                Trace.traceEnd(262144L);
                return parceledListSlice;
            } catch (Throwable th) {
                Trace.traceEnd(262144L);
                throw th;
            }
        }

        /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
            java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
            	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
            	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
            	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
            */
        public final android.content.pm.ParceledListSlice queryIntentActivityOptions(android.content.ComponentName r31, android.content.Intent[] r32, java.lang.String[] r33, android.content.Intent r34, java.lang.String r35, long r36, int r38) {
            /*
                Method dump skipped, instructions count: 559
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.queryIntentActivityOptions(android.content.ComponentName, android.content.Intent[], java.lang.String[], android.content.Intent, java.lang.String, long, int):android.content.pm.ParceledListSlice");
        }

        public final ParceledListSlice queryIntentContentProviders(Intent intent, String str, long j, int i) {
            Intent intent2;
            List emptyList;
            List queryProviders;
            ResolveIntentHelper resolveIntentHelper = this.mResolveIntentHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            if (resolveIntentHelper.mUserManager.mLocalService.exists(i)) {
                int callingUid = Binder.getCallingUid();
                String instantAppPackageName = snapshotComputer.getInstantAppPackageName(callingUid);
                long updateFlagsForResolve = snapshotComputer.updateFlagsForResolve(i, callingUid, j, false, false);
                ComponentName component = intent.getComponent();
                if (component != null || intent.getSelector() == null) {
                    intent2 = intent;
                } else {
                    Intent selector = intent.getSelector();
                    intent2 = selector;
                    component = selector.getComponent();
                }
                if (component != null) {
                    ArrayList arrayList = new ArrayList(1);
                    ProviderInfo providerInfo = snapshotComputer.getProviderInfo(component, updateFlagsForResolve, i);
                    if (providerInfo != null) {
                        boolean z = (8388608 & updateFlagsForResolve) != 0;
                        boolean z2 = (updateFlagsForResolve & 16777216) != 0;
                        boolean z3 = instantAppPackageName != null;
                        boolean equals = component.getPackageName().equals(instantAppPackageName);
                        ApplicationInfo applicationInfo = providerInfo.applicationInfo;
                        boolean z4 = (applicationInfo.privateFlags & 128) != 0;
                        boolean z5 = !equals && (!(z || z3 || !z4) || (z2 && z3 && ((providerInfo.flags & 1048576) == 0)));
                        boolean z6 = (z4 || z3 || !snapshotComputer.shouldFilterApplication(snapshotComputer.getPackageStateInternal(1000, applicationInfo.packageName), callingUid, i)) ? false : true;
                        if (!z5 && !z6) {
                            ResolveInfo resolveInfo = new ResolveInfo();
                            resolveInfo.providerInfo = providerInfo;
                            arrayList.add(resolveInfo);
                        }
                    }
                    emptyList = arrayList;
                } else {
                    ComponentResolverApi componentResolver = snapshotComputer.getComponentResolver();
                    String str2 = intent2.getPackage();
                    if (str2 == null) {
                        queryProviders = componentResolver.queryProviders(snapshotComputer, intent2, str, updateFlagsForResolve, i);
                        if (queryProviders == null) {
                            emptyList = Collections.emptyList();
                        } else {
                            resolveIntentHelper.applyPostContentProviderResolutionFilter(snapshotComputer, queryProviders, instantAppPackageName, i, callingUid);
                            emptyList = queryProviders;
                        }
                    } else {
                        AndroidPackage androidPackage = snapshotComputer.getPackage(str2);
                        if (androidPackage != null) {
                            queryProviders = componentResolver.queryProviders(snapshotComputer, intent2, str, updateFlagsForResolve, androidPackage.getProviders(), i);
                            if (queryProviders == null) {
                                emptyList = Collections.emptyList();
                            } else {
                                resolveIntentHelper.applyPostContentProviderResolutionFilter(snapshotComputer, queryProviders, instantAppPackageName, i, callingUid);
                                emptyList = queryProviders;
                            }
                        } else {
                            emptyList = Collections.emptyList();
                        }
                    }
                }
            } else {
                emptyList = Collections.emptyList();
            }
            return new ParceledListSlice(emptyList);
        }

        public final ParceledListSlice queryIntentReceivers(Intent intent, String str, long j, int i) {
            return new ParceledListSlice(this.mResolveIntentHelper.queryIntentReceiversInternal(this.mService.snapshotComputer(), intent, str, j, i, Binder.getCallingUid(), -1, false));
        }

        public final ParceledListSlice queryIntentServices(Intent intent, String str, long j, int i) {
            return new ParceledListSlice(this.mService.snapshotComputer().queryIntentServicesInternal(intent, str, j, i, Binder.getCallingUid(), -1, false));
        }

        public final ParceledListSlice queryProperty(String str, int i) {
            ArrayMap arrayMap;
            ArrayMap arrayMap2;
            Objects.requireNonNull(str);
            final int callingUid = Binder.getCallingUid();
            final int callingUserId = UserHandle.getCallingUserId();
            final Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageProperty packageProperty = PackageManagerService.this.mPackageProperty;
            Predicate predicate = new Predicate() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Computer.this.getPackageStateForInstalledAndFiltered(callingUid, callingUserId, (String) obj) == null;
                }
            };
            ArrayList arrayList = null;
            if (i == 5) {
                arrayMap = packageProperty.mApplicationProperties;
            } else if (i == 1) {
                arrayMap = packageProperty.mActivityProperties;
            } else if (i == 4) {
                arrayMap = packageProperty.mProviderProperties;
            } else if (i == 2) {
                arrayMap = packageProperty.mReceiverProperties;
            } else if (i == 3) {
                arrayMap = packageProperty.mServiceProperties;
            } else {
                packageProperty.getClass();
                arrayMap = null;
            }
            if (arrayMap != null && (arrayMap2 = (ArrayMap) arrayMap.get(str)) != null) {
                Binder.getCallingUid();
                UserHandle.getCallingUserId();
                int size = arrayMap2.size();
                arrayList = new ArrayList(size);
                for (int i2 = 0; i2 < size; i2++) {
                    if (!predicate.test((String) arrayMap2.keyAt(i2))) {
                        arrayList.addAll((Collection) arrayMap2.valueAt(i2));
                    }
                }
            }
            return arrayList == null ? ParceledListSlice.emptyList() : new ParceledListSlice(arrayList);
        }

        public final void querySyncProviders(List list, List list2) {
            this.mService.snapshotComputer().querySyncProviders(list, list2, this.mService.mSafeMode);
        }

        public final void registerDexModule(String str, final String str2, boolean z, final IDexModuleRegisterCallback iDexModuleRegisterCallback) {
            Slog.i("PackageManager", "Ignored unsupported registerDexModule call for " + str2 + " in " + str);
            final DexManager.RegisterDexModuleResult registerDexModuleResult = new DexManager.RegisterDexModuleResult();
            if (iDexModuleRegisterCallback != null) {
                PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        IDexModuleRegisterCallback iDexModuleRegisterCallback2 = iDexModuleRegisterCallback;
                        String str3 = str2;
                        try {
                            registerDexModuleResult.getClass();
                            iDexModuleRegisterCallback2.onDexModuleRegistered(str3, false, "registerDexModule call not supported since Android U");
                        } catch (RemoteException e) {
                            Slog.w("PackageManager", "Failed to callback after module registration " + str3, e);
                        }
                    }
                });
            }
        }

        public final void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) {
            registerMoveCallback_enforcePermission();
            PackageManagerService.this.mMoveCallbacks.mCallbacks.register(iPackageMoveObserver);
        }

        public final void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) {
            int callingUid = Binder.getCallingUid();
            int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), callingUid, i, true, true, "registerPackageMonitorCallback", PackageManagerService.this.mContext.getPackageName());
            PackageMonitorCallbackHelper packageMonitorCallbackHelper = PackageManagerService.this.mPackageMonitorCallbackHelper;
            synchronized (packageMonitorCallbackHelper.mLock) {
                RemoteCallbackList remoteCallbackList = packageMonitorCallbackHelper.mCallbacks;
                PackageMonitorCallbackHelper.RegisterUser registerUser = new PackageMonitorCallbackHelper.RegisterUser();
                registerUser.mUid = callingUid;
                registerUser.mUserId = handleIncomingUser;
                remoteCallbackList.register(iRemoteCallback, registerUser);
            }
        }

        public final void relinquishUpdateOwnership(String str) {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, userId, str);
            if (packageStateForInstalledAndFiltered == null) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown target package: ", str));
            }
            String str2 = packageStateForInstalledAndFiltered.installSource.mUpdateOwnerPackageName;
            PackageSetting packageStateInternal = str2 == null ? null : snapshotComputer.getPackageStateInternal(str2);
            if (packageStateInternal == null) {
                return;
            }
            int appId = UserHandle.getAppId(callingUid);
            int i = packageStateInternal.mAppId;
            if (appId != 1000 && appId != 2000 && appId != i) {
                throw new SecurityException("Caller is not the current update owner.");
            }
            PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda21());
            PackageManagerService.this.scheduleWriteSettings();
        }

        public final boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) {
            boolean z = true;
            removeCrossProfileIntentFilter_enforcePermission();
            int callingUid = Binder.getCallingUid();
            PackageManagerService.enforceOwnerRights(PackageManagerService.this.snapshotComputer(), str, callingUid);
            if (!PackageManagerService.this.mUserManager.isCrossProfileIntentFilterAccessible(i, i2, false)) {
                throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "CrossProfileIntentFilter cannot be accessed by user "));
            }
            PackageManagerServiceUtils.enforceShellRestriction(PackageManagerService.this.mInjector.getUserManagerService().mLocalService, callingUid, i);
            synchronized (PackageManagerService.this.mLock) {
                try {
                    CrossProfileIntentResolver editCrossProfileIntentResolverLPw = PackageManagerService.this.mSettings.editCrossProfileIntentResolverLPw(i);
                    ArraySet arraySet = new ArraySet(Collections.unmodifiableSet(editCrossProfileIntentResolverLPw.mFilters));
                    int i4 = 0;
                    while (true) {
                        if (i4 >= arraySet.size()) {
                            z = false;
                            break;
                        }
                        CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) arraySet.valueAt(i4);
                        if (IntentFilter.filterEquals(crossProfileIntentFilter.mFilter, intentFilter) && crossProfileIntentFilter.mOwnerPackage.equals(str) && crossProfileIntentFilter.mTargetUserId == i2 && crossProfileIntentFilter.mFlags == i3) {
                            editCrossProfileIntentResolverLPw.removeFilter((WatchedIntentFilter) crossProfileIntentFilter);
                            break;
                        }
                        i4++;
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            if (z) {
                PackageManagerService.this.scheduleWritePackageRestrictions(i);
            }
            return z;
        }

        public final boolean removeEncPkgDir(int i, String str) {
            return false;
        }

        public final boolean removeEncUserDir(int i) {
            return false;
        }

        public final void removePermission(String str) {
            ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).removePermission(str);
        }

        public final void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) {
            this.mPreferredActivityHelper.replacePreferredActivity(this.mService.snapshotComputer(), new WatchedIntentFilter(intentFilter), i, componentNameArr, componentName, i2);
        }

        public final void requestPackageChecksums(String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            PackageManagerServiceInjector packageManagerServiceInjector = PackageManagerService.this.mInjector;
            packageManagerService.requestChecksumsInternal(snapshotComputer, str, z, i, i2, list, iOnChecksumsReadyListener, i3, packageManagerServiceInjector.mBackgroundExecutor, packageManagerServiceInjector.mBackgroundHandler);
        }

        public final void resetApplicationPreferences(int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.mPm.mContext.enforceCallingOrSelfPermission("android.permission.SET_PREFERRED_APPLICATIONS", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                PackageManagerTracedLock packageManagerTracedLock = preferredActivityHelper.mPm.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        preferredActivityHelper.mPm.clearPackagePreferredActivitiesLPw(null, sparseBooleanArray, i);
                    } finally {
                    }
                }
                if (sparseBooleanArray.size() > 0) {
                    preferredActivityHelper.mBroadcastHelper.sendPreferredActivityChangedBroadcast(i);
                }
                synchronized (preferredActivityHelper.mPm.mLock) {
                    try {
                        preferredActivityHelper.mPm.mSettings.applyDefaultPreferredAppsLPw(i);
                        ((DomainVerificationService) preferredActivityHelper.mPm.mDomainVerificationManager).clearUser(i);
                        PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissionsForUser(i);
                    } finally {
                    }
                }
                preferredActivityHelper.updateDefaultHomeNotLocked(preferredActivityHelper.mPm.snapshotComputer(), i);
                preferredActivityHelper.resetNetworkPolicies(i);
                preferredActivityHelper.mPm.scheduleWritePackageRestrictions(i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final ProviderInfo resolveContentProvider(String str, long j, int i) {
            return this.mService.snapshotComputer().resolveContentProvider(i, Binder.getCallingUid(), j, str);
        }

        public final ResolveInfo resolveIntent(Intent intent, String str, long j, int i) {
            return this.mResolveIntentHelper.resolveIntentInternal(this.mService.snapshotComputer(), intent, str, j, 0L, i, false, Binder.getCallingUid(), Binder.getCallingPid());
        }

        public final ResolveInfo resolveService(Intent intent, String str, long j, int i) {
            return this.mResolveIntentHelper.resolveServiceInternal(this.mService.snapshotComputer(), intent, str, j, i, Binder.getCallingUid(), -1, false);
        }

        public final void restoreDefaultApps(byte[] bArr, int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.getClass();
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call restoreDefaultApps()");
            }
            try {
                TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
                newFastPullParser.setInput(new ByteArrayInputStream(bArr), StandardCharsets.UTF_8.name());
                PreferredActivityHelper.restoreFromXml(newFastPullParser, i, "da", new PreferredActivityHelper$$ExternalSyntheticLambda1(preferredActivityHelper, 0));
            } catch (Exception unused) {
            }
        }

        public final void restoreDomainVerification(byte[] bArr, int i) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call restorePreferredActivities()");
            }
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(byteArrayInputStream);
                PackageManagerService packageManagerService = PackageManagerService.this;
                DomainVerificationManagerInternal domainVerificationManagerInternal = packageManagerService.mDomainVerificationManager;
                Computer snapshotComputer = packageManagerService.snapshotComputer();
                DomainVerificationService domainVerificationService = (DomainVerificationService) domainVerificationManagerInternal;
                synchronized (domainVerificationService.mLock) {
                    domainVerificationService.mSettings.restoreSettings(resolvePullParser, domainVerificationService.mAttachedPkgStates, snapshotComputer);
                }
                byteArrayInputStream.close();
            } catch (Exception unused) {
            }
        }

        public final void restoreLabelAndIcon(ComponentName componentName, int i) {
            PackageManagerService.this.updateComponentLabelIcon(componentName, null, null, i);
        }

        public final void restorePreferredActivities(byte[] bArr, int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            preferredActivityHelper.getClass();
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system may call restorePreferredActivities()");
            }
            try {
                TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
                newFastPullParser.setInput(new ByteArrayInputStream(bArr), StandardCharsets.UTF_8.name());
                PreferredActivityHelper.restoreFromXml(newFastPullParser, i, "pa", new PreferredActivityHelper$$ExternalSyntheticLambda1(preferredActivityHelper, 1));
            } catch (Exception unused) {
            }
        }

        public final boolean semIsPermissionRevokedByUserFixed(String str, String str2, int i) {
            if (UserHandle.getCallingUserId() != i) {
                PackageManagerService.this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "semIsPermissionRevokedByUserFixed for user " + i);
            }
            if (this.mService.checkPermission(str2, str, i) == 0) {
                return false;
            }
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
            if (packageStateInternal == null || snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i)) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return (((PermissionManager) PackageManagerService.this.mContext.getSystemService(PermissionManager.class)).getPermissionFlags(str2, str, UserHandle.of(i)) & 2) != 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendDeviceCustomizationReadyBroadcast() {
            PackageManagerService.this.mContext.enforceCallingPermission("android.permission.SEND_DEVICE_CUSTOMIZATION_READY", "sendDeviceCustomizationReadyBroadcast");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                String[] strArr = BroadcastHelper.INSTANT_APP_BROADCAST_PERMISSION;
                Intent intent = new Intent("android.intent.action.DEVICE_CUSTOMIZATION_READY");
                intent.setFlags(16777216);
                try {
                    ActivityManager.getService().broadcastIntentWithFeature((IApplicationThread) null, (String) null, intent, (String) null, (IIntentReceiver) null, 0, (String) null, (Bundle) null, new String[]{"android.permission.RECEIVE_DEVICE_CUSTOMIZATION_READY"}, (String[]) null, (String[]) null, -1, (Bundle) null, false, false, -1);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setAppCategoryHintUser(String str, int i) {
            AppCategoryHintHelper appCategoryHintHelper = PackageManagerService.sAppCategoryHintHelper;
            appCategoryHintHelper.getClass();
            if (!AppCategoryHintHelper.isSystemServerOrShell()) {
                throw new RemoteException("calling uid is not system server!");
            }
            if (!appCategoryHintHelper.mInit.get()) {
                android.util.secutil.Slog.d("AppCategoryHintHelper", "AppCategoryHintHelper is not initialized, can't set category");
                throw new RemoteException("AppCategoryHintHelper is not initialized, can't set category");
            }
            if (str == null || str.isEmpty()) {
                android.util.secutil.Slog.d("AppCategoryHintHelper", "packageName is null or empty!");
                throw new RemoteException("packageName is null or empty!");
            }
            if (i == -1) {
                appCategoryHintHelper.clearAppCategoryHintUser(str);
                return;
            }
            synchronized (appCategoryHintHelper.mCategoryMap) {
                appCategoryHintHelper.mCategoryMap.put(str, Integer.valueOf(i));
                appCategoryHintHelper.mChangedByUserApp.add(str);
            }
            appCategoryHintHelper.mHandler.removeCallbacks(new AppCategoryHintHelper$$ExternalSyntheticLambda0(appCategoryHintHelper));
            appCategoryHintHelper.mHandler.postDelayed(new AppCategoryHintHelper$$ExternalSyntheticLambda0(appCategoryHintHelper), 3000L);
        }

        public final void setApplicationCategoryHint(final String str, final int i, final String str2) {
            final int callingUid = Binder.getCallingUid();
            final int callingUserId = UserHandle.getCallingUserId();
            FunctionalUtils.ThrowingBiFunction throwingBiFunction = new FunctionalUtils.ThrowingBiFunction() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda19
                public final Object applyOrThrow(Object obj, Object obj2) {
                    PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                    int i2 = callingUid;
                    String str3 = str2;
                    int i3 = callingUserId;
                    String str4 = str;
                    final int i4 = i;
                    PackageStateMutator.InitialState initialState = (PackageStateMutator.InitialState) obj;
                    Computer computer = (Computer) obj2;
                    iPackageManagerImpl.getClass();
                    if (computer.getInstantAppPackageName(i2) != null) {
                        throw new SecurityException("Instant applications don't have access to this method");
                    }
                    if (computer.getPackageUid(str3, 0L, i3) != i2) {
                        throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, "Package ", str3, " does not belong to "));
                    }
                    PackageSetting packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(i2, i3, str4);
                    if (packageStateForInstalledAndFiltered == null) {
                        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown target package ", str4));
                    }
                    boolean z = false;
                    boolean z2 = Binder.getCallingUid() == 1000 && Binder.getCallingPid() == Process.myPid();
                    if ("com.sec.android.easyMover".equals(str3) && "com.sec.android.easyMover".equals(packageStateForInstalledAndFiltered.installSource.mInitiatingPackageName) && ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).isPlatformSigned(str3)) {
                        z = true;
                    }
                    if (!z2 && !z && !Objects.equals(str3, packageStateForInstalledAndFiltered.installSource.mInstallerPackageName)) {
                        throw new IllegalArgumentException(BootReceiver$$ExternalSyntheticOutline0.m("Calling package ", str3, " is not installer for ", str4));
                    }
                    AndroidPackageInternal androidPackageInternal = packageStateForInstalledAndFiltered.pkg;
                    int category = androidPackageInternal != null ? androidPackageInternal.getCategory() : -1;
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("setApplicationCategoryHint, pkg: ", str4, ", oldCategory: ");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(packageStateForInstalledAndFiltered.categoryOverride, i4, ", newCategory: ", ", manifestCategory: ", m);
                    m.append(category);
                    m.append(", caller: ");
                    m.append(str3);
                    m.append("/");
                    m.append(Binder.getCallingUid());
                    PmLog.logDebugInfoAndLogcat(m.toString());
                    if (packageStateForInstalledAndFiltered.categoryOverride != i4) {
                        return PackageManagerService.this.commitPackageStateMutation(initialState, str4, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda22
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj3) {
                                int i5 = i4;
                                PackageSetting packageSetting = ((PackageStateMutator.StateWriteWrapper) obj3).mState;
                                if (packageSetting != null) {
                                    packageSetting.categoryOverride = i5;
                                    packageSetting.onChanged$2();
                                }
                            }
                        });
                    }
                    return null;
                }
            };
            PackageStateMutator.Result result = (PackageStateMutator.Result) throwingBiFunction.apply(PackageManagerService.this.recordInitialState(), PackageManagerService.this.snapshotComputer());
            if (result != null && result.mStateChanged && !result.mSpecificPackageNull) {
                PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mPackageStateWriteLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        result = (PackageStateMutator.Result) throwingBiFunction.apply(PackageManagerService.this.recordInitialState(), PackageManagerService.this.snapshotComputer());
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
            }
            if (result == null || !result.mCommitted) {
                return;
            }
            PackageManagerService.this.scheduleWriteSettings();
            PackageManagerService.sAppCategoryHintHelper.sendAppCategoryBroadcast(i, str);
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setApplicationEnabledSetting(java.lang.String r7, int r8, int r9, int r10, java.lang.String r11) {
            /*
                r6 = this;
                com.android.server.pm.PackageManagerService r0 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.UserManagerService r0 = r0.mUserManager
                com.android.server.pm.UserManagerService$LocalService r0 = r0.mLocalService
                boolean r0 = r0.exists(r10)
                if (r0 != 0) goto Ld
                return
            Ld:
                if (r11 != 0) goto L17
                int r11 = android.os.Binder.getCallingUid()
                java.lang.String r11 = java.lang.Integer.toString(r11)
            L17:
                java.lang.String r0 = "auto_disabler"
                boolean r1 = r0.equals(r11)
                r2 = 0
                r3 = 1
                if (r1 != 0) goto L4c
                java.lang.String r1 = "com.sec.android.emergencymode.service"
                boolean r1 = r1.equals(r11)
                if (r1 != 0) goto L4c
                com.android.server.pm.PackageManagerService r1 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.Computer r1 = r1.snapshotComputer()
                com.android.server.pm.PackageSetting r1 = r1.getPackageStateInternal(r7)
                if (r1 == 0) goto L4c
                com.android.server.pm.pkg.PackageUserStateInternal r1 = r1.getUserStateOrDefault(r10)
                int r4 = r1.getEnabledState()
                r5 = 4
                if (r4 != r5) goto L4c
                java.lang.String r1 = r1.getLastDisableAppCaller()
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L4c
                r0 = r3
                goto L4d
            L4c:
                r0 = r2
            L4d:
                com.android.server.pm.PackageManagerService r1 = com.android.server.pm.PackageManagerService.this
                android.content.pm.PackageManager$ComponentEnabledSetting r4 = new android.content.pm.PackageManager$ComponentEnabledSetting
                r4.<init>(r7, r8, r9)
                java.util.List r9 = java.util.List.of(r4)
                com.android.server.pm.PackageManagerService.m760$$Nest$msetEnabledSettings(r1, r9, r10, r11)
                if (r0 == 0) goto Lab
                java.lang.String r9 = "com.google.android.partnersetup"
                boolean r9 = r9.equals(r11)
                if (r9 == 0) goto L6a
                if (r8 == 0) goto L69
                if (r8 != r3) goto L6a
            L69:
                r2 = r3
            L6a:
                com.android.server.pm.PackageManagerService r9 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.Computer r9 = r9.snapshotComputer()
                com.android.server.pm.PackageSetting r9 = r9.getPackageStateInternal(r7)
                if (r9 == 0) goto Lab
                com.android.server.pm.pkg.PackageUserStateInternal r9 = r9.getUserStateOrDefault(r10)
                int r9 = r9.getEnabledState()
                if (r9 != r8) goto Lab
                com.android.server.pm.PackageManagerService r6 = com.android.server.pm.PackageManagerService.this
                com.android.server.pm.AutoDisableHandler r6 = r6.mAutoDisableHandler
                r6.getClass()
                android.os.Bundle r8 = new android.os.Bundle
                r8.<init>()
                java.lang.String r9 = "packageName"
                r8.putString(r9, r7)
                java.lang.String r7 = "userId"
                r8.putInt(r7, r10)
                java.lang.String r7 = "isGoogleChanged"
                r8.putBoolean(r7, r2)
                com.android.server.pm.AutoDisableHandler$ADHandler r7 = r6.mAutoDisableHandler
                android.os.Message r7 = r7.obtainMessage(r3)
                r7.setData(r8)
                com.android.server.pm.AutoDisableHandler$ADHandler r6 = r6.mAutoDisableHandler
                r6.sendMessage(r7)
            Lab:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.setApplicationEnabledSetting(java.lang.String, int, int, int, java.lang.String):void");
        }

        public final boolean setApplicationHiddenSettingAsUser(String str, final boolean z, final int i) {
            setApplicationHiddenSettingAsUser_enforcePermission();
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "setApplicationHiddenSetting for user "), callingUid, i, true, true);
            String processNameForPid = PmServerUtils.getProcessNameForPid(Binder.getCallingPid());
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "setApplicationHiddenSettingAsUser, packageName: ", str, ", userId: ", ", hidden: ");
            m.append(z);
            m.append(", callingUid: ");
            m.append(callingUid);
            m.append(", callingPackage: ");
            m.append(processNameForPid);
            PmLog.logDebugInfoAndLogcat(m.toString());
            if (z && PackageManagerService.this.isPackageDeviceAdmin(i, str)) {
                PinnerService$$ExternalSyntheticOutline0.m("Not hiding package ", str, ": has active device admin", "PackageManager");
                return false;
            }
            if ("android".equals(str) || PackageManagerService.this.isRequiredSystemPackage(snapshotComputer, str, i)) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Cannot hide package: ", str, "PackageManager");
                return false;
            }
            if ("android".equals(str)) {
                Slog.w("PackageManager", "Cannot hide package: android");
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    return false;
                }
                PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                if (userStateOrDefault.isHidden() != z && userStateOrDefault.isInstalled() && !snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i)) {
                    AndroidPackageInternal androidPackageInternal = packageStateInternal.pkg;
                    if (androidPackageInternal != null) {
                        if (androidPackageInternal.getSdkLibraryName() != null) {
                            Slog.w("PackageManager", "Cannot hide package: " + str + " providing SDK library: " + androidPackageInternal.getSdkLibraryName());
                            return false;
                        }
                        if (androidPackageInternal.getStaticSharedLibraryName() != null) {
                            Slog.w("PackageManager", "Cannot hide package: " + str + " providing static shared library: " + androidPackageInternal.getStaticSharedLibraryName());
                            return false;
                        }
                    }
                    if (z && !UserHandle.isSameApp(callingUid, packageStateInternal.mAppId) && PackageManagerService.this.mProtectedPackages.isPackageStateProtected(i, str)) {
                        Slog.w("PackageManager", "Not hiding protected package: " + str);
                        return false;
                    }
                    PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i2 = i;
                            boolean z2 = z;
                            PackageUserStateImpl packageUserStateImpl = ((PackageStateMutator.StateWriteWrapper) obj).userState(i2).mUserState;
                            if (packageUserStateImpl != null) {
                                packageUserStateImpl.setBoolean$1(8, z2);
                                packageUserStateImpl.onChanged$4();
                            }
                        }
                    });
                    Computer snapshotComputer2 = PackageManagerService.this.snapshotComputer();
                    PackageSetting packageStateInternal2 = snapshotComputer2.getPackageStateInternal(str);
                    if (z) {
                        PackageManagerService packageManagerService = PackageManagerService.this;
                        int i2 = packageStateInternal2.mAppId;
                        packageManagerService.getClass();
                        PackageManagerService.killApplication(str, i2, i, "hiding pkg", 13);
                        PackageManagerService packageManagerService2 = PackageManagerService.this;
                        packageManagerService2.mBroadcastHelper.sendApplicationHiddenForUser(str, packageStateInternal2, i, packageManagerService2);
                    } else if (PackageManagerService.this.isLocaleOptimizedPackage(i, str)) {
                        PackageManagerService.this.updateLocaleOverlaysForPackage(-1, 0, InstallLocaleOverlaysType.PACKAGE_ENABLE, i, str);
                    } else {
                        PackageManagerService packageManagerService3 = PackageManagerService.this;
                        packageManagerService3.mBroadcastHelper.sendPackageAddedForUser(snapshotComputer2, str, packageStateInternal2, i, false, packageManagerService3.mAppPredictionServicePackage);
                    }
                    PackageManagerService.this.scheduleWritePackageRestrictions(i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setBlockUninstallForUser(String str, boolean z, int i) {
            AndroidPackageInternal androidPackageInternal;
            setBlockUninstallForUser_enforcePermission();
            PackageSetting packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal != null && (androidPackageInternal = packageStateInternal.pkg) != null) {
                if (androidPackageInternal.getSdkLibraryName() != null) {
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Cannot block uninstall of package: ", str, " providing SDK library: ");
                    m.append(androidPackageInternal.getSdkLibraryName());
                    Slog.w("PackageManager", m.toString());
                    return false;
                }
                if (androidPackageInternal.getStaticSharedLibraryName() != null) {
                    StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("Cannot block uninstall of package: ", str, " providing static shared library: ");
                    m2.append(androidPackageInternal.getStaticSharedLibraryName());
                    Slog.w("PackageManager", m2.toString());
                    return false;
                }
            }
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    PackageManagerService.this.mSettings.setBlockUninstallLPw(i, str, z);
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            PackageManagerService.this.scheduleWritePackageRestrictions(i);
            return true;
        }

        public final void setComponentEnabledSetting(ComponentName componentName, int i, int i2, int i3, String str) {
            if (PackageManagerService.this.mUserManager.mLocalService.exists(i3)) {
                if (str == null) {
                    str = Integer.toString(Binder.getCallingUid());
                }
                PackageManagerService.m760$$Nest$msetEnabledSettings(PackageManagerService.this, List.of(new PackageManager.ComponentEnabledSetting(componentName, i, i2)), i3, str);
            }
        }

        public final void setComponentEnabledSettings(List list, int i, String str) {
            if (PackageManagerService.this.mUserManager.mLocalService.exists(i)) {
                if (list == null || list.isEmpty()) {
                    throw new IllegalArgumentException("The list of enabled settings is empty");
                }
                if (str == null) {
                    str = Integer.toString(Binder.getCallingUid());
                }
                PackageManagerService.m760$$Nest$msetEnabledSettings(PackageManagerService.this, list, i, str);
            }
        }

        public final String[] setDistractingPackageRestrictionsAsUser(String[] strArr, final int i, final int i2) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setDistractingPackageRestrictionsAsUser");
            if (!PackageManagerServiceUtils.isSystemOrRoot(callingUid) && UserHandle.getUserId(callingUid) != i2) {
                throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, i2, "Calling uid ", " cannot call for user "));
            }
            Objects.requireNonNull(strArr, "packageNames cannot be null");
            DistractingPackageHelper distractingPackageHelper = PackageManagerService.this.mDistractingPackageHelper;
            distractingPackageHelper.getClass();
            if (ArrayUtils.isEmpty(strArr)) {
                return strArr;
            }
            SuspendPackageHelper suspendPackageHelper = distractingPackageHelper.mSuspendPackageHelper;
            if (i != 0 && !suspendPackageHelper.isSuspendAllowedForUser(snapshotComputer, i2, callingUid)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "Cannot restrict packages due to restrictions on user ", "PackageManager");
                return strArr;
            }
            ArrayList arrayList = new ArrayList(strArr.length);
            IntArray intArray = new IntArray(strArr.length);
            ArrayList arrayList2 = new ArrayList(strArr.length);
            final ArraySet arraySet = new ArraySet();
            boolean[] canSuspendPackageForUser = i != 0 ? suspendPackageHelper.canSuspendPackageForUser(snapshotComputer, strArr, i2, callingUid) : null;
            for (int i3 = 0; i3 < strArr.length; i3++) {
                String str = strArr[i3];
                PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i2, str);
                if (packageStateForInstalledAndFiltered == null) {
                    PinnerService$$ExternalSyntheticOutline0.m("Could not find package setting for package: ", str, ". Skipping...", "PackageManager");
                    arrayList2.add(str);
                } else if (canSuspendPackageForUser != null && !canSuspendPackageForUser[i3]) {
                    arrayList2.add(str);
                } else if (i != packageStateForInstalledAndFiltered.getUserStateOrDefault(i2).getDistractionFlags()) {
                    arrayList.add(str);
                    intArray.add(UserHandle.getUid(i2, packageStateForInstalledAndFiltered.mAppId));
                    arraySet.add(str);
                }
            }
            Consumer consumer = new Consumer() { // from class: com.android.server.pm.DistractingPackageHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ArraySet arraySet2 = arraySet;
                    int i4 = i2;
                    int i5 = i;
                    PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                    int size = arraySet2.size();
                    for (int i6 = 0; i6 < size; i6++) {
                        PackageUserStateImpl packageUserStateImpl = packageStateMutator.forPackage((String) arraySet2.valueAt(i6)).userState(i4).mUserState;
                        if (packageUserStateImpl != null) {
                            packageUserStateImpl.mDistractionFlags = i5;
                            packageUserStateImpl.onChanged$4();
                        }
                    }
                }
            };
            PackageManagerService packageManagerService = distractingPackageHelper.mPm;
            packageManagerService.commitPackageStateMutation(consumer);
            if (!arrayList.isEmpty()) {
                distractingPackageHelper.mBroadcastHelper.sendDistractingPackagesChanged(packageManagerService.snapshotComputer(), (String[]) arrayList.toArray(new String[arrayList.size()]), intArray.toArray(), i2, i);
                packageManagerService.scheduleWritePackageRestrictions(i2);
            }
            return (String[]) arrayList2.toArray(new String[0]);
        }

        public final void setHarmfulAppWarning(String str, CharSequence charSequence, int i) {
            int callingUid = Binder.getCallingUid();
            int appId = UserHandle.getAppId(callingUid);
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("setHarmfulAppInfo", callingUid, i, true, true);
            if (!PackageManagerServiceUtils.isSystemOrRoot(appId) && snapshotComputer.checkUidPermission("android.permission.SET_HARMFUL_APP_WARNINGS", callingUid) != 0) {
                throw new SecurityException("Caller must have the android.permission.SET_HARMFUL_APP_WARNINGS permission.");
            }
            if (PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(i, charSequence)).mSpecificPackageNull) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown package: ", str));
            }
            PackageManagerService.this.scheduleWritePackageRestrictions(i);
        }

        public final void setHomeActivity(ComponentName componentName, int i) {
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            preferredActivityHelper.getClass();
            if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            snapshotComputer.getHomeActivitiesAsUser(i, arrayList);
            int size = arrayList.size();
            ComponentName[] componentNameArr = new ComponentName[size];
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                ActivityInfo activityInfo = ((ResolveInfo) arrayList.get(i2)).activityInfo;
                ComponentName componentName2 = new ComponentName(activityInfo.packageName, activityInfo.name);
                componentNameArr[i2] = componentName2;
                if (!z && componentName2.equals(componentName)) {
                    z = true;
                }
            }
            if (!z) {
                throw new IllegalArgumentException("Component " + componentName + " cannot be home on user " + i);
            }
            WatchedIntentFilter watchedIntentFilter = new WatchedIntentFilter();
            watchedIntentFilter.mFilter = new IntentFilter("android.intent.action.MAIN");
            watchedIntentFilter.addCategory("android.intent.category.HOME");
            watchedIntentFilter.addCategory("android.intent.category.DEFAULT");
            preferredActivityHelper.replacePreferredActivity(snapshotComputer, watchedIntentFilter, 1048576, componentNameArr, componentName, i);
        }

        public final boolean setInstallLocation(int i) {
            setInstallLocation_enforcePermission();
            if (getInstallLocation() == i) {
                return true;
            }
            if (i != 0 && i != 1 && i != 2) {
                return false;
            }
            Settings.Global.putInt(PackageManagerService.this.mContext.getContentResolver(), "default_install_location", i);
            return true;
        }

        public final void setInstallerPackageName(final String str, final String str2) {
            int i = 0;
            final int callingUid = Binder.getCallingUid();
            final int userId = UserHandle.getUserId(callingUid);
            ?? r8 = new FunctionalUtils.ThrowingCheckedFunction() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda1
                public final Object apply(Object obj) {
                    PackageSetting packageSetting;
                    PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                    int i2 = callingUid;
                    String str3 = str;
                    int i3 = userId;
                    String str4 = str2;
                    Computer computer = (Computer) obj;
                    iPackageManagerImpl.getClass();
                    if (computer.getInstantAppPackageName(i2) != null) {
                        return Boolean.FALSE;
                    }
                    PackageSetting packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(i2, i3, str3);
                    if (packageStateForInstalledAndFiltered == null) {
                        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown target package: ", str3));
                    }
                    if (str4 != null) {
                        packageSetting = computer.getPackageStateForInstalledAndFiltered(i2, i3, str4);
                        if (packageSetting == null) {
                            throw new IllegalArgumentException("Unknown installer package: ".concat(str4));
                        }
                    } else {
                        packageSetting = null;
                    }
                    Pair packageOrSharedUser = computer.getPackageOrSharedUser(UserHandle.getAppId(i2));
                    if (packageOrSharedUser == null) {
                        throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Unknown calling UID: "));
                    }
                    Object obj2 = packageOrSharedUser.first;
                    SigningDetails signingDetails = obj2 != null ? ((PackageStateInternal) obj2).getSigningDetails() : ((SharedUserSetting) packageOrSharedUser.second).signatures.mSigningDetails;
                    if (packageSetting != null && PackageManagerServiceUtils.compareSignatures(signingDetails, packageSetting.signatures.mSigningDetails) != 0) {
                        throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Caller does not have same cert as new installer package ", str4));
                    }
                    String str5 = packageStateForInstalledAndFiltered.installSource.mInstallerPackageName;
                    PackageSetting packageStateInternal = str5 != null ? computer.getPackageStateInternal(str5) : null;
                    if (packageStateInternal != null) {
                        if (PackageManagerServiceUtils.compareSignatures(signingDetails, packageStateInternal.signatures.mSigningDetails) != 0) {
                            throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Caller does not have same cert as old installer package ", str5));
                        }
                    } else if (PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
                        EventLog.writeEvent(1397638484, "150857253", Integer.valueOf(i2), "");
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (!PackageManagerService.this.mInjector.getCompatibility().isChangeEnabledByUid(150857253L, i2)) {
                                return Boolean.FALSE;
                            }
                            throw new SecurityException("Neither user " + i2 + " nor current process has android.permission.INSTALL_PACKAGES");
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    return Boolean.TRUE;
                }
            };
            PackageStateMutator.InitialState recordInitialState = PackageManagerService.this.recordInitialState();
            if (((Boolean) r8.apply(PackageManagerService.this.snapshotComputer())).booleanValue()) {
                int packageUid = str2 == null ? -1 : PackageManagerService.this.snapshotComputer().getPackageUid(str2, 0L, userId);
                PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        PackageStateMutator.Result commitPackageStateMutation = PackageManagerService.this.commitPackageStateMutation(recordInitialState, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(packageUid, i, str2));
                        if (commitPackageStateMutation.mPackagesChanged || commitPackageStateMutation.mStateChanged) {
                            synchronized (PackageManagerService.this.mPackageStateWriteLock) {
                                try {
                                    if (!((Boolean) r8.apply(PackageManagerService.this.snapshotComputer())).booleanValue()) {
                                        return;
                                    } else {
                                        PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(packageUid, 1, str2));
                                    }
                                } finally {
                                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                                }
                            }
                        }
                        PackageSetting packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
                        PackageManagerService.this.mSettings.addInstallerPackageNames(packageStateInternal.installSource);
                        PackageManagerService packageManagerService = PackageManagerService.this;
                        packageManagerService.mAppsFilter.addPackage(packageManagerService.snapshotComputer(), packageStateInternal, false, false);
                        PackageManagerService.this.scheduleWriteSettings();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final boolean setInstantAppCookie(String str, byte[] bArr, int i) {
            PackageSetting packageStateInternal;
            AndroidPackage androidPackage;
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("setInstantAppCookie", Binder.getCallingUid(), i, true, true);
            boolean z = false;
            if (!snapshotComputer.isCallerSameApp(Binder.getCallingUid(), str) || (packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || (androidPackage = packageStateInternal.pkg) == null) {
                return false;
            }
            PackageManagerService packageManagerService = PackageManagerService.this;
            InstantAppRegistry instantAppRegistry = packageManagerService.mInstantAppRegistry;
            int instantAppCookieMaxBytes = packageManagerService.mContext.getPackageManager().getInstantAppCookieMaxBytes();
            synchronized (instantAppRegistry.mLock) {
                if (bArr != null) {
                    try {
                        if (bArr.length > 0 && bArr.length > instantAppCookieMaxBytes) {
                            Slog.e("InstantAppRegistry", "Instant app cookie for package " + androidPackage.getPackageName() + " size " + bArr.length + " bytes while max size is " + instantAppCookieMaxBytes);
                        }
                    } finally {
                    }
                }
                instantAppRegistry.mCookiePersistence.schedulePersistLPw(i, androidPackage, bArr);
                z = true;
            }
            return z;
        }

        public final void setKeepUninstalledPackages(List list) {
            PackageManagerService.this.mContext.enforceCallingPermission("android.permission.KEEP_UNINSTALLED_PACKAGES", "setKeepUninstalledPackages requires KEEP_UNINSTALLED_PACKAGES permission");
            Objects.requireNonNull(list);
            PackageManagerService.this.setKeepUninstalledPackagesInternal(this.mService.snapshotComputer(), list);
        }

        public final void setLastChosenActivity(Intent intent, String str, int i, IntentFilter intentFilter, int i2, ComponentName componentName) {
            ArrayList<Integer> integerArrayListExtra;
            int i3;
            int i4;
            boolean z;
            long j;
            boolean z2;
            PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            WatchedIntentFilter watchedIntentFilter = new WatchedIntentFilter(intentFilter);
            preferredActivityHelper.getClass();
            if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            intent.setComponent(null);
            List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
            if (Binder.getCallingUid() == 1027 && (integerArrayListExtra = intent.getIntegerArrayListExtra("com.samsung.sec.knox.EXTRA_KNOX_ARRAY")) != null) {
                Log.d("PersonaServiceHelper", "Set last chosen activity with user list.");
                long j2 = i;
                boolean z3 = true;
                if (integerArrayListExtra.size() == 1) {
                    boolean z4 = false;
                    int intValue = integerArrayListExtra.get(0).intValue();
                    UserInfo userInfo = PersonaServiceHelper.getUserManager().getUserInfo(intValue);
                    if (userInfo != null) {
                        int i5 = userInfo.profileGroupId;
                        if (i5 == -10000) {
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intValue, "profile group id is not set for user ", "PersonaServiceHelper");
                        } else {
                            Log.d("PersonaServiceHelper", "setLastChosenActivityForKnox parent id " + i5);
                            Iterator it = ((ArrayList) PersonaServiceHelper.getUserManager().getUsers(true, true, true)).iterator();
                            while (it.hasNext()) {
                                UserInfo userInfo2 = (UserInfo) it.next();
                                if ((userInfo2.isManagedProfile() && userInfo2.profileGroupId == i5) || userInfo2.id == i5) {
                                    GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("setLastChosenActivityForKnox remove pref for "), userInfo2.id, "PersonaServiceHelper");
                                    try {
                                        i3 = i5;
                                        i4 = intValue;
                                        try {
                                            z = z4;
                                            j = j2;
                                            z2 = z3;
                                        } catch (RemoteException e) {
                                            e = e;
                                            z = z4;
                                            j = j2;
                                            z2 = z3;
                                            e.printStackTrace();
                                            i5 = i3;
                                            intValue = i4;
                                            z3 = z2;
                                            z4 = z;
                                            j2 = j;
                                        }
                                        try {
                                            preferredActivityHelper.findPreferredActivityNotLocked(intent, str, j2, ActivityThread.getPackageManager().queryIntentActivities(intent, str, j2, userInfo2.id).getList(), false, true, false, userInfo2.id, UserHandle.getAppId(Binder.getCallingUid()) >= 10000 ? z3 : z4);
                                        } catch (RemoteException e2) {
                                            e = e2;
                                            e.printStackTrace();
                                            i5 = i3;
                                            intValue = i4;
                                            z3 = z2;
                                            z4 = z;
                                            j2 = j;
                                        }
                                    } catch (RemoteException e3) {
                                        e = e3;
                                        i3 = i5;
                                        i4 = intValue;
                                    }
                                } else {
                                    i3 = i5;
                                    i4 = intValue;
                                    z = z4;
                                    j = j2;
                                    z2 = z3;
                                }
                                i5 = i3;
                                intValue = i4;
                                z3 = z2;
                                z4 = z;
                                j2 = j;
                            }
                            int i6 = intValue;
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i6, "setLastChosenActivityForKnox setting preferred activity for ", "PersonaServiceHelper");
                            preferredActivityHelper.addPreferredActivity(snapshotComputer, watchedIntentFilter, i2, null, componentName, false, i6, "Setting last chosen", false);
                        }
                    } else {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intValue, "setLastChosenActivityForKnox invalid user ", "PersonaServiceHelper");
                    }
                } else {
                    Log.e("PersonaServiceHelper", "setLastChosenActivityForKnox invalid size for user list: " + integerArrayListExtra.size());
                }
                Log.d("PersonaServiceHelper", "set last chosen activity has been handled by Knox logic");
                return;
            }
            long j3 = i;
            preferredActivityHelper.findPreferredActivityNotLocked(intent, str, j3, snapshotComputer.queryIntentActivitiesInternal(intent, str, j3, callingUserId), false, true, callingUserId);
            preferredActivityHelper.addPreferredActivity(snapshotComputer, watchedIntentFilter, i2, null, componentName, false, callingUserId, "Setting last chosen", false);
        }

        public final int setLicensePermissionsForMDM(String str) {
            int licensePermissionsForMDM;
            Log.i("PackageManager", "setLicensePermissions ");
            if (Binder.getCallingUid() != Process.myUid()) {
                Log.i("PackageManager", "Caller is not SYSTEM_PROCESS");
                return -1;
            }
            Set keySet = EnterpriseDeviceAdminInfo.sKnownPolicies.keySet();
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    licensePermissionsForMDM = PermissionManagerService.this.mPermissionManagerServiceImpl.setLicensePermissionsForMDM(str, keySet);
                    PackageManagerService packageManagerService = PackageManagerService.this;
                    packageManagerService.mSettings.writeLPr(packageManagerService.mLiveComputer, false);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            return licensePermissionsForMDM;
        }

        public final void setMimeGroup(final String str, String str2, List list) {
            boolean updateMimeGroup;
            int i = 1;
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            PackageManagerService packageManagerService = PackageManagerService.this;
            int callingUid = Binder.getCallingUid();
            packageManagerService.getClass();
            PackageManagerService.enforceOwnerRights(snapshotComputer, str, callingUid);
            List emptyIfNull = CollectionUtils.emptyIfNull(list);
            for (int i2 = 0; i2 < emptyIfNull.size(); i2++) {
                if (((String) emptyIfNull.get(i2)).length() > 255) {
                    throw new IllegalArgumentException("MIME type length exceeds 255 characters");
                }
            }
            final PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
            Set set = (Set) packageStateInternal.getMimeGroups().get(str2);
            if (set == null) {
                throw new IllegalArgumentException(BootReceiver$$ExternalSyntheticOutline0.m("Unknown MIME group ", str2, " for package ", str));
            }
            if (set.size() == emptyIfNull.size() && set.containsAll(emptyIfNull)) {
                return;
            }
            if (emptyIfNull.size() > 500) {
                throw new IllegalStateException(BootReceiver$$ExternalSyntheticOutline0.m("Max limit on MIME types for MIME group ", str2, " exceeded for package ", str));
            }
            PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$$ExternalSyntheticLambda47(i, str2, new ArraySet(emptyIfNull)));
            PackageManagerService packageManagerService2 = PackageManagerService.this;
            ComponentResolver componentResolver = packageManagerService2.mComponentResolver;
            Computer snapshotComputer2 = packageManagerService2.snapshotComputer();
            PackageManagerTracedLock packageManagerTracedLock = componentResolver.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    updateMimeGroup = componentResolver.mServices.updateMimeGroup(snapshotComputer2, str, str2) | componentResolver.mActivities.updateMimeGroup(snapshotComputer2, str, str2) | componentResolver.mProviders.updateMimeGroup(snapshotComputer2, str, str2) | componentResolver.mReceivers.updateMimeGroup(snapshotComputer2, str, str2);
                    if (updateMimeGroup) {
                        componentResolver.dispatchChange(componentResolver);
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            if (updateMimeGroup) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda16
                    public final void runOrThrow() {
                        PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                        String str3 = str;
                        PackageStateInternal packageStateInternal2 = packageStateInternal;
                        PackageManagerService.this.mPreferredActivityHelper.clearPackagePreferredActivities(-1, str3);
                        Computer snapshotComputer3 = PackageManagerService.this.snapshotComputer();
                        ArrayList arrayList = new ArrayList(Collections.singletonList(str3));
                        int appId = packageStateInternal2.getAppId();
                        int[] resolveUserIds = PackageManagerService.this.resolveUserIds(-1);
                        for (int i3 = 0; i3 < resolveUserIds.length; i3++) {
                            PackageUserStateInternal packageUserStateInternal = (PackageUserStateInternal) packageStateInternal2.getUserStates().get(resolveUserIds[i3]);
                            if (packageUserStateInternal != null && packageUserStateInternal.isInstalled()) {
                                PackageManagerService.this.mBroadcastHelper.sendPackageChangedBroadcast(snapshotComputer3, str3, true, arrayList, UserHandle.getUid(resolveUserIds[i3], appId), "The mimeGroup is changed");
                            }
                        }
                    }
                });
            }
            PackageManagerService.this.scheduleWriteSettings();
        }

        public final void setPackageStoppedState(String str, boolean z, int i) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.setPackageStoppedState(i, packageManagerService.snapshotComputer(), str, z);
        }

        public final String[] setPackagesSuspendedAsUser(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i, String str, int i2, int i3) {
            String deviceOwnerOrProfileOwnerPackage;
            int callingUid = Binder.getCallingUid();
            boolean z2 = (i & 1) != 0 && Flags.quarantinedEnabled();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            UserPackage of = android.app.admin.flags.Flags.crossUserSuspensionEnabledRo() ? UserPackage.of(i2, str) : UserPackage.of(i3, str);
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.getClass();
            if (callingUid != 0 && UserHandle.getAppId(callingUid) != 1000 && ((deviceOwnerOrProfileOwnerPackage = packageManagerService.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(i3)) == null || snapshotComputer.getPackageUid(deviceOwnerOrProfileOwnerPackage, 0L, i3) != callingUid)) {
                if (z2) {
                    packageManagerService.mContext.enforceCallingOrSelfPermission("android.permission.QUARANTINE_APPS", "setPackagesSuspendedAsUser");
                } else {
                    packageManagerService.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setPackagesSuspendedAsUser");
                }
                if (!android.app.admin.flags.Flags.crossUserSuspensionEnabledRo()) {
                    int packageUid = snapshotComputer.getPackageUid(of.packageName, 0L, i3);
                    boolean z3 = packageUid == callingUid;
                    if ((callingUid != 2000 || !UserHandle.isSameApp(packageUid, callingUid)) && !z3) {
                        StringBuilder sb = new StringBuilder("Suspending package ");
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i3, of.packageName, " in user ", " does not belong to calling uid ", sb);
                        sb.append(callingUid);
                        throw new SecurityException(sb.toString());
                    }
                } else {
                    if (snapshotComputer.getPackageUid(of.packageName, 0L, of.userId) != callingUid) {
                        throw new SecurityException("Suspender package %s doesn't match calling uid %d".formatted(of.packageName, Integer.valueOf(callingUid)));
                    }
                    if (i3 != of.userId) {
                        packageManagerService.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "setPackagesSuspendedAsUser");
                    }
                }
            }
            PmLog.logDebugInfoAndLogcat("setPackagesSuspendedAsUser, packageName: " + Arrays.asList(strArr) + ", userId: " + i3 + ", suspended: " + z + ", callingUid: " + callingUid + ", callingPackage: " + str);
            return PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspended(snapshotComputer, strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, of, i3, callingUid, z2);
        }

        public final boolean setRequiredForSystemUser(String str, boolean z) {
            PackageManagerServiceUtils.enforceSystemOrRoot("setRequiredForSystemUser can only be run by the system or root");
            if (!PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda0(1, z)).mCommitted) {
                return false;
            }
            PackageManagerService.this.scheduleWriteSettings();
            return true;
        }

        public final void setRuntimePermissionsVersion(int i, int i2) {
            Preconditions.checkArgumentNonnegative(i);
            Preconditions.checkArgumentNonnegative(i2);
            PackageManagerService.m757x2d683684(PackageManagerService.this, "setRuntimePermissionVersion");
            Settings.RuntimePermissionPersistence runtimePermissionPersistence = PackageManagerService.this.mSettings.mRuntimePermissionsPersistence;
            synchronized (runtimePermissionPersistence.mLock) {
                runtimePermissionPersistence.mVersions.put(i2, i);
                runtimePermissionPersistence.writeStateForUserAsync(i2);
            }
        }

        public final void setSplashScreenTheme(String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("setSplashScreenTheme", callingUid, i, false, false);
            PackageManagerService.this.getClass();
            PackageManagerService.enforceOwnerRights(snapshotComputer, str, callingUid);
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, str) == null) {
                return;
            }
            PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(i, str2));
        }

        public final void setSystemAppHiddenUntilInstalled(String str, boolean z) {
            PackageManagerService packageManagerService = this.mService;
            packageManagerService.setSystemAppHiddenUntilInstalled(packageManagerService.snapshotComputer(), str, z);
        }

        public final boolean setSystemAppInstallState(String str, boolean z, int i) {
            PackageManagerService packageManagerService = this.mService;
            return packageManagerService.setSystemAppInstallState(i, packageManagerService.snapshotComputer(), str, z);
        }

        public final void setUpdateAvailable(String str, boolean z) {
            setUpdateAvailable_enforcePermission();
            PackageManagerService.this.commitPackageStateMutation(null, str, new PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda0(0, z));
        }

        public final void setUserMinAspectRatio(String str, final int i, final int i2) {
            setUserMinAspectRatio_enforcePermission();
            int callingUid = Binder.getCallingUid();
            Computer snapshotComputer = PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission("setUserMinAspectRatio", callingUid, i, false, false);
            PackageManagerService.this.getClass();
            PackageManagerService.enforceOwnerRights(snapshotComputer, str, callingUid);
            PackageSetting packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(callingUid, i, str);
            if (packageStateForInstalledAndFiltered == null || packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getMinAspectRatio() == i2) {
                return;
            }
            PackageManagerService.this.commitPackageStateMutation(null, str, new Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i3 = i;
                    int i4 = i2;
                    PackageUserStateImpl packageUserStateImpl = ((PackageStateMutator.StateWriteWrapper) obj).userState(i3).mUserState;
                    if (packageUserStateImpl != null) {
                        packageUserStateImpl.mMinAspectRatio = i4;
                        AnnotationValidations.validate(PackageManager.UserMinAspectRatio.class, (Annotation) null, i4);
                        packageUserStateImpl.onChanged$4();
                    }
                }
            });
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x002f, code lost:
        
            if (r3.mGalaxyStoreAppsForBadge.contains(r4) != false) goto L18;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean shouldAppSupportBadgeIcon(java.lang.String r4) {
            /*
                r3 = this;
                com.android.server.pm.PackageManagerService r3 = com.android.server.pm.PackageManagerService.this
                com.samsung.android.server.pm.monetization.MonetizationUtils r3 = r3.mMonetizationUtils
                r3.getClass()
                boolean r0 = com.samsung.android.rune.PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED
                r1 = 0
                if (r0 != 0) goto Ld
                goto L33
            Ld:
                java.lang.Object r0 = r3.mBadgeLock
                monitor-enter(r0)
                boolean r2 = r3.isMonetizedPreloadApp(r4)     // Catch: java.lang.Throwable -> L1f
                if (r2 == 0) goto L21
                android.util.ArraySet r2 = r3.mPreloadAppsLaunched     // Catch: java.lang.Throwable -> L1f
                boolean r2 = r2.contains(r4)     // Catch: java.lang.Throwable -> L1f
                if (r2 == 0) goto L31
                goto L21
            L1f:
                r3 = move-exception
                goto L34
            L21:
                java.util.concurrent.atomic.AtomicBoolean r2 = r3.mGalaxyStoreBadgeEnabled     // Catch: java.lang.Throwable -> L1f
                boolean r2 = r2.get()     // Catch: java.lang.Throwable -> L1f
                if (r2 == 0) goto L32
                android.util.ArraySet r3 = r3.mGalaxyStoreAppsForBadge     // Catch: java.lang.Throwable -> L1f
                boolean r3 = r3.contains(r4)     // Catch: java.lang.Throwable -> L1f
                if (r3 == 0) goto L32
            L31:
                r1 = 1
            L32:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L1f
            L33:
                return r1
            L34:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L1f
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.IPackageManagerImpl.shouldAppSupportBadgeIcon(java.lang.String):boolean");
        }

        public final void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) {
            unregisterMoveCallback_enforcePermission();
            PackageManagerService.this.mMoveCallbacks.mCallbacks.unregister(iPackageMoveObserver);
        }

        public final void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) {
            PackageMonitorCallbackHelper packageMonitorCallbackHelper = PackageManagerService.this.mPackageMonitorCallbackHelper;
            synchronized (packageMonitorCallbackHelper.mLock) {
                packageMonitorCallbackHelper.mCallbacks.unregister(iRemoteCallback);
            }
        }

        public final boolean updateIntentVerificationStatus(String str, int i, int i2) {
            return ((DomainVerificationService) this.mDomainVerificationManager).setLegacyUserState(i2, i, str);
        }

        public final void verifyIntentFilter(int i, int i2, List list) {
            Context context = this.mContext;
            DomainVerificationConnection domainVerificationConnection = this.mDomainVerificationConnection;
            int callingUid = Binder.getCallingUid();
            context.enforceCallingOrSelfPermission("android.permission.INTENT_FILTER_VERIFICATION_AGENT", "Only the intent filter verification agent can verify applications");
            domainVerificationConnection.schedule(3, new DomainVerificationProxyV1.Response(callingUid, i, list));
        }

        public final void verifyPendingInstall(final int i, final int i2) {
            if (i >= 0) {
                PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can verify applications");
            }
            final int callingUid = Binder.getCallingUid();
            PackageManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    PackageManagerService.IPackageManagerImpl iPackageManagerImpl = PackageManagerService.IPackageManagerImpl.this;
                    int i3 = i;
                    int i4 = callingUid;
                    int i5 = i2;
                    iPackageManagerImpl.getClass();
                    if (i3 < 0) {
                        i3 = -i3;
                    }
                    PackageVerificationState packageVerificationState = (PackageVerificationState) PackageManagerService.this.mPendingVerification.get(i3);
                    if (packageVerificationState == null) {
                        return;
                    }
                    if (packageVerificationState.mRequiredVerifierUids.get(i4, false) || packageVerificationState.mSufficientVerifierUids.get(i4, false)) {
                        Message obtainMessage = PackageManagerService.this.mHandler.obtainMessage(15);
                        PackageVerificationResponse packageVerificationResponse = new PackageVerificationResponse(i5, i4);
                        obtainMessage.arg1 = i3;
                        obtainMessage.obj = packageVerificationResponse;
                        PackageManagerService.this.mHandler.sendMessage(obtainMessage);
                    }
                }
            });
            DeviceIdleController$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "verifyPendingInstall vid: ", ", vcode: ", ", callingUid: "), callingUid, "PackageManager");
        }

        public final boolean waitForHandler(long j, boolean z) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            if (z) {
                PackageManagerService.this.mBackgroundHandler.post(new SettingsStore$$ExternalSyntheticLambda0(countDownLatch));
            } else {
                PackageManagerService.this.mHandler.post(new SettingsStore$$ExternalSyntheticLambda0(countDownLatch));
            }
            long currentTimeMillis = System.currentTimeMillis() + j;
            while (countDownLatch.getCount() > 0) {
                try {
                    long currentTimeMillis2 = currentTimeMillis - System.currentTimeMillis();
                    if (currentTimeMillis2 <= 0) {
                        return false;
                    }
                    return countDownLatch.await(currentTimeMillis2, TimeUnit.MILLISECONDS);
                } catch (InterruptedException unused) {
                }
            }
            return true;
        }
    }

    final class InstallLocaleOverlaysType {
        public static final /* synthetic */ InstallLocaleOverlaysType[] $VALUES;
        public static final InstallLocaleOverlaysType PACKAGE_ENABLE;
        public static final InstallLocaleOverlaysType PACKAGE_INSTALL;
        public static final InstallLocaleOverlaysType PACKAGE_UNINSTALL_UPDATES;
        public static final InstallLocaleOverlaysType REMOVE_PACKAGE_OVERLAYS;

        static {
            InstallLocaleOverlaysType installLocaleOverlaysType = new InstallLocaleOverlaysType("PACKAGE_INSTALL", 0);
            PACKAGE_INSTALL = installLocaleOverlaysType;
            InstallLocaleOverlaysType installLocaleOverlaysType2 = new InstallLocaleOverlaysType("PACKAGE_UNINSTALL_UPDATES", 1);
            PACKAGE_UNINSTALL_UPDATES = installLocaleOverlaysType2;
            InstallLocaleOverlaysType installLocaleOverlaysType3 = new InstallLocaleOverlaysType("PACKAGE_ENABLE", 2);
            PACKAGE_ENABLE = installLocaleOverlaysType3;
            InstallLocaleOverlaysType installLocaleOverlaysType4 = new InstallLocaleOverlaysType("REMOVE_PACKAGE_OVERLAYS", 3);
            REMOVE_PACKAGE_OVERLAYS = installLocaleOverlaysType4;
            $VALUES = new InstallLocaleOverlaysType[]{installLocaleOverlaysType, installLocaleOverlaysType2, installLocaleOverlaysType3, installLocaleOverlaysType4};
        }

        public static InstallLocaleOverlaysType valueOf(String str) {
            return (InstallLocaleOverlaysType) Enum.valueOf(InstallLocaleOverlaysType.class, str);
        }

        public static InstallLocaleOverlaysType[] values() {
            return (InstallLocaleOverlaysType[]) $VALUES.clone();
        }
    }

    public final class PackageManagerInternalImpl extends PackageManagerInternal {
        public final PackageManagerService mService;

        public PackageManagerInternalImpl() {
            this.mService = PackageManagerService.this;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean canAccessComponent(int i, int i2, ComponentName componentName) {
            return this.mService.snapshotComputer().canAccessComponent(i, i2, componentName);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean canQueryPackage(int i, String str) {
            return this.mService.snapshotComputer().canQueryPackage(i, str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean filterAppAccess(int i, int i2, String str, boolean z) {
            return this.mService.snapshotComputer().filterAppAccess(i, i2, str, z);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void forEachInstalledPackage(int i, Consumer consumer) {
            ArrayMap packageStates = this.mService.snapshotComputer().getPackageStates();
            int size = packageStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i2);
                if (packageStateInternal.getPkg() != null && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                    consumer.accept(packageStateInternal.getPkg());
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void forEachPackage(Consumer consumer) {
            ArrayMap packageStates = this.mService.snapshotComputer().getPackageStates();
            int size = packageStates.size();
            for (int i = 0; i < size; i++) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i);
                if (packageStateInternal.getPkg() != null) {
                    consumer.accept(packageStateInternal.getPkg());
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void forEachPackageSetting(PermissionManagerServiceImpl$$ExternalSyntheticLambda10 permissionManagerServiceImpl$$ExternalSyntheticLambda10) {
            PackageManagerService packageManagerService = this.mService;
            synchronized (packageManagerService.mLock) {
                try {
                    int size = packageManagerService.mSettings.mPackages.mStorage.size();
                    for (int i = 0; i < size; i++) {
                        permissionManagerServiceImpl$$ExternalSyntheticLambda10.accept((PackageSetting) packageManagerService.mSettings.mPackages.mStorage.valueAt(i));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void forEachPackageState(Consumer consumer) {
            PackageManagerService.forEachPackageState(this.mService.snapshotComputer(), consumer);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final ApplicationInfo getApplicationInfo(int i, int i2, long j, String str) {
            return this.mService.snapshotComputer().getApplicationInfoInternal(i, i2, j, str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final IncrementalStatesInfo getIncrementalStatesInfo(int i, int i2, String str) {
            PackageSetting packageStateForInstalledAndFiltered = PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(i, i2, str);
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            return new IncrementalStatesInfo(packageStateForInstalledAndFiltered.isLoading(), packageStateForInstalledAndFiltered.mLoadingProgress, packageStateForInstalledAndFiltered.mLoadingCompletedTime);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final List getInstalledApplications(int i, int i2, long j) {
            return this.mService.snapshotComputer().getInstalledApplications(i, i2, false, j);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final String getInstantAppPackageName(int i) {
            return this.mService.snapshotComputer().getInstantAppPackageName(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final String[] getKnownPackageNames(int i, int i2) {
            PackageManagerService packageManagerService = this.mService;
            return packageManagerService.getKnownPackageNamesInternal(packageManagerService.snapshotComputer(), i, i2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final LegacyPermissionSettings getLegacyPermissions() {
            LegacyPermissionSettings legacyPermissionSettings;
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    legacyPermissionSettings = PackageManagerService.this.mSettings.mPermissions;
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            return legacyPermissionSettings;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final String getNameForUid(int i) {
            return this.mService.snapshotComputer().getNameForUid(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final AndroidPackage getPackage(int i) {
            return this.mService.snapshotComputer().getPackage(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final AndroidPackage getPackage(String str) {
            return this.mService.snapshotComputer().getPackage(str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final PackageInfo getPackageInfo(int i, int i2, long j, String str) {
            return this.mService.snapshotComputer().getPackageInfoInternal(i, i2, str, -1L, j);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final PackageList getPackageList(PackageManagerInternal.PackageListObserver packageListObserver) {
            ArrayList arrayList = new ArrayList();
            PackageManagerService packageManagerService = PackageManagerService.this;
            Computer snapshotComputer = this.mService.snapshotComputer();
            PackageManagerService$$ExternalSyntheticLambda40 packageManagerService$$ExternalSyntheticLambda40 = new PackageManagerService$$ExternalSyntheticLambda40(2, arrayList);
            packageManagerService.getClass();
            PackageManagerService.forEachPackageState(snapshotComputer, packageManagerService$$ExternalSyntheticLambda40);
            PackageList packageList = new PackageList(arrayList, packageListObserver);
            if (packageListObserver != null) {
                PackageObserverHelper packageObserverHelper = PackageManagerService.this.mPackageObserverHelper;
                synchronized (packageObserverHelper.mLock) {
                    ArraySet arraySet = new ArraySet(packageObserverHelper.mActiveSnapshot);
                    arraySet.add(packageList);
                    packageObserverHelper.mActiveSnapshot = arraySet;
                }
            }
            return packageList;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final PackageStateInternal getPackageStateInternal(String str) {
            return this.mService.snapshotComputer().getPackageStateInternal(str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final int getPackageUid(String str, long j, int i) {
            return this.mService.snapshotComputer().getPackageUidInternal(i, 1000, j, str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final String[] getSharedUserPackagesForPackage(int i, String str) {
            return this.mService.snapshotComputer().getSharedUserPackagesForPackage(i, str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final SuspendDialogInfo getSuspendedDialogInfo(String str, UserPackage userPackage, int i) {
            WatchedArrayMap suspendParams;
            SuspendParams suspendParams2;
            SuspendPackageHelper suspendPackageHelper = PackageManagerService.this.mSuspendPackageHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            suspendPackageHelper.getClass();
            PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(callingUid, str);
            if (packageStateInternal == null) {
                return null;
            }
            PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
            if (!userStateOrDefault.isSuspended() || (suspendParams = userStateOrDefault.getSuspendParams()) == null || (suspendParams2 = (SuspendParams) suspendParams.mStorage.get(userPackage)) == null) {
                return null;
            }
            return suspendParams2.mDialogInfo;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final Bundle getSuspendedPackageLauncherExtras(int i, String str) {
            PersistableBundle persistableBundle;
            SuspendPackageHelper suspendPackageHelper = PackageManagerService.this.mSuspendPackageHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            suspendPackageHelper.getClass();
            PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(callingUid, str);
            if (packageStateInternal == null) {
                return null;
            }
            Bundle bundle = new Bundle();
            PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
            if (userStateOrDefault.isSuspended()) {
                for (int i2 = 0; i2 < userStateOrDefault.getSuspendParams().mStorage.size(); i2++) {
                    SuspendParams suspendParams = (SuspendParams) userStateOrDefault.getSuspendParams().mStorage.valueAt(i2);
                    if (suspendParams != null && (persistableBundle = suspendParams.mLauncherExtras) != null) {
                        bundle.putAll(persistableBundle);
                    }
                }
            }
            if (bundle.size() > 0) {
                return bundle;
            }
            return null;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final ComponentName getSystemUiServiceComponent() {
            return ComponentName.unflattenFromString(PackageManagerService.this.mContext.getResources().getString(R.string.face_acquired_too_similar));
        }

        @Override // android.content.pm.PackageManagerInternal
        public final int getUidTargetSdkVersion(int i) {
            return this.mService.snapshotComputer().getUidTargetSdkVersion(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void grantImplicitAccess(int i, Intent intent, int i2, int i3, boolean z, boolean z2) {
            PackageManagerService packageManagerService = this.mService;
            packageManagerService.grantImplicitAccess(packageManagerService.snapshotComputer(), i, intent, i2, i3, z, z2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean hasInstantApplicationMetadata(int i, String str) {
            InstantAppRegistry instantAppRegistry = PackageManagerService.this.mInstantAppRegistry;
            synchronized (instantAppRegistry.mLock) {
                try {
                    WatchedSparseArray watchedSparseArray = instantAppRegistry.mUninstalledInstantApps;
                    if (watchedSparseArray != null) {
                        List list = (List) watchedSparseArray.mStorage.get(i);
                        if (list != null) {
                            int size = list.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                if (!str.equals(((InstantAppRegistry.UninstalledInstantAppState) list.get(i2)).mInstantAppInfo.getPackageName())) {
                                }
                            }
                        }
                    }
                    File instantApplicationDir = InstantAppRegistry.getInstantApplicationDir(i, str);
                    if (new File(instantApplicationDir, "metadata.xml").exists() || new File(instantApplicationDir, "icon.png").exists() || new File(instantApplicationDir, "android_id").exists() || InstantAppRegistry.peekInstantCookieFile(i, str) != null) {
                        break;
                    }
                    return false;
                } finally {
                }
            }
            return true;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean hasSignatureCapability(int i, int i2) {
            Computer snapshotComputer = this.mService.snapshotComputer();
            SigningDetails signingDetails = snapshotComputer.getSigningDetails(i);
            SigningDetails signingDetails2 = snapshotComputer.getSigningDetails(i2);
            return signingDetails.checkCapability(signingDetails2, 16) || signingDetails2.hasAncestorOrSelf(signingDetails);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isInstantApp(String str, int i) {
            return this.mService.snapshotComputer().isInstantApp(str, i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isPackageEphemeral(int i, String str) {
            PackageStateInternal packageStateInternal = getPackageStateInternal(str);
            return packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isInstantApp();
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isPackageFrozen(int i, int i2, String str) {
            return this.mService.snapshotComputer().getPackageStartability(i, i2, str, this.mService.mSafeMode) == 3;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isPackageSuspended(int i, String str) {
            SuspendPackageHelper suspendPackageHelper = PackageManagerService.this.mSuspendPackageHelper;
            Computer snapshotComputer = this.mService.snapshotComputer();
            int callingUid = Binder.getCallingUid();
            suspendPackageHelper.getClass();
            PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(callingUid, str);
            return packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isSuspended();
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isPermissionsReviewRequired(int i, String str) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.isPermissionsReviewRequired(i, str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isPlatformSigned(String str) {
            PackageSetting packageStateInternal = this.mService.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return false;
            }
            SigningDetails signingDetails = packageStateInternal.signatures.mSigningDetails;
            PackageManagerService packageManagerService = PackageManagerService.this;
            return signingDetails.hasAncestorOrSelf(packageManagerService.mPlatformPackage.getSigningDetails()) || packageManagerService.mPlatformPackage.getSigningDetails().checkCapability(signingDetails, 4);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean isSameApp(int i, int i2, long j, String str) {
            if (str == null) {
                return false;
            }
            return Process.isSdkSandboxUid(i) ? str.equals(PackageManagerService.this.mRequiredSdkSandboxPackage) : UserHandle.isSameApp(this.mService.snapshotComputer().getPackageUid(str, j, i2), i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void notifyComponentUsed(int i, String str, String str2, String str3) {
            PackageManagerService packageManagerService = this.mService;
            Computer snapshotComputer = packageManagerService.snapshotComputer();
            synchronized (packageManagerService.mLock) {
                try {
                    PackageSetting packageLPr = packageManagerService.mSettings.getPackageLPr(str);
                    if (packageLPr == null) {
                        return;
                    }
                    if (packageLPr.getUserStateOrDefault(i).isQuarantined()) {
                        Slog.i("PackageManager", "Component is quarantined+suspended but being used: " + str + " by " + str2 + ", debugInfo: " + str3);
                    }
                    packageManagerService.setPackageStoppedState(i, snapshotComputer, str, false);
                } finally {
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void onPackageProcessKilledForUninstall(String str) {
            PackageManagerService.this.mHandler.post(new PackageManagerService$$ExternalSyntheticLambda51(3, this, str));
        }

        @Override // android.content.pm.PackageManagerInternal
        public final List queryIntentActivities(Intent intent, String str, long j, int i, int i2) {
            return this.mService.snapshotComputer().queryIntentActivitiesInternal(intent, str, j, i, i2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void reconcileAppsData(int i, int i2, boolean z) {
            AppDataHelper appDataHelper = PackageManagerService.this.mAppDataHelper;
            appDataHelper.getClass();
            Iterator it = ((StorageManager) appDataHelper.mInjector.mGetSystemServiceProducer.produce(StorageManager.class)).getWritablePrivateVolumes().iterator();
            while (it.hasNext()) {
                String fsUuid = ((VolumeInfo) it.next()).getFsUuid();
                PackageManagerTracedLock packageManagerTracedLock = appDataHelper.mPm.mInstallLock;
                packageManagerTracedLock.mLock.lock();
                try {
                    appDataHelper.reconcileAppsDataLI(fsUuid, i, i2, z, false);
                    packageManagerTracedLock.close();
                } catch (Throwable th) {
                    try {
                        packageManagerTracedLock.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean registerInstalledLoadingProgressCallback(String str, LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback packageLoadingProgressCallback, int i) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            PackageSetting packageStateForInstalledAndFiltered = packageManagerService.snapshotComputer().getPackageStateForInstalledAndFiltered(Binder.getCallingUid(), i, str);
            if (packageStateForInstalledAndFiltered == null) {
                return false;
            }
            if (!packageStateForInstalledAndFiltered.isLoading()) {
                Slog.w("PackageManager", "Failed registering loading progress callback. Package is fully loaded.");
                return false;
            }
            IncrementalManager incrementalManager = packageManagerService.mIncrementalManager;
            if (incrementalManager != null) {
                return incrementalManager.registerLoadingProgressCallback(packageStateForInstalledAndFiltered.mPathString, packageLoadingProgressCallback.mBinder);
            }
            Slog.w("PackageManager", "Failed registering loading progress callback. Incremental is not enabled");
            return false;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void removeIsolatedUid(int i) {
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    WatchedSparseIntArray watchedSparseIntArray = PackageManagerService.this.mIsolatedOwners;
                    int indexOfKey = watchedSparseIntArray.mStorage.indexOfKey(i);
                    if (indexOfKey >= 0) {
                        watchedSparseIntArray.mStorage.removeAt(indexOfKey);
                        watchedSparseIntArray.dispatchChange(watchedSparseIntArray);
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void setEnabledOverlayPackages(final int i, final ArrayMap arrayMap, final Set set, Set set2) {
            int i2;
            int i3;
            PackageSetting packageSetting;
            List list;
            String str;
            OverlayPaths overlayPaths;
            OverlayPaths overlayPaths2;
            PackageManagerService packageManagerService = PackageManagerService.this;
            packageManagerService.getClass();
            final ArrayMap arrayMap2 = new ArrayMap();
            final int size = arrayMap.size();
            synchronized (packageManagerService.mOverlayPathsLock) {
                try {
                    Computer snapshotComputer = packageManagerService.snapshotComputer();
                    int i4 = 0;
                    while (i4 < size) {
                        String str2 = (String) arrayMap.keyAt(i4);
                        OverlayPaths overlayPaths3 = (OverlayPaths) arrayMap.valueAt(i4);
                        PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
                        AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.pkg;
                        if (str2 == null || androidPackage == null) {
                            i2 = i4;
                            Slog.e("PackageManager", "failed to find package " + str2);
                            ((HashSet) set2).add(str2);
                        } else if (Objects.equals(packageStateInternal.getUserStateOrDefault(i).getOverlayPaths(), overlayPaths3)) {
                            i2 = i4;
                        } else {
                            if (androidPackage.getLibraryNames() != null) {
                                List libraryNames = androidPackage.getLibraryNames();
                                int i5 = 0;
                                while (i5 < libraryNames.size()) {
                                    String str3 = (String) libraryNames.get(i5);
                                    int i6 = i4;
                                    SharedLibraryInfo sharedLibraryInfo = snapshotComputer.getSharedLibraryInfo(-1L, str3);
                                    if (sharedLibraryInfo == null) {
                                        i3 = i5;
                                        packageSetting = packageStateInternal;
                                        list = libraryNames;
                                        str = str2;
                                        overlayPaths = overlayPaths3;
                                    } else {
                                        i3 = i5;
                                        packageSetting = packageStateInternal;
                                        list = libraryNames;
                                        OverlayPaths overlayPaths4 = overlayPaths3;
                                        str = str2;
                                        List list2 = (List) snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 0L, 1000, i).first;
                                        if (list2 == null) {
                                            overlayPaths = overlayPaths4;
                                        } else {
                                            int i7 = 0;
                                            ArraySet arraySet = null;
                                            while (i7 < list2.size()) {
                                                VersionedPackage versionedPackage = (VersionedPackage) list2.get(i7);
                                                PackageSetting packageStateInternal2 = snapshotComputer.getPackageStateInternal(versionedPackage.getPackageName());
                                                if (packageStateInternal2 == null) {
                                                    overlayPaths2 = overlayPaths4;
                                                } else {
                                                    OverlayPaths overlayPaths5 = (OverlayPaths) packageStateInternal2.getUserStateOrDefault(i).getSharedLibraryOverlayPaths().get(str3);
                                                    overlayPaths2 = overlayPaths4;
                                                    if (!Objects.equals(overlayPaths5, overlayPaths2) && ((overlayPaths5 != null || !overlayPaths2.isEmpty()) && (overlayPaths2 != null || !overlayPaths5.isEmpty()))) {
                                                        String packageName = versionedPackage.getPackageName();
                                                        arraySet = ArrayUtils.add(arraySet, packageName);
                                                        ((HashSet) set).add(packageName);
                                                    }
                                                }
                                                i7++;
                                                overlayPaths4 = overlayPaths2;
                                            }
                                            overlayPaths = overlayPaths4;
                                            if (arraySet != null) {
                                                ArrayMap arrayMap3 = (ArrayMap) arrayMap2.get(str);
                                                if (arrayMap3 == null) {
                                                    arrayMap3 = new ArrayMap();
                                                    arrayMap2.put(str, arrayMap3);
                                                }
                                                arrayMap3.put(str3, arraySet);
                                            }
                                        }
                                    }
                                    i5 = i3 + 1;
                                    overlayPaths3 = overlayPaths;
                                    packageStateInternal = packageSetting;
                                    str2 = str;
                                    i4 = i6;
                                    libraryNames = list;
                                }
                            }
                            i2 = i4;
                            String str4 = str2;
                            OverlayPaths overlayPaths6 = overlayPaths3;
                            OverlayPaths overlayPaths7 = packageStateInternal.getUserStateOrDefault(i).getOverlayPaths();
                            if (!Objects.equals(overlayPaths7, overlayPaths6) && ((overlayPaths7 != null || !overlayPaths6.isEmpty()) && (overlayPaths6 != null || !overlayPaths7.isEmpty()))) {
                                ((HashSet) set).add(str4);
                            }
                        }
                        i4 = i2 + 1;
                    }
                    packageManagerService.commitPackageStateMutation(new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda63
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i8 = size;
                            ArrayMap arrayMap4 = arrayMap;
                            Set set3 = set;
                            int i9 = i;
                            ArrayMap arrayMap5 = arrayMap2;
                            PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                            for (int i10 = 0; i10 < i8; i10++) {
                                String str5 = (String) arrayMap4.keyAt(i10);
                                OverlayPaths overlayPaths8 = (OverlayPaths) arrayMap4.valueAt(i10);
                                if (set3.contains(str5)) {
                                    PackageUserStateImpl packageUserStateImpl = packageStateMutator.forPackage(str5).userState(i9).mUserState;
                                    if (packageUserStateImpl != null && !Objects.equals(overlayPaths8, packageUserStateImpl.mOverlayPaths) && ((packageUserStateImpl.mOverlayPaths != null || !overlayPaths8.isEmpty()) && (overlayPaths8 != null || !packageUserStateImpl.mOverlayPaths.isEmpty()))) {
                                        packageUserStateImpl.mOverlayPaths = overlayPaths8;
                                        packageUserStateImpl.onChanged$4();
                                    }
                                    ArrayMap arrayMap6 = (ArrayMap) arrayMap5.get(str5);
                                    if (arrayMap6 != null) {
                                        for (int i11 = 0; i11 < arrayMap6.size(); i11++) {
                                            String str6 = (String) arrayMap6.keyAt(i11);
                                            ArraySet arraySet2 = (ArraySet) arrayMap6.valueAt(i11);
                                            for (int i12 = 0; i12 < arraySet2.size(); i12++) {
                                                PackageUserStateImpl packageUserStateImpl2 = packageStateMutator.forPackage((String) arraySet2.valueAt(i12)).userState(i9).mUserState;
                                                if (packageUserStateImpl2 != null) {
                                                    packageUserStateImpl2.setSharedLibraryOverlayPaths(str6, overlayPaths8);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (i == 0) {
                for (int i8 = 0; i8 < size; i8++) {
                    String str5 = (String) arrayMap.keyAt(i8);
                    OverlayPaths overlayPaths8 = (OverlayPaths) arrayMap.valueAt(i8);
                    if (packageManagerService.mResolverReplaced) {
                        if (str5.equals(packageManagerService.mResolveActivity.applicationInfo.packageName)) {
                            if (overlayPaths8 == null) {
                                packageManagerService.mReplacedResolverPackageOverlayPaths = null;
                                packageManagerService.mReplacedResolverPackageOverlayResourceDirs = null;
                            } else {
                                packageManagerService.mReplacedResolverPackageOverlayPaths = (String[]) overlayPaths8.getOverlayPaths().toArray(new String[0]);
                                packageManagerService.mReplacedResolverPackageOverlayResourceDirs = (String[]) overlayPaths8.getResourceDirs().toArray(new String[0]);
                            }
                            packageManagerService.applyUpdatedSystemOverlayPaths();
                        }
                    } else if (str5.equals("android")) {
                        if (overlayPaths8 == null) {
                            packageManagerService.mPlatformPackageOverlayPaths = null;
                            packageManagerService.mPlatformPackageOverlayResourceDirs = null;
                        } else {
                            packageManagerService.mPlatformPackageOverlayPaths = (String[]) overlayPaths8.getOverlayPaths().toArray(new String[0]);
                            packageManagerService.mPlatformPackageOverlayResourceDirs = (String[]) overlayPaths8.getResourceDirs().toArray(new String[0]);
                        }
                        packageManagerService.applyUpdatedSystemOverlayPaths();
                    }
                }
            }
            PackageManagerService.invalidatePackageInfoCache();
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void setIntegrityVerificationResult(int i, int i2) {
            PackageManagerService packageManagerService = PackageManagerService.this;
            Message obtainMessage = packageManagerService.mHandler.obtainMessage(25);
            obtainMessage.arg1 = i;
            obtainMessage.obj = Integer.valueOf(i2);
            packageManagerService.mHandler.sendMessage(obtainMessage);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final String[] setPackagesSuspendedByAdmin(int i, boolean z, String[] strArr) {
            UserPackage of = UserPackage.of(android.app.admin.flags.Flags.crossUserSuspensionEnabledRo() ? 0 : i, "android");
            PackageManagerService packageManagerService = PackageManagerService.this;
            return packageManagerService.mSuspendPackageHelper.setPackagesSuspended(packageManagerService.snapshotComputer(), strArr, z, null, null, null, of, i, 1000, false);
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void shutdown() {
            PackageManagerService packageManagerService = this.mService;
            CompilerStats compilerStats = packageManagerService.mCompilerStats;
            compilerStats.writeImpl(null);
            compilerStats.mLastTimeWritten.set(SystemClock.elapsedRealtime());
            packageManagerService.mDexManager.mPackageDexUsage.writeInternal();
            PackageDynamicCodeLoading packageDynamicCodeLoading = packageManagerService.mDynamicCodeLogger.mPackageDynamicCodeLoading;
            packageDynamicCodeLoading.writeImpl(null);
            packageDynamicCodeLoading.mLastTimeWritten.set(SystemClock.elapsedRealtime());
            PackageWatchdog packageWatchdog = PackageWatchdog.getInstance(packageManagerService.mContext);
            synchronized (packageWatchdog.mLock) {
                try {
                    if (!packageWatchdog.mAllObservers.isEmpty()) {
                        packageWatchdog.mLongTaskHandler.removeCallbacks(packageWatchdog.mSaveToFile);
                        packageWatchdog.pruneObserversLocked();
                        packageWatchdog.saveToFile();
                        Slog.i("PackageWatchdog", "Last write to update package durations");
                    }
                } finally {
                }
            }
            PmLifecycleImpl pmLifecycleImpl = packageManagerService.mPmLifecycle;
            if (pmLifecycleImpl.mIsUserTrial) {
                pmLifecycleImpl.mPm.snapshotComputer().getInstalledPackages(131072L, 0).getList().forEach(new PmLifecycleImpl$$ExternalSyntheticLambda2());
                PackageManagerServiceUtils.logCriticalInfo(6, "finish verify codepath");
            }
            synchronized (packageManagerService.mLock) {
                try {
                    PackageUsage packageUsage = packageManagerService.mPackageUsage;
                    packageUsage.writeImpl(packageManagerService.mSettings.mPackages);
                    packageUsage.mLastTimeWritten.set(SystemClock.elapsedRealtime());
                    if (!packageManagerService.mHandler.hasMessages(13)) {
                        if (!packageManagerService.mBackgroundHandler.hasMessages(14)) {
                            if (packageManagerService.mHandler.hasMessages(19)) {
                            }
                        }
                    }
                    packageManagerService.writeSettings(true);
                } finally {
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean wasPackageEverLaunched(int i, String str) {
            if (getPackageStateInternal(str) != null) {
                return !r0.getUserStateOrDefault(i).isNotLaunched();
            }
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown package: ", str));
        }

        @Override // android.content.pm.PackageManagerInternal
        public final boolean wasPackageStopped(int i, String str) {
            PackageSetting packageStateInternal = PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return true;
            }
            return packageStateInternal.getUserStateOrDefault(i).isStopped();
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void writePermissionSettings(int[] iArr, boolean z) {
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    for (int i : iArr) {
                        Settings settings = PackageManagerService.this.mSettings;
                        if (!z) {
                            settings.mRuntimePermissionsPersistence.writeStateForUser(i, settings.mPermissionDataProvider, settings.mPackages, settings.mSharedUsers, null, settings.mLock, true);
                        } else {
                            settings.mRuntimePermissionsPersistence.writeStateForUserAsync(i);
                        }
                    }
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
        }

        @Override // android.content.pm.PackageManagerInternal
        public final void writeSettings(boolean z) {
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    if (z) {
                        PackageManagerService.this.scheduleWriteSettings();
                    } else {
                        PackageManagerService.this.writeSettingsLPrTEMP(false);
                    }
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
    }

    public final class Snapshot {
        public final ApplicationInfo androidApplication;
        public final String appPredictionServicePackage;
        public final AppsFilterBase appsFilter;
        public final ComponentResolverApi componentResolver;
        public final WatchedArrayMap frozenPackages;
        public final ActivityInfo instantAppInstallerActivity;
        public final ResolveInfo instantAppInstallerInfo;
        public final InstantAppRegistry instantAppRegistry;
        public final WatchedArrayMap instrumentation;
        public final WatchedSparseIntArray isolatedOwners;
        public final WatchedArrayMap packages;
        public final ActivityInfo resolveActivity;
        public final ComponentName resolveComponentName;
        public final PackageManagerService service;
        public final Settings settings;
        public final SharedLibrariesImpl sharedLibraries;
        public final WatchedSparseBooleanArray webInstantAppsDisabled;

        public Snapshot(PackageManagerService packageManagerService, int i) {
            if (i == 2) {
                this.settings = (Settings) packageManagerService.mSettings.mSnapshot.snapshot();
                this.isolatedOwners = (WatchedSparseIntArray) packageManagerService.mIsolatedOwnersSnapshot.snapshot();
                this.packages = (WatchedArrayMap) packageManagerService.mPackagesSnapshot.snapshot();
                this.instrumentation = (WatchedArrayMap) packageManagerService.mInstrumentationSnapshot.snapshot();
                ComponentName componentName = packageManagerService.mResolveComponentName;
                this.resolveComponentName = componentName == null ? null : componentName.clone();
                this.resolveActivity = new ActivityInfo(packageManagerService.mResolveActivity);
                this.instantAppInstallerActivity = packageManagerService.mInstantAppInstallerActivity == null ? null : new ActivityInfo(packageManagerService.mInstantAppInstallerActivity);
                this.instantAppInstallerInfo = new ResolveInfo(packageManagerService.mInstantAppInstallerInfo);
                WatchedSparseBooleanArray watchedSparseBooleanArray = packageManagerService.mWebInstantAppsDisabled;
                watchedSparseBooleanArray.getClass();
                WatchedSparseBooleanArray watchedSparseBooleanArray2 = new WatchedSparseBooleanArray(watchedSparseBooleanArray);
                watchedSparseBooleanArray2.seal();
                this.webInstantAppsDisabled = watchedSparseBooleanArray2;
                this.instantAppRegistry = (InstantAppRegistry) packageManagerService.mInstantAppRegistry.mSnapshot.snapshot();
                this.androidApplication = packageManagerService.mAndroidApplication != null ? new ApplicationInfo(packageManagerService.mAndroidApplication) : null;
                this.appPredictionServicePackage = packageManagerService.mAppPredictionServicePackage;
                this.appsFilter = packageManagerService.mAppsFilter.snapshot();
                this.componentResolver = (ComponentResolverApi) packageManagerService.mComponentResolver.mSnapshot.snapshot();
                this.frozenPackages = (WatchedArrayMap) packageManagerService.mFrozenPackagesSnapshot.snapshot();
                this.sharedLibraries = (SharedLibrariesImpl) packageManagerService.mSharedLibraries.mSnapshot.snapshot();
            } else {
                if (i != 1) {
                    throw new IllegalArgumentException();
                }
                this.settings = packageManagerService.mSettings;
                this.isolatedOwners = packageManagerService.mIsolatedOwners;
                this.packages = packageManagerService.mPackages;
                this.instrumentation = packageManagerService.mInstrumentation;
                this.resolveComponentName = packageManagerService.mResolveComponentName;
                this.resolveActivity = packageManagerService.mResolveActivity;
                this.instantAppInstallerActivity = packageManagerService.mInstantAppInstallerActivity;
                this.instantAppInstallerInfo = packageManagerService.mInstantAppInstallerInfo;
                this.webInstantAppsDisabled = packageManagerService.mWebInstantAppsDisabled;
                this.instantAppRegistry = packageManagerService.mInstantAppRegistry;
                this.androidApplication = packageManagerService.mAndroidApplication;
                this.appPredictionServicePackage = packageManagerService.mAppPredictionServicePackage;
                this.appsFilter = packageManagerService.mAppsFilter;
                this.componentResolver = packageManagerService.mComponentResolver;
                this.frozenPackages = packageManagerService.mFrozenPackages;
                this.sharedLibraries = packageManagerService.mSharedLibraries;
            }
            this.service = packageManagerService;
        }
    }

    /* renamed from: -$$Nest$menforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions, reason: not valid java name */
    public static void m757x2d683684(PackageManagerService packageManagerService, String str) {
        if (packageManagerService.mContext.checkCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") != 0 && packageManagerService.mContext.checkCallingOrSelfPermission("android.permission.UPGRADE_RUNTIME_PERMISSIONS") != 0) {
            throw new SecurityException(str.concat(" requires android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY or android.permission.UPGRADE_RUNTIME_PERMISSIONS"));
        }
    }

    /* renamed from: -$$Nest$mnotifyPackageUseInternal, reason: not valid java name */
    public static void m758$$Nest$mnotifyPackageUseInternal(PackageManagerService packageManagerService, String str, int i) {
        packageManagerService.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (packageManagerService.mLock) {
            try {
                PackageSetting packageLPr = packageManagerService.mSettings.getPackageLPr(str);
                if (packageLPr == null) {
                    return;
                }
                PackageStateUnserialized packageStateUnserialized = packageLPr.pkgState;
                packageStateUnserialized.getClass();
                if (i >= 0 && i < 8) {
                    packageStateUnserialized.getLastPackageUsageTimeInMills()[i] = currentTimeMillis;
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mresetComponentEnabledSettingsIfNeededLPw, reason: not valid java name */
    public static void m759$$Nest$mresetComponentEnabledSettingsIfNeededLPw(PackageManagerService packageManagerService, String str, final int i) {
        AndroidPackage androidPackage;
        final PackageSetting packageLPr;
        if (str != null) {
            androidPackage = (AndroidPackage) packageManagerService.mPackages.mStorage.get(str);
        } else {
            packageManagerService.getClass();
            androidPackage = null;
        }
        if (androidPackage == null || !androidPackage.isResetEnabledSettingsOnAppDataCleared() || (packageLPr = packageManagerService.mSettings.getPackageLPr(str)) == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        Consumer consumer = new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda71
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageSetting packageSetting = PackageSetting.this;
                int i2 = i;
                ArrayList arrayList2 = arrayList;
                ParsedMainComponent parsedMainComponent = (ParsedMainComponent) obj;
                if (packageSetting.restoreComponentLPw(i2, parsedMainComponent.getClassName())) {
                    arrayList2.add(parsedMainComponent.getClassName());
                }
            }
        };
        for (int i2 = 0; i2 < androidPackage.getActivities().size(); i2++) {
            consumer.accept(androidPackage.getActivities().get(i2));
        }
        for (int i3 = 0; i3 < androidPackage.getReceivers().size(); i3++) {
            consumer.accept(androidPackage.getReceivers().get(i3));
        }
        for (int i4 = 0; i4 < androidPackage.getServices().size(); i4++) {
            consumer.accept(androidPackage.getServices().get(i4));
        }
        for (int i5 = 0; i5 < androidPackage.getProviders().size(); i5++) {
            consumer.accept(androidPackage.getProviders().get(i5));
        }
        if (ArrayUtils.isEmpty(arrayList)) {
            return;
        }
        packageManagerService.updateSequenceNumberLP(packageLPr, new int[]{i});
        packageManagerService.updateInstantAppInstallerLocked(str);
        packageManagerService.scheduleWritePackageRestrictions(i);
        PendingPackageBroadcasts pendingPackageBroadcasts = packageManagerService.mPendingBroadcasts;
        synchronized (pendingPackageBroadcasts.mLock) {
            try {
                ArrayList orAllocate = pendingPackageBroadcasts.getOrAllocate(i, str);
                for (int i6 = 0; i6 < arrayList.size(); i6++) {
                    String str2 = (String) arrayList.get(i6);
                    if (!orAllocate.contains(str2)) {
                        orAllocate.add(str2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (packageManagerService.mHandler.hasMessages(1)) {
            return;
        }
        packageManagerService.mHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    /* renamed from: -$$Nest$msetEnabledSettings, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m760$$Nest$msetEnabledSettings(com.android.server.pm.PackageManagerService r26, java.util.List r27, int r28, java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 1815
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.m760$$Nest$msetEnabledSettings(com.android.server.pm.PackageManagerService, java.util.List, int, java.lang.String):void");
    }

    static {
        boolean z = Build.IS_DEBUGGABLE;
        DEBUG_COMPRESSION = z;
        DEBUG_INSTANT = z;
        EMPTY_INT_ARRAY = new int[0];
        MIN_INSTALLABLE_TARGET_SDK = Flags.minTargetSdk24() ? 24 : 23;
        SYSTEM_PARTITIONS = Collections.unmodifiableList(PackagePartitions.getOrderedPartitions(new PackageManagerService$$ExternalSyntheticLambda1()));
        EMPTY_PER_UID_READ_TIMEOUTS_ARRAY = new PerUidReadTimeouts[0];
        TimeUnit timeUnit = TimeUnit.DAYS;
        DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED = timeUnit.toMillis(1L);
        PRUNE_UNUSED_SHARED_LIBRARIES_DELAY = TimeUnit.MINUTES.toMillis(3L);
        DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD = timeUnit.toMillis(7L);
        new ThreadPriorityBooster(-2, 3);
        sSnapshot = new AtomicReference();
        sSnapshotPendingVersion = new AtomicInteger(1);
        BACKGROUND_HANDLER_CALLBACK = new AnonymousClass2();
        AppCategoryHintHelper appCategoryHintHelper = new AppCategoryHintHelper();
        appCategoryHintHelper.mCategoryMap = new HashMap();
        appCategoryHintHelper.mChangedByUserApp = new ArraySet();
        appCategoryHintHelper.mInit = new AtomicBoolean(false);
        sAppCategoryHintHelper = appCategoryHintHelper;
    }

    public PackageManagerService(PackageManagerServiceInjector packageManagerServiceInjector, PackageManagerServiceTestParams packageManagerServiceTestParams) {
        this.mOverlayPathsLock = new PackageManagerTracedLock(null);
        final int i = 0;
        final int i2 = 1;
        this.mPackageStateMutator = new PackageStateMutator(new Function(this) { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda2
            public final /* synthetic */ PackageManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i3 = i;
                PackageManagerService packageManagerService = this.f$0;
                String str = (String) obj;
                switch (i3) {
                    case 0:
                        return packageManagerService.mSettings.getPackageLPr(str);
                    default:
                        return packageManagerService.mSettings.getDisabledSystemPkgLPr(str);
                }
            }
        }, new Function(this) { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda2
            public final /* synthetic */ PackageManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i3 = i2;
                PackageManagerService packageManagerService = this.f$0;
                String str = (String) obj;
                switch (i3) {
                    case 0:
                        return packageManagerService.mSettings.getPackageLPr(str);
                    default:
                        return packageManagerService.mSettings.getDisabledSystemPkgLPr(str);
                }
            }
        });
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "PackageManagerService.mPackages", 0);
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray();
        this.mIsolatedOwners = watchedSparseIntArray;
        this.mIsolatedOwnersSnapshot = new SnapshotCache.Auto(watchedSparseIntArray, watchedSparseIntArray, "PackageManagerService.mIsolatedOwners", 0);
        this.mExistingPackages = null;
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(0);
        this.mFrozenPackages = watchedArrayMap2;
        this.mFrozenPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "PackageManagerService.mFrozenPackages", 0);
        this.mPackageObserverHelper = new PackageObserverHelper();
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap(0);
        this.mInstrumentation = watchedArrayMap3;
        this.mInstrumentationSnapshot = new SnapshotCache.Auto(watchedArrayMap3, watchedArrayMap3, "PackageManagerService.mInstrumentation", 0);
        this.mTransferredPackages = new ArraySet();
        this.mProtectedBroadcasts = new ArraySet();
        this.mPendingVerification = new SparseArray();
        this.mPendingEnableRollback = new SparseArray();
        this.mNextMoveId = new AtomicInteger();
        this.mPendingVerificationToken = 0;
        this.mPendingEnableRollbackToken = 0;
        this.mWebInstantAppsDisabled = new WatchedSparseBooleanArray();
        this.mResolveActivity = new ActivityInfo();
        this.mResolveInfo = new ResolveInfo();
        this.mPlatformPackageOverlayPaths = null;
        this.mPlatformPackageOverlayResourceDirs = null;
        this.mReplacedResolverPackageOverlayPaths = null;
        this.mReplacedResolverPackageOverlayResourceDirs = null;
        this.mResolverReplaced = false;
        this.mInstantAppInstallerInfo = new ResolveInfo();
        this.mNoKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mPendingKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mKeepUninstalledPackages = new ArraySet();
        this.mDevicePolicyManager = null;
        this.mPackageProperty = new PackageProperty();
        this.mDirtyUsers = new ArraySet();
        this.mRunningInstalls = new SparseArray();
        this.mNextInstallToken = 1;
        this.mPackageUsage = new PackageUsage();
        this.mCompilerStats = new CompilerStats();
        this.mWatcher = new Watcher() { // from class: com.android.server.pm.PackageManagerService.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                PackageManagerService.onChange();
            }
        };
        this.mSnapshotLock = new Object();
        this.mInjector = packageManagerServiceInjector;
        packageManagerServiceInjector.mPackageManager = this;
        this.mAppsFilter = (AppsFilterImpl) packageManagerServiceInjector.mAppsFilterProducer.get(this, packageManagerServiceInjector);
        this.mComponentResolver = (ComponentResolver) packageManagerServiceInjector.mComponentResolverProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mContext = packageManagerServiceInjector.mContext;
        this.mInstaller = packageManagerServiceInjector.mInstaller;
        this.mInstallLock = packageManagerServiceInjector.mInstallLock;
        PackageManagerTracedLock packageManagerTracedLock = packageManagerServiceInjector.mLock;
        this.mLock = packageManagerTracedLock;
        this.mPackageStateWriteLock = packageManagerTracedLock;
        this.mPermissionManager = (PermissionManagerService.PermissionManagerServiceInternalImpl) packageManagerServiceInjector.mPermissionManagerServiceProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mSettings = (Settings) packageManagerServiceInjector.mSettingsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        UserManagerService userManagerService = packageManagerServiceInjector.getUserManagerService();
        this.mUserManager = userManagerService;
        this.mUserNeedsBadging = new UserNeedsBadgingCache(userManagerService);
        this.mDomainVerificationManager = (DomainVerificationManagerInternal) packageManagerServiceInjector.mDomainVerificationManagerInternalProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mHandler = packageManagerServiceInjector.getHandler();
        this.mBackgroundHandler = packageManagerServiceInjector.mBackgroundHandler;
        this.mSharedLibraries = packageManagerServiceInjector.getSharedLibrariesImpl();
        packageManagerServiceTestParams.getClass();
        this.mApexManager = null;
        this.mArtManagerService = null;
        this.mAvailableFeatures = null;
        this.mDefParseFlags = 0;
        this.mDefaultAppProvider = null;
        this.mLegacyPermissionManager = null;
        this.mDexManager = null;
        this.mDynamicCodeLogger = null;
        this.mFactoryTest = false;
        this.mIncrementalManager = null;
        this.mInstallerService = null;
        this.mInstantAppRegistry = null;
        this.mChangedPackagesTracker = packageManagerServiceTestParams.changedPackagesTracker;
        this.mInstantAppResolverConnection = null;
        this.mInstantAppResolverSettingsComponent = null;
        this.mIsPreNMR1Upgrade = false;
        this.mIsPreQUpgrade = false;
        this.mPriorSdkVersion = packageManagerServiceTestParams.priorSdkVersion;
        this.mIsUpgrade = false;
        this.mModuleInfoProvider = null;
        this.mMoveCallbacks = null;
        this.mOverlayConfig = null;
        this.mPackageDexOptimizer = null;
        this.mPackageParserCallback = null;
        this.mPendingBroadcasts = null;
        this.mTestUtilityService = null;
        this.mProcessLoggingHandler = null;
        this.mProtectedPackages = null;
        this.mSeparateProcesses = null;
        this.mRequiredVerifierPackages = null;
        this.mRequiredInstallerPackage = null;
        this.mRequiredUninstallerPackage = null;
        this.mRequiredPermissionControllerPackage = null;
        this.mSetupWizardPackage = null;
        this.mStorageManagerPackage = null;
        this.mDefaultTextClassifierPackage = null;
        this.mSystemTextClassifierPackageName = null;
        this.mRetailDemoPackage = null;
        this.mRecentsPackage = null;
        this.mAmbientContextDetectionPackage = null;
        this.mWearableSensingPackage = null;
        this.mConfiguratorPackage = null;
        this.mAppPredictionServicePackage = null;
        this.mIncidentReportApproverPackage = null;
        this.mServicesExtensionPackageName = null;
        this.mSharedSystemSharedLibraryPackageName = null;
        this.mOverlayConfigSignaturePackage = null;
        this.mResolveComponentName = null;
        this.mRequiredSdkSandboxPackage = null;
        this.mInitialNonStoppedSystemPackages = packageManagerServiceTestParams.initialNonStoppedSystemPackages;
        this.mShouldStopSystemPackagesByDefault = false;
        this.mLiveComputer = new ComputerLocked(new Snapshot(this, 1), -1);
        this.mSnapshotStatistics = null;
        watchedArrayMap.putAll(null);
        throw null;
    }

    public PackageManagerService(PackageManagerServiceInjector packageManagerServiceInjector, boolean z, String str, boolean z2, boolean z3, int i, String str2) {
        ComputerLocked computerLocked;
        ?? r18;
        Throwable th;
        Throwable th2;
        AutoCloseable autoCloseable;
        String str3;
        int i2;
        WatchedArrayMap watchedArrayMap;
        int i3;
        int i4 = 22;
        this.mOverlayPathsLock = new PackageManagerTracedLock();
        final int i5 = 0;
        final int i6 = 1;
        this.mPackageStateMutator = new PackageStateMutator(new Function(this) { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda2
            public final /* synthetic */ PackageManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i32 = i5;
                PackageManagerService packageManagerService = this.f$0;
                String str4 = (String) obj;
                switch (i32) {
                    case 0:
                        return packageManagerService.mSettings.getPackageLPr(str4);
                    default:
                        return packageManagerService.mSettings.getDisabledSystemPkgLPr(str4);
                }
            }
        }, new Function(this) { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda2
            public final /* synthetic */ PackageManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i32 = i6;
                PackageManagerService packageManagerService = this.f$0;
                String str4 = (String) obj;
                switch (i32) {
                    case 0:
                        return packageManagerService.mSettings.getPackageLPr(str4);
                    default:
                        return packageManagerService.mSettings.getDisabledSystemPkgLPr(str4);
                }
            }
        });
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
        this.mPackages = watchedArrayMap2;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "PackageManagerService.mPackages", i5);
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray();
        this.mIsolatedOwners = watchedSparseIntArray;
        this.mIsolatedOwnersSnapshot = new SnapshotCache.Auto(watchedSparseIntArray, watchedSparseIntArray, "PackageManagerService.mIsolatedOwners", i5);
        this.mExistingPackages = null;
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap();
        this.mFrozenPackages = watchedArrayMap3;
        this.mFrozenPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap3, watchedArrayMap3, "PackageManagerService.mFrozenPackages", i5);
        this.mPackageObserverHelper = new PackageObserverHelper();
        WatchedArrayMap watchedArrayMap4 = new WatchedArrayMap();
        this.mInstrumentation = watchedArrayMap4;
        this.mInstrumentationSnapshot = new SnapshotCache.Auto(watchedArrayMap4, watchedArrayMap4, "PackageManagerService.mInstrumentation", i5);
        this.mTransferredPackages = new ArraySet();
        this.mProtectedBroadcasts = new ArraySet();
        this.mPendingVerification = new SparseArray();
        this.mPendingEnableRollback = new SparseArray();
        this.mNextMoveId = new AtomicInteger();
        this.mPendingVerificationToken = 0;
        this.mPendingEnableRollbackToken = 0;
        this.mWebInstantAppsDisabled = new WatchedSparseBooleanArray();
        this.mResolveActivity = new ActivityInfo();
        this.mResolveInfo = new ResolveInfo();
        this.mPlatformPackageOverlayPaths = null;
        this.mPlatformPackageOverlayResourceDirs = null;
        this.mReplacedResolverPackageOverlayPaths = null;
        this.mReplacedResolverPackageOverlayResourceDirs = null;
        this.mResolverReplaced = false;
        this.mInstantAppInstallerInfo = new ResolveInfo();
        this.mNoKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mPendingKillInstallObservers = Collections.synchronizedMap(new HashMap());
        this.mKeepUninstalledPackages = new ArraySet();
        this.mDevicePolicyManager = null;
        this.mPackageProperty = new PackageProperty();
        this.mDirtyUsers = new ArraySet();
        this.mRunningInstalls = new SparseArray();
        this.mNextInstallToken = 1;
        this.mPackageUsage = new PackageUsage();
        this.mCompilerStats = new CompilerStats();
        this.mWatcher = new Watcher() { // from class: com.android.server.pm.PackageManagerService.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                PackageManagerService.onChange();
            }
        };
        this.mSnapshotLock = new Object();
        this.mIsEngBuild = z2;
        this.mIsUserDebugBuild = z3;
        this.mSdkVersion = i;
        this.mIncrementalVersion = str2;
        this.mInjector = packageManagerServiceInjector;
        packageManagerServiceInjector.mSystemWrapper.getClass();
        DefaultSystemWrapper.disablePackageCaches();
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog(262144L, "PackageManagerTiming");
        this.mPendingBroadcasts = new PendingPackageBroadcasts();
        packageManagerServiceInjector.mPackageManager = this;
        PackageManagerTracedLock packageManagerTracedLock = packageManagerServiceInjector.mLock;
        this.mLock = packageManagerTracedLock;
        this.mPackageStateWriteLock = packageManagerTracedLock;
        PackageManagerTracedLock packageManagerTracedLock2 = packageManagerServiceInjector.mInstallLock;
        this.mInstallLock = packageManagerTracedLock2;
        LockGuard.installLock(packageManagerTracedLock, 3, false);
        Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_start");
        EventLog.writeEvent(3060, SystemClock.uptimeMillis());
        Context context = packageManagerServiceInjector.mContext;
        this.mContext = context;
        this.mFactoryTest = z;
        DisplayMetrics displayMetrics = (DisplayMetrics) packageManagerServiceInjector.mDisplayMetricsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        Installer installer = packageManagerServiceInjector.mInstaller;
        this.mInstaller = installer;
        this.mFreeStorageHelper = new FreeStorageHelper(this);
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            this.mMonetizationUtils = MonetizationUtils.getInstance(context);
        }
        timingsTraceAndSlog.traceBegin("createSubComponents");
        sPersonaManager = null;
        List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
        SemPersonaManager.getKnoxInfo();
        sPersonaManager = new PersonaManagerService(context, this, packageManagerTracedLock);
        LocalServices.addService(PackageManagerInternal.class, new PackageManagerInternalImpl());
        LocalManagerRegistry.addManager(PackageManagerLocal.class, new PackageManagerLocalImpl(this));
        PmCustomInjector pmCustomInjector = new PmCustomInjector(packageManagerServiceInjector, this, new PackageManagerService$$ExternalSyntheticLambda9(i4), new PackageManagerService$$ExternalSyntheticLambda44(packageManagerServiceInjector));
        this.mCustomInjector = pmCustomInjector;
        PmLifecycleImpl pmLifecycleImpl = new PmLifecycleImpl(this, packageManagerServiceInjector, pmCustomInjector);
        this.mPmLifecycle = pmLifecycleImpl;
        pmLifecycleImpl.onInstalldStarting();
        LocalServices.addService(TestUtilityService.class, this);
        this.mTestUtilityService = (TestUtilityService) LocalServices.getService(TestUtilityService.class);
        UserManagerService userManagerService = packageManagerServiceInjector.getUserManagerService();
        this.mUserManager = userManagerService;
        UserNeedsBadgingCache userNeedsBadgingCache = new UserNeedsBadgingCache(userManagerService);
        this.mUserNeedsBadging = userNeedsBadgingCache;
        this.mComponentResolver = (ComponentResolver) packageManagerServiceInjector.mComponentResolverProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = (PermissionManagerService.PermissionManagerServiceInternalImpl) packageManagerServiceInjector.mPermissionManagerServiceProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mPermissionManager = permissionManagerServiceInternalImpl;
        Settings settings = (Settings) packageManagerServiceInjector.mSettingsProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mSettings = settings;
        this.mIncrementalManager = (IncrementalManager) packageManagerServiceInjector.mIncrementalManagerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mDefaultAppProvider = (DefaultAppProvider) packageManagerServiceInjector.mDefaultAppProviderProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mLegacyPermissionManager = packageManagerServiceInjector.getLegacyPermissionManagerInternal();
        final PlatformCompat compatibility = packageManagerServiceInjector.getCompatibility();
        this.mPackageParserCallback = new PackageParser2.Callback() { // from class: com.android.server.pm.PackageManagerService.3
            public final Set getHiddenApiWhitelistedApps() {
                return SystemConfig.getInstance().mHiddenApiPackageWhitelist;
            }

            public final Set getInstallConstraintsAllowlist() {
                return SystemConfig.getInstance().mInstallConstraintsAllowlist;
            }

            public final boolean hasFeature(String str4) {
                return PackageManagerService.this.hasSystemFeature(str4, 0);
            }

            public final boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) {
                return compatibility.isChangeEnabled(j, applicationInfo);
            }
        };
        pmLifecycleImpl.onInitPreparing();
        sAppCategoryHintHelper.onInit(this, packageManagerServiceInjector.getHandler(), (AppCategoryFilter) pmCustomInjector.mAppCategoryFilterProducer.get(this, packageManagerServiceInjector));
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("addSharedUsers");
        settings.addSharedUserLPw(1000, 1, 8, "android.uid.system");
        settings.addSharedUserLPw(1001, 1, 8, "android.uid.phone");
        settings.addSharedUserLPw(1007, 1, 8, "android.uid.log");
        settings.addSharedUserLPw(1027, 1, 8, "android.uid.nfc");
        settings.addSharedUserLPw(1002, 1, 8, "android.uid.bluetooth");
        settings.addSharedUserLPw(2000, 1, 8, "android.uid.shell");
        settings.addSharedUserLPw(1068, 1, 8, "android.uid.se");
        settings.addSharedUserLPw(5009, 1, 8, "android.uid.samsungcloud");
        settings.addSharedUserLPw(5006, 1, 8, "android.uid.bcmgr");
        settings.addSharedUserLPw(5004, 1, 8, "android.uid.cmhservice");
        settings.addSharedUserLPw(1073, 1, 8, "android.uid.networkstack");
        settings.addSharedUserLPw(1083, 1, 8, "android.uid.uwb");
        settings.addSharedUserLPw(2918, 262144, 8, "android.uid.vendordata");
        settings.addSharedUserLPw(5278, 1, 8, "android.uid.spass");
        settings.addSharedUserLPw(5279, 1, 8, "android.uid.spay");
        settings.addSharedUserLPw(5021, 1, 8, "android.uid.adaptive_brightness");
        settings.addSharedUserLPw(5017, 1, 8, "android.uid.advmodem");
        settings.addSharedUserLPw(5022, 1, 8, "android.uid.ipsgeofence");
        settings.addSharedUserLPw(5023, 1, 8, "android.uid.networkdiagnostic");
        settings.addSharedUserLPw(5025, 1, 8, "android.uid.mdxkit");
        settings.addSharedUserLPw(5026, 1, 8, "android.uid.sharelive");
        settings.addSharedUserLPw(5554, 1, 8, "android.uid.ker");
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 31) {
            settings.addSharedUserLPw(5010, 1, 8, "android.uid.intelligenceservice");
        }
        timingsTraceAndSlog.traceEnd();
        settings.addSharedUserLPw(5013, 1, 8, "android.uid.nsflp");
        List list2 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
        settings.addSharedUserLPw(5250, 1, 8, "android.uid.knoxcore");
        String str4 = SystemProperties.get("debug.separate_processes");
        if (str4 == null || str4.length() <= 0) {
            this.mDefParseFlags = 0;
            this.mSeparateProcesses = null;
        } else if ("*".equals(str4)) {
            this.mDefParseFlags = 2;
            this.mSeparateProcesses = null;
            Slog.w("PackageManager", "Running with debug.separate_processes: * (ALL)");
        } else {
            this.mDefParseFlags = 0;
            this.mSeparateProcesses = str4.split(",");
            Slog.w("PackageManager", "Running with debug.separate_processes: ".concat(str4));
        }
        EnterprisePartitionManager.setInstaller(installer, packageManagerTracedLock2);
        this.mPackageDexOptimizer = (PackageDexOptimizer) packageManagerServiceInjector.mPackageDexOptimizerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mDexManager = (DexManager) packageManagerServiceInjector.mDexManagerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mDynamicCodeLogger = (DynamicCodeLogger) packageManagerServiceInjector.mDynamicCodeLoggerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mArtManagerService = (ArtManagerService) packageManagerServiceInjector.mArtManagerServiceProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mMoveCallbacks = new MovePackageHelper.MoveCallbacks(FgThread.get().getLooper());
        SharedLibrariesImpl sharedLibrariesImpl = packageManagerServiceInjector.getSharedLibrariesImpl();
        this.mSharedLibraries = sharedLibrariesImpl;
        this.mBackgroundHandler = packageManagerServiceInjector.mBackgroundHandler;
        ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0).getMetrics(displayMetrics);
        timingsTraceAndSlog.traceBegin("get system config");
        SystemConfig systemConfig = packageManagerServiceInjector.getSystemConfig();
        this.mAvailableFeatures = systemConfig.mAvailableFeatures;
        timingsTraceAndSlog.traceEnd();
        ProtectedPackages protectedPackages = new ProtectedPackages(context);
        this.mProtectedPackages = protectedPackages;
        this.mApexManager = (ApexManager) packageManagerServiceInjector.mApexManagerProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        AppsFilterImpl appsFilterImpl = (AppsFilterImpl) packageManagerServiceInjector.mAppsFilterProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mAppsFilter = appsFilterImpl;
        this.mChangedPackagesTracker = new ChangedPackagesTracker();
        this.mAppInstallDir = new File(Environment.getDataDirectory(), "app");
        DomainVerificationConnection domainVerificationConnection = new DomainVerificationConnection(this);
        this.mDomainVerificationConnection = domainVerificationConnection;
        DomainVerificationManagerInternal domainVerificationManagerInternal = (DomainVerificationManagerInternal) packageManagerServiceInjector.mDomainVerificationManagerInternalProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        ((DomainVerificationService) domainVerificationManagerInternal).setConnection(domainVerificationConnection);
        BroadcastHelper broadcastHelper = new BroadcastHelper(packageManagerServiceInjector);
        this.mBroadcastHelper = broadcastHelper;
        this.mPackageMonitorCallbackHelper = (PackageMonitorCallbackHelper) packageManagerServiceInjector.mPackageMonitorCallbackHelper.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
        AppDataHelper appDataHelper = new AppDataHelper(this);
        this.mAppDataHelper = appDataHelper;
        RemovePackageHelper removePackageHelper = new RemovePackageHelper(this, appDataHelper, broadcastHelper);
        this.mRemovePackageHelper = removePackageHelper;
        DeletePackageHelper deletePackageHelper = new DeletePackageHelper(this, removePackageHelper, broadcastHelper);
        this.mDeletePackageHelper = deletePackageHelper;
        this.mInstallPackageHelper = new InstallPackageHelper(this, appDataHelper, removePackageHelper, deletePackageHelper, broadcastHelper);
        InstantAppRegistry instantAppRegistry = new InstantAppRegistry(this.mContext, permissionManagerServiceInternalImpl, this.mInjector.getUserManagerService().mLocalService, deletePackageHelper);
        this.mInstantAppRegistry = instantAppRegistry;
        sharedLibrariesImpl.mDeletePackageHelper = deletePackageHelper;
        PreferredActivityHelper preferredActivityHelper = new PreferredActivityHelper(this, broadcastHelper);
        this.mPreferredActivityHelper = preferredActivityHelper;
        final int i7 = 0;
        final int i8 = 1;
        PreferredActivityHelper preferredActivityHelper2 = preferredActivityHelper;
        this.mResolveIntentHelper = new ResolveIntentHelper(this.mContext, preferredActivityHelper2, packageManagerServiceInjector.getCompatibility(), userManagerService, domainVerificationManagerInternal, userNeedsBadgingCache, new Supplier(this) { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda45
            public final /* synthetic */ PackageManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i9 = i7;
                PackageManagerService packageManagerService = this.f$0;
                switch (i9) {
                    case 0:
                        return packageManagerService.mResolveInfo;
                    default:
                        return packageManagerService.mInstantAppInstallerActivity;
                }
            }
        }, new Supplier(this) { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda45
            public final /* synthetic */ PackageManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                int i9 = i8;
                PackageManagerService packageManagerService = this.f$0;
                switch (i9) {
                    case 0:
                        return packageManagerService.mResolveInfo;
                    default:
                        return packageManagerService.mInstantAppInstallerActivity;
                }
            }
        });
        this.mDexOptHelper = new DexOptHelper(this);
        SuspendPackageHelper suspendPackageHelper = new SuspendPackageHelper(this, this.mInjector, broadcastHelper, protectedPackages);
        this.mSuspendPackageHelper = suspendPackageHelper;
        this.mDistractingPackageHelper = new DistractingPackageHelper(this, broadcastHelper, suspendPackageHelper);
        this.mStorageEventHelper = new StorageEventHelper(this, deletePackageHelper, removePackageHelper);
        synchronized (this.mLock) {
            this.mSnapshotStatistics = new SnapshotStatistics();
            sSnapshotPendingVersion.incrementAndGet();
            computerLocked = new ComputerLocked(new Snapshot(this, 1), -1);
            this.mLiveComputer = computerLocked;
            AnonymousClass1 anonymousClass1 = this.mWatcher;
            WatchedArrayMap watchedArrayMap5 = this.mPackages;
            if (watchedArrayMap5 != null) {
                watchedArrayMap5.registerObserver(anonymousClass1);
            }
            SharedLibrariesImpl sharedLibrariesImpl2 = this.mSharedLibraries;
            if (sharedLibrariesImpl2 != null) {
                sharedLibrariesImpl2.registerObserver(anonymousClass1);
            }
            WatchedArrayMap watchedArrayMap6 = this.mInstrumentation;
            if (watchedArrayMap6 != null) {
                watchedArrayMap6.registerObserver(anonymousClass1);
            }
            WatchedSparseBooleanArray watchedSparseBooleanArray = this.mWebInstantAppsDisabled;
            if (watchedSparseBooleanArray != null) {
                watchedSparseBooleanArray.registerObserver(anonymousClass1);
            }
            if (appsFilterImpl != null) {
                appsFilterImpl.registerObserver(anonymousClass1);
            }
            instantAppRegistry.registerObserver(anonymousClass1);
            Settings settings2 = this.mSettings;
            if (settings2 != null) {
                settings2.registerObserver(anonymousClass1);
            }
            WatchedSparseIntArray watchedSparseIntArray2 = this.mIsolatedOwners;
            if (watchedSparseIntArray2 != null) {
                watchedSparseIntArray2.registerObserver(anonymousClass1);
            }
            ComponentResolver componentResolver = this.mComponentResolver;
            if (componentResolver != null) {
                componentResolver.registerObserver(anonymousClass1);
            }
            WatchedArrayMap watchedArrayMap7 = this.mFrozenPackages;
            if (watchedArrayMap7 != null) {
                watchedArrayMap7.registerObserver(anonymousClass1);
            }
            Watchable.verifyWatchedAttributes(this, anonymousClass1, (this.mIsEngBuild || this.mIsUserDebugBuild) ? false : true);
        }
        this.mPmLifecycle.onInitStarting();
        ?? r1 = this.mInstallLock;
        r1.mLock.lock();
        try {
            synchronized (this.mLock) {
                try {
                    Handler handler = packageManagerServiceInjector.getHandler();
                    this.mHandler = handler;
                    this.mProcessLoggingHandler = new ProcessLoggingHandler();
                    Watchdog.getInstance().addThread(handler, 600000L);
                    this.mAutoDisableHandler = new AutoDisableHandler();
                    ArrayMap arrayMap = systemConfig.mSharedLibraries;
                    int size = arrayMap.size();
                    for (int i9 = 0; i9 < size; i9++) {
                        try {
                            this.mSharedLibraries.addBuiltInSharedLibraryLPw((SystemConfig.SharedLibraryEntry) arrayMap.valueAt(i9));
                        } catch (Throwable th3) {
                            th = th3;
                            th2 = th;
                            r18 = r1;
                            throw th2;
                        }
                    }
                    for (int i10 = 0; i10 < size; i10++) {
                        String str5 = (String) arrayMap.keyAt(i10);
                        SystemConfig.SharedLibraryEntry sharedLibraryEntry = (SystemConfig.SharedLibraryEntry) arrayMap.valueAt(i10);
                        int length = sharedLibraryEntry.dependencies.length;
                        int i11 = 0;
                        while (i11 < length) {
                            int i12 = size;
                            SharedLibraryInfo sharedLibraryInfo = computerLocked.getSharedLibraryInfo(-1L, sharedLibraryEntry.dependencies[i11]);
                            if (sharedLibraryInfo != null) {
                                computerLocked.getSharedLibraryInfo(-1L, str5).addDependency(sharedLibraryInfo);
                            }
                            i11++;
                            size = i12;
                        }
                    }
                    SELinuxMMAC.readInstallPolicy();
                    timingsTraceAndSlog.traceBegin("loadFallbacks");
                    FallbackCategoryProvider.loadFallbacks();
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("read user settings");
                    Settings settings3 = this.mSettings;
                    List usersInternal = UserManagerService.this.getUsersInternal(true, false, false);
                    PmCustomInjector pmCustomInjector2 = this.mCustomInjector;
                    try {
                        boolean z4 = !settings3.readLPw(computerLocked, usersInternal, (PackageManagerBackupController) pmCustomInjector2.mPackageManagerBackupControllerProducer.get(pmCustomInjector2.mPmService, pmCustomInjector2.mInjector));
                        this.mFirstBoot = z4;
                        timingsTraceAndSlog.traceEnd();
                        if (z4) {
                            timingsTraceAndSlog.traceBegin("setFirstBoot: ");
                            try {
                                this.mInstaller.setFirstBoot();
                            } catch (Installer.InstallerException e) {
                                Slog.w("PackageManager", "Could not set First Boot: ", e);
                            }
                            timingsTraceAndSlog.traceEnd();
                        }
                        this.mPermissionManager.readLegacyPermissionsTEMP(this.mSettings.mPermissions);
                        try {
                            PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionStateTEMP();
                            if (this.mFirstBoot) {
                                DexOptHelper.requestCopyPreoptedFiles();
                            }
                            String string = Resources.getSystem().getString(R.string.data_usage_rapid_app_body);
                            if (!TextUtils.isEmpty(string)) {
                                this.mCustomResolverComponentName = ComponentName.unflattenFromString(string);
                            }
                            synchronized (this.mAvailableFeatures) {
                                try {
                                    try {
                                        ArrayMap arrayMap2 = this.mAvailableFeatures;
                                        PmCustomInjector pmCustomInjector3 = this.mCustomInjector;
                                        NfcFeatureManager.updateFeatureAndPackage(arrayMap2, (SkippingApks) pmCustomInjector3.mSkippingApksProducer.get(pmCustomInjector3.mPmService, pmCustomInjector3.mInjector));
                                    } catch (Throwable th4) {
                                        th = th4;
                                        th2 = th;
                                        r18 = preferredActivityHelper2;
                                        throw th2;
                                    }
                                } finally {
                                    th = th;
                                    while (true) {
                                        Throwable th5 = th;
                                        try {
                                        } catch (Throwable th6) {
                                            th = th6;
                                        }
                                    }
                                }
                            }
                            long uptimeMillis = SystemClock.uptimeMillis();
                            Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_system_scan_start");
                            EventLog.writeEvent(3070, uptimeMillis);
                            String str6 = System.getenv("BOOTCLASSPATH");
                            String str7 = System.getenv("SYSTEMSERVERCLASSPATH");
                            if (str6 == null) {
                                Slog.w("PackageManager", "No BOOTCLASSPATH found!");
                            }
                            if (str7 == null) {
                                Slog.w("PackageManager", "No SYSTEMSERVERCLASSPATH found!");
                            }
                            Settings.VersionInfo internalVersion = this.mSettings.getInternalVersion();
                            boolean z5 = !str.equals(internalVersion.fingerprint);
                            this.mIsUpgrade = z5;
                            if (z5) {
                                PackageManagerServiceUtils.logCriticalInfo(4, "Upgrading from " + internalVersion.fingerprint + " (" + internalVersion.buildFingerprint + ") to " + PackagePartitions.FINGERPRINT + " (" + Build.FINGERPRINT + ")");
                            }
                            if (z5 || this.mFirstBoot) {
                                PackageManagerServiceUtils.logCriticalInfo(5, "[PM]Build Info: " + SystemProperties.get("ro.omc.build.id") + PackageManagerShellCommandDataLoader.STDIN_PATH + Build.FINGERPRINT);
                                SystemProperties.set("sys.boot.is_upgrade", "1");
                            }
                            this.mPriorSdkVersion = z5 ? internalVersion.sdkVersion : -1;
                            this.mInitAppsHelper = new InitAppsHelper(this, this.mApexManager, this.mInstallPackageHelper, this.mInjector.getSystemPartitions());
                            this.mPromoteSystemApps = z5 && internalVersion.sdkVersion <= 22;
                            this.mIsPreNMR1Upgrade = z5 && internalVersion.sdkVersion < 25;
                            this.mIsPreQUpgrade = z5 && internalVersion.sdkVersion < 29;
                            WatchedArrayMap watchedArrayMap8 = this.mSettings.mPackages;
                            if (isDeviceUpgrading()) {
                                this.mExistingPackages = new ArraySet(watchedArrayMap8.size());
                                for (int i13 = 0; i13 < watchedArrayMap8.size(); i13++) {
                                    try {
                                        this.mExistingPackages.add(((PackageSetting) watchedArrayMap8.mStorage.valueAt(i13)).getPackageName());
                                    } catch (Throwable th7) {
                                        th = th7;
                                        th2 = th;
                                        r18 = r1;
                                        throw th2;
                                    }
                                }
                                timingsTraceAndSlog.traceBegin("cross profile intent filter update");
                                PackageManagerServiceInjector packageManagerServiceInjector2 = this.mInjector;
                                try {
                                    ((CrossProfileIntentFilterHelper) packageManagerServiceInjector2.mCrossProfileIntentFilterHelperProducer.get(packageManagerServiceInjector2.mPackageManager, packageManagerServiceInjector2)).updateDefaultCrossProfileIntentFilter();
                                    timingsTraceAndSlog.traceEnd();
                                } catch (Throwable th8) {
                                    th = th8;
                                    th2 = th;
                                    r18 = r1;
                                    throw th2;
                                }
                            }
                            File preparePackageParserCache = PackageManagerServiceUtils.preparePackageParserCache(this.mIncrementalVersion, this.mIsEngBuild, this.mIsUserDebugBuild);
                            this.mCacheDir = preparePackageParserCache;
                            this.mInitialNonStoppedSystemPackages = this.mInjector.getSystemConfig().getInitialNonStoppedSystemPackages();
                            this.mShouldStopSystemPackagesByDefault = this.mContext.getResources().getBoolean(R.bool.config_supportsHardwareMicToggle);
                            this.mPmLifecycle.onSystemScanning(preparePackageParserCache);
                            int[] userIds = this.mUserManager.getUserIds();
                            PackageParser2 scanningCachingPackageParser = this.mInjector.getScanningCachingPackageParser();
                            autoCloseable = r1;
                            this.mOverlayConfig = this.mInitAppsHelper.initSystemApps(scanningCachingPackageParser, watchedArrayMap8, userIds, uptimeMillis);
                            this.mPmLifecycle.getClass();
                            this.mInitAppsHelper.initNonSystemApps(scanningCachingPackageParser, userIds, uptimeMillis);
                            scanningCachingPackageParser.close();
                            this.mRequiredVerifierPackages = getRequiredButNotReallyRequiredVerifiersLPr(computerLocked);
                            this.mRequiredInstallerPackage = getRequiredInstallerLPr(computerLocked);
                            this.mRequiredUninstallerPackage = getRequiredUninstallerLPr(computerLocked);
                            this.mRequiredPermissionControllerPackage = getRequiredPermissionControllerLPr(computerLocked);
                            this.mStorageManagerPackage = getStorageManagerPackageName(computerLocked);
                            String setupWizardPackageNameImpl = getSetupWizardPackageNameImpl(computerLocked);
                            this.mSetupWizardPackage = setupWizardPackageNameImpl;
                            this.mComponentResolver.fixProtectedFilterPriorities(setupWizardPackageNameImpl);
                            this.mDefaultTextClassifierPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.face_acquired_sensor_dirty));
                            this.mSystemTextClassifierPackageName = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.description_target_unlock_tablet));
                            this.mConfiguratorPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.disable_accessibility_shortcut));
                            this.mAppPredictionServicePackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.date_and_time));
                            this.mIncidentReportApproverPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(R.string.emailTypeCustom));
                            this.mRetailDemoPackage = getRetailDemoPackageName();
                            this.mOverlayConfigSignaturePackage = ensureSystemPackageName(computerLocked, this.mInjector.getSystemConfig().getOverlayConfigSignaturePackage());
                            this.mRecentsPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.ext_media_status_mounted));
                            this.mAmbientContextDetectionPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.data_usage_wifi_limit_title));
                            this.mWearableSensingPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(R.string.device_storage_monitor_notification_channel));
                            this.mSharedLibraries.updateAllSharedLibrariesLPw(null, null, Collections.unmodifiableMap(this.mPackages));
                            for (SharedUserSetting sharedUserSetting : this.mSettings.getAllSharedUsersLPw()) {
                                ScanPackageUtils.applyAdjustedAbiToSharedUser(sharedUserSetting, null, this.mInjector.mAbiHelper.getAdjustedAbiForSharedUser(sharedUserSetting.mPackages.mStorage, null));
                                sharedUserSetting.fixSeInfoLocked();
                                sharedUserSetting.updateProcesses();
                            }
                            this.mPackageUsage.read(watchedArrayMap8);
                            this.mCompilerStats.read();
                            Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_end");
                            EventLog.writeEvent(3090, SystemClock.uptimeMillis());
                            Slog.i("PackageManager", "Time to scan packages: " + ((SystemClock.uptimeMillis() - uptimeMillis) / 1000.0f) + " seconds");
                            if (this.mIsUpgrade) {
                                Slog.i("PackageManager", "Partitions fingerprint changed from " + internalVersion.fingerprint + " to " + PackagePartitions.FINGERPRINT + "; regranting permissions for internal storage");
                            }
                            try {
                                PermissionManagerService.this.mPermissionManagerServiceImpl.onStorageVolumeMounted(StorageManager.UUID_PRIVATE_INTERNAL, this.mIsUpgrade);
                                internalVersion.sdkVersion = this.mSdkVersion;
                                if (this.mPromoteSystemApps || this.mFirstBoot) {
                                    List users = this.mInjector.getUserManagerService().mLocalService.getUsers(true);
                                    int i14 = 0;
                                    while (true) {
                                        ArrayList arrayList = (ArrayList) users;
                                        if (i14 >= arrayList.size()) {
                                            break;
                                        }
                                        this.mSettings.applyDefaultPreferredAppsLPw(((UserInfo) arrayList.get(i14)).id);
                                        i14++;
                                    }
                                }
                                if (this.mIsUpgrade) {
                                    Slog.i("PackageManager", "Build fingerprint changed; clearing code caches");
                                    for (int i15 = 0; i15 < watchedArrayMap8.size(); i15++) {
                                        PackageSetting packageSetting = (PackageSetting) watchedArrayMap8.mStorage.valueAt(i15);
                                        if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, packageSetting.getVolumeUuid())) {
                                            this.mAppDataHelper.clearAppDataLIF(packageSetting.getPkg(), -1, 131111);
                                        }
                                    }
                                    internalVersion.buildFingerprint = Build.FINGERPRINT;
                                    internalVersion.fingerprint = PackagePartitions.FINGERPRINT;
                                }
                                this.mPrepareAppDataFuture = this.mAppDataHelper.fixAppsDataOnBoot();
                                if (this.mIsPreQUpgrade) {
                                    Slog.i("PackageManager", "Allowlisting all existing apps to hide their icons");
                                    int size2 = watchedArrayMap8.size();
                                    int i16 = 0;
                                    while (i16 < size2) {
                                        PackageSetting packageSetting2 = (PackageSetting) watchedArrayMap8.mStorage.valueAt(i16);
                                        if ((packageSetting2.getFlags() & 1) != 0) {
                                            i2 = size2;
                                            watchedArrayMap = watchedArrayMap8;
                                            i3 = 1;
                                        } else {
                                            String str8 = PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME;
                                            i2 = size2;
                                            watchedArrayMap = watchedArrayMap8;
                                            PackageUserStateImpl modifyUserStateComponents = packageSetting2.modifyUserStateComponents(0, true, false);
                                            try {
                                                WatchedArraySet watchedArraySet = modifyUserStateComponents.mEnabledComponentsWatched;
                                                if ((watchedArraySet != null ? watchedArraySet.remove(str8) : false) | modifyUserStateComponents.mDisabledComponentsWatched.add(str8)) {
                                                    packageSetting2.onChanged$2();
                                                }
                                                i3 = 1;
                                            } catch (Throwable th9) {
                                                th = th9;
                                                th2 = th;
                                                r18 = autoCloseable;
                                                throw th2;
                                            }
                                        }
                                        i16 += i3;
                                        size2 = i2;
                                        watchedArrayMap8 = watchedArrayMap;
                                    }
                                }
                                this.mPromoteSystemApps = false;
                                internalVersion.databaseVersion = 3;
                                timingsTraceAndSlog.traceBegin("write settings");
                                writeSettingsLPrTEMP(false);
                                timingsTraceAndSlog.traceEnd();
                                Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_ready");
                                EventLog.writeEvent(3100, SystemClock.uptimeMillis());
                                ComponentName intentFilterVerifierComponentNameLPr = getIntentFilterVerifierComponentNameLPr(computerLocked);
                                ComponentName domainVerificationAgentComponentNameLPr = getDomainVerificationAgentComponentNameLPr(computerLocked);
                                Context context2 = this.mContext;
                                DomainVerificationManagerInternal domainVerificationManagerInternal2 = this.mDomainVerificationManager;
                                ((DomainVerificationService) this.mDomainVerificationManager).setProxy(DomainVerificationProxy.makeProxy(intentFilterVerifierComponentNameLPr, domainVerificationAgentComponentNameLPr, context2, domainVerificationManagerInternal2, ((DomainVerificationService) domainVerificationManagerInternal2).getCollector(), this.mDomainVerificationConnection));
                                this.mServicesExtensionPackageName = getRequiredServicesExtensionPackageLPr(computerLocked);
                                this.mSharedSystemSharedLibraryPackageName = getRequiredSharedLibrary(computerLocked);
                                this.mSettings.setPermissionControllerVersion(computerLocked.getPackageInfo(this.mRequiredPermissionControllerPackage, 0L, 0).getLongVersionCode());
                                this.mRequiredSdkSandboxPackage = getRequiredSdkSandboxPackageName(computerLocked);
                                this.mRequiredSystemPackages = buildRequiredSystemPackages();
                                int i17 = 0;
                                forEachPackageState(computerLocked, new PackageManagerService$$ExternalSyntheticLambda47(i17, this, userIds));
                                this.mInstallerService = this.mInjector.getPackageInstallerService();
                                ComponentName instantAppResolver = getInstantAppResolver(computerLocked);
                                if (instantAppResolver != null) {
                                    if (DEBUG_INSTANT) {
                                        Slog.d("PackageManager", "Set ephemeral resolver: " + instantAppResolver);
                                    }
                                    this.mInstantAppResolverConnection = this.mInjector.getInstantAppResolverConnection(instantAppResolver);
                                    this.mInstantAppResolverSettingsComponent = getInstantAppResolverSettingsLPr(computerLocked, instantAppResolver);
                                    str3 = null;
                                } else {
                                    str3 = null;
                                    this.mInstantAppResolverConnection = null;
                                    this.mInstantAppResolverSettingsComponent = null;
                                }
                                updateInstantAppInstallerLocked(str3);
                                HashMap hashMap = new HashMap();
                                int length2 = userIds.length;
                                while (i17 < length2) {
                                    int i18 = userIds[i17];
                                    hashMap.put(Integer.valueOf(i18), computerLocked.getInstalledPackages(0L, i18).getList());
                                    i17++;
                                }
                                this.mDexManager.load(hashMap);
                                this.mDynamicCodeLogger.load(hashMap);
                                if (this.mIsUpgrade) {
                                    FrameworkStatsLog.write(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 13, SystemClock.uptimeMillis() - uptimeMillis);
                                }
                                if (this.mFirstBoot || isDeviceUpgrading()) {
                                    for (Map.Entry entry : systemConfig.getAppMetadataFilePaths().entrySet()) {
                                        String str9 = (String) entry.getKey();
                                        String str10 = (String) entry.getValue();
                                        str10 = new File(str10).exists() ? str10 : str3;
                                        PackageSetting disabledSystemPkgLPr = this.mSettings.getDisabledSystemPkgLPr(str9);
                                        if (disabledSystemPkgLPr == null) {
                                            PackageSetting packageLPr = this.mSettings.getPackageLPr(str9);
                                            if (packageLPr != null) {
                                                packageLPr.setAppMetadataFilePath(str10);
                                                if (Flags.aslInApkAppMetadataSource()) {
                                                    packageLPr.setAppMetadataSource(3);
                                                }
                                            } else {
                                                Slog.w("PackageManager", "Cannot set app metadata file for nonexistent package " + str9);
                                            }
                                        } else {
                                            disabledSystemPkgLPr.setAppMetadataFilePath(str10);
                                            if (Flags.aslInApkAppMetadataSource()) {
                                                disabledSystemPkgLPr.setAppMetadataSource(3);
                                            }
                                        }
                                    }
                                }
                            } catch (Throwable th10) {
                                th = th10;
                            }
                            try {
                                this.mLiveComputer = new ComputerLocked(new Snapshot(this, 1), -1);
                                autoCloseable.close();
                                this.mModuleInfoProvider = this.mInjector.getModuleInfoProvider();
                                this.mInjector.mSystemWrapper.getClass();
                                DefaultSystemWrapper.enablePackageCaches();
                                this.mInstaller.setWarnIfHeld(this.mLock);
                                ParsingPackageUtils.readConfigUseRoundIcon(this.mContext.getResources());
                                this.mServiceStartWithDelay = SystemClock.uptimeMillis() + 60000;
                                this.mFrozenPackageInterceptor = new FrozenPackageInterceptor(this.mContext);
                                Slog.i("PackageManager", "Fix for b/169414761 is applied");
                                this.mPmLifecycle.getClass();
                                return;
                            } catch (Throwable th11) {
                                th = th11;
                                th2 = th;
                                r18 = autoCloseable;
                                throw th2;
                            }
                        } catch (Throwable th12) {
                            th = th12;
                            autoCloseable = r1;
                        }
                    } catch (Throwable th13) {
                        th = th13;
                        autoCloseable = r1;
                    }
                } catch (Throwable th14) {
                    th = th14;
                    preferredActivityHelper2 = r1;
                }
            }
        } catch (Throwable th15) {
            th = th15;
            r18 = r1;
        }
        try {
            throw th2;
        } catch (Throwable th16) {
            th = th16;
            try {
                r18.close();
                throw th;
            } catch (Throwable th17) {
                th.addSuppressed(th17);
                throw th;
            }
        }
    }

    public static Certificate[] decodeCertificates(List list) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate[] certificateArr = new Certificate[list.size()];
            int size = list.size();
            for (int i = 0; i < size; i++) {
                certificateArr[i] = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream((byte[]) list.get(i)));
            }
            return certificateArr;
        } catch (CertificateException e) {
            throw ExceptionUtils.propagate(e);
        }
    }

    public static long deleteOatArtifactsOfPackage(String str) {
        boolean z = PackageManagerServiceUtils.DEBUG;
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Only the system or shell can delete oat artifacts");
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            try {
                long freedBytes = DexOptHelper.getArtManagerLocal().deleteDexoptArtifacts(withFilteredSnapshot, str).getFreedBytes();
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                }
                return freedBytes;
            } catch (IllegalArgumentException e) {
                Log.e("PackageManager", e.toString());
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                }
                return -1L;
            } catch (IllegalStateException e2) {
                Slog.wtfStack("PackageManager", e2.toString());
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                }
                return -1L;
            }
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

    public static void enforceOwnerRights(Computer computer, String str, int i) {
        if (UserHandle.getAppId(i) == 1000 || UserHandle.getAppId(i) == 5250) {
            return;
        }
        if (!ArrayUtils.contains(computer.getPackagesForUid(i), str)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "Calling uid ", " does not own package ", str));
        }
        int userId = UserHandle.getUserId(i);
        if (computer.getPackageInfo(str, 0L, userId) == null) {
            throw new IllegalArgumentException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(userId, "Unknown package ", str, " on user "));
        }
    }

    public static String ensureSystemPackageName(Computer computer, String str) {
        if (str == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (computer.getPackageInfo(str, 2097152L, 0) != null) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return str;
            }
            PackageInfo packageInfo = computer.getPackageInfo(str, 0L, 0);
            if (packageInfo != null) {
                EventLog.writeEvent(1397638484, "145981139", Integer.valueOf(packageInfo.applicationInfo.uid), "");
            }
            StringBuilder sb = new StringBuilder("Missing required system package: ");
            sb.append(str);
            sb.append(packageInfo != null ? ", but found with extended search." : ".");
            Log.w("PackageManager", sb.toString());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static Bundle extrasForInstallResult(InstallRequest installRequest) {
        Bundle bundle;
        int i = installRequest.mReturnCode;
        if (i != -112) {
            if (i != 1) {
                bundle = null;
            } else {
                bundle = new Bundle();
                PackageRemovedInfo packageRemovedInfo = installRequest.mRemovedInfo;
                bundle.putBoolean("android.intent.extra.REPLACING", (packageRemovedInfo == null || packageRemovedInfo.mRemovedPackage == null) ? false : true);
            }
        } else {
            bundle = new Bundle();
            bundle.putString("android.content.pm.extra.FAILURE_EXISTING_PERMISSION", installRequest.mOrigPermission);
            bundle.putString("android.content.pm.extra.FAILURE_EXISTING_PACKAGE", installRequest.mOrigPackage);
        }
        if (!installRequest.mWarnings.isEmpty()) {
            bundle.putStringArrayList("android.content.pm.extra.WARNINGS", installRequest.mWarnings);
        }
        return bundle;
    }

    public static void forEachPackageState(Computer computer, Consumer consumer) {
        ArrayMap packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((PackageStateInternal) packageStates.valueAt(i));
        }
    }

    public static ComponentName getInstantAppResolverSettingsLPr(ComputerLocked computerLocked, ComponentName componentName) {
        List queryIntentActivitiesInternal = computerLocked.queryIntentActivitiesInternal(new Intent("android.intent.action.INSTANT_APP_RESOLVER_SETTINGS").addCategory("android.intent.category.DEFAULT").setPackage(componentName.getPackageName()), null, 786432L, 0);
        if (queryIntentActivitiesInternal.isEmpty()) {
            return null;
        }
        return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().getComponentName();
    }

    public static String getRequiredSdkSandboxPackageName(ComputerLocked computerLocked) {
        List queryIntentServicesInternal = computerLocked.queryIntentServicesInternal(new Intent("com.android.sdksandbox.SdkSandboxService"), null, 1835008L, 0, Process.myUid(), -1, false);
        if (queryIntentServicesInternal.size() == 1) {
            return ((ResolveInfo) queryIntentServicesInternal.get(0)).getComponentInfo().packageName;
        }
        throw new RuntimeException("There should exactly one sdk sandbox package; found " + queryIntentServicesInternal.size() + ": matches=" + queryIntentServicesInternal);
    }

    public static String getRequiredSharedLibrary(ComputerLocked computerLocked) {
        SharedLibraryInfo sharedLibraryInfo = computerLocked.mSharedLibraries.getSharedLibraryInfo(-1, "android.ext.shared");
        if (sharedLibraryInfo == null) {
            throw new IllegalStateException("Missing required shared library:android.ext.shared");
        }
        String packageName = sharedLibraryInfo.getPackageName();
        if (packageName != null) {
            return packageName;
        }
        throw new IllegalStateException("Expected a package for shared library android.ext.shared");
    }

    public static String getSetupWizardPackageNameImpl(ComputerLocked computerLocked) {
        List queryIntentActivitiesInternal = computerLocked.queryIntentActivitiesInternal(PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MAIN", "android.intent.category.SETUP_WIZARD"), null, 1835520L, UserHandle.myUserId());
        if (queryIntentActivitiesInternal.size() == 1) {
            return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
        }
        Slog.e("PackageManager", "There should probably be exactly one setup wizard; found " + queryIntentActivitiesInternal.size() + ": matches=" + queryIntentActivitiesInternal);
        return null;
    }

    public static String getStorageManagerPackageName(ComputerLocked computerLocked) {
        List queryIntentActivitiesInternal = computerLocked.queryIntentActivitiesInternal(new Intent("android.os.storage.action.MANAGE_STORAGE"), null, 1835520L, UserHandle.myUserId());
        if (queryIntentActivitiesInternal.size() == 1) {
            return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
        }
        Slog.w("PackageManager", "There should probably be exactly one storage manager; found " + queryIntentActivitiesInternal.size() + ": matches=" + queryIntentActivitiesInternal);
        return null;
    }

    public static boolean hasZippedOverlays(PackageSetting packageSetting) {
        Bundle metaData;
        AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        if (androidPackageInternal == null || (metaData = androidPackageInternal.getMetaData()) == null) {
            return false;
        }
        return metaData.getBoolean("com.samsung.android.hasZippedOverlays");
    }

    public static void invalidatePackageInfoCache() {
        PackageManager.invalidatePackageInfoCache();
        onChange();
    }

    public static void killApplication(String str, int i, int i2, String str2, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                try {
                    service.killApplication(str, i, i2, str2, i3);
                } catch (RemoteException unused) {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static PackageManagerService main(final Context context, final Installer installer, final DomainVerificationService domainVerificationService, boolean z) {
        String systemPropertyName;
        int i = 16;
        int i2 = 0;
        int i3 = 1;
        String[] strArr = PackageManagerServiceCompilerMapping.REASON_STRINGS;
        IllegalStateException illegalStateException = null;
        for (int i4 = 0; i4 <= 14; i4++) {
            try {
                systemPropertyName = PackageManagerServiceCompilerMapping.getSystemPropertyName(i4);
            } catch (Exception e) {
                if (illegalStateException == null) {
                    illegalStateException = new IllegalStateException("PMS compiler filter settings are bad.");
                }
                illegalStateException.addSuppressed(e);
            }
            if (systemPropertyName == null || systemPropertyName.isEmpty()) {
                throw new IllegalStateException("Reason system property name \"" + systemPropertyName + "\" for reason " + PackageManagerServiceCompilerMapping.REASON_STRINGS[i4]);
            }
            PackageManagerServiceCompilerMapping.getAndCheckValidity(i4);
        }
        if (illegalStateException != null) {
            throw illegalStateException;
        }
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog(262144L, "PackageManagerTiming");
        timingsTraceAndSlog.traceBegin("create package manager");
        final PackageManagerTracedLock packageManagerTracedLock = new PackageManagerTracedLock("mLock");
        final PackageManagerTracedLock packageManagerTracedLock2 = new PackageManagerTracedLock("mInstallLock");
        final Handler handler = new Handler(Watchdog$$ExternalSyntheticOutline0.m(10, "PackageManagerBg", true).getLooper(), BACKGROUND_HANDLER_CALLBACK);
        PackageAbiHelperImpl packageAbiHelperImpl = new PackageAbiHelperImpl();
        List list = SYSTEM_PARTITIONS;
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda9 = new PackageManagerService$$ExternalSyntheticLambda9(i2);
        PackageManagerService$$ExternalSyntheticLambda13 packageManagerService$$ExternalSyntheticLambda13 = new PackageManagerService$$ExternalSyntheticLambda13(context, i3);
        ?? r14 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda30
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector) {
                Context context2 = context;
                UserDataPreparer userDataPreparer = new UserDataPreparer(installer, packageManagerTracedLock2, context2);
                PersonaManagerService personaManagerService = PackageManagerService.sPersonaManager;
                return new UserManagerService(context2, packageManagerService, userDataPreparer, packageManagerTracedLock, Environment.getDataDirectory(), null, personaManagerService);
            }
        };
        ?? r3 = new PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda33
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final Object produce(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector) {
                return new Settings(Environment.getDataDirectory(), RuntimePermissionsPersistence.createInstance(), (PermissionManagerService.PermissionManagerServiceInternalImpl) packageManagerServiceInjector.mPermissionManagerServiceProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector), DomainVerificationService.this, handler, packageManagerTracedLock);
            }
        };
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda92 = new PackageManagerService$$ExternalSyntheticLambda9(i);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda93 = new PackageManagerService$$ExternalSyntheticLambda9(17);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda94 = new PackageManagerService$$ExternalSyntheticLambda9(18);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda95 = new PackageManagerService$$ExternalSyntheticLambda9(19);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda96 = new PackageManagerService$$ExternalSyntheticLambda9(20);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda97 = new PackageManagerService$$ExternalSyntheticLambda9(21);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda98 = new PackageManagerService$$ExternalSyntheticLambda9(1);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda99 = new PackageManagerService$$ExternalSyntheticLambda9(2);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda910 = new PackageManagerService$$ExternalSyntheticLambda9(3);
        PackageManagerService$$ExternalSyntheticLambda13 packageManagerService$$ExternalSyntheticLambda132 = new PackageManagerService$$ExternalSyntheticLambda13(context, 0);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda911 = new PackageManagerService$$ExternalSyntheticLambda9(4);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda912 = new PackageManagerService$$ExternalSyntheticLambda9(5);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda913 = new PackageManagerService$$ExternalSyntheticLambda9(6);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda914 = new PackageManagerService$$ExternalSyntheticLambda9(7);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda915 = new PackageManagerService$$ExternalSyntheticLambda9(8);
        VcnManagementService$$ExternalSyntheticLambda10 vcnManagementService$$ExternalSyntheticLambda10 = new VcnManagementService$$ExternalSyntheticLambda10();
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda916 = new PackageManagerService$$ExternalSyntheticLambda9(9);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda917 = new PackageManagerService$$ExternalSyntheticLambda9(10);
        PackageManagerService$$ExternalSyntheticLambda22 packageManagerService$$ExternalSyntheticLambda22 = new PackageManagerService$$ExternalSyntheticLambda22(domainVerificationService);
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda918 = new PackageManagerService$$ExternalSyntheticLambda9(11);
        DefaultSystemWrapper defaultSystemWrapper = new DefaultSystemWrapper();
        PackageManagerService$$ExternalSyntheticLambda9 packageManagerService$$ExternalSyntheticLambda919 = new PackageManagerService$$ExternalSyntheticLambda9(23);
        Objects.requireNonNull(context);
        PackageManagerServiceInjector packageManagerServiceInjector = new PackageManagerServiceInjector(context, packageManagerTracedLock, installer, packageManagerTracedLock2, packageAbiHelperImpl, handler, list, packageManagerService$$ExternalSyntheticLambda9, packageManagerService$$ExternalSyntheticLambda13, r14, r3, packageManagerService$$ExternalSyntheticLambda92, packageManagerService$$ExternalSyntheticLambda93, packageManagerService$$ExternalSyntheticLambda94, packageManagerService$$ExternalSyntheticLambda95, packageManagerService$$ExternalSyntheticLambda96, packageManagerService$$ExternalSyntheticLambda97, packageManagerService$$ExternalSyntheticLambda98, packageManagerService$$ExternalSyntheticLambda99, packageManagerService$$ExternalSyntheticLambda910, packageManagerService$$ExternalSyntheticLambda132, packageManagerService$$ExternalSyntheticLambda911, packageManagerService$$ExternalSyntheticLambda912, packageManagerService$$ExternalSyntheticLambda913, packageManagerService$$ExternalSyntheticLambda914, packageManagerService$$ExternalSyntheticLambda915, vcnManagementService$$ExternalSyntheticLambda10, packageManagerService$$ExternalSyntheticLambda916, packageManagerService$$ExternalSyntheticLambda917, packageManagerService$$ExternalSyntheticLambda22, packageManagerService$$ExternalSyntheticLambda918, defaultSystemWrapper, packageManagerService$$ExternalSyntheticLambda919, new PackageManagerService$$ExternalSyntheticLambda13(context, 3), new PackageManagerService$$ExternalSyntheticLambda9(12), new PackageManagerService$$ExternalSyntheticLambda9(13), new PackageManagerService$$ExternalSyntheticLambda13(context, 2), new PackageManagerService$$ExternalSyntheticLambda9(14), new PackageManagerService$$ExternalSyntheticLambda9(15));
        PackageManagerService packageManagerService = new PackageManagerService(packageManagerServiceInjector, z, PackagePartitions.FINGERPRINT, Build.IS_ENG, Build.IS_USERDEBUG, Build.VERSION.SDK_INT, Build.VERSION.INCREMENTAL);
        timingsTraceAndSlog.traceEnd();
        PackageManagerService$$ExternalSyntheticLambda57 packageManagerService$$ExternalSyntheticLambda57 = new PackageManagerService$$ExternalSyntheticLambda57(packageManagerService, 4);
        packageManagerServiceInjector.getCompatibility().registerListener(143539591L, packageManagerService$$ExternalSyntheticLambda57);
        packageManagerServiceInjector.getCompatibility().registerListener(168782947L, packageManagerService$$ExternalSyntheticLambda57);
        boolean isDeviceUpgrading = packageManagerService.isDeviceUpgrading();
        final ArraySet arraySet = packageManagerService.mExistingPackages;
        UserManagerService userManagerService = packageManagerService.mUserManager;
        UserSystemPackageInstaller userSystemPackageInstaller = userManagerService.mSystemPackageInstaller;
        boolean z2 = packageManagerService.mFirstBoot || userManagerService.mUpdatingSystemUserMode;
        userSystemPackageInstaller.getClass();
        int whitelistMode = UserSystemPackageInstaller.getWhitelistMode();
        int i5 = whitelistMode & 2;
        if ((i5 != 0) || (whitelistMode & 1) != 0) {
            Slog.v("UserSystemPackageInstaller", "Checking that all system packages are whitelisted.");
            ArrayList arrayList = (ArrayList) userSystemPackageInstaller.getPackagesWhitelistWarnings();
            int size = arrayList.size();
            if (size == 0) {
                Slog.v("UserSystemPackageInstaller", "checkWhitelistedSystemPackages(mode=" + UserSystemPackageInstaller.modeToString(whitelistMode) + ") has no warnings");
            } else {
                Slog.w("UserSystemPackageInstaller", "checkWhitelistedSystemPackages(mode=" + UserSystemPackageInstaller.modeToString(whitelistMode) + ") has " + size + " warnings:");
                for (int i6 = 0; i6 < size; i6++) {
                    Slog.w("UserSystemPackageInstaller", (String) arrayList.get(i6));
                }
            }
            int i7 = 4 & whitelistMode;
            if (i7 == 0 || i5 != 0) {
                List packagesWhitelistErrors = userSystemPackageInstaller.getPackagesWhitelistErrors(whitelistMode);
                int size2 = packagesWhitelistErrors.size();
                if (size2 == 0) {
                    Slog.v("UserSystemPackageInstaller", "checkWhitelistedSystemPackages(mode=" + UserSystemPackageInstaller.modeToString(whitelistMode) + ") has no errors");
                } else {
                    Slog.e("UserSystemPackageInstaller", "checkWhitelistedSystemPackages(mode=" + UserSystemPackageInstaller.modeToString(whitelistMode) + ") has " + size2 + " errors:");
                    boolean z3 = !(i7 != 0);
                    for (int i8 = 0; i8 < size2; i8++) {
                        String str = (String) packagesWhitelistErrors.get(i8);
                        if (z3) {
                            Slog.wtf("UserSystemPackageInstaller", str);
                        } else {
                            Slog.e("UserSystemPackageInstaller", str);
                        }
                    }
                }
            }
        }
        boolean z4 = isDeviceUpgrading && (whitelistMode & 16) == 0;
        if ((z4 || z2) && (!z2 || (whitelistMode & 1) != 0)) {
            StringBuilder sb = new StringBuilder("Reviewing whitelisted packages due to ");
            sb.append(z2 ? "[firstBoot]" : "");
            sb.append(z4 ? "[upgrade]" : "");
            Slog.i("UserSystemPackageInstaller", sb.toString());
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            final SparseArrayMap sparseArrayMap = new SparseArrayMap();
            UserManagerService userManagerService2 = userSystemPackageInstaller.mUm;
            int[] userIds = userManagerService2.getUserIds();
            int i9 = 0;
            for (int length = userIds.length; i9 < length; length = length) {
                final int i10 = userIds[i9];
                final Set installablePackagesForUserType = userSystemPackageInstaller.getInstallablePackagesForUserType(userManagerService2.getUserInfo(i10).userType);
                final boolean z5 = z2;
                final boolean z6 = z4;
                packageManagerInternal.forEachPackageState(new Consumer() { // from class: com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Set set = installablePackagesForUserType;
                        int i11 = i10;
                        boolean z7 = z5;
                        boolean z8 = z6;
                        ArraySet arraySet2 = arraySet;
                        SparseArrayMap sparseArrayMap2 = sparseArrayMap;
                        PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                        if (packageStateInternal.getPkg() == null || !packageStateInternal.isSystem()) {
                            return;
                        }
                        boolean z9 = (set == null || set.contains(packageStateInternal.getPackageName())) && !packageStateInternal.getTransientState().hiddenUntilInstalled;
                        if (packageStateInternal.getUserStateOrDefault(i11).isInstalled() != z9) {
                            if (z9) {
                                if (packageStateInternal.getUserStateOrDefault(i11).getUninstallReason() != 1) {
                                    return;
                                }
                            } else {
                                if (z7 && !packageStateInternal.isSystem()) {
                                    return;
                                }
                                if (!z7 && (!z8 || arraySet2.contains(packageStateInternal.getPackageName()))) {
                                    return;
                                }
                            }
                            sparseArrayMap2.add(i11, packageStateInternal.getPackageName(), Boolean.valueOf(z9));
                        }
                    }
                });
                i9++;
                userSystemPackageInstaller = userSystemPackageInstaller;
            }
            ((PackageManagerInternalImpl) packageManagerInternal).mService.commitPackageStateMutation(new Consumer() { // from class: com.android.server.pm.UserSystemPackageInstaller$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SparseArrayMap sparseArrayMap2 = sparseArrayMap;
                    PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                    for (int i11 = 0; i11 < sparseArrayMap2.numMaps(); i11++) {
                        int keyAt = sparseArrayMap2.keyAt(i11);
                        int numElementsForKey = sparseArrayMap2.numElementsForKey(keyAt);
                        for (int i12 = 0; i12 < numElementsForKey; i12++) {
                            String str2 = (String) sparseArrayMap2.keyAt(i11, i12);
                            boolean booleanValue = ((Boolean) sparseArrayMap2.valueAt(i11, i12)).booleanValue();
                            PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper userState = packageStateMutator.forPackage(str2).userState(keyAt);
                            PackageUserStateImpl packageUserStateImpl = userState.mUserState;
                            if (packageUserStateImpl != null) {
                                packageUserStateImpl.setBoolean$1(1, booleanValue);
                                packageUserStateImpl.onChanged$4();
                            }
                            int i13 = !booleanValue ? 1 : 0;
                            PackageUserStateImpl packageUserStateImpl2 = userState.mUserState;
                            if (packageUserStateImpl2 != null) {
                                packageUserStateImpl2.mUninstallReason = i13;
                                AnnotationValidations.validate(PackageManager.UninstallReason.class, (Annotation) null, i13);
                                packageUserStateImpl2.onChanged$4();
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(booleanValue ? "Installed " : "Uninstalled ");
                            sb2.append(str2);
                            sb2.append(" for user ");
                            sb2.append(keyAt);
                            Slog.i("UserSystemPackageInstallerCommitDebug", sb2.toString());
                        }
                    }
                }
            });
            packageManagerService.scheduleWritePackageRestrictions(-1);
            packageManagerService.scheduleWriteSettings();
        }
        ServiceManager.addService("package", packageManagerService.new IPackageManagerImpl());
        ServiceManager.addService("package_native", new PackageManagerNative(packageManagerService));
        return packageManagerService;
    }

    public static void onChange() {
        sSnapshotPendingVersion.incrementAndGet();
    }

    public static void renameStaticSharedLibraryPackage(ParsedPackage parsedPackage) {
        parsedPackage.setPackageName(parsedPackage.getPackageName() + "_" + parsedPackage.getStaticSharedLibraryVersion());
    }

    public final void addAllPackageProperties(AndroidPackage androidPackage) {
        PackageProperty packageProperty = this.mPackageProperty;
        packageProperty.getClass();
        packageProperty.mApplicationProperties = PackageProperty.addProperties(androidPackage.getProperties(), packageProperty.mApplicationProperties);
        packageProperty.mActivityProperties = PackageProperty.addComponentProperties(androidPackage.getActivities(), packageProperty.mActivityProperties);
        packageProperty.mProviderProperties = PackageProperty.addComponentProperties(androidPackage.getProviders(), packageProperty.mProviderProperties);
        packageProperty.mReceiverProperties = PackageProperty.addComponentProperties(androidPackage.getReceivers(), packageProperty.mReceiverProperties);
        packageProperty.mServiceProperties = PackageProperty.addComponentProperties(androidPackage.getServices(), packageProperty.mServiceProperties);
    }

    public final void addCrossProfileIntentFilter(Computer computer, WatchedIntentFilter watchedIntentFilter, String str, int i, int i2, int i3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        int callingUid = Binder.getCallingUid();
        enforceOwnerRights(computer, str, callingUid);
        if (!this.mUserManager.isCrossProfileIntentFilterAccessible(i, i2, true)) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUid, "CrossProfileIntentFilter cannot be accessed by user "));
        }
        PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerService().mLocalService, callingUid, i);
        if (!watchedIntentFilter.mFilter.checkDataPathAndSchemeSpecificParts()) {
            EventLog.writeEvent(1397638484, "246749936", Integer.valueOf(callingUid));
            throw new IllegalArgumentException("Invalid intent data paths or scheme specific parts in the filter.");
        }
        if (watchedIntentFilter.mFilter.countActions() == 0) {
            Slog.w("PackageManager", "Cannot set a crossProfile intent filter with no filter actions");
            return;
        }
        synchronized (this.mLock) {
            try {
                UserManagerService userManagerService = this.mUserManager;
                UserProperties userPropertiesInternal = userManagerService.getUserPropertiesInternal(i);
                int crossProfileIntentFilterAccessControl = userPropertiesInternal != null ? userPropertiesInternal.getCrossProfileIntentFilterAccessControl() : 0;
                UserProperties userPropertiesInternal2 = userManagerService.getUserPropertiesInternal(i2);
                CrossProfileIntentFilter crossProfileIntentFilter = new CrossProfileIntentFilter(watchedIntentFilter, str, i2, i3, Math.max(crossProfileIntentFilterAccessControl, userPropertiesInternal2 != null ? userPropertiesInternal2.getCrossProfileIntentFilterAccessControl() : 0));
                CrossProfileIntentResolver editCrossProfileIntentResolverLPw = this.mSettings.editCrossProfileIntentResolverLPw(i);
                ArrayList findFilters = editCrossProfileIntentResolverLPw.findFilters(watchedIntentFilter);
                if (findFilters != null) {
                    int size = findFilters.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        CrossProfileIntentFilter crossProfileIntentFilter2 = (CrossProfileIntentFilter) findFilters.get(i4);
                        if (crossProfileIntentFilter.mTargetUserId == crossProfileIntentFilter2.mTargetUserId && crossProfileIntentFilter.mOwnerPackage.equals(crossProfileIntentFilter2.mOwnerPackage) && crossProfileIntentFilter.mFlags == crossProfileIntentFilter2.mFlags && crossProfileIntentFilter.mAccessControlLevel == crossProfileIntentFilter2.mAccessControlLevel) {
                            return;
                        }
                    }
                }
                editCrossProfileIntentResolverLPw.addFilter((PackageDataSnapshot) snapshotComputer(), (WatchedIntentFilter) crossProfileIntentFilter);
                scheduleWritePackageRestrictions(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void applyUpdatedSystemOverlayPaths() {
        ApplicationInfo applicationInfo = this.mAndroidApplication;
        if (applicationInfo == null) {
            Slog.i("PackageManager", "Skipped the AndroidApplication overlay paths update - no app yet");
        } else {
            applicationInfo.overlayPaths = this.mPlatformPackageOverlayPaths;
            applicationInfo.resourceDirs = this.mPlatformPackageOverlayResourceDirs;
        }
        if (this.mResolverReplaced) {
            ApplicationInfo applicationInfo2 = this.mResolveActivity.applicationInfo;
            applicationInfo2.overlayPaths = this.mReplacedResolverPackageOverlayPaths;
            applicationInfo2.resourceDirs = this.mReplacedResolverPackageOverlayResourceDirs;
        }
    }

    public final Set buildRequiredSystemPackages() {
        Set set = this.mInjector.getSystemConfig().mRequiredSystemPackages;
        set.add(this.mRequiredInstallerPackage);
        set.add(this.mRequiredUninstallerPackage);
        set.add(this.mRequiredPermissionControllerPackage);
        set.add(this.mRequiredSdkSandboxPackage);
        String replaceAll = SemCscFeature.getInstance().getString("CscFeature_Framework_ConfigRequiredSystemPackages", "").replaceAll(" ", "");
        if (!replaceAll.isEmpty()) {
            for (String str : replaceAll.split(",")) {
                set.add(str);
            }
        }
        return set;
    }

    public final int checkPermission(String str, String str2, int i) {
        return PermissionManagerService.this.checkPermission(str2, str, "default:0", i);
    }

    public final void cleanUpUser(UserManagerService userManagerService, final int i) {
        synchronized (this.mLock) {
            synchronized (this.mDirtyUsers) {
                this.mDirtyUsers.remove(Integer.valueOf(i));
            }
            UserNeedsBadgingCache userNeedsBadgingCache = this.mUserNeedsBadging;
            synchronized (userNeedsBadgingCache.mLock) {
                userNeedsBadgingCache.mUserCache.delete(i);
            }
            this.mDeletePackageHelper.removeUnusedPackagesLPw(userManagerService, i);
            this.mSettings.removeUserLPw(i);
            PendingPackageBroadcasts pendingPackageBroadcasts = this.mPendingBroadcasts;
            synchronized (pendingPackageBroadcasts.mLock) {
                pendingPackageBroadcasts.mUidMap.remove(i);
            }
            this.mAppsFilter.onUserDeleted(snapshotComputer(), i);
            PermissionManagerService.this.mPermissionManagerServiceImpl.onUserRemoved(i);
        }
        this.mInstantAppRegistry.onUserRemoved(i);
        this.mPackageMonitorCallbackHelper.onUserRemoved(i);
        if (android.app.admin.flags.Flags.crossUserSuspensionEnabledRo()) {
            Computer snapshotComputer = snapshotComputer();
            String[] allAvailablePackageNames = snapshotComputer.getAllAvailablePackageNames();
            for (int i2 : this.mUserManager.getUserIds()) {
                if (i2 != i) {
                    this.mSuspendPackageHelper.removeSuspensionsBySuspendingPackage(snapshotComputer, allAvailablePackageNames, new Predicate() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return ((UserPackage) obj).userId == i;
                        }
                    }, i2);
                }
            }
        }
    }

    public final boolean clearApplicationUserDataLIF(Computer computer, String str, int i) {
        SpegService spegService;
        int i2 = 0;
        if (str == null) {
            Slog.w("PackageManager", "Attempt to delete null packageName.");
            return false;
        }
        AndroidPackage androidPackage = computer.getPackage(str);
        if (androidPackage == null) {
            PinnerService$$ExternalSyntheticOutline0.m("Package named '", str, "' doesn't exist.", "PackageManager");
            return false;
        }
        if (CoreRune.SYSFW_APP_SPEG && (spegService = this.mSpeg) != null && spegService.isSpegInOpeartion(str)) {
            Slog.d("SPEG", "Skip permissions reset");
        } else {
            PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissions(androidPackage, i);
        }
        AppDataHelper appDataHelper = this.mAppDataHelper;
        appDataHelper.clearAppDataLIF(androidPackage, i, 7);
        appDataHelper.clearKeystoreData(i, UserHandle.getAppId(androidPackage.getUid()));
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        UserManagerService.LocalService localService = packageManagerServiceInjector.getUserManagerService().mLocalService;
        StorageManagerInternal storageManagerInternal = (StorageManagerInternal) packageManagerServiceInjector.mGetLocalServiceProducer.produce(StorageManagerInternal.class);
        if (StorageManager.isCeStorageUnlocked(i) && storageManagerInternal.isCeStoragePrepared(i)) {
            i2 = 3;
        } else if (localService.isUserRunning(i)) {
            i2 = 1;
        }
        appDataHelper.prepareAppDataContentsLeafLIF(androidPackage, computer.getPackageStateInternal(str), i, i2);
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void clearPackagePreferredActivitiesLPw(java.lang.String r11, android.util.SparseBooleanArray r12, int r13) {
        /*
            r10 = this;
            com.android.server.pm.Settings r10 = r10.mSettings
            r10.getClass()
            java.lang.String r0 = "kioskmode"
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)
            com.samsung.android.knox.kiosk.IKioskMode r0 = com.samsung.android.knox.kiosk.IKioskMode.Stub.asInterface(r0)
            r1 = 0
            if (r0 == 0) goto L29
            java.lang.String r0 = r0.getKioskHomePackageAsUser(r13)     // Catch: android.os.RemoteException -> L21
            if (r0 == 0) goto L29
            java.lang.String[] r0 = new java.lang.String[]{r0}     // Catch: android.os.RemoteException -> L21
            java.util.List r0 = java.util.Arrays.asList(r0)     // Catch: android.os.RemoteException -> L21
            goto L2a
        L21:
            r0 = move-exception
            java.lang.String r2 = "PackageSettings"
            java.lang.String r3 = "Failed talking with kiosk mode service"
            android.util.Log.e(r2, r3, r0)
        L29:
            r0 = r1
        L2a:
            r2 = 0
            r3 = r2
            r4 = r3
        L2d:
            com.android.server.utils.WatchedSparseArray r5 = r10.mPreferredActivities
            android.util.SparseArray r6 = r5.mStorage
            int r6 = r6.size()
            if (r3 >= r6) goto Lb0
            android.util.SparseArray r6 = r5.mStorage
            int r6 = r6.keyAt(r3)
            android.util.SparseArray r5 = r5.mStorage
            java.lang.Object r5 = r5.valueAt(r3)
            com.android.server.pm.PreferredIntentResolver r5 = (com.android.server.pm.PreferredIntentResolver) r5
            r7 = -1
            if (r13 == r7) goto L4b
            if (r13 == r6) goto L4b
            goto Lac
        L4b:
            com.android.server.IntentResolver$IteratorWrapper r7 = r5.filterIterator()
        L4f:
            java.util.Iterator r8 = r7.mI
            boolean r8 = r8.hasNext()
            if (r8 == 0) goto L8e
            java.lang.Object r8 = r7.next()
            com.android.server.pm.PreferredActivity r8 = (com.android.server.pm.PreferredActivity) r8
            if (r11 == 0) goto L73
            com.android.server.pm.PreferredComponent r9 = r8.mPref
            android.content.ComponentName r9 = r9.mComponent
            java.lang.String r9 = r9.getPackageName()
            boolean r9 = r9.equals(r11)
            if (r9 == 0) goto L4f
            com.android.server.pm.PreferredComponent r9 = r8.mPref
            boolean r9 = r9.mAlways
            if (r9 == 0) goto L4f
        L73:
            if (r1 != 0) goto L7a
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L7a:
            if (r0 == 0) goto L8a
            com.android.server.pm.PreferredComponent r9 = r8.mPref
            android.content.ComponentName r9 = r9.mComponent
            java.lang.String r9 = r9.getPackageName()
            boolean r9 = r0.contains(r9)
            if (r9 != 0) goto L4f
        L8a:
            r1.add(r8)
            goto L4f
        L8e:
            if (r1 == 0) goto Lac
            r4 = r2
        L91:
            int r7 = r1.size()
            if (r4 >= r7) goto La8
            java.lang.Object r7 = r1.get(r4)
            com.android.server.pm.PreferredActivity r7 = (com.android.server.pm.PreferredActivity) r7
            r5.removeFilter(r7)
            java.lang.String r8 = "Removing preference<clear>"
            com.android.server.pm.PreferredActivityLog.logPreferenceChange(r7, r8)
            int r4 = r4 + 1
            goto L91
        La8:
            r4 = 1
            r12.put(r6, r4)
        Lac:
            int r3 = r3 + 1
            goto L2d
        Lb0:
            if (r4 == 0) goto Lb5
            r10.dispatchChange(r10)
        Lb5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.clearPackagePreferredActivitiesLPw(java.lang.String, android.util.SparseBooleanArray, int):void");
    }

    public final PackageStateMutator.Result commitPackageStateMutation(PackageStateMutator.InitialState initialState, String str, Consumer consumer) {
        PackageStateMutator.Result result;
        PackageStateMutator.Result result2 = Thread.holdsLock(this.mPackageStateWriteLock) ? PackageStateMutator.Result.SUCCESS : null;
        synchronized (this.mPackageStateWriteLock) {
            if (result2 == null) {
                try {
                    PackageStateMutator packageStateMutator = this.mPackageStateMutator;
                    int i = this.mChangedPackagesTracker.mChangedPackagesSequenceNumber;
                    packageStateMutator.getClass();
                    result2 = PackageStateMutator.Result.SUCCESS;
                    if (initialState != null) {
                        boolean z = i != initialState.mPackageSequence;
                        boolean z2 = PackageStateMutator.sStateChangeSequence.get() != initialState.mStateSequence;
                        if (z && z2) {
                            result = PackageStateMutator.Result.PACKAGES_AND_STATE_CHANGED;
                        } else if (z) {
                            result = PackageStateMutator.Result.PACKAGES_CHANGED;
                        } else if (z2) {
                            result = PackageStateMutator.Result.STATE_CHANGED;
                        }
                        result2 = result;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            PackageStateMutator.Result result3 = PackageStateMutator.Result.SUCCESS;
            if (result2 != result3) {
                return result2;
            }
            PackageStateMutator.StateWriteWrapper forPackage = this.mPackageStateMutator.forPackage(str);
            consumer.accept(forPackage);
            PackageSetting packageSetting = forPackage.mState;
            if (packageSetting != null) {
                packageSetting.onChanged$2();
            }
            return result3;
        }
    }

    public final PackageStateMutator.Result commitPackageStateMutation(Consumer consumer) {
        synchronized (this.mPackageStateWriteLock) {
            PackageStateMutator packageStateMutator = this.mPackageStateMutator;
            int i = this.mChangedPackagesTracker.mChangedPackagesSequenceNumber;
            packageStateMutator.getClass();
            consumer.accept(this.mPackageStateMutator);
            PackageStateMutator packageStateMutator2 = this.mPackageStateMutator;
            for (int i2 = 0; i2 < packageStateMutator2.mChangedStates.size(); i2++) {
                ((PackageSetting) packageStateMutator2.mChangedStates.valueAt(i2)).onChanged$2();
            }
        }
        return PackageStateMutator.Result.SUCCESS;
    }

    public final void createNewUser(int i, Set set, String[] strArr) {
        PackageManagerTracedLock packageManagerTracedLock = this.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            this.mSettings.createNewUserLI(this, this.mInstaller, i, set, strArr);
            packageManagerTracedLock.close();
            synchronized (this.mLock) {
                scheduleWritePackageRestrictions(i);
                invalidatePackageInfoCache();
                Handler handler = this.mHandler;
                if (!handler.hasMessages(19)) {
                    Message obtainMessage = handler.obtainMessage(19);
                    obtainMessage.arg1 = i;
                    handler.sendMessageDelayed(obtainMessage, 10000L);
                }
                this.mAppsFilter.onUserCreated(snapshotComputer(), i);
            }
        } catch (Throwable th) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void deleteExistingPackageAsUser(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        int length;
        DeletePackageHelper deletePackageHelper = this.mDeletePackageHelper;
        deletePackageHelper.mPm.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        Preconditions.checkNotNull(versionedPackage);
        Preconditions.checkNotNull(iPackageDeleteObserver2);
        String packageName = versionedPackage.getPackageName();
        long longVersionCode = versionedPackage.getLongVersionCode();
        synchronized (deletePackageHelper.mPm.mLock) {
            try {
                PackageSetting packageLPr = deletePackageHelper.mPm.mSettings.getPackageLPr(deletePackageHelper.mPm.snapshotComputer().resolveInternalPackageName(longVersionCode, packageName));
                length = packageLPr != null ? packageLPr.queryInstalledUsers(UserManagerService.this.getUserIds(), true).length : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (length > 1) {
            deletePackageHelper.deletePackageVersionedInternal(versionedPackage, iPackageDeleteObserver2, i, 0, true);
        } else {
            try {
                iPackageDeleteObserver2.onPackageDeleted(packageName, -1, (String) null);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void deletePreloadsFileCache() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", "deletePreloadsFileCache");
        File dataPreloadsFileCacheDirectory = Environment.getDataPreloadsFileCacheDirectory();
        Slog.i("PackageManager", "Deleting preloaded file cache " + dataPreloadsFileCacheDirectory);
        FileUtils.deleteContents(dataPreloadsFileCacheDirectory);
    }

    public final boolean enableCompressedPackage(PackageSetting packageSetting, AndroidPackage androidPackage) {
        PackageFreezer freezePackage;
        InstallPackageHelper installPackageHelper = this.mInstallPackageHelper;
        PackageManagerService packageManagerService = installPackageHelper.mPm;
        int i = packageManagerService.mDefParseFlags | (-2147483584);
        PackageManagerTracedLock packageManagerTracedLock = packageManagerService.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            try {
                freezePackage = installPackageHelper.mPm.freezePackage(androidPackage.getPackageName(), -1, "setEnabledSetting", 16, null);
                try {
                    AndroidPackage installStubPackageLI = installPackageHelper.installStubPackageLI(androidPackage, i, 0);
                    installPackageHelper.mAppDataHelper.prepareAppDataAfterInstallLIF(installStubPackageLI);
                    synchronized (installPackageHelper.mPm.mLock) {
                        try {
                            installPackageHelper.mSharedLibraries.updateSharedLibraries(installStubPackageLI, packageSetting, null, null, Collections.unmodifiableMap(installPackageHelper.mPm.mPackages));
                        } catch (PackageManagerException e) {
                            Slog.w("PackageManager", "updateAllSharedLibrariesLPw failed: ", e);
                        }
                        installPackageHelper.mPm.mPermissionManager.onPackageInstalled(installStubPackageLI, PermissionManagerServiceInternal$PackageInstalledParams.DEFAULT, -1);
                        installPackageHelper.mPm.writeSettingsLPrTEMP(false);
                    }
                    freezePackage.close();
                    installPackageHelper.mAppDataHelper.clearAppDataLIF(installStubPackageLI, -1, 39);
                    installPackageHelper.mDexManager.notifyPackageUpdated(installStubPackageLI.getSplitCodePaths(), installStubPackageLI.getPackageName(), installStubPackageLI.getBaseApkPath());
                    packageManagerTracedLock.close();
                    return true;
                } finally {
                }
            } catch (PackageManagerException unused) {
                try {
                    try {
                        freezePackage = installPackageHelper.mPm.freezePackage(androidPackage.getPackageName(), -1, "setEnabledSetting", 16, null);
                    } catch (PackageManagerException e2) {
                        Slog.wtf("PackageManager", "Failed to restore system package:" + androidPackage.getPackageName(), e2);
                        synchronized (installPackageHelper.mPm.mLock) {
                            try {
                                PackageSetting packageLPr = installPackageHelper.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                                if (packageLPr != null) {
                                    packageLPr.setEnabled(2, 0, "android");
                                }
                                installPackageHelper.mPm.writeSettingsLPrTEMP(false);
                                packageManagerTracedLock.close();
                                return false;
                            } finally {
                            }
                        }
                    }
                    try {
                        synchronized (installPackageHelper.mPm.mLock) {
                            installPackageHelper.mPm.mSettings.enableSystemPackageLPw(androidPackage.getPackageName());
                            installPackageHelper.installPackageFromSystemLIF(androidPackage.getPath(), installPackageHelper.mPm.mUserManager.getUserIds(), null, true);
                            freezePackage.close();
                            synchronized (installPackageHelper.mPm.mLock) {
                                try {
                                    PackageSetting packageLPr2 = installPackageHelper.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                                    if (packageLPr2 != null) {
                                        packageLPr2.setEnabled(2, 0, "android");
                                    }
                                    installPackageHelper.mPm.writeSettingsLPrTEMP(false);
                                    packageManagerTracedLock.close();
                                    return false;
                                } finally {
                                }
                            }
                        }
                    } finally {
                    }
                } catch (Throwable th) {
                    synchronized (installPackageHelper.mPm.mLock) {
                        try {
                            PackageSetting packageLPr3 = installPackageHelper.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
                            if (packageLPr3 != null) {
                                packageLPr3.setEnabled(2, 0, "android");
                            }
                            installPackageHelper.mPm.writeSettingsLPrTEMP(false);
                            throw th;
                        } finally {
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th3) {
                th2.addSuppressed(th3);
            }
            throw th2;
        }
    }

    public final void flushPackageRestrictionsAsUserInternalLocked(int i) {
        this.mSettings.writePackageRestrictionsLPr(i, false);
        synchronized (this.mDirtyUsers) {
            try {
                this.mDirtyUsers.remove(Integer.valueOf(i));
                if (this.mDirtyUsers.isEmpty()) {
                    this.mBackgroundHandler.removeMessages(14);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void freeStorage(int i, String str, long j) {
        PackageManagerTracedLock packageManagerTracedLock;
        boolean z;
        boolean z2;
        FreeStorageHelper freeStorageHelper = this.mFreeStorageHelper;
        File findPathForUuid = ((StorageManager) freeStorageHelper.mInjector.mGetSystemServiceProducer.produce(StorageManager.class)).findPathForUuid(str);
        if (findPathForUuid.getUsableSpace() >= j) {
            return;
        }
        if (freeStorageHelper.mEnableFreeCacheV2) {
            boolean equals = Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str);
            boolean z3 = (i & 1) != 0;
            if (equals && (z3 || SystemProperties.getBoolean("persist.sys.preloads.file_cache_expired", false))) {
                freeStorageHelper.mPm.deletePreloadsFileCache();
                if (findPathForUuid.getUsableSpace() >= j) {
                    return;
                }
            }
            if (equals && z3) {
                FileUtils.deleteContents(freeStorageHelper.mPm.mCacheDir);
                if (findPathForUuid.getUsableSpace() >= j) {
                    return;
                }
            }
            try {
                packageManagerTracedLock = freeStorageHelper.mPm.mInstallLock;
                packageManagerTracedLock.mLock.lock();
                try {
                    freeStorageHelper.mPm.mInstaller.freeCache(str, j, 256);
                    packageManagerTracedLock.close();
                } finally {
                    try {
                        packageManagerTracedLock.close();
                        throw th;
                    } catch (Throwable th) {
                        th.addSuppressed(th);
                    }
                }
            } catch (Installer.InstallerException unused) {
            }
            if (findPathForUuid.getUsableSpace() >= j) {
                return;
            }
            Computer snapshotComputer = freeStorageHelper.mPm.snapshotComputer();
            SharedLibrariesImpl sharedLibrariesImpl = freeStorageHelper.mPm.mInjector.getSharedLibrariesImpl();
            if (equals && sharedLibrariesImpl.pruneUnusedStaticSharedLibraries(snapshotComputer, j, Settings.Global.getLong(freeStorageHelper.mContext.getContentResolver(), "unused_static_shared_lib_min_cache_period", FreeStorageHelper.FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD))) {
                return;
            }
            if (equals) {
                InstantAppRegistry instantAppRegistry = freeStorageHelper.mPm.mInstantAppRegistry;
                long j2 = Settings.Global.getLong(freeStorageHelper.mContext.getContentResolver(), "installed_instant_app_min_cache_period", 604800000L);
                instantAppRegistry.getClass();
                try {
                    z2 = instantAppRegistry.pruneInstantApps(snapshotComputer, j, j2, Long.MAX_VALUE);
                } catch (IOException e) {
                    Slog.e("InstantAppRegistry", "Error pruning installed instant apps", e);
                    z2 = false;
                }
                if (z2) {
                    return;
                }
            }
            try {
                PackageManagerTracedLock packageManagerTracedLock2 = freeStorageHelper.mPm.mInstallLock;
                packageManagerTracedLock2.mLock.lock();
                try {
                    freeStorageHelper.mPm.mInstaller.freeCache(str, j, FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE);
                    packageManagerTracedLock2.close();
                } finally {
                    try {
                        packageManagerTracedLock2.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
            } catch (Installer.InstallerException unused2) {
            }
            if (findPathForUuid.getUsableSpace() >= j) {
                return;
            }
            if (equals) {
                InstantAppRegistry instantAppRegistry2 = freeStorageHelper.mPm.mInstantAppRegistry;
                long j3 = Settings.Global.getLong(freeStorageHelper.mContext.getContentResolver(), "uninstalled_instant_app_min_cache_period", 604800000L);
                instantAppRegistry2.getClass();
                try {
                    z = instantAppRegistry2.pruneInstantApps(snapshotComputer, j, Long.MAX_VALUE, j3);
                } catch (IOException e2) {
                    Slog.e("InstantAppRegistry", "Error pruning uninstalled instant apps", e2);
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            StorageManagerInternal storageManagerInternal = (StorageManagerInternal) freeStorageHelper.mInjector.mGetLocalServiceProducer.produce(StorageManagerInternal.class);
            long usableSpace = j - findPathForUuid.getUsableSpace();
            if (usableSpace > 0) {
                storageManagerInternal.freeCache(str, usableSpace);
            }
            PackageInstallerService packageInstallerService = freeStorageHelper.mPm.mInstallerService;
            packageInstallerService.getClass();
            File[] listFiles = Environment.getDataAppDirectory(str).listFiles(PackageInstallerService.sStageFilter);
            ArraySet arraySet = new ArraySet();
            if (listFiles != null) {
                arraySet.ensureCapacity(listFiles.length);
                Collections.addAll(arraySet, listFiles);
            }
            File[] listFiles2 = Environment.getDataStagingDirectory(str).listFiles();
            ArraySet arraySet2 = new ArraySet();
            if (listFiles2 != null) {
                arraySet2.ensureCapacity(listFiles2.length);
                Collections.addAll(arraySet2, listFiles2);
            }
            arraySet.addAll(arraySet2);
            long currentTimeMillis = System.currentTimeMillis();
            synchronized (packageInstallerService.mSessions) {
                for (int i2 = 0; i2 < packageInstallerService.mSessions.size(); i2++) {
                    try {
                        PackageInstallerSession packageInstallerSession = (PackageInstallerSession) packageInstallerService.mSessions.valueAt(i2);
                        if (arraySet.contains(packageInstallerSession.stageDir)) {
                            if (currentTimeMillis - packageInstallerSession.createdMillis >= 28800000) {
                                PackageInstallerSession packageInstallerSession2 = !packageInstallerSession.hasParentSessionId() ? packageInstallerSession : (PackageInstallerSession) packageInstallerService.mSessions.get(packageInstallerSession.getParentSessionId());
                                if (packageInstallerSession2 == null) {
                                    Slog.e("PackageInstaller", "freeStageDirs: found an orphaned session: " + packageInstallerSession.sessionId + " parent=" + packageInstallerSession.getParentSessionId());
                                } else if (!packageInstallerSession2.isDestroyed()) {
                                    packageInstallerSession2.abandon();
                                }
                            } else {
                                arraySet.remove(packageInstallerSession.stageDir);
                            }
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                }
            }
            packageInstallerService.removeStagingDirs(arraySet);
        } else {
            try {
                packageManagerTracedLock = freeStorageHelper.mPm.mInstallLock;
                packageManagerTracedLock.mLock.lock();
                try {
                    freeStorageHelper.mPm.mInstaller.freeCache(str, j, 0);
                    packageManagerTracedLock.close();
                } finally {
                }
            } catch (Installer.InstallerException unused3) {
            }
        }
        if (findPathForUuid.getUsableSpace() >= j) {
            return;
        }
        throw new IOException("Failed to free " + j + " on storage device at " + findPathForUuid);
    }

    public final PackageFreezer freezePackage(String str, int i, String str2, int i2, InstallRequest installRequest) {
        return new PackageFreezer(str, i, str2, this, i2, installRequest, false);
    }

    public final PackageFreezer freezePackageForDelete(int i, int i2, String str, String str2) {
        return (i2 & 8) != 0 ? new PackageFreezer(this, null) : freezePackage(str, i, str2, 13, null);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName getDomainVerificationAgentComponentNameLPr(com.android.server.pm.ComputerLocked r15) {
        /*
            r14 = this;
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.DOMAINS_NEED_VERIFICATION"
            r2.<init>(r0)
            int r7 = android.os.Binder.getCallingUid()
            r3 = 0
            r4 = 1835008(0x1c0000, double:9.066144E-318)
            com.android.server.pm.ResolveIntentHelper r0 = r14.mResolveIntentHelper
            r10 = 0
            r8 = -1
            r9 = 0
            r1 = r15
            r6 = r10
            java.util.List r0 = r0.queryIntentReceiversInternal(r1, r2, r3, r4, r6, r7, r8, r9)
            int r1 = r0.size()
            r2 = 0
            r3 = 0
            r4 = r2
        L21:
            java.lang.String r5 = "PackageManager"
            if (r3 >= r1) goto L87
            java.lang.Object r6 = r0.get(r3)
            android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6
            android.content.pm.ComponentInfo r7 = r6.getComponentInfo()
            java.lang.String r7 = r7.packageName
            java.lang.String r8 = "android.permission.DOMAIN_VERIFICATION_AGENT"
            int r8 = r14.checkPermission(r8, r7, r10)
            if (r8 == 0) goto L3f
            java.lang.String r6 = "Domain verification agent found but does not hold permission: "
            com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0.m(r6, r7, r5)
            goto L84
        L3f:
            if (r4 == 0) goto L47
            int r7 = r6.priority
            int r8 = r4.priority
            if (r7 <= r8) goto L84
        L47:
            android.content.pm.ComponentInfo r7 = r6.getComponentInfo()
            android.os.UserHandle r8 = android.os.UserHandle.of(r10)
            com.android.server.pm.ComputerEngine$Settings r9 = r15.mSettings
            r11 = 0
            java.lang.String r12 = r7.packageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            int r8 = r8.getIdentifier()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            int r12 = r9.getApplicationEnabledSetting(r12, r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            r13 = 1
            if (r12 != 0) goto L66
            android.content.pm.ApplicationInfo r12 = r7.applicationInfo     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            boolean r12 = r12.enabled     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            if (r12 != 0) goto L69
            goto L7b
        L66:
            if (r12 == r13) goto L69
            goto L7b
        L69:
            android.content.ComponentName r12 = r7.getComponentName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            int r8 = r9.getComponentEnabledSetting(r12, r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            if (r8 != 0) goto L78
            boolean r11 = r7.isEnabled()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L7b
            goto L7b
        L78:
            if (r8 != r13) goto L7b
            r11 = r13
        L7b:
            if (r11 == 0) goto L7f
            r4 = r6
            goto L84
        L7f:
            java.lang.String r6 = "Domain verification agent found but not enabled"
            android.util.Slog.w(r5, r6)
        L84:
            int r3 = r3 + 1
            goto L21
        L87:
            if (r4 == 0) goto L92
            android.content.pm.ComponentInfo r14 = r4.getComponentInfo()
            android.content.ComponentName r14 = r14.getComponentName()
            return r14
        L92:
            java.lang.String r14 = "Domain verification agent not found"
            android.util.Slog.w(r5, r14)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.getDomainVerificationAgentComponentNameLPr(com.android.server.pm.ComputerLocked):android.content.ComponentName");
    }

    public final ComponentName getInstantAppResolver(Computer computer) {
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.networkAttributes);
        int length = stringArray.length;
        boolean z = DEBUG_INSTANT;
        if (length == 0 && !Build.IS_DEBUGGABLE) {
            if (z) {
                Slog.d("PackageManager", "Ephemeral resolver NOT found; empty package list");
            }
            return null;
        }
        List queryIntentServicesInternal = computer.queryIntentServicesInternal(new Intent("android.intent.action.RESOLVE_INSTANT_APP_PACKAGE"), null, (!Build.IS_DEBUGGABLE ? 1048576 : 0) | 786432, 0, Binder.getCallingUid(), -1, false);
        int size = queryIntentServicesInternal.size();
        if (size == 0) {
            if (z) {
                Slog.d("PackageManager", "Ephemeral resolver NOT found; no matching intent filters");
            }
            return null;
        }
        ArraySet arraySet = new ArraySet(Arrays.asList(stringArray));
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentServicesInternal.get(i);
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo != null) {
                String str = serviceInfo.packageName;
                if (arraySet.contains(str) || Build.IS_DEBUGGABLE) {
                    if (z) {
                        Slog.v("PackageManager", "Ephemeral resolver found; pkg: " + str + ", info:" + resolveInfo);
                    }
                    return new ComponentName(str, resolveInfo.serviceInfo.name);
                }
                if (z) {
                    Slog.d("PackageManager", "Ephemeral resolver not in allowed package list; pkg: " + str + ", info:" + resolveInfo);
                }
            }
        }
        if (z) {
            Slog.v("PackageManager", "Ephemeral resolver NOT found");
        }
        return null;
    }

    public final ComponentName getIntentFilterVerifierComponentNameLPr(ComputerLocked computerLocked) {
        List queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computerLocked, new Intent("android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION"), "application/vnd.android.package-archive", 1835008L, 0, Binder.getCallingUid(), -1, false);
        int size = queryIntentReceiversInternal.size();
        ResolveInfo resolveInfo = null;
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentReceiversInternal.get(i);
            if (checkPermission("android.permission.INTENT_FILTER_VERIFICATION_AGENT", resolveInfo2.getComponentInfo().packageName, 0) == 0 && (resolveInfo == null || resolveInfo2.priority > resolveInfo.priority)) {
                resolveInfo = resolveInfo2;
            }
        }
        if (resolveInfo != null) {
            return resolveInfo.getComponentInfo().getComponentName();
        }
        Slog.w("PackageManager", "Intent filter verifier not found");
        return null;
    }

    public final String[] getKnownPackageNamesInternal(Computer computer, int i, int i2) {
        return new KnownPackages(this.mDefaultAppProvider, this.mRequiredInstallerPackage, this.mRequiredUninstallerPackage, this.mSetupWizardPackage, this.mRequiredVerifierPackages, this.mDefaultTextClassifierPackage, this.mSystemTextClassifierPackageName, this.mRequiredPermissionControllerPackage, this.mConfiguratorPackage, this.mIncidentReportApproverPackage, this.mAmbientContextDetectionPackage, this.mWearableSensingPackage, this.mAppPredictionServicePackage, this.mRetailDemoPackage, this.mOverlayConfigSignaturePackage, this.mRecentsPackage).getKnownPackageNames(computer, i, i2);
    }

    public final String getPackageFromComponentString(int i) {
        ComponentName unflattenFromString;
        String string = this.mContext.getString(i);
        if (TextUtils.isEmpty(string) || (unflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return null;
        }
        return unflattenFromString.getPackageName();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.incremental.PerUidReadTimeouts[] getPerUidReadTimeouts(com.android.server.pm.Computer r18) {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.getPerUidReadTimeouts(com.android.server.pm.Computer):android.os.incremental.PerUidReadTimeouts[]");
    }

    public final String[] getRequiredButNotReallyRequiredVerifiersLPr(ComputerLocked computerLocked) {
        List queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computerLocked, new Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION"), "application/vnd.android.package-archive", 1835008L, 0, Binder.getCallingUid(), -1, false);
        int size = queryIntentReceiversInternal.size();
        if (size == 0) {
            Log.w("PackageManager", "There should probably be a verifier, but, none were found");
            return EmptyArray.STRING;
        }
        if (size > 2) {
            throw new RuntimeException("There must be no more than 2 verifiers; found " + queryIntentReceiversInternal);
        }
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            String str = ((ResolveInfo) queryIntentReceiversInternal.get(i)).getComponentInfo().packageName;
            strArr[i] = str;
            if (TextUtils.isEmpty(str)) {
                throw new RuntimeException("Invalid verifier: " + queryIntentReceiversInternal);
            }
        }
        return strArr;
    }

    public final String getRequiredInstallerLPr(ComputerLocked computerLocked) {
        Intent m = PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.INSTALL_PACKAGE", "android.intent.category.DEFAULT");
        m.setDataAndType(Uri.parse("content://com.example/foo.apk"), "application/vnd.android.package-archive");
        List queryIntentActivitiesInternal = computerLocked.queryIntentActivitiesInternal(m, "application/vnd.android.package-archive", 1835008L, 0);
        if (queryIntentActivitiesInternal.size() == 1) {
            if (((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.applicationInfo.isPrivilegedApp()) {
                return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
            }
            throw new RuntimeException("The installer must be a privileged app");
        }
        if (queryIntentActivitiesInternal.size() == 0) {
            throwSystemRequiredPackageState(computerLocked, m, "application/vnd.android.package-archive");
        }
        throw new RuntimeException("There must be exactly one installer; found " + queryIntentActivitiesInternal);
    }

    public final String getRequiredPermissionControllerLPr(ComputerLocked computerLocked) {
        Intent m = PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MANAGE_PERMISSIONS", "android.intent.category.DEFAULT");
        List queryIntentActivitiesInternal = computerLocked.queryIntentActivitiesInternal(m, null, 1835008L, 0);
        if (queryIntentActivitiesInternal.size() == 1) {
            if (((ResolveInfo) queryIntentActivitiesInternal.get(0)).activityInfo.applicationInfo.isPrivilegedApp()) {
                return ((ResolveInfo) queryIntentActivitiesInternal.get(0)).getComponentInfo().packageName;
            }
            throw new RuntimeException("The permissions manager must be a privileged app");
        }
        if (queryIntentActivitiesInternal.size() == 0) {
            throwSystemRequiredPackageState(computerLocked, m, null);
        }
        throw new RuntimeException("There must be exactly one permissions manager; found " + queryIntentActivitiesInternal);
    }

    public final String getRequiredServicesExtensionPackageLPr(ComputerLocked computerLocked) {
        String string = this.mContext.getString(R.string.face_acquired_sensor_dirty);
        if (TextUtils.isEmpty(string)) {
            throw new RuntimeException("Required services extension package failed due to config_servicesExtensionPackage is empty.");
        }
        String ensureSystemPackageName = ensureSystemPackageName(computerLocked, string);
        if (TextUtils.isEmpty(ensureSystemPackageName)) {
            throw new RuntimeException(XmlUtils$$ExternalSyntheticOutline0.m("Required services extension package is missing, config_servicesExtensionPackage had defined with ", string, ", but can not find the package info on the system image, check if the package has a problem."));
        }
        return ensureSystemPackageName;
    }

    public final String getRequiredUninstallerLPr(ComputerLocked computerLocked) {
        Intent m = PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.UNINSTALL_PACKAGE", "android.intent.category.DEFAULT");
        m.setData(Uri.fromParts("package", "foo.bar", null));
        ResolveInfo resolveIntentInternal = this.mResolveIntentHelper.resolveIntentInternal(computerLocked, m, null, 1835008L, 0L, 0, false, Binder.getCallingUid(), Binder.getCallingPid());
        if (resolveIntentInternal != null && !this.mResolveActivity.name.equals(resolveIntentInternal.getComponentInfo().name)) {
            return resolveIntentInternal.getComponentInfo().packageName;
        }
        throwSystemRequiredPackageState(computerLocked, m, null);
        throw new RuntimeException("There must be exactly one uninstaller; found " + resolveIntentInternal);
    }

    public final String getRetailDemoPackageName() {
        AndroidPackage androidPackage;
        SigningDetails signingDetails;
        String string = this.mContext.getString(R.string.ext_media_status_removed);
        String string2 = this.mContext.getString(R.string.ext_media_status_unmountable);
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && (androidPackage = (AndroidPackage) this.mPackages.mStorage.get(string)) != null && (signingDetails = androidPackage.getSigningDetails()) != null && signingDetails.getSignatures() != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                for (Signature signature : signingDetails.getSignatures()) {
                    if (TextUtils.equals(string2, HexEncoding.encodeToString(messageDigest.digest(signature.toByteArray()), false))) {
                        return string;
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                Slog.e("PackageManager", "Unable to verify signatures as getting the retail demo package name", e);
            }
        }
        return null;
    }

    public final Settings.VersionInfo getSettingsVersionForPackage(AndroidPackage androidPackage) {
        boolean isExternalStorage = androidPackage.isExternalStorage();
        Settings settings = this.mSettings;
        return isExternalStorage ? TextUtils.isEmpty(androidPackage.getVolumeUuid()) ? (Settings.VersionInfo) settings.mVersion.mStorage.get("primary_physical") : settings.findOrCreateVersion(androidPackage.getVolumeUuid()) : settings.getInternalVersion();
    }

    public final int getSystemPackageScanFlags(File file) {
        ScanPartition scanPartition;
        ArrayList arrayList = (ArrayList) this.mInitAppsHelper.mDirsToScanAsSystem;
        int size = arrayList.size();
        do {
            size--;
            if (size < 0) {
                return EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            }
            scanPartition = (ScanPartition) arrayList.get(size);
        } while (!scanPartition.containsFile(file));
        int i = scanPartition.scanFlag;
        return scanPartition.containsPrivApp(file) ? i | 196608 : 65536 | i;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void grantImplicitAccess(com.android.server.pm.Computer r4, int r5, android.content.Intent r6, int r7, int r8, boolean r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.grantImplicitAccess(com.android.server.pm.Computer, int, android.content.Intent, int, int, boolean, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0046, code lost:
    
        if (android.provider.Settings.Secure.getInt(r0.getContentResolver(), "shopdemo", 0) == 1) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean hasSystemFeature(java.lang.String r8, int r9) {
        /*
            r7 = this;
            boolean r0 = com.samsung.android.core.pm.mm.MaintenanceModeUtils.isMaintenanceModeFeature(r8)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L63
            android.content.Context r0 = r7.mContext
            java.io.File r3 = com.samsung.android.server.pm.mm.MaintenanceModeManager.LOG_DIR
            java.lang.String r3 = "persist.sys.disallow_maintenance_mode"
            boolean r3 = android.os.SystemProperties.getBoolean(r3, r2)
            if (r3 == 0) goto L16
            goto L62
        L16:
            java.lang.String r3 = "ril.product_code"
            java.lang.String r4 = ""
            java.lang.String r3 = android.os.SystemProperties.get(r3, r4)
            int r4 = r3.length()
            r5 = 11
            if (r4 >= r5) goto L28
            goto L3b
        L28:
            r4 = 10
            char r5 = r3.charAt(r4)
            r6 = 56
            if (r5 == r6) goto L62
            char r3 = r3.charAt(r4)
            r4 = 57
            if (r3 != r4) goto L3b
            goto L62
        L3b:
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: java.lang.Exception -> L49
            java.lang.String r3 = "shopdemo"
            int r0 = android.provider.Settings.Secure.getInt(r0, r3, r2)     // Catch: java.lang.Exception -> L49
            if (r0 != r1) goto L63
            goto L62
        L49:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Failed to check shopdemo: "
            r3.<init>(r4)
            java.lang.String r0 = r0.toString()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            java.lang.String r3 = "MaintenanceMode"
            android.util.Log.i(r3, r0)
            goto L63
        L62:
            return r2
        L63:
            android.util.ArrayMap r7 = r7.mAvailableFeatures
            java.lang.Object r7 = r7.get(r8)
            android.content.pm.FeatureInfo r7 = (android.content.pm.FeatureInfo) r7
            if (r7 != 0) goto L6e
            return r2
        L6e:
            int r7 = r7.version
            if (r7 < r9) goto L73
            goto L74
        L73:
            r1 = r2
        L74:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.hasSystemFeature(java.lang.String, int):boolean");
    }

    public final boolean isDeviceUpgrading() {
        return this.mIsUpgrade || SystemProperties.getBoolean("persist.pm.mock-upgrade", false);
    }

    public final boolean isLocaleOptimizedPackage(int i, String str) {
        Bundle bundle;
        if (str == null) {
            return false;
        }
        ApplicationInfo applicationInfo = snapshotComputer().getApplicationInfo(str, 128L, i);
        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && bundle.getBoolean("com.samsung.android.hasZippedOverlays")) {
            return true;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m("isLocaleOptimizedPackage() -  Non optimized app: ", str, ". Proceed with normal install", "PackageManager");
        return false;
    }

    public final boolean isPackageDeviceAdmin(int i, String str) {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        IDevicePolicyManager iDevicePolicyManager = this.mDevicePolicyManager;
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) this.mInjector.mGetLocalServiceProducer.produce(DevicePolicyManagerInternal.class);
        if (iDevicePolicyManager != null && devicePolicyManagerInternal != null) {
            try {
                ComponentName deviceOwnerComponent = iDevicePolicyManager.getDeviceOwnerComponent(false);
                if (str.equals(deviceOwnerComponent == null ? null : deviceOwnerComponent.getPackageName())) {
                    return true;
                }
                int[] userIds = this.mUserManager.getUserIds();
                int[] iArr = i == -1 ? userIds : new int[]{i};
                for (int i2 : iArr) {
                    if (iDevicePolicyManager.packageHasActiveAdmins(str, i2)) {
                        return true;
                    }
                }
                PackageSetting packageStateInternal = snapshotComputer().getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    return false;
                }
                if (!packageStateInternal.isSystem()) {
                    userIds = iArr;
                }
                for (final int i3 : userIds) {
                    if (str.equals((String) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda7
                        public final Object getOrThrow() {
                            List roleHoldersAsUser = ((RoleManager) PackageManagerService.this.mContext.getSystemService(RoleManager.class)).getRoleHoldersAsUser("android.app.role.DEVICE_POLICY_MANAGEMENT", UserHandle.of(i3));
                            if (roleHoldersAsUser.isEmpty()) {
                                return null;
                            }
                            return (String) roleHoldersAsUser.get(0);
                        }
                    })) && devicePolicyManagerInternal.isUserOrganizationManaged(i3)) {
                        return true;
                    }
                }
            } catch (RemoteException unused) {
            }
        }
        return false;
    }

    public final boolean isPackageDeviceAdminOnAnyUser(Computer computer, String str) {
        int callingUid = Binder.getCallingUid();
        if (computer.checkUidPermission("android.permission.MANAGE_USERS", callingUid) != 0) {
            EventLog.writeEvent(1397638484, "128599183", -1, "");
            throw new SecurityException("android.permission.MANAGE_USERS permission is required to call this API");
        }
        if (computer.getInstantAppPackageName(callingUid) == null || computer.isCallerSameApp(callingUid, str)) {
            return isPackageDeviceAdmin(-1, str);
        }
        return false;
    }

    public final boolean isRequiredSystemPackage(Computer computer, String str, int i) {
        return this.mRequiredSystemPackages.contains(str) && computer.getPackageStateInternal(str).isSystem() && i == 0;
    }

    public final boolean isUserRestricted(int i, String str) {
        if (!this.mUserManager.getUserRestrictions(i).getBoolean(str, false)) {
            return false;
        }
        Log.w("PackageManager", "User is restricted: ".concat(str));
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void markPackageAsArchivedIfNeeded(com.android.server.pm.PackageSetting r22, android.content.pm.ArchivedPackageParcel r23, android.util.SparseArray r24, int[] r25) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.markPackageAsArchivedIfNeeded(com.android.server.pm.PackageSetting, android.content.pm.ArchivedPackageParcel, android.util.SparseArray, int[]):void");
    }

    public final void notifyFirstLaunch(final int i, final String str, final String str2) {
        SpegService spegService;
        if (CoreRune.SYSFW_APP_SPEG && (spegService = this.mSpeg) != null && spegService.isSpegInOpeartion(str)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("Skip first launch notification for ", str, "SPEG");
        } else {
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda61
                /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:
                
                    continue;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        r9 = this;
                        com.android.server.pm.PackageManagerService r0 = com.android.server.pm.PackageManagerService.this
                        java.lang.String r1 = r2
                        int r2 = r3
                        java.lang.String r9 = r4
                        r3 = 0
                        r4 = r3
                    La:
                        android.util.SparseArray r5 = r0.mRunningInstalls
                        int r5 = r5.size()
                        r6 = 1
                        if (r4 >= r5) goto L3d
                        android.util.SparseArray r5 = r0.mRunningInstalls
                        java.lang.Object r5 = r5.valueAt(r4)
                        com.android.server.pm.InstallRequest r5 = (com.android.server.pm.InstallRequest) r5
                        int r7 = r5.mReturnCode
                        if (r7 == r6) goto L20
                        goto L3a
                    L20:
                        com.android.server.pm.pkg.AndroidPackage r6 = r5.mPkg
                        java.lang.String r6 = r6.getPackageName()
                        boolean r6 = r1.equals(r6)
                        if (r6 == 0) goto L3a
                        r6 = r3
                    L2d:
                        int[] r7 = r5.mNewUsers
                        int r8 = r7.length
                        if (r6 >= r8) goto L3a
                        r7 = r7[r6]
                        if (r2 != r7) goto L37
                        goto L5c
                    L37:
                        int r6 = r6 + 1
                        goto L2d
                    L3a:
                        int r4 = r4 + 1
                        goto La
                    L3d:
                        com.android.server.pm.Computer r4 = r0.snapshotComputer()
                        r5 = 1000(0x3e8, float:1.401E-42)
                        boolean r4 = r4.isInstantAppInternal(r2, r5, r1)
                        int[] r5 = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY
                        if (r4 == 0) goto L4d
                        r7 = r5
                        goto L51
                    L4d:
                        int[] r7 = new int[r6]
                        r7[r3] = r2
                    L51:
                        if (r4 == 0) goto L57
                        int[] r5 = new int[r6]
                        r5[r3] = r2
                    L57:
                        com.android.server.pm.BroadcastHelper r0 = r0.mBroadcastHelper
                        r0.sendFirstLaunchBroadcast(r1, r9, r7, r5)
                    L5c:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda61.run():void");
                }
            });
        }
    }

    public final void notifyInstallObserver(InstallRequest installRequest) {
        InstallArgs installArgs = installRequest.mInstallArgs;
        IPackageInstallObserver2 iPackageInstallObserver2 = null;
        if ((installArgs == null ? null : installArgs.mObserver) != null) {
            try {
                this.mHandler.post(new PackageManagerService$$ExternalSyntheticLambda51(1, this, installRequest));
                StringBuilder sb = new StringBuilder("result of install: ");
                sb.append(installRequest.mReturnCode);
                sb.append("{");
                InstallArgs installArgs2 = installRequest.mInstallArgs;
                sb.append((installArgs2 == null ? null : installArgs2.mObserver).hashCode());
                sb.append("}");
                PmLog.logDebugInfoAndLogcat(sb.toString());
                Bundle extrasForInstallResult = extrasForInstallResult(installRequest);
                if (installArgs2 != null) {
                    iPackageInstallObserver2 = installArgs2.mObserver;
                }
                iPackageInstallObserver2.onPackageInstalled(installRequest.mName, installRequest.mReturnCode, installRequest.mReturnMsg, extrasForInstallResult);
            } catch (RemoteException unused) {
                Slog.i("PackageManager", "Observer no longer exists.");
            }
        }
    }

    public final void notifyInstallObserver(String str, boolean z) {
        InstallRequest installRequest = z ? (InstallRequest) this.mPendingKillInstallObservers.remove(str) : (InstallRequest) this.mNoKillInstallObservers.remove(str);
        if (installRequest != null) {
            notifyInstallObserver(installRequest);
        }
    }

    public final void notifyPackageChanged(int i, String str) {
        ArraySet arraySet;
        PackageObserverHelper packageObserverHelper = this.mPackageObserverHelper;
        synchronized (packageObserverHelper.mLock) {
            arraySet = packageObserverHelper.mActiveSnapshot;
        }
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((PackageManagerInternal.PackageListObserver) arraySet.valueAt(i2)).onPackageChanged(i, str);
        }
    }

    public final void onNewUserCreated(int i, boolean z) {
        PmLifecycleImpl pmLifecycleImpl;
        boolean z2;
        if (z) {
            synchronized (this.mLock) {
                PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionStateTEMP();
                Settings settings = this.mSettings;
                settings.mRuntimePermissionsPersistence.readStateForUserSync(i, settings.getInternalVersion(), settings.mPackages, settings.mSharedUsers, new File(settings.getUserSystemDirectory(i), "runtime-permissions.xml"));
                PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionStateTEMP();
                z2 = !Objects.equals(PermissionManagerService.this.mPermissionManagerServiceImpl.getDefaultPermissionGrantFingerprint(i), Build.FINGERPRINT);
            }
            if (!z2) {
            }
            pmLifecycleImpl = this.mPmLifecycle;
            if (!pmLifecycleImpl.mEnabled && PMRune.PM_ENABLE_GMS) {
                pmLifecycleImpl.mChinaGmsToggleUtils.setGmsEnabledSetting(i);
            }
            return;
        }
        PermissionManagerService.this.mPermissionManagerServiceImpl.onUserCreated(i);
        this.mLegacyPermissionManager.grantDefaultPermissions(i);
        PermissionManagerService.this.mPermissionManagerServiceImpl.setDefaultPermissionGrantFingerprint(i, Build.FINGERPRINT);
        ((DomainVerificationService) this.mDomainVerificationManager).clearUser(i);
        pmLifecycleImpl = this.mPmLifecycle;
        if (pmLifecycleImpl.mEnabled) {
            return;
        }
        pmLifecycleImpl.mChinaGmsToggleUtils.setGmsEnabledSetting(i);
    }

    public final void overlaysInstallComplete(int i, int i2, InstallLocaleOverlaysType installLocaleOverlaysType, int i3, String str, OverlayChangeObserver.AnonymousClass1 anonymousClass1) {
        Handler handler = this.mHandler;
        if (anonymousClass1 != null) {
            handler.removeCallbacks(anonymousClass1);
        }
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "overlaysInstallComplete() called with: token = [", "], didLaunch = [", "], type = [");
        m.append(installLocaleOverlaysType);
        m.append("], userId = [");
        m.append(i3);
        m.append("], targetPackage = [");
        m.append(str);
        m.append("], timeoutRunnable = [");
        m.append(anonymousClass1);
        m.append("]");
        Slog.d("PackageManager", m.toString());
        int ordinal = installLocaleOverlaysType.ordinal();
        if (ordinal == 0) {
            handler.sendMessage(handler.obtainMessage(10, i, i2));
            return;
        }
        if (ordinal != 1) {
            if (ordinal != 2) {
                return;
            }
            Computer snapshotComputer = snapshotComputer();
            this.mBroadcastHelper.sendPackageAddedForUser(snapshotComputer, str, snapshotComputer.getPackageStateInternal(str), i3, false, this.mAppPredictionServicePackage);
            return;
        }
        DeletePackageHelper deletePackageHelper = this.mDeletePackageHelper;
        deletePackageHelper.getClass();
        Slog.i("PackageManager", "overlaysInstallComplete() called with: token = [" + i + "]");
        PackageRemovedInfo packageRemovedInfo = (PackageRemovedInfo) deletePackageHelper.mRunningOverlayInstalls.get(i);
        deletePackageHelper.mRunningOverlayInstalls.delete(i);
        if (packageRemovedInfo != null) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("overlaysInstallComplete(): Calling sendSystemPackageUpdatedBroadcasts for package: "), packageRemovedInfo.mRemovedPackage, "PackageManager");
            deletePackageHelper.mBroadcastHelper.sendSystemPackageUpdatedBroadcasts(packageRemovedInfo);
        }
    }

    public final void performFstrimIfNeeded() {
        FreeStorageHelper freeStorageHelper = this.mFreeStorageHelper;
        freeStorageHelper.getClass();
        PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request fstrim");
        try {
            IStorageManager storageManager = InstallLocationUtils.getStorageManager();
            if (storageManager != null) {
                long j = Settings.Global.getLong(freeStorageHelper.mContext.getContentResolver(), "fstrim_mandatory_interval", 259200000L);
                if (j > 0) {
                    long currentTimeMillis = System.currentTimeMillis() - storageManager.lastMaintenance();
                    if (currentTimeMillis > j) {
                        Slog.w("PackageManager", "No disk maintenance in " + currentTimeMillis + "; running immediately");
                        storageManager.runMaintenance();
                    }
                }
            } else {
                Slog.e("PackageManager", "storageManager service unavailable!");
            }
        } catch (RemoteException unused) {
        }
    }

    public final ComputerEngine rebuildSnapshot(Computer computer, int i) {
        boolean z;
        long currentTimeMicro = SystemClock.currentTimeMicro();
        int used = computer == null ? -1 : computer.getUsed();
        ComputerEngine computerEngine = new ComputerEngine(new Snapshot(this, 2), i);
        long currentTimeMicro2 = SystemClock.currentTimeMicro();
        SnapshotStatistics snapshotStatistics = this.mSnapshotStatistics;
        if (snapshotStatistics != null) {
            int size = computerEngine.mSettings.getPackages().size();
            int i2 = (int) (currentTimeMicro2 - currentTimeMicro);
            synchronized (snapshotStatistics.mLock) {
                try {
                    snapshotStatistics.mPackageCount = size;
                    int bin = snapshotStatistics.mTimeBins.getBin(i2 / 1000);
                    int bin2 = snapshotStatistics.mUseBins.getBin(used);
                    z = true;
                    boolean z2 = i2 >= 10000;
                    boolean z3 = used <= 5;
                    SnapshotStatistics.Stats.m779$$Nest$mrebuild(snapshotStatistics.mShort[0], i2, used, bin, bin2, z2, z3);
                    SnapshotStatistics.Stats.m779$$Nest$mrebuild(snapshotStatistics.mLong[0], i2, used, bin, bin2, z2, z3);
                    if (i2 >= 30000) {
                        int i3 = snapshotStatistics.mEventsReported;
                        snapshotStatistics.mEventsReported = i3 + 1;
                        if (i3 < 10) {
                        }
                    }
                    z = false;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                EventLog.writeEvent(3131, Integer.valueOf(i2 / 1000), Integer.valueOf(used));
            }
        }
        return computerEngine;
    }

    public final PackageStateMutator.InitialState recordInitialState() {
        int i = this.mChangedPackagesTracker.mChangedPackagesSequenceNumber;
        this.mPackageStateMutator.getClass();
        return new PackageStateMutator.InitialState(i, PackageStateMutator.sStateChangeSequence.get());
    }

    public final void requestChecksumsInternal(Computer computer, String str, boolean z, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3, Executor executor, Handler handler) {
        String str2;
        String[] strArr;
        Objects.requireNonNull(str);
        Objects.requireNonNull(iOnChecksumsReadyListener);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(handler);
        ApplicationInfo applicationInfoInternal = computer.getApplicationInfoInternal(Binder.getCallingUid(), i3, 0L, str);
        if (applicationInfoInternal == null) {
            throw new ParcelableException(new PackageManager.NameNotFoundException(str));
        }
        InstallSourceInfo installSourceInfo = computer.getInstallSourceInfo(str, i3);
        if (installSourceInfo != null) {
            String initiatingPackageName = installSourceInfo.getInitiatingPackageName();
            if (PackageManagerServiceUtils.isInstalledByAdb(initiatingPackageName)) {
                initiatingPackageName = installSourceInfo.getInstallingPackageName();
            }
            str2 = initiatingPackageName;
        } else {
            str2 = null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Pair.create(null, new File(applicationInfoInternal.sourceDir)));
        if (z && (strArr = applicationInfoInternal.splitNames) != null) {
            int length = strArr.length;
            for (int i4 = 0; i4 < length; i4++) {
                arrayList.add(Pair.create(applicationInfoInternal.splitNames[i4], new File(applicationInfoInternal.splitSourceDirs[i4])));
            }
        }
        ((HandlerExecutor) executor).execute(new PackageManagerService$$ExternalSyntheticLambda53(this, handler, arrayList, i, i2, str2, list != null ? decodeCertificates(list) : null, iOnChecksumsReadyListener, 0));
    }

    public final void requestFileChecksums(File file, String str, int i, int i2, List list, IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        HandlerExecutor handlerExecutor = packageManagerServiceInjector.mBackgroundExecutor;
        Certificate[] decodeCertificates = list != null ? decodeCertificates(list) : null;
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Pair.create(null, file));
        handlerExecutor.execute(new PackageManagerService$$ExternalSyntheticLambda53(this, packageManagerServiceInjector.mBackgroundHandler, arrayList, i, i2, str, decodeCertificates, iOnChecksumsReadyListener, 1));
    }

    public final int[] resolveUserIds(int i) {
        return i == -1 ? this.mUserManager.getUserIds() : new int[]{i};
    }

    public final void restoreDisabledSystemPackageLIF(DeletePackageAction deletePackageAction, int[] iArr, boolean z) {
        int[] iArr2;
        InstallPackageHelper installPackageHelper = this.mInstallPackageHelper;
        installPackageHelper.getClass();
        PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        PackageRemovedInfo packageRemovedInfo = deletePackageAction.mRemovedInfo;
        PackageSetting packageSetting2 = deletePackageAction.mDisabledPs;
        synchronized (installPackageHelper.mPm.mLock) {
            installPackageHelper.mPm.mSettings.enableSystemPackageLPw(packageSetting2.pkg.getPackageName());
            boolean z2 = PackageManagerServiceUtils.DEBUG;
            if (packageSetting != null) {
                NativeLibraryHelper.removeNativeBinariesLI(packageSetting.legacyNativeLibraryPath);
            }
        }
        try {
            try {
                PackageManagerTracedLock packageManagerTracedLock = installPackageHelper.mPm.mInstallLock;
                packageManagerTracedLock.mLock.lock();
                if (packageRemovedInfo == null) {
                    iArr2 = null;
                } else {
                    try {
                        iArr2 = packageRemovedInfo.mOrigUsers;
                    } catch (Throwable th) {
                        try {
                            packageManagerTracedLock.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                installPackageHelper.installPackageFromSystemLIF(packageSetting2.mPathString, iArr, iArr2, z);
                packageManagerTracedLock.close();
                if (packageSetting2.pkg.isStub()) {
                    synchronized (installPackageHelper.mPm.mLock) {
                        installPackageHelper.disableStubPackage(deletePackageAction, packageSetting, iArr);
                    }
                }
            } catch (PackageManagerException e) {
                Slog.w("PackageManager", "Failed to restore system package:" + packageSetting.mName + ": " + e.getMessage());
                throw new SystemDeleteException(e);
            }
        } catch (Throwable th3) {
            if (packageSetting2.pkg.isStub()) {
                synchronized (installPackageHelper.mPm.mLock) {
                    installPackageHelper.disableStubPackage(deletePackageAction, packageSetting, iArr);
                }
            }
            throw th3;
        }
    }

    public final void restorePermissionsAndUpdateRolesForNewUserInstall(int i, String str) {
        String str2;
        this.mPermissionManager.restoreDelayedRuntimePermissions(str, i);
        synchronized (this.mLock) {
            str2 = (String) this.mSettings.mPendingDefaultBrowser.mStorage.get(i);
        }
        if (Objects.equals(str, str2)) {
            this.mDefaultAppProvider.setDefaultBrowser(i, str);
            synchronized (this.mLock) {
                WatchedSparseArray watchedSparseArray = this.mSettings.mPendingDefaultBrowser;
                Object removeReturnOld = watchedSparseArray.mStorage.removeReturnOld(i);
                watchedSparseArray.unregisterChildIf$4(removeReturnOld);
            }
        }
        this.mPreferredActivityHelper.updateDefaultHomeNotLocked(snapshotComputer(), i);
    }

    public final void schedulePruneUnusedStaticSharedLibraries(boolean z) {
        Handler handler = this.mHandler;
        handler.removeMessages(28);
        handler.sendEmptyMessageDelayed(28, z ? SystemProperties.getLong("debug.pm.prune_unused_shared_libraries_delay", PRUNE_UNUSED_SHARED_LIBRARIES_DELAY) : 0L);
    }

    public final void scheduleWritePackageRestrictions(int i) {
        invalidatePackageInfoCache();
        if (i == -1) {
            synchronized (this.mDirtyUsers) {
                try {
                    for (int i2 : this.mUserManager.getUserIds()) {
                        this.mDirtyUsers.add(Integer.valueOf(i2));
                    }
                } finally {
                }
            }
        } else {
            if (!this.mUserManager.mLocalService.exists(i)) {
                return;
            }
            synchronized (this.mDirtyUsers) {
                this.mDirtyUsers.add(Integer.valueOf(i));
            }
        }
        if (this.mBackgroundHandler.hasMessages(14)) {
            return;
        }
        Handler handler = this.mBackgroundHandler;
        handler.sendMessageDelayed(handler.obtainMessage(14, this), 10000L);
    }

    public final void scheduleWriteSettings() {
        invalidatePackageInfoCache();
        Handler handler = this.mHandler;
        if (handler.hasMessages(13)) {
            return;
        }
        handler.sendEmptyMessageDelayed(13, 10000L);
    }

    public final boolean setEnabledSettingInternalLocked(Computer computer, PackageSetting packageSetting, PackageManager.ComponentEnabledSetting componentEnabledSetting, int i, String str) {
        boolean restoreComponentLPw;
        int enabledState = componentEnabledSetting.getEnabledState();
        String packageName = componentEnabledSetting.getPackageName();
        if (componentEnabledSetting.isComponent()) {
            String className = componentEnabledSetting.getClassName();
            if (enabledState == 0) {
                restoreComponentLPw = packageSetting.restoreComponentLPw(i, className);
            } else if (enabledState == 1) {
                PackageUserStateImpl modifyUserStateComponents = packageSetting.modifyUserStateComponents(i, false, true);
                WatchedArraySet watchedArraySet = modifyUserStateComponents.mDisabledComponentsWatched;
                restoreComponentLPw = modifyUserStateComponents.mEnabledComponentsWatched.add(className) | (watchedArraySet != null ? watchedArraySet.remove(className) : false);
                if (restoreComponentLPw) {
                    packageSetting.onChanged$2();
                }
            } else if (enabledState != 2) {
                VaultKeeperService$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("Failed setComponentEnabledSetting: component ", packageName, "/", className, " requested an invalid new component state: "), enabledState, "PackageManager");
                restoreComponentLPw = false;
            } else {
                PackageUserStateImpl modifyUserStateComponents2 = packageSetting.modifyUserStateComponents(i, true, false);
                WatchedArraySet watchedArraySet2 = modifyUserStateComponents2.mEnabledComponentsWatched;
                restoreComponentLPw = modifyUserStateComponents2.mDisabledComponentsWatched.add(className) | (watchedArraySet2 != null ? watchedArraySet2.remove(className) : false);
                if (restoreComponentLPw) {
                    packageSetting.onChanged$2();
                }
            }
        } else {
            packageSetting.setEnabled(enabledState, i, str);
            if ((enabledState == 3 || enabledState == 2) && checkPermission("android.permission.SUSPEND_APPS", packageName, i) == 0) {
                unsuspendForSuspendingPackage(i, computer, packageName, true);
                this.mDistractingPackageHelper.removeDistractingPackageRestrictions(computer, computer.getAllAvailablePackageNames(), i);
            }
            restoreComponentLPw = true;
        }
        if (!restoreComponentLPw) {
            return false;
        }
        updateSequenceNumberLP(packageSetting, new int[]{i});
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            updateInstantAppInstallerLocked(packageName);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setKeepUninstalledPackagesInternal(Computer computer, List list) {
        Preconditions.checkNotNull(list);
        synchronized (this.mKeepUninstalledPackages) {
            try {
                ArrayList arrayList = new ArrayList(this.mKeepUninstalledPackages);
                arrayList.removeAll(list);
                this.mKeepUninstalledPackages.clear();
                this.mKeepUninstalledPackages.addAll(list);
                for (int i = 0; i < arrayList.size(); i++) {
                    String str = (String) arrayList.get(i);
                    PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
                    if (packageStateInternal != null) {
                        SparseArray sparseArray = packageStateInternal.mUserStates;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= sparseArray.size()) {
                                this.mHandler.post(new PackageManagerService$$ExternalSyntheticLambda51(0, this, str));
                                break;
                            } else if (((PackageUserStateInternal) sparseArray.valueAt(i2)).isInstalled()) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPackageStoppedState(final int r12, com.android.server.pm.Computer r13, final java.lang.String r14, final boolean r15) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.setPackageStoppedState(int, com.android.server.pm.Computer, java.lang.String, boolean):void");
    }

    public final void setSystemAppHiddenUntilInstalled(Computer computer, final String str, final boolean z) {
        AndroidPackageInternal androidPackageInternal;
        int callingUid = Binder.getCallingUid();
        boolean z2 = callingUid == 1001 || callingUid == 1000;
        if (!z2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setSystemAppHiddenUntilInstalled");
        }
        PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.isSystem() || (androidPackageInternal = packageStateInternal.pkg) == null) {
            return;
        }
        if (androidPackageInternal.isCoreApp() && !z2) {
            throw new SecurityException("Only system or phone callers can modify core apps");
        }
        commitPackageStateMutation(new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda54
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String str2 = str;
                boolean z3 = z;
                PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                PackageSetting packageSetting = packageStateMutator.forPackage(str2).mState;
                if (packageSetting != null) {
                    PackageStateUnserialized packageStateUnserialized = packageSetting.pkgState;
                    packageStateUnserialized.hiddenUntilInstalled = z3;
                    packageStateUnserialized.mPackageSetting.onChanged$2();
                }
                PackageSetting packageSetting2 = (PackageSetting) packageStateMutator.mDisabledStateFunction.apply(str2);
                if (packageSetting2 != null) {
                    packageStateMutator.mChangedStates.add(packageSetting2);
                }
                packageStateMutator.mStateWrite.mState = packageSetting2;
                if (packageSetting2 != null) {
                    PackageStateUnserialized packageStateUnserialized2 = packageSetting2.pkgState;
                    packageStateUnserialized2.hiddenUntilInstalled = z3;
                    packageStateUnserialized2.mPackageSetting.onChanged$2();
                }
            }
        });
    }

    public final boolean setSystemAppInstallState(int i, Computer computer, String str, boolean z) {
        AndroidPackageInternal androidPackageInternal;
        int callingUid = Binder.getCallingUid();
        boolean z2 = callingUid == 1001 || callingUid == 1000;
        if (!z2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setSystemAppHiddenUntilInstalled");
        }
        PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.isSystem() || (androidPackageInternal = packageStateInternal.pkg) == null) {
            return false;
        }
        if (androidPackageInternal.isCoreApp() && !z2) {
            throw new SecurityException("Only system or phone callers can modify core apps");
        }
        if (packageStateInternal.getUserStateOrDefault(i).isInstalled() == z) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                this.mInstallPackageHelper.installExistingPackageAsUser(str, i, 4194304, 3, null);
                return true;
            }
            this.mDeletePackageHelper.deletePackageVersionedInternal(new VersionedPackage(str, -1), new PackageManager.LegacyPackageDeleteObserver((IPackageDeleteObserver) null).getBinder(), i, 4, false);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Computer snapshotComputer() {
        return snapshotComputer(true);
    }

    public final Computer snapshotComputer(boolean z) {
        boolean holdsLock = Thread.holdsLock(this.mLock);
        if (z && holdsLock) {
            return this.mLiveComputer;
        }
        AtomicReference atomicReference = sSnapshot;
        Computer computer = (Computer) atomicReference.get();
        AtomicInteger atomicInteger = sSnapshotPendingVersion;
        int i = atomicInteger.get();
        if (computer != null && computer.getVersion() == i) {
            return computer.use();
        }
        if (holdsLock) {
            ComputerEngine rebuildSnapshot = rebuildSnapshot(computer, i);
            atomicReference.set(rebuildSnapshot);
            rebuildSnapshot.use();
            return rebuildSnapshot;
        }
        synchronized (this.mSnapshotLock) {
            try {
                Computer computer2 = (Computer) atomicReference.get();
                int i2 = atomicInteger.get();
                if (computer2 != null && computer2.getVersion() == i2) {
                    return computer2.use();
                }
                synchronized (this.mLock) {
                    Computer computer3 = (Computer) atomicReference.get();
                    int i3 = atomicInteger.get();
                    if (computer3 != null && computer3.getVersion() == i3) {
                        return computer3.use();
                    }
                    ComputerEngine rebuildSnapshot2 = rebuildSnapshot(computer3, i3);
                    atomicReference.set(rebuildSnapshot2);
                    rebuildSnapshot2.use();
                    return rebuildSnapshot2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void systemReady() {
        boolean z;
        int mainUserIdUnchecked;
        PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can claim the system is ready");
        final ContentResolver contentResolver = this.mContext.getContentResolver();
        List list = this.mReleaseOnSystemReady;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                F2fsUtils.releaseCompressedBlocks(contentResolver, (File) ((ArrayList) this.mReleaseOnSystemReady).get(size));
            }
            this.mReleaseOnSystemReady = null;
        }
        this.mSystemReady = true;
        ContentObserver contentObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.pm.PackageManagerService.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                boolean z3 = Settings.Global.getInt(contentResolver, "enable_ephemeral_feature", 1) == 0;
                for (int i : UserManagerService.getInstance().getUserIds()) {
                    boolean z4 = z3 || Settings.Secure.getIntForUser(contentResolver, "instant_apps_enabled", 1, i) == 0;
                    WatchedSparseBooleanArray watchedSparseBooleanArray = PackageManagerService.this.mWebInstantAppsDisabled;
                    watchedSparseBooleanArray.mStorage.put(i, z4);
                    watchedSparseBooleanArray.dispatchChange(watchedSparseBooleanArray);
                }
            }
        };
        int i = -1;
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("enable_ephemeral_feature"), false, contentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("instant_apps_enabled"), false, contentObserver, -1);
        contentObserver.onChange(true);
        AppsFilterImpl appsFilterImpl = this.mAppsFilter;
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        OverlayReferenceMapper overlayReferenceMapper = appsFilterImpl.mOverlayReferenceMapper;
        synchronized (overlayReferenceMapper.mLock) {
            try {
                if (overlayReferenceMapper.mDeferRebuild) {
                    overlayReferenceMapper.rebuild();
                    overlayReferenceMapper.mDeferRebuild = false;
                }
            } finally {
            }
        }
        appsFilterImpl.mFeatureConfig.onSystemReady();
        appsFilterImpl.mHandler.postDelayed(new AppsFilterImpl$$ExternalSyntheticLambda1(appsFilterImpl, packageManagerInternal, 10000L), 10000L);
        CarrierAppUtils.disableCarrierAppsUntilPrivileged(this.mContext.getOpPackageName(), 0, this.mContext);
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.config_tether_upstream_types);
        String[] stringArray2 = this.mContext.getResources().getStringArray(R.array.config_tether_dhcp_range);
        if (!ArrayUtils.isEmpty(stringArray)) {
            String str = SystemProperties.get("ro.boot.hardware.sku");
            if (TextUtils.isEmpty(str) || !ArrayUtils.contains(stringArray2, str)) {
                Computer snapshotComputer = snapshotComputer();
                for (String str2 : stringArray) {
                    setSystemAppHiddenUntilInstalled(snapshotComputer, str2, true);
                    List users = this.mInjector.getUserManagerService().mLocalService.getUsers(false);
                    int i2 = 0;
                    while (true) {
                        ArrayList arrayList = (ArrayList) users;
                        if (i2 < arrayList.size()) {
                            setSystemAppInstallState(((UserInfo) arrayList.get(i2)).id, snapshotComputer, str2, false);
                            i2++;
                        }
                    }
                }
            }
        }
        ParsingPackageUtils.setCompatibilityModeEnabled(Settings.Global.getInt(this.mContext.getContentResolver(), "compatibility_mode", 1) == 1);
        synchronized (this.mLock) {
            try {
                ArrayList systemReady = this.mSettings.systemReady(this.mComponentResolver);
                for (int i3 = 0; i3 < systemReady.size(); i3++) {
                    this.mSettings.writePackageRestrictionsLPr(((Integer) systemReady.get(i3)).intValue(), false);
                }
            } finally {
            }
        }
        UserManagerService userManagerService = this.mUserManager;
        userManagerService.getClass();
        userManagerService.mAppOpsService = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        synchronized (userManagerService.mRestrictionsLock) {
            userManagerService.updateUserRestrictionsInternalLR(0, null);
            userManagerService.scheduleWriteUser(0);
        }
        userManagerService.mContext.registerReceiver(userManagerService.mDisableQuietModeCallback, new IntentFilter("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK"), null, userManagerService.mHandler);
        userManagerService.mContext.registerReceiver(userManagerService.mConfigurationChangeReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"), null, userManagerService.mHandler);
        if (UserManagerService.isAutoLockForPrivateSpaceEnabled() && (mainUserIdUnchecked = userManagerService.getMainUserIdUnchecked()) != -10000) {
            userManagerService.mContext.getContentResolver().registerContentObserverAsUser(Settings.Secure.getUriFor("private_space_auto_lock"), false, userManagerService.mPrivateSpaceAutoLockSettingsObserver, UserHandle.of(mainUserIdUnchecked));
            userManagerService.setOrUpdateAutoLockPreferenceForPrivateProfile(Settings.Secure.getIntForUser(userManagerService.mContext.getContentResolver(), "private_space_auto_lock", 2, mainUserIdUnchecked));
        }
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceAutolockOnRestarts() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            userManagerService.autoLockPrivateSpace();
        }
        ((StorageManager) this.mInjector.mGetSystemServiceProducer.produce(StorageManager.class)).registerListener(this.mStorageEventHelper);
        PackageInstallerService packageInstallerService = this.mInstallerService;
        packageInstallerService.mAppOps = (AppOpsManager) packageInstallerService.mContext.getSystemService(AppOpsManager.class);
        final StagingManager stagingManager = packageInstallerService.mStagingManager;
        StagingManager.Lifecycle lifecycle = new StagingManager.Lifecycle(stagingManager.mContext);
        StagingManager.Lifecycle.sStagingManager = stagingManager;
        ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class)).startService(lifecycle);
        stagingManager.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.pm.StagingManager.2
            public AnonymousClass2() {
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                StagingManager.this.onBootCompletedBroadcastReceived();
                context.unregisterReceiver(this);
            }
        }, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        stagingManager.mFailureReasonFile.delete();
        final GentleUpdateHelper gentleUpdateHelper = packageInstallerService.mGentleUpdateHelper;
        ActivityManager activityManager = (ActivityManager) gentleUpdateHelper.mContext.getSystemService(ActivityManager.class);
        activityManager.addOnUidImportanceListener(new ActivityManager.OnUidImportanceListener() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda1
            public final void onUidImportance(int i4, int i5) {
                GentleUpdateHelper.$r8$lambda$JQiy908NuzFx59XC1nxYVaiXKqM(GentleUpdateHelper.this, i4, i5);
            }
        }, 100);
        activityManager.addOnUidImportanceListener(new ActivityManager.OnUidImportanceListener() { // from class: com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda1
            public final void onUidImportance(int i4, int i5) {
                GentleUpdateHelper.$r8$lambda$JQiy908NuzFx59XC1nxYVaiXKqM(GentleUpdateHelper.this, i4, i5);
            }
        }, 125);
        synchronized (packageInstallerService.mSessions) {
            try {
                packageInstallerService.readSessionsLocked();
                packageInstallerService.expireSessionsLocked();
                packageInstallerService.reconcileStagesLocked(StorageManager.UUID_PRIVATE_INTERNAL);
                File[] listFiles = packageInstallerService.mSessionsDir.listFiles();
                ArraySet arraySet = new ArraySet();
                if (listFiles != null) {
                    arraySet.ensureCapacity(listFiles.length);
                    Collections.addAll(arraySet, listFiles);
                }
                for (int i4 = 0; i4 < packageInstallerService.mSessions.size(); i4++) {
                    arraySet.remove(new File(packageInstallerService.mSessionsDir, "app_icon." + ((PackageInstallerSession) packageInstallerService.mSessions.valueAt(i4)).sessionId + ".png"));
                }
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    File file = (File) it.next();
                    Slog.w("PackageInstaller", "Deleting orphan icon " + file);
                    file.delete();
                }
                RequestThrottle requestThrottle = packageInstallerService.mSettingsWriteRequest;
                requestThrottle.mLastRequest.incrementAndGet();
                requestThrottle.runInternal();
            } finally {
            }
        }
        if (PMRune.PM_SPEED_INSTALL) {
            File file2 = new File(Environment.getDataDirectory(), "apk-tmp");
            if (file2.exists()) {
                FileUtils.deleteContents(file2);
            }
        }
        this.mPackageDexOptimizer.mSystemReady = true;
        UserManagerService userManagerService2 = this.mUserManager;
        String str3 = StorageManager.UUID_PRIVATE_INTERNAL;
        userManagerService2.reconcileUsers(str3);
        this.mStorageEventHelper.reconcileApps(snapshotComputer(), str3);
        PermissionManagerService.this.mPermissionManagerServiceImpl.onSystemReady();
        if (CoreRune.SYSFW_APP_SPEG) {
            final SpegService spegService = (SpegService) LocalServices.getService(SpegService.class);
            this.mSpeg = spegService;
            if (spegService != null) {
                final ContentResolver contentResolver2 = spegService.mContext.getContentResolver();
                final Handler handler = spegService.mPm.mHandler;
                ContentObserver anonymousClass1 = new ContentObserver
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0306: CONSTRUCTOR (r5v42 'anonymousClass1' android.database.ContentObserver) = 
                      (r0v81 'spegService' com.android.server.pm.SpegService A[DONT_INLINE])
                      (r8v35 'handler' android.os.Handler A[DONT_INLINE])
                      (r2v57 'contentResolver2' android.content.ContentResolver A[DONT_INLINE])
                     A[DECLARE_VAR, MD:(com.android.server.pm.SpegService, android.os.Handler, android.content.ContentResolver):void (m)] call: com.android.server.pm.SpegService.1.<init>(com.android.server.pm.SpegService, android.os.Handler, android.content.ContentResolver):void type: CONSTRUCTOR in method: com.android.server.pm.PackageManagerService.systemReady():void, file: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.server.pm.SpegService.1.<init>(com.android.server.pm.SpegService, android.os.Handler, android.content.ContentResolver):void, class status: GENERATED_AND_UNLOADED
                    	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:290)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:829)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                    	... 27 more
                    */
                /*
                    Method dump skipped, instructions count: 1458
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.systemReady():void");
            }

            /* JADX WARN: Code restructure failed: missing block: B:20:0x0073, code lost:
            
                r8 = ((com.android.server.pm.PackageSetting) r4.getValue()).mName;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void throwSystemRequiredPackageState(com.android.server.pm.ComputerLocked r7, android.content.Intent r8, java.lang.String r9) {
                /*
                    r6 = this;
                    r3 = 1966592(0x1e0200, double:9.716255E-318)
                    r5 = 0
                    r0 = r7
                    r1 = r8
                    r2 = r9
                    java.util.List r7 = r0.queryIntentActivitiesInternal(r1, r2, r3, r5)
                    int r8 = r7.size()
                    if (r8 == 0) goto Lc3
                    r8 = 0
                    java.lang.Object r7 = r7.get(r8)
                    android.content.pm.ResolveInfo r7 = (android.content.pm.ResolveInfo) r7
                    android.content.pm.ComponentInfo r7 = r7.getComponentInfo()
                    java.lang.String r7 = r7.packageName
                    com.android.server.pm.Settings r6 = r6.mSettings
                    com.android.server.pm.PackageSetting r7 = r6.getPackageLPr(r7)
                    if (r7 == 0) goto Lc3
                    com.android.server.utils.WatchedArrayMap r6 = r6.mPackages
                    com.android.server.pm.pkg.PackageUserStateInternal r8 = r7.readUserState(r8)
                    boolean r9 = r8.isHidden()
                    boolean r0 = r8.isInstalled()
                    boolean r1 = r8.isSuspended()
                    int r2 = r8.getEnabledState()
                    java.lang.String r8 = r8.getLastDisableAppCaller()
                    if (r8 == 0) goto L8e
                    java.lang.String r3 = "ApplicationPolicy"
                    boolean r3 = r8.startsWith(r3)     // Catch: java.lang.Exception -> L7c
                    if (r3 == 0) goto L8e
                    r3 = 20
                    java.lang.String r3 = r8.substring(r3)     // Catch: java.lang.Exception -> L7c
                    int r3 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.Exception -> L7c
                    java.util.Set r6 = r6.entrySet()     // Catch: java.lang.Exception -> L7c
                    java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Exception -> L7c
                L5c:
                    boolean r4 = r6.hasNext()     // Catch: java.lang.Exception -> L7c
                    if (r4 == 0) goto L8e
                    java.lang.Object r4 = r6.next()     // Catch: java.lang.Exception -> L7c
                    java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch: java.lang.Exception -> L7c
                    java.lang.Object r5 = r4.getValue()     // Catch: java.lang.Exception -> L7c
                    com.android.server.pm.PackageSetting r5 = (com.android.server.pm.PackageSetting) r5     // Catch: java.lang.Exception -> L7c
                    int r5 = r5.mAppId     // Catch: java.lang.Exception -> L7c
                    if (r5 == r3) goto L73
                    goto L5c
                L73:
                    java.lang.Object r6 = r4.getValue()     // Catch: java.lang.Exception -> L7c
                    com.android.server.pm.PackageSetting r6 = (com.android.server.pm.PackageSetting) r6     // Catch: java.lang.Exception -> L7c
                    java.lang.String r8 = r6.mName     // Catch: java.lang.Exception -> L7c
                    goto L8e
                L7c:
                    r6 = move-exception
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder
                    java.lang.String r4 = "Fail to find Required Package Disabler : "
                    r3.<init>(r4)
                    r3.append(r6)
                    java.lang.String r6 = r3.toString()
                    com.samsung.android.server.pm.PmLog.logDebugInfo(r6)
                L8e:
                    java.lang.RuntimeException r6 = new java.lang.RuntimeException
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder
                    java.lang.String r4 = "RequiredPackage can not be queried. pkg: "
                    r3.<init>(r4)
                    java.lang.String r7 = r7.mName
                    java.lang.String r4 = ", enabled: "
                    java.lang.String r5 = ", lastDisableCaller: "
                    com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r2, r7, r4, r5, r3)
                    r3.append(r8)
                    java.lang.String r7 = ", isHidden: "
                    r3.append(r7)
                    r3.append(r9)
                    java.lang.String r7 = ", isInstalled: "
                    r3.append(r7)
                    r3.append(r0)
                    java.lang.String r7 = ", isSuspended: "
                    r3.append(r7)
                    r3.append(r1)
                    java.lang.String r7 = r3.toString()
                    r6.<init>(r7)
                    throw r6
                Lc3:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerService.throwSystemRequiredPackageState(com.android.server.pm.ComputerLocked, android.content.Intent, java.lang.String):void");
            }

            public final void unsuspendForSuspendingPackage(int i, Computer computer, String str, boolean z) {
                String[] strArr = (String[]) computer.getPackageStates().keySet().toArray(new String[0]);
                final UserPackage of = UserPackage.of(i, str);
                Objects.requireNonNull(of);
                Predicate predicate = new Predicate() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda62
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return of.equals((UserPackage) obj);
                    }
                };
                boolean crossUserSuspensionEnabledRo = android.app.admin.flags.Flags.crossUserSuspensionEnabledRo();
                SuspendPackageHelper suspendPackageHelper = this.mSuspendPackageHelper;
                if (!crossUserSuspensionEnabledRo || !z) {
                    suspendPackageHelper.removeSuspensionsBySuspendingPackage(computer, strArr, predicate, i);
                    return;
                }
                for (int i2 : this.mUserManager.getUserIds()) {
                    suspendPackageHelper.removeSuspensionsBySuspendingPackage(computer, strArr, predicate, i2);
                }
            }

            public void updateComponentLabelIcon(final ComponentName componentName, final String str, final Integer num, final int i) {
                if (componentName == null) {
                    throw new IllegalArgumentException("Must specify a component");
                }
                int callingUid = Binder.getCallingUid();
                String packageName = componentName.getPackageName();
                Computer snapshotComputer = snapshotComputer();
                if (!UserHandle.isSameApp(callingUid, snapshotComputer.getPackageUid(packageName, 0L, i))) {
                    throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUid, "The calling UID (", ") does not match the target UID"));
                }
                String string = this.mContext.getString(R.string.ext_media_checking_notification_title);
                if (TextUtils.isEmpty(string)) {
                    throw new SecurityException("There is no package defined as allowed to change a component's label or icon");
                }
                int packageUid = snapshotComputer.getPackageUid(string, 1048576L, i);
                if (packageUid == -1 || !UserHandle.isSameApp(callingUid, packageUid)) {
                    throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUid, "The calling UID (", ") is not allowed to change a component's label or icon"));
                }
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(packageName);
                if (packageStateInternal == null || packageStateInternal.pkg == null || !(packageStateInternal.isSystem() || packageStateInternal.pkgState.updatedSystemApp)) {
                    throw new SecurityException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Changing the label is not allowed for "));
                }
                if (!snapshotComputer.getComponentResolver().componentExists(componentName)) {
                    throw new IllegalArgumentException("Component " + componentName + " not found");
                }
                Pair overrideLabelIconForComponent = packageStateInternal.getUserStateOrDefault(i).getOverrideLabelIconForComponent(componentName);
                String str2 = overrideLabelIconForComponent == null ? null : (String) overrideLabelIconForComponent.first;
                Integer num2 = overrideLabelIconForComponent == null ? null : (Integer) overrideLabelIconForComponent.second;
                if (TextUtils.equals(str2, str) && Objects.equals(num2, num)) {
                    return;
                }
                commitPackageStateMutation(null, packageName, new Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i2 = i;
                        ComponentName componentName2 = componentName;
                        String str3 = str;
                        Integer num3 = num;
                        PackageUserStateImpl packageUserStateImpl = ((PackageStateMutator.StateWriteWrapper) obj).userState(i2).mUserState;
                        if (packageUserStateImpl != null) {
                            packageUserStateImpl.overrideLabelAndIcon(componentName2, str3, num3);
                        }
                    }
                });
                this.mPendingBroadcasts.addComponent(i, packageName, componentName.getClassName());
                Handler handler = this.mHandler;
                if (handler.hasMessages(1)) {
                    return;
                }
                handler.sendEmptyMessageDelayed(1, 1000L);
            }

            public final void updateInstantAppInstallerLocked(String str) {
                boolean z;
                String str2;
                ActivityInfo activityInfo;
                ActivityInfo activityInfo2 = this.mInstantAppInstallerActivity;
                if (activityInfo2 == null || activityInfo2.getComponentName().getPackageName().equals(str)) {
                    boolean z2 = this.mIsEngBuild;
                    String[] strArr = z2 ? new String[]{"android.intent.action.INSTALL_INSTANT_APP_PACKAGE_TEST", "android.intent.action.INSTALL_INSTANT_APP_PACKAGE"} : new String[]{"android.intent.action.INSTALL_INSTANT_APP_PACKAGE"};
                    int i = (z2 ? 0 : 1048576) | (-2146697216);
                    Computer snapshotComputer = snapshotComputer();
                    Intent intent = new Intent();
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setDataAndType(Uri.fromFile(new File("foo.apk")), "application/vnd.android.package-archive");
                    int length = strArr.length;
                    int i2 = 0;
                    List list = null;
                    while (true) {
                        z = DEBUG_INSTANT;
                        if (i2 >= length) {
                            str2 = "PackageManager";
                            break;
                        }
                        String str3 = strArr[i2];
                        intent.setAction(str3);
                        str2 = "PackageManager";
                        int i3 = i2;
                        list = snapshotComputer.queryIntentActivitiesInternal(intent, "application/vnd.android.package-archive", i, 0);
                        if (!list.isEmpty()) {
                            break;
                        }
                        if (z) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m("Instant App installer not found with ", str3, str2);
                        }
                        i2 = i3 + 1;
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (checkPermission("android.permission.INSTALL_PACKAGES", ((ResolveInfo) it.next()).activityInfo.packageName, 0) != 0 && !z2) {
                            it.remove();
                        }
                    }
                    if (list.size() == 0) {
                        activityInfo = null;
                    } else {
                        if (list.size() != 1) {
                            throw new RuntimeException("There must be at most one ephemeral installer; found " + list);
                        }
                        activityInfo = (ActivityInfo) ((ResolveInfo) list.get(0)).getComponentInfo();
                    }
                    if (activityInfo == null) {
                        if (z) {
                            Slog.d(str2, "Clear ephemeral installer activity");
                        }
                        this.mInstantAppInstallerActivity = null;
                        onChange();
                        return;
                    }
                    if (z) {
                        Slog.d(str2, "Set ephemeral installer activity: " + activityInfo.getComponentName());
                    }
                    this.mInstantAppInstallerActivity = activityInfo;
                    activityInfo.flags |= 288;
                    activityInfo.exported = true;
                    activityInfo.enabled = true;
                    ResolveInfo resolveInfo = this.mInstantAppInstallerInfo;
                    resolveInfo.activityInfo = activityInfo;
                    resolveInfo.priority = 1;
                    resolveInfo.preferredOrder = 1;
                    resolveInfo.isDefault = true;
                    resolveInfo.match = 5799936;
                    onChange();
                }
            }

            public final void updateLocaleOverlaysForPackage(int i, int i2, InstallLocaleOverlaysType installLocaleOverlaysType, int i3, String str) {
                if (str == null) {
                    Slog.e("PackageManager", "updateLocaleOverlaysForPackage() called with null packageName");
                    overlaysInstallComplete(i, i2, installLocaleOverlaysType, i3, str, null);
                    return;
                }
                LocaleOverlayManagerWrapper localeOverlayManagerWrapper = LocaleOverlayManagerWrapper.getInstance(this.mContext);
                try {
                    OverlayChangeObserverImpl overlayChangeObserverImpl = new OverlayChangeObserverImpl(i, i2, installLocaleOverlaysType, i3, str, this);
                    this.mHandler.postDelayed(overlayChangeObserverImpl.mTimeoutRunnable, 30000L);
                    localeOverlayManagerWrapper.applyLocalesForPackage(str, i, i3, overlayChangeObserverImpl);
                } catch (Exception e) {
                    overlaysInstallComplete(i, i2, installLocaleOverlaysType, i3, str, null);
                    e.printStackTrace();
                }
            }

            public final void updatePackagesIfNeeded() {
                int i;
                final DexOptHelper dexOptHelper = this.mDexOptHelper;
                dexOptHelper.getClass();
                PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request package update");
                PackageManagerService packageManagerService = dexOptHelper.mPm;
                if (packageManagerService.mFirstBoot) {
                    i = 0;
                } else if (packageManagerService.isDeviceUpgrading()) {
                    i = 1;
                } else if (!DexOptHelper.hasBcpApexesChanged()) {
                    return;
                } else {
                    i = 13;
                }
                Log.i("PackageManager", "Starting boot dexopt for reason ".concat(DexoptOptions.convertToArtServiceDexoptReason(i)));
                long nanoTime = System.nanoTime();
                dexOptHelper.mBootDexoptStartTime = nanoTime;
                DexOptHelper.getArtManagerLocal().onBoot(DexoptOptions.convertToArtServiceDexoptReason(i), Executors.newSingleThreadExecutor(), new Consumer() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((WindowManagerInternal) DexOptHelper.this.mPm.mInjector.mGetLocalServiceProducer.produce(WindowManagerInternal.class)).showBootDialog(((OperationProgress) obj).getPercentage());
                    }
                });
                if (i == 1) {
                    PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT to compile designatedPkgs for boot-after-ota");
                    String[] strArr = DexOptHelper.designatedPkgs;
                    for (int i2 = 0; i2 < 6; i2++) {
                        String str = strArr[i2];
                        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                        try {
                            DexOptHelper.getArtManagerLocal().dexoptPackage(withFilteredSnapshot, str, new DexoptParams.Builder("boot-after-ota").setCompilerFilter("speed-profile").build());
                            if (withFilteredSnapshot != null) {
                                withFilteredSnapshot.close();
                            }
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
                }
                PackageManagerServiceUtils.logCriticalInfo(4, "Finish boot dexopt for " + DexoptOptions.convertToArtServiceDexoptReason(i) + " takes " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime) + "ms");
            }

            public final void updateSequenceNumberLP(PackageSetting packageSetting, int[] iArr) {
                ChangedPackagesTracker changedPackagesTracker = this.mChangedPackagesTracker;
                String str = packageSetting.mName;
                synchronized (changedPackagesTracker.mLock) {
                    try {
                        for (int length = iArr.length - 1; length >= 0; length--) {
                            int i = iArr[length];
                            SparseArray sparseArray = (SparseArray) changedPackagesTracker.mUserIdToSequenceToPackage.get(i);
                            if (sparseArray == null) {
                                sparseArray = new SparseArray();
                                changedPackagesTracker.mUserIdToSequenceToPackage.put(i, sparseArray);
                            }
                            Map map = (Map) changedPackagesTracker.mChangedPackagesSequenceNumbers.get(i);
                            if (map == null) {
                                map = new HashMap();
                                changedPackagesTracker.mChangedPackagesSequenceNumbers.put(i, map);
                            }
                            Integer num = (Integer) map.get(str);
                            if (num != null) {
                                sparseArray.remove(num.intValue());
                            }
                            sparseArray.put(changedPackagesTracker.mChangedPackagesSequenceNumber, str);
                            map.put(str, Integer.valueOf(changedPackagesTracker.mChangedPackagesSequenceNumber));
                        }
                        changedPackagesTracker.mChangedPackagesSequenceNumber++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void verifyHoldLockToken(IBinder iBinder) {
                if (!Build.IS_DEBUGGABLE) {
                    throw new SecurityException("holdLock requires a debuggable build");
                }
                if (iBinder == null) {
                    throw new SecurityException("null holdLockToken");
                }
                if (iBinder.queryLocalInterface("holdLock:" + Binder.getCallingUid()) != this) {
                    throw new SecurityException("Invalid holdLock() token");
                }
            }

            public final Pair verifyReplacingVersionCode(PackageInfoLite packageInfoLite, long j, int i) {
                InstallPackageHelper installPackageHelper = this.mInstallPackageHelper;
                installPackageHelper.getClass();
                if (packageInfoLite.verifiers == null) {
                    return Pair.create(-22, "Package verifiers are not set");
                }
                if ((131072 & i) != 0) {
                    String str = packageInfoLite.packageName;
                    PackageInfo packageInfo = installPackageHelper.mPm.snapshotComputer().getPackageInfo(str, 1073741824L, 0);
                    if (packageInfo == null) {
                        String str2 = "Attempting to install new APEX package " + str;
                        Slog.w("PackageManager", str2);
                        return Pair.create(-23, str2);
                    }
                    long longVersionCode = packageInfo.getLongVersionCode();
                    if (j != -1 && longVersionCode != j) {
                        String str3 = "Installed version of APEX package " + str + " does not match required. Active version: " + longVersionCode + " required: " + j;
                        Slog.w("PackageManager", str3);
                        return Pair.create(-121, str3);
                    }
                    boolean z = (packageInfo.applicationInfo.flags & 2) != 0;
                    long longVersionCode2 = packageInfoLite.getLongVersionCode();
                    if (PackageManagerServiceUtils.isDowngradePermitted(i, z) || longVersionCode2 >= longVersionCode) {
                        return Pair.create(1, null);
                    }
                    String str4 = "Downgrade of APEX package " + str + " is not allowed. Active version: " + longVersionCode + " attempted: " + longVersionCode2;
                    Slog.w("PackageManager", str4);
                    return Pair.create(-25, str4);
                }
                String str5 = packageInfoLite.packageName;
                synchronized (installPackageHelper.mPm.mLock) {
                    try {
                        PackageSetting packageLPr = installPackageHelper.mPm.mSettings.getPackageLPr(str5);
                        if (packageLPr == null) {
                            if (j == -1) {
                                return Pair.create(1, null);
                            }
                            String str6 = "Required installed version code was " + j + " but package is not installed";
                            Slog.w("PackageManager", str6);
                            return Pair.create(-121, str6);
                        }
                        AndroidPackageInternal androidPackageInternal = packageLPr.pkg;
                        if (j != -1) {
                            if (androidPackageInternal == null) {
                                String str7 = "Required installed version code was " + j + " but package is not installed";
                                Slog.w("PackageManager", str7);
                                return Pair.create(-121, str7);
                            }
                            if (androidPackageInternal.getLongVersionCode() != j) {
                                String str8 = "Required installed version code was " + j + " but actual installed version is " + androidPackageInternal.getLongVersionCode();
                                Slog.w("PackageManager", str8);
                                return Pair.create(-121, str8);
                            }
                        }
                        if (androidPackageInternal == null) {
                            if (!PackageManagerServiceUtils.isDowngradePermitted(i, packageLPr.getBoolean(32))) {
                                PackageManagerServiceUtils.checkDowngrade(packageLPr.versionCode, packageLPr.mBaseRevisionCode, packageLPr.getSplitNames(), packageLPr.getSplitRevisionCodes(), packageInfoLite);
                            }
                            return Pair.create(1, null);
                        }
                        if (!androidPackageInternal.isSdkLibrary()) {
                            if (!PackageManagerServiceUtils.isDowngradePermitted(i, androidPackageInternal.isDebuggable())) {
                                PackageManagerServiceUtils.checkDowngrade(androidPackageInternal.getLongVersionCode(), androidPackageInternal.getBaseRevisionCode(), androidPackageInternal.getSplitNames(), androidPackageInternal.getSplitRevisionCodes(), packageInfoLite);
                            } else if (packageLPr.isSystem()) {
                                Settings settings = installPackageHelper.mPm.mSettings;
                                settings.getClass();
                                PackageSetting disabledSystemPkgLPr = settings.getDisabledSystemPkgLPr(packageLPr.mName);
                                if (disabledSystemPkgLPr != null) {
                                    androidPackageInternal = disabledSystemPkgLPr.pkg;
                                }
                                if (!Build.IS_DEBUGGABLE && !androidPackageInternal.isDebuggable()) {
                                    PackageManagerServiceUtils.checkDowngrade(androidPackageInternal.getLongVersionCode(), androidPackageInternal.getBaseRevisionCode(), androidPackageInternal.getSplitNames(), androidPackageInternal.getSplitRevisionCodes(), packageInfoLite);
                                }
                            }
                        }
                        return Pair.create(1, null);
                    } catch (PackageManagerException e) {
                        String str9 = "Downgrade detected on app uninstalled with DELETE_KEEP_DATA: " + e.getMessage();
                        Slog.w("PackageManager", str9);
                        return Pair.create(-25, str9);
                    } catch (PackageManagerException e2) {
                        String str10 = "Downgrade detected: " + e2.getMessage();
                        Slog.w("PackageManager", str10);
                        return Pair.create(-25, str10);
                    } catch (PackageManagerException e3) {
                        String str11 = "System app: " + str5 + " cannot be downgraded to older than its preloaded version on the system image. " + e3.getMessage();
                        Slog.w("PackageManager", str11);
                        return Pair.create(-25, str11);
                    } finally {
                    }
                }
            }

            public final void waitForAppDataPrepared() {
                PmLifecycleImpl pmLifecycleImpl = this.mPmLifecycle;
                pmLifecycleImpl.getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                try {
                    Watchdog.getInstance().pauseWatchingCurrentThread("prepackageinstaller");
                    PmCustomInjector pmCustomInjector = pmLifecycleImpl.mCustomInjector;
                    Future runPrePackageInstaller = ((PrePackageInstallerBase) pmCustomInjector.mPrePackageInstaller.get(pmCustomInjector.mPmService, pmCustomInjector.mInjector)).runPrePackageInstaller();
                    if (runPrePackageInstaller != null) {
                        ConcurrentUtils.waitForFutureNoInterrupt(runPrePackageInstaller, "wait for pre-installing");
                    }
                    Watchdog.getInstance().resumeWatchingCurrentThread("prepackageinstaller");
                    Slog.i("PrePackageInstaller", "Install took " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
                    boolean z = true;
                    if (this.mIsUpgrade) {
                        InstallPackageHelper installPackageHelper = this.mInstallPackageHelper;
                        installPackageHelper.getClass();
                        List<String> parsePackages = PmConfigParser.parsePackages("/system/etc/system_to_data_app_list.xml");
                        int[] userIds = installPackageHelper.mPm.mUserManager.getUserIds();
                        synchronized (installPackageHelper.mPm.mInstallLock) {
                            synchronized (installPackageHelper.mPm.mLock) {
                                try {
                                    boolean z2 = false;
                                    for (String str : parsePackages) {
                                        PackageSetting packageSetting = (PackageSetting) installPackageHelper.mPm.mSettings.mPackages.mStorage.get(str);
                                        if (packageSetting != null && packageSetting.pkg == null && !packageSetting.isExternalStorage()) {
                                            PackageManagerServiceUtils.logCriticalInfo(5, "Clear non-installed migration package " + str);
                                            installPackageHelper.mRemovePackageHelper.removePackageData(packageSetting, userIds);
                                            z2 = true;
                                        }
                                    }
                                    if (z2) {
                                        installPackageHelper.mPm.mSettings.writeLPr(installPackageHelper.mPm.snapshotComputer(), false);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                    boolean z3 = this.mFirstBoot;
                    PmCustomInjector pmCustomInjector2 = this.mCustomInjector;
                    OmcInstallHelper omcInstallHelper = (OmcInstallHelper) pmCustomInjector2.mOmcInstallHelperProducer.get(pmCustomInjector2.mPmService, pmCustomInjector2.mInjector);
                    if (!omcInstallHelper.mNeedsOmcInit && !omcInstallHelper.mNeedsTssInit) {
                        z = false;
                    }
                    Context context = this.mContext;
                    if (z3 || z) {
                        String str2 = SystemProperties.get("persist.omc.sales_code");
                        if (TextUtils.isEmpty(str2)) {
                            str2 = SystemProperties.get("ro.csc.sales_code");
                        }
                        if (!TextUtils.isEmpty(str2) && "VZW".equals(str2)) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add("com.verizon.mips.services");
                            arrayList.add("com.vzw.hss.myverizon");
                            arrayList.add("com.verizon.pushtotalkplus");
                            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                String str3 = (String) it.next();
                                try {
                                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str3, 0);
                                    if (packageInfo != null && packageInfo.applicationInfo.isSystemApp()) {
                                        appOpsManager.setMode("android:system_alert_window", packageInfo.applicationInfo.uid, str3, 0);
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }
                    if (z3) {
                        try {
                            PackageInfo packageInfo2 = context.getPackageManager().getPackageInfo("com.sec.android.app.samsungapps", 0);
                            if (packageInfo2 != null && packageInfo2.applicationInfo.isSystemApp()) {
                                ((AppOpsManager) context.getSystemService(AppOpsManager.class)).setMode("android:system_alert_window", packageInfo2.applicationInfo.uid, "com.sec.android.app.samsungapps", 0);
                            }
                        } catch (Exception unused2) {
                        }
                    }
                    Future future = this.mPrepareAppDataFuture;
                    if (future == null) {
                        return;
                    }
                    ConcurrentUtils.waitForFutureNoInterrupt(future, "wait for prepareAppData");
                    this.mPrepareAppDataFuture = null;
                } catch (Throwable th2) {
                    Watchdog.getInstance().resumeWatchingCurrentThread("prepackageinstaller");
                    throw th2;
                }
            }

            public final void writeSettings(boolean z) {
                synchronized (this.mLock) {
                    this.mHandler.removeMessages(13);
                    this.mBackgroundHandler.removeMessages(14);
                    writeSettingsLPrTEMP(z);
                    synchronized (this.mDirtyUsers) {
                        this.mDirtyUsers.clear();
                    }
                }
            }

            public final void writeSettingsLPrTEMP(boolean z) {
                snapshotComputer(false);
                Settings settings = this.mSettings;
                PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionsTEMP(settings.mPermissions);
                settings.writeLPr(this.mLiveComputer, z);
            }
        }
