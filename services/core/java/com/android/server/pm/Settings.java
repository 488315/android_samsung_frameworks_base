package com.android.server.pm;

import android.app.compat.ChangeIdStateCache;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackagePartitions;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.overlay.OverlayPaths;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.PatternMatcher;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.internal.logging.EventLogTags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.JournaledFile;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.permission.persistence.RuntimePermissionsPersistence;
import com.android.permission.persistence.RuntimePermissionsState;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.Installer;
import com.android.server.pm.ResilientAtomicFile;
import com.android.server.pm.Settings;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.permission.LegacyPermissionDataProvider;
import com.android.server.pm.permission.LegacyPermissionSettings;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserState;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.SuspendParams;
import com.android.server.pm.pkg.component.ParsedComponent;
import com.android.server.pm.pkg.component.ParsedIntentInfo;
import com.android.server.pm.pkg.component.ParsedPermission;
import com.android.server.pm.pkg.component.ParsedProcess;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.utils.Slogf;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.Watched;
import com.android.server.utils.WatchedArrayList;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseArray;
import com.android.server.utils.WatchedSparseIntArray;
import com.android.server.utils.Watcher;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.kiosk.IKioskMode;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.install.MultiUserInstallPolicy;
import com.samsung.android.server.p025pm.rescueparty.PackageManagerBackupController;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public final class Settings implements Watchable, Snappable, ResilientAtomicFile.ReadEventLogger {
    public static final Object[] FLAG_DUMP_SPEC;
    public static int PRE_M_APP_INFO_FLAG_CANT_SAVE_STATE = 268435456;
    public static int PRE_M_APP_INFO_FLAG_HIDDEN = 134217728;
    public static int PRE_M_APP_INFO_FLAG_PRIVILEGED = 1073741824;
    public static final Object[] PRIVATE_FLAG_DUMP_SPEC;
    public final AppIdSettingMap mAppIds;
    public final File mBackupStoppedPackagesFilename;
    public final WatchedSparseArray mBlockUninstallPackages;
    public final WatchedSparseArray mCrossProfileIntentResolvers;
    public final SnapshotCache mCrossProfileIntentResolversSnapshot;
    public final WatchedSparseArray mDefaultBrowserApp;

    @Watched
    final WatchedArrayMap mDisabledSysPackages;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final Handler mHandler;
    public final WatchedArraySet mInstallerPackages;
    public final SnapshotCache mInstallerPackagesSnapshot;
    public final WatchedArrayMap mKernelMapping;
    public final File mKernelMappingFilename;
    public final SnapshotCache mKernelMappingSnapshot;
    public final KeySetManagerService mKeySetManagerService;
    public final WatchedArrayMap mKeySetRefs;
    public final SnapshotCache mKeySetRefsSnapshot;
    public final PackageManagerTracedLock mLock;
    public final WatchedSparseIntArray mNextAppLinkGeneration;
    public final Watcher mObserver;
    public final File mPackageListFilename;
    public final Object mPackageRestrictionsLock;

    @Watched
    final WatchedArrayMap mPackages;
    public final SnapshotCache mPackagesSnapshot;
    public final WatchedArrayList mPastSignatures;
    public final SnapshotCache mPastSignaturesSnapshot;
    public final SparseIntArray mPendingAsyncPackageRestrictionsWrites;
    public final WatchedArrayList mPendingPackages;
    public final SnapshotCache mPendingPackagesSnapshot;
    public final LegacyPermissionDataProvider mPermissionDataProvider;
    public final LegacyPermissionSettings mPermissions;
    public final WatchedSparseArray mPersistentPreferredActivities;
    public final SnapshotCache mPersistentPreferredActivitiesSnapshot;
    public final WatchedSparseArray mPreferredActivities;
    public final SnapshotCache mPreferredActivitiesSnapshot;
    public final File mPreviousSettingsFilename;
    public final StringBuilder mReadMessages;
    public final WatchedArrayMap mRenamedPackages;
    public final RuntimePermissionPersistence mRuntimePermissionsPersistence;
    public final File mSettingsFilename;
    public final File mSettingsReserveCopyFilename;
    public final WatchedArrayMap mSharedUsers;
    public final SnapshotCache mSnapshot;
    public final File mStoppedPackagesFilename;
    public final File mSystemDir;
    public VerifierDeviceIdentity mVerifierDeviceIdentity;
    public final WatchedArrayMap mVersion;
    public final WatchableImpl mWatchable;

    public final class KernelPackageState {
        public int appId;
        public int[] excludedUserIds;

        public KernelPackageState() {
        }
    }

    @Override // com.android.server.utils.Watchable
    public void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public void onChanged() {
        dispatchChange(this);
    }

    public class VersionInfo {
        public String buildFingerprint;
        public int databaseVersion;
        public String fingerprint;
        public int sdkVersion;

        public void forceCurrent() {
            this.sdkVersion = Build.VERSION.SDK_INT;
            this.databaseVersion = 3;
            this.buildFingerprint = Build.FINGERPRINT;
            this.fingerprint = PackagePartitions.FINGERPRINT;
        }
    }

    public final SnapshotCache makeCache() {
        return new SnapshotCache(this, this) { // from class: com.android.server.pm.Settings.2
            @Override // com.android.server.utils.SnapshotCache
            public Settings createSnapshot() {
                Settings settings = new Settings();
                settings.mWatchable.seal();
                return settings;
            }
        };
    }

    public final void registerObservers() {
        this.mPackages.registerObserver(this.mObserver);
        this.mInstallerPackages.registerObserver(this.mObserver);
        this.mKernelMapping.registerObserver(this.mObserver);
        this.mDisabledSysPackages.registerObserver(this.mObserver);
        this.mBlockUninstallPackages.registerObserver(this.mObserver);
        this.mVersion.registerObserver(this.mObserver);
        this.mPreferredActivities.registerObserver(this.mObserver);
        this.mPersistentPreferredActivities.registerObserver(this.mObserver);
        this.mCrossProfileIntentResolvers.registerObserver(this.mObserver);
        this.mSharedUsers.registerObserver(this.mObserver);
        this.mAppIds.registerObserver(this.mObserver);
        this.mRenamedPackages.registerObserver(this.mObserver);
        this.mNextAppLinkGeneration.registerObserver(this.mObserver);
        this.mDefaultBrowserApp.registerObserver(this.mObserver);
        this.mPendingPackages.registerObserver(this.mObserver);
        this.mPastSignatures.registerObserver(this.mObserver);
        this.mKeySetRefs.registerObserver(this.mObserver);
    }

    public Settings(Map map) {
        this.mWatchable = new WatchableImpl();
        this.mPackageRestrictionsLock = new Object();
        this.mPendingAsyncPackageRestrictionsWrites = new SparseIntArray();
        this.mDisabledSysPackages = new WatchedArrayMap();
        this.mBlockUninstallPackages = new WatchedSparseArray();
        this.mVersion = new WatchedArrayMap();
        this.mSharedUsers = new WatchedArrayMap();
        this.mRenamedPackages = new WatchedArrayMap();
        this.mDefaultBrowserApp = new WatchedSparseArray();
        this.mNextAppLinkGeneration = new WatchedSparseIntArray();
        this.mReadMessages = new StringBuilder();
        Watcher watcher = new Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                Settings.this.dispatchChange(watchable);
            }
        };
        this.mObserver = watcher;
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "Settings.mPackages");
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
        this.mKernelMapping = watchedArrayMap2;
        this.mKernelMappingSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "Settings.mKernelMapping");
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        this.mInstallerPackages = watchedArraySet;
        this.mInstallerPackagesSnapshot = new SnapshotCache.Auto(watchedArraySet, watchedArraySet, "Settings.mInstallerPackages");
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mPreferredActivities = watchedSparseArray;
        this.mPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray, watchedSparseArray, "Settings.mPreferredActivities");
        WatchedSparseArray watchedSparseArray2 = new WatchedSparseArray();
        this.mPersistentPreferredActivities = watchedSparseArray2;
        this.mPersistentPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray2, watchedSparseArray2, "Settings.mPersistentPreferredActivities");
        WatchedSparseArray watchedSparseArray3 = new WatchedSparseArray();
        this.mCrossProfileIntentResolvers = watchedSparseArray3;
        this.mCrossProfileIntentResolversSnapshot = new SnapshotCache.Auto(watchedSparseArray3, watchedSparseArray3, "Settings.mCrossProfileIntentResolvers");
        WatchedArrayList watchedArrayList = new WatchedArrayList();
        this.mPastSignatures = watchedArrayList;
        this.mPastSignaturesSnapshot = new SnapshotCache.Auto(watchedArrayList, watchedArrayList, "Settings.mPastSignatures");
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap();
        this.mKeySetRefs = watchedArrayMap3;
        this.mKeySetRefsSnapshot = new SnapshotCache.Auto(watchedArrayMap3, watchedArrayMap3, "Settings.mKeySetRefs");
        WatchedArrayList watchedArrayList2 = new WatchedArrayList();
        this.mPendingPackages = watchedArrayList2;
        this.mPendingPackagesSnapshot = new SnapshotCache.Auto(watchedArrayList2, watchedArrayList2, "Settings.mPendingPackages");
        this.mKeySetManagerService = new KeySetManagerService(watchedArrayMap);
        this.mHandler = new Handler(BackgroundThread.getHandler().getLooper());
        this.mLock = new PackageManagerTracedLock();
        watchedArrayMap.putAll(map);
        this.mAppIds = new AppIdSettingMap();
        this.mSystemDir = null;
        this.mPermissions = null;
        this.mRuntimePermissionsPersistence = null;
        this.mPermissionDataProvider = null;
        this.mSettingsFilename = null;
        this.mSettingsReserveCopyFilename = null;
        this.mPreviousSettingsFilename = null;
        this.mPackageListFilename = null;
        this.mStoppedPackagesFilename = null;
        this.mBackupStoppedPackagesFilename = null;
        this.mKernelMappingFilename = null;
        this.mDomainVerificationManager = null;
        registerObservers();
        Watchable.verifyWatchedAttributes(this, watcher);
        this.mSnapshot = makeCache();
    }

    public Settings(File file, RuntimePermissionsPersistence runtimePermissionsPersistence, LegacyPermissionDataProvider legacyPermissionDataProvider, DomainVerificationManagerInternal domainVerificationManagerInternal, Handler handler, PackageManagerTracedLock packageManagerTracedLock) {
        this.mWatchable = new WatchableImpl();
        this.mPackageRestrictionsLock = new Object();
        this.mPendingAsyncPackageRestrictionsWrites = new SparseIntArray();
        this.mDisabledSysPackages = new WatchedArrayMap();
        this.mBlockUninstallPackages = new WatchedSparseArray();
        this.mVersion = new WatchedArrayMap();
        this.mSharedUsers = new WatchedArrayMap();
        this.mRenamedPackages = new WatchedArrayMap();
        this.mDefaultBrowserApp = new WatchedSparseArray();
        this.mNextAppLinkGeneration = new WatchedSparseIntArray();
        this.mReadMessages = new StringBuilder();
        Watcher watcher = new Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                Settings.this.dispatchChange(watchable);
            }
        };
        this.mObserver = watcher;
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "Settings.mPackages");
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
        this.mKernelMapping = watchedArrayMap2;
        this.mKernelMappingSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "Settings.mKernelMapping");
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        this.mInstallerPackages = watchedArraySet;
        this.mInstallerPackagesSnapshot = new SnapshotCache.Auto(watchedArraySet, watchedArraySet, "Settings.mInstallerPackages");
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mPreferredActivities = watchedSparseArray;
        this.mPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray, watchedSparseArray, "Settings.mPreferredActivities");
        WatchedSparseArray watchedSparseArray2 = new WatchedSparseArray();
        this.mPersistentPreferredActivities = watchedSparseArray2;
        this.mPersistentPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray2, watchedSparseArray2, "Settings.mPersistentPreferredActivities");
        WatchedSparseArray watchedSparseArray3 = new WatchedSparseArray();
        this.mCrossProfileIntentResolvers = watchedSparseArray3;
        this.mCrossProfileIntentResolversSnapshot = new SnapshotCache.Auto(watchedSparseArray3, watchedSparseArray3, "Settings.mCrossProfileIntentResolvers");
        WatchedArrayList watchedArrayList = new WatchedArrayList();
        this.mPastSignatures = watchedArrayList;
        this.mPastSignaturesSnapshot = new SnapshotCache.Auto(watchedArrayList, watchedArrayList, "Settings.mPastSignatures");
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap();
        this.mKeySetRefs = watchedArrayMap3;
        this.mKeySetRefsSnapshot = new SnapshotCache.Auto(watchedArrayMap3, watchedArrayMap3, "Settings.mKeySetRefs");
        WatchedArrayList watchedArrayList2 = new WatchedArrayList();
        this.mPendingPackages = watchedArrayList2;
        this.mPendingPackagesSnapshot = new SnapshotCache.Auto(watchedArrayList2, watchedArrayList2, "Settings.mPendingPackages");
        this.mKeySetManagerService = new KeySetManagerService(watchedArrayMap);
        this.mHandler = handler;
        this.mLock = packageManagerTracedLock;
        this.mAppIds = new AppIdSettingMap();
        this.mPermissions = new LegacyPermissionSettings(packageManagerTracedLock);
        this.mRuntimePermissionsPersistence = new RuntimePermissionPersistence(runtimePermissionsPersistence, new Consumer() { // from class: com.android.server.pm.Settings.3
            @Override // java.util.function.Consumer
            public void accept(Integer num) {
                RuntimePermissionPersistence runtimePermissionPersistence = Settings.this.mRuntimePermissionsPersistence;
                int intValue = num.intValue();
                LegacyPermissionDataProvider legacyPermissionDataProvider2 = Settings.this.mPermissionDataProvider;
                Settings settings = Settings.this;
                runtimePermissionPersistence.writeStateForUser(intValue, legacyPermissionDataProvider2, settings.mPackages, settings.mSharedUsers, settings.mHandler, Settings.this.mLock, false);
            }
        });
        this.mPermissionDataProvider = legacyPermissionDataProvider;
        File file2 = new File(file, "system");
        this.mSystemDir = file2;
        file2.mkdirs();
        FileUtils.setPermissions(file2.toString(), 509, -1, -1);
        this.mSettingsFilename = new File(file2, "packages.xml");
        this.mSettingsReserveCopyFilename = new File(file2, "packages.xml.reservecopy");
        this.mPreviousSettingsFilename = new File(file2, "packages-backup.xml");
        File file3 = new File(file2, "packages.list");
        this.mPackageListFilename = file3;
        FileUtils.setPermissions(file3, FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
        File file4 = new File("/config/sdcardfs");
        this.mKernelMappingFilename = file4.exists() ? file4 : null;
        this.mStoppedPackagesFilename = new File(file2, "packages-stopped.xml");
        this.mBackupStoppedPackagesFilename = new File(file2, "packages-stopped-backup.xml");
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        registerObservers();
        Watchable.verifyWatchedAttributes(this, watcher);
        this.mSnapshot = makeCache();
    }

    public Settings(Settings settings) {
        this.mWatchable = new WatchableImpl();
        this.mPackageRestrictionsLock = new Object();
        this.mPendingAsyncPackageRestrictionsWrites = new SparseIntArray();
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
        this.mDisabledSysPackages = watchedArrayMap;
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mBlockUninstallPackages = watchedSparseArray;
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap();
        this.mVersion = watchedArrayMap2;
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap();
        this.mSharedUsers = watchedArrayMap3;
        WatchedArrayMap watchedArrayMap4 = new WatchedArrayMap();
        this.mRenamedPackages = watchedArrayMap4;
        WatchedSparseArray watchedSparseArray2 = new WatchedSparseArray();
        this.mDefaultBrowserApp = watchedSparseArray2;
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray();
        this.mNextAppLinkGeneration = watchedSparseIntArray;
        this.mReadMessages = new StringBuilder();
        this.mObserver = new Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                Settings.this.dispatchChange(watchable);
            }
        };
        WatchedArrayMap watchedArrayMap5 = (WatchedArrayMap) settings.mPackagesSnapshot.snapshot();
        this.mPackages = watchedArrayMap5;
        this.mPackagesSnapshot = new SnapshotCache.Sealed();
        this.mKernelMapping = (WatchedArrayMap) settings.mKernelMappingSnapshot.snapshot();
        this.mKernelMappingSnapshot = new SnapshotCache.Sealed();
        this.mInstallerPackages = (WatchedArraySet) settings.mInstallerPackagesSnapshot.snapshot();
        this.mInstallerPackagesSnapshot = new SnapshotCache.Sealed();
        this.mKeySetManagerService = new KeySetManagerService(settings.mKeySetManagerService, watchedArrayMap5);
        this.mHandler = null;
        this.mLock = null;
        this.mRuntimePermissionsPersistence = settings.mRuntimePermissionsPersistence;
        this.mSettingsFilename = null;
        this.mSettingsReserveCopyFilename = null;
        this.mPreviousSettingsFilename = null;
        this.mPackageListFilename = null;
        this.mStoppedPackagesFilename = null;
        this.mBackupStoppedPackagesFilename = null;
        this.mKernelMappingFilename = null;
        this.mDomainVerificationManager = settings.mDomainVerificationManager;
        watchedArrayMap.snapshot(settings.mDisabledSysPackages);
        watchedSparseArray.snapshot(settings.mBlockUninstallPackages);
        watchedArrayMap2.putAll(settings.mVersion);
        this.mVerifierDeviceIdentity = settings.mVerifierDeviceIdentity;
        this.mPreferredActivities = (WatchedSparseArray) settings.mPreferredActivitiesSnapshot.snapshot();
        this.mPreferredActivitiesSnapshot = new SnapshotCache.Sealed();
        this.mPersistentPreferredActivities = (WatchedSparseArray) settings.mPersistentPreferredActivitiesSnapshot.snapshot();
        this.mPersistentPreferredActivitiesSnapshot = new SnapshotCache.Sealed();
        this.mCrossProfileIntentResolvers = (WatchedSparseArray) settings.mCrossProfileIntentResolversSnapshot.snapshot();
        this.mCrossProfileIntentResolversSnapshot = new SnapshotCache.Sealed();
        watchedArrayMap3.snapshot(settings.mSharedUsers);
        this.mAppIds = settings.mAppIds.snapshot();
        this.mPastSignatures = (WatchedArrayList) settings.mPastSignaturesSnapshot.snapshot();
        this.mPastSignaturesSnapshot = new SnapshotCache.Sealed();
        this.mKeySetRefs = (WatchedArrayMap) settings.mKeySetRefsSnapshot.snapshot();
        this.mKeySetRefsSnapshot = new SnapshotCache.Sealed();
        watchedArrayMap4.snapshot(settings.mRenamedPackages);
        watchedSparseIntArray.snapshot(settings.mNextAppLinkGeneration);
        watchedSparseArray2.snapshot(settings.mDefaultBrowserApp);
        this.mPendingPackages = (WatchedArrayList) settings.mPendingPackagesSnapshot.snapshot();
        this.mPendingPackagesSnapshot = new SnapshotCache.Sealed();
        this.mSystemDir = null;
        this.mPermissions = settings.mPermissions;
        this.mPermissionDataProvider = settings.mPermissionDataProvider;
        this.mSnapshot = new SnapshotCache.Sealed();
    }

    @Override // com.android.server.utils.Snappable
    public Settings snapshot() {
        return (Settings) this.mSnapshot.snapshot();
    }

    public final void invalidatePackageCache() {
        PackageManagerService.invalidatePackageInfoCache();
        ChangeIdStateCache.invalidate();
        onChanged();
    }

    public PackageSetting getPackageLPr(String str) {
        return (PackageSetting) this.mPackages.get(str);
    }

    public WatchedArrayMap getPackagesLocked() {
        return this.mPackages;
    }

    public WatchedArrayMap getDisabledSystemPackagesLocked() {
        return this.mDisabledSysPackages;
    }

    public KeySetManagerService getKeySetManagerService() {
        return this.mKeySetManagerService;
    }

    public String getRenamedPackageLPr(String str) {
        return (String) this.mRenamedPackages.get(str);
    }

    public String addRenamedPackageLPw(String str, String str2) {
        return (String) this.mRenamedPackages.put(str, str2);
    }

    public void removeRenamedPackageLPw(String str) {
        this.mRenamedPackages.remove(str);
    }

    public void pruneRenamedPackagesLPw() {
        for (int size = this.mRenamedPackages.size() - 1; size >= 0; size--) {
            if (((PackageSetting) this.mPackages.get(this.mRenamedPackages.valueAt(size))) == null) {
                this.mRenamedPackages.removeAt(size);
            }
        }
    }

    public SharedUserSetting getSharedUserLPw(String str, int i, int i2, boolean z) {
        SharedUserSetting sharedUserSetting = (SharedUserSetting) this.mSharedUsers.get(str);
        if (sharedUserSetting == null && z) {
            sharedUserSetting = new SharedUserSetting(str, i, i2);
            int acquireAndRegisterNewAppId = this.mAppIds.acquireAndRegisterNewAppId(sharedUserSetting);
            sharedUserSetting.mAppId = acquireAndRegisterNewAppId;
            if (acquireAndRegisterNewAppId < 0) {
                throw new PackageManagerException(-4, "Creating shared user " + str + " failed");
            }
            Log.i("PackageManager", "New shared user " + str + ": id=" + sharedUserSetting.mAppId);
            this.mSharedUsers.put(str, sharedUserSetting);
        }
        return sharedUserSetting;
    }

    public Collection getAllSharedUsersLPw() {
        return this.mSharedUsers.values();
    }

    public boolean disableSystemPackageLPw(String str, boolean z) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
        boolean z2 = false;
        if (packageSetting == null) {
            Log.w("PackageManager", "Package " + str + " is not an installed package");
            return false;
        }
        if (((PackageSetting) this.mDisabledSysPackages.get(str)) == null && packageSetting.getPkg() != null && packageSetting.isSystem() && !packageSetting.isUpdatedSystemApp()) {
            PackageSetting packageSetting2 = z ? new PackageSetting(packageSetting) : packageSetting;
            z2 = true;
            packageSetting.getPkgState().setUpdatedSystemApp(true);
            this.mDisabledSysPackages.put(str, packageSetting2);
            SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting2);
            if (sharedUserSettingLPr != null) {
                sharedUserSettingLPr.mDisabledPackages.add(packageSetting2);
            }
        }
        return z2;
    }

    public PackageSetting enableSystemPackageLPw(String str) {
        PackageSetting packageSetting = (PackageSetting) this.mDisabledSysPackages.get(str);
        if (packageSetting == null) {
            Log.w("PackageManager", "Package " + str + " is not disabled");
            return null;
        }
        SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.mDisabledPackages.remove(packageSetting);
        }
        packageSetting.getPkgState().setUpdatedSystemApp(false);
        PackageSetting addPackageLPw = addPackageLPw(str, packageSetting.getRealName(), packageSetting.getPath(), packageSetting.getLegacyNativeLibraryPath(), packageSetting.getPrimaryCpuAbiLegacy(), packageSetting.getSecondaryCpuAbiLegacy(), packageSetting.getCpuAbiOverride(), packageSetting.getAppId(), packageSetting.getVersionCode(), packageSetting.getFlags(), packageSetting.getPrivateFlags(), packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor(), packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions(), packageSetting.getMimeGroups(), this.mDomainVerificationManager.generateNewId());
        if (addPackageLPw != null) {
            addPackageLPw.setAppMetadataFilePath(packageSetting.getAppMetadataFilePath());
            addPackageLPw.getPkgState().setUpdatedSystemApp(false);
        }
        this.mDisabledSysPackages.remove(str);
        return addPackageLPw;
    }

    public boolean isDisabledSystemPackageLPr(String str) {
        return this.mDisabledSysPackages.containsKey(str);
    }

    public void removeDisabledSystemPackageLPw(String str) {
        SharedUserSetting sharedUserSettingLPr;
        PackageSetting packageSetting = (PackageSetting) this.mDisabledSysPackages.remove(str);
        if (packageSetting == null || (sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting)) == null) {
            return;
        }
        sharedUserSettingLPr.mDisabledPackages.remove(packageSetting);
        checkAndPruneSharedUserLPw(sharedUserSettingLPr, false);
    }

    public PackageSetting addPackageLPw(String str, String str2, File file, String str3, String str4, String str5, String str6, int i, long j, int i2, int i3, String[] strArr, long[] jArr, String[] strArr2, long[] jArr2, Map map, UUID uuid) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
        if (packageSetting != null) {
            if (packageSetting.getAppId() == i) {
                return packageSetting;
            }
            PackageManagerService.reportSettingsProblem(6, "Adding duplicate package, keeping first: " + str);
            return null;
        }
        PackageSetting packageSetting2 = new PackageSetting(str, str2, file, str3, str4, str5, str6, j, i2, i3, 0, strArr, jArr, strArr2, jArr2, map, uuid);
        packageSetting2.setAppId(i);
        if (!this.mAppIds.registerExistingAppId(i, packageSetting2, str)) {
            return null;
        }
        this.mPackages.put(str, packageSetting2);
        return packageSetting2;
    }

    public SharedUserSetting addSharedUserLPw(String str, int i, int i2, int i3) {
        SharedUserSetting sharedUserSetting = (SharedUserSetting) this.mSharedUsers.get(str);
        if (sharedUserSetting != null) {
            if (sharedUserSetting.mAppId == i) {
                return sharedUserSetting;
            }
            PackageManagerService.reportSettingsProblem(6, "Adding duplicate shared user, keeping first: " + str);
            return null;
        }
        SharedUserSetting sharedUserSetting2 = new SharedUserSetting(str, i2, i3);
        sharedUserSetting2.mAppId = i;
        if (!this.mAppIds.registerExistingAppId(i, sharedUserSetting2, str)) {
            return null;
        }
        this.mSharedUsers.put(str, sharedUserSetting2);
        return sharedUserSetting2;
    }

    public void pruneSharedUsersLPw() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : this.mSharedUsers.entrySet()) {
            SharedUserSetting sharedUserSetting = (SharedUserSetting) entry.getValue();
            if (sharedUserSetting == null) {
                arrayList.add((String) entry.getKey());
            } else {
                WatchedArraySet packageSettings = sharedUserSetting.getPackageSettings();
                boolean z = false;
                for (int size = packageSettings.size() - 1; size >= 0; size--) {
                    if (this.mPackages.get(((PackageSetting) packageSettings.valueAt(size)).getPackageName()) == null) {
                        packageSettings.removeAt(size);
                        z = true;
                    }
                }
                WatchedArraySet disabledPackageSettings = sharedUserSetting.getDisabledPackageSettings();
                for (int size2 = disabledPackageSettings.size() - 1; size2 >= 0; size2--) {
                    if (this.mDisabledSysPackages.get(((PackageSetting) disabledPackageSettings.valueAt(size2)).getPackageName()) == null) {
                        disabledPackageSettings.removeAt(size2);
                        z = true;
                    }
                }
                if (z) {
                    sharedUserSetting.onChanged();
                }
                if (packageSettings.isEmpty() && disabledPackageSettings.isEmpty()) {
                    arrayList2.add(sharedUserSetting);
                }
            }
        }
        final WatchedArrayMap watchedArrayMap = this.mSharedUsers;
        Objects.requireNonNull(watchedArrayMap);
        arrayList.forEach(new Consumer() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WatchedArrayMap.this.remove((String) obj);
            }
        });
        arrayList2.forEach(new Consumer() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Settings.this.lambda$pruneSharedUsersLPw$0((SharedUserSetting) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneSharedUsersLPw$0(SharedUserSetting sharedUserSetting) {
        checkAndPruneSharedUserLPw(sharedUserSetting, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00fe, code lost:
    
        if (r5.preCreated == false) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PackageSetting createNewSetting(String str, PackageSetting packageSetting, PackageSetting packageSetting2, String str2, SharedUserSetting sharedUserSetting, File file, String str3, String str4, String str5, long j, int i, int i2, UserHandle userHandle, boolean z, boolean z2, boolean z3, boolean z4, UserManagerService userManagerService, String[] strArr, long[] jArr, String[] strArr2, long[] jArr2, Set set, UUID uuid) {
        int i3;
        boolean z5;
        if (packageSetting != null) {
            PackageSetting domainSetId = new PackageSetting(packageSetting, str).setPath(file).setLegacyNativeLibraryPath(str3).setPrimaryCpuAbi(str4).setSecondaryCpuAbi(str5).setSignatures(new PackageSignatures()).setLongVersionCode(j).setUsesSdkLibraries(strArr).setUsesSdkLibrariesVersionsMajor(jArr).setUsesStaticLibraries(strArr2).setUsesStaticLibrariesVersions(jArr2).setLastModifiedTime(file.lastModified()).setDomainSetId(uuid);
            domainSetId.setFlags(i).setPrivateFlags(i2);
            return domainSetId;
        }
        int identifier = userHandle != null ? userHandle.getIdentifier() : 0;
        PackageSetting packageSetting3 = new PackageSetting(str, str2, file, str3, str4, str5, null, j, i, i2, 0, strArr, jArr, strArr2, jArr2, createMimeGroups(set), uuid);
        packageSetting3.setLastModifiedTime(file.lastModified());
        if (sharedUserSetting != null) {
            packageSetting3.setSharedUserAppId(sharedUserSetting.mAppId);
        }
        if ((i & 1) == 0) {
            List<UserInfo> allUsers = getAllUsers(userManagerService);
            if (allUsers != null && z) {
                for (UserInfo userInfo : allUsers) {
                    if (userHandle != null) {
                        i3 = identifier;
                        if (i3 == -1) {
                            if (!isAdbInstallDisallowed(userManagerService, userInfo.id)) {
                            }
                        }
                        if (i3 != userInfo.id) {
                            z5 = false;
                            packageSetting3.setUserState(userInfo.id, 0L, 0, z5, true, true, false, 0, null, z2, z3, null, null, null, 0, 0, null, null, 0L);
                            identifier = i3;
                        }
                    } else {
                        i3 = identifier;
                    }
                    z5 = true;
                    packageSetting3.setUserState(userInfo.id, 0L, 0, z5, true, true, false, 0, null, z2, z3, null, null, null, 0, 0, null, null, 0L);
                    identifier = i3;
                }
            }
        } else {
            int i4 = identifier;
            if (z4) {
                packageSetting3.setStopped(true, i4);
            }
        }
        if (sharedUserSetting != null) {
            packageSetting3.setAppId(sharedUserSetting.mAppId);
            return packageSetting3;
        }
        if (packageSetting2 == null) {
            return packageSetting3;
        }
        packageSetting3.setSignatures(new PackageSignatures(packageSetting2.getSignatures()));
        packageSetting3.setAppId(packageSetting2.getAppId());
        packageSetting3.getLegacyPermissionState().copyFrom(packageSetting2.getLegacyPermissionState());
        List allUsers2 = getAllUsers(userManagerService);
        if (allUsers2 == null) {
            return packageSetting3;
        }
        Iterator it = allUsers2.iterator();
        while (it.hasNext()) {
            int i5 = ((UserInfo) it.next()).id;
            packageSetting3.setDisabledComponentsCopy(packageSetting2.getDisabledComponents(i5), i5);
            packageSetting3.setEnabledComponentsCopy(packageSetting2.getEnabledComponents(i5), i5);
        }
        return packageSetting3;
    }

    public static Map createMimeGroups(Set set) {
        if (set == null) {
            return null;
        }
        return new KeySetToValueMap(set, new ArraySet());
    }

    public static void updatePackageSetting(PackageSetting packageSetting, PackageSetting packageSetting2, SharedUserSetting sharedUserSetting, SharedUserSetting sharedUserSetting2, File file, String str, String str2, String str3, int i, int i2, UserManagerService userManagerService, String[] strArr, long[] jArr, String[] strArr2, long[] jArr2, Set set, UUID uuid) {
        List<UserInfo> allUsers;
        String packageName = packageSetting.getPackageName();
        if (sharedUserSetting2 != null) {
            if (!Objects.equals(sharedUserSetting, sharedUserSetting2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Package ");
                sb.append(packageName);
                sb.append(" shared user changed from ");
                sb.append(sharedUserSetting != null ? sharedUserSetting.name : "<nothing>");
                sb.append(" to ");
                sb.append(sharedUserSetting2.name);
                PackageManagerService.reportSettingsProblem(5, sb.toString());
                throw new PackageManagerException(-24, "Updating application package " + packageName + " failed");
            }
            packageSetting.setSharedUserAppId(sharedUserSetting2.mAppId);
        } else {
            packageSetting.setSharedUserAppId(-1);
        }
        if (!packageSetting.getPath().equals(file)) {
            boolean isSystem = packageSetting.isSystem();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Update");
            sb2.append(isSystem ? " system" : "");
            sb2.append(" package ");
            sb2.append(packageName);
            sb2.append(" code path from ");
            sb2.append(packageSetting.getPathString());
            sb2.append(" to ");
            sb2.append(file.toString());
            sb2.append("; Retain data and using new");
            Slog.i("PackageManager", sb2.toString());
            if (!isSystem) {
                if ((i & 1) != 0 && packageSetting2 == null && (allUsers = getAllUsers(userManagerService)) != null) {
                    for (UserInfo userInfo : allUsers) {
                        if (userInfo.isManagedProfile() || userInfo.isDualAppProfile()) {
                            Slog.i("PackageManager", "User " + userInfo.id + " is Premium container. do not set install flag. / " + packageName + " / installed = " + packageSetting.getInstalled(userInfo.id));
                        } else {
                            packageSetting.setInstalled(true, userInfo.id);
                            packageSetting.setUninstallReason(0, userInfo.id);
                        }
                    }
                }
                packageSetting.setLegacyNativeLibraryPath(str);
            }
            packageSetting.setPath(file);
        }
        packageSetting.setPrimaryCpuAbi(str2).setSecondaryCpuAbi(str3).updateMimeGroups(set).setDomainSetId(uuid);
        if (strArr != null && jArr != null && strArr.length == jArr.length) {
            packageSetting.setUsesSdkLibraries(strArr).setUsesSdkLibrariesVersionsMajor(jArr);
        } else {
            packageSetting.setUsesSdkLibraries(null).setUsesSdkLibrariesVersionsMajor(null);
        }
        if (strArr2 != null && jArr2 != null && strArr2.length == jArr2.length) {
            packageSetting.setUsesStaticLibraries(strArr2).setUsesStaticLibrariesVersions(jArr2);
        } else {
            packageSetting.setUsesStaticLibraries(null).setUsesStaticLibrariesVersions(null);
        }
        packageSetting.setFlags((packageSetting.getFlags() & (-2)) | (i & 1));
        packageSetting.setPrivateFlags((packageSetting.getPrivateFlags() & 512) != 0 ? i2 | 512 : i2 & (-513));
    }

    public boolean registerAppIdLPw(PackageSetting packageSetting, boolean z) {
        boolean z2;
        if (packageSetting.getAppId() == 0 || z) {
            packageSetting.setAppId(this.mAppIds.acquireAndRegisterNewAppId(packageSetting));
            z2 = true;
        } else {
            z2 = this.mAppIds.registerExistingAppId(packageSetting.getAppId(), packageSetting, packageSetting.getPackageName());
        }
        if (packageSetting.getAppId() >= 0) {
            return z2;
        }
        PackageManagerService.reportSettingsProblem(5, "Package " + packageSetting.getPackageName() + " could not be assigned a valid UID");
        throw new PackageManagerException(-4, "Package " + packageSetting.getPackageName() + " could not be assigned a valid UID");
    }

    public void writeUserRestrictionsLPw(PackageSetting packageSetting, PackageSetting packageSetting2) {
        List<UserInfo> allUsers;
        Object readUserState;
        if (getPackageLPr(packageSetting.getPackageName()) == null || (allUsers = getAllUsers(UserManagerService.getInstance())) == null) {
            return;
        }
        for (UserInfo userInfo : allUsers) {
            if (packageSetting2 == null) {
                readUserState = PackageUserState.DEFAULT;
            } else {
                readUserState = packageSetting2.readUserState(userInfo.id);
            }
            if (!readUserState.equals(packageSetting.readUserState(userInfo.id))) {
                writePackageRestrictionsLPr(userInfo.id);
            }
        }
    }

    public static boolean isAdbInstallDisallowed(UserManagerService userManagerService, int i) {
        return userManagerService.hasUserRestriction("no_debugging_features", i);
    }

    public void insertPackageSettingLPw(PackageSetting packageSetting, AndroidPackage androidPackage) {
        if (packageSetting.getSigningDetails().getSignatures() == null) {
            packageSetting.setSigningDetails(androidPackage.getSigningDetails());
        }
        SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null && sharedUserSettingLPr.signatures.mSigningDetails.getSignatures() == null) {
            sharedUserSettingLPr.signatures.mSigningDetails = androidPackage.getSigningDetails();
        }
        addPackageSettingLPw(packageSetting, sharedUserSettingLPr);
    }

    public void addPackageSettingLPw(PackageSetting packageSetting, SharedUserSetting sharedUserSetting) {
        this.mPackages.put(packageSetting.getPackageName(), packageSetting);
        if (sharedUserSetting != null) {
            SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
            if (sharedUserSettingLPr != null && sharedUserSettingLPr != sharedUserSetting) {
                PackageManagerService.reportSettingsProblem(6, "Package " + packageSetting.getPackageName() + " was user " + sharedUserSettingLPr + " but is now " + sharedUserSetting + "; I am not changing its files so it will probably fail!");
                sharedUserSettingLPr.removePackage(packageSetting);
            } else if (packageSetting.getAppId() != 0 && packageSetting.getAppId() != sharedUserSetting.mAppId) {
                PackageManagerService.reportSettingsProblem(6, "Package " + packageSetting.getPackageName() + " was app id " + packageSetting.getAppId() + " but is now user " + sharedUserSetting + " with app id " + sharedUserSetting.mAppId + "; I am not changing its files so it will probably fail!");
            }
            sharedUserSetting.addPackage(packageSetting);
            packageSetting.setSharedUserAppId(sharedUserSetting.mAppId);
            packageSetting.setAppId(sharedUserSetting.mAppId);
        }
        SettingBase settingLPr = getSettingLPr(packageSetting.getAppId());
        if (sharedUserSetting == null) {
            if (settingLPr == null || settingLPr == packageSetting) {
                return;
            }
            this.mAppIds.replaceSetting(packageSetting.getAppId(), packageSetting);
            return;
        }
        if (settingLPr == null || settingLPr == sharedUserSetting) {
            return;
        }
        this.mAppIds.replaceSetting(packageSetting.getAppId(), sharedUserSetting);
    }

    public boolean checkAndPruneSharedUserLPw(SharedUserSetting sharedUserSetting, boolean z) {
        if ((!z && (!sharedUserSetting.getPackageStates().isEmpty() || !sharedUserSetting.getDisabledPackageStates().isEmpty())) || this.mSharedUsers.remove(sharedUserSetting.name) == null) {
            return false;
        }
        removeAppIdLPw(sharedUserSetting.mAppId);
        return true;
    }

    public int removePackageLPw(String str) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.remove(str);
        if (packageSetting == null) {
            return -1;
        }
        removeInstallerPackageStatus(str);
        SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.removePackage(packageSetting);
            if (checkAndPruneSharedUserLPw(sharedUserSettingLPr, false)) {
                return sharedUserSettingLPr.mAppId;
            }
            return -1;
        }
        removeAppIdLPw(packageSetting.getAppId());
        return packageSetting.getAppId();
    }

    public final void removeInstallerPackageStatus(String str) {
        if (this.mInstallerPackages.contains(str)) {
            if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED && "com.sec.android.app.samsungapps".equals(str) && this.mDisabledSysPackages.get(str) != null) {
                return;
            }
            for (int i = 0; i < this.mPackages.size(); i++) {
                ((PackageSetting) this.mPackages.valueAt(i)).removeInstallerPackage(str);
            }
            this.mInstallerPackages.remove(str);
        }
    }

    public SettingBase getSettingLPr(int i) {
        return this.mAppIds.getSetting(i);
    }

    public void removeAppIdLPw(int i) {
        this.mAppIds.removeSetting(i);
    }

    public void convertSharedUserSettingsLPw(SharedUserSetting sharedUserSetting) {
        PackageSetting packageSetting = (PackageSetting) sharedUserSetting.getPackageSettings().valueAt(0);
        this.mAppIds.replaceSetting(sharedUserSetting.getAppId(), packageSetting);
        packageSetting.setSharedUserAppId(-1);
        if (!sharedUserSetting.getDisabledPackageSettings().isEmpty()) {
            ((PackageSetting) sharedUserSetting.getDisabledPackageSettings().valueAt(0)).setSharedUserAppId(-1);
        }
        this.mSharedUsers.remove(sharedUserSetting.getName());
    }

    public void checkAndConvertSharedUserSettingsLPw(SharedUserSetting sharedUserSetting) {
        AndroidPackageInternal pkg;
        if (sharedUserSetting.isSingleUser() && (pkg = ((PackageSetting) sharedUserSetting.getPackageSettings().valueAt(0)).getPkg()) != null && pkg.isLeavingSharedUser() && SharedUidMigration.applyStrategy(2)) {
            convertSharedUserSettingsLPw(sharedUserSetting);
        }
    }

    public PreferredIntentResolver editPreferredActivitiesLPw(int i) {
        PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.get(i);
        if (preferredIntentResolver != null) {
            return preferredIntentResolver;
        }
        PreferredIntentResolver preferredIntentResolver2 = new PreferredIntentResolver();
        this.mPreferredActivities.put(i, preferredIntentResolver2);
        return preferredIntentResolver2;
    }

    public PersistentPreferredIntentResolver editPersistentPreferredActivitiesLPw(int i) {
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.get(i);
        if (persistentPreferredIntentResolver != null) {
            return persistentPreferredIntentResolver;
        }
        PersistentPreferredIntentResolver persistentPreferredIntentResolver2 = new PersistentPreferredIntentResolver();
        this.mPersistentPreferredActivities.put(i, persistentPreferredIntentResolver2);
        return persistentPreferredIntentResolver2;
    }

    public CrossProfileIntentResolver editCrossProfileIntentResolverLPw(int i) {
        CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) this.mCrossProfileIntentResolvers.get(i);
        if (crossProfileIntentResolver != null) {
            return crossProfileIntentResolver;
        }
        CrossProfileIntentResolver crossProfileIntentResolver2 = new CrossProfileIntentResolver();
        this.mCrossProfileIntentResolvers.put(i, crossProfileIntentResolver2);
        return crossProfileIntentResolver2;
    }

    public String removeDefaultBrowserPackageNameLPw(int i) {
        if (i == -1) {
            return null;
        }
        return (String) this.mDefaultBrowserApp.removeReturnOld(i);
    }

    public final File getUserSystemDirectory(int i) {
        return new File(new File(this.mSystemDir, "users"), Integer.toString(i));
    }

    public final ResilientAtomicFile getUserPackagesStateFile(int i) {
        return new ResilientAtomicFile(new File(getUserSystemDirectory(i), "package-restrictions.xml"), new File(getUserSystemDirectory(i), "package-restrictions-backup.xml"), new File(getUserSystemDirectory(i), "package-restrictions.xml.reservecopy"), FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, "package restrictions", this);
    }

    public final ResilientAtomicFile getSettingsFile() {
        return new ResilientAtomicFile(this.mSettingsFilename, this.mPreviousSettingsFilename, this.mSettingsReserveCopyFilename, FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, "package manager settings", this);
    }

    public final File getUserRuntimePermissionsFile(int i) {
        return new File(getUserSystemDirectory(i), "runtime-permissions.xml");
    }

    public void writeAllUsersPackageRestrictionsLPr() {
        writeAllUsersPackageRestrictionsLPr(false);
    }

    public void writeAllUsersPackageRestrictionsLPr(boolean z) {
        List allUsers = getAllUsers(UserManagerService.getInstance());
        if (allUsers == null) {
            return;
        }
        if (z) {
            synchronized (this.mPackageRestrictionsLock) {
                this.mPendingAsyncPackageRestrictionsWrites.clear();
            }
            this.mHandler.removeMessages(30);
        }
        Iterator it = allUsers.iterator();
        while (it.hasNext()) {
            writePackageRestrictionsLPr(((UserInfo) it.next()).id, z);
        }
    }

    public void writeAllRuntimePermissionsLPr() {
        for (int i : UserManagerService.getInstance().getUserIds()) {
            this.mRuntimePermissionsPersistence.writeStateForUserAsync(i);
        }
    }

    public boolean isPermissionUpgradeNeeded(int i) {
        return this.mRuntimePermissionsPersistence.isPermissionUpgradeNeeded(i);
    }

    public void updateRuntimePermissionsFingerprint(int i) {
        this.mRuntimePermissionsPersistence.updateRuntimePermissionsFingerprint(i);
    }

    public int getDefaultRuntimePermissionsVersion(int i) {
        return this.mRuntimePermissionsPersistence.getVersion(i);
    }

    public void setDefaultRuntimePermissionsVersion(int i, int i2) {
        this.mRuntimePermissionsPersistence.setVersion(i, i2);
    }

    public void setPermissionControllerVersion(long j) {
        this.mRuntimePermissionsPersistence.setPermissionControllerVersion(j);
    }

    public VersionInfo findOrCreateVersion(String str) {
        VersionInfo versionInfo = (VersionInfo) this.mVersion.get(str);
        if (versionInfo != null) {
            return versionInfo;
        }
        VersionInfo versionInfo2 = new VersionInfo();
        this.mVersion.put(str, versionInfo2);
        return versionInfo2;
    }

    public VersionInfo getInternalVersion() {
        return (VersionInfo) this.mVersion.get(StorageManager.UUID_PRIVATE_INTERNAL);
    }

    public VersionInfo getExternalVersion() {
        return (VersionInfo) this.mVersion.get("primary_physical");
    }

    public void onVolumeForgotten(String str) {
        this.mVersion.remove(str);
    }

    public void readPreferredActivitiesLPw(TypedXmlPullParser typedXmlPullParser, int i) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    PreferredActivity preferredActivity = new PreferredActivity(typedXmlPullParser);
                    if (preferredActivity.mPref.getParseError() == null) {
                        PreferredIntentResolver editPreferredActivitiesLPw = editPreferredActivitiesLPw(i);
                        if (editPreferredActivitiesLPw.shouldAddPreferredActivity(preferredActivity)) {
                            editPreferredActivitiesLPw.addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) preferredActivity);
                        }
                    } else {
                        PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <preferred-activity> " + preferredActivity.mPref.getParseError() + " at " + typedXmlPullParser.getPositionDescription());
                    }
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under <preferred-activities>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readPersistentPreferredActivitiesLPw(TypedXmlPullParser typedXmlPullParser, int i) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    editPersistentPreferredActivitiesLPw(i).addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) new PersistentPreferredActivity(typedXmlPullParser));
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under <persistent-preferred-activities>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readCrossProfileIntentFiltersLPw(TypedXmlPullParser typedXmlPullParser, int i) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals("item")) {
                    editCrossProfileIntentResolverLPw(i).addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) new CrossProfileIntentFilter(typedXmlPullParser));
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under crossProfile-intent-filters: " + name);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public void readDefaultAppsLPw(XmlPullParser xmlPullParser, int i) {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if (name.equals("default-browser")) {
                    this.mDefaultBrowserApp.put(i, xmlPullParser.getAttributeValue(null, "packageName"));
                } else if (!name.equals("default-dialer")) {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under default-apps: " + xmlPullParser.getName());
                    XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
    }

    public void readBlockUninstallPackagesLPw(TypedXmlPullParser typedXmlPullParser, int i) {
        int depth = typedXmlPullParser.getDepth();
        ArraySet arraySet = new ArraySet();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("block-uninstall")) {
                    arraySet.add(typedXmlPullParser.getAttributeValue((String) null, "packageName"));
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under block-uninstall-packages: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        if (arraySet.isEmpty()) {
            this.mBlockUninstallPackages.remove(i);
        } else {
            this.mBlockUninstallPackages.put(i, arraySet);
        }
    }

    @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
    public void logEvent(int i, String str) {
        this.mReadMessages.append(str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        PackageManagerService.reportSettingsProblem(i, str);
    }

    /* JADX WARN: Not initialized variable reg: 27, insn: 0x03c5: MOVE (r2 I:??[OBJECT, ARRAY]) = (r27 I:??[OBJECT, ARRAY]), block:B:249:0x03c4 */
    /* JADX WARN: Removed duplicated region for block: B:102:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x03f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:119:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x03e7  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void readPackageRestrictionsLPr(int i, ArrayMap arrayMap, PackageManagerBackupController packageManagerBackupController) {
        ResilientAtomicFile resilientAtomicFile;
        Throwable th;
        ResilientAtomicFile resilientAtomicFile2;
        Object obj;
        SuspendDialogInfo suspendDialogInfo;
        TypedXmlPullParser resolvePullParser;
        int next;
        char c;
        boolean z;
        char c2;
        int i2;
        char c3;
        boolean z2;
        char c4;
        SuspendDialogInfo suspendDialogInfo2;
        int i3;
        SuspendDialogInfo suspendDialogInfo3;
        boolean z3;
        int i4;
        char c5;
        ResilientAtomicFile userPackagesStateFile = getUserPackagesStateFile(i);
        SuspendDialogInfo suspendDialogInfo4 = null;
        try {
            try {
                Object obj2 = this.mPackageRestrictionsLock;
                try {
                    synchronized (obj2) {
                        try {
                            SuspendDialogInfo openRead = userPackagesStateFile.openRead();
                            if (openRead == null) {
                                try {
                                    openRead = restorePackagesState(packageManagerBackupController, i);
                                } catch (Throwable th2) {
                                    th = th2;
                                    resilientAtomicFile = userPackagesStateFile;
                                    suspendDialogInfo4 = openRead;
                                    obj = obj2;
                                    while (true) {
                                        try {
                                            try {
                                                break;
                                            } catch (IOException | XmlPullParserException e) {
                                                e = e;
                                                try {
                                                    if (resilientAtomicFile.existsReadableFile()) {
                                                    }
                                                    readPackageRestrictionsLPr(i, arrayMap, packageManagerBackupController);
                                                    if (userPackagesStateFile == null) {
                                                    }
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    userPackagesStateFile = resilientAtomicFile;
                                                    th = th;
                                                    if (userPackagesStateFile == null) {
                                                    }
                                                }
                                            }
                                        } catch (Throwable th4) {
                                            th = th4;
                                        }
                                    }
                                    throw th;
                                }
                            }
                            suspendDialogInfo = openRead;
                            try {
                                if (suspendDialogInfo == null) {
                                    try {
                                        Iterator it = this.mPackages.values().iterator();
                                        while (it.hasNext()) {
                                            Object obj3 = obj2;
                                            ResilientAtomicFile resilientAtomicFile3 = userPackagesStateFile;
                                            ((PackageSetting) it.next()).setUserState(i, 0L, 0, true, false, false, false, 0, null, false, false, null, null, null, 0, 0, null, null, 0L);
                                            obj2 = obj3;
                                            userPackagesStateFile = resilientAtomicFile3;
                                        }
                                        ResilientAtomicFile resilientAtomicFile4 = userPackagesStateFile;
                                        resilientAtomicFile4.close();
                                        return;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        resilientAtomicFile = userPackagesStateFile;
                                        obj = obj2;
                                        suspendDialogInfo4 = suspendDialogInfo;
                                        while (true) {
                                            break;
                                            break;
                                        }
                                        throw th;
                                    }
                                }
                                resilientAtomicFile = userPackagesStateFile;
                            } catch (Throwable th6) {
                                th = th6;
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            resilientAtomicFile = userPackagesStateFile;
                            obj = obj2;
                        }
                    }
                    try {
                        resolvePullParser = Xml.resolvePullParser(suspendDialogInfo);
                        do {
                            next = resolvePullParser.next();
                            c = 2;
                            z = true;
                            if (next == 2) {
                                break;
                            }
                        } while (next != 1);
                        c2 = 5;
                    } catch (IOException | XmlPullParserException e2) {
                        e = e2;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    userPackagesStateFile = resilientAtomicFile2;
                    if (userPackagesStateFile == null) {
                        throw th;
                    }
                    try {
                        userPackagesStateFile.close();
                        throw th;
                    } catch (Throwable th9) {
                        th.addSuppressed(th9);
                        throw th;
                    }
                }
            } catch (Throwable th10) {
                th = th10;
                th = th;
                if (userPackagesStateFile == null) {
                }
            }
        } catch (IOException | XmlPullParserException e3) {
            e = e3;
            resilientAtomicFile = userPackagesStateFile;
        }
        if (next != 2) {
            this.mReadMessages.append("No start tag found in package restrictions file\n");
            PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager package restrictions file");
            resilientAtomicFile.close();
            return;
        }
        int depth = resolvePullParser.getDepth();
        while (true) {
            int next2 = resolvePullParser.next();
            if (next2 != z && (next2 != 3 || resolvePullParser.getDepth() > depth)) {
                if (next2 != 3 && next2 != 4) {
                    String name = resolvePullParser.getName();
                    if (name.equals("pkg")) {
                        String attributeValue = resolvePullParser.getAttributeValue(suspendDialogInfo4, "name");
                        PackageSetting packageSetting = (PackageSetting) this.mPackages.get(attributeValue);
                        if (packageSetting == null) {
                            Slog.w("PackageManager", "No package known for package restrictions " + attributeValue);
                            XmlUtils.skipCurrentTag(resolvePullParser);
                        } else {
                            long attributeLong = resolvePullParser.getAttributeLong(suspendDialogInfo4, "ceDataInode", 0L);
                            boolean attributeBoolean = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "inst", z);
                            boolean attributeBoolean2 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "stopped", false);
                            boolean attributeBoolean3 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "nl", false);
                            boolean attributeBoolean4 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "hidden", false);
                            if (!attributeBoolean4) {
                                attributeBoolean4 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "blocked", false);
                            }
                            boolean z4 = attributeBoolean4;
                            int attributeInt = resolvePullParser.getAttributeInt(suspendDialogInfo4, "distraction_flags", 0);
                            boolean attributeBoolean5 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "suspended", false);
                            String attributeValue2 = resolvePullParser.getAttributeValue(suspendDialogInfo4, "suspending-package");
                            String attributeValue3 = resolvePullParser.getAttributeValue(suspendDialogInfo4, "suspend_dialog_message");
                            if (attributeBoolean5 && attributeValue2 == null) {
                                attributeValue2 = "android";
                            }
                            boolean attributeBoolean6 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "blockUninstall", false);
                            boolean attributeBoolean7 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "instant-app", false);
                            boolean attributeBoolean8 = resolvePullParser.getAttributeBoolean(suspendDialogInfo4, "virtual-preload", false);
                            int attributeInt2 = resolvePullParser.getAttributeInt(suspendDialogInfo4, "enabled", 0);
                            String attributeValue4 = resolvePullParser.getAttributeValue(suspendDialogInfo4, "enabledCaller");
                            String attributeValue5 = resolvePullParser.getAttributeValue(suspendDialogInfo4, "harmful-app-warning");
                            int attributeInt3 = resolvePullParser.getAttributeInt(suspendDialogInfo4, "domainVerificationStatus", 0);
                            int attributeInt4 = resolvePullParser.getAttributeInt(suspendDialogInfo4, "install-reason", 0);
                            int attributeInt5 = resolvePullParser.getAttributeInt(suspendDialogInfo4, "uninstall-reason", 0);
                            String attributeValue6 = resolvePullParser.getAttributeValue(suspendDialogInfo4, "splash-screen-theme");
                            long attributeLongHex = resolvePullParser.getAttributeLongHex(suspendDialogInfo4, "first-install-time", 0L);
                            int depth2 = resolvePullParser.getDepth();
                            SuspendDialogInfo suspendDialogInfo5 = suspendDialogInfo4;
                            SuspendDialogInfo suspendDialogInfo6 = suspendDialogInfo5;
                            SuspendDialogInfo suspendDialogInfo7 = suspendDialogInfo6;
                            SuspendDialogInfo suspendDialogInfo8 = suspendDialogInfo7;
                            SuspendDialogInfo suspendDialogInfo9 = suspendDialogInfo8;
                            SuspendDialogInfo suspendDialogInfo10 = suspendDialogInfo9;
                            while (true) {
                                int next3 = resolvePullParser.next();
                                i3 = depth;
                                if (next3 != 1) {
                                    int i5 = 3;
                                    if (next3 == 3) {
                                        if (resolvePullParser.getDepth() > depth2) {
                                            i5 = 3;
                                        }
                                    }
                                    if (next3 != i5 && next3 != 4) {
                                        String name2 = resolvePullParser.getName();
                                        switch (name2.hashCode()) {
                                            case -2027581689:
                                                if (name2.equals("disabled-components")) {
                                                    c5 = 1;
                                                    break;
                                                }
                                                c5 = 65535;
                                                break;
                                            case -1963032286:
                                                if (name2.equals("enabled-components")) {
                                                    c5 = 0;
                                                    break;
                                                }
                                                c5 = 65535;
                                                break;
                                            case -1592287551:
                                                if (name2.equals("suspended-app-extras")) {
                                                    c5 = 2;
                                                    break;
                                                }
                                                c5 = 65535;
                                                break;
                                            case -1422791362:
                                                if (name2.equals("suspended-launcher-extras")) {
                                                    c5 = 3;
                                                    break;
                                                }
                                                c5 = 65535;
                                                break;
                                            case -858175433:
                                                if (name2.equals("suspend-params")) {
                                                    c5 = 5;
                                                    break;
                                                }
                                                c5 = 65535;
                                                break;
                                            case 1660896545:
                                                if (name2.equals("suspended-dialog-info")) {
                                                    c5 = 4;
                                                    break;
                                                }
                                                c5 = 65535;
                                                break;
                                            default:
                                                c5 = 65535;
                                                break;
                                        }
                                        if (c5 == 0) {
                                            i4 = depth2;
                                            suspendDialogInfo7 = readComponentsLPr(resolvePullParser);
                                        } else if (c5 == 1) {
                                            i4 = depth2;
                                            suspendDialogInfo8 = readComponentsLPr(resolvePullParser);
                                        } else if (c5 == 2) {
                                            i4 = depth2;
                                            suspendDialogInfo6 = PersistableBundle.restoreFromXml(resolvePullParser);
                                        } else if (c5 == 3) {
                                            i4 = depth2;
                                            suspendDialogInfo9 = PersistableBundle.restoreFromXml(resolvePullParser);
                                        } else if (c5 == 4) {
                                            i4 = depth2;
                                            suspendDialogInfo5 = SuspendDialogInfo.restoreFromXml(resolvePullParser);
                                        } else if (c5 == 5) {
                                            i4 = depth2;
                                            String attributeValue7 = resolvePullParser.getAttributeValue((String) null, "suspending-package");
                                            if (attributeValue7 == null) {
                                                Slog.wtf("PackageSettings", "No suspendingPackage found inside tag suspend-params");
                                            } else {
                                                if (suspendDialogInfo10 == null) {
                                                    suspendDialogInfo10 = new ArrayMap();
                                                }
                                                SuspendDialogInfo suspendDialogInfo11 = suspendDialogInfo10;
                                                suspendDialogInfo11.put(attributeValue7, SuspendParams.restoreFromXml(resolvePullParser));
                                                suspendDialogInfo10 = suspendDialogInfo11;
                                            }
                                        } else {
                                            StringBuilder sb = new StringBuilder();
                                            i4 = depth2;
                                            sb.append("Unknown tag ");
                                            sb.append(resolvePullParser.getName());
                                            sb.append(" under tag ");
                                            sb.append("pkg");
                                            Slog.wtf("PackageSettings", sb.toString());
                                        }
                                        depth = i3;
                                        depth2 = i4;
                                    }
                                    i4 = depth2;
                                    depth = i3;
                                    depth2 = i4;
                                }
                            }
                            if (suspendDialogInfo5 == null && !TextUtils.isEmpty(attributeValue3)) {
                                suspendDialogInfo5 = new SuspendDialogInfo.Builder().setMessage(attributeValue3).build();
                            }
                            if (attributeBoolean5 && suspendDialogInfo10 == null) {
                                SuspendParams suspendParams = new SuspendParams(suspendDialogInfo5, suspendDialogInfo6, suspendDialogInfo9);
                                SuspendDialogInfo arrayMap2 = new ArrayMap();
                                arrayMap2.put(attributeValue2, suspendParams);
                                suspendDialogInfo3 = arrayMap2;
                            } else {
                                suspendDialogInfo3 = suspendDialogInfo10;
                            }
                            if (attributeBoolean6) {
                                z3 = true;
                                try {
                                    setBlockUninstallLPw(i, attributeValue, true);
                                } catch (IOException | XmlPullParserException e4) {
                                    e = e4;
                                    suspendDialogInfo4 = suspendDialogInfo;
                                    if (resilientAtomicFile.existsReadableFile()) {
                                    }
                                    readPackageRestrictionsLPr(i, arrayMap, packageManagerBackupController);
                                    if (userPackagesStateFile == null) {
                                    }
                                }
                            } else {
                                z3 = true;
                            }
                            if (attributeLongHex == 0) {
                                attributeLongHex = ((Long) arrayMap.getOrDefault(attributeValue, 0L)).longValue();
                            }
                            TypedXmlPullParser typedXmlPullParser = resolvePullParser;
                            i2 = i3;
                            c3 = 5;
                            z2 = z3;
                            c4 = 2;
                            suspendDialogInfo2 = null;
                            packageSetting.setUserState(i, attributeLong, attributeInt2, attributeBoolean, attributeBoolean2, attributeBoolean3, z4, attributeInt, suspendDialogInfo3, attributeBoolean7, attributeBoolean8, attributeValue4, suspendDialogInfo7, suspendDialogInfo8, attributeInt4, attributeInt5, attributeValue5, attributeValue6, attributeLongHex);
                            try {
                                this.mDomainVerificationManager.setLegacyUserState(attributeValue, i, attributeInt3);
                                resolvePullParser = typedXmlPullParser;
                            } catch (IOException | XmlPullParserException e5) {
                                e = e5;
                                suspendDialogInfo4 = suspendDialogInfo;
                                if (resilientAtomicFile.existsReadableFile()) {
                                    userPackagesStateFile = resilientAtomicFile;
                                    userPackagesStateFile.failRead(suspendDialogInfo4, e);
                                } else {
                                    userPackagesStateFile = resilientAtomicFile;
                                }
                                readPackageRestrictionsLPr(i, arrayMap, packageManagerBackupController);
                                if (userPackagesStateFile == null) {
                                }
                            }
                        }
                    } else {
                        TypedXmlPullParser typedXmlPullParser2 = resolvePullParser;
                        i2 = depth;
                        c3 = c2;
                        z2 = z;
                        c4 = c;
                        suspendDialogInfo2 = suspendDialogInfo4;
                        if (name.equals("preferred-activities")) {
                            resolvePullParser = typedXmlPullParser2;
                            readPreferredActivitiesLPw(resolvePullParser, i);
                        } else {
                            resolvePullParser = typedXmlPullParser2;
                            if (name.equals("persistent-preferred-activities")) {
                                readPersistentPreferredActivitiesLPw(resolvePullParser, i);
                            } else if (name.equals("crossProfile-intent-filters")) {
                                readCrossProfileIntentFiltersLPw(resolvePullParser, i);
                            } else if (name.equals("default-apps")) {
                                readDefaultAppsLPw(resolvePullParser, i);
                            } else if (name.equals("block-uninstall-packages")) {
                                readBlockUninstallPackagesLPw(resolvePullParser, i);
                            } else {
                                Slog.w("PackageManager", "Unknown element under <stopped-packages>: " + resolvePullParser.getName());
                                XmlUtils.skipCurrentTag(resolvePullParser);
                            }
                        }
                    }
                    depth = i2;
                    c2 = c3;
                    z = z2;
                    suspendDialogInfo4 = suspendDialogInfo2;
                    c = c4;
                }
            }
        }
        userPackagesStateFile = resilientAtomicFile;
        if (userPackagesStateFile == null) {
            userPackagesStateFile.close();
        }
    }

    public void setBlockUninstallLPw(int i, String str, boolean z) {
        ArraySet arraySet = (ArraySet) this.mBlockUninstallPackages.get(i);
        if (z) {
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mBlockUninstallPackages.put(i, arraySet);
            }
            arraySet.add(str);
            return;
        }
        if (arraySet != null) {
            arraySet.remove(str);
            if (arraySet.isEmpty()) {
                this.mBlockUninstallPackages.remove(i);
            }
        }
    }

    public void clearBlockUninstallLPw(int i) {
        this.mBlockUninstallPackages.remove(i);
    }

    public boolean getBlockUninstallLPr(int i, String str) {
        ArraySet arraySet = (ArraySet) this.mBlockUninstallPackages.get(i);
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(str);
    }

    public final ArraySet readComponentsLPr(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue;
        int depth = typedXmlPullParser.getDepth();
        ArraySet arraySet = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("item") && (attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name")) != null) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                }
                arraySet.add(attributeValue);
            }
        }
        return arraySet;
    }

    public void writePreferredActivitiesLPr(TypedXmlSerializer typedXmlSerializer, int i, boolean z) {
        typedXmlSerializer.startTag((String) null, "preferred-activities");
        PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.get(i);
        if (preferredIntentResolver != null) {
            for (PreferredActivity preferredActivity : preferredIntentResolver.filterSet()) {
                typedXmlSerializer.startTag((String) null, "item");
                preferredActivity.writeToXml(typedXmlSerializer, z);
                typedXmlSerializer.endTag((String) null, "item");
            }
        }
        typedXmlSerializer.endTag((String) null, "preferred-activities");
    }

    public void writePersistentPreferredActivitiesLPr(TypedXmlSerializer typedXmlSerializer, int i) {
        typedXmlSerializer.startTag((String) null, "persistent-preferred-activities");
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.get(i);
        if (persistentPreferredIntentResolver != null) {
            for (PersistentPreferredActivity persistentPreferredActivity : persistentPreferredIntentResolver.filterSet()) {
                typedXmlSerializer.startTag((String) null, "item");
                persistentPreferredActivity.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "item");
            }
        }
        typedXmlSerializer.endTag((String) null, "persistent-preferred-activities");
    }

    public void writeCrossProfileIntentFiltersLPr(TypedXmlSerializer typedXmlSerializer, int i) {
        typedXmlSerializer.startTag((String) null, "crossProfile-intent-filters");
        CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) this.mCrossProfileIntentResolvers.get(i);
        if (crossProfileIntentResolver != null) {
            for (CrossProfileIntentFilter crossProfileIntentFilter : crossProfileIntentResolver.filterSet()) {
                typedXmlSerializer.startTag((String) null, "item");
                crossProfileIntentFilter.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "item");
            }
        }
        typedXmlSerializer.endTag((String) null, "crossProfile-intent-filters");
    }

    public void writeDefaultAppsLPr(XmlSerializer xmlSerializer, int i) {
        xmlSerializer.startTag(null, "default-apps");
        String str = (String) this.mDefaultBrowserApp.get(i);
        if (!TextUtils.isEmpty(str)) {
            xmlSerializer.startTag(null, "default-browser");
            xmlSerializer.attribute(null, "packageName", str);
            xmlSerializer.endTag(null, "default-browser");
        }
        xmlSerializer.endTag(null, "default-apps");
    }

    public void writeBlockUninstallPackagesLPr(TypedXmlSerializer typedXmlSerializer, int i) {
        ArraySet arraySet = (ArraySet) this.mBlockUninstallPackages.get(i);
        if (arraySet != null) {
            typedXmlSerializer.startTag((String) null, "block-uninstall-packages");
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                typedXmlSerializer.startTag((String) null, "block-uninstall");
                typedXmlSerializer.attribute((String) null, "packageName", (String) arraySet.valueAt(i2));
                typedXmlSerializer.endTag((String) null, "block-uninstall");
            }
            typedXmlSerializer.endTag((String) null, "block-uninstall-packages");
        }
    }

    public void writePackageRestrictionsLPr(int i) {
        writePackageRestrictionsLPr(i, false);
    }

    public void writePackageRestrictionsLPr(final int i, final boolean z) {
        invalidatePackageCache();
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (z) {
            lambda$writePackageRestrictionsLPr$1(i, uptimeMillis, z);
            return;
        }
        synchronized (this.mPackageRestrictionsLock) {
            this.mPendingAsyncPackageRestrictionsWrites.put(i, this.mPendingAsyncPackageRestrictionsWrites.get(i, 0) + 1);
        }
        this.mHandler.obtainMessage(30, new Runnable() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Settings.this.lambda$writePackageRestrictionsLPr$1(i, uptimeMillis, z);
            }
        }).sendToTarget();
    }

    public void writePackageRestrictions(Integer[] numArr) {
        invalidatePackageCache();
        long uptimeMillis = SystemClock.uptimeMillis();
        for (Integer num : numArr) {
            lambda$writePackageRestrictionsLPr$1(num.intValue(), uptimeMillis, true);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0030, code lost:
    
        if (r2 == null) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0032, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return;
     */
    /* renamed from: writePackageRestrictions, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lambda$writePackageRestrictionsLPr$1(int i, long j, boolean z) {
        FileOutputStream startWrite;
        ResilientAtomicFile userPackagesStateFile = getUserPackagesStateFile(i);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                synchronized (this.mPackageRestrictionsLock) {
                    try {
                        if (!z) {
                            int i2 = this.mPendingAsyncPackageRestrictionsWrites.get(i, 0) - 1;
                            if (i2 < 0) {
                                Log.i("PackageSettings", "Cancel writing package restrictions for user=" + i);
                            } else {
                                this.mPendingAsyncPackageRestrictionsWrites.put(i, i2);
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        startWrite = userPackagesStateFile.startWrite();
                        try {
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = startWrite;
                            throw th;
                        }
                    } catch (IOException e) {
                        Slog.wtf("PackageManager", "Unable to write package manager package restrictions,  current changes will be lost at reboot", e);
                        if (userPackagesStateFile != null) {
                            userPackagesStateFile.close();
                            return;
                        }
                        return;
                    }
                }
                try {
                    synchronized (this.mLock) {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                        resolveSerializer.startTag((String) null, "package-restrictions");
                        for (PackageSetting packageSetting : this.mPackages.values()) {
                            PackageUserStateInternal readUserState = packageSetting.readUserState(i);
                            resolveSerializer.startTag((String) null, "pkg");
                            resolveSerializer.attribute((String) null, "name", packageSetting.getPackageName());
                            if (readUserState.getCeDataInode() != 0) {
                                resolveSerializer.attributeLong((String) null, "ceDataInode", readUserState.getCeDataInode());
                            }
                            if (!readUserState.isInstalled()) {
                                resolveSerializer.attributeBoolean((String) null, "inst", false);
                            }
                            if (readUserState.isStopped()) {
                                resolveSerializer.attributeBoolean((String) null, "stopped", true);
                            }
                            if (readUserState.isNotLaunched()) {
                                resolveSerializer.attributeBoolean((String) null, "nl", true);
                            }
                            if (readUserState.isHidden()) {
                                resolveSerializer.attributeBoolean((String) null, "hidden", true);
                            }
                            if (readUserState.getDistractionFlags() != 0) {
                                resolveSerializer.attributeInt((String) null, "distraction_flags", readUserState.getDistractionFlags());
                            }
                            if (readUserState.isSuspended()) {
                                resolveSerializer.attributeBoolean((String) null, "suspended", true);
                            }
                            if (readUserState.isInstantApp()) {
                                resolveSerializer.attributeBoolean((String) null, "instant-app", true);
                            }
                            if (readUserState.isVirtualPreload()) {
                                resolveSerializer.attributeBoolean((String) null, "virtual-preload", true);
                            }
                            if (readUserState.getEnabledState() != 0) {
                                resolveSerializer.attributeInt((String) null, "enabled", readUserState.getEnabledState());
                            }
                            if (readUserState.getLastDisableAppCaller() != null) {
                                resolveSerializer.attribute((String) null, "enabledCaller", readUserState.getLastDisableAppCaller());
                            }
                            if (readUserState.getInstallReason() != 0) {
                                resolveSerializer.attributeInt((String) null, "install-reason", readUserState.getInstallReason());
                            }
                            resolveSerializer.attributeLongHex((String) null, "first-install-time", readUserState.getFirstInstallTimeMillis());
                            if (readUserState.getUninstallReason() != 0) {
                                resolveSerializer.attributeInt((String) null, "uninstall-reason", readUserState.getUninstallReason());
                            }
                            if (readUserState.getHarmfulAppWarning() != null) {
                                resolveSerializer.attribute((String) null, "harmful-app-warning", readUserState.getHarmfulAppWarning());
                            }
                            if (readUserState.getSplashScreenTheme() != null) {
                                resolveSerializer.attribute((String) null, "splash-screen-theme", readUserState.getSplashScreenTheme());
                            }
                            if (readUserState.isSuspended()) {
                                for (int i3 = 0; i3 < readUserState.getSuspendParams().size(); i3++) {
                                    String str = (String) readUserState.getSuspendParams().keyAt(i3);
                                    resolveSerializer.startTag((String) null, "suspend-params");
                                    resolveSerializer.attribute((String) null, "suspending-package", str);
                                    SuspendParams suspendParams = (SuspendParams) readUserState.getSuspendParams().valueAt(i3);
                                    if (suspendParams != null) {
                                        suspendParams.saveToXml(resolveSerializer);
                                    }
                                    resolveSerializer.endTag((String) null, "suspend-params");
                                }
                            }
                            ArraySet m10014getEnabledComponents = readUserState.m10014getEnabledComponents();
                            if (m10014getEnabledComponents != null && m10014getEnabledComponents.size() > 0) {
                                resolveSerializer.startTag((String) null, "enabled-components");
                                for (int i4 = 0; i4 < m10014getEnabledComponents.size(); i4++) {
                                    resolveSerializer.startTag((String) null, "item");
                                    resolveSerializer.attribute((String) null, "name", (String) m10014getEnabledComponents.valueAt(i4));
                                    resolveSerializer.endTag((String) null, "item");
                                }
                                resolveSerializer.endTag((String) null, "enabled-components");
                            }
                            ArraySet m10013getDisabledComponents = readUserState.m10013getDisabledComponents();
                            if (m10013getDisabledComponents != null && m10013getDisabledComponents.size() > 0) {
                                resolveSerializer.startTag((String) null, "disabled-components");
                                for (int i5 = 0; i5 < m10013getDisabledComponents.size(); i5++) {
                                    resolveSerializer.startTag((String) null, "item");
                                    resolveSerializer.attribute((String) null, "name", (String) m10013getDisabledComponents.valueAt(i5));
                                    resolveSerializer.endTag((String) null, "item");
                                }
                                resolveSerializer.endTag((String) null, "disabled-components");
                            }
                            resolveSerializer.endTag((String) null, "pkg");
                        }
                        writePreferredActivitiesLPr(resolveSerializer, i, true);
                        writePersistentPreferredActivitiesLPr(resolveSerializer, i);
                        writeCrossProfileIntentFiltersLPr(resolveSerializer, i);
                        writeDefaultAppsLPr(resolveSerializer, i);
                        writeBlockUninstallPackagesLPr(resolveSerializer, i);
                        resolveSerializer.endTag((String) null, "package-restrictions");
                        resolveSerializer.endDocument();
                    }
                    userPackagesStateFile.finishWrite(startWrite);
                    EventLogTags.writeCommitSysConfigFile("package-user-" + i, SystemClock.uptimeMillis() - j);
                    userPackagesStateFile.close();
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = startWrite;
                    Slog.wtf("PackageManager", "Unable to write package manager package restrictions,  current changes will be lost at reboot", e);
                    if (fileOutputStream != null) {
                        userPackagesStateFile.failWrite(fileOutputStream);
                    }
                    if (userPackagesStateFile != null) {
                        userPackagesStateFile.close();
                    }
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            if (userPackagesStateFile == null) {
                throw th3;
            }
            try {
                userPackagesStateFile.close();
                throw th3;
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
                throw th3;
            }
        }
    }

    public void readInstallPermissionsLPr(TypedXmlPullParser typedXmlPullParser, LegacyPermissionState legacyPermissionState, List list) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                    boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "granted", true);
                    int attributeIntHex = typedXmlPullParser.getAttributeIntHex((String) null, "flags", 0);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(attributeValue, false, attributeBoolean, attributeIntHex), ((UserInfo) it.next()).id);
                    }
                } else {
                    Slog.w("PackageManager", "Unknown element under <permissions>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public void readUsesSdkLibLPw(TypedXmlPullParser typedXmlPullParser, PackageSetting packageSetting) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "version", -1L);
        if (attributeValue != null && attributeLong >= 0) {
            packageSetting.setUsesSdkLibraries((String[]) ArrayUtils.appendElement(String.class, packageSetting.getUsesSdkLibraries(), attributeValue));
            packageSetting.setUsesSdkLibrariesVersionsMajor(ArrayUtils.appendLong(packageSetting.getUsesSdkLibrariesVersionsMajor(), attributeLong));
        }
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public void readUsesStaticLibLPw(TypedXmlPullParser typedXmlPullParser, PackageSetting packageSetting) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "version", -1L);
        if (attributeValue != null && attributeLong >= 0) {
            packageSetting.setUsesStaticLibraries((String[]) ArrayUtils.appendElement(String.class, packageSetting.getUsesStaticLibraries(), attributeValue));
            packageSetting.setUsesStaticLibrariesVersions(ArrayUtils.appendLong(packageSetting.getUsesStaticLibrariesVersions(), attributeLong));
        }
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public void writeUsesSdkLibLPw(TypedXmlSerializer typedXmlSerializer, String[] strArr, long[] jArr) {
        if (ArrayUtils.isEmpty(strArr) || ArrayUtils.isEmpty(jArr) || strArr.length != jArr.length) {
            return;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            long j = jArr[i];
            typedXmlSerializer.startTag((String) null, "uses-sdk-lib");
            typedXmlSerializer.attribute((String) null, "name", str);
            typedXmlSerializer.attributeLong((String) null, "version", j);
            typedXmlSerializer.endTag((String) null, "uses-sdk-lib");
        }
    }

    public void writeUsesStaticLibLPw(TypedXmlSerializer typedXmlSerializer, String[] strArr, long[] jArr) {
        if (ArrayUtils.isEmpty(strArr) || ArrayUtils.isEmpty(jArr) || strArr.length != jArr.length) {
            return;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            long j = jArr[i];
            typedXmlSerializer.startTag((String) null, "uses-static-lib");
            typedXmlSerializer.attribute((String) null, "name", str);
            typedXmlSerializer.attributeLong((String) null, "version", j);
            typedXmlSerializer.endTag((String) null, "uses-static-lib");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0096 A[Catch: IOException -> 0x0128, XmlPullParserException -> 0x0159, TryCatch #4 {IOException -> 0x0128, XmlPullParserException -> 0x0159, blocks: (B:52:0x004c, B:54:0x0054, B:55:0x006a, B:57:0x0070, B:60:0x007e, B:6:0x0085, B:7:0x0089, B:13:0x0096, B:16:0x00a4, B:17:0x00a8, B:21:0x00b1, B:30:0x00bc, B:37:0x00c9, B:39:0x00da, B:41:0x00ec, B:42:0x0104, B:44:0x00f0, B:33:0x0108, B:24:0x0124), top: B:51:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a4 A[Catch: IOException -> 0x0128, XmlPullParserException -> 0x0159, TryCatch #4 {IOException -> 0x0128, XmlPullParserException -> 0x0159, blocks: (B:52:0x004c, B:54:0x0054, B:55:0x006a, B:57:0x0070, B:60:0x007e, B:6:0x0085, B:7:0x0089, B:13:0x0096, B:16:0x00a4, B:17:0x00a8, B:21:0x00b1, B:30:0x00bc, B:37:0x00c9, B:39:0x00da, B:41:0x00ec, B:42:0x0104, B:44:0x00f0, B:33:0x0108, B:24:0x0124), top: B:51:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0094 A[EDGE_INSN: B:50:0x0094->B:12:0x0094 BREAK  A[LOOP:0: B:7:0x0089->B:10:0x0093], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x004c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0091 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void readStoppedLPw() {
        FileInputStream fileInputStream;
        TypedXmlPullParser resolvePullParser;
        int next;
        if (this.mBackupStoppedPackagesFilename.exists()) {
            try {
                fileInputStream = new FileInputStream(this.mBackupStoppedPackagesFilename);
                try {
                    this.mReadMessages.append("Reading from backup stopped packages file\n");
                    PackageManagerService.reportSettingsProblem(4, "Need to read from backup stopped packages file");
                    if (this.mStoppedPackagesFilename.exists()) {
                        Slog.w("PackageManager", "Cleaning up stopped packages file " + this.mStoppedPackagesFilename);
                        this.mStoppedPackagesFilename.delete();
                    }
                } catch (IOException unused) {
                }
            } catch (IOException unused2) {
            }
            if (fileInputStream == null) {
                try {
                    if (!this.mStoppedPackagesFilename.exists()) {
                        this.mReadMessages.append("No stopped packages file found\n");
                        PackageManagerService.reportSettingsProblem(4, "No stopped packages file file; assuming all started");
                        for (PackageSetting packageSetting : this.mPackages.values()) {
                            packageSetting.setStopped(false, 0);
                            packageSetting.setNotLaunched(false, 0);
                        }
                        return;
                    }
                    fileInputStream = new FileInputStream(this.mStoppedPackagesFilename);
                } catch (IOException e) {
                    this.mReadMessages.append("Error reading: " + e.toString());
                    PackageManagerService.reportSettingsProblem(6, "!@Error reading settings: " + e);
                    Slog.wtf("PackageManager", "Error reading package manager stopped packages", e);
                    return;
                } catch (XmlPullParserException e2) {
                    this.mReadMessages.append("Error reading: " + e2.toString());
                    PackageManagerService.reportSettingsProblem(6, "!@Error reading stopped packages: " + e2);
                    Slog.wtf("PackageManager", "Error reading package manager stopped packages", e2);
                    return;
                }
            }
            resolvePullParser = Xml.resolvePullParser(fileInputStream);
            do {
                next = resolvePullParser.next();
                if (next != 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                this.mReadMessages.append("No start tag found in stopped packages file\n");
                PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager stopped packages");
                return;
            }
            int depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                    break;
                }
                if (next2 != 3 && next2 != 4) {
                    if (resolvePullParser.getName().equals("pkg")) {
                        String attributeValue = resolvePullParser.getAttributeValue((String) null, "name");
                        PackageSetting packageSetting2 = (PackageSetting) this.mPackages.get(attributeValue);
                        if (packageSetting2 != null) {
                            packageSetting2.setStopped(true, 0);
                            if ("1".equals(resolvePullParser.getAttributeValue((String) null, "nl"))) {
                                packageSetting2.setNotLaunched(true, 0);
                            }
                        } else {
                            Slog.w("PackageManager", "No package known for stopped package " + attributeValue);
                        }
                        XmlUtils.skipCurrentTag(resolvePullParser);
                    } else {
                        Slog.w("PackageManager", "Unknown element under <stopped-packages>: " + resolvePullParser.getName());
                        XmlUtils.skipCurrentTag(resolvePullParser);
                    }
                }
            }
            fileInputStream.close();
            return;
        }
        fileInputStream = null;
        if (fileInputStream == null) {
        }
        resolvePullParser = Xml.resolvePullParser(fileInputStream);
        do {
            next = resolvePullParser.next();
            if (next != 2) {
            }
        } while (next != 1);
        if (next == 2) {
        }
    }

    public void writeLPr(Computer computer, boolean z) {
        FileOutputStream startWrite;
        long uptimeMillis = SystemClock.uptimeMillis();
        invalidatePackageCache();
        this.mPastSignatures.clear();
        ResilientAtomicFile settingsFile = getSettingsFile();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                startWrite = settingsFile.startWrite();
            } catch (IOException e) {
                e = e;
            }
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((String) null, "packages");
                int i = 0;
                while (i < this.mVersion.size()) {
                    String str = (String) this.mVersion.keyAt(i);
                    VersionInfo versionInfo = (VersionInfo) this.mVersion.valueAt(i);
                    resolveSerializer.startTag((String) null, "version");
                    XmlUtils.writeStringAttribute(resolveSerializer, "volumeUuid", str);
                    resolveSerializer.attributeInt((String) null, "sdkVersion", versionInfo.sdkVersion);
                    resolveSerializer.attributeInt((String) null, "databaseVersion", versionInfo.databaseVersion);
                    XmlUtils.writeStringAttribute(resolveSerializer, "buildFingerprint", versionInfo.buildFingerprint);
                    XmlUtils.writeStringAttribute(resolveSerializer, "fingerprint", versionInfo.fingerprint);
                    resolveSerializer.endTag((String) null, "version");
                    i++;
                    uptimeMillis = uptimeMillis;
                }
                long j = uptimeMillis;
                if (this.mVerifierDeviceIdentity != null) {
                    resolveSerializer.startTag((String) null, "verifier");
                    resolveSerializer.attribute((String) null, "device", this.mVerifierDeviceIdentity.toString());
                    resolveSerializer.endTag((String) null, "verifier");
                }
                resolveSerializer.startTag((String) null, "permission-trees");
                this.mPermissions.writePermissionTrees(resolveSerializer);
                resolveSerializer.endTag((String) null, "permission-trees");
                resolveSerializer.startTag((String) null, "permissions");
                this.mPermissions.writePermissions(resolveSerializer);
                resolveSerializer.endTag((String) null, "permissions");
                for (PackageSetting packageSetting : this.mPackages.values()) {
                    if (packageSetting.getPkg() == null || !packageSetting.getPkg().isApex()) {
                        writePackageLPr(resolveSerializer, packageSetting);
                    }
                }
                for (PackageSetting packageSetting2 : this.mDisabledSysPackages.values()) {
                    if (packageSetting2.getPkg() == null || !packageSetting2.getPkg().isApex()) {
                        writeDisabledSysPackageLPr(resolveSerializer, packageSetting2);
                    }
                }
                for (SharedUserSetting sharedUserSetting : this.mSharedUsers.values()) {
                    resolveSerializer.startTag((String) null, "shared-user");
                    resolveSerializer.attribute((String) null, "name", sharedUserSetting.name);
                    resolveSerializer.attributeInt((String) null, "userId", sharedUserSetting.mAppId);
                    sharedUserSetting.signatures.writeXml(resolveSerializer, "sigs", this.mPastSignatures.untrackedStorage());
                    resolveSerializer.endTag((String) null, "shared-user");
                }
                if (this.mRenamedPackages.size() > 0) {
                    for (Map.Entry entry : this.mRenamedPackages.entrySet()) {
                        resolveSerializer.startTag((String) null, "renamed-package");
                        resolveSerializer.attribute((String) null, "new", (String) entry.getKey());
                        resolveSerializer.attribute((String) null, "old", (String) entry.getValue());
                        resolveSerializer.endTag((String) null, "renamed-package");
                    }
                }
                this.mDomainVerificationManager.writeSettings(computer, resolveSerializer, false, -1);
                this.mKeySetManagerService.writeKeySetManagerServiceLPr(resolveSerializer);
                resolveSerializer.endTag((String) null, "packages");
                resolveSerializer.endDocument();
                settingsFile.finishWrite(startWrite);
                writeKernelMappingLPr();
                writePackageListLPr();
                writeAllUsersPackageRestrictionsLPr(z);
                writeAllRuntimePermissionsLPr();
                EventLogTags.writeCommitSysConfigFile("package", SystemClock.uptimeMillis() - j);
                settingsFile.close();
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = startWrite;
                Slog.wtf("PackageManager", "Unable to write package manager settings, current changes will be lost at reboot", e);
                if (fileOutputStream != null) {
                    settingsFile.failWrite(fileOutputStream);
                }
                if (settingsFile != null) {
                    settingsFile.close();
                }
            }
        } catch (Throwable th) {
            if (settingsFile == null) {
                throw th;
            }
            try {
                settingsFile.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    public final void writeKernelRemoveUserLPr(int i) {
        if (this.mKernelMappingFilename == null) {
            return;
        }
        writeIntToFile(new File(this.mKernelMappingFilename, "remove_userid"), i);
    }

    public void writeKernelMappingLPr() {
        File file = this.mKernelMappingFilename;
        if (file == null) {
            return;
        }
        String[] list = file.list();
        ArraySet arraySet = new ArraySet(list.length);
        for (String str : list) {
            arraySet.add(str);
        }
        for (PackageSetting packageSetting : this.mPackages.values()) {
            arraySet.remove(packageSetting.getPackageName());
            writeKernelMappingLPr(packageSetting);
        }
        for (int i = 0; i < arraySet.size(); i++) {
            String str2 = (String) arraySet.valueAt(i);
            this.mKernelMapping.remove(str2);
            new File(this.mKernelMappingFilename, str2).delete();
        }
    }

    public void writeKernelMappingLPr(PackageSetting packageSetting) {
        if (this.mKernelMappingFilename == null || packageSetting == null || packageSetting.getPackageName() == null) {
            return;
        }
        writeKernelMappingLPr(packageSetting.getPackageName(), packageSetting.getAppId(), packageSetting.getNotInstalledUserIds());
    }

    public void writeKernelMappingLPr(String str, int i, int[] iArr) {
        KernelPackageState kernelPackageState = (KernelPackageState) this.mKernelMapping.get(str);
        boolean z = true;
        int i2 = 0;
        boolean z2 = kernelPackageState == null;
        if (!z2 && Arrays.equals(iArr, kernelPackageState.excludedUserIds)) {
            z = false;
        }
        File file = new File(this.mKernelMappingFilename, str);
        if (z2) {
            file.mkdir();
            kernelPackageState = new KernelPackageState();
            this.mKernelMapping.put(str, kernelPackageState);
        }
        if (kernelPackageState.appId != i) {
            writeIntToFile(new File(file, "appid"), i);
        }
        if (z) {
            for (int i3 = 0; i3 < iArr.length; i3++) {
                int[] iArr2 = kernelPackageState.excludedUserIds;
                if (iArr2 == null || !ArrayUtils.contains(iArr2, iArr[i3])) {
                    writeIntToFile(new File(file, "excluded_userids"), iArr[i3]);
                }
            }
            if (kernelPackageState.excludedUserIds != null) {
                while (true) {
                    int[] iArr3 = kernelPackageState.excludedUserIds;
                    if (i2 >= iArr3.length) {
                        break;
                    }
                    if (!ArrayUtils.contains(iArr, iArr3[i2])) {
                        writeIntToFile(new File(file, "clear_userid"), kernelPackageState.excludedUserIds[i2]);
                    }
                    i2++;
                }
            }
            kernelPackageState.excludedUserIds = iArr;
        }
    }

    public final void writeIntToFile(File file, int i) {
        try {
            FileUtils.bytesToFile(file.getAbsolutePath(), Integer.toString(i).getBytes(StandardCharsets.US_ASCII));
        } catch (IOException unused) {
            Slog.w("PackageSettings", "Couldn't write " + i + " to " + file.getAbsolutePath());
        }
    }

    public void writePackageListLPr() {
        writePackageListLPr(-1);
    }

    public void writePackageListLPr(int i) {
        String fileSelabelLookup = SELinux.fileSelabelLookup(this.mPackageListFilename.getAbsolutePath());
        if (fileSelabelLookup == null) {
            Slog.wtf("PackageSettings", "Failed to get SELinux context for " + this.mPackageListFilename.getAbsolutePath());
        }
        if (!SELinux.setFSCreateContext(fileSelabelLookup)) {
            Slog.wtf("PackageSettings", "Failed to set packages.list SELinux context");
        }
        try {
            writePackageListLPrInternal(i);
        } finally {
            SELinux.setFSCreateContext((String) null);
        }
    }

    public final void writePackageListLPrInternal(int i) {
        BufferedWriter bufferedWriter;
        int i2;
        Settings settings = this;
        List activeUsers = getActiveUsers(UserManagerService.getInstance(), true);
        int size = activeUsers.size();
        int[] iArr = new int[size];
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            iArr[i4] = ((UserInfo) activeUsers.get(i4)).id;
        }
        if (i != -1) {
            iArr = ArrayUtils.appendInt(iArr, i);
        }
        JournaledFile journaledFile = new JournaledFile(settings.mPackageListFilename, new File(settings.mPackageListFilename.getAbsolutePath() + ".tmp"));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(journaledFile.chooseForWrite());
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.defaultCharset()));
            try {
                FileUtils.setPermissions(fileOutputStream.getFD(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
                StringBuilder sb = new StringBuilder();
                for (PackageSetting packageSetting : settings.mPackages.values()) {
                    String absolutePath = packageSetting.getPkg() == null ? null : PackageInfoUtils.getDataDir(packageSetting.getPkg(), i3).getAbsolutePath();
                    if (packageSetting.getPkg() != null && absolutePath != null) {
                        if (!packageSetting.getPkg().isApex()) {
                            boolean isDebuggable = packageSetting.getPkg().isDebuggable();
                            IntArray intArray = new IntArray();
                            int i5 = i3;
                            for (int length = iArr.length; i5 < length; length = length) {
                                intArray.addAll(settings.mPermissionDataProvider.getGidsForUid(UserHandle.getUid(iArr[i5], packageSetting.getAppId())));
                                i5++;
                                settings = this;
                            }
                            if (absolutePath.indexOf(32) >= 0) {
                                settings = this;
                                i3 = 0;
                            } else {
                                sb.setLength(0);
                                sb.append(packageSetting.getPkg().getPackageName());
                                sb.append(" ");
                                sb.append(packageSetting.getPkg().getUid());
                                sb.append(isDebuggable ? " 1 " : " 0 ");
                                sb.append(absolutePath);
                                sb.append(" ");
                                sb.append(packageSetting.getSeInfo());
                                sb.append(" ");
                                int size2 = intArray.size();
                                if (intArray.size() > 0) {
                                    i2 = 0;
                                    sb.append(intArray.get(0));
                                    for (int i6 = 1; i6 < size2; i6++) {
                                        sb.append(",");
                                        sb.append(intArray.get(i6));
                                    }
                                } else {
                                    i2 = 0;
                                    sb.append("none");
                                }
                                sb.append(" ");
                                String str = "1";
                                sb.append(packageSetting.getPkg().isProfileableByShell() ? "1" : "0");
                                sb.append(" ");
                                sb.append(packageSetting.getPkg().getLongVersionCode());
                                sb.append(" ");
                                if (!packageSetting.getPkg().isProfileable()) {
                                    str = "0";
                                }
                                sb.append(str);
                                sb.append(" ");
                                if (packageSetting.isSystem()) {
                                    sb.append("@system");
                                } else if (packageSetting.isProduct()) {
                                    sb.append("@product");
                                } else if (packageSetting.getInstallSource().mInstallerPackageName != null && !packageSetting.getInstallSource().mInstallerPackageName.isEmpty()) {
                                    sb.append(packageSetting.getInstallSource().mInstallerPackageName);
                                } else {
                                    sb.append("@null");
                                }
                                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                                bufferedWriter2.append((CharSequence) sb);
                                settings = this;
                                i3 = i2;
                            }
                        }
                    }
                    i2 = i3;
                    if (!"android".equals(packageSetting.getPackageName())) {
                        Slog.w("PackageSettings", "Skipping " + packageSetting + " due to missing metadata");
                    }
                    settings = this;
                    i3 = i2;
                }
                bufferedWriter2.flush();
                FileUtils.sync(fileOutputStream);
                bufferedWriter2.close();
                journaledFile.commit();
            } catch (Exception e) {
                e = e;
                bufferedWriter = bufferedWriter2;
                Slog.wtf("PackageSettings", "Failed to write packages.list", e);
                IoUtils.closeQuietly(bufferedWriter);
                journaledFile.rollback();
            }
        } catch (Exception e2) {
            e = e2;
            bufferedWriter = null;
        }
    }

    public void writeDisabledSysPackageLPr(TypedXmlSerializer typedXmlSerializer, PackageSetting packageSetting) {
        typedXmlSerializer.startTag((String) null, "updated-package");
        typedXmlSerializer.attribute((String) null, "name", packageSetting.getPackageName());
        if (packageSetting.getRealName() != null) {
            typedXmlSerializer.attribute((String) null, "realName", packageSetting.getRealName());
        }
        typedXmlSerializer.attribute((String) null, "codePath", packageSetting.getPathString());
        typedXmlSerializer.attributeLongHex((String) null, "ft", packageSetting.getLastModifiedTime());
        typedXmlSerializer.attributeLongHex((String) null, "ut", packageSetting.getLastUpdateTime());
        typedXmlSerializer.attributeLong((String) null, "version", packageSetting.getVersionCode());
        if (packageSetting.getLegacyNativeLibraryPath() != null) {
            typedXmlSerializer.attribute((String) null, "nativeLibraryPath", packageSetting.getLegacyNativeLibraryPath());
        }
        if (packageSetting.getPrimaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((String) null, "primaryCpuAbi", packageSetting.getPrimaryCpuAbiLegacy());
        }
        if (packageSetting.getSecondaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((String) null, "secondaryCpuAbi", packageSetting.getSecondaryCpuAbiLegacy());
        }
        if (packageSetting.getCpuAbiOverride() != null) {
            typedXmlSerializer.attribute((String) null, "cpuAbiOverride", packageSetting.getCpuAbiOverride());
        }
        if (!packageSetting.hasSharedUser()) {
            typedXmlSerializer.attributeInt((String) null, "userId", packageSetting.getAppId());
        } else {
            typedXmlSerializer.attributeInt((String) null, "sharedUserId", packageSetting.getAppId());
        }
        typedXmlSerializer.attributeFloat((String) null, "loadingProgress", packageSetting.getLoadingProgress());
        typedXmlSerializer.attributeLongHex((String) null, "loadingCompletedTime", packageSetting.getLoadingCompletedTime());
        if (packageSetting.getAppMetadataFilePath() != null) {
            typedXmlSerializer.attribute((String) null, "appMetadataFilePath", packageSetting.getAppMetadataFilePath());
        }
        writeUsesSdkLibLPw(typedXmlSerializer, packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor());
        writeUsesStaticLibLPw(typedXmlSerializer, packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions());
        typedXmlSerializer.endTag((String) null, "updated-package");
    }

    public void writePackageLPr(TypedXmlSerializer typedXmlSerializer, PackageSetting packageSetting) {
        typedXmlSerializer.startTag((String) null, "package");
        typedXmlSerializer.attribute((String) null, "name", packageSetting.getPackageName());
        if (packageSetting.getRealName() != null) {
            typedXmlSerializer.attribute((String) null, "realName", packageSetting.getRealName());
        }
        typedXmlSerializer.attribute((String) null, "codePath", packageSetting.getPathString());
        if (packageSetting.getLegacyNativeLibraryPath() != null) {
            typedXmlSerializer.attribute((String) null, "nativeLibraryPath", packageSetting.getLegacyNativeLibraryPath());
        }
        if (packageSetting.getPrimaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((String) null, "primaryCpuAbi", packageSetting.getPrimaryCpuAbiLegacy());
        }
        if (packageSetting.getSecondaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((String) null, "secondaryCpuAbi", packageSetting.getSecondaryCpuAbiLegacy());
        }
        if (packageSetting.getCpuAbiOverride() != null) {
            typedXmlSerializer.attribute((String) null, "cpuAbiOverride", packageSetting.getCpuAbiOverride());
        }
        typedXmlSerializer.attributeInt((String) null, "publicFlags", packageSetting.getFlags());
        typedXmlSerializer.attributeInt((String) null, "privateFlags", packageSetting.getPrivateFlags());
        typedXmlSerializer.attributeLongHex((String) null, "ft", packageSetting.getLastModifiedTime());
        typedXmlSerializer.attributeLongHex((String) null, "ut", packageSetting.getLastUpdateTime());
        typedXmlSerializer.attributeLong((String) null, "version", packageSetting.getVersionCode());
        if (!packageSetting.hasSharedUser()) {
            typedXmlSerializer.attributeInt((String) null, "userId", packageSetting.getAppId());
        } else {
            typedXmlSerializer.attributeInt((String) null, "sharedUserId", packageSetting.getAppId());
        }
        InstallSource installSource = packageSetting.getInstallSource();
        String str = installSource.mInstallerPackageName;
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "installer", str);
        }
        int i = installSource.mInstallerPackageUid;
        if (i != -1) {
            typedXmlSerializer.attributeInt((String) null, "installerUid", i);
        }
        String str2 = installSource.mUpdateOwnerPackageName;
        if (str2 != null) {
            typedXmlSerializer.attribute((String) null, "updateOwner", str2);
        }
        String str3 = installSource.mInstallerAttributionTag;
        if (str3 != null) {
            typedXmlSerializer.attribute((String) null, "installerAttributionTag", str3);
        }
        typedXmlSerializer.attributeInt((String) null, "packageSource", installSource.mPackageSource);
        if (installSource.mIsOrphaned) {
            typedXmlSerializer.attributeBoolean((String) null, "isOrphaned", true);
        }
        String str4 = installSource.mInitiatingPackageName;
        if (str4 != null) {
            typedXmlSerializer.attribute((String) null, "installInitiator", str4);
        }
        if (installSource.mIsInitiatingPackageUninstalled) {
            typedXmlSerializer.attributeBoolean((String) null, "installInitiatorUninstalled", true);
        }
        String str5 = installSource.mOriginatingPackageName;
        if (str5 != null) {
            typedXmlSerializer.attribute((String) null, "installOriginator", str5);
        }
        if (packageSetting.getVolumeUuid() != null) {
            typedXmlSerializer.attribute((String) null, "volumeUuid", packageSetting.getVolumeUuid());
        }
        if (packageSetting.getCategoryOverride() != -1) {
            typedXmlSerializer.attributeInt((String) null, "categoryHint", packageSetting.getCategoryOverride());
        }
        if (packageSetting.isUpdateAvailable()) {
            typedXmlSerializer.attributeBoolean((String) null, "updateAvailable", true);
        }
        if (packageSetting.isForceQueryableOverride()) {
            typedXmlSerializer.attributeBoolean((String) null, "forceQueryable", true);
        }
        if (packageSetting.isLoading()) {
            typedXmlSerializer.attributeBoolean((String) null, "isLoading", true);
        }
        typedXmlSerializer.attributeFloat((String) null, "loadingProgress", packageSetting.getLoadingProgress());
        typedXmlSerializer.attributeLongHex((String) null, "loadingCompletedTime", packageSetting.getLoadingCompletedTime());
        typedXmlSerializer.attribute((String) null, "domainSetId", packageSetting.getDomainSetId().toString());
        if (packageSetting.getAppMetadataFilePath() != null) {
            typedXmlSerializer.attribute((String) null, "appMetadataFilePath", packageSetting.getAppMetadataFilePath());
        }
        writeUsesSdkLibLPw(typedXmlSerializer, packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor());
        writeUsesStaticLibLPw(typedXmlSerializer, packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions());
        packageSetting.getSignatures().writeXml(typedXmlSerializer, "sigs", this.mPastSignatures.untrackedStorage());
        PackageSignatures packageSignatures = installSource.mInitiatingPackageSignatures;
        if (packageSignatures != null) {
            packageSignatures.writeXml(typedXmlSerializer, "install-initiator-sigs", this.mPastSignatures.untrackedStorage());
        }
        writeSigningKeySetLPr(typedXmlSerializer, packageSetting.getKeySetData());
        writeUpgradeKeySetsLPr(typedXmlSerializer, packageSetting.getKeySetData());
        writeKeySetAliasesLPr(typedXmlSerializer, packageSetting.getKeySetData());
        writeMimeGroupLPr(typedXmlSerializer, packageSetting.getMimeGroups());
        typedXmlSerializer.endTag((String) null, "package");
    }

    public void writeSigningKeySetLPr(TypedXmlSerializer typedXmlSerializer, PackageKeySetData packageKeySetData) {
        typedXmlSerializer.startTag((String) null, "proper-signing-keyset");
        typedXmlSerializer.attributeLong((String) null, "identifier", packageKeySetData.getProperSigningKeySet());
        typedXmlSerializer.endTag((String) null, "proper-signing-keyset");
    }

    public void writeUpgradeKeySetsLPr(TypedXmlSerializer typedXmlSerializer, PackageKeySetData packageKeySetData) {
        if (packageKeySetData.isUsingUpgradeKeySets()) {
            for (long j : packageKeySetData.getUpgradeKeySets()) {
                typedXmlSerializer.startTag((String) null, "upgrade-keyset");
                typedXmlSerializer.attributeLong((String) null, "identifier", j);
                typedXmlSerializer.endTag((String) null, "upgrade-keyset");
            }
        }
    }

    public void writeKeySetAliasesLPr(TypedXmlSerializer typedXmlSerializer, PackageKeySetData packageKeySetData) {
        for (Map.Entry entry : packageKeySetData.getAliases().entrySet()) {
            typedXmlSerializer.startTag((String) null, "defined-keyset");
            typedXmlSerializer.attribute((String) null, "alias", (String) entry.getKey());
            typedXmlSerializer.attributeLong((String) null, "identifier", ((Long) entry.getValue()).longValue());
            typedXmlSerializer.endTag((String) null, "defined-keyset");
        }
    }

    public final FileInputStream restorePackages(PackageManagerBackupController packageManagerBackupController) {
        File backupPackagesFile;
        if (packageManagerBackupController == null || packageManagerBackupController.getRebootCntByPackages() >= 3 || (backupPackagesFile = packageManagerBackupController.getBackupPackagesFile()) == null || !backupPackagesFile.exists()) {
            return null;
        }
        PackageManagerService.reportSettingsProblem(4, "Restoring " + backupPackagesFile);
        packageManagerBackupController.incRebootCntByPackages();
        return new FileInputStream(backupPackagesFile);
    }

    public final FileInputStream restorePackagesState(PackageManagerBackupController packageManagerBackupController, int i) {
        File backupPackagesStateFile;
        if (packageManagerBackupController == null || packageManagerBackupController.getRebootCntByPackages() >= 3 || (backupPackagesStateFile = packageManagerBackupController.getBackupPackagesStateFile(i)) == null || !backupPackagesStateFile.exists()) {
            return null;
        }
        PackageManagerService.reportSettingsProblem(4, "Restoring " + backupPackagesStateFile);
        packageManagerBackupController.incRebootCntByPackages();
        return new FileInputStream(backupPackagesStateFile);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean readSettingsLPw(Computer computer, List list, ArrayMap arrayMap, PackageManagerBackupController packageManagerBackupController) {
        FileInputStream openRead;
        int i;
        int next;
        int i2;
        List list2 = list;
        this.mPendingPackages.clear();
        this.mPastSignatures.clear();
        this.mKeySetRefs.clear();
        this.mInstallerPackages.clear();
        arrayMap.clear();
        ResilientAtomicFile settingsFile = getSettingsFile();
        int i3 = 1;
        FileInputStream fileInputStream = null;
        String str = null;
        try {
            try {
                openRead = settingsFile.openRead();
                if (openRead == null) {
                    try {
                        openRead = restorePackages(packageManagerBackupController);
                    } catch (Exception e) {
                        e = e;
                    }
                }
                i = 0;
            } catch (Throwable th) {
                if (settingsFile == null) {
                    throw th;
                }
                try {
                    settingsFile.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (openRead == null) {
            findOrCreateVersion(StorageManager.UUID_PRIVATE_INTERNAL).forceCurrent();
            findOrCreateVersion("primary_physical").forceCurrent();
            settingsFile.close();
            return false;
        }
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
        do {
            next = resolvePullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            this.mReadMessages.append("No start tag found in settings file\n");
            PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager settings");
            Slog.wtf("PackageManager", "No start tag found in package manager settings");
            settingsFile.close();
            return false;
        }
        int depth = resolvePullParser.getDepth();
        while (true) {
            int next2 = resolvePullParser.next();
            if (next2 == i3 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 == 3) {
                list2 = list;
            } else if (next2 != 4) {
                String name = resolvePullParser.getName();
                if (name.equals("package")) {
                    try {
                        readPackageLPw(resolvePullParser, list2, arrayMap);
                    } catch (Exception e3) {
                        e = e3;
                    }
                } else if (name.equals("permissions")) {
                    this.mPermissions.readPermissions(resolvePullParser);
                } else if (name.equals("permission-trees")) {
                    this.mPermissions.readPermissionTrees(resolvePullParser);
                } else if (name.equals("shared-user")) {
                    readSharedUserLPw(resolvePullParser, list2);
                } else if (!name.equals("preferred-packages")) {
                    if (name.equals("preferred-activities")) {
                        readPreferredActivitiesLPw(resolvePullParser, i);
                    } else if (name.equals("persistent-preferred-activities")) {
                        readPersistentPreferredActivitiesLPw(resolvePullParser, i);
                    } else if (name.equals("crossProfile-intent-filters")) {
                        readCrossProfileIntentFiltersLPw(resolvePullParser, i);
                    } else if (name.equals("default-browser")) {
                        readDefaultAppsLPw(resolvePullParser, i);
                    } else if (name.equals("updated-package")) {
                        readDisabledSysPackageLPw(resolvePullParser, list2);
                    } else if (name.equals("renamed-package")) {
                        String attributeValue = resolvePullParser.getAttributeValue(str, "new");
                        String attributeValue2 = resolvePullParser.getAttributeValue(str, "old");
                        if (attributeValue != null && attributeValue2 != null) {
                            this.mRenamedPackages.put(attributeValue, attributeValue2);
                        }
                    } else {
                        if (name.equals("last-platform-version")) {
                            VersionInfo findOrCreateVersion = findOrCreateVersion(StorageManager.UUID_PRIVATE_INTERNAL);
                            VersionInfo findOrCreateVersion2 = findOrCreateVersion("primary_physical");
                            findOrCreateVersion.sdkVersion = resolvePullParser.getAttributeInt((String) null, "internal", 0);
                            findOrCreateVersion2.sdkVersion = resolvePullParser.getAttributeInt((String) null, "external", 0);
                            String readStringAttribute = XmlUtils.readStringAttribute(resolvePullParser, "buildFingerprint");
                            findOrCreateVersion2.buildFingerprint = readStringAttribute;
                            findOrCreateVersion.buildFingerprint = readStringAttribute;
                            String readStringAttribute2 = XmlUtils.readStringAttribute(resolvePullParser, "fingerprint");
                            findOrCreateVersion2.fingerprint = readStringAttribute2;
                            findOrCreateVersion.fingerprint = readStringAttribute2;
                            str = null;
                            i2 = 0;
                        } else {
                            if (name.equals("database-version")) {
                                VersionInfo findOrCreateVersion3 = findOrCreateVersion(StorageManager.UUID_PRIVATE_INTERNAL);
                                VersionInfo findOrCreateVersion4 = findOrCreateVersion("primary_physical");
                                i2 = 0;
                                findOrCreateVersion3.databaseVersion = resolvePullParser.getAttributeInt((String) null, "internal", 0);
                                findOrCreateVersion4.databaseVersion = resolvePullParser.getAttributeInt((String) null, "external", 0);
                            } else {
                                i2 = 0;
                                if (name.equals("verifier")) {
                                    try {
                                        this.mVerifierDeviceIdentity = VerifierDeviceIdentity.parse(resolvePullParser.getAttributeValue((String) null, "device"));
                                    } catch (IllegalArgumentException e4) {
                                        Slog.w("PackageManager", "Discard invalid verifier device id: " + e4.getMessage());
                                    }
                                } else if (!"read-external-storage".equals(name)) {
                                    if (name.equals("keyset-settings")) {
                                        this.mKeySetManagerService.readKeySetsLPw(resolvePullParser, this.mKeySetRefs.untrackedStorage());
                                    } else if ("version".equals(name)) {
                                        VersionInfo findOrCreateVersion5 = findOrCreateVersion(XmlUtils.readStringAttribute(resolvePullParser, "volumeUuid"));
                                        str = null;
                                        findOrCreateVersion5.sdkVersion = resolvePullParser.getAttributeInt((String) null, "sdkVersion");
                                        findOrCreateVersion5.databaseVersion = resolvePullParser.getAttributeInt((String) null, "databaseVersion");
                                        findOrCreateVersion5.buildFingerprint = XmlUtils.readStringAttribute(resolvePullParser, "buildFingerprint");
                                        findOrCreateVersion5.fingerprint = XmlUtils.readStringAttribute(resolvePullParser, "fingerprint");
                                    } else {
                                        str = null;
                                        str = null;
                                        str = null;
                                        if (name.equals("domain-verifications")) {
                                            try {
                                                this.mDomainVerificationManager.readSettings(computer, resolvePullParser);
                                            } catch (Exception e5) {
                                                e = e5;
                                            }
                                        } else if (name.equals("domain-verifications-legacy")) {
                                            this.mDomainVerificationManager.readLegacySettings(resolvePullParser);
                                        } else {
                                            Slog.w("PackageManager", "Unknown element under <packages>: " + resolvePullParser.getName());
                                            XmlUtils.skipCurrentTag(resolvePullParser);
                                        }
                                    }
                                }
                            }
                            str = null;
                        }
                        list2 = list;
                        i = i2;
                        i3 = 1;
                    }
                }
                i2 = i;
                list2 = list;
                i = i2;
                i3 = 1;
            }
            e = e5;
            fileInputStream = openRead;
            if (settingsFile.existsReadableFile()) {
                settingsFile.failRead(fileInputStream, e);
            }
            readSettingsLPw(computer, list, arrayMap, packageManagerBackupController);
            if (settingsFile != null) {
                return true;
            }
            settingsFile.close();
            return true;
        }
        openRead.close();
        if (settingsFile != null) {
        }
    }

    public boolean readLPw(Computer computer, List list, PackageManagerBackupController packageManagerBackupController) {
        ArrayMap arrayMap = new ArrayMap();
        try {
            if (!readSettingsLPw(computer, list, arrayMap, packageManagerBackupController)) {
                return false;
            }
            if (!this.mVersion.containsKey(StorageManager.UUID_PRIVATE_INTERNAL)) {
                Slog.wtf("PackageManager", "No internal VersionInfo found in settings, using current.");
                findOrCreateVersion(StorageManager.UUID_PRIVATE_INTERNAL).forceCurrent();
            }
            if (!this.mVersion.containsKey("primary_physical")) {
                Slog.wtf("PackageManager", "No external VersionInfo found in settings, using current.");
                findOrCreateVersion("primary_physical").forceCurrent();
            }
            int size = this.mPendingPackages.size();
            for (int i = 0; i < size; i++) {
                PackageSetting packageSetting = (PackageSetting) this.mPendingPackages.get(i);
                int sharedUserAppId = packageSetting.getSharedUserAppId();
                if (sharedUserAppId > 0) {
                    SettingBase settingLPr = getSettingLPr(sharedUserAppId);
                    if (settingLPr instanceof SharedUserSetting) {
                        addPackageSettingLPw(packageSetting, (SharedUserSetting) settingLPr);
                    } else if (settingLPr != null) {
                        String str = "Bad package setting: package " + packageSetting.getPackageName() + " has shared uid " + sharedUserAppId + " that is not a shared uid\n";
                        this.mReadMessages.append(str);
                        PackageManagerService.reportSettingsProblem(6, str);
                    } else {
                        String str2 = "Bad package setting: package " + packageSetting.getPackageName() + " has shared uid " + sharedUserAppId + " that is not defined\n";
                        this.mReadMessages.append(str2);
                        PackageManagerService.reportSettingsProblem(6, str2);
                    }
                }
            }
            this.mPendingPackages.clear();
            if (this.mBackupStoppedPackagesFilename.exists() || this.mStoppedPackagesFilename.exists()) {
                readStoppedLPw();
                this.mBackupStoppedPackagesFilename.delete();
                this.mStoppedPackagesFilename.delete();
                writePackageRestrictionsLPr(0, true);
            } else {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    readPackageRestrictionsLPr(((UserInfo) it.next()).id, arrayMap, packageManagerBackupController);
                }
            }
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                UserInfo userInfo = (UserInfo) it2.next();
                this.mRuntimePermissionsPersistence.readStateForUserSync(userInfo.id, getInternalVersion(), this.mPackages, this.mSharedUsers, getUserRuntimePermissionsFile(userInfo.id));
            }
            for (PackageSetting packageSetting2 : this.mDisabledSysPackages.values()) {
                SettingBase settingLPr2 = getSettingLPr(packageSetting2.getAppId());
                if (settingLPr2 instanceof SharedUserSetting) {
                    SharedUserSetting sharedUserSetting = (SharedUserSetting) settingLPr2;
                    sharedUserSetting.mDisabledPackages.add(packageSetting2);
                    packageSetting2.setSharedUserAppId(sharedUserSetting.mAppId);
                }
            }
            StringBuilder sb = this.mReadMessages;
            sb.append("Read completed successfully: ");
            sb.append(this.mPackages.size());
            sb.append(" packages, ");
            sb.append(this.mSharedUsers.size());
            sb.append(" shared uids\n");
            writeKernelMappingLPr();
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                UserInfo userInfo2 = (UserInfo) it3.next();
                String[] strArr = {"com.samsung.android.mtp", "com.android.mtp"};
                for (int i2 = 0; i2 < 2; i2++) {
                    PackageSetting packageSetting3 = (PackageSetting) this.mPackages.get(strArr[i2]);
                    if (packageSetting3 != null && packageSetting3.getStopped(userInfo2.id)) {
                        packageSetting3.setStopped(false, userInfo2.id);
                    }
                }
            }
            return true;
        } finally {
            if (!this.mVersion.containsKey(StorageManager.UUID_PRIVATE_INTERNAL)) {
                Slog.wtf("PackageManager", "No internal VersionInfo found in settings, using current.");
                findOrCreateVersion(StorageManager.UUID_PRIVATE_INTERNAL).forceCurrent();
            }
            if (!this.mVersion.containsKey("primary_physical")) {
                Slog.wtf("PackageManager", "No external VersionInfo found in settings, using current.");
                findOrCreateVersion("primary_physical").forceCurrent();
            }
        }
    }

    public void readPermissionStateForUserSyncLPr(int i) {
        this.mRuntimePermissionsPersistence.readStateForUserSync(i, getInternalVersion(), this.mPackages, this.mSharedUsers, getUserRuntimePermissionsFile(i));
    }

    public void applyDefaultPreferredAppsLPw(int i) {
        int i2;
        int next;
        int i3;
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        for (PackageSetting packageSetting : this.mPackages.values()) {
            if ((1 & packageSetting.getFlags()) != 0 && packageSetting.getPkg() != null && !packageSetting.getPkg().getPreferredActivityFilters().isEmpty()) {
                List preferredActivityFilters = packageSetting.getPkg().getPreferredActivityFilters();
                for (int i4 = 0; i4 < preferredActivityFilters.size(); i4++) {
                    Pair pair = (Pair) preferredActivityFilters.get(i4);
                    applyDefaultPreferredActivityLPw(packageManagerInternal, ((ParsedIntentInfo) pair.second).getIntentFilter(), new ComponentName(packageSetting.getPackageName(), (String) pair.first), i);
                }
            }
        }
        int size = PackageManagerService.SYSTEM_PARTITIONS.size();
        int i5 = 0;
        while (i5 < size) {
            File file = new File(((ScanPartition) PackageManagerService.SYSTEM_PARTITIONS.get(i5)).getFolder(), "etc/preferred-apps");
            if (file.exists() && file.isDirectory()) {
                if (file.canRead()) {
                    File[] listFiles = file.listFiles();
                    if (ArrayUtils.isEmpty(listFiles)) {
                        continue;
                    } else {
                        int length = listFiles.length;
                        int i6 = 0;
                        while (i6 < length) {
                            File file2 = listFiles[i6];
                            if (!file2.getPath().endsWith(".xml")) {
                                Slog.i("PackageSettings", "Non-xml file " + file2 + " in " + file + " directory, ignoring");
                            } else if (file2.canRead()) {
                                try {
                                    FileInputStream fileInputStream = new FileInputStream(file2);
                                    try {
                                        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                                        while (true) {
                                            next = resolvePullParser.next();
                                            i2 = size;
                                            i3 = 2;
                                            if (next == 2) {
                                                break;
                                            }
                                            if (next == 1) {
                                                i3 = 2;
                                                break;
                                            }
                                            size = i2;
                                        }
                                        if (next != i3) {
                                            try {
                                                Slog.w("PackageSettings", "Preferred apps file " + file2 + " does not have start tag");
                                            } catch (Throwable th) {
                                                th = th;
                                                Throwable th2 = th;
                                                try {
                                                    fileInputStream.close();
                                                } catch (Throwable th3) {
                                                    th2.addSuppressed(th3);
                                                }
                                                throw th2;
                                            }
                                        } else if ("preferred-activities".equals(resolvePullParser.getName())) {
                                            readDefaultPreferredActivitiesLPw(resolvePullParser, i);
                                        } else {
                                            Slog.w("PackageSettings", "Preferred apps file " + file2 + " does not start with 'preferred-activities'");
                                        }
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e) {
                                            e = e;
                                            Slog.w("PackageSettings", "Error reading apps file " + file2, e);
                                            i6++;
                                            size = i2;
                                        } catch (XmlPullParserException e2) {
                                            e = e2;
                                            Slog.w("PackageSettings", "Error reading apps file " + file2, e);
                                            i6++;
                                            size = i2;
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        i2 = size;
                                    }
                                } catch (IOException e3) {
                                    e = e3;
                                    i2 = size;
                                } catch (XmlPullParserException e4) {
                                    e = e4;
                                    i2 = size;
                                }
                                i6++;
                                size = i2;
                            } else {
                                Slog.w("PackageSettings", "Preferred apps file " + file2 + " cannot be read");
                            }
                            i2 = size;
                            i6++;
                            size = i2;
                        }
                    }
                } else {
                    Slog.w("PackageSettings", "Directory " + file + " cannot be read");
                }
            }
            i5++;
            size = size;
        }
    }

    public static void removeFilters(PreferredIntentResolver preferredIntentResolver, WatchedIntentFilter watchedIntentFilter, List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            PreferredActivity preferredActivity = (PreferredActivity) list.get(size);
            preferredIntentResolver.removeFilter((WatchedIntentFilter) preferredActivity);
            PreferredActivityLog.logPreferenceChange(preferredActivity, "Removing preference<replace>");
        }
    }

    public final void applyDefaultPreferredActivityLPw(PackageManagerInternal packageManagerInternal, IntentFilter intentFilter, ComponentName componentName, int i) {
        int i2;
        Intent intent = new Intent();
        int i3 = 0;
        intent.setAction(intentFilter.getAction(0));
        int i4 = 786432;
        for (int i5 = 0; i5 < intentFilter.countCategories(); i5++) {
            String category = intentFilter.getCategory(i5);
            if (category.equals("android.intent.category.DEFAULT")) {
                i4 |= 65536;
            } else {
                intent.addCategory(category);
            }
        }
        int countDataSchemes = intentFilter.countDataSchemes();
        int i6 = 0;
        boolean z = false;
        boolean z2 = true;
        while (i6 < countDataSchemes) {
            String dataScheme = intentFilter.getDataScheme(i6);
            if (dataScheme != null && !dataScheme.isEmpty()) {
                z = true;
            }
            int countDataSchemeSpecificParts = intentFilter.countDataSchemeSpecificParts();
            int i7 = i3;
            boolean z3 = true;
            while (i7 < countDataSchemeSpecificParts) {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme(dataScheme);
                PatternMatcher dataSchemeSpecificPart = intentFilter.getDataSchemeSpecificPart(i7);
                builder.opaquePart(dataSchemeSpecificPart.getPath());
                Intent intent2 = new Intent(intent);
                intent2.setData(builder.build());
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent2, i4, componentName, dataScheme, dataSchemeSpecificPart, null, null, i);
                i7++;
                dataScheme = dataScheme;
                countDataSchemeSpecificParts = countDataSchemeSpecificParts;
                i6 = i6;
                z3 = false;
            }
            String str = dataScheme;
            int i8 = i6;
            int countDataAuthorities = intentFilter.countDataAuthorities();
            int i9 = 0;
            while (i9 < countDataAuthorities) {
                IntentFilter.AuthorityEntry dataAuthority = intentFilter.getDataAuthority(i9);
                int countDataPaths = intentFilter.countDataPaths();
                boolean z4 = true;
                int i10 = 0;
                while (i10 < countDataPaths) {
                    Uri.Builder builder2 = new Uri.Builder();
                    builder2.scheme(str);
                    if (dataAuthority.getHost() != null) {
                        builder2.authority(dataAuthority.getHost());
                    }
                    PatternMatcher dataPath = intentFilter.getDataPath(i10);
                    builder2.path(dataPath.getPath());
                    Intent intent3 = new Intent(intent);
                    intent3.setData(builder2.build());
                    applyDefaultPreferredActivityLPw(packageManagerInternal, intent3, i4, componentName, str, null, dataAuthority, dataPath, i);
                    i10++;
                    countDataAuthorities = countDataAuthorities;
                    countDataPaths = countDataPaths;
                    i9 = i9;
                    z3 = false;
                    z4 = false;
                }
                int i11 = i9;
                int i12 = countDataAuthorities;
                if (z4) {
                    Uri.Builder builder3 = new Uri.Builder();
                    builder3.scheme(str);
                    if (dataAuthority.getHost() != null) {
                        builder3.authority(dataAuthority.getHost());
                    }
                    Intent intent4 = new Intent(intent);
                    intent4.setData(builder3.build());
                    applyDefaultPreferredActivityLPw(packageManagerInternal, intent4, i4, componentName, str, null, dataAuthority, null, i);
                    z3 = false;
                }
                i9 = i11 + 1;
                countDataAuthorities = i12;
            }
            if (z3) {
                Uri.Builder builder4 = new Uri.Builder();
                builder4.scheme(str);
                Intent intent5 = new Intent(intent);
                intent5.setData(builder4.build());
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent5, i4, componentName, str, null, null, null, i);
            }
            i6 = i8 + 1;
            i3 = 0;
            z2 = false;
        }
        int i13 = 0;
        while (i13 < intentFilter.countDataTypes()) {
            String dataType = intentFilter.getDataType(i13);
            if (z) {
                Uri.Builder builder5 = new Uri.Builder();
                int i14 = 0;
                while (i14 < intentFilter.countDataSchemes()) {
                    String dataScheme2 = intentFilter.getDataScheme(i14);
                    if (dataScheme2 == null || dataScheme2.isEmpty()) {
                        i2 = i14;
                    } else {
                        Intent intent6 = new Intent(intent);
                        builder5.scheme(dataScheme2);
                        intent6.setDataAndType(builder5.build(), dataType);
                        i2 = i14;
                        applyDefaultPreferredActivityLPw(packageManagerInternal, intent6, i4, componentName, dataScheme2, null, null, null, i);
                    }
                    i14 = i2 + 1;
                }
            } else {
                Intent intent7 = new Intent(intent);
                intent7.setType(dataType);
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent7, i4, componentName, null, null, null, null, i);
            }
            i13++;
            z2 = false;
        }
        if (z2) {
            applyDefaultPreferredActivityLPw(packageManagerInternal, intent, i4, componentName, null, null, null, null, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0151  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyDefaultPreferredActivityLPw(PackageManagerInternal packageManagerInternal, Intent intent, int i, ComponentName componentName, String str, PatternMatcher patternMatcher, IntentFilter.AuthorityEntry authorityEntry, PatternMatcher patternMatcher2, int i2) {
        Settings settings;
        ArrayList findFilters;
        boolean z;
        List queryIntentActivities = packageManagerInternal.queryIntentActivities(intent, intent.getType(), i, Binder.getCallingUid(), i2);
        int size = queryIntentActivities == null ? 0 : queryIntentActivities.size();
        if (size < 1) {
            Slog.w("PackageSettings", "No potential matches found for " + intent + " while setting preferred " + componentName.flattenToShortString());
            return;
        }
        int size2 = queryIntentActivities.size();
        ComponentName[] componentNameArr = new ComponentName[size2];
        int i3 = 0;
        int i4 = 0;
        ComponentName componentName2 = null;
        boolean z2 = false;
        while (i3 < size) {
            ActivityInfo activityInfo = ((ResolveInfo) queryIntentActivities.get(i3)).activityInfo;
            int i5 = size;
            componentNameArr[i3] = new ComponentName(activityInfo.packageName, activityInfo.name);
            if (componentName.getPackageName().equals(activityInfo.packageName) && componentName.getClassName().equals(activityInfo.name)) {
                i4 = ((ResolveInfo) queryIntentActivities.get(i3)).match;
                z = true;
                z2 = true;
            } else {
                z = true;
                if ((activityInfo.applicationInfo.flags & 1) == 0 && ((ResolveInfo) queryIntentActivities.get(i3)).match >= 0) {
                    componentName2 = componentNameArr[i3];
                }
            }
            i3++;
            size = i5;
        }
        if (componentName2 != null && i4 > 0) {
            componentName2 = null;
        }
        if (!z2 || componentName2 != null) {
            if (componentName2 == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("No component ");
                sb.append(componentName.flattenToShortString());
                sb.append(" found setting preferred ");
                sb.append(intent);
                sb.append("; possible matches are ");
                for (int i6 = 0; i6 < size2; i6++) {
                    if (i6 > 0) {
                        sb.append(", ");
                    }
                    sb.append(componentNameArr[i6].flattenToShortString());
                }
                Slog.w("PackageSettings", sb.toString());
                return;
            }
            Slog.i("PackageSettings", "Not setting preferred " + intent + "; found third party match " + componentName2.flattenToShortString());
            return;
        }
        WatchedIntentFilter watchedIntentFilter = new WatchedIntentFilter();
        if (intent.getAction() != null) {
            watchedIntentFilter.addAction(intent.getAction());
        }
        if (intent.getCategories() != null) {
            Iterator<String> it = intent.getCategories().iterator();
            while (it.hasNext()) {
                watchedIntentFilter.addCategory(it.next());
            }
        }
        if ((65536 & i) != 0) {
            watchedIntentFilter.addCategory("android.intent.category.DEFAULT");
        }
        if (str != null) {
            watchedIntentFilter.addDataScheme(str);
        }
        if (patternMatcher != null) {
            watchedIntentFilter.addDataSchemeSpecificPart(patternMatcher.getPath(), patternMatcher.getType());
        }
        if (authorityEntry != null) {
            watchedIntentFilter.addDataAuthority(authorityEntry);
        }
        if (patternMatcher2 != null) {
            watchedIntentFilter.addDataPath(patternMatcher2);
        }
        if (intent.getType() != null) {
            try {
                watchedIntentFilter.addDataType(intent.getType());
                settings = this;
            } catch (IntentFilter.MalformedMimeTypeException unused) {
                Slog.w("PackageSettings", "Malformed mimetype " + intent.getType() + " for " + componentName);
            }
            PreferredIntentResolver editPreferredActivitiesLPw = settings.editPreferredActivitiesLPw(i2);
            findFilters = editPreferredActivitiesLPw.findFilters(watchedIntentFilter);
            if (findFilters != null) {
                removeFilters(editPreferredActivitiesLPw, watchedIntentFilter, findFilters);
            }
            editPreferredActivitiesLPw.addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) new PreferredActivity(watchedIntentFilter, i4, componentNameArr, componentName, true));
        }
        settings = this;
        PreferredIntentResolver editPreferredActivitiesLPw2 = settings.editPreferredActivitiesLPw(i2);
        findFilters = editPreferredActivitiesLPw2.findFilters(watchedIntentFilter);
        if (findFilters != null) {
        }
        editPreferredActivitiesLPw2.addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) new PreferredActivity(watchedIntentFilter, i4, componentNameArr, componentName, true));
    }

    public final void readDefaultPreferredActivitiesLPw(TypedXmlPullParser typedXmlPullParser, int i) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    PreferredActivity preferredActivity = new PreferredActivity(typedXmlPullParser);
                    if (preferredActivity.mPref.getParseError() == null) {
                        applyDefaultPreferredActivityLPw(packageManagerInternal, preferredActivity.getIntentFilter(), preferredActivity.mPref.mComponent, i);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <preferred-activity> " + preferredActivity.mPref.getParseError() + " at " + typedXmlPullParser.getPositionDescription());
                    }
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under <preferred-activities>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readDisabledSysPackageLPw(TypedXmlPullParser typedXmlPullParser, List list) {
        LegacyPermissionState legacyPermissionState;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "realName");
        String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "codePath");
        String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "requiredCpuAbi");
        String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "nativeLibraryPath");
        String attributeValue6 = typedXmlPullParser.getAttributeValue((String) null, "primaryCpuAbi");
        String attributeValue7 = typedXmlPullParser.getAttributeValue((String) null, "secondaryCpuAbi");
        String attributeValue8 = typedXmlPullParser.getAttributeValue((String) null, "cpuAbiOverride");
        PackageSetting packageSetting = new PackageSetting(attributeValue, attributeValue2, new File(attributeValue3), attributeValue5, (attributeValue6 != null || attributeValue4 == null) ? attributeValue6 : attributeValue4, attributeValue7, attributeValue8, typedXmlPullParser.getAttributeLong((String) null, "version", 0L), 1, attributeValue3.contains("/priv-app/") ? 8 : 0, 0, null, null, null, null, null, DomainVerificationManagerInternal.DISABLED_ID);
        long attributeLongHex = typedXmlPullParser.getAttributeLongHex((String) null, "ft", 0L);
        if (attributeLongHex == 0) {
            attributeLongHex = typedXmlPullParser.getAttributeLong((String) null, "ts", 0L);
        }
        packageSetting.setLastModifiedTime(attributeLongHex);
        packageSetting.setLastUpdateTime(typedXmlPullParser.getAttributeLongHex((String) null, "ut", 0L));
        packageSetting.setAppId(parseAppId(typedXmlPullParser));
        if (packageSetting.getAppId() <= 0) {
            int parseSharedUserAppId = parseSharedUserAppId(typedXmlPullParser);
            packageSetting.setAppId(parseSharedUserAppId);
            packageSetting.setSharedUserAppId(parseSharedUserAppId);
        }
        packageSetting.setAppMetadataFilePath(typedXmlPullParser.getAttributeValue((String) null, "appMetadataFilePath"));
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3) {
                if (next != 4) {
                    if (typedXmlPullParser.getName().equals("perms")) {
                        if (packageSetting.hasSharedUser()) {
                            SettingBase settingLPr = getSettingLPr(packageSetting.getSharedUserAppId());
                            legacyPermissionState = settingLPr != null ? settingLPr.getLegacyPermissionState() : null;
                        } else {
                            legacyPermissionState = packageSetting.getLegacyPermissionState();
                        }
                        if (legacyPermissionState != null) {
                            readInstallPermissionsLPr(typedXmlPullParser, legacyPermissionState, list);
                        }
                    } else if (typedXmlPullParser.getName().equals("uses-static-lib")) {
                        readUsesStaticLibLPw(typedXmlPullParser, packageSetting);
                    } else if (typedXmlPullParser.getName().equals("uses-sdk-lib")) {
                        readUsesSdkLibLPw(typedXmlPullParser, packageSetting);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, "Unknown element under <updated-package>: " + typedXmlPullParser.getName());
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            }
        }
        this.mDisabledSysPackages.put(attributeValue, packageSetting);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 10 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 8 */
    public final void readPackageLPw(com.android.modules.utils.TypedXmlPullParser r73, java.util.List r74, android.util.ArrayMap r75) {
        /*
            Method dump skipped, instructions count: 2329
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.readPackageLPw(com.android.modules.utils.TypedXmlPullParser, java.util.List, android.util.ArrayMap):void");
    }

    public static int parseAppId(TypedXmlPullParser typedXmlPullParser) {
        return typedXmlPullParser.getAttributeInt((String) null, "userId", 0);
    }

    public static int parseSharedUserAppId(TypedXmlPullParser typedXmlPullParser) {
        return typedXmlPullParser.getAttributeInt((String) null, "sharedUserId", 0);
    }

    public void addInstallerPackageNames(InstallSource installSource) {
        String str = installSource.mInstallerPackageName;
        if (str != null) {
            this.mInstallerPackages.add(str);
        }
        String str2 = installSource.mInitiatingPackageName;
        if (str2 != null) {
            this.mInstallerPackages.add(str2);
        }
        String str3 = installSource.mOriginatingPackageName;
        if (str3 != null) {
            this.mInstallerPackages.add(str3);
        }
    }

    public final Pair readMimeGroupLPw(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        if (attributeValue == null) {
            XmlUtils.skipCurrentTag(typedXmlPullParser);
            return null;
        }
        ArraySet arraySet = new ArraySet();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("mime-type")) {
                    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "value");
                    if (attributeValue2 != null) {
                        arraySet.add(attributeValue2);
                    }
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under <mime-group>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        return Pair.create(attributeValue, arraySet);
    }

    public final void writeMimeGroupLPr(TypedXmlSerializer typedXmlSerializer, Map map) {
        if (map == null) {
            return;
        }
        for (String str : map.keySet()) {
            typedXmlSerializer.startTag((String) null, "mime-group");
            typedXmlSerializer.attribute((String) null, "name", str);
            for (String str2 : (Set) map.get(str)) {
                typedXmlSerializer.startTag((String) null, "mime-type");
                typedXmlSerializer.attribute((String) null, "value", str2);
                typedXmlSerializer.endTag((String) null, "mime-type");
            }
            typedXmlSerializer.endTag((String) null, "mime-group");
        }
    }

    public final void readDisabledComponentsLPw(PackageSetting packageSetting, TypedXmlPullParser typedXmlPullParser, int i) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                    if (attributeValue != null) {
                        packageSetting.addDisabledComponent(attributeValue.intern(), i);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <disabled-components> has no name at " + typedXmlPullParser.getPositionDescription());
                    }
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under <disabled-components>: " + typedXmlPullParser.getName());
                }
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public final void readEnabledComponentsLPw(PackageSetting packageSetting, TypedXmlPullParser typedXmlPullParser, int i) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                    if (attributeValue != null) {
                        packageSetting.addEnabledComponent(attributeValue.intern(), i);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <enabled-components> has no name at " + typedXmlPullParser.getPositionDescription());
                    }
                } else {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element under <enabled-components>: " + typedXmlPullParser.getName());
                }
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public final void readSharedUserLPw(TypedXmlPullParser typedXmlPullParser, List list) {
        SharedUserSetting sharedUserSetting = null;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        int parseAppId = parseAppId(typedXmlPullParser);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "system", false);
        if (attributeValue == null) {
            PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <shared-user> has no name at " + typedXmlPullParser.getPositionDescription());
        } else if (parseAppId == 0) {
            PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: shared-user " + attributeValue + " has bad appId " + parseAppId + " at " + typedXmlPullParser.getPositionDescription());
        } else {
            sharedUserSetting = addSharedUserLPw(attributeValue.intern(), parseAppId, attributeBoolean ? 1 : 0, 0);
            if (sharedUserSetting == null) {
                PackageManagerService.reportSettingsProblem(6, "Occurred while parsing settings at " + typedXmlPullParser.getPositionDescription());
            }
        }
        if (sharedUserSetting != null) {
            int depth = typedXmlPullParser.getDepth();
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1) {
                    return;
                }
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    String name = typedXmlPullParser.getName();
                    if (name.equals("sigs")) {
                        sharedUserSetting.signatures.readXml(typedXmlPullParser, this.mPastSignatures.untrackedStorage());
                    } else if (name.equals("perms")) {
                        readInstallPermissionsLPr(typedXmlPullParser, sharedUserSetting.getLegacyPermissionState(), list);
                    } else {
                        PackageManagerService.reportSettingsProblem(5, "Unknown element under <shared-user>: " + typedXmlPullParser.getName());
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            }
        } else {
            XmlUtils.skipCurrentTag(typedXmlPullParser);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00d5 A[Catch: all -> 0x01a7, TryCatch #1 {all -> 0x01a7, blocks: (B:7:0x0035, B:9:0x004f, B:15:0x006a, B:17:0x0081, B:20:0x008f, B:26:0x00a4, B:29:0x00b1, B:31:0x00b8, B:33:0x00be, B:35:0x00c4, B:39:0x00d5, B:42:0x0107, B:46:0x0111, B:48:0x0116, B:51:0x011e, B:54:0x0133), top: B:6:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x010c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0116 A[Catch: all -> 0x01a7, TryCatch #1 {all -> 0x01a7, blocks: (B:7:0x0035, B:9:0x004f, B:15:0x006a, B:17:0x0081, B:20:0x008f, B:26:0x00a4, B:29:0x00b1, B:31:0x00b8, B:33:0x00be, B:35:0x00c4, B:39:0x00d5, B:42:0x0107, B:46:0x0111, B:48:0x0116, B:51:0x011e, B:54:0x0133), top: B:6:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0166 A[Catch: all -> 0x01ac, TryCatch #3 {all -> 0x01ac, blocks: (B:14:0x0172, B:57:0x015e, B:60:0x01aa, B:64:0x0166, B:76:0x0181), top: B:56:0x015e }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0103  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void createNewUserLI(PackageManagerService packageManagerService, Installer installer, int i, Set set, String[] strArr) {
        PackageManagerTracedLock packageManagerTracedLock;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i2;
        int i3;
        ArrayList arrayList;
        int i4;
        PackageManagerService packageManagerService2 = packageManagerService;
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog("PackageSettingsTiming", 262144L);
        timingsTraceAndSlog.traceBegin("createNewUser-" + i);
        Installer.Batch batch = new Installer.Batch();
        boolean z6 = set == null;
        PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
        synchronized (packageManagerTracedLock2) {
            try {
                int findCurrentGuestUserId = MultiUserInstallPolicy.findCurrentGuestUserId();
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(new Integer(i));
                int size = this.mPackages.size();
                int i5 = 0;
                while (i5 < size) {
                    PackageSetting packageSetting = (PackageSetting) this.mPackages.valueAt(i5);
                    if (packageSetting.getPkg() != null) {
                        packageManagerService2.mCustomInjector.getMumUserInstallPolicy().applyInstallPolicyPackageInternalLPw(packageSetting, arrayList2, findCurrentGuestUserId);
                        boolean installed = packageSetting.getInstalled(i);
                        boolean isDualAppId = SemDualAppManager.isDualAppId(i);
                        if (packageSetting.isSystem() && !ArrayUtils.contains(strArr, packageSetting.getPackageName()) && installed && !packageSetting.getPkgState().isHiddenUntilInstalled() && !isDualAppId) {
                            z = true;
                            z2 = !z && (z6 || set.contains(packageSetting.getPackageName()));
                            packageSetting.setInstalled(z2, i);
                            z3 = (packageManagerService2.mShouldStopSystemPackagesByDefault || !packageSetting.isSystem() || packageSetting.isApex() || packageManagerService2.mInitialNonStoppedSystemPackages.contains(packageSetting.getPackageName())) ? false : true;
                            if (z3) {
                                z4 = z3;
                            } else {
                                z4 = z3;
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.addCategory("android.intent.category.LAUNCHER");
                                intent.setPackage(packageSetting.getPackageName());
                                if (packageManagerService.snapshotComputer().queryIntentActivitiesInternal(intent, null, 786432L, 0).isEmpty()) {
                                    z5 = false;
                                    packageSetting.setStopped(z5, i);
                                    packageSetting.setUninstallReason((z || z2) ? 0 : 1, i);
                                    if (z2) {
                                        if (packageSetting.getAppId() >= 0) {
                                            i2 = i5;
                                            i3 = size;
                                            arrayList = arrayList2;
                                            i4 = findCurrentGuestUserId;
                                            packageManagerTracedLock = packageManagerTracedLock2;
                                            try {
                                                batch.createAppData(Installer.buildCreateAppDataArgs(packageSetting.getVolumeUuid(), packageSetting.getPackageName(), i, 1, packageSetting.getAppId(), packageSetting.getSeInfo(), packageSetting.getPkg().getTargetSdkVersion(), !packageSetting.getPkg().getUsesSdkLibraries().isEmpty()));
                                            } catch (Throwable th) {
                                                th = th;
                                                throw th;
                                            }
                                        }
                                    } else {
                                        i2 = i5;
                                        i3 = size;
                                        arrayList = arrayList2;
                                        i4 = findCurrentGuestUserId;
                                        packageManagerTracedLock = packageManagerTracedLock2;
                                        writeKernelMappingLPr(packageSetting);
                                    }
                                    i5 = i2 + 1;
                                    findCurrentGuestUserId = i4;
                                    size = i3;
                                    arrayList2 = arrayList;
                                    packageManagerTracedLock2 = packageManagerTracedLock;
                                    packageManagerService2 = packageManagerService;
                                }
                            }
                            z5 = z4;
                            packageSetting.setStopped(z5, i);
                            packageSetting.setUninstallReason((z || z2) ? 0 : 1, i);
                            if (z2) {
                            }
                            i5 = i2 + 1;
                            findCurrentGuestUserId = i4;
                            size = i3;
                            arrayList2 = arrayList;
                            packageManagerTracedLock2 = packageManagerTracedLock;
                            packageManagerService2 = packageManagerService;
                        }
                        z = false;
                        if (z) {
                        }
                        packageSetting.setInstalled(z2, i);
                        if (packageManagerService2.mShouldStopSystemPackagesByDefault) {
                        }
                        if (z3) {
                        }
                        z5 = z4;
                        packageSetting.setStopped(z5, i);
                        packageSetting.setUninstallReason((z || z2) ? 0 : 1, i);
                        if (z2) {
                        }
                        i5 = i2 + 1;
                        findCurrentGuestUserId = i4;
                        size = i3;
                        arrayList2 = arrayList;
                        packageManagerTracedLock2 = packageManagerTracedLock;
                        packageManagerService2 = packageManagerService;
                    }
                    i2 = i5;
                    i3 = size;
                    arrayList = arrayList2;
                    i4 = findCurrentGuestUserId;
                    packageManagerTracedLock = packageManagerTracedLock2;
                    i5 = i2 + 1;
                    findCurrentGuestUserId = i4;
                    size = i3;
                    arrayList2 = arrayList;
                    packageManagerTracedLock2 = packageManagerTracedLock;
                    packageManagerService2 = packageManagerService;
                }
                timingsTraceAndSlog.traceBegin("createAppData");
                try {
                    batch.execute(installer);
                } catch (Installer.InstallerException e) {
                    Slog.w("PackageSettings", "Failed to prepare app data", e);
                }
                timingsTraceAndSlog.traceEnd();
                synchronized (this.mLock) {
                    applyDefaultPreferredAppsLPw(i);
                }
                timingsTraceAndSlog.traceEnd();
            } catch (Throwable th2) {
                th = th2;
                packageManagerTracedLock = packageManagerTracedLock2;
            }
        }
    }

    public void removeUserLPw(int i) {
        Iterator it = this.mPackages.entrySet().iterator();
        while (it.hasNext()) {
            ((PackageSetting) ((Map.Entry) it.next()).getValue()).removeUser(i);
        }
        this.mPreferredActivities.remove(i);
        synchronized (this.mPackageRestrictionsLock) {
            getUserPackagesStateFile(i).delete();
            this.mPendingAsyncPackageRestrictionsWrites.delete(i);
        }
        removeCrossProfileIntentFiltersLPw(i);
        this.mRuntimePermissionsPersistence.onUserRemoved(i);
        this.mDomainVerificationManager.clearUser(i);
        writePackageListLPr();
        writeKernelRemoveUserLPr(i);
    }

    public void removeCrossProfileIntentFiltersLPw(int i) {
        synchronized (this.mCrossProfileIntentResolvers) {
            if (this.mCrossProfileIntentResolvers.get(i) != null) {
                this.mCrossProfileIntentResolvers.remove(i);
                writePackageRestrictionsLPr(i);
            }
            int size = this.mCrossProfileIntentResolvers.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = this.mCrossProfileIntentResolvers.keyAt(i2);
                CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) this.mCrossProfileIntentResolvers.get(keyAt);
                Iterator it = new ArraySet(crossProfileIntentResolver.filterSet()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) it.next();
                    if (crossProfileIntentFilter.getTargetUserId() == i) {
                        crossProfileIntentResolver.removeFilter((WatchedIntentFilter) crossProfileIntentFilter);
                        z = true;
                    }
                }
                if (z) {
                    writePackageRestrictionsLPr(keyAt);
                }
            }
        }
    }

    public VerifierDeviceIdentity getVerifierDeviceIdentityLPw(Computer computer) {
        if (this.mVerifierDeviceIdentity == null) {
            this.mVerifierDeviceIdentity = VerifierDeviceIdentity.generate();
            writeLPr(computer, false);
        }
        return this.mVerifierDeviceIdentity;
    }

    public PackageSetting getDisabledSystemPkgLPr(String str) {
        return (PackageSetting) this.mDisabledSysPackages.get(str);
    }

    public PackageSetting getDisabledSystemPkgLPr(PackageSetting packageSetting) {
        if (packageSetting == null) {
            return null;
        }
        return getDisabledSystemPkgLPr(packageSetting.getPackageName());
    }

    public int getApplicationEnabledSettingLPr(String str, int i) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
        if (packageSetting == null) {
            throw new PackageManager.NameNotFoundException(str);
        }
        return packageSetting.getEnabled(i);
    }

    public int getComponentEnabledSettingLPr(ComponentName componentName, int i) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.get(componentName.getPackageName());
        if (packageSetting == null) {
            throw new PackageManager.NameNotFoundException(componentName.getPackageName());
        }
        return packageSetting.getCurrentEnabledStateLPr(componentName.getClassName(), i);
    }

    public SharedUserSetting getSharedUserSettingLPr(String str) {
        return getSharedUserSettingLPr((PackageSetting) this.mPackages.get(str));
    }

    public SharedUserSetting getSharedUserSettingLPr(PackageSetting packageSetting) {
        if (packageSetting == null || !packageSetting.hasSharedUser()) {
            return null;
        }
        return (SharedUserSetting) getSettingLPr(packageSetting.getSharedUserAppId());
    }

    public static List getAllUsers(UserManagerService userManagerService) {
        return getUsers(userManagerService, false, false);
    }

    public static List getActiveUsers(UserManagerService userManagerService, boolean z) {
        return getUsers(userManagerService, z, true);
    }

    public static List getUsers(UserManagerService userManagerService, boolean z, boolean z2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List users = userManagerService.getUsers(true, z, z2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return users;
        } catch (NullPointerException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public List getVolumePackagesLPr(String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPackages.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) this.mPackages.valueAt(i);
            if (Objects.equals(str, packageSetting.getVolumeUuid())) {
                arrayList.add(packageSetting);
            }
        }
        return arrayList;
    }

    public static void printFlags(PrintWriter printWriter, int i, Object[] objArr) {
        printWriter.print("[ ");
        for (int i2 = 0; i2 < objArr.length; i2 += 2) {
            if ((((Integer) objArr[i2]).intValue() & i) != 0) {
                printWriter.print(objArr[i2 + 1]);
                printWriter.print(" ");
            }
        }
        printWriter.print("]");
    }

    static {
        Integer valueOf = Integer.valueOf(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES);
        FLAG_DUMP_SPEC = new Object[]{1, "SYSTEM", 2, "DEBUGGABLE", 4, "HAS_CODE", 8, "PERSISTENT", 16, "FACTORY_TEST", 32, "ALLOW_TASK_REPARENTING", 64, "ALLOW_CLEAR_USER_DATA", 128, "UPDATED_SYSTEM_APP", 256, "TEST_ONLY", 16384, "VM_SAFE_MODE", 32768, "ALLOW_BACKUP", 65536, "KILL_AFTER_RESTORE", valueOf, "RESTORE_ANY_VERSION", 262144, "EXTERNAL_STORAGE", 1048576, "LARGE_HEAP"};
        PRIVATE_FLAG_DUMP_SPEC = new Object[]{1024, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE", Integer.valueOf(IInstalld.FLAG_USE_QUOTA), "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION", Integer.valueOf(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES), "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE", 134217728, "ALLOW_AUDIO_PLAYBACK_CAPTURE", 536870912, "PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE", Integer.valueOf(IInstalld.FLAG_FORCE), "BACKUP_IN_FOREGROUND", 2, "CANT_SAVE_STATE", 32, "DEFAULT_TO_DEVICE_PROTECTED_STORAGE", 64, "DIRECT_BOOT_AWARE", 16, "HAS_DOMAIN_URLS", 1, "HIDDEN", 128, "EPHEMERAL", 32768, "ISOLATED_SPLIT_LOADING", valueOf, "OEM", 256, "PARTIALLY_DIRECT_BOOT_AWARE", 8, "PRIVILEGED", 512, "REQUIRED_FOR_SYSTEM_USER", 16384, "STATIC_SHARED_LIBRARY", 262144, "VENDOR", 524288, "PRODUCT", 2097152, "SYSTEM_EXT", 65536, "VIRTUAL_PRELOAD", 1073741824, "ODM", Integer.MIN_VALUE, "PRIVATE_FLAG_ALLOW_NATIVE_HEAP_POINTER_TAGGING"};
    }

    public void dumpVersionLPr(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mVersion.size(); i++) {
            String str = (String) this.mVersion.keyAt(i);
            VersionInfo versionInfo = (VersionInfo) this.mVersion.valueAt(i);
            if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str)) {
                indentingPrintWriter.println("Internal:");
            } else if ("primary_physical".equals(str)) {
                indentingPrintWriter.println("External:");
            } else {
                indentingPrintWriter.println("UUID " + str + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
            }
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.printPair("sdkVersion", Integer.valueOf(versionInfo.sdkVersion));
            indentingPrintWriter.printPair("databaseVersion", Integer.valueOf(versionInfo.databaseVersion));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("buildFingerprint", versionInfo.buildFingerprint);
            indentingPrintWriter.printPair("fingerprint", versionInfo.fingerprint);
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    @NeverCompile
    public void dumpPackageLPr(PrintWriter printWriter, String str, String str2, ArraySet arraySet, PackageSetting packageSetting, LegacyPermissionState legacyPermissionState, SimpleDateFormat simpleDateFormat, Date date, List list, boolean z, boolean z2) {
        boolean z3;
        AndroidPackageInternal pkg = packageSetting.getPkg();
        if (str2 != null) {
            printWriter.print(str2);
            printWriter.print(",");
            printWriter.print(packageSetting.getRealName() != null ? packageSetting.getRealName() : packageSetting.getPackageName());
            printWriter.print(",");
            printWriter.print(packageSetting.getAppId());
            printWriter.print(",");
            printWriter.print(packageSetting.getVersionCode());
            printWriter.print(",");
            printWriter.print(packageSetting.getLastUpdateTime());
            printWriter.print(",");
            printWriter.print(packageSetting.getInstallSource().mInstallerPackageName != null ? packageSetting.getInstallSource().mInstallerPackageName : "?");
            printWriter.print(packageSetting.getInstallSource().mInstallerPackageUid);
            printWriter.print(packageSetting.getInstallSource().mUpdateOwnerPackageName != null ? packageSetting.getInstallSource().mUpdateOwnerPackageName : "?");
            printWriter.print(packageSetting.getInstallSource().mInstallerAttributionTag != null ? "(" + packageSetting.getInstallSource().mInstallerAttributionTag + ")" : "");
            printWriter.print(",");
            printWriter.print(packageSetting.getInstallSource().mPackageSource);
            printWriter.println();
            if (pkg != null) {
                printWriter.print(str2);
                printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                printWriter.print("splt,");
                printWriter.print("base,");
                printWriter.println(pkg.getBaseRevisionCode());
                int[] splitRevisionCodes = pkg.getSplitRevisionCodes();
                for (int i = 0; i < pkg.getSplitNames().length; i++) {
                    printWriter.print(str2);
                    printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                    printWriter.print("splt,");
                    printWriter.print(pkg.getSplitNames()[i]);
                    printWriter.print(",");
                    printWriter.println(splitRevisionCodes[i]);
                }
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                UserInfo userInfo = (UserInfo) it.next();
                PackageUserStateInternal userStateOrDefault = packageSetting.getUserStateOrDefault(userInfo.id);
                printWriter.print(str2);
                printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                printWriter.print("usr");
                printWriter.print(",");
                printWriter.print(userInfo.id);
                printWriter.print(",");
                printWriter.print(userStateOrDefault.isInstalled() ? "I" : "i");
                printWriter.print(userStateOrDefault.isHidden() ? "B" : "b");
                printWriter.print(userStateOrDefault.isSuspended() ? "SU" : "su");
                printWriter.print(userStateOrDefault.isStopped() ? "S" : "s");
                printWriter.print(userStateOrDefault.isNotLaunched() ? "l" : "L");
                printWriter.print(userStateOrDefault.isInstantApp() ? "IA" : "ia");
                printWriter.print(userStateOrDefault.isVirtualPreload() ? "VPI" : "vpi");
                printWriter.print(userStateOrDefault.getHarmfulAppWarning() != null ? "HA" : "ha");
                printWriter.print(",");
                printWriter.print(userStateOrDefault.getEnabledState());
                String lastDisableAppCaller = userStateOrDefault.getLastDisableAppCaller();
                printWriter.print(",");
                if (lastDisableAppCaller == null) {
                    lastDisableAppCaller = "?";
                }
                printWriter.print(lastDisableAppCaller);
                printWriter.print(",");
                printWriter.print(packageSetting.readUserState(userInfo.id).getFirstInstallTimeMillis());
                printWriter.print(",");
                printWriter.println();
            }
            return;
        }
        printWriter.print(str);
        printWriter.print("Package [");
        printWriter.print(packageSetting.getRealName() != null ? packageSetting.getRealName() : packageSetting.getPackageName());
        printWriter.print("] (");
        printWriter.print(Integer.toHexString(System.identityHashCode(packageSetting)));
        printWriter.println("):");
        if (packageSetting.getRealName() != null) {
            printWriter.print(str);
            printWriter.print("  compat name=");
            printWriter.println(packageSetting.getPackageName());
        }
        printWriter.print(str);
        printWriter.print("  appId=");
        printWriter.println(packageSetting.getAppId());
        SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            printWriter.print(str);
            printWriter.print("  sharedUser=");
            printWriter.println(sharedUserSettingLPr);
        }
        printWriter.print(str);
        printWriter.print("  pkg=");
        printWriter.println(pkg);
        printWriter.print(str);
        printWriter.print("  codePath=");
        printWriter.println(packageSetting.getPathString());
        if (arraySet == null) {
            printWriter.print(str);
            printWriter.print("  resourcePath=");
            printWriter.println(packageSetting.getPathString());
            printWriter.print(str);
            printWriter.print("  legacyNativeLibraryDir=");
            printWriter.println(packageSetting.getLegacyNativeLibraryPath());
            printWriter.print(str);
            printWriter.print("  extractNativeLibs=");
            printWriter.println((packageSetting.getFlags() & 268435456) != 0 ? "true" : "false");
            printWriter.print(str);
            printWriter.print("  primaryCpuAbi=");
            printWriter.println(packageSetting.getPrimaryCpuAbiLegacy());
            printWriter.print(str);
            printWriter.print("  secondaryCpuAbi=");
            printWriter.println(packageSetting.getSecondaryCpuAbiLegacy());
            printWriter.print(str);
            printWriter.print("  cpuAbiOverride=");
            printWriter.println(packageSetting.getCpuAbiOverride());
        }
        printWriter.print(str);
        printWriter.print("  versionCode=");
        printWriter.print(packageSetting.getVersionCode());
        if (pkg != null) {
            printWriter.print(" minSdk=");
            printWriter.print(pkg.getMinSdkVersion());
            printWriter.print(" targetSdk=");
            printWriter.println(pkg.getTargetSdkVersion());
            SparseIntArray minExtensionVersions = pkg.getMinExtensionVersions();
            printWriter.print(str);
            printWriter.print("  minExtensionVersions=[");
            if (minExtensionVersions != null) {
                ArrayList arrayList = new ArrayList();
                int size = minExtensionVersions.size();
                int i2 = 0;
                while (i2 < size) {
                    arrayList.add(minExtensionVersions.keyAt(i2) + "=" + minExtensionVersions.valueAt(i2));
                    i2++;
                    minExtensionVersions = minExtensionVersions;
                }
                printWriter.print(TextUtils.join(", ", arrayList));
            }
            printWriter.print("]");
        }
        printWriter.println();
        if (pkg != null) {
            printWriter.print(str);
            printWriter.print("  versionName=");
            printWriter.println(pkg.getVersionName());
            printWriter.print(str);
            printWriter.print("  usesNonSdkApi=");
            printWriter.println(pkg.isNonSdkApiRequested());
            printWriter.print(str);
            printWriter.print("  splits=");
            dumpSplitNames(printWriter, pkg);
            printWriter.println();
            int signatureSchemeVersion = pkg.getSigningDetails().getSignatureSchemeVersion();
            printWriter.print(str);
            printWriter.print("  apkSigningVersion=");
            printWriter.println(signatureSchemeVersion);
            printWriter.print(str);
            printWriter.print("  flags=");
            printFlags(printWriter, PackageInfoUtils.appInfoFlags(pkg, packageSetting), FLAG_DUMP_SPEC);
            printWriter.println();
            int appInfoPrivateFlags = PackageInfoUtils.appInfoPrivateFlags(pkg, packageSetting);
            if (appInfoPrivateFlags != 0) {
                printWriter.print(str);
                printWriter.print("  privateFlags=");
                printFlags(printWriter, appInfoPrivateFlags, PRIVATE_FLAG_DUMP_SPEC);
                printWriter.println();
            }
            if (pkg.hasPreserveLegacyExternalStorage()) {
                printWriter.print(str);
                printWriter.print("  hasPreserveLegacyExternalStorage=true");
                printWriter.println();
            }
            printWriter.print(str);
            printWriter.print("  forceQueryable=");
            printWriter.print(packageSetting.getPkg().isForceQueryable());
            if (packageSetting.isForceQueryableOverride()) {
                printWriter.print(" (override=true)");
            }
            printWriter.println();
            if (!packageSetting.getPkg().getQueriesPackages().isEmpty()) {
                printWriter.append((CharSequence) str).append((CharSequence) "  queriesPackages=").println(packageSetting.getPkg().getQueriesPackages());
            }
            if (!packageSetting.getPkg().getQueriesIntents().isEmpty()) {
                printWriter.append((CharSequence) str).append((CharSequence) "  queriesIntents=").println(packageSetting.getPkg().getQueriesIntents());
            }
            File dataDir = PackageInfoUtils.getDataDir(pkg, UserHandle.myUserId());
            printWriter.print(str);
            printWriter.print("  dataDir=");
            printWriter.println(dataDir.getAbsolutePath());
            printWriter.print(str);
            printWriter.print("  supportsScreens=[");
            if (pkg.isSmallScreensSupported()) {
                printWriter.print("small");
                z3 = false;
            } else {
                z3 = true;
            }
            if (pkg.isNormalScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("medium");
                z3 = false;
            }
            if (pkg.isLargeScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("large");
                z3 = false;
            }
            if (pkg.isExtraLargeScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("xlarge");
                z3 = false;
            }
            if (pkg.isResizeable()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("resizeable");
                z3 = false;
            }
            if (pkg.isAnyDensity()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("anyDensity");
            }
            printWriter.println("]");
            List libraryNames = pkg.getLibraryNames();
            if (libraryNames != null && libraryNames.size() > 0) {
                printWriter.print(str);
                printWriter.println("  dynamic libraries:");
                for (int i3 = 0; i3 < libraryNames.size(); i3++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((String) libraryNames.get(i3));
                }
            }
            String str3 = " version:";
            if (pkg.getStaticSharedLibraryName() != null) {
                printWriter.print(str);
                printWriter.println("  static library:");
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("name:");
                printWriter.print(pkg.getStaticSharedLibraryName());
                printWriter.print(" version:");
                printWriter.println(pkg.getStaticSharedLibraryVersion());
            }
            if (pkg.getSdkLibraryName() != null) {
                printWriter.print(str);
                printWriter.println("  SDK library:");
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("name:");
                printWriter.print(pkg.getSdkLibraryName());
                printWriter.print(" versionMajor:");
                printWriter.println(pkg.getSdkLibVersionMajor());
            }
            List usesLibraries = pkg.getUsesLibraries();
            if (usesLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesLibraries:");
                for (int i4 = 0; i4 < usesLibraries.size(); i4++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((String) usesLibraries.get(i4));
                }
            }
            List usesStaticLibraries = pkg.getUsesStaticLibraries();
            long[] usesStaticLibrariesVersions = pkg.getUsesStaticLibrariesVersions();
            if (usesStaticLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesStaticLibraries:");
                for (int i5 = 0; i5 < usesStaticLibraries.size(); i5++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.print((String) usesStaticLibraries.get(i5));
                    printWriter.print(" version:");
                    printWriter.println(usesStaticLibrariesVersions[i5]);
                }
            }
            List usesSdkLibraries = pkg.getUsesSdkLibraries();
            long[] usesSdkLibrariesVersionsMajor = pkg.getUsesSdkLibrariesVersionsMajor();
            if (usesSdkLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesSdkLibraries:");
                int size2 = usesSdkLibraries.size();
                int i6 = 0;
                while (i6 < size2) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.print((String) usesSdkLibraries.get(i6));
                    printWriter.print(str3);
                    printWriter.println(usesSdkLibrariesVersionsMajor[i6]);
                    i6++;
                    str3 = str3;
                }
            }
            List usesOptionalLibraries = pkg.getUsesOptionalLibraries();
            if (usesOptionalLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesOptionalLibraries:");
                for (int i7 = 0; i7 < usesOptionalLibraries.size(); i7++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((String) usesOptionalLibraries.get(i7));
                }
            }
            List usesNativeLibraries = pkg.getUsesNativeLibraries();
            if (usesNativeLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesNativeLibraries:");
                for (int i8 = 0; i8 < usesNativeLibraries.size(); i8++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((String) usesNativeLibraries.get(i8));
                }
            }
            List usesOptionalNativeLibraries = pkg.getUsesOptionalNativeLibraries();
            if (usesOptionalNativeLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesOptionalNativeLibraries:");
                for (int i9 = 0; i9 < usesOptionalNativeLibraries.size(); i9++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((String) usesOptionalNativeLibraries.get(i9));
                }
            }
            List usesLibraryFiles = packageSetting.getPkgState().getUsesLibraryFiles();
            if (usesLibraryFiles.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesLibraryFiles:");
                for (int i10 = 0; i10 < usesLibraryFiles.size(); i10++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((String) usesLibraryFiles.get(i10));
                }
            }
            Map processes = pkg.getProcesses();
            if (!processes.isEmpty()) {
                printWriter.print(str);
                printWriter.println("  processes:");
                for (ParsedProcess parsedProcess : processes.values()) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println(parsedProcess.getName());
                    if (parsedProcess.getDeniedPermissions() != null) {
                        for (String str4 : parsedProcess.getDeniedPermissions()) {
                            printWriter.print(str);
                            printWriter.print("      deny: ");
                            printWriter.println(str4);
                        }
                    }
                }
            }
        }
        printWriter.print(str);
        printWriter.print("  timeStamp=");
        date.setTime(packageSetting.getLastModifiedTime());
        printWriter.println(simpleDateFormat.format(date));
        printWriter.print(str);
        printWriter.print("  lastUpdateTime=");
        date.setTime(packageSetting.getLastUpdateTime());
        printWriter.println(simpleDateFormat.format(date));
        printWriter.print(str);
        printWriter.print("  installerPackageName=");
        printWriter.println(packageSetting.getInstallSource().mInstallerPackageName);
        printWriter.print(str);
        printWriter.print("  installerPackageUid=");
        printWriter.println(packageSetting.getInstallSource().mInstallerPackageUid);
        printWriter.print(str);
        printWriter.print("  initiatingPackageName=");
        printWriter.println(packageSetting.getInstallSource().mInitiatingPackageName);
        printWriter.print(str);
        printWriter.print("  originatingPackageName=");
        printWriter.println(packageSetting.getInstallSource().mOriginatingPackageName);
        if (packageSetting.getInstallSource().mUpdateOwnerPackageName != null) {
            printWriter.print(str);
            printWriter.print("  updateOwnerPackageName=");
            printWriter.println(packageSetting.getInstallSource().mUpdateOwnerPackageName);
        }
        if (packageSetting.getInstallSource().mInstallerAttributionTag != null) {
            printWriter.print(str);
            printWriter.print("  installerAttributionTag=");
            printWriter.println(packageSetting.getInstallSource().mInstallerAttributionTag);
        }
        int categoryOverride = packageSetting.getCategoryOverride();
        int category = pkg != null ? pkg.getCategory() : -1;
        if (categoryOverride != -1 || category != -1) {
            printWriter.print(str);
            printWriter.print("  category=");
            printWriter.print(categoryOverride);
            if (category != -1) {
                printWriter.print(" (manifest=" + category + ")");
            }
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("  packageSource=");
        printWriter.println(packageSetting.getInstallSource().mPackageSource);
        if (packageSetting.isIncremental()) {
            printWriter.print(str);
            printWriter.println("  loadingProgress=" + ((int) (packageSetting.getLoadingProgress() * 100.0f)) + "%");
            date.setTime(packageSetting.getLoadingCompletedTime());
            printWriter.print(str);
            printWriter.println("  loadingCompletedTime=" + simpleDateFormat.format(date));
        }
        printWriter.print(str);
        printWriter.print("  appMetadataFilePath=");
        printWriter.println(packageSetting.getAppMetadataFilePath());
        if (packageSetting.getVolumeUuid() != null) {
            printWriter.print(str);
            printWriter.print("  volumeUuid=");
            printWriter.println(packageSetting.getVolumeUuid());
        }
        printWriter.print(str);
        printWriter.print("  signatures=");
        printWriter.println(packageSetting.getSignatures());
        printWriter.print(str);
        printWriter.print("  installPermissionsFixed=");
        printWriter.print(packageSetting.isInstallPermissionsFixed());
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  pkgFlags=");
        printFlags(printWriter, packageSetting.getFlags(), FLAG_DUMP_SPEC);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  privatePkgFlags=");
        printFlags(printWriter, packageSetting.getPrivateFlags(), PRIVATE_FLAG_DUMP_SPEC);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  apexModuleName=");
        printWriter.println(packageSetting.getApexModuleName());
        if (pkg != null && pkg.getOverlayTarget() != null) {
            printWriter.print(str);
            printWriter.print("  overlayTarget=");
            printWriter.println(pkg.getOverlayTarget());
            printWriter.print(str);
            printWriter.print("  overlayCategory=");
            printWriter.println(pkg.getOverlayCategory());
        }
        if (pkg != null && !pkg.getPermissions().isEmpty()) {
            List permissions = pkg.getPermissions();
            printWriter.print(str);
            printWriter.println("  declared permissions:");
            for (int i11 = 0; i11 < permissions.size(); i11++) {
                ParsedPermission parsedPermission = (ParsedPermission) permissions.get(i11);
                if (arraySet == null || arraySet.contains(parsedPermission.getName())) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.print(parsedPermission.getName());
                    printWriter.print(": prot=");
                    printWriter.print(PermissionInfo.protectionToString(parsedPermission.getProtectionLevel()));
                    if ((parsedPermission.getFlags() & 1) != 0) {
                        printWriter.print(", COSTS_MONEY");
                    }
                    if ((parsedPermission.getFlags() & 2) != 0) {
                        printWriter.print(", HIDDEN");
                    }
                    if ((parsedPermission.getFlags() & 1073741824) != 0) {
                        printWriter.print(", INSTALLED");
                    }
                    printWriter.println();
                }
            }
        }
        if ((arraySet != null || z) && pkg != null && pkg.getRequestedPermissions() != null && pkg.getRequestedPermissions().size() > 0) {
            List requestedPermissions = pkg.getRequestedPermissions();
            printWriter.print(str);
            printWriter.println("  requested permissions:");
            for (int i12 = 0; i12 < requestedPermissions.size(); i12++) {
                String str5 = (String) requestedPermissions.get(i12);
                if (arraySet == null || arraySet.contains(str5)) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println(str5);
                }
            }
        }
        if (!packageSetting.hasSharedUser() || arraySet != null || z) {
            dumpInstallPermissionsLPr(printWriter, str + "  ", arraySet, legacyPermissionState, list);
        }
        if (z2) {
            dumpComponents(printWriter, str + "  ", packageSetting);
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            UserInfo userInfo2 = (UserInfo) it2.next();
            PackageUserStateInternal userStateOrDefault2 = packageSetting.getUserStateOrDefault(userInfo2.id);
            printWriter.print(str);
            printWriter.print("  User ");
            printWriter.print(userInfo2.id);
            printWriter.print(": ");
            printWriter.print("ceDataInode=");
            printWriter.print(userStateOrDefault2.getCeDataInode());
            printWriter.print(" installed=");
            printWriter.print(userStateOrDefault2.isInstalled());
            printWriter.print(" hidden=");
            printWriter.print(userStateOrDefault2.isHidden());
            printWriter.print(" suspended=");
            printWriter.print(userStateOrDefault2.isSuspended());
            printWriter.print(" distractionFlags=");
            printWriter.print(userStateOrDefault2.getDistractionFlags());
            printWriter.print(" stopped=");
            printWriter.print(userStateOrDefault2.isStopped());
            printWriter.print(" notLaunched=");
            printWriter.print(userStateOrDefault2.isNotLaunched());
            printWriter.print(" enabled=");
            printWriter.print(userStateOrDefault2.getEnabledState());
            printWriter.print(" instant=");
            printWriter.print(userStateOrDefault2.isInstantApp());
            printWriter.print(" virtual=");
            printWriter.println(userStateOrDefault2.isVirtualPreload());
            printWriter.print("      installReason=");
            printWriter.println(userStateOrDefault2.getInstallReason());
            PackageUserStateInternal readUserState = packageSetting.readUserState(userInfo2.id);
            printWriter.print("      firstInstallTime=");
            date.setTime(readUserState.getFirstInstallTimeMillis());
            printWriter.println(simpleDateFormat.format(date));
            printWriter.print("      uninstallReason=");
            printWriter.println(userStateOrDefault2.getUninstallReason());
            if (userStateOrDefault2.isSuspended()) {
                printWriter.print(str);
                printWriter.println("  Suspend params:");
                for (int i13 = 0; i13 < userStateOrDefault2.getSuspendParams().size(); i13++) {
                    printWriter.print(str);
                    printWriter.print("    suspendingPackage=");
                    printWriter.print((String) userStateOrDefault2.getSuspendParams().keyAt(i13));
                    SuspendParams suspendParams = (SuspendParams) userStateOrDefault2.getSuspendParams().valueAt(i13);
                    if (suspendParams != null) {
                        printWriter.print(" dialogInfo=");
                        printWriter.print(suspendParams.getDialogInfo());
                    }
                    printWriter.println();
                }
            }
            OverlayPaths overlayPaths = userStateOrDefault2.getOverlayPaths();
            if (overlayPaths != null) {
                if (!overlayPaths.getOverlayPaths().isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("    overlay paths:");
                    for (String str6 : overlayPaths.getOverlayPaths()) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println(str6);
                    }
                }
                if (!overlayPaths.getResourceDirs().isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("    legacy overlay paths:");
                    for (String str7 : overlayPaths.getResourceDirs()) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println(str7);
                    }
                }
            }
            Map sharedLibraryOverlayPaths = userStateOrDefault2.getSharedLibraryOverlayPaths();
            if (sharedLibraryOverlayPaths != null) {
                Iterator it3 = sharedLibraryOverlayPaths.entrySet().iterator();
                while (it3.hasNext()) {
                    Map.Entry entry = (Map.Entry) it3.next();
                    OverlayPaths overlayPaths2 = (OverlayPaths) entry.getValue();
                    if (overlayPaths2 != null) {
                        if (!overlayPaths2.getOverlayPaths().isEmpty()) {
                            printWriter.print(str);
                            printWriter.println("    ");
                            printWriter.print((String) entry.getKey());
                            printWriter.println(" overlay paths:");
                            for (String str8 : overlayPaths2.getOverlayPaths()) {
                                printWriter.print(str);
                                printWriter.print("        ");
                                printWriter.println(str8);
                                it3 = it3;
                            }
                        }
                        Iterator it4 = it3;
                        if (!overlayPaths2.getResourceDirs().isEmpty()) {
                            printWriter.print(str);
                            printWriter.println("      ");
                            printWriter.print((String) entry.getKey());
                            printWriter.println(" legacy overlay paths:");
                            for (String str9 : overlayPaths2.getResourceDirs()) {
                                printWriter.print(str);
                                printWriter.print("      ");
                                printWriter.println(str9);
                            }
                        }
                        it3 = it4;
                    }
                }
            }
            String lastDisableAppCaller2 = userStateOrDefault2.getLastDisableAppCaller();
            if (lastDisableAppCaller2 != null) {
                printWriter.print(str);
                printWriter.print("    lastDisabledCaller: ");
                printWriter.println(lastDisableAppCaller2);
            }
            if (!packageSetting.hasSharedUser()) {
                dumpGidsLPr(printWriter, str + "    ", this.mPermissionDataProvider.getGidsForUid(UserHandle.getUid(userInfo2.id, packageSetting.getAppId())));
                dumpRuntimePermissionsLPr(printWriter, str + "    ", arraySet, legacyPermissionState.getPermissionStates(userInfo2.id), z);
            }
            String harmfulAppWarning = userStateOrDefault2.getHarmfulAppWarning();
            if (harmfulAppWarning != null) {
                printWriter.print(str);
                printWriter.print("      harmfulAppWarning: ");
                printWriter.println(harmfulAppWarning);
            }
            if (arraySet == null) {
                WatchedArraySet disabledComponentsNoCopy = userStateOrDefault2.getDisabledComponentsNoCopy();
                if (disabledComponentsNoCopy != null && disabledComponentsNoCopy.size() > 0) {
                    printWriter.print(str);
                    printWriter.println("    disabledComponents:");
                    for (int i14 = 0; i14 < disabledComponentsNoCopy.size(); i14++) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println((String) disabledComponentsNoCopy.valueAt(i14));
                    }
                }
                WatchedArraySet enabledComponentsNoCopy = userStateOrDefault2.getEnabledComponentsNoCopy();
                if (enabledComponentsNoCopy != null && enabledComponentsNoCopy.size() > 0) {
                    printWriter.print(str);
                    printWriter.println("    enabledComponents:");
                    for (int i15 = 0; i15 < enabledComponentsNoCopy.size(); i15++) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println((String) enabledComponentsNoCopy.valueAt(i15));
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x0111, code lost:
    
        if (r1 != false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0117, code lost:
    
        if (r30.onTitlePrinted() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0119, code lost:
    
        r27.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x011c, code lost:
    
        r27.println("Renamed packages:");
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0123, code lost:
    
        r27.print("  ");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dumpPackagesLPr(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
        int i;
        boolean z2;
        boolean z3;
        DumpState dumpState2 = dumpState;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        boolean isOptionEnabled = dumpState2.isOptionEnabled(2);
        List allUsers = getAllUsers(UserManagerService.getInstance());
        Iterator it = this.mPackages.values().iterator();
        boolean z4 = false;
        while (true) {
            i = 8;
            if (!it.hasNext()) {
                break;
            }
            PackageSetting packageSetting = (PackageSetting) it.next();
            if (str == null || str.equals(packageSetting.getRealName()) || str.equals(packageSetting.getPackageName())) {
                if (packageSetting.getPkg() == null || !packageSetting.getPkg().isApex() || dumpState2.isOptionEnabled(8)) {
                    LegacyPermissionState legacyPermissionState = this.mPermissionDataProvider.getLegacyPermissionState(packageSetting.getAppId());
                    if (arraySet == null || legacyPermissionState.hasPermissionState(arraySet)) {
                        if (!z && str != null) {
                            dumpState2.setSharedUser(getSharedUserSettingLPr(packageSetting));
                        }
                        if (z || z4) {
                            z3 = z4;
                        } else {
                            if (dumpState.onTitlePrinted()) {
                                printWriter.println();
                            }
                            printWriter.println("Packages:");
                            z3 = true;
                        }
                        dumpPackageLPr(printWriter, "  ", z ? "pkg" : null, arraySet, packageSetting, legacyPermissionState, simpleDateFormat, date, allUsers, str != null, isOptionEnabled);
                        dumpState2 = dumpState;
                        z4 = z3;
                        simpleDateFormat = simpleDateFormat;
                    }
                }
            }
        }
        SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
        if (this.mRenamedPackages.size() > 0 && arraySet == null) {
            boolean z5 = false;
            for (Map.Entry entry : this.mRenamedPackages.entrySet()) {
                if (str == null || str.equals(entry.getKey()) || str.equals(entry.getValue())) {
                    printWriter.print("ren,");
                    printWriter.print((String) entry.getKey());
                    printWriter.print(z ? " -> " : ",");
                    printWriter.println((String) entry.getValue());
                }
            }
        }
        if (this.mDisabledSysPackages.size() <= 0 || arraySet != null) {
            return;
        }
        boolean z6 = false;
        for (PackageSetting packageSetting2 : this.mDisabledSysPackages.values()) {
            if (str == null || str.equals(packageSetting2.getRealName()) || str.equals(packageSetting2.getPackageName())) {
                if (packageSetting2.getPkg() != null && packageSetting2.getPkg().isApex()) {
                    if (!dumpState.isOptionEnabled(i)) {
                    }
                }
                if (z || z6) {
                    z2 = z6;
                } else {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("Hidden system packages:");
                    z2 = true;
                }
                dumpPackageLPr(printWriter, "  ", z ? "dis" : null, arraySet, packageSetting2, this.mPermissionDataProvider.getLegacyPermissionState(packageSetting2.getAppId()), simpleDateFormat2, date, allUsers, str != null, isOptionEnabled);
                z6 = z2;
                i = i;
            }
        }
    }

    public void dumpPackagesProto(ProtoOutputStream protoOutputStream) {
        List allUsers = getAllUsers(UserManagerService.getInstance());
        int size = this.mPackages.size();
        for (int i = 0; i < size; i++) {
            ((PackageSetting) this.mPackages.valueAt(i)).dumpDebug(protoOutputStream, 2246267895813L, allUsers, this.mPermissionDataProvider);
        }
    }

    public void dumpPermissions(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState) {
        LegacyPermissionSettings.dumpPermissions(printWriter, str, arraySet, this.mPermissionDataProvider.getLegacyPermissions(), this.mPermissionDataProvider.getAllAppOpPermissionPackages(), true, dumpState);
    }

    public void dumpSharedUsersLPr(PrintWriter printWriter, String str, ArraySet arraySet, DumpState dumpState, boolean z) {
        boolean z2;
        boolean z3 = false;
        for (SharedUserSetting sharedUserSetting : this.mSharedUsers.values()) {
            if (str == null || sharedUserSetting == dumpState.getSharedUser()) {
                LegacyPermissionState legacyPermissionState = this.mPermissionDataProvider.getLegacyPermissionState(sharedUserSetting.mAppId);
                if (arraySet == null || legacyPermissionState.hasPermissionState(arraySet)) {
                    if (!z) {
                        if (z3) {
                            z2 = z3;
                        } else {
                            if (dumpState.onTitlePrinted()) {
                                printWriter.println();
                            }
                            printWriter.println("Shared users:");
                            z2 = true;
                        }
                        printWriter.print("  SharedUser [");
                        printWriter.print(sharedUserSetting.name);
                        printWriter.print("] (");
                        printWriter.print(Integer.toHexString(System.identityHashCode(sharedUserSetting)));
                        printWriter.println("):");
                        printWriter.print("    ");
                        printWriter.print("appId=");
                        printWriter.println(sharedUserSetting.mAppId);
                        printWriter.print("    ");
                        printWriter.println("Packages");
                        ArraySet packageStates = sharedUserSetting.getPackageStates();
                        int size = packageStates.size();
                        for (int i = 0; i < size; i++) {
                            PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i);
                            if (packageStateInternal != null) {
                                printWriter.print("      ");
                                printWriter.println(packageStateInternal);
                            } else {
                                printWriter.print("      ");
                                printWriter.println("NULL?!");
                            }
                        }
                        if (!dumpState.isOptionEnabled(4)) {
                            List allUsers = getAllUsers(UserManagerService.getInstance());
                            dumpInstallPermissionsLPr(printWriter, "    ", arraySet, legacyPermissionState, allUsers);
                            Iterator it = allUsers.iterator();
                            while (it.hasNext()) {
                                int i2 = ((UserInfo) it.next()).id;
                                int[] gidsForUid = this.mPermissionDataProvider.getGidsForUid(UserHandle.getUid(i2, sharedUserSetting.mAppId));
                                Collection permissionStates = legacyPermissionState.getPermissionStates(i2);
                                if (!ArrayUtils.isEmpty(gidsForUid) || !permissionStates.isEmpty()) {
                                    printWriter.print("    ");
                                    printWriter.print("User ");
                                    printWriter.print(i2);
                                    printWriter.println(": ");
                                    dumpGidsLPr(printWriter, "      ", gidsForUid);
                                    dumpRuntimePermissionsLPr(printWriter, "      ", arraySet, permissionStates, str != null);
                                }
                            }
                        }
                        z3 = z2;
                    } else {
                        printWriter.print("suid,");
                        printWriter.print(sharedUserSetting.mAppId);
                        printWriter.print(",");
                        printWriter.println(sharedUserSetting.name);
                    }
                }
            }
        }
    }

    public void dumpSharedUsersProto(ProtoOutputStream protoOutputStream) {
        int size = this.mSharedUsers.size();
        for (int i = 0; i < size; i++) {
            ((SharedUserSetting) this.mSharedUsers.valueAt(i)).dumpDebug(protoOutputStream, 2246267895814L);
        }
    }

    public void dumpReadMessages(PrintWriter printWriter, DumpState dumpState) {
        printWriter.println("Settings parse messages:");
        printWriter.print(this.mReadMessages.toString());
    }

    public static void dumpSplitNames(PrintWriter printWriter, AndroidPackage androidPackage) {
        if (androidPackage == null) {
            printWriter.print("unknown");
            return;
        }
        printWriter.print("[");
        printWriter.print("base");
        if (androidPackage.getBaseRevisionCode() != 0) {
            printWriter.print(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
            printWriter.print(androidPackage.getBaseRevisionCode());
        }
        String[] splitNames = androidPackage.getSplitNames();
        int[] splitRevisionCodes = androidPackage.getSplitRevisionCodes();
        for (int i = 0; i < splitNames.length; i++) {
            printWriter.print(", ");
            printWriter.print(splitNames[i]);
            if (splitRevisionCodes[i] != 0) {
                printWriter.print(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                printWriter.print(splitRevisionCodes[i]);
            }
        }
        printWriter.print("]");
    }

    public void dumpGidsLPr(PrintWriter printWriter, String str, int[] iArr) {
        if (ArrayUtils.isEmpty(iArr)) {
            return;
        }
        printWriter.print(str);
        printWriter.print("gids=");
        printWriter.println(PackageManagerServiceUtils.arrayToString(iArr));
    }

    public void dumpRuntimePermissionsLPr(PrintWriter printWriter, String str, ArraySet arraySet, Collection collection, boolean z) {
        boolean z2;
        Iterator it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            } else if (((LegacyPermissionState.PermissionState) it.next()).isRuntime()) {
                z2 = true;
                break;
            }
        }
        if (z2 || z) {
            printWriter.print(str);
            printWriter.println("runtime permissions:");
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                LegacyPermissionState.PermissionState permissionState = (LegacyPermissionState.PermissionState) it2.next();
                if (permissionState.isRuntime() && (arraySet == null || arraySet.contains(permissionState.getName()))) {
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.print(permissionState.getName());
                    printWriter.print(": granted=");
                    printWriter.print(permissionState.isGranted());
                    printWriter.println(permissionFlagsToString(", flags=", permissionState.getFlags()));
                }
            }
        }
    }

    public static String permissionFlagsToString(String str, int i) {
        StringBuilder sb = null;
        while (i != 0) {
            if (sb == null) {
                sb = new StringBuilder();
                sb.append(str);
                sb.append("[ ");
            }
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            sb.append(PackageManager.permissionFlagToString(numberOfTrailingZeros));
            if (i != 0) {
                sb.append('|');
            }
        }
        if (sb == null) {
            return "";
        }
        sb.append(']');
        return sb.toString();
    }

    public void dumpInstallPermissionsLPr(PrintWriter printWriter, String str, ArraySet arraySet, LegacyPermissionState legacyPermissionState, List list) {
        LegacyPermissionState.PermissionState permissionState;
        ArraySet arraySet2 = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            for (LegacyPermissionState.PermissionState permissionState2 : legacyPermissionState.getPermissionStates(((UserInfo) it.next()).id)) {
                if (!permissionState2.isRuntime()) {
                    String name = permissionState2.getName();
                    if (arraySet == null || arraySet.contains(name)) {
                        arraySet2.add(name);
                    }
                }
            }
        }
        Iterator it2 = arraySet2.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            LegacyPermissionState.PermissionState permissionState3 = legacyPermissionState.getPermissionState(str2, 0);
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                int i = ((UserInfo) it3.next()).id;
                if (i == 0) {
                    permissionState = permissionState3;
                } else {
                    permissionState = legacyPermissionState.getPermissionState(str2, i);
                    if (Objects.equals(permissionState, permissionState3)) {
                    }
                }
                if (!z) {
                    printWriter.print(str);
                    printWriter.println("install permissions:");
                    z = true;
                }
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print(str2);
                printWriter.print(": granted=");
                printWriter.print(permissionState != null && permissionState.isGranted());
                printWriter.print(permissionFlagsToString(", flags=", permissionState != null ? permissionState.getFlags() : 0));
                if (i == 0) {
                    printWriter.println();
                } else {
                    printWriter.print(", userId=");
                    printWriter.println(i);
                }
            }
        }
    }

    public void dumpComponents(PrintWriter printWriter, String str, PackageSetting packageSetting) {
        dumpComponents(printWriter, str, "activities:", packageSetting.getPkg().getActivities());
        dumpComponents(printWriter, str, "services:", packageSetting.getPkg().getServices());
        dumpComponents(printWriter, str, "receivers:", packageSetting.getPkg().getReceivers());
        dumpComponents(printWriter, str, "providers:", packageSetting.getPkg().getProviders());
        dumpComponents(printWriter, str, "instrumentations:", packageSetting.getPkg().getInstrumentations());
    }

    public void dumpComponents(PrintWriter printWriter, String str, String str2, List list) {
        int size = CollectionUtils.size(list);
        if (size == 0) {
            return;
        }
        printWriter.print(str);
        printWriter.println(str2);
        for (int i = 0; i < size; i++) {
            ParsedComponent parsedComponent = (ParsedComponent) list.get(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.println(parsedComponent.getComponentName().flattenToShortString());
        }
    }

    public void writePermissionStateForUserLPr(int i, boolean z) {
        if (z) {
            this.mRuntimePermissionsPersistence.writeStateForUser(i, this.mPermissionDataProvider, this.mPackages, this.mSharedUsers, null, this.mLock, true);
        } else {
            this.mRuntimePermissionsPersistence.writeStateForUserAsync(i);
        }
    }

    public class KeySetToValueMap implements Map {
        public final Set mKeySet;
        public final Object mValue;

        public KeySetToValueMap(Set set, Object obj) {
            this.mKeySet = set;
            this.mValue = obj;
        }

        @Override // java.util.Map
        public int size() {
            return this.mKeySet.size();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.mKeySet.isEmpty();
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return this.mKeySet.contains(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return this.mValue == obj;
        }

        @Override // java.util.Map
        public Object get(Object obj) {
            return this.mValue;
        }

        @Override // java.util.Map
        public Object put(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Object remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Set keySet() {
            return this.mKeySet;
        }

        @Override // java.util.Map
        public Collection values() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Set entrySet() {
            throw new UnsupportedOperationException();
        }
    }

    public final class RuntimePermissionPersistence {
        public static final Random sRandom = new Random();
        public String mExtendedFingerprint;
        public final Consumer mInvokeWriteUserStateAsyncCallback;
        public final RuntimePermissionsPersistence mPersistence;
        public final Object mPersistenceLock = new Object();
        public final Handler mAsyncHandler = new MyHandler();
        public final Handler mPersistenceHandler = new PersistenceHandler();
        public final Object mLock = new Object();
        public long mLastWriteDoneTimeMillis = 0;
        public final SparseBooleanArray mWriteScheduled = new SparseBooleanArray();
        public final SparseLongArray mLastNotWrittenMutationTimesMillis = new SparseLongArray();
        public AtomicBoolean mIsLegacyPermissionStateStale = new AtomicBoolean(false);
        public final SparseIntArray mVersions = new SparseIntArray();
        public final SparseArray mFingerprints = new SparseArray();
        public final SparseBooleanArray mPermissionUpgradeNeeded = new SparseBooleanArray();
        public final SparseArray mPendingStatesToWrite = new SparseArray();

        public RuntimePermissionPersistence(RuntimePermissionsPersistence runtimePermissionsPersistence, Consumer consumer) {
            this.mPersistence = runtimePermissionsPersistence;
            this.mInvokeWriteUserStateAsyncCallback = consumer;
        }

        public int getVersion(int i) {
            int i2;
            synchronized (this.mLock) {
                i2 = this.mVersions.get(i, 0);
            }
            return i2;
        }

        public void setVersion(int i, int i2) {
            synchronized (this.mLock) {
                this.mVersions.put(i2, i);
                writeStateForUserAsync(i2);
            }
        }

        public boolean isPermissionUpgradeNeeded(int i) {
            boolean z;
            synchronized (this.mLock) {
                z = this.mPermissionUpgradeNeeded.get(i, true);
            }
            return z;
        }

        public void updateRuntimePermissionsFingerprint(int i) {
            synchronized (this.mLock) {
                String str = this.mExtendedFingerprint;
                if (str == null) {
                    throw new RuntimeException("The version of the permission controller hasn't been set before trying to update the fingerprint.");
                }
                this.mFingerprints.put(i, str);
                this.mPermissionUpgradeNeeded.put(i, false);
                writeStateForUserAsync(i);
            }
        }

        public void setPermissionControllerVersion(long j) {
            synchronized (this.mLock) {
                int size = this.mFingerprints.size();
                this.mExtendedFingerprint = getExtendedFingerprint(j);
                for (int i = 0; i < size; i++) {
                    this.mPermissionUpgradeNeeded.put(this.mFingerprints.keyAt(i), !TextUtils.equals(this.mExtendedFingerprint, (String) this.mFingerprints.valueAt(i)));
                }
            }
        }

        public final String getExtendedFingerprint(long j) {
            return PackagePartitions.FINGERPRINT + "?pc_version=" + j;
        }

        public static long uniformRandom(double d, double d2) {
            return (long) ((sRandom.nextDouble() * (d2 - d)) + d);
        }

        public static long nextWritePermissionDelayMillis() {
            return uniformRandom(-300.0d, 300.0d) + 1000;
        }

        public void writeStateForUserAsync(int i) {
            this.mIsLegacyPermissionStateStale.set(true);
            synchronized (this.mLock) {
                long uptimeMillis = SystemClock.uptimeMillis();
                long nextWritePermissionDelayMillis = nextWritePermissionDelayMillis();
                if (this.mWriteScheduled.get(i)) {
                    this.mAsyncHandler.removeMessages(i);
                    long j = this.mLastNotWrittenMutationTimesMillis.get(i);
                    if (uptimeMillis - j >= 2000) {
                        this.mAsyncHandler.obtainMessage(i).sendToTarget();
                    } else {
                        long min = Math.min(nextWritePermissionDelayMillis, Math.max((j + 2000) - uptimeMillis, 0L));
                        this.mAsyncHandler.sendMessageDelayed(this.mAsyncHandler.obtainMessage(i), min);
                    }
                } else {
                    this.mLastNotWrittenMutationTimesMillis.put(i, uptimeMillis);
                    this.mAsyncHandler.sendMessageDelayed(this.mAsyncHandler.obtainMessage(i), nextWritePermissionDelayMillis);
                    this.mWriteScheduled.put(i, true);
                }
            }
        }

        public void writeStateForUser(final int i, final LegacyPermissionDataProvider legacyPermissionDataProvider, final WatchedArrayMap watchedArrayMap, final WatchedArrayMap watchedArrayMap2, final Handler handler, final Object obj, final boolean z) {
            Trace.traceBegin(262144L, "writePerm");
            Slog.d("PackageSettings", "++ writeStateForUserSyncLPr(" + i + ")");
            synchronized (this.mLock) {
                this.mAsyncHandler.removeMessages(i);
                this.mWriteScheduled.delete(i);
            }
            Runnable runnable = new Runnable() { // from class: com.android.server.pm.Settings$RuntimePermissionPersistence$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Settings.RuntimePermissionPersistence.this.lambda$writeStateForUser$0(obj, z, legacyPermissionDataProvider, watchedArrayMap, i, watchedArrayMap2, handler);
                }
            };
            if (handler != null) {
                handler.post(runnable);
            } else {
                runnable.run();
            }
            this.mLastWriteDoneTimeMillis = SystemClock.uptimeMillis();
            Slog.w("PackageSettings", "-- writeStateForUserSyncLPr(" + i + ")");
            Trace.traceEnd(262144L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$writeStateForUser$0(Object obj, boolean z, LegacyPermissionDataProvider legacyPermissionDataProvider, WatchedArrayMap watchedArrayMap, int i, WatchedArrayMap watchedArrayMap2, Handler handler) {
            boolean andSet = this.mIsLegacyPermissionStateStale.getAndSet(false);
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            synchronized (obj) {
                if (z || andSet) {
                    legacyPermissionDataProvider.writeLegacyPermissionStateTEMP();
                }
                int size = watchedArrayMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String str = (String) watchedArrayMap.keyAt(i2);
                    PackageStateInternal packageStateInternal = (PackageStateInternal) watchedArrayMap.valueAt(i2);
                    if (!packageStateInternal.hasSharedUser()) {
                        List permissionsFromPermissionsState = getPermissionsFromPermissionsState(packageStateInternal.getLegacyPermissionState(), i);
                        if (!permissionsFromPermissionsState.isEmpty() || packageStateInternal.isInstallPermissionsFixed()) {
                            arrayMap.put(str, permissionsFromPermissionsState);
                        }
                    }
                }
                int size2 = watchedArrayMap2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayMap2.put((String) watchedArrayMap2.keyAt(i3), getPermissionsFromPermissionsState(((SharedUserSetting) watchedArrayMap2.valueAt(i3)).getLegacyPermissionState(), i));
                }
            }
            synchronized (this.mLock) {
                this.mPendingStatesToWrite.put(i, new RuntimePermissionsState(this.mVersions.get(i, 0), (String) this.mFingerprints.get(i), arrayMap, arrayMap2));
            }
            if (handler != null) {
                this.mPersistenceHandler.obtainMessage(i).sendToTarget();
            } else {
                writePendingStates();
            }
        }

        public final void writePendingStates() {
            int keyAt;
            RuntimePermissionsState runtimePermissionsState;
            while (true) {
                synchronized (this.mLock) {
                    if (this.mPendingStatesToWrite.size() == 0) {
                        return;
                    }
                    keyAt = this.mPendingStatesToWrite.keyAt(0);
                    runtimePermissionsState = (RuntimePermissionsState) this.mPendingStatesToWrite.valueAt(0);
                    this.mPendingStatesToWrite.removeAt(0);
                }
                synchronized (this.mPersistenceLock) {
                    this.mPersistence.writeForUser(runtimePermissionsState, UserHandle.of(keyAt));
                }
            }
        }

        public final List getPermissionsFromPermissionsState(LegacyPermissionState legacyPermissionState, int i) {
            Collection<LegacyPermissionState.PermissionState> permissionStates = legacyPermissionState.getPermissionStates(i);
            ArrayList arrayList = new ArrayList();
            for (LegacyPermissionState.PermissionState permissionState : permissionStates) {
                arrayList.add(new RuntimePermissionsState.PermissionState(permissionState.getName(), permissionState.isGranted(), permissionState.getFlags()));
            }
            return arrayList;
        }

        public final void onUserRemoved(int i) {
            synchronized (this.mLock) {
                this.mAsyncHandler.removeMessages(i);
                this.mPermissionUpgradeNeeded.delete(i);
                this.mVersions.delete(i);
                this.mFingerprints.remove(i);
            }
        }

        public void readStateForUserSync(int i, VersionInfo versionInfo, WatchedArrayMap watchedArrayMap, WatchedArrayMap watchedArrayMap2, File file) {
            RuntimePermissionsState readForUser;
            synchronized (this.mPersistenceLock) {
                readForUser = this.mPersistence.readForUser(UserHandle.of(i));
            }
            if (readForUser == null) {
                readLegacyStateForUserSync(i, file, watchedArrayMap, watchedArrayMap2);
                writeStateForUserAsync(i);
                return;
            }
            synchronized (this.mLock) {
                int version = readForUser.getVersion();
                if (version == -1) {
                    version = -1;
                }
                this.mVersions.put(i, version);
                this.mFingerprints.put(i, readForUser.getFingerprint());
                char c = 0;
                boolean z = true;
                boolean z2 = versionInfo.sdkVersion < 30;
                Map packagePermissions = readForUser.getPackagePermissions();
                int size = watchedArrayMap.size();
                int i2 = 0;
                while (i2 < size) {
                    String str = (String) watchedArrayMap.keyAt(i2);
                    PackageSetting packageSetting = (PackageSetting) watchedArrayMap.valueAt(i2);
                    List list = (List) packagePermissions.get(str);
                    if (list != null) {
                        readPermissionsState(list, packageSetting.getLegacyPermissionState(), i);
                        packageSetting.setInstallPermissionsFixed(z);
                    } else if (!packageSetting.hasSharedUser() && !z2) {
                        Object[] objArr = new Object[2];
                        objArr[c] = str;
                        objArr[1] = Integer.valueOf(i);
                        Slogf.m105w("PackageSettings", "Missing permission state for package %s on user %d", objArr);
                        packageSetting.getLegacyPermissionState().setMissing(true, i);
                    }
                    i2++;
                    c = 0;
                    z = true;
                }
                Map sharedUserPermissions = readForUser.getSharedUserPermissions();
                int size2 = watchedArrayMap2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    String str2 = (String) watchedArrayMap2.keyAt(i3);
                    SharedUserSetting sharedUserSetting = (SharedUserSetting) watchedArrayMap2.valueAt(i3);
                    List list2 = (List) sharedUserPermissions.get(str2);
                    if (list2 != null) {
                        readPermissionsState(list2, sharedUserSetting.getLegacyPermissionState(), i);
                    } else if (!z2) {
                        Slog.w("PackageSettings", "Missing permission state for shared user: " + str2);
                        sharedUserSetting.getLegacyPermissionState().setMissing(true, i);
                    }
                }
            }
        }

        public final void readPermissionsState(List list, LegacyPermissionState legacyPermissionState, int i) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                RuntimePermissionsState.PermissionState permissionState = (RuntimePermissionsState.PermissionState) list.get(i2);
                legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(permissionState.getName(), true, permissionState.isGranted(), permissionState.getFlags()), i);
            }
        }

        public final void readLegacyStateForUserSync(int i, File file, WatchedArrayMap watchedArrayMap, WatchedArrayMap watchedArrayMap2) {
            synchronized (this.mLock) {
                if (file.exists()) {
                    try {
                        FileInputStream openRead = new AtomicFile(file).openRead();
                        try {
                            try {
                                parseLegacyRuntimePermissions(Xml.resolvePullParser(openRead), i, watchedArrayMap, watchedArrayMap2);
                            } catch (IOException | XmlPullParserException e) {
                                throw new IllegalStateException("Failed parsing permissions file: " + file, e);
                            }
                        } finally {
                            IoUtils.closeQuietly(openRead);
                        }
                    } catch (FileNotFoundException unused) {
                        Slog.i("PackageManager", "No permissions state");
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x00c7 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x005c A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void parseLegacyRuntimePermissions(TypedXmlPullParser typedXmlPullParser, int i, WatchedArrayMap watchedArrayMap, WatchedArrayMap watchedArrayMap2) {
            char c;
            synchronized (this.mLock) {
                int depth = typedXmlPullParser.getDepth();
                while (true) {
                    int next = typedXmlPullParser.next();
                    if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                        break;
                    }
                    if (next != 3 && next != 4) {
                        String name = typedXmlPullParser.getName();
                        int hashCode = name.hashCode();
                        if (hashCode == 111052) {
                            if (name.equals("pkg")) {
                                c = 1;
                                if (c != 0) {
                                }
                            }
                            c = 65535;
                            if (c != 0) {
                            }
                        } else if (hashCode != 160289295) {
                            if (hashCode == 485578803 && name.equals("shared-user")) {
                                c = 2;
                                if (c != 0) {
                                    this.mVersions.put(i, typedXmlPullParser.getAttributeInt((String) null, "version", -1));
                                    this.mFingerprints.put(i, typedXmlPullParser.getAttributeValue((String) null, "fingerprint"));
                                } else if (c == 1) {
                                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                                    PackageStateInternal packageStateInternal = (PackageStateInternal) watchedArrayMap.get(attributeValue);
                                    if (packageStateInternal == null) {
                                        Slog.w("PackageManager", "Unknown package:" + attributeValue);
                                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                                    } else {
                                        parseLegacyPermissionsLPr(typedXmlPullParser, packageStateInternal.getLegacyPermissionState(), i);
                                    }
                                } else if (c == 2) {
                                    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "name");
                                    SharedUserSetting sharedUserSetting = (SharedUserSetting) watchedArrayMap2.get(attributeValue2);
                                    if (sharedUserSetting == null) {
                                        Slog.w("PackageManager", "Unknown shared user:" + attributeValue2);
                                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                                    } else {
                                        parseLegacyPermissionsLPr(typedXmlPullParser, sharedUserSetting.getLegacyPermissionState(), i);
                                    }
                                }
                            }
                            c = 65535;
                            if (c != 0) {
                            }
                        } else {
                            if (name.equals("runtime-permissions")) {
                                c = 0;
                                if (c != 0) {
                                }
                            }
                            c = 65535;
                            if (c != 0) {
                            }
                        }
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x003a A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0039 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void parseLegacyPermissionsLPr(TypedXmlPullParser typedXmlPullParser, LegacyPermissionState legacyPermissionState, int i) {
            char c;
            synchronized (this.mLock) {
                int depth = typedXmlPullParser.getDepth();
                while (true) {
                    int next = typedXmlPullParser.next();
                    if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                        break;
                    }
                    if (next != 3 && next != 4) {
                        String name = typedXmlPullParser.getName();
                        if (name.hashCode() == 3242771 && name.equals("item")) {
                            c = 0;
                            if (c != 0) {
                                legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(typedXmlPullParser.getAttributeValue((String) null, "name"), true, typedXmlPullParser.getAttributeBoolean((String) null, "granted", true), typedXmlPullParser.getAttributeIntHex((String) null, "flags", 0)), i);
                            }
                        }
                        c = 65535;
                        if (c != 0) {
                        }
                    }
                }
            }
        }

        public final class MyHandler extends Handler {
            public MyHandler() {
                super(BackgroundThread.getHandler().getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                Runnable runnable = (Runnable) message.obj;
                long uptimeMillis = SystemClock.uptimeMillis() - RuntimePermissionPersistence.this.mLastWriteDoneTimeMillis;
                if (uptimeMillis < 500) {
                    Message obtainMessage = RuntimePermissionPersistence.this.mAsyncHandler.obtainMessage(i);
                    RuntimePermissionPersistence.this.mAsyncHandler.sendMessageDelayed(obtainMessage, 500 - uptimeMillis);
                } else {
                    RuntimePermissionPersistence.this.mInvokeWriteUserStateAsyncCallback.accept(Integer.valueOf(i));
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
        }

        public final class PersistenceHandler extends Handler {
            public PersistenceHandler() {
                super(BackgroundThread.getHandler().getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                RuntimePermissionPersistence.this.writePendingStates();
            }
        }
    }

    public PersistentPreferredIntentResolver getPersistentPreferredActivities(int i) {
        return (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.get(i);
    }

    public PreferredIntentResolver getPreferredActivities(int i) {
        return (PreferredIntentResolver) this.mPreferredActivities.get(i);
    }

    public CrossProfileIntentResolver getCrossProfileIntentResolver(int i) {
        return (CrossProfileIntentResolver) this.mCrossProfileIntentResolvers.get(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void clearPackagePreferredActivities(String str, SparseBooleanArray sparseBooleanArray, int i) {
        String kioskHomePackageAsUser;
        List asList;
        int i2;
        boolean z;
        IKioskMode asInterface = IKioskMode.Stub.asInterface(ServiceManager.getService("kioskmode"));
        ArrayList arrayList = null;
        if (asInterface != null) {
            try {
                kioskHomePackageAsUser = asInterface.getKioskHomePackageAsUser(i);
            } catch (RemoteException e) {
                Log.e("PackageSettings", "Failed talking with kiosk mode service", e);
            }
            if (kioskHomePackageAsUser != null) {
                asList = Arrays.asList(kioskHomePackageAsUser);
                z = false;
                for (i2 = 0; i2 < this.mPreferredActivities.size(); i2++) {
                    int keyAt = this.mPreferredActivities.keyAt(i2);
                    PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.valueAt(i2);
                    if (i == -1 || i == keyAt) {
                        Iterator filterIterator = preferredIntentResolver.filterIterator();
                        while (filterIterator.hasNext()) {
                            PreferredActivity preferredActivity = (PreferredActivity) filterIterator.next();
                            if (str == null || (preferredActivity.mPref.mComponent.getPackageName().equals(str) && preferredActivity.mPref.mAlways)) {
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                if (asList == null || !asList.contains(preferredActivity.mPref.mComponent.getPackageName())) {
                                    arrayList.add(preferredActivity);
                                }
                            }
                        }
                        if (arrayList != null) {
                            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                PreferredActivity preferredActivity2 = (PreferredActivity) arrayList.get(i3);
                                preferredIntentResolver.removeFilter((WatchedIntentFilter) preferredActivity2);
                                PreferredActivityLog.logPreferenceChange(preferredActivity2, "Removing preference<clear>");
                            }
                            z = true;
                            sparseBooleanArray.put(keyAt, true);
                        }
                    }
                }
                if (z) {
                    return;
                }
                onChanged();
                return;
            }
        }
        asList = null;
        z = false;
        while (i2 < this.mPreferredActivities.size()) {
        }
        if (z) {
        }
    }

    public boolean clearPackagePersistentPreferredActivities(String str, int i) {
        ArrayList arrayList = null;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPersistentPreferredActivities.size(); i2++) {
            int keyAt = this.mPersistentPreferredActivities.keyAt(i2);
            PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.valueAt(i2);
            if (i == keyAt) {
                Iterator filterIterator = persistentPreferredIntentResolver.filterIterator();
                while (filterIterator.hasNext()) {
                    PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) filterIterator.next();
                    if (persistentPreferredActivity.mComponent.getPackageName().equals(str)) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(persistentPreferredActivity);
                    }
                }
                if (arrayList != null) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        persistentPreferredIntentResolver.removeFilter((WatchedIntentFilter) arrayList.get(i3));
                    }
                    z = true;
                }
            }
        }
        if (z) {
            onChanged();
        }
        return z;
    }

    public boolean clearPersistentPreferredActivity(IntentFilter intentFilter, int i) {
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.get(i);
        Iterator filterIterator = persistentPreferredIntentResolver.filterIterator();
        ArrayList arrayList = null;
        while (filterIterator.hasNext()) {
            PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) filterIterator.next();
            if (IntentFilter.filterEquals(persistentPreferredActivity.getIntentFilter(), intentFilter)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(persistentPreferredActivity);
            }
        }
        boolean z = false;
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                persistentPreferredIntentResolver.removeFilter((WatchedIntentFilter) arrayList.get(i2));
            }
            z = true;
        }
        if (z) {
            onChanged();
        }
        return z;
    }

    public ArrayList systemReady(ComponentResolver componentResolver) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.mPreferredActivities.size(); i++) {
            PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.valueAt(i);
            arrayList2.clear();
            for (PreferredActivity preferredActivity : preferredIntentResolver.filterSet()) {
                if (!componentResolver.isActivityDefined(preferredActivity.mPref.mComponent)) {
                    arrayList2.add(preferredActivity);
                }
            }
            if (arrayList2.size() > 0) {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    PreferredActivity preferredActivity2 = (PreferredActivity) arrayList2.get(i2);
                    Slog.w("PackageSettings", "Removing dangling preferred activity: " + preferredActivity2.mPref.mComponent);
                    preferredIntentResolver.removeFilter((WatchedIntentFilter) preferredActivity2);
                }
                arrayList.add(Integer.valueOf(this.mPreferredActivities.keyAt(i)));
            }
        }
        onChanged();
        return arrayList;
    }

    public void dumpPreferred(PrintWriter printWriter, DumpState dumpState, String str) {
        for (int i = 0; i < this.mPreferredActivities.size(); i++) {
            PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.valueAt(i);
            int keyAt = this.mPreferredActivities.keyAt(i);
            if (preferredIntentResolver.dump(printWriter, dumpState.getTitlePrinted() ? "\nPreferred Activities User " + keyAt + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR : "Preferred Activities User " + keyAt + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR, "  ", str, true, false)) {
                dumpState.setTitlePrinted(true);
            }
        }
    }

    public boolean isInstallerPackage(String str) {
        return this.mInstallerPackages.contains(str);
    }
}
