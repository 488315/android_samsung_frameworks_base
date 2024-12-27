package com.android.server;

import android.apex.ApexInfo;
import android.apex.IApexService;
import android.app.compat.CompatChanges;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApexStagedEvent;
import android.content.pm.IBackgroundInstallControlService;
import android.content.pm.IPackageManagerNative;
import android.content.pm.IStagedApexObserver;
import android.content.pm.InstallSourceInfo;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorProperties;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorProperties;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.security.Flags;
import android.text.TextUtils;
import android.util.PackageUtils;
import android.util.Slog;
import android.util.apk.ApkSignatureVerifier;

import com.android.internal.os.IBinaryTransparencyService;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.modules.expresslog.Histogram;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.pm.ApexManager;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;

import libcore.util.HexEncoding;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class BinaryTransparencyService extends SystemService {
    static final String BINARY_HASH_ERROR = "SHA256HashError";
    static final String KEY_ENABLE_BIOMETRIC_PROPERTY_VERIFICATION =
            "enable_biometric_property_verification";
    static final String SYSPROP_NAME_VBETA_DIGEST = "ro.boot.vbmeta.digest";
    static final String VBMETA_DIGEST_UNAVAILABLE = "vbmeta-digest-unavailable";
    static final String VBMETA_DIGEST_UNINITIALIZED = "vbmeta-digest-uninitialized";
    public static final Histogram digestAllPackagesLatency =
            new Histogram(
                    "binary_transparency.value_digest_all_packages_latency_uniform",
                    new Histogram.UniformOptions(
                            50, FullScreenMagnificationGestureHandler.MAX_SCALE, 500.0f));
    public final BiometricLogger mBiometricLogger;
    public final Context mContext;
    public long mMeasurementsLastRecordedMs;
    public final PackageManagerInternal mPackageManagerInternal;
    public final BinaryTransparencyServiceImpl mServiceImpl;
    public String mVbmetaDigest;

    public final class BinaryTransparencyServiceImpl extends IBinaryTransparencyService.Stub {
        public BinaryTransparencyServiceImpl() {}

        public static Map computeApkContentDigest(String str) {
            ParseResult verifySignaturesInternal =
                    ApkSignatureVerifier.verifySignaturesInternal(
                            ParseTypeImpl.forDefaultParsing(), str, 2, false);
            if (!verifySignaturesInternal.isError()) {
                return ((ApkSignatureVerifier.SigningDetailsWithDigests)
                                verifySignaturesInternal.getResult())
                        .contentDigests;
            }
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Failed to compute content digest for ", str, " due to: ");
            m.append(verifySignaturesInternal.getErrorMessage());
            Slog.e("TransparencyService", m.toString());
            return null;
        }

        public static String[] computePackageSignerSha256Digests(SigningInfo signingInfo) {
            if (signingInfo == null) {
                Slog.e("TransparencyService", "signingInfo is null");
                return null;
            }
            Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
            ArrayList arrayList = new ArrayList();
            for (Signature signature : apkContentsSigners) {
                arrayList.add(
                        HexEncoding.encodeToString(
                                PackageUtils.computeSha256DigestBytes(signature.toByteArray()),
                                false));
            }
            return (String[]) arrayList.toArray(new String[1]);
        }

        public static Digest measureApk(String str) {
            Map computeApkContentDigest = computeApkContentDigest(str);
            if (computeApkContentDigest == null) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "Failed to compute content digest for ", str, "TransparencyService");
            } else {
                if (computeApkContentDigest.containsKey(1)) {
                    return new Digest(1, (byte[]) computeApkContentDigest.get(1));
                }
                if (computeApkContentDigest.containsKey(2)) {
                    return new Digest(2, (byte[]) computeApkContentDigest.get(2));
                }
            }
            return new Digest(
                    4,
                    PackageUtils.computeSha256DigestForLargeFileAsBytes(
                            str, PackageUtils.createLargeFileBuffer()));
        }

        public static void writeAppInfoToLog(IBinaryTransparencyService.AppInfo appInfo) {
            String str = appInfo.packageName;
            long j = appInfo.longVersion;
            byte[] bArr = appInfo.digest;
            FrameworkStatsLog.write(
                    FrameworkStatsLog.MOBILE_BUNDLED_APP_INFO_GATHERED,
                    str,
                    j,
                    bArr != null ? HexEncoding.encodeToString(bArr, false) : null,
                    appInfo.digestAlgorithm,
                    appInfo.signerDigests,
                    appInfo.mbaStatus,
                    appInfo.initiator,
                    appInfo.initiatorSignerDigests,
                    appInfo.installer,
                    appInfo.originator,
                    appInfo.splitName);
        }

        public final List collectAllApexInfo(boolean z) {
            ArrayList arrayList = new ArrayList();
            for (PackageInfo packageInfo :
                    BinaryTransparencyService.m46$$Nest$mgetCurrentInstalledApexs(
                            BinaryTransparencyService.this)) {
                PackageStateInternal packageStateInternal =
                        BinaryTransparencyService.this.mPackageManagerInternal
                                .getPackageStateInternal(packageInfo.packageName);
                if (packageStateInternal == null) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Package state is unavailable, ignoring the APEX "),
                            packageInfo.packageName,
                            "TransparencyService");
                } else {
                    PackageSetting packageSetting = (PackageSetting) packageStateInternal;
                    AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
                    if (androidPackageInternal == null) {
                        Slog.w(
                                "TransparencyService",
                                "Skipping the missing APK in " + androidPackageInternal.getPath());
                    } else {
                        Digest measureApk = measureApk(androidPackageInternal.getPath());
                        IBinaryTransparencyService.ApexInfo apexInfo =
                                new IBinaryTransparencyService.ApexInfo();
                        apexInfo.packageName = packageSetting.mName;
                        apexInfo.longVersion = packageSetting.versionCode;
                        apexInfo.digest = measureApk.value;
                        apexInfo.digestAlgorithm = measureApk.algorithm;
                        apexInfo.signerDigests =
                                computePackageSignerSha256Digests(packageSetting.getSigningInfo());
                        if (z) {
                            BinaryTransparencyService binaryTransparencyService =
                                    BinaryTransparencyService.this;
                            String str = packageSetting.mName;
                            binaryTransparencyService.getClass();
                            apexInfo.moduleName =
                                    ApexManager.getInstance().getApexModuleNameForPackageName(str);
                        }
                        arrayList.add(apexInfo);
                    }
                }
            }
            return arrayList;
        }

        public final List collectAllSilentInstalledMbaInfo(Bundle bundle) {
            ArrayList arrayList = new ArrayList();
            for (PackageInfo packageInfo :
                    BinaryTransparencyService.m47$$Nest$mgetNewlyInstalledMbas(
                            BinaryTransparencyService.this)) {
                if (!bundle.containsKey(packageInfo.packageName)) {
                    PackageStateInternal packageStateInternal =
                            BinaryTransparencyService.this.mPackageManagerInternal
                                    .getPackageStateInternal(packageInfo.packageName);
                    if (packageStateInternal == null) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "Package state is unavailable, ignoring the package "),
                                packageInfo.packageName,
                                "TransparencyService");
                    } else {
                        arrayList.addAll(collectAppInfo(packageStateInternal, 3));
                    }
                }
            }
            return arrayList;
        }

        public final List collectAllUpdatedPreloadInfo(final Bundle bundle) {
            final ArrayList arrayList = new ArrayList();
            BinaryTransparencyService.this.mContext.getPackageManager();
            BinaryTransparencyService.this.mPackageManagerInternal.forEachPackageState(
                    new Consumer() { // from class:
                                     // com.android.server.BinaryTransparencyService$BinaryTransparencyServiceImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BinaryTransparencyService.BinaryTransparencyServiceImpl
                                    binaryTransparencyServiceImpl =
                                            BinaryTransparencyService.BinaryTransparencyServiceImpl
                                                    .this;
                            Bundle bundle2 = bundle;
                            ArrayList arrayList2 = arrayList;
                            PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                            binaryTransparencyServiceImpl.getClass();
                            if (packageStateInternal.isUpdatedSystemApp()
                                    && !bundle2.containsKey(
                                            packageStateInternal.getPackageName())) {
                                Slog.d(
                                        "TransparencyService",
                                        "Preload "
                                                + packageStateInternal.getPackageName()
                                                + " at "
                                                + packageStateInternal.getPath()
                                                + " has likely been updated.");
                                arrayList2.addAll(
                                        binaryTransparencyServiceImpl.collectAppInfo(
                                                packageStateInternal, 2));
                            }
                        }
                    });
            return arrayList;
        }

        public final List collectAppInfo(PackageState packageState, int i) {
            ArrayList arrayList = new ArrayList();
            String packageName = packageState.getPackageName();
            long versionCode = packageState.getVersionCode();
            String[] computePackageSignerSha256Digests =
                    computePackageSignerSha256Digests(packageState.getSigningInfo());
            AndroidPackage androidPackage = packageState.getAndroidPackage();
            if (androidPackage == null) {
                Slog.w(
                        "TransparencyService",
                        "Skipping the missing APK in " + packageState.getPath());
                return arrayList;
            }
            for (AndroidPackageSplit androidPackageSplit : androidPackage.getSplits()) {
                IBinaryTransparencyService.AppInfo appInfo =
                        new IBinaryTransparencyService.AppInfo();
                appInfo.packageName = packageName;
                appInfo.longVersion = versionCode;
                appInfo.splitName = androidPackageSplit.getName();
                appInfo.signerDigests = computePackageSignerSha256Digests;
                appInfo.mbaStatus = i;
                Digest measureApk = measureApk(androidPackageSplit.getPath());
                appInfo.digest = measureApk.value;
                appInfo.digestAlgorithm = measureApk.algorithm;
                arrayList.add(appInfo);
            }
            IBinaryTransparencyService.AppInfo appInfo2 =
                    (IBinaryTransparencyService.AppInfo) arrayList.get(0);
            BinaryTransparencyService binaryTransparencyService = BinaryTransparencyService.this;
            String packageName2 = packageState.getPackageName();
            PackageManager packageManager = binaryTransparencyService.mContext.getPackageManager();
            InstallSourceInfo installSourceInfo = null;
            if (packageManager == null) {
                Slog.e("TransparencyService", "Error obtaining an instance of PackageManager.");
            } else {
                try {
                    installSourceInfo = packageManager.getInstallSourceInfo(packageName2);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (installSourceInfo != null) {
                appInfo2.initiator = installSourceInfo.getInitiatingPackageName();
                SigningInfo initiatingPackageSigningInfo =
                        installSourceInfo.getInitiatingPackageSigningInfo();
                if (initiatingPackageSigningInfo != null) {
                    appInfo2.initiatorSignerDigests =
                            computePackageSignerSha256Digests(initiatingPackageSigningInfo);
                }
                appInfo2.installer = installSourceInfo.getInstallingPackageName();
                appInfo2.originator = installSourceInfo.getOriginatingPackageName();
            }
            return arrayList;
        }

        public final String getSignedImageInfo() {
            return BinaryTransparencyService.this.mVbmetaDigest;
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new ShellCommand() { // from class:
                // com.android.server.BinaryTransparencyService.BinaryTransparencyServiceImpl.1
                public static void printHeadersHelper(
                        PrintWriter printWriter, String str, boolean z) {
                    printWriter.print(str.concat(" Info [Format: package_name,package_version,"));
                    if (z) {
                        printWriter.print("package_sha256_digest,");
                    }
                    printWriter.print("content_digest_algorithm:content_digest]:\n");
                }

                public static void printModuleDetails(
                        ModuleInfo moduleInfo, PrintWriter printWriter) {
                    StringBuilder m$1 =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                    printWriter, "--- Module Details ---", "Module name: ");
                    m$1.append((Object) moduleInfo.getName());
                    printWriter.println(m$1.toString());
                    printWriter.println(
                            "Module visibility: "
                                    .concat(moduleInfo.isHidden() ? "hidden" : "visible"));
                }

                public static void printPackageSignerDetails(
                        SigningInfo signingInfo, PrintWriter printWriter) {
                    if (signingInfo == null) {
                        printWriter.println("ERROR: Package's signingInfo is null.");
                        return;
                    }
                    StringBuilder m$1 =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                    printWriter,
                                    "--- Package Signer Info ---",
                                    "Has multiple signers: ");
                    m$1.append(signingInfo.hasMultipleSigners());
                    printWriter.println(m$1.toString());
                    printWriter.println(
                            "Signing key has been rotated: "
                                    + signingInfo.hasPastSigningCertificates());
                    for (Signature signature : signingInfo.getApkContentsSigners()) {
                        String encodeToString =
                                HexEncoding.encodeToString(
                                        PackageUtils.computeSha256DigestBytes(
                                                signature.toByteArray()),
                                        false);
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                                printWriter, "Signer cert's SHA256-digest: ", encodeToString);
                        try {
                            printWriter.println(
                                    "Signing key algorithm: "
                                            + signature.getPublicKey().getAlgorithm());
                        } catch (CertificateException e) {
                            Slog.e(
                                    "ShellCommand",
                                    "Failed to obtain public key of signer for cert with hash: "
                                            + encodeToString,
                                    e);
                        }
                    }
                    if (signingInfo.hasMultipleSigners()
                            || !signingInfo.hasPastSigningCertificates()) {
                        return;
                    }
                    printWriter.println("== Signing Cert Lineage (Excluding The Most Recent) ==");
                    printWriter.println(
                            "(Certs are sorted in the order of rotation, beginning with the"
                                + " original signing cert)");
                    Signature[] signingCertificateHistory =
                            signingInfo.getSigningCertificateHistory();
                    int i = 0;
                    while (i < signingCertificateHistory.length - 1) {
                        Signature signature2 = signingCertificateHistory[i];
                        String encodeToString2 =
                                HexEncoding.encodeToString(
                                        PackageUtils.computeSha256DigestBytes(
                                                signature2.toByteArray()),
                                        false);
                        StringBuilder sb = new StringBuilder("  ++ Signer cert #");
                        i++;
                        sb.append(i);
                        sb.append(" ++");
                        printWriter.println(sb.toString());
                        printWriter.println("  Cert SHA256-digest: " + encodeToString2);
                        try {
                            printWriter.println(
                                    "  Signing key algorithm: "
                                            + signature2.getPublicKey().getAlgorithm());
                        } catch (CertificateException e2) {
                            Slog.e(
                                    "ShellCommand",
                                    "Failed to obtain public key of signer for cert with hash: "
                                            + encodeToString2,
                                    e2);
                        }
                    }
                }

                public final int onCommand(String str) {
                    char c;
                    String str2;
                    Iterator<ModuleInfo> it;
                    PackageInfo packageInfo;
                    StringBuilder sb;
                    Object obj;
                    char c2;
                    PackageManager packageManager;
                    String str3;
                    char c3;
                    char c4;
                    char c5;
                    String str4 = "--no-headers";
                    if (str == null) {
                        return handleDefaultCommands(str);
                    }
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    if (!str.equals("get")) {
                        return handleDefaultCommands(str);
                    }
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        printHelpMenu();
                        return -1;
                    }
                    switch (nextArg.hashCode()) {
                        case -1443097326:
                            if (nextArg.equals("image_info")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1195140447:
                            if (nextArg.equals("module_info")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 636812193:
                            if (nextArg.equals("mba_info")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1366866347:
                            if (nextArg.equals("apex_info")) {
                                c = 3;
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
                            PrintWriter outPrintWriter2 = getOutPrintWriter();
                            boolean z = false;
                            while (true) {
                                String nextOption = getNextOption();
                                if (nextOption == null) {
                                    String str5 = BinaryTransparencyService.this.mVbmetaDigest;
                                    outPrintWriter2.println("Image Info:");
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            outPrintWriter2, Build.FINGERPRINT, str5, "");
                                    if (z) {
                                        if (BinaryTransparencyService.this.mContext
                                                        .getPackageManager()
                                                == null) {
                                            outPrintWriter2.println(
                                                    "ERROR: Failed to obtain an instance of package"
                                                        + " manager.");
                                            break;
                                        } else {
                                            outPrintWriter2.println("Other partitions:");
                                            for (Build.Partition partition :
                                                    Build.getFingerprintedPartitions()) {
                                                outPrintWriter2.println(
                                                        "Name: " + partition.getName());
                                                outPrintWriter2.println(
                                                        "Fingerprint: "
                                                                + partition.getFingerprint());
                                                outPrintWriter2.println(
                                                        "Build time (ms): "
                                                                + partition.getBuildTimeMillis());
                                            }
                                            break;
                                        }
                                    }
                                } else if (!nextOption.equals("-a")) {
                                    outPrintWriter2.println(
                                            "ERROR: Unknown option: ".concat(nextOption));
                                    break;
                                } else {
                                    z = true;
                                }
                            }
                            break;
                        case 1:
                            Object obj2 = "--no-headers";
                            PrintWriter outPrintWriter3 = getOutPrintWriter();
                            boolean z2 = false;
                            boolean z3 = true;
                            boolean z4 = false;
                            while (true) {
                                String nextOption2 = getNextOption();
                                if (nextOption2 == null) {
                                    PackageManager packageManager2 =
                                            BinaryTransparencyService.this.mContext
                                                    .getPackageManager();
                                    if (packageManager2 == null) {
                                        outPrintWriter3.println(
                                                "ERROR: Failed to obtain an instance of package"
                                                    + " manager.");
                                        break;
                                    } else {
                                        String str6 = "Module";
                                        if (!z2 && z3) {
                                            printHeadersHelper(outPrintWriter3, "Module", z4);
                                        }
                                        Iterator<ModuleInfo> it2 =
                                                packageManager2
                                                        .getInstalledModules(131072)
                                                        .iterator();
                                        while (it2.hasNext()) {
                                            ModuleInfo next = it2.next();
                                            String packageName = next.getPackageName();
                                            if (z2 && z3) {
                                                printHeadersHelper(outPrintWriter3, str6, z4);
                                            }
                                            try {
                                                packageInfo =
                                                        packageManager2.getPackageInfo(
                                                                packageName, 1207959552);
                                                outPrintWriter3.print(
                                                        packageInfo.packageName + ",");
                                                sb = new StringBuilder();
                                                str2 = str6;
                                                it = it2;
                                            } catch (PackageManager.NameNotFoundException unused) {
                                                str2 = str6;
                                                it = it2;
                                            }
                                            try {
                                                sb.append(packageInfo.getLongVersionCode());
                                                sb.append(",");
                                                outPrintWriter3.print(sb.toString());
                                                printPackageMeasurements(
                                                        packageInfo, z4, outPrintWriter3);
                                                if (z2) {
                                                    printModuleDetails(next, outPrintWriter3);
                                                    printPackageInstallationInfo(
                                                            packageInfo, z4, outPrintWriter3);
                                                    printPackageSignerDetails(
                                                            packageInfo.signingInfo,
                                                            outPrintWriter3);
                                                    outPrintWriter3.println("");
                                                }
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                outPrintWriter3.println(
                                                        packageName
                                                                + ",ERROR:Unable to find"
                                                                + " PackageInfo for this module.");
                                                if (z2) {
                                                    printModuleDetails(next, outPrintWriter3);
                                                    outPrintWriter3.println("");
                                                }
                                                str6 = str2;
                                                it2 = it;
                                            }
                                            str6 = str2;
                                            it2 = it;
                                        }
                                        break;
                                    }
                                } else {
                                    switch (nextOption2.hashCode()) {
                                        case 1506:
                                            obj = obj2;
                                            if (nextOption2.equals("-o")) {
                                                c2 = 0;
                                                break;
                                            }
                                            c2 = 65535;
                                            break;
                                        case 1513:
                                            obj = obj2;
                                            if (nextOption2.equals("-v")) {
                                                c2 = 1;
                                                break;
                                            }
                                            c2 = 65535;
                                            break;
                                        case 43009159:
                                            obj = obj2;
                                            if (nextOption2.equals("--old")) {
                                                c2 = 2;
                                                break;
                                            }
                                            c2 = 65535;
                                            break;
                                        case 967085338:
                                            obj = obj2;
                                            if (nextOption2.equals(obj)) {
                                                c2 = 3;
                                                break;
                                            }
                                            c2 = 65535;
                                            break;
                                        case 1737088994:
                                            if (nextOption2.equals("--verbose")) {
                                                obj = obj2;
                                                c2 = 4;
                                                break;
                                            }
                                        default:
                                            obj = obj2;
                                            c2 = 65535;
                                            break;
                                    }
                                    switch (c2) {
                                        case 0:
                                        case 2:
                                            z4 = true;
                                            break;
                                        case 1:
                                        case 4:
                                            z2 = true;
                                            break;
                                        case 3:
                                            z3 = false;
                                            break;
                                        default:
                                            outPrintWriter3.println(
                                                    "ERROR: Unknown option: ".concat(nextOption2));
                                            break;
                                    }
                                    obj2 = obj;
                                }
                            }
                        case 2:
                            PrintWriter outPrintWriter4 = getOutPrintWriter();
                            boolean z5 = false;
                            boolean z6 = false;
                            boolean z7 = false;
                            boolean z8 = false;
                            boolean z9 = true;
                            while (true) {
                                String nextOption3 = getNextOption();
                                if (nextOption3 == null) {
                                    String str7 = "MBA";
                                    String str8 = "Preload";
                                    if (!z5 && z9) {
                                        if (z6) {
                                            printHeadersHelper(outPrintWriter4, "Preload", z7);
                                        } else {
                                            printHeadersHelper(outPrintWriter4, "MBA", z7);
                                        }
                                    }
                                    PackageManager packageManager3 =
                                            BinaryTransparencyService.this.mContext
                                                    .getPackageManager();
                                    for (PackageInfo packageInfo2 :
                                            packageManager3.getInstalledPackages(
                                                    PackageManager.PackageInfoFlags.of(
                                                            136314880L))) {
                                        if (packageInfo2.signingInfo == null) {
                                            try {
                                                packageManager3.getPackageInfo(
                                                        packageInfo2.packageName,
                                                        PackageManager.PackageInfoFlags.of(
                                                                134348800L));
                                            } catch (PackageManager.NameNotFoundException unused3) {
                                                packageManager = packageManager3;
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m$1(
                                                                new StringBuilder(
                                                                        "Failed to obtain an"
                                                                            + " updated PackageInfo"
                                                                            + " of "),
                                                                packageInfo2.packageName,
                                                                "ShellCommand");
                                            }
                                        }
                                        packageManager = packageManager3;
                                        if (z5 && z9) {
                                            printHeadersHelper(outPrintWriter4, str8, z7);
                                        }
                                        outPrintWriter4.print(packageInfo2.packageName + ",");
                                        StringBuilder sb2 = new StringBuilder();
                                        String str9 = str7;
                                        String str10 = str8;
                                        sb2.append(packageInfo2.getLongVersionCode());
                                        sb2.append(",");
                                        outPrintWriter4.print(sb2.toString());
                                        printPackageMeasurements(packageInfo2, z7, outPrintWriter4);
                                        if (z5) {
                                            printAppDetails(packageInfo2, z8, outPrintWriter4);
                                            printPackageInstallationInfo(
                                                    packageInfo2, z7, outPrintWriter4);
                                            printPackageSignerDetails(
                                                    packageInfo2.signingInfo, outPrintWriter4);
                                            outPrintWriter4.println("");
                                        }
                                        packageManager3 = packageManager;
                                        str7 = str9;
                                        str8 = str10;
                                    }
                                    String str11 = str7;
                                    if (!z6) {
                                        for (PackageInfo packageInfo3 :
                                                BinaryTransparencyService
                                                        .m47$$Nest$mgetNewlyInstalledMbas(
                                                                BinaryTransparencyService.this)) {
                                            if (z5 && z9) {
                                                printHeadersHelper(outPrintWriter4, str11, z7);
                                            }
                                            outPrintWriter4.print(packageInfo3.packageName + ",");
                                            outPrintWriter4.print(
                                                    packageInfo3.getLongVersionCode() + ",");
                                            printPackageMeasurements(
                                                    packageInfo3, z7, outPrintWriter4);
                                            if (z5) {
                                                printAppDetails(packageInfo3, z8, outPrintWriter4);
                                                printPackageInstallationInfo(
                                                        packageInfo3, z7, outPrintWriter4);
                                                printPackageSignerDetails(
                                                        packageInfo3.signingInfo, outPrintWriter4);
                                                outPrintWriter4.println("");
                                            }
                                        }
                                        break;
                                    }
                                } else {
                                    switch (nextOption3.hashCode()) {
                                        case 1503:
                                            str3 = str4;
                                            if (nextOption3.equals("-l")) {
                                                c3 = 0;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1506:
                                            str3 = str4;
                                            if (nextOption3.equals("-o")) {
                                                c3 = 1;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 1513:
                                            str3 = str4;
                                            if (nextOption3.equals("-v")) {
                                                c3 = 2;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 43009159:
                                            str3 = str4;
                                            if (nextOption3.equals("--old")) {
                                                c3 = 3;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 705409647:
                                            str3 = str4;
                                            if (nextOption3.equals("--preloads-only")) {
                                                c3 = 4;
                                                break;
                                            }
                                            c3 = 65535;
                                            break;
                                        case 967085338:
                                            if (nextOption3.equals(str4)) {
                                                c4 = 5;
                                                char c6 = c4;
                                                str3 = str4;
                                                c3 = c6;
                                                break;
                                            }
                                            str3 = str4;
                                            c3 = 65535;
                                            break;
                                        case 1737088994:
                                            if (nextOption3.equals("--verbose")) {
                                                c4 = 6;
                                                char c62 = c4;
                                                str3 = str4;
                                                c3 = c62;
                                                break;
                                            }
                                            str3 = str4;
                                            c3 = 65535;
                                            break;
                                        default:
                                            str3 = str4;
                                            c3 = 65535;
                                            break;
                                    }
                                    switch (c3) {
                                        case 0:
                                            z8 = true;
                                            break;
                                        case 1:
                                        case 3:
                                            z7 = true;
                                            break;
                                        case 2:
                                        case 6:
                                            z5 = true;
                                            break;
                                        case 4:
                                            z6 = true;
                                            break;
                                        case 5:
                                            z9 = false;
                                            break;
                                        default:
                                            outPrintWriter4.println(
                                                    "ERROR: Unknown option: ".concat(nextOption3));
                                            break;
                                    }
                                    str4 = str3;
                                }
                            }
                            break;
                        case 3:
                            PrintWriter outPrintWriter5 = getOutPrintWriter();
                            boolean z10 = true;
                            boolean z11 = false;
                            boolean z12 = false;
                            while (true) {
                                String nextOption4 = getNextOption();
                                if (nextOption4 != null) {
                                    switch (nextOption4.hashCode()) {
                                        case 1506:
                                            if (nextOption4.equals("-o")) {
                                                c5 = 0;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 1513:
                                            if (nextOption4.equals("-v")) {
                                                c5 = 1;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 43009159:
                                            if (nextOption4.equals("--old")) {
                                                c5 = 2;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 967085338:
                                            if (nextOption4.equals("--no-headers")) {
                                                c5 = 3;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 1737088994:
                                            if (nextOption4.equals("--verbose")) {
                                                c5 = 4;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        default:
                                            c5 = 65535;
                                            break;
                                    }
                                    switch (c5) {
                                        case 0:
                                        case 2:
                                            z12 = true;
                                            break;
                                        case 1:
                                        case 4:
                                            z11 = true;
                                            break;
                                        case 3:
                                            z10 = false;
                                            break;
                                        default:
                                            outPrintWriter5.println(
                                                    "ERROR: Unknown option: ".concat(nextOption4));
                                            break;
                                    }
                                } else {
                                    PackageManager packageManager4 =
                                            BinaryTransparencyService.this.mContext
                                                    .getPackageManager();
                                    if (packageManager4 == null) {
                                        outPrintWriter5.println(
                                                "ERROR: Failed to obtain an instance of package"
                                                    + " manager.");
                                        break;
                                    } else {
                                        if (!z11 && z10) {
                                            printHeadersHelper(outPrintWriter5, "APEX", z12);
                                        }
                                        for (PackageInfo packageInfo4 :
                                                BinaryTransparencyService
                                                        .m46$$Nest$mgetCurrentInstalledApexs(
                                                                BinaryTransparencyService.this)) {
                                            if (z11 && z10) {
                                                printHeadersHelper(outPrintWriter5, "APEX", z12);
                                            }
                                            StringBuilder m =
                                                    Preconditions$$ExternalSyntheticOutline0.m(
                                                            packageInfo4.packageName, ",");
                                            m.append(packageInfo4.getLongVersionCode());
                                            m.append(",");
                                            outPrintWriter5.print(m.toString());
                                            printPackageMeasurements(
                                                    packageInfo4, z12, outPrintWriter5);
                                            if (z11) {
                                                try {
                                                    ModuleInfo moduleInfo =
                                                            packageManager4.getModuleInfo(
                                                                    packageInfo4.packageName, 0);
                                                    outPrintWriter5.println("Is a module: true");
                                                    printModuleDetails(moduleInfo, outPrintWriter5);
                                                } catch (
                                                        PackageManager.NameNotFoundException
                                                                unused4) {
                                                    outPrintWriter5.println("Is a module: false");
                                                    printPackageInstallationInfo(
                                                            packageInfo4, z12, outPrintWriter5);
                                                    printPackageSignerDetails(
                                                            packageInfo4.signingInfo,
                                                            outPrintWriter5);
                                                    outPrintWriter5.println("");
                                                }
                                                printPackageInstallationInfo(
                                                        packageInfo4, z12, outPrintWriter5);
                                                printPackageSignerDetails(
                                                        packageInfo4.signingInfo, outPrintWriter5);
                                                outPrintWriter5.println("");
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        default:
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    outPrintWriter, "ERROR: Unknown info type '", nextArg, "'");
                            break;
                    }
                    return handleDefaultCommands(str);
                }

                public final void onHelp() {
                    printHelpMenu();
                }

                public final void printAppDetails(
                        PackageInfo packageInfo, boolean z, PrintWriter printWriter) {
                    StringBuilder m =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    printWriter,
                                    packageInfo.applicationInfo.name,
                                    "Label: ",
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                            printWriter, "--- App Details ---", "Name: "));
                    m.append(
                            (Object)
                                    BinaryTransparencyService.this
                                            .mContext
                                            .getPackageManager()
                                            .getApplicationLabel(packageInfo.applicationInfo));
                    printWriter.println(m.toString());
                    printWriter.println(
                            "Description: "
                                    + ((Object)
                                            packageInfo.applicationInfo.loadDescription(
                                                    BinaryTransparencyService.this.mContext
                                                            .getPackageManager())));
                    printWriter.println("Has code: " + packageInfo.applicationInfo.hasCode());
                    int i = 0;
                    StringBuilder m2 =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    new StringBuilder(
                                                                            "Is enabled: "),
                                                                    packageInfo
                                                                            .applicationInfo
                                                                            .enabled,
                                                                    printWriter,
                                                                    "Is suspended: "),
                                                    (packageInfo.applicationInfo.flags & 1073741824)
                                                            != 0,
                                                    printWriter,
                                                    "Compile SDK version: "),
                                            packageInfo.compileSdkVersion,
                                            printWriter,
                                            "Target SDK version: "),
                                    packageInfo.applicationInfo.targetSdkVersion,
                                    printWriter,
                                    "Is privileged: ");
                    m2.append(packageInfo.applicationInfo.isPrivilegedApp());
                    printWriter.println(m2.toString());
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            printWriter,
                                            packageInfo.applicationInfo.taskAffinity,
                                            "UID: ",
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    printWriter,
                                                    packageInfo.applicationInfo.processName,
                                                    "Task affinity: ",
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    printWriter,
                                                                    packageInfo
                                                                            .applicationInfo
                                                                            .appComponentFactory,
                                                                    "Process name: ",
                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    printWriter,
                                                                                    packageInfo
                                                                                            .applicationInfo
                                                                                            .seInfo,
                                                                                    "Component"
                                                                                        + " factory:"
                                                                                        + " ",
                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                            .m(
                                                                                                                    new StringBuilder(
                                                                                                                            "Is a stub:"
                                                                                                                                + " "),
                                                                                                                    packageInfo
                                                                                                                            .isStub,
                                                                                                                    printWriter,
                                                                                                                    "Is a core"
                                                                                                                        + " app:"
                                                                                                                        + " "),
                                                                                                    packageInfo
                                                                                                            .coreApp,
                                                                                                    printWriter,
                                                                                                    "SEInfo:"
                                                                                                        + " "))))),
                                    packageInfo.applicationInfo.uid,
                                    printWriter,
                                    "Shared UID: "),
                            packageInfo.sharedUserId,
                            printWriter);
                    if (z) {
                        printWriter.println("== App's Shared Libraries ==");
                        List sharedLibraryInfos =
                                packageInfo.applicationInfo.getSharedLibraryInfos();
                        if (sharedLibraryInfos == null || sharedLibraryInfos.isEmpty()) {
                            printWriter.println("<none>");
                        }
                        while (i < sharedLibraryInfos.size()) {
                            SharedLibraryInfo sharedLibraryInfo =
                                    (SharedLibraryInfo) sharedLibraryInfos.get(i);
                            StringBuilder sb = new StringBuilder("  ++ Library #");
                            i++;
                            sb.append(i);
                            sb.append(" ++");
                            printWriter.println(sb.toString());
                            printWriter.println("  Lib name: " + sharedLibraryInfo.getName());
                            long longVersion = sharedLibraryInfo.getLongVersion();
                            printWriter.print("  Lib version: ");
                            if (longVersion == -1) {
                                printWriter.print("undefined");
                            } else {
                                printWriter.print(longVersion);
                            }
                            StringBuilder m3 =
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            printWriter,
                                            "\n",
                                            "  Lib package name (if available): ");
                            m3.append(sharedLibraryInfo.getPackageName());
                            printWriter.println(m3.toString());
                            printWriter.println("  Lib path: " + sharedLibraryInfo.getPath());
                            printWriter.print("  Lib type: ");
                            int type = sharedLibraryInfo.getType();
                            if (type == 0) {
                                printWriter.print("built-in");
                            } else if (type == 1) {
                                printWriter.print("dynamic");
                            } else if (type == 2) {
                                printWriter.print("static");
                            } else if (type != 3) {
                                printWriter.print("undefined");
                            } else {
                                printWriter.print("SDK");
                            }
                            StringBuilder m4 =
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            printWriter, "\n", "  Is a native lib: ");
                            m4.append(sharedLibraryInfo.isNative());
                            printWriter.println(m4.toString());
                        }
                    }
                }

                public final void printHelpMenu() {
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    outPrintWriter.println("Transparency manager (transparency) commands:");
                    outPrintWriter.println("  help");
                    outPrintWriter.println("    Print this help text.");
                    outPrintWriter.println("");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "  get image_info [-a]",
                            "    Print information about loaded image (firmware). Options:",
                            "        -a: lists all other identifiable partitions.",
                            "");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "  get apex_info [-o] [-v] [--no-headers]",
                            "    Print information about installed APEXs on device.",
                            "      -o: also uses the old digest scheme (SHA256) to compute APEX"
                                + " hashes. WARNING: This can be a very slow and CPU-intensive"
                                + " computation.",
                            "      -v: lists more verbose information about each APEX.");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "      --no-headers: does not print the header if specified.",
                            "",
                            "  get module_info [-o] [-v] [--no-headers]",
                            "    Print information about installed modules on device.");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "      -o: also uses the old digest scheme (SHA256) to compute module"
                                + " hashes. WARNING: This can be a very slow and CPU-intensive"
                                + " computation.",
                            "      -v: lists more verbose information about each module.",
                            "      --no-headers: does not print the header if specified.",
                            "");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "  get mba_info [-o] [-v] [-l] [--no-headers] [--preloads-only]",
                            "    Print information about installed mobile bundle apps (MBAs on"
                                + " device).",
                            "      -o: also uses the old digest scheme (SHA256) to compute MBA"
                                + " hashes. WARNING: This can be a very slow and CPU-intensive"
                                + " computation.",
                            "      -v: lists more verbose information about each app.");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "      -l: lists shared library info. (This option only works when -v"
                                + " option is also specified)",
                            "      --no-headers: does not print the header if specified.",
                            "      --preloads-only: lists only preloaded apps. This options can"
                                + " also be combined with others.",
                            "");
                }

                public final void printPackageInstallationInfo(
                        PackageInfo packageInfo, boolean z, PrintWriter printWriter) {
                    String str;
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                    printWriter,
                                    "--- Package Installation Info ---",
                                    "Current install location: "),
                            packageInfo.applicationInfo.sourceDir,
                            printWriter);
                    if (packageInfo.applicationInfo.sourceDir.startsWith("/data/apex/")) {
                        BinaryTransparencyService binaryTransparencyService =
                                BinaryTransparencyService.this;
                        String str2 = packageInfo.packageName;
                        Histogram histogram = BinaryTransparencyService.digestAllPackagesLatency;
                        binaryTransparencyService.getClass();
                        try {
                            String apexModuleNameForPackageName =
                                    ApexManager.getInstance().getApexModuleNameForPackageName(str2);
                            for (ApexInfo apexInfo :
                                    IApexService.Stub.asInterface(
                                                    Binder.allowBlocking(
                                                            ServiceManager.waitForService(
                                                                    "apexservice")))
                                            .getAllPackages()) {
                                if (apexModuleNameForPackageName.equals(apexInfo.moduleName)) {
                                    str = apexInfo.preinstalledModulePath;
                                    break;
                                }
                            }
                        } catch (RemoteException e) {
                            Slog.e(
                                    "TransparencyService",
                                    "Unable to get package list from apexservice",
                                    e);
                        }
                        str = "could-not-be-determined";
                        printWriter.println("|--> Pre-installed package install location: " + str);
                        if (!str.equals("could-not-be-determined")) {
                            if (z) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                                        printWriter,
                                        "|--> Pre-installed package SHA-256 digest: ",
                                        PackageUtils.computeSha256DigestForLargeFile(
                                                str, PackageUtils.createLargeFileBuffer()));
                            }
                            BinaryTransparencyServiceImpl.this.getClass();
                            Map computeApkContentDigest =
                                    BinaryTransparencyServiceImpl.computeApkContentDigest(str);
                            if (computeApkContentDigest == null) {
                                printWriter.println(
                                        "|--> ERROR: Failed to compute package content digest for "
                                                .concat(str));
                            } else {
                                for (Map.Entry entry : computeApkContentDigest.entrySet()) {
                                    Integer num = (Integer) entry.getKey();
                                    printWriter.println(
                                            "|--> Pre-installed package content digest: "
                                                    + HexEncoding.encodeToString(
                                                            (byte[]) entry.getValue(), false));
                                    printWriter.println(
                                            "|--> Pre-installed package content digest algorithm: "
                                                    + BinaryTransparencyService
                                                            .m49$$Nest$mtranslateContentDigestAlgorithmIdToString(
                                                                    BinaryTransparencyService.this,
                                                                    num.intValue()));
                                }
                            }
                        }
                    }
                    StringBuilder m =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("First install time (ms): "),
                                    packageInfo.firstInstallTime,
                                    printWriter,
                                    "Last update time (ms):   ");
                    m.append(packageInfo.lastUpdateTime);
                    printWriter.println(m.toString());
                    printWriter.println(
                            "Is preloaded: "
                                    + (packageInfo.firstInstallTime == packageInfo.lastUpdateTime));
                    BinaryTransparencyService binaryTransparencyService2 =
                            BinaryTransparencyService.this;
                    String str3 = packageInfo.packageName;
                    PackageManager packageManager =
                            binaryTransparencyService2.mContext.getPackageManager();
                    InstallSourceInfo installSourceInfo = null;
                    if (packageManager == null) {
                        Slog.e(
                                "TransparencyService",
                                "Error obtaining an instance of PackageManager.");
                    } else {
                        try {
                            installSourceInfo = packageManager.getInstallSourceInfo(str3);
                        } catch (PackageManager.NameNotFoundException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (installSourceInfo == null) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("ERROR: Unable to obtain installSourceInfo of "),
                                packageInfo.packageName,
                                printWriter);
                    } else {
                        printWriter.println(
                                "Installation initiated by: "
                                        + installSourceInfo.getInitiatingPackageName());
                        printWriter.println(
                                "Installation done by: "
                                        + installSourceInfo.getInstallingPackageName());
                        printWriter.println(
                                "Installation originating from: "
                                        + installSourceInfo.getOriginatingPackageName());
                    }
                    if (packageInfo.isApex) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Is an active APEX: "),
                                packageInfo.isActiveApex,
                                printWriter);
                    }
                }

                public final void printPackageMeasurements(
                        PackageInfo packageInfo, boolean z, PrintWriter printWriter) {
                    BinaryTransparencyServiceImpl binaryTransparencyServiceImpl =
                            BinaryTransparencyServiceImpl.this;
                    String str = packageInfo.applicationInfo.sourceDir;
                    binaryTransparencyServiceImpl.getClass();
                    Map computeApkContentDigest =
                            BinaryTransparencyServiceImpl.computeApkContentDigest(str);
                    if (computeApkContentDigest == null) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "ERROR: Failed to compute package content digest for "),
                                packageInfo.applicationInfo.sourceDir,
                                printWriter);
                        return;
                    }
                    if (z) {
                        printWriter.print(
                                PackageUtils.computeSha256DigestForLargeFile(
                                                packageInfo.applicationInfo.sourceDir,
                                                PackageUtils.createLargeFileBuffer())
                                        + ",");
                    }
                    for (Map.Entry entry : computeApkContentDigest.entrySet()) {
                        Integer num = (Integer) entry.getKey();
                        byte[] bArr = (byte[]) entry.getValue();
                        printWriter.print(
                                BinaryTransparencyService
                                        .m49$$Nest$mtranslateContentDigestAlgorithmIdToString(
                                                BinaryTransparencyService.this, num.intValue()));
                        printWriter.print(":");
                        printWriter.print(HexEncoding.encodeToString(bArr, false));
                        printWriter.print("\n");
                    }
                }
            }.exec(
                    this,
                    fileDescriptor,
                    fileDescriptor2,
                    fileDescriptor3,
                    strArr,
                    shellCallback,
                    resultReceiver);
        }

        public final void recordMeasurementsForAllPackages() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - BinaryTransparencyService.this.mMeasurementsLastRecordedMs
                    < BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                Slog.d(
                        "TransparencyService",
                        "Skip measurement since the last measurement was only taken at "
                                + BinaryTransparencyService.this.mMeasurementsLastRecordedMs
                                + " within the cooldown period");
                return;
            }
            Slog.d(
                    "TransparencyService",
                    "Measurement was last taken at "
                            + BinaryTransparencyService.this.mMeasurementsLastRecordedMs
                            + " and is now updated to: "
                            + currentTimeMillis);
            BinaryTransparencyService.this.mMeasurementsLastRecordedMs = currentTimeMillis;
            Bundle bundle = new Bundle();
            Iterator it = ((ArrayList) collectAllApexInfo(false)).iterator();
            while (it.hasNext()) {
                IBinaryTransparencyService.ApexInfo apexInfo =
                        (IBinaryTransparencyService.ApexInfo) it.next();
                bundle.putBoolean(apexInfo.packageName, true);
                String str = apexInfo.packageName;
                long j = apexInfo.longVersion;
                byte[] bArr = apexInfo.digest;
                FrameworkStatsLog.write(
                        FrameworkStatsLog.APEX_INFO_GATHERED,
                        str,
                        j,
                        bArr != null ? HexEncoding.encodeToString(bArr, false) : null,
                        apexInfo.digestAlgorithm,
                        apexInfo.signerDigests);
            }
            Iterator it2 = ((ArrayList) collectAllUpdatedPreloadInfo(bundle)).iterator();
            while (it2.hasNext()) {
                IBinaryTransparencyService.AppInfo appInfo =
                        (IBinaryTransparencyService.AppInfo) it2.next();
                bundle.putBoolean(appInfo.packageName, true);
                writeAppInfoToLog(appInfo);
            }
            if (CompatChanges.isChangeEnabled(245692487L)) {
                Iterator it3 = ((ArrayList) collectAllSilentInstalledMbaInfo(bundle)).iterator();
                while (it3.hasNext()) {
                    IBinaryTransparencyService.AppInfo appInfo2 =
                            (IBinaryTransparencyService.AppInfo) it3.next();
                    bundle.putBoolean(appInfo2.packageName, true);
                    writeAppInfoToLog(appInfo2);
                }
            }
            BinaryTransparencyService.digestAllPackagesLatency.logSample(
                    System.currentTimeMillis() - currentTimeMillis);
        }
    }

    public class BiometricLogger {
        public static final BiometricLogger sInstance = new BiometricLogger();

        private BiometricLogger() {}
    }

    public final class Digest extends Record {
        public final int algorithm;
        public final byte[] value;

        public Digest(int i, byte[] bArr) {
            this.algorithm = i;
            this.value = bArr;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(Boolean.TYPE, Digest.class, Object.class),
                                    Digest.class,
                                    "algorithm;value",
                                    "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->algorithm:I",
                                    "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->value:[B")
                            .dynamicInvoker()
                            .invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(Integer.TYPE, Digest.class),
                                    Digest.class,
                                    "algorithm;value",
                                    "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->algorithm:I",
                                    "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->value:[B")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(String.class, Digest.class),
                                    Digest.class,
                                    "algorithm;value",
                                    "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->algorithm:I",
                                    "FIELD:Lcom/android/server/BinaryTransparencyService$Digest;->value:[B")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }
    }

    public final class PackageUpdatedReceiver extends BroadcastReceiver {
        public PackageUpdatedReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                Uri data = intent.getData();
                if (data == null) {
                    Slog.e("TransparencyService", "Shouldn't happen: intent data is null!");
                    return;
                }
                boolean z = false;
                if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    Slog.d("TransparencyService", "Not an update. Skipping...");
                    return;
                }
                String schemeSpecificPart = data.getSchemeSpecificPart();
                try {
                    BinaryTransparencyService.this
                            .mContext
                            .getPackageManager()
                            .getPackageInfo(
                                    schemeSpecificPart,
                                    PackageManager.PackageInfoFlags.of(2097152L));
                } catch (PackageManager.NameNotFoundException unused) {
                    try {
                        z =
                                BinaryTransparencyService.this
                                        .mContext
                                        .getPackageManager()
                                        .getPackageInfo(
                                                schemeSpecificPart,
                                                PackageManager.PackageInfoFlags.of(1073741824L))
                                        .isApex;
                    } catch (PackageManager.NameNotFoundException unused2) {
                    }
                    if (!z) {
                        return;
                    }
                }
                Slog.d(
                        "TransparencyService",
                        schemeSpecificPart + " was updated. Scheduling measurement...");
                UpdateMeasurementsJobService.scheduleBinaryMeasurements(
                        BinaryTransparencyService.this.mContext);
            }
        }
    }

    public class UpdateMeasurementsJobService extends JobService {
        public static final /* synthetic */ int $r8$clinit = 0;
        public static long sTimeLastRanMs;

        public static void scheduleBinaryMeasurements(Context context) {
            Slog.i("TransparencyService", "Scheduling binary content-digest computation job");
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
            if (jobScheduler == null) {
                Slog.e("TransparencyService", "Failed to obtain an instance of JobScheduler.");
                return;
            }
            if (jobScheduler.getPendingJob(1740526926) != null) {
                Slog.d("TransparencyService", "A measurement job has already been scheduled.");
                return;
            }
            long j = 0;
            if (sTimeLastRanMs != 0) {
                j =
                        Math.max(
                                0L,
                                Math.min(
                                        BackupManagerConstants
                                                        .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS
                                                - (System.currentTimeMillis() - sTimeLastRanMs),
                                        BackupManagerConstants
                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS));
                Slog.d(
                        "TransparencyService",
                        "Scheduling the next measurement to be done at least "
                                + j
                                + "ms from now.");
            }
            if (jobScheduler.schedule(
                            new JobInfo.Builder(
                                            1740526926,
                                            new ComponentName(
                                                    context,
                                                    (Class<?>) UpdateMeasurementsJobService.class))
                                    .setRequiresDeviceIdle(true)
                                    .setRequiresCharging(true)
                                    .setMinimumLatency(j)
                                    .build())
                    != 1) {
                Slog.e("TransparencyService", "Failed to schedule job to measure binaries.");
            } else {
                Slog.d(
                        "TransparencyService",
                        TextUtils.formatSimple(
                                "Job %d to measure binaries was scheduled successfully.",
                                new Object[] {1740526926}));
            }
        }

        @Override // android.app.job.JobService
        public final boolean onStartJob(final JobParameters jobParameters) {
            Slog.d("TransparencyService", "Job to update binary measurements started.");
            if (jobParameters.getJobId() != 1740526926) {
                return false;
            }
            Executors.defaultThreadFactory()
                    .newThread(
                            new Runnable() { // from class:
                                             // com.android.server.BinaryTransparencyService$UpdateMeasurementsJobService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BinaryTransparencyService.UpdateMeasurementsJobService
                                            updateMeasurementsJobService =
                                                    BinaryTransparencyService
                                                            .UpdateMeasurementsJobService.this;
                                    JobParameters jobParameters2 = jobParameters;
                                    int i =
                                            BinaryTransparencyService.UpdateMeasurementsJobService
                                                    .$r8$clinit;
                                    updateMeasurementsJobService.getClass();
                                    try {
                                        IBinaryTransparencyService.Stub.asInterface(
                                                        ServiceManager.getService("transparency"))
                                                .recordMeasurementsForAllPackages();
                                        BinaryTransparencyService.UpdateMeasurementsJobService
                                                        .sTimeLastRanMs =
                                                System.currentTimeMillis();
                                        updateMeasurementsJobService.jobFinished(
                                                jobParameters2, false);
                                    } catch (RemoteException e) {
                                        Slog.e(
                                                "TransparencyService",
                                                "Taking binary measurements was interrupted.",
                                                e);
                                    }
                                }
                            })
                    .start();
            return true;
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    /* renamed from: -$$Nest$mgetCurrentInstalledApexs, reason: not valid java name */
    public static List m46$$Nest$mgetCurrentInstalledApexs(
            BinaryTransparencyService binaryTransparencyService) {
        binaryTransparencyService.getClass();
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = binaryTransparencyService.mContext.getPackageManager();
        if (packageManager == null) {
            Slog.e("TransparencyService", "Error obtaining an instance of PackageManager.");
            return arrayList;
        }
        List<PackageInfo> installedPackages =
                packageManager.getInstalledPackages(
                        PackageManager.PackageInfoFlags.of(1207959552L));
        if (installedPackages != null) {
            return (List)
                    installedPackages.stream()
                            .filter(new BinaryTransparencyService$$ExternalSyntheticLambda3())
                            .collect(Collectors.toList());
        }
        Slog.e("TransparencyService", "Error obtaining installed packages (including APEX)");
        return arrayList;
    }

    /* renamed from: -$$Nest$mgetNewlyInstalledMbas, reason: not valid java name */
    public static List m47$$Nest$mgetNewlyInstalledMbas(
            BinaryTransparencyService binaryTransparencyService) {
        binaryTransparencyService.getClass();
        ArrayList arrayList = new ArrayList();
        IBackgroundInstallControlService asInterface =
                IBackgroundInstallControlService.Stub.asInterface(
                        ServiceManager.getService("background_install_control"));
        if (asInterface == null) {
            Slog.e(
                    "TransparencyService",
                    "Failed to obtain an IBinder instance of IBackgroundInstallControlService");
            return arrayList;
        }
        try {
            return asInterface.getBackgroundInstalledPackages(134348800L, 0).getList();
        } catch (RemoteException e) {
            Slog.e("TransparencyService", "Failed to get a list of MBAs.", e);
            return arrayList;
        }
    }

    /* renamed from: -$$Nest$mlogBiometricProperties, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m48$$Nest$mlogBiometricProperties(
            com.android.server.BinaryTransparencyService r12,
            android.hardware.biometrics.SensorProperties r13,
            int r14,
            int r15) {
        /*
            r12.getClass()
            int r10 = r13.getSensorId()
            int r0 = r13.getSensorStrength()
            r1 = 1
            if (r0 == 0) goto L14
            r2 = 2
            if (r0 == r1) goto L18
            if (r0 == r2) goto L16
            r1 = 0
        L14:
            r11 = r1
            goto L19
        L16:
            r1 = 3
            goto L14
        L18:
            r11 = r2
        L19:
            java.util.List r13 = r13.getComponentInfo()
            java.util.Iterator r13 = r13.iterator()
        L21:
            boolean r0 = r13.hasNext()
            if (r0 == 0) goto L64
            java.lang.Object r0 = r13.next()
            android.hardware.biometrics.SensorProperties$ComponentInfo r0 = (android.hardware.biometrics.SensorProperties.ComponentInfo) r0
            java.lang.String r1 = r0.getComponentId()
            java.lang.String r5 = r1.trim()
            java.lang.String r1 = r0.getHardwareVersion()
            java.lang.String r6 = r1.trim()
            java.lang.String r1 = r0.getFirmwareVersion()
            java.lang.String r7 = r1.trim()
            java.lang.String r1 = r0.getSerialNumber()
            java.lang.String r8 = r1.trim()
            java.lang.String r0 = r0.getSoftwareVersion()
            java.lang.String r9 = r0.trim()
            com.android.server.BinaryTransparencyService$BiometricLogger r0 = r12.mBiometricLogger
            r0.getClass()
            r0 = 587(0x24b, float:8.23E-43)
            r1 = r10
            r2 = r14
            r3 = r15
            r4 = r11
            com.android.internal.util.FrameworkStatsLog.write(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L21
        L64:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.BinaryTransparencyService.m48$$Nest$mlogBiometricProperties(com.android.server.BinaryTransparencyService,"
                    + " android.hardware.biometrics.SensorProperties, int, int):void");
    }

    /* renamed from: -$$Nest$mtranslateContentDigestAlgorithmIdToString, reason: not valid java name */
    public static String m49$$Nest$mtranslateContentDigestAlgorithmIdToString(
            BinaryTransparencyService binaryTransparencyService, int i) {
        binaryTransparencyService.getClass();
        return i != 1
                ? i != 2
                        ? i != 3
                                ? i != 4
                                        ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                i, "UNKNOWN_ALGO_ID(", ")")
                                        : "SHA256"
                                : "VERITY_CHUNKED_SHA256"
                        : "CHUNKED_SHA512"
                : "CHUNKED_SHA256";
    }

    public BinaryTransparencyService(Context context) {
        this(context, BiometricLogger.sInstance);
    }

    public BinaryTransparencyService(Context context, BiometricLogger biometricLogger) {
        super(context);
        this.mContext = context;
        this.mServiceImpl = new BinaryTransparencyServiceImpl();
        this.mVbmetaDigest = VBMETA_DIGEST_UNINITIALIZED;
        this.mMeasurementsLastRecordedMs = 0L;
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mBiometricLogger = biometricLogger;
    }

    public void collectBiometricProperties() {
        if (DeviceConfig.getBoolean(
                "biometrics", KEY_ENABLE_BIOMETRIC_PROPERTY_VERIFICATION, true)) {
            PackageManager packageManager = this.mContext.getPackageManager();
            FaceManager faceManager = null;
            FingerprintManager fingerprintManager =
                    (packageManager == null
                                    || !packageManager.hasSystemFeature(
                                            "android.hardware.fingerprint"))
                            ? null
                            : (FingerprintManager)
                                    this.mContext.getSystemService(FingerprintManager.class);
            if (packageManager != null
                    && packageManager.hasSystemFeature("android.hardware.biometrics.face")) {
                faceManager = (FaceManager) this.mContext.getSystemService(FaceManager.class);
            }
            if (fingerprintManager != null) {
                fingerprintManager.addAuthenticatorsRegisteredCallback(
                        new IFingerprintAuthenticatorsRegisteredCallback
                                .Stub() { // from class:
                                          // com.android.server.BinaryTransparencyService.1
                            public final void onAllAuthenticatorsRegistered(List list) {
                                int i;
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    FingerprintSensorProperties from =
                                            FingerprintSensorProperties.from(
                                                    (FingerprintSensorPropertiesInternal)
                                                            it.next());
                                    BinaryTransparencyService binaryTransparencyService =
                                            BinaryTransparencyService.this;
                                    int sensorType = from.getSensorType();
                                    binaryTransparencyService.getClass();
                                    if (sensorType != 1) {
                                        i = 2;
                                        if (sensorType != 2) {
                                            i = 3;
                                            if (sensorType != 3) {
                                                i = 4;
                                                if (sensorType != 4) {
                                                    i = 5;
                                                    if (sensorType != 5) {
                                                        i = 0;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        i = 1;
                                    }
                                    BinaryTransparencyService.m48$$Nest$mlogBiometricProperties(
                                            binaryTransparencyService, from, 1, i);
                                }
                            }
                        });
            }
            if (faceManager != null) {
                faceManager.addAuthenticatorsRegisteredCallback(
                        new IFaceAuthenticatorsRegisteredCallback
                                .Stub() { // from class:
                                          // com.android.server.BinaryTransparencyService.2
                            public final void onAllAuthenticatorsRegistered(List list) {
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    FaceSensorProperties from =
                                            FaceSensorProperties.from(
                                                    (FaceSensorPropertiesInternal) it.next());
                                    BinaryTransparencyService binaryTransparencyService =
                                            BinaryTransparencyService.this;
                                    int sensorType = from.getSensorType();
                                    binaryTransparencyService.getClass();
                                    BinaryTransparencyService.m48$$Nest$mlogBiometricProperties(
                                            binaryTransparencyService,
                                            from,
                                            4,
                                            sensorType != 1 ? sensorType != 2 ? 0 : 7 : 6);
                                }
                            }
                        });
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            Slog.i("TransparencyService", "Boot completed. Getting boot integrity data.");
            String str = SystemProperties.get(SYSPROP_NAME_VBETA_DIGEST, VBMETA_DIGEST_UNAVAILABLE);
            this.mVbmetaDigest = str;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "VBMeta Digest: ", str, "TransparencyService");
            FrameworkStatsLog.write(FrameworkStatsLog.VBMETA_DIGEST_REPORTED, this.mVbmetaDigest);
            if (Flags.binaryTransparencySepolicyHash()) {
                IoThread.getExecutor()
                        .execute(
                                new Runnable() { // from class:
                                                 // com.android.server.BinaryTransparencyService$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        String str2;
                                        BinaryTransparencyService binaryTransparencyService =
                                                BinaryTransparencyService.this;
                                        binaryTransparencyService.getClass();
                                        byte[] computeSha256DigestForLargeFileAsBytes =
                                                PackageUtils.computeSha256DigestForLargeFileAsBytes(
                                                        "/sys/fs/selinux/policy",
                                                        PackageUtils.createLargeFileBuffer());
                                        if (computeSha256DigestForLargeFileAsBytes != null) {
                                            str2 =
                                                    HexEncoding.encodeToString(
                                                            computeSha256DigestForLargeFileAsBytes,
                                                            false);
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    "sepolicy hash: ", str2, "TransparencyService");
                                        } else {
                                            str2 = null;
                                        }
                                        FrameworkStatsLog.write(
                                                FrameworkStatsLog.BOOT_INTEGRITY_INFO_REPORTED,
                                                str2,
                                                binaryTransparencyService.mVbmetaDigest);
                                    }
                                });
            }
            Slog.i(
                    "TransparencyService",
                    "Boot completed. Collecting biometric system properties.");
            collectBiometricProperties();
            Slog.i("TransparencyService", "Scheduling measurements to be taken.");
            UpdateMeasurementsJobService.scheduleBinaryMeasurements(this.mContext);
            Slog.d("TransparencyService", "Registering APK & Non-Staged APEX updates...");
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiver(new PackageUpdatedReceiver(), intentFilter);
            Slog.d("TransparencyService", "Registering APEX updates...");
            IPackageManagerNative asInterface =
                    IPackageManagerNative.Stub.asInterface(
                            ServiceManager.getService("package_native"));
            if (asInterface == null) {
                Slog.e("TransparencyService", "IPackageManagerNative is null");
                return;
            }
            try {
                asInterface.registerStagedApexObserver(
                        new IStagedApexObserver.Stub() { // from class:
                            // com.android.server.BinaryTransparencyService.3
                            public final void onApexStaged(ApexStagedEvent apexStagedEvent) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(
                                                "A new APEX has been staged for update. There are"
                                                    + " currently "),
                                        apexStagedEvent.stagedApexModuleNames.length,
                                        " APEX(s) staged for update. Scheduling measurement...",
                                        "TransparencyService");
                                UpdateMeasurementsJobService.scheduleBinaryMeasurements(
                                        BinaryTransparencyService.this.mContext);
                            }
                        });
            } catch (RemoteException unused) {
                Slog.e("TransparencyService", "Failed to register a StagedApexObserver.");
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        try {
            publishBinderService("transparency", this.mServiceImpl);
            Slog.i("TransparencyService", "Started BinaryTransparencyService");
        } catch (Throwable th) {
            Slog.e("TransparencyService", "Failed to start BinaryTransparencyService.", th);
        }
    }
}
