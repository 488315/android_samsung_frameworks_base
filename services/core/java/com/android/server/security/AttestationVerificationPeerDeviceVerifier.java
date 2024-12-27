package com.android.server.security;

import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Bundle;
import android.security.attestationverification.AttestationVerificationManager;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;

import com.samsung.android.knoxguard.service.utils.Constants;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AttestationVerificationPeerDeviceVerifier {
    public static final Set ANDROID_SYSTEM_PACKAGE_NAME_SET;
    public static final boolean DEBUG;
    public final CertPathValidator mCertPathValidator;
    public final CertificateFactory mCertificateFactory;
    public final Context mContext;
    public final AttestationVerificationManagerService.DumpLogger mDumpLogger;
    public final boolean mRevocationEnabled;
    public final LocalDate mTestLocalPatchDate;
    public final LocalDate mTestSystemDate;
    public final Set mTrustAnchors;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AndroidRevocationStatusListChecker extends PKIXCertPathChecker {
        public JSONObject mJsonStatusMap;
        public String mStatusUrl;

        public AndroidRevocationStatusListChecker() {}

        @Override // java.security.cert.PKIXCertPathChecker
        public final void check(Certificate certificate, Collection collection) {
            String bigInteger = ((X509Certificate) certificate).getSerialNumber().toString(16);
            if (bigInteger == null) {
                throw new CertPathValidatorException("Certificate serial number can not be null.");
            }
            if (this.mJsonStatusMap.has(bigInteger)) {
                JSONObject jSONObject = this.mJsonStatusMap.getJSONObject(bigInteger);
                String string = jSONObject.getString(Constants.JSON_CLIENT_DATA_STATUS);
                String string2 = jSONObject.getString("reason");
                StringBuilder m =
                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                "Invalid certificate with serial number ",
                                bigInteger,
                                " has status ",
                                string,
                                " because reason ");
                m.append(string2);
                throw new CertPathValidatorException(m.toString());
            }
        }

        @Override // java.security.cert.PKIXCertPathChecker
        public final Set getSupportedExtensions() {
            return null;
        }

        @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
        public final void init(boolean z) {
            String string =
                    AttestationVerificationPeerDeviceVerifier.this
                            .mContext
                            .getResources()
                            .getString(17043440);
            this.mStatusUrl = string;
            if (string == null || string.isEmpty()) {
                throw new CertPathValidatorException(
                        "R.string.vendor_required_attestation_revocation_list_url is empty.");
            }
            try {
                try {
                    InputStream openStream = new URL(this.mStatusUrl).openStream();
                    try {
                        JSONObject jSONObject =
                                new JSONObject(
                                                new String(
                                                        openStream.readAllBytes(),
                                                        StandardCharsets.UTF_8))
                                        .getJSONObject("entries");
                        openStream.close();
                        this.mJsonStatusMap = jSONObject;
                    } finally {
                    }
                } catch (Throwable th) {
                    throw new CertPathValidatorException(
                            "Unable to parse revocation status from " + this.mStatusUrl, th);
                }
            } catch (Throwable th2) {
                throw new CertPathValidatorException(
                        "Unable to get revocation status from " + this.mStatusUrl, th2);
            }
        }

        @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
        public final boolean isForwardCheckingSupported() {
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyDumpData {
        public boolean mAttestationParametersOk;
        public boolean mAttestationVersionAtLeast3;
        public boolean mBindingOk;
        public int mBindingType;
        public boolean mBootStateIsVerified;
        public boolean mCertChainOk;
        public boolean mCertPathValidatorAvailable;
        public boolean mCertificationFactoryAvailable;
        public int mEventNumber;
        public long mEventTimeMs;
        public boolean mKeyBootPatchLevelInRange;
        public boolean mKeyHwBacked;
        public boolean mKeyVendorPatchLevelInRange;
        public boolean mKeymasterHwBacked;
        public boolean mKeymasterVersionAtLeast4;
        public boolean mOsPatchLevelInRange;
        public boolean mOsVersionAtLeast10;
        public int mResult;
        public boolean mSystemOwned;
        public boolean mSystemOwnershipChecked;
        public boolean mVerifiedBootStateLocked;

        public static String booleanToOkFail(boolean z) {
            return z ? "OK" : "FAILURE";
        }

        public final void dumpTo(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(
                    "Result: "
                            + AttestationVerificationManager.verificationResultCodeToString(
                                    this.mResult));
            if (!this.mCertificationFactoryAvailable) {
                indentingPrintWriter.println("Certificate Factory Unavailable");
                return;
            }
            if (!this.mCertPathValidatorAvailable) {
                indentingPrintWriter.println("Cert Path Validator Unavailable");
                return;
            }
            if (!this.mAttestationParametersOk) {
                indentingPrintWriter.println("Attestation parameters set incorrectly.");
                return;
            }
            indentingPrintWriter.println(
                    "Certificate Chain Valid (inc. Trust Anchor): "
                            .concat(booleanToOkFail(this.mCertChainOk)));
            if (this.mCertChainOk) {
                indentingPrintWriter.println(
                        "Local Binding: ".concat(booleanToOkFail(this.mBindingOk)));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Binding Type: " + this.mBindingType);
                indentingPrintWriter.decreaseIndent();
                if (this.mSystemOwnershipChecked) {
                    indentingPrintWriter.println(
                            "System Ownership: ".concat(booleanToOkFail(this.mSystemOwned)));
                }
                indentingPrintWriter.println("KeyStore Attestation Parameters");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(
                        "OS Version >= 10: ".concat(booleanToOkFail(this.mOsVersionAtLeast10)));
                indentingPrintWriter.println(
                        "OS Patch Level in Range: "
                                .concat(booleanToOkFail(this.mOsPatchLevelInRange)));
                indentingPrintWriter.println(
                        "Attestation Version >= 3: "
                                .concat(booleanToOkFail(this.mAttestationVersionAtLeast3)));
                indentingPrintWriter.println(
                        "Keymaster Version >= 4: "
                                .concat(booleanToOkFail(this.mKeymasterVersionAtLeast4)));
                indentingPrintWriter.println(
                        "Keymaster HW-Backed: ".concat(booleanToOkFail(this.mKeymasterHwBacked)));
                indentingPrintWriter.println(
                        "Key is HW Backed: ".concat(booleanToOkFail(this.mKeyHwBacked)));
                indentingPrintWriter.println(
                        "Boot State is VERIFIED: "
                                .concat(booleanToOkFail(this.mBootStateIsVerified)));
                indentingPrintWriter.println(
                        "Verified Boot is LOCKED: "
                                .concat(booleanToOkFail(this.mVerifiedBootStateLocked)));
                indentingPrintWriter.println(
                        "Key Boot Level in Range: "
                                .concat(booleanToOkFail(this.mKeyBootPatchLevelInRange)));
                indentingPrintWriter.println(
                        "Key Vendor Patch Level in Range: "
                                .concat(booleanToOkFail(this.mKeyVendorPatchLevelInRange)));
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    static {
        DEBUG = Build.IS_DEBUGGABLE && Log.isLoggable("AVF", 2);
        ANDROID_SYSTEM_PACKAGE_NAME_SET = Collections.singleton("AndroidSystem");
    }

    public AttestationVerificationPeerDeviceVerifier(
            Context context, AttestationVerificationManagerService.DumpLogger dumpLogger) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mDumpLogger = dumpLogger;
        this.mCertificateFactory = CertificateFactory.getInstance("X.509");
        this.mCertPathValidator = CertPathValidator.getInstance("PKIX");
        HashSet hashSet = new HashSet();
        try {
            for (String str : context.getResources().getStringArray(17236487)) {
                hashSet.add(
                        new TrustAnchor(
                                (X509Certificate)
                                        this.mCertificateFactory.generateCertificate(
                                                new ByteArrayInputStream(
                                                        str.replaceAll("\\s+", "\n")
                                                                .replaceAll(
                                                                        "-BEGIN\\nCERTIFICATE-",
                                                                        "-BEGIN CERTIFICATE-")
                                                                .replaceAll(
                                                                        "-END\\nCERTIFICATE-",
                                                                        "-END CERTIFICATE-")
                                                                .getBytes(StandardCharsets.UTF_8))),
                                null));
            }
            this.mTrustAnchors = Collections.unmodifiableSet(hashSet);
            this.mRevocationEnabled = true;
            this.mTestSystemDate = null;
            this.mTestLocalPatchDate = null;
        } catch (CertificateException e) {
            e.printStackTrace();
            throw new CertPathValidatorException("Invalid trust anchor certificate.", e);
        }
    }

    public AttestationVerificationPeerDeviceVerifier(
            Context context,
            AttestationVerificationManagerService.DumpLogger dumpLogger,
            Set set,
            boolean z,
            LocalDate localDate,
            LocalDate localDate2)
            throws Exception {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mDumpLogger = dumpLogger;
        this.mCertificateFactory = CertificateFactory.getInstance("X.509");
        this.mCertPathValidator = CertPathValidator.getInstance("PKIX");
        this.mTrustAnchors = set;
        this.mRevocationEnabled = z;
        this.mTestSystemDate = localDate;
        this.mTestLocalPatchDate = localDate2;
    }

    public static boolean checkLocalBindingRequirements(
            X509Certificate x509Certificate,
            AndroidKeystoreAttestationVerificationAttributes
                    androidKeystoreAttestationVerificationAttributes,
            int i,
            Bundle bundle,
            MyDumpData myDumpData) {
        myDumpData.mBindingType = i;
        if (i == 2) {
            if (!Arrays.equals(
                    x509Certificate.getPublicKey().getEncoded(),
                    bundle.getByteArray("localbinding.public_key"))) {
                debugVerboseLog("Provided public key does not match leaf certificate public key.");
                return false;
            }
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(
                        "Unsupported local binding type "
                                + AttestationVerificationManager.localBindingTypeToString(i));
            }
            if (!Arrays.equals(
                    androidKeystoreAttestationVerificationAttributes.mAttestationChallenge
                            .toByteArray(),
                    bundle.getByteArray("localbinding.challenge"))) {
                debugVerboseLog("Provided challenge does not match leaf certificate challenge.");
                return false;
            }
        }
        myDumpData.mBindingOk = true;
        if (bundle.containsKey("android.key_owned_by_system")) {
            myDumpData.mSystemOwnershipChecked = true;
            if (!bundle.getBoolean("android.key_owned_by_system")) {
                throw new IllegalArgumentException(
                        "The value of the requirement key android.key_owned_by_system cannot be"
                            + " false. You can remove the key if you don't want to verify it.");
            }
            Set keySet =
                    Collections.unmodifiableMap(
                                    androidKeystoreAttestationVerificationAttributes
                                            .mApplicationPackageNameVersion)
                            .keySet();
            if (!ANDROID_SYSTEM_PACKAGE_NAME_SET.equals(keySet)) {
                debugVerboseLog("Owner is not system, packages=" + keySet);
                debugVerboseLog("Certificate public key is not owned by the AndroidSystem.");
                return false;
            }
            myDumpData.mSystemOwned = true;
        }
        return true;
    }

    public static void debugVerboseLog(String str) {
        if (DEBUG) {
            Slog.v("AVF", str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkAttestationForPeerDeviceProfile(
            com.android.server.security.AndroidKeystoreAttestationVerificationAttributes r7,
            com.android.server.security.AttestationVerificationPeerDeviceVerifier.MyDumpData r8) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.security.AttestationVerificationPeerDeviceVerifier.checkAttestationForPeerDeviceProfile(com.android.server.security.AndroidKeystoreAttestationVerificationAttributes,"
                    + " com.android.server.security.AttestationVerificationPeerDeviceVerifier$MyDumpData):boolean");
    }

    public final List getCertificates(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        while (byteArrayInputStream.available() > 0) {
            arrayList.add(
                    (X509Certificate)
                            this.mCertificateFactory.generateCertificate(byteArrayInputStream));
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0061, code lost:

       if (r2.between(r8, r9) <= 12) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:

       r1 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0075, code lost:

       return r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0072, code lost:

       if (r2.between(r9, r8) <= 12) goto L24;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isValidPatchLevel(int r9) {
        /*
            r8 = this;
            java.time.LocalDate r0 = r8.mTestSystemDate
            if (r0 == 0) goto L5
            goto Ld
        L5:
            java.time.ZoneId r0 = java.time.ZoneId.systemDefault()
            java.time.LocalDate r0 = java.time.LocalDate.now(r0)
        Ld:
            r1 = 0
            java.time.LocalDate r8 = r8.mTestLocalPatchDate     // Catch: java.lang.Throwable -> L76
            if (r8 == 0) goto L13
            goto L19
        L13:
            java.lang.String r8 = android.os.Build.VERSION.SECURITY_PATCH     // Catch: java.lang.Throwable -> L76
            java.time.LocalDate r8 = java.time.LocalDate.parse(r8)     // Catch: java.lang.Throwable -> L76
        L19:
            java.time.temporal.ChronoUnit r2 = java.time.temporal.ChronoUnit.MONTHS
            long r3 = r2.between(r8, r0)
            r5 = 12
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            r3 = 1
            if (r0 <= 0) goto L27
            return r3
        L27:
            java.lang.String r9 = java.lang.String.valueOf(r9)
            int r0 = r9.length()
            r4 = 6
            if (r0 == r4) goto L40
            int r0 = r9.length()
            r7 = 8
            if (r0 == r7) goto L40
            java.lang.String r8 = "Patch level is not in format YYYYMM or YYYYMMDD"
            debugVerboseLog(r8)
            return r1
        L40:
            r0 = 4
            java.lang.String r7 = r9.substring(r1, r0)
            int r7 = java.lang.Integer.parseInt(r7)
            java.lang.String r9 = r9.substring(r0, r4)
            int r9 = java.lang.Integer.parseInt(r9)
            java.time.LocalDate r9 = java.time.LocalDate.of(r7, r9, r3)
            int r0 = r9.compareTo(r8)
            if (r0 <= 0) goto L66
            long r8 = r2.between(r8, r9)
            int r8 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r8 > 0) goto L64
        L63:
            r1 = r3
        L64:
            r3 = r1
            goto L75
        L66:
            int r0 = r9.compareTo(r8)
            if (r0 >= 0) goto L75
            long r8 = r2.between(r9, r8)
            int r8 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r8 > 0) goto L64
            goto L63
        L75:
            return r3
        L76:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Build.VERSION.SECURITY_PATCH: "
            r8.<init>(r9)
            java.lang.String r9 = android.os.Build.VERSION.SECURITY_PATCH
            r8.append(r9)
            java.lang.String r9 = " is not in format YYYY-MM-DD"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            debugVerboseLog(r8)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.security.AttestationVerificationPeerDeviceVerifier.isValidPatchLevel(int):boolean");
    }

    public final void validateCertificateChain(List list) {
        if (((ArrayList) list).size() < 2) {
            debugVerboseLog("Certificate chain less than 2 in size.");
            throw new CertificateException("Certificate chain less than 2 in size.");
        }
        CertPath generateCertPath =
                this.mCertificateFactory.generateCertPath((List<? extends Certificate>) list);
        PKIXParameters pKIXParameters = new PKIXParameters((Set<TrustAnchor>) this.mTrustAnchors);
        if (this.mRevocationEnabled) {
            pKIXParameters.addCertPathChecker(new AndroidRevocationStatusListChecker());
        }
        pKIXParameters.setRevocationEnabled(false);
        this.mCertPathValidator.validate(generateCertPath, pKIXParameters);
    }
}
