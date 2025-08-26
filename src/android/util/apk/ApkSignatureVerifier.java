package android.util.apk;

import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.os.Build;
import android.os.Trace;
import android.os.incremental.V4Signature;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.apk.ApkSignatureSchemeV2Verifier;
import android.util.apk.ApkSignatureSchemeV3Verifier;
import android.util.apk.ApkSignatureSchemeV4Verifier;
import android.util.jar.StrictJarFile;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
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
        ParseResult<SigningDetailsWithDigests> parseResultVerifySignaturesInternal = verifySignaturesInternal(parseInput, str, i, z);
        if (parseResultVerifySignaturesInternal.isError()) {
            return parseInput.error(parseResultVerifySignaturesInternal);
        }
        SigningDetails signingDetails2 = parseResultVerifySignaturesInternal.getResult().signingDetails;
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

    /* JADX WARN: Can't wrap try/catch for region: R(12:76|(7:10|(0)(1:13)|26|(2:28|(8:30|(2:32|(1:34)(3:80|35|36))|81|37|(3:40|(2:83|42)(1:84)|38)|82|43|44)(2:45|46))|47|48|49)|72|14|(3:16|(4:18|19|74|20)|85)|21|22|26|(0)|47|48|49) */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0087, code lost:
    
        r16 = r3;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a6 A[Catch: SignatureNotFoundException -> 0x011b, Exception -> 0x011d, all -> 0x0162, TryCatch #0 {all -> 0x0162, blocks: (B:26:0x0096, B:28:0x00a6, B:30:0x00ae, B:32:0x00b1, B:34:0x00bb, B:35:0x00be, B:36:0x00c5, B:37:0x00c6, B:38:0x00ce, B:40:0x00d4, B:43:0x00e6, B:44:0x00ed, B:45:0x00ee, B:46:0x0105, B:47:0x0106, B:66:0x0161, B:20:0x0056, B:24:0x0089, B:55:0x0120, B:56:0x0136, B:61:0x013e, B:21:0x007f), top: B:71:0x0012 }] */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.security.cert.Certificate[][]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<SigningDetailsWithDigests> verifyV4Signature(ParseInput parseInput, String str, int i, boolean z) throws Throwable {
        long j;
        Map<Integer, byte[]> map;
        Signature[] signatureArr;
        long j2 = 262144;
        Trace.traceBegin(262144L, z ? "verifyV4" : "certsOnlyV4");
        try {
            try {
                try {
                    Pair<V4Signature.HashingInfo, V4Signature.SigningInfos> pairExtractSignature = ApkSignatureSchemeV4Verifier.extractSignature(str);
                    V4Signature.HashingInfo hashingInfo = pairExtractSignature.first;
                    V4Signature.SigningInfos signingInfos = pairExtractSignature.second;
                    Signature[] signatureArr2 = null;
                    X509Certificate[][] x509CertificateArr = null;
                    signatureArr2 = null;
                    int i2 = -1;
                    try {
                        if (!Flags.alwaysLoadPastCertsV4() && !z) {
                            if (signingInfos.signingInfoBlocks.length <= 0) {
                                j = 262144;
                                signatureArr = null;
                                map = null;
                            }
                            ApkSignatureSchemeV4Verifier.VerifiedSigner verifiedSignerVerify = ApkSignatureSchemeV4Verifier.verify(str, hashingInfo, signingInfos, i2);
                            Signature[] signatureArrConvertToSignatures = convertToSignatures(new Certificate[][]{verifiedSignerVerify.certs});
                            if (z) {
                                Signature[] signatureArrConvertToSignatures2 = convertToSignatures(x509CertificateArr);
                                if (signatureArrConvertToSignatures2.length != signatureArrConvertToSignatures.length) {
                                    throw new SecurityException("Invalid number of certificates: " + signatureArrConvertToSignatures2.length);
                                }
                                int length = signatureArrConvertToSignatures.length;
                                for (int i3 = 0; i3 < length; i3++) {
                                    if (!signatureArrConvertToSignatures2[i3].equals(signatureArrConvertToSignatures[i3])) {
                                        throw new SecurityException("V4 signature certificate does not match V2/V3");
                                    }
                                }
                                Iterator<byte[]> it = map.values().iterator();
                                while (it.hasNext()) {
                                    if (ArrayUtils.equals(verifiedSignerVerify.apkDigest, it.next(), verifiedSignerVerify.apkDigest.length)) {
                                    }
                                }
                                throw new SecurityException("APK digest in V4 signature does not match V2/V3");
                            }
                            ParseResult<SigningDetailsWithDigests> parseResultSuccess = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(signatureArrConvertToSignatures, 4, signatureArr), verifiedSignerVerify.contentDigests));
                            Trace.traceEnd(j);
                            return parseResultSuccess;
                        }
                        ApkSignatureSchemeV3Verifier.VerifiedSigner verifiedSignerUnsafeGetCertsWithoutVerification = ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(str);
                        Map<Integer, byte[]> map2 = verifiedSignerUnsafeGetCertsWithoutVerification.contentDigests;
                        ?? r12 = {verifiedSignerUnsafeGetCertsWithoutVerification.certs};
                        if (verifiedSignerUnsafeGetCertsWithoutVerification.por != null) {
                            int size = verifiedSignerUnsafeGetCertsWithoutVerification.por.certs.size();
                            signatureArr2 = new Signature[size];
                            int i4 = 0;
                            while (i4 < size) {
                                j = j2;
                                try {
                                    try {
                                        Signature signature = new Signature(verifiedSignerUnsafeGetCertsWithoutVerification.por.certs.get(i4).getEncoded());
                                        signatureArr2[i4] = signature;
                                        signature.setFlags(verifiedSignerUnsafeGetCertsWithoutVerification.por.flagsList.get(i4).intValue());
                                        i4++;
                                        j2 = j;
                                    } catch (Exception e) {
                                        e = e;
                                        ParseResult<SigningDetailsWithDigests> parseResultError = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v4", e);
                                        Trace.traceEnd(j);
                                        return parseResultError;
                                    }
                                } catch (SignatureNotFoundException unused) {
                                    try {
                                        ApkSignatureSchemeV2Verifier.VerifiedSigner verifiedSignerVerify2 = ApkSignatureSchemeV2Verifier.verify(str, false);
                                        map = verifiedSignerVerify2.contentDigests;
                                        Signature[] signatureArr3 = signatureArr2;
                                        x509CertificateArr = verifiedSignerVerify2.certs;
                                        signatureArr = signatureArr3;
                                        ApkSignatureSchemeV4Verifier.VerifiedSigner verifiedSignerVerify3 = ApkSignatureSchemeV4Verifier.verify(str, hashingInfo, signingInfos, i2);
                                        Signature[] signatureArrConvertToSignatures3 = convertToSignatures(new Certificate[][]{verifiedSignerVerify3.certs});
                                        if (z) {
                                        }
                                        ParseResult<SigningDetailsWithDigests> parseResultSuccess2 = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(signatureArrConvertToSignatures3, 4, signatureArr), verifiedSignerVerify3.contentDigests));
                                        Trace.traceEnd(j);
                                        return parseResultSuccess2;
                                    } catch (SignatureNotFoundException e2) {
                                        throw new SecurityException("V4 verification failed to collect V2/V3 certificates from : " + str, e2);
                                    }
                                }
                            }
                        }
                        j = j2;
                        i2 = verifiedSignerUnsafeGetCertsWithoutVerification.blockId;
                        signatureArr = signatureArr2;
                        map = map2;
                        x509CertificateArr = r12;
                        ApkSignatureSchemeV4Verifier.VerifiedSigner verifiedSignerVerify32 = ApkSignatureSchemeV4Verifier.verify(str, hashingInfo, signingInfos, i2);
                        Signature[] signatureArrConvertToSignatures32 = convertToSignatures(new Certificate[][]{verifiedSignerVerify32.certs});
                        if (z) {
                        }
                        ParseResult<SigningDetailsWithDigests> parseResultSuccess22 = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(signatureArrConvertToSignatures32, 4, signatureArr), verifiedSignerVerify32.contentDigests));
                        Trace.traceEnd(j);
                        return parseResultSuccess22;
                    } catch (SignatureNotFoundException e3) {
                        throw e3;
                    }
                } catch (Throwable th) {
                    th = th;
                    Trace.traceEnd(j);
                    throw th;
                }
            } catch (SignatureNotFoundException e4) {
                throw e4;
            }
        } catch (Exception e5) {
            e = e5;
            j = j2;
        } catch (Throwable th2) {
            th = th2;
            long j3 = j2;
            Trace.traceEnd(j3);
            throw th;
        }
    }

    private static ParseResult<SigningDetailsWithDigests> verifyV3Signature(ParseInput parseInput, String str, boolean z) throws SignatureNotFoundException {
        ApkSignatureSchemeV3Verifier.VerifiedSigner verifiedSignerUnsafeGetCertsWithoutVerification;
        Signature[] signatureArr;
        Trace.traceBegin(262144L, z ? "verifyV3" : "certsOnlyV3");
        try {
            try {
                if (z) {
                    verifiedSignerUnsafeGetCertsWithoutVerification = ApkSignatureSchemeV3Verifier.verify(str);
                } else {
                    verifiedSignerUnsafeGetCertsWithoutVerification = ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(str);
                }
                Signature[] signatureArrConvertToSignatures = convertToSignatures(new Certificate[][]{verifiedSignerUnsafeGetCertsWithoutVerification.certs});
                if (verifiedSignerUnsafeGetCertsWithoutVerification.por != null) {
                    int size = verifiedSignerUnsafeGetCertsWithoutVerification.por.certs.size();
                    signatureArr = new Signature[size];
                    for (int i = 0; i < size; i++) {
                        Signature signature = new Signature(verifiedSignerUnsafeGetCertsWithoutVerification.por.certs.get(i).getEncoded());
                        signatureArr[i] = signature;
                        signature.setFlags(verifiedSignerUnsafeGetCertsWithoutVerification.por.flagsList.get(i).intValue());
                    }
                } else {
                    signatureArr = null;
                }
                ParseResult<SigningDetailsWithDigests> parseResultSuccess = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(signatureArrConvertToSignatures, 3, signatureArr), verifiedSignerUnsafeGetCertsWithoutVerification.contentDigests));
                Trace.traceEnd(262144L);
                return parseResultSuccess;
            } catch (SignatureNotFoundException e) {
                throw e;
            } catch (Exception e2) {
                ParseResult<SigningDetailsWithDigests> parseResultError = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v3", e2);
                Trace.traceEnd(262144L);
                return parseResultError;
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
                ApkSignatureSchemeV2Verifier.VerifiedSigner verifiedSignerVerify = ApkSignatureSchemeV2Verifier.verify(str, z);
                ParseResult<SigningDetailsWithDigests> parseResultSuccess = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(convertToSignatures(verifiedSignerVerify.certs), 2), verifiedSignerVerify.contentDigests));
                Trace.traceEnd(262144L);
                return parseResultSuccess;
            } catch (SignatureNotFoundException e) {
                throw e;
            } catch (Exception e2) {
                ParseResult<SigningDetailsWithDigests> parseResultError = parseInput.error(-103, "Failed to collect certificates from " + str + " using APK Signature Scheme v2", e2);
                Trace.traceEnd(262144L);
                return parseResultError;
            }
        } catch (Throwable th) {
            Trace.traceEnd(262144L);
            throw th;
        }
    }

    private static ParseResult<SigningDetailsWithDigests> verifyV1Signature(ParseInput parseInput, String str, boolean z) throws Throwable {
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
                        ZipEntry zipEntryFindEntry = strictJarFile2.findEntry("AndroidManifest.xml");
                        if (zipEntryFindEntry == null) {
                            ParseResult<SigningDetailsWithDigests> parseResultError = parseInput.error(-101, "Package " + str + " has no manifest");
                            Trace.traceEnd(262144L);
                            closeQuietly(strictJarFile2);
                            return parseResultError;
                        }
                        ParseResult<Certificate[][]> parseResultLoadCertificates = loadCertificates(parseInput, strictJarFile2, zipEntryFindEntry);
                        if (parseResultLoadCertificates.isError()) {
                            ParseResult<SigningDetailsWithDigests> parseResultError2 = parseInput.error((ParseResult<?>) parseResultLoadCertificates);
                            Trace.traceEnd(262144L);
                            closeQuietly(strictJarFile2);
                            return parseResultError2;
                        }
                        Certificate[][] result = parseResultLoadCertificates.getResult();
                        if (ArrayUtils.isEmpty(result)) {
                            ParseResult<SigningDetailsWithDigests> parseResultError3 = parseInput.error(-103, "Package " + str + " has no certificates at entry AndroidManifest.xml");
                            Trace.traceEnd(262144L);
                            closeQuietly(strictJarFile2);
                            return parseResultError3;
                        }
                        Signature[] signatureArrConvertToSignatures = convertToSignatures(result);
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
                                        } catch (IOException | RuntimeException e) {
                                            e = e;
                                            strictJarFile = strictJarFile2;
                                            ParseResult<SigningDetailsWithDigests> parseResultError4 = parseInput.error(-103, "Failed to collect certificates from " + str, e);
                                            Trace.traceEnd(j);
                                            closeQuietly(strictJarFile);
                                            return parseResultError4;
                                        }
                                    } catch (GeneralSecurityException e2) {
                                        e = e2;
                                        strictJarFile = strictJarFile2;
                                        ParseResult<SigningDetailsWithDigests> parseResultError5 = parseInput.error(-105, "Failed to collect certificates from " + str, e);
                                        Trace.traceEnd(j);
                                        closeQuietly(strictJarFile);
                                        return parseResultError5;
                                    } catch (Throwable th) {
                                        th = th;
                                        strictJarFile = strictJarFile2;
                                        Trace.traceEnd(j);
                                        closeQuietly(strictJarFile);
                                        throw th;
                                    }
                                }
                            }
                            j2 = j3;
                            for (ZipEntry zipEntry : arrayList) {
                                ParseResult<Certificate[][]> parseResultLoadCertificates2 = loadCertificates(parseInput, strictJarFile2, zipEntry);
                                if (parseResultLoadCertificates2.isError()) {
                                    ParseResult<SigningDetailsWithDigests> parseResultError6 = parseInput.error((ParseResult<?>) parseResultLoadCertificates2);
                                    Trace.traceEnd(j2);
                                    closeQuietly(strictJarFile2);
                                    return parseResultError6;
                                }
                                Certificate[][] result2 = parseResultLoadCertificates2.getResult();
                                if (ArrayUtils.isEmpty(result2)) {
                                    ParseResult<SigningDetailsWithDigests> parseResultError7 = parseInput.error(-103, "Package " + str + " has no certificates at entry " + zipEntry.getName());
                                    Trace.traceEnd(j2);
                                    closeQuietly(strictJarFile2);
                                    return parseResultError7;
                                }
                                if (!Arrays.equals(signatureArrConvertToSignatures, convertToSignatures(result2))) {
                                    ParseResult<SigningDetailsWithDigests> parseResultError8 = parseInput.error(-104, "Package " + str + " has mismatched certificates at entry " + zipEntry.getName());
                                    Trace.traceEnd(j2);
                                    closeQuietly(strictJarFile2);
                                    return parseResultError8;
                                }
                            }
                        } else {
                            j2 = 262144;
                        }
                        ParseResult<SigningDetailsWithDigests> parseResultSuccess = parseInput.success(new SigningDetailsWithDigests(new SigningDetails(signatureArrConvertToSignatures, 1), null));
                        Trace.traceEnd(j2);
                        closeQuietly(strictJarFile2);
                        return parseResultSuccess;
                    } catch (IOException | RuntimeException e3) {
                        e = e3;
                        j = 262144;
                    }
                } catch (GeneralSecurityException e4) {
                    e = e4;
                    j = 262144;
                } catch (Throwable th2) {
                    th = th2;
                    j = 262144;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException | RuntimeException e5) {
            e = e5;
            j = 262144;
        } catch (GeneralSecurityException e6) {
            e = e6;
            j = 262144;
        } catch (Throwable th4) {
            th = th4;
            j = 262144;
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
                return ApkSignatureSchemeV2Verifier.getVerityRootHash(str);
            }
        } catch (SignatureNotFoundException unused2) {
            return null;
        }
    }

    public static byte[] generateApkVerity(String str, ByteBufferFactory byteBufferFactory) throws SignatureNotFoundException, NoSuchAlgorithmException, DigestException, IOException, SecurityException {
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
