package android.app;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.p001om.SamsungThemeConstants;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ApkChecksum;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.ChangedPackages;
import android.content.p002pm.ComponentInfo;
import android.content.p002pm.FeatureInfo;
import android.content.p002pm.IDexModuleRegisterCallback;
import android.content.p002pm.IMemorySaverPackageMoveObserver;
import android.content.p002pm.IOnChecksumsReadyListener;
import android.content.p002pm.IPackageDataObserver;
import android.content.p002pm.IPackageDeleteObserver;
import android.content.p002pm.IPackageManager;
import android.content.p002pm.IPackageMoveObserver;
import android.content.p002pm.IPackageStatsObserver;
import android.content.p002pm.InstallSourceInfo;
import android.content.p002pm.InstantAppInfo;
import android.content.p002pm.InstrumentationInfo;
import android.content.p002pm.IntentFilterVerificationInfo;
import android.content.p002pm.KeySet;
import android.content.p002pm.ModuleInfo;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageInstaller;
import android.content.p002pm.PackageItemInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ParceledListSlice;
import android.content.p002pm.PermissionGroupInfo;
import android.content.p002pm.PermissionInfo;
import android.content.p002pm.ProviderInfo;
import android.content.p002pm.ResolveInfo;
import android.content.p002pm.ServiceInfo;
import android.content.p002pm.SharedLibraryInfo;
import android.content.p002pm.SuspendDialogInfo;
import android.content.p002pm.UserInfo;
import android.content.p002pm.VerifierDeviceIdentity;
import android.content.p002pm.VersionedPackage;
import android.content.p002pm.dex.ArtManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.ParcelFileDescriptor;
import android.p009os.ParcelableException;
import android.p009os.PersistableBundle;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.StrictMode;
import android.p009os.SystemProperties;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.p009os.storage.StorageManager;
import android.p009os.storage.VolumeInfo;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.sec.enterprise.ApplicationPolicy;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LauncherIcons;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import com.android.internal.C4337R;
import com.android.internal.p029os.SomeArgs;
import com.android.internal.util.UserIcons;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.p036pm.AbiAppHelper;
import com.samsung.android.core.p036pm.PmUtils;
import com.samsung.android.core.p036pm.p037mm.MaintenanceModeUtils;
import com.samsung.android.ims.options.SemCapabilities;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.PMRune;
import dalvik.system.PathClassLoader;
import dalvik.system.VMRuntime;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import libcore.util.EmptyArray;

