package android.app;

import android.app.PropertyInvalidatedCache;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ActivityInfo;
import android.content.pm.ApkChecksum;
import android.content.pm.ApplicationInfo;
import android.content.pm.ArchivedPackageInfo;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.ChangedPackages;
import android.content.pm.ComponentInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IMemorySaverPackageMoveObserver;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstallSourceInfo;
import android.content.pm.InstantAppInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.SystemFeaturesCache;
import android.content.pm.UserInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.ArtManager;
import android.content.res.ApkAssets;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.sec.enterprise.ApplicationPolicy;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LauncherIcons;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.internal.os.SomeArgs;
import com.android.internal.pm.RoSystemFeatures;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.pm.AbiAppHelper;
import com.samsung.android.core.pm.containerservice.AsecUtils;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.PMRune;
import dalvik.system.PathClassLoader;
import dalvik.system.VMRuntime;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import libcore.util.EmptyArray;

/* loaded from: classes.dex */
public class ApplicationPackageManager extends PackageManager {
    public static final String APP_PERMISSION_BUTTON_ALLOW_ALWAYS = "app_permission_button_allow_always";
    private static final String CACHE_KEY_PACKAGES_FOR_UID_API = "get_packages_for_uid";
    private static final boolean DEBUG_ICONS = false;
    private static final int DEFAULT_CHECKSUMS = 127;
    private static final int DEFAULT_EPHEMERAL_COOKIE_MAX_SIZE_BYTES = 16384;
    private static final String HAS_SYSTEM_FEATURE_API = "has_system_feature";
    private static final String LIVE_ICON_SUFFIX = ".LiveIconLoader";
    public static final String PERMISSION_CONTROLLER_RESOURCE_PACKAGE = "com.android.permissioncontroller";
    private static final String TAG = "ApplicationPackageManager";
    private static final PropertyInvalidatedCache<HasSystemFeatureQuery, Boolean> mHasSystemFeatureCache;
    private static final int sDefaultFlags = 1024;
    public static final PropertyInvalidatedCache<Integer, GetPackagesForUidResult> sGetPackagesForUidCache;
    private static ArrayMap<ResourceName, WeakReference<Drawable.ConstantState>> sIconCache;
    private static final ArrayMap<String, Method> sLiveIconLoaders = new ArrayMap<>();
    private static final ArrayMap<String, String> sLiveIconPackageMatchers = new ArrayMap<>();
    private static ArrayMap<ResourceName, WeakReference<CharSequence>> sStringCache;
    private static final Object sSync;
    private volatile ArtManager mArtManager;
    private final ContextImpl mContext;
    private volatile DevicePolicyManager mDevicePolicyManager;
    private volatile PackageInstaller mInstaller;
    private KnoxSdkHook mKnoxSdkHook;
    private final IPackageManager mPM;
    private volatile PermissionManager mPermissionManager;
    private volatile String mPermissionsControllerPackageName;
    private volatile UserManager mUserManager;
    private AbiAppHelper mAbiAppHelper = new AbiAppHelper();
    private final ArrayList<MoveCallbackDelegate> mDelegates = new ArrayList<>();
    private final ArraySet<IRemoteCallback> mPackageMonitorCallbacks = new ArraySet<>();
    volatile int mCachedSafeMode = -1;
    private volatile boolean mUserUnlocked = false;
    private SemAppIconSolution mAppIconSolution = null;
    private ApplicationPolicy mApplicationPolicy = null;
    private final String FEATURE_ADAPTIVEICON_SHADOW = "ADAPTIVEICON_SHADOW";
    private final String FEATURE_COLOR_NO_ADAPTIVE = "COLOR_NO_ADAPTIVE";
    private final String FEATURE_COLOR_ONLY_BG = "COLOR_ONLY_BG";
    private final boolean mUseSystemFeaturesCache = isSystemFeaturesCacheEnabledAndAvailable();

    interface KnoxSdkHook {
        default boolean applyRuntimePermissionsForAllApplicationsForMdm(int i, int i2) {
            return false;
        }

        default boolean applyRuntimePermissionsForMdm(String str, List<String> list, int i, int i2) {
            return false;
        }

        default List<String> getRequestedRuntimePermissionsForMdm(String str) {
            return null;
        }
    }

    private static void configurationChanged$ravenwood() {
    }

    @Override // android.content.pm.PackageManager
    public boolean semCheckComponentMetadataForIconTray(String str, String str2) {
        return false;
    }

    @Override // android.content.pm.PackageManager
    public Drawable semGetCscPackageItemIcon(String str) {
        return null;
    }

    @Override // android.content.pm.PackageManager
    public CharSequence semGetCscPackageItemText(String str) {
        return null;
    }

