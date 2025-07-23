package android.content.pm;

import android.Manifest;
import android.apex.ApexInfo;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.overlay.OverlayPaths;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.pm.pkg.FrameworkPackageUserState;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.sec.enterprise.proxy.EnterpriseProxyConstants;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.IntArray;
import android.util.Log;
import android.util.PackageUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.R;
import com.android.internal.pm.pkg.SEInfoUtil;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import libcore.util.HexEncoding;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
/* loaded from: classes.dex */
public class PackageParser {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    public static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    public static final String APEX_FILE_EXTENSION = ".apex";
    public static final String APK_FILE_EXTENSION = ".apk";
    public static final Set<String> CHILD_PACKAGE_TAGS;
    public static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_JAR = false;
    public static final boolean DEBUG_PARSER = false;
    private static final int DEFAULT_MIN_SDK_VERSION = 1;
    public static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    public static final boolean LOG_PARSE_TIMINGS = Build.IS_DEBUGGABLE;
    public static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    public static final boolean LOG_UNSAFE_BROADCASTS = false;
    public static final String METADATA_ACTIVITY_WINDOW_LAYOUT_AFFINITY = "android.activity_window_layout_affinity";
    public static final String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    public static final String METADATA_SUPPORTS_SIZE_CHANGES = "android.supports_size_changes";
    public static final String MNT_EXPAND = "/mnt/expand/";
    public static final boolean MULTI_PACKAGE_APK_ENABLED;
    public static final NewPermissionInfo[] NEW_PERMISSIONS;
    public static final int PARSE_CHATTY = Integer.MIN_VALUE;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    public static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    public static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    public static final int PARSE_ENFORCE_CODE = 64;
    public static final int PARSE_EXTERNAL_STORAGE = 8;
    public static final int PARSE_IGNORE_PROCESSES = 2;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_MUST_BE_APK = 1;
    private static final String PROPERTY_CHILD_PACKAGES_ENABLED = "persist.sys.child_packages_enabled";
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    public static final boolean RIGID_PARSER = false;
    public static final Set<String> SAFE_BROADCASTS;
    public static final String[] SDK_CODENAMES;
    public static final int SDK_VERSION;
    private static final String TAG = "PackageParser";
    public static final String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    public static final String TAG_APPLICATION = "application";
    public static final String TAG_ATTRIBUTION = "attribution";
    public static final String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    public static final String TAG_EAT_COMMENT = "eat-comment";
    public static final String TAG_FEATURE_GROUP = "feature-group";
    public static final String TAG_INSTRUMENTATION = "instrumentation";
    public static final String TAG_KEY_SETS = "key-sets";
    public static final String TAG_MANIFEST = "manifest";
    public static final String TAG_ORIGINAL_PACKAGE = "original-package";
    public static final String TAG_OVERLAY = "overlay";
    public static final String TAG_PACKAGE = "package";
    public static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    public static final String TAG_PERMISSION = "permission";
    public static final String TAG_PERMISSION_GROUP = "permission-group";
    public static final String TAG_PERMISSION_TREE = "permission-tree";
    public static final String TAG_PROFILEABLE = "profileable";
    public static final String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    public static final String TAG_QUERIES = "queries";
    public static final String TAG_RESTRICT_UPDATE = "restrict-update";
    public static final String TAG_SUPPORTS_INPUT = "supports-input";
    public static final String TAG_SUPPORT_SCREENS = "supports-screens";
    public static final String TAG_USES_CONFIGURATION = "uses-configuration";
    public static final String TAG_USES_FEATURE = "uses-feature";
    public static final String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    public static final String TAG_USES_PERMISSION = "uses-permission";
    public static final String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    public static final String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    public static final String TAG_USES_SDK = "uses-sdk";
    public static final String TAG_USES_SPLIT = "uses-split";
    public static boolean sCompatibilityModeEnabled;
    public static final Comparator<String> sSplitNameComparator;
    public static boolean sUseRoundIcon;

    @Deprecated
    public String mArchiveSourcePath;
    private File mCacheDir;
    public Callback mCallback;
    private DisplayMetrics mMetrics;
    private boolean mOnlyCoreApps;
    public int mParseError = 1;
    private ParsePackageItemArgs mParseInstrumentationArgs;
    public String[] mSeparateProcesses;

    public interface Callback {
        boolean hasFeature(String str);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParseFlags {
    }

    @Deprecated
    private interface SplitAssetLoader extends AutoCloseable {
        ApkAssets getBaseApkAssets();

        AssetManager getBaseAssetManager() throws PackageParserException;

        AssetManager getSplitAssetManager(int i) throws PackageParserException;
    }

    public static int getActivityConfigChanges(int i, int i2) {
        return i | ((~i2) & 3);
    }

    public static boolean reportIfDebug(boolean z, long j) {
        return z;
    }

    static {
        MULTI_PACKAGE_APK_ENABLED = Build.IS_DEBUGGABLE && SystemProperties.getBoolean(PROPERTY_CHILD_PACKAGES_ENABLED, false);
        ArraySet arraySet = new ArraySet();
        CHILD_PACKAGE_TAGS = arraySet;
        arraySet.add("application");
        arraySet.add("compatible-screens");
        arraySet.add("eat-comment");
        arraySet.add("feature-group");
        arraySet.add("instrumentation");
        arraySet.add("supports-screens");
        arraySet.add("supports-input");
        arraySet.add("uses-configuration");
        arraySet.add("uses-feature");
        arraySet.add("uses-gl-texture");
        arraySet.add("uses-permission");
        arraySet.add("uses-permission-sdk-23");
        arraySet.add("uses-permission-sdk-m");
        arraySet.add("uses-sdk");
        ArraySet arraySet2 = new ArraySet();
        SAFE_BROADCASTS = arraySet2;
        arraySet2.add(Intent.ACTION_BOOT_COMPLETED);
        NEW_PERMISSIONS = new NewPermissionInfo[]{new NewPermissionInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE, 4, 0), new NewPermissionInfo(Manifest.permission.READ_PHONE_STATE, 4, 0)};
        SDK_VERSION = Build.VERSION.SDK_INT;
        SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;
        sCompatibilityModeEnabled = true;
        sUseRoundIcon = false;
        sSplitNameComparator = new SplitNameComparator();
    }

    public static class NewPermissionInfo {
        public final int fileVersion;
        public final String name;
        public final int sdkVersion;

        public NewPermissionInfo(String str, int i, int i2) {
            this.name = str;
            this.sdkVersion = i;
            this.fileVersion = i2;
        }
    }

    static class ParsePackageItemArgs {
        final int bannerRes;
        final int iconRes;
        final int labelRes;
        final int logoRes;
        final int nameRes;
        final String[] outError;
        final Package owner;
        final int roundIconRes;
        TypedArray sa;
        String tag;

        ParsePackageItemArgs(Package r1, String[] strArr, int i, int i2, int i3, int i4, int i5, int i6) {
            this.owner = r1;
            this.outError = strArr;
            this.nameRes = i;
            this.labelRes = i2;
            this.iconRes = i3;
            this.logoRes = i5;
            this.bannerRes = i6;
            this.roundIconRes = i4;
        }
    }

    public static class ParseComponentArgs extends ParsePackageItemArgs {
        final int descriptionRes;
        final int enabledRes;
        int flags;
        final int processRes;
        final String[] sepProcesses;

        public ParseComponentArgs(Package r1, String[] strArr, int i, int i2, int i3, int i4, int i5, int i6, String[] strArr2, int i7, int i8, int i9) {
            super(r1, strArr, i, i2, i3, i4, i5, i6);
            this.sepProcesses = strArr2;
            this.processRes = i7;
            this.descriptionRes = i8;
            this.enabledRes = i9;
        }
    }

    public static class PackageLite {
        public final String baseCodePath;
        public final int baseRevisionCode;
        public final String codePath;
        public final String[] configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        public final int installLocation;
        public final boolean[] isFeatureSplits;
        public final boolean isSplitRequired;
        public final boolean isolatedSplits;
        public final boolean multiArch;
        public final String packageName;
        public final boolean profilableByShell;
        public final String[] splitCodePaths;
        public final String[] splitNames;
        public final int[] splitRevisionCodes;
        public final boolean use32bitAbi;
        public final boolean useEmbeddedDex;
        public final String[] usesSplitNames;
        public final VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public PackageLite(String str, String str2, ApkLite apkLite, String[] strArr, boolean[] zArr, String[] strArr2, String[] strArr3, String[] strArr4, int[] iArr) {
            this.packageName = apkLite.packageName;
            this.versionCode = apkLite.versionCode;
            this.versionCodeMajor = apkLite.versionCodeMajor;
            this.installLocation = apkLite.installLocation;
            this.verifiers = apkLite.verifiers;
            this.splitNames = strArr;
            this.isFeatureSplits = zArr;
            this.usesSplitNames = strArr2;
            this.configForSplit = strArr3;
            this.codePath = str;
            this.baseCodePath = str2;
            this.splitCodePaths = strArr4;
            this.baseRevisionCode = apkLite.revisionCode;
            this.splitRevisionCodes = iArr;
            this.coreApp = apkLite.coreApp;
            this.debuggable = apkLite.debuggable;
            this.multiArch = apkLite.multiArch;
            this.use32bitAbi = apkLite.use32bitAbi;
            this.extractNativeLibs = apkLite.extractNativeLibs;
            this.isolatedSplits = apkLite.isolatedSplits;
            this.useEmbeddedDex = apkLite.useEmbeddedDex;
            this.isSplitRequired = apkLite.isSplitRequired;
            this.profilableByShell = apkLite.profilableByShell;
        }

        public List<String> getAllCodePaths() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(arrayList, this.splitCodePaths);
            }
            return arrayList;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

    public static class ApkLite {
        public final String codePath;
        public final String configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        public final int installLocation;
        public boolean isFeatureSplit;
        public final boolean isSplitRequired;
        public final boolean isolatedSplits;
        public final int minSdkVersion;
        public final boolean multiArch;
        public final boolean overlayIsStatic;
        public final int overlayPriority;
        public final String packageName;
        public final boolean profilableByShell;
        public final int revisionCode;
        public final int rollbackDataPolicy;
        public final SigningDetails signingDetails;
        public final String splitName;
        public final String targetPackageName;
        public final int targetSdkVersion;
        public final boolean use32bitAbi;
        public final boolean useEmbeddedDex;
        public final String usesSplitName;
        public final VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public ApkLite(String str, String str2, String str3, boolean z, String str4, String str5, boolean z2, int i, int i2, int i3, int i4, List<VerifierInfo> list, SigningDetails signingDetails, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, String str6, boolean z11, int i5, int i6, int i7, int i8) {
            this.codePath = str;
            this.packageName = str2;
            this.splitName = str3;
            this.isFeatureSplit = z;
            this.configForSplit = str4;
            this.usesSplitName = str5;
            this.versionCode = i;
            this.versionCodeMajor = i2;
            this.revisionCode = i3;
            this.installLocation = i4;
            this.signingDetails = signingDetails;
            this.verifiers = (VerifierInfo[]) list.toArray(new VerifierInfo[list.size()]);
            this.coreApp = z3;
            this.debuggable = z4;
            this.profilableByShell = z5;
            this.multiArch = z6;
            this.use32bitAbi = z7;
            this.useEmbeddedDex = z8;
            this.extractNativeLibs = z9;
            this.isolatedSplits = z10;
            this.isSplitRequired = z2;
            this.targetPackageName = str6;
            this.overlayIsStatic = z11;
            this.overlayPriority = i5;
            this.minSdkVersion = i6;
            this.targetSdkVersion = i7;
            this.rollbackDataPolicy = i8;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

    private static class CachedComponentArgs {
        ParseComponentArgs mActivityAliasArgs;
        ParseComponentArgs mActivityArgs;
        ParseComponentArgs mProviderArgs;
        ParseComponentArgs mServiceArgs;

        private CachedComponentArgs() {
        }
    }

    public PackageParser() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mMetrics = displayMetrics;
        displayMetrics.setToDefaults();
    }

    public void setSeparateProcesses(String[] strArr) {
        this.mSeparateProcesses = strArr;
    }

    public void setOnlyCoreApps(boolean z) {
        this.mOnlyCoreApps = z;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.mMetrics = displayMetrics;
    }

    public void setCacheDir(File file) {
        this.mCacheDir = file;
    }

    public static final class CallbackImpl implements Callback {
        private final PackageManager mPm;

        public CallbackImpl(PackageManager packageManager) {
            this.mPm = packageManager;
        }

