package android.util.apk;

import android.os.incremental.IncrementalManager;
import android.os.incremental.V4Signature;
import android.util.ArrayMap;
import android.util.Pair;
import com.android.internal.security.VerityUtils;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public class ApkSignatureSchemeV4Verifier {
    static final int APK_SIGNATURE_SCHEME_DEFAULT = -1;

    public static VerifiedSigner extractCertificates(String str) throws SignatureNotFoundException, SignatureException, SecurityException {
        Pair<V4Signature.HashingInfo, V4Signature.SigningInfos> extractSignature = extractSignature(str);
        return verify(str, extractSignature.first, extractSignature.second, -1);
    }

    public static Pair<V4Signature.HashingInfo, V4Signature.SigningInfos> extractSignature(String str) throws SignatureNotFoundException, SignatureException {
        V4Signature readFrom;
        boolean z;
        try {
            try {
                try {
                    File file = new File(str);
                    byte[] unsafeGetFileSignature = IncrementalManager.unsafeGetFileSignature(file.getAbsolutePath());
                    if (unsafeGetFileSignature != null && unsafeGetFileSignature.length > 0) {
                        readFrom = V4Signature.readFrom(unsafeGetFileSignature);
                        z = false;
                    } else {
                        try {
                            FileInputStream fileInputStream = new FileInputStream(new File(file.getAbsolutePath() + V4Signature.EXT).getAbsolutePath());
                            try {
                                readFrom = V4Signature.readFrom(fileInputStream);
                                fileInputStream.close();
                                z = true;
                            } catch (Throwable th) {
                                try {
                                    fileInputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } catch (IOException unused) {
                            throw new SignatureNotFoundException("Failed to obtain signature bytes from .idsig");
                        }
                    }
                    if (!readFrom.isVersionSupported()) {
                        throw new SecurityException("v4 signature version " + readFrom.version + " is not supported");
                    }
                    V4Signature.HashingInfo fromByteArray = V4Signature.HashingInfo.fromByteArray(readFrom.hashingInfo);
                    V4Signature.SigningInfos fromByteArray2 = V4Signature.SigningInfos.fromByteArray(readFrom.signingInfos);
                    if (z) {
                        byte[] fsverityDigest = VerityUtils.getFsverityDigest(file.getAbsolutePath());
                        if (fsverityDigest == null) {
                            throw new SecurityException("The APK does not have fs-verity");
                        }
                        if (!Arrays.equals(VerityUtils.generateFsVerityDigest(file.length(), fromByteArray), fsverityDigest)) {
                            throw new SignatureException("Actual digest does not match the v4 signature");
                        }
                    }
                    return Pair.create(fromByteArray, fromByteArray2);
                } catch (IOException e) {
                    throw new SignatureNotFoundException("Failed to read V4 signature.", e);
                }
            } catch (EOFException e2) {
                throw new SignatureException("V4 signature is invalid.", e2);
            }
        } catch (DigestException | NoSuchAlgorithmException e3) {
            throw new SecurityException("Failed to calculate the digest", e3);
        }
    }

    public static VerifiedSigner verify(String str, V4Signature.HashingInfo hashingInfo, V4Signature.SigningInfos signingInfos, int i) throws SignatureNotFoundException, SecurityException {
        V4Signature.SigningInfo findSigningInfoForBlockId = findSigningInfoForBlockId(signingInfos, i);
        Pair<Certificate, byte[]> verifySigner = verifySigner(findSigningInfoForBlockId, V4Signature.getSignedData(new File(str).length(), hashingInfo, findSigningInfoForBlockId));
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(Integer.valueOf(convertToContentDigestType(hashingInfo.hashAlgorithm)), hashingInfo.rawRootHash);
        return new VerifiedSigner(new Certificate[]{verifySigner.first}, verifySigner.second, arrayMap);
    }

    private static V4Signature.SigningInfo findSigningInfoForBlockId(V4Signature.SigningInfos signingInfos, int i) throws SignatureNotFoundException {
        if (i == -1 || i == -262969152) {
            return signingInfos.signingInfo;
        }
        for (V4Signature.SigningInfoBlock signingInfoBlock : signingInfos.signingInfoBlocks) {
            if (i == signingInfoBlock.blockId) {
                try {
                    return V4Signature.SigningInfo.fromByteArray(signingInfoBlock.signingInfo);
                } catch (IOException e) {
                    throw new SecurityException("Failed to read V4 signature block: " + signingInfoBlock.blockId, e);
                }
            }
        }
        throw new SecurityException("Failed to find V4 signature block corresponding to V3 blockId: " + i);
    }

    private static Pair<Certificate, byte[]> verifySigner(V4Signature.SigningInfo signingInfo, byte[] bArr) throws SecurityException {
        if (!ApkSigningBlockUtils.isSupportedSignatureAlgorithm(signingInfo.signatureAlgorithmId)) {
            throw new SecurityException("No supported signatures found");
        }
        int i = signingInfo.signatureAlgorithmId;
        byte[] bArr2 = signingInfo.signature;
        byte[] bArr3 = signingInfo.publicKey;
        byte[] bArr4 = signingInfo.certificate;
        String signatureAlgorithmJcaKeyAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(i);
        Pair<String, ? extends AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i);
        String str = signatureAlgorithmJcaSignatureAlgorithm.first;
        AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second;
        try {
            PublicKey generatePublic = KeyFactory.getInstance(signatureAlgorithmJcaKeyAlgorithm).generatePublic(new X509EncodedKeySpec(bArr3));
            Signature signature = Signature.getInstance(str);
            signature.initVerify(generatePublic);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(bArr);
            if (!signature.verify(bArr2)) {
                throw new SecurityException(str + " signature did not verify");
            }
            try {
                try {
                    VerbatimX509Certificate verbatimX509Certificate = new VerbatimX509Certificate((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr4)), bArr4);
                    if (!Arrays.equals(bArr3, verbatimX509Certificate.getPublicKey().getEncoded())) {
                        throw new SecurityException("Public key mismatch between certificate and signature record");
                    }
                    return Pair.create(verbatimX509Certificate, signingInfo.apkDigest);
                } catch (CertificateException e) {
                    throw new SecurityException("Failed to decode certificate", e);
                }
            } catch (CertificateException e2) {
                throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e2);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e3) {
            throw new SecurityException("Failed to verify " + str + " signature", e3);
        }
    }

    private static int convertToContentDigestType(int i) throws SecurityException {
        if (i == 1) {
            return 3;
        }
        throw new SecurityException("Unsupported hashAlgorithm: " + i);
    }

    public static class VerifiedSigner {
        public final byte[] apkDigest;
        public final Certificate[] certs;
        public final Map<Integer, byte[]> contentDigests;

        public VerifiedSigner(Certificate[] certificateArr, byte[] bArr, Map<Integer, byte[]> map) {
            this.certs = certificateArr;
            this.apkDigest = bArr;
            this.contentDigests = map;
        }
    }
}