    static {
        PropertyInvalidatedCache.Args args = new PropertyInvalidatedCache.Args("system_server");
        PropertyInvalidatedCache.QueryHandler queryHandler = null;
        mHasSystemFeatureCache = new PropertyInvalidatedCache<HasSystemFeatureQuery, Boolean>(args.api(HAS_SYSTEM_FEATURE_API).maxEntries(SDK_FEATURE_COUNT).isolateUids(false), HAS_SYSTEM_FEATURE_API, queryHandler) { // from class: android.app.ApplicationPackageManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Boolean recompute(HasSystemFeatureQuery hasSystemFeatureQuery) {
                try {
                    ActivityThread.currentActivityThread();
                    return Boolean.valueOf(ActivityThread.getPackageManager().hasSystemFeature(hasSystemFeatureQuery.name, hasSystemFeatureQuery.version));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        sGetPackagesForUidCache = new PropertyInvalidatedCache<Integer, GetPackagesForUidResult>(new PropertyInvalidatedCache.Args("system_server").maxEntries(1024).api(CACHE_KEY_PACKAGES_FOR_UID_API).cacheNulls(true), CACHE_KEY_PACKAGES_FOR_UID_API, queryHandler) { // from class: android.app.ApplicationPackageManager.3
            @Override // android.app.PropertyInvalidatedCache
            public GetPackagesForUidResult recompute(Integer num) {
                try {
                    ActivityThread.currentActivityThread();
                    return new GetPackagesForUidResult(ActivityThread.getPackageManager().getPackagesForUid(num.intValue()));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }

            @Override // android.app.PropertyInvalidatedCache
            public String queryToString(Integer num) {
                num.intValue();
                return String.format("uid=%d", num);
            }
        };
        sSync = new Object();
        sIconCache = new ArrayMap<>();
        sStringCache = new ArrayMap<>();
    }

    UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    DevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        }
        return this.mDevicePolicyManager;
    }

    private PermissionManager getPermissionManager() {
        if (this.mPermissionManager == null) {
            this.mPermissionManager = (PermissionManager) this.mContext.getSystemService(PermissionManager.class);
        }
        return this.mPermissionManager;
    }

    @Override // android.content.pm.PackageManager
    public int getUserId() {
        return this.mContext.getUserId();
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo getPackageInfo(String str, int i) throws PackageManager.NameNotFoundException {
        return getPackageInfo(str, PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo getPackageInfo(String str, PackageManager.PackageInfoFlags packageInfoFlags) throws PackageManager.NameNotFoundException {
        return getPackageInfoAsUser(str, packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo getPackageInfo(VersionedPackage versionedPackage, int i) throws PackageManager.NameNotFoundException {
        return getPackageInfo(versionedPackage, PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo getPackageInfo(VersionedPackage versionedPackage, PackageManager.PackageInfoFlags packageInfoFlags) throws PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            PackageInfo packageInfoVersioned = this.mPM.getPackageInfoVersioned(versionedPackage, updateFlagsForPackage(packageInfoFlags.getValue(), userId), userId);
            if (packageInfoVersioned != null) {
                return packageInfoVersioned;
            }
            throw new PackageManager.NameNotFoundException(versionedPackage.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo getPackageInfoAsUser(String str, int i, int i2) throws PackageManager.NameNotFoundException {
        return getPackageInfoAsUser(str, PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo getPackageInfoAsUser(String str, PackageManager.PackageInfoFlags packageInfoFlags, int i) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfoAsUserCached = getPackageInfoAsUserCached(str, updateFlagsForPackage(packageInfoFlags.getValue(), i), i);
        if (packageInfoAsUserCached != null) {
            return packageInfoAsUserCached;
        }
        throw new PackageManager.NameNotFoundException(str);
    }

    @Override // android.content.pm.PackageManager
    public String[] currentToCanonicalPackageNames(String[] strArr) {
        try {
            return this.mPM.currentToCanonicalPackageNames(strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] canonicalToCurrentPackageNames(String[] strArr) {
        try {
            return this.mPM.canonicalToCurrentPackageNames(strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Intent getLaunchIntentForPackage(String str) {
        return getLaunchIntentForPackage(str, false);
    }

    @Override // android.content.pm.PackageManager
    public Intent getLaunchIntentForPackage(String str, boolean z) {
        PackageManager.ResolveInfoFlags of = PackageManager.ResolveInfoFlags.of(z ? 786432L : 0L);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_INFO);
        intent.setPackage(str);
        List<ResolveInfo> queryIntentActivities = queryIntentActivities(intent, of);
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            intent.removeCategory(Intent.CATEGORY_INFO);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setPackage(str);
            queryIntentActivities = queryIntentActivities(intent, of);
        }
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(queryIntentActivities.get(0).activityInfo.packageName, queryIntentActivities.get(0).activityInfo.name);
        return intent2;
    }

    @Override // android.content.pm.PackageManager
    public Intent getLeanbackLaunchIntentForPackage(String str) {
        return getLaunchIntentForPackageAndCategory(str, Intent.CATEGORY_LEANBACK_LAUNCHER);
    }

    @Override // android.content.pm.PackageManager
    public Intent getCarLaunchIntentForPackage(String str) {
        return getLaunchIntentForPackageAndCategory(str, Intent.CATEGORY_CAR_LAUNCHER);
    }

    private Intent getLaunchIntentForPackageAndCategory(String str, String str2) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(str2);
        intent.setPackage(str);
        List<ResolveInfo> queryIntentActivities = queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(queryIntentActivities.get(0).activityInfo.packageName, queryIntentActivities.get(0).activityInfo.name);
        return intent2;
    }

    @Override // android.content.pm.PackageManager
    public IntentSender getLaunchIntentSenderForPackage(String str) {
        try {
            return this.mPM.getLaunchIntentSenderForPackage(str, this.mContext.getPackageName(), this.mContext.getAttributionTag(), getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int[] getPackageGids(String str) throws PackageManager.NameNotFoundException {
        return getPackageGids(str, 0);
    }

    @Override // android.content.pm.PackageManager
    public int[] getPackageGids(String str, int i) throws PackageManager.NameNotFoundException {
        return getPackageGids(str, PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public int[] getPackageGids(String str, PackageManager.PackageInfoFlags packageInfoFlags) throws PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            int[] packageGids = this.mPM.getPackageGids(str, updateFlagsForPackage(packageInfoFlags.getValue(), userId), userId);
            if (packageGids != null) {
                return packageGids;
            }
            throw new PackageManager.NameNotFoundException(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUid(String str, int i) throws PackageManager.NameNotFoundException {
        return getPackageUid(str, PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUid(String str, PackageManager.PackageInfoFlags packageInfoFlags) throws PackageManager.NameNotFoundException {
        return getPackageUidAsUser(str, packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUidAsUser(String str, int i) throws PackageManager.NameNotFoundException {
        return getPackageUidAsUser(str, 0, i);
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUidAsUser(String str, int i, int i2) throws PackageManager.NameNotFoundException {
        return getPackageUidAsUser(str, PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUidAsUser(String str, PackageManager.PackageInfoFlags packageInfoFlags, int i) throws PackageManager.NameNotFoundException {
        try {
            int packageUid = this.mPM.getPackageUid(str, updateFlagsForPackage(packageInfoFlags.getValue(), i), i);
            if (packageUid >= 0) {
                return packageUid;
            }
            throw new PackageManager.NameNotFoundException(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PermissionGroupInfo> getAllPermissionGroups(int i) {
        return getPermissionManager().getAllPermissionGroups(i);
    }

    @Override // android.content.pm.PackageManager
    public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws PackageManager.NameNotFoundException {
        PermissionGroupInfo permissionGroupInfo = getPermissionManager().getPermissionGroupInfo(str, i);
        if (permissionGroupInfo != null) {
            return permissionGroupInfo;
        }
        throw new PackageManager.NameNotFoundException(str);
    }

    @Override // android.content.pm.PackageManager
    public PermissionInfo getPermissionInfo(String str, int i) throws PackageManager.NameNotFoundException {
        PermissionInfo permissionInfo = getPermissionManager().getPermissionInfo(str, i);
        if (permissionInfo != null) {
            return permissionInfo;
        }
        throw new PackageManager.NameNotFoundException(str);
    }

    @Override // android.content.pm.PackageManager
    public List<PermissionInfo> queryPermissionsByGroup(String str, int i) throws PackageManager.NameNotFoundException {
        List<PermissionInfo> queryPermissionsByGroup = getPermissionManager().queryPermissionsByGroup(str, i);
        if (queryPermissionsByGroup != null) {
            return queryPermissionsByGroup;
        }
        throw new PackageManager.NameNotFoundException(str);
    }

    @Override // android.content.pm.PackageManager
    public void getPlatformPermissionsForGroup(String str, Executor executor, Consumer<List<String>> consumer) {
        ((PermissionControllerManager) this.mContext.getSystemService(PermissionControllerManager.class)).getPlatformPermissionsForGroup(str, executor, consumer);
    }

    @Override // android.content.pm.PackageManager
    public void getGroupOfPlatformPermission(String str, Executor executor, Consumer<String> consumer) {
        ((PermissionControllerManager) this.mContext.getSystemService(PermissionControllerManager.class)).getGroupOfPlatformPermission(str, executor, consumer);
    }

    @Override // android.content.pm.PackageManager
    public boolean arePermissionsIndividuallyControlled() {
        return this.mContext.getResources().getBoolean(R.bool.config_permissionsIndividuallyControlled);
    }

    @Override // android.content.pm.PackageManager
    public boolean isWirelessConsentModeEnabled() {
        return this.mContext.getResources().getBoolean(R.bool.config_wirelessConsentRequired);
    }

    @Override // android.content.pm.PackageManager
    public ApplicationInfo getApplicationInfo(String str, int i) throws PackageManager.NameNotFoundException {
        return getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ApplicationInfo getApplicationInfo(String str, PackageManager.ApplicationInfoFlags applicationInfoFlags) throws PackageManager.NameNotFoundException {
        return getApplicationInfoAsUser(str, applicationInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public ApplicationInfo getApplicationInfoAsUser(String str, int i, int i2) throws PackageManager.NameNotFoundException {
        return getApplicationInfoAsUser(str, PackageManager.ApplicationInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public ApplicationInfo getApplicationInfoAsUser(String str, PackageManager.ApplicationInfoFlags applicationInfoFlags, int i) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfoAsUserCached = getApplicationInfoAsUserCached(str, updateFlagsForApplication(applicationInfoFlags.getValue(), i), i);
        if (applicationInfoAsUserCached == null) {
            throw new PackageManager.NameNotFoundException(str);
        }
        return maybeAdjustApplicationInfo(applicationInfoAsUserCached);
    }

    private static ApplicationInfo maybeAdjustApplicationInfo(ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null) {
            String vmInstructionSet = VMRuntime.getRuntime().vmInstructionSet();
            String instructionSet = VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi);
            String str = SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
            if (!str.isEmpty()) {
                instructionSet = str;
            }
            if (vmInstructionSet.equals(instructionSet)) {
                ApplicationInfo applicationInfo2 = new ApplicationInfo(applicationInfo);
                applicationInfo2.nativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
                return applicationInfo2;
            }
        }
        return applicationInfo;
    }

    @Override // android.content.pm.PackageManager
    public int getTargetSdkVersion(String str) throws PackageManager.NameNotFoundException {
        try {
            int targetSdkVersion = this.mPM.getTargetSdkVersion(str);
            if (targetSdkVersion != -1) {
                return targetSdkVersion;
            }
            throw new PackageManager.NameNotFoundException(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ActivityInfo getActivityInfo(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ActivityInfo getActivityInfo(ComponentName componentName, PackageManager.ComponentInfoFlags componentInfoFlags) throws PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            ActivityInfo activityInfo = this.mPM.getActivityInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (activityInfo != null) {
                return activityInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ActivityInfo getReceiverInfo(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        return getReceiverInfo(componentName, PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ActivityInfo getReceiverInfo(ComponentName componentName, PackageManager.ComponentInfoFlags componentInfoFlags) throws PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            ActivityInfo receiverInfo = this.mPM.getReceiverInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (receiverInfo != null) {
                return receiverInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ServiceInfo getServiceInfo(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        return getServiceInfo(componentName, PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ServiceInfo getServiceInfo(ComponentName componentName, PackageManager.ComponentInfoFlags componentInfoFlags) throws PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            ServiceInfo serviceInfo = this.mPM.getServiceInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (serviceInfo != null) {
                return serviceInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo getProviderInfo(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        return getProviderInfo(componentName, PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo getProviderInfo(ComponentName componentName, PackageManager.ComponentInfoFlags componentInfoFlags) throws PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            ProviderInfo providerInfo = this.mPM.getProviderInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (providerInfo != null) {
                return providerInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] getSystemSharedLibraryNames() {
        try {
            return this.mPM.getSystemSharedLibraryNames();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<SharedLibraryInfo> getSharedLibraries(int i) {
        return getSharedLibraries(PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<SharedLibraryInfo> getSharedLibraries(PackageManager.PackageInfoFlags packageInfoFlags) {
        return getSharedLibrariesAsUser(packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<SharedLibraryInfo> getSharedLibrariesAsUser(int i, int i2) {
        return getSharedLibrariesAsUser(PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<SharedLibraryInfo> getSharedLibrariesAsUser(PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        try {
            ParceledListSlice sharedLibraries = this.mPM.getSharedLibraries(this.mContext.getOpPackageName(), packageInfoFlags.getValue(), i);
            if (sharedLibraries == null) {
                return Collections.EMPTY_LIST;
            }
            return sharedLibraries.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<SharedLibraryInfo> getDeclaredSharedLibraries(String str, int i) {
        return getDeclaredSharedLibraries(str, PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<SharedLibraryInfo> getDeclaredSharedLibraries(String str, PackageManager.PackageInfoFlags packageInfoFlags) {
        try {
            ParceledListSlice declaredSharedLibraries = this.mPM.getDeclaredSharedLibraries(str, packageInfoFlags.getValue(), this.mContext.getUserId());
            return declaredSharedLibraries != null ? declaredSharedLibraries.getList() : Collections.EMPTY_LIST;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getServicesSystemSharedLibraryPackageName() {
        try {
            return this.mPM.getServicesSystemSharedLibraryPackageName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getSharedSystemSharedLibraryPackageName() {
        try {
            return this.mPM.getSharedSystemSharedLibraryPackageName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ChangedPackages getChangedPackages(int i) {
        try {
            return this.mPM.getChangedPackages(i, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public FeatureInfo[] getSystemAvailableFeatures() {
        try {
            ParceledListSlice systemAvailableFeatures = this.mPM.getSystemAvailableFeatures();
            if (systemAvailableFeatures == null) {
                return new FeatureInfo[0];
            }
            List list = systemAvailableFeatures.getList();
            int size = list.size();
            FeatureInfo[] featureInfoArr = new FeatureInfo[size];
            for (int i = 0; i < size; i++) {
                featureInfoArr[i] = (FeatureInfo) list.get(i);
            }
            return featureInfoArr;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSystemFeature(String str) {
        return hasSystemFeature(str, 0);
    }

    private static final class HasSystemFeatureQuery extends Record {
        private final String name;
        private final int version;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof HasSystemFeatureQuery)) {
                return false;
            }
            HasSystemFeatureQuery hasSystemFeatureQuery = (HasSystemFeatureQuery) obj;
            return this.version == hasSystemFeatureQuery.version && Objects.equals(this.name, hasSystemFeatureQuery.name);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.name, Integer.valueOf(this.version)};
        }

        private HasSystemFeatureQuery(String name, int version) {
            this.name = name;
            this.version = version;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.version, this.name);
        }

        public String name() {
            return this.name;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), HasSystemFeatureQuery.class, "name;version");
        }

        public int version() {
            return this.version;
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSystemFeature(String str, int i) {
        Boolean maybeHasFeature;
        if (MaintenanceModeUtils.isMaintenanceModeFeature(str)) {
            return MaintenanceModeUtils.hasSystemFeature();
        }
        Boolean maybeHasFeature2 = RoSystemFeatures.maybeHasFeature(str, i);
        if (maybeHasFeature2 != null) {
            return maybeHasFeature2.booleanValue();
        }
        if (this.mUseSystemFeaturesCache && (maybeHasFeature = SystemFeaturesCache.getInstance().maybeHasFeature(str, i)) != null) {
            return maybeHasFeature.booleanValue();
        }
        return mHasSystemFeatureCache.query(new HasSystemFeatureQuery(str, i)).booleanValue();
    }

    public static void invalidateHasSystemFeatureCache() {
        mHasSystemFeatureCache.invalidateCache();
    }

    @Override // android.content.pm.PackageManager
    public int checkPermission(String str, String str2) {
        return getPermissionManager().checkPackageNamePermission(str, str2, this.mContext.getDeviceId(), getUserId());
    }

    @Override // android.content.pm.PackageManager
    public boolean isPermissionRevokedByPolicy(String str, String str2) {
        return getPermissionManager().isPermissionRevokedByPolicy(str2, str);
    }

    @Override // android.content.pm.PackageManager
    public String getPermissionControllerPackageName() {
        if (this.mPermissionsControllerPackageName == null) {
            try {
                this.mPermissionsControllerPackageName = this.mPM.getPermissionControllerPackageName();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mPermissionsControllerPackageName;
    }

    @Override // android.content.pm.PackageManager
    public String getSdkSandboxPackageName() {
        try {
            return this.mPM.getSdkSandboxPackageName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean addPermission(PermissionInfo permissionInfo) {
        return getPermissionManager().addPermission(permissionInfo, false);
    }

    @Override // android.content.pm.PackageManager
    public boolean addPermissionAsync(PermissionInfo permissionInfo) {
        return getPermissionManager().addPermission(permissionInfo, true);
    }

    @Override // android.content.pm.PackageManager
    public void removePermission(String str) {
        getPermissionManager().removePermission(str);
    }

    @Override // android.content.pm.PackageManager
    public void grantRuntimePermission(String str, String str2, UserHandle userHandle) {
        getPermissionManager().grantRuntimePermission(str, str2, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public void revokeRuntimePermission(String str, String str2, UserHandle userHandle) {
        revokeRuntimePermission(str, str2, userHandle, null);
    }

    @Override // android.content.pm.PackageManager
    public void revokeRuntimePermission(String str, String str2, UserHandle userHandle, String str3) {
        getPermissionManager().revokeRuntimePermission(str, str2, userHandle, str3);
    }

    @Override // android.content.pm.PackageManager
    public int getPermissionFlags(String str, String str2, UserHandle userHandle) {
        return getPermissionManager().getPermissionFlags(str2, str, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public void updatePermissionFlags(String str, String str2, int i, int i2, UserHandle userHandle) {
        getPermissionManager().updatePermissionFlags(str2, str, i, i2, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public Set<String> getWhitelistedRestrictedPermissions(String str, int i) {
        return getPermissionManager().getAllowlistedRestrictedPermissions(str, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean addWhitelistedRestrictedPermission(String str, String str2, int i) {
        return getPermissionManager().addAllowlistedRestrictedPermission(str, str2, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean setAutoRevokeWhitelisted(String str, boolean z) {
        return getPermissionManager().setAutoRevokeExempted(str, z);
    }

    @Override // android.content.pm.PackageManager
    public boolean isAutoRevokeWhitelisted(String str) {
        return getPermissionManager().isAutoRevokeExempted(str);
    }

    @Override // android.content.pm.PackageManager
    public boolean removeWhitelistedRestrictedPermission(String str, String str2, int i) {
        return getPermissionManager().removeAllowlistedRestrictedPermission(str, str2, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean shouldShowRequestPermissionRationale(String str) {
        return getPermissionManager().shouldShowRequestPermissionRationale(str);
    }

    @Override // android.content.pm.PackageManager
    public Intent buildRequestPermissionsIntent(String[] strArr) {
        Intent buildRequestPermissionsIntent = super.buildRequestPermissionsIntent(strArr);
        buildRequestPermissionsIntent.putExtra(PackageManager.EXTRA_REQUEST_PERMISSIONS_DEVICE_ID, this.mContext.getDeviceId());
        return buildRequestPermissionsIntent;
    }

    @Override // android.content.pm.PackageManager
    public CharSequence getBackgroundPermissionOptionLabel() {
        try {
            Context createPackageContext = this.mContext.createPackageContext(getPermissionControllerPackageName(), 0);
            int identifier = createPackageContext.getResources().getIdentifier(APP_PERMISSION_BUTTON_ALLOW_ALWAYS, "string", PERMISSION_CONTROLLER_RESOURCE_PACKAGE);
            if (identifier != 0) {
                return createPackageContext.getText(identifier);
            }
            return "";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Permission controller not found.", e);
            return "";
        }
    }

    @Override // android.content.pm.PackageManager
    public int checkSignatures(String str, String str2) {
        try {
            return this.mPM.checkSignatures(str, str2, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int checkSignatures(int i, int i2) {
        try {
            return this.mPM.checkUidSignatures(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSigningCertificate(String str, byte[] bArr, int i) {
        try {
            return this.mPM.hasSigningCertificate(str, bArr, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSigningCertificate(int i, byte[] bArr, int i2) {
        try {
            return this.mPM.hasUidSigningCertificate(i, bArr, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setPageSizeAppCompatFlagsSettingsOverride(String str, boolean z) {
        try {
            this.mPM.setPageSizeAppCompatFlagsSettingsOverride(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPageSizeCompatEnabled(String str) {
        try {
            return this.mPM.isPageSizeCompatEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getPageSizeCompatWarningMessage(String str) {
        try {
            return this.mPM.getPageSizeCompatWarningMessage(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static List<byte[]> encodeCertificates(List<Certificate> list) throws CertificateEncodingException {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (Certificate certificate : list) {
            if (!(certificate instanceof X509Certificate)) {
                throw new CertificateEncodingException("Only X509 certificates supported.");
            }
            arrayList.add(certificate.getEncoded());
        }
        return arrayList;
    }

    @Override // android.content.pm.PackageManager
    public void requestChecksums(String str, boolean z, int i, List<Certificate> list, final PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) throws CertificateEncodingException, PackageManager.NameNotFoundException {
        Objects.requireNonNull(str);
        Objects.requireNonNull(onChecksumsReadyListener);
        Objects.requireNonNull(list);
        if (list == TRUST_ALL) {
            list = null;
        } else if (list == TRUST_NONE) {
            list = Collections.EMPTY_LIST;
        } else if (list.isEmpty()) {
            throw new IllegalArgumentException("trustedInstallers has to be one of TRUST_ALL/TRUST_NONE or a non-empty list of certificates.");
        }
        try {
            this.mPM.requestPackageChecksums(str, z, 127, i, encodeCertificates(list), new IOnChecksumsReadyListener.Stub(this) { // from class: android.app.ApplicationPackageManager.2
                @Override // android.content.pm.IOnChecksumsReadyListener
                public void onChecksumsReady(List<ApkChecksum> list2) throws RemoteException {
                    onChecksumsReadyListener.onChecksumsReady(list2);
                }
            }, getUserId());
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private static class GetPackagesForUidResult {
        private final String[] mValue;

        GetPackagesForUidResult(String[] strArr) {
            this.mValue = strArr;
        }

        public String[] value() {
            return this.mValue;
        }

        public String toString() {
            return Arrays.toString(this.mValue);
        }

        public int hashCode() {
            return Arrays.hashCode(this.mValue);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof GetPackagesForUidResult)) {
                return false;
            }
            String[] strArr = ((GetPackagesForUidResult) obj).mValue;
            String[] strArr2 = this.mValue;
            if ((strArr == null) != (strArr2 == null)) {
                return false;
            }
            if (strArr == null) {
                return true;
            }
            Arrays.sort(strArr);
            Arrays.sort(strArr2);
            return Arrays.equals(strArr2, strArr);
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] getPackagesForUid(int i) {
        return sGetPackagesForUidCache.query(Integer.valueOf(i)).value();
    }

    public static void disableGetPackagesForUidCache() {
        sGetPackagesForUidCache.disableLocal();
    }

    public static void invalidateGetPackagesForUidCache() {
        sGetPackagesForUidCache.invalidateCache();
    }

    @Override // android.content.pm.PackageManager
    public String getNameForUid(int i) {
        try {
            return this.mPM.getNameForUid(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] getNamesForUids(int[] iArr) {
        try {
            return this.mPM.getNamesForUids(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getUidForSharedUser(String str) throws PackageManager.NameNotFoundException {
        try {
            int uidForSharedUser = this.mPM.getUidForSharedUser(str);
            if (uidForSharedUser != -1) {
                return uidForSharedUser;
            }
            throw new PackageManager.NameNotFoundException("No shared userid for user:" + str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ModuleInfo> getInstalledModules(int i) {
        try {
            return this.mPM.getInstalledModules(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ModuleInfo getModuleInfo(String str, int i) throws PackageManager.NameNotFoundException {
        try {
            ModuleInfo moduleInfo = this.mPM.getModuleInfo(str, i);
            if (moduleInfo != null) {
                return moduleInfo;
            }
            throw new PackageManager.NameNotFoundException("No module info for package: " + str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getInstalledPackages(int i) {
        return getInstalledPackages(PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getInstalledPackages(PackageManager.PackageInfoFlags packageInfoFlags) {
        return getInstalledPackagesAsUser(packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getInstalledPackagesAsUser(int i, int i2) {
        return getInstalledPackagesAsUser(PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getInstalledPackagesAsUser(PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        try {
            ParceledListSlice installedPackages = this.mPM.getInstalledPackages(updateFlagsForPackage(packageInfoFlags.getValue(), i), i);
            if (installedPackages == null) {
                return Collections.EMPTY_LIST;
            }
            return installedPackages.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public PersistableBundle getAppMetadata(String str) throws PackageManager.NameNotFoundException {
        PersistableBundle readFromStream;
        try {
            ParcelFileDescriptor appMetadataFd = this.mPM.getAppMetadataFd(str, getUserId());
            if (appMetadataFd != null) {
                try {
                    ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(appMetadataFd);
                    try {
                        readFromStream = PersistableBundle.readFromStream(autoCloseInputStream);
                        autoCloseInputStream.close();
                    } finally {
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                readFromStream = null;
            }
            return readFromStream != null ? readFromStream : new PersistableBundle();
        } catch (ParcelableException e2) {
            e2.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e2);
        } catch (RemoteException e3) {
            throw e3.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getAppMetadataSource(String str) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(str, "packageName cannot be null");
        try {
            return this.mPM.getAppMetadataSource(str, getUserId());
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getPackagesHoldingPermissions(String[] strArr, int i) {
        return getPackagesHoldingPermissions(strArr, PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getPackagesHoldingPermissions(String[] strArr, PackageManager.PackageInfoFlags packageInfoFlags) {
        int userId = getUserId();
        try {
            ParceledListSlice packagesHoldingPermissions = this.mPM.getPackagesHoldingPermissions(strArr, updateFlagsForPackage(packageInfoFlags.getValue(), userId), userId);
            if (packagesHoldingPermissions == null) {
                return Collections.EMPTY_LIST;
            }
            return packagesHoldingPermissions.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ApplicationInfo> getInstalledApplications(int i) {
        return getInstalledApplicationsAsUser(i, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<ApplicationInfo> getInstalledApplications(PackageManager.ApplicationInfoFlags applicationInfoFlags) {
        return getInstalledApplicationsAsUser(applicationInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<ApplicationInfo> getInstalledApplicationsAsUser(int i, int i2) {
        return getInstalledApplicationsAsUser(PackageManager.ApplicationInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<ApplicationInfo> getInstalledApplicationsAsUser(PackageManager.ApplicationInfoFlags applicationInfoFlags, int i) {
        try {
            ParceledListSlice installedApplications = this.mPM.getInstalledApplications(updateFlagsForApplication(applicationInfoFlags.getValue(), i), i);
            if (installedApplications == null) {
                return Collections.EMPTY_LIST;
            }
            return installedApplications.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<InstantAppInfo> getInstantApps() {
        try {
            ParceledListSlice instantApps = this.mPM.getInstantApps(getUserId());
            if (instantApps != null) {
                return instantApps.getList();
            }
            return Collections.EMPTY_LIST;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Drawable getInstantAppIcon(String str) {
        try {
            Bitmap instantAppIcon = this.mPM.getInstantAppIcon(str, getUserId());
            if (instantAppIcon != null) {
                return new BitmapDrawable((Resources) null, instantAppIcon);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isInstantApp() {
        return isInstantApp(this.mContext.getPackageName());
    }

    @Override // android.content.pm.PackageManager
    public boolean isInstantApp(String str) {
        try {
            return this.mPM.isInstantApp(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getInstantAppCookieMaxBytes() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.EPHEMERAL_COOKIE_MAX_SIZE_BYTES, 16384);
    }

    @Override // android.content.pm.PackageManager
    public int getInstantAppCookieMaxSize() {
        return getInstantAppCookieMaxBytes();
    }

    @Override // android.content.pm.PackageManager
    public byte[] getInstantAppCookie() {
        try {
            byte[] instantAppCookie = this.mPM.getInstantAppCookie(this.mContext.getPackageName(), getUserId());
            return instantAppCookie != null ? instantAppCookie : EmptyArray.BYTE;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearInstantAppCookie() {
        updateInstantAppCookie(null);
    }

    @Override // android.content.pm.PackageManager
    public void updateInstantAppCookie(byte[] bArr) {
        if (bArr != null && bArr.length > getInstantAppCookieMaxBytes()) {
            throw new IllegalArgumentException("instant cookie longer than " + getInstantAppCookieMaxBytes());
        }
        try {
            this.mPM.setInstantAppCookie(this.mContext.getPackageName(), bArr, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean setInstantAppCookie(byte[] bArr) {
        try {
            return this.mPM.setInstantAppCookie(this.mContext.getPackageName(), bArr, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveActivity(Intent intent, int i) {
        return resolveActivity(intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveActivity(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return resolveActivityAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveActivityAsUser(Intent intent, int i, int i2) {
        return resolveActivityAsUser(intent, PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveActivityAsUser(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            return this.mPM.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentActivities(Intent intent, int i) {
        return queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentActivities(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryIntentActivitiesAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int i, int i2) {
        return queryIntentActivitiesAsUser(intent, PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            ParceledListSlice queryIntentActivities = this.mPM.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentActivities == null) {
                return Collections.EMPTY_LIST;
            }
            return queryIntentActivities.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentActivityOptions(ComponentName componentName, Intent[] intentArr, Intent intent, int i) {
        return queryIntentActivityOptions(componentName, intentArr == null ? null : new ArrayList(Arrays.asList(intentArr)), intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentActivityOptions(ComponentName componentName, List<Intent> list, Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        String[] strArr;
        String resolveTypeIfNeeded;
        int userId = getUserId();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Intent[] intentArr = null;
        if (list != null) {
            int size = list.size();
            String[] strArr2 = null;
            for (int i = 0; i < size; i++) {
                Intent intent2 = list.get(i);
                if (intent2 != null && (resolveTypeIfNeeded = intent2.resolveTypeIfNeeded(contentResolver)) != null) {
                    if (strArr2 == null) {
                        strArr2 = new String[size];
                    }
                    strArr2[i] = resolveTypeIfNeeded;
                }
            }
            strArr = strArr2;
        } else {
            strArr = null;
        }
        try {
            IPackageManager iPackageManager = this.mPM;
            if (list != null) {
                intentArr = (Intent[]) list.toArray(new Intent[0]);
            }
            ParceledListSlice queryIntentActivityOptions = iPackageManager.queryIntentActivityOptions(componentName, intentArr, strArr, intent, intent.resolveTypeIfNeeded(contentResolver), updateFlagsForComponent(resolveInfoFlags.getValue(), userId, intent), userId);
            if (queryIntentActivityOptions == null) {
                return Collections.EMPTY_LIST;
            }
            return queryIntentActivityOptions.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, int i, int i2) {
        return queryBroadcastReceiversAsUser(intent, PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            ParceledListSlice queryIntentReceivers = this.mPM.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentReceivers == null) {
                return Collections.EMPTY_LIST;
            }
            return queryIntentReceivers.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int i) {
        return queryBroadcastReceivers(intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryBroadcastReceiversAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveServiceAsUser(Intent intent, int i, int i2) {
        return resolveServiceAsUser(intent, PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveServiceAsUser(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            ResolveInfo resolveService = this.mPM.resolveService(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            return (resolveService == null && SemDualAppManager.isDualAppId(i)) ? this.mPM.resolveService(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), 0, intent), 0) : resolveService;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveService(Intent intent, int i) {
        return resolveService(intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ResolveInfo resolveService(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return resolveServiceAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int i, int i2) {
        return queryIntentServicesAsUser(intent, PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            ParceledListSlice queryIntentServices = this.mPM.queryIntentServices(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentServices == null) {
                return Collections.EMPTY_LIST;
            }
            return queryIntentServices.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentServices(Intent intent, int i) {
        return queryIntentServices(intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentServices(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryIntentServicesAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int i, int i2) {
        return queryIntentContentProvidersAsUser(intent, PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            ParceledListSlice queryIntentContentProviders = this.mPM.queryIntentContentProviders(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (SemDualAppManager.isDualAppId(i) && (queryIntentContentProviders == null || queryIntentContentProviders.getList().size() == 0)) {
                queryIntentContentProviders = this.mPM.queryIntentContentProviders(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), resolveInfoFlags.getValue(), 0);
            }
            if (queryIntentContentProviders == null) {
                return Collections.EMPTY_LIST;
            }
            return queryIntentContentProviders.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentContentProviders(Intent intent, int i) {
        return queryIntentContentProviders(intent, PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public List<ResolveInfo> queryIntentContentProviders(Intent intent, PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryIntentContentProvidersAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo resolveContentProvider(String str, int i) {
        return resolveContentProvider(str, PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo resolveContentProvider(String str, PackageManager.ComponentInfoFlags componentInfoFlags) {
        return resolveContentProviderAsUser(str, componentInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo resolveContentProviderAsUser(String str, int i, int i2) {
        return resolveContentProviderAsUser(str, PackageManager.ComponentInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo resolveContentProviderAsUser(String str, PackageManager.ComponentInfoFlags componentInfoFlags, int i) {
        try {
            return this.mPM.resolveContentProvider(str, updateFlagsForComponent(componentInfoFlags.getValue(), i, null), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ProviderInfo resolveContentProviderForUid(String str, PackageManager.ComponentInfoFlags componentInfoFlags, int i) {
        try {
            return this.mPM.resolveContentProviderForUid(str, updateFlagsForComponent(componentInfoFlags.getValue(), getUserId(), null), getUserId(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<ProviderInfo> queryContentProviders(String str, int i, int i2) {
        return queryContentProviders(str, i, PackageManager.ComponentInfoFlags.of(i2));
    }

    @Override // android.content.pm.PackageManager
    public List<ProviderInfo> queryContentProviders(String str, int i, PackageManager.ComponentInfoFlags componentInfoFlags) {
        return queryContentProviders(str, i, componentInfoFlags, (String) null);
    }

    @Override // android.content.pm.PackageManager
    public List<ProviderInfo> queryContentProviders(String str, int i, int i2, String str2) {
        return queryContentProviders(str, i, PackageManager.ComponentInfoFlags.of(i2), str2);
    }

    @Override // android.content.pm.PackageManager
    public List<ProviderInfo> queryContentProviders(String str, int i, PackageManager.ComponentInfoFlags componentInfoFlags, String str2) {
        try {
            ParceledListSlice queryContentProviders = this.mPM.queryContentProviders(str, i, updateFlagsForComponent(componentInfoFlags.getValue(), UserHandle.getUserId(i), null), str2);
            return queryContentProviders != null ? queryContentProviders.getList() : Collections.EMPTY_LIST;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        try {
            InstrumentationInfo instrumentationInfoAsUser = this.mPM.getInstrumentationInfoAsUser(componentName, i, getUserId());
            if (instrumentationInfoAsUser != null) {
                return instrumentationInfoAsUser;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<InstrumentationInfo> queryInstrumentation(String str, int i) {
        try {
            ParceledListSlice queryInstrumentationAsUser = this.mPM.queryInstrumentationAsUser(str, i, getUserId());
            if (queryInstrumentationAsUser == null) {
                return Collections.EMPTY_LIST;
            }
            return queryInstrumentationAsUser.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Drawable getDrawable(String str, int i, ApplicationInfo applicationInfo) {
        if (SystemProperties.getBoolean("sys.knox.app_icon_change", false) && applicationInfo != null && i == applicationInfo.icon) {
            try {
                byte[] applicationIconFromDb = EnterpriseDeviceManager.getInstance().getApplicationPolicy().getApplicationIconFromDb(str, UserHandle.getUserId(applicationInfo.uid));
                if (applicationIconFromDb != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(applicationIconFromDb);
                    TypedValue typedValue = new TypedValue();
                    typedValue.density = this.mContext.getResources().getDisplayMetrics().densityDpi;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inTargetDensity = this.mContext.getResources().getDisplayMetrics().densityDpi;
                    Drawable createFromResourceStream = Drawable.createFromResourceStream(this.mContext.getResources(), typedValue, byteArrayInputStream, null, options);
                    Log.i(TAG, "EDM:ApplicationIcon got from EDM database ");
                    return createFromResourceStream;
                }
            } catch (Exception e) {
                Log.w(TAG, "EDM: Get Icon EX: " + e);
            }
        }
        ResourceName resourceName = new ResourceName(str, i);
        Drawable cachedIcon = getCachedIcon(resourceName);
        if (cachedIcon != null) {
            return cachedIcon;
        }
        if (applicationInfo == null) {
            try {
                applicationInfo = getApplicationInfo(str, 1024);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }
        if (i != 0) {
            try {
                Resources resourcesForApplication = getResourcesForApplication(applicationInfo);
                resourcesForApplication.mPackageName = applicationInfo.packageName;
                resourcesForApplication.mAppIconResId = applicationInfo.icon;
                resourcesForApplication.mUserId = UserHandle.getUserId(applicationInfo.uid);
                Drawable drawable = resourcesForApplication.getDrawable(i, null);
                if (drawable != null) {
                    putCachedIcon(resourceName, drawable);
                }
                return drawable;
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName);
            } catch (Resources.NotFoundException e2) {
                Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName + ": " + e2.getMessage());
            } catch (Exception e3) {
                Log.w("PackageManager", "Failure retrieving icon 0x" + Integer.toHexString(i) + " in package " + str, e3);
            }
        }
        return null;
    }

    @Override // android.content.pm.PackageManager
    public Drawable getActivityIcon(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadIcon(this);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getActivityIcon(Intent intent) throws PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityIcon(intent.getComponent());
        }
        ResolveInfo resolveActivity = resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.loadIcon(this);
        }
        throw new PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getDefaultActivityIcon() {
        return this.mContext.getDrawable(17301651);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getApplicationIcon(ApplicationInfo applicationInfo) {
        return applicationInfo.loadIcon(this);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getApplicationIcon(String str) throws PackageManager.NameNotFoundException {
        return getApplicationIcon(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getActivityBanner(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadBanner(this);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getActivityBanner(Intent intent) throws PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityBanner(intent.getComponent());
        }
        ResolveInfo resolveActivity = resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.loadBanner(this);
        }
        throw new PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getApplicationBanner(ApplicationInfo applicationInfo) {
        return applicationInfo.loadBanner(this);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getApplicationBanner(String str) throws PackageManager.NameNotFoundException {
        return getApplicationBanner(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getActivityLogo(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadLogo(this);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getActivityLogo(Intent intent) throws PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityLogo(intent.getComponent());
        }
        ResolveInfo resolveActivity = resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.loadLogo(this);
        }
        throw new PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getApplicationLogo(ApplicationInfo applicationInfo) {
        return applicationInfo.loadLogo(this);
    }

    @Override // android.content.pm.PackageManager
    public Drawable getApplicationLogo(String str) throws PackageManager.NameNotFoundException {
        return getApplicationLogo(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getUserBadgedIcon(Drawable drawable, final UserHandle userHandle) {
        if (!hasUserBadge(userHandle.getIdentifier())) {
            return drawable;
        }
        int identifier = userHandle.getIdentifier();
        if (SemPersonaManager.isSecureFolderId(identifier) || SemDualAppManager.isDualAppId(identifier) || SemPersonaManager.isAppSeparationUserId(identifier)) {
            return getBadgedDrawable(drawable, getDrawable("system", getBadgeResIdForUser(identifier), null), null, true);
        }
        return getBadgedDrawable(drawable, new LauncherIcons(this.mContext).getBadgeDrawable(getDevicePolicyManager().getResources().getDrawable(getUpdatableUserIconBadgeId(userHandle), DevicePolicyResources.Drawables.Style.SOLID_COLORED, new Supplier() { // from class: android.app.ApplicationPackageManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable lambda$getUserBadgedIcon$0;
                lambda$getUserBadgedIcon$0 = ApplicationPackageManager.this.lambda$getUserBadgedIcon$0(userHandle);
                return lambda$getUserBadgedIcon$0;
            }
        }), getUserBadgeColor(userHandle, false)), null, true);
    }

    private String getUpdatableUserIconBadgeId(UserHandle userHandle) {
        return getUserManager().isManagedProfile(userHandle.getIdentifier()) ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE : DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserIconBadge, reason: merged with bridge method [inline-methods] */
    public Drawable lambda$getUserBadgedIcon$0(UserHandle userHandle) {
        return this.mContext.getDrawable(getUserManager().getUserIconBadgeResId(userHandle.getIdentifier()));
    }

    @Override // android.content.pm.PackageManager
    public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle userHandle, Rect rect, int i) {
        Drawable userBadgeForDensity = getUserBadgeForDensity(userHandle, i);
        return userBadgeForDensity == null ? drawable : getBadgedDrawable(drawable, userBadgeForDensity, rect, true);
    }

    private int getUserBadgeColor(UserHandle userHandle, boolean z) {
        if (z && this.mContext.getResources().getConfiguration().isNightModeActive()) {
            return getUserManager().getUserBadgeDarkColor(userHandle.getIdentifier());
        }
        return getUserManager().getUserBadgeColor(userHandle.getIdentifier());
    }

    @Override // android.content.pm.PackageManager
    public Drawable getUserBadgeForDensity(final UserHandle userHandle, final int i) {
        if (SemPersonaManager.isKnoxId(userHandle.getIdentifier())) {
            Pair<Boolean, Drawable> customBadgeForCustomContainer = SemPersonaManager.getCustomBadgeForCustomContainer(userHandle, i <= 0 ? this.mContext.getResources().getDisplayMetrics().densityDpi : i, this.mContext);
            if (customBadgeForCustomContainer.first.booleanValue()) {
                return customBadgeForCustomContainer.second;
            }
        } else if (SemDualAppManager.isDualAppId(userHandle.getIdentifier())) {
            if (i <= 0) {
                i = this.mContext.getResources().getDisplayMetrics().densityDpi;
            }
            return Resources.getSystem().getDrawableForDensity(R.drawable.ic_dualapp_corner, i);
        }
        Drawable profileIconForDensity = getProfileIconForDensity(userHandle, R.drawable.ic_corp_badge_color, i);
        if (profileIconForDensity == null) {
            return null;
        }
        Drawable drawableForDensity = getDevicePolicyManager().getResources().getDrawableForDensity(getUpdatableUserBadgeId(userHandle), DevicePolicyResources.Drawables.Style.SOLID_COLORED, i, new Supplier() { // from class: android.app.ApplicationPackageManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable lambda$getUserBadgeForDensity$1;
                lambda$getUserBadgeForDensity$1 = ApplicationPackageManager.this.lambda$getUserBadgeForDensity$1(userHandle, i);
                return lambda$getUserBadgeForDensity$1;
            }
        });
        drawableForDensity.setTint(getUserBadgeColor(userHandle, false));
        return new LayerDrawable(new Drawable[]{profileIconForDensity, drawableForDensity});
    }

    private String getUpdatableUserBadgeId(UserHandle userHandle) {
        return getUserManager().isManagedProfile(userHandle.getIdentifier()) ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON : DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserBadgeForDensity, reason: merged with bridge method [inline-methods] */
    public Drawable lambda$getUserBadgeForDensity$1(UserHandle userHandle, int i) {
        return getDrawableForDensity(getUserManager().getUserBadgeResId(userHandle.getIdentifier()), i);
    }

    private UserInfo getUserIfProfile(int i) {
        for (UserInfo userInfo : getUserManager().getProfiles(UserHandle.myUserId())) {
            if (userInfo.id == i) {
                return userInfo;
            }
        }
        return null;
    }

    @Override // android.content.pm.PackageManager
    public Drawable getUserBadgeForDensityNoBackground(final UserHandle userHandle, final int i) {
        if (!hasUserBadge(userHandle.getIdentifier())) {
            return null;
        }
        Drawable drawableForDensity = getDevicePolicyManager().getResources().getDrawableForDensity(getUpdatableUserBadgeId(userHandle), DevicePolicyResources.Drawables.Style.SOLID_NOT_COLORED, i, new Supplier() { // from class: android.app.ApplicationPackageManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                Drawable lambda$getUserBadgeForDensityNoBackground$2;
                lambda$getUserBadgeForDensityNoBackground$2 = ApplicationPackageManager.this.lambda$getUserBadgeForDensityNoBackground$2(userHandle, i);
                return lambda$getUserBadgeForDensityNoBackground$2;
            }
        });
        if (drawableForDensity != null) {
            if (SemPersonaManager.isKnoxId(userHandle.getIdentifier())) {
                Pair<Boolean, Drawable> notificationBadge = SemPersonaManager.getNotificationBadge(userHandle, i, this.mContext);
                if (notificationBadge.first.booleanValue()) {
                    return notificationBadge.second;
                }
            }
            drawableForDensity.setTint(getUserBadgeColor(userHandle, true));
        }
        return drawableForDensity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserBadgeNoBackgroundForDensity, reason: merged with bridge method [inline-methods] */
    public Drawable lambda$getUserBadgeForDensityNoBackground$2(UserHandle userHandle, int i) {
        return getDrawableForDensity(getUserManager().getUserBadgeNoBackgroundResId(userHandle.getIdentifier()), i);
    }

    private Drawable getDrawableForDensity(int i, int i2) {
        if (i2 <= 0) {
            i2 = this.mContext.getResources().getDisplayMetrics().densityDpi;
        }
        return this.mContext.getResources().getDrawableForDensity(i, i2);
    }

    private Drawable getProfileIconForDensity(UserHandle userHandle, int i, int i2) {
        if (hasUserBadge(userHandle.getIdentifier())) {
            return getDrawableForDensity(i, i2);
        }
        return null;
    }

    @Override // android.content.pm.PackageManager
    public CharSequence getUserBadgedLabel(CharSequence charSequence, UserHandle userHandle) {
        return getUserManager().getBadgedLabelForUser(charSequence, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public Resources getResourcesForActivity(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return getResourcesForApplication(getActivityInfo(componentName, 1024).applicationInfo);
    }

    @Override // android.content.pm.PackageManager
    public Resources getResourcesForApplication(ApplicationInfo applicationInfo) throws PackageManager.NameNotFoundException {
        return getResourcesForApplication(applicationInfo, null);
    }

    @Override // android.content.pm.PackageManager
    public Resources getResourcesForApplication(ApplicationInfo applicationInfo, Configuration configuration) throws PackageManager.NameNotFoundException {
        if (applicationInfo.packageName.equals("system")) {
            Context systemUiContext = this.mContext.mMainThread.getSystemUiContext();
            if (configuration != null) {
                systemUiContext = systemUiContext.createConfigurationContext(configuration);
            }
            return systemUiContext.getResources();
        }
        boolean z = applicationInfo.uid == Process.myUid();
        Resources topLevelResources = this.mContext.mMainThread.getTopLevelResources(z ? applicationInfo.sourceDir : applicationInfo.publicSourceDir, z ? applicationInfo.splitSourceDirs : applicationInfo.splitPublicSourceDirs, applicationInfo.resourceDirs, applicationInfo.overlayPaths, applicationInfo.sharedLibraryFiles, this.mContext.mPackageInfo, configuration);
        if (topLevelResources != null) {
            topLevelResources.mPackageName = applicationInfo.packageName;
            topLevelResources.mAppIconResId = applicationInfo.icon;
            topLevelResources.mUserId = UserHandle.getUserId(applicationInfo.uid);
            return topLevelResources;
        }
        throw new PackageManager.NameNotFoundException("Unable to open " + applicationInfo.publicSourceDir);
    }

    @Override // android.content.pm.PackageManager
    public Resources getResourcesForApplication(String str) throws PackageManager.NameNotFoundException {
        return getResourcesForApplication(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public Resources getResourcesForApplicationAsUser(String str, int i) throws PackageManager.NameNotFoundException {
        if (i < 0) {
            throw new IllegalArgumentException("Call does not support special user #" + i);
        }
        if ("system".equals(str)) {
            return this.mContext.mMainThread.getSystemUiContext().getResources();
        }
        try {
            ApplicationInfo applicationInfo = this.mPM.getApplicationInfo(str, 1024L, i);
            if (applicationInfo != null) {
                return getResourcesForApplication(applicationInfo);
            }
            throw new PackageManager.NameNotFoundException("Package " + str + " doesn't exist");
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isSafeMode() {
        try {
            if (this.mCachedSafeMode < 0) {
                this.mCachedSafeMode = this.mPM.isSafeMode() ? 1 : 0;
            }
            return this.mCachedSafeMode != 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        getPermissionManager().addOnPermissionsChangeListener(onPermissionsChangedListener);
    }

    @Override // android.content.pm.PackageManager
    public void removeOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        getPermissionManager().removeOnPermissionsChangeListener(onPermissionsChangedListener);
    }

    static void configurationChanged() {
        synchronized (sSync) {
            sIconCache.clear();
            sStringCache.clear();
        }
    }

    protected ApplicationPackageManager(ContextImpl contextImpl, IPackageManager iPackageManager) {
        this.mContext = contextImpl;
        this.mPM = iPackageManager;
    }

    private static boolean isSystemFeaturesCacheEnabledAndAvailable() {
        if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.cacheSdkSystemFeatures() && com.android.internal.os.Flags.applicationSharedMemoryEnabled()) {
            return !ActivityThread.isSystem() || SystemFeaturesCache.hasInstance();
        }
        return false;
    }

    private long updateFlagsForPackage(long j, int i) {
        if ((15 & j) != 0 && (269221888 & j) == 0) {
            onImplicitDirectBoot(i);
        }
        return j;
    }

    private long updateFlagsForApplication(long j, int i) {
        return updateFlagsForPackage(j, i);
    }

    private long updateFlagsForComponent(long j, int i, Intent intent) {
        if (intent != null && (intent.getFlags() & 256) != 0) {
            j |= 268435456;
        }
        if ((269221888 & j) == 0) {
            onImplicitDirectBoot(i);
        }
        return j;
    }

    private void onImplicitDirectBoot(int i) {
        if (StrictMode.vmImplicitDirectBootEnabled()) {
            if (i == UserHandle.myUserId()) {
                if (this.mUserUnlocked) {
                    return;
                }
                if (((UserManager) this.mContext.getSystemService(UserManager.class)).isUserUnlockingOrUnlocked(i)) {
                    this.mUserUnlocked = true;
                    return;
                } else {
                    StrictMode.onImplicitDirectBoot();
                    return;
                }
            }
            if (((UserManager) this.mContext.getSystemService(UserManager.class)).isUserUnlockingOrUnlocked(i)) {
                return;
            }
            StrictMode.onImplicitDirectBoot();
        }
    }

    private Drawable getCachedIcon(ResourceName resourceName) {
        synchronized (sSync) {
            WeakReference<Drawable.ConstantState> weakReference = sIconCache.get(resourceName);
            if (weakReference != null) {
                Drawable.ConstantState constantState = weakReference.get();
                if (constantState != null) {
                    return constantState.newDrawable();
                }
                sIconCache.remove(resourceName);
            }
            return null;
        }
    }

    private void putCachedIcon(ResourceName resourceName, Drawable drawable) {
        synchronized (sSync) {
            sIconCache.put(resourceName, new WeakReference<>(drawable.getConstantState()));
        }
    }

    static void handlePackageBroadcast(int i, String[] strArr, boolean z) {
        boolean z2 = i == 1;
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        boolean z3 = false;
        for (String str : strArr) {
            synchronized (sSync) {
                for (int size = sIconCache.size() - 1; size >= 0; size--) {
                    if (sIconCache.keyAt(size).packageName.equals(str)) {
                        sIconCache.removeAt(size);
                        z3 = true;
                    }
                }
                for (int size2 = sStringCache.size() - 1; size2 >= 0; size2--) {
                    if (sStringCache.keyAt(size2).packageName.equals(str)) {
                        sStringCache.removeAt(size2);
                        z3 = true;
                    }
                }
            }
        }
        if (z3 || z) {
            if (z2) {
                Runtime.getRuntime().gc();
            } else {
                ActivityThread.currentActivityThread().scheduleGcIdler();
            }
        }
    }

    private static final class ResourceName {
        final int iconId;
        final String packageName;

        ResourceName(String str, int i) {
            this.packageName = str;
            this.iconId = i;
        }

        ResourceName(ApplicationInfo applicationInfo, int i) {
            this(applicationInfo.packageName, i);
        }

        ResourceName(ComponentInfo componentInfo, int i) {
            this(componentInfo.applicationInfo.packageName, i);
        }

        ResourceName(ResolveInfo resolveInfo, int i) {
            this(resolveInfo.activityInfo.applicationInfo.packageName, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ResourceName resourceName = (ResourceName) obj;
                if (this.iconId != resourceName.iconId) {
                    return false;
                }
                String str = this.packageName;
                if (str == null ? resourceName.packageName == null : str.equals(resourceName.packageName)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (this.packageName.hashCode() * 31) + this.iconId;
        }

        public String toString() {
            return "{ResourceName " + this.packageName + " / " + this.iconId + "}";
        }
    }

    private CharSequence getCachedString(ResourceName resourceName) {
        synchronized (sSync) {
            WeakReference<CharSequence> weakReference = sStringCache.get(resourceName);
            if (weakReference != null) {
                CharSequence charSequence = weakReference.get();
                if (charSequence != null) {
                    return charSequence;
                }
                sStringCache.remove(resourceName);
            }
            return null;
        }
    }

    private void putCachedString(ResourceName resourceName, CharSequence charSequence) {
        synchronized (sSync) {
            sStringCache.put(resourceName, new WeakReference<>(charSequence));
        }
    }

    @Override // android.content.pm.PackageManager
    public CharSequence getText(String str, int i, ApplicationInfo applicationInfo) {
        ResourceName resourceName = new ResourceName(str, i);
        CharSequence cachedString = getCachedString(resourceName);
        if (cachedString != null) {
            return cachedString;
        }
        if (applicationInfo == null) {
            try {
                applicationInfo = getApplicationInfo(str, 1024);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }
        if (PMRune.PM_32BIT_APP_RUNNING_IN_ABI64 && !this.mAbiAppHelper.canAccessApkFile(this.mContext.getApplicationInfo(), applicationInfo)) {
            Log.d(TAG, "The apk size is bigger than 2G, native abort might happen. return package name");
            putCachedString(resourceName, str);
            return str;
        }
        try {
            CharSequence text = getResourcesForApplication(applicationInfo).getText(i);
            putCachedString(resourceName, text);
            return text;
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName);
            return null;
        } catch (RuntimeException e) {
            Log.w("PackageManager", "Failure retrieving text 0x" + Integer.toHexString(i) + " in package " + str, e);
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public XmlResourceParser getXml(String str, int i, ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            try {
                applicationInfo = getApplicationInfo(str, 1024);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }
        try {
            return getResourcesForApplication(applicationInfo).getXml(i);
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName);
            return null;
        } catch (RuntimeException e) {
            Log.w("PackageManager", "Failure retrieving xml 0x" + Integer.toHexString(i) + " in package " + str, e);
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public CharSequence getApplicationLabel(ApplicationInfo applicationInfo) {
        return applicationInfo.loadLabel(this);
    }

    @Override // android.content.pm.PackageManager
    public boolean applyRuntimePermissionsForMdm(String str, List<String> list, int i, int i2) {
        return getKnoxSdkHook().applyRuntimePermissionsForMdm(str, list, i, i2);
    }

    @Override // android.content.pm.PackageManager
    public boolean applyRuntimePermissionsForAllApplicationsForMdm(int i, int i2) {
        return getKnoxSdkHook().applyRuntimePermissionsForAllApplicationsForMdm(i, i2);
    }

    @Override // android.content.pm.PackageManager
    public List<String> getRequestedRuntimePermissionsForMdm(String str) {
        return getKnoxSdkHook().getRequestedRuntimePermissionsForMdm(str);
    }

    private KnoxSdkHook getKnoxSdkHook() {
        if (this.mKnoxSdkHook == null) {
            this.mKnoxSdkHook = new KnoxSdkHook(this) { // from class: android.app.ApplicationPackageManager.4
            };
            this.mKnoxSdkHook = new KnoxSdkHookImpl();
        }
        return this.mKnoxSdkHook;
    }

    class KnoxSdkHookImpl implements KnoxSdkHook {
        KnoxSdkHookImpl() {
        }

        @Override // android.app.ApplicationPackageManager.KnoxSdkHook
        public boolean applyRuntimePermissionsForMdm(String str, List<String> list, int i, int i2) {
            try {
                return ApplicationPackageManager.this.mPM.applyRuntimePermissionsForMDM(str, list, i, i2);
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.app.ApplicationPackageManager.KnoxSdkHook
        public boolean applyRuntimePermissionsForAllApplicationsForMdm(int i, int i2) {
            try {
                return ApplicationPackageManager.this.mPM.applyRuntimePermissionsForAllApplicationsForMDM(i, i2);
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.app.ApplicationPackageManager.KnoxSdkHook
        public List<String> getRequestedRuntimePermissionsForMdm(String str) {
            try {
                return ApplicationPackageManager.this.mPM.getRequestedRuntimePermissionsForMDM(str);
            } catch (Exception unused) {
                return null;
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public int installExistingPackage(String str) throws PackageManager.NameNotFoundException {
        return installExistingPackage(str, 0);
    }

    @Override // android.content.pm.PackageManager
    public int installExistingPackage(String str, int i) throws PackageManager.NameNotFoundException {
        return installExistingPackageAsUser(str, i, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public int installExistingPackageAsUser(String str, int i) throws PackageManager.NameNotFoundException {
        return installExistingPackageAsUser(str, 0, i);
    }

    private int installExistingPackageAsUser(String str, int i, int i2) throws PackageManager.NameNotFoundException {
        try {
            int installExistingPackageAsUser = this.mPM.installExistingPackageAsUser(str, i2, 4194304, i, null);
            if (installExistingPackageAsUser != -3) {
                return installExistingPackageAsUser;
            }
            throw new PackageManager.NameNotFoundException("Package " + str + " doesn't exist");
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void verifyPendingInstall(int i, int i2) {
        try {
            this.mPM.verifyPendingInstall(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void extendVerificationTimeout(int i, int i2, long j) {
        try {
            this.mPM.extendVerificationTimeout(i, i2, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void verifyIntentFilter(int i, int i2, List<String> list) {
        try {
            this.mPM.verifyIntentFilter(i, i2, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getIntentVerificationStatusAsUser(String str, int i) {
        try {
            return this.mPM.getIntentVerificationStatus(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean updateIntentVerificationStatusAsUser(String str, int i, int i2) {
        try {
            return this.mPM.updateIntentVerificationStatus(str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<IntentFilterVerificationInfo> getIntentFilterVerifications(String str) {
        try {
            ParceledListSlice intentFilterVerifications = this.mPM.getIntentFilterVerifications(str);
            if (intentFilterVerifications == null) {
                return Collections.EMPTY_LIST;
            }
            return intentFilterVerifications.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<IntentFilter> getAllIntentFilters(String str) {
        try {
            ParceledListSlice allIntentFilters = this.mPM.getAllIntentFilters(str);
            if (allIntentFilters == null) {
                return Collections.EMPTY_LIST;
            }
            return allIntentFilters.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getDefaultBrowserPackageNameAsUser(int i) {
        return ((RoleManager) this.mContext.getSystemService(RoleManager.class)).getBrowserRoleHolder(i);
    }

    @Override // android.content.pm.PackageManager
    public boolean setDefaultBrowserPackageNameAsUser(String str, int i) {
        return ((RoleManager) this.mContext.getSystemService(RoleManager.class)).setBrowserRoleHolder(str, i);
    }

    @Override // android.content.pm.PackageManager
    public void setInstallerPackageName(String str, String str2) {
        try {
            this.mPM.setInstallerPackageName(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setUpdateAvailable(String str, boolean z) {
        try {
            this.mPM.setUpdateAvailable(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getInstallerPackageName(String str) {
        try {
            return this.mPM.getInstallerPackageName(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public InstallSourceInfo getInstallSourceInfo(String str) throws PackageManager.NameNotFoundException {
        try {
            InstallSourceInfo installSourceInfo = this.mPM.getInstallSourceInfo(str, getUserId());
            if (installSourceInfo != null) {
                return installSourceInfo;
            }
            throw new PackageManager.NameNotFoundException(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isAppArchivable(String str) throws PackageManager.NameNotFoundException {
        try {
            Objects.requireNonNull(str);
            return this.mPM.isAppArchivable(str, new UserHandle(getUserId()));
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getMoveStatus(int i) {
        try {
            return this.mPM.getMoveStatus(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void registerMoveCallback(PackageManager.MoveCallback moveCallback, Handler handler) {
        synchronized (this.mDelegates) {
            MoveCallbackDelegate moveCallbackDelegate = new MoveCallbackDelegate(moveCallback, handler.getLooper());
            try {
                this.mPM.registerMoveCallback(moveCallbackDelegate);
                this.mDelegates.add(moveCallbackDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public void unregisterMoveCallback(PackageManager.MoveCallback moveCallback) {
        synchronized (this.mDelegates) {
            Iterator<MoveCallbackDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                MoveCallbackDelegate next = it.next();
                if (next.mCallback == moveCallback) {
                    try {
                        this.mPM.unregisterMoveCallback(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public int movePackage(String str, VolumeInfo volumeInfo) {
        String str2;
        try {
            if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.id)) {
                str2 = StorageManager.UUID_PRIVATE_INTERNAL;
            } else if (volumeInfo.isPrimaryPhysical()) {
                str2 = StorageManager.UUID_PRIMARY_PHYSICAL;
            } else {
                str2 = (String) Objects.requireNonNull(volumeInfo.fsUuid);
            }
            return this.mPM.movePackage(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int movePackageToSd(String str, String str2, IMemorySaverPackageMoveObserver iMemorySaverPackageMoveObserver) {
        try {
            return this.mPM.movePackageToSd(str, str2, iMemorySaverPackageMoveObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public VolumeInfo getPackageCurrentVolume(ApplicationInfo applicationInfo) {
        return getPackageCurrentVolume(applicationInfo, (StorageManager) this.mContext.getSystemService(StorageManager.class));
    }

    protected VolumeInfo getPackageCurrentVolume(ApplicationInfo applicationInfo, StorageManager storageManager) {
        if (applicationInfo.isInternal()) {
            return storageManager.findVolumeById(VolumeInfo.ID_PRIVATE_INTERNAL);
        }
        if (AsecUtils.isExternalAsec(applicationInfo)) {
            return storageManager.getPrimaryPhysicalVolume();
        }
        return storageManager.findVolumeByUuid(applicationInfo.volumeUuid);
    }

    @Override // android.content.pm.PackageManager
    public List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo applicationInfo) {
        return getPackageCandidateVolumes(applicationInfo, (StorageManager) this.mContext.getSystemService(StorageManager.class), this.mPM);
    }

    protected List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo applicationInfo, StorageManager storageManager, IPackageManager iPackageManager) {
        VolumeInfo packageCurrentVolume = getPackageCurrentVolume(applicationInfo, storageManager);
        List<VolumeInfo> volumes = storageManager.getVolumes();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("getPackageCandidateVolumes, currentVol: ");
        sb.append(packageCurrentVolume != null ? packageCurrentVolume.id : PerfettoProtoLogImpl.NULL_STRING);
        Log.i(TAG, sb.toString());
        for (VolumeInfo volumeInfo : volumes) {
            if (Objects.equals(volumeInfo, packageCurrentVolume) || isPackageCandidateVolume(this.mContext, applicationInfo, volumeInfo, iPackageManager)) {
                Log.i(TAG, "Add volume: " + volumeInfo.id + ", mountFlags: " + volumeInfo.mountFlags + ", type: " + volumeInfo.getType());
                arrayList.add(volumeInfo);
            }
        }
        return arrayList;
    }

    protected boolean isForceAllowOnExternal(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.FORCE_ALLOW_ON_EXTERNAL, 0) != 0;
    }

    protected boolean isAllow3rdPartyOnInternal(Context context) {
        return context.getResources().getBoolean(R.bool.config_allow3rdPartyAppOnInternal);
    }

    private boolean isPackageCandidateVolume(ContextImpl contextImpl, ApplicationInfo applicationInfo, VolumeInfo volumeInfo, IPackageManager iPackageManager) {
        boolean isForceAllowOnExternal = isForceAllowOnExternal(contextImpl);
        if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
            return applicationInfo.isSystemApp() || isAllow3rdPartyOnInternal(contextImpl);
        }
        if (applicationInfo.isSystemApp()) {
            return false;
        }
        if (!isForceAllowOnExternal && (applicationInfo.installLocation == 1 || applicationInfo.installLocation == -1)) {
            Log.i(TAG, "Apps demanding internal storage can't be moved, " + applicationInfo.packageName);
            return false;
        }
        if (!volumeInfo.isMountedWritable()) {
            Log.i(TAG, "This volume is not mounted writable, " + volumeInfo);
            return false;
        }
        if (volumeInfo.isPrimaryPhysical()) {
            Log.i(TAG, "This volume is not mounted writable, " + volumeInfo);
            return applicationInfo.isInternal();
        }
        try {
            if (!iPackageManager.isPackageDeviceAdminOnAnyUser(applicationInfo.packageName)) {
                return volumeInfo.getType() == 1 || volumeInfo.getType() == 0;
            }
            Log.i(TAG, "This package is DeviceAdmin or AnyUser, " + applicationInfo.packageName);
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int movePrimaryStorage(VolumeInfo volumeInfo) {
        String str;
        try {
            if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.id)) {
                str = StorageManager.UUID_PRIVATE_INTERNAL;
            } else if (volumeInfo.isPrimaryPhysical()) {
                str = StorageManager.UUID_PRIMARY_PHYSICAL;
            } else {
                str = (String) Objects.requireNonNull(volumeInfo.fsUuid);
            }
            return this.mPM.movePrimaryStorage(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public VolumeInfo getPrimaryStorageCurrentVolume() {
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        return storageManager.findVolumeByQualifiedUuid(storageManager.getPrimaryStorageUuid());
    }

    @Override // android.content.pm.PackageManager
    public List<VolumeInfo> getPrimaryStorageCandidateVolumes() {
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        VolumeInfo primaryStorageCurrentVolume = getPrimaryStorageCurrentVolume();
        List<VolumeInfo> volumes = storageManager.getVolumes();
        ArrayList arrayList = new ArrayList();
        if (StorageManager.UUID_PRIMARY_PHYSICAL.equals(storageManager.getPrimaryStorageUuid()) && primaryStorageCurrentVolume != null) {
            arrayList.add(primaryStorageCurrentVolume);
            return arrayList;
        }
        for (VolumeInfo volumeInfo : volumes) {
            if (Objects.equals(volumeInfo, primaryStorageCurrentVolume) || isPrimaryStorageCandidateVolume(volumeInfo)) {
                arrayList.add(volumeInfo);
            }
        }
        return arrayList;
    }

    private static boolean isPrimaryStorageCandidateVolume(VolumeInfo volumeInfo) {
        if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
            return true;
        }
        return volumeInfo.isMountedWritable() && volumeInfo.getType() == 1;
    }

    @Override // android.content.pm.PackageManager
    public void deletePackage(String str, IPackageDeleteObserver iPackageDeleteObserver, int i) {
        deletePackageAsUser(str, iPackageDeleteObserver, i, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public void deletePackageAsUser(String str, IPackageDeleteObserver iPackageDeleteObserver, int i, int i2) {
        try {
            this.mPM.deletePackageAsUser(str, -1, iPackageDeleteObserver, i2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.clearApplicationUserData(str, iPackageDataObserver, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.deleteApplicationCacheFiles(str, iPackageDataObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void deleteApplicationCacheFilesAsUser(String str, int i, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.deleteApplicationCacheFilesAsUser(str, i, iPackageDataObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void freeStorageAndNotify(String str, long j, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.freeStorageAndNotify(str, j, 0, iPackageDataObserver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void freeStorage(String str, long j, IntentSender intentSender) {
        try {
            this.mPM.freeStorage(str, j, 0, intentSender);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] setDistractingPackageRestrictions(String[] strArr, int i) {
        try {
            return this.mPM.setDistractingPackageRestrictionsAsUser(strArr, i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] setPackagesSuspended(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, String str) {
        return setPackagesSuspended(strArr, z, persistableBundle, persistableBundle2, !TextUtils.isEmpty(str) ? new SuspendDialogInfo.Builder().setMessage(str).build() : null, 0);
    }

    @Override // android.content.pm.PackageManager
    public String[] setPackagesSuspended(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo) {
        return setPackagesSuspended(strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, 0);
    }

    @Override // android.content.pm.PackageManager
    public String[] setPackagesSuspended(String[] strArr, boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, int i) {
        try {
            return this.mPM.setPackagesSuspendedAsUser(strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, i, this.mContext.getOpPackageName(), UserHandle.myUserId(), getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String[] getUnsuspendablePackages(String[] strArr) {
        try {
            return this.mPM.getUnsuspendablePackagesForUser(strArr, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Bundle getSuspendedPackageAppExtras() {
        try {
            return this.mPM.getSuspendedPackageAppExtras(this.mContext.getOpPackageName(), getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getSuspendingPackage(String str) {
        try {
            return this.mPM.getSuspendingPackage(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageSuspendedForUser(String str, int i) {
        try {
            return this.mPM.isPackageSuspendedForUser(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageSuspended(String str) throws PackageManager.NameNotFoundException {
        try {
            return isPackageSuspendedForUser(str, getUserId());
        } catch (IllegalArgumentException unused) {
            throw new PackageManager.NameNotFoundException(str);
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageSuspended() {
        return isPackageSuspendedForUser(this.mContext.getOpPackageName(), getUserId());
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageQuarantined(String str) throws PackageManager.NameNotFoundException {
        try {
            return this.mPM.isPackageQuarantinedForUser(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IllegalArgumentException unused) {
            throw new PackageManager.NameNotFoundException(str);
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageStopped(String str) throws PackageManager.NameNotFoundException {
        try {
            return this.mPM.isPackageStoppedForUser(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IllegalArgumentException unused) {
            throw new PackageManager.NameNotFoundException(str);
        }
    }

    @Override // android.content.pm.PackageManager
    public void setApplicationCategoryHint(String str, int i) {
        try {
            this.mPM.setApplicationCategoryHint(str, i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void getPackageSizeInfoAsUser(String str, int i, IPackageStatsObserver iPackageStatsObserver) {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 26) {
            throw new UnsupportedOperationException("Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
        }
        if (iPackageStatsObserver != null) {
            Log.d(TAG, "Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
            try {
                iPackageStatsObserver.onGetStatsCompleted(null, false);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public void addPackageToPreferred(String str) {
        Log.w(TAG, "addPackageToPreferred() is a no-op");
    }

    @Override // android.content.pm.PackageManager
    public void removePackageFromPreferred(String str) {
        Log.w(TAG, "removePackageFromPreferred() is a no-op");
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getPreferredPackages(int i) {
        Log.w(TAG, "getPreferredPackages() is a no-op");
        return Collections.EMPTY_LIST;
    }

    @Override // android.content.pm.PackageManager
    public void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName) {
        try {
            this.mPM.addPreferredActivity(intentFilter, i, componentNameArr, componentName, getUserId(), false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addPreferredActivityAsUser(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) {
        try {
            this.mPM.addPreferredActivity(intentFilter, i, componentNameArr, componentName, i2, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void replacePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName) {
        try {
            this.mPM.replacePreferredActivity(intentFilter, i, componentNameArr, componentName, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void replacePreferredActivityAsUser(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName, int i2) {
        try {
            this.mPM.replacePreferredActivity(intentFilter, i, componentNameArr, componentName, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearPackagePreferredActivities(String str) {
        try {
            this.mPM.clearPackagePreferredActivities(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addUniquePreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNameArr, ComponentName componentName) {
        try {
            this.mPM.addPreferredActivity(intentFilter, i, componentNameArr, componentName, getUserId(), true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String str) {
        try {
            return this.mPM.getPreferredActivities(list, list2, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public ComponentName getHomeActivities(List<ResolveInfo> list) {
        try {
            return this.mPM.getHomeActivities(list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setSyntheticAppDetailsActivityEnabled(String str, boolean z) {
        try {
            this.mPM.setComponentEnabledSetting(new ComponentName(str, APP_DETAILS_ACTIVITY_CLASS_NAME), z ? 0 : 2, 1, getUserId(), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean getSyntheticAppDetailsActivityEnabled(String str) {
        try {
            int componentEnabledSetting = this.mPM.getComponentEnabledSetting(new ComponentName(str, APP_DETAILS_ACTIVITY_CLASS_NAME), getUserId());
            return componentEnabledSetting == 1 || componentEnabledSetting == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setComponentEnabledSetting(ComponentName componentName, int i, int i2) {
        try {
            this.mPM.setComponentEnabledSetting(componentName, i, i2, getUserId(), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> list) {
        try {
            this.mPM.setComponentEnabledSettings(list, getUserId(), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getComponentEnabledSetting(ComponentName componentName) {
        try {
            return this.mPM.getComponentEnabledSetting(componentName, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setApplicationEnabledSetting(String str, int i, int i2) {
        try {
            this.mPM.setApplicationEnabledSetting(str, i, i2, getUserId(), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getApplicationEnabledSetting(String str) {
        try {
            return this.mPM.getApplicationEnabledSetting(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void flushPackageRestrictionsAsUser(int i) {
        try {
            this.mPM.flushPackageRestrictionsAsUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean setApplicationHiddenSettingAsUser(String str, boolean z, UserHandle userHandle) {
        try {
            return this.mPM.setApplicationHiddenSettingAsUser(str, z, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean getApplicationHiddenSettingAsUser(String str, UserHandle userHandle) {
        try {
            return this.mPM.getApplicationHiddenSettingAsUser(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setSystemAppState(String str, int i) {
        try {
            if (i == 0) {
                this.mPM.setSystemAppHiddenUntilInstalled(str, true);
                return;
            }
            if (i == 1) {
                this.mPM.setSystemAppHiddenUntilInstalled(str, false);
            } else if (i == 2) {
                this.mPM.setSystemAppInstallState(str, true, getUserId());
            } else {
                if (i != 3) {
                    return;
                }
                this.mPM.setSystemAppInstallState(str, false, getUserId());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public KeySet getKeySetByAlias(String str, String str2) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        try {
            return this.mPM.getKeySetByAlias(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public KeySet getSigningKeySet(String str) {
        Objects.requireNonNull(str);
        try {
            return this.mPM.getSigningKeySet(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isSignedBy(String str, KeySet keySet) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(keySet);
        try {
            return this.mPM.isPackageSignedByKeySet(str, keySet);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isSignedByExactly(String str, KeySet keySet) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(keySet);
        try {
            return this.mPM.isPackageSignedByKeySetExactly(str, keySet);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public VerifierDeviceIdentity getVerifierDeviceIdentity() {
        try {
            return this.mPM.getVerifierDeviceIdentity();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isUpgrade() {
        return isDeviceUpgrading();
    }

    @Override // android.content.pm.PackageManager
    public boolean isDeviceUpgrading() {
        try {
            return this.mPM.isDeviceUpgrading();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public PackageInstaller getPackageInstaller() {
        if (this.mInstaller == null) {
            try {
                this.mInstaller = new PackageInstaller(this.mPM.getPackageInstaller(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), getUserId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mInstaller;
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageAvailable(String str) {
        try {
            return this.mPM.isPackageAvailable(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addCrossProfileIntentFilter(IntentFilter intentFilter, int i, int i2, int i3) {
        try {
            this.mPM.addCrossProfileIntentFilter(intentFilter, this.mContext.getOpPackageName(), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean removeCrossProfileIntentFilter(IntentFilter intentFilter, int i, int i2, int i3) {
        try {
            return this.mPM.removeCrossProfileIntentFilter(intentFilter, this.mContext.getOpPackageName(), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearCrossProfileIntentFilters(int i) {
        try {
            this.mPM.clearCrossProfileIntentFilters(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Drawable loadItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo) {
        return loadItemIcon(packageItemInfo, applicationInfo, false, 0);
    }

    @Override // android.content.pm.PackageManager
    public Drawable loadItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo, boolean z, int i) {
        Drawable loadUnbadgedItemIcon = loadUnbadgedItemIcon(packageItemInfo, applicationInfo, z, i);
        if (packageItemInfo.showUserIcon != -10000) {
            return loadUnbadgedItemIcon;
        }
        if (applicationInfo != null && SemDualAppManager.isDualAppId(UserHandle.getUserId(applicationInfo.uid))) {
            return getUserBadgedIcon(loadUnbadgedItemIcon, new UserHandle(UserHandle.getUserId(applicationInfo.uid)));
        }
        if (SemDualAppManager.isDualAppId(this.mContext.getUserId())) {
            return getUserBadgedIcon(loadUnbadgedItemIcon, new UserHandle(0));
        }
        if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED && applicationInfo != null && shouldAppSupportBadgeIcon(packageItemInfo.packageName, UserHandle.getUserId(applicationInfo.uid))) {
            loadUnbadgedItemIcon = getMonetizeBadgedIcon(loadUnbadgedItemIcon);
        }
        return getUserBadgedIcon(loadUnbadgedItemIcon, new UserHandle(getUserId()));
    }

    @Override // android.content.pm.PackageManager
    public Drawable loadUnbadgedItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo applicationInfo) {
        return loadUnbadgedItemIcon(packageItemInfo, applicationInfo, false, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0186 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0187  */
    @Override // android.content.pm.PackageManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.drawable.Drawable loadUnbadgedItemIcon(android.content.pm.PackageItemInfo r19, android.content.pm.ApplicationInfo r20, boolean r21, int r22) {
        /*
            Method dump skipped, instructions count: 534
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ApplicationPackageManager.loadUnbadgedItemIcon(android.content.pm.PackageItemInfo, android.content.pm.ApplicationInfo, boolean, int):android.graphics.drawable.Drawable");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.drawable.Drawable getBadgedDrawable(android.graphics.drawable.Drawable r8, android.graphics.drawable.Drawable r9, android.graphics.Rect r10, boolean r11) {
        /*
            r7 = this;
            int r0 = r8.getIntrinsicWidth()
            int r1 = r8.getIntrinsicHeight()
            boolean r2 = r8 instanceof android.graphics.drawable.BitmapDrawable
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L23
            r5 = r8
            android.graphics.drawable.BitmapDrawable r5 = (android.graphics.drawable.BitmapDrawable) r5
            android.graphics.Bitmap r5 = r5.getBitmap()
            int r6 = r5.getWidth()
            if (r6 != r0) goto L23
            int r5 = r5.getHeight()
            if (r5 != r1) goto L23
            r5 = r3
            goto L24
        L23:
            r5 = r4
        L24:
            if (r11 == 0) goto L38
            if (r2 == 0) goto L38
            r11 = r8
            android.graphics.drawable.BitmapDrawable r11 = (android.graphics.drawable.BitmapDrawable) r11
            android.graphics.Bitmap r11 = r11.getBitmap()
            boolean r11 = r11.isMutable()
            if (r11 == 0) goto L38
            if (r5 == 0) goto L38
            goto L39
        L38:
            r3 = r4
        L39:
            if (r3 == 0) goto L43
            r11 = r8
            android.graphics.drawable.BitmapDrawable r11 = (android.graphics.drawable.BitmapDrawable) r11
            android.graphics.Bitmap r11 = r11.getBitmap()
            goto L49
        L43:
            android.graphics.Bitmap$Config r11 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r11 = android.graphics.Bitmap.createBitmap(r0, r1, r11)
        L49:
            android.graphics.Canvas r5 = new android.graphics.Canvas
            r5.<init>(r11)
            if (r3 != 0) goto L56
            r8.setBounds(r4, r4, r0, r1)
            r8.draw(r5)
        L56:
            if (r10 == 0) goto Lab
            int r6 = r10.left
            if (r6 < 0) goto L8a
            int r6 = r10.top
            if (r6 < 0) goto L8a
            int r6 = r10.width()
            if (r6 > r0) goto L8a
            int r6 = r10.height()
            if (r6 > r1) goto L8a
            int r0 = r10.width()
            int r1 = r10.height()
            r9.setBounds(r4, r4, r0, r1)
            r5.save()
            int r0 = r10.left
            float r0 = (float) r0
            int r10 = r10.top
            float r10 = (float) r10
            r5.translate(r0, r10)
            r9.draw(r5)
            r5.restore()
            goto Lb4
        L8a:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Badge location "
            r8.<init>(r9)
            r8.append(r10)
            java.lang.String r9 = " not in badged drawable bounds "
            r8.append(r9)
            android.graphics.Rect r9 = new android.graphics.Rect
            r9.<init>(r4, r4, r0, r1)
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        Lab:
            r9.mutate()
            r9.setBounds(r4, r4, r0, r1)
            r9.draw(r5)
        Lb4:
            if (r3 != 0) goto Ld1
            android.graphics.drawable.BitmapDrawable r9 = new android.graphics.drawable.BitmapDrawable
            android.app.ContextImpl r7 = r7.mContext
            android.content.res.Resources r7 = r7.getResources()
            r9.<init>(r7, r11)
            if (r2 == 0) goto Ld0
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            android.graphics.Bitmap r7 = r8.getBitmap()
            int r7 = r7.getDensity()
            r9.setTargetDensity(r7)
        Ld0:
            return r9
        Ld1:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ApplicationPackageManager.getBadgedDrawable(android.graphics.drawable.Drawable, android.graphics.drawable.Drawable, android.graphics.Rect, boolean):android.graphics.drawable.Drawable");
    }

    private boolean hasUserBadge(int i) {
        return getUserManager().hasBadge(i);
    }

    private int getBadgeResIdForUser(int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            return R.drawable.sf_badge_circle_full;
        }
        if (SemPersonaManager.isAppSeparationUserId(i)) {
            return R.drawable.apps_separated;
        }
        if (SemDualAppManager.isDualAppId(i)) {
            return R.drawable.ic_dualapp_badge;
        }
        return 0;
    }

    @Override // android.content.pm.PackageManager
    public int getInstallReason(String str, UserHandle userHandle) {
        try {
            return this.mPM.getInstallReason(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class MoveCallbackDelegate extends IPackageMoveObserver.Stub implements Handler.Callback {
        private static final int MSG_CREATED = 1;
        private static final int MSG_STATUS_CHANGED = 2;
        final PackageManager.MoveCallback mCallback;
        final Handler mHandler;

        public MoveCallbackDelegate(PackageManager.MoveCallback moveCallback, Looper looper) {
            this.mCallback = moveCallback;
            this.mHandler = new Handler(looper, this);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                this.mCallback.onCreated(someArgs.argi1, (Bundle) someArgs.arg2);
                someArgs.recycle();
                return true;
            }
            if (i != 2) {
                return false;
            }
            SomeArgs someArgs2 = (SomeArgs) message.obj;
            this.mCallback.onStatusChanged(someArgs2.argi1, someArgs2.argi2, ((Long) someArgs2.arg3).longValue());
            someArgs2.recycle();
            return true;
        }

        @Override // android.content.pm.IPackageMoveObserver
        public void onCreated(int i, Bundle bundle) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg2 = bundle;
            this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }

        @Override // android.content.pm.IPackageMoveObserver
        public void onStatusChanged(int i, int i2, long j) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.arg3 = Long.valueOf(j);
            this.mHandler.obtainMessage(2, obtain).sendToTarget();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean canRequestPackageInstalls() {
        try {
            return this.mPM.canRequestPackageInstalls(this.mContext.getPackageName(), getUserId());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public ComponentName getInstantAppResolverSettingsComponent() {
        try {
            return this.mPM.getInstantAppResolverSettingsComponent();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public ComponentName getInstantAppInstallerComponent() {
        try {
            return this.mPM.getInstantAppInstallerComponent();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getInstantAppAndroidId(String str, UserHandle userHandle) {
        try {
            return this.mPM.getInstantAppAndroidId(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    private static class DexModuleRegisterResult {
        final String dexModulePath;
        final String message;
        final boolean success;

        private DexModuleRegisterResult(String str, boolean z, String str2) {
            this.dexModulePath = str;
            this.success = z;
            this.message = str2;
        }
    }

    private static class DexModuleRegisterCallbackDelegate extends IDexModuleRegisterCallback.Stub implements Handler.Callback {
        private static final int MSG_DEX_MODULE_REGISTERED = 1;
        private final PackageManager.DexModuleRegisterCallback callback;
        private final Handler mHandler = new Handler(Looper.getMainLooper(), this);

        DexModuleRegisterCallbackDelegate(PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback) {
            this.callback = dexModuleRegisterCallback;
        }

        @Override // android.content.pm.IDexModuleRegisterCallback
        public void onDexModuleRegistered(String str, boolean z, String str2) throws RemoteException {
            this.mHandler.obtainMessage(1, new DexModuleRegisterResult(str, z, str2)).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            DexModuleRegisterResult dexModuleRegisterResult = (DexModuleRegisterResult) message.obj;
            this.callback.onDexModuleRegistered(dexModuleRegisterResult.dexModulePath, dexModuleRegisterResult.success, dexModuleRegisterResult.message);
            return true;
        }
    }

    @Override // android.content.pm.PackageManager
    public void registerDexModule(String str, PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback) {
        DexModuleRegisterCallbackDelegate dexModuleRegisterCallbackDelegate = dexModuleRegisterCallback != null ? new DexModuleRegisterCallbackDelegate(dexModuleRegisterCallback) : null;
        try {
            try {
                this.mPM.registerDexModule(this.mContext.getPackageName(), str, (Os.stat(str).st_mode & OsConstants.S_IROTH) != 0, dexModuleRegisterCallbackDelegate);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        } catch (ErrnoException e2) {
            if (dexModuleRegisterCallbackDelegate != null) {
                dexModuleRegisterCallback.onDexModuleRegistered(str, false, "Could not get stat the module file: " + e2.getMessage());
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public CharSequence getHarmfulAppWarning(String str) {
        try {
            return this.mPM.getHarmfulAppWarning(str, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setHarmfulAppWarning(String str, CharSequence charSequence) {
        try {
            this.mPM.setHarmfulAppWarning(str, charSequence, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public ArtManager getArtManager() {
        if (this.mArtManager == null) {
            try {
                this.mArtManager = new ArtManager(this.mContext, this.mPM.getArtManager());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mArtManager;
    }

    @Override // android.content.pm.PackageManager
    public String getDefaultTextClassifierPackageName() {
        try {
            return this.mPM.getDefaultTextClassifierPackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getSystemTextClassifierPackageName() {
        try {
            return this.mPM.getSystemTextClassifierPackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getAttentionServicePackageName() {
        try {
            return this.mPM.getAttentionServicePackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getRotationResolverPackageName() {
        try {
            return this.mPM.getRotationResolverPackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getWellbeingPackageName() {
        try {
            return this.mPM.getWellbeingPackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getAppPredictionServicePackageName() {
        try {
            return this.mPM.getAppPredictionServicePackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getSystemCaptionsServicePackageName() {
        try {
            return this.mPM.getSystemCaptionsServicePackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getSetupWizardPackageName() {
        try {
            return this.mPM.getSetupWizardPackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public String getIncidentReportApproverPackageName() {
        try {
            return this.mPM.getIncidentReportApproverPackageName();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageStateProtected(String str, int i) {
        try {
            return this.mPM.isPackageStateProtected(str, i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void sendDeviceCustomizationReadyBroadcast() {
        try {
            this.mPM.sendDeviceCustomizationReadyBroadcast();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isAutoRevokeWhitelisted() {
        try {
            return this.mPM.isAutoRevokeWhitelisted(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setMimeGroup(String str, Set<String> set) {
        try {
            this.mPM.setMimeGroup(this.mContext.getPackageName(), str, new ArrayList(set));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public Set<String> getMimeGroup(String str) {
        try {
            return new ArraySet(this.mPM.getMimeGroup(this.mContext.getPackageName(), str));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public PackageManager.Property getProperty(String str, String str2) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(str2);
        Objects.requireNonNull(str);
        return getPropertyAsUser(str, str2, null, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public PackageManager.Property getProperty(String str, ComponentName componentName) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(componentName);
        Objects.requireNonNull(str);
        return getPropertyAsUser(str, componentName.getPackageName(), componentName.getClassName(), getUserId());
    }

    @Override // android.content.pm.PackageManager
    public PackageManager.Property getPropertyAsUser(String str, String str2, String str3, int i) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(str2);
        Objects.requireNonNull(str);
        try {
            PackageManager.Property propertyAsUser = this.mPM.getPropertyAsUser(str, str2, str3, i);
            if (propertyAsUser != null) {
                return propertyAsUser;
            }
            throw new PackageManager.NameNotFoundException();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageManager.Property> queryApplicationProperty(String str) {
        Objects.requireNonNull(str);
        try {
            ParceledListSlice queryProperty = this.mPM.queryProperty(str, 5);
            if (queryProperty == null) {
                return Collections.EMPTY_LIST;
            }
            return queryProperty.getList();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageManager.Property> queryActivityProperty(String str) {
        Objects.requireNonNull(str);
        try {
            ParceledListSlice queryProperty = this.mPM.queryProperty(str, 1);
            if (queryProperty == null) {
                return Collections.EMPTY_LIST;
            }
            return queryProperty.getList();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageManager.Property> queryProviderProperty(String str) {
        Objects.requireNonNull(str);
        try {
            ParceledListSlice queryProperty = this.mPM.queryProperty(str, 4);
            if (queryProperty == null) {
                return Collections.EMPTY_LIST;
            }
            return queryProperty.getList();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageManager.Property> queryReceiverProperty(String str) {
        Objects.requireNonNull(str);
        try {
            ParceledListSlice queryProperty = this.mPM.queryProperty(str, 2);
            if (queryProperty == null) {
                return Collections.EMPTY_LIST;
            }
            return queryProperty.getList();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageManager.Property> queryServiceProperty(String str) {
        Objects.requireNonNull(str);
        try {
            ParceledListSlice queryProperty = this.mPM.queryProperty(str, 3);
            if (queryProperty == null) {
                return Collections.EMPTY_LIST;
            }
            return queryProperty.getList();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isUnknownSourcePackage(String str) {
        try {
            return this.mPM.isUnknownSourcePackage(str);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public List<PackageInfo> getUnknownSourcePackages(int i) {
        int userId = getUserId();
        try {
            ParceledListSlice unknownSourcePackagesAsUser = this.mPM.getUnknownSourcePackagesAsUser(updateFlagsForPackage(i, userId), userId);
            if (unknownSourcePackagesAsUser == null) {
                return Collections.EMPTY_LIST;
            }
            return unknownSourcePackagesAsUser.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean canPackageQuery(String str, String str2) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        return canPackageQuery(str, new String[]{str2})[0];
    }

    @Override // android.content.pm.PackageManager
    public boolean[] canPackageQuery(String str, String[] strArr) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(str);
        Objects.requireNonNull(strArr);
        try {
            return this.mPM.canPackageQuery(str, strArr, getUserId());
        } catch (ParcelableException e) {
            e.maybeRethrow(PackageManager.NameNotFoundException.class);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public int semGetSystemFeatureLevel(String str) {
        if (str == null) {
            return 0;
        }
        try {
            ParceledListSlice systemAvailableFeatures = this.mPM.getSystemAvailableFeatures();
            if (systemAvailableFeatures == null) {
                return 0;
            }
            for (FeatureInfo featureInfo : systemAvailableFeatures.getList()) {
                if (featureInfo.name != null && featureInfo.name.equals(str)) {
                    return featureInfo.version;
                }
            }
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean semIsPermissionRevokedByUserFixed(String str, String str2) {
        try {
            return this.mPM.semIsPermissionRevokedByUserFixed(str, str2, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public PackageInfo semGetPackageInfoAsUser(String str, int i, int i2) throws PackageManager.NameNotFoundException {
        return getPackageInfoAsUser(str, PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public boolean semIsInstalledPackageHiddenAsUser(String str, int i) {
        try {
            return this.mPM.semIsInstalledPackageHiddenAsUser(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class LiveIconObject implements Cloneable {
        private Object liveIcon;

        public Object getLiveIcon() {
            return this.liveIcon;
        }

        public void setLiveIcon(Object obj) {
            this.liveIcon = obj;
        }

        public Object clone() throws CloneNotSupportedException {
            return (LiveIconObject) super.clone();
        }
    }

    private Drawable getLiveIcon(PackageItemInfo packageItemInfo, int i, boolean z) {
        String str;
        String packagePath;
        Method findGetLiveIconMethod;
        if (!supportLiveIconByPackage(packageItemInfo).booleanValue() || (packagePath = getPackagePath((str = packageItemInfo.packageName))) == null) {
            return null;
        }
        String str2 = str + LIVE_ICON_SUFFIX;
        ArrayMap<String, String> arrayMap = sLiveIconPackageMatchers;
        synchronized (arrayMap) {
            ArrayMap<String, Method> arrayMap2 = sLiveIconLoaders;
            synchronized (arrayMap2) {
                if (arrayMap.containsKey(str)) {
                    if (packagePath.equals(arrayMap.get(str))) {
                        Log.d(TAG, "we has " + str + " class. reuse it ");
                        findGetLiveIconMethod = arrayMap2.get(str);
                    } else if (packagePath.equals(arrayMap.get(str))) {
                        findGetLiveIconMethod = null;
                    } else {
                        Log.d(TAG, "we don't have " + packagePath + " package path. load it");
                        try {
                            findGetLiveIconMethod = findGetLiveIconMethod(Class.forName(str2, true, new PathClassLoader(packagePath, ClassLoader.getSystemClassLoader())));
                            arrayMap.remove(str);
                            arrayMap2.remove(str);
                            arrayMap.put(str, packagePath);
                            arrayMap2.put(str, findGetLiveIconMethod);
                        } catch (ClassNotFoundException unused) {
                            Log.e(TAG, "!@can't found class" + str2);
                            return null;
                        }
                    }
                } else {
                    Log.d(TAG, "we don't have '" + str + "' package name. load it");
                    try {
                        findGetLiveIconMethod = findGetLiveIconMethod(Class.forName(str2, true, new PathClassLoader(packagePath, ClassLoader.getSystemClassLoader())));
                        arrayMap.put(str, packagePath);
                        arrayMap2.put(str, findGetLiveIconMethod);
                    } catch (ClassNotFoundException unused2) {
                        Log.e(TAG, "!@can't found class" + str2);
                        return null;
                    }
                }
            }
        }
        boolean z2 = (i & 256) != 0;
        if (findGetLiveIconMethod == null) {
            return null;
        }
        try {
            LiveIconObject liveIconObject = new LiveIconObject();
            Log.i(TAG, "package : " + str + ", useAppIconResources : " + z);
            Resources appIconPackageResources = z ? this.mAppIconSolution.getAppIconPackageResources(this.mContext) : null;
            if (findGetLiveIconMethod.getParameterCount() == 1) {
                liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext));
            } else if (findGetLiveIconMethod.getParameterCount() == 2) {
                if (z2) {
                    liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext, 1));
                } else {
                    liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext, 0));
                }
            } else if (findGetLiveIconMethod.getParameterCount() != 3) {
                ComponentName componentName = new ComponentName(str, packageItemInfo.name != null ? packageItemInfo.name : "");
                if (z2) {
                    liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext, 1, appIconPackageResources, componentName));
                } else {
                    liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext, 0, appIconPackageResources, componentName));
                }
            } else if (z2) {
                liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext, 1, appIconPackageResources));
            } else {
                liveIconObject.setLiveIcon(findGetLiveIconMethod.invoke(null, this.mContext, 0, appIconPackageResources));
            }
            Object liveIcon = ((LiveIconObject) liveIconObject.clone()).getLiveIcon();
            if (liveIcon instanceof Drawable) {
                return (Drawable) liveIcon;
            }
            Log.i(TAG, "Abnormal object has returned for liveicon : " + liveIcon);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "FAILED to getLiveIcon", e);
            e.printStackTrace();
            return null;
        }
    }

    private String getPackagePath(String str) {
        try {
            return getApplicationInfo(str, 8320).sourceDir;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.i(TAG, "get application info error in getLiveIcon : " + str);
            return null;
        }
    }

    private Boolean supportLiveIconByPackage(PackageItemInfo packageItemInfo) {
        Bundle bundle;
        String str;
        try {
            if (packageItemInfo instanceof ActivityInfo) {
                bundle = getActivityInfo(new ComponentName(packageItemInfo.packageName, packageItemInfo.name), 128).metaData;
                str = packageItemInfo.name;
            } else {
                bundle = getApplicationInfo(packageItemInfo.packageName, 8320).metaData;
                str = packageItemInfo.packageName;
            }
            String str2 = str;
            if (bundle != null && bundle.getBoolean("LiveIconSupport")) {
                return true;
            }
            Log.d(TAG, "App doesn't support live icon : [" + str2 + "]. just show default Icon.");
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.i(TAG, "get application info error in getLiveIcon : ");
            return false;
        }
    }

    private Method findGetLiveIconMethod(Class<?> cls) {
        try {
            try {
                try {
                    try {
                        return cls.getMethod("getLiveIcon", Context.class, Integer.TYPE, Resources.class, ComponentName.class);
                    } catch (NoSuchMethodException unused) {
                        return cls.getMethod("getLiveIcon", Context.class);
                    }
                } catch (NoSuchMethodException unused2) {
                    return cls.getMethod("getLiveIcon", Context.class, Integer.TYPE);
                }
            } catch (NoSuchMethodException unused3) {
                return cls.getMethod("getLiveIcon", Context.class, Integer.TYPE, Resources.class);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "!@call method fail getLiveIcon", e);
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public Drawable semGetActivityIconForIconTray(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadIcon(this, true, i);
    }

    @Override // android.content.pm.PackageManager
    public Drawable semGetApplicationIconForIconTray(ApplicationInfo applicationInfo, int i) {
        return applicationInfo.loadIcon(this, true, i);
    }

    @Override // android.content.pm.PackageManager
    public Drawable semGetApplicationIconForIconTray(String str, int i) throws PackageManager.NameNotFoundException {
        return semGetApplicationIconForIconTray(getApplicationInfo(str, 1024), i);
    }

    private Drawable hidden_semGetApplicationIconForIconTray(String str, int i) throws PackageManager.NameNotFoundException {
        return semGetApplicationIconForIconTray(str, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean semShouldPackIntoIconTray(String str) {
        if (isNonAdaptiveIconPkg(str)) {
            return true;
        }
        try {
            ArrayList arrayList = new ArrayList();
            boolean metadataForIconTray = this.mPM.getMetadataForIconTray(str, "com.samsung.android.icon_container.has_icon_container", this.mContext.getUserId(), arrayList);
            boolean z = !metadataForIconTray;
            if (metadataForIconTray && !arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if ("ADAPTIVEICON_SHADOW".equals((String) it.next())) {
                        return true;
                    }
                }
            }
            if (metadataForIconTray) {
                Log.i("AppIconSolution", "has_icon_container is maintained so ignore icon processing, pkg = " + str);
            }
            return z;
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.content.pm.PackageManager
    public Drawable semGetDrawableForIconTray(Drawable drawable, int i) {
        return semGetDrawableForIconTray(drawable, i, null, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0074  */
    @Override // android.content.pm.PackageManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.drawable.Drawable semGetDrawableForIconTray(android.graphics.drawable.Drawable r10, int r11, java.lang.String r12, int r13) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ApplicationPackageManager.semGetDrawableForIconTray(android.graphics.drawable.Drawable, int, java.lang.String, int):android.graphics.drawable.Drawable");
    }

    private boolean replacedIconFromAppPolicy(String str, int i) {
        if (str == null) {
            return false;
        }
        try {
            if (this.mApplicationPolicy == null) {
                this.mApplicationPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
            }
            return this.mApplicationPolicy.getApplicationIconFromDb(str, i) != null;
        } catch (Exception unused) {
            Log.e(TAG, "Exception occurred in EnterpriseDeviceManager");
            return false;
        }
    }

    @Override // android.content.pm.PackageManager
    public int semGetAppIconFeatures(String str) {
        if (isNonAdaptiveIconPkg(str)) {
            return 4;
        }
        int i = 0;
        try {
            ArrayList<String> arrayList = new ArrayList();
            if (this.mPM.getMetadataForIconTray(str, "com.samsung.android.icon_container.has_icon_container", this.mContext.getUserId(), arrayList)) {
                Log.i("AppIconSolution", "has_icon_container is maintained so ignore icon processing, pkg = " + str);
                i = 1;
            }
            if (!arrayList.isEmpty()) {
                for (String str2 : arrayList) {
                    if ("ADAPTIVEICON_SHADOW".equals(str2)) {
                        i |= 2;
                    } else if ("COLOR_NO_ADAPTIVE".equals(str2)) {
                        i |= 4;
                    } else if ("COLOR_ONLY_BG".equals(str2)) {
                        i |= 8;
                    }
                }
            }
        } catch (RemoteException unused) {
        }
        return i;
    }

    private boolean isNonAdaptiveIconPkg(String str) {
        return SamsungThemeConstants.nonAdaptiveIconPkgList.contains(str);
    }

    @Override // android.content.pm.PackageManager
    public void makeUidVisible(int i, int i2) {
        try {
            this.mPM.makeUidVisible(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public ArchivedPackageInfo getArchivedPackage(String str) {
        try {
            ArchivedPackageParcel archivedPackage = this.mPM.getArchivedPackage(str, this.mContext.getUserId());
            if (archivedPackage == null) {
                return null;
            }
            return new ArchivedPackageInfo(archivedPackage);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean canUserUninstall(String str, UserHandle userHandle) {
        try {
            return this.mPM.getBlockUninstallForUser(str, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean shouldShowNewAppInstalledNotification() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.SHOW_NEW_APP_INSTALLED_NOTIFICATION_ENABLED, 0) == 1;
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageAutoDisabled(String str, int i) {
        try {
            return this.mPM.isPackageAutoDisabled(str, i);
        } catch (RemoteException unused) {
            Log.e(TAG, "Exception to get lastDisableCaller");
            return false;
        }
    }

    @Override // android.content.pm.PackageManager
    public void relinquishUpdateOwnership(String str) {
        Objects.requireNonNull(str);
        try {
            this.mPM.relinquishUpdateOwnership(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void registerPackageMonitorCallback(IRemoteCallback iRemoteCallback, int i) {
        Objects.requireNonNull(iRemoteCallback);
        try {
            this.mPM.registerPackageMonitorCallback(iRemoteCallback, i);
            synchronized (this.mPackageMonitorCallbacks) {
                if (this.mPackageMonitorCallbacks.contains(iRemoteCallback)) {
                    throw new IllegalStateException("registerPackageMonitorCallback: callback already registered: " + iRemoteCallback);
                }
                this.mPackageMonitorCallbacks.add(iRemoteCallback);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void unregisterPackageMonitorCallback(IRemoteCallback iRemoteCallback) {
        Objects.requireNonNull(iRemoteCallback);
        try {
            this.mPM.unregisterPackageMonitorCallback(iRemoteCallback);
            synchronized (this.mPackageMonitorCallbacks) {
                this.mPackageMonitorCallbacks.remove(iRemoteCallback);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Drawable getArchivedAppIcon(String str) {
        try {
            Bitmap archivedAppIcon = this.mPM.getArchivedAppIcon(str, new UserHandle(getUserId()), this.mContext.getPackageName());
            if (archivedAppIcon == null) {
                return null;
            }
            return new BitmapDrawable((Resources) null, archivedAppIcon);
        } catch (RemoteException e) {
            Slog.e(TAG, "Failed to retrieve archived app icon: " + e.getMessage());
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public <T> T parseAndroidManifest(File file, Function<XmlResourceParser, T> function) throws IOException {
        Objects.requireNonNull(file, "apkFile cannot be null");
        Objects.requireNonNull(function, "parserFunction cannot be null");
        try {
            XmlResourceParser androidManifestParser = getAndroidManifestParser(file);
            try {
                T apply = function.apply(androidManifestParser);
                if (androidManifestParser != null) {
                    androidManifestParser.close();
                }
                return apply;
            } finally {
            }
        } catch (IOException e) {
            Log.w(TAG, "Failed to get the android manifest parser", e);
            throw e;
        }
    }

    private static XmlResourceParser getAndroidManifestParser(File file) throws IOException {
        ApkAssets apkAssets = null;
        try {
            apkAssets = ApkAssets.loadFromPath(file.getAbsolutePath());
            XmlResourceParser openXml = apkAssets.openXml("AndroidManifest.xml");
            if (apkAssets != null) {
                try {
                    return openXml;
                } catch (Throwable th) {
                }
            }
            return openXml;
        } finally {
            if (apkAssets != null) {
                try {
                    apkAssets.close();
                } catch (Throwable th2) {
                    Log.w(TAG, "Failed to close apkAssets", th2);
                }
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public <T> T parseAndroidManifest(ParcelFileDescriptor parcelFileDescriptor, Function<XmlResourceParser, T> function) throws IOException {
        Objects.requireNonNull(parcelFileDescriptor, "apkFileDescriptor cannot be null");
        Objects.requireNonNull(function, "parserFunction cannot be null");
        try {
            XmlResourceParser androidManifestParser = getAndroidManifestParser(parcelFileDescriptor);
            try {
                T apply = function.apply(androidManifestParser);
                if (androidManifestParser != null) {
                    androidManifestParser.close();
                }
                return apply;
            } finally {
            }
        } catch (IOException e) {
            Log.w(TAG, "Failed to get the android manifest parser", e);
            throw e;
        }
    }

    private static XmlResourceParser getAndroidManifestParser(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        ApkAssets apkAssets = null;
        try {
            apkAssets = ApkAssets.loadFromFd(parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor.toString(), 0, null);
            XmlResourceParser openXml = apkAssets.openXml("AndroidManifest.xml");
            if (apkAssets != null) {
                try {
                    return openXml;
                } catch (Throwable th) {
                }
            }
            return openXml;
        } finally {
            if (apkAssets != null) {
                try {
                    apkAssets.close();
                } catch (Throwable th2) {
                    Log.w(TAG, "Failed to close apkAssets", th2);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0027, code lost:
    
        if (r3 != null) goto L11;
     */
    @Override // android.content.pm.PackageManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.content.res.TypedArray extractPackageItemInfoAttributes(android.content.pm.PackageItemInfo r7, java.lang.String r8, java.lang.String r9, int[] r10) {
        /*
            r6 = this;
            java.lang.String r0 = "ApplicationPackageManager"
            java.lang.String r1 = "No "
            r2 = 0
            if (r7 == 0) goto L94
            android.os.Bundle r3 = r7.metaData
            if (r3 != 0) goto Ld
            goto L94
        Ld:
            android.content.res.XmlResourceParser r3 = r7.loadXmlMetaData(r6, r8)     // Catch: java.lang.Throwable -> L80
            if (r3 != 0) goto L2d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L74
            r6.append(r8)     // Catch: java.lang.Throwable -> L74
            java.lang.String r8 = " metadata"
            r6.append(r8)     // Catch: java.lang.Throwable -> L74
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L74
            android.util.Log.w(r0, r6)     // Catch: java.lang.Throwable -> L74
            if (r3 == 0) goto L2c
        L29:
            r3.close()     // Catch: java.lang.Throwable -> L80 java.lang.Throwable -> L80 java.lang.Throwable -> L80
        L2c:
            return r2
        L2d:
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r3)     // Catch: java.lang.Throwable -> L74
        L31:
            int r4 = r3.next()     // Catch: java.lang.Throwable -> L74
            r5 = 1
            if (r4 == r5) goto L3b
            r5 = 2
            if (r4 != r5) goto L31
        L3b:
            java.lang.String r4 = r3.getName()     // Catch: java.lang.Throwable -> L74
            boolean r9 = android.text.TextUtils.equals(r4, r9)     // Catch: java.lang.Throwable -> L74
            if (r9 != 0) goto L62
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74
            r6.<init>()     // Catch: java.lang.Throwable -> L74
            java.lang.String r9 = "Metadata does not start with "
            r6.append(r9)     // Catch: java.lang.Throwable -> L74
            r6.append(r8)     // Catch: java.lang.Throwable -> L74
            java.lang.String r8 = " tag"
            r6.append(r8)     // Catch: java.lang.Throwable -> L74
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L74
            android.util.Log.w(r0, r6)     // Catch: java.lang.Throwable -> L74
            if (r3 == 0) goto L61
            goto L29
        L61:
            return r2
        L62:
            android.content.pm.ApplicationInfo r8 = r7.getApplicationInfo()     // Catch: java.lang.Throwable -> L74
            android.content.res.Resources r6 = r6.getResourcesForApplication(r8)     // Catch: java.lang.Throwable -> L74
            android.content.res.TypedArray r6 = r6.obtainAttributes(r1, r10)     // Catch: java.lang.Throwable -> L74
            if (r3 == 0) goto L73
            r3.close()     // Catch: java.lang.Throwable -> L80 java.lang.Throwable -> L80 java.lang.Throwable -> L80
        L73:
            return r6
        L74:
            r6 = move-exception
            if (r3 == 0) goto L7f
            r3.close()     // Catch: java.lang.Throwable -> L7b
            goto L7f
        L7b:
            r8 = move-exception
            r6.addSuppressed(r8)     // Catch: java.lang.Throwable -> L80 java.lang.Throwable -> L80 java.lang.Throwable -> L80
        L7f:
            throw r6     // Catch: java.lang.Throwable -> L80 java.lang.Throwable -> L80 java.lang.Throwable -> L80
        L80:
            r6 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Error parsing: "
            r8.<init>(r9)
            java.lang.String r7 = r7.packageName
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.e(r0, r7, r6)
        L94:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.ApplicationPackageManager.extractPackageItemInfoAttributes(android.content.pm.PackageItemInfo, java.lang.String, java.lang.String, int[]):android.content.res.TypedArray");
    }

    @Override // android.content.pm.PackageManager
    public boolean shouldAppSupportBadgeIcon(String str, int i) {
        if (str == null || i != 0) {
            return false;
        }
        try {
            return this.mPM.shouldAppSupportBadgeIcon(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Drawable getMonetizeBadgedIcon(Drawable drawable) {
        return getBadgedDrawable(drawable, getDrawable("system", R.drawable.monetization_badge, null), new Rect(0, 0, drawable.getIntrinsicWidth() / 4, drawable.getIntrinsicHeight() / 4), true);
    }

    @Override // android.content.pm.PackageManager
    public void setAppCategoryHintUser(String str, int i) {
        try {
            this.mPM.setAppCategoryHintUser(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearAppCategoryHintUser(String str) {
        try {
            this.mPM.clearAppCategoryHintUser(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setAppCategoryHintDeveloper(String str, int i) {
        try {
            this.mPM.setAppCategoryHintDeveloper(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearAppCategoryHintDeveloper(String str) {
        try {
            this.mPM.clearAppCategoryHintDeveloper(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Map<String, String> getAppCategoryHintUserMap() {
        try {
            return this.mPM.getAppCategoryHintUserMap();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public Map<String, String[]> getAppCategoryInfos(String str) {
        try {
            return this.mPM.getAppCategoryInfos(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
