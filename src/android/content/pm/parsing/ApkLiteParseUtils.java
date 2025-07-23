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
import android.util.EmptyArray;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ApkLiteParseUtils {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    public static final String APK_FILE_EXTENSION = ".apk";
    public static final int DEFAULT_MIN_SDK_VERSION = 1;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    private static final int PARSE_COLLECT_CERTIFICATES = 32;
    private static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    private static final int PARSE_IS_SYSTEM_DIR = 16;
    private static final String TAG = "ApkLiteParseUtils";
    private static final String TAG_APPLICATION = "application";
    private static final String TAG_LIBRARY = "library";
    private static final String TAG_MANIFEST = "manifest";
    private static final String TAG_OVERLAY = "overlay";
    private static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    private static final String TAG_PROCESS = "process";
    private static final String TAG_PROCESSES = "processes";
    private static final String TAG_PROFILEABLE = "profileable";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_SDK_LIBRARY = "sdk-library";
    private static final String TAG_STATIC_LIBRARY = "static-library";
    private static final String TAG_USES_SDK = "uses-sdk";
    private static final String TAG_USES_SDK_LIBRARY = "uses-sdk-library";
    private static final String TAG_USES_SPLIT = "uses-split";
    private static final String TAG_USES_STATIC_LIBRARY = "uses-static-library";
    private static final Comparator<String> sSplitNameComparator = new SplitNameComparator();
    private static final int SDK_VERSION = Build.VERSION.SDK_INT;
    private static final String[] SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;

    public static ParseResult<PackageLite> parsePackageLite(ParseInput parseInput, File file, int i) {
        if (file.isDirectory()) {
            return parseClusterPackageLite(parseInput, file, i);
        }
        return parseMonolithicPackageLite(parseInput, file, i);
    }

    public static ParseResult<PackageLite> parseMonolithicPackageLite(ParseInput parseInput, File file, int i) {
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            ParseResult<ApkLite> parseApkLite = parseApkLite(parseInput, file, i);
            if (parseApkLite.isError()) {
                return parseInput.error(parseApkLite);
            }
            ApkLite result = parseApkLite.getResult();
            return parseInput.success(new PackageLite(file.getAbsolutePath(), result.getPath(), result, null, null, null, null, null, null, result.getTargetSdkVersion(), null, null));
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> parseMonolithicPackageLite(ParseInput parseInput, FileDescriptor fileDescriptor, String str, int i) {
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            ParseResult<ApkLite> parseApkLite = parseApkLite(parseInput, fileDescriptor, str, i);
            if (parseApkLite.isError()) {
                return parseInput.error(parseApkLite);
            }
            ApkLite result = parseApkLite.getResult();
            return parseInput.success(new PackageLite(str, result.getPath(), result, null, null, null, null, null, null, result.getTargetSdkVersion(), null, null));
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> parseClusterPackageLite(ParseInput parseInput, File file, int i) {
        long j;
        File[] listFiles = file.listFiles();
        if (ArrayUtils.isEmpty(listFiles)) {
            return parseInput.error(-100, "No packages found in split");
        }
        int i2 = 0;
        if (listFiles.length == 1 && listFiles[0].isDirectory()) {
            return parseClusterPackageLite(parseInput, listFiles[0], i);
        }
        ArrayMap arrayMap = new ArrayMap();
        long j2 = 262144;
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            int length = listFiles.length;
            int i3 = 0;
            String str = null;
            while (i2 < length) {
                File file2 = listFiles[i2];
                if (isApkFile(file2)) {
                    ParseResult<ApkLite> parseApkLite = parseApkLite(parseInput, file2, i);
                    if (parseApkLite.isError()) {
                        ParseResult<PackageLite> error = parseInput.error(parseApkLite);
                        Trace.traceEnd(j2);
                        return error;
                    }
                    ApkLite result = parseApkLite.getResult();
                    if (str == null) {
                        str = result.getPackageName();
                        i3 = result.getVersionCode();
                        j = j2;
                    } else {
                        j = j2;
                        if (!str.equals(result.getPackageName())) {
                            ParseResult<PackageLite> error2 = parseInput.error(-101, "Inconsistent package " + result.getPackageName() + " in " + file2 + "; expected " + str);
                            Trace.traceEnd(j);
                            return error2;
                        }
                        if (i3 != result.getVersionCode()) {
                            ParseResult<PackageLite> error3 = parseInput.error(-101, "Inconsistent version " + result.getVersionCode() + " in " + file2 + "; expected " + i3);
                            Trace.traceEnd(j);
                            return error3;
                        }
                    }
                    try {
                        ApkLite apkLite = (ApkLite) arrayMap.put(result.getSplitName(), result);
                        if (apkLite != null) {
                            ParseResult<PackageLite> error4 = parseInput.error(-101, "Split name " + result.getSplitName() + " defined more than once; most recent was " + file2 + ", previous was " + apkLite.getPath());
                            Trace.traceEnd(j);
                            return error4;
                        }
                    } catch (Throwable th) {
                        th = th;
                        Trace.traceEnd(j);
                        throw th;
                    }
                } else {
                    j = j2;
                }
                i2++;
                j2 = j;
            }
            long j3 = j2;
            ApkLite apkLite2 = (ApkLite) arrayMap.remove(null);
            Trace.traceEnd(j3);
            return composePackageLiteFromApks(parseInput, file, apkLite2, arrayMap);
        } catch (Throwable th2) {
            th = th2;
            j = j2;
        }
    }

    public static ParseResult<PackageLite> composePackageLiteFromApks(ParseInput parseInput, File file, ApkLite apkLite, ArrayMap<String, ApkLite> arrayMap) {
        return composePackageLiteFromApks(parseInput, file, apkLite, arrayMap, false);
    }

    public static ParseResult<PackageLite> composePackageLiteFromApks(ParseInput parseInput, File file, ApkLite apkLite, ArrayMap<String, ApkLite> arrayMap, boolean z) {
        String[] strArr;
        boolean[] zArr;
        String[] strArr2;
        String[] strArr3;
        String[] strArr4;
        int[] iArr;
        Set[] setArr;
        Set[] setArr2;
        if (apkLite == null) {
            return parseInput.error(-101, "Missing base APK in " + file);
        }
        int size = ArrayUtils.size(arrayMap);
        if (size > 0) {
            Set[] setArr3 = new Set[size];
            Set[] setArr4 = new Set[size];
            boolean[] zArr2 = new boolean[size];
            String[] strArr5 = new String[size];
            String[] strArr6 = new String[size];
            String[] strArr7 = new String[size];
            int[] iArr2 = new int[size];
            String[] strArr8 = (String[]) arrayMap.keySet().toArray(new String[size]);
            Arrays.sort(strArr8, sSplitNameComparator);
            for (int i = 0; i < size; i++) {
                ApkLite apkLite2 = arrayMap.get(strArr8[i]);
                setArr3[i] = apkLite2.getRequiredSplitTypes();
                setArr4[i] = apkLite2.getSplitTypes();
                strArr5[i] = apkLite2.getUsesSplitName();
                zArr2[i] = apkLite2.isFeatureSplit();
                strArr6[i] = apkLite2.getConfigForSplit();
                strArr7[i] = z ? new File(file, splitNameToFileName(apkLite2)).getAbsolutePath() : apkLite2.getPath();
                iArr2[i] = apkLite2.getRevisionCode();
            }
            setArr = setArr3;
            setArr2 = setArr4;
            strArr2 = strArr5;
            strArr3 = strArr6;
            strArr4 = strArr7;
            iArr = iArr2;
            strArr = strArr8;
            zArr = zArr2;
        } else {
            strArr = null;
            zArr = null;
            strArr2 = null;
            strArr3 = null;
            strArr4 = null;
            iArr = null;
            setArr = null;
            setArr2 = null;
        }
        return parseInput.success(new PackageLite(file.getAbsolutePath(), z ? new File(file, splitNameToFileName(apkLite)).getAbsolutePath() : apkLite.getPath(), apkLite, strArr, zArr, strArr2, strArr3, strArr4, iArr, apkLite.getTargetSdkVersion(), setArr, setArr2));
    }

    public static String splitNameToFileName(ApkLite apkLite) {
        String str;
        Objects.requireNonNull(apkLite);
        if (apkLite.getSplitName() == null) {
            str = "base";
        } else {
            str = "split_" + apkLite.getSplitName();
        }
        return str + ".apk";
    }

    public static ParseResult<ApkLite> parseApkLite(ParseInput parseInput, File file, int i) {
        return parseApkLiteInner(parseInput, file, null, null, i);
    }

    public static ParseResult<ApkLite> parseApkLite(ParseInput parseInput, FileDescriptor fileDescriptor, String str, int i) {
        return parseApkLiteInner(parseInput, null, fileDescriptor, str, i);
    }

    private static ParseResult<ApkLite> parseApkLiteInner(ParseInput parseInput, File file, FileDescriptor fileDescriptor, String str, int i) {
        ApkAssets apkAssets;
        XmlResourceParser openXml;
        SigningDetails signingDetails;
        String absolutePath = fileDescriptor != null ? str : file.getAbsolutePath();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    apkAssets = fileDescriptor != null ? ApkAssets.loadFromFd(fileDescriptor, str, 0, null) : ApkAssets.loadFromPath(absolutePath);
                } catch (IOException e) {
                    Slog.w(TAG, "Failed to parse " + absolutePath, e);
                    ParseResult<ApkLite> error = parseInput.error(-100, "Failed to parse " + absolutePath, e);
                    IoUtils.closeQuietly((AutoCloseable) null);
                    return error;
                }
            } catch (Throwable th) {
                th = th;
                apkAssets = null;
            }
        } catch (IOException | RuntimeException | XmlPullParserException e2) {
            e = e2;
            apkAssets = null;
        }
        try {
            try {
                openXml = apkAssets.openXml("AndroidManifest.xml");
            } catch (IOException | RuntimeException | XmlPullParserException e3) {
                e = e3;
            }
            try {
                try {
                    if ((i & 32) != 0) {
                        boolean z = (i & 16) != 0;
                        Trace.traceBegin(262144L, "collectCertificates");
                        try {
                            ParseResult<SigningDetails> signingDetails2 = FrameworkParsingPackageUtils.getSigningDetails(parseInput, file.getAbsolutePath(), z, false, SigningDetails.UNKNOWN, 0);
                            if (signingDetails2.isError()) {
                                parseInput.setPackageNameForAudit(getPackageNameForAudit(openXml));
                                ParseResult<ApkLite> error2 = parseInput.error(signingDetails2);
                                IoUtils.closeQuietly(openXml);
                                if (apkAssets != null) {
                                    try {
                                        apkAssets.close();
                                    } catch (Throwable unused) {
                                    }
                                }
                                return error2;
                            }
                            signingDetails = signingDetails2.getResult();
                            Trace.traceEnd(262144L);
                        } finally {
                            Trace.traceEnd(262144L);
                        }
                    } else {
                        signingDetails = SigningDetails.UNKNOWN;
                    }
                    ParseResult<ApkLite> parseApkLite = parseApkLite(parseInput, absolutePath, openXml, signingDetails, i);
                    IoUtils.closeQuietly(openXml);
                    if (apkAssets != null) {
                        try {
                            apkAssets.close();
                        } catch (Throwable unused2) {
                        }
                    }
                    return parseApkLite;
                } catch (Throwable th2) {
                    th = th2;
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
            } catch (IOException | RuntimeException | XmlPullParserException e4) {
                e = e4;
                xmlResourceParser = openXml;
                Slog.w(TAG, "Failed to parse " + absolutePath, e);
                ParseResult<ApkLite> error3 = parseInput.error(-102, "Failed to parse " + absolutePath, e);
                IoUtils.closeQuietly(xmlResourceParser);
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused4) {
                    }
                }
                return error3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static String getPackageNameForAudit(XmlResourceParser xmlResourceParser) {
        int next;
        do {
            try {
                next = xmlResourceParser.next();
                if (next == 2) {
                    break;
                }
            } catch (IOException | RuntimeException | XmlPullParserException e) {
                Slog.e(TAG, "Failed to get packageName ", e);
                return null;
            }
        } while (next != 1);
        if (next == 2 && xmlResourceParser.getName().equals("manifest")) {
            return xmlResourceParser.getAttributeValue(null, "package");
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x04c5, code lost:
    
        r47 = r15;
        r43 = r4;
        r48 = r12;
        r44 = r34;
        r15 = r46;
        r34 = r47;
        r3 = r51;
        r7 = r53;
        r8 = r54;
        r4 = r55;
        r10 = r56;
        r12 = r58;
        r23 = 1;
        r24 = false;
        r47 = r11;
        r11 = r57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x062b, code lost:
    
        r55 = r4;
        r54 = r8;
        r56 = r10;
        r57 = r11;
        r58 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x063d, code lost:
    
        if ((r66 & 128) != 0) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0643, code lost:
    
        if (android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(r57, r58) != false) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0645, code lost:
    
        r1 = "Skipping target and overlay pair " + r56 + " and " + r63 + ": overlay ignored due to required system property: " + r57 + " with value: " + r58;
        android.util.Slog.i(android.content.pm.parsing.ApkLiteParseUtils.TAG, r1);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite(android.content.pm.parsing.result.ParseInput r62, java.lang.String r63, android.content.res.XmlResourceParser r64, android.content.pm.SigningDetails r65, int r66) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 1782
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(android.content.pm.parsing.result.ParseInput, java.lang.String, android.content.res.XmlResourceParser, android.content.pm.SigningDetails, int):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<String[]> parseAdditionalCertificates(ParseInput parseInput, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        String[] strArr = EmptyArray.STRING;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && xmlResourceParser.getName().equals("additional-certificate")) {
                String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "certDigest");
                if (TextUtils.isEmpty(attributeValue)) {
                    return parseInput.error("Bad additional-certificate declaration with empty certDigest:" + attributeValue);
                }
                strArr = (String[]) ArrayUtils.appendElement(String.class, strArr, normalizeCertDigest(attributeValue));
            }
        }
        return parseInput.success(strArr);
    }

    private static String normalizeCertDigest(String str) {
        return str.replace(":", "").toLowerCase();
    }

    private static boolean isDeviceAdminReceiver(XmlResourceParser xmlResourceParser, boolean z) throws XmlPullParserException, IOException {
        String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "permission");
        boolean z2 = false;
        if (!z && !Manifest.permission.BIND_DEVICE_ADMIN.equals(attributeValue)) {
            return false;
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlResourceParser.getDepth() == depth + 1 && !z2 && "meta-data".equals(xmlResourceParser.getName()) && DeviceAdminReceiver.DEVICE_ADMIN_META_DATA.equals(xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name"))) {
                z2 = true;
            }
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<android.util.Pair<java.lang.String, java.lang.String>> parsePackageSplitNames(android.content.pm.parsing.result.ParseInput r5, android.content.res.XmlResourceParser r6) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
        L0:
            int r0 = r6.next()
            r1 = 2
            r2 = 1
            if (r0 == r1) goto Lb
            if (r0 == r2) goto Lb
            goto L0
        Lb:
            r3 = -108(0xffffffffffffff94, float:NaN)
            if (r0 == r1) goto L16
            java.lang.String r6 = "No start tag found"
            android.content.pm.parsing.result.ParseResult r5 = r5.error(r3, r6)
            return r5
        L16:
            java.lang.String r0 = r6.getName()
            java.lang.String r1 = "manifest"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L2a
            java.lang.String r6 = "No <manifest> tag"
            android.content.pm.parsing.result.ParseResult r5 = r5.error(r3, r6)
            return r5
        L2a:
            java.lang.String r0 = "package"
            r1 = 0
            java.lang.String r0 = r6.getAttributeValue(r1, r0)
            java.lang.String r3 = "android"
            boolean r3 = r3.equals(r0)
            r4 = -106(0xffffffffffffff96, float:NaN)
            if (r3 != 0) goto L5d
            android.content.pm.parsing.result.ParseResult r2 = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(r5, r0, r2, r2)
            boolean r3 = r2.isError()
            if (r3 == 0) goto L5d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "Invalid manifest package: "
            r6.<init>(r0)
            java.lang.String r0 = r2.getErrorMessage()
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            android.content.pm.parsing.result.ParseResult r5 = r5.error(r4, r6)
            return r5
        L5d:
            java.lang.String r2 = "split"
            java.lang.String r6 = r6.getAttributeValue(r1, r2)
            if (r6 == 0) goto L8f
            int r2 = r6.length()
            if (r2 != 0) goto L6d
            goto L90
        L6d:
            r1 = 0
            android.content.pm.parsing.result.ParseResult r1 = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(r5, r6, r1, r1)
            boolean r2 = r1.isError()
            if (r2 == 0) goto L8f
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "Invalid manifest split: "
            r6.<init>(r0)
            java.lang.String r0 = r1.getErrorMessage()
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            android.content.pm.parsing.result.ParseResult r5 = r5.error(r4, r6)
            return r5
        L8f:
            r1 = r6
        L90:
            java.lang.String r6 = r0.intern()
            if (r1 == 0) goto L9a
            java.lang.String r1 = r1.intern()
        L9a:
            android.util.Pair r6 = android.util.Pair.create(r6, r1)
            android.content.pm.parsing.result.ParseResult r5 = r5.success(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.parsing.ApkLiteParseUtils.parsePackageSplitNames(android.content.pm.parsing.result.ParseInput, android.content.res.XmlResourceParser):android.content.pm.parsing.result.ParseResult");
    }

    public static ParseResult<Pair<Set<String>, Set<String>>> parseRequiredSplitTypes(ParseInput parseInput, XmlResourceParser xmlResourceParser) {
        Set<String> set;
        String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSplitTypes");
        Set<String> set2 = null;
        if (TextUtils.isEmpty(attributeValue)) {
            set = null;
        } else {
            ParseResult<Set<String>> separateAndValidateSplitTypes = separateAndValidateSplitTypes(parseInput, attributeValue);
            if (separateAndValidateSplitTypes.isError()) {
                return parseInput.error(separateAndValidateSplitTypes);
            }
            set = separateAndValidateSplitTypes.getResult();
        }
        String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "splitTypes");
        if (!TextUtils.isEmpty(attributeValue2)) {
            ParseResult<Set<String>> separateAndValidateSplitTypes2 = separateAndValidateSplitTypes(parseInput, attributeValue2);
            if (separateAndValidateSplitTypes2.isError()) {
                return parseInput.error(separateAndValidateSplitTypes2);
            }
            set2 = separateAndValidateSplitTypes2.getResult();
        }
        return parseInput.success(Pair.create(set, set2));
    }

    private static ParseResult<Set<String>> separateAndValidateSplitTypes(ParseInput parseInput, String str) {
        ArraySet arraySet = new ArraySet();
        for (String str2 : str.trim().split(",")) {
            String trim = str2.trim();
            ParseResult validateName = FrameworkParsingPackageUtils.validateName(parseInput, trim, false, true);
            if (validateName.isError()) {
                return parseInput.error(-108, "Invalid manifest split types: " + validateName.getErrorMessage());
            }
            if (!arraySet.add(trim)) {
                Slog.w(TAG, trim + " was defined multiple times");
            }
        }
        return parseInput.success(arraySet);
    }

    public static VerifierInfo parseVerifier(AttributeSet attributeSet) {
        String attributeValue = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
        String attributeValue2 = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "publicKey");
        if (attributeValue == null || attributeValue.length() == 0) {
            Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        PublicKey parsePublicKey = FrameworkParsingPackageUtils.parsePublicKey(attributeValue2);
        if (parsePublicKey == null) {
            Slog.i(TAG, "Unable to parse verifier public key for " + attributeValue);
            return null;
        }
        return new VerifierInfo(attributeValue, parsePublicKey);
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

    public static boolean isApkFile(File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(String str) {
        return str.endsWith(".apk");
    }
}
