package android.content.pm.parsing;

import android.Manifest;
import android.app.admin.DeviceAdminReceiver;
import android.content.pm.SigningDetails;
import android.content.pm.VerifierInfo;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.ApkAssets;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class ApkLiteParseUtils {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final String ANDROID_RES_NAMESPACE =
            "http://schemas.android.com/apk/res/android";
    public static final String APK_FILE_EXTENSION = ".apk";
    public static final int DEFAULT_MIN_SDK_VERSION = 1;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    private static final int PARSE_COLLECT_CERTIFICATES = 32;
    private static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    private static final int PARSE_IS_SYSTEM_DIR = 16;
    private static final String TAG = "ApkLiteParseUtils";
    private static final String TAG_APPLICATION = "application";
    private static final String TAG_MANIFEST = "manifest";
    private static final String TAG_OVERLAY = "overlay";
    private static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    private static final String TAG_PROCESS = "process";
    private static final String TAG_PROCESSES = "processes";
    private static final String TAG_PROFILEABLE = "profileable";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_SDK_LIBRARY = "sdk-library";
    private static final String TAG_USES_SDK = "uses-sdk";
    private static final String TAG_USES_SPLIT = "uses-split";
    private static final Comparator<String> sSplitNameComparator = new SplitNameComparator();
    private static final int SDK_VERSION = Build.VERSION.SDK_INT;
    private static final String[] SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;

    public static ParseResult<PackageLite> parsePackageLite(
            ParseInput input, File packageFile, int flags) {
        if (packageFile.isDirectory()) {
            return parseClusterPackageLite(input, packageFile, flags);
        }
        return parseMonolithicPackageLite(input, packageFile, flags);
    }

    public static ParseResult<PackageLite> parseMonolithicPackageLite(
            ParseInput input, File packageFile, int flags) {
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            ParseResult<ApkLite> result = parseApkLite(input, packageFile, flags);
            if (result.isError()) {
                return input.error(result);
            }
            ApkLite baseApk = result.getResult();
            String packagePath = packageFile.getAbsolutePath();
            return input.success(
                    new PackageLite(
                            packagePath,
                            baseApk.getPath(),
                            baseApk,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            baseApk.getTargetSdkVersion(),
                            null,
                            null));
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> parseMonolithicPackageLite(
            ParseInput input, FileDescriptor packageFd, String debugPathName, int flags) {
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            ParseResult<ApkLite> result = parseApkLite(input, packageFd, debugPathName, flags);
            if (result.isError()) {
                return input.error(result);
            }
            ApkLite baseApk = result.getResult();
            return input.success(
                    new PackageLite(
                            debugPathName,
                            baseApk.getPath(),
                            baseApk,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            baseApk.getTargetSdkVersion(),
                            null,
                            null));
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> parseClusterPackageLite(
            ParseInput input, File packageDir, int flags) {
        File[] files = packageDir.listFiles();
        if (ArrayUtils.isEmpty(files)) {
            return input.error(-100, "No packages found in split");
        }
        int i = 0;
        if (files.length == 1 && files[0].isDirectory()) {
            return parseClusterPackageLite(input, files[0], flags);
        }
        String packageName = null;
        int versionCode = 0;
        ArrayMap<String, ApkLite> apks = new ArrayMap<>();
        long j = 262144;
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            int length = files.length;
            while (i < length) {
                File file = files[i];
                if (isApkFile(file)) {
                    ParseResult<ApkLite> result = parseApkLite(input, file, flags);
                    if (result.isError()) {
                        ParseResult<PackageLite> error = input.error(result);
                        Trace.traceEnd(j);
                        return error;
                    }
                    ApkLite lite = result.getResult();
                    if (packageName == null) {
                        packageName = lite.getPackageName();
                        versionCode = lite.getVersionCode();
                    } else {
                        if (!packageName.equals(lite.getPackageName())) {
                            return input.error(
                                    -101,
                                    "Inconsistent package "
                                            + lite.getPackageName()
                                            + " in "
                                            + file
                                            + "; expected "
                                            + packageName);
                        }
                        if (versionCode != lite.getVersionCode()) {
                            return input.error(
                                    -101,
                                    "Inconsistent version "
                                            + lite.getVersionCode()
                                            + " in "
                                            + file
                                            + "; expected "
                                            + versionCode);
                        }
                    }
                    ApkLite prev = apks.put(lite.getSplitName(), lite);
                    if (prev != null) {
                        return input.error(
                                -101,
                                "Split name "
                                        + lite.getSplitName()
                                        + " defined more than once; most recent was "
                                        + file
                                        + ", previous was "
                                        + prev.getPath());
                    }
                }
                i++;
                j = 262144;
            }
            ApkLite baseApk = apks.remove(null);
            Trace.traceEnd(262144L);
            return composePackageLiteFromApks(input, packageDir, baseApk, apks);
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> composePackageLiteFromApks(
            ParseInput input,
            File packageDir,
            ApkLite baseApk,
            ArrayMap<String, ApkLite> splitApks) {
        return composePackageLiteFromApks(input, packageDir, baseApk, splitApks, false);
    }

    public static ParseResult<PackageLite> composePackageLiteFromApks(
            ParseInput input,
            File packageDir,
            ApkLite baseApk,
            ArrayMap<String, ApkLite> splitApks,
            boolean apkRenamed) {
        String[] splitNames;
        Set<String>[] requiredSplitTypes;
        Set<String>[] splitTypes;
        boolean[] isFeatureSplits;
        String[] usesSplitNames;
        String[] configForSplits;
        String[] splitCodePaths;
        int[] splitRevisionCodes;
        if (baseApk == null) {
            return input.error(-101, "Missing base APK in " + packageDir);
        }
        int size = ArrayUtils.size(splitApks);
        if (size <= 0) {
            splitNames = null;
            requiredSplitTypes = null;
            splitTypes = null;
            isFeatureSplits = null;
            usesSplitNames = null;
            configForSplits = null;
            splitCodePaths = null;
            splitRevisionCodes = null;
        } else {
            String[] splitNames2 = new String[size];
            Set<String>[] requiredSplitTypes2 = new Set[size];
            Set<String>[] splitTypes2 = new Set[size];
            boolean[] isFeatureSplits2 = new boolean[size];
            String[] usesSplitNames2 = new String[size];
            String[] configForSplits2 = new String[size];
            String[] splitCodePaths2 = new String[size];
            int[] splitRevisionCodes2 = new int[size];
            String[] splitNames3 = (String[]) splitApks.keySet().toArray(splitNames2);
            Arrays.sort(splitNames3, sSplitNameComparator);
            for (int i = 0; i < size; i++) {
                ApkLite apk = splitApks.get(splitNames3[i]);
                requiredSplitTypes2[i] = apk.getRequiredSplitTypes();
                splitTypes2[i] = apk.getSplitTypes();
                usesSplitNames2[i] = apk.getUsesSplitName();
                isFeatureSplits2[i] = apk.isFeatureSplit();
                configForSplits2[i] = apk.getConfigForSplit();
                splitCodePaths2[i] =
                        apkRenamed
                                ? new File(packageDir, splitNameToFileName(apk)).getAbsolutePath()
                                : apk.getPath();
                splitRevisionCodes2[i] = apk.getRevisionCode();
            }
            splitNames = splitNames3;
            requiredSplitTypes = requiredSplitTypes2;
            splitTypes = splitTypes2;
            isFeatureSplits = isFeatureSplits2;
            usesSplitNames = usesSplitNames2;
            configForSplits = configForSplits2;
            splitCodePaths = splitCodePaths2;
            splitRevisionCodes = splitRevisionCodes2;
        }
        String codePath = packageDir.getAbsolutePath();
        String baseCodePath =
                apkRenamed
                        ? new File(packageDir, splitNameToFileName(baseApk)).getAbsolutePath()
                        : baseApk.getPath();
        return input.success(
                new PackageLite(
                        codePath,
                        baseCodePath,
                        baseApk,
                        splitNames,
                        isFeatureSplits,
                        usesSplitNames,
                        configForSplits,
                        splitCodePaths,
                        splitRevisionCodes,
                        baseApk.getTargetSdkVersion(),
                        requiredSplitTypes,
                        splitTypes));
    }

    public static String splitNameToFileName(ApkLite apk) {
        Objects.requireNonNull(apk);
        String fileName = apk.getSplitName() == null ? "base" : "split_" + apk.getSplitName();
        return fileName + ".apk";
    }

    public static ParseResult<ApkLite> parseApkLite(ParseInput input, File apkFile, int flags) {
        return parseApkLiteInner(input, apkFile, null, null, flags);
    }

    public static ParseResult<ApkLite> parseApkLite(
            ParseInput input, FileDescriptor fd, String debugPathName, int flags) {
        return parseApkLiteInner(input, null, fd, debugPathName, flags);
    }

    private static ParseResult<ApkLite> parseApkLiteInner(
            ParseInput input, File apkFile, FileDescriptor fd, String debugPathName, int flags) {
        Exception e;
        ApkAssets apkAssets;
        XmlResourceParser parser;
        SigningDetails signingDetails;
        long j;
        String apkPath = fd != null ? debugPathName : apkFile.getAbsolutePath();
        XmlResourceParser parser2 = null;
        ApkAssets apkAssets2 = null;
        try {
            try {
                try {
                    apkAssets =
                            fd != null
                                    ? ApkAssets.loadFromFd(fd, debugPathName, 0, null)
                                    : ApkAssets.loadFromPath(apkPath);
                    try {
                        parser = apkAssets.openXml("AndroidManifest.xml");
                    } catch (IOException | RuntimeException | XmlPullParserException e2) {
                        e = e2;
                        apkAssets2 = apkAssets;
                    } catch (Throwable th) {
                        e = th;
                        apkAssets2 = apkAssets;
                    }
                } catch (IOException | RuntimeException | XmlPullParserException e3) {
                    e = e3;
                }
                try {
                    try {
                        if ((flags & 32) != 0) {
                            boolean skipVerify = (flags & 16) != 0;
                            Trace.traceBegin(262144L, "collectCertificates");
                            try {
                                j = 262144;
                            } catch (Throwable th2) {
                                th = th2;
                                j = 262144;
                            }
                            try {
                                ParseResult<SigningDetails> result =
                                        FrameworkParsingPackageUtils.getSigningDetails(
                                                input,
                                                apkFile.getAbsolutePath(),
                                                skipVerify,
                                                false,
                                                SigningDetails.UNKNOWN,
                                                0);
                                if (result.isError()) {
                                    input.setPackageNameForAudit(getPackageNameForAudit(parser));
                                    ParseResult<ApkLite> error = input.error(result);
                                    Trace.traceEnd(262144L);
                                    IoUtils.closeQuietly(parser);
                                    if (apkAssets != null) {
                                        try {
                                            apkAssets.close();
                                        } catch (Throwable th3) {
                                        }
                                    }
                                    return error;
                                }
                                SigningDetails signingDetails2 = result.getResult();
                                Trace.traceEnd(262144L);
                                signingDetails = signingDetails2;
                            } catch (Throwable th4) {
                                th = th4;
                                Trace.traceEnd(j);
                                throw th;
                            }
                        } else {
                            signingDetails = SigningDetails.UNKNOWN;
                        }
                        ParseResult<ApkLite> parseApkLite =
                                parseApkLite(input, apkPath, parser, signingDetails, flags);
                        IoUtils.closeQuietly(parser);
                        if (apkAssets != null) {
                            try {
                                apkAssets.close();
                            } catch (Throwable th5) {
                            }
                        }
                        return parseApkLite;
                    } catch (Throwable th6) {
                        e = th6;
                        apkAssets2 = apkAssets;
                        parser2 = parser;
                        IoUtils.closeQuietly(parser2);
                        if (apkAssets2 == null) {
                            throw e;
                        }
                        try {
                            apkAssets2.close();
                            throw e;
                        } catch (Throwable th7) {
                            throw e;
                        }
                    }
                } catch (IOException | RuntimeException | XmlPullParserException e4) {
                    e = e4;
                    apkAssets2 = apkAssets;
                    parser2 = parser;
                    Exception e5 = e;
                    Slog.w(TAG, "Failed to parse " + apkPath, e5);
                    ParseResult<ApkLite> error2 =
                            input.error(-102, "Failed to parse " + apkPath, e5);
                    IoUtils.closeQuietly(parser2);
                    if (apkAssets2 != null) {
                        try {
                            apkAssets2.close();
                        } catch (Throwable th8) {
                        }
                    }
                    return error2;
                }
            } catch (Throwable th9) {
                e = th9;
            }
        } catch (IOException e6) {
            Slog.w(TAG, "Failed to parse " + apkPath, e6);
            ParseResult<ApkLite> error3 = input.error(-100, "Failed to parse " + apkPath, e6);
            IoUtils.closeQuietly((AutoCloseable) null);
            if (0 != 0) {
                try {
                    apkAssets2.close();
                } catch (Throwable th10) {
                }
            }
            return error3;
        }
    }

    private static String getPackageNameForAudit(XmlResourceParser parser) {
        int type;
        do {
            try {
                type = parser.next();
                if (type == 2) {
                    break;
                }
            } catch (IOException | RuntimeException | XmlPullParserException e) {
                Slog.e(TAG, "Failed to get packageName ", e);
                return null;
            }
        } while (type != 1);
        if (type == 2 && parser.getName().equals("manifest")) {
            String packageName = parser.getAttributeValue(null, "package");
            return packageName;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x03e9, code lost:

       if ((r79 & 128) != 0) goto L154;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x03ef, code lost:

       if (android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(r14, r11) != false) goto L153;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x03f1, code lost:

       r0 = "Skipping target and overlay pair " + r15 + " and " + r76 + ": overlay ignored due to required system property: " + r14 + " with value: " + r11;
       android.util.Slog.i(android.content.pm.parsing.ApkLiteParseUtils.TAG, r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x042b, code lost:

       return r75.skip(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0431, code lost:

       r12 = r5.first;
       r6 = r5.second;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0494, code lost:

       return r75.success(new android.content.pm.parsing.ApkLite(r76, r12, r6, r53, r55, r66, r54, r47, r48, r49, r46, r22, r78, r50, r60, r61, r62, r63, r65, r64, r52, r15, r67, r68, r14, r11, r59, r58, r69, r7.first, r7.second, r70, r71, r51, r56));
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite>
            parseApkLite(
                    android.content.pm.parsing.result.ParseInput r75,
                    java.lang.String r76,
                    android.content.res.XmlResourceParser r77,
                    android.content.pm.SigningDetails r78,
                    int r79)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 1204
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(android.content.pm.parsing.result.ParseInput,"
                    + " java.lang.String, android.content.res.XmlResourceParser,"
                    + " android.content.pm.SigningDetails,"
                    + " int):android.content.pm.parsing.result.ParseResult");
    }

    private static boolean isDeviceAdminReceiver(
            XmlResourceParser parser, boolean applicationHasBindDeviceAdminPermission)
            throws XmlPullParserException, IOException {
        String permission =
                parser.getAttributeValue(
                        "http://schemas.android.com/apk/res/android", "permission");
        if (!applicationHasBindDeviceAdminPermission
                && !Manifest.permission.BIND_DEVICE_ADMIN.equals(permission)) {
            return false;
        }
        boolean hasDeviceAdminReceiver = false;
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type == 1 || (type == 3 && parser.getDepth() <= depth)) {
                break;
            }
            if (type != 3
                    && type != 4
                    && parser.getDepth() == depth + 1
                    && !hasDeviceAdminReceiver
                    && "meta-data".equals(parser.getName())) {
                String name =
                        parser.getAttributeValue(
                                "http://schemas.android.com/apk/res/android", "name");
                if (DeviceAdminReceiver.DEVICE_ADMIN_META_DATA.equals(name)) {
                    hasDeviceAdminReceiver = true;
                }
            }
        }
        return hasDeviceAdminReceiver;
    }

    public static ParseResult<Pair<String, String>> parsePackageSplitNames(
            ParseInput input, XmlResourceParser parser) throws IOException, XmlPullParserException {
        int type;
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            return input.error(-108, "No start tag found");
        }
        if (!parser.getName().equals("manifest")) {
            return input.error(-108, "No <manifest> tag");
        }
        String packageName = parser.getAttributeValue(null, "package");
        if (!"android".equals(packageName)) {
            ParseResult<?> nameResult =
                    FrameworkParsingPackageUtils.validateName(input, packageName, true, true);
            if (nameResult.isError()) {
                return input.error(
                        -106, "Invalid manifest package: " + nameResult.getErrorMessage());
            }
        }
        String splitName = parser.getAttributeValue(null, "split");
        if (splitName != null) {
            if (splitName.length() == 0) {
                splitName = null;
            } else {
                ParseResult<?> nameResult2 =
                        FrameworkParsingPackageUtils.validateName(input, splitName, false, false);
                if (nameResult2.isError()) {
                    return input.error(
                            -106, "Invalid manifest split: " + nameResult2.getErrorMessage());
                }
            }
        }
        return input.success(
                Pair.create(
                        packageName.intern(), splitName != null ? splitName.intern() : splitName));
    }

    public static ParseResult<Pair<Set<String>, Set<String>>> parseRequiredSplitTypes(
            ParseInput input, XmlResourceParser parser) {
        Set<String> requiredSplitTypes = null;
        Set<String> splitTypes = null;
        String value =
                parser.getAttributeValue(
                        "http://schemas.android.com/apk/res/android", "requiredSplitTypes");
        if (!TextUtils.isEmpty(value)) {
            ParseResult<Set<String>> result = separateAndValidateSplitTypes(input, value);
            if (result.isError()) {
                return input.error(result);
            }
            Set<String> requiredSplitTypes2 = result.getResult();
            requiredSplitTypes = requiredSplitTypes2;
        }
        String value2 =
                parser.getAttributeValue(
                        "http://schemas.android.com/apk/res/android", "splitTypes");
        if (!TextUtils.isEmpty(value2)) {
            ParseResult<Set<String>> result2 = separateAndValidateSplitTypes(input, value2);
            if (result2.isError()) {
                return input.error(result2);
            }
            Set<String> splitTypes2 = result2.getResult();
            splitTypes = splitTypes2;
        }
        return input.success(Pair.create(requiredSplitTypes, splitTypes));
    }

    private static ParseResult<Set<String>> separateAndValidateSplitTypes(
            ParseInput input, String values) {
        ArraySet arraySet = new ArraySet();
        for (String value : values.trim().split(",")) {
            String type = value.trim();
            ParseResult<?> nameResult =
                    FrameworkParsingPackageUtils.validateName(input, type, false, true);
            if (nameResult.isError()) {
                return input.error(
                        -108, "Invalid manifest split types: " + nameResult.getErrorMessage());
            }
            if (!arraySet.add(type)) {
                Slog.w(TAG, type + " was defined multiple times");
            }
        }
        return input.success(arraySet);
    }

    public static VerifierInfo parseVerifier(AttributeSet attrs) {
        String packageName =
                attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
        String encodedPublicKey =
                attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "publicKey");
        if (packageName == null || packageName.length() == 0) {
            Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        PublicKey publicKey = FrameworkParsingPackageUtils.parsePublicKey(encodedPublicKey);
        if (publicKey == null) {
            Slog.i(TAG, "Unable to parse verifier public key for " + packageName);
            return null;
        }
        return new VerifierInfo(packageName, publicKey);
    }

    private static class SplitNameComparator implements Comparator<String> {
        private SplitNameComparator() {}

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

    public static boolean isApkFile(File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(String path) {
        return path.endsWith(".apk");
    }
}
