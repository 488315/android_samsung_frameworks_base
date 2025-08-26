package com.android.internal.pm.pkg.parsing;

import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.content.Intent;
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
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
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
import com.android.internal.os.ClassLoaderFactory;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.permission.CompatibilityPermissionInfo;
import com.android.internal.pm.pkg.component.AconfigFlags;
import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ComponentParseUtils;
import com.android.internal.pm.pkg.component.InstallConstraintsTagParser;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedActivityImpl;
import com.android.internal.pm.pkg.component.ParsedActivityUtils;
import com.android.internal.pm.pkg.component.ParsedApexSystemService;
import com.android.internal.pm.pkg.component.ParsedApexSystemServiceUtils;
import com.android.internal.pm.pkg.component.ParsedAttribution;
import com.android.internal.pm.pkg.component.ParsedAttributionUtils;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedInstrumentationUtils;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedIntentInfoImpl;
import com.android.internal.pm.pkg.component.ParsedIntentInfoUtils;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.pm.pkg.component.ParsedPermissionUtils;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.pm.pkg.component.ParsedProcessUtils;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedProviderImpl;
import com.android.internal.pm.pkg.component.ParsedProviderUtils;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.pm.pkg.component.ParsedServiceUtils;
import com.android.internal.pm.split.DefaultSplitAssetLoader;
import com.android.internal.pm.split.SplitAssetDependencyLoader;
import com.android.internal.pm.split.SplitAssetLoader;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.samsung.android.core.pm.runtimemanifest.LegacyRuntimeManifestParseUtils;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestOverlayUtils;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
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
import java.util.StringTokenizer;
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
        ParseResult<ParsingPackage> parseResult = new ParsingPackageUtils(null, null, list, callback).parsePackage(parseInput, file, i);
        if (parseResult.isError()) {
            return parseInput.error(parseResult);
        }
        ParsedPackage parsedPackageHideAsParsed = parseResult.getResult().hideAsParsed();
        if (z) {
            ParseResult<SigningDetails> signingDetails = getSigningDetails(parseInput, parsedPackageHideAsParsed, false);
            if (signingDetails.isError()) {
                return parseInput.error(signingDetails);
            }
            parsedPackageHideAsParsed.setSigningDetails(signingDetails.getResult());
        }
        return parseInput.success(parsedPackageHideAsParsed);
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

    private ParseResult<ParsingPackage> parseClusterPackage(ParseInput parseInput, File file, int i) throws Throwable {
        SplitAssetLoader defaultSplitAssetLoader;
        SparseArray<int[]> sparseArrayCreateDependenciesFromPackage;
        SplitAssetLoader splitAssetLoader;
        int i2 = (i & 512) != 0 ? 512 : 0;
        if ((i & 1024) != 0) {
            i2 |= 1024;
        }
        ParseResult<?> clusterPackageLite = ApkLiteParseUtils.parseClusterPackageLite(parseInput, file, i2);
        if (clusterPackageLite.isError()) {
            return parseInput.error(clusterPackageLite);
        }
        PackageLite result = clusterPackageLite.getResult();
        if (!result.isIsolatedSplits() || ArrayUtils.isEmpty(result.getSplitNames())) {
            defaultSplitAssetLoader = new DefaultSplitAssetLoader(result, i);
            sparseArrayCreateDependenciesFromPackage = null;
        } else {
            try {
                sparseArrayCreateDependenciesFromPackage = SplitAssetDependencyLoader.createDependenciesFromPackage(result);
                defaultSplitAssetLoader = new SplitAssetDependencyLoader(result, sparseArrayCreateDependenciesFromPackage, i);
            } catch (SplitDependencyLoader.IllegalDependencyException e) {
                return parseInput.error(-101, e.getMessage());
            }
        }
        SparseArray<int[]> sparseArray = sparseArrayCreateDependenciesFromPackage;
        SplitAssetLoader splitAssetLoader2 = defaultSplitAssetLoader;
        try {
            try {
                ParseResult<?> baseApk = parseBaseApk(parseInput, new File(result.getBaseApkPath()), result.getPath(), splitAssetLoader2, i, result.isIsSdkLibrary() && Flags.disallowSdkLibsToBeApps());
                splitAssetLoader = splitAssetLoader2;
                try {
                    if (baseApk.isError()) {
                        ParseResult<ParsingPackage> parseResultError = parseInput.error(baseApk);
                        IoUtils.closeQuietly(splitAssetLoader);
                        return parseResultError;
                    }
                    ParsingPackage result2 = baseApk.getResult();
                    if (!ArrayUtils.isEmpty(result.getSplitNames())) {
                        result2.asSplit(result.getSplitNames(), result.getSplitApkPaths(), result.getSplitRevisionCodes(), sparseArray);
                        int length = result.getSplitNames().length;
                        for (int i3 = 0; i3 < length; i3++) {
                            ParseResult<?> splitApk = parseSplitApk(parseInput, result2, i3, splitAssetLoader.getSplitAssetManager(i3), i);
                            if (splitApk.isError()) {
                                ParseResult<ParsingPackage> parseResultError2 = parseInput.error(splitApk);
                                IoUtils.closeQuietly(splitAssetLoader);
                                return parseResultError2;
                            }
                        }
                    }
                    result2.set32BitAbiPreferred(result.isUse32bitAbi());
                    ParseResult<ParsingPackage> parseResultSuccess = parseInput.success(result2);
                    IoUtils.closeQuietly(splitAssetLoader);
                    return parseResultSuccess;
                } catch (IllegalArgumentException e2) {
                    e = e2;
                    ParseResult<ParsingPackage> parseResultError3 = parseInput.error(e.getCause() instanceof IOException ? -2 : -100, e.getMessage(), e);
                    IoUtils.closeQuietly(splitAssetLoader);
                    return parseResultError3;
                }
            } catch (Throwable th) {
                th = th;
                IoUtils.closeQuietly(splitAssetLoader2);
                throw th;
            }
        } catch (IllegalArgumentException e3) {
            e = e3;
            splitAssetLoader = splitAssetLoader2;
        } catch (Throwable th2) {
            th = th2;
            IoUtils.closeQuietly(splitAssetLoader2);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ParseResult<ParsingPackage> parseMonolithicPackage(ParseInput parseInput, File file, int i) {
        ParseInput parseInput2;
        File file2;
        IOException iOException;
        ParseResult<PackageLite> monolithicPackageLite = ApkLiteParseUtils.parseMonolithicPackageLite(parseInput, file, i & (-33));
        if (monolithicPackageLite.isError()) {
            return parseInput.error(monolithicPackageLite);
        }
        PackageLite result = monolithicPackageLite.getResult();
        DefaultSplitAssetLoader defaultSplitAssetLoader = new DefaultSplitAssetLoader(result, i);
        try {
            try {
                if (result.isIsSdkLibrary()) {
                    try {
                        boolean z = Flags.disallowSdkLibsToBeApps();
                        parseInput2 = parseInput;
                        file2 = file;
                        try {
                            ParseResult<ParsingPackage> baseApk = parseBaseApk(parseInput2, file2, file.getCanonicalPath(), defaultSplitAssetLoader, i, z);
                            if (baseApk.isError()) {
                                ParseResult<ParsingPackage> parseResultError = parseInput2.error(baseApk);
                                IoUtils.closeQuietly(defaultSplitAssetLoader);
                                return parseResultError;
                            }
                            ParseResult<ParsingPackage> parseResultSuccess = parseInput2.success(baseApk.getResult().set32BitAbiPreferred(result.isUse32bitAbi()));
                            IoUtils.closeQuietly(defaultSplitAssetLoader);
                            return parseResultSuccess;
                        } catch (IOException e) {
                            e = e;
                            iOException = e;
                            ParseResult<ParsingPackage> parseResultError2 = parseInput2.error(-102, "Failed to get path: " + file2, iOException);
                            IoUtils.closeQuietly(defaultSplitAssetLoader);
                            return parseResultError2;
                        }
                    } catch (IOException e2) {
                        iOException = e2;
                        parseInput2 = parseInput;
                        file2 = file;
                    }
                }
            } catch (IOException e3) {
                e = e3;
                parseInput2 = parseInput;
                file2 = file;
            }
            ParseResult<ParsingPackage> parseResultError22 = parseInput2.error(-102, "Failed to get path: " + file2, iOException);
            IoUtils.closeQuietly(defaultSplitAssetLoader);
            return parseResultError22;
        } catch (Throwable th) {
            IoUtils.closeQuietly(defaultSplitAssetLoader);
            throw th;
        }
    }

    public ParseResult<ParsingPackage> parsePackageFromPackageLite(ParseInput parseInput, PackageLite packageLite, int i) {
        String volumeUuid = getVolumeUuid(packageLite.getPath());
        String packageName = packageLite.getPackageName();
        ParsingPackage parsingPackageStartParsingPackage = this.mCallback.startParsingPackage(packageName, packageLite.getBaseApkPath(), packageLite.getPath(), null, packageLite.isCoreApp());
        int targetSdk = packageLite.getTargetSdk();
        parsingPackageStartParsingPackage.setVersionCode(packageLite.getVersionCode());
        parsingPackageStartParsingPackage.setVersionCodeMajor(packageLite.getVersionCodeMajor());
        parsingPackageStartParsingPackage.setBaseRevisionCode(packageLite.getBaseRevisionCode());
        parsingPackageStartParsingPackage.setVersionName(null);
        parsingPackageStartParsingPackage.setCompileSdkVersion(0);
        parsingPackageStartParsingPackage.setCompileSdkVersionCodeName(null);
        parsingPackageStartParsingPackage.setIsolatedSplitLoading(false);
        parsingPackageStartParsingPackage.setTargetSdkVersion(targetSdk);
        parsingPackageStartParsingPackage.setInstallLocation(packageLite.getInstallLocation()).setTargetSandboxVersion(1).setExternalStorage((i & 8) != 0);
        ArchivedPackageParcel archivedPackage = packageLite.getArchivedPackage();
        if (archivedPackage == null) {
            return parseInput.error(-102, "archivePackage is missing");
        }
        parsingPackageStartParsingPackage.setBackupAllowed(true).setClearUserDataAllowed(true).setClearUserDataOnFailedRestoreAllowed(true).setAllowNativeHeapPointerTagging(true).setEnabled(true).setExtractNativeLibrariesRequested(true).setAllowAudioPlaybackCapture(targetSdk >= 29).setHardwareAccelerated(targetSdk >= 14).setRequestLegacyExternalStorage(XmlUtils.convertValueToBoolean(archivedPackage.requestLegacyExternalStorage, targetSdk < 29)).setCleartextTrafficAllowed(targetSdk < 28).setDefaultToDeviceProtectedStorage(XmlUtils.convertValueToBoolean(archivedPackage.defaultToDeviceProtectedStorage, false)).setUserDataFragile(XmlUtils.convertValueToBoolean(archivedPackage.userDataFragile, false)).setCategory(-1).setMaxAspectRatio(0.0f).setMinAspectRatio(0.0f);
        parsingPackageStartParsingPackage.setDeclaredHavingCode(false);
        ParseResult<String> parseResultBuildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, packageName, null, parseInput);
        if (parseResultBuildTaskAffinityName.isError()) {
            return parseInput.error(parseResultBuildTaskAffinityName);
        }
        parsingPackageStartParsingPackage.setTaskAffinity(parseResultBuildTaskAffinityName.getResult());
        ParseResult<String> parseResultBuildProcessName = ComponentParseUtils.buildProcessName(packageName, null, null, i, this.mSeparateProcesses, parseInput);
        if (parseResultBuildProcessName.isError()) {
            return parseInput.error(parseResultBuildProcessName);
        }
        parsingPackageStartParsingPackage.setProcessName(parseResultBuildProcessName.getResult());
        parsingPackageStartParsingPackage.setGwpAsanMode(-1);
        parsingPackageStartParsingPackage.setMemtagMode(-1);
        parsingPackageStartParsingPackage.setPageSizeAppCompatFlags(0);
        afterParseBaseApplication(parsingPackageStartParsingPackage);
        ParseResult<ParsingPackage> parseResultValidateBaseApkTags = validateBaseApkTags(parseInput, parsingPackageStartParsingPackage, i);
        if (parseResultValidateBaseApkTags.isError()) {
            return parseResultValidateBaseApkTags;
        }
        parsingPackageStartParsingPackage.setVolumeUuid(volumeUuid);
        if ((i & 32) != 0) {
            parsingPackageStartParsingPackage.setSigningDetails(packageLite.getSigningDetails());
        } else {
            parsingPackageStartParsingPackage.setSigningDetails(SigningDetails.UNKNOWN);
        }
        return parseInput.success(parsingPackageStartParsingPackage.set32BitAbiPreferred(packageLite.isUse32bitAbi()));
    }

    private static String getVolumeUuid(String str) {
        if (str.startsWith("/mnt/expand/")) {
            return str.substring(12, str.indexOf(47, 12));
        }
        return null;
    }

    private ParseResult<ParsingPackage> parseBaseApk(ParseInput parseInput, File file, String str, SplitAssetLoader splitAssetLoader, int i, boolean z) throws Throwable {
        ParseInput parseInput2;
        boolean zDefinesOverlayable;
        ParseResult<ParsingPackage> parseResultError;
        String absolutePath = file.getAbsolutePath();
        String volumeUuid = getVolumeUuid(absolutePath);
        try {
            AssetManager baseAssetManager = splitAssetLoader.getBaseAssetManager();
            int iFindCookieForPath = baseAssetManager.findCookieForPath(absolutePath);
            if (iFindCookieForPath == 0) {
                return parseInput.error(-101, "Failed adding asset path: " + absolutePath);
            }
            try {
                try {
                    XmlResourceParser xmlResourceParserOpenXmlResourceParser = baseAssetManager.openXmlResourceParser(iFindCookieForPath, "AndroidManifest.xml");
                    try {
                        parseInput2 = parseInput;
                    } catch (Throwable th) {
                        th = th;
                        parseInput2 = parseInput;
                    }
                    try {
                        ParseResult<ParsingPackage> baseApk = parseBaseApk(parseInput2, absolutePath, str, new Resources(baseAssetManager, this.mDisplayMetrics, null), xmlResourceParserOpenXmlResourceParser, i, z);
                        if (baseApk.isError()) {
                            parseResultError = parseInput2.error(baseApk.getErrorCode(), absolutePath + " (at " + xmlResourceParserOpenXmlResourceParser.getPositionDescription() + "): " + baseApk.getErrorMessage());
                            if (xmlResourceParserOpenXmlResourceParser != null) {
                            }
                            return parseResultError;
                        }
                        ParsingPackage result = baseApk.getResult();
                        if (baseAssetManager.containsAllocatedTable()) {
                            ParseResult<?> parseResultDeferError = parseInput2.deferError("Targeting R+ (version 30 and above) requires the resources.arsc of installed APKs to be stored uncompressed and aligned on a 4-byte boundary", ParseInput.DeferredError.RESOURCES_ARSC_COMPRESSED);
                            if (parseResultDeferError.isError()) {
                                parseResultError = parseInput2.error(PackageManager.INSTALL_PARSE_FAILED_RESOURCES_ARSC_COMPRESSED, parseResultDeferError.getErrorMessage());
                                if (xmlResourceParserOpenXmlResourceParser == null) {
                                    return parseResultError;
                                }
                            }
                        }
                        try {
                            zDefinesOverlayable = splitAssetLoader.getBaseApkAssets().definesOverlayable();
                        } catch (IOException unused) {
                            zDefinesOverlayable = false;
                        }
                        if (zDefinesOverlayable) {
                            SparseArray<String> assignedPackageIdentifiers = baseAssetManager.getAssignedPackageIdentifiers();
                            int size = assignedPackageIdentifiers.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                Map<String, String> overlayableMap = baseAssetManager.getOverlayableMap(assignedPackageIdentifiers.valueAt(i2));
                                if (overlayableMap != null && !overlayableMap.isEmpty()) {
                                    for (String str2 : overlayableMap.keySet()) {
                                        result.addOverlayable(str2, overlayableMap.get(str2));
                                    }
                                }
                            }
                        }
                        result.setVolumeUuid(volumeUuid);
                        if ((i & 32) != 0) {
                            ParseResult<?> signingDetails = getSigningDetails(parseInput2, result, false);
                            if (signingDetails.isError()) {
                                ParseResult<ParsingPackage> parseResultError2 = parseInput2.error(signingDetails);
                                if (xmlResourceParserOpenXmlResourceParser != null) {
                                    xmlResourceParserOpenXmlResourceParser.close();
                                }
                                return parseResultError2;
                            }
                            result.setSigningDetails(signingDetails.getResult());
                        } else {
                            result.setSigningDetails(SigningDetails.UNKNOWN);
                        }
                        ParseResult<ParsingPackage> parseResultSuccess = parseInput2.success(result);
                        if (xmlResourceParserOpenXmlResourceParser != null) {
                            xmlResourceParserOpenXmlResourceParser.close();
                        }
                        return parseResultSuccess;
                        xmlResourceParserOpenXmlResourceParser.close();
                        return parseResultError;
                    } catch (Throwable th2) {
                        th = th2;
                        Throwable th3 = th;
                        if (xmlResourceParserOpenXmlResourceParser == null) {
                            throw th3;
                        }
                        try {
                            xmlResourceParserOpenXmlResourceParser.close();
                            throw th3;
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                            throw th3;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    return parseInput2.error(-102, "Failed to read manifest from " + absolutePath, e);
                }
            } catch (Exception e2) {
                e = e2;
                parseInput2 = parseInput;
                return parseInput2.error(-102, "Failed to read manifest from " + absolutePath, e);
            }
        } catch (IllegalArgumentException e3) {
            return parseInput.error(e3.getCause() instanceof IOException ? -2 : -100, e3.getMessage(), e3);
        }
    }

    private ParseResult<ParsingPackage> parseSplitApk(ParseInput parseInput, ParsingPackage parsingPackage, int i, AssetManager assetManager, int i2) throws Throwable {
        ParseInput parseInput2;
        String str = parsingPackage.getSplitCodePaths()[i];
        int iFindCookieForPath = assetManager.findCookieForPath(str);
        if (iFindCookieForPath == 0) {
            return parseInput.error(-101, "Failed adding asset path: " + str);
        }
        try {
            try {
                XmlResourceParser xmlResourceParserOpenXmlResourceParser = assetManager.openXmlResourceParser(iFindCookieForPath, "AndroidManifest.xml");
                try {
                    parseInput2 = parseInput;
                    try {
                        ParseResult<ParsingPackage> splitApk = parseSplitApk(parseInput2, parsingPackage, new Resources(assetManager, this.mDisplayMetrics, null), xmlResourceParserOpenXmlResourceParser, i2, i);
                        if (splitApk.isError()) {
                            splitApk = parseInput2.error(splitApk.getErrorCode(), str + " (at " + xmlResourceParserOpenXmlResourceParser.getPositionDescription() + "): " + splitApk.getErrorMessage());
                            if (xmlResourceParserOpenXmlResourceParser != null) {
                            }
                            return splitApk;
                        }
                        if (xmlResourceParserOpenXmlResourceParser == null) {
                            return splitApk;
                        }
                        xmlResourceParserOpenXmlResourceParser.close();
                        return splitApk;
                    } catch (Throwable th) {
                        th = th;
                        Throwable th2 = th;
                        if (xmlResourceParserOpenXmlResourceParser == null) {
                            throw th2;
                        }
                        try {
                            xmlResourceParserOpenXmlResourceParser.close();
                            throw th2;
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                            throw th2;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    parseInput2 = parseInput;
                }
            } catch (Exception e) {
                e = e;
                parseInput2 = parseInput;
                return parseInput2.error(-102, "Failed to read manifest from " + str, e);
            }
        } catch (Exception e2) {
            e = e2;
            return parseInput2.error(-102, "Failed to read manifest from " + str, e);
        }
    }

    private ParseResult<ParsingPackage> parseBaseApk(ParseInput parseInput, String str, String str2, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z) throws Throwable {
        TypedArray typedArray;
        ParseResult<Pair<String, String>> packageSplitNames = ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
        if (packageSplitNames.isError()) {
            return parseInput.error(packageSplitNames);
        }
        Pair<String, String> result = packageSplitNames.getResult();
        String str3 = result.first;
        String str4 = result.second;
        if (!TextUtils.isEmpty(str4)) {
            return parseInput.error(-106, "Expected base APK, but found split " + str4);
        }
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifest);
        try {
            ParsingPackage parsingPackageStartParsingPackage = this.mCallback.startParsingPackage(str3, str, str2, typedArrayObtainAttributes, xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false));
            typedArray = typedArrayObtainAttributes;
            try {
                ParseResult<ParsingPackage> baseApkTags = parseBaseApkTags(parseInput, parsingPackageStartParsingPackage, typedArray, resources, xmlResourceParser, i, z);
                if (!baseApkTags.isError()) {
                    ParseResult<ParsingPackage> parseResultSuccess = parseInput.success(parsingPackageStartParsingPackage);
                    typedArray.recycle();
                    return parseResultSuccess;
                }
                typedArray.recycle();
                return baseApkTags;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                typedArray.recycle();
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            typedArray = typedArrayObtainAttributes;
        }
    }

    private ParseResult<ParsingPackage> parseSplitApk(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, int i2) throws Throwable {
        ParseResult<ParsingPackage> parseResultUnknownTag;
        ParseResult<Pair<String, String>> packageSplitNames = ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
        if (packageSplitNames.isError()) {
            return parseInput.error(packageSplitNames);
        }
        int depth = xmlResourceParser.getDepth();
        boolean z = false;
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1) {
                if (depth + 1 >= xmlResourceParser.getDepth() && next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser)) {
                    if (!"application".equals(xmlResourceParser.getName())) {
                        parseResultUnknownTag = ParsingUtils.unknownTag("<manifest>", parsingPackage, xmlResourceParser, parseInput);
                    } else if (z) {
                        Slog.w("PackageParsing", "<manifest> has more than one <application>");
                        parseResultUnknownTag = parseInput.success(null);
                    } else {
                        parseResultUnknownTag = parseSplitApplication(parseInput, parsingPackage, resources, xmlResourceParser, i, i2);
                        z = true;
                    }
                    if (parseResultUnknownTag.isError()) {
                        return parseInput.error(parseResultUnknownTag);
                    }
                }
            } else {
                if (!z) {
                    ParseResult<?> parseResultDeferError = parseInput.deferError("<manifest> does not contain an <application>", ParseInput.DeferredError.MISSING_APP_TAG);
                    if (parseResultDeferError.isError()) {
                        return parseInput.error(parseResultDeferError);
                    }
                }
                return parseInput.success(parsingPackage);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0153, code lost:
    
        return r13.success(r14);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ParseResult<ParsingPackage> parseSplitApplication(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, int i2) throws Throwable {
        boolean z;
        ParseResult<?> provider;
        ParseResult<?> splitBaseAppChildTags;
        XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestApplication);
        try {
            parsingPackage.setSplitHasCode(i2, typedArrayObtainAttributes.getBoolean(7, true));
            String string = typedArrayObtainAttributes.getString(46);
            if (string != null && !ClassLoaderFactory.isValidClassLoaderName(string)) {
                return parseInput.error("Invalid class loader name: " + string);
            }
            parsingPackage.setSplitClassLoaderName(i2, string);
            typedArrayObtainAttributes.recycle();
            String str = parsingPackage.getSplitNames()[i2];
            int depth = xmlResourceParser2.getDepth();
            while (true) {
                int next = xmlResourceParser2.next();
                if (next != 1) {
                    char c = 3;
                    if (next != 3 || xmlResourceParser2.getDepth() > depth) {
                        if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser2)) {
                            String name = xmlResourceParser2.getName();
                            name.hashCode();
                            switch (name.hashCode()) {
                                case -1655966961:
                                    if (!name.equals("activity")) {
                                        c = 65535;
                                        break;
                                    } else {
                                        c = 0;
                                        break;
                                    }
                                case -987494927:
                                    if (name.equals(RuntimeManifestUtils.TAG_PROVIDER)) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case -808719889:
                                    if (name.equals("receiver")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 790287890:
                                    if (!name.equals("activity-alias")) {
                                    }
                                    break;
                                case 1984153269:
                                    if (name.equals("service")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    z = true;
                                    provider = ParsedActivityUtils.parseActivityOrReceiver(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (provider.isSuccess()) {
                                        ParsedActivity parsedActivity = (ParsedActivity) provider.getResult();
                                        if (z) {
                                            parsingPackage.addActivity(parsedActivity);
                                        } else {
                                            parsingPackage.addReceiver(parsedActivity);
                                        }
                                    }
                                    splitBaseAppChildTags = provider;
                                    break;
                                case 1:
                                    provider = ParsedProviderUtils.parseProvider(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (provider.isSuccess()) {
                                        parsingPackage.addProvider((ParsedProvider) provider.getResult());
                                    }
                                    splitBaseAppChildTags = provider;
                                    break;
                                case 2:
                                    z = false;
                                    provider = ParsedActivityUtils.parseActivityOrReceiver(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (provider.isSuccess()) {
                                    }
                                    splitBaseAppChildTags = provider;
                                    break;
                                case 3:
                                    ParseResult<ParsedActivity> activityAlias = ParsedActivityUtils.parseActivityAlias(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, str, parseInput);
                                    if (activityAlias.isSuccess()) {
                                        parsingPackage.addActivity(activityAlias.getResult());
                                    }
                                    splitBaseAppChildTags = activityAlias;
                                    break;
                                case 4:
                                    provider = ParsedServiceUtils.parseService(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (provider.isSuccess()) {
                                        parsingPackage.addService((ParsedService) provider.getResult());
                                    }
                                    splitBaseAppChildTags = provider;
                                    break;
                                default:
                                    splitBaseAppChildTags = parseSplitBaseAppChildTags(parseInput, name, parsingPackage, resources, xmlResourceParser2);
                                    break;
                            }
                            if (splitBaseAppChildTags.isError()) {
                                return parseInput.error(splitBaseAppChildTags);
                            }
                            xmlResourceParser2 = xmlResourceParser;
                        }
                    }
                }
            }
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private ParseResult parseSplitBaseAppChildTags(ParseInput parseInput, String str, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        str.hashCode();
        switch (str) {
            case "uses-native-library":
                return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-sdk-library":
                return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-library":
                return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "meta-data":
                ParseResult<PackageManager.Property> metaData = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
                if (metaData.isSuccess() && metaData.getResult() != null) {
                    parsingPackage.setMetaData(metaData.getResult().toBundle(parsingPackage.getMetaData()));
                }
                return metaData;
            case "property":
                ParseResult<PackageManager.Property> metaData2 = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
                if (metaData2.isSuccess()) {
                    parsingPackage.addProperty(metaData2.getResult());
                }
                return metaData2;
            case "uses-static-library":
                return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case "uses-package":
                return parseInput.success(null);
            default:
                return ParsingUtils.unknownTag("<application>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c2, code lost:
    
        if (r10 == null) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00cc, code lost:
    
        if (com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.useLegacyRuntimeManifest(r13.getMetaData()) == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ce, code lost:
    
        com.samsung.android.core.pm.runtimemanifest.LegacyRuntimeManifestParseUtils.modifyParsingPackageWithReplacement(r13, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d1, code lost:
    
        if (r9 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00db, code lost:
    
        if (com.android.internal.util.ArrayUtils.size(r13.getInstrumentations()) != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00dd, code lost:
    
        r14 = r12.deferError("<manifest> does not contain an <application> or <instrumentation>", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_APP_TAG);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ea, code lost:
    
        if (r14.isError() == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f0, code lost:
    
        return r12.error(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f5, code lost:
    
        return validateBaseApkTags(r12, r13, r17);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ParseResult<ParsingPackage> parseBaseApkTags(ParseInput parseInput, ParsingPackage parsingPackage, TypedArray typedArray, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z) throws Throwable {
        ParseResult<ParsingPackage> baseApkTag;
        XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        ParseResult<ParsingPackage> sharedUser = parseSharedUser(parseInput, parsingPackage, typedArray);
        if (sharedUser.isError()) {
            return sharedUser;
        }
        parsingPackage.setInstallLocation(anInteger(-1, 4, typedArray)).setTargetSandboxVersion(anInteger(1, 7, typedArray)).setExternalStorage((i & 8) != 0).setUpdatableSystem(xmlResourceParser2.getAttributeBooleanValue(null, "updatableSystem", true)).setEmergencyInstaller(xmlResourceParser2.getAttributeValue(null, "emergencyInstaller"));
        int depth = xmlResourceParser2.getDepth();
        boolean z2 = false;
        LegacyRuntimeManifestParseUtils.ApplicationReplacement applicationReplacement = null;
        while (true) {
            int next = xmlResourceParser2.next();
            if (next == 1 || (next == 3 && xmlResourceParser2.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser2)) {
                String name = xmlResourceParser2.getName();
                if ("application".equals(name)) {
                    if (z2) {
                        Slog.w("PackageParsing", "<manifest> has more than one <application>");
                        baseApkTag = parseInput.success(null);
                    } else {
                        baseApkTag = parseBaseApplication(parseInput, parsingPackage, resources, xmlResourceParser2, i, z);
                        z2 = true;
                    }
                } else if ("application-salescode".equals(name)) {
                    LegacyRuntimeManifestParseUtils.ApplicationReplacement replacementForApplicationSalescode = LegacyRuntimeManifestParseUtils.getReplacementForApplicationSalescode(parseInput, parsingPackage, resources, xmlResourceParser2);
                    if (replacementForApplicationSalescode != null) {
                        applicationReplacement = replacementForApplicationSalescode;
                    }
                    baseApkTag = parseInput.success(parsingPackage);
                } else {
                    baseApkTag = parseBaseApkTag(name, parseInput, parsingPackage, resources, xmlResourceParser2, i);
                }
                if (baseApkTag.isError()) {
                    return parseInput.error(baseApkTag);
                }
                xmlResourceParser2 = xmlResourceParser;
            }
        }
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

    private ParseResult parseBaseApkTag(String str, ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException, IOException {
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
        String strNonConfigString = nonConfigString(0, 0, typedArray);
        if (TextUtils.isEmpty(strNonConfigString)) {
            return parseInput.success(parsingPackage);
        }
        if (!"android".equals(parsingPackage.getPackageName())) {
            ParseResult parseResultValidateName = FrameworkParsingPackageUtils.validateName(parseInput, strNonConfigString, true, true);
            if (parseResultValidateName.isError()) {
                return parseInput.error(-107, "<manifest> specifies bad sharedUserId name \"" + strNonConfigString + "\": " + parseResultValidateName.getErrorMessage());
            }
        }
        int iAnInteger = anInteger(0, 13, typedArray);
        if (iAnInteger != 0 && iAnInteger < Build.VERSION.RESOURCES_SDK_INT) {
            z = true;
        }
        return parseInput.success(parsingPackage.setLeavingSharedUser(z).setSharedUserId(strNonConfigString.intern()).setSharedUserLabelResourceId(resId(3, typedArray)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x0271, code lost:
    
        return r16.success(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0287, code lost:
    
        return r16.error("Package" + r2 + " AndroidManifest.xml does not define all 'upgrade-key-set's .");
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ae, code lost:
    
        r2 = r17.getPackageName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01c0, code lost:
    
        if (r5.keySet().removeAll(r7.keySet()) == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01d7, code lost:
    
        return r16.error("Package" + r2 + " AndroidManifest.xml 'key-set' and 'public-key' names must be distinct.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01d8, code lost:
    
        r3 = r7.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01e4, code lost:
    
        if (r3.hasNext() == false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01e6, code lost:
    
        r7 = (java.util.Map.Entry) r3.next();
        r9 = (java.lang.String) r7.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01fe, code lost:
    
        if (((android.util.ArraySet) r7.getValue()).size() != 0) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0200, code lost:
    
        android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x021f, code lost:
    
        if (r8.contains(r9) == false) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0221, code lost:
    
        android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x023c, code lost:
    
        r7 = ((android.util.ArraySet) r7.getValue()).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x024a, code lost:
    
        if (r7.hasNext() == false) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x024c, code lost:
    
        r17.addKeySet(r9, (java.security.PublicKey) r5.get((java.lang.String) r7.next()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0268, code lost:
    
        if (r17.getKeySetMapping().keySet().containsAll(r6) == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x026a, code lost:
    
        r17.setUpgradeKeySets(r6);
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ParsingPackage> parseKeySets(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        ArrayMap arrayMap = new ArrayMap();
        ArraySet arraySet = new ArraySet();
        ArrayMap arrayMap2 = new ArrayMap();
        ArraySet arraySet2 = new ArraySet();
        while (true) {
            int depth2 = -1;
            String nonResourceString = null;
            while (true) {
                int next = xmlResourceParser.next();
                if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                    if (next != 3) {
                        String name = xmlResourceParser.getName();
                        name.hashCode();
                        switch (name) {
                            case "upgrade-key-set":
                                try {
                                    arraySet.add(resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUpgradeKeySet).getNonResourceString(0));
                                    XmlUtils.skipCurrentTag(xmlResourceParser);
                                    break;
                                } finally {
                                }
                            case "key-set":
                                if (nonResourceString != null) {
                                    return parseInput.error("Improperly nested 'key-set' tag at " + xmlResourceParser.getPositionDescription());
                                }
                                try {
                                    nonResourceString = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestKeySet).getNonResourceString(0);
                                    arrayMap2.put(nonResourceString, new ArraySet());
                                    depth2 = xmlResourceParser.getDepth();
                                    break;
                                } finally {
                                }
                            case "public-key":
                                if (nonResourceString == null) {
                                    return parseInput.error("Improperly nested 'key-set' tag at " + xmlResourceParser.getPositionDescription());
                                }
                                TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPublicKey);
                                try {
                                    String strNonResString = nonResString(0, typedArrayObtainAttributes);
                                    String strNonResString2 = nonResString(1, typedArrayObtainAttributes);
                                    if (strNonResString2 == null && arrayMap.get(strNonResString) == null) {
                                        return parseInput.error("'public-key' " + strNonResString + " must define a public-key value on first use at " + xmlResourceParser.getPositionDescription());
                                    }
                                    if (strNonResString2 == null) {
                                        ((ArraySet) arrayMap2.get(nonResourceString)).add(strNonResString);
                                        XmlUtils.skipCurrentTag(xmlResourceParser);
                                        break;
                                    } else {
                                        PublicKey publicKey = FrameworkParsingPackageUtils.parsePublicKey(strNonResString2);
                                        if (publicKey == null) {
                                            Slog.w("PackageParsing", "No recognized valid key in 'public-key' tag at " + xmlResourceParser.getPositionDescription() + " key-set " + nonResourceString + " will not be added to the package's defined key-sets.");
                                            arraySet2.add(nonResourceString);
                                            XmlUtils.skipCurrentTag(xmlResourceParser);
                                        } else {
                                            if (arrayMap.get(strNonResString) != null && !((PublicKey) arrayMap.get(strNonResString)).equals(publicKey)) {
                                                return parseInput.error("Value of 'public-key' " + strNonResString + " conflicts with previously defined value at " + xmlResourceParser.getPositionDescription());
                                            }
                                            arrayMap.put(strNonResString, publicKey);
                                            ((ArraySet) arrayMap2.get(nonResourceString)).add(strNonResString);
                                            XmlUtils.skipCurrentTag(xmlResourceParser);
                                        }
                                    }
                                } finally {
                                }
                                break;
                            default:
                                ParseResult<?> parseResultUnknownTag = ParsingUtils.unknownTag("<key-sets>", parsingPackage, xmlResourceParser, parseInput);
                                if (!parseResultUnknownTag.isError()) {
                                    break;
                                } else {
                                    return parseInput.error(parseResultUnknownTag);
                                }
                        }
                    } else if (xmlResourceParser.getDepth() == depth2) {
                        break;
                    }
                }
            }
        }
    }

    private static ParseResult<ParsingPackage> parseAttribution(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedAttribution> attribution = ParsedAttributionUtils.parseAttribution(resources, xmlResourceParser, parseInput);
        if (attribution.isError()) {
            return parseInput.error(attribution);
        }
        return parseInput.success(parsingPackage.addAttribution(attribution.getResult()));
    }

    private static ParseResult<ParsingPackage> parsePermissionGroup(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedPermissionGroup> permissionGroup = ParsedPermissionUtils.parsePermissionGroup(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (permissionGroup.isError()) {
            return parseInput.error(permissionGroup);
        }
        return parseInput.success(parsingPackage.addPermissionGroup(permissionGroup.getResult()));
    }

    private static ParseResult<ParsingPackage> parsePermission(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException, IOException {
        ParseResult<ParsedPermission> permission = ParsedPermissionUtils.parsePermission(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput, i);
        if (permission.isError()) {
            return parseInput.error(permission);
        }
        ParsedPermission result = permission.getResult();
        if (result != null) {
            parsingPackage.addPermission(result);
        }
        return parseInput.success(parsingPackage);
    }

    private static ParseResult<ParsingPackage> parsePermissionTree(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedPermission> permissionTree = ParsedPermissionUtils.parsePermissionTree(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (permissionTree.isError()) {
            return parseInput.error(permissionTree);
        }
        return parseInput.success(parsingPackage.addPermission(permissionTree.getResult()));
    }

    private int parseMinOrMaxSdkVersion(TypedArray typedArray, int i, int i2) {
        TypedValue typedValuePeekValue = typedArray.peekValue(i);
        return (typedValuePeekValue == null || typedValuePeekValue.type < 16 || typedValuePeekValue.type > 31) ? i2 : typedValuePeekValue.data;
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00d2, code lost:
    
        r3 = r18.success(r19);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00d6, code lost:
    
        if (r7 != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00db, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00de, code lost:
    
        if (android.os.Build.VERSION.RESOURCES_SDK_INT < r8) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e2, code lost:
    
        if (android.os.Build.VERSION.RESOURCES_SDK_INT <= r10) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e8, code lost:
    
        if (r17.mCallback == null) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ea, code lost:
    
        r6 = r11.size() - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00f2, code lost:
    
        if (r6 < 0) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0100, code lost:
    
        if (r17.mCallback.hasFeature((java.lang.String) r11.valueAt(r6)) != false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0105, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0106, code lost:
    
        r6 = r6 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0109, code lost:
    
        r6 = r13.size() - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0111, code lost:
    
        if (r6 < 0) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x011f, code lost:
    
        if (r17.mCallback.hasFeature((java.lang.String) r13.valueAt(r6)) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0124, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0125, code lost:
    
        r6 = r6 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0128, code lost:
    
        r0 = r19.getUsesPermissions();
        r6 = r0.size();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0131, code lost:
    
        if (r8 >= r6) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0133, code lost:
    
        r9 = r0.get(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0141, code lost:
    
        if (java.util.Objects.equals(r9.getName(), r7) == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x014b, code lost:
    
        if (r9.getUsesPermissionFlags() == r15) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0179, code lost:
    
        return r18.error("Conflicting uses-permissions flags: " + r7 + " in package: " + r19.getPackageName() + " at: " + r21.getPositionDescription());
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x017a, code lost:
    
        android.util.Slog.w("PackageParsing", "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + r7 + " in package: " + r19.getPackageName() + " at: " + r21.getPositionDescription());
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01a5, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01a8, code lost:
    
        r19.addUsesPermission(new com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl(r7, r15));
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01b3, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01b7, code lost:
    
        return r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00aa A[Catch: all -> 0x01b8, TryCatch #0 {all -> 0x01b8, blocks: (B:3:0x0011, B:5:0x0030, B:6:0x0033, B:8:0x003f, B:9:0x0042, B:10:0x004b, B:13:0x0053, B:19:0x0060, B:34:0x008f, B:41:0x00bd, B:43:0x00c3, B:35:0x0096, B:37:0x00a0, B:38:0x00aa, B:40:0x00b4, B:24:0x0073, B:27:0x007e, B:48:0x00d2, B:52:0x00dc, B:54:0x00e0, B:57:0x00e6, B:59:0x00ea, B:61:0x00f4, B:66:0x0109, B:68:0x0113, B:73:0x0128, B:75:0x0133, B:77:0x0143, B:80:0x014d, B:83:0x017a, B:84:0x01a5, B:85:0x01a8), top: B:93:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x00c3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ParseResult<ParsingPackage> parseUsesPermission(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        char c;
        ParseResult<String> requiredFeature;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesPermission);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            int i = 1;
            int minOrMaxSdkVersion = parseMinOrMaxSdkVersion(typedArrayObtainAttributes, 1, Integer.MIN_VALUE);
            int minOrMaxSdkVersion2 = parseMinOrMaxSdkVersion(typedArrayObtainAttributes, 2, Integer.MAX_VALUE);
            ArraySet arraySet = new ArraySet();
            int i2 = 3;
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString != null) {
                arraySet.add(nonConfigurationString);
            }
            ArraySet arraySet2 = new ArraySet();
            String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString2 != null) {
                arraySet2.add(nonConfigurationString2);
            }
            int i3 = typedArrayObtainAttributes.getInt(5, 0);
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next == i || (next == i2 && xmlResourceParser.getDepth() <= depth)) {
                    break;
                }
                if (next == i2 || next == 4) {
                    i = 1;
                } else {
                    String name = xmlResourceParser.getName();
                    int iHashCode = name.hashCode();
                    if (iHashCode != 874138830) {
                        c = (iHashCode == 1693350600 && name.equals("required-feature")) ? (char) 0 : (char) 65535;
                        if (c != 0) {
                            requiredFeature = parseRequiredFeature(parseInput, resources, xmlResourceParser);
                            if (requiredFeature.isSuccess()) {
                                arraySet.add(requiredFeature.getResult());
                            }
                        } else if (c != 1) {
                            requiredFeature = ParsingUtils.unknownTag("<uses-permission>", parsingPackage, xmlResourceParser, parseInput);
                        } else {
                            requiredFeature = parseRequiredNotFeature(parseInput, resources, xmlResourceParser);
                            if (requiredFeature.isSuccess()) {
                                arraySet2.add(requiredFeature.getResult());
                            }
                        }
                        if (!requiredFeature.isError()) {
                            return parseInput.error(requiredFeature);
                        }
                        i = 1;
                        i2 = 3;
                    } else {
                        if (name.equals("required-not-feature")) {
                            c = 1;
                        }
                        if (c != 0) {
                        }
                        if (!requiredFeature.isError()) {
                        }
                    }
                }
            }
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private ParseResult<String> parseRequiredFeature(ParseInput parseInput, Resources resources, AttributeSet attributeSet) {
        ParseResult<String> parseResultSuccess;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestRequiredFeature);
        try {
            String string = typedArrayObtainAttributes.getString(0);
            if (TextUtils.isEmpty(string)) {
                parseResultSuccess = parseInput.error("Feature name is missing from <required-feature> tag.");
            } else {
                parseResultSuccess = parseInput.success(string);
            }
            return parseResultSuccess;
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private ParseResult<String> parseRequiredNotFeature(ParseInput parseInput, Resources resources, AttributeSet attributeSet) {
        ParseResult<String> parseResultSuccess;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestRequiredNotFeature);
        try {
            String string = typedArrayObtainAttributes.getString(0);
            if (TextUtils.isEmpty(string)) {
                parseResultSuccess = parseInput.error("Feature name is missing from <required-not-feature> tag.");
            } else {
                parseResultSuccess = parseInput.success(string);
            }
            return parseResultSuccess;
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesConfiguration(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        ConfigurationInfo configurationInfo = new ConfigurationInfo();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesConfiguration);
        try {
            configurationInfo.reqTouchScreen = typedArrayObtainAttributes.getInt(0, 0);
            configurationInfo.reqKeyboardType = typedArrayObtainAttributes.getInt(1, 0);
            if (typedArrayObtainAttributes.getBoolean(2, false)) {
                configurationInfo.reqInputFeatures = 1 | configurationInfo.reqInputFeatures;
            }
            configurationInfo.reqNavigation = typedArrayObtainAttributes.getInt(3, 0);
            if (typedArrayObtainAttributes.getBoolean(4, false)) {
                configurationInfo.reqInputFeatures |= 2;
            }
            parsingPackage.addConfigPreference(configurationInfo);
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesFeature(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        FeatureInfo featureInfo = parseFeatureInfo(resources, xmlResourceParser);
        parsingPackage.addReqFeature(featureInfo);
        if (featureInfo.name == null) {
            ConfigurationInfo configurationInfo = new ConfigurationInfo();
            configurationInfo.reqGlEsVersion = featureInfo.reqGlEsVersion;
            parsingPackage.addConfigPreference(configurationInfo);
        }
        return parseInput.success(parsingPackage);
    }

    private static FeatureInfo parseFeatureInfo(Resources resources, AttributeSet attributeSet) {
        FeatureInfo featureInfo = new FeatureInfo();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestUsesFeature);
        try {
            featureInfo.name = typedArrayObtainAttributes.getNonResourceString(0);
            featureInfo.version = typedArrayObtainAttributes.getInt(3, 0);
            if (featureInfo.name == null) {
                featureInfo.reqGlEsVersion = typedArrayObtainAttributes.getInt(1, 0);
            }
            if (typedArrayObtainAttributes.getBoolean(2, true)) {
                featureInfo.flags |= 1;
            }
            return featureInfo;
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseFeatureGroup(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        FeatureGroupInfo featureGroupInfo = new FeatureGroupInfo();
        int depth = xmlResourceParser.getDepth();
        ArrayList arrayListAdd = null;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser)) {
                String name = xmlResourceParser.getName();
                if (name.equals("uses-feature")) {
                    FeatureInfo featureInfo = parseFeatureInfo(resources, xmlResourceParser);
                    featureInfo.flags = 1 | featureInfo.flags;
                    arrayListAdd = ArrayUtils.add((ArrayList<FeatureInfo>) arrayListAdd, featureInfo);
                } else {
                    Slog.w("PackageParsing", "Unknown element under <feature-group>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                }
            }
        }
        if (arrayListAdd != null) {
            featureGroupInfo.features = new FeatureInfo[arrayListAdd.size()];
            featureGroupInfo.features = (FeatureInfo[]) arrayListAdd.toArray(featureGroupInfo.features);
        }
        parsingPackage.addFeatureGroup(featureGroupInfo);
        return parseInput.success(parsingPackage);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0049 A[Catch: all -> 0x0141, TryCatch #0 {all -> 0x0141, blocks: (B:9:0x001d, B:11:0x0025, B:13:0x0029, B:15:0x002d, B:19:0x0043, B:21:0x0049, B:23:0x004d, B:25:0x0051, B:32:0x006a, B:34:0x0071, B:36:0x0077, B:38:0x0083, B:41:0x008b, B:43:0x00a3, B:46:0x00ab, B:48:0x00b5, B:51:0x00bd, B:53:0x00d0, B:55:0x00da, B:58:0x00e2, B:59:0x00ef, B:60:0x00f3, B:63:0x00fb, B:69:0x0107, B:72:0x0115, B:73:0x011a, B:75:0x0128, B:77:0x012e, B:74:0x0122, B:80:0x0136, B:29:0x0061, B:16:0x003c), top: B:87:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0083 A[Catch: all -> 0x0141, TRY_LEAVE, TryCatch #0 {all -> 0x0141, blocks: (B:9:0x001d, B:11:0x0025, B:13:0x0029, B:15:0x002d, B:19:0x0043, B:21:0x0049, B:23:0x004d, B:25:0x0051, B:32:0x006a, B:34:0x0071, B:36:0x0077, B:38:0x0083, B:41:0x008b, B:43:0x00a3, B:46:0x00ab, B:48:0x00b5, B:51:0x00bd, B:53:0x00d0, B:55:0x00da, B:58:0x00e2, B:59:0x00ef, B:60:0x00f3, B:63:0x00fb, B:69:0x0107, B:72:0x0115, B:73:0x011a, B:75:0x0128, B:77:0x012e, B:74:0x0122, B:80:0x0136, B:29:0x0061, B:16:0x003c), top: B:87:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008b A[Catch: all -> 0x0141, TRY_ENTER, TryCatch #0 {all -> 0x0141, blocks: (B:9:0x001d, B:11:0x0025, B:13:0x0029, B:15:0x002d, B:19:0x0043, B:21:0x0049, B:23:0x004d, B:25:0x0051, B:32:0x006a, B:34:0x0071, B:36:0x0077, B:38:0x0083, B:41:0x008b, B:43:0x00a3, B:46:0x00ab, B:48:0x00b5, B:51:0x00bd, B:53:0x00d0, B:55:0x00da, B:58:0x00e2, B:59:0x00ef, B:60:0x00f3, B:63:0x00fb, B:69:0x0107, B:72:0x0115, B:73:0x011a, B:75:0x0128, B:77:0x012e, B:74:0x0122, B:80:0x0136, B:29:0x0061, B:16:0x003c), top: B:87:0x001d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ParsingPackage> parseUsesSdk(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException, IOException {
        boolean z;
        int i2;
        String str;
        TypedValue typedValuePeekValue;
        String string;
        ParseResult<Integer> parseResultComputeTargetSdkVersion;
        ParseResult<SparseIntArray> parseResultUnknownTag;
        TypedValue typedValuePeekValue2;
        int i3 = SDK_VERSION;
        if (i3 > 0) {
            int i4 = 0;
            boolean z2 = (i & 512) != 0;
            TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesSdk);
            try {
                TypedValue typedValuePeekValue3 = typedArrayObtainAttributes.peekValue(0);
                SparseIntArray sparseIntArray = null;
                if (typedValuePeekValue3 == null) {
                    z = false;
                    i2 = 1;
                } else if (typedValuePeekValue3.type != 3 || typedValuePeekValue3.string == null) {
                    i2 = typedValuePeekValue3.data;
                    z = true;
                } else {
                    String string2 = typedValuePeekValue3.string.toString();
                    z = !TextUtils.isEmpty(string2);
                    str = string2;
                    i2 = 1;
                    typedValuePeekValue = typedArrayObtainAttributes.peekValue(1);
                    if (typedValuePeekValue != null) {
                        i4 = i2;
                        string = str;
                    } else if (typedValuePeekValue.type != 3 || typedValuePeekValue.string == null) {
                        i4 = typedValuePeekValue.data;
                        string = str;
                        str = null;
                    } else {
                        string = typedValuePeekValue.string.toString();
                        if (z) {
                            string = str;
                            str = string;
                        } else {
                            str = string;
                        }
                    }
                    int i5 = (z2 || (typedValuePeekValue2 = typedArrayObtainAttributes.peekValue(2)) == null) ? Integer.MAX_VALUE : typedValuePeekValue2.data;
                    String[] strArr = SDK_CODENAMES;
                    parseResultComputeTargetSdkVersion = FrameworkParsingPackageUtils.computeTargetSdkVersion(i4, str, strArr, parseInput, z2);
                    if (!parseResultComputeTargetSdkVersion.isError()) {
                        return parseInput.error(parseResultComputeTargetSdkVersion);
                    }
                    int iIntValue = parseResultComputeTargetSdkVersion.getResult().intValue();
                    ParseResult<?> parseResultEnableDeferredError = parseInput.enableDeferredError(parsingPackage.getPackageName(), iIntValue);
                    if (parseResultEnableDeferredError.isError()) {
                        return parseInput.error(parseResultEnableDeferredError);
                    }
                    ParseResult<Integer> parseResultComputeMinSdkVersion = FrameworkParsingPackageUtils.computeMinSdkVersion(i2, string, i3, strArr, parseInput);
                    if (parseResultComputeMinSdkVersion.isError()) {
                        return parseInput.error(parseResultComputeMinSdkVersion);
                    }
                    parsingPackage.setMinSdkVersion(parseResultComputeMinSdkVersion.getResult().intValue()).setTargetSdkVersion(iIntValue);
                    if (z2) {
                        ParseResult<Integer> parseResultComputeMaxSdkVersion = FrameworkParsingPackageUtils.computeMaxSdkVersion(i5, i3, parseInput);
                        if (parseResultComputeMaxSdkVersion.isError()) {
                            return parseInput.error(parseResultComputeMaxSdkVersion);
                        }
                        parsingPackage.setMaxSdkVersion(parseResultComputeMaxSdkVersion.getResult().intValue());
                    }
                    int depth = xmlResourceParser.getDepth();
                    while (true) {
                        int next = xmlResourceParser.next();
                        if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                            break;
                        }
                        if (next != 3 && next != 4) {
                            if (xmlResourceParser.getName().equals("extension-sdk")) {
                                if (sparseIntArray == null) {
                                    sparseIntArray = new SparseIntArray();
                                }
                                parseResultUnknownTag = parseExtensionSdk(parseInput, resources, xmlResourceParser, sparseIntArray);
                                XmlUtils.skipCurrentTag(xmlResourceParser);
                            } else {
                                parseResultUnknownTag = ParsingUtils.unknownTag("<uses-sdk>", parsingPackage, xmlResourceParser, parseInput);
                            }
                            if (parseResultUnknownTag.isError()) {
                                return parseInput.error(parseResultUnknownTag);
                            }
                        }
                    }
                    parsingPackage.setMinExtensionVersions(exactSizedCopyOfSparseArray(sparseIntArray));
                }
                str = null;
                typedValuePeekValue = typedArrayObtainAttributes.peekValue(1);
                if (typedValuePeekValue != null) {
                }
                if (z2) {
                    String[] strArr2 = SDK_CODENAMES;
                    parseResultComputeTargetSdkVersion = FrameworkParsingPackageUtils.computeTargetSdkVersion(i4, str, strArr2, parseInput, z2);
                    if (!parseResultComputeTargetSdkVersion.isError()) {
                    }
                }
            } finally {
                typedArrayObtainAttributes.recycle();
            }
        }
        return parseInput.success(parsingPackage);
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
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestExtensionSdk);
        try {
            int i = typedArrayObtainAttributes.getInt(0, -1);
            int i2 = typedArrayObtainAttributes.getInt(1, -1);
            typedArrayObtainAttributes.recycle();
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
            typedArrayObtainAttributes.recycle();
            throw th;
        }
    }

    private static ParseResult<ParsingPackage> parseRestrictUpdateHash(int i, ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        if ((i & 16) != 0) {
            TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestRestrictUpdate);
            try {
                String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
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
                typedArrayObtainAttributes.recycle();
            }
        }
        return parseInput.success(parsingPackage);
    }

    private static ParseResult<ParsingPackage> parseInstallConstraints(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, Set<String> set) throws XmlPullParserException, IOException {
        return InstallConstraintsTagParser.parseInstallConstraints(parseInput, parsingPackage, resources, xmlResourceParser, set);
    }

    /* JADX WARN: Code restructure failed: missing block: B:93:0x01a9, code lost:
    
        return r12.success(r13);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ParsingPackage> parseQueries(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseInput parseInput2;
        ParsingPackage parsingPackage2;
        Resources resources2;
        XmlResourceParser xmlResourceParser2;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser)) {
                if (xmlResourceParser.getName().equals("intent")) {
                    parseInput2 = parseInput;
                    parsingPackage2 = parsingPackage;
                    resources2 = resources;
                    xmlResourceParser2 = xmlResourceParser;
                    ParseResult<ParsedIntentInfoImpl> intentInfo = ParsedIntentInfoUtils.parseIntentInfo(null, parsingPackage2, resources2, xmlResourceParser2, true, true, parseInput2);
                    if (intentInfo.isError()) {
                        return parseInput2.error(intentInfo);
                    }
                    IntentFilter intentFilter = intentInfo.getResult().getIntentFilter();
                    int iCountActions = intentFilter.countActions();
                    int iCountDataSchemes = intentFilter.countDataSchemes();
                    int iCountDataTypes = intentFilter.countDataTypes();
                    int length = intentFilter.getHosts().length;
                    if (iCountDataSchemes == 0 && iCountDataTypes == 0 && iCountActions == 0) {
                        return parseInput2.error("intent tags must contain either an action or data.");
                    }
                    if (iCountActions > 1) {
                        return parseInput2.error("intent tag may have at most one action.");
                    }
                    if (iCountDataTypes > 1) {
                        return parseInput2.error("intent tag may have at most one data type.");
                    }
                    if (iCountDataSchemes > 1) {
                        return parseInput2.error("intent tag may have at most one data scheme.");
                    }
                    if (length > 1) {
                        return parseInput2.error("intent tag may have at most one data host.");
                    }
                    Intent intent = new Intent();
                    int iCountCategories = intentFilter.countCategories();
                    for (int i = 0; i < iCountCategories; i++) {
                        intent.addCategory(intentFilter.getCategory(i));
                    }
                    String str = null;
                    Uri uriBuild = iCountDataSchemes == 1 ? new Uri.Builder().scheme(intentFilter.getDataScheme(0)).authority(length == 1 ? intentFilter.getHosts()[0] : null).path(IntentFilter.WILDCARD_PATH).build() : null;
                    if (iCountDataTypes == 1) {
                        String dataType = intentFilter.getDataType(0);
                        if (!dataType.contains("/")) {
                            dataType = dataType + IntentFilter.WILDCARD_PATH;
                        }
                        str = dataType;
                        if (uriBuild == null) {
                            uriBuild = new Uri.Builder().scheme("content").authority("*").path(IntentFilter.WILDCARD_PATH).build();
                        }
                    }
                    intent.setDataAndType(uriBuild, str);
                    if (iCountActions == 1) {
                        intent.setAction(intentFilter.getAction(0));
                    }
                    parsingPackage2.addQueriesIntent(intent);
                } else {
                    parseInput2 = parseInput;
                    parsingPackage2 = parsingPackage;
                    resources2 = resources;
                    xmlResourceParser2 = xmlResourceParser;
                    if (xmlResourceParser2.getName().equals("package")) {
                        try {
                            String nonConfigurationString = resources2.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestQueriesPackage).getNonConfigurationString(0, 0);
                            if (TextUtils.isEmpty(nonConfigurationString)) {
                                return parseInput2.error("Package name is missing from package tag.");
                            }
                            parsingPackage2.addQueriesPackage(nonConfigurationString.intern());
                        } finally {
                        }
                    } else if (xmlResourceParser2.getName().equals(RuntimeManifestUtils.TAG_PROVIDER)) {
                        try {
                            String nonConfigurationString2 = resources2.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestQueriesProvider).getNonConfigurationString(0, 0);
                            if (TextUtils.isEmpty(nonConfigurationString2)) {
                                return parseInput2.error(-108, "Authority missing from provider tag.");
                            }
                            StringTokenizer stringTokenizer = new StringTokenizer(nonConfigurationString2, NavigationBarInflaterView.GRAVITY_SEPARATOR);
                            while (stringTokenizer.hasMoreElements()) {
                                parsingPackage2.addQueriesProvider(stringTokenizer.nextToken());
                            }
                        } finally {
                        }
                    }
                }
                parsingPackage = parsingPackage2;
                resources = resources2;
                xmlResourceParser = xmlResourceParser2;
                parseInput = parseInput2;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:162:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x04d6 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ParseResult<ParsingPackage> parseBaseApplication(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z) throws Throwable {
        Resources resources2;
        XmlResourceParser xmlResourceParser2;
        boolean z2;
        ParsingPackage parsingPackage2;
        ParsingPackageUtils parsingPackageUtils;
        ParseResult<?> apexSystemService;
        ParseInput parseInput2;
        boolean z3;
        boolean z4;
        ParseInput parseInput3;
        ParseResult<?> parseResultSuccess;
        ParseInput parseInput4;
        ParseInput parseInput5;
        ParsingPackage parsingPackage3;
        ParseInput parseInput6;
        boolean z5;
        ParseInput parseInput7;
        ParseInput parseInput8;
        ParseInput parseInput9;
        ParsingPackageUtils parsingPackageUtils2 = this;
        ParseInput parseInput10 = parseInput;
        ParsingPackage parsingPackage4 = parsingPackage;
        Resources resources3 = resources;
        XmlResourceParser xmlResourceParser3 = xmlResourceParser;
        String packageName = parsingPackage4.getPackageName();
        int targetSdkVersion = parsingPackage4.getTargetSdkVersion();
        TypedArray typedArrayObtainAttributes = resources3.obtainAttributes(xmlResourceParser3, R.styleable.AndroidManifestApplication);
        try {
            if (typedArrayObtainAttributes == null) {
                return parseInput10.error("<application> does not contain any attributes");
            }
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString != null) {
                String packageName2 = parsingPackage4.getPackageName();
                String strBuildClassName = ParsingUtils.buildClassName(packageName2, nonConfigurationString);
                if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(strBuildClassName)) {
                    return parseInput10.error("<application> invalid android:name");
                }
                if (strBuildClassName == null) {
                    return parseInput10.error("Empty class name in package " + packageName2);
                }
                parsingPackage4.setApplicationClassName(strBuildClassName);
            }
            TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(1);
            if (typedValuePeekValue != null) {
                parsingPackage4.setLabelResourceId(typedValuePeekValue.resourceId);
                if (typedValuePeekValue.resourceId == 0) {
                    parsingPackage4.setNonLocalizedLabel(typedValuePeekValue.coerceToString());
                }
            }
            parsingPackageUtils2.parseBaseAppBasicFlags(parsingPackage4, typedArrayObtainAttributes);
            String strNonConfigString = nonConfigString(1024, 4, typedArrayObtainAttributes);
            if (strNonConfigString != null) {
                String strBuildClassName2 = ParsingUtils.buildClassName(packageName, strNonConfigString);
                if (strBuildClassName2 == null) {
                    return parseInput10.error("Empty class name in package " + packageName);
                }
                parsingPackage4.setManageSpaceActivityName(strBuildClassName2);
            }
            if (Flags.changeLauncherBadging()) {
                ParseResult<int[]> parseResultDrawableResIdArray = drawableResIdArray(parseInput10, typedArrayObtainAttributes, resources3, 78, 500);
                if (parseResultDrawableResIdArray.isError()) {
                    return parseInput10.error((ParseResult<?>) parseResultDrawableResIdArray);
                }
                parsingPackage4.setAlternateLauncherIconResIds(parseResultDrawableResIdArray.getResult());
                ParseResult<int[]> parseResultStringResIdArray = stringResIdArray(parseInput10, typedArrayObtainAttributes, resources3, 79, 500);
                if (parseResultStringResIdArray.isError()) {
                    return parseInput10.error((ParseResult<?>) parseResultStringResIdArray);
                }
                parsingPackage4.setAlternateLauncherLabelResIds(parseResultStringResIdArray.getResult());
            }
            if (parsingPackage4.isBackupAllowed()) {
                String strNonConfigString2 = nonConfigString(1024, 16, typedArrayObtainAttributes);
                if (strNonConfigString2 != null) {
                    String strBuildClassName3 = ParsingUtils.buildClassName(packageName, strNonConfigString2);
                    if (strBuildClassName3 == null) {
                        return parseInput10.error("Empty class name in package " + packageName);
                    }
                    parsingPackage4.setBackupAgentName(strBuildClassName3).setKillAfterRestoreAllowed(bool(true, 18, typedArrayObtainAttributes)).setRestoreAnyVersion(bool(false, 21, typedArrayObtainAttributes)).setFullBackupOnly(bool(false, 32, typedArrayObtainAttributes)).setBackupInForeground(bool(false, 40, typedArrayObtainAttributes));
                }
                TypedValue typedValuePeekValue2 = typedArrayObtainAttributes.peekValue(35);
                if (typedValuePeekValue2 != null) {
                    int i2 = typedValuePeekValue2.resourceId;
                    if (typedValuePeekValue2.resourceId == 0) {
                        i2 = typedValuePeekValue2.data == 0 ? -1 : 0;
                    }
                    parsingPackage4.setFullBackupContentResourceId(i2);
                }
            }
            if (typedArrayObtainAttributes.getBoolean(8, false)) {
                String nonResourceString = typedArrayObtainAttributes.getNonResourceString(45);
                parsingPackage4.setPersistent(nonResourceString == null || parsingPackageUtils2.mCallback.hasFeature(nonResourceString));
            }
            if (typedArrayObtainAttributes.hasValueOrEmpty(37)) {
                parsingPackage4.setResizeableActivity(Boolean.valueOf(typedArrayObtainAttributes.getBoolean(37, true)));
            } else {
                parsingPackage4.setResizeableActivityViaSdkVersion(targetSdkVersion >= 24);
            }
            ParseResult<String> parseResultBuildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, packageName, targetSdkVersion >= 8 ? typedArrayObtainAttributes.getNonConfigurationString(12, 1024) : typedArrayObtainAttributes.getNonResourceString(12), parseInput10);
            if (parseResultBuildTaskAffinityName.isError()) {
                return parseInput10.error(parseResultBuildTaskAffinityName);
            }
            parsingPackage4.setTaskAffinity(parseResultBuildTaskAffinityName.getResult());
            String nonResourceString2 = typedArrayObtainAttributes.getNonResourceString(48);
            if (nonResourceString2 != null) {
                String strBuildClassName4 = ParsingUtils.buildClassName(packageName, nonResourceString2);
                if (strBuildClassName4 == null) {
                    return parseInput10.error("Empty class name in package " + packageName);
                }
                parsingPackage4.setAppComponentFactory(strBuildClassName4);
            }
            ParseResult<String> parseResultBuildProcessName = ComponentParseUtils.buildProcessName(packageName, null, targetSdkVersion >= 8 ? typedArrayObtainAttributes.getNonConfigurationString(11, 1024) : typedArrayObtainAttributes.getNonResourceString(11), i, parsingPackageUtils2.mSeparateProcesses, parseInput10);
            if (parseResultBuildProcessName.isError()) {
                return parseInput10.error(parseResultBuildProcessName);
            }
            String result = parseResultBuildProcessName.getResult();
            parsingPackage4.setProcessName(result);
            if (parsingPackage4.isSaveStateDisallowed() && result != null && !result.equals(packageName)) {
                return parseInput10.error("cantSaveState applications can not use custom processes");
            }
            String classLoaderName = parsingPackage4.getClassLoaderName();
            if (classLoaderName != null && !ClassLoaderFactory.isValidClassLoaderName(classLoaderName)) {
                return parseInput10.error("Invalid class loader name: " + classLoaderName);
            }
            parsingPackage4.setGwpAsanMode(typedArrayObtainAttributes.getInt(62, -1));
            parsingPackage4.setMemtagMode(typedArrayObtainAttributes.getInt(64, -1));
            if (Flags.appCompatOption16kb()) {
                parsingPackage4.setPageSizeAppCompatFlags(typedArrayObtainAttributes.getInt(76, 0));
            }
            if (typedArrayObtainAttributes.hasValue(65)) {
                parsingPackage4.setNativeHeapZeroInitialized(typedArrayObtainAttributes.getBoolean(65, false) ? 1 : 0);
            }
            if (typedArrayObtainAttributes.hasValue(67)) {
                parsingPackage4.setRequestRawExternalStorageAccess(Boolean.valueOf(typedArrayObtainAttributes.getBoolean(67, false)));
            }
            if (typedArrayObtainAttributes.hasValue(68)) {
                parsingPackage4.setRequestForegroundServiceExemption(typedArrayObtainAttributes.getBoolean(68, false));
            }
            ParseResult<Set<String>> knownActivityEmbeddingCerts = ParsingUtils.parseKnownActivityEmbeddingCerts(typedArrayObtainAttributes, resources3, 72, parseInput10);
            if (knownActivityEmbeddingCerts.isError()) {
                return parseInput10.error(knownActivityEmbeddingCerts);
            }
            Set<String> result2 = knownActivityEmbeddingCerts.getResult();
            if (result2 != null) {
                parsingPackage4.setKnownActivityEmbeddingCerts(result2);
            }
            parsingPackage4.setIntentMatchingFlags(typedArrayObtainAttributes.getInt(75, 0));
            typedArrayObtainAttributes.recycle();
            int depth = xmlResourceParser3.getDepth();
            boolean z6 = false;
            boolean z7 = false;
            boolean z8 = false;
            ParseInput parseInput11 = parseInput10;
            while (true) {
                int next = xmlResourceParser3.next();
                if (next != 1 && (next != 3 || xmlResourceParser3.getDepth() > depth)) {
                    char c = 2;
                    if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage4, xmlResourceParser3)) {
                        String name = xmlResourceParser3.getName();
                        name.hashCode();
                        switch (name.hashCode()) {
                            case -1655966961:
                                if (!name.equals("activity")) {
                                    c = 65535;
                                    break;
                                } else {
                                    c = 0;
                                    break;
                                }
                            case -1572095710:
                                if (name.equals("apex-system-service")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case -987494927:
                                if (!name.equals(RuntimeManifestUtils.TAG_PROVIDER)) {
                                }
                                break;
                            case -808719889:
                                if (name.equals("receiver")) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case 790287890:
                                if (name.equals("activity-alias")) {
                                    c = 4;
                                    break;
                                }
                                break;
                            case 920944669:
                                if (name.equals("provider-salescode")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case 1984153269:
                                if (name.equals("service")) {
                                    c = 6;
                                    break;
                                }
                                break;
                            case 1989484475:
                                if (name.equals("activity-salescode")) {
                                    c = 7;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                resources2 = resources3;
                                xmlResourceParser2 = xmlResourceParser3;
                                z2 = true;
                                parseInput4 = parseInput11;
                                parsingPackage2 = parsingPackage4;
                                parsingPackageUtils = parsingPackageUtils2;
                                parseInput5 = parseInput4;
                                if (!z) {
                                    apexSystemService = ParsedActivityUtils.parseActivityOrReceiver(parsingPackageUtils.mSeparateProcesses, parsingPackage2, resources2, xmlResourceParser2, i, sUseRoundIcon, null, parseInput4);
                                    parseInput3 = parseInput4;
                                    z4 = z6;
                                    z3 = z7;
                                    if (apexSystemService.isSuccess()) {
                                        ParsedActivity parsedActivity = (ParsedActivity) apexSystemService.getResult();
                                        if (z2) {
                                            boolean z9 = parsedActivity.getOrder() != 0;
                                            parsingPackage2.addActivity(parsedActivity);
                                            z4 = z9 | z6;
                                            parseInput3 = parseInput4;
                                            z3 = z7;
                                        } else {
                                            boolean z10 = parsedActivity.getOrder() != 0;
                                            parsingPackage2.addReceiver(parsedActivity);
                                            z3 = z7 | z10;
                                            parseInput3 = parseInput4;
                                            z4 = z6;
                                        }
                                    }
                                    parseResultSuccess = apexSystemService;
                                    parseInput5 = parseInput3;
                                    z6 = z4;
                                    z7 = z3;
                                    z8 = z8;
                                    if (!parseResultSuccess.isError()) {
                                        return parseInput5.error(parseResultSuccess);
                                    }
                                    resources3 = resources;
                                    xmlResourceParser3 = xmlResourceParser;
                                    parsingPackageUtils2 = parsingPackageUtils;
                                    parsingPackage4 = parsingPackage2;
                                    parseInput11 = parseInput5;
                                    z6 = z6;
                                    z7 = z7;
                                    z8 = z8;
                                }
                                break;
                            case 1:
                                parsingPackage2 = parsingPackage4;
                                parsingPackageUtils = parsingPackageUtils2;
                                apexSystemService = ParsedApexSystemServiceUtils.parseApexSystemService(resources3, xmlResourceParser3, parseInput11);
                                parseInput3 = parseInput11;
                                z4 = z6;
                                z3 = z7;
                                if (apexSystemService.isSuccess()) {
                                    parsingPackage2.addApexSystemService((ParsedApexSystemService) apexSystemService.getResult());
                                    parseInput3 = parseInput11;
                                    z4 = z6;
                                    z3 = z7;
                                }
                                parseResultSuccess = apexSystemService;
                                parseInput5 = parseInput3;
                                z6 = z4;
                                z7 = z3;
                                z8 = z8;
                                if (!parseResultSuccess.isError()) {
                                }
                                break;
                            case 2:
                                parsingPackage2 = parsingPackage4;
                                parsingPackageUtils = parsingPackageUtils2;
                                if (z) {
                                    parseInput2 = parseInput;
                                    resources2 = resources;
                                    xmlResourceParser2 = xmlResourceParser;
                                    parseInput5 = parseInput2;
                                    break;
                                } else {
                                    ParseInput parseInput12 = parseInput;
                                    apexSystemService = ParsedProviderUtils.parseProvider(parsingPackageUtils.mSeparateProcesses, parsingPackage2, resources, xmlResourceParser, i, sUseRoundIcon, null, parseInput12);
                                    parseInput3 = parseInput12;
                                    z4 = z6;
                                    z3 = z7;
                                    if (apexSystemService.isSuccess()) {
                                        parsingPackage2.addProvider((ParsedProvider) apexSystemService.getResult());
                                        parseInput3 = parseInput12;
                                        z4 = z6;
                                        z3 = z7;
                                    }
                                    parseResultSuccess = apexSystemService;
                                    parseInput5 = parseInput3;
                                    z6 = z4;
                                    z7 = z3;
                                    z8 = z8;
                                    if (!parseResultSuccess.isError()) {
                                    }
                                }
                                break;
                            case 3:
                                parseInput4 = parseInput;
                                resources2 = resources;
                                xmlResourceParser2 = xmlResourceParser;
                                z2 = false;
                                parsingPackage2 = parsingPackage4;
                                parsingPackageUtils = parsingPackageUtils2;
                                parseInput5 = parseInput4;
                                if (!z) {
                                }
                                break;
                            case 4:
                                ParsingPackage parsingPackage5 = parsingPackage4;
                                parsingPackageUtils = parsingPackageUtils2;
                                if (z) {
                                    resources2 = resources;
                                    xmlResourceParser2 = xmlResourceParser;
                                    parsingPackage2 = parsingPackage5;
                                    parseInput5 = parseInput11;
                                    break;
                                } else {
                                    ParseResult<ParsedActivity> activityAlias = ParsedActivityUtils.parseActivityAlias(parsingPackage5, resources, xmlResourceParser, sUseRoundIcon, null, parseInput11);
                                    parsingPackage2 = parsingPackage5;
                                    boolean z11 = z6;
                                    if (activityAlias.isSuccess()) {
                                        ParsedActivity result3 = activityAlias.getResult();
                                        boolean z12 = result3.getOrder() != 0;
                                        parsingPackage2.addActivity(result3);
                                        z11 = z12 | z6;
                                    }
                                    parseInput5 = parseInput;
                                    parseResultSuccess = activityAlias;
                                    z6 = z11;
                                    z7 = z7;
                                    z8 = z8;
                                    if (!parseResultSuccess.isError()) {
                                    }
                                }
                                break;
                            case 5:
                                parsingPackage3 = parsingPackage4;
                                parsingPackageUtils = parsingPackageUtils2;
                                if (RuntimeManifestUtils.useLegacyRuntimeManifest(parsingPackage3.getMetaData())) {
                                    LegacyRuntimeManifestParseUtils.parseOverlayComponentAndModify(parsingPackage3.getPackageName(), parsingPackage3.getProviders(), resources, xmlResourceParser, parseInput, "<provider-salescode>");
                                    parseInput6 = parseInput;
                                } else {
                                    parseInput6 = parseInput;
                                }
                                parseResultSuccess = parseInput6.success(new ParsedProviderImpl());
                                parseInput7 = parseInput6;
                                z5 = z8;
                                parsingPackage2 = parsingPackage3;
                                parseInput5 = parseInput7;
                                z6 = z6;
                                z7 = z7;
                                z8 = z5;
                                if (!parseResultSuccess.isError()) {
                                }
                                break;
                            case 6:
                                parsingPackageUtils = parsingPackageUtils2;
                                if (z) {
                                    parsingPackage2 = parsingPackage;
                                    parseInput2 = parseInput11;
                                    resources2 = resources;
                                    xmlResourceParser2 = xmlResourceParser;
                                    parseInput5 = parseInput2;
                                    break;
                                } else {
                                    ParseResult<ParsedService> service = ParsedServiceUtils.parseService(parsingPackageUtils.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, null, parseInput11);
                                    parsingPackage3 = parsingPackage;
                                    boolean z13 = z8;
                                    if (service.isSuccess()) {
                                        ParsedService result4 = service.getResult();
                                        boolean z14 = result4.getOrder() != 0;
                                        parsingPackage3.addService(result4);
                                        z13 = z8 | z14;
                                    }
                                    parseInput7 = parseInput;
                                    parseResultSuccess = service;
                                    z5 = z13;
                                    parsingPackage2 = parsingPackage3;
                                    parseInput5 = parseInput7;
                                    z6 = z6;
                                    z7 = z7;
                                    z8 = z5;
                                    if (!parseResultSuccess.isError()) {
                                    }
                                }
                                break;
                            case 7:
                                parsingPackageUtils = parsingPackageUtils2;
                                if (RuntimeManifestUtils.useLegacyRuntimeManifest(parsingPackage.getMetaData())) {
                                    LegacyRuntimeManifestParseUtils.parseOverlayComponentAndModify(parsingPackage.getPackageName(), parsingPackage.getActivities(), resources, xmlResourceParser, parseInput, "<activity-salescode>");
                                    parseInput8 = parseInput;
                                } else {
                                    parseInput8 = parseInput;
                                }
                                parseResultSuccess = parseInput8.success(new ParsedActivityImpl());
                                parseInput9 = parseInput8;
                                parsingPackage2 = parsingPackage;
                                parseInput5 = parseInput9;
                                z6 = z6;
                                z7 = z7;
                                z8 = z8;
                                if (!parseResultSuccess.isError()) {
                                }
                                break;
                            default:
                                parseResultSuccess = parsingPackageUtils2.parseBaseAppChildTag(parseInput11, name, parsingPackage4, resources3, xmlResourceParser3, i);
                                parsingPackageUtils = parsingPackageUtils2;
                                parseInput9 = parseInput;
                                parsingPackage2 = parsingPackage;
                                parseInput5 = parseInput9;
                                z6 = z6;
                                z7 = z7;
                                z8 = z8;
                                if (!parseResultSuccess.isError()) {
                                }
                                break;
                        }
                    } else {
                        parsingPackage2 = parsingPackage4;
                        resources2 = resources3;
                        xmlResourceParser2 = xmlResourceParser3;
                        parsingPackageUtils = parsingPackageUtils2;
                        parseInput5 = parseInput11;
                    }
                    resources3 = resources2;
                    xmlResourceParser3 = xmlResourceParser2;
                    parsingPackageUtils2 = parsingPackageUtils;
                    parsingPackage4 = parsingPackage2;
                    parseInput11 = parseInput5;
                    z6 = z6;
                    z7 = z7;
                    z8 = z8;
                }
            }
            ParsingPackage parsingPackage6 = parsingPackage4;
            ParsingPackageUtils parsingPackageUtils3 = parsingPackageUtils2;
            if (!RuntimeManifestUtils.useLegacyRuntimeManifest(parsingPackage6.getMetaData())) {
                RuntimeManifestOverlayUtils.applyRuntimeManifestIfNeeded(parsingPackage, resources);
            }
            if (TextUtils.isEmpty(parsingPackage6.getStaticSharedLibraryName()) && TextUtils.isEmpty(parsingPackage6.getSdkLibraryName())) {
                ParseResult<ParsedActivity> parseResultGenerateAppDetailsHiddenActivity = generateAppDetailsHiddenActivity(parseInput, parsingPackage);
                if (parseResultGenerateAppDetailsHiddenActivity.isError()) {
                    return parseInput11.error(parseResultGenerateAppDetailsHiddenActivity);
                }
                parsingPackage6.addActivity(parseResultGenerateAppDetailsHiddenActivity.getResult());
            }
            if (z6) {
                parsingPackage6.sortActivities();
            }
            if (z7) {
                parsingPackage6.sortReceivers();
            }
            if (z8) {
                parsingPackage6.sortServices();
            }
            parsingPackageUtils3.afterParseBaseApplication(parsingPackage6);
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
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

    private ParseResult parseBaseAppChildTag(ParseInput parseInput, String str, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException, IOException {
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
                ParseResult<PackageManager.Property> metaData = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
                if (metaData.isSuccess() && metaData.getResult() != null) {
                    parsingPackage.setMetaData(metaData.getResult().toBundle(parsingPackage.getMetaData()));
                }
                return metaData;
            case "processes":
                return parseProcesses(parseInput, parsingPackage, resources, xmlResourceParser, this.mSeparateProcesses, i);
            case "static-library":
                return parseStaticLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case "property":
                ParseResult<PackageManager.Property> metaData2 = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
                if (metaData2.isSuccess()) {
                    parsingPackage.addProperty(metaData2.getResult());
                }
                return metaData2;
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
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestSdkLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            int i = typedArrayObtainAttributes.getInt(1, -1);
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
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseStaticLibrary(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestStaticLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            int i = typedArrayObtainAttributes.getInt(1, -1);
            int i2 = typedArrayObtainAttributes.getInt(2, 0);
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
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseLibrary(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            if (nonResourceString != null) {
                String strIntern = nonResourceString.intern();
                if (!ArrayUtils.contains(parsingPackage.getLibraryNames(), strIntern)) {
                    parsingPackage.addLibraryName(strIntern);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesSdkLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        String str = "";
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesSdkLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            int i = typedArrayObtainAttributes.getInt(2, -1);
            String nonResourceString2 = typedArrayObtainAttributes.getNonResourceString(1);
            boolean z = typedArrayObtainAttributes.getBoolean(3, false);
            if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
                if (parsingPackage.getUsesSdkLibraries().contains(nonResourceString)) {
                    return parseInput.error("Depending on multiple versions of SDK library " + nonResourceString);
                }
                String strIntern = nonResourceString.intern();
                String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
                if ("".equals(lowerCase)) {
                    lowerCase = SystemProperties.get("debug.pm.uses_sdk_library_default_cert_digest", "");
                    try {
                        HexEncoding.decode(lowerCase, false);
                        str = lowerCase;
                    } catch (IllegalArgumentException unused) {
                    }
                } else {
                    str = lowerCase;
                }
                ParseResult<String[]> additionalCertificates = parseAdditionalCertificates(parseInput, parsingPackage, resources, xmlResourceParser);
                if (additionalCertificates.isError()) {
                    return parseInput.error((ParseResult<?>) additionalCertificates);
                }
                String[] result = additionalCertificates.getResult();
                String[] strArr = new String[result.length + 1];
                strArr[0] = str;
                System.arraycopy(result, 0, strArr, 1, result.length);
                return parseInput.success(parsingPackage.addUsesSdkLibrary(strIntern, i, strArr, z));
            }
            return parseInput.error("Bad uses-sdk-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesStaticLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesStaticLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            int i = typedArrayObtainAttributes.getInt(1, -1);
            String nonResourceString2 = typedArrayObtainAttributes.getNonResourceString(2);
            if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
                if (parsingPackage.getUsesStaticLibraries().contains(nonResourceString)) {
                    return parseInput.error("Depending on multiple versions of static library " + nonResourceString);
                }
                String strIntern = nonResourceString.intern();
                String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
                String[] result = EmptyArray.STRING;
                if (parsingPackage.getTargetSdkVersion() >= 27) {
                    ParseResult<String[]> additionalCertificates = parseAdditionalCertificates(parseInput, parsingPackage, resources, xmlResourceParser);
                    if (additionalCertificates.isError()) {
                        return parseInput.error((ParseResult<?>) additionalCertificates);
                    }
                    result = additionalCertificates.getResult();
                }
                String[] strArr = new String[result.length + 1];
                strArr[0] = lowerCase;
                System.arraycopy(result, 0, strArr, 1, result.length);
                return parseInput.success(parsingPackage.addUsesStaticLibrary(strIntern, i, strArr));
            }
            return parseInput.error("Bad uses-static-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            boolean z = typedArrayObtainAttributes.getBoolean(1, true);
            if (nonResourceString != null) {
                String strIntern = nonResourceString.intern();
                if (z) {
                    parsingPackage.addUsesLibrary(strIntern).removeUsesOptionalLibrary(strIntern);
                } else if (!ArrayUtils.contains(parsingPackage.getUsesLibraries(), strIntern)) {
                    parsingPackage.addUsesOptionalLibrary(strIntern);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesNativeLibrary(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesNativeLibrary);
        try {
            String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
            boolean z = typedArrayObtainAttributes.getBoolean(1, true);
            if (nonResourceString != null) {
                if (z) {
                    parsingPackage.addUsesNativeLibrary(nonResourceString).removeUsesOptionalNativeLibrary(nonResourceString);
                } else if (!ArrayUtils.contains(parsingPackage.getUsesNativeLibraries(), nonResourceString)) {
                    parsingPackage.addUsesOptionalNativeLibrary(nonResourceString);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseProcesses(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, String[] strArr, int i) throws XmlPullParserException, IOException {
        ParseResult<ArrayMap<String, ParsedProcess>> processes = ParsedProcessUtils.parseProcesses(strArr, parsingPackage, resources, xmlResourceParser, i, parseInput);
        if (processes.isError()) {
            return parseInput.error(processes);
        }
        return parseInput.success(parsingPackage.setProcesses(processes.getResult()));
    }

    private static ParseResult<ParsingPackage> parseProfileable(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProfileable);
        try {
            boolean z = false;
            ParsingPackage profileableByShell = parsingPackage.setProfileableByShell(parsingPackage.isProfileableByShell() || bool(false, 1, typedArrayObtainAttributes));
            if (profileableByShell.isProfileable() && bool(true, 0, typedArrayObtainAttributes)) {
                z = true;
            }
            return parseInput.success(profileableByShell.setProfileable(z));
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007a, code lost:
    
        return r6.success(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<String[]> parseAdditionalCertificates(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        String[] strArr = EmptyArray.STRING;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !sAconfigFlags.skipCurrentElement(parsingPackage, xmlResourceParser) && xmlResourceParser.getName().equals("additional-certificate")) {
                TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAdditionalCertificate);
                try {
                    String nonResourceString = typedArrayObtainAttributes.getNonResourceString(0);
                    if (TextUtils.isEmpty(nonResourceString)) {
                        return parseInput.error("Bad additional-certificate declaration with empty certDigest:" + nonResourceString);
                    }
                    strArr = (String[]) ArrayUtils.appendElement(String.class, strArr, nonResourceString.replace(":", "").toLowerCase());
                } finally {
                    typedArrayObtainAttributes.recycle();
                }
            }
        }
    }

    private static ParseResult<ParsedActivity> generateAppDetailsHiddenActivity(ParseInput parseInput, ParsingPackage parsingPackage) {
        String packageName = parsingPackage.getPackageName();
        ParseResult<String> parseResultBuildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, packageName, ":app_details", parseInput);
        if (parseResultBuildTaskAffinityName.isError()) {
            return parseInput.error(parseResultBuildTaskAffinityName);
        }
        return parseInput.success(ParsedActivityImpl.makeAppDetailsActivity(packageName, parsingPackage.getProcessName(), parsingPackage.getUiOptions(), parseResultBuildTaskAffinityName.getResult(), parsingPackage.isHardwareAccelerated()));
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
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestResourceOverlay);
        try {
            String string = typedArrayObtainAttributes.getString(1);
            int iAnInt = anInt(0, 0, typedArrayObtainAttributes);
            if (string == null) {
                return parseInput.error("<overlay> does not specify a target package");
            }
            if (iAnInt < 0 || iAnInt > 9999) {
                return parseInput.error("<overlay> priority must be between 0 and 9999");
            }
            String string2 = typedArrayObtainAttributes.getString(5);
            String string3 = typedArrayObtainAttributes.getString(6);
            if (FrameworkParsingPackageUtils.checkRequiredSystemProperties(string2, string3)) {
                return parseInput.success(parsingPackage.setResourceOverlay(true).setOverlayTarget(string).setOverlayPriority(iAnInt).setOverlayTargetOverlayableName(typedArrayObtainAttributes.getString(3)).setOverlayCategory(typedArrayObtainAttributes.getString(2)).setOverlayIsStatic(bool(false, 4, typedArrayObtainAttributes)));
            }
            String str = "Skipping target and overlay pair " + string + " and " + parsingPackage.getBaseApkPath() + ": overlay ignored due to required system property: " + string2 + " with value: " + string3;
            Slog.i("PackageParsing", str);
            return parseInput.skip(str);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseProtectedBroadcast(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProtectedBroadcast);
        try {
            String strNonResString = nonResString(0, typedArrayObtainAttributes);
            if (strNonResString != null) {
                parsingPackage.addProtectedBroadcast(strNonResString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseSupportScreens(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestSupportsScreens);
        try {
            int iAnInt = anInt(0, 6, typedArrayObtainAttributes);
            int iAnInt2 = anInt(0, 7, typedArrayObtainAttributes);
            return parseInput.success(parsingPackage.setSmallScreensSupported(anInt(1, 1, typedArrayObtainAttributes)).setNormalScreensSupported(anInt(1, 2, typedArrayObtainAttributes)).setLargeScreensSupported(anInt(1, 3, typedArrayObtainAttributes)).setExtraLargeScreensSupported(anInt(1, 5, typedArrayObtainAttributes)).setResizeable(anInt(1, 4, typedArrayObtainAttributes)).setAnyDensity(anInt(1, 0, typedArrayObtainAttributes)).setRequiresSmallestWidthDp(iAnInt).setCompatibleWidthLimitDp(iAnInt2).setLargestWidthLimitDp(anInt(0, 8, typedArrayObtainAttributes)));
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseInstrumentation(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ParseResult<ParsedInstrumentation> instrumentation = ParsedInstrumentationUtils.parseInstrumentation(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (instrumentation.isError()) {
            return parseInput.error(instrumentation);
        }
        return parseInput.success(parsingPackage.addInstrumentation(instrumentation.getResult()));
    }

    private static ParseResult<ParsingPackage> parseOriginalPackage(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestOriginalPackage);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
            if (!parsingPackage.getPackageName().equals(nonConfigurationString)) {
                parsingPackage.addOriginalPackage(nonConfigurationString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseAdoptPermissions(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAdoptPermissions);
        try {
            String strNonConfigString = nonConfigString(0, 0, typedArrayObtainAttributes);
            if (strNonConfigString != null) {
                parsingPackage.addAdoptPermission(strNonConfigString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            typedArrayObtainAttributes.recycle();
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
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestMetaData);
        try {
            String strSafeIntern = TextUtils.safeIntern(nonConfigString(0, 0, typedArrayObtainAttributes));
            if (strSafeIntern == null) {
                return parseInput.error(str + " requires an android:name attribute");
            }
            String packageName = parsingPackage.getPackageName();
            PackageManager.Property property2 = null;
            String name = parsedComponent != null ? parsedComponent.getName() : null;
            TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(2);
            if (typedValuePeekValue != null && typedValuePeekValue.resourceId != 0) {
                property = new PackageManager.Property(strSafeIntern, typedValuePeekValue.resourceId, true, packageName, name);
            } else {
                TypedValue typedValuePeekValue2 = typedArrayObtainAttributes.peekValue(1);
                if (typedValuePeekValue2 != null) {
                    if (typedValuePeekValue2.type == 3) {
                        CharSequence charSequenceCoerceToString = typedValuePeekValue2.coerceToString();
                        property2 = new PackageManager.Property(strSafeIntern, charSequenceCoerceToString != null ? charSequenceCoerceToString.toString() : null, packageName, name);
                    } else if (typedValuePeekValue2.type == 18) {
                        property2 = new PackageManager.Property(strSafeIntern, typedValuePeekValue2.data != 0, packageName, name);
                    } else if (typedValuePeekValue2.type >= 16 && typedValuePeekValue2.type <= 31) {
                        property = new PackageManager.Property(strSafeIntern, typedValuePeekValue2.data, false, packageName, name);
                    } else if (typedValuePeekValue2.type == 4) {
                        property2 = new PackageManager.Property(strSafeIntern, typedValuePeekValue2.getFloat(), packageName, name);
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
            typedArrayObtainAttributes.recycle();
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
            boolean zEquals = new File(Environment.getRootDirectory(), "framework/framework-res.apk").getAbsolutePath().equals(str);
            if (!ArrayUtils.isEmpty(strArr) && !zEquals) {
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
        ParseResult<SigningDetails> parseResultVerify;
        int minimumSignatureSchemeVersionForTargetSdk = ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(i);
        if (z2) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        if (z) {
            parseResultVerify = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        } else {
            parseResultVerify = ApkSignatureVerifier.verify(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (parseResultVerify.isError()) {
            return parseInput.error(parseResultVerify);
        }
        if (signingDetails == SigningDetails.UNKNOWN) {
            return parseResultVerify;
        }
        if (!Signature.areExactMatch(signingDetails, parseResultVerify.getResult())) {
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

    private static ParseResult<int[]> resIdArray(ParseInput parseInput, TypedArray typedArray, Resources resources, int i, String str, int i2) throws Resources.NotFoundException {
        if (!typedArray.hasValue(i)) {
            return parseInput.success(null);
        }
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId == 0) {
            return parseInput.success(null);
        }
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(resourceId);
        try {
            String resourceName = resources.getResourceName(resourceId);
            int length = typedArrayObtainTypedArray.length();
            if (i2 > 0 && length > i2) {
                ParseResult<int[]> parseResultError = parseInput.error(TextUtils.formatSimple("The length of the typedArray (%s) is larger than %d.", resourceName, Integer.valueOf(i2)));
                if (typedArrayObtainTypedArray != null) {
                    typedArrayObtainTypedArray.close();
                }
                return parseResultError;
            }
            ArraySet arraySet = new ArraySet();
            for (int i3 = 0; i3 < length; i3++) {
                int resourceId2 = typedArrayObtainTypedArray.getResourceId(i3, 0);
                if (resourceId2 == 0) {
                    ParseResult<int[]> parseResultError2 = parseInput.error(TextUtils.formatSimple("There is an item that is not a resource id in the typedArray (%s).", resourceName));
                    if (typedArrayObtainTypedArray != null) {
                        typedArrayObtainTypedArray.close();
                    }
                    return parseResultError2;
                }
                try {
                    if (arraySet.contains(Integer.valueOf(resourceId2))) {
                        ParseResult<int[]> parseResultError3 = parseInput.error(TextUtils.formatSimple("There is a duplicated resource (%s) in the typedArray (%s).", resources.getResourceName(resourceId2), resourceName));
                        if (typedArrayObtainTypedArray != null) {
                            typedArrayObtainTypedArray.close();
                        }
                        return parseResultError3;
                    }
                    String resourceTypeName = resources.getResourceTypeName(resourceId2);
                    if (str != null && !TextUtils.equals(resourceTypeName, str)) {
                        ParseResult<int[]> parseResultError4 = parseInput.error(TextUtils.formatSimple("There is a resource (%s) in the typedArray (%s) that is not a %s type.", resources.getResourceName(resourceId2), resourceName, str));
                        if (typedArrayObtainTypedArray != null) {
                            typedArrayObtainTypedArray.close();
                        }
                        return parseResultError4;
                    }
                    arraySet.add(Integer.valueOf(resourceId2));
                } catch (Resources.NotFoundException unused) {
                    ParseResult<int[]> parseResultError5 = parseInput.error(TextUtils.formatSimple("There is a resource in the typedArray (%s) that is not found in the app resources.", resourceName));
                    if (typedArrayObtainTypedArray != null) {
                        typedArrayObtainTypedArray.close();
                    }
                    return parseResultError5;
                }
            }
            if (arraySet.isEmpty()) {
                ParseResult<int[]> parseResultSuccess = parseInput.success(null);
                if (typedArrayObtainTypedArray != null) {
                    typedArrayObtainTypedArray.close();
                }
                return parseResultSuccess;
            }
            ParseResult<int[]> parseResultSuccess2 = parseInput.success(arraySet.stream().mapToInt(new ToIntFunction() { // from class: com.android.internal.pm.pkg.parsing.ParsingPackageUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((Integer) obj).intValue();
                }
            }).toArray());
            if (typedArrayObtainTypedArray != null) {
                typedArrayObtainTypedArray.close();
            }
            return parseResultSuccess2;
        } catch (Throwable th) {
            if (typedArrayObtainTypedArray != null) {
                try {
                    typedArrayObtainTypedArray.close();
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
        int i = parcel.readInt();
        if (i == -1) {
            return null;
        }
        ArrayMap<String, ArraySet<PublicKey>> arrayMap = new ArrayMap<>();
        for (int i2 = 0; i2 < i; i2++) {
            String string = parcel.readString();
            int i3 = parcel.readInt();
            if (i3 == -1) {
                arrayMap.put(string, null);
            } else {
                ArraySet<PublicKey> arraySet = new ArraySet<>(i3);
                for (int i4 = 0; i4 < i3; i4++) {
                    arraySet.add((PublicKey) parcel.readSerializable(PublicKey.class.getClassLoader(), PublicKey.class));
                }
                arrayMap.put(string, arraySet);
            }
        }
        return arrayMap;
    }

    public static AconfigFlags getAconfigFlags() {
        return sAconfigFlags;
    }
}