/* loaded from: classes.dex */
public class ApplicationPackageManager extends PackageManager {
  public static final String APP_PERMISSION_BUTTON_ALLOW_ALWAYS =
      "app_permission_button_allow_always";
  private static final boolean DEBUG_ICONS = false;
  private static final int DEFAULT_CHECKSUMS = 127;
  private static final int DEFAULT_EPHEMERAL_COOKIE_MAX_SIZE_BYTES = 16384;
  public static final String PERMISSION_CONTROLLER_RESOURCE_PACKAGE =
      "com.android.permissioncontroller";
  private static final String TAG = "ApplicationPackageManager";
  private static final int sDefaultFlags = 1024;
  private volatile ArtManager mArtManager;
  private final ContextImpl mContext;
  private volatile DevicePolicyManager mDevicePolicyManager;
  private volatile PackageInstaller mInstaller;
  private KnoxSdkHook mKnoxSdkHook;
  private final IPackageManager mPM;
  private volatile PermissionManager mPermissionManager;
  private volatile String mPermissionsControllerPackageName;
  private volatile UserManager mUserManager;
  private static final ArrayMap<String, Method> sLiveIconLoaders = new ArrayMap<>();
  private static final ArrayMap<String, String> sLiveIconPackageMatchers = new ArrayMap<>();
  private static final PropertyInvalidatedCache<HasSystemFeatureQuery, Boolean>
      mHasSystemFeatureCache =
          new PropertyInvalidatedCache<HasSystemFeatureQuery, Boolean>(
              256,
              "cache_key.has_system_feature") { // from class:
                                                // android.app.ApplicationPackageManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Boolean recompute(HasSystemFeatureQuery query) {
              try {
                ActivityThread.currentActivityThread();
                return Boolean.valueOf(
                    ActivityThread.getPackageManager().hasSystemFeature(query.name, query.version));
              } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
              }
            }
          };
  private static final String CACHE_KEY_PACKAGES_FOR_UID_PROPERTY =
      "cache_key.get_packages_for_uid";
  private static final PropertyInvalidatedCache<Integer, GetPackagesForUidResult>
      mGetPackagesForUidCache =
          new PropertyInvalidatedCache<Integer, GetPackagesForUidResult>(
              32,
              CACHE_KEY_PACKAGES_FOR_UID_PROPERTY) { // from class:
                                                     // android.app.ApplicationPackageManager.3
            @Override // android.app.PropertyInvalidatedCache
            public GetPackagesForUidResult recompute(Integer uid) {
              try {
                ActivityThread.currentActivityThread();
                return new GetPackagesForUidResult(
                    ActivityThread.getPackageManager().getPackagesForUid(uid.intValue()));
              } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
              }
            }

            @Override // android.app.PropertyInvalidatedCache
            public String queryToString(Integer uid) {
              return String.format("uid=%d", Integer.valueOf(uid.intValue()));
            }
          };
  private static final Object sSync = new Object();
  private static ArrayMap<ResourceName, WeakReference<Drawable.ConstantState>> sIconCache =
      new ArrayMap<>();
  private static ArrayMap<ResourceName, WeakReference<CharSequence>> sStringCache =
      new ArrayMap<>();
  private AbiAppHelper mAbiAppHelper = new AbiAppHelper();
  private final ArrayList<MoveCallbackDelegate> mDelegates = new ArrayList<>();
  volatile int mCachedSafeMode = -1;
  private volatile boolean mUserUnlocked = false;
  private SemAppIconSolution mAppIconSolution = null;
  private ApplicationPolicy mApplicationPolicy = null;
  private final String FEATURE_ADAPTIVEICON_SHADOW = "ADAPTIVEICON_SHADOW";
  private final String FEATURE_COLOR_NO_ADAPTIVE = "COLOR_NO_ADAPTIVE";
  private final String FEATURE_COLOR_ONLY_BG = "COLOR_ONLY_BG";

  UserManager getUserManager() {
    if (this.mUserManager == null) {
      this.mUserManager = UserManager.get(this.mContext);
    }
    return this.mUserManager;
  }

  DevicePolicyManager getDevicePolicyManager() {
    if (this.mDevicePolicyManager == null) {
      this.mDevicePolicyManager =
          (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
    }
    return this.mDevicePolicyManager;
  }

  private PermissionManager getPermissionManager() {
    if (this.mPermissionManager == null) {
      this.mPermissionManager =
          (PermissionManager) this.mContext.getSystemService(PermissionManager.class);
    }
    return this.mPermissionManager;
  }

  @Override // android.content.p002pm.PackageManager
  public int getUserId() {
    return this.mContext.getUserId();
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo getPackageInfo(String packageName, int flags)
      throws PackageManager.NameNotFoundException {
    return getPackageInfo(packageName, PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo getPackageInfo(String packageName, PackageManager.PackageInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    return getPackageInfoAsUser(packageName, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo getPackageInfo(VersionedPackage versionedPackage, int flags)
      throws PackageManager.NameNotFoundException {
    return getPackageInfo(versionedPackage, PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo getPackageInfo(
      VersionedPackage versionedPackage, PackageManager.PackageInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    int userId = getUserId();
    try {
      PackageInfo pi =
          this.mPM.getPackageInfoVersioned(
              versionedPackage, updateFlagsForPackage(flags.getValue(), userId), userId);
      if (pi != null) {
        return pi;
      }
      throw new PackageManager.NameNotFoundException(versionedPackage.toString());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo getPackageInfoAsUser(String packageName, int flags, int userId)
      throws PackageManager.NameNotFoundException {
    return getPackageInfoAsUser(packageName, PackageManager.PackageInfoFlags.m11of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo getPackageInfoAsUser(
      String packageName, PackageManager.PackageInfoFlags flags, int userId)
      throws PackageManager.NameNotFoundException {
    PackageInfo pi =
        getPackageInfoAsUserCached(
            packageName, updateFlagsForPackage(flags.getValue(), userId), userId);
    if (pi == null) {
      throw new PackageManager.NameNotFoundException(packageName);
    }
    return pi;
  }

  @Override // android.content.p002pm.PackageManager
  public String[] currentToCanonicalPackageNames(String[] names) {
    try {
      return this.mPM.currentToCanonicalPackageNames(names);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] canonicalToCurrentPackageNames(String[] names) {
    try {
      return this.mPM.canonicalToCurrentPackageNames(names);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Intent getLaunchIntentForPackage(String packageName) {
    Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
    intentToResolve.addCategory(Intent.CATEGORY_INFO);
    intentToResolve.setPackage(packageName);
    List<ResolveInfo> ris = queryIntentActivities(intentToResolve, 0);
    if (ris == null || ris.size() <= 0) {
      intentToResolve.removeCategory(Intent.CATEGORY_INFO);
      intentToResolve.addCategory(Intent.CATEGORY_LAUNCHER);
      intentToResolve.setPackage(packageName);
      ris = queryIntentActivities(intentToResolve, 0);
    }
    if (ris == null || ris.size() <= 0) {
      return null;
    }
    Intent intent = new Intent(intentToResolve);
    intent.setFlags(268435456);
    intent.setClassName(ris.get(0).activityInfo.packageName, ris.get(0).activityInfo.name);
    return intent;
  }

  @Override // android.content.p002pm.PackageManager
  public Intent getLeanbackLaunchIntentForPackage(String packageName) {
    return getLaunchIntentForPackageAndCategory(packageName, Intent.CATEGORY_LEANBACK_LAUNCHER);
  }

  @Override // android.content.p002pm.PackageManager
  public Intent getCarLaunchIntentForPackage(String packageName) {
    return getLaunchIntentForPackageAndCategory(packageName, Intent.CATEGORY_CAR_LAUNCHER);
  }

  private Intent getLaunchIntentForPackageAndCategory(String packageName, String category) {
    Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
    intentToResolve.addCategory(category);
    intentToResolve.setPackage(packageName);
    List<ResolveInfo> ris = queryIntentActivities(intentToResolve, 0);
    if (ris == null || ris.size() <= 0) {
      return null;
    }
    Intent intent = new Intent(intentToResolve);
    intent.setFlags(268435456);
    intent.setClassName(ris.get(0).activityInfo.packageName, ris.get(0).activityInfo.name);
    return intent;
  }

  @Override // android.content.p002pm.PackageManager
  public IntentSender getLaunchIntentSenderForPackage(String packageName) {
    try {
      return this.mPM.getLaunchIntentSenderForPackage(
          packageName,
          this.mContext.getPackageName(),
          this.mContext.getAttributionTag(),
          getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int[] getPackageGids(String packageName) throws PackageManager.NameNotFoundException {
    return getPackageGids(packageName, 0);
  }

  @Override // android.content.p002pm.PackageManager
  public int[] getPackageGids(String packageName, int flags)
      throws PackageManager.NameNotFoundException {
    return getPackageGids(packageName, PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public int[] getPackageGids(String packageName, PackageManager.PackageInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    int userId = getUserId();
    try {
      int[] gids =
          this.mPM.getPackageGids(
              packageName, updateFlagsForPackage(flags.getValue(), userId), userId);
      if (gids != null) {
        return gids;
      }
      throw new PackageManager.NameNotFoundException(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getPackageUid(String packageName, int flags)
      throws PackageManager.NameNotFoundException {
    return getPackageUid(packageName, PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public int getPackageUid(String packageName, PackageManager.PackageInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    return getPackageUidAsUser(packageName, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public int getPackageUidAsUser(String packageName, int userId)
      throws PackageManager.NameNotFoundException {
    return getPackageUidAsUser(packageName, 0, userId);
  }

  @Override // android.content.p002pm.PackageManager
  public int getPackageUidAsUser(String packageName, int flags, int userId)
      throws PackageManager.NameNotFoundException {
    return getPackageUidAsUser(packageName, PackageManager.PackageInfoFlags.m11of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public int getPackageUidAsUser(
      String packageName, PackageManager.PackageInfoFlags flags, int userId)
      throws PackageManager.NameNotFoundException {
    try {
      int uid =
          this.mPM.getPackageUid(
              packageName, updateFlagsForPackage(flags.getValue(), userId), userId);
      if (uid >= 0) {
        return uid;
      }
      throw new PackageManager.NameNotFoundException(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
    return getPermissionManager().getAllPermissionGroups(flags);
  }

  @Override // android.content.p002pm.PackageManager
  public PermissionGroupInfo getPermissionGroupInfo(String groupName, int flags)
      throws PackageManager.NameNotFoundException {
    PermissionGroupInfo permissionGroupInfo =
        getPermissionManager().getPermissionGroupInfo(groupName, flags);
    if (permissionGroupInfo == null) {
      throw new PackageManager.NameNotFoundException(groupName);
    }
    return permissionGroupInfo;
  }

  @Override // android.content.p002pm.PackageManager
  public PermissionInfo getPermissionInfo(String permName, int flags)
      throws PackageManager.NameNotFoundException {
    PermissionInfo permissionInfo = getPermissionManager().getPermissionInfo(permName, flags);
    if (permissionInfo == null) {
      throw new PackageManager.NameNotFoundException(permName);
    }
    return permissionInfo;
  }

  @Override // android.content.p002pm.PackageManager
  public List<PermissionInfo> queryPermissionsByGroup(String groupName, int flags)
      throws PackageManager.NameNotFoundException {
    List<PermissionInfo> permissionInfos =
        getPermissionManager().queryPermissionsByGroup(groupName, flags);
    if (permissionInfos == null) {
      throw new PackageManager.NameNotFoundException(groupName);
    }
    return permissionInfos;
  }

  @Override // android.content.p002pm.PackageManager
  public void getPlatformPermissionsForGroup(
      String permissionGroupName, Executor executor, Consumer<List<String>> callback) {
    PermissionControllerManager permissionControllerManager =
        (PermissionControllerManager)
            this.mContext.getSystemService(PermissionControllerManager.class);
    permissionControllerManager.getPlatformPermissionsForGroup(
        permissionGroupName, executor, callback);
  }

  @Override // android.content.p002pm.PackageManager
  public void getGroupOfPlatformPermission(
      String permissionName, Executor executor, Consumer<String> callback) {
    PermissionControllerManager permissionControllerManager =
        (PermissionControllerManager)
            this.mContext.getSystemService(PermissionControllerManager.class);
    permissionControllerManager.getGroupOfPlatformPermission(permissionName, executor, callback);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean arePermissionsIndividuallyControlled() {
    return this.mContext
        .getResources()
        .getBoolean(C4337R.bool.config_permissionsIndividuallyControlled);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isWirelessConsentModeEnabled() {
    return this.mContext.getResources().getBoolean(C4337R.bool.config_wirelessConsentRequired);
  }

  @Override // android.content.p002pm.PackageManager
  public ApplicationInfo getApplicationInfo(String packageName, int flags)
      throws PackageManager.NameNotFoundException {
    return getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.m9of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ApplicationInfo getApplicationInfo(
      String packageName, PackageManager.ApplicationInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    return getApplicationInfoAsUser(packageName, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public ApplicationInfo getApplicationInfoAsUser(String packageName, int flags, int userId)
      throws PackageManager.NameNotFoundException {
    return getApplicationInfoAsUser(
        packageName, PackageManager.ApplicationInfoFlags.m9of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public ApplicationInfo getApplicationInfoAsUser(
      String packageName, PackageManager.ApplicationInfoFlags flags, int userId)
      throws PackageManager.NameNotFoundException {
    ApplicationInfo ai =
        getApplicationInfoAsUserCached(
            packageName, updateFlagsForApplication(flags.getValue(), userId), userId);
    if (ai == null) {
      throw new PackageManager.NameNotFoundException(packageName);
    }
    return maybeAdjustApplicationInfo(ai);
  }

  private static ApplicationInfo maybeAdjustApplicationInfo(ApplicationInfo info) {
    if (info.primaryCpuAbi != null && info.secondaryCpuAbi != null) {
      String runtimeIsa = VMRuntime.getRuntime().vmInstructionSet();
      String secondaryIsa = VMRuntime.getInstructionSet(info.secondaryCpuAbi);
      String secondaryDexCodeIsa = SystemProperties.get("ro.dalvik.vm.isa." + secondaryIsa);
      if (runtimeIsa.equals(secondaryDexCodeIsa.isEmpty() ? secondaryIsa : secondaryDexCodeIsa)) {
        ApplicationInfo modified = new ApplicationInfo(info);
        modified.nativeLibraryDir = info.secondaryNativeLibraryDir;
        return modified;
      }
    }
    return info;
  }

  @Override // android.content.p002pm.PackageManager
  public int getTargetSdkVersion(String packageName) throws PackageManager.NameNotFoundException {
    try {
      int version = this.mPM.getTargetSdkVersion(packageName);
      if (version != -1) {
        return version;
      }
      throw new PackageManager.NameNotFoundException(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ActivityInfo getActivityInfo(ComponentName className, int flags)
      throws PackageManager.NameNotFoundException {
    return getActivityInfo(className, PackageManager.ComponentInfoFlags.m10of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ActivityInfo getActivityInfo(
      ComponentName className, PackageManager.ComponentInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    int userId = getUserId();
    try {
      ActivityInfo ai =
          this.mPM.getActivityInfo(
              className, updateFlagsForComponent(flags.getValue(), userId, null), userId);
      if (ai != null) {
        return ai;
      }
      throw new PackageManager.NameNotFoundException(className.toString());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ActivityInfo getReceiverInfo(ComponentName className, int flags)
      throws PackageManager.NameNotFoundException {
    return getReceiverInfo(className, PackageManager.ComponentInfoFlags.m10of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ActivityInfo getReceiverInfo(
      ComponentName className, PackageManager.ComponentInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    int userId = getUserId();
    try {
      ActivityInfo ai =
          this.mPM.getReceiverInfo(
              className, updateFlagsForComponent(flags.getValue(), userId, null), userId);
      if (ai != null) {
        return ai;
      }
      throw new PackageManager.NameNotFoundException(className.toString());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ServiceInfo getServiceInfo(ComponentName className, int flags)
      throws PackageManager.NameNotFoundException {
    return getServiceInfo(className, PackageManager.ComponentInfoFlags.m10of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ServiceInfo getServiceInfo(
      ComponentName className, PackageManager.ComponentInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    int userId = getUserId();
    try {
      ServiceInfo si =
          this.mPM.getServiceInfo(
              className, updateFlagsForComponent(flags.getValue(), userId, null), userId);
      if (si != null) {
        return si;
      }
      throw new PackageManager.NameNotFoundException(className.toString());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ProviderInfo getProviderInfo(ComponentName className, int flags)
      throws PackageManager.NameNotFoundException {
    return getProviderInfo(className, PackageManager.ComponentInfoFlags.m10of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ProviderInfo getProviderInfo(
      ComponentName className, PackageManager.ComponentInfoFlags flags)
      throws PackageManager.NameNotFoundException {
    int userId = getUserId();
    try {
      ProviderInfo pi =
          this.mPM.getProviderInfo(
              className, updateFlagsForComponent(flags.getValue(), userId, null), userId);
      if (pi != null) {
        return pi;
      }
      throw new PackageManager.NameNotFoundException(className.toString());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] getSystemSharedLibraryNames() {
    try {
      return this.mPM.getSystemSharedLibraryNames();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<SharedLibraryInfo> getSharedLibraries(int flags) {
    return getSharedLibraries(PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<SharedLibraryInfo> getSharedLibraries(PackageManager.PackageInfoFlags flags) {
    return getSharedLibrariesAsUser(flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<SharedLibraryInfo> getSharedLibrariesAsUser(int flags, int userId) {
    return getSharedLibrariesAsUser(PackageManager.PackageInfoFlags.m11of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<SharedLibraryInfo> getSharedLibrariesAsUser(
      PackageManager.PackageInfoFlags flags, int userId) {
    try {
      ParceledListSlice<SharedLibraryInfo> sharedLibs =
          this.mPM.getSharedLibraries(this.mContext.getOpPackageName(), flags.getValue(), userId);
      if (sharedLibs == null) {
        return Collections.emptyList();
      }
      return sharedLibs.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<SharedLibraryInfo> getDeclaredSharedLibraries(String packageName, int flags) {
    return getDeclaredSharedLibraries(packageName, PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<SharedLibraryInfo> getDeclaredSharedLibraries(
      String packageName, PackageManager.PackageInfoFlags flags) {
    try {
      ParceledListSlice<SharedLibraryInfo> sharedLibraries =
          this.mPM.getDeclaredSharedLibraries(
              packageName, flags.getValue(), this.mContext.getUserId());
      return sharedLibraries != null ? sharedLibraries.getList() : Collections.emptyList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getServicesSystemSharedLibraryPackageName() {
    try {
      return this.mPM.getServicesSystemSharedLibraryPackageName();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getSharedSystemSharedLibraryPackageName() {
    try {
      return this.mPM.getSharedSystemSharedLibraryPackageName();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ChangedPackages getChangedPackages(int sequenceNumber) {
    try {
      return this.mPM.getChangedPackages(sequenceNumber, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public FeatureInfo[] getSystemAvailableFeatures() {
    try {
      ParceledListSlice<FeatureInfo> parceledList = this.mPM.getSystemAvailableFeatures();
      if (parceledList == null) {
        return new FeatureInfo[0];
      }
      List<FeatureInfo> list = parceledList.getList();
      FeatureInfo[] res = new FeatureInfo[list.size()];
      for (int i = 0; i < res.length; i++) {
        res[i] = list.get(i);
      }
      return res;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean hasSystemFeature(String name) {
    return hasSystemFeature(name, 0);
  }

  private static final class HasSystemFeatureQuery {
    public final String name;
    public final int version;

    public HasSystemFeatureQuery(String n, int v) {
      this.name = n;
      this.version = v;
    }

    public String toString() {
      return String.format(
          "HasSystemFeatureQuery(name=\"%s\", version=%d)",
          this.name, Integer.valueOf(this.version));
    }

    public boolean equals(Object o) {
      if (!(o instanceof HasSystemFeatureQuery)) {
        return false;
      }
      HasSystemFeatureQuery r = (HasSystemFeatureQuery) o;
      return Objects.equals(this.name, r.name) && this.version == r.version;
    }

    public int hashCode() {
      return (Objects.hashCode(this.name) * 13) + this.version;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean hasSystemFeature(String name, int version) {
    if (MaintenanceModeUtils.isMaintenanceModeFeature(name)) {
      return MaintenanceModeUtils.hasSystemFeature();
    }
    return mHasSystemFeatureCache.query(new HasSystemFeatureQuery(name, version)).booleanValue();
  }

  public void disableHasSystemFeatureCache() {
    mHasSystemFeatureCache.disableLocal();
  }

  public static void invalidateHasSystemFeatureCache() {
    mHasSystemFeatureCache.invalidateCache();
  }

  @Override // android.content.p002pm.PackageManager
  public int checkPermission(String permName, String pkgName) {
    return PermissionManager.checkPackageNamePermission(permName, pkgName, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPermissionRevokedByPolicy(String permName, String pkgName) {
    return getPermissionManager().isPermissionRevokedByPolicy(pkgName, permName);
  }

  @Override // android.content.p002pm.PackageManager
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

  @Override // android.content.p002pm.PackageManager
  public String getSdkSandboxPackageName() {
    try {
      return this.mPM.getSdkSandboxPackageName();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean addPermission(PermissionInfo info) {
    return getPermissionManager().addPermission(info, false);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean addPermissionAsync(PermissionInfo info) {
    return getPermissionManager().addPermission(info, true);
  }

  @Override // android.content.p002pm.PackageManager
  public void removePermission(String name) {
    getPermissionManager().removePermission(name);
  }

  @Override // android.content.p002pm.PackageManager
  public void grantRuntimePermission(String packageName, String permissionName, UserHandle user) {
    getPermissionManager().grantRuntimePermission(packageName, permissionName, user);
  }

  @Override // android.content.p002pm.PackageManager
  public void revokeRuntimePermission(String packageName, String permName, UserHandle user) {
    revokeRuntimePermission(packageName, permName, user, null);
  }

  @Override // android.content.p002pm.PackageManager
  public void revokeRuntimePermission(
      String packageName, String permName, UserHandle user, String reason) {
    getPermissionManager().revokeRuntimePermission(packageName, permName, user, reason);
  }

  @Override // android.content.p002pm.PackageManager
  public int getPermissionFlags(String permName, String packageName, UserHandle user) {
    return getPermissionManager().getPermissionFlags(packageName, permName, user);
  }

  @Override // android.content.p002pm.PackageManager
  public void updatePermissionFlags(
      String permName, String packageName, int flagMask, int flagValues, UserHandle user) {
    getPermissionManager().updatePermissionFlags(packageName, permName, flagMask, flagValues, user);
  }

  @Override // android.content.p002pm.PackageManager
  public Set<String> getWhitelistedRestrictedPermissions(String packageName, int flags) {
    return getPermissionManager().getAllowlistedRestrictedPermissions(packageName, flags);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean addWhitelistedRestrictedPermission(
      String packageName, String permName, int flags) {
    return getPermissionManager().addAllowlistedRestrictedPermission(packageName, permName, flags);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean setAutoRevokeWhitelisted(String packageName, boolean whitelisted) {
    return getPermissionManager().setAutoRevokeExempted(packageName, whitelisted);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isAutoRevokeWhitelisted(String packageName) {
    return getPermissionManager().isAutoRevokeExempted(packageName);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean removeWhitelistedRestrictedPermission(
      String packageName, String permName, int flags) {
    return getPermissionManager()
        .removeAllowlistedRestrictedPermission(packageName, permName, flags);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean shouldShowRequestPermissionRationale(String permName) {
    return getPermissionManager().shouldShowRequestPermissionRationale(permName);
  }

  @Override // android.content.p002pm.PackageManager
  public CharSequence getBackgroundPermissionOptionLabel() {
    try {
      String permissionController = getPermissionControllerPackageName();
      Context context = this.mContext.createPackageContext(permissionController, 0);
      int textId =
          context
              .getResources()
              .getIdentifier(
                  APP_PERMISSION_BUTTON_ALLOW_ALWAYS,
                  "string",
                  PERMISSION_CONTROLLER_RESOURCE_PACKAGE);
      if (textId != 0) {
        return context.getText(textId);
      }
      return "";
    } catch (PackageManager.NameNotFoundException e) {
      Log.m97e(TAG, "Permission controller not found.", e);
      return "";
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int checkSignatures(String pkg1, String pkg2) {
    try {
      return this.mPM.checkSignatures(pkg1, pkg2, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int checkSignatures(int uid1, int uid2) {
    try {
      return this.mPM.checkUidSignatures(uid1, uid2);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean hasSigningCertificate(String packageName, byte[] certificate, int type) {
    try {
      return this.mPM.hasSigningCertificate(packageName, certificate, type);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean hasSigningCertificate(int uid, byte[] certificate, int type) {
    try {
      return this.mPM.hasUidSigningCertificate(uid, certificate, type);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  private static List<byte[]> encodeCertificates(List<Certificate> certs)
      throws CertificateEncodingException {
    if (certs == null) {
      return null;
    }
    List<byte[]> result = new ArrayList<>(certs.size());
    for (Certificate cert : certs) {
      if (!(cert instanceof X509Certificate)) {
        throw new CertificateEncodingException("Only X509 certificates supported.");
      }
      result.add(cert.getEncoded());
    }
    return result;
  }

  @Override // android.content.p002pm.PackageManager
  public void requestChecksums(
      String packageName,
      boolean includeSplits,
      int required,
      List<Certificate> trustedInstallers,
      final PackageManager.OnChecksumsReadyListener onChecksumsReadyListener)
      throws CertificateEncodingException, PackageManager.NameNotFoundException {
    Objects.requireNonNull(packageName);
    Objects.requireNonNull(onChecksumsReadyListener);
    Objects.requireNonNull(trustedInstallers);
    if (trustedInstallers == TRUST_ALL) {
      trustedInstallers = null;
    } else if (trustedInstallers == TRUST_NONE) {
      trustedInstallers = Collections.emptyList();
    } else if (trustedInstallers.isEmpty()) {
      throw new IllegalArgumentException(
          "trustedInstallers has to be one of TRUST_ALL/TRUST_NONE or a non-empty list of"
              + " certificates.");
    }
    try {
      IOnChecksumsReadyListener onChecksumsReadyListenerDelegate =
          new IOnChecksumsReadyListener
              .Stub() { // from class: android.app.ApplicationPackageManager.2
            @Override // android.content.p002pm.IOnChecksumsReadyListener
            public void onChecksumsReady(List<ApkChecksum> checksums) throws RemoteException {
              onChecksumsReadyListener.onChecksumsReady(checksums);
            }
          };
      this.mPM.requestPackageChecksums(
          packageName,
          includeSplits,
          127,
          required,
          encodeCertificates(trustedInstallers),
          onChecksumsReadyListenerDelegate,
          getUserId());
    } catch (ParcelableException e) {
      e.maybeRethrow(PackageManager.NameNotFoundException.class);
      throw new RuntimeException(e);
    } catch (RemoteException e2) {
      throw e2.rethrowFromSystemServer();
    }
  }

  private static class GetPackagesForUidResult {
    private final String[] mValue;

    GetPackagesForUidResult(String[] s) {
      this.mValue = s;
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

    public boolean equals(Object o) {
      if (!(o instanceof GetPackagesForUidResult)) {
        return false;
      }
      String[] r = ((GetPackagesForUidResult) o).mValue;
      String[] l = this.mValue;
      if ((r == null) != (l == null)) {
        return false;
      }
      if (r == null) {
        return true;
      }
      Arrays.sort(r);
      Arrays.sort(l);
      return Arrays.equals(l, r);
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] getPackagesForUid(int uid) {
    return mGetPackagesForUidCache.query(Integer.valueOf(uid)).value();
  }

  public static void disableGetPackagesForUidCache() {
    mGetPackagesForUidCache.disableLocal();
  }

  public static void invalidateGetPackagesForUidCache() {
    PropertyInvalidatedCache.invalidateCache(CACHE_KEY_PACKAGES_FOR_UID_PROPERTY);
  }

  @Override // android.content.p002pm.PackageManager
  public String getNameForUid(int uid) {
    try {
      return this.mPM.getNameForUid(uid);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] getNamesForUids(int[] uids) {
    try {
      return this.mPM.getNamesForUids(uids);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getUidForSharedUser(String sharedUserName)
      throws PackageManager.NameNotFoundException {
    try {
      int uid = this.mPM.getUidForSharedUser(sharedUserName);
      if (uid != -1) {
        return uid;
      }
      throw new PackageManager.NameNotFoundException("No shared userid for user:" + sharedUserName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ModuleInfo> getInstalledModules(int flags) {
    try {
      return this.mPM.getInstalledModules(flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ModuleInfo getModuleInfo(String packageName, int flags)
      throws PackageManager.NameNotFoundException {
    try {
      ModuleInfo mi = this.mPM.getModuleInfo(packageName, flags);
      if (mi != null) {
        return mi;
      }
      throw new PackageManager.NameNotFoundException("No module info for package: " + packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getInstalledPackages(int flags) {
    return getInstalledPackages(PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getInstalledPackages(PackageManager.PackageInfoFlags flags) {
    return getInstalledPackagesAsUser(flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getInstalledPackagesAsUser(int flags, int userId) {
    return getInstalledPackagesAsUser(PackageManager.PackageInfoFlags.m11of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getInstalledPackagesAsUser(
      PackageManager.PackageInfoFlags flags, int userId) {
    try {
      ParceledListSlice<PackageInfo> parceledList =
          this.mPM.getInstalledPackages(updateFlagsForPackage(flags.getValue(), userId), userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public PersistableBundle getAppMetadata(String packageName)
      throws PackageManager.NameNotFoundException {
    PersistableBundle appMetadata = null;
    try {
      ParcelFileDescriptor pfd = this.mPM.getAppMetadataFd(packageName, getUserId());
      if (pfd != null) {
        try {
          InputStream inputStream = new ParcelFileDescriptor.AutoCloseInputStream(pfd);
          try {
            appMetadata = PersistableBundle.readFromStream(inputStream);
            inputStream.close();
          } finally {
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return appMetadata != null ? appMetadata : new PersistableBundle();
    } catch (ParcelableException e2) {
      e2.maybeRethrow(PackageManager.NameNotFoundException.class);
      throw new RuntimeException(e2);
    } catch (RemoteException e3) {
      throw e3.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
    return getPackagesHoldingPermissions(permissions, PackageManager.PackageInfoFlags.m11of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getPackagesHoldingPermissions(
      String[] permissions, PackageManager.PackageInfoFlags flags) {
    int userId = getUserId();
    try {
      ParceledListSlice<PackageInfo> parceledList =
          this.mPM.getPackagesHoldingPermissions(
              permissions, updateFlagsForPackage(flags.getValue(), userId), userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ApplicationInfo> getInstalledApplications(int flags) {
    return getInstalledApplicationsAsUser(flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<ApplicationInfo> getInstalledApplications(PackageManager.ApplicationInfoFlags flags) {
    return getInstalledApplicationsAsUser(flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<ApplicationInfo> getInstalledApplicationsAsUser(int flags, int userId) {
    return getInstalledApplicationsAsUser(PackageManager.ApplicationInfoFlags.m9of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ApplicationInfo> getInstalledApplicationsAsUser(
      PackageManager.ApplicationInfoFlags flags, int userId) {
    try {
      ParceledListSlice<ApplicationInfo> parceledList =
          this.mPM.getInstalledApplications(
              updateFlagsForApplication(flags.getValue(), userId), userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<InstantAppInfo> getInstantApps() {
    try {
      ParceledListSlice<InstantAppInfo> slice = this.mPM.getInstantApps(getUserId());
      if (slice != null) {
        return slice.getList();
      }
      return Collections.emptyList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getInstantAppIcon(String packageName) {
    try {
      Bitmap bitmap = this.mPM.getInstantAppIcon(packageName, getUserId());
      if (bitmap == null) {
        return null;
      }
      return new BitmapDrawable((Resources) null, bitmap);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isInstantApp() {
    return isInstantApp(this.mContext.getPackageName());
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isInstantApp(String packageName) {
    try {
      return this.mPM.isInstantApp(packageName, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getInstantAppCookieMaxBytes() {
    return Settings.Global.getInt(
        this.mContext.getContentResolver(), Settings.Global.EPHEMERAL_COOKIE_MAX_SIZE_BYTES, 16384);
  }

  @Override // android.content.p002pm.PackageManager
  public int getInstantAppCookieMaxSize() {
    return getInstantAppCookieMaxBytes();
  }

  @Override // android.content.p002pm.PackageManager
  public byte[] getInstantAppCookie() {
    try {
      byte[] cookie = this.mPM.getInstantAppCookie(this.mContext.getPackageName(), getUserId());
      if (cookie != null) {
        return cookie;
      }
      return EmptyArray.BYTE;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void clearInstantAppCookie() {
    updateInstantAppCookie(null);
  }

  @Override // android.content.p002pm.PackageManager
  public void updateInstantAppCookie(byte[] cookie) {
    if (cookie != null && cookie.length > getInstantAppCookieMaxBytes()) {
      throw new IllegalArgumentException(
          "instant cookie longer than " + getInstantAppCookieMaxBytes());
    }
    try {
      this.mPM.setInstantAppCookie(this.mContext.getPackageName(), cookie, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean setInstantAppCookie(byte[] cookie) {
    try {
      return this.mPM.setInstantAppCookie(this.mContext.getPackageName(), cookie, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveActivity(Intent intent, int flags) {
    return resolveActivity(intent, PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveActivity(Intent intent, PackageManager.ResolveInfoFlags flags) {
    return resolveActivityAsUser(intent, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveActivityAsUser(Intent intent, int flags, int userId) {
    return resolveActivityAsUser(intent, PackageManager.ResolveInfoFlags.m12of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveActivityAsUser(
      Intent intent, PackageManager.ResolveInfoFlags flags, int userId) {
    try {
      return this.mPM.resolveIntent(
          intent,
          intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
          updateFlagsForComponent(flags.getValue(), userId, intent),
          userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
    return queryIntentActivities(intent, PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentActivities(
      Intent intent, PackageManager.ResolveInfoFlags flags) {
    return queryIntentActivitiesAsUser(intent, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int flags, int userId) {
    return queryIntentActivitiesAsUser(
        intent, PackageManager.ResolveInfoFlags.m12of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentActivitiesAsUser(
      Intent intent, PackageManager.ResolveInfoFlags flags, int userId) {
    try {
      ParceledListSlice<ResolveInfo> parceledList =
          this.mPM.queryIntentActivities(
              intent,
              intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
              updateFlagsForComponent(flags.getValue(), userId, intent),
              userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentActivityOptions(
      ComponentName caller, Intent[] specifics, Intent intent, int flags) {
    return queryIntentActivityOptions(
        caller,
        specifics == null ? null : new ArrayList(Arrays.asList(specifics)),
        intent,
        PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentActivityOptions(
      ComponentName caller,
      List<Intent> specifics,
      Intent intent,
      PackageManager.ResolveInfoFlags flags) {
    String[] specificTypes;
    String t;
    int userId = getUserId();
    ContentResolver resolver = this.mContext.getContentResolver();
    String[] specificTypes2 = null;
    if (specifics == null) {
      specificTypes = null;
    } else {
      int numSpecifics = specifics.size();
      for (int i = 0; i < numSpecifics; i++) {
        Intent sp = specifics.get(i);
        if (sp != null && (t = sp.resolveTypeIfNeeded(resolver)) != null) {
          if (specificTypes2 == null) {
            specificTypes2 = new String[numSpecifics];
          }
          specificTypes2[i] = t;
        }
      }
      specificTypes = specificTypes2;
    }
    try {
      ParceledListSlice<ResolveInfo> parceledList =
          this.mPM.queryIntentActivityOptions(
              caller,
              specifics == null ? null : (Intent[]) specifics.toArray(new Intent[0]),
              specificTypes,
              intent,
              intent.resolveTypeIfNeeded(resolver),
              updateFlagsForComponent(flags.getValue(), userId, intent),
              userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent intent, int flags, int userId) {
    return queryBroadcastReceiversAsUser(
        intent, PackageManager.ResolveInfoFlags.m12of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryBroadcastReceiversAsUser(
      Intent intent, PackageManager.ResolveInfoFlags flags, int userId) {
    try {
      ParceledListSlice<ResolveInfo> parceledList =
          this.mPM.queryIntentReceivers(
              intent,
              intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
              updateFlagsForComponent(flags.getValue(), userId, intent),
              userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
    return queryBroadcastReceivers(intent, PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryBroadcastReceivers(
      Intent intent, PackageManager.ResolveInfoFlags flags) {
    return queryBroadcastReceiversAsUser(intent, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveServiceAsUser(Intent intent, int flags, int userId) {
    return resolveServiceAsUser(intent, PackageManager.ResolveInfoFlags.m12of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveServiceAsUser(
      Intent intent, PackageManager.ResolveInfoFlags flags, int userId) {
    try {
      ResolveInfo rInfo =
          this.mPM.resolveService(
              intent,
              intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
              updateFlagsForComponent(flags.getValue(), userId, intent),
              userId);
      if (rInfo == null && SemDualAppManager.isDualAppId(userId)) {
        return this.mPM.resolveService(
            intent,
            intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
            updateFlagsForComponent(flags.getValue(), 0, intent),
            0);
      }
      return rInfo;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveService(Intent intent, int flags) {
    return resolveService(intent, PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ResolveInfo resolveService(Intent intent, PackageManager.ResolveInfoFlags flags) {
    return resolveServiceAsUser(intent, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int flags, int userId) {
    return queryIntentServicesAsUser(intent, PackageManager.ResolveInfoFlags.m12of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentServicesAsUser(
      Intent intent, PackageManager.ResolveInfoFlags flags, int userId) {
    try {
      ParceledListSlice<ResolveInfo> parceledList =
          this.mPM.queryIntentServices(
              intent,
              intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
              updateFlagsForComponent(flags.getValue(), userId, intent),
              userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
    return queryIntentServices(intent, PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentServices(
      Intent intent, PackageManager.ResolveInfoFlags flags) {
    return queryIntentServicesAsUser(intent, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int flags, int userId) {
    return queryIntentContentProvidersAsUser(
        intent, PackageManager.ResolveInfoFlags.m12of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentContentProvidersAsUser(
      Intent intent, PackageManager.ResolveInfoFlags flags, int userId) {
    try {
      ParceledListSlice<ResolveInfo> parceledList =
          this.mPM.queryIntentContentProviders(
              intent,
              intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
              updateFlagsForComponent(flags.getValue(), userId, intent),
              userId);
      if (SemDualAppManager.isDualAppId(userId)
          && (parceledList == null || parceledList.getList().size() == 0)) {
        parceledList =
            this.mPM.queryIntentContentProviders(
                intent,
                intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                flags.getValue(),
                0);
      }
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
    return queryIntentContentProviders(intent, PackageManager.ResolveInfoFlags.m12of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<ResolveInfo> queryIntentContentProviders(
      Intent intent, PackageManager.ResolveInfoFlags flags) {
    return queryIntentContentProvidersAsUser(intent, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public ProviderInfo resolveContentProvider(String name, int flags) {
    return resolveContentProvider(name, PackageManager.ComponentInfoFlags.m10of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public ProviderInfo resolveContentProvider(String name, PackageManager.ComponentInfoFlags flags) {
    return resolveContentProviderAsUser(name, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public ProviderInfo resolveContentProviderAsUser(String name, int flags, int userId) {
    return resolveContentProviderAsUser(
        name, PackageManager.ComponentInfoFlags.m10of(flags), userId);
  }

  @Override // android.content.p002pm.PackageManager
  public ProviderInfo resolveContentProviderAsUser(
      String name, PackageManager.ComponentInfoFlags flags, int userId) {
    try {
      return this.mPM.resolveContentProvider(
          name, updateFlagsForComponent(flags.getValue(), userId, null), userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
    return queryContentProviders(processName, uid, PackageManager.ComponentInfoFlags.m10of(flags));
  }

  @Override // android.content.p002pm.PackageManager
  public List<ProviderInfo> queryContentProviders(
      String processName, int uid, PackageManager.ComponentInfoFlags flags) {
    return queryContentProviders(processName, uid, flags, (String) null);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ProviderInfo> queryContentProviders(
      String processName, int uid, int flags, String metaDataKey) {
    return queryContentProviders(
        processName, uid, PackageManager.ComponentInfoFlags.m10of(flags), metaDataKey);
  }

  @Override // android.content.p002pm.PackageManager
  public List<ProviderInfo> queryContentProviders(
      String processName, int uid, PackageManager.ComponentInfoFlags flags, String metaDataKey) {
    try {
      ParceledListSlice<ProviderInfo> slice =
          this.mPM.queryContentProviders(
              processName,
              uid,
              updateFlagsForComponent(flags.getValue(), UserHandle.getUserId(uid), null),
              metaDataKey);
      return slice != null ? slice.getList() : Collections.emptyList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags)
      throws PackageManager.NameNotFoundException {
    try {
      InstrumentationInfo ii = this.mPM.getInstrumentationInfoAsUser(className, flags, getUserId());
      if (ii != null) {
        return ii;
      }
      throw new PackageManager.NameNotFoundException(className.toString());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
    try {
      ParceledListSlice<InstrumentationInfo> parceledList =
          this.mPM.queryInstrumentationAsUser(targetPackage, flags, getUserId());
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getDrawable(String packageName, int resId, ApplicationInfo appInfo) {
    boolean check = SystemProperties.getBoolean("sys.knox.app_icon_change", false);
    if (check && appInfo != null && resId == appInfo.icon) {
      try {
        ApplicationPolicy appPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
        byte[] imageData =
            appPolicy.getApplicationIconFromDb(packageName, UserHandle.getUserId(appInfo.uid));
        if (imageData != null) {
          ByteArrayInputStream is = new ByteArrayInputStream(imageData);
          TypedValue typedValue = new TypedValue();
          typedValue.density = this.mContext.getResources().getDisplayMetrics().densityDpi;
          BitmapFactory.Options opts = new BitmapFactory.Options();
          opts.inTargetDensity = this.mContext.getResources().getDisplayMetrics().densityDpi;
          Drawable drw =
              Drawable.createFromResourceStream(
                  this.mContext.getResources(), typedValue, is, null, opts);
          Log.m98i(TAG, "EDM:ApplicationIcon got from EDM database ");
          return drw;
        }
      } catch (Exception e) {
        Log.m102w(TAG, "EDM: Get Icon EX: " + e);
      }
    }
    ResourceName name = new ResourceName(packageName, resId);
    Drawable cachedIcon = getCachedIcon(name);
    if (cachedIcon != null) {
      return cachedIcon;
    }
    if (appInfo == null) {
      try {
        appInfo = getApplicationInfo(packageName, 1024);
      } catch (PackageManager.NameNotFoundException e2) {
        return null;
      }
    }
    if (resId != 0) {
      try {
        Resources r = getResourcesForApplication(appInfo);
        r.mPackageName = appInfo.packageName;
        r.mAppIconResId = appInfo.icon;
        r.mUserId = UserHandle.getUserId(appInfo.uid);
        Drawable dr = r.getDrawable(resId, null);
        if (dr != null) {
          putCachedIcon(name, dr);
        }
        return dr;
      } catch (PackageManager.NameNotFoundException e3) {
        Log.m102w("PackageManager", "Failure retrieving resources for " + appInfo.packageName);
      } catch (Resources.NotFoundException e4) {
        Log.m102w(
            "PackageManager",
            "Failure retrieving resources for " + appInfo.packageName + ": " + e4.getMessage());
      } catch (Exception e5) {
        Log.m103w(
            "PackageManager",
            "Failure retrieving icon 0x"
                + Integer.toHexString(resId)
                + " in package "
                + packageName,
            e5);
      }
    }
    return null;
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getActivityIcon(ComponentName activityName)
      throws PackageManager.NameNotFoundException {
    return getActivityInfo(activityName, 1024).loadIcon(this);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getActivityIcon(Intent intent) throws PackageManager.NameNotFoundException {
    if (intent.getComponent() != null) {
      return getActivityIcon(intent.getComponent());
    }
    ResolveInfo info = resolveActivity(intent, 65536);
    if (info != null) {
      return info.activityInfo.loadIcon(this);
    }
    throw new PackageManager.NameNotFoundException(intent.toUri(0));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getDefaultActivityIcon() {
    return this.mContext.getDrawable(17301651);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getApplicationIcon(ApplicationInfo info) {
    return info.loadIcon(this);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getApplicationIcon(String packageName)
      throws PackageManager.NameNotFoundException {
    return getApplicationIcon(getApplicationInfo(packageName, 1024));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getActivityBanner(ComponentName activityName)
      throws PackageManager.NameNotFoundException {
    return getActivityInfo(activityName, 1024).loadBanner(this);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getActivityBanner(Intent intent) throws PackageManager.NameNotFoundException {
    if (intent.getComponent() != null) {
      return getActivityBanner(intent.getComponent());
    }
    ResolveInfo info = resolveActivity(intent, 65536);
    if (info != null) {
      return info.activityInfo.loadBanner(this);
    }
    throw new PackageManager.NameNotFoundException(intent.toUri(0));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getApplicationBanner(ApplicationInfo info) {
    return info.loadBanner(this);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getApplicationBanner(String packageName)
      throws PackageManager.NameNotFoundException {
    return getApplicationBanner(getApplicationInfo(packageName, 1024));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getActivityLogo(ComponentName activityName)
      throws PackageManager.NameNotFoundException {
    return getActivityInfo(activityName, 1024).loadLogo(this);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getActivityLogo(Intent intent) throws PackageManager.NameNotFoundException {
    if (intent.getComponent() != null) {
      return getActivityLogo(intent.getComponent());
    }
    ResolveInfo info = resolveActivity(intent, 65536);
    if (info != null) {
      return info.activityInfo.loadLogo(this);
    }
    throw new PackageManager.NameNotFoundException(intent.toUri(0));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getApplicationLogo(ApplicationInfo info) {
    return info.loadLogo(this);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getApplicationLogo(String packageName)
      throws PackageManager.NameNotFoundException {
    return getApplicationLogo(getApplicationInfo(packageName, 1024));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getUserBadgedIcon(Drawable icon, final UserHandle user) {
    if (!hasUserBadge(user.getIdentifier())) {
      return icon;
    }
    int userId = user.getIdentifier();
    if (SemPersonaManager.isSecureFolderId(userId)
        || SemDualAppManager.isDualAppId(userId)
        || SemPersonaManager.isAppSeparationUserId(userId)) {
      Drawable badge = getDrawable("system", getBadgeResIdForUser(userId), null);
      return getBadgedDrawable(icon, badge, null, true);
    }
    Drawable badgeForeground =
        getDevicePolicyManager()
            .getResources()
            .getDrawable(
                getUpdatableUserIconBadgeId(user),
                DevicePolicyResources.Drawables.Style.SOLID_COLORED,
                new Supplier() { // from class:
                                 // android.app.ApplicationPackageManager$$ExternalSyntheticLambda1
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    Drawable lambda$getUserBadgedIcon$0;
                    lambda$getUserBadgedIcon$0 =
                        ApplicationPackageManager.this.lambda$getUserBadgedIcon$0(user);
                    return lambda$getUserBadgedIcon$0;
                  }
                });
    Drawable badge2 =
        new LauncherIcons(this.mContext)
            .getBadgeDrawable(badgeForeground, getUserBadgeColor(user, false));
    return getBadgedDrawable(icon, badge2, null, true);
  }

  private String getUpdatableUserIconBadgeId(UserHandle user) {
    return getUserManager().isManagedProfile(user.getIdentifier())
        ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE
        : DevicePolicyResources.UNDEFINED;
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* renamed from: getDefaultUserIconBadge, reason: merged with bridge method [inline-methods] */
  public Drawable lambda$getUserBadgedIcon$0(UserHandle user) {
    return this.mContext.getDrawable(getUserManager().getUserIconBadgeResId(user.getIdentifier()));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getUserBadgedDrawableForDensity(
      Drawable drawable, UserHandle user, Rect badgeLocation, int badgeDensity) {
    Drawable badgeDrawable = getUserBadgeForDensity(user, badgeDensity);
    if (badgeDrawable == null) {
      return drawable;
    }
    return getBadgedDrawable(drawable, badgeDrawable, badgeLocation, true);
  }

  private int getUserBadgeColor(UserHandle user, boolean checkTheme) {
    if (checkTheme && this.mContext.getResources().getConfiguration().isNightModeActive()) {
      return getUserManager().getUserBadgeDarkColor(user.getIdentifier());
    }
    return getUserManager().getUserBadgeColor(user.getIdentifier());
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getUserBadgeForDensity(final UserHandle user, final int density) {
    int densityLocal = density;
    if (SemPersonaManager.isKnoxId(user.getIdentifier())) {
      if (densityLocal <= 0) {
        densityLocal = this.mContext.getResources().getDisplayMetrics().densityDpi;
      }
      Pair<Boolean, Drawable> pair =
          SemPersonaManager.getCustomBadgeForCustomContainer(user, densityLocal, this.mContext);
      if (pair.first.booleanValue()) {
        return pair.second;
      }
    } else if (SemDualAppManager.isDualAppId(user.getIdentifier())) {
      if (densityLocal <= 0) {
        densityLocal = this.mContext.getResources().getDisplayMetrics().densityDpi;
      }
      return Resources.getSystem()
          .getDrawableForDensity(C4337R.drawable.ic_dualapp_corner, densityLocal);
    }
    Drawable badgeColor =
        getProfileIconForDensity(user, C4337R.drawable.ic_corp_badge_color, density);
    if (badgeColor == null) {
      return null;
    }
    Drawable badgeForeground =
        getDevicePolicyManager()
            .getResources()
            .getDrawableForDensity(
                getUpdatableUserBadgeId(user),
                DevicePolicyResources.Drawables.Style.SOLID_COLORED,
                density,
                new Supplier() { // from class:
                                 // android.app.ApplicationPackageManager$$ExternalSyntheticLambda2
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    Drawable lambda$getUserBadgeForDensity$1;
                    lambda$getUserBadgeForDensity$1 =
                        ApplicationPackageManager.this.lambda$getUserBadgeForDensity$1(
                            user, density);
                    return lambda$getUserBadgeForDensity$1;
                  }
                });
    badgeForeground.setTint(getUserBadgeColor(user, false));
    Drawable badge = new LayerDrawable(new Drawable[] {badgeColor, badgeForeground});
    return badge;
  }

  private String getUpdatableUserBadgeId(UserHandle user) {
    return getUserManager().isManagedProfile(user.getIdentifier())
        ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON
        : DevicePolicyResources.UNDEFINED;
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* renamed from: getDefaultUserBadgeForDensity, reason: merged with bridge method [inline-methods] */
  public Drawable lambda$getUserBadgeForDensity$1(UserHandle user, int density) {
    return getDrawableForDensity(getUserManager().getUserBadgeResId(user.getIdentifier()), density);
  }

  private UserInfo getUserIfProfile(int userHandle) {
    List<UserInfo> userProfiles = getUserManager().getProfiles(UserHandle.myUserId());
    for (UserInfo user : userProfiles) {
      if (user.f54id == userHandle) {
        return user;
      }
    }
    return null;
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getUserBadgeForDensityNoBackground(final UserHandle user, final int density) {
    if (!hasUserBadge(user.getIdentifier())) {
      return null;
    }
    Drawable badge =
        getDevicePolicyManager()
            .getResources()
            .getDrawableForDensity(
                getUpdatableUserBadgeId(user),
                DevicePolicyResources.Drawables.Style.SOLID_NOT_COLORED,
                density,
                new Supplier() { // from class:
                                 // android.app.ApplicationPackageManager$$ExternalSyntheticLambda0
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    Drawable lambda$getUserBadgeForDensityNoBackground$2;
                    lambda$getUserBadgeForDensityNoBackground$2 =
                        ApplicationPackageManager.this.lambda$getUserBadgeForDensityNoBackground$2(
                            user, density);
                    return lambda$getUserBadgeForDensityNoBackground$2;
                  }
                });
    if (badge != null) {
      if (SemPersonaManager.isKnoxId(user.getIdentifier())) {
        Pair<Boolean, Drawable> pair =
            SemPersonaManager.getNotificationBadge(user, density, this.mContext);
        if (pair.first.booleanValue()) {
          return pair.second;
        }
      }
      badge.setTint(getUserBadgeColor(user, true));
    }
    return badge;
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* renamed from: getDefaultUserBadgeNoBackgroundForDensity, reason: merged with bridge method [inline-methods] */
  public Drawable lambda$getUserBadgeForDensityNoBackground$2(UserHandle user, int density) {
    return getDrawableForDensity(
        getUserManager().getUserBadgeNoBackgroundResId(user.getIdentifier()), density);
  }

  private Drawable getDrawableForDensity(int drawableId, int density) {
    if (density <= 0) {
      density = this.mContext.getResources().getDisplayMetrics().densityDpi;
    }
    return this.mContext.getResources().getDrawableForDensity(drawableId, density);
  }

  private Drawable getProfileIconForDensity(UserHandle user, int drawableId, int density) {
    if (hasUserBadge(user.getIdentifier())) {
      return getDrawableForDensity(drawableId, density);
    }
    return null;
  }

  @Override // android.content.p002pm.PackageManager
  public CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
    if (isManagedProfile(user.getIdentifier())
        && SemPersonaManager.isSecureFolderId(user.getIdentifier())) {
      return label;
    }
    if (SemDualAppManager.isDualAppId(user.getIdentifier())) {
      return label;
    }
    return getUserManager().getBadgedLabelForUser(label, user);
  }

  @Override // android.content.p002pm.PackageManager
  public Resources getResourcesForActivity(ComponentName activityName)
      throws PackageManager.NameNotFoundException {
    return getResourcesForApplication(getActivityInfo(activityName, 1024).applicationInfo);
  }

  @Override // android.content.p002pm.PackageManager
  public Resources getResourcesForApplication(ApplicationInfo app)
      throws PackageManager.NameNotFoundException {
    return getResourcesForApplication(app, null);
  }

  @Override // android.content.p002pm.PackageManager
  public Resources getResourcesForApplication(ApplicationInfo app, Configuration configuration)
      throws PackageManager.NameNotFoundException {
    if (app.packageName.equals("system")) {
      Context sysuiContext = this.mContext.mMainThread.getSystemUiContext();
      if (configuration != null) {
        sysuiContext = sysuiContext.createConfigurationContext(configuration);
      }
      return sysuiContext.getResources();
    }
    boolean sameUid = app.uid == Process.myUid();
    Resources r =
        this.mContext.mMainThread.getTopLevelResources(
            sameUid ? app.sourceDir : app.publicSourceDir,
            sameUid ? app.splitSourceDirs : app.splitPublicSourceDirs,
            app.resourceDirs,
            app.overlayPaths,
            app.sharedLibraryFiles,
            this.mContext.mPackageInfo,
            configuration);
    if (r != null) {
      r.mPackageName = app.packageName;
      r.mAppIconResId = app.icon;
      r.mUserId = UserHandle.getUserId(app.uid);
      return r;
    }
    throw new PackageManager.NameNotFoundException("Unable to open " + app.publicSourceDir);
  }

  @Override // android.content.p002pm.PackageManager
  public Resources getResourcesForApplication(String appPackageName)
      throws PackageManager.NameNotFoundException {
    return getResourcesForApplication(getApplicationInfo(appPackageName, 1024));
  }

  @Override // android.content.p002pm.PackageManager
  public Resources getResourcesForApplicationAsUser(String appPackageName, int userId)
      throws PackageManager.NameNotFoundException {
    if (userId < 0) {
      throw new IllegalArgumentException("Call does not support special user #" + userId);
    }
    if ("system".equals(appPackageName)) {
      return this.mContext.mMainThread.getSystemUiContext().getResources();
    }
    try {
      ApplicationInfo ai = this.mPM.getApplicationInfo(appPackageName, 1024L, userId);
      if (ai != null) {
        return getResourcesForApplication(ai);
      }
      throw new PackageManager.NameNotFoundException(
          "Package " + appPackageName + " doesn't exist");
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
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

  @Override // android.content.p002pm.PackageManager
  public void addOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener listener) {
    getPermissionManager().addOnPermissionsChangeListener(listener);
  }

  @Override // android.content.p002pm.PackageManager
  public void removeOnPermissionsChangeListener(
      PackageManager.OnPermissionsChangedListener listener) {
    getPermissionManager().removeOnPermissionsChangeListener(listener);
  }

  static void configurationChanged() {
    synchronized (sSync) {
      sIconCache.clear();
      sStringCache.clear();
    }
  }

  protected ApplicationPackageManager(ContextImpl context, IPackageManager pm) {
    this.mContext = context;
    this.mPM = pm;
  }

  private long updateFlagsForPackage(long flags, int userId) {
    if ((15 & flags) != 0 && (269221888 & flags) == 0) {
      onImplicitDirectBoot(userId);
    }
    return flags;
  }

  private long updateFlagsForApplication(long flags, int userId) {
    return updateFlagsForPackage(flags, userId);
  }

  private long updateFlagsForComponent(long flags, int userId, Intent intent) {
    if (intent != null && (intent.getFlags() & 256) != 0) {
      flags |= 268435456;
    }
    if ((269221888 & flags) == 0) {
      onImplicitDirectBoot(userId);
    }
    return flags;
  }

  private void onImplicitDirectBoot(int userId) {
    if (StrictMode.vmImplicitDirectBootEnabled()) {
      if (userId == UserHandle.myUserId()) {
        if (this.mUserUnlocked) {
          return;
        }
        if (((UserManager) this.mContext.getSystemService(UserManager.class))
            .isUserUnlockingOrUnlocked(userId)) {
          this.mUserUnlocked = true;
          return;
        } else {
          StrictMode.onImplicitDirectBoot();
          return;
        }
      }
      if (!((UserManager) this.mContext.getSystemService(UserManager.class))
          .isUserUnlockingOrUnlocked(userId)) {
        StrictMode.onImplicitDirectBoot();
      }
    }
  }

  private Drawable getCachedIcon(ResourceName name) {
    synchronized (sSync) {
      WeakReference<Drawable.ConstantState> wr = sIconCache.get(name);
      if (wr != null) {
        Drawable.ConstantState state = wr.get();
        if (state != null) {
          return state.newDrawable();
        }
        sIconCache.remove(name);
      }
      return null;
    }
  }

  private void putCachedIcon(ResourceName name, Drawable dr) {
    synchronized (sSync) {
      sIconCache.put(name, new WeakReference<>(dr.getConstantState()));
    }
  }

  static void handlePackageBroadcast(int cmd, String[] pkgList, boolean hasPkgInfo) {
    boolean immediateGc = false;
    if (cmd == 1) {
      immediateGc = true;
    }
    if (pkgList != null && pkgList.length > 0) {
      boolean needCleanup = false;
      for (String ssp : pkgList) {
        synchronized (sSync) {
          for (int i = sIconCache.size() - 1; i >= 0; i--) {
            ResourceName nm = sIconCache.keyAt(i);
            if (nm.packageName.equals(ssp)) {
              sIconCache.removeAt(i);
              needCleanup = true;
            }
          }
          for (int i2 = sStringCache.size() - 1; i2 >= 0; i2--) {
            ResourceName nm2 = sStringCache.keyAt(i2);
            if (nm2.packageName.equals(ssp)) {
              sStringCache.removeAt(i2);
              needCleanup = true;
            }
          }
        }
      }
      if (needCleanup || hasPkgInfo) {
        if (immediateGc) {
          Runtime.getRuntime().gc();
        } else {
          ActivityThread.currentActivityThread().scheduleGcIdler();
        }
      }
    }
  }

  private static final class ResourceName {
    final int iconId;
    final String packageName;

    ResourceName(String _packageName, int _iconId) {
      this.packageName = _packageName;
      this.iconId = _iconId;
    }

    ResourceName(ApplicationInfo aInfo, int _iconId) {
      this(aInfo.packageName, _iconId);
    }

    ResourceName(ComponentInfo cInfo, int _iconId) {
      this(cInfo.applicationInfo.packageName, _iconId);
    }

    ResourceName(ResolveInfo rInfo, int _iconId) {
      this(rInfo.activityInfo.applicationInfo.packageName, _iconId);
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ResourceName that = (ResourceName) o;
      if (this.iconId != that.iconId) {
        return false;
      }
      String str = this.packageName;
      if (str != null) {
        if (str.equals(that.packageName)) {
          return true;
        }
      } else if (that.packageName == null) {
        return true;
      }
      return false;
    }

    public int hashCode() {
      int result = this.packageName.hashCode();
      return (result * 31) + this.iconId;
    }

    public String toString() {
      return "{ResourceName " + this.packageName + " / " + this.iconId + "}";
    }
  }

  private CharSequence getCachedString(ResourceName name) {
    synchronized (sSync) {
      WeakReference<CharSequence> wr = sStringCache.get(name);
      if (wr != null) {
        CharSequence cs = wr.get();
        if (cs != null) {
          return cs;
        }
        sStringCache.remove(name);
      }
      return null;
    }
  }

  private void putCachedString(ResourceName name, CharSequence cs) {
    synchronized (sSync) {
      sStringCache.put(name, new WeakReference<>(cs));
    }
  }

  @Override // android.content.p002pm.PackageManager
  public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
    ResourceName name = new ResourceName(packageName, resid);
    CharSequence text = getCachedString(name);
    if (text != null) {
      return text;
    }
    if (appInfo == null) {
      try {
        appInfo = getApplicationInfo(packageName, 1024);
      } catch (PackageManager.NameNotFoundException e) {
        return null;
      }
    }
    if (PMRune.PM_32BIT_APP_RUNNING_IN_ABI64
        && !this.mAbiAppHelper.canAccessApkFile(this.mContext.getApplicationInfo(), appInfo)) {
      Log.m94d(
          TAG, "The apk size is bigger than 2G, native abort might happen. return package name");
      putCachedString(name, packageName);
      return packageName;
    }
    try {
      Resources r = getResourcesForApplication(appInfo);
      CharSequence text2 = r.getText(resid);
      putCachedString(name, text2);
      return text2;
    } catch (PackageManager.NameNotFoundException e2) {
      Log.m102w("PackageManager", "Failure retrieving resources for " + appInfo.packageName);
      return null;
    } catch (RuntimeException e3) {
      Log.m103w(
          "PackageManager",
          "Failure retrieving text 0x" + Integer.toHexString(resid) + " in package " + packageName,
          e3);
      return null;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
    if (appInfo == null) {
      try {
        appInfo = getApplicationInfo(packageName, 1024);
      } catch (PackageManager.NameNotFoundException e) {
        return null;
      }
    }
    try {
      Resources r = getResourcesForApplication(appInfo);
      return r.getXml(resid);
    } catch (PackageManager.NameNotFoundException e2) {
      Log.m102w("PackageManager", "Failure retrieving resources for " + appInfo.packageName);
      return null;
    } catch (RuntimeException e3) {
      Log.m103w(
          "PackageManager",
          "Failure retrieving xml 0x" + Integer.toHexString(resid) + " in package " + packageName,
          e3);
      return null;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public CharSequence getApplicationLabel(ApplicationInfo info) {
    return info.loadLabel(this);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean applyRuntimePermissionsForMdm(
      String pkgName, List<String> permissions, int permState, int userId) {
    return getKnoxSdkHook().applyRuntimePermissionsForMdm(pkgName, permissions, permState, userId);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean applyRuntimePermissionsForAllApplicationsForMdm(int permState, int userId) {
    return getKnoxSdkHook().applyRuntimePermissionsForAllApplicationsForMdm(permState, userId);
  }

  @Override // android.content.p002pm.PackageManager
  public List<String> getRequestedRuntimePermissionsForMdm(String pkgName) {
    return getKnoxSdkHook().getRequestedRuntimePermissionsForMdm(pkgName);
  }

  private KnoxSdkHook getKnoxSdkHook() {
    if (this.mKnoxSdkHook == null) {
      this.mKnoxSdkHook =
          new KnoxSdkHook() { // from class: android.app.ApplicationPackageManager.4
          };
      this.mKnoxSdkHook = new KnoxSdkHookImpl();
    }
    return this.mKnoxSdkHook;
  }

  interface KnoxSdkHook {
    default boolean applyRuntimePermissionsForMdm(
        String pkgName, List<String> permissions, int permState, int userId) {
      return false;
    }

    default boolean applyRuntimePermissionsForAllApplicationsForMdm(int permState, int userId) {
      return false;
    }

    default List<String> getRequestedRuntimePermissionsForMdm(String pkgName) {
      return null;
    }
  }

  class KnoxSdkHookImpl implements KnoxSdkHook {
    KnoxSdkHookImpl() {}

    @Override // android.app.ApplicationPackageManager.KnoxSdkHook
    public boolean applyRuntimePermissionsForMdm(
        String pkgName, List<String> permissions, int permState, int userId) {
      try {
        return ApplicationPackageManager.this.mPM.applyRuntimePermissionsForMDM(
            pkgName, permissions, permState, userId);
      } catch (RemoteException e) {
        return false;
      }
    }

    @Override // android.app.ApplicationPackageManager.KnoxSdkHook
    public boolean applyRuntimePermissionsForAllApplicationsForMdm(int permState, int userId) {
      try {
        return ApplicationPackageManager.this.mPM.applyRuntimePermissionsForAllApplicationsForMDM(
            permState, userId);
      } catch (RemoteException e) {
        return false;
      }
    }

    @Override // android.app.ApplicationPackageManager.KnoxSdkHook
    public List<String> getRequestedRuntimePermissionsForMdm(String pkgName) {
      try {
        return ApplicationPackageManager.this.mPM.getRequestedRuntimePermissionsForMDM(pkgName);
      } catch (Exception e) {
        return null;
      }
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int installExistingPackage(String packageName)
      throws PackageManager.NameNotFoundException {
    return installExistingPackage(packageName, 0);
  }

  @Override // android.content.p002pm.PackageManager
  public int installExistingPackage(String packageName, int installReason)
      throws PackageManager.NameNotFoundException {
    return installExistingPackageAsUser(packageName, installReason, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public int installExistingPackageAsUser(String packageName, int userId)
      throws PackageManager.NameNotFoundException {
    return installExistingPackageAsUser(packageName, 0, userId);
  }

  private int installExistingPackageAsUser(String packageName, int installReason, int userId)
      throws PackageManager.NameNotFoundException {
    try {
      int res =
          this.mPM.installExistingPackageAsUser(packageName, userId, 4194304, installReason, null);
      if (res == -3) {
        throw new PackageManager.NameNotFoundException("Package " + packageName + " doesn't exist");
      }
      return res;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void verifyPendingInstall(int id, int response) {
    try {
      this.mPM.verifyPendingInstall(id, response);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void extendVerificationTimeout(
      int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
    try {
      this.mPM.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void verifyIntentFilter(int id, int verificationCode, List<String> failedDomains) {
    try {
      this.mPM.verifyIntentFilter(id, verificationCode, failedDomains);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getIntentVerificationStatusAsUser(String packageName, int userId) {
    try {
      return this.mPM.getIntentVerificationStatus(packageName, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean updateIntentVerificationStatusAsUser(String packageName, int status, int userId) {
    try {
      return this.mPM.updateIntentVerificationStatus(packageName, status, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<IntentFilterVerificationInfo> getIntentFilterVerifications(String packageName) {
    try {
      ParceledListSlice<IntentFilterVerificationInfo> parceledList =
          this.mPM.getIntentFilterVerifications(packageName);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<IntentFilter> getAllIntentFilters(String packageName) {
    try {
      ParceledListSlice<IntentFilter> parceledList = this.mPM.getAllIntentFilters(packageName);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getDefaultBrowserPackageNameAsUser(int userId) {
    RoleManager roleManager = (RoleManager) this.mContext.getSystemService(RoleManager.class);
    return roleManager.getBrowserRoleHolder(userId);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean setDefaultBrowserPackageNameAsUser(String packageName, int userId) {
    RoleManager roleManager = (RoleManager) this.mContext.getSystemService(RoleManager.class);
    return roleManager.setBrowserRoleHolder(packageName, userId);
  }

  @Override // android.content.p002pm.PackageManager
  public void setInstallerPackageName(String targetPackage, String installerPackageName) {
    try {
      this.mPM.setInstallerPackageName(targetPackage, installerPackageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setUpdateAvailable(String packageName, boolean updateAvailable) {
    try {
      this.mPM.setUpdateAvailable(packageName, updateAvailable);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getInstallerPackageName(String packageName) {
    try {
      return this.mPM.getInstallerPackageName(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public InstallSourceInfo getInstallSourceInfo(String packageName)
      throws PackageManager.NameNotFoundException {
    try {
      InstallSourceInfo installSourceInfo = this.mPM.getInstallSourceInfo(packageName, getUserId());
      if (installSourceInfo == null) {
        throw new PackageManager.NameNotFoundException(packageName);
      }
      return installSourceInfo;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getMoveStatus(int moveId) {
    try {
      return this.mPM.getMoveStatus(moveId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void registerMoveCallback(PackageManager.MoveCallback callback, Handler handler) {
    synchronized (this.mDelegates) {
      MoveCallbackDelegate delegate = new MoveCallbackDelegate(callback, handler.getLooper());
      try {
        this.mPM.registerMoveCallback(delegate);
        this.mDelegates.add(delegate);
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void unregisterMoveCallback(PackageManager.MoveCallback callback) {
    synchronized (this.mDelegates) {
      Iterator<MoveCallbackDelegate> i = this.mDelegates.iterator();
      while (i.hasNext()) {
        MoveCallbackDelegate delegate = i.next();
        if (delegate.mCallback == callback) {
          try {
            this.mPM.unregisterMoveCallback(delegate);
            i.remove();
          } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
          }
        }
      }
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int movePackage(String packageName, VolumeInfo vol) {
    String volumeUuid;
    try {
      if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(vol.f345id)) {
        volumeUuid = StorageManager.UUID_PRIVATE_INTERNAL;
      } else if (vol.isPrimaryPhysical()) {
        volumeUuid = StorageManager.UUID_PRIMARY_PHYSICAL;
      } else {
        String volumeUuid2 = vol.fsUuid;
        volumeUuid = (String) Objects.requireNonNull(volumeUuid2);
      }
      return this.mPM.movePackage(packageName, volumeUuid);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int movePackageToSd(
      String packageName, String volumeUuid, IMemorySaverPackageMoveObserver observer) {
    try {
      return this.mPM.movePackageToSd(packageName, volumeUuid, observer);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public VolumeInfo getPackageCurrentVolume(ApplicationInfo app) {
    StorageManager storage = (StorageManager) this.mContext.getSystemService(StorageManager.class);
    return getPackageCurrentVolume(app, storage);
  }

  protected VolumeInfo getPackageCurrentVolume(ApplicationInfo app, StorageManager storage) {
    if (app.isInternal()) {
      return storage.findVolumeById(VolumeInfo.ID_PRIVATE_INTERNAL);
    }
    if (app.isExternalAsec()) {
      return storage.getPrimaryPhysicalVolume();
    }
    return storage.findVolumeByUuid(app.volumeUuid);
  }

  @Override // android.content.p002pm.PackageManager
  public List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo app) {
    StorageManager storageManager =
        (StorageManager) this.mContext.getSystemService(StorageManager.class);
    return getPackageCandidateVolumes(app, storageManager, this.mPM);
  }

  protected List<VolumeInfo> getPackageCandidateVolumes(
      ApplicationInfo app, StorageManager storageManager, IPackageManager pm) {
    VolumeInfo currentVol = getPackageCurrentVolume(app, storageManager);
    List<VolumeInfo> vols = storageManager.getVolumes();
    List<VolumeInfo> candidates = new ArrayList<>();
    Log.m98i(
        TAG,
        "getPackageCandidateVolumes, currentVol: "
            + (currentVol != null ? currentVol.f345id : SemCapabilities.FEATURE_TAG_NULL));
    for (VolumeInfo vol : vols) {
      if (Objects.equals(vol, currentVol)
          || isPackageCandidateVolume(this.mContext, app, vol, pm)) {
        Log.m98i(
            TAG,
            "Add volume: "
                + vol.f345id
                + ", mountFlags: "
                + vol.mountFlags
                + ", type: "
                + vol.getType());
        candidates.add(vol);
      }
    }
    return candidates;
  }

  protected boolean isForceAllowOnExternal(Context context) {
    return Settings.Global.getInt(
            context.getContentResolver(), Settings.Global.FORCE_ALLOW_ON_EXTERNAL, 0)
        != 0;
  }

  protected boolean isAllow3rdPartyOnInternal(Context context) {
    return context.getResources().getBoolean(C4337R.bool.config_allow3rdPartyAppOnInternal);
  }

  private boolean isPackageCandidateVolume(
      ContextImpl context, ApplicationInfo app, VolumeInfo vol, IPackageManager pm) {
    boolean forceAllowOnExternal = isForceAllowOnExternal(context);
    if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(vol.getId())) {
      return app.isSystemApp() || isAllow3rdPartyOnInternal(context);
    }
    if (app.isSystemApp()) {
      return false;
    }
    if (!forceAllowOnExternal && (app.installLocation == 1 || app.installLocation == -1)) {
      Log.m98i(TAG, "Apps demanding internal storage can't be moved, " + app.packageName);
      return false;
    }
    if (!vol.isMountedWritable()) {
      Log.m98i(TAG, "This volume is not mounted writable, " + vol);
      return false;
    }
    if (vol.isPrimaryPhysical()) {
      Log.m98i(TAG, "This volume is not mounted writable, " + vol);
      return app.isInternal();
    }
    try {
      if (!pm.isPackageDeviceAdminOnAnyUser(app.packageName)) {
        return vol.getType() == 1 || vol.getType() == 0;
      }
      Log.m98i(TAG, "This package is DeviceAdmin or AnyUser, " + app.packageName);
      return false;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int movePrimaryStorage(VolumeInfo vol) {
    String volumeUuid;
    try {
      if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(vol.f345id)) {
        volumeUuid = StorageManager.UUID_PRIVATE_INTERNAL;
      } else if (vol.isPrimaryPhysical()) {
        volumeUuid = StorageManager.UUID_PRIMARY_PHYSICAL;
      } else {
        String volumeUuid2 = vol.fsUuid;
        volumeUuid = (String) Objects.requireNonNull(volumeUuid2);
      }
      return this.mPM.movePrimaryStorage(volumeUuid);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public VolumeInfo getPrimaryStorageCurrentVolume() {
    StorageManager storage = (StorageManager) this.mContext.getSystemService(StorageManager.class);
    String volumeUuid = storage.getPrimaryStorageUuid();
    return storage.findVolumeByQualifiedUuid(volumeUuid);
  }

  @Override // android.content.p002pm.PackageManager
  public List<VolumeInfo> getPrimaryStorageCandidateVolumes() {
    StorageManager storage = (StorageManager) this.mContext.getSystemService(StorageManager.class);
    VolumeInfo currentVol = getPrimaryStorageCurrentVolume();
    List<VolumeInfo> vols = storage.getVolumes();
    List<VolumeInfo> candidates = new ArrayList<>();
    if (Objects.equals(StorageManager.UUID_PRIMARY_PHYSICAL, storage.getPrimaryStorageUuid())
        && currentVol != null) {
      candidates.add(currentVol);
    } else {
      for (VolumeInfo vol : vols) {
        if (Objects.equals(vol, currentVol) || isPrimaryStorageCandidateVolume(vol)) {
          candidates.add(vol);
        }
      }
    }
    return candidates;
  }

  private static boolean isPrimaryStorageCandidateVolume(VolumeInfo vol) {
    if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(vol.getId())) {
      return true;
    }
    return vol.isMountedWritable() && vol.getType() == 1;
  }

  @Override // android.content.p002pm.PackageManager
  public void deletePackage(String packageName, IPackageDeleteObserver observer, int flags) {
    deletePackageAsUser(packageName, observer, flags, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public void deletePackageAsUser(
      String packageName, IPackageDeleteObserver observer, int flags, int userId) {
    try {
      this.mPM.deletePackageAsUser(packageName, -1, observer, userId, flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void clearApplicationUserData(String packageName, IPackageDataObserver observer) {
    try {
      this.mPM.clearApplicationUserData(packageName, observer, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) {
    try {
      this.mPM.deleteApplicationCacheFiles(packageName, observer);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void deleteApplicationCacheFilesAsUser(
      String packageName, int userId, IPackageDataObserver observer) {
    try {
      this.mPM.deleteApplicationCacheFilesAsUser(packageName, userId, observer);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void freeStorageAndNotify(
      String volumeUuid, long idealStorageSize, IPackageDataObserver observer) {
    try {
      this.mPM.freeStorageAndNotify(volumeUuid, idealStorageSize, 0, observer);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void freeStorage(String volumeUuid, long freeStorageSize, IntentSender pi) {
    try {
      this.mPM.freeStorage(volumeUuid, freeStorageSize, 0, pi);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] setDistractingPackageRestrictions(String[] packages, int distractionFlags) {
    try {
      return this.mPM.setDistractingPackageRestrictionsAsUser(
          packages, distractionFlags, this.mContext.getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] setPackagesSuspended(
      String[] packageNames,
      boolean suspended,
      PersistableBundle appExtras,
      PersistableBundle launcherExtras,
      String dialogMessage) {
    SuspendDialogInfo dialogInfo;
    if (!TextUtils.isEmpty(dialogMessage)) {
      dialogInfo = new SuspendDialogInfo.Builder().setMessage(dialogMessage).build();
    } else {
      dialogInfo = null;
    }
    return setPackagesSuspended(packageNames, suspended, appExtras, launcherExtras, dialogInfo);
  }

  @Override // android.content.p002pm.PackageManager
  public String[] setPackagesSuspended(
      String[] packageNames,
      boolean suspended,
      PersistableBundle appExtras,
      PersistableBundle launcherExtras,
      SuspendDialogInfo dialogInfo) {
    try {
      return this.mPM.setPackagesSuspendedAsUser(
          packageNames,
          suspended,
          appExtras,
          launcherExtras,
          dialogInfo,
          this.mContext.getOpPackageName(),
          getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String[] getUnsuspendablePackages(String[] packageNames) {
    try {
      return this.mPM.getUnsuspendablePackagesForUser(packageNames, this.mContext.getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Bundle getSuspendedPackageAppExtras() {
    try {
      return this.mPM.getSuspendedPackageAppExtras(this.mContext.getOpPackageName(), getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPackageSuspendedForUser(String packageName, int userId) {
    try {
      return this.mPM.isPackageSuspendedForUser(packageName, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPackageSuspended(String packageName)
      throws PackageManager.NameNotFoundException {
    try {
      return isPackageSuspendedForUser(packageName, getUserId());
    } catch (IllegalArgumentException e) {
      throw new PackageManager.NameNotFoundException(packageName);
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPackageSuspended() {
    return isPackageSuspendedForUser(this.mContext.getOpPackageName(), getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public void setApplicationCategoryHint(String packageName, int categoryHint) {
    try {
      this.mPM.setApplicationCategoryHint(
          packageName, categoryHint, this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void getPackageSizeInfoAsUser(
      String packageName, int userHandle, IPackageStatsObserver observer) {
    if (this.mContext.getApplicationInfo().targetSdkVersion >= 26) {
      throw new UnsupportedOperationException(
          "Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
    }
    if (observer != null) {
      Log.m94d(TAG, "Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
      try {
        observer.onGetStatsCompleted(null, false);
      } catch (RemoteException e) {
      }
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void addPackageToPreferred(String packageName) {
    Log.m102w(TAG, "addPackageToPreferred() is a no-op");
  }

  @Override // android.content.p002pm.PackageManager
  public void removePackageFromPreferred(String packageName) {
    Log.m102w(TAG, "removePackageFromPreferred() is a no-op");
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getPreferredPackages(int flags) {
    Log.m102w(TAG, "getPreferredPackages() is a no-op");
    return Collections.emptyList();
  }

  @Override // android.content.p002pm.PackageManager
  public void addPreferredActivity(
      IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
    try {
      this.mPM.addPreferredActivity(filter, match, set, activity, getUserId(), false);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void addPreferredActivityAsUser(
      IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) {
    try {
      this.mPM.addPreferredActivity(filter, match, set, activity, userId, false);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void replacePreferredActivity(
      IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
    try {
      this.mPM.replacePreferredActivity(filter, match, set, activity, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void replacePreferredActivityAsUser(
      IntentFilter filter, int match, ComponentName[] set, ComponentName activity, int userId) {
    try {
      this.mPM.replacePreferredActivity(filter, match, set, activity, userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void clearPackagePreferredActivities(String packageName) {
    try {
      this.mPM.clearPackagePreferredActivities(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void addUniquePreferredActivity(
      IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
    try {
      this.mPM.addPreferredActivity(filter, match, set, activity, getUserId(), true);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getPreferredActivities(
      List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) {
    try {
      return this.mPM.getPreferredActivities(outFilters, outActivities, packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ComponentName getHomeActivities(List<ResolveInfo> outActivities) {
    try {
      return this.mPM.getHomeActivities(outActivities);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setSyntheticAppDetailsActivityEnabled(String packageName, boolean enabled) {
    int i;
    try {
      ComponentName componentName = new ComponentName(packageName, APP_DETAILS_ACTIVITY_CLASS_NAME);
      IPackageManager iPackageManager = this.mPM;
      if (enabled) {
        i = 0;
      } else {
        i = 2;
      }
      iPackageManager.setComponentEnabledSetting(
          componentName, i, 1, getUserId(), this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean getSyntheticAppDetailsActivityEnabled(String packageName) {
    try {
      ComponentName componentName = new ComponentName(packageName, APP_DETAILS_ACTIVITY_CLASS_NAME);
      int state = this.mPM.getComponentEnabledSetting(componentName, getUserId());
      return state == 1 || state == 0;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
    try {
      this.mPM.setComponentEnabledSetting(
          componentName, newState, flags, getUserId(), this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setComponentEnabledSettings(List<PackageManager.ComponentEnabledSetting> settings) {
    try {
      this.mPM.setComponentEnabledSettings(settings, getUserId(), this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getComponentEnabledSetting(ComponentName componentName) {
    try {
      return this.mPM.getComponentEnabledSetting(componentName, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
    try {
      this.mPM.setApplicationEnabledSetting(
          packageName, newState, flags, getUserId(), this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getApplicationEnabledSetting(String packageName) {
    try {
      return this.mPM.getApplicationEnabledSetting(packageName, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void flushPackageRestrictionsAsUser(int userId) {
    try {
      this.mPM.flushPackageRestrictionsAsUser(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean setApplicationHiddenSettingAsUser(
      String packageName, boolean hidden, UserHandle user) {
    try {
      return this.mPM.setApplicationHiddenSettingAsUser(packageName, hidden, user.getIdentifier());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean getApplicationHiddenSettingAsUser(String packageName, UserHandle user) {
    try {
      return this.mPM.getApplicationHiddenSettingAsUser(packageName, user.getIdentifier());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setSystemAppState(String packageName, int state) {
    try {
      switch (state) {
        case 0:
          this.mPM.setSystemAppHiddenUntilInstalled(packageName, true);
          break;
        case 1:
          this.mPM.setSystemAppHiddenUntilInstalled(packageName, false);
          break;
        case 2:
          this.mPM.setSystemAppInstallState(packageName, true, getUserId());
          break;
        case 3:
          this.mPM.setSystemAppInstallState(packageName, false, getUserId());
          break;
        default:
          return;
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public KeySet getKeySetByAlias(String packageName, String alias) {
    Objects.requireNonNull(packageName);
    Objects.requireNonNull(alias);
    try {
      return this.mPM.getKeySetByAlias(packageName, alias);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public KeySet getSigningKeySet(String packageName) {
    Objects.requireNonNull(packageName);
    try {
      return this.mPM.getSigningKeySet(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isSignedBy(String packageName, KeySet ks) {
    Objects.requireNonNull(packageName);
    Objects.requireNonNull(ks);
    try {
      return this.mPM.isPackageSignedByKeySet(packageName, ks);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isSignedByExactly(String packageName, KeySet ks) {
    Objects.requireNonNull(packageName);
    Objects.requireNonNull(ks);
    try {
      return this.mPM.isPackageSignedByKeySetExactly(packageName, ks);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public VerifierDeviceIdentity getVerifierDeviceIdentity() {
    try {
      return this.mPM.getVerifierDeviceIdentity();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isUpgrade() {
    return isDeviceUpgrading();
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isDeviceUpgrading() {
    try {
      return this.mPM.isDeviceUpgrading();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInstaller getPackageInstaller() {
    if (this.mInstaller == null) {
      try {
        this.mInstaller =
            new PackageInstaller(
                this.mPM.getPackageInstaller(),
                this.mContext.getPackageName(),
                this.mContext.getAttributionTag(),
                getUserId());
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    return this.mInstaller;
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPackageAvailable(String packageName) {
    try {
      return this.mPM.isPackageAvailable(packageName, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void addCrossProfileIntentFilter(
      IntentFilter filter, int sourceUserId, int targetUserId, int flags) {
    try {
      this.mPM.addCrossProfileIntentFilter(
          filter, this.mContext.getOpPackageName(), sourceUserId, targetUserId, flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean removeCrossProfileIntentFilter(
      IntentFilter filter, int sourceUserId, int targetUserId, int flags) {
    try {
      return this.mPM.removeCrossProfileIntentFilter(
          filter, this.mContext.getOpPackageName(), sourceUserId, targetUserId, flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void clearCrossProfileIntentFilters(int sourceUserId) {
    try {
      this.mPM.clearCrossProfileIntentFilters(sourceUserId, this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable loadItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo) {
    return loadItemIcon(itemInfo, appInfo, false, 0);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable loadItemIcon(
      PackageItemInfo itemInfo, ApplicationInfo appInfo, boolean forIconContainer, int mode) {
    Drawable dr = loadUnbadgedItemIcon(itemInfo, appInfo, forIconContainer, mode);
    if (itemInfo.showUserIcon != -10000) {
      return dr;
    }
    if (appInfo != null && SemDualAppManager.isDualAppId(UserHandle.getUserId(appInfo.uid))) {
      return getUserBadgedIcon(dr, new UserHandle(UserHandle.getUserId(appInfo.uid)));
    }
    if (SemDualAppManager.isDualAppId(this.mContext.getUserId())) {
      return getUserBadgedIcon(dr, new UserHandle(0));
    }
    if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED
        && appInfo != null
        && shouldAppSupportBadgeIcon(itemInfo.packageName, UserHandle.getUserId(appInfo.uid))) {
      dr = getMonetizeBadgedIcon(dr);
    }
    return getUserBadgedIcon(dr, new UserHandle(getUserId()));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable loadUnbadgedItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo) {
    return loadUnbadgedItemIcon(itemInfo, appInfo, false, 0);
  }

  /* JADX WARN: Removed duplicated region for block: B:49:0x00d2  */
  /* JADX WARN: Removed duplicated region for block: B:51:0x00df  */
  @Override // android.content.p002pm.PackageManager
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Drawable loadUnbadgedItemIcon(
      PackageItemInfo itemInfo, ApplicationInfo appInfo, boolean forIconContainer, int mode) {
    int userId;
    boolean needSquircle;
    boolean needColorTheme;
    boolean isSetByMDM;
    Drawable dr;
    boolean isLoadDefaultApplicationIcon;
    int iconFeature;
    Drawable drLiveIcon;
    int iconFeature2;
    Drawable dr2;
    Drawable dr3;
    Drawable dr4;
    Drawable colorThemeDr;
    byte[] knoxIcon;
    if (PMRune.PM_32BIT_APP_RUNNING_IN_ABI64
        && !this.mAbiAppHelper.canAccessApkFile(this.mContext, appInfo, itemInfo.packageName)) {
      Log.m94d(
          TAG, "The apk size is bigger than 2G, native abort might happen. return default icon");
      return getDefaultActivityIcon();
    }
    Drawable dr5 = null;
    if (appInfo == null) {
      userId = 0;
    } else {
      int userId2 = UserHandle.getUserId(appInfo.uid);
      userId = userId2;
    }
    if (this.mAppIconSolution == null) {
      this.mAppIconSolution = SemAppIconSolution.getInstance(this.mContext);
    }
    int typeAppIconTheme = this.mAppIconSolution.checkAppIconThemePackage(this.mContext);
    boolean needSquircle2 = false;
    switch (mode) {
      case 1:
        needSquircle2 = true;
      case 0:
        needSquircle = needSquircle2;
        needColorTheme = false;
        break;
      default:
        boolean needSquircle3 = (mode & 16) != 0;
        boolean needColorTheme2 = (mode & 32) != 0;
        needSquircle = needSquircle3;
        needColorTheme = needColorTheme2;
        break;
    }
    if (SemPersonaManager.isKnoxIcon(itemInfo.packageName, itemInfo.name)
        && (knoxIcon = SemPersonaManager.getKnoxIcon(itemInfo.packageName, itemInfo.name, userId))
            != null) {
      Drawable dr6 =
          new BitmapDrawable(
              this.mContext.getResources(),
              BitmapFactory.decodeByteArray(knoxIcon, 0, knoxIcon.length));
      SemAppIconSolution semAppIconSolution = this.mAppIconSolution;
      return (semAppIconSolution == null || !semAppIconSolution.isAppIconThemePackageSet())
          ? dr6
          : this.mAppIconSolution.getThemeIconWithBG(this.mContext, itemInfo, dr6, mode);
    }
    boolean setThemeIcon = typeAppIconTheme == 0;
    boolean setColorThemeIcon = typeAppIconTheme == 3;
    boolean isSetByMDM2 = false;
    if (setThemeIcon) {
      boolean replacedIconFromAppPolicy = replacedIconFromAppPolicy(itemInfo.packageName, userId);
      isSetByMDM2 = replacedIconFromAppPolicy;
      if (!replacedIconFromAppPolicy) {
        dr5 = this.mAppIconSolution.getAppIconFromTheme(this.mContext, itemInfo, null, mode);
        if (dr5 == null) {
          isSetByMDM = isSetByMDM2;
          if (itemInfo.showUserIcon == -10000) {
            int targetUserId = itemInfo.showUserIcon;
            return UserIcons.getDefaultUserIcon(this.mContext.getResources(), targetUserId, false);
          }
          if (itemInfo.packageName != null) {
            dr5 = getDrawable(itemInfo.packageName, itemInfo.icon, appInfo);
          }
          if (dr5 == null && itemInfo != appInfo && appInfo != null) {
            return loadUnbadgedItemIcon(appInfo, appInfo, forIconContainer, mode);
          }
          if (dr5 != null) {
            dr = dr5;
            isLoadDefaultApplicationIcon = false;
          } else {
            Drawable dr7 = itemInfo.loadDefaultIcon(this);
            if (dr7 != null && (itemInfo instanceof ComponentInfo)) {
              dr = dr7;
              isLoadDefaultApplicationIcon = true;
            } else {
              dr = dr7;
              isLoadDefaultApplicationIcon = false;
            }
          }
          if (needSquircle || needColorTheme) {
            int iconFeature3 = semGetAppIconFeatures(itemInfo.packageName);
            iconFeature = iconFeature3;
          } else {
            iconFeature = 0;
          }
          if (appInfo != null
              && typeAppIconTheme != 2
              && PmUtils.supportLiveIcon(appInfo, this.mContext)) {
            Drawable drLiveIcon2 =
                getLiveIcon(appInfo.packageName, UserHandle.getUserId(appInfo.uid), mode);
            drLiveIcon = drLiveIcon2;
          } else {
            drLiveIcon = null;
          }
          if (drLiveIcon != null && !isSetByMDM) {
            if (needColorTheme && setColorThemeIcon) {
              Drawable colorThemeDr2 =
                  this.mAppIconSolution.getColorThemeIcon(
                      this.mContext, drLiveIcon, itemInfo.packageName, iconFeature);
              if (colorThemeDr2 != null) {
                drLiveIcon = this.mAppIconSolution.wrapIconShadow(colorThemeDr2);
              }
              iconFeature2 = iconFeature;
              dr2 = dr;
              dr3 = drLiveIcon;
            } else {
              iconFeature2 = iconFeature;
              dr2 = dr;
              dr3 =
                  this.mAppIconSolution.checkAndDrawLiveIconFromTheme(
                      this.mContext,
                      itemInfo,
                      drLiveIcon,
                      forIconContainer && needSquircle,
                      setThemeIcon,
                      mode);
            }
          } else {
            iconFeature2 = iconFeature;
            dr2 = dr;
            dr3 = drLiveIcon;
          }
          if (dr3 != null) {
            if (this.mAppIconSolution.hasAppIconColorFilter()) {
              this.mAppIconSolution.applyAppIconColorFilter(dr3);
            }
            return dr3;
          }
          if (needColorTheme
              && setColorThemeIcon
              && dr2 != null
              && (colorThemeDr =
                      this.mAppIconSolution.getColorThemeIcon(
                          this.mContext, dr2, itemInfo.packageName, iconFeature2))
                  != null) {
            return this.mAppIconSolution.wrapIconShadow(colorThemeDr);
          }
          if (forIconContainer && !setThemeIcon && dr2 != null) {
            boolean shouldPackIntoIconTray = (iconFeature2 & 1) == 0 || (iconFeature2 & 2) != 0;
            boolean drawIconTray =
                semCheckComponentMetadataForIconTray(itemInfo.packageName, itemInfo.name)
                    || shouldPackIntoIconTray;
            if (drawIconTray && needSquircle) {
              if (!isNonAdaptiveIconPkg(itemInfo.packageName)) {
                return this.mAppIconSolution.getThemeIconWithBG(
                    this.mContext, itemInfo, dr2, true, mode);
              }
              return this.mAppIconSolution.wrapIconShadowAndNight(dr2, mode);
            }
          }
          if (itemInfo.name != null && itemInfo.name.startsWith("android.permission-group")) {
            dr4 = this.mAppIconSolution.applyPrimaryColorToIcon(this.mContext, dr2);
          } else {
            dr4 =
                (dr2 == null
                        || !setThemeIcon
                        || isLoadDefaultApplicationIcon
                        || (isSetByMDM
                            && !SemPersonaManager.SECUREFOLDER_PACKAGE.equals(
                                itemInfo.packageName)))
                    ? dr2
                    : this.mAppIconSolution.getThemeIconWithBG(this.mContext, itemInfo, dr2, mode);
          }
          if (dr4 != null && this.mAppIconSolution.hasAppIconColorFilter()) {
            this.mAppIconSolution.applyAppIconColorFilter(dr4);
          }
          return dr4;
        }
        return dr5;
      }
    }
    isSetByMDM = isSetByMDM2;
    if (itemInfo.showUserIcon == -10000) {}
  }

  private Drawable getBadgedDrawable(
      Drawable drawable, Drawable badgeDrawable, Rect badgeLocation, boolean tryBadgeInPlace) {
    Bitmap bitmap;
    int badgedWidth = drawable.getIntrinsicWidth();
    int badgedHeight = drawable.getIntrinsicHeight();
    boolean reusableBitmap = false;
    if (drawable instanceof BitmapDrawable) {
      Bitmap originBitmap = ((BitmapDrawable) drawable).getBitmap();
      reusableBitmap =
          originBitmap.getWidth() == badgedWidth && originBitmap.getHeight() == badgedHeight;
    }
    boolean canBadgeInPlace =
        tryBadgeInPlace
            && (drawable instanceof BitmapDrawable)
            && ((BitmapDrawable) drawable).getBitmap().isMutable()
            && reusableBitmap;
    if (canBadgeInPlace) {
      bitmap = ((BitmapDrawable) drawable).getBitmap();
    } else {
      bitmap = Bitmap.createBitmap(badgedWidth, badgedHeight, Bitmap.Config.ARGB_8888);
    }
    Canvas canvas = new Canvas(bitmap);
    if (!canBadgeInPlace) {
      drawable.setBounds(0, 0, badgedWidth, badgedHeight);
      drawable.draw(canvas);
    }
    if (badgeLocation != null) {
      if (badgeLocation.left < 0
          || badgeLocation.top < 0
          || badgeLocation.width() > badgedWidth
          || badgeLocation.height() > badgedHeight) {
        throw new IllegalArgumentException(
            "Badge location "
                + badgeLocation
                + " not in badged drawable bounds "
                + new Rect(0, 0, badgedWidth, badgedHeight));
      }
      badgeDrawable.setBounds(0, 0, badgeLocation.width(), badgeLocation.height());
      canvas.save();
      canvas.translate(badgeLocation.left, badgeLocation.top);
      badgeDrawable.draw(canvas);
      canvas.restore();
    } else {
      badgeDrawable.mutate();
      badgeDrawable.setBounds(0, 0, badgedWidth, badgedHeight);
      badgeDrawable.draw(canvas);
    }
    if (!canBadgeInPlace) {
      BitmapDrawable mergedDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
      if (drawable instanceof BitmapDrawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        mergedDrawable.setTargetDensity(bitmapDrawable.getBitmap().getDensity());
      }
      return mergedDrawable;
    }
    return drawable;
  }

  private boolean hasUserBadge(int userId) {
    return getUserManager().hasBadge(userId);
  }

  private int getBadgeResIdForUser(int userId) {
    if (SemPersonaManager.isSecureFolderId(userId)) {
      return C4337R.drawable.ic_sf_badge;
    }
    if (SemPersonaManager.isAppSeparationUserId(userId)) {
      return C4337R.drawable.apps_separated;
    }
    if (SemDualAppManager.isDualAppId(userId)) {
      return C4337R.drawable.ic_dualapp_badge;
    }
    return 0;
  }

  private boolean isManagedProfile(int userId) {
    return getUserManager().isManagedProfile(userId);
  }

  @Override // android.content.p002pm.PackageManager
  public int getInstallReason(String packageName, UserHandle user) {
    try {
      return this.mPM.getInstallReason(packageName, user.getIdentifier());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  private static class MoveCallbackDelegate extends IPackageMoveObserver.Stub
      implements Handler.Callback {
    private static final int MSG_CREATED = 1;
    private static final int MSG_STATUS_CHANGED = 2;
    final PackageManager.MoveCallback mCallback;
    final Handler mHandler;

    public MoveCallbackDelegate(PackageManager.MoveCallback callback, Looper looper) {
      this.mCallback = callback;
      this.mHandler = new Handler(looper, this);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
      switch (msg.what) {
        case 1:
          SomeArgs args = (SomeArgs) msg.obj;
          this.mCallback.onCreated(args.argi1, (Bundle) args.arg2);
          args.recycle();
          break;
        case 2:
          SomeArgs args2 = (SomeArgs) msg.obj;
          this.mCallback.onStatusChanged(args2.argi1, args2.argi2, ((Long) args2.arg3).longValue());
          args2.recycle();
          break;
      }
      return true;
    }

    @Override // android.content.p002pm.IPackageMoveObserver
    public void onCreated(int moveId, Bundle extras) {
      SomeArgs args = SomeArgs.obtain();
      args.argi1 = moveId;
      args.arg2 = extras;
      this.mHandler.obtainMessage(1, args).sendToTarget();
    }

    @Override // android.content.p002pm.IPackageMoveObserver
    public void onStatusChanged(int moveId, int status, long estMillis) {
      SomeArgs args = SomeArgs.obtain();
      args.argi1 = moveId;
      args.argi2 = status;
      args.arg3 = Long.valueOf(estMillis);
      this.mHandler.obtainMessage(2, args).sendToTarget();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean canRequestPackageInstalls() {
    try {
      return this.mPM.canRequestPackageInstalls(this.mContext.getPackageName(), getUserId());
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ComponentName getInstantAppResolverSettingsComponent() {
    try {
      return this.mPM.getInstantAppResolverSettingsComponent();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public ComponentName getInstantAppInstallerComponent() {
    try {
      return this.mPM.getInstantAppInstallerComponent();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getInstantAppAndroidId(String packageName, UserHandle user) {
    try {
      return this.mPM.getInstantAppAndroidId(packageName, user.getIdentifier());
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  private static class DexModuleRegisterResult {
    final String dexModulePath;
    final String message;
    final boolean success;

    private DexModuleRegisterResult(String dexModulePath, boolean success, String message) {
      this.dexModulePath = dexModulePath;
      this.success = success;
      this.message = message;
    }
  }

  private static class DexModuleRegisterCallbackDelegate extends IDexModuleRegisterCallback.Stub
      implements Handler.Callback {
    private static final int MSG_DEX_MODULE_REGISTERED = 1;
    private final PackageManager.DexModuleRegisterCallback callback;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), this);

    DexModuleRegisterCallbackDelegate(PackageManager.DexModuleRegisterCallback callback) {
      this.callback = callback;
    }

    @Override // android.content.p002pm.IDexModuleRegisterCallback
    public void onDexModuleRegistered(String dexModulePath, boolean success, String message)
        throws RemoteException {
      this.mHandler
          .obtainMessage(1, new DexModuleRegisterResult(dexModulePath, success, message))
          .sendToTarget();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
      if (msg.what != 1) {
        return false;
      }
      DexModuleRegisterResult result = (DexModuleRegisterResult) msg.obj;
      this.callback.onDexModuleRegistered(result.dexModulePath, result.success, result.message);
      return true;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void registerDexModule(
      String dexModule, PackageManager.DexModuleRegisterCallback callback) {
    DexModuleRegisterCallbackDelegate callbackDelegate = null;
    if (callback != null) {
      callbackDelegate = new DexModuleRegisterCallbackDelegate(callback);
    }
    boolean isSharedModule = false;
    try {
      StructStat stat = Os.stat(dexModule);
      if ((OsConstants.S_IROTH & stat.st_mode) != 0) {
        isSharedModule = true;
      }
      try {
        this.mPM.registerDexModule(
            this.mContext.getPackageName(), dexModule, isSharedModule, callbackDelegate);
      } catch (RemoteException e) {
        throw e.rethrowAsRuntimeException();
      }
    } catch (ErrnoException e2) {
      if (callbackDelegate != null) {
        callback.onDexModuleRegistered(
            dexModule, false, "Could not get stat the module file: " + e2.getMessage());
      }
    }
  }

  @Override // android.content.p002pm.PackageManager
  public CharSequence getHarmfulAppWarning(String packageName) {
    try {
      return this.mPM.getHarmfulAppWarning(packageName, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setHarmfulAppWarning(String packageName, CharSequence warning) {
    try {
      this.mPM.setHarmfulAppWarning(packageName, warning, getUserId());
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
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

  @Override // android.content.p002pm.PackageManager
  public String getDefaultTextClassifierPackageName() {
    try {
      return this.mPM.getDefaultTextClassifierPackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getSystemTextClassifierPackageName() {
    try {
      return this.mPM.getSystemTextClassifierPackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getAttentionServicePackageName() {
    try {
      return this.mPM.getAttentionServicePackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getRotationResolverPackageName() {
    try {
      return this.mPM.getRotationResolverPackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getWellbeingPackageName() {
    try {
      return this.mPM.getWellbeingPackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getAppPredictionServicePackageName() {
    try {
      return this.mPM.getAppPredictionServicePackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getSystemCaptionsServicePackageName() {
    try {
      return this.mPM.getSystemCaptionsServicePackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getSetupWizardPackageName() {
    try {
      return this.mPM.getSetupWizardPackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public String getIncidentReportApproverPackageName() {
    try {
      return this.mPM.getIncidentReportApproverPackageName();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPackageStateProtected(String packageName, int userId) {
    try {
      return this.mPM.isPackageStateProtected(packageName, userId);
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void sendDeviceCustomizationReadyBroadcast() {
    try {
      this.mPM.sendDeviceCustomizationReadyBroadcast();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isAutoRevokeWhitelisted() {
    try {
      return this.mPM.isAutoRevokeWhitelisted(this.mContext.getPackageName());
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setMimeGroup(String mimeGroup, Set<String> mimeTypes) {
    try {
      this.mPM.setMimeGroup(this.mContext.getPackageName(), mimeGroup, new ArrayList(mimeTypes));
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Set<String> getMimeGroup(String group) {
    try {
      List<String> mimeGroup = this.mPM.getMimeGroup(this.mContext.getPackageName(), group);
      return new ArraySet(mimeGroup);
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public PackageManager.Property getProperty(String propertyName, String packageName)
      throws PackageManager.NameNotFoundException {
    Objects.requireNonNull(packageName);
    Objects.requireNonNull(propertyName);
    return getPropertyAsUser(propertyName, packageName, null, getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public PackageManager.Property getProperty(String propertyName, ComponentName component)
      throws PackageManager.NameNotFoundException {
    Objects.requireNonNull(component);
    Objects.requireNonNull(propertyName);
    return getPropertyAsUser(
        propertyName, component.getPackageName(), component.getClassName(), getUserId());
  }

  @Override // android.content.p002pm.PackageManager
  public PackageManager.Property getPropertyAsUser(
      String propertyName, String packageName, String className, int userId)
      throws PackageManager.NameNotFoundException {
    Objects.requireNonNull(packageName);
    Objects.requireNonNull(propertyName);
    try {
      PackageManager.Property property =
          this.mPM.getPropertyAsUser(propertyName, packageName, className, userId);
      if (property == null) {
        throw new PackageManager.NameNotFoundException();
      }
      return property;
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageManager.Property> queryApplicationProperty(String propertyName) {
    Objects.requireNonNull(propertyName);
    try {
      ParceledListSlice<PackageManager.Property> parceledList =
          this.mPM.queryProperty(propertyName, 5);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageManager.Property> queryActivityProperty(String propertyName) {
    Objects.requireNonNull(propertyName);
    try {
      ParceledListSlice<PackageManager.Property> parceledList =
          this.mPM.queryProperty(propertyName, 1);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageManager.Property> queryProviderProperty(String propertyName) {
    Objects.requireNonNull(propertyName);
    try {
      ParceledListSlice<PackageManager.Property> parceledList =
          this.mPM.queryProperty(propertyName, 4);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageManager.Property> queryReceiverProperty(String propertyName) {
    Objects.requireNonNull(propertyName);
    try {
      ParceledListSlice<PackageManager.Property> parceledList =
          this.mPM.queryProperty(propertyName, 2);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageManager.Property> queryServiceProperty(String propertyName) {
    Objects.requireNonNull(propertyName);
    try {
      ParceledListSlice<PackageManager.Property> parceledList =
          this.mPM.queryProperty(propertyName, 3);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isUnknownSourcePackage(String packageName) {
    try {
      return this.mPM.isUnknownSourcePackage(packageName);
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public List<PackageInfo> getUnknownSourcePackages(int flags) {
    int userId = getUserId();
    try {
      ParceledListSlice<PackageInfo> parceledList =
          this.mPM.getUnknownSourcePackagesAsUser(updateFlagsForPackage(flags, userId), userId);
      if (parceledList == null) {
        return Collections.emptyList();
      }
      return parceledList.getList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean canPackageQuery(String sourcePackageName, String targetPackageName)
      throws PackageManager.NameNotFoundException {
    Objects.requireNonNull(sourcePackageName);
    Objects.requireNonNull(targetPackageName);
    return canPackageQuery(sourcePackageName, new String[] {targetPackageName})[0];
  }

  @Override // android.content.p002pm.PackageManager
  public boolean[] canPackageQuery(String sourcePackageName, String[] targetPackageNames)
      throws PackageManager.NameNotFoundException {
    Objects.requireNonNull(sourcePackageName);
    Objects.requireNonNull(targetPackageNames);
    try {
      return this.mPM.canPackageQuery(sourcePackageName, targetPackageNames, getUserId());
    } catch (ParcelableException e) {
      e.maybeRethrow(PackageManager.NameNotFoundException.class);
      throw new RuntimeException(e);
    } catch (RemoteException re) {
      throw re.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void makeUidVisible(int recipientUid, int visibleUid) {
    try {
      this.mPM.makeUidVisible(recipientUid, visibleUid);
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public CharSequence semGetCscPackageItemText(String packageItemName) {
    return null;
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetCscPackageItemIcon(String packageItemName) {
    return null;
  }

  @Override // android.content.p002pm.PackageManager
  public int semGetSystemFeatureLevel(String name) {
    if (name == null) {
      return 0;
    }
    try {
      ParceledListSlice<FeatureInfo> parceledList = this.mPM.getSystemAvailableFeatures();
      if (parceledList == null) {
        return 0;
      }
      List<FeatureInfo> list = parceledList.getList();
      for (FeatureInfo fi : list) {
        if (fi.name != null && fi.name.equals(name)) {
          return fi.version;
        }
      }
      return 0;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean semIsPermissionRevokedByUserFixed(String permission, String packageName) {
    try {
      return this.mPM.semIsPermissionRevokedByUserFixed(
          permission, packageName, this.mContext.getUserId());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public PackageInfo semGetPackageInfoAsUser(String packageName, int flags, int userId)
      throws PackageManager.NameNotFoundException {
    return getPackageInfoAsUser(packageName, PackageManager.PackageInfoFlags.m11of(flags), userId);
  }

  public class LiveIconObject implements Cloneable {
    private Object liveIcon;

    public LiveIconObject() {}

    public Object getLiveIcon() {
      return this.liveIcon;
    }

    public void setLiveIcon(Object object) {
      this.liveIcon = object;
    }

    public Object clone() throws CloneNotSupportedException {
      LiveIconObject clone = (LiveIconObject) super.clone();
      return clone;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:30:0x0194  */
  /* JADX WARN: Removed duplicated region for block: B:34:0x019a A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:50:0x0196  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private Drawable getLiveIcon(String pkgName, int userId, int mode) {
    int param;
    Method m = null;
    try {
      ApplicationInfo appInfo = getApplicationInfo(pkgName, 8320);
      String pkgPath = appInfo.sourceDir;
      Bundle meta = appInfo.metaData;
      if (meta == null) {
        Log.m94d(
            TAG, "Doesn't have metadata for LiveIcon : [" + pkgName + "]  just show default Icon.");
        return null;
      }
      boolean liveIconSupport = meta.getBoolean("LiveIconSupport");
      if (liveIconSupport) {
        Method m2 = null;
        String getLiveIconClassName = pkgName + ".LiveIconLoader";
        ArrayMap<String, String> arrayMap = sLiveIconPackageMatchers;
        synchronized (arrayMap) {
          try {
            try {
              ArrayMap<String, Method> arrayMap2 = sLiveIconLoaders;
              try {
                synchronized (arrayMap2) {
                  try {
                  } catch (Throwable th) {
                    ex = th;
                  }
                  try {
                    if (arrayMap.containsKey(pkgName)) {
                      if (pkgPath != null && pkgPath.equals(arrayMap.get(pkgName))) {
                        Log.m94d(TAG, "we has " + pkgName + " class. reuse it ");
                        m2 = arrayMap2.get(pkgName);
                      } else if (pkgPath != null && !pkgPath.equals(arrayMap.get(pkgName))) {
                        Log.m94d(TAG, "we don't have " + pkgPath + " package path. load it");
                        PathClassLoader liveIconClassLoader =
                            new PathClassLoader(pkgPath, ClassLoader.getSystemClassLoader());
                        try {
                          Class<?> cl =
                              Class.forName(getLiveIconClassName, true, liveIconClassLoader);
                          try {
                            Method m3 = cl.getMethod("getLiveIcon", Context.class, Integer.TYPE);
                            m = m3;
                          } catch (NoSuchMethodException e) {
                            try {
                              Method m4 = cl.getMethod("getLiveIcon", Context.class);
                              m = m4;
                            } catch (NoSuchMethodException ex2) {
                              Log.m97e(TAG, "!@call method fail getLiveIcon", ex2);
                              return null;
                            }
                          }
                          ArrayMap<String, String> arrayMap3 = sLiveIconPackageMatchers;
                          arrayMap3.remove(pkgName);
                          ArrayMap<String, Method> arrayMap4 = sLiveIconLoaders;
                          arrayMap4.remove(pkgName);
                          arrayMap3.put(pkgName, pkgPath);
                          arrayMap4.put(pkgName, m);
                          m2 = m;
                        } catch (ClassNotFoundException e2) {
                          Log.m96e(TAG, "!@can't found class" + getLiveIconClassName);
                          return null;
                        }
                      }
                      if ((mode & 256) == 0) {
                        param = 1;
                      } else {
                        param = 0;
                      }
                      if (m2 != null) {
                        try {
                          LiveIconObject liveIconObj = new LiveIconObject();
                          if (m2.getParameterCount() == 1) {
                            liveIconObj.setLiveIcon(m2.invoke(null, this.mContext));
                          } else {
                            switch (param) {
                              case 1:
                                liveIconObj.setLiveIcon(m2.invoke(null, this.mContext, 1));
                                break;
                            }
                            liveIconObj.setLiveIcon(m2.invoke(null, this.mContext, 0));
                          }
                          LiveIconObject cloneLiveIcon = (LiveIconObject) liveIconObj.clone();
                          Object o = cloneLiveIcon.getLiveIcon();
                          if (o instanceof Drawable) {
                            return (Drawable) o;
                          }
                          Log.m98i(TAG, "Abnormal object has returned for liveicon : " + o);
                        } catch (Exception ex) {
                          Log.m97e(TAG, "FAILED to getLiveIcon", ex);
                          ex.printStackTrace();
                          return null;
                        }
                      }
                    } else {
                      Log.m94d(TAG, "we don't have " + pkgName + " package name. load it");
                      PathClassLoader liveIconClassLoader2 =
                          new PathClassLoader(pkgPath, ClassLoader.getSystemClassLoader());
                      try {
                        Class<?> cl2 =
                            Class.forName(getLiveIconClassName, true, liveIconClassLoader2);
                        try {
                          Method m5 = cl2.getMethod("getLiveIcon", Context.class, Integer.TYPE);
                          m = m5;
                        } catch (NoSuchMethodException e3) {
                          try {
                            Method m6 = cl2.getMethod("getLiveIcon", Context.class);
                            m = m6;
                          } catch (NoSuchMethodException ex22) {
                            Log.m97e(TAG, "!@call method fail getLiveIcon", ex22);
                            return null;
                          }
                        }
                        sLiveIconPackageMatchers.put(pkgName, pkgPath);
                        sLiveIconLoaders.put(pkgName, m);
                        m2 = m;
                      } catch (ClassNotFoundException e4) {
                        Log.m96e(TAG, "!@can't found class" + getLiveIconClassName);
                        return null;
                      }
                    }
                    if ((mode & 256) == 0) {}
                    if (m2 != null) {}
                  } catch (Throwable th2) {
                    ex = th2;
                    throw ex;
                  }
                }
              } catch (Throwable th3) {
                ex = th3;
                throw ex;
              }
            } catch (Throwable th4) {
              th = th4;
              throw th;
            }
          } catch (Throwable th5) {
            th = th5;
          }
        }
      }
      Log.m98i(TAG, "Failed to get live icon, liveIconSupport:" + liveIconSupport);
      return null;
    } catch (PackageManager.NameNotFoundException e5) {
      Log.m98i(TAG, "get application info error in getLiveIcon : " + pkgName);
      return null;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetActivityIconForIconTray(ComponentName activityName, int mode)
      throws PackageManager.NameNotFoundException {
    return getActivityInfo(activityName, 1024).loadIcon(this, true, mode);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetActivityIconForIconTray(Intent intent, int mode)
      throws PackageManager.NameNotFoundException {
    if (intent.getComponent() != null) {
      return semGetActivityIconForIconTray(intent.getComponent(), mode);
    }
    ResolveInfo info = resolveActivity(intent, 65536);
    if (info != null) {
      return info.activityInfo.loadIcon(this, true, mode);
    }
    throw new PackageManager.NameNotFoundException(intent.toUri(0));
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetApplicationIconForIconTray(ApplicationInfo info, int mode) {
    return info.loadIcon(this, true, mode);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetApplicationIconForIconTray(String packageName, int mode)
      throws PackageManager.NameNotFoundException {
    return semGetApplicationIconForIconTray(getApplicationInfo(packageName, 1024), mode);
  }

  private Drawable hidden_semGetApplicationIconForIconTray(String packageName, int mode)
      throws PackageManager.NameNotFoundException {
    return semGetApplicationIconForIconTray(packageName, mode);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean semCheckComponentMetadataForIconTray(String packageName, String componentName) {
    return false;
  }

  @Override // android.content.p002pm.PackageManager
  public boolean semShouldPackIntoIconTray(String packageName) {
    if (isNonAdaptiveIconPkg(packageName)) {
      return true;
    }
    try {
      List<String> featureList = new ArrayList<>();
      boolean ret =
          !this.mPM.getMetadataForIconTray(
              packageName,
              "com.samsung.android.icon_container.has_icon_container",
              this.mContext.getUserId(),
              featureList);
      if (!ret && !featureList.isEmpty()) {
        for (String feature : featureList) {
          if ("ADAPTIVEICON_SHADOW".equals(feature)) {
            return true;
          }
        }
      }
      if (!ret) {
        Log.m98i(
            "AppIconSolution",
            "has_icon_container is maintained so ignore icon processing, pkg = " + packageName);
      }
      return ret;
    } catch (RemoteException e) {
      return false;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetDrawableForIconTray(Drawable icon, int mode) {
    return semGetDrawableForIconTray(icon, mode, null, 0);
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable semGetDrawableForIconTray(
      Drawable icon, int mode, String packageName, int density) {
    boolean needSquircle;
    boolean needColorTheme;
    boolean ignoreThemeTray;
    boolean z;
    Log.m98i(
        "AppIconSolution",
        "getThemeIconWithBG called with public API, pkg = " + packageName + ", mode = " + mode);
    boolean needSquircle2 = false;
    boolean ignoreThemeTray2 = false;
    if (icon instanceof SemAppIconSolution.ShadowDrawable) {
      Log.m98i(
          "AppIconSolution",
          "shadow is already applied, pkg = " + packageName + ", mode = " + mode);
      return icon;
    }
    boolean z2 = true;
    switch (mode) {
      case 2:
        ignoreThemeTray2 = true;
      case 1:
        needSquircle2 = true;
      case 0:
        needSquircle = needSquircle2;
        needColorTheme = false;
        ignoreThemeTray = ignoreThemeTray2;
        break;
      default:
        if ((mode & 16) != 0) {
          z = true;
        } else {
          z = false;
        }
        boolean needSquircle3 = z;
        boolean needColorTheme2 = (mode & 32) != 0;
        needSquircle = needSquircle3;
        needColorTheme = needColorTheme2;
        ignoreThemeTray = false;
        break;
    }
    if (this.mAppIconSolution == null) {
      this.mAppIconSolution = SemAppIconSolution.getInstance(this.mContext);
    }
    int typeAppIconTheme = this.mAppIconSolution.checkAppIconThemePackage(this.mContext);
    boolean useColorThemeIcon = typeAppIconTheme == 3;
    if (needColorTheme && useColorThemeIcon && icon != null) {
      if (SemPersonaManager.SECUREFOLDER_PACKAGE.equals(packageName)
          && replacedIconFromAppPolicy(packageName, this.mContext.getUserId())) {
        Log.m98i(
            "AppIconSolution", "customized secure folder icon is skipped to apply color palette");
      } else {
        int iconFeature = semGetAppIconFeatures(packageName);
        Drawable colorThemeDr =
            this.mAppIconSolution.getColorThemeIcon(this.mContext, icon, packageName, iconFeature);
        if (colorThemeDr != null) {
          return this.mAppIconSolution.wrapIconShadow(colorThemeDr);
        }
      }
    }
    if (!needSquircle) {
      return icon;
    }
    if (isNonAdaptiveIconPkg(packageName)
        && (ignoreThemeTray || !this.mAppIconSolution.isAppIconThemePackageSet())) {
      return this.mAppIconSolution.wrapIconShadowAndNight(icon, mode);
    }
    SemAppIconSolution semAppIconSolution = this.mAppIconSolution;
    ContextImpl contextImpl = this.mContext;
    if (!ignoreThemeTray && semAppIconSolution.isAppIconThemePackageSet()) {
      z2 = false;
    }
    return semAppIconSolution.getThemeIconWithBG(
        contextImpl, null, icon, Boolean.valueOf(z2), false, density, packageName, mode);
  }

  private boolean replacedIconFromAppPolicy(String packageName, int userId) {
    if (packageName == null) {
      return false;
    }
    try {
      if (this.mApplicationPolicy == null) {
        this.mApplicationPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
      }
      return this.mApplicationPolicy.getApplicationIconFromDb(packageName, userId) != null;
    } catch (Exception e) {
      Log.m96e(TAG, "Exception occurred in EnterpriseDeviceManager");
      return false;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int semGetAppIconFeatures(String packageName) {
    if (isNonAdaptiveIconPkg(packageName)) {
      return 4;
    }
    int retValue = 0;
    try {
      List<String> featureList = new ArrayList<>();
      boolean hasIconContainer =
          this.mPM.getMetadataForIconTray(
              packageName,
              "com.samsung.android.icon_container.has_icon_container",
              this.mContext.getUserId(),
              featureList);
      if (hasIconContainer) {
        Log.m98i(
            "AppIconSolution",
            "has_icon_container is maintained so ignore icon processing, pkg = " + packageName);
        retValue = 0 | 1;
      }
      if (!featureList.isEmpty()) {
        for (String feature : featureList) {
          if ("ADAPTIVEICON_SHADOW".equals(feature)) {
            retValue |= 2;
          } else if ("COLOR_NO_ADAPTIVE".equals(feature)) {
            retValue |= 4;
          } else if ("COLOR_ONLY_BG".equals(feature)) {
            retValue |= 8;
          }
        }
      }
    } catch (RemoteException e) {
    }
    return retValue;
  }

  private boolean isNonAdaptiveIconPkg(String packageName) {
    return SamsungThemeConstants.nonAdaptiveIconPkgList.contains(packageName);
  }

  @Override // android.content.p002pm.PackageManager
  public boolean canUserUninstall(String packageName, UserHandle user) {
    try {
      return this.mPM.getBlockUninstallForUser(packageName, user.getIdentifier());
    } catch (RemoteException e) {
      throw e.rethrowAsRuntimeException();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean shouldShowNewAppInstalledNotification() {
    return Settings.Global.getInt(
            this.mContext.getContentResolver(),
            Settings.Global.SHOW_NEW_APP_INSTALLED_NOTIFICATION_ENABLED,
            0)
        == 1;
  }

  @Override // android.content.p002pm.PackageManager
  public boolean isPackageAutoDisabled(String packageName, int uid) {
    try {
      return this.mPM.isPackageAutoDisabled(packageName, uid);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Exception to get lastDisableCaller");
      return false;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void relinquishUpdateOwnership(String targetPackage) {
    Objects.requireNonNull(targetPackage);
    try {
      this.mPM.relinquishUpdateOwnership(targetPackage);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void setApplicationEnabledSettingWithList(
      List<String> listPackageName, int newState, int flags, boolean usePending, boolean startNow) {
    try {
      this.mPM.setApplicationEnabledSettingWithList(
          listPackageName,
          newState,
          flags,
          usePending,
          startNow,
          this.mContext.getUserId(),
          this.mContext.getBasePackageName());
    } catch (RemoteException e) {
    }
  }

  @Override // android.content.p002pm.PackageManager
  public int getProgressionOfPackageChanged() {
    try {
      return this.mPM.getProgressionOfPackageChanged();
    } catch (RemoteException e) {
      return -1;
    }
  }

  @Override // android.content.p002pm.PackageManager
  public void cancelEMPHandlerSendPendingBroadcast() {
    try {
      this.mPM.cancelEMPHandlerSendPendingBroadcast();
    } catch (RemoteException e) {
    }
  }

  @Override // android.content.p002pm.PackageManager
  public boolean shouldAppSupportBadgeIcon(String packageName, int userId) {
    if (packageName != null && userId == 0) {
      try {
        return this.mPM.shouldAppSupportBadgeIcon(packageName);
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }
    return false;
  }

  @Override // android.content.p002pm.PackageManager
  public Drawable getMonetizeBadgedIcon(Drawable icon) {
    Drawable badgeIcon = getDrawable("system", C4337R.drawable.monetization_badge, null);
    int icon_width = icon.getIntrinsicWidth();
    int icon_height = icon.getIntrinsicHeight();
    return getBadgedDrawable(
        icon, badgeIcon, new Rect(0, 0, icon_width / 4, icon_height / 4), true);
  }
}
