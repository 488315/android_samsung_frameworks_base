package android.content.pm.parsing;

import android.Manifest;
import android.app.admin.DeviceAdminReceiver;
import android.content.pm.PackageInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SigningDetails;
import android.content.pm.VerifierInfo;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.ApkAssets;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.SystemProperties;
import android.os.Trace;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.EmptyArray;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.pm.pkg.component.flags.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.sec.android.iaft.SmLib_IafdConstant;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import libcore.io.IoUtils;
import libcore.util.HexEncoding;
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
            ParseResult<ApkLite> apkLite = parseApkLite(parseInput, file, i);
            if (apkLite.isError()) {
                return parseInput.error(apkLite);
            }
            ApkLite result = apkLite.getResult();
            return parseInput.success(new PackageLite(file.getAbsolutePath(), result.getPath(), result, null, null, null, null, null, null, result.getTargetSdkVersion(), null, null));
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> parseMonolithicPackageLite(ParseInput parseInput, FileDescriptor fileDescriptor, String str, int i) {
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            ParseResult<ApkLite> apkLite = parseApkLite(parseInput, fileDescriptor, str, i);
            if (apkLite.isError()) {
                return parseInput.error(apkLite);
            }
            ApkLite result = apkLite.getResult();
            return parseInput.success(new PackageLite(str, result.getPath(), result, null, null, null, null, null, null, result.getTargetSdkVersion(), null, null));
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public static ParseResult<PackageLite> parseClusterPackageLite(ParseInput parseInput, File file, int i) throws Throwable {
        long j;
        File[] fileArrListFiles = file.listFiles();
        if (ArrayUtils.isEmpty(fileArrListFiles)) {
            return parseInput.error(-100, "No packages found in split");
        }
        int i2 = 0;
        if (fileArrListFiles.length == 1 && fileArrListFiles[0].isDirectory()) {
            return parseClusterPackageLite(parseInput, fileArrListFiles[0], i);
        }
        ArrayMap arrayMap = new ArrayMap();
        long j2 = 262144;
        Trace.traceBegin(262144L, "parseApkLite");
        try {
            int length = fileArrListFiles.length;
            int versionCode = 0;
            String packageName = null;
            while (i2 < length) {
                File file2 = fileArrListFiles[i2];
                if (isApkFile(file2)) {
                    ParseResult<ApkLite> apkLite = parseApkLite(parseInput, file2, i);
                    if (apkLite.isError()) {
                        ParseResult<PackageLite> parseResultError = parseInput.error(apkLite);
                        Trace.traceEnd(j2);
                        return parseResultError;
                    }
                    ApkLite result = apkLite.getResult();
                    if (packageName == null) {
                        packageName = result.getPackageName();
                        versionCode = result.getVersionCode();
                        j = j2;
                    } else {
                        j = j2;
                        if (!packageName.equals(result.getPackageName())) {
                            ParseResult<PackageLite> parseResultError2 = parseInput.error(-101, "Inconsistent package " + result.getPackageName() + " in " + file2 + "; expected " + packageName);
                            Trace.traceEnd(j);
                            return parseResultError2;
                        }
                        if (versionCode != result.getVersionCode()) {
                            ParseResult<PackageLite> parseResultError3 = parseInput.error(-101, "Inconsistent version " + result.getVersionCode() + " in " + file2 + "; expected " + versionCode);
                            Trace.traceEnd(j);
                            return parseResultError3;
                        }
                    }
                    try {
                        ApkLite apkLite2 = (ApkLite) arrayMap.put(result.getSplitName(), result);
                        if (apkLite2 != null) {
                            ParseResult<PackageLite> parseResultError4 = parseInput.error(-101, "Split name " + result.getSplitName() + " defined more than once; most recent was " + file2 + ", previous was " + apkLite2.getPath());
                            Trace.traceEnd(j);
                            return parseResultError4;
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
            ApkLite apkLite3 = (ApkLite) arrayMap.remove(null);
            Trace.traceEnd(j3);
            return composePackageLiteFromApks(parseInput, file, apkLite3, arrayMap);
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

    private static ParseResult<ApkLite> parseApkLiteInner(ParseInput parseInput, File file, FileDescriptor fileDescriptor, String str, int i) throws Throwable {
        ApkAssets apkAssetsLoadFromFd;
        XmlResourceParser xmlResourceParserOpenXml;
        SigningDetails result;
        String absolutePath = fileDescriptor != null ? str : file.getAbsolutePath();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    apkAssetsLoadFromFd = fileDescriptor != null ? ApkAssets.loadFromFd(fileDescriptor, str, 0, null) : ApkAssets.loadFromPath(absolutePath);
                    try {
                        try {
                            xmlResourceParserOpenXml = apkAssetsLoadFromFd.openXml("AndroidManifest.xml");
                        } catch (Throwable th) {
                            th = th;
                        }
                        try {
                            try {
                                if ((i & 32) != 0) {
                                    boolean z = (i & 16) != 0;
                                    Trace.traceBegin(262144L, "collectCertificates");
                                    try {
                                        ParseResult<SigningDetails> signingDetails = FrameworkParsingPackageUtils.getSigningDetails(parseInput, file.getAbsolutePath(), z, false, SigningDetails.UNKNOWN, 0);
                                        if (signingDetails.isError()) {
                                            parseInput.setPackageNameForAudit(getPackageNameForAudit(xmlResourceParserOpenXml));
                                            ParseResult<ApkLite> parseResultError = parseInput.error(signingDetails);
                                            IoUtils.closeQuietly(xmlResourceParserOpenXml);
                                            if (apkAssetsLoadFromFd != null) {
                                                try {
                                                    apkAssetsLoadFromFd.close();
                                                } catch (Throwable unused) {
                                                }
                                            }
                                            return parseResultError;
                                        }
                                        result = signingDetails.getResult();
                                        Trace.traceEnd(262144L);
                                    } finally {
                                        Trace.traceEnd(262144L);
                                    }
                                } else {
                                    result = SigningDetails.UNKNOWN;
                                }
                                ParseResult<ApkLite> apkLite = parseApkLite(parseInput, absolutePath, xmlResourceParserOpenXml, result, i);
                                IoUtils.closeQuietly(xmlResourceParserOpenXml);
                                if (apkAssetsLoadFromFd != null) {
                                    try {
                                        apkAssetsLoadFromFd.close();
                                    } catch (Throwable unused2) {
                                    }
                                }
                                return apkLite;
                            } catch (IOException | RuntimeException | XmlPullParserException e) {
                                e = e;
                                xmlResourceParser = xmlResourceParserOpenXml;
                                Slog.w(TAG, "Failed to parse " + absolutePath, e);
                                ParseResult<ApkLite> parseResultError2 = parseInput.error(-102, "Failed to parse " + absolutePath, e);
                                IoUtils.closeQuietly(xmlResourceParser);
                                if (apkAssetsLoadFromFd != null) {
                                    try {
                                        apkAssetsLoadFromFd.close();
                                    } catch (Throwable unused3) {
                                    }
                                }
                                return parseResultError2;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            xmlResourceParser = xmlResourceParserOpenXml;
                            IoUtils.closeQuietly(xmlResourceParser);
                            if (apkAssetsLoadFromFd != null) {
                                try {
                                    apkAssetsLoadFromFd.close();
                                } catch (Throwable unused4) {
                                }
                            }
                            throw th;
                        }
                    } catch (IOException | RuntimeException | XmlPullParserException e2) {
                        e = e2;
                    }
                } catch (IOException | RuntimeException | XmlPullParserException e3) {
                    e = e3;
                    apkAssetsLoadFromFd = null;
                }
            } catch (Throwable th3) {
                th = th3;
                apkAssetsLoadFromFd = null;
            }
        } catch (IOException e4) {
            Slog.w(TAG, "Failed to parse " + absolutePath, e4);
            ParseResult<ApkLite> parseResultError3 = parseInput.error(-100, "Failed to parse " + absolutePath, e4);
            IoUtils.closeQuietly((AutoCloseable) null);
            return parseResultError3;
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
    /* JADX WARN: Code restructure failed: missing block: B:174:0x04c5, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:220:0x062b, code lost:
    
        r55 = r4;
        r54 = r8;
        r56 = r10;
        r57 = r11;
        r58 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x063d, code lost:
    
        if ((r66 & 128) != 0) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0643, code lost:
    
        if (android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(r57, r58) != false) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x0645, code lost:
    
        r1 = "Skipping target and overlay pair " + r56 + " and " + r63 + ": overlay ignored due to required system property: " + r57 + " with value: " + r58;
        android.util.Slog.i(android.content.pm.parsing.ApkLiteParseUtils.TAG, r1);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ApkLite> parseApkLite(ParseInput parseInput, String str, XmlResourceParser xmlResourceParser, SigningDetails signingDetails, int i) throws XmlPullParserException, IOException, NumberFormatException {
        Pair<String, String> pair;
        int i2;
        ArrayList arrayList;
        String str2;
        String str3;
        String str4;
        boolean z;
        String str5;
        boolean z2;
        int i3;
        boolean z3;
        String[][] strArr;
        long[] jArr;
        int i4;
        boolean z4;
        int i5;
        int i6;
        ParseResult<?> packageSplitNames = parsePackageSplitNames(parseInput, xmlResourceParser);
        if (packageSplitNames.isError()) {
            return parseInput.error(packageSplitNames);
        }
        Pair<String, String> result = packageSplitNames.getResult();
        ParseResult<Pair<Set<String>, Set<String>>> requiredSplitTypes = parseRequiredSplitTypes(parseInput, xmlResourceParser);
        if (requiredSplitTypes.isError()) {
            return parseInput.error(packageSplitNames);
        }
        Pair<Set<String>, Set<String>> result2 = requiredSplitTypes.getResult();
        int attributeIntValue = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "installLocation", -1);
        int attributeIntValue2 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", SmLib_IafdConstant.KEY_VERSION_CODE, 0);
        int attributeIntValue3 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "versionCodeMajor", 0);
        int attributeIntValue4 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "revisionCode", 0);
        boolean attributeBooleanValue = xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false);
        boolean attributeBooleanValue2 = xmlResourceParser.getAttributeBooleanValue(null, "updatableSystem", true);
        boolean attributeBooleanValue3 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isolatedSplits", false);
        boolean attributeBooleanValue4 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isFeatureSplit", false);
        boolean attributeBooleanValue5 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isSplitRequired", false);
        String attributeValue = xmlResourceParser.getAttributeValue(null, "configForSplit");
        String attributeValue2 = xmlResourceParser.getAttributeValue(null, "emergencyInstaller");
        ArrayList arrayList2 = new ArrayList();
        String[][] strArr2 = (String[][]) Array.newInstance((Class<?>) String.class, 0, 0);
        ArrayList arrayList3 = new ArrayList();
        int i7 = 1;
        boolean z5 = false;
        String[][] strArr3 = (String[][]) Array.newInstance((Class<?>) String.class, 0, 0);
        ArrayList arrayList4 = new ArrayList();
        int depth = xmlResourceParser.getDepth() + 1;
        ArrayList arrayList5 = new ArrayList();
        long[] jArr2 = new long[0];
        long[] jArr3 = new long[0];
        boolean attributeBooleanValue6 = true;
        int iIntValue = 1;
        boolean attributeBooleanValue7 = false;
        boolean attributeBooleanValue8 = false;
        boolean attributeBooleanValue9 = false;
        int attributeIntValue5 = 0;
        boolean z6 = false;
        int iIntValue2 = 0;
        int attributeIntValue6 = 0;
        boolean zIsDeviceAdminReceiver = false;
        boolean z7 = false;
        boolean z8 = false;
        int attributeIntValue7 = 0;
        String[][] strArr4 = strArr3;
        String[][] strArr5 = strArr2;
        String attributeValue3 = null;
        String attributeValue4 = null;
        String attributeValue5 = null;
        String attributeValue6 = null;
        int i8 = 2;
        boolean attributeBooleanValue10 = false;
        boolean attributeBooleanValue11 = false;
        while (true) {
            int next = xmlResourceParser.next();
            boolean z9 = attributeBooleanValue5;
            Pair<Set<String>, Set<String>> pair2 = result2;
            if (next != i7) {
                int i9 = 3;
                if (next == 3) {
                    if (xmlResourceParser.getDepth() >= depth) {
                        i9 = 3;
                    }
                }
                if (next != i9 && next != 4 && xmlResourceParser.getDepth() == depth) {
                    if ("package-verifier".equals(xmlResourceParser.getName())) {
                        VerifierInfo verifier = parseVerifier(xmlResourceParser);
                        if (verifier != null) {
                            arrayList5.add(verifier);
                        }
                    } else if ("application".equals(xmlResourceParser.getName())) {
                        boolean z10 = z5;
                        attributeBooleanValue8 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "debuggable", z10);
                        attributeBooleanValue7 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "multiArch", z10);
                        attributeBooleanValue10 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "use32bitAbi", z10);
                        attributeBooleanValue6 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "extractNativeLibs", true);
                        String str6 = "useEmbeddedDex";
                        attributeBooleanValue11 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "useEmbeddedDex", false);
                        int i10 = depth;
                        attributeIntValue6 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "rollbackDataPolicy", 0);
                        boolean zEquals = Manifest.permission.BIND_DEVICE_ADMIN.equals(xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "permission"));
                        ArrayList arrayList6 = arrayList5;
                        attributeIntValue7 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "pageSizeCompat", 0);
                        int depth2 = xmlResourceParser.getDepth();
                        Pair<String, String> pair3 = result;
                        String str7 = attributeValue3;
                        String str8 = attributeValue4;
                        String str9 = attributeValue5;
                        boolean attributeBooleanValue12 = z6;
                        long[] jArrAppendLong = jArr3;
                        String[][] strArr6 = strArr5;
                        long[] jArrAppendLong2 = jArr2;
                        String[][] strArr7 = strArr4;
                        while (true) {
                            int next2 = xmlResourceParser.next();
                            String[][] strArr8 = strArr6;
                            if (next2 != 1) {
                                int i11 = 3;
                                if (next2 == 3) {
                                    if (xmlResourceParser.getDepth() > depth2) {
                                        i11 = 3;
                                    }
                                }
                                if (next2 == i11) {
                                    str5 = str6;
                                    z2 = zEquals;
                                    i3 = depth2;
                                    z3 = attributeBooleanValue12;
                                    strArr = strArr8;
                                    jArr = jArrAppendLong;
                                } else {
                                    if (next2 != 4 && xmlResourceParser.getDepth() == depth2 + 1) {
                                        String name = xmlResourceParser.getName();
                                        name.hashCode();
                                        switch (name.hashCode()) {
                                            case -1803294168:
                                                if (!name.equals(TAG_SDK_LIBRARY)) {
                                                    i4 = -1;
                                                    break;
                                                } else {
                                                    i4 = 0;
                                                    break;
                                                }
                                            case -1521117785:
                                                if (name.equals(TAG_USES_SDK_LIBRARY)) {
                                                    i4 = 1;
                                                    break;
                                                }
                                                break;
                                            case -1094759587:
                                                if (name.equals(TAG_PROCESSES)) {
                                                    i4 = i8;
                                                    break;
                                                }
                                                break;
                                            case -1056667556:
                                                if (name.equals(TAG_STATIC_LIBRARY)) {
                                                    i4 = 3;
                                                    break;
                                                }
                                                break;
                                            case -808719889:
                                                if (name.equals("receiver")) {
                                                    i4 = 4;
                                                    break;
                                                }
                                                break;
                                            case 8960125:
                                                if (name.equals(TAG_USES_STATIC_LIBRARY)) {
                                                    i4 = 5;
                                                    break;
                                                }
                                                break;
                                            case 166208699:
                                                if (name.equals(TAG_LIBRARY)) {
                                                    i4 = 6;
                                                    break;
                                                }
                                                break;
                                            case 178070147:
                                                if (name.equals("profileable")) {
                                                    i4 = 7;
                                                    break;
                                                }
                                                break;
                                        }
                                        i3 = depth2;
                                        long[] jArr4 = jArrAppendLong;
                                        switch (i4) {
                                            case 0:
                                                z2 = zEquals;
                                                boolean z11 = attributeBooleanValue12;
                                                String attributeValue7 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                                int attributeIntValue8 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "versionMajor", -1);
                                                if (attributeValue7 == null || attributeIntValue8 < 0) {
                                                    break;
                                                } else {
                                                    str5 = str6;
                                                    arrayList4.add(new SharedLibraryInfo(attributeValue7, attributeIntValue8, 3));
                                                    jArrAppendLong = jArr4;
                                                    strArr6 = strArr8;
                                                    attributeBooleanValue12 = z11;
                                                    z7 = true;
                                                    depth2 = i3;
                                                    zEquals = z2;
                                                    str6 = str5;
                                                }
                                                break;
                                            case 1:
                                                boolean z12 = attributeBooleanValue12;
                                                String attributeValue8 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                                z2 = zEquals;
                                                long jConvertValueToInt = XmlUtils.convertValueToInt(xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "versionMajor"), -1);
                                                String attributeValue9 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "certDigest");
                                                if (attributeValue8 == null || attributeValue8.isBlank() || jConvertValueToInt < 0) {
                                                    break;
                                                } else if (arrayList2.contains(attributeValue8)) {
                                                    break;
                                                } else {
                                                    arrayList2.add(attributeValue8);
                                                    jArrAppendLong = ArrayUtils.appendLong(jArr4, jConvertValueToInt, true);
                                                    String strNormalizeCertDigest = normalizeCertDigest(attributeValue9);
                                                    if ("".equals(strNormalizeCertDigest)) {
                                                        strNormalizeCertDigest = SystemProperties.get("debug.pm.uses_sdk_library_default_cert_digest", "");
                                                        try {
                                                            HexEncoding.decode(strNormalizeCertDigest, false);
                                                        } catch (IllegalArgumentException unused) {
                                                            strNormalizeCertDigest = "";
                                                        }
                                                    }
                                                    strArr6 = (String[][]) ArrayUtils.appendElement(String[].class, strArr8, new String[]{strNormalizeCertDigest}, true);
                                                    str5 = str6;
                                                    attributeBooleanValue12 = z12;
                                                    depth2 = i3;
                                                    zEquals = z2;
                                                    str6 = str5;
                                                }
                                                break;
                                            case 2:
                                                boolean z13 = attributeBooleanValue12;
                                                int i12 = i8;
                                                int depth3 = xmlResourceParser.getDepth();
                                                while (true) {
                                                    int next3 = xmlResourceParser.next();
                                                    if (next3 != 1 && (next3 != 3 || xmlResourceParser.getDepth() > depth3)) {
                                                        if (next3 != 3 && next3 != 4 && xmlResourceParser.getDepth() == depth3 + 1 && xmlResourceParser.getName().equals(TAG_PROCESS) && Flags.enablePerProcessUseEmbeddedDexAttr()) {
                                                            attributeBooleanValue11 |= xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", str6, false);
                                                        }
                                                    }
                                                }
                                                str5 = str6;
                                                z2 = zEquals;
                                                i8 = i12;
                                                strArr6 = strArr8;
                                                attributeBooleanValue12 = z13;
                                                jArrAppendLong = jArr4;
                                                depth2 = i3;
                                                zEquals = z2;
                                                str6 = str5;
                                                break;
                                            case 3:
                                                ArrayList arrayList7 = arrayList4;
                                                boolean z14 = attributeBooleanValue12;
                                                String attributeValue10 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                                int attributeIntValue9 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "version", -1);
                                                int attributeIntValue10 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "versionMajor", 0);
                                                if (attributeValue10 == null || attributeIntValue9 < 0) {
                                                    break;
                                                } else {
                                                    arrayList4 = arrayList7;
                                                    arrayList4.add(new SharedLibraryInfo(attributeValue10, PackageInfo.composeLongVersionCode(attributeIntValue10, attributeIntValue9), i8));
                                                    str5 = str6;
                                                    z2 = zEquals;
                                                    strArr6 = strArr8;
                                                    attributeBooleanValue12 = z14;
                                                    jArrAppendLong = jArr4;
                                                    z8 = true;
                                                    depth2 = i3;
                                                    zEquals = z2;
                                                    str6 = str5;
                                                }
                                                break;
                                            case 4:
                                                zIsDeviceAdminReceiver |= isDeviceAdminReceiver(xmlResourceParser, zEquals);
                                                z2 = zEquals;
                                                strArr6 = strArr8;
                                                jArrAppendLong = jArr4;
                                                str5 = str6;
                                                depth2 = i3;
                                                zEquals = z2;
                                                str6 = str5;
                                            case 5:
                                                z2 = zEquals;
                                                String attributeValue11 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                                ArrayList arrayList8 = arrayList4;
                                                boolean z15 = attributeBooleanValue12;
                                                long attributeIntValue11 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "version", -1);
                                                String attributeValue12 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "certDigest");
                                                if (attributeValue11 == null || attributeValue11.isBlank() || attributeIntValue11 < 0 || attributeValue12 == null) {
                                                    break;
                                                } else if (arrayList3.contains(attributeValue11)) {
                                                    break;
                                                } else {
                                                    arrayList3.add(attributeValue11);
                                                    jArrAppendLong2 = ArrayUtils.appendLong(jArrAppendLong2, attributeIntValue11, true);
                                                    String strNormalizeCertDigest2 = normalizeCertDigest(attributeValue12);
                                                    ParseResult<String[]> additionalCertificates = parseAdditionalCertificates(parseInput, xmlResourceParser);
                                                    if (additionalCertificates.isError()) {
                                                        break;
                                                    } else {
                                                        String[] result3 = additionalCertificates.getResult();
                                                        String[] strArr9 = new String[result3.length + 1];
                                                        strArr9[0] = strNormalizeCertDigest2;
                                                        System.arraycopy(result3, 0, strArr9, 1, result3.length);
                                                        strArr7 = (String[][]) ArrayUtils.appendElement(String[].class, strArr7, strArr9, true);
                                                        strArr6 = strArr8;
                                                        attributeBooleanValue12 = z15;
                                                        jArrAppendLong = jArr4;
                                                        arrayList4 = arrayList8;
                                                        str5 = str6;
                                                        depth2 = i3;
                                                        zEquals = z2;
                                                        str6 = str5;
                                                    }
                                                }
                                                break;
                                            case 6:
                                                String attributeValue13 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                                if (attributeValue13 == null) {
                                                    break;
                                                } else {
                                                    z2 = zEquals;
                                                    arrayList4.add(new SharedLibraryInfo(attributeValue13.intern(), -1L, 1));
                                                    str5 = str6;
                                                    strArr6 = strArr8;
                                                    jArrAppendLong = jArr4;
                                                    depth2 = i3;
                                                    zEquals = z2;
                                                    str6 = str5;
                                                }
                                            case 7:
                                                attributeBooleanValue12 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "shell", attributeBooleanValue12);
                                                str5 = str6;
                                                z2 = zEquals;
                                                strArr6 = strArr8;
                                                jArrAppendLong = jArr4;
                                                depth2 = i3;
                                                zEquals = z2;
                                                str6 = str5;
                                            default:
                                                z2 = zEquals;
                                                str5 = str6;
                                                strArr6 = strArr8;
                                                jArrAppendLong = jArr4;
                                                depth2 = i3;
                                                zEquals = z2;
                                                str6 = str5;
                                        }
                                        return parseInput.error(packageSplitNames);
                                    }
                                    str5 = str6;
                                    jArr = jArrAppendLong;
                                    z2 = zEquals;
                                    i3 = depth2;
                                    z3 = attributeBooleanValue12;
                                    strArr = strArr8;
                                }
                                jArrAppendLong = jArr;
                                strArr6 = strArr;
                                attributeBooleanValue12 = z3;
                                depth2 = i3;
                                zEquals = z2;
                                str6 = str5;
                            }
                        }
                    } else {
                        pair = result;
                        i2 = depth;
                        arrayList = arrayList5;
                        str2 = attributeValue3;
                        str3 = attributeValue4;
                        str4 = attributeValue5;
                        if ("overlay".equals(xmlResourceParser.getName())) {
                            attributeValue4 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSystemPropertyName");
                            attributeValue5 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSystemPropertyValue");
                            attributeValue3 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "targetPackage");
                            attributeBooleanValue9 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isStatic", false);
                            attributeIntValue5 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "priority", 0);
                            z5 = false;
                            attributeBooleanValue5 = z9;
                            result2 = pair2;
                            depth = i2;
                            arrayList5 = arrayList;
                            result = pair;
                        } else {
                            z = false;
                            if (!"uses-split".equals(xmlResourceParser.getName())) {
                                if ("uses-sdk".equals(xmlResourceParser.getName())) {
                                    String attributeValue14 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "minSdkVersion");
                                    String attributeValue15 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "targetSdkVersion");
                                    if (TextUtils.isEmpty(attributeValue14)) {
                                        i7 = 1;
                                        z4 = false;
                                        i5 = 1;
                                        attributeValue14 = null;
                                    } else {
                                        try {
                                            i5 = Integer.parseInt(attributeValue14);
                                            attributeValue14 = null;
                                            z4 = true;
                                            i7 = 1;
                                        } catch (NumberFormatException unused2) {
                                            i7 = 1;
                                            z4 = !TextUtils.isEmpty(attributeValue14);
                                            i5 = 1;
                                        }
                                    }
                                    if (TextUtils.isEmpty(attributeValue15)) {
                                        attributeValue15 = attributeValue14;
                                        i6 = i5;
                                    } else {
                                        try {
                                            i6 = Integer.parseInt(attributeValue15);
                                            attributeValue15 = null;
                                        } catch (NumberFormatException unused3) {
                                            if (!z4) {
                                                attributeValue14 = attributeValue15;
                                            }
                                            i6 = 0;
                                        }
                                    }
                                    boolean z16 = (i & 512) != 0 ? i7 : 0;
                                    String[] strArr10 = SDK_CODENAMES;
                                    ParseResult<?> parseResultComputeTargetSdkVersion = FrameworkParsingPackageUtils.computeTargetSdkVersion(i6, attributeValue15, strArr10, parseInput, z16);
                                    if (parseResultComputeTargetSdkVersion.isError()) {
                                        return parseInput.error(parseResultComputeTargetSdkVersion);
                                    }
                                    iIntValue2 = parseResultComputeTargetSdkVersion.getResult().intValue();
                                    ParseResult<?> parseResultComputeMinSdkVersion = FrameworkParsingPackageUtils.computeMinSdkVersion(i5, attributeValue14, SDK_VERSION, strArr10, parseInput);
                                    if (parseResultComputeMinSdkVersion.isError()) {
                                        return parseInput.error(parseResultComputeMinSdkVersion);
                                    }
                                    iIntValue = parseResultComputeMinSdkVersion.getResult().intValue();
                                }
                                z5 = z;
                                attributeBooleanValue5 = z9;
                                result2 = pair2;
                                depth = i2;
                                arrayList5 = arrayList;
                                result = pair;
                                attributeValue3 = str2;
                                attributeValue4 = str3;
                                attributeValue5 = str4;
                            } else if (attributeValue6 != null) {
                                Slog.w(TAG, "Only one <uses-split> permitted. Ignoring others.");
                            } else {
                                attributeValue6 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                if (attributeValue6 == null) {
                                    return parseInput.error(-108, "<uses-split> tag requires 'android:name' attribute");
                                }
                                z5 = false;
                                attributeBooleanValue5 = z9;
                                result2 = pair2;
                                depth = i2;
                                arrayList5 = arrayList;
                                result = pair;
                                attributeValue3 = str2;
                                attributeValue4 = str3;
                                attributeValue5 = str4;
                            }
                            i7 = 1;
                            z5 = z;
                            attributeBooleanValue5 = z9;
                            result2 = pair2;
                            depth = i2;
                            arrayList5 = arrayList;
                            result = pair;
                            attributeValue3 = str2;
                            attributeValue4 = str3;
                            attributeValue5 = str4;
                        }
                        i7 = 1;
                    }
                }
                pair = result;
                i2 = depth;
                arrayList = arrayList5;
                str2 = attributeValue3;
                str3 = attributeValue4;
                str4 = attributeValue5;
                z = z5;
                i7 = 1;
                z5 = z;
                attributeBooleanValue5 = z9;
                result2 = pair2;
                depth = i2;
                arrayList5 = arrayList;
                result = pair;
                attributeValue3 = str2;
                attributeValue4 = str3;
                attributeValue5 = str4;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        return r4.success(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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

    public static ParseResult<Pair<String, String>> parsePackageSplitNames(ParseInput parseInput, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            return parseInput.error(-108, "No start tag found");
        }
        if (!xmlResourceParser.getName().equals("manifest")) {
            return parseInput.error(-108, "No <manifest> tag");
        }
        String strIntern = null;
        String attributeValue = xmlResourceParser.getAttributeValue(null, "package");
        if (!"android".equals(attributeValue)) {
            ParseResult parseResultValidateName = FrameworkParsingPackageUtils.validateName(parseInput, attributeValue, true, true);
            if (parseResultValidateName.isError()) {
                return parseInput.error(-106, "Invalid manifest package: " + parseResultValidateName.getErrorMessage());
            }
        }
        String attributeValue2 = xmlResourceParser.getAttributeValue(null, "split");
        if (attributeValue2 == null) {
            strIntern = attributeValue2;
        } else if (attributeValue2.length() != 0) {
            ParseResult parseResultValidateName2 = FrameworkParsingPackageUtils.validateName(parseInput, attributeValue2, false, false);
            if (parseResultValidateName2.isError()) {
                return parseInput.error(-106, "Invalid manifest split: " + parseResultValidateName2.getErrorMessage());
            }
            strIntern = attributeValue2;
        }
        String strIntern2 = attributeValue.intern();
        if (strIntern != null) {
            strIntern = strIntern.intern();
        }
        return parseInput.success(Pair.create(strIntern2, strIntern));
    }

    public static ParseResult<Pair<Set<String>, Set<String>>> parseRequiredSplitTypes(ParseInput parseInput, XmlResourceParser xmlResourceParser) {
        Set<String> result;
        String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSplitTypes");
        Set<String> result2 = null;
        if (TextUtils.isEmpty(attributeValue)) {
            result = null;
        } else {
            ParseResult<Set<String>> parseResultSeparateAndValidateSplitTypes = separateAndValidateSplitTypes(parseInput, attributeValue);
            if (parseResultSeparateAndValidateSplitTypes.isError()) {
                return parseInput.error(parseResultSeparateAndValidateSplitTypes);
            }
            result = parseResultSeparateAndValidateSplitTypes.getResult();
        }
        String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "splitTypes");
        if (!TextUtils.isEmpty(attributeValue2)) {
            ParseResult<Set<String>> parseResultSeparateAndValidateSplitTypes2 = separateAndValidateSplitTypes(parseInput, attributeValue2);
            if (parseResultSeparateAndValidateSplitTypes2.isError()) {
                return parseInput.error(parseResultSeparateAndValidateSplitTypes2);
            }
            result2 = parseResultSeparateAndValidateSplitTypes2.getResult();
        }
        return parseInput.success(Pair.create(result, result2));
    }

    private static ParseResult<Set<String>> separateAndValidateSplitTypes(ParseInput parseInput, String str) {
        ArraySet arraySet = new ArraySet();
        for (String str2 : str.trim().split(",")) {
            String strTrim = str2.trim();
            ParseResult parseResultValidateName = FrameworkParsingPackageUtils.validateName(parseInput, strTrim, false, true);
            if (parseResultValidateName.isError()) {
                return parseInput.error(-108, "Invalid manifest split types: " + parseResultValidateName.getErrorMessage());
            }
            if (!arraySet.add(strTrim)) {
                Slog.w(TAG, strTrim + " was defined multiple times");
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
        PublicKey publicKey = FrameworkParsingPackageUtils.parsePublicKey(attributeValue2);
        if (publicKey == null) {
            Slog.i(TAG, "Unable to parse verifier public key for " + attributeValue);
            return null;
        }
        return new VerifierInfo(attributeValue, publicKey);
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
