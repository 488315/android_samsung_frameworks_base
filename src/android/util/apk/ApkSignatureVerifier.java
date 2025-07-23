package android.util.apk;

import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.os.Build;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.apk.ApkSignatureSchemeV2Verifier;
import android.util.apk.ApkSignatureSchemeV3Verifier;
import android.util.jar.StrictJarFile;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import libcore.io.IoUtils;

/* loaded from: classes4.dex */
public class ApkSignatureVerifier {
    private static final String LOG_TAG = "ApkSignatureVerifier";
    private static final AtomicReference<byte[]> sBuffer = new AtomicReference<>();
    private static final ArrayMap<SigningDetails, SigningDetails> sOverrideSigningDetails = new ArrayMap<>();

    public static int getMinimumSignatureSchemeVersionForTargetSdk(int i) {
        return i >= 30 ? 2 : 1;
    }

    public static ParseResult<SigningDetails> verify(ParseInput parseInput, String str, int i) {
        return verifySignatures(parseInput, str, i, true);
    }

    public static ParseResult<SigningDetails> unsafeGetCertsWithoutVerification(ParseInput parseInput, String str, int i) {
        return verifySignatures(parseInput, str, i, false);
    }

    private static ParseResult<SigningDetails> verifySignatures(ParseInput parseInput, String str, int i, boolean z) {
        SigningDetails signingDetails;
        ParseResult<SigningDetailsWithDigests> verifySignaturesInternal = verifySignaturesInternal(parseInput, str, i, z);
        if (verifySignaturesInternal.isError()) {
            return parseInput.error(verifySignaturesInternal);
        }
        SigningDetails signingDetails2 = verifySignaturesInternal.getResult().signingDetails;
        if (Build.isDebuggable()) {
            ArrayMap<SigningDetails, SigningDetails> arrayMap = sOverrideSigningDetails;
            synchronized (arrayMap) {
                signingDetails = arrayMap.get(signingDetails2);
            }
            if (signingDetails != null) {
                Slog.i(LOG_TAG, "Applying override signing details for APK " + str);
                signingDetails2 = signingDetails;
            }
        }
        return parseInput.success(signingDetails2);
    }

    public static void addOverrideSigningDetails(SigningDetails signingDetails, SigningDetails signingDetails2) {
        ArrayMap<SigningDetails, SigningDetails> arrayMap = sOverrideSigningDetails;
        synchronized (arrayMap) {
            arrayMap.put(signingDetails, signingDetails2);
        }
    }

    public static void removeOverrideSigningDetails(SigningDetails signingDetails) {
        ArrayMap<SigningDetails, SigningDetails> arrayMap = sOverrideSigningDetails;
        synchronized (arrayMap) {
            arrayMap.remove(signingDetails);
        }
    }

    public static void clearOverrideSigningDetails() {
        ArrayMap<SigningDetails, SigningDetails> arrayMap = sOverrideSigningDetails;
        synchronized (arrayMap) {
            arrayMap.clear();
        }
    }

    public static ParseResult<SigningDetailsWithDigests> verifySignaturesInternal(ParseInput parseInput, String str, int i, boolean z) {
        if (i > 4) {
            return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
        }
        try {
            return verifyV4Signature(parseInput, str, i, z);
        } catch (SignatureNotFoundException e) {
            if (i >= 4) {
                return parseInput.error(-103, "No APK Signature Scheme v4 signature in package " + str, e);
            }
            if (i > 3) {
                return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
            }
            return verifyV3AndBelowSignatures(parseInput, str, i, z);
        }
    }

