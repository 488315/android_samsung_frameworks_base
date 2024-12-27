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
import android.os.PatternMatcher;
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
import com.sec.android.iaft.SmLib_IafdConstant;
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
import java.security.spec.EncodedKeySpec;
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
import org.xmlpull.v1.XmlPullParser;
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
    private boolean mOnlyCoreApps;
    private ParsePackageItemArgs mParseInstrumentationArgs;
    public String[] mSeparateProcesses;
    public int mParseError = 1;
    private DisplayMetrics mMetrics = new DisplayMetrics();

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

    static {
        MULTI_PACKAGE_APK_ENABLED = Build.IS_DEBUGGABLE && SystemProperties.getBoolean(PROPERTY_CHILD_PACKAGES_ENABLED, false);
        CHILD_PACKAGE_TAGS = new ArraySet();
        CHILD_PACKAGE_TAGS.add("application");
        CHILD_PACKAGE_TAGS.add("compatible-screens");
        CHILD_PACKAGE_TAGS.add("eat-comment");
        CHILD_PACKAGE_TAGS.add("feature-group");
        CHILD_PACKAGE_TAGS.add("instrumentation");
        CHILD_PACKAGE_TAGS.add("supports-screens");
        CHILD_PACKAGE_TAGS.add("supports-input");
        CHILD_PACKAGE_TAGS.add("uses-configuration");
        CHILD_PACKAGE_TAGS.add("uses-feature");
        CHILD_PACKAGE_TAGS.add("uses-gl-texture");
        CHILD_PACKAGE_TAGS.add("uses-permission");
        CHILD_PACKAGE_TAGS.add("uses-permission-sdk-23");
        CHILD_PACKAGE_TAGS.add("uses-permission-sdk-m");
        CHILD_PACKAGE_TAGS.add("uses-sdk");
        SAFE_BROADCASTS = new ArraySet();
        SAFE_BROADCASTS.add("android.intent.action.BOOT_COMPLETED");
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

        public NewPermissionInfo(String name, int sdkVersion, int fileVersion) {
            this.name = name;
            this.sdkVersion = sdkVersion;
            this.fileVersion = fileVersion;
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

        ParsePackageItemArgs(Package _owner, String[] _outError, int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes, int _bannerRes) {
            this.owner = _owner;
            this.outError = _outError;
            this.nameRes = _nameRes;
            this.labelRes = _labelRes;
            this.iconRes = _iconRes;
            this.logoRes = _logoRes;
            this.bannerRes = _bannerRes;
            this.roundIconRes = _roundIconRes;
        }
    }

    public static class ParseComponentArgs extends ParsePackageItemArgs {
        final int descriptionRes;
        final int enabledRes;
        int flags;
        final int processRes;
        final String[] sepProcesses;

        public ParseComponentArgs(Package _owner, String[] _outError, int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes, int _bannerRes, String[] _sepProcesses, int _processRes, int _descriptionRes, int _enabledRes) {
            super(_owner, _outError, _nameRes, _labelRes, _iconRes, _roundIconRes, _logoRes, _bannerRes);
            this.sepProcesses = _sepProcesses;
            this.processRes = _processRes;
            this.descriptionRes = _descriptionRes;
            this.enabledRes = _enabledRes;
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

        public PackageLite(String codePath, String baseCodePath, ApkLite baseApk, String[] splitNames, boolean[] isFeatureSplits, String[] usesSplitNames, String[] configForSplit, String[] splitCodePaths, int[] splitRevisionCodes) {
            this.packageName = baseApk.packageName;
            this.versionCode = baseApk.versionCode;
            this.versionCodeMajor = baseApk.versionCodeMajor;
            this.installLocation = baseApk.installLocation;
            this.verifiers = baseApk.verifiers;
            this.splitNames = splitNames;
            this.isFeatureSplits = isFeatureSplits;
            this.usesSplitNames = usesSplitNames;
            this.configForSplit = configForSplit;
            this.codePath = codePath;
            this.baseCodePath = baseCodePath;
            this.splitCodePaths = splitCodePaths;
            this.baseRevisionCode = baseApk.revisionCode;
            this.splitRevisionCodes = splitRevisionCodes;
            this.coreApp = baseApk.coreApp;
            this.debuggable = baseApk.debuggable;
            this.multiArch = baseApk.multiArch;
            this.use32bitAbi = baseApk.use32bitAbi;
            this.extractNativeLibs = baseApk.extractNativeLibs;
            this.isolatedSplits = baseApk.isolatedSplits;
            this.useEmbeddedDex = baseApk.useEmbeddedDex;
            this.isSplitRequired = baseApk.isSplitRequired;
            this.profilableByShell = baseApk.profilableByShell;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList<>();
            paths.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }
            return paths;
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

        public ApkLite(String codePath, String packageName, String splitName, boolean isFeatureSplit, String configForSplit, String usesSplitName, boolean isSplitRequired, int versionCode, int versionCodeMajor, int revisionCode, int installLocation, List<VerifierInfo> verifiers, SigningDetails signingDetails, boolean coreApp, boolean debuggable, boolean profilableByShell, boolean multiArch, boolean use32bitAbi, boolean useEmbeddedDex, boolean extractNativeLibs, boolean isolatedSplits, String targetPackageName, boolean overlayIsStatic, int overlayPriority, int minSdkVersion, int targetSdkVersion, int rollbackDataPolicy) {
            this.codePath = codePath;
            this.packageName = packageName;
            this.splitName = splitName;
            this.isFeatureSplit = isFeatureSplit;
            this.configForSplit = configForSplit;
            this.usesSplitName = usesSplitName;
            this.versionCode = versionCode;
            this.versionCodeMajor = versionCodeMajor;
            this.revisionCode = revisionCode;
            this.installLocation = installLocation;
            this.signingDetails = signingDetails;
            this.verifiers = (VerifierInfo[]) verifiers.toArray(new VerifierInfo[verifiers.size()]);
            this.coreApp = coreApp;
            this.debuggable = debuggable;
            this.profilableByShell = profilableByShell;
            this.multiArch = multiArch;
            this.use32bitAbi = use32bitAbi;
            this.useEmbeddedDex = useEmbeddedDex;
            this.extractNativeLibs = extractNativeLibs;
            this.isolatedSplits = isolatedSplits;
            this.isSplitRequired = isSplitRequired;
            this.targetPackageName = targetPackageName;
            this.overlayIsStatic = overlayIsStatic;
            this.overlayPriority = overlayPriority;
            this.minSdkVersion = minSdkVersion;
            this.targetSdkVersion = targetSdkVersion;
            this.rollbackDataPolicy = rollbackDataPolicy;
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
        this.mMetrics.setToDefaults();
    }

    public void setSeparateProcesses(String[] procs) {
        this.mSeparateProcesses = procs;
    }

    public void setOnlyCoreApps(boolean onlyCoreApps) {
        this.mOnlyCoreApps = onlyCoreApps;
    }

    public void setDisplayMetrics(DisplayMetrics metrics) {
        this.mMetrics = metrics;
    }

    public void setCacheDir(File cacheDir) {
        this.mCacheDir = cacheDir;
    }

    public static final class CallbackImpl implements Callback {
        private final PackageManager mPm;

        public CallbackImpl(PackageManager pm) {
            this.mPm = pm;
        }

        @Override // android.content.pm.PackageParser.Callback
        public boolean hasFeature(String feature) {
            return this.mPm.hasSystemFeature(feature);
        }
    }

    public void setCallback(Callback cb) {
        this.mCallback = cb;
    }

    public static final boolean isApkFile(File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(String path) {
        return path.endsWith(".apk");
    }

    private static boolean checkUseInstalledOrHidden(int flags, FrameworkPackageUserState state, ApplicationInfo appInfo) {
        if ((flags & 536870912) == 0 && !state.isInstalled() && appInfo != null && appInfo.hiddenUntilInstalled) {
            return false;
        }
        if (!isAvailable(state, flags)) {
            if (appInfo == null || !appInfo.isSystemApp()) {
                return false;
            }
            if ((4202496 & flags) == 0 && (536870912 & flags) == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAvailable(FrameworkPackageUserState state) {
        return checkUseInstalledOrHidden(0, state, null);
    }

    public static PackageInfo generatePackageInfo(Package p, int[] gids, int flags, long firstInstallTime, long lastUpdateTime, Set<String> grantedPermissions, FrameworkPackageUserState state) {
        return generatePackageInfo(p, gids, flags, firstInstallTime, lastUpdateTime, grantedPermissions, state, UserHandle.getCallingUserId());
    }

    public static PackageInfo generatePackageInfo(Package p, int[] gids, int flags, long firstInstallTime, long lastUpdateTime, Set<String> grantedPermissions, FrameworkPackageUserState state, int userId) {
        return generatePackageInfo(p, null, gids, flags, firstInstallTime, lastUpdateTime, grantedPermissions, state, userId);
    }

    public static PackageInfo generatePackageInfo(Package pkg, ApexInfo apexInfo, int flags) {
        return generatePackageInfo(pkg, apexInfo, EmptyArray.INT, flags, 0L, 0L, Collections.emptySet(), FrameworkPackageUserState.DEFAULT, UserHandle.getCallingUserId());
    }

    private static PackageInfo generatePackageInfo(Package p, ApexInfo apexInfo, int[] gids, int flags, long firstInstallTime, long lastUpdateTime, Set<String> grantedPermissions, FrameworkPackageUserState state, int userId) {
        ApplicationInfo applicationInfo;
        int N;
        int N2;
        int N3;
        int N4;
        int N5;
        if (!checkUseInstalledOrHidden(flags, state, p.applicationInfo) || !p.isMatch(flags)) {
            return null;
        }
        if ((flags & 15) != 0) {
            applicationInfo = generateApplicationInfo(p, flags, state, userId);
        } else {
            applicationInfo = null;
        }
        PackageInfo pi = new PackageInfo();
        pi.packageName = p.packageName;
        pi.splitNames = p.splitNames;
        pi.versionCode = p.mVersionCode;
        pi.versionCodeMajor = p.mVersionCodeMajor;
        pi.baseRevisionCode = p.baseRevisionCode;
        pi.splitRevisionCodes = p.splitRevisionCodes;
        pi.versionName = p.mVersionName;
        pi.sharedUserId = p.mSharedUserId;
        pi.sharedUserLabel = p.mSharedUserLabel;
        pi.applicationInfo = generateApplicationInfo(p, flags, state, userId);
        pi.installLocation = p.installLocation;
        pi.isStub = p.isStub;
        pi.coreApp = p.coreApp;
        if ((pi.applicationInfo.flags & 1) != 0 || (pi.applicationInfo.flags & 128) != 0) {
            pi.requiredForAllUsers = p.mRequiredForAllUsers;
        }
        pi.restrictedAccountType = p.mRestrictedAccountType;
        pi.requiredAccountType = p.mRequiredAccountType;
        pi.overlayTarget = p.mOverlayTarget;
        pi.targetOverlayableName = p.mOverlayTargetName;
        pi.overlayCategory = p.mOverlayCategory;
        pi.overlayPriority = p.mOverlayPriority;
        pi.mOverlayIsStatic = p.mOverlayIsStatic;
        pi.compileSdkVersion = p.mCompileSdkVersion;
        pi.compileSdkVersionCodename = p.mCompileSdkVersionCodename;
        pi.firstInstallTime = firstInstallTime;
        pi.lastUpdateTime = lastUpdateTime;
        if ((flags & 256) != 0) {
            pi.gids = gids;
        }
        if ((flags & 16384) != 0) {
            int N6 = p.configPreferences != null ? p.configPreferences.size() : 0;
            if (N6 > 0) {
                pi.configPreferences = new ConfigurationInfo[N6];
                p.configPreferences.toArray(pi.configPreferences);
            }
            int N7 = p.reqFeatures != null ? p.reqFeatures.size() : 0;
            if (N7 > 0) {
                pi.reqFeatures = new FeatureInfo[N7];
                p.reqFeatures.toArray(pi.reqFeatures);
            }
            int N8 = p.featureGroups != null ? p.featureGroups.size() : 0;
            if (N8 > 0) {
                pi.featureGroups = new FeatureGroupInfo[N8];
                p.featureGroups.toArray(pi.featureGroups);
            }
        }
        int N9 = flags & 1;
        if (N9 != 0 && (N5 = p.activities.size()) > 0) {
            int num = 0;
            ActivityInfo[] res = new ActivityInfo[N5];
            int i = 0;
            while (i < N5) {
                int N10 = N5;
                Activity a = p.activities.get(i);
                if (isMatch(state, a.info, flags) && !PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(a.className)) {
                    res[num] = generateActivityInfo(a, flags, state, userId, applicationInfo);
                    num++;
                }
                i++;
                N5 = N10;
            }
            pi.activities = (ActivityInfo[]) ArrayUtils.trimToSize(res, num);
        }
        int N11 = flags & 2;
        if (N11 != 0 && (N4 = p.receivers.size()) > 0) {
            int num2 = 0;
            ActivityInfo[] res2 = new ActivityInfo[N4];
            for (int i2 = 0; i2 < N4; i2++) {
                Activity a2 = p.receivers.get(i2);
                if (isMatch(state, a2.info, flags)) {
                    res2[num2] = generateActivityInfo(a2, flags, state, userId, applicationInfo);
                    num2++;
                }
            }
            pi.receivers = (ActivityInfo[]) ArrayUtils.trimToSize(res2, num2);
        }
        int N12 = flags & 4;
        if (N12 != 0 && (N3 = p.services.size()) > 0) {
            int num3 = 0;
            ServiceInfo[] res3 = new ServiceInfo[N3];
            for (int i3 = 0; i3 < N3; i3++) {
                Service s = p.services.get(i3);
                if (isMatch(state, s.info, flags)) {
                    res3[num3] = generateServiceInfo(s, flags, state, userId, applicationInfo);
                    num3++;
                }
            }
            pi.services = (ServiceInfo[]) ArrayUtils.trimToSize(res3, num3);
        }
        int N13 = flags & 8;
        if (N13 != 0 && (N2 = p.providers.size()) > 0) {
            int num4 = 0;
            ProviderInfo[] res4 = new ProviderInfo[N2];
            for (int i4 = 0; i4 < N2; i4++) {
                Provider pr = p.providers.get(i4);
                if (isMatch(state, pr.info, flags)) {
                    res4[num4] = generateProviderInfo(pr, flags, state, userId, applicationInfo);
                    num4++;
                }
            }
            pi.providers = (ProviderInfo[]) ArrayUtils.trimToSize(res4, num4);
        }
        int N14 = flags & 16;
        if (N14 != 0 && (N = p.instrumentation.size()) > 0) {
            pi.instrumentation = new InstrumentationInfo[N];
            for (int i5 = 0; i5 < N; i5++) {
                pi.instrumentation[i5] = generateInstrumentationInfo(p.instrumentation.get(i5), flags);
            }
        }
        int N15 = flags & 4096;
        if (N15 != 0) {
            int N16 = p.permissions.size();
            if (N16 > 0) {
                pi.permissions = new PermissionInfo[N16];
                for (int i6 = 0; i6 < N16; i6++) {
                    pi.permissions[i6] = generatePermissionInfo(p.permissions.get(i6), flags);
                }
            }
            int N17 = p.requestedPermissions.size();
            if (N17 > 0) {
                pi.requestedPermissions = new String[N17];
                pi.requestedPermissionsFlags = new int[N17];
                for (int i7 = 0; i7 < N17; i7++) {
                    String perm = p.requestedPermissions.get(i7);
                    pi.requestedPermissions[i7] = perm;
                    int[] iArr = pi.requestedPermissionsFlags;
                    iArr[i7] = iArr[i7] | 1;
                    if (grantedPermissions != null && grantedPermissions.contains(perm)) {
                        int[] iArr2 = pi.requestedPermissionsFlags;
                        iArr2[i7] = iArr2[i7] | 2;
                    }
                }
            }
        }
        if (apexInfo != null) {
            File apexFile = new File(apexInfo.modulePath);
            pi.applicationInfo.sourceDir = apexFile.getPath();
            pi.applicationInfo.publicSourceDir = apexFile.getPath();
            if (apexInfo.isFactory) {
                pi.applicationInfo.flags |= 1;
            } else {
                pi.applicationInfo.flags &= -2;
            }
            if (apexInfo.isActive) {
                pi.applicationInfo.flags |= 8388608;
            } else {
                pi.applicationInfo.flags &= -8388609;
            }
            pi.isApex = true;
        }
        if ((flags & 64) != 0) {
            if (p.mSigningDetails.hasPastSigningCertificates()) {
                pi.signatures = new Signature[1];
                pi.signatures[0] = p.mSigningDetails.pastSigningCertificates[0];
            } else if (p.mSigningDetails.hasSignatures()) {
                int numberOfSigs = p.mSigningDetails.signatures.length;
                pi.signatures = new Signature[numberOfSigs];
                System.arraycopy(p.mSigningDetails.signatures, 0, pi.signatures, 0, numberOfSigs);
            }
        }
        if ((134217728 & flags) != 0) {
            if (p.mSigningDetails != SigningDetails.UNKNOWN) {
                pi.signingInfo = new SigningInfo(new android.content.pm.SigningDetails(p.mSigningDetails.signatures, p.mSigningDetails.signatureSchemeVersion, p.mSigningDetails.publicKeys, p.mSigningDetails.pastSigningCertificates));
            } else {
                pi.signingInfo = null;
            }
        }
        return pi;
    }

    private static class SplitNameComparator implements Comparator<String> {
        private SplitNameComparator() {
        }

        @Override // java.util.Comparator
        public int compare(String lhs, String rhs) {
            if (lhs == null) {
                return -1;
            }
            if (rhs == null) {
                return 1;
            }
            return lhs.compareTo(rhs);
        }
    }

    public static PackageLite parsePackageLite(File packageFile, int flags) throws PackageParserException {
        if (packageFile.isDirectory()) {
            return parseClusterPackageLite(packageFile, flags);
        }
        return parseMonolithicPackageLite(packageFile, flags);
    }

    private static PackageLite parseMonolithicPackageLite(File packageFile, int flags) throws PackageParserException {
        Trace.traceBegin(262144L, "parseApkLite");
        ApkLite baseApk = parseApkLite(packageFile, flags);
        String packagePath = packageFile.getAbsolutePath();
        Trace.traceEnd(262144L);
        return new PackageLite(packagePath, baseApk.codePath, baseApk, null, null, null, null, null, null);
    }

    static PackageLite parseClusterPackageLite(File packageDir, int flags) throws PackageParserException {
        String[] configForSplits;
        String[] splitCodePaths;
        int[] splitRevisionCodes;
        File[] files = packageDir.listFiles();
        if (ArrayUtils.isEmpty(files)) {
            throw new PackageParserException(-100, "No packages found in split");
        }
        if (files.length == 1 && files[0].isDirectory()) {
            return parseClusterPackageLite(files[0], flags);
        }
        String packageName = null;
        int versionCode = 0;
        Trace.traceBegin(262144L, "parseApkLite");
        ArrayMap<String, ApkLite> apks = new ArrayMap<>();
        for (File file : files) {
            if (isApkFile(file)) {
                ApkLite lite = parseApkLite(file, flags);
                if (packageName == null) {
                    packageName = lite.packageName;
                    versionCode = lite.versionCode;
                } else {
                    if (!packageName.equals(lite.packageName)) {
                        throw new PackageParserException(-101, "Inconsistent package " + lite.packageName + " in " + file + "; expected " + packageName);
                    }
                    if (versionCode != lite.versionCode) {
                        throw new PackageParserException(-101, "Inconsistent version " + lite.versionCode + " in " + file + "; expected " + versionCode);
                    }
                }
                if (apks.put(lite.splitName, lite) != null) {
                    throw new PackageParserException(-101, "Split name " + lite.splitName + " defined more than once; most recent was " + file);
                }
            }
        }
        Trace.traceEnd(262144L);
        ApkLite baseApk = apks.remove(null);
        if (baseApk == null) {
            throw new PackageParserException(-101, "Missing base APK in " + packageDir);
        }
        int size = apks.size();
        String[] splitNames = null;
        boolean[] isFeatureSplits = null;
        String[] usesSplitNames = null;
        if (size <= 0) {
            configForSplits = null;
            splitCodePaths = null;
            splitRevisionCodes = null;
        } else {
            String[] splitNames2 = new String[size];
            isFeatureSplits = new boolean[size];
            usesSplitNames = new String[size];
            String[] configForSplits2 = new String[size];
            String[] splitCodePaths2 = new String[size];
            int[] splitRevisionCodes2 = new int[size];
            splitNames = (String[]) apks.keySet().toArray(splitNames2);
            Arrays.sort(splitNames, sSplitNameComparator);
            for (int i = 0; i < size; i++) {
                ApkLite apk = apks.get(splitNames[i]);
                usesSplitNames[i] = apk.usesSplitName;
                isFeatureSplits[i] = apk.isFeatureSplit;
                configForSplits2[i] = apk.configForSplit;
                splitCodePaths2[i] = apk.codePath;
                splitRevisionCodes2[i] = apk.revisionCode;
            }
            configForSplits = configForSplits2;
            splitCodePaths = splitCodePaths2;
            splitRevisionCodes = splitRevisionCodes2;
        }
        String codePath = packageDir.getAbsolutePath();
        return new PackageLite(codePath, baseApk.codePath, baseApk, splitNames, isFeatureSplits, usesSplitNames, configForSplits, splitCodePaths, splitRevisionCodes);
    }

    public Package parsePackage(File packageFile, int flags, boolean useCaches) throws PackageParserException {
        if (packageFile.isDirectory()) {
            return parseClusterPackage(packageFile, flags);
        }
        return parseMonolithicPackage(packageFile, flags);
    }

    public Package parsePackage(File packageFile, int flags) throws PackageParserException {
        return parsePackage(packageFile, flags, false);
    }

    private Package parseClusterPackage(File packageDir, int flags) throws PackageParserException {
        SplitAssetLoader assetLoader;
        PackageLite lite = parseClusterPackageLite(packageDir, 0);
        if (this.mOnlyCoreApps && !lite.coreApp) {
            throw new PackageParserException(-108, "Not a coreApp: " + packageDir);
        }
        SparseArray<int[]> splitDependencies = null;
        if (lite.isolatedSplits && !ArrayUtils.isEmpty(lite.splitNames)) {
            try {
                splitDependencies = SplitAssetDependencyLoader.createDependenciesFromPackage(lite);
                assetLoader = new SplitAssetDependencyLoader(lite, splitDependencies, flags);
            } catch (SplitDependencyLoader.IllegalDependencyException e) {
                throw new PackageParserException(-101, e.getMessage());
            }
        } else {
            assetLoader = new DefaultSplitAssetLoader(lite, flags);
        }
        try {
            AssetManager assets = assetLoader.getBaseAssetManager();
            File baseApk = new File(lite.baseCodePath);
            Package pkg = parseBaseApk(baseApk, assets, flags);
            if (pkg == null) {
                throw new PackageParserException(-100, "Failed to parse base APK: " + baseApk);
            }
            if (!ArrayUtils.isEmpty(lite.splitNames)) {
                int num = lite.splitNames.length;
                pkg.splitNames = lite.splitNames;
                pkg.splitCodePaths = lite.splitCodePaths;
                pkg.splitRevisionCodes = lite.splitRevisionCodes;
                pkg.splitFlags = new int[num];
                pkg.splitPrivateFlags = new int[num];
                pkg.applicationInfo.splitNames = pkg.splitNames;
                pkg.applicationInfo.splitDependencies = splitDependencies;
                pkg.applicationInfo.splitClassLoaderNames = new String[num];
                for (int i = 0; i < num; i++) {
                    AssetManager splitAssets = assetLoader.getSplitAssetManager(i);
                    parseSplitApk(pkg, i, splitAssets, flags);
                }
            }
            pkg.setCodePath(lite.codePath);
            pkg.setUse32bitAbi(lite.use32bitAbi);
            return pkg;
        } finally {
            IoUtils.closeQuietly(assetLoader);
        }
    }

    public Package parseMonolithicPackage(File apkFile, int flags) throws PackageParserException {
        PackageLite lite = parseMonolithicPackageLite(apkFile, flags);
        if (this.mOnlyCoreApps && !lite.coreApp) {
            throw new PackageParserException(-108, "Not a coreApp: " + apkFile);
        }
        SplitAssetLoader assetLoader = new DefaultSplitAssetLoader(lite, flags);
        try {
            try {
                Package pkg = parseBaseApk(apkFile, assetLoader.getBaseAssetManager(), flags);
                pkg.setCodePath(apkFile.getCanonicalPath());
                pkg.setUse32bitAbi(lite.use32bitAbi);
                return pkg;
            } catch (IOException e) {
                throw new PackageParserException(-102, "Failed to get path: " + apkFile, e);
            }
        } finally {
            IoUtils.closeQuietly(assetLoader);
        }
    }

    private Package parseBaseApk(File apkFile, AssetManager assets, int flags) throws PackageParserException {
        String volumeUuid;
        String apkPath = apkFile.getAbsolutePath();
        if (apkPath.startsWith("/mnt/expand/")) {
            int end = apkPath.indexOf(47, "/mnt/expand/".length());
            String volumeUuid2 = apkPath.substring("/mnt/expand/".length(), end);
            volumeUuid = volumeUuid2;
        } else {
            volumeUuid = null;
        }
        this.mParseError = 1;
        this.mArchiveSourcePath = apkFile.getAbsolutePath();
        XmlResourceParser parser = null;
        try {
            try {
                int cookie = assets.findCookieForPath(apkPath);
                if (cookie == 0) {
                    throw new PackageParserException(-101, "Failed adding asset path: " + apkPath);
                }
                XmlResourceParser parser2 = assets.openXmlResourceParser(cookie, "AndroidManifest.xml");
                try {
                    Resources res = new Resources(assets, this.mMetrics, null);
                    String[] outError = new String[1];
                    Package pkg = parseBaseApk(apkPath, res, parser2, flags, outError);
                    if (pkg == null) {
                        throw new PackageParserException(this.mParseError, apkPath + " (at " + parser2.getPositionDescription() + "): " + outError[0]);
                    }
                    pkg.setVolumeUuid(volumeUuid);
                    pkg.setApplicationVolumeUuid(volumeUuid);
                    pkg.setBaseCodePath(apkPath);
                    pkg.setSigningDetails(SigningDetails.UNKNOWN);
                    IoUtils.closeQuietly(parser2);
                    return pkg;
                } catch (PackageParserException e) {
                    throw e;
                } catch (Exception e2) {
                    e = e2;
                    throw new PackageParserException(-102, "Failed to read manifest from " + apkPath, e);
                } catch (Throwable th) {
                    e = th;
                    parser = parser2;
                    IoUtils.closeQuietly(parser);
                    throw e;
                }
            } catch (PackageParserException e3) {
                throw e3;
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            e = th2;
        }
    }

    private void parseSplitApk(Package pkg, int splitIndex, AssetManager assets, int flags) throws PackageParserException {
        String apkPath = pkg.splitCodePaths[splitIndex];
        this.mParseError = 1;
        this.mArchiveSourcePath = apkPath;
        XmlResourceParser parser = null;
        try {
            try {
                int cookie = assets.findCookieForPath(apkPath);
                if (cookie == 0) {
                    throw new PackageParserException(-101, "Failed adding asset path: " + apkPath);
                }
                XmlResourceParser parser2 = assets.openXmlResourceParser(cookie, "AndroidManifest.xml");
                try {
                    Resources res = new Resources(assets, this.mMetrics, null);
                    String[] outError = new String[1];
                    if (parseSplitApk(pkg, res, parser2, flags, splitIndex, outError) != null) {
                        IoUtils.closeQuietly(parser2);
                        return;
                    }
                    try {
                        throw new PackageParserException(this.mParseError, apkPath + " (at " + parser2.getPositionDescription() + "): " + outError[0]);
                    } catch (PackageParserException e) {
                    } catch (Exception e2) {
                        e = e2;
                        throw new PackageParserException(-102, "Failed to read manifest from " + apkPath, e);
                    } catch (Throwable th) {
                        e = th;
                        parser = parser2;
                        IoUtils.closeQuietly(parser);
                        throw e;
                    }
                } catch (PackageParserException e3) {
                } catch (Exception e4) {
                    e = e4;
                } catch (Throwable th2) {
                    e = th2;
                    parser = parser2;
                }
            } catch (PackageParserException e5) {
                throw e5;
            } catch (Exception e6) {
                e = e6;
            } catch (Throwable th3) {
                e = th3;
            }
        } catch (Throwable th4) {
            e = th4;
        }
    }

    private Package parseSplitApk(Package pkg, Resources res, XmlResourceParser parser, int flags, int splitIndex, String[] outError) throws XmlPullParserException, IOException, PackageParserException {
        parsePackageSplitNames(parser, parser);
        this.mParseInstrumentationArgs = null;
        boolean foundApp = false;
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            }
            if (type != 3 && type != 4) {
                String tagName = parser.getName();
                if (tagName.equals("application")) {
                    if (foundApp) {
                        Slog.w(TAG, "<manifest> has more than one <application>");
                        XmlUtils.skipCurrentTag(parser);
                    } else {
                        foundApp = true;
                        if (!parseSplitApplication(pkg, res, parser, flags, splitIndex, outError)) {
                            return null;
                        }
                    }
                } else {
                    Slog.w(TAG, "Unknown element under <manifest>: " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
        if (!foundApp) {
            outError[0] = "<manifest> does not contain an <application>";
            this.mParseError = -109;
        }
        return pkg;
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] signatures) throws CertificateException {
        ArraySet<PublicKey> keys = new ArraySet<>(signatures.length);
        for (Signature signature : signatures) {
            keys.add(signature.getPublicKey());
        }
        return keys;
    }

    public static void collectCertificates(Package pkg, boolean skipVerify) throws PackageParserException {
        collectCertificatesInternal(pkg, skipVerify);
        int childCount = pkg.childPackages != null ? pkg.childPackages.size() : 0;
        for (int i = 0; i < childCount; i++) {
            Package childPkg = pkg.childPackages.get(i);
            childPkg.mSigningDetails = pkg.mSigningDetails;
        }
    }

    private static void collectCertificatesInternal(Package pkg, boolean skipVerify) throws PackageParserException {
        pkg.mSigningDetails = SigningDetails.UNKNOWN;
        Trace.traceBegin(262144L, "collectCertificates");
        try {
            collectCertificates(pkg, new File(pkg.baseCodePath), skipVerify);
            if (!ArrayUtils.isEmpty(pkg.splitCodePaths)) {
                for (int i = 0; i < pkg.splitCodePaths.length; i++) {
                    collectCertificates(pkg, new File(pkg.splitCodePaths[i]), skipVerify);
                }
            }
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    private static void collectCertificates(Package pkg, File apkFile, boolean skipVerify) throws PackageParserException {
        ParseResult<android.content.pm.SigningDetails> result;
        String apkPath = apkFile.getAbsolutePath();
        int minSignatureScheme = ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(pkg.applicationInfo.targetSdkVersion);
        if (pkg.applicationInfo.isStaticSharedLibrary()) {
            minSignatureScheme = 2;
        }
        ParseTypeImpl input = ParseTypeImpl.forDefaultParsing();
        if (skipVerify) {
            result = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(input, apkPath, minSignatureScheme);
        } else {
            result = ApkSignatureVerifier.verify(input, apkPath, minSignatureScheme);
        }
        if (result.isError()) {
            throw new PackageParserException(result.getErrorCode(), result.getErrorMessage(), result.getException());
        }
        android.content.pm.SigningDetails verified = result.getResult();
        if (pkg.mSigningDetails == SigningDetails.UNKNOWN) {
            pkg.mSigningDetails = new SigningDetails(verified.getSignatures(), verified.getSignatureSchemeVersion(), verified.getPublicKeys(), verified.getPastSigningCertificates());
        } else if (!Signature.areExactArraysMatch(pkg.mSigningDetails.signatures, verified.getSignatures())) {
            throw new PackageParserException(-104, apkPath + " has mismatched certificates");
        }
    }

    private static AssetManager newConfiguredAssetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        return assetManager;
    }

    public static ApkLite parseApkLite(File apkFile, int flags) throws PackageParserException {
        return parseApkLiteInner(apkFile, null, null, flags);
    }

    public static ApkLite parseApkLite(FileDescriptor fd, String debugPathName, int flags) throws PackageParserException {
        return parseApkLiteInner(null, fd, debugPathName, flags);
    }

    private static ApkLite parseApkLiteInner(File apkFile, FileDescriptor fd, String debugPathName, int flags) throws PackageParserException {
        ApkAssets loadFromPath;
        SigningDetails signingDetails;
        String apkPath = fd != null ? debugPathName : apkFile.getAbsolutePath();
        XmlResourceParser parser = null;
        ApkAssets apkAssets = null;
        try {
            try {
                try {
                    if (fd != null) {
                        loadFromPath = ApkAssets.loadFromFd(fd, debugPathName, 0, null);
                    } else {
                        loadFromPath = ApkAssets.loadFromPath(apkPath);
                    }
                    apkAssets = loadFromPath;
                    parser = apkAssets.openXml("AndroidManifest.xml");
                    if ((flags & 32) != 0) {
                        Package tempPkg = new Package((String) null);
                        boolean skipVerify = (flags & 16) != 0;
                        Trace.traceBegin(262144L, "collectCertificates");
                        try {
                            collectCertificates(tempPkg, apkFile, skipVerify);
                            Trace.traceEnd(262144L);
                            signingDetails = tempPkg.mSigningDetails;
                        } catch (Throwable th) {
                            Trace.traceEnd(262144L);
                            throw th;
                        }
                    } else {
                        signingDetails = SigningDetails.UNKNOWN;
                    }
                    return parseApkLite(apkPath, parser, parser, signingDetails);
                } finally {
                    IoUtils.closeQuietly(parser);
                    if (apkAssets != null) {
                        try {
                            apkAssets.close();
                        } catch (Throwable th2) {
                        }
                    }
                }
            } catch (IOException e) {
                throw new PackageParserException(-100, "Failed to parse " + apkPath);
            }
        } catch (IOException | RuntimeException | XmlPullParserException e2) {
            Slog.w(TAG, "Failed to parse " + apkPath, e2);
            throw new PackageParserException(-102, "Failed to parse " + apkPath, e2);
        }
    }

    public static String validateName(String name, boolean requireSeparator, boolean requireFilename) {
        int N = name.length();
        boolean hasSep = false;
        boolean front = true;
        for (int i = 0; i < N; i++) {
            char c = name.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                front = false;
            } else if (front || ((c < '0' || c > '9') && c != '_')) {
                if (c == '.') {
                    hasSep = true;
                    front = true;
                } else {
                    return "bad character '" + c + "'";
                }
            }
        }
        if (requireFilename && !FileUtils.isValidExtFilename(name)) {
            return "Invalid filename";
        }
        if (hasSep || !requireSeparator) {
            return null;
        }
        return "must have at least one '.' separator";
    }

    @Deprecated
    public static Pair<String, String> parsePackageSplitNames(XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException, PackageParserException {
        int type;
        String error;
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new PackageParserException(-108, "No start tag found");
        }
        if (!parser.getName().equals("manifest")) {
            throw new PackageParserException(-108, "No <manifest> tag");
        }
        String packageName = attrs.getAttributeValue(null, "package");
        if (!"android".equals(packageName) && (error = validateName(packageName, true, true)) != null) {
            throw new PackageParserException(-106, "Invalid manifest package: " + error);
        }
        String splitName = attrs.getAttributeValue(null, "split");
        if (splitName != null) {
            if (splitName.length() == 0) {
                splitName = null;
            } else {
                String error2 = validateName(splitName, false, false);
                if (error2 != null) {
                    throw new PackageParserException(-106, "Invalid manifest split: " + error2);
                }
            }
        }
        return Pair.create(packageName.intern(), splitName != null ? splitName.intern() : splitName);
    }

    private static ApkLite parseApkLite(String codePath, XmlPullParser parser, AttributeSet attrs, SigningDetails signingDetails) throws IOException, XmlPullParserException, PackageParserException {
        int targetSdkVersion;
        int minSdkVersion;
        int type;
        String targetPackage;
        boolean overlayIsStatic;
        int overlayPriority;
        Pair<String, String> packageSplit = parsePackageSplitNames(parser, attrs);
        int installLocation = -1;
        int versionCode = 0;
        int versionCodeMajor = 0;
        int i = 0;
        int minSdkVersion2 = 1;
        int revisionCode = 0;
        boolean coreApp = false;
        boolean debuggable = false;
        boolean overlayIsStatic2 = false;
        int overlayPriority2 = 0;
        boolean isolatedSplits = false;
        boolean isFeatureSplit = false;
        boolean isSplitRequired = false;
        String configForSplit = null;
        for (int i2 = 0; i2 < attrs.getAttributeCount(); i2++) {
            String attr = attrs.getAttributeName(i2);
            if (attr.equals("installLocation")) {
                installLocation = attrs.getAttributeIntValue(i2, -1);
            } else if (attr.equals(SmLib_IafdConstant.KEY_VERSION_CODE)) {
                versionCode = attrs.getAttributeIntValue(i2, 0);
            } else if (attr.equals("versionCodeMajor")) {
                versionCodeMajor = attrs.getAttributeIntValue(i2, 0);
            } else if (attr.equals("revisionCode")) {
                revisionCode = attrs.getAttributeIntValue(i2, 0);
            } else if (attr.equals("coreApp")) {
                coreApp = attrs.getAttributeBooleanValue(i2, false);
            } else if (attr.equals("isolatedSplits")) {
                isolatedSplits = attrs.getAttributeBooleanValue(i2, false);
            } else if (attr.equals("configForSplit")) {
                configForSplit = attrs.getAttributeValue(i2);
            } else if (attr.equals("isFeatureSplit")) {
                isFeatureSplit = attrs.getAttributeBooleanValue(i2, false);
            } else if (attr.equals("isSplitRequired")) {
                isSplitRequired = attrs.getAttributeBooleanValue(i2, false);
            }
        }
        int i3 = parser.getDepth();
        int searchDepth = i3 + 1;
        List<VerifierInfo> verifiers = new ArrayList<>();
        boolean multiArch = false;
        boolean use32bitAbi = false;
        boolean extractNativeLibs = true;
        boolean useEmbeddedDex = false;
        String usesSplitName = null;
        String targetPackage2 = null;
        int rollbackDataPolicy = 0;
        String requiredSystemPropertyName = null;
        String requiredSystemPropertyValue = null;
        while (true) {
            int type2 = parser.next();
            targetSdkVersion = i;
            minSdkVersion = minSdkVersion2;
            if (type2 == 1) {
                type = type2;
                break;
            }
            if (type2 == 3 && parser.getDepth() < searchDepth) {
                type = type2;
                break;
            }
            if (type2 != 3 && type2 != 4 && parser.getDepth() == searchDepth) {
                if ("package-verifier".equals(parser.getName())) {
                    VerifierInfo verifier = parseVerifier(attrs);
                    if (verifier != null) {
                        verifiers.add(verifier);
                    }
                } else if ("application".equals(parser.getName())) {
                    for (int i4 = 0; i4 < attrs.getAttributeCount(); i4++) {
                        String attr2 = attrs.getAttributeName(i4);
                        if ("debuggable".equals(attr2)) {
                            debuggable = attrs.getAttributeBooleanValue(i4, false);
                        }
                        if ("multiArch".equals(attr2)) {
                            multiArch = attrs.getAttributeBooleanValue(i4, false);
                        }
                        if ("use32bitAbi".equals(attr2)) {
                            use32bitAbi = attrs.getAttributeBooleanValue(i4, false);
                        }
                        if ("extractNativeLibs".equals(attr2)) {
                            extractNativeLibs = attrs.getAttributeBooleanValue(i4, true);
                        }
                        if ("useEmbeddedDex".equals(attr2)) {
                            useEmbeddedDex = attrs.getAttributeBooleanValue(i4, false);
                        }
                        if (attr2.equals("rollbackDataPolicy")) {
                            rollbackDataPolicy = attrs.getAttributeIntValue(i4, 0);
                        }
                    }
                    i = targetSdkVersion;
                    minSdkVersion2 = minSdkVersion;
                } else if ("overlay".equals(parser.getName())) {
                    for (int i5 = 0; i5 < attrs.getAttributeCount(); i5++) {
                        String attr3 = attrs.getAttributeName(i5);
                        if ("requiredSystemPropertyName".equals(attr3)) {
                            requiredSystemPropertyName = attrs.getAttributeValue(i5);
                        } else if ("requiredSystemPropertyValue".equals(attr3)) {
                            requiredSystemPropertyValue = attrs.getAttributeValue(i5);
                        } else if ("targetPackage".equals(attr3)) {
                            targetPackage2 = attrs.getAttributeValue(i5);
                        } else if ("isStatic".equals(attr3)) {
                            overlayIsStatic2 = attrs.getAttributeBooleanValue(i5, false);
                        } else if ("priority".equals(attr3)) {
                            overlayPriority2 = attrs.getAttributeIntValue(i5, 0);
                        }
                    }
                    i = targetSdkVersion;
                    minSdkVersion2 = minSdkVersion;
                } else if ("uses-split".equals(parser.getName())) {
                    if (usesSplitName == null) {
                        usesSplitName = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                        if (usesSplitName == null) {
                            throw new PackageParserException(-108, "<uses-split> tag requires 'android:name' attribute");
                        }
                        i = targetSdkVersion;
                        minSdkVersion2 = minSdkVersion;
                    } else {
                        Slog.w(TAG, "Only one <uses-split> permitted. Ignoring others.");
                    }
                } else if ("uses-sdk".equals(parser.getName())) {
                    int i6 = 0;
                    int minSdkVersion3 = minSdkVersion;
                    while (i6 < attrs.getAttributeCount()) {
                        String attr4 = attrs.getAttributeName(i6);
                        int minSdkVersion4 = minSdkVersion3;
                        if ("targetSdkVersion".equals(attr4)) {
                            targetSdkVersion = attrs.getAttributeIntValue(i6, 0);
                        }
                        int minSdkVersion5 = "minSdkVersion".equals(attr4) ? attrs.getAttributeIntValue(i6, 1) : minSdkVersion4;
                        i6++;
                        minSdkVersion3 = minSdkVersion5;
                    }
                    minSdkVersion2 = minSdkVersion3;
                    i = targetSdkVersion;
                }
            }
            i = targetSdkVersion;
            minSdkVersion2 = minSdkVersion;
        }
        if (checkRequiredSystemProperties(requiredSystemPropertyName, requiredSystemPropertyValue)) {
            targetPackage = targetPackage2;
            overlayIsStatic = overlayIsStatic2;
            overlayPriority = overlayPriority2;
        } else {
            Slog.i(TAG, "Skipping target and overlay pair " + targetPackage2 + " and " + codePath + ": overlay ignored due to required system property: " + requiredSystemPropertyName + " with value: " + requiredSystemPropertyValue);
            targetPackage = null;
            overlayIsStatic = false;
            overlayPriority = 0;
        }
        String requiredSystemPropertyName2 = packageSplit.first;
        return new ApkLite(codePath, requiredSystemPropertyName2, packageSplit.second, isFeatureSplit, configForSplit, usesSplitName, isSplitRequired, versionCode, versionCodeMajor, revisionCode, installLocation, verifiers, signingDetails, coreApp, debuggable, false, multiArch, use32bitAbi, useEmbeddedDex, extractNativeLibs, isolatedSplits, targetPackage, overlayIsStatic, overlayPriority, minSdkVersion, targetSdkVersion, rollbackDataPolicy);
    }

    private boolean parseBaseApkChild(Package parentPkg, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        String childPackageName = parser.getAttributeValue(null, "package");
        if (validateName(childPackageName, true, false) != null) {
            this.mParseError = -106;
            return false;
        }
        if (childPackageName.equals(parentPkg.packageName)) {
            String message = "Child package name cannot be equal to parent package name: " + parentPkg.packageName;
            Slog.w(TAG, message);
            outError[0] = message;
            this.mParseError = -108;
            return false;
        }
        if (parentPkg.hasChildPackage(childPackageName)) {
            String message2 = "Duplicate child package:" + childPackageName;
            Slog.w(TAG, message2);
            outError[0] = message2;
            this.mParseError = -108;
            return false;
        }
        Package childPkg = new Package(childPackageName);
        childPkg.mVersionCode = parentPkg.mVersionCode;
        childPkg.baseRevisionCode = parentPkg.baseRevisionCode;
        childPkg.mVersionName = parentPkg.mVersionName;
        childPkg.applicationInfo.targetSdkVersion = parentPkg.applicationInfo.targetSdkVersion;
        childPkg.applicationInfo.minSdkVersion = parentPkg.applicationInfo.minSdkVersion;
        Package childPkg2 = parseBaseApkCommon(childPkg, CHILD_PACKAGE_TAGS, res, parser, flags, outError);
        if (childPkg2 == null) {
            return false;
        }
        if (parentPkg.childPackages == null) {
            parentPkg.childPackages = new ArrayList<>();
        }
        parentPkg.childPackages.add(childPkg2);
        childPkg2.parentPackage = parentPkg;
        return true;
    }

    private Package parseBaseApk(String apkPath, Resources res, XmlResourceParser parser, int flags, String[] outError) throws XmlPullParserException, IOException {
        try {
            Pair<String, String> packageSplit = parsePackageSplitNames(parser, parser);
            String pkgName = packageSplit.first;
            String splitName = packageSplit.second;
            if (!TextUtils.isEmpty(splitName)) {
                outError[0] = "Expected base APK, but found split " + splitName;
                this.mParseError = -106;
                return null;
            }
            Package pkg = new Package(pkgName);
            TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifest);
            pkg.mVersionCode = sa.getInteger(1, 0);
            pkg.mVersionCodeMajor = sa.getInteger(11, 0);
            pkg.applicationInfo.setVersionCode(pkg.getLongVersionCode());
            pkg.baseRevisionCode = sa.getInteger(5, 0);
            pkg.mVersionName = sa.getNonConfigurationString(2, 0);
            if (pkg.mVersionName != null) {
                pkg.mVersionName = pkg.mVersionName.intern();
            }
            pkg.coreApp = parser.getAttributeBooleanValue(null, "coreApp", false);
            boolean isolatedSplits = sa.getBoolean(6, false);
            if (isolatedSplits) {
                pkg.applicationInfo.privateFlags |= 32768;
            }
            pkg.mCompileSdkVersion = sa.getInteger(9, 0);
            pkg.applicationInfo.compileSdkVersion = pkg.mCompileSdkVersion;
            pkg.mCompileSdkVersionCodename = sa.getNonConfigurationString(10, 0);
            if (pkg.mCompileSdkVersionCodename != null) {
                pkg.mCompileSdkVersionCodename = pkg.mCompileSdkVersionCodename.intern();
            }
            pkg.applicationInfo.compileSdkVersionCodename = pkg.mCompileSdkVersionCodename;
            sa.recycle();
            return parseBaseApkCommon(pkg, null, res, parser, flags, outError);
        } catch (PackageParserException e) {
            this.mParseError = -106;
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0828, code lost:
    
        com.samsung.android.core.pm.runtimemanifest.RuntimeManifestCoreOverlayUtils.applyRuntimeManifestIfNeeded(r36, r38);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x082b, code lost:
    
        if (r18 != 0) goto L267;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0833, code lost:
    
        if (r36.instrumentation.size() != 0) goto L267;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0835, code lost:
    
        r41[0] = "<manifest> does not contain an <application> or <instrumentation>";
        r35.mParseError = -109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x083e, code lost:
    
        r1 = android.content.pm.PackageParser.NEW_PERMISSIONS.length;
        r2 = null;
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0848, code lost:
    
        if (r0 >= r1) goto L390;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x084a, code lost:
    
        r3 = android.content.pm.PackageParser.NEW_PERMISSIONS[r0];
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0854, code lost:
    
        if (r36.applicationInfo.targetSdkVersion < r3.sdkVersion) goto L272;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x085f, code lost:
    
        if (r36.requestedPermissions.contains(r3.name) != false) goto L392;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0861, code lost:
    
        if (r2 != null) goto L276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0863, code lost:
    
        r2 = new java.lang.StringBuilder(128);
        r2.append(r36.packageName);
        r2.append(": compat added ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x087b, code lost:
    
        r2.append(r3.name);
        r36.requestedPermissions.add(r3.name);
        r36.implicitPermissions.add(r3.name);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x088e, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0876, code lost:
    
        r2.append(' ');
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0891, code lost:
    
        if (r2 == null) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x0283, code lost:
    
        r41[0] = "<overlay> priority must be between 0 and 9999";
        r35.mParseError = -108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x028c, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0893, code lost:
    
        android.util.Slog.i(android.content.pm.PackageParser.TAG, r2.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x089a, code lost:
    
        r0 = android.app.ActivityThread.getPermissionManager().getSplitPermissions();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x08a4, code lost:
    
        r0 = java.util.Collections.emptyList();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Package parseBaseApkCommon(android.content.pm.PackageParser.Package r36, java.util.Set<java.lang.String> r37, android.content.res.Resources r38, android.content.res.XmlResourceParser r39, int r40, java.lang.String[] r41) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 2476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseBaseApkCommon(android.content.pm.PackageParser$Package, java.util.Set, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[]):android.content.pm.PackageParser$Package");
    }

    public static boolean checkRequiredSystemProperties(String rawPropNames, String rawPropValues) {
        if (TextUtils.isEmpty(rawPropNames) || TextUtils.isEmpty(rawPropValues)) {
            if (TextUtils.isEmpty(rawPropNames) && TextUtils.isEmpty(rawPropValues)) {
                return true;
            }
            Slog.w(TAG, "Disabling overlay - incomplete property :'" + rawPropNames + "=" + rawPropValues + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
            return false;
        }
        String[] propNames = rawPropNames.split(",");
        String[] propValues = rawPropValues.split(",");
        if (propNames.length != propValues.length) {
            Slog.w(TAG, "Disabling overlay - property :'" + rawPropNames + "=" + rawPropValues + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue lists to have the same size.");
            return false;
        }
        for (int i = 0; i < propNames.length; i++) {
            String currValue = SystemProperties.get(propNames[i]);
            if (!TextUtils.equals(currValue, propValues[i])) {
                return false;
            }
        }
        return true;
    }

    private void adjustPackageToBeUnresizeableAndUnpipable(Package pkg) {
        Iterator<Activity> it = pkg.activities.iterator();
        while (it.hasNext()) {
            Activity a = it.next();
            a.info.resizeMode = 0;
            a.info.flags &= -4194305;
        }
    }

    private static boolean matchTargetCode(String[] codeNames, String targetCode) {
        String targetCodeName;
        int targetCodeIdx = targetCode.indexOf(46);
        if (targetCodeIdx == -1) {
            targetCodeName = targetCode;
        } else {
            targetCodeName = targetCode.substring(0, targetCodeIdx);
        }
        return ArrayUtils.contains(codeNames, targetCodeName);
    }

    public static int computeTargetSdkVersion(int targetVers, String targetCode, String[] platformSdkCodenames, String[] outError) {
        if (targetCode == null) {
            return targetVers;
        }
        if (matchTargetCode(platformSdkCodenames, targetCode)) {
            return 10000;
        }
        if (platformSdkCodenames.length > 0) {
            outError[0] = "Requires development platform " + targetCode + " (current platform is any of " + Arrays.toString(platformSdkCodenames) + NavigationBarInflaterView.KEY_CODE_END;
            return -1;
        }
        outError[0] = "Requires development platform " + targetCode + " but this is a release platform.";
        return -1;
    }

    public static int computeMinSdkVersion(int minVers, String minCode, int platformSdkVersion, String[] platformSdkCodenames, String[] outError) {
        if (minCode == null) {
            if (minVers <= platformSdkVersion) {
                return minVers;
            }
            outError[0] = "Requires newer sdk version #" + minVers + " (current version is #" + platformSdkVersion + NavigationBarInflaterView.KEY_CODE_END;
            return -1;
        }
        if (matchTargetCode(platformSdkCodenames, minCode)) {
            return 10000;
        }
        if (platformSdkCodenames.length > 0) {
            outError[0] = "Requires development platform " + minCode + " (current platform is any of " + Arrays.toString(platformSdkCodenames) + NavigationBarInflaterView.KEY_CODE_END;
        } else {
            outError[0] = "Requires development platform " + minCode + " but this is a release platform.";
        }
        return -1;
    }

    private FeatureInfo parseUsesFeature(Resources res, AttributeSet attrs) {
        FeatureInfo fi = new FeatureInfo();
        TypedArray sa = res.obtainAttributes(attrs, R.styleable.AndroidManifestUsesFeature);
        fi.name = sa.getNonResourceString(0);
        fi.version = sa.getInt(3, 0);
        if (fi.name == null) {
            fi.reqGlEsVersion = sa.getInt(1, 0);
        }
        if (sa.getBoolean(2, true)) {
            fi.flags |= 1;
        }
        sa.recycle();
        return fi;
    }

    private boolean parseUsesStaticLibrary(Package pkg, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesStaticLibrary);
        String lname = sa.getNonResourceString(0);
        int version = sa.getInt(1, -1);
        String certSha256Digest = sa.getNonResourceString(2);
        sa.recycle();
        if (lname == null || version < 0 || certSha256Digest == null) {
            outError[0] = "Bad uses-static-library declaration name: " + lname + " version: " + version + " certDigest" + certSha256Digest;
            this.mParseError = -108;
            XmlUtils.skipCurrentTag(parser);
            return false;
        }
        if (pkg.usesStaticLibraries != null && pkg.usesStaticLibraries.contains(lname)) {
            outError[0] = "Depending on multiple versions of static library " + lname;
            this.mParseError = -108;
            XmlUtils.skipCurrentTag(parser);
            return false;
        }
        String lname2 = lname.intern();
        String certSha256Digest2 = certSha256Digest.replace(":", "").toLowerCase();
        String[] additionalCertSha256Digests = EmptyArray.STRING;
        if (pkg.applicationInfo.targetSdkVersion >= 27) {
            additionalCertSha256Digests = parseAdditionalCertificates(res, parser, outError);
            if (additionalCertSha256Digests == null) {
                return false;
            }
        } else {
            XmlUtils.skipCurrentTag(parser);
        }
        String[] certSha256Digests = new String[additionalCertSha256Digests.length + 1];
        certSha256Digests[0] = certSha256Digest2;
        System.arraycopy(additionalCertSha256Digests, 0, certSha256Digests, 1, additionalCertSha256Digests.length);
        pkg.usesStaticLibraries = ArrayUtils.add(pkg.usesStaticLibraries, lname2);
        pkg.usesStaticLibrariesVersions = ArrayUtils.appendLong(pkg.usesStaticLibrariesVersions, version, true);
        pkg.usesStaticLibrariesCertDigests = (String[][]) ArrayUtils.appendElement(String[].class, pkg.usesStaticLibrariesCertDigests, certSha256Digests, true);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0078, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String[] parseAdditionalCertificates(android.content.res.Resources r10, android.content.res.XmlResourceParser r11, java.lang.String[] r12) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r9 = this;
            java.lang.String[] r0 = libcore.util.EmptyArray.STRING
            int r1 = r11.getDepth()
        L6:
            int r2 = r11.next()
            r3 = r2
            r4 = 1
            if (r2 == r4) goto L78
            r2 = 3
            if (r3 != r2) goto L17
            int r4 = r11.getDepth()
            if (r4 <= r1) goto L78
        L17:
            if (r3 == r2) goto L6
            r2 = 4
            if (r3 != r2) goto L1d
            goto L6
        L1d:
            java.lang.String r2 = r11.getName()
            java.lang.String r4 = "additional-certificate"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L74
            int[] r4 = com.android.internal.R.styleable.AndroidManifestAdditionalCertificate
            android.content.res.TypedArray r4 = r10.obtainAttributes(r11, r4)
            r5 = 0
            java.lang.String r6 = r4.getNonResourceString(r5)
            r4.recycle()
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 == 0) goto L5e
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Bad additional-certificate declaration with empty certDigest:"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r6)
            java.lang.String r7 = r7.toString()
            r12[r5] = r7
            r5 = -108(0xffffffffffffff94, float:NaN)
            r9.mParseError = r5
            com.android.internal.util.XmlUtils.skipCurrentTag(r11)
            r4.recycle()
            r5 = 0
            return r5
        L5e:
            java.lang.String r5 = ":"
            java.lang.String r7 = ""
            java.lang.String r5 = r6.replace(r5, r7)
            java.lang.String r5 = r5.toLowerCase()
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            java.lang.Object[] r6 = com.android.internal.util.ArrayUtils.appendElement(r6, r0, r5)
            r0 = r6
            java.lang.String[] r0 = (java.lang.String[]) r0
            goto L77
        L74:
            com.android.internal.util.XmlUtils.skipCurrentTag(r11)
        L77:
            goto L6
        L78:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseAdditionalCertificates(android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):java.lang.String[]");
    }

    private boolean parseUsesPermission(Package pkg, Resources res, XmlResourceParser parser) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesPermission);
        String name = sa.getNonResourceString(0);
        int maxSdkVersion = 0;
        TypedValue val = sa.peekValue(2);
        if (val != null && val.type >= 16 && val.type <= 31) {
            maxSdkVersion = val.data;
        }
        String requiredFeature = sa.getNonConfigurationString(3, 0);
        String requiredNotfeature = sa.getNonConfigurationString(4, 0);
        sa.recycle();
        XmlUtils.skipCurrentTag(parser);
        if (name == null) {
            return true;
        }
        if (maxSdkVersion != 0 && maxSdkVersion < Build.VERSION.RESOURCES_SDK_INT) {
            return true;
        }
        if (requiredFeature != null && this.mCallback != null && !this.mCallback.hasFeature(requiredFeature)) {
            return true;
        }
        if (requiredNotfeature != null && this.mCallback != null && this.mCallback.hasFeature(requiredNotfeature)) {
            return true;
        }
        int index = pkg.requestedPermissions.indexOf(name);
        if (index == -1) {
            pkg.requestedPermissions.add(name.intern());
        } else {
            Slog.w(TAG, "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + name + " in package: " + pkg.packageName + " at: " + parser.getPositionDescription());
        }
        return true;
    }

    public static String buildClassName(String pkg, CharSequence clsSeq, String[] outError) {
        if (clsSeq == null || clsSeq.length() <= 0) {
            outError[0] = "Empty class name in package " + pkg;
            return null;
        }
        String cls = clsSeq.toString();
        char c = cls.charAt(0);
        if (c == '.') {
            return pkg + cls;
        }
        if (cls.indexOf(46) < 0) {
            return pkg + '.' + cls;
        }
        return cls;
    }

    private static String buildCompoundName(String pkg, CharSequence procSeq, String type, String[] outError) {
        String proc = procSeq.toString();
        char c = proc.charAt(0);
        if (pkg != null && c == ':') {
            if (proc.length() < 2) {
                outError[0] = "Bad " + type + " name " + proc + " in package " + pkg + ": must be at least two characters";
                return null;
            }
            String subName = proc.substring(1);
            String nameError = validateName(subName, false, false);
            if (nameError != null) {
                outError[0] = "Invalid " + type + " name " + proc + " in package " + pkg + ": " + nameError;
                return null;
            }
            return pkg + proc;
        }
        String nameError2 = validateName(proc, true, false);
        if (nameError2 != null && !"system".equals(proc)) {
            outError[0] = "Invalid " + type + " name " + proc + " in package " + pkg + ": " + nameError2;
            return null;
        }
        return proc;
    }

    public static String buildProcessName(String pkg, String defProc, CharSequence procSeq, int flags, String[] separateProcesses, String[] outError) {
        if ((flags & 2) != 0 && !"system".equals(procSeq)) {
            return defProc != null ? defProc : pkg;
        }
        if (separateProcesses != null) {
            for (int i = separateProcesses.length - 1; i >= 0; i--) {
                String sp = separateProcesses[i];
                if (sp.equals(pkg) || sp.equals(defProc) || sp.equals(procSeq)) {
                    return pkg;
                }
            }
        }
        if (procSeq == null || procSeq.length() <= 0) {
            return defProc;
        }
        return TextUtils.safeIntern(buildCompoundName(pkg, procSeq, "process", outError));
    }

    public static String buildTaskAffinityName(String pkg, String defProc, CharSequence procSeq, String[] outError) {
        if (procSeq == null) {
            return defProc;
        }
        if (procSeq.length() <= 0) {
            return null;
        }
        return buildCompoundName(pkg, procSeq, "taskAffinity", outError);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x021a, code lost:
    
        r4 = r7.keySet();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0228, code lost:
    
        if (r4.removeAll(r9.keySet()) == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x022a, code lost:
    
        r26[0] = "Package" + r23.packageName + " AndroidManifext.xml 'key-set' and 'public-key' names must be distinct.";
        r22.mParseError = -108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x024a, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x024b, code lost:
    
        r23.mKeySetMapping = new android.util.ArrayMap<>();
        r5 = r9.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x025e, code lost:
    
        if (r5.hasNext() == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0260, code lost:
    
        r12 = r5.next();
        r13 = r12.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0278, code lost:
    
        if (r12.getValue().size() != 0) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x02a7, code lost:
    
        if (r10.contains(r13) == false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x02d2, code lost:
    
        r23.mKeySetMapping.put(r13, new android.util.ArraySet<>());
        r2 = r12.getValue().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x02ea, code lost:
    
        if (r2.hasNext() == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x02ec, code lost:
    
        r3 = r2.next();
        r23.mKeySetMapping.get(r13).add(r7.get(r3));
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x02a9, code lost:
    
        android.util.Slog.w(android.content.pm.PackageParser.TAG, "Package" + r23.packageName + " AndroidManifext.xml 'key-set' " + r13 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x027a, code lost:
    
        android.util.Slog.w(android.content.pm.PackageParser.TAG, "Package" + r23.packageName + " AndroidManifext.xml 'key-set' " + r13 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x031a, code lost:
    
        if (r23.mKeySetMapping.keySet().containsAll(r8) == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x031c, code lost:
    
        r23.mUpgradeKeySets = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x031f, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0320, code lost:
    
        r26[0] = "Package" + r23.packageName + " AndroidManifext.xml does not define all 'upgrade-key-set's .";
        r22.mParseError = -108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0340, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseKeySets(android.content.pm.PackageParser.Package r23, android.content.res.Resources r24, android.content.res.XmlResourceParser r25, java.lang.String[] r26) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 833
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseKeySets(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):boolean");
    }

    private boolean parsePermissionGroup(Package owner, int flags, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestPermissionGroup);
        int requestDetailResourceId = sa.getResourceId(12, 0);
        int backgroundRequestResourceId = sa.getResourceId(9, 0);
        int backgroundRequestDetailResourceId = sa.getResourceId(10, 0);
        PermissionGroup perm = new PermissionGroup(owner, requestDetailResourceId, backgroundRequestResourceId, backgroundRequestDetailResourceId);
        if (!parsePackageItemInfo(owner, perm.info, outError, "<permission-group>", sa, true, 2, 0, 1, 8, 5, 7)) {
            sa.recycle();
            this.mParseError = -108;
            return false;
        }
        perm.info.descriptionRes = sa.getResourceId(4, 0);
        perm.info.requestRes = sa.getResourceId(11, 0);
        perm.info.flags = sa.getInt(6, 0);
        perm.info.priority = sa.getInt(3, 0);
        sa.recycle();
        if (parseAllMetaData(res, parser, "<permission-group>", perm, outError)) {
            owner.permissionGroups.add(perm);
            return true;
        }
        this.mParseError = -108;
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parsePermission(android.content.pm.PackageParser.Package r22, android.content.res.Resources r23, android.content.res.XmlResourceParser r24, java.lang.String[] r25) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parsePermission(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, java.lang.String[]):boolean");
    }

    private boolean parsePermissionTree(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        int index;
        Permission perm = new Permission(owner, (String) null);
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestPermissionTree);
        if (!parsePackageItemInfo(owner, perm.info, outError, "<permission-tree>", sa, true, 2, 0, 1, 5, 3, 4)) {
            sa.recycle();
            this.mParseError = -108;
            return false;
        }
        sa.recycle();
        int index2 = perm.info.name.indexOf(46);
        if (index2 <= 0) {
            index = index2;
        } else {
            index = perm.info.name.indexOf(46, index2 + 1);
        }
        if (index < 0) {
            outError[0] = "<permission-tree> name has less than three segments: " + perm.info.name;
            this.mParseError = -108;
            return false;
        }
        perm.info.descriptionRes = 0;
        perm.info.requestRes = 0;
        perm.info.protectionLevel = 0;
        perm.tree = true;
        if (!parseAllMetaData(res, parser, "<permission-tree>", perm, outError)) {
            this.mParseError = -108;
            return false;
        }
        owner.permissions.add(perm);
        return true;
    }

    private Instrumentation parseInstrumentation(Package owner, Resources res, XmlResourceParser parser, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestInstrumentation);
        if (this.mParseInstrumentationArgs == null) {
            this.mParseInstrumentationArgs = new ParsePackageItemArgs(owner, outError, 2, 0, 1, 8, 6, 7);
            this.mParseInstrumentationArgs.tag = "<instrumentation>";
        }
        this.mParseInstrumentationArgs.sa = sa;
        Instrumentation a = new Instrumentation(this.mParseInstrumentationArgs, new InstrumentationInfo());
        if (outError[0] != null) {
            sa.recycle();
            this.mParseError = -108;
            return null;
        }
        String str = sa.getNonResourceString(3);
        a.info.targetPackage = str != null ? str.intern() : null;
        String str2 = sa.getNonResourceString(9);
        a.info.targetProcesses = str2 != null ? str2.intern() : null;
        a.info.handleProfiling = sa.getBoolean(4, false);
        a.info.functionalTest = sa.getBoolean(5, false);
        sa.recycle();
        if (a.info.targetPackage == null) {
            outError[0] = "<instrumentation> does not specify targetPackage";
            this.mParseError = -108;
            return null;
        }
        if (!parseAllMetaData(res, parser, "<instrumentation>", a, outError)) {
            this.mParseError = -108;
            return null;
        }
        owner.instrumentation.add(a);
        return a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:184:0x07c4, code lost:
    
        if (android.text.TextUtils.isEmpty(r14.staticSharedLibName) == false) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x07c6, code lost:
    
        r14.activities.add(r0.generateAppDetailsHiddenActivity(r14, r38, r9, r14.baseHardwareAccelerated));
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x07d6, code lost:
    
        if (r21 == 0) goto L321;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x07d8, code lost:
    
        java.util.Collections.sort(r14.activities, new android.content.pm.PackageParser$$ExternalSyntheticLambda0());
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x07e2, code lost:
    
        if (r23 == false) goto L323;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x07e4, code lost:
    
        java.util.Collections.sort(r14.receivers, new android.content.pm.PackageParser$$ExternalSyntheticLambda1());
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x07ee, code lost:
    
        if (r24 == false) goto L325;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x07f0, code lost:
    
        java.util.Collections.sort(r14.services, new android.content.pm.PackageParser$$ExternalSyntheticLambda2());
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x07fa, code lost:
    
        setMaxAspectRatio(r35);
        setMinAspectRatio(r35);
        setSupportsSizeChanges(r35);
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0807, code lost:
    
        if (hasDomainURLs(r35) == false) goto L328;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0809, code lost:
    
        r14.applicationInfo.privateFlags |= 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:?, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x0812, code lost:
    
        r14.applicationInfo.privateFlags &= -17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x081a, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x0667, code lost:
    
        r9[0] = "Bad static-library declaration name: " + r10 + " version: " + r13;
        r0.mParseError = -108;
        com.android.internal.util.XmlUtils.skipCurrentTag(r37);
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x068d, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseBaseApplication(android.content.pm.PackageParser.Package r35, android.content.res.Resources r36, android.content.res.XmlResourceParser r37, int r38, java.lang.String[] r39) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 2076
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseBaseApplication(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[]):boolean");
    }

    private static boolean hasDomainURLs(Package pkg) {
        if (pkg == null || pkg.activities == null) {
            return false;
        }
        ArrayList<Activity> activities = pkg.activities;
        int countActivities = activities.size();
        for (int n = 0; n < countActivities; n++) {
            Activity activity = activities.get(n);
            ArrayList<II> arrayList = activity.intents;
            if (arrayList != 0) {
                int countFilters = arrayList.size();
                for (int m = 0; m < countFilters; m++) {
                    ActivityIntentInfo aii = (ActivityIntentInfo) arrayList.get(m);
                    if (aii.hasAction("android.intent.action.VIEW") && aii.hasAction("android.intent.action.VIEW") && (aii.hasDataScheme(IntentFilter.SCHEME_HTTP) || aii.hasDataScheme(IntentFilter.SCHEME_HTTPS))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    private boolean parseSplitApplication(android.content.pm.PackageParser.Package r24, android.content.res.Resources r25, android.content.res.XmlResourceParser r26, int r27, int r28, java.lang.String[] r29) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 679
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseSplitApplication(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, int, java.lang.String[]):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean parsePackageItemInfo(Package owner, PackageItemInfo outInfo, String[] outError, String tag, TypedArray sa, boolean nameRequired, int nameRes, int labelRes, int iconRes, int roundIconRes, int logoRes, int bannerRes) {
        if (sa == null) {
            outError[0] = tag + " does not contain any attributes";
            return false;
        }
        String name = sa.getNonConfigurationString(nameRes, 0);
        if (name == null) {
            if (nameRequired) {
                outError[0] = tag + " does not specify android:name";
                return false;
            }
        } else {
            String outInfoName = buildClassName(owner.applicationInfo.packageName, name, outError);
            if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(outInfoName)) {
                outError[0] = tag + " invalid android:name";
                return false;
            }
            outInfo.name = outInfoName;
            if (outInfoName == null) {
                return false;
            }
        }
        int roundIconVal = sUseRoundIcon ? sa.getResourceId(roundIconRes, 0) : 0;
        if (roundIconVal != 0) {
            outInfo.icon = roundIconVal;
            outInfo.nonLocalizedLabel = null;
        } else {
            int iconVal = sa.getResourceId(iconRes, 0);
            if (iconVal != 0) {
                outInfo.icon = iconVal;
                outInfo.nonLocalizedLabel = null;
            }
        }
        int logoVal = sa.getResourceId(logoRes, 0);
        if (logoVal != 0) {
            outInfo.logo = logoVal;
        }
        int bannerVal = sa.getResourceId(bannerRes, 0);
        if (bannerVal != 0) {
            outInfo.banner = bannerVal;
        }
        TypedValue v = sa.peekValue(labelRes);
        if (v != null) {
            int i = v.resourceId;
            outInfo.labelRes = i;
            if (i == 0) {
                outInfo.nonLocalizedLabel = v.coerceToString();
            }
        }
        outInfo.packageName = owner.packageName;
        return true;
    }

    private Activity generateAppDetailsHiddenActivity(Package owner, int flags, String[] outError, boolean hardwareAccelerated) {
        Activity a = new Activity(owner, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, new ActivityInfo());
        a.owner = owner;
        a.setPackageName(owner.packageName);
        a.info.theme = 16973909;
        a.info.exported = true;
        a.info.name = PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME;
        a.info.processName = owner.applicationInfo.processName;
        a.info.uiOptions = a.info.applicationInfo.uiOptions;
        a.info.taskAffinity = buildTaskAffinityName(owner.packageName, owner.packageName, ":app_details", outError);
        a.info.enabled = true;
        a.info.launchMode = 0;
        a.info.documentLaunchMode = 0;
        a.info.maxRecents = ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        a.info.configChanges = getActivityConfigChanges(0, 0);
        a.info.softInputMode = 0;
        a.info.persistableMode = 1;
        a.info.screenOrientation = -1;
        a.info.resizeMode = 4;
        a.info.lockTaskLaunchMode = 0;
        a.info.directBootAware = false;
        a.info.rotationAnimation = -1;
        a.info.colorMode = 0;
        if (hardwareAccelerated) {
            a.info.flags |= 512;
        }
        return a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x06cb, code lost:
    
        resolveWindowLayout(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x06ce, code lost:
    
        if (r12 != false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x06d0, code lost:
    
        r0 = r0.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x06d8, code lost:
    
        if (r0.intents.size() <= 0) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x06da, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x06dd, code lost:
    
        r0.exported = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x06dc, code lost:
    
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x06e1, code lost:
    
        if (r0.metaData == null) goto L236;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x06e3, code lost:
    
        r0 = r0.metaData.getInt(com.samsung.android.rune.CoreMetaData.METADATA_DEX_TRANSIENT_BAR_DELAY, -1);
        r0.info.transientBarShowingDelayMillis = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x06f0, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Activity parseActivity(android.content.pm.PackageParser.Package r29, android.content.res.Resources r30, android.content.res.XmlResourceParser r31, int r32, java.lang.String[] r33, android.content.pm.PackageParser.CachedComponentArgs r34, boolean r35, boolean r36) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1777
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseActivity(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[], android.content.pm.PackageParser$CachedComponentArgs, boolean, boolean):android.content.pm.PackageParser$Activity");
    }

    private void setActivityResizeMode(ActivityInfo aInfo, TypedArray sa, Package owner) {
        boolean appExplicitDefault = (owner.applicationInfo.privateFlags & 3072) != 0;
        if (sa.hasValue(40) || appExplicitDefault) {
            boolean appResizeable = (owner.applicationInfo.privateFlags & 1024) != 0;
            if (sa.getBoolean(40, appResizeable)) {
                aInfo.resizeMode = 2;
                return;
            } else {
                aInfo.resizeMode = 0;
                return;
            }
        }
        if ((owner.applicationInfo.privateFlags & 4096) != 0) {
            aInfo.resizeMode = 1;
            return;
        }
        if (aInfo.isFixedOrientationPortrait()) {
            aInfo.resizeMode = 6;
            return;
        }
        if (aInfo.isFixedOrientationLandscape()) {
            aInfo.resizeMode = 5;
        } else if (aInfo.isFixedOrientation()) {
            aInfo.resizeMode = 7;
        } else {
            aInfo.resizeMode = 4;
        }
    }

    private void setMaxAspectRatio(Package owner) {
        float activityAspectRatio;
        float maxAspectRatio = owner.applicationInfo.targetSdkVersion < 26 ? 1.86f : 0.0f;
        if (owner.applicationInfo.maxAspectRatio != 0.0f) {
            maxAspectRatio = owner.applicationInfo.maxAspectRatio;
        } else if (owner.mAppMetaData != null && owner.mAppMetaData.containsKey("android.max_aspect")) {
            maxAspectRatio = owner.mAppMetaData.getFloat("android.max_aspect", maxAspectRatio);
        }
        Iterator<Activity> it = owner.activities.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (!activity.hasMaxAspectRatio()) {
                if (activity.metaData != null) {
                    activityAspectRatio = activity.metaData.getFloat("android.max_aspect", maxAspectRatio);
                } else {
                    activityAspectRatio = maxAspectRatio;
                }
                activity.setMaxAspectRatio(activityAspectRatio);
            }
        }
    }

    private void setMinAspectRatio(Package owner) {
        float minAspectRatio = owner.applicationInfo.minAspectRatio;
        Iterator<Activity> it = owner.activities.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (!activity.hasMinAspectRatio()) {
                activity.setMinAspectRatio(minAspectRatio);
            }
        }
    }

    private void setSupportsSizeChanges(Package owner) {
        boolean supportsSizeChanges = owner.mAppMetaData != null && owner.mAppMetaData.getBoolean("android.supports_size_changes", false);
        Iterator<Activity> it = owner.activities.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (supportsSizeChanges || (activity.metaData != null && activity.metaData.getBoolean("android.supports_size_changes", false))) {
                activity.info.supportsSizeChanges = true;
            }
        }
    }

    public static int getActivityConfigChanges(int configChanges, int recreateOnConfigChanges) {
        return ((~recreateOnConfigChanges) & 3) | configChanges;
    }

    private void parseLayout(Resources res, AttributeSet attrs, Activity a) {
        TypedArray sw = res.obtainAttributes(attrs, R.styleable.AndroidManifestLayout);
        int width = -1;
        float widthFraction = -1.0f;
        int height = -1;
        float heightFraction = -1.0f;
        int widthType = sw.getType(3);
        if (widthType == 6) {
            widthFraction = sw.getFraction(3, 1, 1, -1.0f);
        } else if (widthType == 5) {
            width = sw.getDimensionPixelSize(3, -1);
        }
        int heightType = sw.getType(4);
        if (heightType == 6) {
            heightFraction = sw.getFraction(4, 1, 1, -1.0f);
        } else if (heightType == 5) {
            height = sw.getDimensionPixelSize(4, -1);
        }
        int gravity = sw.getInt(0, 17);
        int minWidth = sw.getDimensionPixelSize(1, -1);
        int minHeight = sw.getDimensionPixelSize(2, -1);
        sw.recycle();
        a.info.windowLayout = new ActivityInfo.WindowLayout(width, widthFraction, height, heightFraction, gravity, minWidth, minHeight);
    }

    private void resolveWindowLayout(Activity activity) {
        if (activity.metaData == null || !activity.metaData.containsKey("android.activity_window_layout_affinity")) {
            return;
        }
        ActivityInfo aInfo = activity.info;
        if (aInfo.windowLayout == null || aInfo.windowLayout.windowLayoutAffinity == null) {
            String windowLayoutAffinity = activity.metaData.getString("android.activity_window_layout_affinity");
            if (aInfo.windowLayout == null) {
                aInfo.windowLayout = new ActivityInfo.WindowLayout(-1, -1.0f, -1, -1.0f, 0, -1, -1);
            }
            aInfo.windowLayout.windowLayoutAffinity = windowLayoutAffinity;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x03b7, code lost:
    
        if (r14 != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x03b9, code lost:
    
        r0 = r12.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x03c1, code lost:
    
        if (r12.intents.size() <= 0) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x03c3, code lost:
    
        r1 = r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x03c8, code lost:
    
        r0.exported = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x03c6, code lost:
    
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x03ca, code lost:
    
        return r12;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.res.TypedArray] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v28 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Activity parseActivityAlias(android.content.pm.PackageParser.Package r34, android.content.res.Resources r35, android.content.res.XmlResourceParser r36, int r37, java.lang.String[] r38, android.content.pm.PackageParser.CachedComponentArgs r39) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 971
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseActivityAlias(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[], android.content.pm.PackageParser$CachedComponentArgs):android.content.pm.PackageParser$Activity");
    }

    private Provider parseProvider(Package owner, Resources res, XmlResourceParser parser, int flags, String[] outError, CachedComponentArgs cachedArgs) throws XmlPullParserException, IOException {
        TypedArray sa;
        boolean providerExportedDefault;
        String str;
        TypedArray sa2 = res.obtainAttributes(parser, R.styleable.AndroidManifestProvider);
        if (cachedArgs.mProviderArgs != null) {
            sa = sa2;
        } else {
            sa = sa2;
            cachedArgs.mProviderArgs = new ParseComponentArgs(owner, outError, 2, 0, 1, 19, 15, 17, this.mSeparateProcesses, 8, 14, 6);
            cachedArgs.mProviderArgs.tag = "<provider>";
        }
        TypedArray sa3 = sa;
        cachedArgs.mProviderArgs.sa = sa3;
        cachedArgs.mProviderArgs.flags = flags;
        Provider p = new Provider(cachedArgs.mProviderArgs, new ProviderInfo());
        if (outError[0] != null) {
            sa3.recycle();
            return null;
        }
        if (owner.applicationInfo.targetSdkVersion >= 17) {
            providerExportedDefault = false;
        } else {
            providerExportedDefault = true;
        }
        p.info.exported = sa3.getBoolean(7, providerExportedDefault);
        String cpname = sa3.getNonConfigurationString(10, 0);
        p.info.isSyncable = sa3.getBoolean(11, false);
        String permission = sa3.getNonConfigurationString(3, 0);
        String str2 = sa3.getNonConfigurationString(4, 0);
        if (str2 == null) {
            str2 = permission;
        }
        if (str2 == null) {
            p.info.readPermission = owner.applicationInfo.permission;
        } else {
            p.info.readPermission = str2.length() > 0 ? str2.toString().intern() : null;
        }
        String str3 = sa3.getNonConfigurationString(5, 0);
        if (str3 != null) {
            str = str3;
        } else {
            str = permission;
        }
        if (str == null) {
            p.info.writePermission = owner.applicationInfo.permission;
        } else {
            p.info.writePermission = str.length() > 0 ? str.toString().intern() : null;
        }
        p.info.grantUriPermissions = sa3.getBoolean(13, false);
        p.info.forceUriPermissions = sa3.getBoolean(22, false);
        p.info.multiprocess = sa3.getBoolean(9, false);
        p.info.initOrder = sa3.getInt(12, 0);
        p.info.splitName = sa3.getNonConfigurationString(21, 0);
        p.info.flags = 0;
        if (sa3.getBoolean(16, false)) {
            p.info.flags |= 1073741824;
        }
        p.info.directBootAware = sa3.getBoolean(18, false);
        if (p.info.directBootAware) {
            owner.applicationInfo.privateFlags |= 256;
        }
        boolean visibleToEphemeral = sa3.getBoolean(20, false);
        if (visibleToEphemeral) {
            p.info.flags |= 1048576;
            owner.visibleToInstantApps = true;
        }
        sa3.recycle();
        if ((owner.applicationInfo.privateFlags & 2) != 0 && p.info.processName == owner.packageName) {
            outError[0] = "Heavy-weight applications can not have providers in main process";
            return null;
        }
        if (cpname == null) {
            outError[0] = "<provider> does not include authorities attribute";
            return null;
        }
        if (cpname.length() <= 0) {
            outError[0] = "<provider> has empty authorities attribute";
            return null;
        }
        p.info.authority = cpname.intern();
        if (parseProviderTags(res, parser, visibleToEphemeral, p, outError)) {
            return p;
        }
        return null;
    }

    private boolean parseProviderTags(Resources res, XmlResourceParser parser, boolean visibleToEphemeral, Provider outInfo, String[] outError) throws XmlPullParserException, IOException {
        String readPermission;
        String readPermission2;
        String writePermission;
        PathPermission pa;
        PathPermission pa2;
        PathPermission pa3;
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1) {
                if (type != 3 || parser.getDepth() > outerDepth) {
                    if (type != 3 && type != 4) {
                        if (parser.getName().equals("intent-filter")) {
                            ProviderIntentInfo intent = new ProviderIntentInfo(outInfo);
                            if (!parseIntent(res, parser, true, false, intent, outError)) {
                                return false;
                            }
                            if (visibleToEphemeral) {
                                intent.setVisibilityToInstantApp(1);
                                outInfo.info.flags |= 1048576;
                            }
                            outInfo.order = Math.max(intent.getOrder(), outInfo.order);
                            outInfo.intents.add(intent);
                        } else if (parser.getName().equals("meta-data")) {
                            Bundle parseMetaData = parseMetaData(res, parser, outInfo.metaData, outError);
                            outInfo.metaData = parseMetaData;
                            if (parseMetaData == null) {
                                return false;
                            }
                        } else if (parser.getName().equals("grant-uri-permission")) {
                            TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestGrantUriPermission);
                            PatternMatcher pa4 = null;
                            String str = sa.getNonConfigurationString(0, 0);
                            if (str != null) {
                                pa4 = new PatternMatcher(str, 0);
                            }
                            String str2 = sa.getNonConfigurationString(1, 0);
                            if (str2 != null) {
                                pa4 = new PatternMatcher(str2, 1);
                            }
                            String str3 = sa.getNonConfigurationString(2, 0);
                            if (str3 != null) {
                                pa4 = new PatternMatcher(str3, 2);
                            }
                            sa.recycle();
                            if (pa4 != null) {
                                if (outInfo.info.uriPermissionPatterns == null) {
                                    outInfo.info.uriPermissionPatterns = new PatternMatcher[1];
                                    outInfo.info.uriPermissionPatterns[0] = pa4;
                                } else {
                                    int N = outInfo.info.uriPermissionPatterns.length;
                                    PatternMatcher[] newp = new PatternMatcher[N + 1];
                                    System.arraycopy(outInfo.info.uriPermissionPatterns, 0, newp, 0, N);
                                    newp[N] = pa4;
                                    outInfo.info.uriPermissionPatterns = newp;
                                }
                                outInfo.info.grantUriPermissions = true;
                                XmlUtils.skipCurrentTag(parser);
                            } else {
                                Slog.w(TAG, "Unknown element under <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
                                XmlUtils.skipCurrentTag(parser);
                            }
                        } else if (parser.getName().equals("path-permission")) {
                            TypedArray sa2 = res.obtainAttributes(parser, R.styleable.AndroidManifestPathPermission);
                            String permission = sa2.getNonConfigurationString(0, 0);
                            String readPermission3 = sa2.getNonConfigurationString(1, 0);
                            if (readPermission3 != null) {
                                readPermission = readPermission3;
                            } else {
                                readPermission = permission;
                            }
                            String writePermission2 = sa2.getNonConfigurationString(2, 0);
                            if (writePermission2 == null) {
                                writePermission2 = permission;
                            }
                            boolean havePerm = false;
                            if (readPermission == null) {
                                readPermission2 = readPermission;
                            } else {
                                havePerm = true;
                                readPermission2 = readPermission.intern();
                            }
                            if (writePermission2 == null) {
                                writePermission = writePermission2;
                            } else {
                                havePerm = true;
                                writePermission = writePermission2.intern();
                            }
                            if (!havePerm) {
                                Slog.w(TAG, "No readPermission or writePermssion for <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
                                sa2.recycle();
                                XmlUtils.skipCurrentTag(parser);
                            } else {
                                String path = sa2.getNonConfigurationString(3, 0);
                                if (path == null) {
                                    pa = null;
                                } else {
                                    pa = new PathPermission(path, 0, readPermission2, writePermission);
                                }
                                PathPermission pa5 = pa;
                                String path2 = sa2.getNonConfigurationString(4, 0);
                                if (path2 == null) {
                                    pa2 = pa5;
                                } else {
                                    pa2 = new PathPermission(path2, 1, readPermission2, writePermission);
                                }
                                String path3 = sa2.getNonConfigurationString(5, 0);
                                if (path3 != null) {
                                    pa2 = new PathPermission(path3, 2, readPermission2, writePermission);
                                }
                                PathPermission pa6 = pa2;
                                String path4 = sa2.getNonConfigurationString(7, 0);
                                if (path4 == null) {
                                    pa3 = pa6;
                                } else {
                                    pa3 = new PathPermission(path4, 3, readPermission2, writePermission);
                                }
                                sa2.recycle();
                                if (pa3 != null) {
                                    if (outInfo.info.pathPermissions == null) {
                                        outInfo.info.pathPermissions = new PathPermission[1];
                                        outInfo.info.pathPermissions[0] = pa3;
                                    } else {
                                        int N2 = outInfo.info.pathPermissions.length;
                                        PathPermission[] newp2 = new PathPermission[N2 + 1];
                                        System.arraycopy(outInfo.info.pathPermissions, 0, newp2, 0, N2);
                                        newp2[N2] = pa3;
                                        outInfo.info.pathPermissions = newp2;
                                    }
                                    XmlUtils.skipCurrentTag(parser);
                                } else {
                                    Slog.w(TAG, "No path, pathPrefix, or pathPattern for <path-permission>: " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
                                    XmlUtils.skipCurrentTag(parser);
                                }
                            }
                        } else {
                            Slog.w(TAG, "Unknown element under <provider>: " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
                            XmlUtils.skipCurrentTag(parser);
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0268, code lost:
    
        if (r12 != false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x026a, code lost:
    
        r1 = r0.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0272, code lost:
    
        if (r0.intents.size() <= 0) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0274, code lost:
    
        r10 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0277, code lost:
    
        r1.exported = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0276, code lost:
    
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0279, code lost:
    
        return r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.PackageParser.Service parseService(android.content.pm.PackageParser.Package r26, android.content.res.Resources r27, android.content.res.XmlResourceParser r28, int r29, java.lang.String[] r30, android.content.pm.PackageParser.CachedComponentArgs r31) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.parseService(android.content.pm.PackageParser$Package, android.content.res.Resources, android.content.res.XmlResourceParser, int, java.lang.String[], android.content.pm.PackageParser$CachedComponentArgs):android.content.pm.PackageParser$Service");
    }

    private boolean isImplicitlyExposedIntent(IntentInfo intent) {
        return intent.hasCategory(Intent.CATEGORY_BROWSABLE) || intent.hasAction(Intent.ACTION_SEND) || intent.hasAction(Intent.ACTION_SENDTO) || intent.hasAction(Intent.ACTION_SEND_MULTIPLE);
    }

    private boolean parseAllMetaData(Resources res, XmlResourceParser parser, String tag, Component<?> outInfo, String[] outError) throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                break;
            }
            if (type != 3 && type != 4) {
                if (parser.getName().equals("meta-data")) {
                    Bundle parseMetaData = parseMetaData(res, parser, outInfo.metaData, outError);
                    outInfo.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return false;
                    }
                } else {
                    Slog.w(TAG, "Unknown element under " + tag + ": " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
                    XmlUtils.skipCurrentTag(parser);
                }
            }
        }
        return true;
    }

    private Bundle parseMetaData(Resources res, XmlResourceParser parser, Bundle data, String[] outError) throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestMetaData);
        if (data == null) {
            data = new Bundle();
        }
        boolean z = false;
        String name = sa.getNonConfigurationString(0, 0);
        if (name == null) {
            outError[0] = "<meta-data> requires an android:name attribute";
            sa.recycle();
            return null;
        }
        String name2 = name.intern();
        TypedValue v = sa.peekValue(2);
        if (v != null && v.resourceId != 0) {
            data.putInt(name2, v.resourceId);
        } else {
            TypedValue v2 = sa.peekValue(1);
            if (v2 == null) {
                outError[0] = "<meta-data> requires an android:value or android:resource attribute";
                data = null;
            } else if (v2.type == 3) {
                CharSequence cs = v2.coerceToString();
                data.putString(name2, cs != null ? cs.toString() : null);
            } else if (v2.type == 18) {
                if (v2.data != 0) {
                    z = true;
                }
                data.putBoolean(name2, z);
            } else if (v2.type >= 16 && v2.type <= 31) {
                data.putInt(name2, v2.data);
            } else if (v2.type == 4) {
                data.putFloat(name2, v2.getFloat());
            } else {
                Slog.w(TAG, "<meta-data> only supports string, integer, float, color, boolean, and resource reference types: " + parser.getName() + " at " + this.mArchiveSourcePath + " " + parser.getPositionDescription());
            }
        }
        sa.recycle();
        XmlUtils.skipCurrentTag(parser);
        return data;
    }

    private static VerifierInfo parseVerifier(AttributeSet attrs) {
        String packageName = null;
        String encodedPublicKey = null;
        int attrCount = attrs.getAttributeCount();
        for (int i = 0; i < attrCount; i++) {
            int attrResId = attrs.getAttributeNameResource(i);
            switch (attrResId) {
                case 16842755:
                    packageName = attrs.getAttributeValue(i);
                    break;
                case 16843686:
                    encodedPublicKey = attrs.getAttributeValue(i);
                    break;
            }
        }
        if (packageName == null || packageName.length() == 0) {
            Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        PublicKey publicKey = parsePublicKey(encodedPublicKey);
        if (publicKey == null) {
            Slog.i(TAG, "Unable to parse verifier public key for " + packageName);
            return null;
        }
        return new VerifierInfo(packageName, publicKey);
    }

    public static final PublicKey parsePublicKey(String encodedPublicKey) {
        if (encodedPublicKey == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            return parsePublicKey(Base64.decode(encodedPublicKey, 0));
        } catch (IllegalArgumentException e) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static final PublicKey parsePublicKey(byte[] publicKey) {
        if (publicKey == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    KeyFactory keyFactory2 = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC);
                    return keyFactory2.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException e2) {
                    Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        KeyFactory keyFactory3 = KeyFactory.getInstance("DSA");
                        return keyFactory3.generatePublic(keySpec);
                    } catch (NoSuchAlgorithmException e3) {
                        Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (InvalidKeySpecException e4) {
                        return null;
                    }
                } catch (InvalidKeySpecException e5) {
                    KeyFactory keyFactory32 = KeyFactory.getInstance("DSA");
                    return keyFactory32.generatePublic(keySpec);
                }
            } catch (InvalidKeySpecException e6) {
                KeyFactory keyFactory22 = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC);
                return keyFactory22.generatePublic(keySpec);
            }
        } catch (IllegalArgumentException e7) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x01e1, code lost:
    
        r23.hasDefault = r23.hasCategory(android.content.Intent.CATEGORY_DEFAULT);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x01ea, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b0, code lost:
    
        r24[0] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b2, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d3, code lost:
    
        r24[0] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d5, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean parseIntent(android.content.res.Resources r19, android.content.res.XmlResourceParser r20, boolean r21, boolean r22, android.content.pm.PackageParser.IntentInfo r23, java.lang.String[] r24) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 491
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
            public SigningDetails createFromParcel(Parcel source) {
                if (source.readBoolean()) {
                    return SigningDetails.UNKNOWN;
                }
                return new SigningDetails(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SigningDetails[] newArray(int size) {
                return new SigningDetails[size];
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

        public SigningDetails(Signature[] signatures, int signatureSchemeVersion, ArraySet<PublicKey> keys, Signature[] pastSigningCertificates) {
            this.signatures = signatures;
            this.signatureSchemeVersion = signatureSchemeVersion;
            this.publicKeys = keys;
            this.pastSigningCertificates = pastSigningCertificates;
        }

        public SigningDetails(Signature[] signatures, int signatureSchemeVersion, Signature[] pastSigningCertificates) throws CertificateException {
            this(signatures, signatureSchemeVersion, PackageParser.toSigningKeys(signatures), pastSigningCertificates);
        }

        public SigningDetails(Signature[] signatures, int signatureSchemeVersion) throws CertificateException {
            this(signatures, signatureSchemeVersion, null);
        }

        public SigningDetails(SigningDetails orig) {
            if (orig != null) {
                if (orig.signatures != null) {
                    this.signatures = (Signature[]) orig.signatures.clone();
                } else {
                    this.signatures = null;
                }
                this.signatureSchemeVersion = orig.signatureSchemeVersion;
                this.publicKeys = new ArraySet<>((ArraySet) orig.publicKeys);
                if (orig.pastSigningCertificates != null) {
                    this.pastSigningCertificates = (Signature[]) orig.pastSigningCertificates.clone();
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

        public SigningDetails mergeLineageWith(SigningDetails otherSigningDetails) {
            if (!hasPastSigningCertificates()) {
                return (otherSigningDetails.hasPastSigningCertificates() && otherSigningDetails.hasAncestorOrSelf(this)) ? otherSigningDetails : this;
            }
            if (!otherSigningDetails.hasPastSigningCertificates()) {
                return this;
            }
            SigningDetails descendantSigningDetails = getDescendantOrSelf(otherSigningDetails);
            if (descendantSigningDetails == null) {
                return this;
            }
            return descendantSigningDetails == this ? mergeLineageWithAncestorOrSelf(otherSigningDetails) : otherSigningDetails.mergeLineageWithAncestorOrSelf(this);
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0077, code lost:
        
            if (r7 < 0) goto L49;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0079, code lost:
        
            return r10;
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

        public boolean hasCommonAncestor(SigningDetails otherSigningDetails) {
            if (!hasPastSigningCertificates()) {
                return otherSigningDetails.hasAncestorOrSelf(this);
            }
            if (otherSigningDetails.hasPastSigningCertificates()) {
                return getDescendantOrSelf(otherSigningDetails) != null;
            }
            return hasAncestorOrSelf(otherSigningDetails);
        }

        public boolean hasAncestorOrSelfWithDigest(Set<String> certDigests) {
            if (this == UNKNOWN || certDigests == null || certDigests.size() == 0) {
                return false;
            }
            if (this.signatures.length > 1) {
                if (certDigests.size() < this.signatures.length) {
                    return false;
                }
                for (Signature signature : this.signatures) {
                    String signatureDigest = PackageUtils.computeSha256Digest(signature.toByteArray());
                    if (!certDigests.contains(signatureDigest)) {
                        return false;
                    }
                }
                return true;
            }
            String signatureDigest2 = PackageUtils.computeSha256Digest(this.signatures[0].toByteArray());
            if (certDigests.contains(signatureDigest2)) {
                return true;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    String signatureDigest3 = PackageUtils.computeSha256Digest(this.pastSigningCertificates[i].toByteArray());
                    if (certDigests.contains(signatureDigest3)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private SigningDetails getDescendantOrSelf(SigningDetails otherSigningDetails) {
            SigningDetails descendantSigningDetails;
            SigningDetails ancestorSigningDetails;
            if (hasAncestorOrSelf(otherSigningDetails)) {
                descendantSigningDetails = this;
                ancestorSigningDetails = otherSigningDetails;
            } else {
                if (!otherSigningDetails.hasAncestor(this)) {
                    return null;
                }
                descendantSigningDetails = otherSigningDetails;
                ancestorSigningDetails = this;
            }
            int descendantIndex = descendantSigningDetails.pastSigningCertificates.length - 1;
            int ancestorIndex = ancestorSigningDetails.pastSigningCertificates.length - 1;
            while (descendantIndex >= 0 && !descendantSigningDetails.pastSigningCertificates[descendantIndex].equals(ancestorSigningDetails.pastSigningCertificates[ancestorIndex])) {
                descendantIndex--;
            }
            if (descendantIndex < 0) {
                return null;
            }
            do {
                descendantIndex--;
                ancestorIndex--;
                if (descendantIndex < 0 || ancestorIndex < 0) {
                    break;
                }
            } while (descendantSigningDetails.pastSigningCertificates[descendantIndex].equals(ancestorSigningDetails.pastSigningCertificates[ancestorIndex]));
            if (descendantIndex < 0 || ancestorIndex < 0) {
                return descendantSigningDetails;
            }
            return null;
        }

        public boolean hasSignatures() {
            return this.signatures != null && this.signatures.length > 0;
        }

        public boolean hasPastSigningCertificates() {
            return this.pastSigningCertificates != null && this.pastSigningCertificates.length > 0;
        }

        public boolean hasAncestorOrSelf(SigningDetails oldDetails) {
            if (this == UNKNOWN || oldDetails == UNKNOWN) {
                return false;
            }
            if (oldDetails.signatures.length > 1) {
                return signaturesMatchExactly(oldDetails);
            }
            return hasCertificate(oldDetails.signatures[0]);
        }

        public boolean hasAncestor(SigningDetails oldDetails) {
            if (this != UNKNOWN && oldDetails != UNKNOWN && hasPastSigningCertificates() && oldDetails.signatures.length == 1) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (this.pastSigningCertificates[i].equals(oldDetails.signatures[0])) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean hasCommonSignerWithCapability(SigningDetails otherDetails, int flags) {
            if (this == UNKNOWN || otherDetails == UNKNOWN) {
                return false;
            }
            if (this.signatures.length > 1 || otherDetails.signatures.length > 1) {
                return signaturesMatchExactly(otherDetails);
            }
            Set<Signature> otherSignatures = new ArraySet<>();
            if (otherDetails.hasPastSigningCertificates()) {
                otherSignatures.addAll(Arrays.asList(otherDetails.pastSigningCertificates));
            } else {
                otherSignatures.addAll(Arrays.asList(otherDetails.signatures));
            }
            if (otherSignatures.contains(this.signatures[0])) {
                return true;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (otherSignatures.contains(this.pastSigningCertificates[i]) && (this.pastSigningCertificates[i].getFlags() & flags) == flags) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean checkCapability(SigningDetails oldDetails, int flags) {
            if (this == UNKNOWN || oldDetails == UNKNOWN) {
                return false;
            }
            if (oldDetails.signatures.length == 0) {
                Slog.e(PackageParser.TAG, "There isn't any certificates in this package");
                return false;
            }
            if (oldDetails.signatures.length > 1) {
                return signaturesMatchExactly(oldDetails);
            }
            return hasCertificate(oldDetails.signatures[0], flags);
        }

        public boolean checkCapabilityRecover(SigningDetails oldDetails, int flags) throws CertificateException {
            if (oldDetails == UNKNOWN || this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates() && oldDetails.signatures.length == 1) {
                for (int i = 0; i < this.pastSigningCertificates.length; i++) {
                    if (Signature.areEffectiveMatch(oldDetails.signatures[0], this.pastSigningCertificates[i]) && this.pastSigningCertificates[i].getFlags() == flags) {
                        return true;
                    }
                }
                return false;
            }
            return Signature.areEffectiveArraysMatch(oldDetails.signatures, this.signatures);
        }

        public boolean hasCertificate(Signature signature) {
            return hasCertificateInternal(signature, 0);
        }

        public boolean hasCertificate(Signature signature, int flags) {
            return hasCertificateInternal(signature, flags);
        }

        public boolean hasCertificate(byte[] certificate) {
            Signature signature = new Signature(certificate);
            return hasCertificate(signature);
        }

        private boolean hasCertificateInternal(Signature signature, int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (this.pastSigningCertificates[i].equals(signature) && (flags == 0 || (this.pastSigningCertificates[i].getFlags() & flags) == flags)) {
                        return true;
                    }
                }
            }
            return this.signatures.length == 1 && this.signatures[0].equals(signature);
        }

        public boolean checkCapability(String sha256String, int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            byte[] sha256Bytes = sha256String == null ? null : HexEncoding.decode(sha256String, false);
            if (hasSha256Certificate(sha256Bytes, flags)) {
                return true;
            }
            String[] mSignaturesSha256Digests = PackageUtils.computeSignaturesSha256Digests(this.signatures);
            String mSignaturesSha256Digest = PackageUtils.computeSignaturesSha256Digest(mSignaturesSha256Digests);
            return mSignaturesSha256Digest.equals(sha256String);
        }

        public boolean hasSha256Certificate(byte[] sha256Certificate) {
            return hasSha256CertificateInternal(sha256Certificate, 0);
        }

        public boolean hasSha256Certificate(byte[] sha256Certificate, int flags) {
            return hasSha256CertificateInternal(sha256Certificate, flags);
        }

        private boolean hasSha256CertificateInternal(byte[] sha256Certificate, int flags) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    byte[] digest = PackageUtils.computeSha256DigestBytes(this.pastSigningCertificates[i].toByteArray());
                    if (Arrays.equals(sha256Certificate, digest) && (flags == 0 || (this.pastSigningCertificates[i].getFlags() & flags) == flags)) {
                        return true;
                    }
                }
            }
            if (this.signatures.length != 1) {
                return false;
            }
            byte[] digest2 = PackageUtils.computeSha256DigestBytes(this.signatures[0].toByteArray());
            return Arrays.equals(sha256Certificate, digest2);
        }

        public boolean signaturesMatchExactly(SigningDetails other) {
            return Signature.areExactArraysMatch(this.signatures, other.signatures);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            boolean isUnknown = UNKNOWN == this;
            dest.writeBoolean(isUnknown);
            if (isUnknown) {
                return;
            }
            dest.writeTypedArray(this.signatures, flags);
            dest.writeInt(this.signatureSchemeVersion);
            dest.writeArraySet(this.publicKeys);
            dest.writeTypedArray(this.pastSigningCertificates, flags);
        }

        protected SigningDetails(Parcel in) {
            ClassLoader boot = Object.class.getClassLoader();
            this.signatures = (Signature[]) in.createTypedArray(Signature.CREATOR);
            this.signatureSchemeVersion = in.readInt();
            this.publicKeys = in.readArraySet(boot);
            this.pastSigningCertificates = (Signature[]) in.createTypedArray(Signature.CREATOR);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SigningDetails)) {
                return false;
            }
            SigningDetails that = (SigningDetails) o;
            if (this.signatureSchemeVersion != that.signatureSchemeVersion || !Signature.areExactArraysMatch(this.signatures, that.signatures)) {
                return false;
            }
            if (this.publicKeys != null) {
                if (!this.publicKeys.equals(that.publicKeys)) {
                    return false;
                }
            } else if (that.publicKeys != null) {
                return false;
            }
            if (!Arrays.equals(this.pastSigningCertificates, that.pastSigningCertificates)) {
                return false;
            }
            for (int i = 0; i < this.pastSigningCertificates.length; i++) {
                if (this.pastSigningCertificates[i].getFlags() != that.pastSigningCertificates[i].getFlags()) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int result = Arrays.hashCode(this.signatures);
            return (((((result * 31) + this.signatureSchemeVersion) * 31) + (this.publicKeys != null ? this.publicKeys.hashCode() : 0)) * 31) + Arrays.hashCode(this.pastSigningCertificates);
        }

        public static class Builder {
            private Signature[] mPastSigningCertificates;
            private int mSignatureSchemeVersion = 0;
            private Signature[] mSignatures;

            public Builder setSignatures(Signature[] signatures) {
                this.mSignatures = signatures;
                return this;
            }

            public Builder setSignatureSchemeVersion(int signatureSchemeVersion) {
                this.mSignatureSchemeVersion = signatureSchemeVersion;
                return this;
            }

            public Builder setPastSigningCertificates(Signature[] pastSigningCertificates) {
                this.mPastSigningCertificates = pastSigningCertificates;
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
            public Package createFromParcel(Parcel in) {
                return new Package(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Package[] newArray(int size) {
                return new Package[size];
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

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
        }

        public Package(String packageName) {
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
            this.packageName = packageName;
            this.manifestPackageName = packageName;
            this.applicationInfo.packageName = packageName;
            this.applicationInfo.uid = -1;
        }

        public void setApplicationVolumeUuid(String volumeUuid) {
            UUID storageUuid = StorageManager.convert(volumeUuid);
            this.applicationInfo.volumeUuid = volumeUuid;
            this.applicationInfo.storageUuid = storageUuid;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.volumeUuid = volumeUuid;
                    this.childPackages.get(i).applicationInfo.storageUuid = storageUuid;
                }
            }
        }

        public void setApplicationInfoCodePath(String codePath) {
            this.applicationInfo.setCodePath(codePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setCodePath(codePath);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoResourcePath(String resourcePath) {
            this.applicationInfo.setResourcePath(resourcePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setResourcePath(resourcePath);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoBaseResourcePath(String resourcePath) {
            this.applicationInfo.setBaseResourcePath(resourcePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseResourcePath(resourcePath);
                }
            }
        }

        public void setApplicationInfoBaseCodePath(String baseCodePath) {
            this.applicationInfo.setBaseCodePath(baseCodePath);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseCodePath(baseCodePath);
                }
            }
        }

        public List<String> getChildPackageNames() {
            if (this.childPackages == null) {
                return null;
            }
            int childCount = this.childPackages.size();
            List<String> childPackageNames = new ArrayList<>(childCount);
            for (int i = 0; i < childCount; i++) {
                String childPackageName = this.childPackages.get(i).packageName;
                childPackageNames.add(childPackageName);
            }
            return childPackageNames;
        }

        public boolean hasChildPackage(String packageName) {
            int childCount = this.childPackages != null ? this.childPackages.size() : 0;
            for (int i = 0; i < childCount; i++) {
                if (this.childPackages.get(i).packageName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }

        public void setApplicationInfoSplitCodePaths(String[] splitCodePaths) {
            this.applicationInfo.setSplitCodePaths(splitCodePaths);
        }

        @Deprecated
        public void setApplicationInfoSplitResourcePaths(String[] resroucePaths) {
            this.applicationInfo.setSplitResourcePaths(resroucePaths);
        }

        public void setSplitCodePaths(String[] codePaths) {
            this.splitCodePaths = codePaths;
        }

        public void setCodePath(String codePath) {
            this.codePath = codePath;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).codePath = codePath;
                }
            }
        }

        public void setBaseCodePath(String baseCodePath) {
            this.baseCodePath = baseCodePath;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).baseCodePath = baseCodePath;
                }
            }
        }

        public void setSigningDetails(SigningDetails signingDetails) {
            this.mSigningDetails = signingDetails;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).mSigningDetails = signingDetails;
                }
            }
        }

        public void setVolumeUuid(String volumeUuid) {
            this.volumeUuid = volumeUuid;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).volumeUuid = volumeUuid;
                }
            }
        }

        public void setApplicationInfoFlags(int mask, int flags) {
            this.applicationInfo.flags = (this.applicationInfo.flags & (~mask)) | (mask & flags);
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).applicationInfo.flags = (this.applicationInfo.flags & (~mask)) | (mask & flags);
                }
            }
        }

        public void setUse32bitAbi(boolean use32bitAbi) {
            this.use32bitAbi = use32bitAbi;
            if (this.childPackages != null) {
                int packageCount = this.childPackages.size();
                for (int i = 0; i < packageCount; i++) {
                    this.childPackages.get(i).use32bitAbi = use32bitAbi;
                }
            }
        }

        public boolean isLibrary() {
            return (this.staticSharedLibName == null && ArrayUtils.isEmpty(this.libraryNames)) ? false : true;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList<>();
            paths.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }
            return paths;
        }

        public List<String> getAllCodePathsExcludingResourceOnly() {
            ArrayList<String> paths = new ArrayList<>();
            if ((this.applicationInfo.flags & 4) != 0) {
                paths.add(this.baseCodePath);
            }
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                for (int i = 0; i < this.splitCodePaths.length; i++) {
                    if ((this.splitFlags[i] & 4) != 0) {
                        paths.add(this.splitCodePaths[i]);
                    }
                }
            }
            return paths;
        }

        public void setPackageName(String newName) {
            this.packageName = newName;
            this.applicationInfo.packageName = newName;
            for (int i = this.permissions.size() - 1; i >= 0; i--) {
                this.permissions.get(i).setPackageName(newName);
            }
            for (int i2 = this.permissionGroups.size() - 1; i2 >= 0; i2--) {
                this.permissionGroups.get(i2).setPackageName(newName);
            }
            for (int i3 = this.activities.size() - 1; i3 >= 0; i3--) {
                this.activities.get(i3).setPackageName(newName);
            }
            for (int i4 = this.receivers.size() - 1; i4 >= 0; i4--) {
                this.receivers.get(i4).setPackageName(newName);
            }
            for (int i5 = this.providers.size() - 1; i5 >= 0; i5--) {
                this.providers.get(i5).setPackageName(newName);
            }
            for (int i6 = this.services.size() - 1; i6 >= 0; i6--) {
                this.services.get(i6).setPackageName(newName);
            }
            for (int i7 = this.instrumentation.size() - 1; i7 >= 0; i7--) {
                this.instrumentation.get(i7).setPackageName(newName);
            }
        }

        public boolean hasComponentClassName(String name) {
            for (int i = this.activities.size() - 1; i >= 0; i--) {
                if (name.equals(this.activities.get(i).className)) {
                    return true;
                }
            }
            for (int i2 = this.receivers.size() - 1; i2 >= 0; i2--) {
                if (name.equals(this.receivers.get(i2).className)) {
                    return true;
                }
            }
            for (int i3 = this.providers.size() - 1; i3 >= 0; i3--) {
                if (name.equals(this.providers.get(i3).className)) {
                    return true;
                }
            }
            for (int i4 = this.services.size() - 1; i4 >= 0; i4--) {
                if (name.equals(this.services.get(i4).className)) {
                    return true;
                }
            }
            for (int i5 = this.instrumentation.size() - 1; i5 >= 0; i5--) {
                if (name.equals(this.instrumentation.get(i5).className)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isExternal() {
            return this.applicationInfo.isExternal();
        }

        public boolean isForwardLocked() {
            return false;
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

        public boolean canHaveOatDir() {
            return true;
        }

        public boolean isMatch(int flags) {
            if ((1048576 & flags) != 0) {
                return isSystem();
            }
            return true;
        }

        public long getLatestPackageUseTimeInMills() {
            long latestUse = 0;
            for (long use : this.mLastPackageUsageTimeInMills) {
                latestUse = Math.max(latestUse, use);
            }
            return latestUse;
        }

        public long getLatestForegroundPackageUseTimeInMills() {
            int[] foregroundReasons = {0, 2};
            long latestUse = 0;
            for (int reason : foregroundReasons) {
                latestUse = Math.max(latestUse, this.mLastPackageUsageTimeInMills[reason]);
            }
            return latestUse;
        }

        public String toString() {
            return "Package{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Package(Parcel dest) {
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
            ClassLoader boot = Object.class.getClassLoader();
            this.packageName = dest.readString().intern();
            this.manifestPackageName = dest.readString();
            this.splitNames = dest.readStringArray();
            this.volumeUuid = dest.readString();
            this.codePath = dest.readString();
            this.baseCodePath = dest.readString();
            this.splitCodePaths = dest.readStringArray();
            this.baseRevisionCode = dest.readInt();
            this.splitRevisionCodes = dest.createIntArray();
            this.splitFlags = dest.createIntArray();
            this.splitPrivateFlags = dest.createIntArray();
            this.baseHardwareAccelerated = dest.readInt() == 1;
            this.applicationInfo = (ApplicationInfo) dest.readParcelable(boot, ApplicationInfo.class);
            if (this.applicationInfo.permission != null) {
                this.applicationInfo.permission = this.applicationInfo.permission.intern();
            }
            dest.readParcelableList(this.permissions, boot, Permission.class);
            fixupOwner(this.permissions);
            dest.readParcelableList(this.permissionGroups, boot, PermissionGroup.class);
            fixupOwner(this.permissionGroups);
            dest.readParcelableList(this.activities, boot, Activity.class);
            fixupOwner(this.activities);
            dest.readParcelableList(this.receivers, boot, Activity.class);
            fixupOwner(this.receivers);
            dest.readParcelableList(this.providers, boot, Provider.class);
            fixupOwner(this.providers);
            dest.readParcelableList(this.services, boot, Service.class);
            fixupOwner(this.services);
            dest.readParcelableList(this.instrumentation, boot, Instrumentation.class);
            fixupOwner(this.instrumentation);
            dest.readStringList(this.requestedPermissions);
            internStringArrayList(this.requestedPermissions);
            dest.readStringList(this.implicitPermissions);
            internStringArrayList(this.implicitPermissions);
            this.protectedBroadcasts = dest.createStringArrayList();
            internStringArrayList(this.protectedBroadcasts);
            this.parentPackage = (Package) dest.readParcelable(boot, Package.class);
            this.childPackages = new ArrayList<>();
            dest.readParcelableList(this.childPackages, boot, Package.class);
            if (this.childPackages.size() == 0) {
                this.childPackages = null;
            }
            this.staticSharedLibName = dest.readString();
            if (this.staticSharedLibName != null) {
                this.staticSharedLibName = this.staticSharedLibName.intern();
            }
            this.staticSharedLibVersion = dest.readLong();
            this.libraryNames = dest.createStringArrayList();
            internStringArrayList(this.libraryNames);
            this.usesLibraries = dest.createStringArrayList();
            internStringArrayList(this.usesLibraries);
            this.usesOptionalLibraries = dest.createStringArrayList();
            internStringArrayList(this.usesOptionalLibraries);
            this.usesLibraryFiles = dest.readStringArray();
            this.usesLibraryInfos = dest.createTypedArrayList(SharedLibraryInfo.CREATOR);
            int libCount = dest.readInt();
            if (libCount > 0) {
                this.usesStaticLibraries = new ArrayList<>(libCount);
                dest.readStringList(this.usesStaticLibraries);
                internStringArrayList(this.usesStaticLibraries);
                this.usesStaticLibrariesVersions = new long[libCount];
                dest.readLongArray(this.usesStaticLibrariesVersions);
                this.usesStaticLibrariesCertDigests = new String[libCount][];
                for (int i = 0; i < libCount; i++) {
                    this.usesStaticLibrariesCertDigests[i] = dest.createStringArray();
                }
            }
            this.preferredActivityFilters = new ArrayList<>();
            dest.readParcelableList(this.preferredActivityFilters, boot, ActivityIntentInfo.class);
            if (this.preferredActivityFilters.size() == 0) {
                this.preferredActivityFilters = null;
            }
            this.mOriginalPackages = dest.createStringArrayList();
            this.mRealPackage = dest.readString();
            this.mAdoptPermissions = dest.createStringArrayList();
            this.mAppMetaData = dest.readBundle();
            this.mVersionCode = dest.readInt();
            this.mVersionCodeMajor = dest.readInt();
            this.mVersionName = dest.readString();
            if (this.mVersionName != null) {
                this.mVersionName = this.mVersionName.intern();
            }
            this.mSharedUserId = dest.readString();
            if (this.mSharedUserId != null) {
                this.mSharedUserId = this.mSharedUserId.intern();
            }
            this.mSharedUserLabel = dest.readInt();
            this.mSigningDetails = (SigningDetails) dest.readParcelable(boot, SigningDetails.class);
            this.mPreferredOrder = dest.readInt();
            this.configPreferences = new ArrayList<>();
            dest.readParcelableList(this.configPreferences, boot, ConfigurationInfo.class);
            if (this.configPreferences.size() == 0) {
                this.configPreferences = null;
            }
            this.reqFeatures = new ArrayList<>();
            dest.readParcelableList(this.reqFeatures, boot, FeatureInfo.class);
            if (this.reqFeatures.size() == 0) {
                this.reqFeatures = null;
            }
            this.featureGroups = new ArrayList<>();
            dest.readParcelableList(this.featureGroups, boot, FeatureGroupInfo.class);
            if (this.featureGroups.size() == 0) {
                this.featureGroups = null;
            }
            this.installLocation = dest.readInt();
            this.coreApp = dest.readInt() == 1;
            this.mRequiredForAllUsers = dest.readInt() == 1;
            this.mRestrictedAccountType = dest.readString();
            this.mRequiredAccountType = dest.readString();
            this.mOverlayTarget = dest.readString();
            this.mOverlayTargetName = dest.readString();
            this.mOverlayCategory = dest.readString();
            this.mOverlayPriority = dest.readInt();
            this.mOverlayIsStatic = dest.readInt() == 1;
            this.mCompileSdkVersion = dest.readInt();
            this.mCompileSdkVersionCodename = dest.readString();
            this.mUpgradeKeySets = dest.readArraySet(boot);
            this.mKeySetMapping = PackageParser.readKeySetMapping(dest);
            this.cpuAbiOverride = dest.readString();
            this.use32bitAbi = dest.readInt() == 1;
            this.restrictUpdateHash = dest.createByteArray();
            this.visibleToInstantApps = dest.readInt() == 1;
        }

        private static void internStringArrayList(List<String> list) {
            if (list != null) {
                int N = list.size();
                for (int i = 0; i < N; i++) {
                    list.set(i, list.get(i).intern());
                }
            }
        }

        public void fixupOwner(List<? extends Component<?>> list) {
            if (list != null) {
                for (Component<?> c : list) {
                    c.owner = this;
                    if (c instanceof Activity) {
                        ((Activity) c).info.applicationInfo = this.applicationInfo;
                    } else if (c instanceof Service) {
                        ((Service) c).info.applicationInfo = this.applicationInfo;
                    } else if (c instanceof Provider) {
                        ((Provider) c).info.applicationInfo = this.applicationInfo;
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

        public Component(Package owner, ArrayList<II> intents, String className) {
            this.owner = owner;
            this.intents = intents;
            this.className = className;
        }

        public Component(Package owner) {
            this.owner = owner;
            this.intents = null;
            this.className = null;
        }

        public Component(ParsePackageItemArgs args, PackageItemInfo outInfo) {
            this.owner = args.owner;
            this.intents = new ArrayList<>(0);
            if (PackageParser.parsePackageItemInfo(args.owner, outInfo, args.outError, args.tag, args.sa, true, args.nameRes, args.labelRes, args.iconRes, args.roundIconRes, args.logoRes, args.bannerRes)) {
                this.className = outInfo.name;
            } else {
                this.className = null;
            }
        }

        public Component(ParseComponentArgs args, ComponentInfo outInfo) {
            this((ParsePackageItemArgs) args, (PackageItemInfo) outInfo);
            CharSequence pname;
            if (args.outError[0] != null) {
                return;
            }
            if (args.processRes != 0) {
                if (this.owner.applicationInfo.targetSdkVersion >= 8) {
                    pname = args.sa.getNonConfigurationString(args.processRes, 1024);
                } else {
                    pname = args.sa.getNonResourceString(args.processRes);
                }
                outInfo.processName = PackageParser.buildProcessName(this.owner.applicationInfo.packageName, this.owner.applicationInfo.processName, pname, args.flags, args.sepProcesses, args.outError);
            }
            if (args.descriptionRes != 0) {
                outInfo.descriptionRes = args.sa.getResourceId(args.descriptionRes, 0);
            }
            outInfo.enabled = args.sa.getBoolean(args.enabledRes, true);
        }

        public Component(Component<II> clone) {
            this.owner = clone.owner;
            this.intents = clone.intents;
            this.className = clone.className;
            this.componentName = clone.componentName;
            this.componentShortName = clone.componentShortName;
        }

        public ComponentName getComponentName() {
            if (this.componentName != null) {
                return this.componentName;
            }
            if (this.className != null) {
                this.componentName = new ComponentName(this.owner.applicationInfo.packageName, this.className);
            }
            return this.componentName;
        }

        protected Component(Parcel in) {
            this.className = in.readString();
            this.metaData = in.readBundle();
            this.intents = createIntentsList(in);
            this.owner = null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.className);
            dest.writeBundle(this.metaData);
            writeIntentsList(this.intents, dest, flags);
        }

        private static void writeIntentsList(ArrayList<? extends IntentInfo> list, Parcel out, int flags) {
            if (list == null) {
                out.writeInt(-1);
                return;
            }
            int N = list.size();
            out.writeInt(N);
            if (N > 0) {
                IntentInfo info = list.get(0);
                out.writeString(info.getClass().getName());
                for (int i = 0; i < N; i++) {
                    list.get(i).writeIntentInfoToParcel(out, flags);
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
                Constructor<?> constructor = Class.forName(readString).getConstructor(Parcel.class);
                EnterpriseProxyConstants.AnonymousClass1 anonymousClass1 = (ArrayList<T>) new ArrayList(readInt);
                for (int i = 0; i < readInt; i++) {
                    anonymousClass1.add((IntentInfo) constructor.newInstance(parcel));
                }
                return anonymousClass1;
            } catch (ReflectiveOperationException e) {
                throw new AssertionError("Unable to construct intent list for: " + readString);
            }
        }

        public void appendComponentShortName(StringBuilder sb) {
            ComponentName.appendShortString(sb, this.owner.applicationInfo.packageName, this.className);
        }

        public void printComponentShortName(PrintWriter pw) {
            ComponentName.printShortString(pw, this.owner.applicationInfo.packageName, this.className);
        }

        public void setPackageName(String packageName) {
            this.componentName = null;
            this.componentShortName = null;
        }
    }

    public static final class Permission extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Permission>() { // from class: android.content.pm.PackageParser.Permission.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Permission createFromParcel(Parcel in) {
                return new Permission(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Permission[] newArray(int size) {
                return new Permission[size];
            }
        };
        public PermissionGroup group;
        public final PermissionInfo info;
        public boolean tree;

        public Permission(Package owner, String backgroundPermission) {
            super(owner);
            this.info = new PermissionInfo(backgroundPermission);
        }

        public Permission(Package _owner, PermissionInfo _info) {
            super(_owner);
            this.info = _info;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            return "Permission{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.info.name + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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

        private Permission(Parcel in) {
            super(in);
            ClassLoader boot = Object.class.getClassLoader();
            this.info = (PermissionInfo) in.readParcelable(boot, PermissionInfo.class);
            if (this.info.group != null) {
                this.info.group = this.info.group.intern();
            }
            this.tree = in.readInt() == 1;
            this.group = (PermissionGroup) in.readParcelable(boot, PermissionGroup.class);
        }
    }

    public static final class PermissionGroup extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<PermissionGroup>() { // from class: android.content.pm.PackageParser.PermissionGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionGroup createFromParcel(Parcel in) {
                return new PermissionGroup(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PermissionGroup[] newArray(int size) {
                return new PermissionGroup[size];
            }
        };
        public final PermissionGroupInfo info;

        public PermissionGroup(Package owner, int requestDetailResourceId, int backgroundRequestResourceId, int backgroundRequestDetailResourceId) {
            super(owner);
            this.info = new PermissionGroupInfo(requestDetailResourceId, backgroundRequestResourceId, backgroundRequestDetailResourceId);
        }

        public PermissionGroup(Package _owner, PermissionGroupInfo _info) {
            super(_owner);
            this.info = _info;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        public String toString() {
            return "PermissionGroup{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.info.name + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags);
        }

        private PermissionGroup(Parcel in) {
            super(in);
            this.info = (PermissionGroupInfo) in.readParcelable(Object.class.getClassLoader(), PermissionGroupInfo.class);
        }
    }

    private static boolean copyNeeded(int flags, Package p, FrameworkPackageUserState state, Bundle metaData, int userId) {
        if (userId != 0) {
            return true;
        }
        if (state.getEnabledState() != 0) {
            boolean enabled = state.getEnabledState() == 1;
            if (p.applicationInfo.enabled != enabled) {
                return true;
            }
        }
        boolean suspended = (p.applicationInfo.flags & 1073741824) != 0;
        if (state.isSuspended() != suspended || !state.isInstalled() || state.isHidden() || state.isStopped() || state.isInstantApp() != p.applicationInfo.isInstantApp()) {
            return true;
        }
        if ((flags & 128) != 0 && (metaData != null || p.mAppMetaData != null)) {
            return true;
        }
        if ((flags & 1024) == 0 || p.usesLibraryFiles == null) {
            return (((flags & 1024) == 0 || p.usesLibraryInfos == null) && p.staticSharedLibName == null) ? false : true;
        }
        return true;
    }

    public static ApplicationInfo generateApplicationInfo(Package p, int flags, FrameworkPackageUserState state) {
        return generateApplicationInfo(p, flags, state, UserHandle.getCallingUserId());
    }

    private static void updateApplicationInfo(ApplicationInfo ai, int flags, FrameworkPackageUserState state) {
        if (!sCompatibilityModeEnabled) {
            ai.disableCompatibilityMode();
        }
        if (state.isInstalled()) {
            ai.flags |= 8388608;
        } else {
            ai.flags &= -8388609;
        }
        if (state.isSuspended()) {
            ai.flags |= 1073741824;
        } else {
            ai.flags &= -1073741825;
        }
        if (state.isInstantApp()) {
            ai.privateFlags |= 128;
        } else {
            ai.privateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
        if (state.isVirtualPreload()) {
            ai.privateFlags |= 65536;
        } else {
            ai.privateFlags &= -65537;
        }
        boolean z = true;
        if (state.isHidden()) {
            ai.privateFlags |= 1;
        } else {
            ai.privateFlags &= -2;
        }
        if (state.getEnabledState() == 1) {
            ai.enabled = true;
        } else if (state.getEnabledState() == 4) {
            if ((32768 & flags) == 0 && (536870912 & flags) == 0) {
                z = false;
            }
            ai.enabled = z;
        } else if (state.getEnabledState() == 2 || state.getEnabledState() == 3) {
            ai.enabled = false;
        }
        ai.enabledSetting = state.getEnabledState();
        if (ai.category == -1) {
            ai.category = FallbackCategoryProvider.getFallbackCategory(ai.packageName);
        }
        ai.seInfoUser = getSeinfoUser(state);
        OverlayPaths overlayPaths = state.getAllOverlayPaths();
        if (overlayPaths != null) {
            ai.resourceDirs = (String[]) overlayPaths.getResourceDirs().toArray(new String[0]);
            ai.overlayPaths = (String[]) overlayPaths.getOverlayPaths().toArray(new String[0]);
        }
        ai.icon = (!sUseRoundIcon || ai.roundIconRes == 0) ? ai.iconRes : ai.roundIconRes;
    }

    public static ApplicationInfo generateApplicationInfo(Package p, int flags, FrameworkPackageUserState state, int userId) {
        if (p == null || !checkUseInstalledOrHidden(flags, state, p.applicationInfo) || !p.isMatch(flags)) {
            return null;
        }
        if (!copyNeeded(flags, p, state, null, userId) && ((32768 & flags) == 0 || state.getEnabledState() != 4)) {
            updateApplicationInfo(p.applicationInfo, flags, state);
            return p.applicationInfo;
        }
        ApplicationInfo ai = new ApplicationInfo(p.applicationInfo);
        ai.initForUser(userId);
        if ((flags & 128) != 0) {
            ai.metaData = p.mAppMetaData;
        }
        if ((flags & 1024) != 0) {
            ai.sharedLibraryFiles = p.usesLibraryFiles;
            ai.sharedLibraryInfos = p.usesLibraryInfos;
        }
        if (state.isStopped()) {
            ai.flags |= 2097152;
        } else {
            ai.flags &= -2097153;
        }
        updateApplicationInfo(ai, flags, state);
        return ai;
    }

    public static ApplicationInfo generateApplicationInfo(ApplicationInfo ai, int flags, FrameworkPackageUserState state, int userId) {
        if (ai == null || !checkUseInstalledOrHidden(flags, state, ai)) {
            return null;
        }
        ApplicationInfo ai2 = new ApplicationInfo(ai);
        ai2.initForUser(userId);
        if (state.isStopped()) {
            ai2.flags |= 2097152;
        } else {
            ai2.flags &= -2097153;
        }
        updateApplicationInfo(ai2, flags, state);
        return ai2;
    }

    public static final PermissionInfo generatePermissionInfo(Permission p, int flags) {
        if (p == null) {
            return null;
        }
        if ((flags & 128) == 0) {
            return p.info;
        }
        PermissionInfo pi = new PermissionInfo(p.info);
        pi.metaData = p.metaData;
        return pi;
    }

    public static final PermissionGroupInfo generatePermissionGroupInfo(PermissionGroup pg, int flags) {
        if (pg == null) {
            return null;
        }
        if ((flags & 128) == 0) {
            return pg.info;
        }
        PermissionGroupInfo pgi = new PermissionGroupInfo(pg.info);
        pgi.metaData = pg.metaData;
        return pgi;
    }

    public static final class Activity extends Component<ActivityIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Activity>() { // from class: android.content.pm.PackageParser.Activity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Activity createFromParcel(Parcel in) {
                return new Activity(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Activity[] newArray(int size) {
                return new Activity[size];
            }
        };
        public final ActivityInfo info;
        private boolean mHasMaxAspectRatio;
        private boolean mHasMinAspectRatio;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMaxAspectRatio() {
            return this.mHasMaxAspectRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMinAspectRatio() {
            return this.mHasMinAspectRatio;
        }

        Activity(Package owner, String className, ActivityInfo info) {
            super(owner, new ArrayList(0), className);
            this.info = info;
            this.info.applicationInfo = owner.applicationInfo;
        }

        public Activity(ParseComponentArgs args, ActivityInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMaxAspectRatio(float maxAspectRatio) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (maxAspectRatio < 1.0f && maxAspectRatio != 0.0f) {
                return;
            }
            this.info.setMaxAspectRatio(maxAspectRatio);
            this.mHasMaxAspectRatio = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMinAspectRatio(float minAspectRatio) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (minAspectRatio < 1.0f && minAspectRatio != 0.0f) {
                return;
            }
            this.info.setMinAspectRatio(minAspectRatio);
            this.mHasMinAspectRatio = true;
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags | 2);
            dest.writeBoolean(this.mHasMaxAspectRatio);
            dest.writeBoolean(this.mHasMinAspectRatio);
        }

        private Activity(Parcel in) {
            super(in);
            this.info = (ActivityInfo) in.readParcelable(Object.class.getClassLoader(), ActivityInfo.class);
            this.mHasMaxAspectRatio = in.readBoolean();
            this.mHasMinAspectRatio = in.readBoolean();
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ActivityIntentInfo aii = (ActivityIntentInfo) it.next();
                aii.activity = this;
                this.order = Math.max(aii.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                this.info.permission = this.info.permission.intern();
            }
        }
    }

    public static final ActivityInfo generateActivityInfo(Activity a, int flags, FrameworkPackageUserState state, int userId) {
        return generateActivityInfo(a, flags, state, userId, null);
    }

    private static ActivityInfo generateActivityInfo(Activity a, int flags, FrameworkPackageUserState state, int userId, ApplicationInfo applicationInfo) {
        if (a == null || !checkUseInstalledOrHidden(flags, state, a.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(flags, a.owner, state, a.metaData, userId)) {
            updateApplicationInfo(a.info.applicationInfo, flags, state);
            return a.info;
        }
        ActivityInfo ai = new ActivityInfo(a.info);
        ai.metaData = a.metaData;
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(a.owner, flags, state, userId);
        }
        ai.applicationInfo = applicationInfo;
        return ai;
    }

    public static final ActivityInfo generateActivityInfo(ActivityInfo ai, int flags, FrameworkPackageUserState state, int userId) {
        if (ai == null || !checkUseInstalledOrHidden(flags, state, ai.applicationInfo)) {
            return null;
        }
        ActivityInfo ai2 = new ActivityInfo(ai);
        ai2.applicationInfo = generateApplicationInfo(ai2.applicationInfo, flags, state, userId);
        return ai2;
    }

    public static final class Service extends Component<ServiceIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Service>() { // from class: android.content.pm.PackageParser.Service.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Service createFromParcel(Parcel in) {
                return new Service(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Service[] newArray(int size) {
                return new Service[size];
            }
        };
        public final ServiceInfo info;

        public Service(ParseComponentArgs args, ServiceInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags | 2);
        }

        private Service(Parcel in) {
            super(in);
            this.info = (ServiceInfo) in.readParcelable(Object.class.getClassLoader(), ServiceInfo.class);
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ServiceIntentInfo aii = (ServiceIntentInfo) it.next();
                aii.service = this;
                this.order = Math.max(aii.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                this.info.permission = this.info.permission.intern();
            }
        }
    }

    public static final ServiceInfo generateServiceInfo(Service s, int flags, FrameworkPackageUserState state, int userId) {
        return generateServiceInfo(s, flags, state, userId, null);
    }

    private static ServiceInfo generateServiceInfo(Service s, int flags, FrameworkPackageUserState state, int userId, ApplicationInfo applicationInfo) {
        if (s == null || !checkUseInstalledOrHidden(flags, state, s.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(flags, s.owner, state, s.metaData, userId)) {
            updateApplicationInfo(s.info.applicationInfo, flags, state);
            return s.info;
        }
        ServiceInfo si = new ServiceInfo(s.info);
        si.metaData = s.metaData;
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(s.owner, flags, state, userId);
        }
        si.applicationInfo = applicationInfo;
        return si;
    }

    public static final class Provider extends Component<ProviderIntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Provider>() { // from class: android.content.pm.PackageParser.Provider.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Provider createFromParcel(Parcel in) {
                return new Provider(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Provider[] newArray(int size) {
                return new Provider[size];
            }
        };
        public final ProviderInfo info;
        public boolean syncable;

        public Provider(ParseComponentArgs args, ProviderInfo _info) {
            super(args, (ComponentInfo) _info);
            this.info = _info;
            this.info.applicationInfo = args.owner.applicationInfo;
            this.syncable = false;
        }

        public Provider(Provider existingProvider) {
            super(existingProvider);
            this.info = existingProvider.info;
            this.syncable = existingProvider.syncable;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
            parcel.writeInt(this.syncable ? 1 : 0);
        }

        private Provider(Parcel in) {
            super(in);
            this.info = (ProviderInfo) in.readParcelable(Object.class.getClassLoader(), ProviderInfo.class);
            this.syncable = in.readInt() == 1;
            Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ProviderIntentInfo aii = (ProviderIntentInfo) it.next();
                aii.provider = this;
            }
            if (this.info.readPermission != null) {
                this.info.readPermission = this.info.readPermission.intern();
            }
            if (this.info.writePermission != null) {
                this.info.writePermission = this.info.writePermission.intern();
            }
            if (this.info.authority != null) {
                this.info.authority = this.info.authority.intern();
            }
        }
    }

    public static final ProviderInfo generateProviderInfo(Provider p, int flags, FrameworkPackageUserState state, int userId) {
        return generateProviderInfo(p, flags, state, userId, null);
    }

    private static ProviderInfo generateProviderInfo(Provider p, int flags, FrameworkPackageUserState state, int userId, ApplicationInfo applicationInfo) {
        if (p == null || !checkUseInstalledOrHidden(flags, state, p.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(flags, p.owner, state, p.metaData, userId) && ((flags & 2048) != 0 || p.info.uriPermissionPatterns == null)) {
            updateApplicationInfo(p.info.applicationInfo, flags, state);
            return p.info;
        }
        ProviderInfo pi = new ProviderInfo(p.info);
        pi.metaData = p.metaData;
        if ((flags & 2048) == 0) {
            pi.uriPermissionPatterns = null;
        }
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(p.owner, flags, state, userId);
        }
        pi.applicationInfo = applicationInfo;
        return pi;
    }

    public static final class Instrumentation extends Component<IntentInfo> implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Instrumentation>() { // from class: android.content.pm.PackageParser.Instrumentation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Instrumentation createFromParcel(Parcel in) {
                return new Instrumentation(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Instrumentation[] newArray(int size) {
                return new Instrumentation[size];
            }
        };
        public final InstrumentationInfo info;

        public Instrumentation(ParsePackageItemArgs args, InstrumentationInfo _info) {
            super(args, _info);
            this.info = _info;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(String packageName) {
            super.setPackageName(packageName);
            this.info.packageName = packageName;
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(this.info, flags);
        }

        private Instrumentation(Parcel in) {
            super(in);
            this.info = (InstrumentationInfo) in.readParcelable(Object.class.getClassLoader(), InstrumentationInfo.class);
            if (this.info.targetPackage != null) {
                this.info.targetPackage = this.info.targetPackage.intern();
            }
            if (this.info.targetProcesses != null) {
                this.info.targetProcesses = this.info.targetProcesses.intern();
            }
        }
    }

    public static final InstrumentationInfo generateInstrumentationInfo(Instrumentation i, int flags) {
        if (i == null) {
            return null;
        }
        if ((flags & 128) == 0) {
            return i.info;
        }
        InstrumentationInfo ii = new InstrumentationInfo(i.info);
        ii.metaData = i.metaData;
        return ii;
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

        protected IntentInfo(Parcel dest) {
            super(dest);
            this.hasDefault = dest.readInt() == 1;
            this.labelRes = dest.readInt();
            this.nonLocalizedLabel = dest.readCharSequence();
            this.icon = dest.readInt();
            this.logo = dest.readInt();
            this.banner = dest.readInt();
            this.preferred = dest.readInt();
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

        public ActivityIntentInfo(Activity _activity) {
            this.activity = _activity;
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

        public ActivityIntentInfo(Parcel in) {
            super(in);
        }
    }

    public static final class ServiceIntentInfo extends IntentInfo {
        public Service service;

        public ServiceIntentInfo(Service _service) {
            this.service = _service;
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

        public ServiceIntentInfo(Parcel in) {
            super(in);
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

        public ProviderIntentInfo(Parcel in) {
            super(in);
        }
    }

    public static void setCompatibilityModeEnabled(boolean compatibilityModeEnabled) {
        sCompatibilityModeEnabled = compatibilityModeEnabled;
    }

    public static void readConfigUseRoundIcon(Resources r) {
        if (r != null) {
            sUseRoundIcon = r.getBoolean(R.bool.config_useRoundIcon);
            return;
        }
        try {
            ApplicationInfo androidAppInfo = ActivityThread.getPackageManager().getApplicationInfo("android", 0L, UserHandle.myUserId());
            Resources systemResources = Resources.getSystem();
            Resources overlayableRes = ResourcesManager.getInstance().getResources(null, null, null, androidAppInfo.resourceDirs, androidAppInfo.overlayPaths, androidAppInfo.sharedLibraryFiles, null, null, systemResources.getCompatibilityInfo(), systemResources.getClassLoader(), null);
            sUseRoundIcon = overlayableRes.getBoolean(R.bool.config_useRoundIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class PackageParserException extends Exception {
        public final int error;

        public PackageParserException(int error, String detailMessage) {
            super(detailMessage);
            this.error = error;
        }

        public PackageParserException(int error, String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
            this.error = error;
        }
    }

    @Deprecated
    private static abstract class SplitDependencyLoader<E extends Exception> {
        private final SparseArray<int[]> mDependencies;

        protected abstract void constructSplit(int i, int[] iArr, int i2) throws Exception;

        protected abstract boolean isSplitCached(int i);

        protected SplitDependencyLoader(SparseArray<int[]> dependencies) {
            this.mDependencies = dependencies;
        }

        protected void loadDependenciesForSplit(int splitIdx) throws Exception {
            if (isSplitCached(splitIdx)) {
                return;
            }
            if (splitIdx == 0) {
                int[] configSplitIndices = collectConfigSplitIndices(0);
                constructSplit(0, configSplitIndices, -1);
                return;
            }
            IntArray linearDependencies = new IntArray();
            linearDependencies.add(splitIdx);
            while (true) {
                int[] deps = this.mDependencies.get(splitIdx);
                if (deps != null && deps.length > 0) {
                    splitIdx = deps[0];
                } else {
                    splitIdx = -1;
                }
                if (splitIdx < 0 || isSplitCached(splitIdx)) {
                    break;
                } else {
                    linearDependencies.add(splitIdx);
                }
            }
            int parentIdx = splitIdx;
            for (int i = linearDependencies.size() - 1; i >= 0; i--) {
                int idx = linearDependencies.get(i);
                int[] configSplitIndices2 = collectConfigSplitIndices(idx);
                constructSplit(idx, configSplitIndices2, parentIdx);
                parentIdx = idx;
            }
        }

        private int[] collectConfigSplitIndices(int splitIdx) {
            int[] deps = this.mDependencies.get(splitIdx);
            if (deps == null || deps.length <= 1) {
                return EmptyArray.INT;
            }
            return Arrays.copyOfRange(deps, 1, deps.length);
        }

        public static class IllegalDependencyException extends Exception {
            private IllegalDependencyException(String message) {
                super(message);
            }
        }

        private static int[] append(int[] src, int elem) {
            if (src == null) {
                return new int[]{elem};
            }
            int[] dst = Arrays.copyOf(src, src.length + 1);
            dst[src.length] = elem;
            return dst;
        }

        public static SparseArray<int[]> createDependenciesFromPackage(PackageLite pkg) throws IllegalDependencyException {
            int depIdx;
            int depIdx2;
            SparseArray<int[]> splitDependencies = new SparseArray<>();
            splitDependencies.put(0, new int[]{-1});
            int splitIdx = 0;
            while (true) {
                if (splitIdx < pkg.splitNames.length) {
                    if (pkg.isFeatureSplits[splitIdx]) {
                        String splitDependency = pkg.usesSplitNames[splitIdx];
                        if (splitDependency != null) {
                            int depIdx3 = Arrays.binarySearch(pkg.splitNames, splitDependency);
                            if (depIdx3 < 0) {
                                throw new IllegalDependencyException("Split '" + pkg.splitNames[splitIdx] + "' requires split '" + splitDependency + "', which is missing.");
                            }
                            depIdx2 = depIdx3 + 1;
                        } else {
                            depIdx2 = 0;
                        }
                        splitDependencies.put(splitIdx + 1, new int[]{depIdx2});
                    }
                    splitIdx++;
                } else {
                    int size = pkg.splitNames.length;
                    for (int splitIdx2 = 0; splitIdx2 < size; splitIdx2++) {
                        if (!pkg.isFeatureSplits[splitIdx2]) {
                            String configForSplit = pkg.configForSplit[splitIdx2];
                            if (configForSplit != null) {
                                int depIdx4 = Arrays.binarySearch(pkg.splitNames, configForSplit);
                                if (depIdx4 < 0) {
                                    throw new IllegalDependencyException("Split '" + pkg.splitNames[splitIdx2] + "' targets split '" + configForSplit + "', which is missing.");
                                }
                                if (!pkg.isFeatureSplits[depIdx4]) {
                                    throw new IllegalDependencyException("Split '" + pkg.splitNames[splitIdx2] + "' declares itself as configuration split for a non-feature split '" + pkg.splitNames[depIdx4] + "'");
                                }
                                depIdx = depIdx4 + 1;
                            } else {
                                depIdx = 0;
                            }
                            splitDependencies.put(depIdx, append(splitDependencies.get(depIdx), splitIdx2 + 1));
                        }
                    }
                    BitSet bitset = new BitSet();
                    int size2 = splitDependencies.size();
                    for (int i = 0; i < size2; i++) {
                        int splitIdx3 = splitDependencies.keyAt(i);
                        bitset.clear();
                        while (splitIdx3 != -1) {
                            if (bitset.get(splitIdx3)) {
                                throw new IllegalDependencyException("Cycle detected in split dependencies.");
                            }
                            bitset.set(splitIdx3);
                            int[] deps = splitDependencies.get(splitIdx3);
                            splitIdx3 = deps != null ? deps[0] : -1;
                        }
                    }
                    return splitDependencies;
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

        DefaultSplitAssetLoader(PackageLite pkg, int flags) {
            this.mBaseCodePath = pkg.baseCodePath;
            this.mSplitCodePaths = pkg.splitCodePaths;
            this.mFlags = flags;
        }

        private static ApkAssets loadApkAssets(String path, int flags) throws PackageParserException {
            if ((flags & 1) != 0 && !PackageParser.isApkPath(path)) {
                throw new PackageParserException(-100, "Invalid package file: " + path);
            }
            try {
                return ApkAssets.loadFromPath(path);
            } catch (IOException e) {
                throw new PackageParserException(-2, "Failed to load APK at path " + path, e);
            }
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getBaseAssetManager() throws PackageParserException {
            if (this.mCachedAssetManager == null) {
                ApkAssets[] apkAssets = new ApkAssets[(this.mSplitCodePaths != null ? this.mSplitCodePaths.length : 0) + 1];
                this.mBaseApkAssets = loadApkAssets(this.mBaseCodePath, this.mFlags);
                int splitIdx = 0 + 1;
                apkAssets[0] = this.mBaseApkAssets;
                if (!ArrayUtils.isEmpty(this.mSplitCodePaths)) {
                    String[] strArr = this.mSplitCodePaths;
                    int length = strArr.length;
                    int i = 0;
                    while (i < length) {
                        String apkPath = strArr[i];
                        apkAssets[splitIdx] = loadApkAssets(apkPath, this.mFlags);
                        i++;
                        splitIdx++;
                    }
                }
                AssetManager assets = new AssetManager();
                assets.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
                assets.setApkAssets(apkAssets, false);
                this.mCachedAssetManager = assets;
                return this.mCachedAssetManager;
            }
            return this.mCachedAssetManager;
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getSplitAssetManager(int splitIdx) throws PackageParserException {
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

        SplitAssetDependencyLoader(PackageLite pkg, SparseArray<int[]> dependencies, int flags) {
            super(dependencies);
            this.mSplitPaths = new String[pkg.splitCodePaths.length + 1];
            this.mSplitPaths[0] = pkg.baseCodePath;
            System.arraycopy(pkg.splitCodePaths, 0, this.mSplitPaths, 1, pkg.splitCodePaths.length);
            this.mFlags = flags;
            this.mCachedSplitApks = new ApkAssets[this.mSplitPaths.length][];
            this.mCachedAssetManagers = new AssetManager[this.mSplitPaths.length];
        }

        @Override // android.content.pm.PackageParser.SplitDependencyLoader
        protected boolean isSplitCached(int splitIdx) {
            return this.mCachedAssetManagers[splitIdx] != null;
        }

        private static ApkAssets loadApkAssets(String path, int flags) throws PackageParserException {
            if ((flags & 1) != 0 && !PackageParser.isApkPath(path)) {
                throw new PackageParserException(-100, "Invalid package file: " + path);
            }
            try {
                return ApkAssets.loadFromPath(path);
            } catch (IOException e) {
                throw new PackageParserException(-2, "Failed to load APK at path " + path, e);
            }
        }

        private static AssetManager createAssetManagerWithAssets(ApkAssets[] apkAssets) {
            AssetManager assets = new AssetManager();
            assets.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
            assets.setApkAssets(apkAssets, false);
            return assets;
        }

        @Override // android.content.pm.PackageParser.SplitDependencyLoader
        protected void constructSplit(int splitIdx, int[] configSplitIndices, int parentSplitIdx) throws PackageParserException {
            ArrayList<ApkAssets> assets = new ArrayList<>();
            if (parentSplitIdx >= 0) {
                Collections.addAll(assets, this.mCachedSplitApks[parentSplitIdx]);
            }
            assets.add(loadApkAssets(this.mSplitPaths[splitIdx], this.mFlags));
            for (int configSplitIdx : configSplitIndices) {
                assets.add(loadApkAssets(this.mSplitPaths[configSplitIdx], this.mFlags));
            }
            this.mCachedSplitApks[splitIdx] = (ApkAssets[]) assets.toArray(new ApkAssets[assets.size()]);
            this.mCachedAssetManagers[splitIdx] = createAssetManagerWithAssets(this.mCachedSplitApks[splitIdx]);
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getBaseAssetManager() throws PackageParserException {
            loadDependenciesForSplit(0);
            return this.mCachedAssetManagers[0];
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public AssetManager getSplitAssetManager(int idx) throws PackageParserException {
            loadDependenciesForSplit(idx + 1);
            return this.mCachedAssetManagers[idx + 1];
        }

        @Override // java.lang.AutoCloseable
        public void close() throws Exception {
            for (AssetManager assets : this.mCachedAssetManagers) {
                IoUtils.closeQuietly(assets);
            }
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public ApkAssets getBaseApkAssets() {
            return this.mCachedSplitApks[0][0];
        }
    }

    public static boolean isMatch(FrameworkPackageUserState state, ComponentInfo componentInfo, long flags) {
        return isMatch(state, componentInfo.applicationInfo.isSystemApp(), componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.directBootAware, componentInfo.name, flags);
    }

    public static boolean isMatch(FrameworkPackageUserState state, boolean isSystem, boolean isPackageEnabled, ComponentInfo component, long flags) {
        return isMatch(state, isSystem, isPackageEnabled, component.isEnabled(), component.directBootAware, component.name, flags);
    }

    public static boolean isMatch(FrameworkPackageUserState state, boolean isSystem, boolean isPackageEnabled, boolean isComponentEnabled, boolean isComponentDirectBootAware, String componentName, long flags) {
        boolean z = true;
        boolean matchUninstalled = (4202496 & flags) != 0;
        if (!isAvailable(state, flags) && (!isSystem || !matchUninstalled)) {
            return reportIfDebug(false, flags);
        }
        if (!isEnabled(state, isPackageEnabled, isComponentEnabled, componentName, flags)) {
            return reportIfDebug(false, flags);
        }
        if ((1048576 & flags) != 0 && !isSystem) {
            return reportIfDebug(false, flags);
        }
        boolean matchesUnaware = ((262144 & flags) == 0 || isComponentDirectBootAware) ? false : true;
        boolean matchesAware = (524288 & flags) != 0 && isComponentDirectBootAware;
        if (!matchesUnaware && !matchesAware) {
            z = false;
        }
        return reportIfDebug(z, flags);
    }

    public static boolean isAvailable(FrameworkPackageUserState state, long flags) {
        boolean matchAnyUser = (4194304 & flags) != 0;
        boolean matchUninstalled = (8192 & flags) != 0;
        if (matchAnyUser) {
            return true;
        }
        return state.isInstalled() && (!state.isHidden() || matchUninstalled);
    }

    public static boolean reportIfDebug(boolean result, long flags) {
        return result;
    }

    public static boolean isEnabled(FrameworkPackageUserState state, ComponentInfo componentInfo, long flags) {
        return isEnabled(state, componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.name, flags);
    }

    public static boolean isEnabled(FrameworkPackageUserState state, boolean isPackageEnabled, ComponentInfo parsedComponent, long flags) {
        return isEnabled(state, isPackageEnabled, parsedComponent.isEnabled(), parsedComponent.name, flags);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0020 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0027 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0028  */
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
            switch(r0) {
                case 0: goto L1e;
                case 1: goto L13;
                case 2: goto L1d;
                case 3: goto L1d;
                case 4: goto L14;
                default: goto L13;
            }
        L13:
            goto L21
        L14:
            r5 = 32768(0x8000, double:1.61895E-319)
            long r5 = r5 & r11
            int r0 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r0 != 0) goto L1e
            return r4
        L1d:
            return r4
        L1e:
            if (r8 != 0) goto L21
            return r4
        L21:
            boolean r0 = r7.isComponentEnabled(r10)
            if (r0 == 0) goto L28
            return r1
        L28:
            boolean r0 = r7.isComponentDisabled(r10)
            if (r0 == 0) goto L2f
            return r4
        L2f:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackageParser.isEnabled(android.content.pm.pkg.FrameworkPackageUserState, boolean, boolean, java.lang.String, long):boolean");
    }

    public static void writeKeySetMapping(Parcel dest, Map<String, ArraySet<PublicKey>> keySetMapping) {
        if (keySetMapping == null) {
            dest.writeInt(-1);
            return;
        }
        int N = keySetMapping.size();
        dest.writeInt(N);
        for (String key : keySetMapping.keySet()) {
            dest.writeString(key);
            ArraySet<PublicKey> keys = keySetMapping.get(key);
            if (keys == null) {
                dest.writeInt(-1);
            } else {
                int M = keys.size();
                dest.writeInt(M);
                for (int j = 0; j < M; j++) {
                    dest.writeSerializable(keys.valueAt(j));
                }
            }
        }
    }

    public static ArrayMap<String, ArraySet<PublicKey>> readKeySetMapping(Parcel in) {
        int N = in.readInt();
        if (N == -1) {
            return null;
        }
        ArrayMap<String, ArraySet<PublicKey>> keySetMapping = new ArrayMap<>();
        for (int i = 0; i < N; i++) {
            String key = in.readString();
            int M = in.readInt();
            if (M == -1) {
                keySetMapping.put(key, null);
            } else {
                ArraySet<PublicKey> keys = new ArraySet<>(M);
                for (int j = 0; j < M; j++) {
                    PublicKey pk = (PublicKey) in.readSerializable(PublicKey.class.getClassLoader(), PublicKey.class);
                    keys.add(pk);
                }
                keySetMapping.put(key, keys);
            }
        }
        return keySetMapping;
    }

    public static String getSeinfoUser(FrameworkPackageUserState userState) {
        if (userState.isInstantApp()) {
            return ":ephemeralapp:complete";
        }
        return SEInfoUtil.COMPLETE_STR;
    }
}
