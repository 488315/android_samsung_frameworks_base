package com.android.server.pm;

import android.content.Context;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageInstaller;
import android.content.pm.PackagePartitions;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SELinux;
import android.os.SystemProperties;
import android.os.incremental.IncrementalManager;
import android.os.incremental.V4Signature;
import android.security.Flags;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Base64;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.HexDump;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

import libcore.io.IoUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.GZIPInputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PackageManagerServiceUtils {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final PackageManagerServiceUtils$$ExternalSyntheticLambda0 REMOVE_IF_APEX_PKG;
    public static final PackageManagerServiceUtils$$ExternalSyntheticLambda0 REMOVE_IF_NULL_PKG;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.PackageManagerServiceUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements FilenameFilter {
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return str.toLowerCase().endsWith(".gz");
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda0] */
    static {
        final int i = 0;
        REMOVE_IF_APEX_PKG =
                new Predicate() { // from class:
                                  // com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                        switch (i) {
                            case 0:
                                return packageStateInternal.getPkg().isApex();
                            default:
                                return packageStateInternal.getPkg() == null;
                        }
                    }
                };
        final int i2 = 1;
        REMOVE_IF_NULL_PKG =
                new Predicate() { // from class:
                                  // com.android.server.pm.PackageManagerServiceUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                        switch (i2) {
                            case 0:
                                return packageStateInternal.getPkg().isApex();
                            default:
                                return packageStateInternal.getPkg() == null;
                        }
                    }
                };
    }

    public static String buildVerificationRootHashString(String str, String[] strArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(str.lastIndexOf(File.separator) + 1));
        sb.append(":");
        byte[] rootHash = getRootHash(str);
        if (rootHash == null) {
            sb.append("0");
        } else {
            sb.append(HexDump.toHexString(rootHash));
        }
        if (strArr == null || strArr.length == 0) {
            return sb.toString();
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            String str2 = strArr[length];
            String substring = str2.substring(str2.lastIndexOf(File.separator) + 1);
            byte[] rootHash2 = getRootHash(str2);
            sb.append(";");
            sb.append(substring);
            sb.append(":");
            if (rootHash2 == null) {
                sb.append("0");
            } else {
                sb.append(HexDump.toHexString(rootHash2));
            }
        }
        return sb.toString();
    }

    public static boolean canJoinSharedUserId(
            String str, SigningDetails signingDetails, SharedUserSetting sharedUserSetting, int i) {
        SigningDetails signingDetails2 = sharedUserSetting.signatures.mSigningDetails;
        boolean z =
                signingDetails.checkCapability(signingDetails2, 2)
                        || signingDetails2.checkCapability(signingDetails, 2);
        if (z && i != 0) {
            return true;
        }
        if (!z && signingDetails2.hasAncestor(signingDetails)) {
            return i == 2;
        }
        if (!z && signingDetails.hasAncestor(signingDetails2)) {
            return i != 0;
        }
        if (!z) {
            return false;
        }
        ArraySet arraySet = sharedUserSetting.mPackages.mStorage;
        if (signingDetails.hasPastSigningCertificates()) {
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
                SigningDetails signingDetails3 = packageStateInternal.getSigningDetails();
                if (signingDetails.hasAncestor(signingDetails3)
                        && !signingDetails.checkCapability(signingDetails3, 2)) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Package ",
                                    str,
                                    " revoked the sharedUserId capability from the signing key used"
                                        + " to sign ");
                    m.append(packageStateInternal.getPackageName());
                    Slog.d("PackageManager", m.toString());
                    return false;
                }
            }
        }
        return true;
    }

    public static void checkDowngrade(
            long j, int i, String[] strArr, int[] iArr, PackageInfoLite packageInfoLite) {
        if (packageInfoLite.getLongVersionCode() < j) {
            throw new PackageManagerException(
                    -25,
                    "Update version code "
                            + packageInfoLite.versionCode
                            + " is older than current "
                            + j);
        }
        if (packageInfoLite.getLongVersionCode() != j) {
            return;
        }
        if (packageInfoLite.baseRevisionCode < i) {
            throw new PackageManagerException(
                    -25,
                    "Update base revision code "
                            + packageInfoLite.baseRevisionCode
                            + " is older than current "
                            + i);
        }
        if (ArrayUtils.isEmpty(packageInfoLite.splitNames)) {
            return;
        }
        if (strArr.length != iArr.length) {
            throw new PackageManagerException(
                    -25,
                    "Current split names and the split revision codes are not 1:1 mapping.This"
                        + " indicates that the package info data has been corrupted.");
        }
        int i2 = 0;
        while (true) {
            String[] strArr2 = packageInfoLite.splitNames;
            if (i2 >= strArr2.length) {
                return;
            }
            String str = strArr2[i2];
            int indexOf = ArrayUtils.indexOf(strArr, str);
            if (indexOf != -1 && packageInfoLite.splitRevisionCodes[i2] < iArr[indexOf]) {
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Update split ", str, " revision code ");
                m.append(packageInfoLite.splitRevisionCodes[i2]);
                m.append(" is older than current ");
                m.append(iArr[indexOf]);
                throw new PackageManagerException(-25, m.toString());
            }
            i2++;
        }
    }

    public static boolean comparePackageSignatures(
            PackageSetting packageSetting, SigningDetails signingDetails) {
        SigningDetails signingDetails2 = packageSetting.signatures.mSigningDetails;
        return signingDetails2 == SigningDetails.UNKNOWN
                || compareSignatures(signingDetails2, signingDetails) == 0;
    }

    public static int compareSignatureArrays(Signature[] signatureArr, Signature[] signatureArr2) {
        if (signatureArr == null) {
            return signatureArr2 == null ? 1 : -1;
        }
        if (signatureArr2 == null) {
            return -2;
        }
        if (signatureArr.length != signatureArr2.length) {
            return -3;
        }
        if (signatureArr.length == 1) {
            return signatureArr[0].equals(signatureArr2[0]) ? 0 : -3;
        }
        ArraySet arraySet = new ArraySet();
        for (Signature signature : signatureArr) {
            arraySet.add(signature);
        }
        ArraySet arraySet2 = new ArraySet();
        for (Signature signature2 : signatureArr2) {
            arraySet2.add(signature2);
        }
        return arraySet.equals(arraySet2) ? 0 : -3;
    }

    public static int compareSignatures(
            SigningDetails signingDetails, SigningDetails signingDetails2) {
        return compareSignatureArrays(
                signingDetails.getSignatures(), signingDetails2.getSignatures());
    }

    public static void copyFile(File file, String str, String str2) {
        if (!FileUtils.isValidExtFilename(str2)) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Invalid filename: ", str2));
        }
        Slog.d("PackageManager", "Copying " + str + " to " + str2);
        File file2 = new File(file, str2);
        FileDescriptor open =
                Os.open(
                        file2.getAbsolutePath(),
                        OsConstants.O_RDWR | OsConstants.O_CREAT,
                        FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
        Os.chmod(file2.getAbsolutePath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                FileUtils.copy(fileInputStream2.getFD(), open);
                IoUtils.closeQuietly(fileInputStream2);
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                IoUtils.closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static int copyPackage(File file, String str) {
        if (str == null) {
            return -3;
        }
        try {
            ParseResult parsePackageLite =
                    ApkLiteParseUtils.parsePackageLite(
                            ParseTypeImpl.forDefaultParsing().reset(), new File(str), 0);
            if (parsePackageLite.isError()) {
                Slog.w("PackageManager", "Failed to parse package at ".concat(str));
                return parsePackageLite.getErrorCode();
            }
            PackageLite packageLite = (PackageLite) parsePackageLite.getResult();
            copyFile(file, packageLite.getBaseApkPath(), "base.apk");
            if (ArrayUtils.isEmpty(packageLite.getSplitNames())) {
                return 1;
            }
            for (int i = 0; i < packageLite.getSplitNames().length; i++) {
                copyFile(
                        file,
                        packageLite.getSplitApkPaths()[i],
                        "split_" + packageLite.getSplitNames()[i] + ".apk");
            }
            return 1;
        } catch (ErrnoException | IOException e) {
            Slog.w("PackageManager", "Failed to copy package at " + str + ": " + e);
            return -4;
        }
    }

    public static int decompressFile(File file, File file2) {
        if (PackageManagerService.DEBUG_COMPRESSION) {
            Slog.i(
                    "PackageManager",
                    "Decompress file; src: "
                            + file.getAbsolutePath()
                            + ", dst: "
                            + file2.getAbsolutePath());
        }
        AtomicFile atomicFile = new AtomicFile(file2);
        FileOutputStream fileOutputStream = null;
        try {
            GZIPInputStream gZIPInputStream = new GZIPInputStream(new FileInputStream(file));
            try {
                fileOutputStream = atomicFile.startWrite();
                FileUtils.copy(gZIPInputStream, fileOutputStream);
                fileOutputStream.flush();
                Os.fchmod(fileOutputStream.getFD(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                atomicFile.finishWrite(fileOutputStream);
                gZIPInputStream.close();
                return 1;
            } finally {
            }
        } catch (IOException unused) {
            logCriticalInfo(
                    6,
                    "Failed to decompress file; src: "
                            + file.getAbsolutePath()
                            + ", dst: "
                            + file2.getAbsolutePath());
            atomicFile.failWrite(fileOutputStream);
            return -110;
        }
    }

    public static void dumpCriticalInfo(PrintWriter printWriter, String str) {
        File settingsProblemFile = getSettingsProblemFile();
        long length = settingsProblemFile.length() - 3000000;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(settingsProblemFile));
            if (length > 0) {
                try {
                    bufferedReader.skip(length);
                } finally {
                }
            }
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return;
                } else if (!readLine.contains("ignored: updated version")) {
                    if (str != null) {
                        printWriter.print(str);
                    }
                    printWriter.println(readLine);
                }
            }
        } catch (IOException unused) {
        }
    }

    public static void enforceShellRestriction(
            UserManagerInternal userManagerInternal, int i, int i2) {
        if (i == 2000) {
            if (i2 >= 0 && userManagerInternal.hasUserRestriction("no_debugging_features", i2)) {
                throw new SecurityException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i2, "Shell does not have permission to access user "));
            }
            if (i2 < 0) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i2, "Unable to check shell permission for user ", "\n\t");
                m.append(Debug.getCallers(3));
                Slog.e("PackageManager", m.toString());
            }
        }
    }

    public static void enforceSystemOrPhoneCaller(int i, String str) {
        if (i != 1001 && i != 1000) {
            throw new SecurityException(
                    SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                            .m(i, "Cannot call ", str, " from UID "));
        }
    }

    public static void enforceSystemOrRoot(String str) {
        if (!isSystemOrRoot(Binder.getCallingUid())) {
            throw new SecurityException(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0148, code lost:

       return !r9.get();
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0140, code lost:

       if (r9.get() == false) goto L72;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean extractAppMetadataFromApk(
            com.android.server.pm.pkg.AndroidPackage r9, java.lang.String r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageManagerServiceUtils.extractAppMetadataFromApk(com.android.server.pm.pkg.AndroidPackage,"
                    + " java.lang.String, boolean):boolean");
    }

    public static File[] getCompressedFiles(String str) {
        File file = new File(str);
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf("-Stub");
        if (lastIndexOf < 0 || name.length() != lastIndexOf + 5) {
            return null;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Unable to determine stub parent dir for codePath: ", str, "PackageManager");
            return null;
        }
        File[] listFiles =
                new File(parentFile, name.substring(0, lastIndexOf))
                        .listFiles(new AnonymousClass1());
        if (PackageManagerService.DEBUG_COMPRESSION && listFiles != null && listFiles.length > 0) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    DumpUtils$$ExternalSyntheticOutline0.m("getCompressedFiles[", str, "]: "),
                    Arrays.toString(listFiles),
                    "PackageManager");
        }
        return listFiles;
    }

    public static long getLastModifiedTime(AndroidPackage androidPackage) {
        File file = new File(androidPackage.getPath());
        if (!file.isDirectory()) {
            return file.lastModified();
        }
        long lastModified = new File(androidPackage.getBaseApkPath()).lastModified();
        for (int length = androidPackage.getSplitCodePaths().length - 1; length >= 0; length--) {
            lastModified =
                    Math.max(
                            lastModified,
                            new File(androidPackage.getSplitCodePaths()[length]).lastModified());
        }
        return lastModified;
    }

    public static PackageInfoLite getMinimalPackageInfo(
            Context context, PackageLite packageLite, String str, int i, String str2) {
        long calculateInstalledSize;
        PackageInfoLite packageInfoLite = new PackageInfoLite();
        if (str == null || packageLite == null) {
            Slog.i("PackageManager", "Invalid package file " + str);
            packageInfoLite.recommendedInstallLocation = -2;
            return packageInfoLite;
        }
        File file = new File(str);
        int[] iArr = PackageInstallerSession.EMPTY_CHILD_SESSION_ARRAY;
        if ((134217728 & i) != 0) {
            calculateInstalledSize = 0;
        } else {
            try {
                calculateInstalledSize =
                        InstallLocationUtils.calculateInstalledSize(packageLite, str2);
            } catch (IOException unused) {
                if (file.exists()) {
                    packageInfoLite.recommendedInstallLocation = -2;
                } else {
                    packageInfoLite.recommendedInstallLocation = -6;
                }
                return packageInfoLite;
            }
        }
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(-1);
        sessionParams.appPackageName = packageLite.getPackageName();
        sessionParams.installLocation = packageLite.getInstallLocation();
        sessionParams.sizeBytes = calculateInstalledSize;
        sessionParams.installFlags = i;
        try {
            int resolveInstallLocation =
                    InstallLocationUtils.resolveInstallLocation(context, sessionParams);
            packageInfoLite.packageName = packageLite.getPackageName();
            packageInfoLite.splitNames = packageLite.getSplitNames();
            packageInfoLite.versionCode = packageLite.getVersionCode();
            packageInfoLite.versionCodeMajor = packageLite.getVersionCodeMajor();
            packageInfoLite.baseRevisionCode = packageLite.getBaseRevisionCode();
            packageInfoLite.splitRevisionCodes = packageLite.getSplitRevisionCodes();
            packageInfoLite.installLocation = packageLite.getInstallLocation();
            packageInfoLite.verifiers = packageLite.getVerifiers();
            packageInfoLite.recommendedInstallLocation = resolveInstallLocation;
            packageInfoLite.multiArch = packageLite.isMultiArch();
            packageInfoLite.debuggable = packageLite.isDebuggable();
            packageInfoLite.isSdkLibrary = packageLite.isIsSdkLibrary();
            return packageInfoLite;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static File getNextCodePath(File file, String str) {
        File file2;
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        do {
            secureRandom.nextBytes(bArr);
            file2 = new File(file, "~~" + Base64.encodeToString(bArr, 10));
        } while (file2.exists());
        secureRandom.nextBytes(bArr);
        File file3 = new File(file2, str + '-' + Base64.encodeToString(bArr, 10));
        if (DEBUG) {
            String name = file3.getName();
            int indexOf = name.indexOf(45);
            if (indexOf == -1) {
                throw new IllegalArgumentException("Not a valid package folder name");
            }
            if (!Objects.equals(name.substring(0, indexOf), str)) {
                throw new RuntimeException(
                        "codepath is off: " + file3.getName() + " (" + str + ")");
            }
        }
        return file3;
    }

    public static PackageManagerLocal getPackageManagerLocal() {
        try {
            return (PackageManagerLocal)
                    LocalManagerRegistry.getManagerOrThrow(PackageManagerLocal.class);
        } catch (LocalManagerRegistry.ManagerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getRootHash(String str) {
        try {
            byte[] unsafeGetFileSignature = IncrementalManager.unsafeGetFileSignature(str);
            if (unsafeGetFileSignature == null) {
                throw new IOException("File signature not present");
            }
            byte[] bArr = V4Signature.readFrom(unsafeGetFileSignature).hashingInfo;
            if (bArr == null) {
                throw new IOException("Hashing info not present");
            }
            V4Signature.HashingInfo fromByteArray = V4Signature.HashingInfo.fromByteArray(bArr);
            if (ArrayUtils.isEmpty(fromByteArray.rawRootHash)) {
                throw new IOException("Root has not present");
            }
            return ApkChecksums.verityHashForFile(new File(str), fromByteArray.rawRootHash);
        } catch (IOException e) {
            Slog.i("PackageManager", "Could not obtain verity root hash", e);
            return null;
        }
    }

    public static File getSettingsProblemFile() {
        return new File(new File(Environment.getDataDirectory(), "system"), "uiderrors.txt");
    }

    public static boolean isDowngradePermitted(int i, boolean z) {
        if ((i & 128) != 0) {
            return Build.IS_DEBUGGABLE || z || (i & 1048576) != 0;
        }
        return false;
    }

    public static boolean isInstalledByAdb(String str) {
        return str == null || "com.android.shell".equals(str);
    }

    public static boolean isRootOrShell(int i) {
        return i == 0 || i == 2000;
    }

    public static boolean isSystemOrRoot(int i) {
        return i == 1000 || i == 0;
    }

    public static boolean isSystemOrRootOrShell(int i) {
        return i == 1000 || i == 0 || i == 2000;
    }

    public static void linkFilesAndSetModes(
            Installer installer, String str, File file, File file2, File[] fileArr, int i) {
        for (File file3 : fileArr) {
            String name = file3.getName();
            File file4 = new File(file, name);
            File file5 = new File(file2, name);
            boolean exists = file5.exists();
            boolean z = DEBUG;
            if (!exists) {
                try {
                } catch (Installer.InstallerException e) {
                    e = e;
                }
                try {
                    installer.linkFile(str, name, file.getAbsolutePath(), file2.getAbsolutePath());
                    if (z) {
                        Slog.d("PackageManager", "Linked <" + file4 + "> to <" + file5 + ">");
                    }
                    try {
                        try {
                            Os.chmod(file5.getAbsolutePath(), i);
                            if (!SELinux.restorecon(file5)) {
                                Slog.w(
                                        "PackageManager",
                                        "Failed to restorecon for linked file <" + file5 + ">");
                            }
                        } catch (ErrnoException e2) {
                            e = e2;
                            Slog.w(
                                    "PackageManager",
                                    "Failed to set mode for linked file <" + file5 + ">",
                                    e);
                        }
                    } catch (ErrnoException e3) {
                        e = e3;
                    }
                } catch (Installer.InstallerException e4) {
                    e = e4;
                    Slog.w(
                            "PackageManager",
                            "Failed to link native library <" + file4 + "> to <" + file5 + ">",
                            e);
                }
            } else if (z) {
                Slog.d("PackageManager", "Skipping existing linked file <" + file5 + ">");
            }
        }
    }

    public static void linkFilesToOldDirs(Installer installer, String str, File file, Set set) {
        File[] listFiles;
        if (set == null
                || set.isEmpty()
                || IncrementalManager.isIncrementalPath(file.getPath())
                || (listFiles = file.listFiles()) == null
                || listFiles.length == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (File file2 : listFiles) {
            if (!file2.isDirectory() && file2.toString().endsWith(".apk")) {
                arrayList.add(file2);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        File[] fileArr = (File[]) arrayList.toArray(new File[0]);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            File file3 = (File) it.next();
            if (file3.exists()) {
                linkFilesAndSetModes(
                        installer,
                        str,
                        file,
                        file3,
                        fileArr,
                        FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                linkNativeLibraries(installer, str, file, file3, "lib");
                linkNativeLibraries(installer, str, file, file3, "lib64");
            }
        }
    }

    public static void linkNativeLibraries(
            Installer installer, String str, File file, File file2, String str2) {
        File file3 = new File(file, str2);
        if (file3.exists()) {
            File file4 = new File(file2, str2);
            if (!file4.exists()) {
                try {
                    NativeLibraryHelper.createNativeLibrarySubdir(file4);
                } catch (IOException e) {
                    Slog.w(
                            "PackageManager",
                            "Failed to create native library dir at <" + file4 + ">",
                            e);
                    return;
                }
            }
            File[] listFiles = file3.listFiles();
            if (listFiles == null) {
                return;
            }
            for (File file5 : listFiles) {
                File file6 = new File(file4, file5.getName());
                if (!file6.exists()) {
                    try {
                        NativeLibraryHelper.createNativeLibrarySubdir(file6);
                    } catch (IOException e2) {
                        Slog.w(
                                "PackageManager",
                                "Failed to create native library subdir at <" + file6 + ">",
                                e2);
                    }
                }
                File file7 = new File(file3, file5.getName());
                File[] listFiles2 = file7.listFiles();
                if (listFiles2 != null && listFiles2.length != 0) {
                    linkFilesAndSetModes(installer, str, file7, file6, listFiles2, 493);
                }
            }
        }
    }

    public static void logCriticalInfo(int i, String str) {
        Slog.println(i, "PackageManager", str);
        EventLog.writeEvent(3120, str);
        try {
            File settingsProblemFile = getSettingsProblemFile();
            FastPrintWriter fastPrintWriter =
                    new FastPrintWriter(new FileOutputStream(settingsProblemFile, true));
            fastPrintWriter.println(
                    new SimpleDateFormat().format(new Date(System.currentTimeMillis()))
                            + ": "
                            + str);
            fastPrintWriter.close();
            FileUtils.setPermissions(settingsProblemFile.toString(), 508, -1, -1);
        } catch (IOException unused) {
        }
    }

    public static void makeDirRecursive(File file, int i) {
        Path path = file.toPath();
        int nameCount = path.getNameCount();
        for (int i2 = 1; i2 <= nameCount; i2++) {
            File file2 = path.subpath(0, i2).toFile();
            if (!file2.exists()) {
                Os.mkdir(file2.getAbsolutePath(), i);
                Os.chmod(file2.getAbsolutePath(), i);
            }
        }
    }

    public static boolean matchSignaturesCompat(
            String str, PackageSignatures packageSignatures, SigningDetails signingDetails) {
        ArraySet arraySet = new ArraySet();
        for (Signature signature : packageSignatures.mSigningDetails.getSignatures()) {
            arraySet.add(signature);
        }
        ArraySet arraySet2 = new ArraySet();
        for (Signature signature2 : signingDetails.getSignatures()) {
            try {
                for (Signature signature3 : signature2.getChainSignatures()) {
                    arraySet2.add(signature3);
                }
            } catch (CertificateEncodingException unused) {
                arraySet2.add(signature2);
            }
        }
        if (arraySet2.equals(arraySet)) {
            packageSignatures.mSigningDetails = signingDetails;
            return true;
        }
        if (signingDetails.hasPastSigningCertificates()) {
            logCriticalInfo(
                    4,
                    "Existing package "
                            + str
                            + " has flattened signing certificate chain. Unable to install newer"
                            + " version with rotated signing certificate.");
        }
        return false;
    }

    public static boolean matchSignaturesRecover(
            String str, SigningDetails signingDetails, SigningDetails signingDetails2, int i) {
        String message;
        try {
        } catch (CertificateException e) {
            message = e.getMessage();
        }
        if (signingDetails2.checkCapabilityRecover(signingDetails, i)) {
            logCriticalInfo(4, "Recovered effectively matching certificates for " + str);
            return true;
        }
        message = null;
        logCriticalInfo(4, "Failed to recover certificates for " + str + ": " + message);
        return false;
    }

    public static File preparePackageParserCache(String str, boolean z, boolean z2) {
        if (z) {
            return null;
        }
        if (SystemProperties.getBoolean("pm.boot.disable_package_cache", false)) {
            Slog.i("PackageManager", "Disabling package parser cache due to system property.");
            return null;
        }
        File packageCacheDirectory = Environment.getPackageCacheDirectory();
        if (!FileUtils.createDir(packageCacheDirectory)) {
            return null;
        }
        String str2 = PackagePartitions.FINGERPRINT;
        for (File file : FileUtils.listFilesOrEmpty(packageCacheDirectory)) {
            if (Objects.equals(str2, file.getName())) {
                Slog.d("PackageManager", "Keeping known cache " + file.getName());
            } else {
                Slog.d("PackageManager", "Destroying unknown cache " + file.getName());
                FileUtils.deleteContentsAndDir(file);
            }
        }
        File createDir = FileUtils.createDir(packageCacheDirectory, str2);
        if (createDir == null) {
            Slog.wtf(
                    "PackageManager",
                    "Cache directory cannot be created - wiping base dir " + packageCacheDirectory);
            FileUtils.deleteContentsAndDir(packageCacheDirectory);
            return null;
        }
        if (!z2 || !str.startsWith("eng.")) {
            return createDir;
        }
        Slog.w("PackageManager", "Wiping cache directory because the system partition changed.");
        if (createDir.lastModified()
                >= new File(Environment.getRootDirectory(), "framework").lastModified()) {
            return createDir;
        }
        FileUtils.deleteContents(packageCacheDirectory);
        return FileUtils.createDir(packageCacheDirectory, str2);
    }

    public static boolean verifySignatures(
            PackageSetting packageSetting,
            SharedUserSetting sharedUserSetting,
            PackageSetting packageSetting2,
            SigningDetails signingDetails,
            boolean z,
            boolean z2,
            boolean z3) {
        boolean z4;
        SigningDetails signingDetails2;
        String str = packageSetting.mName;
        if (packageSetting.signatures.mSigningDetails.getSignatures() != null) {
            boolean z5 =
                    signingDetails.checkCapability(packageSetting.signatures.mSigningDetails, 1)
                            || packageSetting.signatures.mSigningDetails.checkCapability(
                                    signingDetails, 8);
            if (Flags.extendVbChainToUpdatedApk()
                    && z5
                    && packageSetting2 != null
                    && (signingDetails2 = packageSetting2.signatures.mSigningDetails)
                            != SigningDetails.UNKNOWN) {
                if (signingDetails.checkCapability(signingDetails2, 1)
                        || packageSetting2.signatures.mSigningDetails.checkCapability(
                                signingDetails, 8)) {
                    z5 = true;
                } else {
                    logCriticalInfo(6, "Updated system app mismatches cert on /system: " + str);
                    z5 = false;
                }
            }
            if (z5 || !z) {
                z4 = false;
            } else {
                z5 = matchSignaturesCompat(str, packageSetting.signatures, signingDetails);
                z4 = z5;
            }
            if (!z5 && z2) {
                z5 =
                        matchSignaturesRecover(
                                        str,
                                        packageSetting.signatures.mSigningDetails,
                                        signingDetails,
                                        1)
                                || matchSignaturesRecover(
                                        str,
                                        signingDetails,
                                        packageSetting.signatures.mSigningDetails,
                                        8);
            }
            if (!z5 && z3) {
                z5 = packageSetting.signatures.mSigningDetails.hasAncestorOrSelf(signingDetails);
            }
            if (!z5) {
                throw new PackageManagerException(
                        -7,
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Existing package ",
                                str,
                                " signatures do not match newer version; ignoring!"));
            }
        } else {
            z4 = false;
        }
        if (sharedUserSetting != null) {
            PackageSignatures packageSignatures = sharedUserSetting.signatures;
            if (packageSignatures.mSigningDetails != SigningDetails.UNKNOWN) {
                boolean canJoinSharedUserId =
                        canJoinSharedUserId(
                                str,
                                signingDetails,
                                sharedUserSetting,
                                packageSetting.signatures.mSigningDetails.getSignatures() != null
                                        ? 1
                                        : 0);
                if (!canJoinSharedUserId && z) {
                    canJoinSharedUserId =
                            matchSignaturesCompat(str, packageSignatures, signingDetails);
                }
                if (!canJoinSharedUserId && z2) {
                    boolean z6 =
                            matchSignaturesRecover(
                                            str,
                                            packageSignatures.mSigningDetails,
                                            signingDetails,
                                            2)
                                    || matchSignaturesRecover(
                                            str,
                                            signingDetails,
                                            packageSignatures.mSigningDetails,
                                            2);
                    z4 |= z6;
                    canJoinSharedUserId = z6;
                }
                if (!canJoinSharedUserId) {
                    throw new PackageManagerException(
                            -8,
                            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Package ",
                                            str,
                                            " has no signatures that match those in shared user "),
                                    sharedUserSetting.name,
                                    "; ignoring!"));
                }
                if (!signingDetails.hasCommonAncestor(packageSignatures.mSigningDetails)) {
                    throw new PackageManagerException(
                            -8,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Package ",
                                    str,
                                    " has a signing lineage that diverges from the lineage of the"
                                        + " sharedUserId"));
                }
            }
        }
        return z4;
    }
}