    private static ParseResult<SigningDetailsWithDigests> verifyV3AndBelowSignatures(ParseInput parseInput, String str, int i, boolean z) {
        try {
            return verifyV3Signature(parseInput, str, z);
        } catch (SignatureNotFoundException e) {
            if (i >= 3) {
                return parseInput.error(-103, "No APK Signature Scheme v3 signature in package " + str, e);
            }
            if (i > 2) {
                return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
            }
            try {
                return verifyV2Signature(parseInput, str, z);
            } catch (SignatureNotFoundException e2) {
                if (i >= 2) {
                    return parseInput.error(-103, "No APK Signature Scheme v2 signature in package " + str, e2);
                }
                if (i > 1) {
                    return parseInput.error(-103, "No signature found in package of version " + i + " or newer for package " + str);
                }
                return verifyV1Signature(parseInput, str, z);
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:5|6|7|(2:10|(6:12|13|(2:15|(8:17|(2:19|(1:21)(3:22|23|24))|26|27|(3:30|(2:32|33)(1:34)|28)|35|36|37)(2:38|39))|40|41|42))|44|45|(3:47|(4:49|50|51|52)|66)|67|68|13|(0)|40|41|42) */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0087, code lost:
    
        r16 = r3;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00a6 A[Catch: SignatureNotFoundException -> 0x011b, Exception -> 0x011d, all -> 0x0162, TryCatch #0 {all -> 0x0162, blocks: (B:13:0x0096, B:15:0x00a6, B:17:0x00ae, B:19:0x00b1, B:21:0x00bb, B:23:0x00be, B:24:0x00c5, B:27:0x00c6, B:28:0x00ce, B:30:0x00d4, B:36:0x00e6, B:37:0x00ed, B:38:0x00ee, B:39:0x0105, B:40:0x0106, B:78:0x0161, B:52:0x0056, B:59:0x0089, B:63:0x0120, B:64:0x0136, B:54:0x013e, B:67:0x007f), top: B:5:0x0012 }] */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.security.cert.Certificate[][]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<android.util.apk.ApkSignatureVerifier.SigningDetailsWithDigests> verifyV4Signature(android.content.pm.parsing.result.ParseInput r19, java.lang.String r20, int r21, boolean r22) throws android.util.apk.SignatureNotFoundException {
        /*
            Method dump skipped, instructions count: 359
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.apk.ApkSignatureVerifier.verifyV4Signature(android.content.pm.parsing.result.ParseInput, java.lang.String, int, boolean):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<SigningDetailsWithDigests> verifyV3Signature(ParseInput parseInput, String str, boolean z) throws SignatureNotFoundException {
        ApkSignatureSchemeV3Verifier.VerifiedSigner unsafeGetCertsWithoutVerification;
        Signature[] signatureArr;
        Trace.traceBegin(262144L, z ? "verifyV3" : "certsOnlyV3");
        try {
            try {
                if (z) {
                    unsafeGetCertsWithoutVerification = ApkSignatureSchemeV3Verifier.verify(str);
                } else {
                    unsafeGetCertsWithoutVerification = ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(str);
                }
                Signature[] convertToSignatures = convertToSignatures(new Certificate[][]{unsafeGetCertsWithoutVerification.certs});
                if (unsafeGetCertsWithoutVerification.por != null) {
                    int size = unsafeGetCertsWithoutVerification.por.certs.size();
                    signatureArr = new Signature[size];
                    for (int i = 0; i < size; i++) {
                        Signature signature = new Signature(unsafeGetCertsWithoutVerification.por.certs.get(i).getEncoded());
                        signatureArr[i] = signature;
                        signature.setFlags(unsafeGetCertsWithoutVerification.por.flagsList.get(i).intValue());
                    }
                } else {
                    signatureArr = null;
                }
                ParseResult<SigningDetailsWithDigests> success = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(convertToSignatures, 3, signatureArr), unsafeGetCertsWithoutVerification.contentDigests));
                Trace.traceEnd(262144L);
                return success;
            } catch (SignatureNotFoundException e) {
                throw e;
            } catch (Exception e2) {
                ParseResult<SigningDetailsWithDigests> error = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v3", e2);
                Trace.traceEnd(262144L);
                return error;
            }
        } catch (Throwable th) {
            Trace.traceEnd(262144L);
            throw th;
        }
    }

    private static ParseResult<SigningDetailsWithDigests> verifyV2Signature(ParseInput parseInput, String str, boolean z) throws SignatureNotFoundException {
        Trace.traceBegin(262144L, z ? "verifyV2" : "certsOnlyV2");
        try {
            try {
                ApkSignatureSchemeV2Verifier.VerifiedSigner verify = ApkSignatureSchemeV2Verifier.verify(str, z);
                ParseResult<SigningDetailsWithDigests> success = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(convertToSignatures(verify.certs), 2), verify.contentDigests));
                Trace.traceEnd(262144L);
                return success;
            } catch (SignatureNotFoundException e) {
                throw e;
            } catch (Exception e2) {
                ParseResult<SigningDetailsWithDigests> error = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v2", e2);
                Trace.traceEnd(262144L);
                return error;
            }
        } catch (Throwable th) {
            Trace.traceEnd(262144L);
            throw th;
        }
    }

    private static ParseResult<SigningDetailsWithDigests> verifyV1Signature(ParseInput parseInput, String str, boolean z) {
        long j;
        long j2;
        long j3 = 262144;
        StrictJarFile strictJarFile = null;
        try {
            try {
                Trace.traceBegin(262144L, "strictJarFileCtor");
                StrictJarFile strictJarFile2 = new StrictJarFile(str, true, z);
                try {
                    try {
                        ArrayList<ZipEntry> arrayList = new ArrayList();
                        ZipEntry findEntry = strictJarFile2.findEntry("AndroidManifest.xml");
                        if (findEntry == null) {
                            ParseResult<SigningDetailsWithDigests> error = parseInput.error(-101, "Package " + str + " has no manifest");
                            Trace.traceEnd(262144L);
                            closeQuietly(strictJarFile2);
                            return error;
                        }
                        ParseResult<Certificate[][]> loadCertificates = loadCertificates(parseInput, strictJarFile2, findEntry);
                        if (loadCertificates.isError()) {
                            ParseResult<SigningDetailsWithDigests> error2 = parseInput.error((ParseResult<?>) loadCertificates);
                            Trace.traceEnd(262144L);
                            closeQuietly(strictJarFile2);
                            return error2;
                        }
                        Certificate[][] result = loadCertificates.getResult();
                        if (ArrayUtils.isEmpty(result)) {
                            ParseResult<SigningDetailsWithDigests> error3 = parseInput.error(-103, "Package " + str + " has no certificates at entry AndroidManifest.xml");
                            Trace.traceEnd(262144L);
                            closeQuietly(strictJarFile2);
                            return error3;
                        }
                        Signature[] convertToSignatures = convertToSignatures(result);
                        if (z) {
                            Iterator<ZipEntry> it = strictJarFile2.iterator();
                            while (it.hasNext()) {
                                ZipEntry next = it.next();
                                if (!next.isDirectory()) {
                                    String name = next.getName();
                                    j = j3;
                                    try {
                                        try {
                                            if (!name.startsWith("META-INF/") && !name.equals("AndroidManifest.xml")) {
                                                arrayList.add(next);
                                            }
                                            j3 = j;
                                        } catch (GeneralSecurityException e) {
                                            e = e;
                                            strictJarFile = strictJarFile2;
                                            ParseResult<SigningDetailsWithDigests> error4 = parseInput.error(-105, "Failed to collect certificates from " + str, e);
                                            Trace.traceEnd(j);
                                            closeQuietly(strictJarFile);
                                            return error4;
                                        } catch (Throwable th) {
                                            th = th;
                                            strictJarFile = strictJarFile2;
                                            Trace.traceEnd(j);
                                            closeQuietly(strictJarFile);
                                            throw th;
                                        }
                                    } catch (IOException | RuntimeException e2) {
                                        e = e2;
                                        strictJarFile = strictJarFile2;
                                        ParseResult<SigningDetailsWithDigests> error5 = parseInput.error(-103, "Failed to collect certificates from " + str, e);
                                        Trace.traceEnd(j);
                                        closeQuietly(strictJarFile);
                                        return error5;
                                    }
                                }
                            }
                            j2 = j3;
                            for (ZipEntry zipEntry : arrayList) {
                                ParseResult<Certificate[][]> loadCertificates2 = loadCertificates(parseInput, strictJarFile2, zipEntry);
                                if (loadCertificates2.isError()) {
                                    ParseResult<SigningDetailsWithDigests> error6 = parseInput.error((ParseResult<?>) loadCertificates2);
                                    Trace.traceEnd(j2);
                                    closeQuietly(strictJarFile2);
                                    return error6;
                                }
                                Certificate[][] result2 = loadCertificates2.getResult();
                                if (ArrayUtils.isEmpty(result2)) {
                                    ParseResult<SigningDetailsWithDigests> error7 = parseInput.error(-103, "Package " + str + " has no certificates at entry " + zipEntry.getName());
                                    Trace.traceEnd(j2);
                                    closeQuietly(strictJarFile2);
                                    return error7;
                                }
                                if (!Arrays.equals(convertToSignatures, convertToSignatures(result2))) {
                                    ParseResult<SigningDetailsWithDigests> error8 = parseInput.error(-104, "Package " + str + " has mismatched certificates at entry " + zipEntry.getName());
                                    Trace.traceEnd(j2);
                                    closeQuietly(strictJarFile2);
                                    return error8;
                                }
                            }
                        } else {
                            j2 = 262144;
                        }
                        ParseResult<SigningDetailsWithDigests> success = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(convertToSignatures, 1), null));
                        Trace.traceEnd(j2);
                        closeQuietly(strictJarFile2);
                        return success;
                    } catch (GeneralSecurityException e3) {
                        e = e3;
                        j = 262144;
                    } catch (Throwable th2) {
                        th = th2;
                        j = 262144;
                    }
                } catch (IOException | RuntimeException e4) {
                    e = e4;
                    j = 262144;
                }
            } catch (IOException | RuntimeException e5) {
                e = e5;
                j = 262144;
            } catch (GeneralSecurityException e6) {
                e = e6;
                j = 262144;
            } catch (Throwable th3) {
                th = th3;
                j = 262144;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    private static ParseResult<Certificate[][]> loadCertificates(ParseInput parseInput, StrictJarFile strictJarFile, ZipEntry zipEntry) {
        InputStream inputStream = null;
        try {
            inputStream = strictJarFile.getInputStream(zipEntry);
            readFullyIgnoringContents(inputStream);
            return parseInput.success(strictJarFile.getCertificateChains(zipEntry));
        } catch (IOException | RuntimeException e) {
            return parseInput.error(-102, "Failed reading " + zipEntry.getName() + " in " + strictJarFile, e);
        } finally {
            IoUtils.closeQuietly(inputStream);
        }
    }

    private static void readFullyIgnoringContents(InputStream inputStream) throws IOException {
        byte[] andSet = sBuffer.getAndSet(null);
        if (andSet == null) {
            andSet = new byte[4096];
        }
        while (inputStream.read(andSet, 0, andSet.length) != -1) {
        }
        sBuffer.set(andSet);
    }

    private static Signature[] convertToSignatures(Certificate[][] certificateArr) throws CertificateEncodingException {
        Signature[] signatureArr = new Signature[certificateArr.length];
        for (int i = 0; i < certificateArr.length; i++) {
            signatureArr[i] = new Signature(certificateArr[i]);
        }
        return signatureArr;
    }

    private static void closeQuietly(StrictJarFile strictJarFile) {
        if (strictJarFile != null) {
            try {
                strictJarFile.close();
            } catch (Exception unused) {
            }
        }
    }

    public static class Result {
        public final Certificate[][] certs;
        public final int signatureSchemeVersion;
        public final Signature[] sigs;

        public Result(Certificate[][] certificateArr, Signature[] signatureArr, int i) {
            this.certs = certificateArr;
            this.sigs = signatureArr;
            this.signatureSchemeVersion = i;
        }
    }

    public static byte[] getVerityRootHash(String str) throws IOException, SecurityException {
        try {
            try {
                return ApkSignatureSchemeV3Verifier.getVerityRootHash(str);
            } catch (SignatureNotFoundException unused) {
                return null;
            }
        } catch (SignatureNotFoundException unused2) {
            return ApkSignatureSchemeV2Verifier.getVerityRootHash(str);
        }
    }

    public static byte[] generateApkVerity(String str, ByteBufferFactory byteBufferFactory) throws IOException, SignatureNotFoundException, SecurityException, DigestException, NoSuchAlgorithmException {
        try {
            return ApkSignatureSchemeV3Verifier.generateApkVerity(str, byteBufferFactory);
        } catch (SignatureNotFoundException unused) {
            return ApkSignatureSchemeV2Verifier.generateApkVerity(str, byteBufferFactory);
        }
    }

    public static class SigningDetailsWithDigests {
        public final Map<Integer, byte[]> contentDigests;
        public final SigningDetails signingDetails;

        SigningDetailsWithDigests(SigningDetails signingDetails, Map<Integer, byte[]> map) {
            this.signingDetails = signingDetails;
            this.contentDigests = map;
        }
    }
}
