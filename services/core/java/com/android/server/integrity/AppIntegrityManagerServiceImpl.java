package com.android.server.integrity;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.integrity.AppInstallMetadata;
import android.content.integrity.IAppIntegrityManager;
import android.content.integrity.IntegrityUtils;
import android.content.integrity.Rule;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.util.Slog;
import android.util.apk.SourceStampVerificationResult;
import android.util.apk.SourceStampVerifier;

import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.integrity.engine.RuleEvaluationEngine;
import com.android.server.integrity.model.IntegrityCheckResult;
import com.android.server.integrity.model.RuleMetadata;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.pkg.AndroidPackage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppIntegrityManagerServiceImpl extends IAppIntegrityManager.Stub {
    public static final Set PACKAGE_INSTALLER =
            new HashSet(
                    Arrays.asList(
                            "com.google.android.packageinstaller", "com.android.packageinstaller"));
    public final Context mContext;
    public final RuleEvaluationEngine mEvaluationEngine;
    public final Handler mHandler;
    public final IntegrityFileManager mIntegrityFileManager;
    public final PackageManagerInternal mPackageManagerInternal;
    public final Supplier mParserSupplier;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.integrity.AppIntegrityManagerServiceImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, final Intent intent) {
            if ("android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION"
                    .equals(intent.getAction())) {
                AppIntegrityManagerServiceImpl.this.mHandler.post(
                        new Runnable() { // from class:
                            // com.android.server.integrity.AppIntegrityManagerServiceImpl$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppIntegrityManagerServiceImpl.AnonymousClass1 anonymousClass1 =
                                        AppIntegrityManagerServiceImpl.AnonymousClass1.this;
                                Intent intent2 = intent;
                                AppIntegrityManagerServiceImpl appIntegrityManagerServiceImpl =
                                        AppIntegrityManagerServiceImpl.this;
                                appIntegrityManagerServiceImpl.getClass();
                                int intExtra =
                                        intent2.getIntExtra(
                                                "android.content.pm.extra.VERIFICATION_ID", -1);
                                try {
                                    String installerPackageName =
                                            appIntegrityManagerServiceImpl.getInstallerPackageName(
                                                    intent2);
                                    if (Settings.Global.getInt(
                                                    appIntegrityManagerServiceImpl.mContext
                                                            .getContentResolver(),
                                                    "verify_integrity_for_rule_provider",
                                                    0)
                                            != 1) {
                                        Iterator it =
                                                ((ArrayList)
                                                                appIntegrityManagerServiceImpl
                                                                        .getAllowedRuleProviderSystemApps())
                                                        .iterator();
                                        while (it.hasNext()) {
                                            if (((String) it.next())
                                                    .matches(installerPackageName)) {
                                                appIntegrityManagerServiceImpl
                                                        .mPackageManagerInternal
                                                        .setIntegrityVerificationResult(
                                                                intExtra, 1);
                                                return;
                                            }
                                        }
                                    }
                                    String stringExtra =
                                            intent2.getStringExtra(
                                                    "android.intent.extra.PACKAGE_NAME");
                                    Pair packageSigningAndMetadata =
                                            appIntegrityManagerServiceImpl
                                                    .getPackageSigningAndMetadata(
                                                            intent2.getData());
                                    if (packageSigningAndMetadata == null) {
                                        Slog.w(
                                                "AppIntegrityManagerServiceImpl",
                                                "Cannot parse package " + stringExtra);
                                        appIntegrityManagerServiceImpl.mPackageManagerInternal
                                                .setIntegrityVerificationResult(intExtra, 1);
                                        return;
                                    }
                                    SigningDetails signingDetails =
                                            (SigningDetails) packageSigningAndMetadata.first;
                                    ArrayList arrayList = new ArrayList();
                                    for (Signature signature :
                                            AppIntegrityManagerServiceImpl.getSignatures(
                                                    stringExtra, signingDetails)) {
                                        arrayList.add(
                                                AppIntegrityManagerServiceImpl.getFingerprint(
                                                        signature));
                                    }
                                    List certificateLineage =
                                            AppIntegrityManagerServiceImpl.getCertificateLineage(
                                                    stringExtra, signingDetails);
                                    List installerCertificateFingerprint =
                                            appIntegrityManagerServiceImpl
                                                    .getInstallerCertificateFingerprint(
                                                            installerPackageName);
                                    AppInstallMetadata.Builder builder =
                                            new AppInstallMetadata.Builder();
                                    builder.setPackageName(
                                            AppIntegrityManagerServiceImpl.getPackageNameNormalized(
                                                    stringExtra));
                                    builder.setAppCertificates(arrayList);
                                    builder.setAppCertificateLineage(certificateLineage);
                                    builder.setVersionCode(
                                            intent2.getLongExtra(
                                                    "android.intent.extra.LONG_VERSION_CODE", -1L));
                                    builder.setInstallerName(
                                            AppIntegrityManagerServiceImpl.getPackageNameNormalized(
                                                    installerPackageName));
                                    builder.setInstallerCertificates(
                                            installerCertificateFingerprint);
                                    builder.setIsPreInstalled(
                                            appIntegrityManagerServiceImpl.isSystemApp(
                                                    stringExtra));
                                    builder.setAllowedInstallersAndCert(
                                            AppIntegrityManagerServiceImpl.getAllowedInstallers(
                                                    (Bundle) packageSigningAndMetadata.second));
                                    AppIntegrityManagerServiceImpl.extractSourceStamp(
                                            intent2.getData(), builder);
                                    AppInstallMetadata build = builder.build();
                                    IntegrityCheckResult evaluate =
                                            appIntegrityManagerServiceImpl.mEvaluationEngine
                                                    .evaluate(build);
                                    boolean isEmpty = evaluate.mRuleList.isEmpty();
                                    IntegrityCheckResult.Effect effect = evaluate.mEffect;
                                    if (!isEmpty) {
                                        Slog.i(
                                                "AppIntegrityManagerServiceImpl",
                                                String.format(
                                                        "Integrity check of %s result: %s due to"
                                                            + " %s",
                                                        stringExtra, effect, evaluate.mRuleList));
                                    }
                                    String list = arrayList.toString();
                                    long versionCode = build.getVersionCode();
                                    int loggingResponse = evaluate.getLoggingResponse();
                                    final int i = 0;
                                    boolean anyMatch =
                                            evaluate.mRuleList.stream()
                                                    .anyMatch(
                                                            new Predicate() { // from class:
                                                                // com.android.server.integrity.model.IntegrityCheckResult$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    Rule rule = (Rule) obj;
                                                                    switch (i) {
                                                                        case 0:
                                                                            return rule.getFormula()
                                                                                    .isAppCertificateFormula();
                                                                        default:
                                                                            return rule.getFormula()
                                                                                    .isInstallerFormula();
                                                                    }
                                                                }
                                                            });
                                    final int i2 = 1;
                                    FrameworkStatsLog.write(
                                            FrameworkStatsLog.INTEGRITY_CHECK_RESULT_REPORTED,
                                            stringExtra,
                                            list,
                                            versionCode,
                                            installerPackageName,
                                            loggingResponse,
                                            anyMatch,
                                            evaluate.mRuleList.stream()
                                                    .anyMatch(
                                                            new Predicate() { // from class:
                                                                // com.android.server.integrity.model.IntegrityCheckResult$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    Rule rule = (Rule) obj;
                                                                    switch (i2) {
                                                                        case 0:
                                                                            return rule.getFormula()
                                                                                    .isAppCertificateFormula();
                                                                        default:
                                                                            return rule.getFormula()
                                                                                    .isInstallerFormula();
                                                                    }
                                                                }
                                                            }));
                                    appIntegrityManagerServiceImpl.mPackageManagerInternal
                                            .setIntegrityVerificationResult(
                                                    intExtra,
                                                    effect == IntegrityCheckResult.Effect.ALLOW
                                                            ? 1
                                                            : 0);
                                } catch (IllegalArgumentException e) {
                                    Slog.e(
                                            "AppIntegrityManagerServiceImpl",
                                            "Invalid input to integrity verification",
                                            e);
                                    appIntegrityManagerServiceImpl.mPackageManagerInternal
                                            .setIntegrityVerificationResult(intExtra, 0);
                                } catch (Exception e2) {
                                    Slog.e(
                                            "AppIntegrityManagerServiceImpl",
                                            "Error handling integrity verification",
                                            e2);
                                    appIntegrityManagerServiceImpl.mPackageManagerInternal
                                            .setIntegrityVerificationResult(intExtra, 1);
                                }
                            }
                        });
            }
        }
    }

    public AppIntegrityManagerServiceImpl(
            Context context,
            PackageManagerInternal packageManagerInternal,
            Supplier supplier,
            RuleEvaluationEngine ruleEvaluationEngine,
            IntegrityFileManager integrityFileManager,
            Handler handler) {
        this.mContext = context;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mParserSupplier = supplier;
        this.mEvaluationEngine = ruleEvaluationEngine;
        this.mIntegrityFileManager = integrityFileManager;
        this.mHandler = handler;
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION");
        try {
            m.addDataType("application/vnd.android.package-archive");
            context.registerReceiver(new AnonymousClass1(), m, null, handler);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Mime type malformed: should never happen.", e);
        }
    }

    public static void extractSourceStamp(Uri uri, AppInstallMetadata.Builder builder) {
        SourceStampVerificationResult verify;
        File installationPath = getInstallationPath(uri);
        if (installationPath.isDirectory()) {
            try {
                Stream<Path> list = Files.list(installationPath.toPath());
                try {
                    verify =
                            SourceStampVerifier.verify(
                                    (List)
                                            list.map(
                                                            new AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda2())
                                                    .filter(
                                                            new AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda3())
                                                    .collect(Collectors.toList()));
                    list.close();
                } finally {
                }
            } catch (IOException unused) {
                throw new IllegalArgumentException("Could not read APK directory");
            }
        } else {
            verify = SourceStampVerifier.verify(installationPath.getAbsolutePath());
        }
        builder.setIsStampPresent(verify.isPresent());
        builder.setIsStampVerified(verify.isVerified());
        builder.setIsStampTrusted(verify.isVerified());
        if (verify.isVerified()) {
            try {
                builder.setStampCertificateHash(
                        IntegrityUtils.getHexDigest(
                                MessageDigest.getInstance("SHA-256")
                                        .digest(
                                                ((X509Certificate) verify.getCertificate())
                                                        .getEncoded())));
            } catch (NoSuchAlgorithmException | CertificateEncodingException e) {
                throw new IllegalArgumentException(
                        "Error computing source stamp certificate digest", e);
            }
        }
    }

    public static Map getAllowedInstallers(Bundle bundle) {
        String string;
        HashMap hashMap = new HashMap();
        if (bundle != null && (string = bundle.getString("allowed-installers")) != null) {
            for (String str : string.split(",")) {
                String[] split = str.split("\\|");
                if (split.length == 2) {
                    hashMap.put(getPackageNameNormalized(split[0]), split[1]);
                } else if (split.length == 1) {
                    hashMap.put(getPackageNameNormalized(split[0]), "");
                }
            }
        }
        return hashMap;
    }

    public static List getCertificateLineage(String str, SigningDetails signingDetails) {
        ArrayList arrayList = new ArrayList();
        Signature[] signatures = getSignatures(str, signingDetails);
        Signature[] pastSigningCertificates = signingDetails.getPastSigningCertificates();
        if (signatures.length == 1 && !ArrayUtils.isEmpty(pastSigningCertificates)) {
            Signature[] signatureArr =
                    new Signature[signatures.length + pastSigningCertificates.length];
            int i = 0;
            while (i < signatures.length) {
                signatureArr[i] = signatures[i];
                i++;
            }
            for (Signature signature : pastSigningCertificates) {
                signatureArr[i] = signature;
                i++;
            }
            signatures = signatureArr;
        }
        for (Signature signature2 : signatures) {
            arrayList.add(getFingerprint(signature2));
        }
        return arrayList;
    }

    public static String getFingerprint(Signature signature) {
        X509Certificate x509Certificate;
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(signature.toByteArray());
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            if (certificateFactory != null) {
                try {
                    x509Certificate =
                            (X509Certificate)
                                    certificateFactory.generateCertificate(byteArrayInputStream);
                } catch (CertificateException e) {
                    throw new RuntimeException("Error getting X509Certificate", e);
                }
            } else {
                x509Certificate = null;
            }
            if (x509Certificate == null) {
                throw new RuntimeException("X509 Certificate not found");
            }
            try {
                return IntegrityUtils.getHexDigest(
                        MessageDigest.getInstance("SHA-256").digest(x509Certificate.getEncoded()));
            } catch (NoSuchAlgorithmException | CertificateEncodingException e2) {
                throw new IllegalArgumentException("Error error computing fingerprint", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Error getting CertificateFactory", e3);
        }
    }

    public static File getInstallationPath(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Null data uri");
        }
        if (!"file".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("Unsupported scheme for " + uri);
        }
        File file = new File(uri.getPath());
        if (!file.exists()) {
            throw new IllegalArgumentException("Cannot find file for " + uri);
        }
        if (file.canRead()) {
            return file;
        }
        throw new IllegalArgumentException("Cannot read file for " + uri);
    }

    public static String getPackageNameNormalized(String str) {
        if (str.length() <= 32) {
            return str;
        }
        try {
            return IntegrityUtils.getHexDigest(
                    MessageDigest.getInstance("SHA-256")
                            .digest(str.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static Signature[] getSignatures(String str, SigningDetails signingDetails) {
        Signature[] signatures = signingDetails.getSignatures();
        if (signatures == null || signatures.length < 1) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Package signature not found in ", str));
        }
        return signatures;
    }

    public final List getAllowedRuleProviderSystemApps() {
        List<String> asList =
                Arrays.asList(
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.vendor_disallowed_apps_managed_profile));
        ArrayList arrayList = new ArrayList();
        for (String str : asList) {
            if (isSystemApp(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final String getCallerPackageNameOrThrow(int i) {
        List allowedRuleProviderSystemApps = getAllowedRuleProviderSystemApps();
        List<String> packageListForUid = getPackageListForUid(i);
        ArrayList arrayList = new ArrayList();
        for (String str : packageListForUid) {
            if (((ArrayList) allowedRuleProviderSystemApps).contains(str)) {
                arrayList.add(str);
            }
        }
        String str2 = arrayList.isEmpty() ? null : (String) arrayList.get(0);
        if (str2 != null) {
            return str2;
        }
        throw new SecurityException(
                "Only system packages specified in config_integrityRuleProviderPackages are allowed"
                    + " to call this method.");
    }

    public final String getCurrentRuleSetProvider() {
        String str;
        getCallerPackageNameOrThrow(Binder.getCallingUid());
        RuleMetadata ruleMetadata = this.mIntegrityFileManager.mRuleMetadataCache;
        return (ruleMetadata == null || (str = ruleMetadata.mRuleProvider) == null) ? "" : str;
    }

    public final String getCurrentRuleSetVersion() {
        String str;
        getCallerPackageNameOrThrow(Binder.getCallingUid());
        RuleMetadata ruleMetadata = this.mIntegrityFileManager.mRuleMetadataCache;
        return (ruleMetadata == null || (str = ruleMetadata.mVersion) == null) ? "" : str;
    }

    public final ParceledListSlice getCurrentRules() {
        List emptyList = Collections.emptyList();
        try {
            emptyList = this.mIntegrityFileManager.readRules(null);
        } catch (Exception e) {
            Slog.e("AppIntegrityManagerServiceImpl", "Error getting current rules", e);
        }
        return new ParceledListSlice(emptyList);
    }

    public final List getInstallerCertificateFingerprint(String str) {
        if (str.equals("adb") || str.equals("")) {
            return Collections.emptyList();
        }
        AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(str);
        if (androidPackage == null) {
            Slog.w("AppIntegrityManagerServiceImpl", "Installer package " + str + " not found.");
            return Collections.emptyList();
        }
        String packageName = androidPackage.getPackageName();
        SigningDetails signingDetails = androidPackage.getSigningDetails();
        ArrayList arrayList = new ArrayList();
        for (Signature signature : getSignatures(packageName, signingDetails)) {
            arrayList.add(getFingerprint(signature));
        }
        return arrayList;
    }

    public final String getInstallerPackageName(Intent intent) {
        String stringExtra =
                intent.getStringExtra("android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE");
        if (PackageManagerServiceUtils.isInstalledByAdb(stringExtra)) {
            return "adb";
        }
        int intExtra =
                intent.getIntExtra("android.content.pm.extra.VERIFICATION_INSTALLER_UID", -1);
        if (intExtra < 0) {
            Slog.e(
                    "AppIntegrityManagerServiceImpl",
                    "Installer cannot be determined: installer: "
                            + stringExtra
                            + " installer UID: "
                            + intExtra);
            return "";
        }
        if (!getPackageListForUid(intExtra).contains(stringExtra)) {
            return "";
        }
        if (!((HashSet) PACKAGE_INSTALLER).contains(stringExtra)) {
            return stringExtra;
        }
        int intExtra2 = intent.getIntExtra("android.intent.extra.ORIGINATING_UID", -1);
        if (intExtra2 < 0) {
            Slog.e(
                    "AppIntegrityManagerServiceImpl",
                    "Installer is package installer but originating UID not found.");
            return "";
        }
        List packageListForUid = getPackageListForUid(intExtra2);
        if (!packageListForUid.isEmpty()) {
            return (String) packageListForUid.get(0);
        }
        NandswapManager$$ExternalSyntheticOutline0.m(
                intExtra2,
                "No package found associated with originating UID ",
                "AppIntegrityManagerServiceImpl");
        return "";
    }

    public final List getPackageListForUid(int i) {
        try {
            return Arrays.asList(this.mContext.getPackageManager().getPackagesForUid(i));
        } catch (NullPointerException unused) {
            Slog.w(
                    "AppIntegrityManagerServiceImpl",
                    String.format("No packages were found for uid: %d", Integer.valueOf(i)));
            return List.of();
        }
    }

    public final Pair getPackageSigningAndMetadata(Uri uri) {
        File installationPath = getInstallationPath(uri);
        try {
            PackageParser2 packageParser2 = (PackageParser2) this.mParserSupplier.get();
            try {
                ParsedPackage parsePackage =
                        packageParser2.parsePackage(installationPath, 0, false);
                ParseResult signingDetails =
                        ParsingPackageUtils.getSigningDetails(
                                ParseTypeImpl.forDefaultParsing(), parsePackage, true);
                if (signingDetails.isError()) {
                    Slog.w(
                            "AppIntegrityManagerServiceImpl",
                            signingDetails.getErrorMessage(),
                            signingDetails.getException());
                    packageParser2.close();
                    return null;
                }
                Pair create =
                        Pair.create(
                                (SigningDetails) signingDetails.getResult(),
                                parsePackage.getMetaData());
                packageParser2.close();
                return create;
            } finally {
            }
        } catch (Exception e) {
            Slog.w("AppIntegrityManagerServiceImpl", "Exception reading " + uri, e);
            return null;
        }
    }

    public final List getWhitelistedRuleProviders() {
        return getAllowedRuleProviderSystemApps();
    }

    public final boolean isSystemApp(String str) {
        try {
            ApplicationInfo applicationInfo =
                    this.mContext.getPackageManager().getPackageInfo(str, 0).applicationInfo;
            if (applicationInfo != null) {
                return applicationInfo.isSystemApp();
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void updateRuleSet(
            final String str,
            final ParceledListSlice parceledListSlice,
            final IntentSender intentSender) {
        final String callerPackageNameOrThrow = getCallerPackageNameOrThrow(Binder.getCallingUid());
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.integrity.AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z;
                        AppIntegrityManagerServiceImpl appIntegrityManagerServiceImpl =
                                AppIntegrityManagerServiceImpl.this;
                        String str2 = str;
                        String str3 = callerPackageNameOrThrow;
                        ParceledListSlice parceledListSlice2 = parceledListSlice;
                        IntentSender intentSender2 = intentSender;
                        appIntegrityManagerServiceImpl.getClass();
                        try {
                            appIntegrityManagerServiceImpl.mIntegrityFileManager.writeRules(
                                    str2, str3, parceledListSlice2.getList());
                            z = 1;
                        } catch (Exception e) {
                            Slog.e("AppIntegrityManagerServiceImpl", "Error writing rules.", e);
                            z = 0;
                        }
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.INTEGRITY_RULES_PUSHED, z, str3, str2);
                        Intent intent = new Intent();
                        intent.putExtra("android.content.integrity.extra.STATUS", !z);
                        try {
                            intentSender2.sendIntent(
                                    appIntegrityManagerServiceImpl.mContext, 0, intent, null, null);
                        } catch (Exception e2) {
                            Slog.e(
                                    "AppIntegrityManagerServiceImpl",
                                    "Error sending status feedback.",
                                    e2);
                        }
                    }
                });
    }
}
