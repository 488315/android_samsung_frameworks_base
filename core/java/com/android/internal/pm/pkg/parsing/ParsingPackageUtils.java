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
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
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
import com.android.internal.pm.split.SplitAssetLoader;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;

import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import libcore.util.HexEncoding;

import com.samsung.android.rune.PMRune;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParsingPackageUtils {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    public static final float ASPECT_RATIO_NOT_SET = -1.0f;
    public static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_JAR = false;
    public static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f;
    public static final String METADATA_ACTIVITY_LAUNCH_MODE = "android.activity.launch_mode";
    public static final String METADATA_ACTIVITY_WINDOW_LAYOUT_AFFINITY =
            "android.activity_window_layout_affinity";
    public static final String METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES =
            "android.can_display_on_remote_devices";
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

        ParsingPackage startParsingPackage(
                String str, String str2, String str3, TypedArray typedArray, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParseFlags {}

    public static ParseResult<ParsedPackage> parseDefault(
            ParseInput input,
            File file,
            int parseFlags,
            List<PermissionManager.SplitPermissionInfo> splitPermissions,
            boolean collectCertificates,
            Callback callback) {
        ParsingPackageUtils parser =
                new ParsingPackageUtils(null, null, splitPermissions, callback);
        ParseResult<ParsingPackage> parseResult = parser.parsePackage(input, file, parseFlags);
        if (parseResult.isError()) {
            return input.error(parseResult);
        }
        ParsedPackage pkg = parseResult.getResult().hideAsParsed();
        if (collectCertificates) {
            ParseResult<SigningDetails> ret = getSigningDetails(input, pkg, false);
            if (ret.isError()) {
                return input.error(ret);
            }
            pkg.setSigningDetails(ret.getResult());
        }
        return input.success(pkg);
    }

    public ParsingPackageUtils(
            String[] separateProcesses,
            DisplayMetrics displayMetrics,
            List<PermissionManager.SplitPermissionInfo> splitPermissions,
            Callback callback) {
        this.mSeparateProcesses = separateProcesses;
        this.mDisplayMetrics = displayMetrics;
        this.mSplitPermissionInfos = splitPermissions;
        this.mCallback = callback;
    }

    public ParseResult<ParsingPackage> parsePackage(ParseInput input, File packageFile, int flags) {
        if (packageFile.isDirectory()) {
            return parseClusterPackage(input, packageFile, flags);
        }
        return parseMonolithicPackage(input, packageFile, flags);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseClusterPackage(
                    android.content.pm.parsing.result.ParseInput r20, java.io.File r21, int r22) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseClusterPackage(android.content.pm.parsing.result.ParseInput,"
                    + " java.io.File, int):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult<ParsingPackage> parseMonolithicPackage(
            ParseInput input, File apkFile, int flags) {
        int liteParseFlags = flags & (-33);
        ParseResult<PackageLite> liteResult =
                ApkLiteParseUtils.parseMonolithicPackageLite(input, apkFile, liteParseFlags);
        if (liteResult.isError()) {
            return input.error(liteResult);
        }
        PackageLite lite = liteResult.getResult();
        SplitAssetLoader assetLoader = new DefaultSplitAssetLoader(lite, flags);
        try {
            boolean shouldSkipComponents = lite.isIsSdkLibrary() && Flags.disallowSdkLibsToBeApps();
            ParseResult<ParsingPackage> result =
                    parseBaseApk(
                            input,
                            apkFile,
                            apkFile.getCanonicalPath(),
                            assetLoader,
                            flags,
                            shouldSkipComponents);
            return result.isError()
                    ? input.error(result)
                    : input.success(result.getResult().set32BitAbiPreferred(lite.isUse32bitAbi()));
        } catch (IOException e) {
            return input.error(-102, "Failed to get path: " + apkFile, e);
        } finally {
            IoUtils.closeQuietly(assetLoader);
        }
    }

    public ParseResult<ParsingPackage> parsePackageFromPackageLite(
            ParseInput input, PackageLite lite, int flags) {
        boolean z;
        String volumeUuid = getVolumeUuid(lite.getPath());
        String pkgName = lite.getPackageName();
        ParsingPackage pkg =
                this.mCallback.startParsingPackage(
                        pkgName, lite.getBaseApkPath(), lite.getPath(), null, lite.isCoreApp());
        int targetSdk = lite.getTargetSdk();
        pkg.setVersionCode(lite.getVersionCode());
        pkg.setVersionCodeMajor(lite.getVersionCodeMajor());
        pkg.setBaseRevisionCode(lite.getBaseRevisionCode());
        pkg.setVersionName(null);
        pkg.setCompileSdkVersion(0);
        pkg.setCompileSdkVersionCodeName(null);
        pkg.setIsolatedSplitLoading(false);
        pkg.setTargetSdkVersion(targetSdk);
        boolean z2 = true;
        ParsingPackage targetSandboxVersion =
                pkg.setInstallLocation(lite.getInstallLocation()).setTargetSandboxVersion(1);
        if ((flags & 8) == 0) {
            z = false;
        } else {
            z = true;
        }
        targetSandboxVersion.setExternalStorage(z);
        ArchivedPackageParcel archivedPackage = lite.getArchivedPackage();
        if (archivedPackage == null) {
            return input.error(-102, "archivePackage is missing");
        }
        ParsingPackage extractNativeLibrariesRequested =
                pkg.setBackupAllowed(true)
                        .setClearUserDataAllowed(true)
                        .setClearUserDataOnFailedRestoreAllowed(true)
                        .setAllowNativeHeapPointerTagging(true)
                        .setEnabled(true)
                        .setExtractNativeLibrariesRequested(true);
        if (targetSdk < 29) {
            z2 = false;
        }
        extractNativeLibrariesRequested
                .setAllowAudioPlaybackCapture(z2)
                .setHardwareAccelerated(targetSdk >= 14)
                .setRequestLegacyExternalStorage(
                        XmlUtils.convertValueToBoolean(
                                archivedPackage.requestLegacyExternalStorage, targetSdk < 29))
                .setCleartextTrafficAllowed(targetSdk < 28)
                .setDefaultToDeviceProtectedStorage(
                        XmlUtils.convertValueToBoolean(
                                archivedPackage.defaultToDeviceProtectedStorage, false))
                .setUserDataFragile(
                        XmlUtils.convertValueToBoolean(archivedPackage.userDataFragile, false))
                .setCategory(-1)
                .setMaxAspectRatio(0.0f)
                .setMinAspectRatio(0.0f);
        pkg.setDeclaredHavingCode(false);
        ParseResult<String> taskAffinityResult =
                ComponentParseUtils.buildTaskAffinityName(pkgName, pkgName, null, input);
        if (taskAffinityResult.isError()) {
            return input.error(taskAffinityResult);
        }
        pkg.setTaskAffinity(taskAffinityResult.getResult());
        ParseResult<String> processNameResult =
                ComponentParseUtils.buildProcessName(
                        pkgName, null, null, flags, this.mSeparateProcesses, input);
        if (processNameResult.isError()) {
            return input.error(processNameResult);
        }
        pkg.setProcessName(processNameResult.getResult());
        pkg.setGwpAsanMode(-1);
        pkg.setMemtagMode(-1);
        afterParseBaseApplication(pkg);
        ParseResult<ParsingPackage> result = validateBaseApkTags(input, pkg, flags);
        if (result.isError()) {
            return result;
        }
        pkg.setVolumeUuid(volumeUuid);
        if ((flags & 32) != 0) {
            pkg.setSigningDetails(lite.getSigningDetails());
        } else {
            pkg.setSigningDetails(SigningDetails.UNKNOWN);
        }
        return input.success(pkg.set32BitAbiPreferred(lite.isUse32bitAbi()));
    }

    private static String getVolumeUuid(String apkPath) {
        if (!apkPath.startsWith("/mnt/expand/")) {
            return null;
        }
        int end = apkPath.indexOf(47, "/mnt/expand/".length());
        String volumeUuid = apkPath.substring("/mnt/expand/".length(), end);
        return volumeUuid;
    }

    private ParseResult<ParsingPackage> parseBaseApk(
            ParseInput input,
            File apkFile,
            String codePath,
            SplitAssetLoader assetLoader,
            int flags,
            boolean shouldSkipComponents) {
        ParseResult<ParsingPackage> result;
        String apkPath = apkFile.getAbsolutePath();
        String volumeUuid = getVolumeUuid(apkPath);
        try {
            AssetManager assets = assetLoader.getBaseAssetManager();
            int cookie = assets.findCookieForPath(apkPath);
            if (cookie == 0) {
                return input.error(-101, "Failed adding asset path: " + apkPath);
            }
            try {
                XmlResourceParser parser =
                        assets.openXmlResourceParser(cookie, "AndroidManifest.xml");
                try {
                    try {
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        Resources res = new Resources(assets, this.mDisplayMetrics, null);
                        ParseResult<ParsingPackage> result2 =
                                parseBaseApk(
                                        input,
                                        apkPath,
                                        codePath,
                                        res,
                                        parser,
                                        flags,
                                        shouldSkipComponents);
                        if (result2.isError()) {
                            ParseResult<ParsingPackage> error =
                                    input.error(
                                            result2.getErrorCode(),
                                            apkPath
                                                    + " (at "
                                                    + parser.getPositionDescription()
                                                    + "): "
                                                    + result2.getErrorMessage());
                            if (parser != null) {
                                parser.close();
                            }
                            return error;
                        }
                        ParsingPackage pkg = result2.getResult();
                        if (assets.containsAllocatedTable()) {
                            ParseResult<?> deferResult =
                                    input.deferError(
                                            "Targeting R+ (version 30 and above) requires the"
                                                + " resources.arsc of installed APKs to be stored"
                                                + " uncompressed and aligned on a 4-byte boundary",
                                            ParseInput.DeferredError.RESOURCES_ARSC_COMPRESSED);
                            if (deferResult.isError()) {
                                ParseResult<ParsingPackage> error2 =
                                        input.error(
                                                PackageManager
                                                        .INSTALL_PARSE_FAILED_RESOURCES_ARSC_COMPRESSED,
                                                deferResult.getErrorMessage());
                                if (parser != null) {
                                    parser.close();
                                }
                                return error2;
                            }
                        }
                        ApkAssets apkAssets = assetLoader.getBaseApkAssets();
                        boolean definesOverlayable = false;
                        try {
                            definesOverlayable = apkAssets.definesOverlayable();
                        } catch (IOException e) {
                        }
                        if (definesOverlayable) {
                            SparseArray<String> packageNames =
                                    assets.getAssignedPackageIdentifiers();
                            int size = packageNames.size();
                            int index = 0;
                            while (index < size) {
                                String packageName = packageNames.valueAt(index);
                                SparseArray<String> packageNames2 = packageNames;
                                Map<String, String> overlayableToActor =
                                        assets.getOverlayableMap(packageName);
                                if (overlayableToActor == null || overlayableToActor.isEmpty()) {
                                    result = result2;
                                } else {
                                    for (String overlayable : overlayableToActor.keySet()) {
                                        pkg.addOverlayable(
                                                overlayable, overlayableToActor.get(overlayable));
                                        result2 = result2;
                                        overlayableToActor = overlayableToActor;
                                    }
                                    result = result2;
                                }
                                index++;
                                packageNames = packageNames2;
                                result2 = result;
                            }
                        }
                        pkg.setVolumeUuid(volumeUuid);
                        if ((flags & 32) != 0) {
                            ParseResult<SigningDetails> ret = getSigningDetails(input, pkg, false);
                            if (ret.isError()) {
                                ParseResult<ParsingPackage> error3 = input.error(ret);
                                if (parser != null) {
                                    parser.close();
                                }
                                return error3;
                            }
                            pkg.setSigningDetails(ret.getResult());
                        } else {
                            pkg.setSigningDetails(SigningDetails.UNKNOWN);
                        }
                        ParseResult<ParsingPackage> success = input.success(pkg);
                        if (parser != null) {
                            parser.close();
                        }
                        return success;
                    } catch (Throwable th2) {
                        th = th2;
                        Throwable th3 = th;
                        if (parser == null) {
                            throw th3;
                        }
                        try {
                            parser.close();
                            throw th3;
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                            throw th3;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    return input.error(-102, "Failed to read manifest from " + apkPath, e);
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (IllegalArgumentException e4) {
            return input.error(
                    e4.getCause() instanceof IOException ? -2 : -100, e4.getMessage(), e4);
        }
    }

    private ParseResult<ParsingPackage> parseSplitApk(
            ParseInput input, ParsingPackage pkg, int splitIndex, AssetManager assets, int flags) {
        String apkPath = pkg.getSplitCodePaths()[splitIndex];
        int cookie = assets.findCookieForPath(apkPath);
        if (cookie == 0) {
            return input.error(-101, "Failed adding asset path: " + apkPath);
        }
        try {
            XmlResourceParser parser = assets.openXmlResourceParser(cookie, "AndroidManifest.xml");
            try {
                Resources res = new Resources(assets, this.mDisplayMetrics, null);
                ParseResult<ParsingPackage> parseResult =
                        parseSplitApk(input, pkg, res, parser, flags, splitIndex);
                if (parseResult.isError()) {
                    ParseResult<ParsingPackage> error =
                            input.error(
                                    parseResult.getErrorCode(),
                                    apkPath
                                            + " (at "
                                            + parser.getPositionDescription()
                                            + "): "
                                            + parseResult.getErrorMessage());
                    if (parser != null) {
                        parser.close();
                    }
                    return error;
                }
                if (parser != null) {
                    parser.close();
                }
                return parseResult;
            } finally {
            }
        } catch (Exception e) {
            return input.error(-102, "Failed to read manifest from " + apkPath, e);
        }
    }

    private ParseResult<ParsingPackage> parseBaseApk(
            ParseInput input,
            String apkPath,
            String codePath,
            Resources res,
            XmlResourceParser parser,
            int flags,
            boolean shouldSkipComponents)
            throws XmlPullParserException, IOException {
        ParsingPackage pkg;
        ParseResult<Pair<String, String>> packageSplitResult =
                ApkLiteParseUtils.parsePackageSplitNames(input, parser);
        if (packageSplitResult.isError()) {
            return input.error(packageSplitResult);
        }
        Pair<String, String> packageSplit = packageSplitResult.getResult();
        String pkgName = packageSplit.first;
        String splitName = packageSplit.second;
        if (!TextUtils.isEmpty(splitName)) {
            return input.error(-106, "Expected base APK, but found split " + splitName);
        }
        TypedArray manifestArray = res.obtainAttributes(parser, R.styleable.AndroidManifest);
        try {
            boolean isCoreApp = parser.getAttributeBooleanValue(null, "coreApp", false);
            pkg =
                    this.mCallback.startParsingPackage(
                            pkgName, apkPath, codePath, manifestArray, isCoreApp);
        } catch (Throwable th) {
            th = th;
        }
        try {
            ParseResult<ParsingPackage> result =
                    parseBaseApkTags(
                            input, pkg, manifestArray, res, parser, flags, shouldSkipComponents);
            if (!result.isError()) {
                ParseResult<ParsingPackage> success = input.success(pkg);
                manifestArray.recycle();
                return success;
            }
            manifestArray.recycle();
            return result;
        } catch (Throwable th2) {
            th = th2;
            manifestArray.recycle();
            throw th;
        }
    }

    private ParseResult<ParsingPackage> parseSplitApk(
            ParseInput input,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            int flags,
            int splitIndex)
            throws XmlPullParserException, IOException {
        ParseResult result;
        ParseResult<Pair<String, String>> packageSplitResult =
                ApkLiteParseUtils.parsePackageSplitNames(input, parser);
        if (packageSplitResult.isError()) {
            return input.error(packageSplitResult);
        }
        boolean foundApp = false;
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1) {
                if (outerDepth + 1 >= parser.getDepth()
                        && type == 2
                        && !sAconfigFlags.skipCurrentElement(parser)) {
                    String tagName = parser.getName();
                    if ("application".equals(tagName)) {
                        if (foundApp) {
                            Slog.w("PackageParsing", "<manifest> has more than one <application>");
                            result = input.success(null);
                        } else {
                            foundApp = true;
                            result =
                                    parseSplitApplication(
                                            input, pkg, res, parser, flags, splitIndex);
                        }
                    } else {
                        result = ParsingUtils.unknownTag("<manifest>", pkg, parser, input);
                    }
                    if (result.isError()) {
                        return input.error((ParseResult<?>) result);
                    }
                }
            } else {
                if (!foundApp) {
                    ParseResult<?> deferResult =
                            input.deferError(
                                    "<manifest> does not contain an <application>",
                                    ParseInput.DeferredError.MISSING_APP_TAG);
                    if (deferResult.isError()) {
                        return input.error(deferResult);
                    }
                }
                return input.success(pkg);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x00ba, code lost:

       if (r5.equals(com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.TAG_PROVIDER) != false) goto L48;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseSplitApplication(
                    android.content.pm.parsing.result.ParseInput r24,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r25,
                    android.content.res.Resources r26,
                    android.content.res.XmlResourceParser r27,
                    int r28,
                    int r29)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 542
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseSplitApplication(android.content.pm.parsing.result.ParseInput,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser, int,"
                    + " int):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult parseSplitBaseAppChildTags(
            ParseInput input,
            String tag,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser)
            throws IOException, XmlPullParserException {
        char c;
        switch (tag.hashCode()) {
            case -1608941274:
                if (tag.equals("uses-native-library")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1521117785:
                if (tag.equals("uses-sdk-library")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (tag.equals("uses-library")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (tag.equals("meta-data")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (tag.equals("property")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 8960125:
                if (tag.equals("uses-static-library")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1964930885:
                if (tag.equals("uses-package")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                ParseResult<PackageManager.Property> metaDataResult =
                        parseMetaData(pkg, null, res, parser, "<meta-data>", input);
                if (metaDataResult.isSuccess() && metaDataResult.getResult() != null) {
                    pkg.setMetaData(metaDataResult.getResult().toBundle(pkg.getMetaData()));
                }
                return metaDataResult;
            case 1:
                ParseResult<PackageManager.Property> propertyResult =
                        parseMetaData(pkg, null, res, parser, "<property>", input);
                if (propertyResult.isSuccess()) {
                    pkg.addProperty(propertyResult.getResult());
                }
                return propertyResult;
            case 2:
                return parseUsesSdkLibrary(input, pkg, res, parser);
            case 3:
                return parseUsesStaticLibrary(input, pkg, res, parser);
            case 4:
                return parseUsesLibrary(input, pkg, res, parser);
            case 5:
                return parseUsesNativeLibrary(input, pkg, res, parser);
            case 6:
                return input.success(null);
            default:
                return ParsingUtils.unknownTag("<application>", pkg, parser, input);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0108, code lost:

       if (r14 == null) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0112, code lost:

       if (com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.useLegacyRuntimeManifest(r22.getMetaData()) == false) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0114, code lost:

       com.samsung.android.core.pm.runtimemanifest.LegacyRuntimeManifestParseUtils.modifyParsingPackageWithReplacement(r22, r14);
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0117, code lost:

       if (r16 != false) goto L54;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0121, code lost:

       if (com.android.internal.util.ArrayUtils.size(r22.getInstrumentations()) != 0) goto L54;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0123, code lost:

       r0 = r21.deferError("<manifest> does not contain an <application> or <instrumentation>", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_APP_TAG);
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0130, code lost:

       if (r0.isError() == false) goto L54;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0136, code lost:

       return r21.error(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x013d, code lost:

       return validateBaseApkTags(r21, r22, r26);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseBaseApkTags(
                    android.content.pm.parsing.result.ParseInput r21,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r22,
                    android.content.res.TypedArray r23,
                    android.content.res.Resources r24,
                    android.content.res.XmlResourceParser r25,
                    int r26,
                    boolean r27)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseBaseApkTags(android.content.pm.parsing.result.ParseInput,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.TypedArray, android.content.res.Resources,"
                    + " android.content.res.XmlResourceParser, int,"
                    + " boolean):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult<ParsingPackage> validateBaseApkTags(
            ParseInput input, ParsingPackage pkg, int flags) {
        if (!ParsedAttributionUtils.isCombinationValid(pkg.getAttributions())) {
            return input.error(-101, "Combination <attribution> tags are not valid");
        }
        if (ParsedPermissionUtils.declareDuplicatePermission(pkg)) {
            return input.error(
                    -108, "Found duplicate permission with a different attribute value.");
        }
        convertCompatPermissions(pkg);
        convertSplitPermissions(pkg);
        if (PMRune.PM_NAL_GET_APP_LIST) {
            String pkgName = pkg.getPackageName();
            boolean isCtsPackage =
                    "android.permission.cts.appthataccesseslocation".equals(pkgName)
                            || "android.permission.cts.appthatrequestcustompermission"
                                    .equals(pkgName)
                            || "com.google.android.sdksandbox".equals(pkgName)
                            || "android.appenumeration.queries.nothing.hasprovider".equals(pkgName)
                            || "android.appenumeration.queries.nothing.haspermission"
                                    .equals(pkgName);
            if (!isCtsPackage && ArrayUtils.size(pkg.getInstrumentations()) == 0) {
                pkg.addImplicitPermission(PackageManager.GET_APP_LIST_PERMISSION);
            }
        }
        if (pkg.getTargetSdkVersion() < 4
                || (!pkg.isSmallScreensSupported()
                        && !pkg.isNormalScreensSupported()
                        && !pkg.isLargeScreensSupported()
                        && !pkg.isExtraLargeScreensSupported()
                        && !pkg.isResizeable()
                        && !pkg.isAnyDensity())) {
            adjustPackageToBeUnresizeableAndUnpipable(pkg);
        }
        boolean isApex = (flags & 1024) != 0;
        if (isApex && !pkg.getPermissions().isEmpty()) {
            return input.error(
                    -108,
                    pkg.getPackageName()
                            + " is an APEX package and shouldn't declare permissions.");
        }
        return input.success(pkg);
    }

    private ParseResult parseBaseApkTag(
            String tag,
            ParseInput input,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            int flags)
            throws IOException, XmlPullParserException {
        char c;
        switch (tag.hashCode()) {
            case -1773650763:
                if (tag.equals("uses-configuration")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (tag.equals("permission-tree")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1108197302:
                if (tag.equals("original-package")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (tag.equals("overlay")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -998269702:
                if (tag.equals("restrict-update")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -979207434:
                if (tag.equals("feature")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (tag.equals("permission")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -309882753:
                if (tag.equals("attribution")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -266709319:
                if (tag.equals("uses-sdk")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (tag.equals("permission-group")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -129269526:
                if (tag.equals("eat-comment")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 119109844:
                if (tag.equals("uses-gl-texture")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 349565761:
                if (tag.equals("supports-input")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 454915839:
                if (tag.equals("key-sets")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (tag.equals("instrumentation")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (tag.equals("uses-permission")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 632228327:
                if (tag.equals("adopt-permissions")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (tag.equals("queries")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 862539012:
                if (tag.equals(TAG_INSTALL_CONSTRAINTS)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 896788286:
                if (tag.equals("supports-screens")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (tag.equals("uses-permission-sdk-23")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1439495522:
                if (tag.equals("protected-broadcast")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1682371816:
                if (tag.equals("feature-group")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (tag.equals("uses-permission-sdk-m")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (tag.equals("uses-feature")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (tag.equals("compatible-screens")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return parseOverlay(input, pkg, res, parser);
            case 1:
                return parseKeySets(input, pkg, res, parser);
            case 2:
            case 3:
                return parseAttribution(input, pkg, res, parser);
            case 4:
                return parsePermissionGroup(input, pkg, res, parser);
            case 5:
                return parsePermission(input, pkg, res, parser);
            case 6:
                return parsePermissionTree(input, pkg, res, parser);
            case 7:
            case '\b':
            case '\t':
                return parseUsesPermission(input, pkg, res, parser);
            case '\n':
                return parseUsesConfiguration(input, pkg, res, parser);
            case 11:
                return parseUsesFeature(input, pkg, res, parser);
            case '\f':
                return parseFeatureGroup(input, pkg, res, parser);
            case '\r':
                return parseUsesSdk(input, pkg, res, parser, flags);
            case 14:
                return parseSupportScreens(input, pkg, res, parser);
            case 15:
                return parseProtectedBroadcast(input, pkg, res, parser);
            case 16:
                return parseInstrumentation(input, pkg, res, parser);
            case 17:
                return parseOriginalPackage(input, pkg, res, parser);
            case 18:
                return parseAdoptPermissions(input, pkg, res, parser);
            case 19:
            case 20:
            case 21:
            case 22:
                XmlUtils.skipCurrentTag(parser);
                return input.success(pkg);
            case 23:
                return parseRestrictUpdateHash(flags, input, pkg, res, parser);
            case 24:
                return parseInstallConstraints(
                        input, pkg, res, parser, this.mCallback.getInstallConstraintsAllowlist());
            case 25:
                return parseQueries(input, pkg, res, parser);
            default:
                return ParsingUtils.unknownTag("<manifest>", pkg, parser, input);
        }
    }

    private static ParseResult<ParsingPackage> parseSharedUser(
            ParseInput input, ParsingPackage pkg, TypedArray sa) {
        boolean leaving = false;
        String str = nonConfigString(0, 0, sa);
        if (TextUtils.isEmpty(str)) {
            return input.success(pkg);
        }
        if (!"android".equals(pkg.getPackageName())) {
            ParseResult<?> nameResult =
                    FrameworkParsingPackageUtils.validateName(input, str, true, true);
            if (nameResult.isError()) {
                return input.error(
                        -107,
                        "<manifest> specifies bad sharedUserId name \""
                                + str
                                + "\": "
                                + nameResult.getErrorMessage());
            }
        }
        int max = anInteger(0, 13, sa);
        if (max != 0 && max < Build.VERSION.RESOURCES_SDK_INT) {
            leaving = true;
        }
        return input.success(
                pkg.setLeavingSharedUser(leaving)
                        .setSharedUserId(str.intern())
                        .setSharedUserLabelResourceId(resId(3, sa)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0235, code lost:

       r0 = r22.getPackageName();
       r5 = r7.keySet();
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0247, code lost:

       if (r5.removeAll(r9.keySet()) == false) goto L109;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0264, code lost:

       return r21.error("Package" + r0 + " AndroidManifest.xml 'key-set' and 'public-key' names must be distinct.");
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0265, code lost:

       r6 = r9.entrySet().iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0271, code lost:

       if (r6.hasNext() == false) goto L166;
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0273, code lost:

       r14 = r6.next();
       r15 = r14.getKey();
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x028b, code lost:

       if (r14.getValue().size() != 0) goto L167;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x02b8, code lost:

       if (r10.contains(r15) == false) goto L168;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x02e1, code lost:

       r3 = r14.getValue().iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x02ef, code lost:

       if (r3.hasNext() == false) goto L175;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x02f1, code lost:

       r4 = r3.next();
       r22.addKeySet(r15, r7.get(r4));
       r3 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x02ba, code lost:

       android.util.Slog.w("PackageParsing", "Package" + r0 + " AndroidManifest.xml 'key-set' " + r15 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x028d, code lost:

       android.util.Slog.w("PackageParsing", "Package" + r0 + " AndroidManifest.xml 'key-set' " + r15 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0319, code lost:

       if (r22.getKeySetMapping().keySet().containsAll(r8) == false) goto L127;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x031b, code lost:

       r22.setUpgradeKeySets(r8);
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0322, code lost:

       return r21.success(r22);
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x033e, code lost:

       return r21.error("Package" + r0 + " AndroidManifest.xml does not define all 'upgrade-key-set's .");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseKeySets(
                    android.content.pm.parsing.result.ParseInput r21,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r22,
                    android.content.res.Resources r23,
                    android.content.res.XmlResourceParser r24)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 856
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseKeySets(android.content.pm.parsing.result.ParseInput,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources,"
                    + " android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsingPackage> parseAttribution(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws IOException, XmlPullParserException {
        ParseResult<ParsedAttribution> result =
                ParsedAttributionUtils.parseAttribution(res, parser, input);
        if (result.isError()) {
            return input.error(result);
        }
        return input.success(pkg.addAttribution(result.getResult()));
    }

    private static ParseResult<ParsingPackage> parsePermissionGroup(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        ParseResult<ParsedPermissionGroup> result =
                ParsedPermissionUtils.parsePermissionGroup(pkg, res, parser, sUseRoundIcon, input);
        if (result.isError()) {
            return input.error(result);
        }
        return input.success(pkg.addPermissionGroup(result.getResult()));
    }

    private static ParseResult<ParsingPackage> parsePermission(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        ParseResult<ParsedPermission> result =
                ParsedPermissionUtils.parsePermission(pkg, res, parser, sUseRoundIcon, input);
        if (result.isError()) {
            return input.error(result);
        }
        ParsedPermission permission = result.getResult();
        if (permission != null) {
            pkg.addPermission(permission);
        }
        return input.success(pkg);
    }

    private static ParseResult<ParsingPackage> parsePermissionTree(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        ParseResult<ParsedPermission> result =
                ParsedPermissionUtils.parsePermissionTree(pkg, res, parser, sUseRoundIcon, input);
        if (result.isError()) {
            return input.error(result);
        }
        return input.success(pkg.addPermission(result.getResult()));
    }

    private int parseMinOrMaxSdkVersion(TypedArray sa, int attr, int defaultValue) {
        TypedValue peekVal = sa.peekValue(attr);
        if (peekVal == null || peekVal.type < 16 || peekVal.type > 31) {
            return defaultValue;
        }
        int val = peekVal.data;
        return val;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseUsesPermission(
                    android.content.pm.parsing.result.ParseInput r23,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r24,
                    android.content.res.Resources r25,
                    android.content.res.XmlResourceParser r26)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseUsesPermission(android.content.pm.parsing.result.ParseInput,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources,"
                    + " android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    private ParseResult<String> parseRequiredFeature(
            ParseInput input, Resources res, AttributeSet attrs) {
        ParseResult<String> success;
        TypedArray sa = res.obtainAttributes(attrs, R.styleable.AndroidManifestRequiredFeature);
        try {
            String featureName = sa.getString(0);
            if (TextUtils.isEmpty(featureName)) {
                success = input.error("Feature name is missing from <required-feature> tag.");
            } else {
                success = input.success(featureName);
            }
            return success;
        } finally {
            sa.recycle();
        }
    }

    private ParseResult<String> parseRequiredNotFeature(
            ParseInput input, Resources res, AttributeSet attrs) {
        ParseResult<String> success;
        TypedArray sa = res.obtainAttributes(attrs, R.styleable.AndroidManifestRequiredNotFeature);
        try {
            String featureName = sa.getString(0);
            if (TextUtils.isEmpty(featureName)) {
                success = input.error("Feature name is missing from <required-not-feature> tag.");
            } else {
                success = input.success(featureName);
            }
            return success;
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesConfiguration(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        ConfigurationInfo cPref = new ConfigurationInfo();
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesConfiguration);
        try {
            cPref.reqTouchScreen = sa.getInt(0, 0);
            cPref.reqKeyboardType = sa.getInt(1, 0);
            if (sa.getBoolean(2, false)) {
                cPref.reqInputFeatures = 1 | cPref.reqInputFeatures;
            }
            cPref.reqNavigation = sa.getInt(3, 0);
            if (sa.getBoolean(4, false)) {
                cPref.reqInputFeatures |= 2;
            }
            pkg.addConfigPreference(cPref);
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesFeature(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        FeatureInfo fi = parseFeatureInfo(res, parser);
        pkg.addReqFeature(fi);
        if (fi.name == null) {
            ConfigurationInfo cPref = new ConfigurationInfo();
            cPref.reqGlEsVersion = fi.reqGlEsVersion;
            pkg.addConfigPreference(cPref);
        }
        return input.success(pkg);
    }

    private static FeatureInfo parseFeatureInfo(Resources res, AttributeSet attrs) {
        FeatureInfo fi = new FeatureInfo();
        TypedArray sa = res.obtainAttributes(attrs, R.styleable.AndroidManifestUsesFeature);
        try {
            fi.name = sa.getNonResourceString(0);
            fi.version = sa.getInt(3, 0);
            if (fi.name == null) {
                fi.reqGlEsVersion = sa.getInt(1, 0);
            }
            if (sa.getBoolean(2, true)) {
                fi.flags |= 1;
            }
            return fi;
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseFeatureGroup(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws IOException, XmlPullParserException {
        FeatureGroupInfo group = new FeatureGroupInfo();
        ArrayList<FeatureInfo> features = null;
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= depth)) {
                break;
            }
            if (type == 2 && !sAconfigFlags.skipCurrentElement(parser)) {
                String innerTagName = parser.getName();
                if (innerTagName.equals("uses-feature")) {
                    FeatureInfo featureInfo = parseFeatureInfo(res, parser);
                    featureInfo.flags = 1 | featureInfo.flags;
                    features = ArrayUtils.add(features, featureInfo);
                } else {
                    Slog.w(
                            "PackageParsing",
                            "Unknown element under <feature-group>: "
                                    + innerTagName
                                    + " at "
                                    + pkg.getBaseApkPath()
                                    + " "
                                    + parser.getPositionDescription());
                }
            }
        }
        if (features != null) {
            group.features = new FeatureInfo[features.size()];
            group.features = (FeatureInfo[]) features.toArray(group.features);
        }
        pkg.addFeatureGroup(group);
        return input.success(pkg);
    }

    private static ParseResult<ParsingPackage> parseUsesSdk(
            ParseInput input,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            int flags)
            throws IOException, XmlPullParserException {
        int innerDepth;
        ParseResult result;
        if (SDK_VERSION > 0) {
            boolean isApkInApex = (flags & 512) != 0;
            TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesSdk);
            int minVers = 1;
            String minCode = null;
            boolean minAssigned = false;
            int targetVers = 0;
            String targetCode = null;
            int maxVers = Integer.MAX_VALUE;
            try {
                TypedValue val = sa.peekValue(0);
                if (val != null) {
                    if (val.type != 3 || val.string == null) {
                        minVers = val.data;
                        minAssigned = true;
                    } else {
                        minCode = val.string.toString();
                        minAssigned = !TextUtils.isEmpty(minCode);
                    }
                }
                TypedValue val2 = sa.peekValue(1);
                if (val2 == null) {
                    targetVers = minVers;
                    targetCode = minCode;
                } else if (val2.type != 3 || val2.string == null) {
                    targetVers = val2.data;
                } else {
                    targetCode = val2.string.toString();
                    if (!minAssigned) {
                        minCode = targetCode;
                    }
                }
                if (isApkInApex && (val2 = sa.peekValue(2)) != null) {
                    maxVers = val2.data;
                }
                ParseResult<Integer> targetSdkVersionResult =
                        FrameworkParsingPackageUtils.computeTargetSdkVersion(
                                targetVers, targetCode, SDK_CODENAMES, input, isApkInApex);
                if (targetSdkVersionResult.isError()) {
                    return input.error(targetSdkVersionResult);
                }
                int type = targetSdkVersionResult.getResult().intValue();
                ParseResult<?> deferResult = input.enableDeferredError(pkg.getPackageName(), type);
                if (deferResult.isError()) {
                    return input.error(deferResult);
                }
                ParseResult<Integer> minSdkVersionResult =
                        FrameworkParsingPackageUtils.computeMinSdkVersion(
                                minVers, minCode, SDK_VERSION, SDK_CODENAMES, input);
                if (minSdkVersionResult.isError()) {
                    return input.error(minSdkVersionResult);
                }
                int minSdkVersion = minSdkVersionResult.getResult().intValue();
                pkg.setMinSdkVersion(minSdkVersion).setTargetSdkVersion(type);
                if (isApkInApex) {
                    ParseResult<Integer> maxSdkVersionResult =
                            FrameworkParsingPackageUtils.computeMaxSdkVersion(
                                    maxVers, SDK_VERSION, input);
                    if (maxSdkVersionResult.isError()) {
                        return input.error(maxSdkVersionResult);
                    }
                    int maxSdkVersion = maxSdkVersionResult.getResult().intValue();
                    pkg.setMaxSdkVersion(maxSdkVersion);
                }
                int innerDepth2 = parser.getDepth();
                SparseIntArray minExtensionVersions = null;
                while (true) {
                    int targetSdkVersion = type;
                    int type2 = parser.next();
                    ParseResult<Integer> minSdkVersionResult2 = minSdkVersionResult;
                    if (type2 == 1) {
                        break;
                    }
                    if (type2 == 3 && parser.getDepth() <= innerDepth2) {
                        break;
                    }
                    if (type2 == 3) {
                        innerDepth = innerDepth2;
                    } else if (type2 == 4) {
                        innerDepth = innerDepth2;
                    } else {
                        int innerDepth3 = innerDepth2;
                        if (parser.getName().equals("extension-sdk")) {
                            SparseIntArray minExtensionVersions2 =
                                    minExtensionVersions == null
                                            ? new SparseIntArray()
                                            : minExtensionVersions;
                            result = parseExtensionSdk(input, res, parser, minExtensionVersions2);
                            XmlUtils.skipCurrentTag(parser);
                            minExtensionVersions = minExtensionVersions2;
                        } else {
                            result = ParsingUtils.unknownTag("<uses-sdk>", pkg, parser, input);
                        }
                        if (result.isError()) {
                            return input.error((ParseResult<?>) result);
                        }
                        type = targetSdkVersion;
                        innerDepth2 = innerDepth3;
                        minSdkVersionResult = minSdkVersionResult2;
                    }
                    type = targetSdkVersion;
                    innerDepth2 = innerDepth;
                    minSdkVersionResult = minSdkVersionResult2;
                }
                pkg.setMinExtensionVersions(exactSizedCopyOfSparseArray(minExtensionVersions));
            } finally {
                sa.recycle();
            }
        }
        return input.success(pkg);
    }

    private static SparseIntArray exactSizedCopyOfSparseArray(SparseIntArray input) {
        if (input == null) {
            return null;
        }
        SparseIntArray output = new SparseIntArray(input.size());
        for (int i = 0; i < input.size(); i++) {
            output.put(input.keyAt(i), input.valueAt(i));
        }
        return output;
    }

    private static ParseResult<SparseIntArray> parseExtensionSdk(
            ParseInput input,
            Resources res,
            XmlResourceParser parser,
            SparseIntArray minExtensionVersions) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestExtensionSdk);
        try {
            int sdkVersion = sa.getInt(0, -1);
            int minVersion = sa.getInt(1, -1);
            sa.recycle();
            if (sdkVersion < 0) {
                return input.error(-108, "<extension-sdk> must specify an sdkVersion >= 0");
            }
            if (minVersion < 0) {
                return input.error(-108, "<extension-sdk> must specify minExtensionVersion >= 0");
            }
            try {
                int version = SdkExtensions.getExtensionVersion(sdkVersion);
                if (version < minVersion) {
                    return input.error(
                            -12,
                            "Package requires "
                                    + sdkVersion
                                    + " extension version "
                                    + minVersion
                                    + " which exceeds device version "
                                    + version);
                }
                minExtensionVersions.put(sdkVersion, minVersion);
                return input.success(minExtensionVersions);
            } catch (RuntimeException e) {
                return input.error(-108, "Specified sdkVersion " + sdkVersion + " is not valid");
            }
        } catch (Throwable th) {
            sa.recycle();
            throw th;
        }
    }

    private static ParseResult<ParsingPackage> parseRestrictUpdateHash(
            int flags,
            ParseInput input,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser) {
        if ((flags & 16) != 0) {
            TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestRestrictUpdate);
            try {
                String hash = sa.getNonConfigurationString(0, 0);
                if (hash != null) {
                    int hashLength = hash.length();
                    byte[] hashBytes = new byte[hashLength / 2];
                    for (int i = 0; i < hashLength; i += 2) {
                        hashBytes[i / 2] =
                                (byte)
                                        ((Character.digit(hash.charAt(i), 16) << 4)
                                                + Character.digit(hash.charAt(i + 1), 16));
                    }
                    pkg.setRestrictUpdateHash(hashBytes);
                } else {
                    pkg.setRestrictUpdateHash(null);
                }
            } finally {
                sa.recycle();
            }
        }
        return input.success(pkg);
    }

    private static ParseResult<ParsingPackage> parseInstallConstraints(
            ParseInput input,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            Set<String> allowlist)
            throws IOException, XmlPullParserException {
        return InstallConstraintsTagParser.parseInstallConstraints(
                input, pkg, res, parser, allowlist);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x01e5, code lost:

       return r20.success(r21);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseQueries(
                    android.content.pm.parsing.result.ParseInput r20,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r21,
                    android.content.res.Resources r22,
                    android.content.res.XmlResourceParser r23)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseQueries(android.content.pm.parsing.result.ParseInput,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources,"
                    + " android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:157:0x062b, code lost:

       if (com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils.useLegacyRuntimeManifest(r33.getMetaData()) != false) goto L277;
    */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x062d, code lost:

       com.samsung.android.core.pm.runtimemanifest.RuntimeManifestOverlayUtils.applyRuntimeManifestIfNeeded(r33, r34);
    */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0638, code lost:

       if (android.text.TextUtils.isEmpty(r33.getStaticSharedLibraryName()) == false) goto L286;
    */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0642, code lost:

       if (android.text.TextUtils.isEmpty(r33.getSdkLibraryName()) == false) goto L286;
    */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0644, code lost:

       r1 = generateAppDetailsHiddenActivity(r32, r33);
    */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x064c, code lost:

       if (r1.isError() == false) goto L285;
    */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0652, code lost:

       return r10.error(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0653, code lost:

       r9.addActivity(r1.getResult());
    */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x065c, code lost:

       if (r0 == false) goto L288;
    */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x065e, code lost:

       r33.sortActivities();
    */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0661, code lost:

       if (r19 == false) goto L290;
    */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0663, code lost:

       r33.sortReceivers();
    */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0666, code lost:

       if (r21 == false) goto L292;
    */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0668, code lost:

       r33.sortServices();
    */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x066b, code lost:

       afterParseBaseApplication(r9);
    */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0672, code lost:

       return r32.success(r33);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.parsing.ParsingPackage>
            parseBaseApplication(
                    android.content.pm.parsing.result.ParseInput r32,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r33,
                    android.content.res.Resources r34,
                    android.content.res.XmlResourceParser r35,
                    int r36,
                    boolean r37)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1718
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseBaseApplication(android.content.pm.parsing.result.ParseInput,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser, int,"
                    + " boolean):android.content.pm.parsing.result.ParseResult");
    }

    private void afterParseBaseApplication(ParsingPackage pkg) {
        setMaxAspectRatio(pkg);
        setMinAspectRatio(pkg);
        setSupportsSizeChanges(pkg);
        pkg.setHasDomainUrls(hasDomainURLs(pkg));
    }

    private void parseBaseAppBasicFlags(ParsingPackage pkg, TypedArray sa) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int targetSdk = pkg.getTargetSdkVersion();
        ParsingPackage onBackInvokedCallbackEnabled =
                pkg.setBackupAllowed(bool(true, 17, sa))
                        .setClearUserDataAllowed(bool(true, 5, sa))
                        .setClearUserDataOnFailedRestoreAllowed(bool(true, 54, sa))
                        .setAllowNativeHeapPointerTagging(bool(true, 59, sa))
                        .setEnabled(bool(true, 9, sa))
                        .setExtractNativeLibrariesRequested(bool(true, 34, sa))
                        .setDeclaredHavingCode(bool(true, 7, sa))
                        .setTaskReparentingAllowed(bool(false, 14, sa))
                        .setSaveStateDisallowed(bool(false, 47, sa))
                        .setCrossProfile(bool(false, 58, sa))
                        .setDebuggable(bool(false, 10, sa))
                        .setDefaultToDeviceProtectedStorage(bool(false, 38, sa))
                        .setDirectBootAware(bool(false, 39, sa))
                        .setForceQueryable(bool(false, 57, sa))
                        .setGame(bool(false, 31, sa))
                        .setUserDataFragile(bool(false, 50, sa))
                        .setLargeHeap(bool(false, 24, sa))
                        .setMultiArch(bool(false, 33, sa))
                        .setPreserveLegacyExternalStorage(bool(false, 61, sa))
                        .setRequiredForAllUsers(bool(false, 27, sa))
                        .setRtlSupported(bool(false, 26, sa))
                        .setTestOnly(bool(false, 15, sa))
                        .setUseEmbeddedDex(bool(false, 53, sa))
                        .setNonSdkApiRequested(bool(false, 49, sa))
                        .setVmSafeMode(bool(false, 20, sa))
                        .setAutoRevokePermissions(anInt(60, sa))
                        .setAttributionsAreUserVisible(bool(false, 69, sa))
                        .setResetEnabledSettingsOnAppDataCleared(bool(false, 70, sa))
                        .setOnBackInvokedCallbackEnabled(bool(false, 73, sa));
        if (targetSdk >= 29) {
            z = true;
        } else {
            z = false;
        }
        ParsingPackage allowAudioPlaybackCapture =
                onBackInvokedCallbackEnabled.setAllowAudioPlaybackCapture(bool(z, 55, sa));
        if (targetSdk >= 14) {
            z2 = true;
        } else {
            z2 = false;
        }
        ParsingPackage hardwareAccelerated =
                allowAudioPlaybackCapture.setHardwareAccelerated(bool(z2, 23, sa));
        if (targetSdk < 29) {
            z3 = true;
        } else {
            z3 = false;
        }
        ParsingPackage requestLegacyExternalStorage =
                hardwareAccelerated.setRequestLegacyExternalStorage(bool(z3, 56, sa));
        if (targetSdk < 28) {
            z4 = true;
        } else {
            z4 = false;
        }
        requestLegacyExternalStorage
                .setCleartextTrafficAllowed(bool(z4, 36, sa))
                .setUiOptions(anInt(25, sa))
                .setCategory(anInt(-1, 43, sa))
                .setMaxAspectRatio(aFloat(44, sa))
                .setMinAspectRatio(aFloat(51, sa))
                .setBannerResourceId(resId(30, sa))
                .setDescriptionResourceId(resId(13, sa))
                .setIconResourceId(resId(2, sa))
                .setLogoResourceId(resId(22, sa))
                .setNetworkSecurityConfigResourceId(resId(41, sa))
                .setRoundIconResourceId(resId(42, sa))
                .setThemeResourceId(resId(0, sa))
                .setDataExtractionRulesResourceId(resId(66, sa))
                .setLocaleConfigResourceId(resId(71, sa))
                .setClassLoaderName(string(46, sa))
                .setRequiredAccountType(string(29, sa))
                .setRestrictedAccountType(string(28, sa))
                .setZygotePreloadName(string(52, sa))
                .setPermission(nonConfigString(0, 6, sa))
                .setAllowCrossUidActivitySwitchFromBelow(bool(true, 74, sa));
    }

    private ParseResult parseBaseAppChildTag(
            ParseInput input,
            String tag,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            int flags)
            throws IOException, XmlPullParserException {
        char c;
        switch (tag.hashCode()) {
            case -1803294168:
                if (tag.equals("sdk-library")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (tag.equals("uses-native-library")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1521117785:
                if (tag.equals("uses-sdk-library")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (tag.equals("uses-library")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (tag.equals("meta-data")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1094759587:
                if (tag.equals("processes")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1056667556:
                if (tag.equals("static-library")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (tag.equals("property")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 8960125:
                if (tag.equals("uses-static-library")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 166208699:
                if (tag.equals("library")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 178070147:
                if (tag.equals("profileable")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1964930885:
                if (tag.equals("uses-package")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                ParseResult<PackageManager.Property> metaDataResult =
                        parseMetaData(pkg, null, res, parser, "<meta-data>", input);
                if (metaDataResult.isSuccess() && metaDataResult.getResult() != null) {
                    pkg.setMetaData(metaDataResult.getResult().toBundle(pkg.getMetaData()));
                }
                return metaDataResult;
            case 1:
                ParseResult<PackageManager.Property> propertyResult =
                        parseMetaData(pkg, null, res, parser, "<property>", input);
                if (propertyResult.isSuccess()) {
                    pkg.addProperty(propertyResult.getResult());
                }
                return propertyResult;
            case 2:
                return parseSdkLibrary(pkg, res, parser, input);
            case 3:
                return parseStaticLibrary(pkg, res, parser, input);
            case 4:
                return parseLibrary(pkg, res, parser, input);
            case 5:
                return parseUsesSdkLibrary(input, pkg, res, parser);
            case 6:
                return parseUsesStaticLibrary(input, pkg, res, parser);
            case 7:
                return parseUsesLibrary(input, pkg, res, parser);
            case '\b':
                return parseUsesNativeLibrary(input, pkg, res, parser);
            case '\t':
                return parseProcesses(input, pkg, res, parser, this.mSeparateProcesses, flags);
            case '\n':
                return input.success(null);
            case 11:
                return parseProfileable(input, pkg, res, parser);
            default:
                return ParsingUtils.unknownTag("<application>", pkg, parser, input);
        }
    }

    private static ParseResult<ParsingPackage> parseSdkLibrary(
            ParsingPackage pkg, Resources res, XmlResourceParser parser, ParseInput input) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestSdkLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            int versionMajor = sa.getInt(1, -1);
            if (lname != null && versionMajor >= 0) {
                return pkg.getSharedUserId() != null
                        ? input.error(-107, "sharedUserId not allowed in SDK library")
                        : pkg.getSdkLibraryName() != null
                                ? input.error("Multiple SDKs for package " + pkg.getPackageName())
                                : input.success(
                                        pkg.setSdkLibraryName(lname.intern())
                                                .setSdkLibVersionMajor(versionMajor)
                                                .setSdkLibrary(true));
            }
            return input.error(
                    "Bad sdk-library declaration name: " + lname + " version: " + versionMajor);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseStaticLibrary(
            ParsingPackage pkg, Resources res, XmlResourceParser parser, ParseInput input) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestStaticLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            int version = sa.getInt(1, -1);
            int versionMajor = sa.getInt(2, 0);
            if (lname != null && version >= 0) {
                return pkg.getSharedUserId() != null
                        ? input.error(-107, "sharedUserId not allowed in static shared library")
                        : pkg.getStaticSharedLibraryName() != null
                                ? input.error(
                                        "Multiple static-shared libs for package "
                                                + pkg.getPackageName())
                                : input.success(
                                        pkg.setStaticSharedLibraryName(lname.intern())
                                                .setStaticSharedLibraryVersion(
                                                        PackageInfo.composeLongVersionCode(
                                                                versionMajor, version))
                                                .setStaticSharedLibrary(true));
            }
            return input.error(
                    "Bad static-library declaration name: " + lname + " version: " + version);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseLibrary(
            ParsingPackage pkg, Resources res, XmlResourceParser parser, ParseInput input) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            if (lname != null) {
                String lname2 = lname.intern();
                if (!ArrayUtils.contains(pkg.getLibraryNames(), lname2)) {
                    pkg.addLibraryName(lname2);
                }
            }
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesSdkLibrary(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesSdkLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            int versionMajor = sa.getInt(2, -1);
            String certSha256Digest = sa.getNonResourceString(1);
            boolean optional = sa.getBoolean(3, false);
            if (lname != null && versionMajor >= 0 && certSha256Digest != null) {
                List<String> usesSdkLibraries = pkg.getUsesSdkLibraries();
                if (usesSdkLibraries.contains(lname)) {
                    return input.error("Depending on multiple versions of SDK library " + lname);
                }
                String lname2 = lname.intern();
                String certSha256Digest2 = certSha256Digest.replace(":", "").toLowerCase();
                if ("".equals(certSha256Digest2)) {
                    String certSha256Digest3 =
                            SystemProperties.get(
                                    "debug.pm.uses_sdk_library_default_cert_digest", "");
                    try {
                        HexEncoding.decode(certSha256Digest3, false);
                        certSha256Digest2 = certSha256Digest3;
                    } catch (IllegalArgumentException e) {
                        certSha256Digest2 = "";
                    }
                }
                ParseResult<String[]> certResult = parseAdditionalCertificates(input, res, parser);
                if (certResult.isError()) {
                    return input.error((ParseResult<?>) certResult);
                }
                String[] additionalCertSha256Digests = certResult.getResult();
                String[] certSha256Digests = new String[additionalCertSha256Digests.length + 1];
                certSha256Digests[0] = certSha256Digest2;
                System.arraycopy(
                        additionalCertSha256Digests,
                        0,
                        certSha256Digests,
                        1,
                        additionalCertSha256Digests.length);
                return input.success(
                        pkg.addUsesSdkLibrary(lname2, versionMajor, certSha256Digests, optional));
            }
            return input.error(
                    "Bad uses-sdk-library declaration name: "
                            + lname
                            + " version: "
                            + versionMajor
                            + " certDigest"
                            + certSha256Digest);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesStaticLibrary(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesStaticLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            int version = sa.getInt(1, -1);
            String certSha256Digest = sa.getNonResourceString(2);
            if (lname != null && version >= 0 && certSha256Digest != null) {
                List<String> usesStaticLibraries = pkg.getUsesStaticLibraries();
                if (usesStaticLibraries.contains(lname)) {
                    return input.error("Depending on multiple versions of static library " + lname);
                }
                String lname2 = lname.intern();
                String certSha256Digest2 = certSha256Digest.replace(":", "").toLowerCase();
                String[] additionalCertSha256Digests = EmptyArray.STRING;
                if (pkg.getTargetSdkVersion() >= 27) {
                    ParseResult<String[]> certResult =
                            parseAdditionalCertificates(input, res, parser);
                    if (certResult.isError()) {
                        return input.error((ParseResult<?>) certResult);
                    }
                    additionalCertSha256Digests = certResult.getResult();
                }
                String[] certSha256Digests = new String[additionalCertSha256Digests.length + 1];
                certSha256Digests[0] = certSha256Digest2;
                System.arraycopy(
                        additionalCertSha256Digests,
                        0,
                        certSha256Digests,
                        1,
                        additionalCertSha256Digests.length);
                return input.success(pkg.addUsesStaticLibrary(lname2, version, certSha256Digests));
            }
            return input.error(
                    "Bad uses-static-library declaration name: "
                            + lname
                            + " version: "
                            + version
                            + " certDigest"
                            + certSha256Digest);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesLibrary(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            boolean req = sa.getBoolean(1, true);
            if (lname != null) {
                String lname2 = lname.intern();
                if (req) {
                    pkg.addUsesLibrary(lname2).removeUsesOptionalLibrary(lname2);
                } else if (!ArrayUtils.contains(pkg.getUsesLibraries(), lname2)) {
                    pkg.addUsesOptionalLibrary(lname2);
                }
            }
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseUsesNativeLibrary(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestUsesNativeLibrary);
        try {
            String lname = sa.getNonResourceString(0);
            boolean req = sa.getBoolean(1, true);
            if (lname != null) {
                if (req) {
                    pkg.addUsesNativeLibrary(lname).removeUsesOptionalNativeLibrary(lname);
                } else if (!ArrayUtils.contains(pkg.getUsesNativeLibraries(), lname)) {
                    pkg.addUsesOptionalNativeLibrary(lname);
                }
            }
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseProcesses(
            ParseInput input,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            String[] separateProcesses,
            int flags)
            throws IOException, XmlPullParserException {
        ParseResult<ArrayMap<String, ParsedProcess>> result =
                ParsedProcessUtils.parseProcesses(
                        separateProcesses, pkg, res, parser, flags, input);
        if (result.isError()) {
            return input.error(result);
        }
        return input.success(pkg.setProcesses(result.getResult()));
    }

    private static ParseResult<ParsingPackage> parseProfileable(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        boolean z;
        ParsingPackage newPkg;
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestProfileable);
        try {
            boolean z2 = false;
            if (!pkg.isProfileableByShell() && !bool(false, 1, sa)) {
                z = false;
                newPkg = pkg.setProfileableByShell(z);
                if (newPkg.isProfileable() && bool(true, 0, sa)) {
                    z2 = true;
                }
                return input.success(newPkg.setProfileable(z2));
            }
            z = true;
            newPkg = pkg.setProfileableByShell(z);
            if (newPkg.isProfileable()) {
                z2 = true;
            }
            return input.success(newPkg.setProfileable(z2));
        } finally {
            sa.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0080, code lost:

       return r8.success(r0);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<java.lang.String[]>
            parseAdditionalCertificates(
                    android.content.pm.parsing.result.ParseInput r8,
                    android.content.res.Resources r9,
                    android.content.res.XmlResourceParser r10)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            java.lang.String[] r0 = libcore.util.EmptyArray.STRING
            int r1 = r10.getDepth()
        L6:
            int r2 = r10.next()
            r3 = r2
            r4 = 1
            if (r2 == r4) goto L7c
            r2 = 3
            if (r3 != r2) goto L17
            int r2 = r10.getDepth()
            if (r2 <= r1) goto L7c
        L17:
            r2 = 2
            if (r3 == r2) goto L1b
            goto L6
        L1b:
            com.android.internal.pm.pkg.component.AconfigFlags r2 = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sAconfigFlags
            boolean r2 = r2.skipCurrentElement(r10)
            if (r2 == 0) goto L24
            goto L6
        L24:
            java.lang.String r2 = r10.getName()
            java.lang.String r4 = "additional-certificate"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L7b
            int[] r4 = com.android.internal.R.styleable.AndroidManifestAdditionalCertificate
            android.content.res.TypedArray r4 = r9.obtainAttributes(r10, r4)
            r5 = 0
            java.lang.String r5 = r4.getNonResourceString(r5)     // Catch: java.lang.Throwable -> L76
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Throwable -> L76
            if (r6 == 0) goto L5c
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L76
            r6.<init>()     // Catch: java.lang.Throwable -> L76
            java.lang.String r7 = "Bad additional-certificate declaration with empty certDigest:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> L76
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch: java.lang.Throwable -> L76
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L76
            android.content.pm.parsing.result.ParseResult r6 = r8.error(r6)     // Catch: java.lang.Throwable -> L76
            r4.recycle()
            return r6
        L5c:
            java.lang.String r6 = ":"
            java.lang.String r7 = ""
            java.lang.String r6 = r5.replace(r6, r7)     // Catch: java.lang.Throwable -> L76
            java.lang.String r6 = r6.toLowerCase()     // Catch: java.lang.Throwable -> L76
            r5 = r6
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            java.lang.Object[] r6 = com.android.internal.util.ArrayUtils.appendElement(r6, r0, r5)     // Catch: java.lang.Throwable -> L76
            java.lang.String[] r6 = (java.lang.String[]) r6     // Catch: java.lang.Throwable -> L76
            r0 = r6
            r4.recycle()
            goto L7b
        L76:
            r5 = move-exception
            r4.recycle()
            throw r5
        L7b:
            goto L6
        L7c:
            android.content.pm.parsing.result.ParseResult r2 = r8.success(r0)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseAdditionalCertificates(android.content.pm.parsing.result.ParseInput,"
                    + " android.content.res.Resources,"
                    + " android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedActivity> generateAppDetailsHiddenActivity(
            ParseInput input, ParsingPackage pkg) {
        String packageName = pkg.getPackageName();
        ParseResult<String> result =
                ComponentParseUtils.buildTaskAffinityName(
                        packageName, packageName, ":app_details", input);
        if (result.isError()) {
            return input.error(result);
        }
        String taskAffinity = result.getResult();
        return input.success(
                ParsedActivityImpl.makeAppDetailsActivity(
                        packageName,
                        pkg.getProcessName(),
                        pkg.getUiOptions(),
                        taskAffinity,
                        pkg.isHardwareAccelerated()));
    }

    private static boolean hasDomainURLs(ParsingPackage pkg) {
        List<ParsedActivity> activities = pkg.getActivities();
        int activitiesSize = activities.size();
        for (int index = 0; index < activitiesSize; index++) {
            ParsedActivity activity = activities.get(index);
            List<ParsedIntentInfo> filters = activity.getIntents();
            int filtersSize = filters.size();
            for (int filtersIndex = 0; filtersIndex < filtersSize; filtersIndex++) {
                IntentFilter aii = filters.get(filtersIndex).getIntentFilter();
                if (aii.hasAction("android.intent.action.VIEW")
                        && aii.hasAction("android.intent.action.VIEW")
                        && (aii.hasDataScheme(IntentFilter.SCHEME_HTTP)
                                || aii.hasDataScheme(IntentFilter.SCHEME_HTTPS))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setMaxAspectRatio(ParsingPackage pkg) {
        float maxAspectRatio = pkg.getTargetSdkVersion() < 26 ? 1.86f : 0.0f;
        float packageMaxAspectRatio = pkg.getMaxAspectRatio();
        if (packageMaxAspectRatio != 0.0f) {
            maxAspectRatio = packageMaxAspectRatio;
        } else {
            Bundle appMetaData = pkg.getMetaData();
            if (appMetaData != null && appMetaData.containsKey("android.max_aspect")) {
                maxAspectRatio = appMetaData.getFloat("android.max_aspect", maxAspectRatio);
            }
        }
        List<ParsedActivity> activities = pkg.getActivities();
        int activitiesSize = activities.size();
        for (int index = 0; index < activitiesSize; index++) {
            ParsedActivity activity = activities.get(index);
            if (activity.getMaxAspectRatio() == -1.0f) {
                float activityAspectRatio =
                        activity.getMetaData().getFloat("android.max_aspect", maxAspectRatio);
                ComponentMutateUtils.setMaxAspectRatio(
                        activity, activity.getResizeMode(), activityAspectRatio);
            }
        }
    }

    private void setMinAspectRatio(ParsingPackage pkg) {
        float minAspectRatio = pkg.getMinAspectRatio();
        List<ParsedActivity> activities = pkg.getActivities();
        int activitiesSize = activities.size();
        for (int index = 0; index < activitiesSize; index++) {
            ParsedActivity activity = activities.get(index);
            if (activity.getMinAspectRatio() == -1.0f) {
                ComponentMutateUtils.setMinAspectRatio(
                        activity, activity.getResizeMode(), minAspectRatio);
            }
        }
    }

    private void setSupportsSizeChanges(ParsingPackage pkg) {
        Bundle appMetaData = pkg.getMetaData();
        boolean supportsSizeChanges =
                appMetaData != null
                        && appMetaData.getBoolean("android.supports_size_changes", false);
        List<ParsedActivity> activities = pkg.getActivities();
        int activitiesSize = activities.size();
        for (int index = 0; index < activitiesSize; index++) {
            ParsedActivity activity = activities.get(index);
            if (supportsSizeChanges
                    || activity.getMetaData().getBoolean("android.supports_size_changes", false)) {
                ComponentMutateUtils.setSupportsSizeChanges(activity, true);
            }
        }
    }

    private static ParseResult<ParsingPackage> parseOverlay(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestResourceOverlay);
        try {
            String target = sa.getString(1);
            int priority = anInt(0, 0, sa);
            if (target == null) {
                return input.error("<overlay> does not specify a target package");
            }
            if (priority < 0 || priority > 9999) {
                return input.error("<overlay> priority must be between 0 and 9999");
            }
            String propName = sa.getString(5);
            String propValue = sa.getString(6);
            if (FrameworkParsingPackageUtils.checkRequiredSystemProperties(propName, propValue)) {
                return input.success(
                        pkg.setResourceOverlay(true)
                                .setOverlayTarget(target)
                                .setOverlayPriority(priority)
                                .setOverlayTargetOverlayableName(sa.getString(3))
                                .setOverlayCategory(sa.getString(2))
                                .setOverlayIsStatic(bool(false, 4, sa)));
            }
            String message =
                    "Skipping target and overlay pair "
                            + target
                            + " and "
                            + pkg.getBaseApkPath()
                            + ": overlay ignored due to required system property: "
                            + propName
                            + " with value: "
                            + propValue;
            Slog.i("PackageParsing", message);
            return input.skip(message);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseProtectedBroadcast(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestProtectedBroadcast);
        try {
            String name = nonResString(0, sa);
            if (name != null) {
                pkg.addProtectedBroadcast(name);
            }
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseSupportScreens(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestSupportsScreens);
        try {
            int requiresSmallestWidthDp = anInt(0, 6, sa);
            int compatibleWidthLimitDp = anInt(0, 7, sa);
            int largestWidthLimitDp = anInt(0, 8, sa);
            return input.success(
                    pkg.setSmallScreensSupported(anInt(1, 1, sa))
                            .setNormalScreensSupported(anInt(1, 2, sa))
                            .setLargeScreensSupported(anInt(1, 3, sa))
                            .setExtraLargeScreensSupported(anInt(1, 5, sa))
                            .setResizeable(anInt(1, 4, sa))
                            .setAnyDensity(anInt(1, 0, sa))
                            .setRequiresSmallestWidthDp(requiresSmallestWidthDp)
                            .setCompatibleWidthLimitDp(compatibleWidthLimitDp)
                            .setLargestWidthLimitDp(largestWidthLimitDp));
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseInstrumentation(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        ParseResult<ParsedInstrumentation> result =
                ParsedInstrumentationUtils.parseInstrumentation(
                        pkg, res, parser, sUseRoundIcon, input);
        if (result.isError()) {
            return input.error(result);
        }
        return input.success(pkg.addInstrumentation(result.getResult()));
    }

    private static ParseResult<ParsingPackage> parseOriginalPackage(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestOriginalPackage);
        try {
            String orig = sa.getNonConfigurationString(0, 0);
            if (!pkg.getPackageName().equals(orig)) {
                pkg.addOriginalPackage(orig);
            }
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsingPackage> parseAdoptPermissions(
            ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestOriginalPackage);
        try {
            String name = nonConfigString(0, 0, sa);
            if (name != null) {
                pkg.addAdoptPermission(name);
            }
            return input.success(pkg);
        } finally {
            sa.recycle();
        }
    }

    private static void convertCompatPermissions(ParsingPackage pkg) {
        int size = CompatibilityPermissionInfo.COMPAT_PERMS.length;
        for (int i = 0; i < size; i++) {
            CompatibilityPermissionInfo info = CompatibilityPermissionInfo.COMPAT_PERMS[i];
            if (pkg.getTargetSdkVersion() < info.getSdkVersion()) {
                if (!pkg.getRequestedPermissions().contains(info.getName())) {
                    pkg.addImplicitPermission(info.getName());
                }
            } else {
                return;
            }
        }
    }

    private void convertSplitPermissions(ParsingPackage pkg) {
        int listSize = this.mSplitPermissionInfos.size();
        for (int is = 0; is < listSize; is++) {
            PermissionManager.SplitPermissionInfo spi = this.mSplitPermissionInfos.get(is);
            Set<String> requestedPermissions = pkg.getRequestedPermissions();
            if (pkg.getTargetSdkVersion() < spi.getTargetSdk()
                    && requestedPermissions.contains(spi.getSplitPermission())) {
                List<String> newPerms = spi.getNewPermissions();
                for (int in = 0; in < newPerms.size(); in++) {
                    String perm = newPerms.get(in);
                    if (!requestedPermissions.contains(perm)) {
                        pkg.addImplicitPermission(perm);
                    }
                }
            }
        }
    }

    private static void adjustPackageToBeUnresizeableAndUnpipable(ParsingPackage pkg) {
        List<ParsedActivity> activities = pkg.getActivities();
        int activitiesSize = activities.size();
        for (int index = 0; index < activitiesSize; index++) {
            ParsedActivity activity = activities.get(index);
            ComponentMutateUtils.setResizeMode(activity, 0);
            ComponentMutateUtils.setExactFlags(activity, activity.getFlags() & (-4194305));
        }
    }

    public static ParseResult<PackageManager.Property> parseMetaData(
            ParsingPackage pkg,
            ParsedComponent component,
            Resources res,
            XmlResourceParser parser,
            String tagName,
            ParseInput input) {
        PackageManager.Property property;
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestMetaData);
        try {
            String name = TextUtils.safeIntern(nonConfigString(0, 0, sa));
            if (name == null) {
                return input.error(tagName + " requires an android:name attribute");
            }
            String packageName = pkg.getPackageName();
            String className = component != null ? component.getName() : null;
            TypedValue v = sa.peekValue(2);
            if (v == null || v.resourceId == 0) {
                TypedValue v2 = sa.peekValue(1);
                if (v2 == null) {
                    return input.error(
                            tagName + " requires an android:value or android:resource attribute");
                }
                if (v2.type == 3) {
                    CharSequence cs = v2.coerceToString();
                    String stringValue = cs != null ? cs.toString() : null;
                    property =
                            new PackageManager.Property(name, stringValue, packageName, className);
                } else if (v2.type == 18) {
                    property =
                            new PackageManager.Property(name, v2.data != 0, packageName, className);
                } else if (v2.type >= 16 && v2.type <= 31) {
                    property =
                            new PackageManager.Property(
                                    name, v2.data, false, packageName, className);
                } else if (v2.type == 4) {
                    property =
                            new PackageManager.Property(
                                    name, v2.getFloat(), packageName, className);
                } else {
                    Slog.w(
                            "PackageParsing",
                            tagName
                                    + " only supports string, integer, float, color, boolean, and"
                                    + " resource reference types: "
                                    + parser.getName()
                                    + " at "
                                    + pkg.getBaseApkPath()
                                    + " "
                                    + parser.getPositionDescription());
                    property = null;
                }
            } else {
                property =
                        new PackageManager.Property(
                                name, v.resourceId, true, packageName, className);
            }
            return input.success(property);
        } finally {
            sa.recycle();
        }
    }

    public static ParseResult<SigningDetails> getSigningDetails(
            ParseInput input, ParsedPackage pkg, boolean skipVerify) {
        return getSigningDetails(
                input,
                pkg.getBaseApkPath(),
                pkg.isStaticSharedLibrary(),
                pkg.getTargetSdkVersion(),
                pkg.getSplitCodePaths(),
                skipVerify);
    }

    private static ParseResult<SigningDetails> getSigningDetails(
            ParseInput input, ParsingPackage pkg, boolean skipVerify) {
        return getSigningDetails(
                input,
                pkg.getBaseApkPath(),
                pkg.isStaticSharedLibrary(),
                pkg.getTargetSdkVersion(),
                pkg.getSplitCodePaths(),
                skipVerify);
    }

    public static ParseResult<SigningDetails> getSigningDetails(
            ParseInput input,
            String baseApkPath,
            boolean isStaticSharedLibrary,
            int targetSdkVersion,
            String[] splitCodePaths,
            boolean skipVerify) {
        File frameworkRes;
        SigningDetails signingDetails = SigningDetails.UNKNOWN;
        Trace.traceBegin(262144L, "collectCertificates");
        try {
            ParseResult<SigningDetails> result =
                    getSigningDetails(
                            input,
                            baseApkPath,
                            skipVerify,
                            isStaticSharedLibrary,
                            signingDetails,
                            targetSdkVersion);
            if (result.isError()) {
                ParseResult<SigningDetails> error = input.error(result);
                Trace.traceEnd(262144L);
                return error;
            }
            SigningDetails signingDetails2 = result.getResult();
            try {
                frameworkRes =
                        new File(Environment.getRootDirectory(), "framework/framework-res.apk");
            } catch (Throwable th) {
                th = th;
            }
            try {
                boolean isFrameworkResSplit = frameworkRes.getAbsolutePath().equals(baseApkPath);
                if (!ArrayUtils.isEmpty(splitCodePaths) && !isFrameworkResSplit) {
                    for (String str : splitCodePaths) {
                        result =
                                getSigningDetails(
                                        input,
                                        str,
                                        skipVerify,
                                        isStaticSharedLibrary,
                                        signingDetails2,
                                        targetSdkVersion);
                        if (result.isError()) {
                            ParseResult<SigningDetails> error2 = input.error(result);
                            Trace.traceEnd(262144L);
                            return error2;
                        }
                    }
                }
                Trace.traceEnd(262144L);
                return result;
            } catch (Throwable th2) {
                th = th2;
                Trace.traceEnd(262144L);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static ParseResult<SigningDetails> getSigningDetails(
            ParseInput input,
            String baseCodePath,
            boolean skipVerify,
            boolean isStaticSharedLibrary,
            SigningDetails existingSigningDetails,
            int targetSdk) {
        ParseResult<SigningDetails> verified;
        int minSignatureScheme =
                ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(targetSdk);
        if (isStaticSharedLibrary) {
            minSignatureScheme = 2;
        }
        if (skipVerify) {
            verified =
                    ApkSignatureVerifier.unsafeGetCertsWithoutVerification(
                            input, baseCodePath, minSignatureScheme);
        } else {
            verified = ApkSignatureVerifier.verify(input, baseCodePath, minSignatureScheme);
        }
        if (verified.isError()) {
            return input.error(verified);
        }
        if (existingSigningDetails == SigningDetails.UNKNOWN) {
            return verified;
        }
        if (!Signature.areExactMatch(existingSigningDetails, verified.getResult())) {
            return input.error(-104, baseCodePath + " has mismatched certificates");
        }
        return input.success(existingSigningDetails);
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
            ApplicationInfo androidAppInfo =
                    ActivityThread.getPackageManager()
                            .getApplicationInfo("android", 0L, UserHandle.myUserId());
            Resources systemResources = Resources.getSystem();
            Resources overlayableRes =
                    ResourcesManager.getInstance()
                            .getResources(
                                    null,
                                    null,
                                    null,
                                    androidAppInfo.resourceDirs,
                                    androidAppInfo.overlayPaths,
                                    androidAppInfo.sharedLibraryFiles,
                                    null,
                                    null,
                                    systemResources.getCompatibilityInfo(),
                                    systemResources.getClassLoader(),
                                    null);
            sUseRoundIcon = overlayableRes.getBoolean(R.bool.config_useRoundIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static boolean bool(boolean defaultValue, int attribute, TypedArray sa) {
        return sa.getBoolean(attribute, defaultValue);
    }

    private static float aFloat(float defaultValue, int attribute, TypedArray sa) {
        return sa.getFloat(attribute, defaultValue);
    }

    private static float aFloat(int attribute, TypedArray sa) {
        return sa.getFloat(attribute, 0.0f);
    }

    private static int anInt(int defaultValue, int attribute, TypedArray sa) {
        return sa.getInt(attribute, defaultValue);
    }

    private static int anInteger(int defaultValue, int attribute, TypedArray sa) {
        return sa.getInteger(attribute, defaultValue);
    }

    private static int anInt(int attribute, TypedArray sa) {
        return sa.getInt(attribute, 0);
    }

    private static int resId(int attribute, TypedArray sa) {
        return sa.getResourceId(attribute, 0);
    }

    private static String string(int attribute, TypedArray sa) {
        return sa.getString(attribute);
    }

    private static String nonConfigString(
            int allowedChangingConfigs, int attribute, TypedArray sa) {
        return sa.getNonConfigurationString(attribute, allowedChangingConfigs);
    }

    private static String nonResString(int index, TypedArray sa) {
        return sa.getNonResourceString(index);
    }

    public static void writeKeySetMapping(
            Parcel dest, Map<String, ArraySet<PublicKey>> keySetMapping) {
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
                    PublicKey pk =
                            (PublicKey)
                                    in.readSerializable(
                                            PublicKey.class.getClassLoader(), PublicKey.class);
                    keys.add(pk);
                }
                keySetMapping.put(key, keys);
            }
        }
        return keySetMapping;
    }

    public static AconfigFlags getAconfigFlags() {
        return sAconfigFlags;
    }
}
