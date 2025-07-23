package com.android.internal.pm.pkg.parsing;

import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.ext.SdkExtensions;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.permission.CompatibilityPermissionInfo;
import com.android.internal.pm.pkg.component.AconfigFlags;
import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ComponentParseUtils;
import com.android.internal.pm.pkg.component.InstallConstraintsTagParser;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedActivityImpl;
import com.android.internal.pm.pkg.component.ParsedAttribution;
import com.android.internal.pm.pkg.component.ParsedAttributionUtils;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedInstrumentationUtils;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.pm.pkg.component.ParsedPermissionUtils;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.pm.pkg.component.ParsedProcessUtils;
import com.android.internal.pm.split.DefaultSplitAssetLoader;
import com.android.internal.pm.split.SplitAssetDependencyLoader;
import com.android.internal.pm.split.SplitAssetLoader;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.samsung.android.rune.PMRune;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import libcore.util.HexEncoding;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsingPackageUtils {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    public static final float ASPECT_RATIO_NOT_SET = -1.0f;
    public static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_JAR = false;
    public static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f;
    private static final int MAXIMUM_LAUNCHER_ALTERNATE_IDS_LENGTH = 500;
    public static final String METADATA_ACTIVITY_LAUNCH_MODE = "android.activity.launch_mode";
    public static final String METADATA_ACTIVITY_WINDOW_LAYOUT_AFFINITY = "android.activity_window_layout_affinity";
    public static final String METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES = "android.can_display_on_remote_devices";
    public static final String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    public static final String METADATA_SUPPORTS_SIZE_CHANGES = "android.supports_size_changes";
    public static final String MNT_EXPAND = "/mnt/expand/";
    public static final int PARSE_APEX = 1024;
    public static final int PARSE_APK_IN_APEX = 512;
    public static final int PARSE_CHATTY = Integer.MIN_VALUE;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    public static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    public static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    public static final int PARSE_ENFORCE_CODE = 64;
    public static final int PARSE_EXTERNAL_STORAGE = 8;
    public static final int PARSE_IGNORE_OVERLAY_REQUIRED_SYSTEM_PROPERTY = 128;
    public static final int PARSE_IGNORE_PROCESSES = 2;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_MUST_BE_APK = 1;
    public static final boolean RIGID_PARSER = false;
    private static final String TAG = "PackageParsing";
    public static final String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    public static final String TAG_APPLICATION = "application";
    public static final String TAG_ATTRIBUTION = "attribution";
    public static final String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    public static final String TAG_EAT_COMMENT = "eat-comment";
    public static final String TAG_FEATURE_GROUP = "feature-group";
    public static final String TAG_INSTALL_CONSTRAINTS = "install-constraints";
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
    public static final String TAG_RECEIVER = "receiver";
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
    private static final String TYPE_DRAWABLE = "drawable";
    private static final String TYPE_STRING = "string";
    private final Callback mCallback;
    private final DisplayMetrics mDisplayMetrics;
    private final String[] mSeparateProcesses;
    private final List<PermissionManager.SplitPermissionInfo> mSplitPermissionInfos;
    public static final int SDK_VERSION = Build.VERSION.SDK_INT;
    public static final String[] SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;
    public static boolean sCompatibilityModeEnabled = true;
    public static boolean sUseRoundIcon = false;
    private static final AconfigFlags sAconfigFlags = new AconfigFlags();

    public interface Callback {
        Set<String> getHiddenApiWhitelistedApps();

        Set<String> getInstallConstraintsAllowlist();

        boolean hasFeature(String str);

        ParsingPackage startParsingPackage(String str, String str2, String str3, TypedArray typedArray, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParseFlags {
    }

    public static ParseResult<ParsedPackage> parseDefault(ParseInput parseInput, File file, int i, List<PermissionManager.SplitPermissionInfo> list, boolean z, Callback callback) {
        ParseResult<ParsingPackage> parsePackage = new ParsingPackageUtils(null, null, list, callback).parsePackage(parseInput, file, i);
        if (parsePackage.isError()) {
            return parseInput.error(parsePackage);
        }
        ParsedPackage hideAsParsed = parsePackage.getResult().hideAsParsed();
        if (z) {
            ParseResult<SigningDetails> signingDetails = getSigningDetails(parseInput, hideAsParsed, false);
            if (signingDetails.isError()) {
                return parseInput.error(signingDetails);
            }
            hideAsParsed.setSigningDetails(signingDetails.getResult());
        }
        return parseInput.success(hideAsParsed);
    }

    public ParsingPackageUtils(String[] strArr, DisplayMetrics displayMetrics, List<PermissionManager.SplitPermissionInfo> list, Callback callback) {
        this.mSeparateProcesses = strArr;
        this.mDisplayMetrics = displayMetrics;
        this.mSplitPermissionInfos = list;
        this.mCallback = callback;
    }

    public ParseResult<ParsingPackage> parsePackage(ParseInput parseInput, File file, int i) {
        if (file.isDirectory()) {
            return parseClusterPackage(parseInput, file, i);
        }
        return parseMonolithicPackage(parseInput, file, i);
    }

    private ParseResult<ParsingPackage> parseClusterPackage(ParseInput parseInput, File file, int i) {
        SplitAssetLoader defaultSplitAssetLoader;
        SparseArray<int[]> sparseArray;
        SplitAssetLoader splitAssetLoader;
        ParseResult<?> parseBaseApk;
        int i2 = (i & 512) != 0 ? 512 : 0;
        if ((i & 1024) != 0) {
            i2 |= 1024;
        }
        ParseResult<?> parseClusterPackageLite = ApkLiteParseUtils.parseClusterPackageLite(parseInput, file, i2);
        if (parseClusterPackageLite.isError()) {
            return parseInput.error(parseClusterPackageLite);
        }
        PackageLite result = parseClusterPackageLite.getResult();
        if (!result.isIsolatedSplits() || ArrayUtils.isEmpty(result.getSplitNames())) {
            defaultSplitAssetLoader = new DefaultSplitAssetLoader(result, i);
            sparseArray = null;
        } else {
            try {
                sparseArray = SplitAssetDependencyLoader.createDependenciesFromPackage(result);
                defaultSplitAssetLoader = new SplitAssetDependencyLoader(result, sparseArray, i);
            } catch (SplitDependencyLoader.IllegalDependencyException e) {
                return parseInput.error(-101, e.getMessage());
            }
        }
        SparseArray<int[]> sparseArray2 = sparseArray;
        SplitAssetLoader splitAssetLoader2 = defaultSplitAssetLoader;
        try {
            try {
                parseBaseApk = parseBaseApk(parseInput, new File(result.getBaseApkPath()), result.getPath(), splitAssetLoader2, i, result.isIsSdkLibrary() && Flags.disallowSdkLibsToBeApps());
                splitAssetLoader = splitAssetLoader2;
            } catch (IllegalArgumentException e2) {
                e = e2;
                splitAssetLoader = splitAssetLoader2;
            } catch (Throwable th) {
                th = th;
                IoUtils.closeQuietly(splitAssetLoader2);
                throw th;
            }
            try {
                if (parseBaseApk.isError()) {
                    ParseResult<ParsingPackage> error = parseInput.error(parseBaseApk);
                    IoUtils.closeQuietly(splitAssetLoader);
                    return error;
                }
                ParsingPackage result2 = parseBaseApk.getResult();
                if (!ArrayUtils.isEmpty(result.getSplitNames())) {
                    result2.asSplit(result.getSplitNames(), result.getSplitApkPaths(), result.getSplitRevisionCodes(), sparseArray2);
                    int length = result.getSplitNames().length;
                    for (int i3 = 0; i3 < length; i3++) {
                        ParseResult<?> parseSplitApk = parseSplitApk(parseInput, result2, i3, splitAssetLoader.getSplitAssetManager(i3), i);
                        if (parseSplitApk.isError()) {
                            ParseResult<ParsingPackage> error2 = parseInput.error(parseSplitApk);
                            IoUtils.closeQuietly(splitAssetLoader);
                            return error2;
                        }
                    }
                }
                result2.set32BitAbiPreferred(result.isUse32bitAbi());
                ParseResult<ParsingPackage> success = parseInput.success(result2);
                IoUtils.closeQuietly(splitAssetLoader);
                return success;
            } catch (IllegalArgumentException e3) {
                e = e3;
                ParseResult<ParsingPackage> error3 = parseInput.error(e.getCause() instanceof IOException ? -2 : -100, e.getMessage(), e);
                IoUtils.closeQuietly(splitAssetLoader);
                return error3;
            }
        } catch (Throwable th2) {
            th = th2;
            IoUtils.closeQuietly(splitAssetLoader2);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0045 A[Catch: IOException -> 0x0063, all -> 0x0065, TRY_LEAVE, TryCatch #1 {all -> 0x0065, blocks: (B:8:0x001e, B:30:0x0024, B:12:0x0033, B:15:0x003b, B:17:0x0045, B:20:0x004d, B:25:0x006c), top: B:7:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d A[Catch: IOException -> 0x0063, all -> 0x0065, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0065, blocks: (B:8:0x001e, B:30:0x0024, B:12:0x0033, B:15:0x003b, B:17:0x0045, B:20:0x004d, B:25:0x006c), top: B:7:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseMonolithicPackage(android.content.pm.parsing.result.ParseInput r10, java.io.File r11, int r12) {
        /*
            r9 = this;
            java.lang.String r1 = "Failed to get path: "
            r0 = r12 & (-33)
            android.content.pm.parsing.result.ParseResult r0 = android.content.pm.parsing.ApkLiteParseUtils.parseMonolithicPackageLite(r10, r11, r0)
            boolean r2 = r0.isError()
            if (r2 == 0) goto L13
            android.content.pm.parsing.result.ParseResult r9 = r10.error(r0)
            return r9
        L13:
            java.lang.Object r0 = r0.getResult()
            android.content.pm.parsing.PackageLite r0 = (android.content.pm.parsing.PackageLite) r0
            com.android.internal.pm.split.DefaultSplitAssetLoader r6 = new com.android.internal.pm.split.DefaultSplitAssetLoader
            r6.<init>(r0, r12)
            boolean r2 = r0.isIsSdkLibrary()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            if (r2 == 0) goto L31
            boolean r2 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.disallowSdkLibsToBeApps()     // Catch: java.io.IOException -> L2c java.lang.Throwable -> L65
            if (r2 == 0) goto L31
            r2 = 1
            goto L32
        L2c:
            r0 = move-exception
            r9 = r0
            r3 = r10
            r4 = r11
            goto L6c
        L31:
            r2 = 0
        L32:
            r8 = r2
            java.lang.String r5 = r11.getCanonicalPath()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            r2 = r9
            r3 = r10
            r4 = r11
            r7 = r12
            android.content.pm.parsing.result.ParseResult r9 = r2.parseBaseApk(r3, r4, r5, r6, r7, r8)     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            boolean r10 = r9.isError()     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            if (r10 == 0) goto L4d
            android.content.pm.parsing.result.ParseResult r9 = r3.error(r9)     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            libcore.io.IoUtils.closeQuietly(r6)
            return r9
        L4d:
            java.lang.Object r9 = r9.getResult()     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            com.android.internal.pm.pkg.parsing.ParsingPackage r9 = (com.android.internal.pm.pkg.parsing.ParsingPackage) r9     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            boolean r10 = r0.isUse32bitAbi()     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            com.android.internal.pm.pkg.parsing.ParsingPackage r9 = r9.set32BitAbiPreferred(r10)     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            android.content.pm.parsing.result.ParseResult r9 = r3.success(r9)     // Catch: java.io.IOException -> L63 java.lang.Throwable -> L65
            libcore.io.IoUtils.closeQuietly(r6)
            return r9
        L63:
            r0 = move-exception
            goto L6b
        L65:
            r0 = move-exception
            r9 = r0
            goto L82
        L68:
            r0 = move-exception
            r3 = r10
            r4 = r11
        L6b:
            r9 = r0
        L6c:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65
            r10.<init>(r1)     // Catch: java.lang.Throwable -> L65
            r10.append(r4)     // Catch: java.lang.Throwable -> L65
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L65
            r11 = -102(0xffffffffffffff9a, float:NaN)
            android.content.pm.parsing.result.ParseResult r9 = r3.error(r11, r10, r9)     // Catch: java.lang.Throwable -> L65
            libcore.io.IoUtils.closeQuietly(r6)
            return r9
        L82:
            libcore.io.IoUtils.closeQuietly(r6)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseMonolithicPackage(android.content.pm.parsing.result.ParseInput, java.io.File, int):android.content.pm.parsing.result.ParseResult");
    }

    public ParseResult<ParsingPackage> parsePackageFromPackageLite(ParseInput parseInput, PackageLite packageLite, int i) {
        String volumeUuid = getVolumeUuid(packageLite.getPath());
        String packageName = packageLite.getPackageName();
        ParsingPackage startParsingPackage = this.mCallback.startParsingPackage(packageName, packageLite.getBaseApkPath(), packageLite.getPath(), null, packageLite.isCoreApp());
        int targetSdk = packageLite.getTargetSdk();
        startParsingPackage.setVersionCode(packageLite.getVersionCode());
        startParsingPackage.setVersionCodeMajor(packageLite.getVersionCodeMajor());
        startParsingPackage.setBaseRevisionCode(packageLite.getBaseRevisionCode());
        startParsingPackage.setVersionName(null);
        startParsingPackage.setCompileSdkVersion(0);
        startParsingPackage.setCompileSdkVersionCodeName(null);
        startParsingPackage.setIsolatedSplitLoading(false);
        startParsingPackage.setTargetSdkVersion(targetSdk);
        startParsingPackage.setInstallLocation(packageLite.getInstallLocation()).setTargetSandboxVersion(1).setExternalStorage((i & 8) != 0);
        ArchivedPackageParcel archivedPackage = packageLite.getArchivedPackage();
        if (archivedPackage == null) {
            return parseInput.error(-102, "archivePackage is missing");
        }
        startParsingPackage.setBackupAllowed(true).setClearUserDataAllowed(true).setClearUserDataOnFailedRestoreAllowed(true).setAllowNativeHeapPointerTagging(true).setEnabled(true).setExtractNativeLibrariesRequested(true).setAllowAudioPlaybackCapture(targetSdk >= 29).setHardwareAccelerated(targetSdk >= 14).setRequestLegacyExternalStorage(XmlUtils.convertValueToBoolean(archivedPackage.requestLegacyExternalStorage, targetSdk < 29)).setCleartextTrafficAllowed(targetSdk < 28).setDefaultToDeviceProtectedStorage(XmlUtils.convertValueToBoolean(archivedPackage.defaultToDeviceProtectedStorage, false)).setUserDataFragile(XmlUtils.convertValueToBoolean(archivedPackage.userDataFragile, false)).setCategory(-1).setMaxAspectRatio(0.0f).setMinAspectRatio(0.0f);
        startParsingPackage.setDeclaredHavingCode(false);
        ParseResult<String> buildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, packageName, null, parseInput);
        if (buildTaskAffinityName.isError()) {
            return parseInput.error(buildTaskAffinityName);
        }
        startParsingPackage.setTaskAffinity(buildTaskAffinityName.getResult());
        ParseResult<String> buildProcessName = ComponentParseUtils.buildProcessName(packageName, null, null, i, this.mSeparateProcesses, parseInput);
        if (buildProcessName.isError()) {
            return parseInput.error(buildProcessName);
        }
        startParsingPackage.setProcessName(buildProcessName.getResult());
        startParsingPackage.setGwpAsanMode(-1);
        startParsingPackage.setMemtagMode(-1);
        startParsingPackage.setPageSizeAppCompatFlags(0);
        afterParseBaseApplication(startParsingPackage);
        ParseResult<ParsingPackage> validateBaseApkTags = validateBaseApkTags(parseInput, startParsingPackage, i);
        if (validateBaseApkTags.isError()) {
            return validateBaseApkTags;
        }
        startParsingPackage.setVolumeUuid(volumeUuid);
        if ((i & 32) != 0) {
            startParsingPackage.setSigningDetails(packageLite.getSigningDetails());
        } else {
            startParsingPackage.setSigningDetails(SigningDetails.UNKNOWN);
        }
        return parseInput.success(startParsingPackage.set32BitAbiPreferred(packageLite.isUse32bitAbi()));
    }

    private static String getVolumeUuid(String str) {
        if (str.startsWith("/mnt/expand/")) {
            return str.substring(12, str.indexOf(47, 12));
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0070, code lost:
    
        if (r5 != null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApk(android.content.pm.parsing.result.ParseInput r10, java.io.File r11, java.lang.String r12, com.android.internal.pm.split.SplitAssetLoader r13, int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseBaseApk(android.content.pm.parsing.result.ParseInput, java.io.File, java.lang.String, com.android.internal.pm.split.SplitAssetLoader, int, boolean):android.content.pm.parsing.result.ParseResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006a, code lost:
    
        if (r7 != null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSplitApk(android.content.pm.parsing.result.ParseInput r11, com.android.internal.pm.pkg.parsing.ParsingPackage r12, int r13, android.content.res.AssetManager r14, int r15) {
        /*
            r10 = this;
            java.lang.String[] r0 = r12.getSplitCodePaths()
            r1 = r0[r13]
            int r0 = r14.findCookieForPath(r1)
            if (r0 != 0) goto L21
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r12 = "Failed adding asset path: "
            r10.<init>(r12)
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            r12 = -101(0xffffffffffffff9b, float:NaN)
            android.content.pm.parsing.result.ParseResult r10 = r11.error(r12, r10)
            return r10
        L21:
            java.lang.String r2 = "AndroidManifest.xml"
            android.content.res.XmlResourceParser r7 = r14.openXmlResourceParser(r0, r2)     // Catch: java.lang.Exception -> L87
            android.content.res.Resources r6 = new android.content.res.Resources     // Catch: java.lang.Throwable -> L76
            android.util.DisplayMetrics r0 = r10.mDisplayMetrics     // Catch: java.lang.Throwable -> L76
            r2 = 0
            r6.<init>(r14, r0, r2)     // Catch: java.lang.Throwable -> L76
            r3 = r10
            r4 = r11
            r5 = r12
            r9 = r13
            r8 = r15
            android.content.pm.parsing.result.ParseResult r10 = r3.parseSplitApk(r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L74
            boolean r11 = r10.isError()     // Catch: java.lang.Throwable -> L74
            if (r11 == 0) goto L70
            int r11 = r10.getErrorCode()     // Catch: java.lang.Throwable -> L74
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74
            r12.<init>()     // Catch: java.lang.Throwable -> L74
            r12.append(r1)     // Catch: java.lang.Throwable -> L74
            java.lang.String r13 = " (at "
            r12.append(r13)     // Catch: java.lang.Throwable -> L74
            java.lang.String r13 = r7.getPositionDescription()     // Catch: java.lang.Throwable -> L74
            r12.append(r13)     // Catch: java.lang.Throwable -> L74
            java.lang.String r13 = "): "
            r12.append(r13)     // Catch: java.lang.Throwable -> L74
            java.lang.String r10 = r10.getErrorMessage()     // Catch: java.lang.Throwable -> L74
            r12.append(r10)     // Catch: java.lang.Throwable -> L74
            java.lang.String r10 = r12.toString()     // Catch: java.lang.Throwable -> L74
            android.content.pm.parsing.result.ParseResult r10 = r4.error(r11, r10)     // Catch: java.lang.Throwable -> L74
            if (r7 == 0) goto L6f
        L6c:
            r7.close()     // Catch: java.lang.Exception -> L85
        L6f:
            return r10
        L70:
            if (r7 == 0) goto L73
            goto L6c
        L73:
            return r10
        L74:
            r0 = move-exception
            goto L78
        L76:
            r0 = move-exception
            r4 = r11
        L78:
            r10 = r0
            if (r7 == 0) goto L84
            r7.close()     // Catch: java.lang.Throwable -> L7f
            goto L84
        L7f:
            r0 = move-exception
            r11 = r0
            r10.addSuppressed(r11)     // Catch: java.lang.Exception -> L85
        L84:
            throw r10     // Catch: java.lang.Exception -> L85
        L85:
            r0 = move-exception
            goto L89
        L87:
            r0 = move-exception
            r4 = r11
        L89:
            r10 = r0
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "Failed to read manifest from "
            r11.<init>(r12)
            r11.append(r1)
            java.lang.String r11 = r11.toString()
            r12 = -102(0xffffffffffffff9a, float:NaN)
            android.content.pm.parsing.result.ParseResult r10 = r4.error(r12, r11, r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseSplitApk(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, int, android.content.res.AssetManager, int):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult<ParsingPackage> parseBaseApk(ParseInput parseInput, String str, String str2, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z) throws XmlPullParserException, IOException {
        TypedArray typedArray;
        ParsingPackage startParsingPackage;
        ParseResult<Pair<String, String>> parsePackageSplitNames = ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
        if (parsePackageSplitNames.isError()) {
            return parseInput.error(parsePackageSplitNames);
        }
        Pair<String, String> result = parsePackageSplitNames.getResult();
        String str3 = result.first;
        String str4 = result.second;
        if (!TextUtils.isEmpty(str4)) {
            return parseInput.error(-106, "Expected base APK, but found split " + str4);
        }
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifest);
        try {
            startParsingPackage = this.mCallback.startParsingPackage(str3, str, str2, obtainAttributes, xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false));
            typedArray = obtainAttributes;
        } catch (Throwable th) {
            th = th;
            typedArray = obtainAttributes;
        }
        try {
            ParseResult<ParsingPackage> parseBaseApkTags = parseBaseApkTags(parseInput, startParsingPackage, typedArray, resources, xmlResourceParser, i, z);
            if (!parseBaseApkTags.isError()) {
                ParseResult<ParsingPackage> success = parseInput.success(startParsingPackage);
                typedArray.recycle();
                return success;
            }
            typedArray.recycle();
            return parseBaseApkTags;
        } catch (Throwable th2) {
            th = th2;
            Throwable th3 = th;
            typedArray.recycle();
            throw th3;
        }
    }

    private ParseResult<ParsingPackage> parseSplitApk(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, int i2) throws XmlPullParserException, IOException {
        ParseResult<ParsingPackage> unknownTag;
        ParseResult<Pair<String, String>> parsePackageSplitNames = ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
        if (parsePackageSplitNames.isError()) {
            return parseInput.error(parsePackageSplitNames);
        }
        int depth = xmlResourceParser.getDepth();
        boolean z = false;
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1) {
                if (depth + 1 >= xmlResourceParser.getDepth() && next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser)) {
                    if (!"application".equals(xmlResourceParser.getName())) {
                        unknownTag = ParsingUtils.unknownTag("<manifest>", parsingPackage, xmlResourceParser, parseInput);
                    } else if (z) {
                        Slog.w("PackageParsing", "<manifest> has more than one <application>");
                        unknownTag = parseInput.success(null);
                    } else {
                        unknownTag = parseSplitApplication(parseInput, parsingPackage, resources, xmlResourceParser, i, i2);
                        z = true;
                    }
                    if (unknownTag.isError()) {
                        return parseInput.error(unknownTag);
                    }
                }
            } else {
                if (!z) {
                    ParseResult<?> deferError = parseInput.deferError("<manifest> does not contain an <application>", ParseInput.DeferredError.MISSING_APP_TAG);
                    if (deferError.isError()) {
                        return parseInput.error(deferError);
                    }
                }
                return parseInput.success(parsingPackage);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0089, code lost:
    
        if (r2.equals("activity-alias") == false) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSplitApplication(android.content.pm.parsing.result.ParseInput r13, com.android.internal.pm.pkg.parsing.ParsingPackage r14, android.content.res.Resources r15, android.content.res.XmlResourceParser r16, int r17, int r18) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseSplitApplication(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, int, int):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult parseSplitBaseAppChildTags(ParseInput parseInput, String str, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        str.hashCode();
        switch (str) {
            case "uses-native-library":
                return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-sdk-library":
                return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-library":
                return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "meta-data":
                ParseResult<PackageManager.Property> parseMetaData = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
                if (parseMetaData.isSuccess() && parseMetaData.getResult() != null) {
                    parsingPackage.setMetaData(parseMetaData.getResult().toBundle(parsingPackage.getMetaData()));
                }
                return parseMetaData;
            case "property":
                ParseResult<PackageManager.Property> parseMetaData2 = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
                if (parseMetaData2.isSuccess()) {
                    parsingPackage.addProperty(parseMetaData2.getResult());
                }
                return parseMetaData2;
            case "uses-static-library":
                return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-package":
                return parseInput.success(null);
            default:
                return ParsingUtils.unknownTag("<application>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00c2, code lost:
    
        if (r10 == null) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00cc, code lost:
    
        if (com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.useLegacyRuntimeManifest(r13.getMetaData()) == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00ce, code lost:
    
        com.samsung.android.core.pm.runtimemanifest.LegacyRuntimeManifestParseUtils.modifyParsingPackageWithReplacement(r13, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00d1, code lost:
    
        if (r9 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00db, code lost:
    
        if (com.android.internal.util.ArrayUtils.size(r13.getInstrumentations()) != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00dd, code lost:
    
        r14 = r12.deferError("<manifest> does not contain an <application> or <instrumentation>", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_APP_TAG);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ea, code lost:
    
        if (r14.isError() == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00f0, code lost:
    
        return r12.error(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00f5, code lost:
    
        return validateBaseApkTags(r12, r13, r17);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApkTags(android.content.pm.parsing.result.ParseInput r12, com.android.internal.pm.pkg.parsing.ParsingPackage r13, android.content.res.TypedArray r14, android.content.res.Resources r15, android.content.res.XmlResourceParser r16, int r17, boolean r18) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseBaseApkTags(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.TypedArray, android.content.res.Resources, android.content.res.XmlResourceParser, int, boolean):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult<ParsingPackage> validateBaseApkTags(ParseInput parseInput, ParsingPackage parsingPackage, int i) {
        if (!ParsedAttributionUtils.isCombinationValid(parsingPackage.getAttributions())) {
            return parseInput.error(-101, "Combination <attribution> tags are not valid");
        }
        if (ParsedPermissionUtils.declareDuplicatePermission(parsingPackage)) {
            return parseInput.error(-108, "Found duplicate permission with a different attribute value.");
        }
        convertCompatPermissions(parsingPackage);
        convertSplitPermissions(parsingPackage);
        if (PMRune.PM_NAL_GET_APP_LIST) {
            String packageName = parsingPackage.getPackageName();
            if (!"android.permission.cts.appthataccesseslocation".equals(packageName) && !"android.permission.cts.appthatrequestcustompermission".equals(packageName) && !"com.google.android.sdksandbox".equals(packageName) && !"android.appenumeration.queries.nothing.hasprovider".equals(packageName) && !"android.os.lib.consumer1".equals(packageName) && !"com.android.tests.packagemanager.multiuser.app".equals(packageName) && !"android.appenumeration.queries.nothing.haspermission".equals(packageName)) {
                parsingPackage.addImplicitPermission(PackageManager.GET_APP_LIST_PERMISSION);
            }
        }
        if (parsingPackage.getTargetSdkVersion() < 4 || (!parsingPackage.isSmallScreensSupported() && !parsingPackage.isNormalScreensSupported() && !parsingPackage.isLargeScreensSupported() && !parsingPackage.isExtraLargeScreensSupported() && !parsingPackage.isResizeable() && !parsingPackage.isAnyDensity())) {
            adjustPackageToBeUnresizeableAndUnpipable(parsingPackage);
        }
        if ((i & 1024) != 0 && !parsingPackage.getPermissions().isEmpty()) {
            return parseInput.error(-108, parsingPackage.getPackageName() + " is an APEX package and shouldn't declare permissions.");
        }
        return parseInput.success(parsingPackage);
    }

    private ParseResult parseBaseApkTag(String str, ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws IOException, XmlPullParserException {
        str.hashCode();
        switch (str) {
            case "uses-configuration":
                return parseUsesConfiguration(parseInput, parsingPackage, resources, xmlResourceParser);
            case "permission-tree":
                return parsePermissionTree(parseInput, parsingPackage, resources, xmlResourceParser);
            case "original-package":
                return parseOriginalPackage(parseInput, parsingPackage, resources, xmlResourceParser);
            case "overlay":
                return parseOverlay(parseInput, parsingPackage, resources, xmlResourceParser);
            case "restrict-update":
                return parseRestrictUpdateHash(i, parseInput, parsingPackage, resources, xmlResourceParser);
            case "feature":
            case "attribution":
                return parseAttribution(parseInput, parsingPackage, resources, xmlResourceParser);
            case "permission":
                return parsePermission(parseInput, parsingPackage, resources, xmlResourceParser, i);
            case "uses-sdk":
                return parseUsesSdk(parseInput, parsingPackage, resources, xmlResourceParser, i);
            case "permission-group":
                return parsePermissionGroup(parseInput, parsingPackage, resources, xmlResourceParser);
            case "eat-comment":
            case "uses-gl-texture":
            case "supports-input":
            case "compatible-screens":
                XmlUtils.skipCurrentTag(xmlResourceParser);
                return parseInput.success(parsingPackage);
            case "key-sets":
                return parseKeySets(parseInput, parsingPackage, resources, xmlResourceParser);
            case "instrumentation":
                return parseInstrumentation(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-permission":
            case "uses-permission-sdk-23":
            case "uses-permission-sdk-m":
                return parseUsesPermission(parseInput, parsingPackage, resources, xmlResourceParser);
            case "adopt-permissions":
                return parseAdoptPermissions(parseInput, parsingPackage, resources, xmlResourceParser);
            case "queries":
                return parseQueries(parseInput, parsingPackage, resources, xmlResourceParser);
            case "install-constraints":
                return parseInstallConstraints(parseInput, parsingPackage, resources, xmlResourceParser, this.mCallback.getInstallConstraintsAllowlist());
            case "supports-screens":
                return parseSupportScreens(parseInput, parsingPackage, resources, xmlResourceParser);
            case "protected-broadcast":
                return parseProtectedBroadcast(parseInput, parsingPackage, resources, xmlResourceParser);
            case "feature-group":
                return parseFeatureGroup(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-feature":
                return parseUsesFeature(parseInput, parsingPackage, resources, xmlResourceParser);
            default:
                return ParsingUtils.unknownTag("<manifest>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    private static ParseResult<ParsingPackage> parseSharedUser(ParseInput parseInput, ParsingPackage parsingPackage, TypedArray typedArray) {
        boolean z = false;
        String nonConfigString = nonConfigString(0, 0, typedArray);
        if (TextUtils.isEmpty(nonConfigString)) {
            return parseInput.success(parsingPackage);
        }
        if (!"android".equals(parsingPackage.getPackageName())) {
            ParseResult validateName = FrameworkParsingPackageUtils.validateName(parseInput, nonConfigString, true, true);
            if (validateName.isError()) {
                return parseInput.error(-107, "<manifest> specifies bad sharedUserId name \"" + nonConfigString + "\": " + validateName.getErrorMessage());
            }
        }
        int anInteger = anInteger(0, 13, typedArray);
        if (anInteger != 0 && anInteger < Build.VERSION.RESOURCES_SDK_INT) {
            z = true;
        }
        return parseInput.success(parsingPackage.setLeavingSharedUser(z).setSharedUserId(nonConfigString.intern()).setSharedUserLabelResourceId(resId(3, typedArray)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x01ae, code lost:
    
        r2 = r17.getPackageName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x01c0, code lost:
    
        if (r5.keySet().removeAll(r7.keySet()) == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x01d7, code lost:
    
        return r16.error("Package" + r2 + " AndroidManifest.xml 'key-set' and 'public-key' names must be distinct.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x01d8, code lost:
    
        r3 = r7.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x01e4, code lost:
    
        if (r3.hasNext() == false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x01e6, code lost:
    
        r7 = (java.util.Map.Entry) r3.next();
        r9 = (java.lang.String) r7.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x01fe, code lost:
    
        if (((android.util.ArraySet) r7.getValue()).size() != 0) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x021f, code lost:
    
        if (r8.contains(r9) == false) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x023c, code lost:
    
        r7 = ((android.util.ArraySet) r7.getValue()).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x024a, code lost:
    
        if (r7.hasNext() == false) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x024c, code lost:
    
        r17.addKeySet(r9, (java.security.PublicKey) r5.get((java.lang.String) r7.next()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0221, code lost:
    
        android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0200, code lost:
    
        android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0268, code lost:
    
        if (r17.getKeySetMapping().keySet().containsAll(r6) == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x026a, code lost:
    
        r17.setUpgradeKeySets(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0271, code lost:
    
        return r16.success(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0287, code lost:
    
        return r16.error("Package" + r2 + " AndroidManifest.xml does not define all 'upgrade-key-set's .");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseKeySets(android.content.pm.parsing.result.ParseInput r16, com.android.internal.pm.pkg.parsing.ParsingPackage r17, android.content.res.Resources r18, android.content.res.XmlResourceParser r19) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 672
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseKeySets(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsingPackage> parseAttribution(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        ParseResult<ParsedAttribution> parseAttribution = ParsedAttributionUtils.parseAttribution(resources, xmlResourceParser, parseInput);
        if (parseAttribution.isError()) {
            return parseInput.error(parseAttribution);
        }
        return parseInput.success(parsingPackage.addAttribution(parseAttribution.getResult()));
    }

    private static ParseResult<ParsingPackage> parsePermissionGroup(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedPermissionGroup> parsePermissionGroup = ParsedPermissionUtils.parsePermissionGroup(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parsePermissionGroup.isError()) {
            return parseInput.error(parsePermissionGroup);
        }
        return parseInput.success(parsingPackage.addPermissionGroup(parsePermissionGroup.getResult()));
    }

    private static ParseResult<ParsingPackage> parsePermission(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException, IOException {
        ParseResult<ParsedPermission> parsePermission = ParsedPermissionUtils.parsePermission(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput, i);
        if (parsePermission.isError()) {
            return parseInput.error(parsePermission);
        }
        ParsedPermission result = parsePermission.getResult();
        if (result != null) {
            parsingPackage.addPermission(result);
        }
        return parseInput.success(parsingPackage);
    }

    private static ParseResult<ParsingPackage> parsePermissionTree(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedPermission> parsePermissionTree = ParsedPermissionUtils.parsePermissionTree(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parsePermissionTree.isError()) {
            return parseInput.error(parsePermissionTree);
        }
        return parseInput.success(parsingPackage.addPermission(parsePermissionTree.getResult()));
    }

    private int parseMinOrMaxSdkVersion(TypedArray typedArray, int i, int i2) {
        TypedValue peekValue = typedArray.peekValue(i);
        return (peekValue == null || peekValue.type < 16 || peekValue.type > 31) ? i2 : peekValue.data;
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00c3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00aa A[Catch: all -> 0x01b8, TryCatch #0 {all -> 0x01b8, blocks: (B:3:0x0011, B:5:0x0030, B:6:0x0033, B:8:0x003f, B:9:0x0042, B:10:0x004b, B:13:0x0053, B:74:0x0060, B:83:0x008f, B:84:0x00bd, B:89:0x00c3, B:92:0x0096, B:94:0x00a0, B:95:0x00aa, B:97:0x00b4, B:98:0x0073, B:101:0x007e, B:16:0x00d2, B:21:0x00dc, B:23:0x00e0, B:26:0x00e6, B:28:0x00ea, B:30:0x00f4, B:37:0x0109, B:39:0x0113, B:46:0x0128, B:48:0x0133, B:52:0x0143, B:55:0x014d, B:58:0x017a, B:50:0x01a5, B:62:0x01a8), top: B:2:0x0011 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesPermission(android.content.pm.parsing.result.ParseInput r18, com.android.internal.pm.pkg.parsing.ParsingPackage r19, android.content.res.Resources r20, android.content.res.XmlResourceParser r21) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseUsesPermission(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult<String> parseRequiredFeature(ParseInput parseInput, Resources resources, AttributeSet attributeSet) {
        ParseResult<String> success;
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestRequiredFeature);
        try {
            String string = obtainAttributes.getString(0);
            if (TextUtils.isEmpty(string)) {
                success = parseInput.error("Feature name is missing from <required-feature> tag.");
            } else {
                success = parseInput.success(string);
            }
            return success;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private ParseResult<String> parseRequiredNotFeature(ParseInput parseInput, Resources resources, AttributeSet attributeSet) {
        ParseResult<String> success;
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestRequiredNotFeature);
        try {
            String string = obtainAttributes.getString(0);
            if (TextUtils.isEmpty(string)) {
                success = parseInput.error("Feature name is missing from <required-not-feature> tag.");
            } else {
                success = parseInput.success(string);
            }
            return success;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesConfiguration(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        ConfigurationInfo configurationInfo = new ConfigurationInfo();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesConfiguration);
        try {
            configurationInfo.reqTouchScreen = obtainAttributes.getInt(0, 0);
            configurationInfo.reqKeyboardType = obtainAttributes.getInt(1, 0);
            if (obtainAttributes.getBoolean(2, false)) {
                configurationInfo.reqInputFeatures = 1 | configurationInfo.reqInputFeatures;
            }
            configurationInfo.reqNavigation = obtainAttributes.getInt(3, 0);
            if (obtainAttributes.getBoolean(4, false)) {
                configurationInfo.reqInputFeatures |= 2;
            }
            parsingPackage.addConfigPreference(configurationInfo);
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesFeature(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        FeatureInfo parseFeatureInfo = parseFeatureInfo(resources, xmlResourceParser);
        parsingPackage.addReqFeature(parseFeatureInfo);
        if (parseFeatureInfo.name == null) {
            ConfigurationInfo configurationInfo = new ConfigurationInfo();
            configurationInfo.reqGlEsVersion = parseFeatureInfo.reqGlEsVersion;
            parsingPackage.addConfigPreference(configurationInfo);
        }
        return parseInput.success(parsingPackage);
    }

    private static FeatureInfo parseFeatureInfo(Resources resources, AttributeSet attributeSet) {
        FeatureInfo featureInfo = new FeatureInfo();
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestUsesFeature);
        try {
            featureInfo.name = obtainAttributes.getNonResourceString(0);
            featureInfo.version = obtainAttributes.getInt(3, 0);
            if (featureInfo.name == null) {
                featureInfo.reqGlEsVersion = obtainAttributes.getInt(1, 0);
            }
            if (obtainAttributes.getBoolean(2, true)) {
                featureInfo.flags |= 1;
            }
            return featureInfo;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseFeatureGroup(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
        int depth = xmlResourceParser.getDepth();
        ArrayList arrayList = null;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser)) {
                String name = xmlResourceParser.getName();
                if (name.equals("uses-feature")) {
                    FeatureInfo parseFeatureInfo = parseFeatureInfo(resources, xmlResourceParser);
                    parseFeatureInfo.flags = 1 | parseFeatureInfo.flags;
                    arrayList = ArrayUtils.add((ArrayList<FeatureInfo>) arrayList, parseFeatureInfo);
                } else {
                    Slog.w("PackageParsing", "Unknown element under <feature-group>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                }
            }
        }
        if (arrayList != null) {
            featureGroupInfo.features = new FeatureInfo[arrayList.size()];
            featureGroupInfo.features = (FeatureInfo[]) arrayList.toArray(featureGroupInfo.features);
        }
        parsingPackage.addFeatureGroup(featureGroupInfo);
        return parseInput.success(parsingPackage);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0049 A[Catch: all -> 0x0141, TryCatch #0 {all -> 0x0141, blocks: (B:8:0x001d, B:10:0x0025, B:12:0x0029, B:14:0x002d, B:15:0x0043, B:17:0x0049, B:19:0x004d, B:21:0x0051, B:25:0x006a, B:27:0x0071, B:28:0x0077, B:30:0x0083, B:34:0x008b, B:36:0x00a3, B:39:0x00ab, B:41:0x00b5, B:44:0x00bd, B:46:0x00d0, B:48:0x00da, B:51:0x00e2, B:52:0x00ef, B:53:0x00f3, B:56:0x00fb, B:66:0x0107, B:69:0x0115, B:70:0x011a, B:71:0x0128, B:74:0x012e, B:79:0x0122, B:59:0x0136, B:87:0x0061, B:89:0x003c), top: B:7:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0083 A[Catch: all -> 0x0141, TRY_LEAVE, TryCatch #0 {all -> 0x0141, blocks: (B:8:0x001d, B:10:0x0025, B:12:0x0029, B:14:0x002d, B:15:0x0043, B:17:0x0049, B:19:0x004d, B:21:0x0051, B:25:0x006a, B:27:0x0071, B:28:0x0077, B:30:0x0083, B:34:0x008b, B:36:0x00a3, B:39:0x00ab, B:41:0x00b5, B:44:0x00bd, B:46:0x00d0, B:48:0x00da, B:51:0x00e2, B:52:0x00ef, B:53:0x00f3, B:56:0x00fb, B:66:0x0107, B:69:0x0115, B:70:0x011a, B:71:0x0128, B:74:0x012e, B:79:0x0122, B:59:0x0136, B:87:0x0061, B:89:0x003c), top: B:7:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008b A[Catch: all -> 0x0141, TRY_ENTER, TryCatch #0 {all -> 0x0141, blocks: (B:8:0x001d, B:10:0x0025, B:12:0x0029, B:14:0x002d, B:15:0x0043, B:17:0x0049, B:19:0x004d, B:21:0x0051, B:25:0x006a, B:27:0x0071, B:28:0x0077, B:30:0x0083, B:34:0x008b, B:36:0x00a3, B:39:0x00ab, B:41:0x00b5, B:44:0x00bd, B:46:0x00d0, B:48:0x00da, B:51:0x00e2, B:52:0x00ef, B:53:0x00f3, B:56:0x00fb, B:66:0x0107, B:69:0x0115, B:70:0x011a, B:71:0x0128, B:74:0x012e, B:79:0x0122, B:59:0x0136, B:87:0x0061, B:89:0x003c), top: B:7:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesSdk(android.content.pm.parsing.result.ParseInput r18, com.android.internal.pm.pkg.parsing.ParsingPackage r19, android.content.res.Resources r20, android.content.res.XmlResourceParser r21, int r22) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseUsesSdk(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, int):android.content.pm.parsing.result.ParseResult");
    }

    private static SparseIntArray exactSizedCopyOfSparseArray(SparseIntArray sparseIntArray) {
        if (sparseIntArray == null) {
            return null;
        }
        SparseIntArray sparseIntArray2 = new SparseIntArray(sparseIntArray.size());
        for (int i = 0; i < sparseIntArray.size(); i++) {
            sparseIntArray2.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
        }
        return sparseIntArray2;
    }

    private static ParseResult<SparseIntArray> parseExtensionSdk(ParseInput parseInput, Resources resources, XmlResourceParser xmlResourceParser, SparseIntArray sparseIntArray) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestExtensionSdk);
        try {
            int i = obtainAttributes.getInt(0, -1);
            int i2 = obtainAttributes.getInt(1, -1);
            obtainAttributes.recycle();
            if (i < 0) {
                return parseInput.error(-108, "<extension-sdk> must specify an sdkVersion >= 0");
            }
            if (i2 < 0) {
                return parseInput.error(-108, "<extension-sdk> must specify minExtensionVersion >= 0");
            }
            try {
                int extensionVersion = SdkExtensions.getExtensionVersion(i);
                if (extensionVersion < i2) {
                    return parseInput.error(-12, "Package requires " + i + " extension version " + i2 + " which exceeds device version " + extensionVersion);
                }
                sparseIntArray.put(i, i2);
                return parseInput.success(sparseIntArray);
            } catch (RuntimeException unused) {
                return parseInput.error(-108, "Specified sdkVersion " + i + " is not valid");
            }
        } catch (Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    private static ParseResult<ParsingPackage> parseRestrictUpdateHash(int i, ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        if ((i & 16) != 0) {
            TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestRestrictUpdate);
            try {
                String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
                if (nonConfigurationString != null) {
                    int length = nonConfigurationString.length();
                    byte[] bArr = new byte[length / 2];
                    for (int i2 = 0; i2 < length; i2 += 2) {
                        bArr[i2 / 2] = (byte) ((Character.digit(nonConfigurationString.charAt(i2), 16) << 4) + Character.digit(nonConfigurationString.charAt(i2 + 1), 16));
                    }
                    parsingPackage.setRestrictUpdateHash(bArr);
                } else {
                    parsingPackage.setRestrictUpdateHash(null);
                }
            } finally {
                obtainAttributes.recycle();
            }
        }
        return parseInput.success(parsingPackage);
    }

    private static ParseResult<ParsingPackage> parseInstallConstraints(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, Set<String> set) throws IOException, XmlPullParserException {
        return InstallConstraintsTagParser.parseInstallConstraints(parseInput, parsingPackage, resources, xmlResourceParser, set);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x01a9, code lost:
    
        return r12.success(r13);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseQueries(android.content.pm.parsing.result.ParseInput r12, com.android.internal.pm.pkg.parsing.ParsingPackage r13, android.content.res.Resources r14, android.content.res.XmlResourceParser r15) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseQueries(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0343, code lost:
    
        if (r2.equals(com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.TAG_PROVIDER) == false) goto L162;
     */
    /* JADX WARN: Removed duplicated region for block: B:185:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x04d6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0497  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApplication(android.content.pm.parsing.result.ParseInput r21, com.android.internal.pm.pkg.parsing.ParsingPackage r22, android.content.res.Resources r23, android.content.res.XmlResourceParser r24, int r25, boolean r26) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseBaseApplication(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, int, boolean):android.content.pm.parsing.result.ParseResult");
    }

    private void afterParseBaseApplication(ParsingPackage parsingPackage) {
        setMaxAspectRatio(parsingPackage);
        setMinAspectRatio(parsingPackage);
        setSupportsSizeChanges(parsingPackage);
        parsingPackage.setHasDomainUrls(hasDomainURLs(parsingPackage));
    }

    private void parseBaseAppBasicFlags(ParsingPackage parsingPackage, TypedArray typedArray) {
        int targetSdkVersion = parsingPackage.getTargetSdkVersion();
        parsingPackage.setBackupAllowed(bool(true, 17, typedArray)).setClearUserDataAllowed(bool(true, 5, typedArray)).setClearUserDataOnFailedRestoreAllowed(bool(true, 54, typedArray)).setAllowNativeHeapPointerTagging(bool(true, 59, typedArray)).setEnabled(bool(true, 9, typedArray)).setExtractNativeLibrariesRequested(bool(true, 34, typedArray)).setDeclaredHavingCode(bool(true, 7, typedArray)).setTaskReparentingAllowed(bool(false, 14, typedArray)).setSaveStateDisallowed(bool(false, 47, typedArray)).setCrossProfile(bool(false, 58, typedArray)).setDebuggable(bool(false, 10, typedArray)).setDefaultToDeviceProtectedStorage(bool(false, 38, typedArray)).setDirectBootAware(bool(false, 39, typedArray)).setForceQueryable(bool(false, 57, typedArray)).setGame(bool(false, 31, typedArray)).setUserDataFragile(bool(false, 50, typedArray)).setLargeHeap(bool(false, 24, typedArray)).setMultiArch(bool(false, 33, typedArray)).setPreserveLegacyExternalStorage(bool(false, 61, typedArray)).setRequiredForAllUsers(bool(false, 27, typedArray)).setRtlSupported(bool(false, 26, typedArray)).setTestOnly(bool(false, 15, typedArray)).setUseEmbeddedDex(bool(false, 53, typedArray)).setNonSdkApiRequested(bool(false, 49, typedArray)).setVmSafeMode(bool(false, 20, typedArray)).setAutoRevokePermissions(anInt(60, typedArray)).setAttributionsAreUserVisible(bool(false, 69, typedArray)).setResetEnabledSettingsOnAppDataCleared(bool(false, 70, typedArray)).setAllowAudioPlaybackCapture(bool(targetSdkVersion >= 29, 55, typedArray)).setHardwareAccelerated(bool(targetSdkVersion >= 14, 23, typedArray)).setRequestLegacyExternalStorage(bool(targetSdkVersion < 29, 56, typedArray)).setCleartextTrafficAllowed(bool(targetSdkVersion < 28, 36, typedArray)).setOnBackInvokedCallbackEnabled(bool(com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.predictiveBackDefaultEnableSdk36() && targetSdkVersion > 35, 73, typedArray)).setUiOptions(anInt(25, typedArray)).setCategory(anInt(-1, 43, typedArray)).setMaxAspectRatio(aFloat(44, typedArray)).setMinAspectRatio(aFloat(51, typedArray)).setBannerResourceId(resId(30, typedArray)).setDescriptionResourceId(resId(13, typedArray)).setIconResourceId(resId(2, typedArray)).setLogoResourceId(resId(22, typedArray)).setNetworkSecurityConfigResourceId(resId(41, typedArray)).setRoundIconResourceId(resId(42, typedArray)).setThemeResourceId(resId(0, typedArray)).setDataExtractionRulesResourceId(resId(66, typedArray)).setLocaleConfigResourceId(resId(71, typedArray)).setClassLoaderName(string(46, typedArray)).setRequiredAccountType(string(29, typedArray)).setRestrictedAccountType(string(28, typedArray)).setZygotePreloadName(string(52, typedArray)).setPermission(nonConfigString(0, 6, typedArray)).setAllowCrossUidActivitySwitchFromBelow(bool(true, 74, typedArray));
    }

    private ParseResult parseBaseAppChildTag(ParseInput parseInput, String str, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws IOException, XmlPullParserException {
        str.hashCode();
        switch (str) {
            case "sdk-library":
                return parseSdkLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case "uses-native-library":
                return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-sdk-library":
                return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-library":
                return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "meta-data":
                ParseResult<PackageManager.Property> parseMetaData = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
                if (parseMetaData.isSuccess() && parseMetaData.getResult() != null) {
                    parsingPackage.setMetaData(parseMetaData.getResult().toBundle(parsingPackage.getMetaData()));
                }
                return parseMetaData;
            case "processes":
                return parseProcesses(parseInput, parsingPackage, resources, xmlResourceParser, this.mSeparateProcesses, i);
            case "static-library":
                return parseStaticLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case "property":
                ParseResult<PackageManager.Property> parseMetaData2 = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
                if (parseMetaData2.isSuccess()) {
                    parsingPackage.addProperty(parseMetaData2.getResult());
                }
                return parseMetaData2;
            case "uses-static-library":
                return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "library":
                return parseLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case "profileable":
                return parseProfileable(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-package":
                return parseInput.success(null);
            default:
                return ParsingUtils.unknownTag("<application>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    private static ParseResult<ParsingPackage> parseSdkLibrary(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestSdkLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(1, -1);
            if (nonResourceString != null && i >= 0) {
                if (parsingPackage.getSharedUserId() != null) {
                    return parseInput.error(-107, "sharedUserId not allowed in SDK library");
                }
                if (parsingPackage.getSdkLibraryName() == null) {
                    return parseInput.success(parsingPackage.setSdkLibraryName(nonResourceString.intern()).setSdkLibVersionMajor(i).setSdkLibrary(true));
                }
                return parseInput.error("Multiple SDKs for package " + parsingPackage.getPackageName());
            }
            return parseInput.error("Bad sdk-library declaration name: " + nonResourceString + " version: " + i);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseStaticLibrary(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestStaticLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(1, -1);
            int i2 = obtainAttributes.getInt(2, 0);
            if (nonResourceString != null && i >= 0) {
                if (parsingPackage.getSharedUserId() != null) {
                    return parseInput.error(-107, "sharedUserId not allowed in static shared library");
                }
                if (parsingPackage.getStaticSharedLibraryName() == null) {
                    return parseInput.success(parsingPackage.setStaticSharedLibraryName(nonResourceString.intern()).setStaticSharedLibraryVersion(PackageInfo.composeLongVersionCode(i2, i)).setStaticSharedLibrary(true));
                }
                return parseInput.error("Multiple static-shared libs for package " + parsingPackage.getPackageName());
            }
            return parseInput.error("Bad static-library declaration name: " + nonResourceString + " version: " + i);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseLibrary(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            if (nonResourceString != null) {
                String intern = nonResourceString.intern();
                if (!ArrayUtils.contains(parsingPackage.getLibraryNames(), intern)) {
                    parsingPackage.addLibraryName(intern);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesSdkLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        String str = "";
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesSdkLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(2, -1);
            String nonResourceString2 = obtainAttributes.getNonResourceString(1);
            boolean z = obtainAttributes.getBoolean(3, false);
            if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
                if (parsingPackage.getUsesSdkLibraries().contains(nonResourceString)) {
                    return parseInput.error("Depending on multiple versions of SDK library " + nonResourceString);
                }
                String intern = nonResourceString.intern();
                String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
                if ("".equals(lowerCase)) {
                    lowerCase = SystemProperties.get("debug.pm.uses_sdk_library_default_cert_digest", "");
                    try {
                        HexEncoding.decode(lowerCase, false);
                    } catch (IllegalArgumentException unused) {
                    }
                }
                str = lowerCase;
                ParseResult<String[]> parseAdditionalCertificates = parseAdditionalCertificates(parseInput, parsingPackage, resources, xmlResourceParser);
                if (parseAdditionalCertificates.isError()) {
                    return parseInput.error((ParseResult<?>) parseAdditionalCertificates);
                }
                String[] result = parseAdditionalCertificates.getResult();
                String[] strArr = new String[result.length + 1];
                strArr[0] = str;
                System.arraycopy(result, 0, strArr, 1, result.length);
                return parseInput.success(parsingPackage.addUsesSdkLibrary(intern, i, strArr, z));
            }
            return parseInput.error("Bad uses-sdk-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesStaticLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesStaticLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(1, -1);
            String nonResourceString2 = obtainAttributes.getNonResourceString(2);
            if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
                if (parsingPackage.getUsesStaticLibraries().contains(nonResourceString)) {
                    return parseInput.error("Depending on multiple versions of static library " + nonResourceString);
                }
                String intern = nonResourceString.intern();
                String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
                String[] strArr = EmptyArray.STRING;
                if (parsingPackage.getTargetSdkVersion() >= 27) {
                    ParseResult<String[]> parseAdditionalCertificates = parseAdditionalCertificates(parseInput, parsingPackage, resources, xmlResourceParser);
                    if (parseAdditionalCertificates.isError()) {
                        return parseInput.error((ParseResult<?>) parseAdditionalCertificates);
                    }
                    strArr = parseAdditionalCertificates.getResult();
                }
                String[] strArr2 = new String[strArr.length + 1];
                strArr2[0] = lowerCase;
                System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
                return parseInput.success(parsingPackage.addUsesStaticLibrary(intern, i, strArr2));
            }
            return parseInput.error("Bad uses-static-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            boolean z = obtainAttributes.getBoolean(1, true);
            if (nonResourceString != null) {
                String intern = nonResourceString.intern();
                if (z) {
                    parsingPackage.addUsesLibrary(intern).removeUsesOptionalLibrary(intern);
                } else if (!ArrayUtils.contains(parsingPackage.getUsesLibraries(), intern)) {
                    parsingPackage.addUsesOptionalLibrary(intern);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesNativeLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesNativeLibrary);
        try {
            String nonResourceString = obtainAttributes.getNonResourceString(0);
            boolean z = obtainAttributes.getBoolean(1, true);
            if (nonResourceString != null) {
                if (z) {
                    parsingPackage.addUsesNativeLibrary(nonResourceString).removeUsesOptionalNativeLibrary(nonResourceString);
                } else if (!ArrayUtils.contains(parsingPackage.getUsesNativeLibraries(), nonResourceString)) {
                    parsingPackage.addUsesOptionalNativeLibrary(nonResourceString);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseProcesses(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, String[] strArr, int i) throws IOException, XmlPullParserException {
        ParseResult<ArrayMap<String, ParsedProcess>> parseProcesses = ParsedProcessUtils.parseProcesses(strArr, parsingPackage, resources, xmlResourceParser, i, parseInput);
        if (parseProcesses.isError()) {
            return parseInput.error(parseProcesses);
        }
        return parseInput.success(parsingPackage.setProcesses(parseProcesses.getResult()));
    }

    private static ParseResult<ParsingPackage> parseProfileable(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        boolean z;
        ParsingPackage profileableByShell;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProfileable);
        try {
            boolean z2 = false;
            if (!parsingPackage.isProfileableByShell() && !bool(false, 1, obtainAttributes)) {
                z = false;
                profileableByShell = parsingPackage.setProfileableByShell(z);
                if (profileableByShell.isProfileable() && bool(true, 0, obtainAttributes)) {
                    z2 = true;
                }
                return parseInput.success(profileableByShell.setProfileable(z2));
            }
            z = true;
            profileableByShell = parsingPackage.setProfileableByShell(z);
            if (profileableByShell.isProfileable()) {
                z2 = true;
            }
            return parseInput.success(profileableByShell.setProfileable(z2));
        } finally {
            obtainAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007a, code lost:
    
        return r6.success(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<java.lang.String[]> parseAdditionalCertificates(android.content.pm.parsing.result.ParseInput r6, com.android.internal.pm.pkg.parsing.ParsingPackage r7, android.content.res.Resources r8, android.content.res.XmlResourceParser r9) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            java.lang.String[] r0 = libcore.util.EmptyArray.STRING
            int r1 = r9.getDepth()
        L6:
            int r2 = r9.next()
            r3 = 1
            if (r2 == r3) goto L76
            r3 = 3
            if (r2 != r3) goto L16
            int r3 = r9.getDepth()
            if (r3 <= r1) goto L76
        L16:
            r3 = 2
            if (r2 == r3) goto L1a
            goto L6
        L1a:
            com.android.internal.pm.pkg.component.AconfigFlags r2 = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sAconfigFlags
            boolean r2 = r2.skipCurrentElement(r7, r9)
            if (r2 == 0) goto L23
            goto L6
        L23:
            java.lang.String r2 = r9.getName()
            java.lang.String r3 = "additional-certificate"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L6
            int[] r2 = com.android.internal.R.styleable.AndroidManifestAdditionalCertificate
            android.content.res.TypedArray r2 = r8.obtainAttributes(r9, r2)
            r3 = 0
            java.lang.String r3 = r2.getNonResourceString(r3)     // Catch: java.lang.Throwable -> L71
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Throwable -> L71
            if (r4 == 0) goto L59
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L71
            r7.<init>()     // Catch: java.lang.Throwable -> L71
            java.lang.String r8 = "Bad additional-certificate declaration with empty certDigest:"
            r7.append(r8)     // Catch: java.lang.Throwable -> L71
            r7.append(r3)     // Catch: java.lang.Throwable -> L71
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L71
            android.content.pm.parsing.result.ParseResult r6 = r6.error(r7)     // Catch: java.lang.Throwable -> L71
            r2.recycle()
            return r6
        L59:
            java.lang.String r4 = ":"
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> L71
            java.lang.String r3 = r3.toLowerCase()     // Catch: java.lang.Throwable -> L71
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            java.lang.Object[] r0 = com.android.internal.util.ArrayUtils.appendElement(r4, r0, r3)     // Catch: java.lang.Throwable -> L71
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch: java.lang.Throwable -> L71
            r2.recycle()
            goto L6
        L71:
            r6 = move-exception
            r2.recycle()
            throw r6
        L76:
            android.content.pm.parsing.result.ParseResult r6 = r6.success(r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseAdditionalCertificates(android.content.pm.parsing.result.ParseInput, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedActivity> generateAppDetailsHiddenActivity(ParseInput parseInput, ParsingPackage parsingPackage) {
        String packageName = parsingPackage.getPackageName();
        ParseResult<String> buildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, packageName, ":app_details", parseInput);
        if (buildTaskAffinityName.isError()) {
            return parseInput.error(buildTaskAffinityName);
        }
        return parseInput.success(ParsedActivityImpl.makeAppDetailsActivity(packageName, parsingPackage.getProcessName(), parsingPackage.getUiOptions(), buildTaskAffinityName.getResult(), parsingPackage.isHardwareAccelerated()));
    }

    private static boolean hasDomainURLs(ParsingPackage parsingPackage) {
        List<ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            List<ParsedIntentInfo> intents = activities.get(i).getIntents();
            int size2 = intents.size();
            for (int i2 = 0; i2 < size2; i2++) {
                IntentFilter intentFilter = intents.get(i2).getIntentFilter();
                if (intentFilter.hasAction("android.intent.action.VIEW") && intentFilter.hasAction("android.intent.action.VIEW") && (intentFilter.hasDataScheme(IntentFilter.SCHEME_HTTP) || intentFilter.hasDataScheme(IntentFilter.SCHEME_HTTPS))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setMaxAspectRatio(ParsingPackage parsingPackage) {
        float f = parsingPackage.getTargetSdkVersion() < 26 ? 1.86f : 0.0f;
        float maxAspectRatio = parsingPackage.getMaxAspectRatio();
        if (maxAspectRatio != 0.0f) {
            f = maxAspectRatio;
        } else {
            Bundle metaData = parsingPackage.getMetaData();
            if (metaData != null && metaData.containsKey("android.max_aspect")) {
                f = metaData.getFloat("android.max_aspect", f);
            }
        }
        List<ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = activities.get(i);
            if (parsedActivity.getMaxAspectRatio() == -1.0f) {
                ComponentMutateUtils.setMaxAspectRatio(parsedActivity, parsedActivity.getResizeMode(), parsedActivity.getMetaData().getFloat("android.max_aspect", f));
            }
        }
    }

    private void setMinAspectRatio(ParsingPackage parsingPackage) {
        float minAspectRatio = parsingPackage.getMinAspectRatio();
        List<ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = activities.get(i);
            if (parsedActivity.getMinAspectRatio() == -1.0f) {
                ComponentMutateUtils.setMinAspectRatio(parsedActivity, parsedActivity.getResizeMode(), minAspectRatio);
            }
        }
    }

    private void setSupportsSizeChanges(ParsingPackage parsingPackage) {
        Bundle metaData = parsingPackage.getMetaData();
        boolean z = metaData != null && metaData.getBoolean("android.supports_size_changes", false);
        List<ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = activities.get(i);
            if (z || parsedActivity.getMetaData().getBoolean("android.supports_size_changes", false)) {
                ComponentMutateUtils.setSupportsSizeChanges(parsedActivity, true);
            }
        }
    }

    private static ParseResult<ParsingPackage> parseOverlay(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestResourceOverlay);
        try {
            String string = obtainAttributes.getString(1);
            int anInt = anInt(0, 0, obtainAttributes);
            if (string == null) {
                return parseInput.error("<overlay> does not specify a target package");
            }
            if (anInt < 0 || anInt > 9999) {
                return parseInput.error("<overlay> priority must be between 0 and 9999");
            }
            String string2 = obtainAttributes.getString(5);
            String string3 = obtainAttributes.getString(6);
            if (FrameworkParsingPackageUtils.checkRequiredSystemProperties(string2, string3)) {
                return parseInput.success(parsingPackage.setResourceOverlay(true).setOverlayTarget(string).setOverlayPriority(anInt).setOverlayTargetOverlayableName(obtainAttributes.getString(3)).setOverlayCategory(obtainAttributes.getString(2)).setOverlayIsStatic(bool(false, 4, obtainAttributes)));
            }
            String str = "Skipping target and overlay pair " + string + " and " + parsingPackage.getBaseApkPath() + ": overlay ignored due to required system property: " + string2 + " with value: " + string3;
            Slog.i("PackageParsing", str);
            return parseInput.skip(str);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseProtectedBroadcast(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProtectedBroadcast);
        try {
            String nonResString = nonResString(0, obtainAttributes);
            if (nonResString != null) {
                parsingPackage.addProtectedBroadcast(nonResString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseSupportScreens(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestSupportsScreens);
        try {
            int anInt = anInt(0, 6, obtainAttributes);
            int anInt2 = anInt(0, 7, obtainAttributes);
            return parseInput.success(parsingPackage.setSmallScreensSupported(anInt(1, 1, obtainAttributes)).setNormalScreensSupported(anInt(1, 2, obtainAttributes)).setLargeScreensSupported(anInt(1, 3, obtainAttributes)).setExtraLargeScreensSupported(anInt(1, 5, obtainAttributes)).setResizeable(anInt(1, 4, obtainAttributes)).setAnyDensity(anInt(1, 0, obtainAttributes)).setRequiresSmallestWidthDp(anInt).setCompatibleWidthLimitDp(anInt2).setLargestWidthLimitDp(anInt(0, 8, obtainAttributes)));
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseInstrumentation(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedInstrumentation> parseInstrumentation = ParsedInstrumentationUtils.parseInstrumentation(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parseInstrumentation.isError()) {
            return parseInput.error(parseInstrumentation);
        }
        return parseInput.success(parsingPackage.addInstrumentation(parseInstrumentation.getResult()));
    }

    private static ParseResult<ParsingPackage> parseOriginalPackage(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestOriginalPackage);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (!parsingPackage.getPackageName().equals(nonConfigurationString)) {
                parsingPackage.addOriginalPackage(nonConfigurationString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseAdoptPermissions(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAdoptPermissions);
        try {
            String nonConfigString = nonConfigString(0, 0, obtainAttributes);
            if (nonConfigString != null) {
                parsingPackage.addAdoptPermission(nonConfigString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static void convertCompatPermissions(ParsingPackage parsingPackage) {
        int length = CompatibilityPermissionInfo.COMPAT_PERMS.length;
        for (int i = 0; i < length; i++) {
            CompatibilityPermissionInfo compatibilityPermissionInfo = CompatibilityPermissionInfo.COMPAT_PERMS[i];
            if (parsingPackage.getTargetSdkVersion() >= compatibilityPermissionInfo.getSdkVersion()) {
                return;
            }
            if (!parsingPackage.getRequestedPermissions().contains(compatibilityPermissionInfo.getName())) {
                parsingPackage.addImplicitPermission(compatibilityPermissionInfo.getName());
            }
        }
    }

    private void convertSplitPermissions(ParsingPackage parsingPackage) {
        int size = this.mSplitPermissionInfos.size();
        for (int i = 0; i < size; i++) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo = this.mSplitPermissionInfos.get(i);
            Set<String> requestedPermissions = parsingPackage.getRequestedPermissions();
            if (parsingPackage.getTargetSdkVersion() < splitPermissionInfo.getTargetSdk() && requestedPermissions.contains(splitPermissionInfo.getSplitPermission())) {
                List<String> newPermissions = splitPermissionInfo.getNewPermissions();
                for (int i2 = 0; i2 < newPermissions.size(); i2++) {
                    String str = newPermissions.get(i2);
                    if (!requestedPermissions.contains(str)) {
                        parsingPackage.addImplicitPermission(str);
                    }
                }
            }
        }
    }

    private static void adjustPackageToBeUnresizeableAndUnpipable(ParsingPackage parsingPackage) {
        List<ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            ParsedActivity parsedActivity = activities.get(i);
            ComponentMutateUtils.setResizeMode(parsedActivity, 0);
            ComponentMutateUtils.setExactFlags(parsedActivity, parsedActivity.getFlags() & (-4194305));
        }
    }

    public static ParseResult<PackageManager.Property> parseMetaData(ParsingPackage parsingPackage, ParsedComponent parsedComponent, Resources resources, XmlResourceParser xmlResourceParser, String str, ParseInput parseInput) {
        PackageManager.Property property;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestMetaData);
        try {
            String safeIntern = TextUtils.safeIntern(nonConfigString(0, 0, obtainAttributes));
            if (safeIntern == null) {
                return parseInput.error(str + " requires an android:name attribute");
            }
            String packageName = parsingPackage.getPackageName();
            PackageManager.Property property2 = null;
            String name = parsedComponent != null ? parsedComponent.getName() : null;
            TypedValue peekValue = obtainAttributes.peekValue(2);
            if (peekValue != null && peekValue.resourceId != 0) {
                property = new PackageManager.Property(safeIntern, peekValue.resourceId, true, packageName, name);
            } else {
                TypedValue peekValue2 = obtainAttributes.peekValue(1);
                if (peekValue2 != null) {
                    if (peekValue2.type == 3) {
                        CharSequence coerceToString = peekValue2.coerceToString();
                        property2 = new PackageManager.Property(safeIntern, coerceToString != null ? coerceToString.toString() : null, packageName, name);
                    } else if (peekValue2.type == 18) {
                        property2 = new PackageManager.Property(safeIntern, peekValue2.data != 0, packageName, name);
                    } else if (peekValue2.type >= 16 && peekValue2.type <= 31) {
                        property = new PackageManager.Property(safeIntern, peekValue2.data, false, packageName, name);
                    } else if (peekValue2.type == 4) {
                        property2 = new PackageManager.Property(safeIntern, peekValue2.getFloat(), packageName, name);
                    } else {
                        Slog.w("PackageParsing", str + " only supports string, integer, float, color, boolean, and resource reference types: " + xmlResourceParser.getName() + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                    }
                    return parseInput.success(property2);
                }
                return parseInput.error(str + " requires an android:value or android:resource attribute");
            }
            property2 = property;
            return parseInput.success(property2);
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static ParseResult<SigningDetails> getSigningDetails(ParseInput parseInput, ParsedPackage parsedPackage, boolean z) {
        return getSigningDetails(parseInput, parsedPackage.getBaseApkPath(), parsedPackage.isStaticSharedLibrary(), parsedPackage.getTargetSdkVersion(), parsedPackage.getSplitCodePaths(), z);
    }

    private static ParseResult<SigningDetails> getSigningDetails(ParseInput parseInput, ParsingPackage parsingPackage, boolean z) {
        return getSigningDetails(parseInput, parsingPackage.getBaseApkPath(), parsingPackage.isStaticSharedLibrary(), parsingPackage.getTargetSdkVersion(), parsingPackage.getSplitCodePaths(), z);
    }

    public static ParseResult<SigningDetails> getSigningDetails(ParseInput parseInput, String str, boolean z, int i, String[] strArr, boolean z2) {
        SigningDetails signingDetails = SigningDetails.UNKNOWN;
        Trace.traceBegin(262144L, "collectCertificates");
        try {
            ParseResult<SigningDetails> signingDetails2 = getSigningDetails(parseInput, str, z2, z, signingDetails, i);
            if (signingDetails2.isError()) {
                return parseInput.error(signingDetails2);
            }
            SigningDetails result = signingDetails2.getResult();
            boolean equals = new File(Environment.getRootDirectory(), "framework/framework-res.apk").getAbsolutePath().equals(str);
            if (!ArrayUtils.isEmpty(strArr) && !equals) {
                for (String str2 : strArr) {
                    signingDetails2 = getSigningDetails(parseInput, str2, z2, z, result, i);
                    if (signingDetails2.isError()) {
                        return parseInput.error(signingDetails2);
                    }
                }
            }
            return signingDetails2;
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<SigningDetails> getSigningDetails(ParseInput parseInput, String str, boolean z, boolean z2, SigningDetails signingDetails, int i) {
        ParseResult<SigningDetails> verify;
        int minimumSignatureSchemeVersionForTargetSdk = ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(i);
        if (z2) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        if (z) {
            verify = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        } else {
            verify = ApkSignatureVerifier.verify(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (verify.isError()) {
            return parseInput.error(verify);
        }
        if (signingDetails == SigningDetails.UNKNOWN) {
            return verify;
        }
        if (!Signature.areExactMatch(signingDetails, verify.getResult())) {
            return parseInput.error(-104, str + " has mismatched certificates");
        }
        return parseInput.success(signingDetails);
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

    private static boolean bool(boolean z, int i, TypedArray typedArray) {
        return typedArray.getBoolean(i, z);
    }

    private static float aFloat(float f, int i, TypedArray typedArray) {
        return typedArray.getFloat(i, f);
    }

    private static float aFloat(int i, TypedArray typedArray) {
        return typedArray.getFloat(i, 0.0f);
    }

    private static int anInt(int i, int i2, TypedArray typedArray) {
        return typedArray.getInt(i2, i);
    }

    private static int anInteger(int i, int i2, TypedArray typedArray) {
        return typedArray.getInteger(i2, i);
    }

    private static int anInt(int i, TypedArray typedArray) {
        return typedArray.getInt(i, 0);
    }

    private static int resId(int i, TypedArray typedArray) {
        return typedArray.getResourceId(i, 0);
    }

    private static ParseResult<int[]> drawableResIdArray(ParseInput parseInput, TypedArray typedArray, Resources resources, int i, int i2) {
        return resIdArray(parseInput, typedArray, resources, i, TYPE_DRAWABLE, i2);
    }

    private static ParseResult<int[]> stringResIdArray(ParseInput parseInput, TypedArray typedArray, Resources resources, int i, int i2) {
        return resIdArray(parseInput, typedArray, resources, i, TYPE_STRING, i2);
    }

    private static ParseResult<int[]> resIdArray(ParseInput parseInput, TypedArray typedArray, Resources resources, int i, String str, int i2) {
        if (!typedArray.hasValue(i)) {
            return parseInput.success(null);
        }
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId == 0) {
            return parseInput.success(null);
        }
        TypedArray obtainTypedArray = resources.obtainTypedArray(resourceId);
        try {
            String resourceName = resources.getResourceName(resourceId);
            int length = obtainTypedArray.length();
            if (i2 > 0 && length > i2) {
                ParseResult<int[]> error = parseInput.error(TextUtils.formatSimple("The length of the typedArray (%s) is larger than %d.", resourceName, Integer.valueOf(i2)));
                if (obtainTypedArray != null) {
                    obtainTypedArray.close();
                }
                return error;
            }
            ArraySet arraySet = new ArraySet();
            for (int i3 = 0; i3 < length; i3++) {
                int resourceId2 = obtainTypedArray.getResourceId(i3, 0);
                if (resourceId2 == 0) {
                    ParseResult<int[]> error2 = parseInput.error(TextUtils.formatSimple("There is an item that is not a resource id in the typedArray (%s).", resourceName));
                    if (obtainTypedArray != null) {
                        obtainTypedArray.close();
                    }
                    return error2;
                }
                try {
                    if (arraySet.contains(Integer.valueOf(resourceId2))) {
                        ParseResult<int[]> error3 = parseInput.error(TextUtils.formatSimple("There is a duplicated resource (%s) in the typedArray (%s).", resources.getResourceName(resourceId2), resourceName));
                        if (obtainTypedArray != null) {
                            obtainTypedArray.close();
                        }
                        return error3;
                    }
                    String resourceTypeName = resources.getResourceTypeName(resourceId2);
                    if (str != null && !TextUtils.equals(resourceTypeName, str)) {
                        ParseResult<int[]> error4 = parseInput.error(TextUtils.formatSimple("There is a resource (%s) in the typedArray (%s) that is not a %s type.", resources.getResourceName(resourceId2), resourceName, str));
                        if (obtainTypedArray != null) {
                            obtainTypedArray.close();
                        }
                        return error4;
                    }
                    arraySet.add(Integer.valueOf(resourceId2));
                } catch (Resources.NotFoundException unused) {
                    ParseResult<int[]> error5 = parseInput.error(TextUtils.formatSimple("There is a resource in the typedArray (%s) that is not found in the app resources.", resourceName));
                    if (obtainTypedArray != null) {
                        obtainTypedArray.close();
                    }
                    return error5;
                }
            }
            if (arraySet.isEmpty()) {
                ParseResult<int[]> success = parseInput.success(null);
                if (obtainTypedArray != null) {
                    obtainTypedArray.close();
                }
                return success;
            }
            ParseResult<int[]> success2 = parseInput.success(arraySet.stream().mapToInt(new ToIntFunction() { // from class: com.android.internal.pm.pkg.parsing.ParsingPackageUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    int intValue;
                    intValue = ((Integer) obj).intValue();
                    return intValue;
                }
            }).toArray());
            if (obtainTypedArray != null) {
                obtainTypedArray.close();
            }
            return success2;
        } catch (Throwable th) {
            if (obtainTypedArray != null) {
                try {
                    obtainTypedArray.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static String string(int i, TypedArray typedArray) {
        return typedArray.getString(i);
    }

    private static String nonConfigString(int i, int i2, TypedArray typedArray) {
        return typedArray.getNonConfigurationString(i2, i);
    }

    private static String nonResString(int i, TypedArray typedArray) {
        return typedArray.getNonResourceString(i);
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

    public static AconfigFlags getAconfigFlags() {
        return sAconfigFlags;
    }
}