        @Override // android.content.pm.PackageParser.Callback
        public boolean hasFeature(String str) {
            return this.mPm.hasSystemFeature(str);
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public static final boolean isApkFile(File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(String str) {
        return str.endsWith(".apk");
    }

    private static boolean checkUseInstalledOrHidden(int i, FrameworkPackageUserState frameworkPackageUserState, ApplicationInfo applicationInfo) {
        int i2 = 536870912 & i;
        if (i2 == 0 && !frameworkPackageUserState.isInstalled() && applicationInfo != null && applicationInfo.hiddenUntilInstalled) {
            return false;
        }
        if (isAvailable(frameworkPackageUserState, i)) {
            return true;
        }
        return (applicationInfo == null || !applicationInfo.isSystemApp() || ((i & PackageManager.MATCH_KNOWN_PACKAGES) == 0 && i2 == 0)) ? false : true;
    }

    public static boolean isAvailable(FrameworkPackageUserState frameworkPackageUserState) {
        return checkUseInstalledOrHidden(0, frameworkPackageUserState, null);
    }

    public static PackageInfo generatePackageInfo(Package r10, int[] iArr, int i, long j, long j2, Set<String> set, FrameworkPackageUserState frameworkPackageUserState) {
        return generatePackageInfo(r10, iArr, i, j, j2, set, frameworkPackageUserState, UserHandle.getCallingUserId());
    }

    public static PackageInfo generatePackageInfo(Package r11, int[] iArr, int i, long j, long j2, Set<String> set, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generatePackageInfo(r11, null, iArr, i, j, j2, set, frameworkPackageUserState, i2);
    }

    public static PackageInfo generatePackageInfo(Package r11, ApexInfo apexInfo, int i) {
        return generatePackageInfo(r11, apexInfo, EmptyArray.INT, i, 0L, 0L, Collections.EMPTY_SET, FrameworkPackageUserState.DEFAULT, UserHandle.getCallingUserId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r1v16, types: [boolean] */
    private static PackageInfo generatePackageInfo(Package r17, ApexInfo apexInfo, int[] iArr, int i, long j, long j2, Set<String> set, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        ?? r16;
        int i3;
        int i4;
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        if (!checkUseInstalledOrHidden(i, frameworkPackageUserState, r17.applicationInfo) || !r17.isMatch(i)) {
            return null;
        }
        ApplicationInfo generateApplicationInfo = (i & 15) != 0 ? generateApplicationInfo(r17, i, frameworkPackageUserState, i2) : null;
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.packageName = r17.packageName;
        packageInfo.splitNames = r17.splitNames;
        packageInfo.versionCode = r17.mVersionCode;
        packageInfo.versionCodeMajor = r17.mVersionCodeMajor;
        packageInfo.baseRevisionCode = r17.baseRevisionCode;
        packageInfo.splitRevisionCodes = r17.splitRevisionCodes;
        packageInfo.versionName = r17.mVersionName;
        packageInfo.sharedUserId = r17.mSharedUserId;
        packageInfo.sharedUserLabel = r17.mSharedUserLabel;
        packageInfo.applicationInfo = generateApplicationInfo(r17, i, frameworkPackageUserState, i2);
        packageInfo.installLocation = r17.installLocation;
        packageInfo.isStub = r17.isStub;
        packageInfo.coreApp = r17.coreApp;
        boolean z = true;
        if ((packageInfo.applicationInfo.flags & 1) != 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
            packageInfo.requiredForAllUsers = r17.mRequiredForAllUsers;
        }
        packageInfo.restrictedAccountType = r17.mRestrictedAccountType;
        packageInfo.requiredAccountType = r17.mRequiredAccountType;
        packageInfo.overlayTarget = r17.mOverlayTarget;
        packageInfo.targetOverlayableName = r17.mOverlayTargetName;
        packageInfo.overlayCategory = r17.mOverlayCategory;
        packageInfo.overlayPriority = r17.mOverlayPriority;
        packageInfo.mOverlayIsStatic = r17.mOverlayIsStatic;
        packageInfo.compileSdkVersion = r17.mCompileSdkVersion;
        packageInfo.compileSdkVersionCodename = r17.mCompileSdkVersionCodename;
        packageInfo.firstInstallTime = j;
        packageInfo.lastUpdateTime = j2;
        if ((i & 256) != 0) {
            packageInfo.gids = iArr;
        }
        if ((i & 16384) != 0) {
            int size6 = r17.configPreferences != null ? r17.configPreferences.size() : 0;
            if (size6 > 0) {
                packageInfo.configPreferences = new ConfigurationInfo[size6];
                r17.configPreferences.toArray(packageInfo.configPreferences);
            }
            int size7 = r17.reqFeatures != null ? r17.reqFeatures.size() : 0;
            if (size7 > 0) {
                packageInfo.reqFeatures = new FeatureInfo[size7];
                r17.reqFeatures.toArray(packageInfo.reqFeatures);
            }
            int size8 = r17.featureGroups != null ? r17.featureGroups.size() : 0;
            if (size8 > 0) {
                packageInfo.featureGroups = new FeatureGroupInfo[size8];
                r17.featureGroups.toArray(packageInfo.featureGroups);
            }
        }
        if ((i & 1) == 0 || (size5 = r17.activities.size()) <= 0) {
            r16 = true;
            i3 = 0;
        } else {
            ActivityInfo[] activityInfoArr = new ActivityInfo[size5];
            int i5 = 0;
            int i6 = 0;
            while (i5 < size5) {
                Activity activity = r17.activities.get(i5);
                boolean z2 = z;
                if (isMatch(frameworkPackageUserState, activity.info, i) && !PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(activity.className)) {
                    activityInfoArr[i6] = generateActivityInfo(activity, i, frameworkPackageUserState, i2, generateApplicationInfo);
                    i6++;
                }
                i5++;
                z = z2;
            }
            r16 = z;
            i3 = 0;
            packageInfo.activities = (ActivityInfo[]) ArrayUtils.trimToSize(activityInfoArr, i6);
        }
        if ((i & 2) != 0 && (size4 = r17.receivers.size()) > 0) {
            ActivityInfo[] activityInfoArr2 = new ActivityInfo[size4];
            int i7 = i3;
            int i8 = i7;
            while (i7 < size4) {
                Activity activity2 = r17.receivers.get(i7);
                if (isMatch(frameworkPackageUserState, activity2.info, i)) {
                    activityInfoArr2[i8] = generateActivityInfo(activity2, i, frameworkPackageUserState, i2, generateApplicationInfo);
                    i8++;
                }
                i7++;
            }
            packageInfo.receivers = (ActivityInfo[]) ArrayUtils.trimToSize(activityInfoArr2, i8);
        }
        if ((i & 4) != 0 && (size3 = r17.services.size()) > 0) {
            ServiceInfo[] serviceInfoArr = new ServiceInfo[size3];
            int i9 = i3;
            int i10 = i9;
            while (i9 < size3) {
                Service service = r17.services.get(i9);
                if (isMatch(frameworkPackageUserState, service.info, i)) {
                    serviceInfoArr[i10] = generateServiceInfo(service, i, frameworkPackageUserState, i2, generateApplicationInfo);
                    i10++;
                }
                i9++;
            }
            packageInfo.services = (ServiceInfo[]) ArrayUtils.trimToSize(serviceInfoArr, i10);
        }
        if ((i & 8) != 0 && (size2 = r17.providers.size()) > 0) {
            ProviderInfo[] providerInfoArr = new ProviderInfo[size2];
            int i11 = i3;
            int i12 = i11;
            while (i11 < size2) {
                Provider provider = r17.providers.get(i11);
                if (isMatch(frameworkPackageUserState, provider.info, i)) {
                    providerInfoArr[i12] = generateProviderInfo(provider, i, frameworkPackageUserState, i2, generateApplicationInfo);
                    i12++;
                }
                i11++;
            }
            packageInfo.providers = (ProviderInfo[]) ArrayUtils.trimToSize(providerInfoArr, i12);
        }
        if ((i & 16) != 0 && (size = r17.instrumentation.size()) > 0) {
            packageInfo.instrumentation = new InstrumentationInfo[size];
            for (int i13 = i3; i13 < size; i13++) {
                packageInfo.instrumentation[i13] = generateInstrumentationInfo(r17.instrumentation.get(i13), i);
            }
        }
        if ((i & 4096) != 0) {
            int size9 = r17.permissions.size();
            if (size9 > 0) {
                packageInfo.permissions = new PermissionInfo[size9];
                for (int i14 = i3; i14 < size9; i14++) {
                    packageInfo.permissions[i14] = generatePermissionInfo(r17.permissions.get(i14), i);
                }
            }
            int size10 = r17.requestedPermissions.size();
            if (size10 > 0) {
                packageInfo.requestedPermissions = new String[size10];
                packageInfo.requestedPermissionsFlags = new int[size10];
                for (int i15 = i3; i15 < size10; i15++) {
                    String str = r17.requestedPermissions.get(i15);
                    packageInfo.requestedPermissions[i15] = str;
                    int[] iArr2 = packageInfo.requestedPermissionsFlags;
                    iArr2[i15] = iArr2[i15] | 1;
                    if (set != null && set.contains(str)) {
                        int[] iArr3 = packageInfo.requestedPermissionsFlags;
                        iArr3[i15] = iArr3[i15] | 2;
                    }
                }
            }
        }
        if (apexInfo != null) {
            File file = new File(apexInfo.modulePath);
            packageInfo.applicationInfo.sourceDir = file.getPath();
            packageInfo.applicationInfo.publicSourceDir = file.getPath();
            if (apexInfo.isFactory) {
                packageInfo.applicationInfo.flags |= 1;
            } else {
                packageInfo.applicationInfo.flags &= -2;
            }
            if (apexInfo.isActive) {
                packageInfo.applicationInfo.flags |= 8388608;
            } else {
                packageInfo.applicationInfo.flags &= -8388609;
            }
            ?? r1 = r16;
            packageInfo.isApex = r1;
            i4 = r1;
        } else {
            i4 = r16;
        }
        if ((i & 64) != 0) {
            if (r17.mSigningDetails.hasPastSigningCertificates()) {
                packageInfo.signatures = new Signature[i4];
                packageInfo.signatures[i3] = r17.mSigningDetails.pastSigningCertificates[i3];
            } else if (r17.mSigningDetails.hasSignatures()) {
                int length = r17.mSigningDetails.signatures.length;
                packageInfo.signatures = new Signature[length];
                int i16 = i3;
                System.arraycopy(r17.mSigningDetails.signatures, i16, packageInfo.signatures, i16, length);
            }
        }
        if ((134217728 & i) != 0) {
            if (r17.mSigningDetails != SigningDetails.UNKNOWN) {
                packageInfo.signingInfo = new SigningInfo(new android.content.pm.SigningDetails(r17.mSigningDetails.signatures, r17.mSigningDetails.signatureSchemeVersion, r17.mSigningDetails.publicKeys, r17.mSigningDetails.pastSigningCertificates));
                return packageInfo;
            }
            packageInfo.signingInfo = null;
        }
        return packageInfo;
    }

    private static class SplitNameComparator implements Comparator<String> {
        private SplitNameComparator() {
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            if (str == null) {
                return -1;
            }
            if (str2 == null) {
                return 1;
            }
            return str.compareTo(str2);
        }
    }

    public static PackageLite parsePackageLite(File file, int i) throws PackageParserException {
        if (file.isDirectory()) {
            return parseClusterPackageLite(file, i);
        }
        return parseMonolithicPackageLite(file, i);
    }

    private static PackageLite parseMonolithicPackageLite(File file, int i) throws PackageParserException {
        Trace.traceBegin(262144L, "parseApkLite");
        ApkLite parseApkLite = parseApkLite(file, i);
        String absolutePath = file.getAbsolutePath();
        Trace.traceEnd(262144L);
        return new PackageLite(absolutePath, parseApkLite.codePath, parseApkLite, null, null, null, null, null, null);
    }

    static PackageLite parseClusterPackageLite(File file, int i) throws PackageParserException {
        String[] strArr;
        boolean[] zArr;
        String[] strArr2;
        String[] strArr3;
        String[] strArr4;
        int[] iArr;
        File[] listFiles = file.listFiles();
        if (ArrayUtils.isEmpty(listFiles)) {
            throw new PackageParserException(-100, "No packages found in split");
        }
        if (listFiles.length == 1 && listFiles[0].isDirectory()) {
            return parseClusterPackageLite(listFiles[0], i);
        }
        Trace.traceBegin(262144L, "parseApkLite");
        ArrayMap arrayMap = new ArrayMap();
        int i2 = 0;
        String str = null;
        for (File file2 : listFiles) {
            if (isApkFile(file2)) {
                ApkLite parseApkLite = parseApkLite(file2, i);
                if (str == null) {
                    str = parseApkLite.packageName;
                    i2 = parseApkLite.versionCode;
                } else {
                    if (!str.equals(parseApkLite.packageName)) {
                        throw new PackageParserException(-101, "Inconsistent package " + parseApkLite.packageName + " in " + file2 + "; expected " + str);
                    }
                    if (i2 != parseApkLite.versionCode) {
                        throw new PackageParserException(-101, "Inconsistent version " + parseApkLite.versionCode + " in " + file2 + "; expected " + i2);
                    }
                }
                if (arrayMap.put(parseApkLite.splitName, parseApkLite) != null) {
                    throw new PackageParserException(-101, "Split name " + parseApkLite.splitName + " defined more than once; most recent was " + file2);
                }
            }
        }
        Trace.traceEnd(262144L);
        ApkLite apkLite = (ApkLite) arrayMap.remove(null);
        if (apkLite == null) {
            throw new PackageParserException(-101, "Missing base APK in " + file);
        }
        int size = arrayMap.size();
        if (size > 0) {
            boolean[] zArr2 = new boolean[size];
            String[] strArr5 = new String[size];
            String[] strArr6 = new String[size];
            String[] strArr7 = new String[size];
            int[] iArr2 = new int[size];
            String[] strArr8 = (String[]) arrayMap.keySet().toArray(new String[size]);
            Arrays.sort(strArr8, sSplitNameComparator);
            for (int i3 = 0; i3 < size; i3++) {
                ApkLite apkLite2 = (ApkLite) arrayMap.get(strArr8[i3]);
                strArr5[i3] = apkLite2.usesSplitName;
                zArr2[i3] = apkLite2.isFeatureSplit;
                strArr6[i3] = apkLite2.configForSplit;
                strArr7[i3] = apkLite2.codePath;
                iArr2[i3] = apkLite2.revisionCode;
            }
            strArr = strArr8;
            strArr2 = strArr5;
            strArr3 = strArr6;
            strArr4 = strArr7;
            zArr = zArr2;
            iArr = iArr2;
        } else {
            strArr = null;
            zArr = null;
            strArr2 = null;
            strArr3 = null;
            strArr4 = null;
            iArr = null;
        }
        return new PackageLite(file.getAbsolutePath(), apkLite.codePath, apkLite, strArr, zArr, strArr2, strArr3, strArr4, iArr);
    }

    public Package parsePackage(File file, int i, boolean z) throws PackageParserException {
        if (file.isDirectory()) {
            return parseClusterPackage(file, i);
        }
        return parseMonolithicPackage(file, i);
    }

    public Package parsePackage(File file, int i) throws PackageParserException {
        return parsePackage(file, i, false);
    }

    private Package parseClusterPackage(File file, int i) throws PackageParserException {
        SplitAssetLoader defaultSplitAssetLoader;
        SparseArray<int[]> sparseArray;
        PackageLite parseClusterPackageLite = parseClusterPackageLite(file, 0);
        if (this.mOnlyCoreApps && !parseClusterPackageLite.coreApp) {
            throw new PackageParserException(-108, "Not a coreApp: " + file);
        }
        if (parseClusterPackageLite.isolatedSplits && !ArrayUtils.isEmpty(parseClusterPackageLite.splitNames)) {
            try {
                sparseArray = SplitAssetDependencyLoader.createDependenciesFromPackage(parseClusterPackageLite);
                defaultSplitAssetLoader = new SplitAssetDependencyLoader(parseClusterPackageLite, sparseArray, i);
            } catch (SplitDependencyLoader.IllegalDependencyException e) {
                throw new PackageParserException(-101, e.getMessage());
            }
        } else {
            defaultSplitAssetLoader = new DefaultSplitAssetLoader(parseClusterPackageLite, i);
            sparseArray = null;
        }
        try {
            AssetManager baseAssetManager = defaultSplitAssetLoader.getBaseAssetManager();
            File file2 = new File(parseClusterPackageLite.baseCodePath);
            Package parseBaseApk = parseBaseApk(file2, baseAssetManager, i);
            if (parseBaseApk == null) {
                throw new PackageParserException(-100, "Failed to parse base APK: " + file2);
            }
            if (!ArrayUtils.isEmpty(parseClusterPackageLite.splitNames)) {
                int length = parseClusterPackageLite.splitNames.length;
                parseBaseApk.splitNames = parseClusterPackageLite.splitNames;
                parseBaseApk.splitCodePaths = parseClusterPackageLite.splitCodePaths;
                parseBaseApk.splitRevisionCodes = parseClusterPackageLite.splitRevisionCodes;
                parseBaseApk.splitFlags = new int[length];
                parseBaseApk.splitPrivateFlags = new int[length];
                parseBaseApk.applicationInfo.splitNames = parseBaseApk.splitNames;
                parseBaseApk.applicationInfo.splitDependencies = sparseArray;
                parseBaseApk.applicationInfo.splitClassLoaderNames = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    parseSplitApk(parseBaseApk, i2, defaultSplitAssetLoader.getSplitAssetManager(i2), i);
                }
            }
            parseBaseApk.setCodePath(parseClusterPackageLite.codePath);
            parseBaseApk.setUse32bitAbi(parseClusterPackageLite.use32bitAbi);
            return parseBaseApk;
        } finally {
            IoUtils.closeQuietly(defaultSplitAssetLoader);
        }
    }

    public Package parseMonolithicPackage(File file, int i) throws PackageParserException {
        PackageLite parseMonolithicPackageLite = parseMonolithicPackageLite(file, i);
        if (this.mOnlyCoreApps && !parseMonolithicPackageLite.coreApp) {
            throw new PackageParserException(-108, "Not a coreApp: " + file);
        }
        DefaultSplitAssetLoader defaultSplitAssetLoader = new DefaultSplitAssetLoader(parseMonolithicPackageLite, i);
        try {
            try {
                Package parseBaseApk = parseBaseApk(file, defaultSplitAssetLoader.getBaseAssetManager(), i);
                parseBaseApk.setCodePath(file.getCanonicalPath());
                parseBaseApk.setUse32bitAbi(parseMonolithicPackageLite.use32bitAbi);
                return parseBaseApk;
            } catch (IOException e) {
                throw new PackageParserException(-102, "Failed to get path: " + file, e);
            }
        } finally {
            IoUtils.closeQuietly(defaultSplitAssetLoader);
        }
    }

    private Package parseBaseApk(File file, AssetManager assetManager, int i) throws PackageParserException {
        Exception exc;
        Throwable th;
        String absolutePath = file.getAbsolutePath();
        XmlResourceParser xmlResourceParser = null;
        String substring = absolutePath.startsWith("/mnt/expand/") ? absolutePath.substring(12, absolutePath.indexOf(47, 12)) : null;
        this.mParseError = 1;
        this.mArchiveSourcePath = file.getAbsolutePath();
        try {
            try {
                int findCookieForPath = assetManager.findCookieForPath(absolutePath);
                if (findCookieForPath == 0) {
                    try {
                        throw new PackageParserException(-101, "Failed adding asset path: " + absolutePath);
                    } catch (PackageParserException e) {
                        throw e;
                    } catch (Exception e2) {
                        exc = e2;
                        throw new PackageParserException(-102, "Failed to read manifest from " + absolutePath, exc);
                    } catch (Throwable th2) {
                        th = th2;
                        xmlResourceParser = null;
                        IoUtils.closeQuietly(xmlResourceParser);
                        throw th;
                    }
                }
                XmlResourceParser openXmlResourceParser = assetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
                try {
                    String[] strArr = new String[1];
                    Package parseBaseApk = parseBaseApk(absolutePath, new Resources(assetManager, this.mMetrics, null), openXmlResourceParser, i, strArr);
                    if (parseBaseApk == null) {
                        throw new PackageParserException(this.mParseError, absolutePath + " (at " + openXmlResourceParser.getPositionDescription() + "): " + strArr[0]);
                    }
                    parseBaseApk.setVolumeUuid(substring);
                    parseBaseApk.setApplicationVolumeUuid(substring);
                    parseBaseApk.setBaseCodePath(absolutePath);
                    parseBaseApk.setSigningDetails(SigningDetails.UNKNOWN);
                    IoUtils.closeQuietly(openXmlResourceParser);
                    return parseBaseApk;
                } catch (PackageParserException e3) {
                    throw e3;
                } catch (Exception e4) {
                    exc = e4;
                    throw new PackageParserException(-102, "Failed to read manifest from " + absolutePath, exc);
                } catch (Throwable th3) {
                    th = th3;
                    xmlResourceParser = openXmlResourceParser;
                    IoUtils.closeQuietly(xmlResourceParser);
                    throw th;
                }
            } catch (PackageParserException e5) {
                throw e5;
            } catch (Exception e6) {
                exc = e6;
            } catch (Throwable th4) {
                th = th4;
                th = th;
                IoUtils.closeQuietly(xmlResourceParser);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            th = th;
            IoUtils.closeQuietly(xmlResourceParser);
            throw th;
        }
    }

    private void parseSplitApk(Package r10, int i, AssetManager assetManager, int i2) throws PackageParserException {
        String str = r10.splitCodePaths[i];
        this.mParseError = 1;
        this.mArchiveSourcePath = str;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                int findCookieForPath = assetManager.findCookieForPath(str);
                if (findCookieForPath == 0) {
                    throw new PackageParserException(-101, "Failed adding asset path: " + str);
                }
                XmlResourceParser openXmlResourceParser = assetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
                try {
                    String[] strArr = new String[1];
                    if (parseSplitApk(r10, new Resources(assetManager, this.mMetrics, null), openXmlResourceParser, i2, i, strArr) == null) {
                        throw new PackageParserException(this.mParseError, str + " (at " + openXmlResourceParser.getPositionDescription() + "): " + strArr[0]);
                    }
                    IoUtils.closeQuietly(openXmlResourceParser);
                } catch (PackageParserException e) {
                } catch (Exception e2) {
                    e = e2;
                    throw new PackageParserException(-102, "Failed to read manifest from " + str, e);
                } catch (Throwable th) {
                    th = th;
                    xmlResourceParser = openXmlResourceParser;
                    IoUtils.closeQuietly(xmlResourceParser);
                    throw th;
                }
            } catch (PackageParserException e3) {
                throw e3;
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0075, code lost:
    
        r14[0] = "<manifest> does not contain an <application>";
        r8.mParseError = -109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x007d, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0073, code lost:
    
        if (r3 != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Package parseSplitApk(android.content.pm.PackageParser.Package r9, android.content.res.Resources r10, android.content.res.XmlResourceParser r11, int r12, int r13, java.lang.String[] r14) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.content.pm.PackageParser.PackageParserException {
        /*
            r8 = this;
            parsePackageSplitNames(r11, r11)
            r0 = 0
            r8.mParseInstrumentationArgs = r0
            int r1 = r11.getDepth()
            r2 = 0
            r3 = r2
        Lc:
            int r4 = r11.next()
            r5 = 1
            if (r4 == r5) goto L73
            r6 = 3
            if (r4 != r6) goto L1c
            int r7 = r11.getDepth()
            if (r7 <= r1) goto L73
        L1c:
            if (r4 == r6) goto Lc
            r6 = 4
            if (r4 != r6) goto L22
            goto Lc
        L22:
            java.lang.String r4 = r11.getName()
            java.lang.String r6 = "application"
            boolean r4 = r4.equals(r6)
            java.lang.String r6 = "PackageParser"
            if (r4 == 0) goto L44
            if (r3 == 0) goto L3b
            java.lang.String r4 = "<manifest> has more than one <application>"
            android.util.Slog.w(r6, r4)
            com.android.internal.util.XmlUtils.skipCurrentTag(r11)
            goto Lc
        L3b:
            boolean r3 = r8.parseSplitApplication(r9, r10, r11, r12, r13, r14)
            if (r3 != 0) goto L42
            return r0
        L42:
            r3 = r5
            goto Lc
        L44:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Unknown element under <manifest>: "
            r4.<init>(r5)
            java.lang.String r5 = r11.getName()
            r4.append(r5)
            java.lang.String r5 = " at "
            r4.append(r5)
            java.lang.String r5 = r8.mArchiveSourcePath
            r4.append(r5)
            java.lang.String r5 = " "
            r4.append(r5)
            java.lang.String r5 = r11.getPositionDescription()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Slog.w(r6, r4)
            com.android.internal.util.XmlUtils.skipCurrentTag(r11)
            goto Lc
        L73:
            if (r3 != 0) goto L7d
            java.lang.String r10 = "<manifest> does not contain an <application>"
            r14[r2] = r10
            r10 = -109(0xffffffffffffff93, float:NaN)
            r8.mParseError = r10
        L7d:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseSplitApk(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, int, java.lang.String[]):android.content.pm.PackageParser$Package");
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] signatureArr) throws CertificateException {
        ArraySet<PublicKey> arraySet = new ArraySet<>(signatureArr.length);
        for (Signature signature : signatureArr) {
            arraySet.add(signature.getPublicKey());
        }
        return arraySet;
    }

    public static void collectCertificates(Package r3, boolean z) throws PackageParserException {
        collectCertificatesInternal(r3, z);
        int size = r3.childPackages != null ? r3.childPackages.size() : 0;
        for (int i = 0; i < size; i++) {
            r3.childPackages.get(i).mSigningDetails = r3.mSigningDetails;
        }
    }

    private static void collectCertificatesInternal(Package r5, boolean z) throws PackageParserException {
        r5.mSigningDetails = SigningDetails.UNKNOWN;
        Trace.traceBegin(262144L, "collectCertificates");
        try {
            collectCertificates(r5, new File(r5.baseCodePath), z);
            if (!ArrayUtils.isEmpty(r5.splitCodePaths)) {
                for (int i = 0; i < r5.splitCodePaths.length; i++) {
                    collectCertificates(r5, new File(r5.splitCodePaths[i]), z);
                }
            }
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    private static void collectCertificates(Package r3, File file, boolean z) throws PackageParserException {
        ParseResult<android.content.pm.SigningDetails> verify;
        String absolutePath = file.getAbsolutePath();
        int minimumSignatureSchemeVersionForTargetSdk = ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(r3.applicationInfo.targetSdkVersion);
        if (r3.applicationInfo.isStaticSharedLibrary()) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        ParseTypeImpl forDefaultParsing = ParseTypeImpl.forDefaultParsing();
        if (z) {
            verify = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(forDefaultParsing, absolutePath, minimumSignatureSchemeVersionForTargetSdk);
        } else {
            verify = ApkSignatureVerifier.verify(forDefaultParsing, absolutePath, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (verify.isError()) {
            throw new PackageParserException(verify.getErrorCode(), verify.getErrorMessage(), verify.getException());
        }
        android.content.pm.SigningDetails result = verify.getResult();
        if (r3.mSigningDetails == SigningDetails.UNKNOWN) {
            r3.mSigningDetails = new SigningDetails(result.getSignatures(), result.getSignatureSchemeVersion(), result.getPublicKeys(), result.getPastSigningCertificates());
        } else {
            if (Signature.areExactArraysMatch(r3.mSigningDetails.signatures, result.getSignatures())) {
                return;
            }
            throw new PackageParserException(-104, absolutePath + " has mismatched certificates");
        }
    }

    private static AssetManager newConfiguredAssetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        return assetManager;
    }

    public static ApkLite parseApkLite(File file, int i) throws PackageParserException {
        return parseApkLiteInner(file, null, null, i);
    }

    public static ApkLite parseApkLite(FileDescriptor fileDescriptor, String str, int i) throws PackageParserException {
        return parseApkLiteInner(null, fileDescriptor, str, i);
    }

    private static ApkLite parseApkLiteInner(File file, FileDescriptor fileDescriptor, String str, int i) throws PackageParserException {
        ApkAssets apkAssets;
        XmlResourceParser openXml;
        SigningDetails signingDetails;
        String absolutePath = fileDescriptor != null ? str : file.getAbsolutePath();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    if (fileDescriptor != null) {
                        apkAssets = ApkAssets.loadFromFd(fileDescriptor, str, 0, null);
                    } else {
                        apkAssets = ApkAssets.loadFromPath(absolutePath);
                    }
                    try {
                        try {
                            openXml = apkAssets.openXml("AndroidManifest.xml");
                        } catch (IOException | RuntimeException | XmlPullParserException e) {
                            e = e;
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    apkAssets = null;
                }
            } catch (IOException unused) {
                throw new PackageParserException(-100, "Failed to parse " + absolutePath);
            }
        } catch (IOException | RuntimeException | XmlPullParserException e2) {
            e = e2;
            apkAssets = null;
        }
        try {
            try {
                if ((i & 32) != 0) {
                    Package r4 = new Package((String) null);
                    boolean z = (i & 16) != 0;
                    Trace.traceBegin(262144L, "collectCertificates");
                    try {
                        collectCertificates(r4, file, z);
                        Trace.traceEnd(262144L);
                        signingDetails = r4.mSigningDetails;
                    } catch (Throwable th3) {
                        Trace.traceEnd(262144L);
                        throw th3;
                    }
                } else {
                    signingDetails = SigningDetails.UNKNOWN;
                }
                ApkLite parseApkLite = parseApkLite(absolutePath, openXml, openXml, signingDetails);
                IoUtils.closeQuietly(openXml);
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused2) {
                    }
                }
                return parseApkLite;
            } catch (Throwable th4) {
                th = th4;
                xmlResourceParser = openXml;
                IoUtils.closeQuietly(xmlResourceParser);
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused3) {
                    }
                }
                throw th;
            }
        } catch (IOException | RuntimeException | XmlPullParserException e3) {
            e = e3;
            xmlResourceParser = openXml;
            Slog.w(TAG, "Failed to parse " + absolutePath, e);
            throw new PackageParserException(-102, "Failed to parse " + absolutePath, e);
        }
    }

    public static String validateName(String str, boolean z, boolean z2) {
        int length = str.length();
        boolean z3 = false;
        boolean z4 = true;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                z4 = false;
            } else if (z4 || ((charAt < '0' || charAt > '9') && charAt != '_')) {
                if (charAt != '.') {
                    return "bad character '" + charAt + "'";
                }
                z3 = true;
                z4 = true;
            }
        }
        if (z2 && !FileUtils.isValidExtFilename(str)) {
            return "Invalid filename";
        }
        if (z3 || !z) {
            return null;
        }
        return "must have at least one '.' separator";
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x007c  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.util.Pair<java.lang.String, java.lang.String> parsePackageSplitNames(org.xmlpull.v1.XmlPullParser r4, android.util.AttributeSet r5) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.content.pm.PackageParser.PackageParserException {
        /*
        L0:
            int r0 = r4.next()
            r1 = 2
            r2 = 1
            if (r0 == r1) goto Lb
            if (r0 == r2) goto Lb
            goto L0
        Lb:
            r3 = -108(0xffffffffffffff94, float:NaN)
            if (r0 != r1) goto L8d
            java.lang.String r4 = r4.getName()
            java.lang.String r0 = "manifest"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L85
            java.lang.String r4 = "package"
            r0 = 0
            java.lang.String r4 = r5.getAttributeValue(r0, r4)
            java.lang.String r1 = "android"
            boolean r1 = r1.equals(r4)
            r3 = -106(0xffffffffffffff96, float:NaN)
            if (r1 != 0) goto L49
            java.lang.String r1 = validateName(r4, r2, r2)
            if (r1 != 0) goto L35
            goto L49
        L35:
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "Invalid manifest package: "
            r5.<init>(r0)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            r4.<init>(r3, r5)
            throw r4
        L49:
            java.lang.String r1 = "split"
            java.lang.String r5 = r5.getAttributeValue(r0, r1)
            if (r5 == 0) goto L75
            int r1 = r5.length()
            if (r1 != 0) goto L59
            goto L76
        L59:
            r0 = 0
            java.lang.String r0 = validateName(r5, r0, r0)
            if (r0 != 0) goto L61
            goto L75
        L61:
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r1 = "Invalid manifest split: "
            r5.<init>(r1)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            r4.<init>(r3, r5)
            throw r4
        L75:
            r0 = r5
        L76:
            java.lang.String r4 = r4.intern()
            if (r0 == 0) goto L80
            java.lang.String r0 = r0.intern()
        L80:
            android.util.Pair r4 = android.util.Pair.create(r4, r0)
            return r4
        L85:
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException
            java.lang.String r5 = "No <manifest> tag"
            r4.<init>(r3, r5)
            throw r4
        L8d:
            android.content.pm.PackageParser$PackageParserException r4 = new android.content.pm.PackageParser$PackageParserException
            java.lang.String r5 = "No start tag found"
            r4.<init>(r3, r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parsePackageSplitNames(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet):android.util.Pair");
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0236, code lost:
    
        r0 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x023c, code lost:
    
        if (checkRequiredSystemProperties(r4, r0) != false) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x023e, code lost:
    
        r2 = new java.lang.StringBuilder("Skipping target and overlay pair ");
        r2.append(r7);
        r2.append(" and ");
        r3 = r35;
        r2.append(r3);
        r2.append(": overlay ignored due to required system property: ");
        r2.append(r4);
        r2.append(" with value: ");
        r2.append(r0);
        android.util.Slog.i(android.content.pm.PackageParser.TAG, r2.toString());
        r28 = null;
        r29 = false;
        r30 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x028e, code lost:
    
        return new android.content.pm.PackageParser.ApkLite(r3, r1.first, r1.second, r10, r11, r12, r13, r14, r15, r16, r17, r6, r38, r20, r21, false, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0270, code lost:
    
        r3 = r35;
        r28 = r7;
        r30 = r9;
        r29 = r22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.PackageParser.ApkLite parseApkLite(java.lang.String r35, org.xmlpull.v1.XmlPullParser r36, android.util.AttributeSet r37, android.content.pm.PackageParser.SigningDetails r38) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.content.pm.PackageParser.PackageParserException {
        /*
            Method dump skipped, instructions count: 655
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseApkLite(java.lang.String, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.pm.PackageParser$SigningDetails):android.content.pm.PackageParser$ApkLite");
    }

    private boolean parseBaseApkChild(Package r10, Resources resources, XmlResourceParser xmlResourceParser, int i, String[] strArr) throws XmlPullParserException, IOException {
        String attributeValue = xmlResourceParser.getAttributeValue(null, "package");
        if (validateName(attributeValue, true, false) != null) {
            this.mParseError = -106;
            return false;
        }
        if (attributeValue.equals(r10.packageName)) {
            String str = "Child package name cannot be equal to parent package name: " + r10.packageName;
            Slog.w(TAG, str);
            strArr[0] = str;
            this.mParseError = -108;
            return false;
        }
        if (r10.hasChildPackage(attributeValue)) {
            String str2 = "Duplicate child package:" + attributeValue;
            Slog.w(TAG, str2);
            strArr[0] = str2;
            this.mParseError = -108;
            return false;
        }
        Package r2 = new Package(attributeValue);
        r2.mVersionCode = r10.mVersionCode;
        r2.baseRevisionCode = r10.baseRevisionCode;
        r2.mVersionName = r10.mVersionName;
        r2.applicationInfo.targetSdkVersion = r10.applicationInfo.targetSdkVersion;
        r2.applicationInfo.minSdkVersion = r10.applicationInfo.minSdkVersion;
        Package parseBaseApkCommon = parseBaseApkCommon(r2, CHILD_PACKAGE_TAGS, resources, xmlResourceParser, i, strArr);
        if (parseBaseApkCommon == null) {
            return false;
        }
        if (r10.childPackages == null) {
            r10.childPackages = new ArrayList<>();
        }
        r10.childPackages.add(parseBaseApkCommon);
        parseBaseApkCommon.parentPackage = r10;
        return true;
    }

    private Package parseBaseApk(String str, Resources resources, XmlResourceParser xmlResourceParser, int i, String[] strArr) throws XmlPullParserException, IOException {
        try {
            Pair<String, String> parsePackageSplitNames = parsePackageSplitNames(xmlResourceParser, xmlResourceParser);
            String str2 = parsePackageSplitNames.first;
            String str3 = parsePackageSplitNames.second;
            if (!TextUtils.isEmpty(str3)) {
                strArr[0] = "Expected base APK, but found split " + str3;
                this.mParseError = -106;
                return null;
            }
            Package r1 = new Package(str2);
            TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifest);
            r1.mVersionCode = obtainAttributes.getInteger(1, 0);
            r1.mVersionCodeMajor = obtainAttributes.getInteger(11, 0);
            r1.applicationInfo.setVersionCode(r1.getLongVersionCode());
            r1.baseRevisionCode = obtainAttributes.getInteger(5, 0);
            r1.mVersionName = obtainAttributes.getNonConfigurationString(2, 0);
            if (r1.mVersionName != null) {
                r1.mVersionName = r1.mVersionName.intern();
            }
            r1.coreApp = xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false);
            if (obtainAttributes.getBoolean(6, false)) {
                r1.applicationInfo.privateFlags |= 32768;
            }
            r1.mCompileSdkVersion = obtainAttributes.getInteger(9, 0);
            r1.applicationInfo.compileSdkVersion = r1.mCompileSdkVersion;
            r1.mCompileSdkVersionCodename = obtainAttributes.getNonConfigurationString(10, 0);
            if (r1.mCompileSdkVersionCodename != null) {
                r1.mCompileSdkVersionCodename = r1.mCompileSdkVersionCodename.intern();
            }
            r1.applicationInfo.compileSdkVersionCodename = r1.mCompileSdkVersionCodename;
            obtainAttributes.recycle();
            return parseBaseApkCommon(r1, null, resources, xmlResourceParser, i, strArr);
        } catch (PackageParserException unused) {
            this.mParseError = -106;
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:139:0x01c9, code lost:
    
        r35[0] = "<overlay> priority must be between 0 and 9999";
        r29.mParseError = -108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01d1, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x061c, code lost:
    
        r9 = r4;
        r27 = r7;
        r26 = r14;
        r14 = r5;
        com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils.applyRuntimeManifestIfNeeded(r1, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0625, code lost:
    
        if (r26 != false) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x062d, code lost:
    
        if (r1.instrumentation.size() != 0) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x062f, code lost:
    
        r19 = 0;
        r35[0] = "<manifest> does not contain an <application> or <instrumentation>";
        r29.mParseError = -109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x063c, code lost:
    
        r2 = android.content.pm.PackageParser.NEW_PERMISSIONS.length;
        r7 = r16;
        r6 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0643, code lost:
    
        if (r6 >= r2) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0645, code lost:
    
        r3 = android.content.pm.PackageParser.NEW_PERMISSIONS[r6];
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x064f, code lost:
    
        if (r1.applicationInfo.targetSdkVersion < r3.sdkVersion) goto L259;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x065a, code lost:
    
        if (r1.requestedPermissions.contains(r3.name) != false) goto L344;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x065c, code lost:
    
        if (r7 != 0) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x065e, code lost:
    
        r7 = new java.lang.StringBuilder(128);
        r7.append(r1.packageName);
        r7.append(": compat added ");
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0675, code lost:
    
        r7.append(r3.name);
        r1.requestedPermissions.add(r3.name);
        r1.implicitPermissions.add(r3.name);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0688, code lost:
    
        r6 = r6 + 1;
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0670, code lost:
    
        r7.append(' ');
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x068b, code lost:
    
        if (r7 == 0) goto L320;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x068d, code lost:
    
        android.util.Slog.i(android.content.pm.PackageParser.TAG, r7.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0694, code lost:
    
        r2 = android.app.ActivityThread.getPermissionManager().getSplitPermissions();
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x069d, code lost:
    
        r2 = java.util.Collections.EMPTY_LIST;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x063a, code lost:
    
        r19 = 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:237:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x03d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x03ca  */
    /* JADX WARN: Type inference failed for: r10v17 */
    /* JADX WARN: Type inference failed for: r10v18, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v37 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v17 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r4v69, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v28, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v51 */
    /* JADX WARN: Type inference failed for: r7v52 */
    /* JADX WARN: Type inference failed for: r7v53 */
    /* JADX WARN: Type inference failed for: r7v57 */
    /* JADX WARN: Type inference failed for: r7v58 */
    /* JADX WARN: Type inference failed for: r7v59 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Package parseBaseApkCommon(android.content.pm.PackageParser.Package r30, java.util.Set<java.lang.String> r31, android.content.res.Resources r32, android.content.res.XmlResourceParser r33, int r34, java.lang.String[] r35) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1935
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseBaseApkCommon(android.content.pm.PackageParser$Package, java.util.Set, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[]):android.content.pm.PackageParser$Package");
    }

    public static boolean checkRequiredSystemProperties(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
                return true;
            }
            Slog.w(TAG, "Disabling overlay - incomplete property :'" + str + "=" + str2 + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
            return false;
        }
        String[] split = str.split(",");
        String[] split2 = str2.split(",");
        if (split.length != split2.length) {
            Slog.w(TAG, "Disabling overlay - property :'" + str + "=" + str2 + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue lists to have the same size.");
            return false;
        }
        for (int i = 0; i < split.length; i++) {
            if (!TextUtils.equals(SystemProperties.get(split[i]), split2[i])) {
                return false;
            }
        }
        return true;
    }

    private void adjustPackageToBeUnresizeableAndUnpipable(Package r3) {
        Iterator<Activity> it = r3.activities.iterator();
        while (it.hasNext()) {
            Activity next = it.next();
            next.info.resizeMode = 0;
            next.info.flags &= -4194305;
        }
    }

    private static boolean matchTargetCode(String[] strArr, String str) {
        int indexOf = str.indexOf(46);
        if (indexOf != -1) {
            str = str.substring(0, indexOf);
        }
        return ArrayUtils.contains(strArr, str);
    }

    public static int computeTargetSdkVersion(int i, String str, String[] strArr, String[] strArr2) {
        if (str == null) {
            return i;
        }
        if (matchTargetCode(strArr, str)) {
            return 10000;
        }
        if (strArr.length > 0) {
            strArr2[0] = "Requires development platform " + str + " (current platform is any of " + Arrays.toString(strArr) + NavigationBarInflaterView.KEY_CODE_END;
            return -1;
        }
        strArr2[0] = "Requires development platform " + str + " but this is a release platform.";
        return -1;
    }

    public static int computeMinSdkVersion(int i, String str, int i2, String[] strArr, String[] strArr2) {
        if (str == null) {
            if (i <= i2) {
                return i;
            }
            strArr2[0] = "Requires newer sdk version #" + i + " (current version is #" + i2 + NavigationBarInflaterView.KEY_CODE_END;
            return -1;
        }
        if (matchTargetCode(strArr, str)) {
            return 10000;
        }
        if (strArr.length > 0) {
            strArr2[0] = "Requires development platform " + str + " (current platform is any of " + Arrays.toString(strArr) + NavigationBarInflaterView.KEY_CODE_END;
        } else {
            strArr2[0] = "Requires development platform " + str + " but this is a release platform.";
        }
        return -1;
    }

    private FeatureInfo parseUsesFeature(Resources resources, AttributeSet attributeSet) {
        FeatureInfo featureInfo = new FeatureInfo();
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestUsesFeature);
        featureInfo.name = obtainAttributes.getNonResourceString(0);
        featureInfo.version = obtainAttributes.getInt(3, 0);
        if (featureInfo.name == null) {
            featureInfo.reqGlEsVersion = obtainAttributes.getInt(1, 0);
        }
        if (obtainAttributes.getBoolean(2, true)) {
            featureInfo.flags |= 1;
        }
        obtainAttributes.recycle();
        return featureInfo;
    }

    private boolean parseUsesStaticLibrary(Package r9, Resources resources, XmlResourceParser xmlResourceParser, String[] strArr) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesStaticLibrary);
        String nonResourceString = obtainAttributes.getNonResourceString(0);
        int i = obtainAttributes.getInt(1, -1);
        String nonResourceString2 = obtainAttributes.getNonResourceString(2);
        obtainAttributes.recycle();
        if (nonResourceString == null || i < 0 || nonResourceString2 == null) {
            strArr[0] = "Bad uses-static-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2;
            this.mParseError = -108;
            XmlUtils.skipCurrentTag(xmlResourceParser);
            return false;
        }
        if (r9.usesStaticLibraries != null && r9.usesStaticLibraries.contains(nonResourceString)) {
            strArr[0] = "Depending on multiple versions of static library " + nonResourceString;
            this.mParseError = -108;
            XmlUtils.skipCurrentTag(xmlResourceParser);
            return false;
        }
        String intern = nonResourceString.intern();
        String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
        String[] strArr2 = EmptyArray.STRING;
        if (r9.applicationInfo.targetSdkVersion >= 27) {
            strArr2 = parseAdditionalCertificates(resources, xmlResourceParser, strArr);
            if (strArr2 == null) {
                return false;
            }
        } else {
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        String[] strArr3 = new String[strArr2.length + 1];
        strArr3[0] = lowerCase;
        System.arraycopy(strArr2, 0, strArr3, 1, strArr2.length);
        r9.usesStaticLibraries = ArrayUtils.add(r9.usesStaticLibraries, intern);
        r9.usesStaticLibrariesVersions = ArrayUtils.appendLong(r9.usesStaticLibrariesVersions, i, true);
        r9.usesStaticLibrariesCertDigests = (String[][]) ArrayUtils.appendElement(String[].class, r9.usesStaticLibrariesCertDigests, strArr3, true);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0071, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String[] parseAdditionalCertificates(android.content.res.Resources r7, android.content.res.XmlResourceParser r8, java.lang.String[] r9) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r6 = this;
            java.lang.String[] r0 = libcore.util.EmptyArray.STRING
            int r1 = r8.getDepth()
        L6:
            int r2 = r8.next()
            r3 = 1
            if (r2 == r3) goto L71
            r3 = 3
            if (r2 != r3) goto L16
            int r4 = r8.getDepth()
            if (r4 <= r1) goto L71
        L16:
            if (r2 == r3) goto L6
            r3 = 4
            if (r2 != r3) goto L1c
            goto L6
        L1c:
            java.lang.String r2 = r8.getName()
            java.lang.String r3 = "additional-certificate"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L6d
            int[] r2 = com.android.internal.R.styleable.AndroidManifestAdditionalCertificate
            android.content.res.TypedArray r2 = r7.obtainAttributes(r8, r2)
            r3 = 0
            java.lang.String r4 = r2.getNonResourceString(r3)
            r2.recycle()
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L58
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "Bad additional-certificate declaration with empty certDigest:"
            r7.<init>(r0)
            r7.append(r4)
            java.lang.String r7 = r7.toString()
            r9[r3] = r7
            r7 = -108(0xffffffffffffff94, float:NaN)
            r6.mParseError = r7
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)
            r2.recycle()
            r6 = 0
            return r6
        L58:
            java.lang.String r2 = ":"
            java.lang.String r3 = ""
            java.lang.String r2 = r4.replace(r2, r3)
            java.lang.String r2 = r2.toLowerCase()
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            java.lang.Object[] r0 = com.android.internal.util.ArrayUtils.appendElement(r3, r0, r2)
            java.lang.String[] r0 = (java.lang.String[]) r0
            goto L6
        L6d:
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)
            goto L6
        L71:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseAdditionalCertificates(android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):java.lang.String[]");
    }

    private boolean parseUsesPermission(Package r6, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        Callback callback;
        Callback callback2;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesPermission);
        String nonResourceString = obtainAttributes.getNonResourceString(0);
        TypedValue peekValue = obtainAttributes.peekValue(2);
        int i = (peekValue == null || peekValue.type < 16 || peekValue.type > 31) ? 0 : peekValue.data;
        String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
        String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(4, 0);
        obtainAttributes.recycle();
        XmlUtils.skipCurrentTag(xmlResourceParser);
        if (nonResourceString == null) {
            return true;
        }
        if (i != 0 && i < Build.VERSION.RESOURCES_SDK_INT) {
            return true;
        }
        if (nonConfigurationString != null && (callback2 = this.mCallback) != null && !callback2.hasFeature(nonConfigurationString)) {
            return true;
        }
        if (nonConfigurationString2 != null && (callback = this.mCallback) != null && callback.hasFeature(nonConfigurationString2)) {
            return true;
        }
        if (r6.requestedPermissions.indexOf(nonResourceString) == -1) {
            r6.requestedPermissions.add(nonResourceString.intern());
        } else {
            Slog.w(TAG, "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + nonResourceString + " in package: " + r6.packageName + " at: " + xmlResourceParser.getPositionDescription());
        }
        return true;
    }

    public static String buildClassName(String str, CharSequence charSequence, String[] strArr) {
        if (charSequence == null || charSequence.length() <= 0) {
            strArr[0] = "Empty class name in package " + str;
            return null;
        }
        String charSequence2 = charSequence.toString();
        if (charSequence2.charAt(0) == '.') {
            return str + charSequence2;
        }
        if (charSequence2.indexOf(46) >= 0) {
            return charSequence2;
        }
        return str + '.' + charSequence2;
    }

    private static String buildCompoundName(String str, CharSequence charSequence, String str2, String[] strArr) {
        String charSequence2 = charSequence.toString();
        char charAt = charSequence2.charAt(0);
        if (str != null && charAt == ':') {
            if (charSequence2.length() < 2) {
                strArr[0] = "Bad " + str2 + " name " + charSequence2 + " in package " + str + ": must be at least two characters";
                return null;
            }
            String validateName = validateName(charSequence2.substring(1), false, false);
            if (validateName != null) {
                strArr[0] = "Invalid " + str2 + " name " + charSequence2 + " in package " + str + ": " + validateName;
                return null;
            }
            return str + charSequence2;
        }
        String validateName2 = validateName(charSequence2, true, false);
        if (validateName2 == null || "system".equals(charSequence2)) {
            return charSequence2;
        }
        strArr[0] = "Invalid " + str2 + " name " + charSequence2 + " in package " + str + ": " + validateName2;
        return null;
    }

    public static String buildProcessName(String str, String str2, CharSequence charSequence, int i, String[] strArr, String[] strArr2) {
        if ((i & 2) != 0 && !"system".equals(charSequence)) {
            return str2 != null ? str2 : str;
        }
        if (strArr != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                String str3 = strArr[length];
                if (!str3.equals(str) && !str3.equals(str2) && !str3.equals(charSequence)) {
                }
            }
        }
        if (charSequence != null && charSequence.length() > 0) {
            return TextUtils.safeIntern(buildCompoundName(str, charSequence, "process", strArr2));
        }
    }

    public static String buildTaskAffinityName(String str, String str2, CharSequence charSequence, String[] strArr) {
        if (charSequence == null) {
            return str2;
        }
        if (charSequence.length() <= 0) {
            return null;
        }
        return buildCompoundName(str, charSequence, "taskAffinity", strArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x01bd, code lost:
    
        if (r5.keySet().removeAll(r7.keySet()) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x01bf, code lost:
    
        r22[0] = "Package" + r19.packageName + " AndroidManifext.xml 'key-set' and 'public-key' names must be distinct.";
        r18.mParseError = -108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x01d8, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x01d9, code lost:
    
        r19.mKeySetMapping = new android.util.ArrayMap<>();
        r2 = r7.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x01ec, code lost:
    
        if (r2.hasNext() == false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x01ee, code lost:
    
        r4 = (java.util.Map.Entry) r2.next();
        r7 = (java.lang.String) r4.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0206, code lost:
    
        if (((android.util.ArraySet) r4.getValue()).size() != 0) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0229, code lost:
    
        if (r8.contains(r7) == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0248, code lost:
    
        r19.mKeySetMapping.put(r7, new android.util.ArraySet<>());
        r4 = ((android.util.ArraySet) r4.getValue()).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0260, code lost:
    
        if (r4.hasNext() == false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0262, code lost:
    
        r19.mKeySetMapping.get(r7).add((java.security.PublicKey) r5.get((java.lang.String) r4.next()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x022b, code lost:
    
        android.util.Slog.w(android.content.pm.PackageParser.TAG, "Package" + r19.packageName + " AndroidManifext.xml 'key-set' " + r7 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0208, code lost:
    
        android.util.Slog.w(android.content.pm.PackageParser.TAG, "Package" + r19.packageName + " AndroidManifext.xml 'key-set' " + r7 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0284, code lost:
    
        if (r19.mKeySetMapping.keySet().containsAll(r6) == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0286, code lost:
    
        r19.mUpgradeKeySets = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x028a, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x028b, code lost:
    
        r22[0] = "Package" + r19.packageName + " AndroidManifext.xml does not define all 'upgrade-key-set's .";
        r18.mParseError = -108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x02a4, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseKeySets(android.content.pm.PackageParser.Package r19, android.content.res.Resources r20, android.content.res.XmlResourceParser r21, java.lang.String[] r22) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 677
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseKeySets(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):boolean");
    }

    private boolean parsePermissionGroup(Package r17, int i, Resources resources, XmlResourceParser xmlResourceParser, String[] strArr) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionGroup);
        PermissionGroup permissionGroup = new PermissionGroup(r17, obtainAttributes.getResourceId(12, 0), obtainAttributes.getResourceId(9, 0), obtainAttributes.getResourceId(10, 0));
        if (!parsePackageItemInfo(r17, permissionGroup.info, strArr, "<permission-group>", obtainAttributes, true, 2, 0, 1, 8, 5, 7)) {
            obtainAttributes.recycle();
            this.mParseError = -108;
            return false;
        }
        permissionGroup.info.descriptionRes = obtainAttributes.getResourceId(4, 0);
        permissionGroup.info.requestRes = obtainAttributes.getResourceId(11, 0);
        permissionGroup.info.flags = obtainAttributes.getInt(6, 0);
        permissionGroup.info.priority = obtainAttributes.getInt(3, 0);
        obtainAttributes.recycle();
        if (!parseAllMetaData(resources, xmlResourceParser, "<permission-group>", permissionGroup, strArr)) {
            this.mParseError = -108;
            return false;
        }
        r17.permissionGroups.add(permissionGroup);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parsePermission(android.content.pm.PackageParser.Package r17, android.content.res.Resources r18, android.content.res.XmlResourceParser r19, java.lang.String[] r20) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parsePermission(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):boolean");
    }

    private boolean parsePermissionTree(Package r17, Resources resources, XmlResourceParser xmlResourceParser, String[] strArr) throws XmlPullParserException, IOException {
        Permission permission = new Permission(r17, (String) null);
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionTree);
        if (!parsePackageItemInfo(r17, permission.info, strArr, "<permission-tree>", obtainAttributes, true, 2, 0, 1, 5, 3, 4)) {
            obtainAttributes.recycle();
            this.mParseError = -108;
            return false;
        }
        obtainAttributes.recycle();
        int indexOf = permission.info.name.indexOf(46);
        if (indexOf > 0) {
            indexOf = permission.info.name.indexOf(46, indexOf + 1);
        }
        if (indexOf < 0) {
            strArr[0] = "<permission-tree> name has less than three segments: " + permission.info.name;
            this.mParseError = -108;
            return false;
        }
        permission.info.descriptionRes = 0;
        permission.info.requestRes = 0;
        permission.info.protectionLevel = 0;
        permission.tree = true;
        if (!parseAllMetaData(resources, xmlResourceParser, "<permission-tree>", permission, strArr)) {
            this.mParseError = -108;
            return false;
        }
        r17.permissions.add(permission);
        return true;
    }

    private Instrumentation parseInstrumentation(Package r12, Resources resources, XmlResourceParser xmlResourceParser, String[] strArr) throws XmlPullParserException, IOException {
        Package r3;
        String[] strArr2;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestInstrumentation);
        if (this.mParseInstrumentationArgs == null) {
            r3 = r12;
            strArr2 = strArr;
            ParsePackageItemArgs parsePackageItemArgs = new ParsePackageItemArgs(r3, strArr2, 2, 0, 1, 8, 6, 7);
            this.mParseInstrumentationArgs = parsePackageItemArgs;
            parsePackageItemArgs.tag = "<instrumentation>";
        } else {
            r3 = r12;
            strArr2 = strArr;
        }
        this.mParseInstrumentationArgs.sa = obtainAttributes;
        Instrumentation instrumentation = new Instrumentation(this.mParseInstrumentationArgs, new InstrumentationInfo());
        if (strArr2[0] != null) {
            obtainAttributes.recycle();
            this.mParseError = -108;
            return null;
        }
        String nonResourceString = obtainAttributes.getNonResourceString(3);
        instrumentation.info.targetPackage = nonResourceString != null ? nonResourceString.intern() : null;
        String nonResourceString2 = obtainAttributes.getNonResourceString(9);
        instrumentation.info.targetProcesses = nonResourceString2 != null ? nonResourceString2.intern() : null;
        instrumentation.info.handleProfiling = obtainAttributes.getBoolean(4, false);
        instrumentation.info.functionalTest = obtainAttributes.getBoolean(5, false);
        obtainAttributes.recycle();
        if (instrumentation.info.targetPackage == null) {
            strArr2[0] = "<instrumentation> does not specify targetPackage";
            this.mParseError = -108;
            return null;
        }
        if (!parseAllMetaData(resources, xmlResourceParser, "<instrumentation>", instrumentation, strArr2)) {
            this.mParseError = -108;
            return null;
        }
        r3.instrumentation.add(instrumentation);
        return instrumentation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:182:0x0693, code lost:
    
        r1 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x069c, code lost:
    
        if (android.text.TextUtils.isEmpty(r1.staticSharedLibName) == false) goto L314;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x069e, code lost:
    
        r1.activities.add(generateAppDetailsHiddenActivity(r1, r30, r31, r1.baseHardwareAccelerated));
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x06ab, code lost:
    
        if (r16 == false) goto L316;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x06ad, code lost:
    
        java.util.Collections.sort(r1.activities, new android.content.pm.PackageParser$$ExternalSyntheticLambda0());
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x06b7, code lost:
    
        if (r18 == false) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x06b9, code lost:
    
        java.util.Collections.sort(r1.receivers, new android.content.pm.PackageParser$$ExternalSyntheticLambda1());
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x06c3, code lost:
    
        if (r22 == false) goto L320;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x06c5, code lost:
    
        java.util.Collections.sort(r1.services, new android.content.pm.PackageParser$$ExternalSyntheticLambda2());
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x06cf, code lost:
    
        setMaxAspectRatio(r27);
        setMinAspectRatio(r27);
        setSupportsSizeChanges(r27);
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x06dc, code lost:
    
        if (hasDomainURLs(r1) == false) goto L323;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x06de, code lost:
    
        r1.applicationInfo.privateFlags |= 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x06ef, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x06e7, code lost:
    
        r1.applicationInfo.privateFlags &= -17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x058a, code lost:
    
        r31[0] = "Bad static-library declaration name: " + r7 + " version: " + r8;
        r26.mParseError = -108;
        com.android.internal.util.XmlUtils.skipCurrentTag(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x05a9, code lost:
    
        return false;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0386  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseBaseApplication(android.content.pm.PackageParser.Package r27, android.content.res.Resources r28, android.content.res.XmlResourceParser r29, int r30, java.lang.String[] r31) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1776
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseBaseApplication(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[]):boolean");
    }

    private static boolean hasDomainURLs(Package r9) {
        if (r9 != null && r9.activities != null) {
            ArrayList<Activity> arrayList = r9.activities;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ArrayList<II> arrayList2 = arrayList.get(i).intents;
                if (arrayList2 != 0) {
                    int size2 = arrayList2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ActivityIntentInfo activityIntentInfo = (ActivityIntentInfo) arrayList2.get(i2);
                        if (activityIntentInfo.hasAction("android.intent.action.VIEW") && activityIntentInfo.hasAction("android.intent.action.VIEW") && (activityIntentInfo.hasDataScheme(IntentFilter.SCHEME_HTTP) || activityIntentInfo.hasDataScheme(IntentFilter.SCHEME_HTTPS))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x01e8, code lost:
    
        return true;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [android.content.pm.PackageParser-IA] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v4, types: [android.content.pm.ComponentInfo] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseSplitApplication(android.content.pm.PackageParser.Package r15, android.content.res.Resources r16, android.content.res.XmlResourceParser r17, int r18, int r19, java.lang.String[] r20) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseSplitApplication(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, int, java.lang.String[]):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean parsePackageItemInfo(Package r1, PackageItemInfo packageItemInfo, String[] strArr, String str, TypedArray typedArray, boolean z, int i, int i2, int i3, int i4, int i5, int i6) {
        if (typedArray == null) {
            strArr[0] = str + " does not contain any attributes";
            return false;
        }
        String nonConfigurationString = typedArray.getNonConfigurationString(i, 0);
        if (nonConfigurationString != null) {
            String buildClassName = buildClassName(r1.applicationInfo.packageName, nonConfigurationString, strArr);
            if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
                strArr[0] = str + " invalid android:name";
                return false;
            }
            packageItemInfo.name = buildClassName;
            if (buildClassName == null) {
                return false;
            }
        } else if (z) {
            strArr[0] = str + " does not specify android:name";
            return false;
        }
        int resourceId = sUseRoundIcon ? typedArray.getResourceId(i4, 0) : 0;
        if (resourceId != 0) {
            packageItemInfo.icon = resourceId;
            packageItemInfo.nonLocalizedLabel = null;
        } else {
            int resourceId2 = typedArray.getResourceId(i3, 0);
            if (resourceId2 != 0) {
                packageItemInfo.icon = resourceId2;
                packageItemInfo.nonLocalizedLabel = null;
            }
        }
        int resourceId3 = typedArray.getResourceId(i5, 0);
        if (resourceId3 != 0) {
            packageItemInfo.logo = resourceId3;
        }
        int resourceId4 = typedArray.getResourceId(i6, 0);
        if (resourceId4 != 0) {
            packageItemInfo.banner = resourceId4;
        }
        TypedValue peekValue = typedArray.peekValue(i2);
        if (peekValue != null) {
            int i7 = peekValue.resourceId;
            packageItemInfo.labelRes = i7;
            if (i7 == 0) {
                packageItemInfo.nonLocalizedLabel = peekValue.coerceToString();
            }
        }
        packageItemInfo.packageName = r1.packageName;
        return true;
    }

    private Activity generateAppDetailsHiddenActivity(Package r4, int i, String[] strArr, boolean z) {
        Activity activity = new Activity(r4, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, new ActivityInfo());
        activity.owner = r4;
        activity.setPackageName(r4.packageName);
        activity.info.theme = 16973909;
        activity.info.exported = true;
        activity.info.name = PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME;
        activity.info.processName = r4.applicationInfo.processName;
        activity.info.uiOptions = activity.info.applicationInfo.uiOptions;
        activity.info.taskAffinity = buildTaskAffinityName(r4.packageName, r4.packageName, ":app_details", strArr);
        activity.info.enabled = true;
        activity.info.launchMode = 0;
        activity.info.documentLaunchMode = 0;
        activity.info.maxRecents = ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        activity.info.configChanges = getActivityConfigChanges(0, 0);
        activity.info.softInputMode = 0;
        activity.info.persistableMode = 1;
        activity.info.screenOrientation = -1;
        activity.info.resizeMode = 4;
        activity.info.lockTaskLaunchMode = 0;
        activity.info.directBootAware = false;
        activity.info.rotationAnimation = -1;
        activity.info.colorMode = 0;
        if (z) {
            activity.info.flags |= 512;
        }
        return activity;
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x0588, code lost:
    
        resolveWindowLayout(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x058b, code lost:
    
        if (r11 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x058d, code lost:
    
        r0 = r8.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0595, code lost:
    
        if (r8.intents.size() <= 0) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0597, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x059a, code lost:
    
        r0.exported = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0599, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x059c, code lost:
    
        return r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Activity parseActivity(android.content.pm.PackageParser.Package r19, android.content.res.Resources r20, android.content.res.XmlResourceParser r21, int r22, java.lang.String[] r23, android.content.pm.PackageParser.CachedComponentArgs r24, boolean r25, boolean r26) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseActivity(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[], android.content.pm.PackageParser$CachedComponentArgs, boolean, boolean):android.content.pm.PackageParser$Activity");
    }

    private void setActivityResizeMode(ActivityInfo activityInfo, TypedArray typedArray, Package r7) {
        boolean z = (r7.applicationInfo.privateFlags & 3072) != 0;
        if (typedArray.hasValue(40) || z) {
            if (typedArray.getBoolean(40, (r7.applicationInfo.privateFlags & 1024) != 0)) {
                activityInfo.resizeMode = 2;
                return;
            } else {
                activityInfo.resizeMode = 0;
                return;
            }
        }
        if ((r7.applicationInfo.privateFlags & 4096) != 0) {
            activityInfo.resizeMode = 1;
            return;
        }
        if (activityInfo.isFixedOrientationPortrait()) {
            activityInfo.resizeMode = 6;
            return;
        }
        if (activityInfo.isFixedOrientationLandscape()) {
            activityInfo.resizeMode = 5;
        } else if (activityInfo.isFixedOrientation()) {
            activityInfo.resizeMode = 7;
        } else {
            activityInfo.resizeMode = 4;
        }
    }

    private void setMaxAspectRatio(Package r4) {
        float f = r4.applicationInfo.targetSdkVersion < 26 ? 1.86f : 0.0f;
        if (r4.applicationInfo.maxAspectRatio != 0.0f) {
            f = r4.applicationInfo.maxAspectRatio;
        } else if (r4.mAppMetaData != null && r4.mAppMetaData.containsKey("android.max_aspect")) {
            f = r4.mAppMetaData.getFloat("android.max_aspect", f);
        }
        Iterator<Activity> it = r4.activities.iterator();
        while (it.hasNext()) {
            Activity next = it.next();
            if (!next.hasMaxAspectRatio()) {
                next.setMaxAspectRatio(next.metaData != null ? next.metaData.getFloat("android.max_aspect", f) : f);
            }
        }
    }

    private void setMinAspectRatio(Package r3) {
        float f = r3.applicationInfo.minAspectRatio;
        Iterator<Activity> it = r3.activities.iterator();
        while (it.hasNext()) {
            Activity next = it.next();
            if (!next.hasMinAspectRatio()) {
                next.setMinAspectRatio(f);
            }
        }
    }

    private void setSupportsSizeChanges(Package r6) {
        boolean z = r6.mAppMetaData != null && r6.mAppMetaData.getBoolean("android.supports_size_changes", false);
        Iterator<Activity> it = r6.activities.iterator();
        while (it.hasNext()) {
            Activity next = it.next();
            if (z || (next.metaData != null && next.metaData.getBoolean("android.supports_size_changes", false))) {
                next.info.supportsSizeChanges = true;
            }
        }
    }

    private void parseLayout(Resources resources, AttributeSet attributeSet, Activity activity) {
        int dimensionPixelSize;
        float f;
        int dimensionPixelSize2;
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestLayout);
        int type = obtainAttributes.getType(3);
        float f2 = -1.0f;
        if (type == 6) {
            f = obtainAttributes.getFraction(3, 1, 1, -1.0f);
            dimensionPixelSize = -1;
        } else {
            dimensionPixelSize = type == 5 ? obtainAttributes.getDimensionPixelSize(3, -1) : -1;
            f = -1.0f;
        }
        int type2 = obtainAttributes.getType(4);
        if (type2 == 6) {
            f2 = obtainAttributes.getFraction(4, 1, 1, -1.0f);
        } else if (type2 == 5) {
            dimensionPixelSize2 = obtainAttributes.getDimensionPixelSize(4, -1);
            int i = obtainAttributes.getInt(0, 17);
            int dimensionPixelSize3 = obtainAttributes.getDimensionPixelSize(1, -1);
            int dimensionPixelSize4 = obtainAttributes.getDimensionPixelSize(2, -1);
            obtainAttributes.recycle();
            activity.info.windowLayout = new ActivityInfo.WindowLayout(dimensionPixelSize, f, dimensionPixelSize2, f2, i, dimensionPixelSize3, dimensionPixelSize4);
        }
        dimensionPixelSize2 = -1;
        int i2 = obtainAttributes.getInt(0, 17);
        int dimensionPixelSize32 = obtainAttributes.getDimensionPixelSize(1, -1);
        int dimensionPixelSize42 = obtainAttributes.getDimensionPixelSize(2, -1);
        obtainAttributes.recycle();
        activity.info.windowLayout = new ActivityInfo.WindowLayout(dimensionPixelSize, f, dimensionPixelSize2, f2, i2, dimensionPixelSize32, dimensionPixelSize42);
    }

    private void resolveWindowLayout(Activity activity) {
        if (activity.metaData == null || !activity.metaData.containsKey("android.activity_window_layout_affinity")) {
            return;
        }
        ActivityInfo activityInfo = activity.info;
        if (activityInfo.windowLayout == null || activityInfo.windowLayout.windowLayoutAffinity == null) {
            String string = activity.metaData.getString("android.activity_window_layout_affinity");
            if (activityInfo.windowLayout == null) {
                activityInfo.windowLayout = new ActivityInfo.WindowLayout(-1, -1.0f, -1, -1.0f, 0, -1, -1);
            }
            activityInfo.windowLayout.windowLayoutAffinity = string;
        }
    }

    private Activity parseActivityAlias(Package r22, Resources resources, XmlResourceParser xmlResourceParser, int i, String[] strArr, CachedComponentArgs cachedComponentArgs) throws XmlPullParserException, IOException {
        CachedComponentArgs cachedComponentArgs2;
        TypedArray typedArray;
        String str;
        int i2;
        Activity activity;
        int i3;
        String[] strArr2;
        int i4;
        String[] strArr3 = strArr;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
        String nonConfigurationString = obtainAttributes.getNonConfigurationString(7, 1024);
        if (nonConfigurationString == null) {
            strArr3[0] = "<activity-alias> does not specify android:targetActivity";
            obtainAttributes.recycle();
            return null;
        }
        String buildClassName = buildClassName(r22.applicationInfo.packageName, nonConfigurationString, strArr3);
        if (buildClassName == null) {
            obtainAttributes.recycle();
            return null;
        }
        if (cachedComponentArgs.mActivityAliasArgs == null) {
            i2 = 0;
            cachedComponentArgs2 = cachedComponentArgs;
            typedArray = obtainAttributes;
            str = buildClassName;
            cachedComponentArgs2.mActivityAliasArgs = new ParseComponentArgs(r22, strArr3, 2, 0, 1, 11, 8, 10, this.mSeparateProcesses, 0, 6, 4);
            cachedComponentArgs2.mActivityAliasArgs.tag = "<activity-alias>";
        } else {
            cachedComponentArgs2 = cachedComponentArgs;
            typedArray = obtainAttributes;
            str = buildClassName;
            i2 = 0;
        }
        cachedComponentArgs2.mActivityAliasArgs.sa = typedArray;
        cachedComponentArgs2.mActivityAliasArgs.flags = i;
        int size = r22.activities.size();
        int i5 = i2;
        while (true) {
            if (i5 >= size) {
                activity = null;
                break;
            }
            activity = r22.activities.get(i5);
            if (str.equals(activity.info.name)) {
                break;
            }
            i5++;
        }
        if (activity == null) {
            strArr3[i2] = "<activity-alias> target activity " + str + " not found in manifest";
            typedArray.recycle();
            return null;
        }
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.targetActivity = str;
        activityInfo.configChanges = activity.info.configChanges;
        activityInfo.flags = activity.info.flags;
        activityInfo.privateFlags = activity.info.privateFlags;
        activityInfo.icon = activity.info.icon;
        activityInfo.logo = activity.info.logo;
        activityInfo.banner = activity.info.banner;
        activityInfo.labelRes = activity.info.labelRes;
        activityInfo.nonLocalizedLabel = activity.info.nonLocalizedLabel;
        activityInfo.launchMode = activity.info.launchMode;
        activityInfo.lockTaskLaunchMode = activity.info.lockTaskLaunchMode;
        activityInfo.processName = activity.info.processName;
        if (activityInfo.descriptionRes == 0) {
            activityInfo.descriptionRes = activity.info.descriptionRes;
        }
        activityInfo.screenOrientation = activity.info.screenOrientation;
        activityInfo.taskAffinity = activity.info.taskAffinity;
        activityInfo.theme = activity.info.theme;
        activityInfo.softInputMode = activity.info.softInputMode;
        activityInfo.uiOptions = activity.info.uiOptions;
        activityInfo.parentActivityName = activity.info.parentActivityName;
        activityInfo.maxRecents = activity.info.maxRecents;
        activityInfo.windowLayout = activity.info.windowLayout;
        activityInfo.resizeMode = activity.info.resizeMode;
        activityInfo.setMaxAspectRatio(activity.info.getMaxAspectRatio());
        activityInfo.setMinAspectRatio(activity.info.getManifestMinAspectRatio());
        activityInfo.supportsSizeChanges = activity.info.supportsSizeChanges;
        activityInfo.requestedVrComponent = activity.info.requestedVrComponent;
        activityInfo.directBootAware = activity.info.directBootAware;
        Activity activity2 = new Activity(cachedComponentArgs2.mActivityAliasArgs, activityInfo);
        if (strArr3[i2] != null) {
            typedArray.recycle();
            return null;
        }
        boolean hasValue = typedArray.hasValue(5);
        if (hasValue) {
            boolean z = i2;
            activity2.info.exported = typedArray.getBoolean(5, z);
            i3 = z;
        } else {
            i3 = i2;
        }
        String nonConfigurationString2 = typedArray.getNonConfigurationString(3, i3);
        if (nonConfigurationString2 != null) {
            activity2.info.permission = nonConfigurationString2.length() > 0 ? nonConfigurationString2.toString().intern() : null;
        }
        String nonConfigurationString3 = typedArray.getNonConfigurationString(9, 1024);
        if (nonConfigurationString3 != null) {
            String buildClassName2 = buildClassName(activity2.info.packageName, nonConfigurationString3, strArr3);
            if (strArr3[0] == null) {
                activity2.info.parentActivityName = buildClassName2;
            } else {
                Log.e(TAG, "Activity alias " + activity2.info.name + " specified invalid parentActivityName " + nonConfigurationString3);
                strArr3[0] = null;
            }
        }
        boolean z2 = (activity2.info.flags & 1048576) != 0;
        typedArray.recycle();
        if (strArr3[0] != null) {
            return null;
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (xmlResourceParser.getName().equals("intent-filter")) {
                    ActivityIntentInfo activityIntentInfo = new ActivityIntentInfo(activity2);
                    strArr2 = strArr;
                    if (!parseIntent(resources, xmlResourceParser, true, true, activityIntentInfo, strArr2)) {
                        return null;
                    }
                    if (activityIntentInfo.countActions() == 0) {
                        Slog.w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    } else {
                        activity2.order = Math.max(activityIntentInfo.getOrder(), activity2.order);
                        activity2.intents.add(activityIntentInfo);
                    }
                    if (z2) {
                        i4 = 1;
                    } else {
                        i4 = isImplicitlyExposedIntent(activityIntentInfo) ? 2 : 0;
                    }
                    activityIntentInfo.setVisibilityToInstantApp(i4);
                    if (activityIntentInfo.isVisibleToInstantApp()) {
                        activity2.info.flags |= 1048576;
                    }
                    if (activityIntentInfo.isImplicitlyVisibleToInstantApp()) {
                        activity2.info.flags |= 2097152;
                    }
                } else {
                    strArr2 = strArr3;
                    if (xmlResourceParser.getName().equals("meta-data")) {
                        Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, activity2.metaData, strArr2);
                        activity2.metaData = parseMetaData;
                        if (parseMetaData == null) {
                            return null;
                        }
                    } else {
                        Slog.w(TAG, "Unknown element under <activity-alias>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                        XmlUtils.skipCurrentTag(xmlResourceParser);
                    }
                }
                strArr3 = strArr2;
            }
        }
        if (!hasValue) {
            activity2.info.exported = activity2.intents.size() > 0;
        }
        return activity2;
    }

    private Provider parseProvider(Package r17, Resources resources, XmlResourceParser xmlResourceParser, int i, String[] strArr, CachedComponentArgs cachedComponentArgs) throws XmlPullParserException, IOException {
        Package r1;
        TypedArray typedArray;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProvider);
        if (cachedComponentArgs.mProviderArgs == null) {
            typedArray = obtainAttributes;
            r1 = r17;
            cachedComponentArgs.mProviderArgs = new ParseComponentArgs(r1, strArr, 2, 0, 1, 19, 15, 17, this.mSeparateProcesses, 8, 14, 6);
            cachedComponentArgs.mProviderArgs.tag = "<provider>";
        } else {
            r1 = r17;
            typedArray = obtainAttributes;
        }
        cachedComponentArgs.mProviderArgs.sa = typedArray;
        cachedComponentArgs.mProviderArgs.flags = i;
        Provider provider = new Provider(cachedComponentArgs.mProviderArgs, new ProviderInfo());
        if (strArr[0] != null) {
            typedArray.recycle();
            return null;
        }
        provider.info.exported = typedArray.getBoolean(7, r1.applicationInfo.targetSdkVersion < 17);
        String nonConfigurationString = typedArray.getNonConfigurationString(10, 0);
        provider.info.isSyncable = typedArray.getBoolean(11, false);
        String nonConfigurationString2 = typedArray.getNonConfigurationString(3, 0);
        String nonConfigurationString3 = typedArray.getNonConfigurationString(4, 0);
        if (nonConfigurationString3 == null) {
            nonConfigurationString3 = nonConfigurationString2;
        }
        if (nonConfigurationString3 == null) {
            provider.info.readPermission = r1.applicationInfo.permission;
        } else {
            provider.info.readPermission = nonConfigurationString3.length() > 0 ? nonConfigurationString3.toString().intern() : null;
        }
        String nonConfigurationString4 = typedArray.getNonConfigurationString(5, 0);
        if (nonConfigurationString4 != null) {
            nonConfigurationString2 = nonConfigurationString4;
        }
        if (nonConfigurationString2 == null) {
            provider.info.writePermission = r1.applicationInfo.permission;
        } else {
            provider.info.writePermission = nonConfigurationString2.length() > 0 ? nonConfigurationString2.toString().intern() : null;
        }
        provider.info.grantUriPermissions = typedArray.getBoolean(13, false);
        provider.info.forceUriPermissions = typedArray.getBoolean(22, false);
        provider.info.multiprocess = typedArray.getBoolean(9, false);
        provider.info.initOrder = typedArray.getInt(12, 0);
        provider.info.splitName = typedArray.getNonConfigurationString(21, 0);
        provider.info.flags = 0;
        if (typedArray.getBoolean(16, false)) {
            provider.info.flags |= 1073741824;
        }
        provider.info.directBootAware = typedArray.getBoolean(18, false);
        if (provider.info.directBootAware) {
            r1.applicationInfo.privateFlags |= 256;
        }
        boolean z = typedArray.getBoolean(20, false);
        if (z) {
            provider.info.flags |= 1048576;
            r1.visibleToInstantApps = true;
        }
        typedArray.recycle();
        if ((r1.applicationInfo.privateFlags & 2) != 0 && provider.info.processName == r1.packageName) {
            strArr[0] = "Heavy-weight applications can not have providers in main process";
            return null;
        }
        if (nonConfigurationString == null) {
            strArr[0] = "<provider> does not include authorities attribute";
            return null;
        }
        if (nonConfigurationString.length() <= 0) {
            strArr[0] = "<provider> has empty authorities attribute";
            return null;
        }
        provider.info.authority = nonConfigurationString.intern();
        if (parseProviderTags(resources, xmlResourceParser, z, provider, strArr)) {
            return provider;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0267, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseProviderTags(android.content.res.Resources r20, android.content.res.XmlResourceParser r21, boolean r22, android.content.pm.PackageParser.Provider r23, java.lang.String[] r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 616
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseProviderTags(android.content.res.Resources, android.content.res.XmlResourceParser, boolean, android.content.pm.PackageParser$Provider, java.lang.String[]):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x01e8, code lost:
    
        if (r10 != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01ea, code lost:
    
        r0 = r7.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01f2, code lost:
    
        if (r7.intents.size() <= 0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01f4, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01f5, code lost:
    
        r0.exported = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01f7, code lost:
    
        return r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Service parseService(android.content.pm.PackageParser.Package r19, android.content.res.Resources r20, android.content.res.XmlResourceParser r21, int r22, java.lang.String[] r23, android.content.pm.PackageParser.CachedComponentArgs r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseService(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[], android.content.pm.PackageParser$CachedComponentArgs):android.content.pm.PackageParser$Service");
    }

    private boolean isImplicitlyExposedIntent(IntentInfo intentInfo) {
        return intentInfo.hasCategory(Intent.CATEGORY_BROWSABLE) || intentInfo.hasAction(Intent.ACTION_SEND) || intentInfo.hasAction(Intent.ACTION_SENDTO) || intentInfo.hasAction(Intent.ACTION_SEND_MULTIPLE);
    }

    private boolean parseAllMetaData(Resources resources, XmlResourceParser xmlResourceParser, String str, Component<?> component, String[] strArr) throws XmlPullParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (xmlResourceParser.getName().equals("meta-data")) {
                    Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, component.metaData, strArr);
                    component.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return false;
                    }
                } else {
                    Slog.w(TAG, "Unknown element under " + str + ": " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
        return true;
    }

    private Bundle parseMetaData(Resources resources, XmlResourceParser xmlResourceParser, Bundle bundle, String[] strArr) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestMetaData);
        if (bundle == null) {
            bundle = new Bundle();
        }
        String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
        if (nonConfigurationString == null) {
            strArr[0] = "<meta-data> requires an android:name attribute";
            obtainAttributes.recycle();
            return null;
        }
        String intern = nonConfigurationString.intern();
        TypedValue peekValue = obtainAttributes.peekValue(2);
        if (peekValue != null && peekValue.resourceId != 0) {
            bundle.putInt(intern, peekValue.resourceId);
        } else {
            TypedValue peekValue2 = obtainAttributes.peekValue(1);
            if (peekValue2 != null) {
                if (peekValue2.type == 3) {
                    CharSequence coerceToString = peekValue2.coerceToString();
                    bundle.putString(intern, coerceToString != null ? coerceToString.toString() : null);
                } else if (peekValue2.type == 18) {
                    bundle.putBoolean(intern, peekValue2.data != 0);
                } else if (peekValue2.type >= 16 && peekValue2.type <= 31) {
                    bundle.putInt(intern, peekValue2.data);
                } else if (peekValue2.type == 4) {
                    bundle.putFloat(intern, peekValue2.getFloat());
                } else {
                    Slog.w(TAG, "<meta-data> only supports string, integer, float, color, boolean, and resource reference types: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                }
            } else {
                strArr[0] = "<meta-data> requires an android:value or android:resource attribute";
                bundle = null;
            }
        }
        obtainAttributes.recycle();
        XmlUtils.skipCurrentTag(xmlResourceParser);
        return bundle;
    }

    private static VerifierInfo parseVerifier(AttributeSet attributeSet) {
        int attributeCount = attributeSet.getAttributeCount();
        String str = null;
        String str2 = null;
        for (int i = 0; i < attributeCount; i++) {
            int attributeNameResource = attributeSet.getAttributeNameResource(i);
            if (attributeNameResource == 16842755) {
                str = attributeSet.getAttributeValue(i);
            } else if (attributeNameResource == 16843686) {
                str2 = attributeSet.getAttributeValue(i);
            }
        }
        if (str == null || str.length() == 0) {
            Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        PublicKey parsePublicKey = parsePublicKey(str2);
        if (parsePublicKey == null) {
            Slog.i(TAG, "Unable to parse verifier public key for " + str);
            return null;
        }
        return new VerifierInfo(str, parsePublicKey);
    }

    public static final PublicKey parsePublicKey(String str) {
        if (str == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            return parsePublicKey(Base64.decode(str, 0));
        } catch (IllegalArgumentException unused) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static final PublicKey parsePublicKey(byte[] bArr) {
        if (bArr == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bArr);
            try {
                return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
            } catch (NoSuchAlgorithmException unused) {
                Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
                } catch (NoSuchAlgorithmException unused2) {
                    Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        return KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                    } catch (NoSuchAlgorithmException unused3) {
                        Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (InvalidKeySpecException unused4) {
                        return null;
                    }
                } catch (InvalidKeySpecException unused5) {
                    return KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                }
            } catch (InvalidKeySpecException unused6) {
                return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
            }
        } catch (IllegalArgumentException unused7) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x00a0, code lost:
    
        r22[0] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x00a2, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00c0, code lost:
    
        r22[0] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00c2, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseIntent(android.content.res.Resources r17, android.content.res.XmlResourceParser r18, boolean r19, boolean r20, android.content.pm.PackageParser.IntentInfo r21, java.lang.String[] r22) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseIntent(android.content.res.Resources, android.content.res.XmlResourceParser, boolean, boolean, android.content.pm.PackageParser$IntentInfo, java.lang.String[]):boolean");
    }

    public static final class SigningDetails implements Parcelable {
        private static final int PAST_CERT_EXISTS = 0;
        public final Signature[] pastSigningCertificates;
        public final ArraySet<PublicKey> publicKeys;
        public final int signatureSchemeVersion;
        public final Signature[] signatures;
        public static final SigningDetails UNKNOWN = new SigningDetails(null, 0, null, null);
        public static final Parcelable.Creator<SigningDetails> CREATOR = new Parcelable.Creator<SigningDetails>() { // from class: android.content.pm.PackageParser.SigningDetails.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SigningDetails createFromParcel(Parcel parcel) {
                if (parcel.readBoolean()) {
                    return SigningDetails.UNKNOWN;
                }
                return new SigningDetails(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SigningDetails[] newArray(int i) {
                return new SigningDetails[i];
            }
        };

        public @interface CertCapabilities {
            public static final int AUTH = 16;
            public static final int INSTALLED_DATA = 1;
            public static final int PERMISSION = 4;
            public static final int ROLLBACK = 8;
            public static final int SHARED_USER_ID = 2;
        }

        public @interface SignatureSchemeVersion {
            public static final int JAR = 1;
            public static final int SIGNING_BLOCK_V2 = 2;
            public static final int SIGNING_BLOCK_V3 = 3;
            public static final int SIGNING_BLOCK_V4 = 4;
            public static final int UNKNOWN = 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SigningDetails(Signature[] signatureArr, int i, ArraySet<PublicKey> arraySet, Signature[] signatureArr2) {
            this.signatures = signatureArr;
            this.signatureSchemeVersion = i;
            this.publicKeys = arraySet;
            this.pastSigningCertificates = signatureArr2;
        }

        public SigningDetails(Signature[] signatureArr, int i, Signature[] signatureArr2) throws CertificateException {
            this(signatureArr, i, PackageParser.toSigningKeys(signatureArr), signatureArr2);
        }

        public SigningDetails(Signature[] signatureArr, int i) throws CertificateException {
            this(signatureArr, i, null);
        }

        public SigningDetails(SigningDetails signingDetails) {
            if (signingDetails != null) {
                Signature[] signatureArr = signingDetails.signatures;
                if (signatureArr != null) {
                    this.signatures = (Signature[]) signatureArr.clone();
                } else {
                    this.signatures = null;
                }
                this.signatureSchemeVersion = signingDetails.signatureSchemeVersion;
                this.publicKeys = new ArraySet<>((ArraySet) signingDetails.publicKeys);
                Signature[] signatureArr2 = signingDetails.pastSigningCertificates;
                if (signatureArr2 != null) {
                    this.pastSigningCertificates = (Signature[]) signatureArr2.clone();
                    return;
                } else {
                    this.pastSigningCertificates = null;
                    return;
                }
            }
            this.signatures = null;
            this.signatureSchemeVersion = 0;
            this.publicKeys = null;
            this.pastSigningCertificates = null;
        }

        public SigningDetails mergeLineageWith(SigningDetails signingDetails) {
            SigningDetails descendantOrSelf;
            if (!hasPastSigningCertificates()) {
                if (signingDetails.hasPastSigningCertificates() && signingDetails.hasAncestorOrSelf(this)) {
                    return signingDetails;
                }
            } else if (signingDetails.hasPastSigningCertificates() && (descendantOrSelf = getDescendantOrSelf(signingDetails)) != null) {
                if (descendantOrSelf == this) {
                    return mergeLineageWithAncestorOrSelf(signingDetails);
                }
                return signingDetails.mergeLineageWithAncestorOrSelf(this);
            }
            return this;
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x0079, code lost:
        
            if (r8 < 0) goto L49;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private android.content.pm.PackageParser.SigningDetails mergeLineageWithAncestorOrSelf(android.content.pm.PackageParser.SigningDetails r11) {
            /*
                Method dump skipped, instructions count: 213
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.SigningDetails.mergeLineageWithAncestorOrSelf(android.content.pm.PackageParser$SigningDetails):android.content.pm.PackageParser$SigningDetails");
        }

        public boolean hasCommonAncestor(SigningDetails signingDetails) {
            if (!hasPastSigningCertificates()) {
                return signingDetails.hasAncestorOrSelf(this);
            }
            if (signingDetails.hasPastSigningCertificates()) {
                return getDescendantOrSelf(signingDetails) != null;
            }
            return hasAncestorOrSelf(signingDetails);
        }

        public boolean hasAncestorOrSelfWithDigest(Set<String> set) {
            if (this != UNKNOWN && set != null && set.size() != 0) {
                Signature[] signatureArr = this.signatures;
                if (signatureArr.length > 1) {
                    int size = set.size();
                    Signature[] signatureArr2 = this.signatures;
                    if (size < signatureArr2.length) {
                        return false;
                    }
                    for (Signature signature : signatureArr2) {
                        if (!set.contains(PackageUtils.computeSha256Digest(signature.toByteArray()))) {
                            return false;
                        }
                    }
                    return true;
                }
                if (set.contains(PackageUtils.computeSha256Digest(signatureArr[0].toByteArray()))) {
                    return true;
                }
                if (hasPastSigningCertificates()) {
                    int i = 0;
                    while (true) {
                        Signature[] signatureArr3 = this.pastSigningCertificates;
                        if (i >= signatureArr3.length - 1) {
                            break;
                        }
                        if (set.contains(PackageUtils.computeSha256Digest(signatureArr3[i].toByteArray()))) {
                            return true;
                        }
                        i++;
                    }
                }
            }
            return false;
        }

        private SigningDetails getDescendantOrSelf(SigningDetails signingDetails) {
            if (!hasAncestorOrSelf(signingDetails)) {
                if (!signingDetails.hasAncestor(this)) {
                    return null;
                }
                signingDetails = this;
                this = signingDetails;
            }
            int length = this.pastSigningCertificates.length - 1;
            int length2 = signingDetails.pastSigningCertificates.length - 1;
            while (length >= 0 && !this.pastSigningCertificates[length].equals(signingDetails.pastSigningCertificates[length2])) {
                length--;
            }
            if (length < 0) {
                return null;
            }
            do {
                length--;
                length2--;
                if (length < 0 || length2 < 0) {
                    break;
                }
            } while (this.pastSigningCertificates[length].equals(signingDetails.pastSigningCertificates[length2]));
            if (length < 0 || length2 < 0) {
                return this;
            }
            return null;
        }

        public boolean hasSignatures() {
            Signature[] signatureArr = this.signatures;
            return signatureArr != null && signatureArr.length > 0;
        }

        public boolean hasPastSigningCertificates() {
            Signature[] signatureArr = this.pastSigningCertificates;
            return signatureArr != null && signatureArr.length > 0;
        }

        public boolean hasAncestorOrSelf(SigningDetails signingDetails) {
            SigningDetails signingDetails2 = UNKNOWN;
            if (this == signingDetails2 || signingDetails == signingDetails2) {
                return false;
            }
            Signature[] signatureArr = signingDetails.signatures;
            if (signatureArr.length > 1) {
                return signaturesMatchExactly(signingDetails);
            }
            return hasCertificate(signatureArr[0]);
        }

        public boolean hasAncestor(SigningDetails signingDetails) {
            SigningDetails signingDetails2 = UNKNOWN;
            if (this != signingDetails2 && signingDetails != signingDetails2 && hasPastSigningCertificates() && signingDetails.signatures.length == 1) {
                int i = 0;
                while (true) {
                    Signature[] signatureArr = this.pastSigningCertificates;
                    if (i >= signatureArr.length - 1) {
                        break;
                    }
                    if (signatureArr[i].equals(signingDetails.signatures[0])) {
                        return true;
                    }
                    i++;
                }
            }
            return false;
        }

        public boolean hasCommonSignerWithCapability(SigningDetails signingDetails, int i) {
            SigningDetails signingDetails2 = UNKNOWN;
            if (this == signingDetails2 || signingDetails == signingDetails2) {
                return false;
            }
            if (this.signatures.length > 1 || signingDetails.signatures.length > 1) {
                return signaturesMatchExactly(signingDetails);
            }
            ArraySet arraySet = new ArraySet();
            if (signingDetails.hasPastSigningCertificates()) {
                arraySet.addAll(Arrays.asList(signingDetails.pastSigningCertificates));
            } else {
                arraySet.addAll(Arrays.asList(signingDetails.signatures));
            }
            if (arraySet.contains(this.signatures[0])) {
                return true;
            }
            if (hasPastSigningCertificates()) {
                int i2 = 0;
                while (true) {
                    Signature[] signatureArr = this.pastSigningCertificates;
                    if (i2 >= signatureArr.length - 1) {
                        break;
                    }
                    if (arraySet.contains(signatureArr[i2]) && (this.pastSigningCertificates[i2].getFlags() & i) == i) {
                        return true;
                    }
                    i2++;
                }
            }
            return false;
        }

        public boolean checkCapability(SigningDetails signingDetails, int i) {
            SigningDetails signingDetails2 = UNKNOWN;
            if (this == signingDetails2 || signingDetails == signingDetails2) {
                return false;
            }
            Signature[] signatureArr = signingDetails.signatures;
            if (signatureArr.length == 0) {
                Slog.e(PackageParser.TAG, "There isn't any certificates in this package");
                return false;
            }
            if (signatureArr.length > 1) {
                return signaturesMatchExactly(signingDetails);
            }
            return hasCertificate(signatureArr[0], i);
        }

        public boolean checkCapabilityRecover(SigningDetails signingDetails, int i) throws CertificateException {
            SigningDetails signingDetails2 = UNKNOWN;
            if (signingDetails == signingDetails2 || this == signingDetails2) {
                return false;
            }
            if (!hasPastSigningCertificates() || signingDetails.signatures.length != 1) {
                return Signature.areEffectiveArraysMatch(signingDetails.signatures, this.signatures);
            }
            int i2 = 0;
            while (true) {
                Signature[] signatureArr = this.pastSigningCertificates;
                if (i2 >= signatureArr.length) {
                    return false;
                }
                if (Signature.areEffectiveMatch(signingDetails.signatures[0], signatureArr[i2]) && this.pastSigningCertificates[i2].getFlags() == i) {
                    return true;
                }
                i2++;
            }
        }

        public boolean hasCertificate(Signature signature) {
            return hasCertificateInternal(signature, 0);
        }

        public boolean hasCertificate(Signature signature, int i) {
            return hasCertificateInternal(signature, i);
        }

        public boolean hasCertificate(byte[] bArr) {
            return hasCertificate(new Signature(bArr));
        }

        private boolean hasCertificateInternal(Signature signature, int i) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                int i2 = 0;
                while (true) {
                    Signature[] signatureArr = this.pastSigningCertificates;
                    if (i2 >= signatureArr.length - 1) {
                        break;
                    }
                    if (!signatureArr[i2].equals(signature) || (i != 0 && (this.pastSigningCertificates[i2].getFlags() & i) != i)) {
                        i2++;
                    }
                }
                return true;
            }
            Signature[] signatureArr2 = this.signatures;
            return signatureArr2.length == 1 && signatureArr2[0].equals(signature);
        }

        public boolean checkCapability(String str, int i) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasSha256Certificate(str == null ? null : HexEncoding.decode(str, false), i)) {
                return true;
            }
            return PackageUtils.computeSignaturesSha256Digest(PackageUtils.computeSignaturesSha256Digests(this.signatures)).equals(str);
        }

        public boolean hasSha256Certificate(byte[] bArr) {
            return hasSha256CertificateInternal(bArr, 0);
        }

        public boolean hasSha256Certificate(byte[] bArr, int i) {
            return hasSha256CertificateInternal(bArr, i);
        }

        private boolean hasSha256CertificateInternal(byte[] bArr, int i) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                int i2 = 0;
                while (true) {
                    Signature[] signatureArr = this.pastSigningCertificates;
                    if (i2 >= signatureArr.length - 1) {
                        break;
                    }
                    if (!Arrays.equals(bArr, PackageUtils.computeSha256DigestBytes(signatureArr[i2].toByteArray())) || (i != 0 && (this.pastSigningCertificates[i2].getFlags() & i) != i)) {
                        i2++;
                    }
                }
                return true;
            }
            Signature[] signatureArr2 = this.signatures;
            if (signatureArr2.length == 1) {
                return Arrays.equals(bArr, PackageUtils.computeSha256DigestBytes(signatureArr2[0].toByteArray()));
            }
            return false;
        }

        public boolean signaturesMatchExactly(SigningDetails signingDetails) {
            return Signature.areExactArraysMatch(this.signatures, signingDetails.signatures);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            boolean z = UNKNOWN == this;
            parcel.writeBoolean(z);
            if (z) {
                return;
            }
            parcel.writeTypedArray(this.signatures, i);
            parcel.writeInt(this.signatureSchemeVersion);
            parcel.writeArraySet(this.publicKeys);
            parcel.writeTypedArray(this.pastSigningCertificates, i);
        }

        protected SigningDetails(Parcel parcel) {
            ClassLoader classLoader = Object.class.getClassLoader();
            this.signatures = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
            this.signatureSchemeVersion = parcel.readInt();
            this.publicKeys = parcel.readArraySet(classLoader);
            this.pastSigningCertificates = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SigningDetails)) {
                return false;
            }
            SigningDetails signingDetails = (SigningDetails) obj;
            if (this.signatureSchemeVersion != signingDetails.signatureSchemeVersion || !Signature.areExactArraysMatch(this.signatures, signingDetails.signatures)) {
                return false;
            }
            ArraySet<PublicKey> arraySet = this.publicKeys;
            if (arraySet != null) {
                if (!arraySet.equals(signingDetails.publicKeys)) {
                    return false;
                }
            } else if (signingDetails.publicKeys != null) {
                return false;
            }
            if (!Arrays.equals(this.pastSigningCertificates, signingDetails.pastSigningCertificates)) {
                return false;
            }
            int i = 0;
            while (true) {
                Signature[] signatureArr = this.pastSigningCertificates;
                if (i >= signatureArr.length) {
                    return true;
                }
                if (signatureArr[i].getFlags() != signingDetails.pastSigningCertificates[i].getFlags()) {
                    return false;
                }
                i++;
            }
        }

        public int hashCode() {
            int hashCode = ((Arrays.hashCode(this.signatures) * 31) + this.signatureSchemeVersion) * 31;
            ArraySet<PublicKey> arraySet = this.publicKeys;
            return ((hashCode + (arraySet != null ? arraySet.hashCode() : 0)) * 31) + Arrays.hashCode(this.pastSigningCertificates);
        }

        public static class Builder {
            private Signature[] mPastSigningCertificates;
            private int mSignatureSchemeVersion = 0;
            private Signature[] mSignatures;

            public Builder setSignatures(Signature[] signatureArr) {
                this.mSignatures = signatureArr;
                return this;
            }

            public Builder setSignatureSchemeVersion(int i) {
                this.mSignatureSchemeVersion = i;
                return this;
            }

            public Builder setPastSigningCertificates(Signature[] signatureArr) {
                this.mPastSigningCertificates = signatureArr;
                return this;
            }

            private void checkInvariants() {
                if (this.mSignatures == null) {
                    throw new IllegalStateException("SigningDetails requires the current signing certificates.");
                }
            }

            public SigningDetails build() throws CertificateException {
                checkInvariants();
                return new SigningDetails(this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
            }
        }
    }

    public static final class Package implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Package>() { // from class: android.content.pm.PackageParser.Package.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Package createFromParcel(Parcel parcel) {
                return new Package(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Package[] newArray(int i) {
                return new Package[i];
            }
        };
        public final ArrayList<Activity> activities;
        public ApplicationInfo applicationInfo;
        public String baseCodePath;
        public boolean baseHardwareAccelerated;
        public int baseRevisionCode;
        public ArrayList<Package> childPackages;
        public String codePath;
        public ArrayList<ConfigurationInfo> configPreferences;
        public boolean coreApp;
        public String cpuAbiOverride;
        public ArrayList<FeatureGroupInfo> featureGroups;
        public final ArrayList<String> implicitPermissions;
        public int installLocation;
        public final ArrayList<Instrumentation> instrumentation;
        public boolean isStub;
        public ArrayList<String> libraryNames;
        public ArrayList<String> mAdoptPermissions;
        public Bundle mAppMetaData;
        public int mCompileSdkVersion;
        public String mCompileSdkVersionCodename;
        public Object mExtras;
        public ArrayMap<String, ArraySet<PublicKey>> mKeySetMapping;
        public long[] mLastPackageUsageTimeInMills;
        public ArrayList<String> mOriginalPackages;
        public String mOverlayCategory;
        public boolean mOverlayIsStatic;
        public int mOverlayPriority;
        public String mOverlayTarget;
        public String mOverlayTargetName;
        public int mPreferredOrder;
        public String mRealPackage;
        public String mRequiredAccountType;
        public boolean mRequiredForAllUsers;
        public String mRestrictedAccountType;
        public String mSharedUserId;
        public int mSharedUserLabel;
        public SigningDetails mSigningDetails;
        public ArraySet<String> mUpgradeKeySets;
        public int mVersionCode;
        public int mVersionCodeMajor;
        public String mVersionName;
        public String manifestPackageName;
        public String packageName;
        public Package parentPackage;
        public final ArrayList<PermissionGroup> permissionGroups;
        public final ArrayList<Permission> permissions;
        public ArrayList<ActivityIntentInfo> preferredActivityFilters;
        public ArrayList<String> protectedBroadcasts;
        public final ArrayList<Provider> providers;
        public final ArrayList<Activity> receivers;
        public ArrayList<FeatureInfo> reqFeatures;
        public final ArrayList<String> requestedPermissions;
        public byte[] restrictUpdateHash;
        public final ArrayList<Service> services;
        public String[] splitCodePaths;
        public int[] splitFlags;
        public String[] splitNames;
        public int[] splitPrivateFlags;
        public int[] splitRevisionCodes;
        public String staticSharedLibName;
        public long staticSharedLibVersion;
        public boolean use32bitAbi;
        public ArrayList<String> usesLibraries;
        public String[] usesLibraryFiles;
        public ArrayList<SharedLibraryInfo> usesLibraryInfos;
        public ArrayList<String> usesOptionalLibraries;
        public ArrayList<String> usesStaticLibraries;
        public String[][] usesStaticLibrariesCertDigests;
        public long[] usesStaticLibrariesVersions;
        public boolean visibleToInstantApps;
        public String volumeUuid;

        public boolean canHaveOatDir() {
            return true;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean isForwardLocked() {
            return false;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
        }

        public Package(String str) {
            this.applicationInfo = new ApplicationInfo();
            this.permissions = new ArrayList<>(0);
            this.permissionGroups = new ArrayList<>(0);
            this.activities = new ArrayList<>(0);
            this.receivers = new ArrayList<>(0);
            this.providers = new ArrayList<>(0);
            this.services = new ArrayList<>(0);
            this.instrumentation = new ArrayList<>(0);
            this.requestedPermissions = new ArrayList<>();
            this.implicitPermissions = new ArrayList<>();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            this.packageName = str;
            this.manifestPackageName = str;
            this.applicationInfo.packageName = str;
            this.applicationInfo.uid = -1;
        }

        public void setApplicationVolumeUuid(String str) {
            UUID convert = StorageManager.convert(str);
            this.applicationInfo.volumeUuid = str;
            this.applicationInfo.storageUuid = convert;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.volumeUuid = str;
                    this.childPackages.get(i).applicationInfo.storageUuid = convert;
                }
            }
        }

        public void setApplicationInfoCodePath(String str) {
            this.applicationInfo.setCodePath(str);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setCodePath(str);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoResourcePath(String str) {
            this.applicationInfo.setResourcePath(str);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setResourcePath(str);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoBaseResourcePath(String str) {
            this.applicationInfo.setBaseResourcePath(str);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseResourcePath(str);
                }
            }
        }

        public void setApplicationInfoBaseCodePath(String str) {
            this.applicationInfo.setBaseCodePath(str);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseCodePath(str);
                }
            }
        }

        public List<String> getChildPackageNames() {
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList == null) {
                return null;
            }
            int size = arrayList.size();
            ArrayList arrayList2 = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList2.add(this.childPackages.get(i).packageName);
            }
            return arrayList2;
        }

        public boolean hasChildPackage(String str) {
            ArrayList<Package> arrayList = this.childPackages;
            int size = arrayList != null ? arrayList.size() : 0;
            for (int i = 0; i < size; i++) {
                if (this.childPackages.get(i).packageName.equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public void setApplicationInfoSplitCodePaths(String[] strArr) {
            this.applicationInfo.setSplitCodePaths(strArr);
        }

        @Deprecated
        public void setApplicationInfoSplitResourcePaths(String[] strArr) {
            this.applicationInfo.setSplitResourcePaths(strArr);
        }

        public void setSplitCodePaths(String[] strArr) {
            this.splitCodePaths = strArr;
        }

        public void setCodePath(String str) {
            this.codePath = str;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).codePath = str;
                }
            }
        }

        public void setBaseCodePath(String str) {
            this.baseCodePath = str;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).baseCodePath = str;
                }
            }
        }

        public void setSigningDetails(SigningDetails signingDetails) {
            this.mSigningDetails = signingDetails;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).mSigningDetails = signingDetails;
                }
            }
        }

        public void setVolumeUuid(String str) {
            this.volumeUuid = str;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).volumeUuid = str;
                }
            }
        }

        public void setApplicationInfoFlags(int i, int i2) {
            ApplicationInfo applicationInfo = this.applicationInfo;
            int i3 = applicationInfo.flags;
            int i4 = ~i;
            int i5 = i & i2;
            applicationInfo.flags = (i3 & i4) | i5;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i6 = 0; i6 < size; i6++) {
                    this.childPackages.get(i6).applicationInfo.flags = (this.applicationInfo.flags & i4) | i5;
                }
            }
        }

        public void setUse32bitAbi(boolean z) {
            this.use32bitAbi = z;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).use32bitAbi = z;
                }
            }
        }

        public boolean isLibrary() {
            return (this.staticSharedLibName == null && ArrayUtils.isEmpty(this.libraryNames)) ? false : true;
        }

        public List<String> getAllCodePaths() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(arrayList, this.splitCodePaths);
            }
            return arrayList;
        }

        public List<String> getAllCodePathsExcludingResourceOnly() {
            ArrayList arrayList = new ArrayList();
            if ((this.applicationInfo.flags & 4) != 0) {
                arrayList.add(this.baseCodePath);
            }
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                int i = 0;
                while (true) {
                    String[] strArr = this.splitCodePaths;
                    if (i >= strArr.length) {
                        break;
                    }
                    if ((this.splitFlags[i] & 4) != 0) {
                        arrayList.add(strArr[i]);
                    }
                    i++;
                }
            }
            return arrayList;
        }

        public void setPackageName(String str) {
            this.packageName = str;
            this.applicationInfo.packageName = str;
            for (int size = this.permissions.size() - 1; size >= 0; size--) {
                this.permissions.get(size).setPackageName(str);
            }
            for (int size2 = this.permissionGroups.size() - 1; size2 >= 0; size2--) {
                this.permissionGroups.get(size2).setPackageName(str);
            }
            for (int size3 = this.activities.size() - 1; size3 >= 0; size3--) {
                this.activities.get(size3).setPackageName(str);
            }
            for (int size4 = this.receivers.size() - 1; size4 >= 0; size4--) {
                this.receivers.get(size4).setPackageName(str);
            }
            for (int size5 = this.providers.size() - 1; size5 >= 0; size5--) {
                this.providers.get(size5).setPackageName(str);
            }
            for (int size6 = this.services.size() - 1; size6 >= 0; size6--) {
                this.services.get(size6).setPackageName(str);
            }
            for (int size7 = this.instrumentation.size() - 1; size7 >= 0; size7--) {
                this.instrumentation.get(size7).setPackageName(str);
            }
        }

        public boolean hasComponentClassName(String str) {
            for (int size = this.activities.size() - 1; size >= 0; size--) {
                if (str.equals(this.activities.get(size).className)) {
                    return true;
                }
            }
            for (int size2 = this.receivers.size() - 1; size2 >= 0; size2--) {
                if (str.equals(this.receivers.get(size2).className)) {
                    return true;
                }
            }
            for (int size3 = this.providers.size() - 1; size3 >= 0; size3--) {
                if (str.equals(this.providers.get(size3).className)) {
                    return true;
                }
            }
            for (int size4 = this.services.size() - 1; size4 >= 0; size4--) {
                if (str.equals(this.services.get(size4).className)) {
                    return true;
                }
            }
            for (int size5 = this.instrumentation.size() - 1; size5 >= 0; size5--) {
                if (str.equals(this.instrumentation.get(size5).className)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isExternal() {
            return this.applicationInfo.isExternal();
        }

        public boolean isOem() {
            return this.applicationInfo.isOem();
        }

        public boolean isVendor() {
            return this.applicationInfo.isVendor();
        }

        public boolean isProduct() {
            return this.applicationInfo.isProduct();
        }

        public boolean isSystemExt() {
            return this.applicationInfo.isSystemExt();
        }

        public boolean isOdm() {
            return this.applicationInfo.isOdm();
        }

        public boolean isPrivileged() {
            return this.applicationInfo.isPrivilegedApp();
        }

        public boolean isSystem() {
            return this.applicationInfo.isSystemApp();
        }

        public boolean isUpdatedSystemApp() {
            return this.applicationInfo.isUpdatedSystemApp();
        }

        public boolean isMatch(int i) {
            if ((i & 1048576) != 0) {
                return isSystem();
            }
            return true;
        }

        public long getLatestPackageUseTimeInMills() {
            long j = 0;
            for (long j2 : this.mLastPackageUsageTimeInMills) {
                j = Math.max(j, j2);
            }
            return j;
        }

        public long getLatestForegroundPackageUseTimeInMills() {
            int[] iArr = {0, 2};
            long j = 0;
            for (int i = 0; i < 2; i++) {
                j = Math.max(j, this.mLastPackageUsageTimeInMills[iArr[i]]);
            }
            return j;
        }

        public String toString() {
            return "Package{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
        }

        public Package(Parcel parcel) {
            this.applicationInfo = new ApplicationInfo();
            ArrayList<Permission> arrayList = new ArrayList<>(0);
            this.permissions = arrayList;
            ArrayList<PermissionGroup> arrayList2 = new ArrayList<>(0);
            this.permissionGroups = arrayList2;
            ArrayList<Activity> arrayList3 = new ArrayList<>(0);
            this.activities = arrayList3;
            ArrayList<Activity> arrayList4 = new ArrayList<>(0);
            this.receivers = arrayList4;
            ArrayList<Provider> arrayList5 = new ArrayList<>(0);
            this.providers = arrayList5;
            ArrayList<Service> arrayList6 = new ArrayList<>(0);
            this.services = arrayList6;
            ArrayList<Instrumentation> arrayList7 = new ArrayList<>(0);
            this.instrumentation = arrayList7;
            ArrayList<String> arrayList8 = new ArrayList<>();
            this.requestedPermissions = arrayList8;
            ArrayList<String> arrayList9 = new ArrayList<>();
            this.implicitPermissions = arrayList9;
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            ClassLoader classLoader = Object.class.getClassLoader();
            this.packageName = parcel.readString().intern();
            this.manifestPackageName = parcel.readString();
            this.splitNames = parcel.readStringArray();
            this.volumeUuid = parcel.readString();
            this.codePath = parcel.readString();
            this.baseCodePath = parcel.readString();
            this.splitCodePaths = parcel.readStringArray();
            this.baseRevisionCode = parcel.readInt();
            this.splitRevisionCodes = parcel.createIntArray();
            this.splitFlags = parcel.createIntArray();
            this.splitPrivateFlags = parcel.createIntArray();
            this.baseHardwareAccelerated = parcel.readInt() == 1;
            ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readParcelable(classLoader, ApplicationInfo.class);
            this.applicationInfo = applicationInfo;
            if (applicationInfo.permission != null) {
                ApplicationInfo applicationInfo2 = this.applicationInfo;
                applicationInfo2.permission = applicationInfo2.permission.intern();
            }
            parcel.readParcelableList(arrayList, classLoader, Permission.class);
            fixupOwner(arrayList);
            parcel.readParcelableList(arrayList2, classLoader, PermissionGroup.class);
            fixupOwner(arrayList2);
            parcel.readParcelableList(arrayList3, classLoader, Activity.class);
            fixupOwner(arrayList3);
            parcel.readParcelableList(arrayList4, classLoader, Activity.class);
            fixupOwner(arrayList4);
            parcel.readParcelableList(arrayList5, classLoader, Provider.class);
            fixupOwner(arrayList5);
            parcel.readParcelableList(arrayList6, classLoader, Service.class);
            fixupOwner(arrayList6);
            parcel.readParcelableList(arrayList7, classLoader, Instrumentation.class);
            fixupOwner(arrayList7);
            parcel.readStringList(arrayList8);
            internStringArrayList(arrayList8);
            parcel.readStringList(arrayList9);
            internStringArrayList(arrayList9);
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            this.protectedBroadcasts = createStringArrayList;
            internStringArrayList(createStringArrayList);
            this.parentPackage = (Package) parcel.readParcelable(classLoader, Package.class);
            ArrayList<Package> arrayList10 = new ArrayList<>();
            this.childPackages = arrayList10;
            parcel.readParcelableList(arrayList10, classLoader, Package.class);
            if (this.childPackages.size() == 0) {
                this.childPackages = null;
            }
            String readString = parcel.readString();
            this.staticSharedLibName = readString;
            if (readString != null) {
                this.staticSharedLibName = readString.intern();
            }
            this.staticSharedLibVersion = parcel.readLong();
            ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
            this.libraryNames = createStringArrayList2;
            internStringArrayList(createStringArrayList2);
            ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
            this.usesLibraries = createStringArrayList3;
            internStringArrayList(createStringArrayList3);
            ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
            this.usesOptionalLibraries = createStringArrayList4;
            internStringArrayList(createStringArrayList4);
            this.usesLibraryFiles = parcel.readStringArray();
            this.usesLibraryInfos = parcel.createTypedArrayList(SharedLibraryInfo.CREATOR);
            int readInt = parcel.readInt();
            if (readInt > 0) {
                ArrayList<String> arrayList11 = new ArrayList<>(readInt);
                this.usesStaticLibraries = arrayList11;
                parcel.readStringList(arrayList11);
                internStringArrayList(this.usesStaticLibraries);
                long[] jArr = new long[readInt];
                this.usesStaticLibrariesVersions = jArr;
                parcel.readLongArray(jArr);
                this.usesStaticLibrariesCertDigests = new String[readInt][];
                for (int i = 0; i < readInt; i++) {
                    this.usesStaticLibrariesCertDigests[i] = parcel.createStringArray();
                }
            }
            ArrayList<ActivityIntentInfo> arrayList12 = new ArrayList<>();
            this.preferredActivityFilters = arrayList12;
            parcel.readParcelableList(arrayList12, classLoader, ActivityIntentInfo.class);
            if (this.preferredActivityFilters.size() == 0) {
                this.preferredActivityFilters = null;
            }
            this.mOriginalPackages = parcel.createStringArrayList();
            this.mRealPackage = parcel.readString();
            this.mAdoptPermissions = parcel.createStringArrayList();
            this.mAppMetaData = parcel.readBundle();
            this.mVersionCode = parcel.readInt();
            this.mVersionCodeMajor = parcel.readInt();
            String readString2 = parcel.readString();
            this.mVersionName = readString2;
            if (readString2 != null) {
                this.mVersionName = readString2.intern();
            }
            String readString3 = parcel.readString();
            this.mSharedUserId = readString3;
            if (readString3 != null) {
                this.mSharedUserId = readString3.intern();
            }
            this.mSharedUserLabel = parcel.readInt();
            this.mSigningDetails = (SigningDetails) parcel.readParcelable(classLoader, SigningDetails.class);
            this.mPreferredOrder = parcel.readInt();
            ArrayList<ConfigurationInfo> arrayList13 = new ArrayList<>();
            this.configPreferences = arrayList13;
            parcel.readParcelableList(arrayList13, classLoader, ConfigurationInfo.class);
            if (this.configPreferences.size() == 0) {
                this.configPreferences = null;
            }
            ArrayList<FeatureInfo> arrayList14 = new ArrayList<>();
            this.reqFeatures = arrayList14;
            parcel.readParcelableList(arrayList14, classLoader, FeatureInfo.class);
            if (this.reqFeatures.size() == 0) {
                this.reqFeatures = null;
            }
            ArrayList<FeatureGroupInfo> arrayList15 = new ArrayList<>();
            this.featureGroups = arrayList15;
            parcel.readParcelableList(arrayList15, classLoader, FeatureGroupInfo.class);
            if (this.featureGroups.size() == 0) {
                this.featureGroups = null;
            }
            this.installLocation = parcel.readInt();
            this.coreApp = parcel.readInt() == 1;
            this.mRequiredForAllUsers = parcel.readInt() == 1;
            this.mRestrictedAccountType = parcel.readString();
            this.mRequiredAccountType = parcel.readString();
            this.mOverlayTarget = parcel.readString();
            this.mOverlayTargetName = parcel.readString();
            this.mOverlayCategory = parcel.readString();
            this.mOverlayPriority = parcel.readInt();
            this.mOverlayIsStatic = parcel.readInt() == 1;
            this.mCompileSdkVersion = parcel.readInt();
            this.mCompileSdkVersionCodename = parcel.readString();
            this.mUpgradeKeySets = parcel.readArraySet(classLoader);
            this.mKeySetMapping = PackageParser.readKeySetMapping(parcel);
            this.cpuAbiOverride = parcel.readString();
            this.use32bitAbi = parcel.readInt() == 1;
            this.restrictUpdateHash = parcel.createByteArray();
            this.visibleToInstantApps = parcel.readInt() == 1;
        }

        private static void internStringArrayList(List<String> list) {
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    list.set(i, list.get(i).intern());
                }
            }
        }

        public void fixupOwner(List<? extends Component<?>> list) {
            if (list != null) {
                for (Component<?> component : list) {
                    component.owner = this;
                    if (component instanceof Activity) {
                        ((Activity) component).info.applicationInfo = this.applicationInfo;
                    } else if (component instanceof Service) {
                        ((Service) component).info.applicationInfo = this.applicationInfo;
                    } else if (component instanceof Provider) {
                        ((Provider) component).info.applicationInfo = this.applicationInfo;
                    }
                }
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.packageName);
            parcel.writeString(this.manifestPackageName);
            parcel.writeStringArray(this.splitNames);
            parcel.writeString(this.volumeUuid);
            parcel.writeString(this.codePath);
            parcel.writeString(this.baseCodePath);
            parcel.writeStringArray(this.splitCodePaths);
            parcel.writeInt(this.baseRevisionCode);
            parcel.writeIntArray(this.splitRevisionCodes);
            parcel.writeIntArray(this.splitFlags);
            parcel.writeIntArray(this.splitPrivateFlags);
            parcel.writeInt(this.baseHardwareAccelerated ? 1 : 0);
            parcel.writeParcelable(this.applicationInfo, i);
            parcel.writeParcelableList(this.permissions, i);
            parcel.writeParcelableList(this.permissionGroups, i);
            parcel.writeParcelableList(this.activities, i);
            parcel.writeParcelableList(this.receivers, i);
            parcel.writeParcelableList(this.providers, i);
            parcel.writeParcelableList(this.services, i);
            parcel.writeParcelableList(this.instrumentation, i);
            parcel.writeStringList(this.requestedPermissions);
            parcel.writeStringList(this.implicitPermissions);
            parcel.writeStringList(this.protectedBroadcasts);
            parcel.writeParcelable(this.parentPackage, i);
            parcel.writeParcelableList(this.childPackages, i);
            parcel.writeString(this.staticSharedLibName);
            parcel.writeLong(this.staticSharedLibVersion);
            parcel.writeStringList(this.libraryNames);
            parcel.writeStringList(this.usesLibraries);
            parcel.writeStringList(this.usesOptionalLibraries);
            parcel.writeStringArray(this.usesLibraryFiles);
            parcel.writeTypedList(this.usesLibraryInfos);
            if (ArrayUtils.isEmpty(this.usesStaticLibraries)) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(this.usesStaticLibraries.size());
                parcel.writeStringList(this.usesStaticLibraries);
                parcel.writeLongArray(this.usesStaticLibrariesVersions);
                for (String[] strArr : this.usesStaticLibrariesCertDigests) {
                    parcel.writeStringArray(strArr);
                }
            }
            parcel.writeParcelableList(this.preferredActivityFilters, i);
            parcel.writeStringList(this.mOriginalPackages);
            parcel.writeString(this.mRealPackage);
            parcel.writeStringList(this.mAdoptPermissions);
            parcel.writeBundle(this.mAppMetaData);
            parcel.writeInt(this.mVersionCode);
            parcel.writeInt(this.mVersionCodeMajor);
            parcel.writeString(this.mVersionName);
            parcel.writeString(this.mSharedUserId);
            parcel.writeInt(this.mSharedUserLabel);
            parcel.writeParcelable(this.mSigningDetails, i);
            parcel.writeInt(this.mPreferredOrder);
            parcel.writeParcelableList(this.configPreferences, i);
            parcel.writeParcelableList(this.reqFeatures, i);
            parcel.writeParcelableList(this.featureGroups, i);
            parcel.writeInt(this.installLocation);
            parcel.writeInt(this.coreApp ? 1 : 0);
            parcel.writeInt(this.mRequiredForAllUsers ? 1 : 0);
            parcel.writeString(this.mRestrictedAccountType);
            parcel.writeString(this.mRequiredAccountType);
            parcel.writeString(this.mOverlayTarget);
            parcel.writeString(this.mOverlayTargetName);
            parcel.writeString(this.mOverlayCategory);
            parcel.writeInt(this.mOverlayPriority);
            parcel.writeInt(this.mOverlayIsStatic ? 1 : 0);
            parcel.writeInt(this.mCompileSdkVersion);
            parcel.writeString(this.mCompileSdkVersionCodename);
            parcel.writeArraySet(this.mUpgradeKeySets);
            PackageParser.writeKeySetMapping(parcel, this.mKeySetMapping);
            parcel.writeString(this.cpuAbiOverride);
            parcel.writeInt(this.use32bitAbi ? 1 : 0);
            parcel.writeByteArray(this.restrictUpdateHash);
            parcel.writeInt(this.visibleToInstantApps ? 1 : 0);
        }
    }

    public static abstract class Component<II extends IntentInfo> {
        public final String className;
        ComponentName componentName;
        String componentShortName;
        public final ArrayList<II> intents;
        public Bundle metaData;
        public int order;
        public Package owner;

        public Component(Package r1, ArrayList<II> arrayList, String str) {
            this.owner = r1;
            this.intents = arrayList;
            this.className = str;
        }

        public Component(Package r1) {
            this.owner = r1;
            this.intents = null;
            this.className = null;
        }

        public Component(ParsePackageItemArgs parsePackageItemArgs, PackageItemInfo packageItemInfo) {
            this.owner = parsePackageItemArgs.owner;
            this.intents = new ArrayList<>(0);
            if (PackageParser.parsePackageItemInfo(parsePackageItemArgs.owner, packageItemInfo, parsePackageItemArgs.outError, parsePackageItemArgs.tag, parsePackageItemArgs.sa, true, parsePackageItemArgs.nameRes, parsePackageItemArgs.labelRes, parsePackageItemArgs.iconRes, parsePackageItemArgs.roundIconRes, parsePackageItemArgs.logoRes, parsePackageItemArgs.bannerRes)) {
                this.className = packageItemInfo.name;
            } else {
                this.className = null;
            }
        }

        public Component(ParseComponentArgs parseComponentArgs, ComponentInfo componentInfo) {
            this((ParsePackageItemArgs) parseComponentArgs, (PackageItemInfo) componentInfo);
            String nonResourceString;
            if (parseComponentArgs.outError[0] != null) {
                return;
            }
            if (parseComponentArgs.processRes != 0) {
                if (this.owner.applicationInfo.targetSdkVersion >= 8) {
                    nonResourceString = parseComponentArgs.sa.getNonConfigurationString(parseComponentArgs.processRes, 1024);
                } else {
                    nonResourceString = parseComponentArgs.sa.getNonResourceString(parseComponentArgs.processRes);
                }
                componentInfo.processName = PackageParser.buildProcessName(this.owner.applicationInfo.packageName, this.owner.applicationInfo.processName, nonResourceString, parseComponentArgs.flags, parseComponentArgs.sepProcesses, parseComponentArgs.outError);
            }
            if (parseComponentArgs.descriptionRes != 0) {
                componentInfo.descriptionRes = parseComponentArgs.sa.getResourceId(parseComponentArgs.descriptionRes, 0);
            }
            componentInfo.enabled = parseComponentArgs.sa.getBoolean(parseComponentArgs.enabledRes, true);
        }

        public Component(Component<II> component) {
            this.owner = component.owner;
            this.intents = component.intents;
            this.className = component.className;
            this.componentName = component.componentName;
            this.componentShortName = component.componentShortName;
        }

        public ComponentName getComponentName() {
            ComponentName componentName = this.componentName;
            if (componentName != null) {
                return componentName;
            }
            if (this.className != null) {
                this.componentName = new ComponentName(this.owner.applicationInfo.packageName, this.className);
            }
            return this.componentName;
        }

        protected Component(Parcel parcel) {
            this.className = parcel.readString();
            this.metaData = parcel.readBundle();
            this.intents = createIntentsList(parcel);
            this.owner = null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.className);
            parcel.writeBundle(this.metaData);
            writeIntentsList(this.intents, parcel, i);
        }

        private static void writeIntentsList(ArrayList<? extends IntentInfo> arrayList, Parcel parcel, int i) {
            if (arrayList == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = arrayList.size();
            parcel.writeInt(size);
            if (size > 0) {
                parcel.writeString(arrayList.get(0).getClass().getName());
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.get(i2).writeIntentInfoToParcel(parcel, i);
                }
            }
        }

        private static <T extends IntentInfo> ArrayList<T> createIntentsList(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            if (readInt == 0) {
                return new ArrayList<>(0);
            }
            String readString = parcel.readString();
            try {
                Class<?> cls = Class.forName(readString);
                if (!IntentInfo.class.isAssignableFrom(cls)) {
                    throw new AssertionError("Intent list requires subclass of IntentInfo, not: " + readString);
                }
                Constructor<?> constructor = cls.getConstructor(Parcel.class);
                EnterpriseProxyConstants.AnonymousClass1 anonymousClass1 = (ArrayList<T>) new ArrayList(readInt);
                for (int i = 0; i < readInt; i++) {
                    anonymousClass1.add((IntentInfo) constructor.newInstance(parcel));
                }
                return anonymousClass1;
            } catch (ReflectiveOperationException unused) {
                throw new AssertionError("Unable to construct intent list for: " + readString);
            }
        }

        public void appendComponentShortName(StringBuilder sb) {
            ComponentName.appendShortString(sb, this.owner.applicationInfo.packageName, this.className);
        }

        public void printComponentShortName(PrintWriter printWriter) {
            ComponentName.printShortString(printWriter, this.owner.applicationInfo.packageName, this.className);
        }

        public void setPackageName(String str) {
            this.componentName = null;
            this.componentShortName = null;
        }
    }

    public static final class Permission extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Permission>() { // from class: android.content.pm.PackageParser.Permission.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Permission createFromParcel(Parcel parcel) {
                return new Permission(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Permission[] newArray(int i) {
                return new Permission[i];
            }
        };
        public PermissionGroup group;
        public final PermissionInfo info;
        public boolean tree;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Permission(Package r1, String str) {
            super(r1);
            this.info = new PermissionInfo(str);
        }

        public Permission(Package r1, PermissionInfo permissionInfo) {
            super(r1);
            this.info = permissionInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public String toString() {
            return "Permission{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.info.name + "}";
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i);
            parcel.writeInt(this.tree ? 1 : 0);
            parcel.writeParcelable(this.group, i);
        }

        public boolean isAppOp() {
            return this.info.isAppOp();
        }

        private Permission(Parcel parcel) {
            super(parcel);
            ClassLoader classLoader = Object.class.getClassLoader();
            PermissionInfo permissionInfo = (PermissionInfo) parcel.readParcelable(classLoader, PermissionInfo.class);
            this.info = permissionInfo;
            if (permissionInfo.group != null) {
                permissionInfo.group = permissionInfo.group.intern();
            }
            this.tree = parcel.readInt() == 1;
            this.group = (PermissionGroup) parcel.readParcelable(classLoader, PermissionGroup.class);
        }
    }

    public static final class PermissionGroup extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<PermissionGroup>() { // from class: android.content.pm.PackageParser.PermissionGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionGroup createFromParcel(Parcel parcel) {
                return new PermissionGroup(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionGroup[] newArray(int i) {
                return new PermissionGroup[i];
            }
        };
        public final PermissionGroupInfo info;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PermissionGroup(Package r1, int i, int i2, int i3) {
            super(r1);
            this.info = new PermissionGroupInfo(i, i2, i3);
        }

        public PermissionGroup(Package r1, PermissionGroupInfo permissionGroupInfo) {
            super(r1);
            this.info = permissionGroupInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public String toString() {
            return "PermissionGroup{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.info.name + "}";
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i);
        }

        private PermissionGroup(Parcel parcel) {
            super(parcel);
            this.info = (PermissionGroupInfo) parcel.readParcelable(Object.class.getClassLoader(), PermissionGroupInfo.class);
        }
    }

    private static boolean copyNeeded(int i, Package r4, FrameworkPackageUserState frameworkPackageUserState, Bundle bundle, int i2) {
        if (i2 != 0) {
            return true;
        }
        if (frameworkPackageUserState.getEnabledState() != 0) {
            if (r4.applicationInfo.enabled != (frameworkPackageUserState.getEnabledState() == 1)) {
                return true;
            }
        }
        if (frameworkPackageUserState.isSuspended() != ((r4.applicationInfo.flags & 1073741824) != 0) || !frameworkPackageUserState.isInstalled() || frameworkPackageUserState.isHidden() || frameworkPackageUserState.isStopped() || frameworkPackageUserState.isInstantApp() != r4.applicationInfo.isInstantApp()) {
            return true;
        }
        if ((i & 128) != 0 && (bundle != null || r4.mAppMetaData != null)) {
            return true;
        }
        int i3 = i & 1024;
        if (i3 == 0 || r4.usesLibraryFiles == null) {
            return ((i3 == 0 || r4.usesLibraryInfos == null) && r4.staticSharedLibName == null) ? false : true;
        }
        return true;
    }

    public static ApplicationInfo generateApplicationInfo(Package r1, int i, FrameworkPackageUserState frameworkPackageUserState) {
        return generateApplicationInfo(r1, i, frameworkPackageUserState, UserHandle.getCallingUserId());
    }

    private static void updateApplicationInfo(ApplicationInfo applicationInfo, int i, FrameworkPackageUserState frameworkPackageUserState) {
        if (!sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        if (frameworkPackageUserState.isInstalled()) {
            applicationInfo.flags |= 8388608;
        } else {
            applicationInfo.flags &= -8388609;
        }
        if (frameworkPackageUserState.isSuspended()) {
            applicationInfo.flags |= 1073741824;
        } else {
            applicationInfo.flags &= -1073741825;
        }
        if (frameworkPackageUserState.isInstantApp()) {
            applicationInfo.privateFlags |= 128;
        } else {
            applicationInfo.privateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
        if (frameworkPackageUserState.isVirtualPreload()) {
            applicationInfo.privateFlags |= 65536;
        } else {
            applicationInfo.privateFlags &= -65537;
        }
        boolean z = true;
        if (frameworkPackageUserState.isHidden()) {
            applicationInfo.privateFlags |= 1;
        } else {
            applicationInfo.privateFlags &= -2;
        }
        if (frameworkPackageUserState.getEnabledState() == 1) {
            applicationInfo.enabled = true;
        } else if (frameworkPackageUserState.getEnabledState() == 4) {
            if ((32768 & i) == 0 && (i & 536870912) == 0) {
                z = false;
            }
            applicationInfo.enabled = z;
        } else if (frameworkPackageUserState.getEnabledState() == 2 || frameworkPackageUserState.getEnabledState() == 3) {
            applicationInfo.enabled = false;
        }
        applicationInfo.enabledSetting = frameworkPackageUserState.getEnabledState();
        if (applicationInfo.category == -1) {
            applicationInfo.category = FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser = getSeinfoUser(frameworkPackageUserState);
        OverlayPaths allOverlayPaths = frameworkPackageUserState.getAllOverlayPaths();
        if (allOverlayPaths != null) {
            applicationInfo.resourceDirs = (String[]) allOverlayPaths.getResourceDirs().toArray(new String[0]);
            applicationInfo.overlayPaths = (String[]) allOverlayPaths.getOverlayPaths().toArray(new String[0]);
        }
        applicationInfo.icon = (!sUseRoundIcon || applicationInfo.roundIconRes == 0) ? applicationInfo.iconRes : applicationInfo.roundIconRes;
    }

    public static ApplicationInfo generateApplicationInfo(Package r2, int i, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        ApplicationInfo applicationInfo = null;
        if (r2 == null) {
            return null;
        }
        if (checkUseInstalledOrHidden(i, frameworkPackageUserState, r2.applicationInfo) && r2.isMatch(i)) {
            if (!copyNeeded(i, r2, frameworkPackageUserState, null, i2) && ((32768 & i) == 0 || frameworkPackageUserState.getEnabledState() != 4)) {
                updateApplicationInfo(r2.applicationInfo, i, frameworkPackageUserState);
                return r2.applicationInfo;
            }
            applicationInfo = new ApplicationInfo(r2.applicationInfo);
            applicationInfo.initForUser(i2);
            if ((i & 128) != 0) {
                applicationInfo.metaData = r2.mAppMetaData;
            }
            if ((i & 1024) != 0) {
                applicationInfo.sharedLibraryFiles = r2.usesLibraryFiles;
                applicationInfo.sharedLibraryInfos = r2.usesLibraryInfos;
            }
            if (frameworkPackageUserState.isStopped()) {
                applicationInfo.flags |= 2097152;
            } else {
                applicationInfo.flags &= -2097153;
            }
            updateApplicationInfo(applicationInfo, i, frameworkPackageUserState);
        }
        return applicationInfo;
    }

    public static ApplicationInfo generateApplicationInfo(ApplicationInfo applicationInfo, int i, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        if (applicationInfo == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, applicationInfo)) {
            return null;
        }
        ApplicationInfo applicationInfo2 = new ApplicationInfo(applicationInfo);
        applicationInfo2.initForUser(i2);
        if (frameworkPackageUserState.isStopped()) {
            applicationInfo2.flags |= 2097152;
        } else {
            applicationInfo2.flags &= -2097153;
        }
        updateApplicationInfo(applicationInfo2, i, frameworkPackageUserState);
        return applicationInfo2;
    }

    public static final PermissionInfo generatePermissionInfo(Permission permission, int i) {
        if (permission == null) {
            return null;
        }
        if ((i & 128) == 0) {
            return permission.info;
        }
        PermissionInfo permissionInfo = new PermissionInfo(permission.info);
        permissionInfo.metaData = permission.metaData;
        return permissionInfo;
    }

    public static final PermissionGroupInfo generatePermissionGroupInfo(PermissionGroup permissionGroup, int i) {
        if (permissionGroup == null) {
            return null;
        }
        if ((i & 128) == 0) {
            return permissionGroup.info;
        }
        PermissionGroupInfo permissionGroupInfo = new PermissionGroupInfo(permissionGroup.info);
        permissionGroupInfo.metaData = permissionGroup.metaData;
        return permissionGroupInfo;
    }

    public static final class Activity extends Component<ActivityIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Activity>() { // from class: android.content.pm.PackageParser.Activity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Activity createFromParcel(Parcel parcel) {
                return new Activity(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Activity[] newArray(int i) {
                return new Activity[i];
            }
        };
        public final ActivityInfo info;
        private boolean mHasMaxAspectRatio;
        private boolean mHasMinAspectRatio;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMaxAspectRatio() {
            return this.mHasMaxAspectRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMinAspectRatio() {
            return this.mHasMinAspectRatio;
        }

        Activity(Package r3, String str, ActivityInfo activityInfo) {
            super(r3, new ArrayList(0), str);
            this.info = activityInfo;
            activityInfo.applicationInfo = r3.applicationInfo;
        }

        public Activity(ParseComponentArgs parseComponentArgs, ActivityInfo activityInfo) {
            super(parseComponentArgs, (ComponentInfo) activityInfo);
            this.info = activityInfo;
            activityInfo.applicationInfo = parseComponentArgs.owner.applicationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMaxAspectRatio(float f) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (f >= 1.0f || f == 0.0f) {
                this.info.setMaxAspectRatio(f);
                this.mHasMaxAspectRatio = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMinAspectRatio(float f) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (f >= 1.0f || f == 0.0f) {
                this.info.setMinAspectRatio(f);
                this.mHasMinAspectRatio = true;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Activity{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
            parcel.writeBoolean(this.mHasMaxAspectRatio);
            parcel.writeBoolean(this.mHasMinAspectRatio);
        }

        private Activity(Parcel parcel) {
            super(parcel);
            this.info = (ActivityInfo) parcel.readParcelable(Object.class.getClassLoader(), ActivityInfo.class);
            this.mHasMaxAspectRatio = parcel.readBoolean();
            this.mHasMinAspectRatio = parcel.readBoolean();
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ActivityIntentInfo activityIntentInfo = (ActivityIntentInfo) it.next();
                activityIntentInfo.activity = this;
                this.order = Math.max(activityIntentInfo.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                ActivityInfo activityInfo = this.info;
                activityInfo.permission = activityInfo.permission.intern();
            }
        }
    }

    public static final ActivityInfo generateActivityInfo(Activity activity, int i, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generateActivityInfo(activity, i, frameworkPackageUserState, i2, null);
    }

    private static ActivityInfo generateActivityInfo(Activity activity, int i, FrameworkPackageUserState frameworkPackageUserState, int i2, ApplicationInfo applicationInfo) {
        if (activity == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, activity.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(i, activity.owner, frameworkPackageUserState, activity.metaData, i2)) {
            updateApplicationInfo(activity.info.applicationInfo, i, frameworkPackageUserState);
            return activity.info;
        }
        ActivityInfo activityInfo = new ActivityInfo(activity.info);
        activityInfo.metaData = activity.metaData;
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(activity.owner, i, frameworkPackageUserState, i2);
        }
        activityInfo.applicationInfo = applicationInfo;
        return activityInfo;
    }

    public static final ActivityInfo generateActivityInfo(ActivityInfo activityInfo, int i, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        if (activityInfo == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, activityInfo.applicationInfo)) {
            return null;
        }
        ActivityInfo activityInfo2 = new ActivityInfo(activityInfo);
        activityInfo2.applicationInfo = generateApplicationInfo(activityInfo2.applicationInfo, i, frameworkPackageUserState, i2);
        return activityInfo2;
    }

    public static final class Service extends Component<ServiceIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Service>() { // from class: android.content.pm.PackageParser.Service.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Service createFromParcel(Parcel parcel) {
                return new Service(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Service[] newArray(int i) {
                return new Service[i];
            }
        };
        public final ServiceInfo info;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Service(ParseComponentArgs parseComponentArgs, ServiceInfo serviceInfo) {
            super(parseComponentArgs, (ComponentInfo) serviceInfo);
            this.info = serviceInfo;
            serviceInfo.applicationInfo = parseComponentArgs.owner.applicationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Service{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
        }

        private Service(Parcel parcel) {
            super(parcel);
            this.info = (ServiceInfo) parcel.readParcelable(Object.class.getClassLoader(), ServiceInfo.class);
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ServiceIntentInfo serviceIntentInfo = (ServiceIntentInfo) it.next();
                serviceIntentInfo.service = this;
                this.order = Math.max(serviceIntentInfo.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                ServiceInfo serviceInfo = this.info;
                serviceInfo.permission = serviceInfo.permission.intern();
            }
        }
    }

    public static final ServiceInfo generateServiceInfo(Service service, int i, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generateServiceInfo(service, i, frameworkPackageUserState, i2, null);
    }

    private static ServiceInfo generateServiceInfo(Service service, int i, FrameworkPackageUserState frameworkPackageUserState, int i2, ApplicationInfo applicationInfo) {
        if (service == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, service.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(i, service.owner, frameworkPackageUserState, service.metaData, i2)) {
            updateApplicationInfo(service.info.applicationInfo, i, frameworkPackageUserState);
            return service.info;
        }
        ServiceInfo serviceInfo = new ServiceInfo(service.info);
        serviceInfo.metaData = service.metaData;
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(service.owner, i, frameworkPackageUserState, i2);
        }
        serviceInfo.applicationInfo = applicationInfo;
        return serviceInfo;
    }

    public static final class Provider extends Component<ProviderIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Provider>() { // from class: android.content.pm.PackageParser.Provider.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Provider createFromParcel(Parcel parcel) {
                return new Provider(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Provider[] newArray(int i) {
                return new Provider[i];
            }
        };
        public final ProviderInfo info;
        public boolean syncable;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Provider(ParseComponentArgs parseComponentArgs, ProviderInfo providerInfo) {
            super(parseComponentArgs, (ComponentInfo) providerInfo);
            this.info = providerInfo;
            providerInfo.applicationInfo = parseComponentArgs.owner.applicationInfo;
            this.syncable = false;
        }

        public Provider(Provider provider) {
            super(provider);
            this.info = provider.info;
            this.syncable = provider.syncable;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Provider{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
            parcel.writeInt(this.syncable ? 1 : 0);
        }

        private Provider(Parcel parcel) {
            super(parcel);
            this.info = (ProviderInfo) parcel.readParcelable(Object.class.getClassLoader(), ProviderInfo.class);
            this.syncable = parcel.readInt() == 1;
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ((ProviderIntentInfo) it.next()).provider = this;
            }
            if (this.info.readPermission != null) {
                ProviderInfo providerInfo = this.info;
                providerInfo.readPermission = providerInfo.readPermission.intern();
            }
            if (this.info.writePermission != null) {
                ProviderInfo providerInfo2 = this.info;
                providerInfo2.writePermission = providerInfo2.writePermission.intern();
            }
            if (this.info.authority != null) {
                ProviderInfo providerInfo3 = this.info;
                providerInfo3.authority = providerInfo3.authority.intern();
            }
        }
    }

    public static final ProviderInfo generateProviderInfo(Provider provider, int i, FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generateProviderInfo(provider, i, frameworkPackageUserState, i2, null);
    }

    private static ProviderInfo generateProviderInfo(Provider provider, int i, FrameworkPackageUserState frameworkPackageUserState, int i2, ApplicationInfo applicationInfo) {
        if (provider == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, provider.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(i, provider.owner, frameworkPackageUserState, provider.metaData, i2) && ((i & 2048) != 0 || provider.info.uriPermissionPatterns == null)) {
            updateApplicationInfo(provider.info.applicationInfo, i, frameworkPackageUserState);
            return provider.info;
        }
        ProviderInfo providerInfo = new ProviderInfo(provider.info);
        providerInfo.metaData = provider.metaData;
        if ((i & 2048) == 0) {
            providerInfo.uriPermissionPatterns = null;
        }
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(provider.owner, i, frameworkPackageUserState, i2);
        }
        providerInfo.applicationInfo = applicationInfo;
        return providerInfo;
    }

    public static final class Instrumentation extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Instrumentation>() { // from class: android.content.pm.PackageParser.Instrumentation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Instrumentation createFromParcel(Parcel parcel) {
                return new Instrumentation(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Instrumentation[] newArray(int i) {
                return new Instrumentation[i];
            }
        };
        public final InstrumentationInfo info;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Instrumentation(ParsePackageItemArgs parsePackageItemArgs, InstrumentationInfo instrumentationInfo) {
            super(parsePackageItemArgs, instrumentationInfo);
            this.info = instrumentationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Instrumentation{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i);
        }

        private Instrumentation(Parcel parcel) {
            super(parcel);
            InstrumentationInfo instrumentationInfo = (InstrumentationInfo) parcel.readParcelable(Object.class.getClassLoader(), InstrumentationInfo.class);
            this.info = instrumentationInfo;
            if (instrumentationInfo.targetPackage != null) {
                instrumentationInfo.targetPackage = instrumentationInfo.targetPackage.intern();
            }
            if (instrumentationInfo.targetProcesses != null) {
                instrumentationInfo.targetProcesses = instrumentationInfo.targetProcesses.intern();
            }
        }
    }

    public static final InstrumentationInfo generateInstrumentationInfo(Instrumentation instrumentation, int i) {
        if (instrumentation == null) {
            return null;
        }
        if ((i & 128) == 0) {
            return instrumentation.info;
        }
        InstrumentationInfo instrumentationInfo = new InstrumentationInfo(instrumentation.info);
        instrumentationInfo.metaData = instrumentation.metaData;
        return instrumentationInfo;
    }

    public static abstract class IntentInfo extends IntentFilter {
        public int banner;
        public boolean hasDefault;
        public int icon;
        public int labelRes;
        public int logo;
        public CharSequence nonLocalizedLabel;
        public int preferred;

        protected IntentInfo() {
        }

        protected IntentInfo(Parcel parcel) {
            super(parcel);
            this.hasDefault = parcel.readInt() == 1;
            this.labelRes = parcel.readInt();
            this.nonLocalizedLabel = parcel.readCharSequence();
            this.icon = parcel.readInt();
            this.logo = parcel.readInt();
            this.banner = parcel.readInt();
            this.preferred = parcel.readInt();
        }

        public void writeIntentInfoToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.hasDefault ? 1 : 0);
            parcel.writeInt(this.labelRes);
            parcel.writeCharSequence(this.nonLocalizedLabel);
            parcel.writeInt(this.icon);
            parcel.writeInt(this.logo);
            parcel.writeInt(this.banner);
            parcel.writeInt(this.preferred);
        }
    }

    public static final class ActivityIntentInfo extends IntentInfo {
        public Activity activity;

        public ActivityIntentInfo(Activity activity) {
            this.activity = activity;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ActivityIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            this.activity.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ActivityIntentInfo(Parcel parcel) {
            super(parcel);
        }
    }

    public static final class ServiceIntentInfo extends IntentInfo {
        public Service service;

        public ServiceIntentInfo(Service service) {
            this.service = service;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ServiceIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            this.service.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ServiceIntentInfo(Parcel parcel) {
            super(parcel);
        }
    }

    public static final class ProviderIntentInfo extends IntentInfo {
        public Provider provider;

        public ProviderIntentInfo(Provider provider) {
            this.provider = provider;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("ProviderIntentInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            this.provider.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ProviderIntentInfo(Parcel parcel) {
            super(parcel);
        }
    }

    public static void setCompatibilityModeEnabled(boolean z) {
        sCompatibilityModeEnabled = z;
    }

    public static void readConfigUseRoundIcon(Resources resources) {
        if (resources != null) {
            sUseRoundIcon = resources.getBoolean(R.bool.config_useRoundIcon);
            return;
        }
        try {
            ApplicationInfo applicationInfo = ActivityThread.getPackageManager().getApplicationInfo("android", 0L, UserHandle.myUserId());
            Resources system = Resources.getSystem();
            sUseRoundIcon = ResourcesManager.getInstance().getResources(null, null, null, applicationInfo.resourceDirs, applicationInfo.overlayPaths, applicationInfo.sharedLibraryFiles, null, null, system.getCompatibilityInfo(), system.getClassLoader(), null).getBoolean(R.bool.config_useRoundIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class PackageParserException extends Exception {
        public final int error;

        public PackageParserException(int i, String str) {
            super(str);
            this.error = i;
        }

        public PackageParserException(int i, String str, Throwable th) {
            super(str, th);
            this.error = i;
        }
    }

    @Deprecated
    private static abstract class SplitDependencyLoader<E extends Exception> {
        private final SparseArray<int[]> mDependencies;

        protected abstract void constructSplit(int i, int[] iArr, int i2) throws Exception;

        protected abstract boolean isSplitCached(int i);

        protected SplitDependencyLoader(SparseArray<int[]> sparseArray) {
            this.mDependencies = sparseArray;
        }

        protected void loadDependenciesForSplit(int i) throws Exception {
            if (isSplitCached(i)) {
                return;
            }
            if (i == 0) {
                constructSplit(0, collectConfigSplitIndices(0), -1);
                return;
            }
            IntArray intArray = new IntArray();
            intArray.add(i);
            while (true) {
                int[] iArr = this.mDependencies.get(i);
                i = (iArr == null || iArr.length <= 0) ? -1 : iArr[0];
                if (i < 0 || isSplitCached(i)) {
                    break;
                } else {
                    intArray.add(i);
                }
            }
            int size = intArray.size() - 1;
            while (size >= 0) {
                int i2 = intArray.get(size);
                constructSplit(i2, collectConfigSplitIndices(i2), i);
                size--;
                i = i2;
            }
        }

        private int[] collectConfigSplitIndices(int i) {
            int[] iArr = this.mDependencies.get(i);
            if (iArr == null || iArr.length <= 1) {
                return EmptyArray.INT;
            }
            return Arrays.copyOfRange(iArr, 1, iArr.length);
        }

        public static class IllegalDependencyException extends Exception {
            private IllegalDependencyException(String str) {
                super(str);
            }
        }

        private static int[] append(int[] iArr, int i) {
            if (iArr == null) {
                return new int[]{i};
            }
            int[] copyOf = Arrays.copyOf(iArr, iArr.length + 1);
            copyOf[iArr.length] = i;
            return copyOf;
        }

        public static SparseArray<int[]> createDependenciesFromPackage(PackageLite packageLite) throws IllegalDependencyException {
            int i;
            int i2;
            SparseArray<int[]> sparseArray = new SparseArray<>();
            sparseArray.put(0, new int[]{-1});
            int i3 = 0;
            while (true) {
                if (i3 < packageLite.splitNames.length) {
                    if (packageLite.isFeatureSplits[i3]) {
                        String str = packageLite.usesSplitNames[i3];
                        if (str != null) {
                            int binarySearch = Arrays.binarySearch(packageLite.splitNames, str);
                            if (binarySearch < 0) {
                                throw new IllegalDependencyException("Split '" + packageLite.splitNames[i3] + "' requires split '" + str + "', which is missing.");
                            }
                            i2 = binarySearch + 1;
                        } else {
                            i2 = 0;
                        }
                        sparseArray.put(i3 + 1, new int[]{i2});
                    }
                    i3++;
                } else {
                    int length = packageLite.splitNames.length;
                    for (int i4 = 0; i4 < length; i4++) {
                        if (!packageLite.isFeatureSplits[i4]) {
                            String str2 = packageLite.configForSplit[i4];
                            if (str2 != null) {
                                int binarySearch2 = Arrays.binarySearch(packageLite.splitNames, str2);
                                if (binarySearch2 < 0) {
                                    throw new IllegalDependencyException("Split '" + packageLite.splitNames[i4] + "' targets split '" + str2 + "', which is missing.");
                                }
                                if (!packageLite.isFeatureSplits[binarySearch2]) {
                                    throw new IllegalDependencyException("Split '" + packageLite.splitNames[i4] + "' declares itself as configuration split for a non-feature split '" + packageLite.splitNames[binarySearch2] + "'");
                                }
                                i = binarySearch2 + 1;
                            } else {
                                i = 0;
                            }
                            sparseArray.put(i, append(sparseArray.get(i), i4 + 1));
                        }
                    }
                    BitSet bitSet = new BitSet();
                    int size = sparseArray.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        int keyAt = sparseArray.keyAt(i5);
                        bitSet.clear();
                        while (keyAt != -1) {
                            if (bitSet.get(keyAt)) {
                                throw new IllegalDependencyException("Cycle detected in split dependencies.");
                            }
                            bitSet.set(keyAt);
                            int[] iArr = sparseArray.get(keyAt);
                            keyAt = iArr != null ? iArr[0] : -1;
                        }
                    }
                    return sparseArray;
                }
            }
        }
    }

    @Deprecated
    private static class DefaultSplitAssetLoader implements SplitAssetLoader {
        private ApkAssets mBaseApkAssets;
        private final String mBaseCodePath;
        private AssetManager mCachedAssetManager;
        private final int mFlags;
        private final String[] mSplitCodePaths;

        DefaultSplitAssetLoader(PackageLite packageLite, int i) {
            this.mBaseCodePath = packageLite.baseCodePath;
            this.mSplitCodePaths = packageLite.splitCodePaths;
            this.mFlags = i;
        }

        private static ApkAssets loadApkAssets(String str, int i) throws PackageParserException {
            if ((i & 1) != 0 && !PackageParser.isApkPath(str)) {
                throw new PackageParserException(-100, "Invalid package file: " + str);
            }
            try {
                return ApkAssets.loadFromPath(str);
            } catch (IOException e) {
                throw new PackageParserException(-2, "Failed to load APK at path " + str, e);
            }
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getBaseAssetManager() throws PackageParserException {
            AssetManager assetManager = this.mCachedAssetManager;
            if (assetManager != null) {
                return assetManager;
            }
            String[] strArr = this.mSplitCodePaths;
            int i = 1;
            ApkAssets[] apkAssetsArr = new ApkAssets[(strArr != null ? strArr.length : 0) + 1];
            ApkAssets loadApkAssets = loadApkAssets(this.mBaseCodePath, this.mFlags);
            this.mBaseApkAssets = loadApkAssets;
            apkAssetsArr[0] = loadApkAssets;
            if (!ArrayUtils.isEmpty(this.mSplitCodePaths)) {
                String[] strArr2 = this.mSplitCodePaths;
                int length = strArr2.length;
                int i2 = 0;
                while (i2 < length) {
                    apkAssetsArr[i] = loadApkAssets(strArr2[i2], this.mFlags);
                    i2++;
                    i++;
                }
            }
            AssetManager assetManager2 = new AssetManager();
            assetManager2.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
            assetManager2.setApkAssets(apkAssetsArr, false);
            this.mCachedAssetManager = assetManager2;
            return assetManager2;
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getSplitAssetManager(int i) throws PackageParserException {
            return getBaseAssetManager();
        }

        @Override // java.lang.AutoCloseable
        public void close() throws Exception {
            IoUtils.closeQuietly(this.mCachedAssetManager);
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public ApkAssets getBaseApkAssets() {
            return this.mBaseApkAssets;
        }
    }

    @Deprecated
    private static class SplitAssetDependencyLoader extends SplitDependencyLoader<PackageParserException> implements SplitAssetLoader {
        private final AssetManager[] mCachedAssetManagers;
        private final ApkAssets[][] mCachedSplitApks;
        private final int mFlags;
        private final String[] mSplitPaths;

        SplitAssetDependencyLoader(PackageLite packageLite, SparseArray<int[]> sparseArray, int i) {
            super(sparseArray);
            String[] strArr = new String[packageLite.splitCodePaths.length + 1];
            this.mSplitPaths = strArr;
            strArr[0] = packageLite.baseCodePath;
            System.arraycopy(packageLite.splitCodePaths, 0, strArr, 1, packageLite.splitCodePaths.length);
            this.mFlags = i;
            this.mCachedSplitApks = new ApkAssets[strArr.length][];
            this.mCachedAssetManagers = new AssetManager[strArr.length];
        }

        @Override // android.content.pm.PackageParser.SplitDependencyLoader
        protected boolean isSplitCached(int i) {
            return this.mCachedAssetManagers[i] != null;
        }

        private static ApkAssets loadApkAssets(String str, int i) throws PackageParserException {
            if ((i & 1) != 0 && !PackageParser.isApkPath(str)) {
                throw new PackageParserException(-100, "Invalid package file: " + str);
            }
            try {
                return ApkAssets.loadFromPath(str);
            } catch (IOException e) {
                throw new PackageParserException(-2, "Failed to load APK at path " + str, e);
            }
        }

        private static AssetManager createAssetManagerWithAssets(ApkAssets[] apkAssetsArr) {
            AssetManager assetManager = new AssetManager();
            assetManager.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
            assetManager.setApkAssets(apkAssetsArr, false);
            return assetManager;
        }

        @Override // android.content.pm.PackageParser.SplitDependencyLoader
        protected void constructSplit(int i, int[] iArr, int i2) throws PackageParserException {
            ArrayList arrayList = new ArrayList();
            if (i2 >= 0) {
                Collections.addAll(arrayList, this.mCachedSplitApks[i2]);
            }
            arrayList.add(loadApkAssets(this.mSplitPaths[i], this.mFlags));
            for (int i3 : iArr) {
                arrayList.add(loadApkAssets(this.mSplitPaths[i3], this.mFlags));
            }
            this.mCachedSplitApks[i] = (ApkAssets[]) arrayList.toArray(new ApkAssets[arrayList.size()]);
            this.mCachedAssetManagers[i] = createAssetManagerWithAssets(this.mCachedSplitApks[i]);
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getBaseAssetManager() throws PackageParserException {
            loadDependenciesForSplit(0);
            return this.mCachedAssetManagers[0];
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getSplitAssetManager(int i) throws PackageParserException {
            int i2 = i + 1;
            loadDependenciesForSplit(i2);
            return this.mCachedAssetManagers[i2];
        }

        @Override // java.lang.AutoCloseable
        public void close() throws Exception {
            for (AssetManager assetManager : this.mCachedAssetManagers) {
                IoUtils.closeQuietly(assetManager);
            }
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public ApkAssets getBaseApkAssets() {
            return this.mCachedSplitApks[0][0];
        }
    }

    public static boolean isMatch(FrameworkPackageUserState frameworkPackageUserState, ComponentInfo componentInfo, long j) {
        return isMatch(frameworkPackageUserState, componentInfo.applicationInfo.isSystemApp(), componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.directBootAware, componentInfo.name, j);
    }

    public static boolean isMatch(FrameworkPackageUserState frameworkPackageUserState, boolean z, boolean z2, ComponentInfo componentInfo, long j) {
        return isMatch(frameworkPackageUserState, z, z2, componentInfo.isEnabled(), componentInfo.directBootAware, componentInfo.name, j);
    }

    public static boolean isMatch(FrameworkPackageUserState frameworkPackageUserState, boolean z, boolean z2, boolean z3, boolean z4, String str, long j) {
        boolean z5 = true;
        boolean z6 = (4202496 & j) != 0;
        if (!isAvailable(frameworkPackageUserState, j) && (!z || !z6)) {
            return reportIfDebug(false, j);
        }
        if (!isEnabled(frameworkPackageUserState, z2, z3, str, j)) {
            return reportIfDebug(false, j);
        }
        if ((1048576 & j) != 0 && !z) {
            return reportIfDebug(false, j);
        }
        boolean z7 = ((262144 & j) == 0 || z4) ? false : true;
        boolean z8 = (524288 & j) != 0 && z4;
        if (!z7 && !z8) {
            z5 = false;
        }
        return reportIfDebug(z5, j);
    }

    public static boolean isAvailable(FrameworkPackageUserState frameworkPackageUserState, long j) {
        return (((4194304 & j) > 0L ? 1 : ((4194304 & j) == 0L ? 0 : -1)) != 0) || (frameworkPackageUserState.isInstalled() && (!frameworkPackageUserState.isHidden() || (((j & 8192) > 0L ? 1 : ((j & 8192) == 0L ? 0 : -1)) != 0)));
    }

    public static boolean isEnabled(FrameworkPackageUserState frameworkPackageUserState, ComponentInfo componentInfo, long j) {
        return isEnabled(frameworkPackageUserState, componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.name, j);
    }

    public static boolean isEnabled(FrameworkPackageUserState frameworkPackageUserState, boolean z, ComponentInfo componentInfo, long j) {
        return isEnabled(frameworkPackageUserState, z, componentInfo.isEnabled(), componentInfo.name, j);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
    
        if ((r11 & 32768) == 0) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isEnabled(android.content.pm.pkg.FrameworkPackageUserState r7, boolean r8, boolean r9, java.lang.String r10, long r11) {
        /*
            r0 = 512(0x200, double:2.53E-321)
            long r0 = r0 & r11
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            if (r0 == 0) goto Lb
            return r1
        Lb:
            int r0 = r7.getEnabledState()
            r4 = 0
            if (r0 == 0) goto L25
            r5 = 2
            if (r0 == r5) goto L24
            r5 = 3
            if (r0 == r5) goto L24
            r5 = 4
            if (r0 == r5) goto L1c
            goto L28
        L1c:
            r5 = 32768(0x8000, double:1.61895E-319)
            long r11 = r11 & r5
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 != 0) goto L25
        L24:
            return r4
        L25:
            if (r8 != 0) goto L28
            return r4
        L28:
            boolean r8 = r7.isComponentEnabled(r10)
            if (r8 == 0) goto L2f
            return r1
        L2f:
            boolean r7 = r7.isComponentDisabled(r10)
            if (r7 == 0) goto L36
            return r4
        L36:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.isEnabled(android.content.pm.pkg.FrameworkPackageUserState, boolean, boolean, java.lang.String, long):boolean");
    }

    public static void writeKeySetMapping(Parcel parcel, Map<String, ArraySet<PublicKey>> map) {
        if (map == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(map.size());
        for (String str : map.keySet()) {
            parcel.writeString(str);
            ArraySet<PublicKey> arraySet = map.get(str);
            if (arraySet == null) {
                parcel.writeInt(-1);
            } else {
                int size = arraySet.size();
                parcel.writeInt(size);
                for (int i = 0; i < size; i++) {
                    parcel.writeSerializable(arraySet.valueAt(i));
                }
            }
        }
    }

    public static ArrayMap<String, ArraySet<PublicKey>> readKeySetMapping(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == -1) {
            return null;
        }
        ArrayMap<String, ArraySet<PublicKey>> arrayMap = new ArrayMap<>();
        for (int i = 0; i < readInt; i++) {
            String readString = parcel.readString();
            int readInt2 = parcel.readInt();
            if (readInt2 == -1) {
                arrayMap.put(readString, null);
            } else {
                ArraySet<PublicKey> arraySet = new ArraySet<>(readInt2);
                for (int i2 = 0; i2 < readInt2; i2++) {
                    arraySet.add((PublicKey) parcel.readSerializable(PublicKey.class.getClassLoader(), PublicKey.class));
                }
                arrayMap.put(readString, arraySet);
            }
        }
        return arrayMap;
    }

    public static String getSeinfoUser(FrameworkPackageUserState frameworkPackageUserState) {
        if (frameworkPackageUserState.isInstantApp()) {
            return ":ephemeralapp:complete";
        }
        return SEInfoUtil.COMPLETE_STR;
    }
}
